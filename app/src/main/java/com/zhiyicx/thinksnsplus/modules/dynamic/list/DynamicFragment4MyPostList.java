package com.zhiyicx.thinksnsplus.modules.dynamic.list;

import android.os.Bundle;
import android.view.View;

import com.zhiyicx.thinksnsplus.R;


public class DynamicFragment4MyPostList extends DynamicFragment {

    public static DynamicFragment4MyPostList newInstance(String dynamicType, OnCommentClickListener l) {
        DynamicFragment4MyPostList fragment = new DynamicFragment4MyPostList();
        fragment.setOnCommentClickListener(l);
        Bundle args = new Bundle();
        args.putString(BUNDLE_DYNAMIC_TYPE, dynamicType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getToolBarLayoutId() {
        return R.layout.toolbar_custom_contain_status_bar;
    }

    @Override
    protected void initData() {
        super.initData();
        mIsLoadedNetData = true;
        startRefrsh();
//        mDynamicPresenter.requestNetData(0l,false);
//        mPresenter.requestNetData(0l,false);
    }
    @Override
    protected boolean isLayzLoad() {
        return false;
    }
    @Override
    protected void initView(View rootView) {
        super.initView(rootView);

    }

    @Override
    protected String setCenterTitle() {
        return "我的帖子";
    }

    @Override
    protected int setLeftImg() {
        return R.mipmap.ic_back;
    }

    @Override
    protected void setLeftClick() {
        super.setLeftClick();
    }

    @Override
    protected boolean setUseStatusView() {
        return false;
    }


    @Override
    protected boolean setUseSatusbar() {
        return true;
    }

    @Override
    protected boolean showToolbar() {
        return true;
    }

    @Override
    protected boolean showToolBarDivider() {
        return false;
    }

}
