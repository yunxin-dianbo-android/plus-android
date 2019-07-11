package com.zhiyicx.thinksnsplus.modules.home.mine.generalize;

import android.view.View;

import com.zhiyicx.baseproject.base.TSFragment;
import com.zhiyicx.thinksnsplus.R;

public class GeneralizeFragment extends TSFragment {

    View vStatusBarPlaceholder;

    public static GeneralizeFragment newInstance(){
        GeneralizeFragment generalizeFragment = new GeneralizeFragment();
        return generalizeFragment;
    }
    @Override
    protected int getBodyLayoutId() {
        return R.layout.fragment_generalize_layout;
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        vStatusBarPlaceholder = rootView.findViewById(R.id.v_status_bar_placeholder);
        initStatusBar(vStatusBarPlaceholder);
    }

    @Override
    protected int getToolBarLayoutId() {
        return R.layout.toolbar_custom_contain_status_bar;
    }

    @Override
    protected int setLeftImg() {
        return R.mipmap.ic_back;
    }

    @Override
    protected String setCenterTitle() {
        return "我的推广";
    }

    @Override
    protected boolean setUseStatusView() {
        return false;
    }

    @Override
    protected boolean setUseSatusbar() {
        return true;
    }

//    @Override
//    public void setPresenter(Object presenter) {
//
//    }
}
