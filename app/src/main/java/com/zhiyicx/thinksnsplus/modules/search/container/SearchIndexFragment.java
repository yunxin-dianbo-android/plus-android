package com.zhiyicx.thinksnsplus.modules.search.container;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhiyicx.baseproject.base.TSFragment;
import com.zhiyicx.common.utils.DeviceUtils;
import com.zhiyicx.common.utils.StatusBarUtils;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.modules.q_a.search.list.IHistoryCententClickListener;
import com.zhiyicx.thinksnsplus.modules.search.adapter.SearchHotAdapter;
import com.zhiyicx.thinksnsplus.widget.FluidLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * ThinkSNS Plus
 * Copyright (c) 2018 Chengdu ZhiYiChuangXiang Technology Co., Ltd.
 *
 * @Author Jliuer
 * @Date 2018/08/06/10:18
 * @Email Jliuer@aliyun.com
 * @Description
 */
public class SearchIndexFragment extends TSFragment implements IHistoryCententClickListener {
    //        @BindView(R.id.v_status_bar_placeholder)
//        View vStatusBarPlaceholder;
    @BindView(R.id.iv_clear_history)
    ImageView ivClearHistory;
    @BindView(R.id.fl_search_history)
    FluidLayout flSearchHistory;
    @BindView(R.id.rv_hot_search)
    RecyclerView rvHotSearch;
    Unbinder unbinder;


//    @BindView(R.id.v_status_bar_placeholder)
//    View vStatusBarPlaceholder;
//    @BindView(R.id.fragment_info_search_edittext)
//    DeleteEditText mFragmentInfoSearchEdittext;

//    @BindView(R.id.fragment_info_search_cancle)
//    TextView mFragmentInfoSearchCancle;

//    @BindView(R.id.fragment_info_search_container)
//    RelativeLayout mFragmentInfoSearchContainer;

//    @BindView(R.id.fragment_container)
//    FrameLayout mFragmentContainer;

//    private boolean hasViewpager;

//    private SearchHistoryViewPagerContainerFragment mSearchHistoryViewPagerContainerFragment;

//    @Override
//    protected View getRightViewOfMusicWindow() {
//        return mFragmentInfoSearchCancle;
//    }

    @Override
    protected int getBodyLayoutId() {
        return R.layout.fragment_search_index_layout;
    }


    @Override
    protected boolean showToolBarDivider() {
        return false;
    }

    @Override
    protected void initView(View rootView) {
//        SearchHistoryFragment historyFragment = new SearchHistoryFragment();
//        historyFragment.setIHistoryCententClickListener(this);
//        ActivityUtils.addFragmentToActivity(getActivity().getSupportFragmentManager()
//                , historyFragment
//                , R.id.fragment_container);
        super.initView(rootView);
        initListener();
        flSearchHistory.removeAllViews();
        FluidLayout.LayoutParams params = new FluidLayout.LayoutParams(
                FluidLayout.LayoutParams.WRAP_CONTENT,
                FluidLayout.LayoutParams.WRAP_CONTENT
        );
        for (int i = 0; i < 10; i++) {
            View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.item_fluid_search_history_layout, flSearchHistory, false);
            ((TextView) contentView.findViewById(R.id.tv_search_history)).setText("搜索历史记录" + i);
            flSearchHistory.addView(contentView, params);
        }
        List<String> datas = new ArrayList<>();
        rvHotSearch.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        SearchHotAdapter adapter = new SearchHotAdapter();
        datas.add("热门搜索1");
        datas.add("热门");
        datas.add("热门搜索3");
        datas.add("热门搜索4");
        datas.add("热门搜索5");
        datas.add("热门搜索6");
        datas.add("热门搜索热门搜索7");
        datas.add("热门搜索8");
        datas.add("热门搜索9");
        datas.add("热门10");
        datas.add("热");
        datas.add("热水电费水电费");
        adapter.setDatas(datas);
        rvHotSearch.setAdapter(adapter);

//        initToolBar();
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
    protected boolean showToolbar() {
        return false;
    }


    @Override
    public void onBackPressed() {
        mActivity.finish();
    }

    @Override
    protected void initData() {

    }


//    @OnClick({R.id.fragment_info_search_cancle})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.fragment_info_search_cancle:
//                mActivity.finish();
//                break;
//            default:
//        }
//    }

    @Override
    public void onContentClick(String str) {
        doSearch(str);
    }

    private void initListener() {
//        RxTextView.editorActionEvents(mFragmentInfoSearchEdittext).subscribe(textViewEditorActionEvent -> {
//            if (textViewEditorActionEvent.actionId() == EditorInfo.IME_ACTION_SEARCH) {
//                if (!TextUtils.isEmpty(mFragmentInfoSearchEdittext.getText().toString())) {
//                    doSearch(mFragmentInfoSearchEdittext.getText().toString());
//                    DeviceUtils.hideSoftKeyboard(getContext(), mFragmentInfoSearchEdittext);
//                }
//            }
//        });
    }

    private void doSearch(String str) {
//        if (!hasViewpager) {
//            hasViewpager = true;
//            Bundle bundle = new Bundle();
//            bundle.putString(SearchHistoryViewPagerContainerFragment.KEY_WORD, str);
//
//            mSearchHistoryViewPagerContainerFragment = SearchHistoryViewPagerContainerFragment.newInstance(bundle);
//            ActivityUtils.replaceFragmentToActivity(getActivity().getSupportFragmentManager()
//                    , mSearchHistoryViewPagerContainerFragment
//                    , R.id.fragment_container);
//        }
//        mSearchHistoryViewPagerContainerFragment.onSearhChanged(str);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
