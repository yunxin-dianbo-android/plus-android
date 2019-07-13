package com.zhiyicx.thinksnsplus.modules.superstar;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.jakewharton.rxbinding.view.RxView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhiyicx.baseproject.base.TSFragment;
import com.zhiyicx.baseproject.utils.glide.GlideManager;
import com.zhiyicx.baseproject.widget.sidebar.ISideBarSelectCallBack;
import com.zhiyicx.baseproject.widget.sidebar.SideBar;
import com.zhiyicx.common.dagger.scope.FragmentScoped;
import com.zhiyicx.common.utils.DeviceUtils;
import com.zhiyicx.common.utils.StatusBarUtils;
import com.zhiyicx.common.utils.ToastUtils;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.data.beans.SuperStarBean;
import com.zhiyicx.thinksnsplus.data.beans.SuperStarClassifyBean;
import com.zhiyicx.thinksnsplus.modules.channel.VideoChannelActivity;
import com.zhiyicx.thinksnsplus.modules.channel.VideoChannelFragment2;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;

import static com.zhiyicx.common.config.ConstantConfig.JITTER_SPACING_TIME;

public class AllStarFragment extends TSFragment<AllStarContract.Presenter> implements AllStarContract.View<AllStarContract.Presenter> {

    @BindView(R.id.rv_all_star)
    RecyclerView rvAllStar;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.side_bar)
    SideBar sideBar;
    //    @BindView(R.id.v_status_bar_placeholder)
    View vStatusBarPlaceHolder;

    public static AllStarFragment getInstance() {
        AllStarFragment allStarFragment = new AllStarFragment();
        return allStarFragment;
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        vStatusBarPlaceHolder = rootView.findViewById(R.id.v_status_bar_placeholder);
        initToolBar();
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.requestNetData();
            }
        });

    }

    private void initToolBar() {
        // toolBar设置状态栏高度的marginTop
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DeviceUtils
                .getStatuBarHeight(getContext()));
        vStatusBarPlaceHolder.setLayoutParams(layoutParams);
        // 适配非6.0以上、非魅族系统、非小米系统状态栏
        if (getActivity() != null && StatusBarUtils.intgetType(getActivity().getWindow()) == 0) {
            vStatusBarPlaceHolder.setBackgroundResource(R.drawable.common_statubar_bg);
        }
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
    protected int getToolBarLayoutId() {
        return R.layout.toolbar_custom_contain_status_bar;
    }

    @Override
    protected String setCenterTitle() {
        return "明星";
    }

    @Override
    protected void initData() {
        super.initData();
//        smartRefreshLayout.autoRefresh();
        mPresenter.requestNetData();
    }

    @Override
    protected int getBodyLayoutId() {
        return R.layout.fragment_all_star;
    }


    @Override
    public void onGetAllSuperStarSuccess(List<SuperStarClassifyBean> superStarListBean) {
        if (superStarListBean == null || superStarListBean.size() == 0) {
            return;
        }
//        ArrayList<String> tags = new ArrayList<>();
        String[] strTags = new String[superStarListBean.size()];
        for (int i = 0; i < superStarListBean.size(); i++) {
            strTags[i] = superStarListBean.get(i).key;
        }
//        String[] strTags = (String[]) tags.toArray();
        sideBar.setDataResource(strTags);
        sideBar.setOnStrSelectCallBack(new ISideBarSelectCallBack() {
            @Override
            public void onSelectStr(int index, String selectStr) {
                Log.e("wulianshu", "index:" + index + "  selectStr:" + selectStr);
                rvAllStar.scrollToPosition(index);
                LinearLayoutManager layoutManager = (LinearLayoutManager) rvAllStar.getLayoutManager();
                layoutManager.scrollToPositionWithOffset(index, 0);
            }
        });
        smartRefreshLayout.finishRefresh();
        rvAllStar.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false));
        rvAllStar.setAdapter(new CommonAdapter<SuperStarClassifyBean>(getContext(), R.layout.item_allstar_layout, superStarListBean) {
            @Override
            protected void convert(ViewHolder holder, SuperStarClassifyBean superStarClassifyBean, int position) {
                holder.getTextView(R.id.tv_tag_name).setText(superStarClassifyBean.key);
                RecyclerView recyclerView = holder.getView(R.id.rv_super_star);
                recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
                int linenum = 1;
                if (superStarClassifyBean.star.size() % 4 == 0) {
                    linenum = superStarClassifyBean.star.size() / 4;
                } else {
                    linenum = superStarClassifyBean.star.size() / 4 + 1;
                }
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) recyclerView.getLayoutParams();
                layoutParams.height = getResources().getDimensionPixelSize(R.dimen.all_star_line_height) * linenum;
                recyclerView.setLayoutParams(layoutParams);
                recyclerView.setAdapter(new CommonAdapter<SuperStarBean>(mContext, R.layout.item_allstar_subitem_layout, superStarClassifyBean.star) {
                    @Override
                    protected void convert(ViewHolder holder, SuperStarBean superStarBean, int position) {
                        holder.getTextView(R.id.tv_star_name).setText(superStarBean.getName() + "");
                        GlideManager.glideCircle(mContext, holder.getImageViwe(R.id.iv_star_head), superStarBean.getAvatar(), R.mipmap.ic_default_user_head_circle);
                        RxView.clicks(holder.getConvertView())
                                .throttleFirst(JITTER_SPACING_TIME, TimeUnit.SECONDS)
                                .subscribe(aVoid -> {
                                    VideoChannelActivity.starVideoChannelActivity(mActivity, superStarBean,null);
                                });
                    }
//                    @Override
//                    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//
//                    }
                });
            }
        });

    }

    @Override
    public void onGetAllSuperStarFailed() {
        ToastUtils.showToast("网络异常,下拉重试");
    }
}
