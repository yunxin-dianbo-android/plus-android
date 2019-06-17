package com.zhiyicx.thinksnsplus.modules.channel;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.zhiyicx.baseproject.base.TSListFragment;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.data.beans.DynamicDetailBeanV2;
import com.zhiyicx.thinksnsplus.modules.channel.adapter.FilterChannelTagAdapter;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListBaseItem4Video;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


import rx.functions.Action1;

import static com.zhiyicx.common.config.ConstantConfig.JITTER_SPACING_TIME;

public class VideoChannelFragment2 extends TSListFragment<VideoChannelFragmentContract.Presenter, DynamicDetailBeanV2> implements VideoChannelFragmentContract.View, MultiItemTypeAdapter.OnItemClickListener {
    RecyclerView rvFilteringCondition1;
    RecyclerView rvFilteringCondition2;
    //    @BindView(R.id.rv_filtering_condition_3)
    RecyclerView rvFilteringCondition3;
    //    @BindView(R.id.rv_filtering_condition_4)
    RecyclerView rvFilteringCondition4;
    //    @BindView(R.id.rv_filtering_condition_5)
    RecyclerView rvFilteringCondition5;
    //    @BindView(R.id.ll_filtering_channel_content)
    LinearLayout llFilteringChannelContent;
    //    @BindView(R.id.ll_filter_rv_parent)
    LinearLayout llFilterRVParent;
    //    @BindView(R.id.rl_content)
    RelativeLayout rlContent;
    List<String> testData1 = new ArrayList<>();
//    @BindView(R.id.iv_filter_content_arrow)
    ImageView ivFilterContentArrow;
    //    @BindView(R.id.tv_filter_content)
    TextView tvFilterContent;
    LinearLayout llChoosedChannel;

    FilterChannelTagAdapter filterChannelTagAdapter1;
    FilterChannelTagAdapter filterChannelTagAdapter2;
    FilterChannelTagAdapter filterChannelTagAdapter3;
    FilterChannelTagAdapter filterChannelTagAdapter4;
    FilterChannelTagAdapter filterChannelTagAdapter5;

//    @Inject
//    VideoChannelFragmentPresenter mPresenter;

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);

//        View channalView = LayoutInflater.from(getContext()).inflate(, llRoot, false);
//        llRoot.addView(channalView, 0);

        rvFilteringCondition1 = rootView.findViewById(R.id.rv_filtering_condition_1);
        rvFilteringCondition2 = rootView.findViewById(R.id.rv_filtering_condition_2);
        rvFilteringCondition3 = rootView.findViewById(R.id.rv_filtering_condition_3);
        rvFilteringCondition4 = rootView.findViewById(R.id.rv_filtering_condition_4);
        rvFilteringCondition5 = rootView.findViewById(R.id.rv_filtering_condition_5);
        llFilteringChannelContent = rootView.findViewById(R.id.ll_filtering_channel_content);
        llFilterRVParent = rootView.findViewById(R.id.ll_filter_rv_parent);
        ivFilterContentArrow = rootView.findViewById(R.id.iv_filter_content_arrow);
        tvFilterContent = rootView.findViewById(R.id.tv_filter_content);
        llChoosedChannel = rootView.findViewById(R.id.ll_choosed_channel);
        initToolBar();
    }

    private void initToolBar() {
//        // toolBar设置状态栏高度的marginTop
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, DeviceUtils
//                .getStatuBarHeight(getContext()));
//        vStatusBarPlaceholder.setLayoutParams(layoutParams);
//        // 适配非6.0以上、非魅族系统、非小米系统状态栏
//        if (getActivity() != null && StatusBarUtils.intgetType(getActivity().getWindow()) == 0) {
////            vStatusBarPlaceholder.setBackgroundResource(R.color.themeColor);
//            vStatusBarPlaceholder.setBackgroundResource(R.drawable.common_statubar_bg);
//        }
//        //不需要返回键
    }



    @Override
    protected boolean showToolBarDivider() {
        return false;
    }

    @Override
    protected int getBodyLayoutId() {
        return R.layout.fragment_video_channel;
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
        return "频道名";
    }


    public static VideoChannelFragment2 newInstance(Bundle args) {
        VideoChannelFragment2 fragment = new VideoChannelFragment2();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
//        unbinder = ButterKnife.bind(this, rootView);
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
        testData1.add("33333");
        testData1.add("44444");
        rvFilteringCondition1.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.HORIZONTAL, false));
        rvFilteringCondition2.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.HORIZONTAL, false));
        rvFilteringCondition3.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.HORIZONTAL, false));
        rvFilteringCondition4.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.HORIZONTAL, false));
        rvFilteringCondition5.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.HORIZONTAL, false));
        filterChannelTagAdapter1 = new FilterChannelTagAdapter(getContext(), R.layout.item_filter_video_channel, testData1);
        filterChannelTagAdapter2 = new FilterChannelTagAdapter(getContext(), R.layout.item_filter_video_channel, testData1);
        filterChannelTagAdapter3 = new FilterChannelTagAdapter(getContext(), R.layout.item_filter_video_channel, testData1);
        filterChannelTagAdapter4 = new FilterChannelTagAdapter(getContext(), R.layout.item_filter_video_channel, testData1);
        filterChannelTagAdapter5 = new FilterChannelTagAdapter(getContext(), R.layout.item_filter_video_channel, testData1);

        rvFilteringCondition1.setAdapter(filterChannelTagAdapter1);
        rvFilteringCondition2.setAdapter(filterChannelTagAdapter2);
        rvFilteringCondition3.setAdapter(filterChannelTagAdapter3);
        rvFilteringCondition4.setAdapter(filterChannelTagAdapter4);
        rvFilteringCondition5.setAdapter(filterChannelTagAdapter5);
//        rvFilteringCondition1.setVisibility(View.GONE);
//        rvFilteringCondition2.setVisibility(View.GONE);
//        rvFilteringCondition3.setVisibility(View.GONE);
//        rvFilteringCondition4.setVisibility(View.GONE);
//        rvFilteringCondition5.setVisibility(View.GONE);
//      llFilterRVParent.addView();
        RxView.clicks(llChoosedChannel)
                .throttleFirst(JITTER_SPACING_TIME, TimeUnit.SECONDS)
                .compose(this.<Void>bindToLifecycle())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (llFilterRVParent.getVisibility() == View.GONE) {
                            llFilterRVParent.setVisibility(View.VISIBLE);
                            ivFilterContentArrow.setImageResource(R.mipmap.ic_arrow_up);
                        } else {
                            llFilterRVParent.setVisibility(View.GONE);
                            ivFilterContentArrow.setImageResource(R.mipmap.ic_arrow_down);
                        }

                    }
                });
//        llFilteringChannelContent.set
//        if(mPresenter!=null){
//            mPresenter.requestNetData(1l,true);
//        }
    }
    MultiItemTypeAdapter myAdapter;
    @Override
    protected MultiItemTypeAdapter getAdapter() {
        myAdapter = new MultiItemTypeAdapter<>(getContext(), mListDatas);
        setAdapter(myAdapter, new DynamicListBaseItem4Video(getContext()));
        myAdapter.setOnItemClickListener(this);
        return myAdapter;
    }

    protected void setAdapter(MultiItemTypeAdapter adapter, ItemViewDelegate
            dynamicListBaseItem) {
        adapter.addItemViewDelegate(dynamicListBaseItem);
    }

    @Override
    protected void setRightClick() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        unbinder.unbind();
    }

//    @Override
//    public void setPresenter(Object presenter) {
//
//    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        return false;
    }


    @Override
    public void onNetResponseSuccess(List<DynamicDetailBeanV2> first) {

    }

    @Override
    public void onNetResponseSuccess(List data, boolean isLoadMore) {
        hideLoading();
        super.onNetResponseSuccess(data,isLoadMore);
//        myAdapter.setmDatas(data);
//         mRvList.setAdapter(mHeaderAndFooterWrapper);
//        mRvList.setBackgroundColor(getResources().getColor(R.color.color_EA3378));
    }

    @Override
    public void onCacheResponseSuccess(List data, boolean isLoadMore) {
        super.onCacheResponseSuccess(data,isLoadMore);
    }

    @Override
    public void refreshData(List datas) {
        super.refreshData(datas);
    }

    /**
     * 是否进入页面进行懒加载
     *
     * @return
     */
    protected boolean isLayzLoad() {
        return true;
    }

    /**
     * 进入页面是否自动调用下拉刷新请求新数据
     */
    protected boolean isNeedRefreshDataWhenComeIn() {
        return true;
    }

    /**
     * 当缓存为空的时候自动刷新
     *
     * @return
     */
    protected boolean isNeedRequestNetDataWhenCacheDataNull() {
        return true;
    }
}
