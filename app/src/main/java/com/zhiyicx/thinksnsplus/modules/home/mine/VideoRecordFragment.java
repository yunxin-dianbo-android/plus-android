package com.zhiyicx.thinksnsplus.modules.home.mine;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhiyicx.baseproject.base.TSListFragment;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.data.beans.AdListBeanV2;
import com.zhiyicx.thinksnsplus.data.beans.VideoListBean;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicBannerHeader;
import com.zhiyicx.thinksnsplus.modules.home.mine.adapter.VideoRecordItem;
import com.zhiyicx.thinksnsplus.modules.settings.aboutus.CustomWEBActivity;
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
    private DynamicBannerHeader mDynamicBannerHeader;
    public static VideoRecordFragment newInstance() {
        VideoRecordFragment videoRecordFragment = new VideoRecordFragment();
        return videoRecordFragment;
    }

    View vStatusBarPlaceHolder;

    @Override
    public void onStart() {
        super.onStart();
        if (mDynamicBannerHeader != null) {
            mDynamicBannerHeader.startBanner();
        }
    }

    @Override
    public void onAdDataResSuccessed(final List<AdListBeanV2> listBeanV2s) {
        if (mHeaderAndFooterWrapper != null && listBeanV2s != null && listBeanV2s.size() > 0) {
            List<String> advertTitle = new ArrayList<>();
            List<String> advertUrls = new ArrayList<>();
            List<String> advertLinks = new ArrayList<>();

            for (AdListBeanV2 advert : listBeanV2s) {
                advertTitle.add(advert.getTitle());
                advertUrls.add(advert.getData().getImage());
                advertLinks.add(advert.getData().getLink());
//                 if ("html".equals(advert.getType())) {
//                     showStickyHtmlMessage((String) advert.getData().);
//                 }
            }
            if (advertUrls.isEmpty()) {
                return;
            }
            mDynamicBannerHeader = new DynamicBannerHeader(mActivity);
            mDynamicBannerHeader.setHeadlerClickEvent(new DynamicBannerHeader.DynamicBannerHeadlerClickEvent() {
                @Override
                public void headClick(int position) {
                    toAdvert(listBeanV2s.get(position).getData().getLink(), listBeanV2s.get(position).getTitle());
                }
            });
            DynamicBannerHeader.DynamicBannerHeaderInfo headerInfo = mDynamicBannerHeader.new
                    DynamicBannerHeaderInfo();
            headerInfo.setTitles(advertTitle);
            headerInfo.setLinks(advertLinks);
            headerInfo.setUrls(advertUrls);
            headerInfo.setDelay(4000);
            headerInfo.setOnBannerListener(position -> {

            });
            mDynamicBannerHeader.setHeadInfo(headerInfo);
            mHeaderAndFooterWrapper.addHeaderView(mDynamicBannerHeader.getDynamicBannerHeader());
//             mLinearDecoration.setHeaderCount(mHeaderAndFooterWrapper.getHeadersCount());
//             mLinearDecoration.setFooterCount(mHeaderAndFooterWrapper.getFootersCount());

        }
    }
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
        if(mPresenter !=null){
            mPresenter.getAdData();
        }
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
        int realPosition = position-mHeaderAndFooterWrapper.getHeadersCount();
        if (mListDatas.get(realPosition).isEditMode) {
            mListDatas.get(realPosition).isChecked = !mListDatas.get(realPosition).isChecked;
            ImageView ivChechedStatus = holder.itemView.findViewById(R.id.iv_checked_status);
            if (mListDatas.get(realPosition).isChecked) {
                ivChechedStatus.setImageResource(R.mipmap.ic_video_record_checked);
            } else {
                ivChechedStatus.setImageResource(R.mipmap.ic_video_record_unchecked);
            }
        } else {
            VideoDetailActivity.starVideoDetailActivity(getContext(), mListDatas.get(realPosition).getVideo());
        }
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        return false;
    }
    private void toAdvert(String link, String title) {
        CustomWEBActivity.startToWEBActivity(getActivity(), link, title);
    }

}
