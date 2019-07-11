package com.zhiyicx.thinksnsplus.modules.search.container;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhiyicx.baseproject.base.TSFragment;
import com.zhiyicx.common.utils.ActivityUtils;
import com.zhiyicx.common.utils.ToastUtils;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.base.AppApplication;
import com.zhiyicx.thinksnsplus.data.beans.SearchHistoryBeanV2;
import com.zhiyicx.thinksnsplus.modules.q_a.search.list.IHistoryCententClickListener;
import com.zhiyicx.thinksnsplus.modules.search.adapter.SearchHotAdapter;
import com.zhiyicx.thinksnsplus.widget.FluidLayout;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

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
public class SearchIndexFragment extends TSFragment<SearchIndexContract.Presenter> implements SearchIndexContract.View<SearchIndexContract.Presenter>, IHistoryCententClickListener {
    @BindView(R.id.iv_clear_history)
    ImageView ivClearHistory;
    @BindView(R.id.ll_search_history_parent)
    LinearLayout llSearchHistoryParent;
    @BindView(R.id.fl_search_history)
    FluidLayout flSearchHistory;
    @BindView(R.id.rv_hot_search)
    RecyclerView rvHotSearch;
    @BindView(R.id.v_bottom_line)
    View vBottomLine;
    Unbinder unbinder;


    public void setiSetSearchEdittextContent(SearchContainerFragment2.IDoSearchCallBack iDoSearchCallBack) {
        this.iDoSearchCallBack = iDoSearchCallBack;
    }

    SearchContainerFragment2.IDoSearchCallBack iDoSearchCallBack;


    @Inject
    SearchIndexPresenter searchIndexPresenter;
    //    SearchHotAdapter adapter = new SearchHotAdapter();
    List<SearchHistoryBeanV2> searchHistoryBeanV2s = new ArrayList<>();

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

        super.initView(rootView);
        DaggerSearchIndexComponent
                .builder()
                .appComponent(AppApplication.AppComponentHolder.getAppComponent())
                .searchIndexPresenterModule(new SearchIndexPresenterModule(this))
                .build()
                .inject(this);
        initListener();
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

        if (searchIndexPresenter != null) {
            searchIndexPresenter.getSearchHistory();
            searchIndexPresenter.getHotSearchHistory();
        }
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
        ivClearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.clearSearchHistory();
                llSearchHistoryParent.setVisibility(View.GONE);
            }
        });
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

    @Override
    public void onGetSearchHistorySuccess(List<SearchHistoryBeanV2> datas) {
        Log.e("wulianshu", "onGetSearchHistorySuccess");
        if (datas == null || datas.size() == 0) {
            llSearchHistoryParent.setVisibility(View.GONE);
        } else {
            searchHistoryBeanV2s.addAll(datas);
            llSearchHistoryParent.setVisibility(View.VISIBLE);
            setSearchHistory();
        }
    }

    private void setSearchHistory() {
        llSearchHistoryParent.setVisibility(View.VISIBLE);
        flSearchHistory.removeAllViews();
        FluidLayout.LayoutParams params = new FluidLayout.LayoutParams(
                FluidLayout.LayoutParams.WRAP_CONTENT,
                FluidLayout.LayoutParams.WRAP_CONTENT
        );
        for (int i = 0; i < searchHistoryBeanV2s.size(); i++) {
            if (i > 16) {
                break;
            }
            final SearchHistoryBeanV2 searchHistoryBeanV2 = searchHistoryBeanV2s.get(i);
            View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.item_fluid_search_history_layout, flSearchHistory, false);
            ((TextView) contentView.findViewById(R.id.tv_search_history)).setText(searchHistoryBeanV2.getKeyword() + "");
            flSearchHistory.addView(contentView, params);
            contentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: 2019/6/26  do search
                    doSearch(searchHistoryBeanV2.getKeyword() + "");
                }
            });
        }
    }

    @Override
    public void onGetHotSearchSuccess(List<SearchHistoryBeanV2> searchHistoryBeanV2s) {
        Log.e("wulianshu", "onGetHotSearchSuccess");
        if (searchHistoryBeanV2s == null || searchHistoryBeanV2s.size() == 0) {
            vBottomLine.setVisibility(View.GONE);
        } else {
            vBottomLine.setVisibility(View.VISIBLE);
        }
        rvHotSearch.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvHotSearch.setAdapter(new CommonAdapter<SearchHistoryBeanV2>(mActivity, R.layout.item_search_hot_layout, searchHistoryBeanV2s) {
            @Override
            protected void convert(ViewHolder holder, SearchHistoryBeanV2 o, int position) {
//                SearchHotAdapter.MyViewHolder myViewHolder = (SearchHotAdapter.MyViewHolder) holder;
                TextView tvPosition = holder.getTextView(R.id.tv_position);
                tvPosition.setText("" + (position + 1));
                if (position < 3) {
                    tvPosition.setTextColor(Color.parseColor("#EA3378"));
                } else {
                    tvPosition.setTextColor(Color.parseColor("#ffffff"));
                }
                holder.getTextView(R.id.tv_search_history).setText(searchHistoryBeanV2s.get(position).getKeyword() + "");
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // TODO: 2019/6/26  do search
                        doSearch(o.getKeyword() + "");
                    }
                });
            }

        });

    }

    public void doSearch(String str) {
        if (TextUtils.isEmpty(str)) {
            ToastUtils.showToast(R.string.have_no_input_search_key_tip);
            return;
        }
        str = str.trim();
        if (iDoSearchCallBack != null) {
            iDoSearchCallBack.doSearch(str);
        }
//        Bundle bundle = new Bundle();
//        bundle.putString(SearchHistoryViewPagerContainerFragment.KEY_WORD, str);
//        SearchHistoryViewPagerContainerFragment mSearchHistoryViewPagerContainerFragment = SearchHistoryViewPagerContainerFragment.newInstance(bundle);
//        ActivityUtils.replaceFragmentToActivity(getActivity().getSupportFragmentManager()
//                , mSearchHistoryViewPagerContainerFragment
//                , R.id.fragment_container);
//        mSearchHistoryViewPagerContainerFragment.onSearhChanged(str);
//        addSearchHistory(str);
//        SearchHistoryBeanV2 needRemoveItem = null;
//        for (int i = 0; i < searchHistoryBeanV2s.size(); i++) {
//            if (str.equals(searchHistoryBeanV2s.get(i).getKeyword())) {
//                needRemoveItem = searchHistoryBeanV2s.get(i);
//                break;
//            }
//        }
//        if (needRemoveItem != null) {
//            searchHistoryBeanV2s.remove(needRemoveItem);
//            searchHistoryBeanV2s.add(0, needRemoveItem);
//        } else {
//            needRemoveItem = new SearchHistoryBeanV2();
//            needRemoveItem.setKeyword(str.trim());
//            searchHistoryBeanV2s.add(0, needRemoveItem);
//        }
//        setSearchHistory();
    }

    public void addSearchHistory(String str) {
        mPresenter.addSearchHistory(str);
    }
}
