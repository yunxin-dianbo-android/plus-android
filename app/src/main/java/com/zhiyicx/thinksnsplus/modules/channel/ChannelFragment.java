package com.zhiyicx.thinksnsplus.modules.channel;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhiyicx.baseproject.base.TSFragment;
import com.zhiyicx.baseproject.widget.dragview.DragAdapter;
import com.zhiyicx.baseproject.widget.dragview.DragAdapter2;
import com.zhiyicx.baseproject.widget.dragview.DragGridView;
import com.zhiyicx.common.mvp.i.IBasePresenter;
import com.zhiyicx.common.utils.DeviceUtils;
import com.zhiyicx.common.utils.StatusBarUtils;
import com.zhiyicx.thinksnsplus.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ChannelFragment<P extends IBasePresenter> extends TSFragment {


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


    List<String> testData1 = new ArrayList<>();
    List<String> testData2 = new ArrayList<>();

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        initToolBar();
    }

    private void initToolBar() {
        // toolBar设置状态栏高度的marginTop
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, DeviceUtils
                .getStatuBarHeight(getContext()));
        vStatusBarPlaceholder.setLayoutParams(layoutParams);
        // 适配非6.0以上、非魅族系统、非小米系统状态栏
        if (getActivity() != null && StatusBarUtils.intgetType(getActivity().getWindow()) == 0) {
//            vStatusBarPlaceholder.setBackgroundResource(R.color.themeColor);
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

    //    protected void setToolBarTextColor() {
//        // 如果toolbar背景是白色的，就将文字颜色设置成黑色
//        if (showToolbar() && ContextCompat.getColor(getContext(), setToolBarBackgroud()) == Color.WHITE) {
//            mToolbarCenter.setTextColor(ContextCompat.getColor(getContext(), com.zhiyicx.baseproject.R.color.important_for_content));
//            mToolbarRight.setTextColor(ContextCompat.getColorStateList(getContext(), com.zhiyicx.baseproject.R.color.selector_text_color));
//            mToolbarRightLeft.setTextColor(ContextCompat.getColorStateList(getContext(), com.zhiyicx.baseproject.R.color.selector_text_color));
//            mToolbarLeft.setTextColor(ContextCompat.getColor(getContext(), getLeftTextColor()));
//        }
//    }

    public static ChannelFragment newInstance(Bundle args) {
        ChannelFragment fragment = new ChannelFragment();
        fragment.setArguments(args);
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
        testData1.add("1111");
        testData1.add("2222");
        testData1.add("3333");
        testData1.add("5555");
        testData1.add("6666");
        testData1.add("7777");
        testData1.add("8888");
        testData1.add("9999");
        testData1.add("1010");
        testData1.add("1212");
        testData1.add("1313");
        testData2.add("1414");
        testData2.add("1414");
        testData2.add("1515");
        testData2.add("11616");
        testData2.add("1717");
        testData2.add("1818");
        testData2.add("1919");
        dragAdapter1 = new DragAdapter(getContext(), testData1);
        dragAdapter2 = new DragAdapter2(getContext(), testData2);
        dragGridView4MyVideoChannel.setAdapter(dragAdapter1);
        dragGridView4OtherVideoChannel.setAdapter(dragAdapter2);
//        dragGridView4OtherVideoChannel.
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
}
