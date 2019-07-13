package com.zhiyicx.thinksnsplus.modules.channel;

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
import com.zhiyicx.thinksnsplus.data.beans.SuperStarBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoChannelBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoListBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoTagListBean;
import com.zhiyicx.thinksnsplus.modules.channel.adapter.FilterChannelTagAdapter;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListBaseItem4Video;
import com.zhiyicx.thinksnsplus.modules.video.VideoDetailActivity;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;


import rx.functions.Action1;

import static com.zhiyicx.common.config.ConstantConfig.JITTER_SPACING_TIME;

public class VideoChannelFragment2 extends TSListFragment<VideoChannelFragmentContract.Presenter, VideoListBean> implements VideoChannelFragmentContract.View, MultiItemTypeAdapter.OnItemClickListener {
    //    RecyclerView rvFilteringCondition1;
//    RecyclerView rvFilteringCondition2;
//    RecyclerView rvFilteringCondition3;
//    RecyclerView rvFilteringCondition4;
//    RecyclerView rvFilteringCondition5;
    LinearLayout llFilteringChannelContent;
    LinearLayout llFilterRVParent;
    RelativeLayout rlContent;
    //    List<String> testData1 = new ArrayList<>();
    ImageView ivFilterContentArrow;
    TextView tvFilterContent;
    LinearLayout llChoosedChannel;

    VideoChannelBean videoChannelBean;

    List<VideoListBean.TagsBean> choosedTags = new ArrayList<>();
//    FilterChannelTagAdapter filterChannelTagAdapter1;
//    FilterChannelTagAdapter filterChannelTagAdapter2;
//    FilterChannelTagAdapter filterChannelTagAdapter3;
//    FilterChannelTagAdapter filterChannelTagAdapter4;
//    FilterChannelTagAdapter filterChannelTagAdapter5;

    public SuperStarBean getSuperStarBean() {
        return superStarBean;
    }

    public VideoChannelBean getVideoChannelBean() {
        return videoChannelBean;
    }

    //    @Inject
//    VideoChannelFragmentPresenter mPresenter;
    private SuperStarBean superStarBean;


    @Override
    protected void initView(View rootView) {
        super.initView(rootView);

//        View channalView = LayoutInflater.from(getContext()).inflate(, llRoot, false);
//        llRoot.addView(channalView, 0);

//        rvFilteringCondition1 = rootView.findViewById(R.id.rv_filtering_condition_1);
//        rvFilteringCondition2 = rootView.findViewById(R.id.rv_filtering_condition_2);
//        rvFilteringCondition3 = rootView.findViewById(R.id.rv_filtering_condition_3);
//        rvFilteringCondition4 = rootView.findViewById(R.id.rv_filtering_condition_4);
//        rvFilteringCondition5 = rootView.findViewById(R.id.rv_filtering_condition_5);
        llFilteringChannelContent = rootView.findViewById(R.id.ll_filtering_channel_content);
        llFilterRVParent = rootView.findViewById(R.id.ll_filter_rv_parent);
        ivFilterContentArrow = rootView.findViewById(R.id.iv_filter_content_arrow);
        tvFilterContent = rootView.findViewById(R.id.tv_filter_content);
        llChoosedChannel = rootView.findViewById(R.id.ll_choosed_channel);

        llFilterRVParent.setVisibility(View.GONE);
        ivFilterContentArrow.setImageResource(R.mipmap.ic_arrow_down);
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
        return rootView;
    }

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getArguments();
        if (bundle != null) {
            superStarBean = bundle.getParcelable(SuperStarBean.class.getSimpleName());
            videoChannelBean = bundle.getParcelable(VideoChannelBean.class.getSimpleName());
        }
        mToolbarCenter.setVisibility(View.VISIBLE);
        if (superStarBean != null) {
            mToolbarCenter.setText(superStarBean.getName());
        } else if (videoChannelBean != null) {
            mToolbarCenter.setText(videoChannelBean.getName());
        }

        mPresenter.requestAllVideoTags();
        mPresenter.requestNetData(0l, false);
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

    /**
     * 设置 item 间距数值
     *
     * @return 上下间距
     */
    protected float getItemDecorationSpacing() {
        return 0f;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return super.getLayoutManager();
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
        VideoDetailActivity.starVideoDetailActivity(getContext(), mListDatas.get(position));
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        return false;
    }


//    @Override
//    public void onNetResponseSuccess(List<VideoListBean> first) {
//
//    }

    @Override
    public void onNetResponseSuccess(List data, boolean isLoadMore) {
        hideLoading();
        super.onNetResponseSuccess(data, isLoadMore);
//        myAdapter.setmDatas(data);
//         mRvList.setAdapter(mHeaderAndFooterWrapper);
//        mRvList.setBackgroundColor(getResources().getColor(R.color.color_EA3378));
    }

    @Override
    public void onCacheResponseSuccess(List data, boolean isLoadMore) {
        super.onCacheResponseSuccess(data, isLoadMore);
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

    @Override
    public void onAllVideoTagResponseSuccess(List<VideoTagListBean> videoTagListBeans) {
        if (videoTagListBeans != null && videoTagListBeans.size() > 0) {
//          LayoutInflater layoutInflater =
            for (VideoTagListBean videoTagListBean : videoTagListBeans) {
                RecyclerView recyclerView = (RecyclerView) LayoutInflater.from(mActivity).inflate(R.layout.item_video_tag_rv_layout, llFilterRVParent, false);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.HORIZONTAL, false));
                final List<VideoListBean.TagsBean> videoTags = videoTagListBean.getTags();
                VideoListBean.TagsBean tagsBean = new VideoListBean.TagsBean();
                tagsBean.setId(videoTagListBean.getId());
                tagsBean.setName(videoTagListBean.getName());
                tagsBean.setWeight(videoTagListBean.getWeight());
                tagsBean.isFirst = true;
                tagsBean.isChoosed = true;
                videoTags.add(0, tagsBean);
                //默认先选中第一个
                choosedTags.add(videoTags.get(0));
                final FilterChannelTagAdapter filterChannelTagAdapter = new FilterChannelTagAdapter(getContext(), R.layout.item_filter_video_channel, videoTags);
                filterChannelTagAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                        int replaceIndex = 0;
                        for (int i = 0; i < filterChannelTagAdapter.getDatas().size(); i++) {
                            VideoListBean.TagsBean tag = filterChannelTagAdapter.getDatas().get(i);
                            tag.isChoosed = false;

                            for (int j = 0; j < choosedTags.size(); j++) {
                                if (choosedTags.get(j).equals(tag)) {
                                    replaceIndex = j;
                                    break;
                                }
                            }

                        }
                        choosedTags.remove(replaceIndex);
                        if (replaceIndex >= choosedTags.size()) {
                            choosedTags.add(filterChannelTagAdapter.getDatas().get(position));
                        } else {
                            choosedTags.add(replaceIndex, filterChannelTagAdapter.getDatas().get(position));
                        }
                        filterChannelTagAdapter.getDatas().get(position).isChoosed = true;
                        filterChannelTagAdapter.notifyDataSetChanged();
                        mPresenter.requestNetData(0l, false);
                        setCurrentChoosedTagText();
                    }

                    @Override
                    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                        return false;
                    }
                });
                recyclerView.setAdapter(filterChannelTagAdapter);
                llFilterRVParent.addView(recyclerView);
            }
            setCurrentChoosedTagText();
        }

    }

    /**
     *
     */
    private void setCurrentChoosedTagText() {
        StringBuilder filterContent = new StringBuilder();
        for (int i = 0; i < choosedTags.size(); i++) {
            filterContent.append(choosedTags.get(i).getName() + " • ");
        }
        tvFilterContent.setText(filterContent.substring(0, filterContent.length() - 3));
    }

    @Override
    public List<VideoListBean.TagsBean> getChoosedVideoTags() {
        return choosedTags;
    }
}
