package com.zhiyicx.thinksnsplus.modules.channel;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhiyicx.baseproject.base.TSFragment;
import com.zhiyicx.common.base.BaseApplication;
import com.zhiyicx.common.utils.DeviceUtils;
import com.zhiyicx.common.utils.StatusBarUtils;
import com.zhiyicx.common.utils.ToastUtils;
import com.zhiyicx.common.utils.log.LogUtils;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.base.AppApplication;
import com.zhiyicx.thinksnsplus.config.EventBusTagConfig;
import com.zhiyicx.thinksnsplus.data.beans.VideoChannelBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoChannelListBean;
import com.zhiyicx.thinksnsplus.widget.dragview.DragAdapter;
import com.zhiyicx.thinksnsplus.widget.dragview.DragAdapter2;
import com.zhiyicx.thinksnsplus.widget.dragview.DragGridView;
import com.zhiyicx.thinksnsplus.widget.dragview.listener.OnDragItemClickLisnter;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ChannelFragment extends TSFragment implements ChannelFragmentContract.View {
    /*<P extends ChannelFragmentContract.Presenter>*/
    @BindView(R.id.v_status_bar_placeholder)
    View vStatusBarPlaceholder;
    @BindView(R.id.drag_grid_view_4_my_video_channel)
    DragGridView dragGridView4MyVideoChannel;
    @BindView(R.id.drag_grid_view_4_other_video_channel)
    DragGridView dragGridView4OtherVideoChannel;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.tv_other_channel_tip)
    TextView tvOtherChannelTip;
    @BindView(R.id.tv_my_channel_tip)
    TextView tvMyChannelTip;
    Unbinder unbinder;
    DragAdapter dragAdapter1;
    DragAdapter2 dragAdapter2;

    @Inject
    ChannelFragmentPresenter channelFragmentPresenter;
    List<VideoChannelBean> testData1 = new ArrayList<>();
    List<VideoChannelBean> testData2 = new ArrayList<>();


    private boolean isChanged = false;

    @Override
    public void showMessage(String message) {
        ToastUtils.showToast(message);
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        initToolBar();
        DaggerChannelPresenterComponent
                .builder()
                .appComponent(AppApplication.AppComponentHolder.getAppComponent())
                .channelPresenterModule(new ChannelPresenterModule(this))
                .build().inject(ChannelFragment.this);
    }


    private void initToolBar() {
        // toolBar设置状态栏高度的marginTop
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, DeviceUtils
                .getStatuBarHeight(getContext()));
        vStatusBarPlaceholder.setLayoutParams(layoutParams);
        // 适配非6.0以上、非魅族系统、非小米系统状态栏
        if (getActivity() != null && StatusBarUtils.intgetType(getActivity().getWindow()) == 0) {
//           vStatusBarPlaceholder.setBackgroundResource(R.color.themeColor);
            vStatusBarPlaceholder.setBackgroundResource(R.drawable.common_statubar_bg);
        }
        //不需要返回键
//        mTsvToolbar.setLeftImg(0);
//        mTsvToolbar.setLeftImg(R.mipmap.app_icon);
//        mTsvToolbar.setRightImg(R.mipmap.ic_home_show_more_channel, R.color.transparent);
////        mTsvToolbar.setRightClickListener(this, () -> startActivity(new Intent(mActivity, SearchContainerActivity.class)));
//        mTsvToolbar.setRightClickListener(this, () -> startActivity(new Intent(mActivity, VideoChannelActivity.class)));
    }

    @Override
    protected boolean showToolBarDivider() {
        return false;
    }

    @Override
    protected int getBodyLayoutId() {
        return R.layout.fragment_channel;
    }

    @Override
    protected boolean showToolbar() {
        return true;
    }

    @Override
    protected boolean setUseSatusbar() {
        return true;
    }

    @Override
    protected boolean setUseStatusView() {
        return true;
    }

//    @Override
//    protected int setToolBarBackgroud() {
//        return R.drawable.common_statubar_bg;
//    }

    @Override
    protected int setLeftImg() {
        return R.mipmap.ic_back;
    }


    @Override
    protected void setLeftClick() {
        if (isChanged) {
            AppApplication.setVideoChannelListBeans(testData1);
//            EventBus.getDefault().post(testData1, EventBusTagConfig.MAIN_FRAGMENT_ADD_DELETE__CHANNEL);
        }
        super.setLeftClick();
    }

    protected void setToolBarTextColor() {
        // 如果toolbar背景是白色的，就将文字颜色设置成黑色
        mToolbarCenter.setTextColor(ContextCompat.getColor(getContext(), com.zhiyicx.baseproject.R.color.white));
        mToolbarRight.setTextColor(ContextCompat.getColorStateList(getContext(), com.zhiyicx.baseproject.R.color.white));
        mToolbarRightLeft.setTextColor(ContextCompat.getColorStateList(getContext(), com.zhiyicx.baseproject.R.color.white));
        mToolbarLeft.setTextColor(ContextCompat.getColor(getContext(), getLeftTextColor()));
    }

    @Override
    protected String setCenterTitle() {
        return "频道管理";
    }

    @Override
    protected String setRightTitle() {
        return "编辑";
    }


    public static ChannelFragment newInstance() {
        ChannelFragment fragment = new ChannelFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initData() {
        super.initData();
        if (channelFragmentPresenter != null) {
            channelFragmentPresenter.requestNetData();
        }
        dragAdapter1 = new DragAdapter(getContext(), testData1);
        dragAdapter2 = new DragAdapter2(getContext(), testData2);
        dragGridView4MyVideoChannel.setAdapter(dragAdapter1);
        dragGridView4OtherVideoChannel.setAdapter(dragAdapter2);
        dragGridView4MyVideoChannel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (dragAdapter1.isModifyMode()) {
                    if (i < dragAdapter1.getFixNum()) {
                        //固定的无法删除
                        return;
                    }
                    isChanged = true;
                    VideoChannelBean item = testData1.get(i);
                    testData1.remove(i);
                    dragAdapter1.notifyDataSetChanged();
                    testData2.add(item);
                    dragAdapter2.notifyDataSetChanged();
                    channelFragmentPresenter.deleteVideoChannel(item.getId() + "");
                } else {
                    VideoChannelBean videoChannelBean = (VideoChannelBean) dragAdapter1.getItem(i);
                    EventBus.getDefault().post(videoChannelBean, EventBusTagConfig.MAIN_FRAGMENT_CHANG_CHANNEL);
                    getActivity().finish();
                }
            }
        });
        dragGridView4OtherVideoChannel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (dragAdapter2.isModifyMode()) {
                    isChanged = true;
                    VideoChannelBean item = testData2.get(i);
                    testData2.remove(i);
                    testData1.add(item);
                    dragAdapter1.notifyDataSetChanged();
                    dragAdapter2.notifyDataSetChanged();
                    channelFragmentPresenter.addVideoChannel(item.getId() + "");

                }
            }
        });

//        dragAdapter1.setOnDragItemClickLisnter(new OnDragItemClickLisnter() {
//            @Override
//            public void onItemClickListener(Object object, int position) {
//                if (dragAdapter1.isModifyMode()) {
//                    testData1.remove(position);
//                    dragAdapter1.notifyDataSetChanged();
//                    testData2.add((VideoChannelBean) object);
//                    dragAdapter2.notifyDataSetChanged();
//                }
//            }
//        });
//        dragAdapter2.setOnDragItemClickLisnter(new OnDragItemClickLisnter() {
//            @Override
//            public void onItemClickListener(Object object, int position) {
//                if (dragAdapter2.isModifyMode()) {
//                    testData2.remove(position);
//                    testData1.add((VideoChannelBean) object);
//                    dragAdapter1.notifyDataSetChanged();
//                    dragAdapter2.notifyDataSetChanged();
//                }
//            }
//        });
    }

    @Override
    protected void setRightClick() {
        dragAdapter1.setModifyMode(!dragAdapter1.isModifyMode(), true);
        dragAdapter2.setModifyMode(!dragAdapter2.isModifyMode(), false);
        if (dragAdapter1.isModifyMode()) {
            //可修改状态
            tvOtherChannelTip.setVisibility(View.VISIBLE);
            tvMyChannelTip.setVisibility(View.VISIBLE);
        } else {
            tvOtherChannelTip.setVisibility(View.GONE);
            tvMyChannelTip.setVisibility(View.GONE);
        }
        dragAdapter1.notifyDataSetChanged();
        dragAdapter2.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onNetResponseSuccess(VideoChannelListBean datas) {
        LogUtils.e("wulianshu", "onNetSuccess");
//        datas.user_channels.addAll(datas.other_channels);
        testData1.clear();
        testData2.clear();
        if (datas.default_channels != null) {
            testData1.addAll(datas.default_channels);
            dragAdapter1.setFixNum(datas.default_channels.size());
            dragGridView4MyVideoChannel.setmFixed_nums(datas.default_channels.size());
        } else {
            dragAdapter1.setFixNum(0);
        }
        if (datas.user_channels != null) {
            testData1.addAll(datas.user_channels);
        }
        if (datas.other_channels != null) {
            testData2.addAll(datas.other_channels);
        }


        dragAdapter1.notifyDataSetChanged();
        dragAdapter2.notifyDataSetChanged();

    }

    @Override
    public void showMsg(String msg) {
        ToastUtils.showToast(msg + "");
    }
}
