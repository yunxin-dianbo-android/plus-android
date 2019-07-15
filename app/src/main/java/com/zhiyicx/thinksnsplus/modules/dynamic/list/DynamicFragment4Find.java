package com.zhiyicx.thinksnsplus.modules.dynamic.list;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.zhiyicx.baseproject.config.TouristConfig;
import com.zhiyicx.common.widget.badgeview.DisplayUtil;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.config.EventBusTagConfig;
import com.zhiyicx.thinksnsplus.data.beans.TopSuperStarBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoChannelBean;
import com.zhiyicx.thinksnsplus.modules.circle.main.adapter.CircleHotAdapter;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListItemForAdvert;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListItemForEightImage;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListItemForFiveImage;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListItemForFourImage;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListItemForNineImage;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListItemForOneImage;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListItemForSevenImage;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListItemForShorVideo;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListItemForSixImage;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListItemForThreeImage;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListItemForTwoImage;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListItemForZeroImage;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListItemForwardAnswer;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListItemForwardCircle;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListItemForwardInfo;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListItemForwardMediaFeed;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListItemForwardPost;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListItemForwardQuestion;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListItemForwardWordFeed;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.util.List;

import static com.zhiyicx.baseproject.config.ApiConfig.DYNAMIC_TYPE_NEW;

public class DynamicFragment4Find extends DynamicFragment {
    DynamicListItemForShorVideo dynamicListItemForShorVideo;
    @Override
    protected MultiItemTypeAdapter getAdapter() {
        MultiItemTypeAdapter adapter = new MultiItemTypeAdapter<>(getContext(), mListDatas);
//        setAdapter(adapter, new DynamicListBaseItem4Video(getContext()));
        setAdapter(adapter, new DynamicListItemForZeroImage(getContext()));
        setAdapter(adapter, new DynamicListItemForOneImage(getContext()));
        setAdapter(adapter, new DynamicListItemForTwoImage(getContext()));
        setAdapter(adapter, new DynamicListItemForThreeImage(getContext()));
        setAdapter(adapter, new DynamicListItemForFourImage(getContext()));
        setAdapter(adapter, new DynamicListItemForFiveImage(getContext()));
        setAdapter(adapter, new DynamicListItemForSixImage(getContext()));
        setAdapter(adapter, new DynamicListItemForSevenImage(getContext()));
        setAdapter(adapter, new DynamicListItemForEightImage(getContext()));
        setAdapter(adapter, new DynamicListItemForNineImage(getContext()));
        setAdapter(adapter, new DynamicListItemForAdvert(getContext()));
        setAdapter(adapter, new DynamicListItemForwardWordFeed(mActivity));
        setAdapter(adapter, new DynamicListItemForwardMediaFeed(mActivity));
        setAdapter(adapter, new DynamicListItemForwardInfo(mActivity));
        setAdapter(adapter, new DynamicListItemForwardCircle(mActivity));
        setAdapter(adapter, new DynamicListItemForwardPost(mActivity));
        setAdapter(adapter, new DynamicListItemForwardQuestion(mActivity));
        setAdapter(adapter, new DynamicListItemForwardAnswer(mActivity));
        dynamicListItemForShorVideo = new DynamicListItemForShorVideo(getContext(), this) {
            @Override
            protected String videoFrom() {
                return mDynamicType;
            }
        };

        setAdapter(adapter, dynamicListItemForShorVideo);
        adapter.setOnItemClickListener(this);
        return adapter;
    }

    @Override
    protected void initData() {
        super.initData();

//        if (mPresenter != null) {
//            startRefrsh();
//            mIsLoadedNetData = true;
//        }
//        if (mDynamicPresenter != null) {
//            mDynamicPresenter.requestNetData(DEFAULT_PAGE_MAX_ID, false);
//        }
        if (mPresenter != null) {
            dynamicListItemForShorVideo.setTourist(mPresenter.isTourist());
            mPresenter.requestNetData(DEFAULT_PAGE_MAX_ID, false);
        }
    }

    public static DynamicFragment4Find newMyInstance(String dynamicType, OnCommentClickListener l) {
        DynamicFragment4Find fragment = new DynamicFragment4Find();
        fragment.setOnCommentClickListener(l);
        Bundle args = new Bundle();
        args.putString(BUNDLE_DYNAMIC_TYPE, dynamicType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void requestNetData(Long maxId, boolean isLoadMore) {
        super.requestNetData(maxId, isLoadMore);
//        requestNetData
    }

    @Override
    public void onNetSuccessHotSuperStar(List<TopSuperStarBean> topSuperStarBeans) {
//        super.onNetSuccessHotSuperStar(result);
        if (topSuperStarBeans != null && topSuperStarBeans.size() > 0)
            bindHotSuperStarView(topSuperStarBeans);
    }

    private void bindHotSuperStarView(List<TopSuperStarBean> topSuperStarBeans) {
        mHeaderAndFooterWrapper.clearHeaderView();
        View super10Star = LayoutInflater.from(getContext()).inflate(R.layout.item_circle_hot_star_layout, mRvList, false);
        mHeaderAndFooterWrapper.clearHeaderView();
        mHeaderAndFooterWrapper.addHeaderView(super10Star);
        mHeaderAndFooterWrapper.notifyDataSetChanged();
        RecyclerView rvSuperStarHead = super10Star.findViewById(R.id.rv_super_star_head);
        rvSuperStarHead.setLayoutManager(new GridLayoutManager(getContext(), 5));
        CircleHotAdapter circleHotAdapter = new CircleHotAdapter(getContext());
        circleHotAdapter.setDatas(topSuperStarBeans);
        rvSuperStarHead.setAdapter(circleHotAdapter);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) rvSuperStarHead.getLayoutParams();
        if (topSuperStarBeans.size() <= 5) {
            layoutParams.height = DisplayUtil.dp2px(getContext(), 80);
        } else {
            layoutParams.height = DisplayUtil.dp2px(getContext(), 170);
        }
    }

    /**
     * @return 是否开启 游客数据限制
     */
    @Override
    protected boolean isUseTouristLoadLimit() {
        return false;
    }



    @Override
    protected boolean useEventBus() {
        return true;
    }

    /**
     * 添加或者删除频道
     *
     * @param
     */
    @Subscriber(tag = EventBusTagConfig.EVENT_LOG_OUT, mode = ThreadMode.MAIN)
    public void logout(boolean isLogOut) {
        //刷新数据  重新登录
        initData();
    }

    @Subscriber(tag = EventBusTagConfig.EVENT_LOG_IN, mode = ThreadMode.MAIN)
    public void login(boolean isLogin) {
        //刷新数据  重新登录
        initData();
    }
//
//    /**
//     * 添加或者删除频道
//     *
//     * @param
//     */
//    @Subscriber(tag = EventBusTagConfig.MAIN_FRAGMENT_ADD_DELETE__CHANNEL, mode = ThreadMode.MAIN)
//    public void addOrDeleteChannel(List<VideoChannelBean> videoChannelBeans) {
//        //刷新数据  重新登录
//        initData();
//    }

}
