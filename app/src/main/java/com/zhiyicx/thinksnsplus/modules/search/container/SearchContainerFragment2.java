package com.zhiyicx.thinksnsplus.modules.search.container;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.zhiyicx.baseproject.base.TSFragment;
import com.zhiyicx.baseproject.widget.edittext.DeleteEditText;
import com.zhiyicx.common.utils.ActivityUtils;
import com.zhiyicx.common.utils.DeviceUtils;
import com.zhiyicx.common.utils.StatusBarUtils;
import com.zhiyicx.common.utils.ToastUtils;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.modules.personal_center.PersonalCenterFragment;
import com.zhiyicx.thinksnsplus.modules.q_a.search.list.IHistoryCententClickListener;
import com.zhiyicx.thinksnsplus.modules.search.history.SearchHistoryFragment;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;

import static com.zhiyicx.common.config.ConstantConfig.JITTER_SPACING_TIME;

/**
 * ThinkSNS Plus
 * Copyright (c) 2018 Chengdu ZhiYiChuangXiang Technology Co., Ltd.
 *
 * @Author Jliuer
 * @Date 2018/08/06/10:18
 * @Email Jliuer@aliyun.com
 * @Description
 */
public class SearchContainerFragment2 extends TSFragment implements IHistoryCententClickListener {


    @BindView(R.id.v_status_bar_placeholder)
    View vStatusBarPlaceholder;
    @BindView(R.id.iv_back)
    ImageView ivback;
    @BindView(R.id.fragment_info_search_edittext)
    DeleteEditText mFragmentInfoSearchEdittext;

    @BindView(R.id.fragment_info_search_cancle)
    TextView mFragmentInfoSearchCancle;

    @BindView(R.id.fragment_info_search_container)
    RelativeLayout mFragmentInfoSearchContainer;

    @BindView(R.id.fragment_container)
    FrameLayout mFragmentContainer;
    SearchIndexFragment historyFragment;
//    private boolean hasViewpager;

//    private SearchHistoryViewPagerContainerFragment mSearchHistoryViewPagerContainerFragment;

    @Override
    protected View getRightViewOfMusicWindow() {
        return mFragmentInfoSearchCancle;
    }

    @Override
    protected int getBodyLayoutId() {
        return R.layout.fragment_search_contaner2;
    }


    @Override
    protected boolean showToolBarDivider() {
        return false;
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        historyFragment = new SearchIndexFragment();
        historyFragment.setiSetSearchEdittextContent(new IDoSearchCallBack() {
            @Override
            public void doSearch(String content) {
                mFragmentInfoSearchEdittext.setText(content);
                SearchContainerFragment2.this.doSearch(content);
            }
        });
        ActivityUtils.addFragmentToActivity(getActivity().getSupportFragmentManager()
                , historyFragment
                , R.id.fragment_container);

        initListener();
        initToolBar();
    }

    private void initToolBar() {
        // toolBar设置状态栏高度的marginTop
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, DeviceUtils
                .getStatuBarHeight(getContext()));
        vStatusBarPlaceholder.setLayoutParams(layoutParams);
        // 适配非6.0以上、非魅族系统、非小米系统状态栏
        if (getActivity() != null && StatusBarUtils.intgetType(getActivity().getWindow()) == 0) {
            vStatusBarPlaceholder.setBackgroundResource(R.color.themeColor);
        }
    }


    @Override
    protected boolean showToolbar() {
        return false;
    }


    @Override
    protected boolean setUseStatusView() {
        return false;
    }


    @Override
    public void onBackPressed() {
        mActivity.finish();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean setUseSatusbar() {
        return true;
    }

    @OnClick({R.id.fragment_info_search_cancle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_info_search_cancle:
//                mActivity.finish();
//                if (!TextUtils.isEmpty(mFragmentInfoSearchEdittext.getText().toString().trim())) {
                doSearch(mFragmentInfoSearchEdittext.getText().toString().trim());
                DeviceUtils.hideSoftKeyboard(getContext(), mFragmentInfoSearchEdittext);
//                }
                break;
            default:
        }
    }

    @Override
    public void onContentClick(String str) {
        doSearch(str);
    }

    private void initListener() {
        RxTextView.editorActionEvents(mFragmentInfoSearchEdittext).subscribe(textViewEditorActionEvent -> {
            if (textViewEditorActionEvent.actionId() == EditorInfo.IME_ACTION_SEARCH) {
//                if (!TextUtils.isEmpty(mFragmentInfoSearchEdittext.getText().toString())) {
                doSearch(mFragmentInfoSearchEdittext.getText().toString());
                DeviceUtils.hideSoftKeyboard(getContext(), mFragmentInfoSearchEdittext);
//                }
            }
        });

        RxView.clicks(ivback)
                .throttleFirst(JITTER_SPACING_TIME, TimeUnit.SECONDS)
                .subscribe(aVoid -> getActivity().finish());
    }

    private void doSearch(String str) {
        if (TextUtils.isEmpty(str)) {
            ToastUtils.showToast(R.string.have_no_input_search_key_tip);
            return;
        }
//        if (historyFragment != null && historyFragment.isAdded()) {
//            historyFragment.doSearch(str);
//        }else{
        if (historyFragment != null) {
            historyFragment.addSearchHistory(str);
        }
        Bundle bundle = new Bundle();
        bundle.putString(SearchHistoryViewPagerContainerFragment.KEY_WORD, str);
        SearchHistoryViewPagerContainerFragment mSearchHistoryViewPagerContainerFragment = SearchHistoryViewPagerContainerFragment.newInstance(bundle);
        ActivityUtils.replaceFragmentToActivity(getActivity().getSupportFragmentManager()
                , mSearchHistoryViewPagerContainerFragment
                , R.id.fragment_container);
        mSearchHistoryViewPagerContainerFragment.onSearhChanged(str);
//        }
    }


    public interface IDoSearchCallBack {
        void doSearch(String content);
    }
}
