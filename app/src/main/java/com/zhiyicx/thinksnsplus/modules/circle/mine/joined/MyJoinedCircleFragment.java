package com.zhiyicx.thinksnsplus.modules.circle.mine.joined;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.data.beans.CircleInfo;
import com.zhiyicx.thinksnsplus.data.source.remote.CircleClient;
import com.zhiyicx.thinksnsplus.modules.circle.main.adapter.CircleListItem;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @Describe
 * @Author Jungle68
 * @Date 2017/12/6
 * @Contact master.jungle68@gmail.com
 */
public class MyJoinedCircleFragment extends BaseCircleListFragment {


    View vStatusBarPlaceholder;
    public static MyJoinedCircleFragment newInstance(boolean isNeedToolBar) {
        MyJoinedCircleFragment circleListFragment = new MyJoinedCircleFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(BUNDLE_IS_NEED_TOOLBAR, isNeedToolBar);
        circleListFragment.setArguments(bundle);
        return circleListFragment;
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
        return true;
    }

    @Override
    protected boolean showToolBarDivider() {
        return false;
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
    protected String setCenterTitle() {
        return getString(R.string.joined_group);
    }

    @Override
    protected Long getMaxId(@NotNull List<CircleInfo> data) {
        return (long) mListDatas.size();
    }

    @Override
    public CircleClient.MineCircleType getMineCircleType() {
        return CircleClient.MineCircleType.JOIN;
    }


}
