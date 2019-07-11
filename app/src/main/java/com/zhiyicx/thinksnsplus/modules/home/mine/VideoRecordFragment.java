package com.zhiyicx.thinksnsplus.modules.home.mine;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhiyicx.baseproject.base.TSListFragment;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.data.beans.VideoListBean;
import com.zhiyicx.thinksnsplus.modules.home.mine.adapter.VideoRecordItem;
import com.zhiyicx.thinksnsplus.modules.video.VideoDetailActivity;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

public class VideoRecordFragment extends TSListFragment<VideoRecordContract.Presenter, VideoListBean> implements MultiItemTypeAdapter.OnItemClickListener, VideoRecordContract.View<VideoRecordContract.Presenter> {
    MultiItemTypeAdapter adapter;


    LinearLayout llBottomMenu;

    TextView tvChoosedAll;
    TextView tvDelete;

    private boolean isChooseAll = false;

    public static VideoRecordFragment newInstance() {
        VideoRecordFragment videoRecordFragment = new VideoRecordFragment();
        return videoRecordFragment;
    }

    View vStatusBarPlaceHolder;

    @Override
    protected RecyclerView.Adapter getAdapter() {
        adapter = new MultiItemTypeAdapter<>(getContext(), mListDatas);
        VideoRecordItem videoRecordItem = new VideoRecordItem();
        adapter.addItemViewDelegate(videoRecordItem);

        adapter.setOnItemClickListener(this);
        return adapter;
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        vStatusBarPlaceHolder = rootView.findViewById(R.id.v_status_bar_placeholder);
        initStatusBar(vStatusBarPlaceHolder);
        llBottomMenu = rootView.findViewById(R.id.ll_bottom_menu);
        tvChoosedAll = rootView.findViewById(R.id.tv_choosed_all);
        tvDelete = rootView.findViewById(R.id.tv_delete);
        tvChoosedAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isChooseAll = !isChooseAll;
                List<VideoListBean> list = adapter.getDatas();
                for (int i = 0; i < list.size(); i++) {
                    VideoListBean videoListBean = list.get(i);
                    if (isChooseAll) {
                        videoListBean.isChecked = true;
                    } else {
                        videoListBean.isChecked = false;
                    }
                }
                if(isChooseAll){
                    tvChoosedAll.setText("全不选");
                }else{
                    tvChoosedAll.setText("全选");
                }
                mHeaderAndFooterWrapper.notifyDataSetChanged();
            }
        });
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isChooseAll) {
                    mPresenter.deleteVideoRecord("all");
                    adapter.getDatas().clear();
                    mHeaderAndFooterWrapper.notifyDataSetChanged();
                } else {
                    List<VideoListBean> list = adapter.getDatas();
                    List<VideoListBean> needDelete = new ArrayList<>();
                    List<Integer> needDeleteIds = new ArrayList<>();
                    for (VideoListBean videoListBean : list) {
                        if (videoListBean.isChecked) {
                            needDelete.add(videoListBean);
                            needDeleteIds.add(videoListBean.getVideo_id());
                        }
                    }
                    // TODO: 2019/6/30 调用删除接口
//                   int[] intIds =  needDeleteIds.toArray();
                    String ids = needDeleteIds.toString();
                    mPresenter.deleteVideoRecord(ids);
                    list.removeAll(needDelete);
                    mHeaderAndFooterWrapper.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected void setRightClick() {
        List<VideoListBean> list = adapter.getDatas();
        isChooseAll = false;
        tvChoosedAll.setText("全选");
        for (int i = 0; i < list.size(); i++) {
            VideoListBean videoListBean = list.get(i);
            videoListBean.isChecked = false;
            videoListBean.isEditMode = !videoListBean.isEditMode;
            if (i == 0) {
                if (videoListBean.isEditMode) {
                    llBottomMenu.setVisibility(View.VISIBLE);
                } else {
                    llBottomMenu.setVisibility(View.GONE);
                }
            }
        }
//        adapter.notifyDataSetChanged();
        mHeaderAndFooterWrapper.notifyDataSetChanged();
        if (mToolbarRight.getText().equals("编辑")) {
            llBottomMenu.setVisibility(View.VISIBLE);
            mToolbarRight.setText("取消");
        }else{
            llBottomMenu.setVisibility(View.GONE);
            mToolbarRight.setText("编辑");
        }

    }

    @Override
    protected String setRightTitle() {
        return "编辑";
    }

    @Override
    protected void initData() {
        super.initData();
        startRefrsh();
    }

    @Override
    protected String setCenterTitle() {
        return "观看历史";
    }


    @Override
    protected boolean showToolBarDivider() {
        return false;
    }

    @Override
    protected boolean setUseSatusbar() {
        return true;
    }

    @Override
    protected boolean setUseStatusView() {
        return false;
    }

    @Override
    protected int getToolBarLayoutId() {
        return R.layout.toolbar_custom_contain_status_bar;
    }

    @Override
    protected int getBodyLayoutId() {
        return R.layout.fragment_video_record_layout;
    }


    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
        if (mListDatas.get(position).isEditMode) {
            mListDatas.get(position).isChecked = !mListDatas.get(position).isChecked;
            ImageView ivChechedStatus = holder.itemView.findViewById(R.id.iv_checked_status);
            if (mListDatas.get(position).isChecked) {
                ivChechedStatus.setImageResource(R.mipmap.ic_video_record_checked);
            } else {
                ivChechedStatus.setImageResource(R.mipmap.ic_video_record_unchecked);
            }
        } else {
            VideoDetailActivity.starVideoDetailActivity(getContext(), mListDatas.get(position).getVideo());
        }
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        return false;
    }

}
