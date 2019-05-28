package com.zhiyicx.thinksnsplus.modules.circle.main;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.zhiyicx.baseproject.base.TSListFragment;
import com.zhiyicx.common.widget.badgeview.DisplayUtil;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.base.AppApplication;
import com.zhiyicx.thinksnsplus.data.beans.TopPostListBean;
import com.zhiyicx.thinksnsplus.data.beans.TopSuperStarBean;
import com.zhiyicx.thinksnsplus.modules.circle.main.adapter.CircleHotAdapter;
import com.zhiyicx.thinksnsplus.modules.circle.main.adapter.CircleHotItem;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

import javax.inject.Inject;


public class CircleHotFragment extends TSListFragment<CircleHotContract.Presenter, TopPostListBean> implements CircleHotContract.View/*, DynamicBannerHeader.DynamicBannerHeadlerClickEvent*/ {

    @Inject
    CircleHotPresenter mPresenter;


    @Override
    protected RecyclerView.Adapter getAdapter() {
        MultiItemTypeAdapter adapter = new MultiItemTypeAdapter<>(getContext(), mListDatas);
//        CircleSuperStar10Item circleSuperStar10Item = new CircleSuperStar10Item(mActivity);
        CircleHotItem circleHotItem = new CircleHotItem(mActivity);
//        adapter.addItemViewDelegate(circleSuperStar10Item);
        adapter.addItemViewDelegate(circleHotItem);
        return adapter;
    }

    public static CircleHotFragment newInstance() {
        CircleHotFragment circleMainFragment = new CircleHotFragment();
        return circleMainFragment;
    }
    @Override
    protected boolean showToolbar() {
        return false;
    }

    @Override
    protected void initData() {
        super.initData();
        DaggerCircleHotFragmentPresenterComponent
                .builder()
                .appComponent(AppApplication.AppComponentHolder.getAppComponent())
                .circleHotPresenterModule(new CircleHotPresenterModule(this))
                .build().inject(CircleHotFragment.this);
        mPresenter.requestNetData(DEFAULT_PAGE_MAX_ID, false);
//        initAdvert();
    }

    @Override
    protected boolean showToolBarDivider() {
        return false;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return super.getLayoutManager();
    }

    @Override
    public void onNetSuccessHotSuperStar(List<TopSuperStarBean> topSuperStarBeans) {
        Log.e("wulianshu", "topSuperStarBeans:" + topSuperStarBeans.size());
        if (topSuperStarBeans != null && topSuperStarBeans.size() > 0)
            bindHotSuperStarView(topSuperStarBeans);

    }

    private void bindHotSuperStarView(List<TopSuperStarBean> topSuperStarBeans) {
        mHeaderAndFooterWrapper.clearHeaderView();
        View super10Star = LayoutInflater.from(getContext()).inflate(R.layout.item_circle_hot_star_layout, mRvList, false);
        mHeaderAndFooterWrapper.addHeaderView(super10Star);
        mHeaderAndFooterWrapper.notifyDataSetChanged();
        RecyclerView rvSuperStarHead = super10Star.findViewById(R.id.rv_super_star_head);
        rvSuperStarHead.setLayoutManager(new GridLayoutManager(getContext(), 5));
        CircleHotAdapter circleHotAdapter = new CircleHotAdapter();
        circleHotAdapter.setDatas(topSuperStarBeans);
        rvSuperStarHead.setAdapter(circleHotAdapter);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) rvSuperStarHead.getLayoutParams();
        if (topSuperStarBeans.size() <= 5) {
            layoutParams.height = DisplayUtil.dp2px(getContext(), 80);
        } else {
            layoutParams.height = DisplayUtil.dp2px(getContext(), 170);
        }
    }

//    @Override
//    public void headClick(int position) {
//
//    }
}
