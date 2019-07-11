//package com.zhiyicx.thinksnsplus.modules.videodetail;
//
//import android.os.Bundle;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.OrientationHelper;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.jakewharton.rxbinding.view.RxView;
//import com.zhiyicx.baseproject.base.TSFragment;
//import com.zhiyicx.common.mvp.i.IBasePresenter;
//import com.zhiyicx.thinksnsplus.R;
//import com.zhiyicx.thinksnsplus.modules.channel.adapter.FilterChannelTagAdapter;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.Unbinder;
//import rx.functions.Action1;
//
//import static com.zhiyicx.common.config.ConstantConfig.JITTER_SPACING_TIME;
//
//public class VideoDetailFragment<P extends IBasePresenter> extends TSFragment {
//
//    //    @BindView(R.id.v_status_bar_placeholder)
////    View vStatusBarPlaceholder;
//    @BindView(R.id.rv_filtering_condition_1)
//    RecyclerView rvFilteringCondition1;
//    @BindView(R.id.rv_filtering_condition_2)
//    RecyclerView rvFilteringCondition2;
//    @BindView(R.id.rv_filtering_condition_3)
//    RecyclerView rvFilteringCondition3;
//    @BindView(R.id.rv_filtering_condition_4)
//    RecyclerView rvFilteringCondition4;
//    @BindView(R.id.rv_filtering_condition_5)
//    RecyclerView rvFilteringCondition5;
//    @BindView(R.id.ll_filtering_channel_content)
//    LinearLayout llFilteringChannelContent;
//    @BindView(R.id.ll_filter_rv_parent)
//    LinearLayout llFilterRVParent;
//    //    @BindView(R.id.rv_video)
////    RecyclerView rvVideo;
//    @BindView(R.id.rl_content)
//    RelativeLayout rlContent;
//    List<String> testData1 = new ArrayList<>();
//    Unbinder unbinder;
//    @BindView(R.id.iv_filter_content_arrow)
//    ImageView ivFilterContentArrow;
//    @BindView(R.id.tv_filter_content)
//    TextView tvFilterContent;
//
//    FilterChannelTagAdapter filterChannelTagAdapter1;
//    FilterChannelTagAdapter filterChannelTagAdapter2;
//    FilterChannelTagAdapter filterChannelTagAdapter3;
//    FilterChannelTagAdapter filterChannelTagAdapter4;
//    FilterChannelTagAdapter filterChannelTagAdapter5;
//
//    @Override
//    protected void initView(View rootView) {
//        super.initView(rootView);
//        initToolBar();
//    }
//
//    private void initToolBar() {
//
//    }
//
//    @Override
//    protected boolean showToolBarDivider() {
//        return false;
//    }
//
//    @Override
//    protected int getBodyLayoutId() {
//        return R.layout.fragment_video_channel;
//    }
//
//    @Override
//    protected boolean showToolbar() {
//        return false;
//    }
//
//    @Override
//    protected boolean setUseSatusbar() {
//        return true;
//    }
//
//    @Override
//    protected boolean setUseStatusView() {
//        return true;
//    }
//
//
//    @Override
//    protected int setLeftImg() {
//        return R.mipmap.ic_back;
//    }
//
//
//    protected void setToolBarTextColor() {
//        // 如果toolbar背景是白色的，就将文字颜色设置成黑色
//        mToolbarCenter.setTextColor(ContextCompat.getColor(getContext(), com.zhiyicx.baseproject.R.color.white));
//        mToolbarRight.setTextColor(ContextCompat.getColorStateList(getContext(), com.zhiyicx.baseproject.R.color.white));
//        mToolbarRightLeft.setTextColor(ContextCompat.getColorStateList(getContext(), com.zhiyicx.baseproject.R.color.white));
//        mToolbarLeft.setTextColor(ContextCompat.getColor(getContext(), getLeftTextColor()));
//    }
//
//    @Override
//    protected String setCenterTitle() {
//        return "频道名";
//    }
//
//
//    public static VideoDetailFragment newInstance(Bundle args) {
//        VideoDetailFragment fragment = new VideoDetailFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // TODO: inflate a fragment view
//        View rootView = super.onCreateView(inflater, container, savedInstanceState);
//        unbinder = ButterKnife.bind(this, rootView);
//        return rootView;
//    }
//
//    @Override
//    protected void initData() {
//        super.initData();
//        testData1.add("1111");
//        testData1.add("2222");
//        testData1.add("3333");
//        testData1.add("5555");
//        testData1.add("6666");
//        testData1.add("7777");
//        testData1.add("8888");
//        testData1.add("9999");
//        testData1.add("1010");
//        testData1.add("1212");
//        testData1.add("1313");
//        testData1.add("33333");
//        testData1.add("44444");
//        rvFilteringCondition1.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.HORIZONTAL, false));
//        rvFilteringCondition2.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.HORIZONTAL, false));
//        rvFilteringCondition3.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.HORIZONTAL, false));
//        rvFilteringCondition4.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.HORIZONTAL, false));
//        rvFilteringCondition5.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.HORIZONTAL, false));
//        filterChannelTagAdapter1 = new FilterChannelTagAdapter(getContext(), R.layout.item_filter_video_channel, testData1);
//        filterChannelTagAdapter2 = new FilterChannelTagAdapter(getContext(), R.layout.item_filter_video_channel, testData1);
//        filterChannelTagAdapter3 = new FilterChannelTagAdapter(getContext(), R.layout.item_filter_video_channel, testData1);
//        filterChannelTagAdapter4 = new FilterChannelTagAdapter(getContext(), R.layout.item_filter_video_channel, testData1);
//        filterChannelTagAdapter5 = new FilterChannelTagAdapter(getContext(), R.layout.item_filter_video_channel, testData1);
//
//        rvFilteringCondition1.setAdapter(filterChannelTagAdapter1);
//        rvFilteringCondition2.setAdapter(filterChannelTagAdapter2);
//        rvFilteringCondition3.setAdapter(filterChannelTagAdapter3);
//        rvFilteringCondition4.setAdapter(filterChannelTagAdapter4);
//        rvFilteringCondition5.setAdapter(filterChannelTagAdapter5);
//
////        llFilterRVParent.addView();
//        RxView.clicks(tvFilterContent)
//                .throttleFirst(JITTER_SPACING_TIME, TimeUnit.SECONDS)
//                .compose(this.<Void>bindToLifecycle())
//                .subscribe(new Action1<Void>() {
//                    @Override
//                    public void call(Void aVoid) {
//                        if (llFilterRVParent.getVisibility() == View.GONE) {
//                            llFilterRVParent.setVisibility(View.VISIBLE);
//                            ivFilterContentArrow.setImageResource(R.mipmap.ic_arrow_up);
//                        } else {
//                            llFilterRVParent.setVisibility(View.GONE);
//                            ivFilterContentArrow.setImageResource(R.mipmap.ic_arrow_down);
//                        }
//
//                    }
//                });
////        llFilteringChannelContent.set
//    }
//
//    @Override
//    protected void setRightClick() {
//
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }
//}
