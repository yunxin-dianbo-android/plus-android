package com.zhiyicx.thinksnsplus.modules.settings.aboutus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.zhiyicx.baseproject.base.TSWebFragment;
import com.zhiyicx.baseproject.config.ApiConfig;
import com.zhiyicx.thinksnsplus.R;

import java.util.HashMap;
import java.util.List;


/**
 * @Describe 关于我们
 * @Author Jungle68
 * @Date 2017/1/10
 * @Contact master.jungle68@gmail.com
 */

public class CustomWEBFragment extends TSWebFragment {
    public static final String BUNDLE_PARAMS_WEB_URL = "web_url";
    public static final String BUNDLE_PARAMS_WEB_TITLE = "web_title";
    public static final String BUNDLE_PARAMS_WEB_HEADERS = "web_headers";

    private String mUrl = ApiConfig.APP_DOMAIN + ApiConfig.URL_ABOUT_US;
    private String mTitle = "";
    private HashMap<String, String> mHeaders;

    private View vStatusBarPlaceHolder;

    public CustomWEBFragment() {
        // Required empty public constructor
    }

    public static CustomWEBFragment newInstance(Bundle bundle) {
        CustomWEBFragment fragment = new CustomWEBFragment();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    protected boolean showToolBarDivider() {
        return false;
    }

    @Override
    protected int getToolBarLayoutId() {
        return R.layout.toolbar_for_web_contain_statu_bar;
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        vStatusBarPlaceHolder = rootView.findViewById(R.id.v_status_bar_placeholder);
        initStatusBar();
    }

    private void initStatusBar() {
        // toolBar设置状态栏高度的marginTop
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, com.zhiyicx.common.utils.DeviceUtils
                .getStatuBarHeight(getContext()));
        vStatusBarPlaceHolder.setLayoutParams(layoutParams);
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle = getString(R.string.about_us);
        if (getArguments() != null) {
            mUrl = getArguments().getString(BUNDLE_PARAMS_WEB_URL);
            mTitle = getArguments().getString(BUNDLE_PARAMS_WEB_TITLE);
            mHeaders = (HashMap<String, String>) getArguments().getSerializable(BUNDLE_PARAMS_WEB_HEADERS);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadUrl(mUrl, mHeaders);
    }

    @Override
    protected String setCenterTitle() {
        return mTitle;
    }


    @Override
    protected void onWebImageClick(String clickUrl, List<String> images) {

    }

    @Override
    protected void onWebImageLongClick(String longClickUrl) {

    }
}
