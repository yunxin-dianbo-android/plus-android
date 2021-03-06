package com.zhiyicx.thinksnsplus.modules.circle.all_circle.container;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zhiyicx.baseproject.base.TSViewPagerAdapter;
import com.zhiyicx.baseproject.base.TSViewPagerFragment;
import com.zhiyicx.baseproject.widget.TabSelectView;
import com.zhiyicx.baseproject.widget.popwindow.ActionPopupWindow;
import com.zhiyicx.common.utils.DeviceUtils;
import com.zhiyicx.common.widget.popwindow.CustomPopupWindow;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.data.beans.CircleTypeBean;
import com.zhiyicx.thinksnsplus.data.beans.UserCertificationInfo;
import com.zhiyicx.thinksnsplus.modules.certification.detail.CertificationDetailActivity;
import com.zhiyicx.thinksnsplus.modules.certification.input.CertificationInputActivity;
import com.zhiyicx.thinksnsplus.modules.circle.all_circle.CircleListFragment;
import com.zhiyicx.thinksnsplus.modules.circle.create.CreateCircleActivity;
import com.zhiyicx.thinksnsplus.modules.circle.create.types.CircleTyepsActivity;
import com.zhiyicx.thinksnsplus.modules.circle.create.types.CircleTypesFragment;
import com.zhiyicx.thinksnsplus.modules.circle.search.container.CircleSearchContainerActivity;
import com.zhiyicx.thinksnsplus.modules.circle.search.container.CircleSearchContainerViewPagerFragment;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;

import java.util.ArrayList;
import java.util.List;

import static com.zhiyicx.thinksnsplus.modules.certification.detail.CertificationDetailActivity.BUNDLE_DETAIL_DATA;
import static com.zhiyicx.thinksnsplus.modules.certification.detail.CertificationDetailActivity.BUNDLE_DETAIL_TYPE;
import static com.zhiyicx.thinksnsplus.modules.certification.input.CertificationInputActivity.BUNDLE_CERTIFICATION_TYPE;
import static com.zhiyicx.thinksnsplus.modules.certification.input.CertificationInputActivity.BUNDLE_TYPE;
import static com.zhiyicx.thinksnsplus.modules.circle.create.CreateCircleFragment.REQUST_CODE_CATEGORY;
import static com.zhiyicx.thinksnsplus.modules.circle.create.types.CircleTypesFragment.BUNDLE_CIRCLE_CATEGORY_NAME;

/**
 * @Author Jliuer
 * @Date 2017/11/21/14:49
 * @Email Jliuer@aliyun.com
 * @Description
 */
public class AllCircleContainerFragment extends TSViewPagerFragment<AllCircleContainerContract.Presenter>
        implements AllCircleContainerContract.View {

    public static final String BUNDLE_ALL_CIRCLE_CATEGORY = "all_circle_category";

    View mStatusBarPlaceholder;

    private CircleTypeBean mCircleTypeBean;

    private List<String> mTitle;

    // 推荐专用
    public static final String RECOMMEND_INFO = "-1";

    public static final String ALL_INFO = "-2";


    private UserCertificationInfo mUserCertificationInfo;

    // 提示需要认证的
    private ActionPopupWindow mCertificationAlertPopWindow;


    @Override
    protected boolean showToolBarDivider() {
        return false;
    }

//    @Override
//    protected boolean showToolbar() {
//        return true;
//    }

    @Override
    protected int setRightImg() {
//        return R.mipmap.ico_createcircle;
//        return R.mipmap.ic_search;
        return super.setRightImg();
    }

    @Override
    protected int getToolBarLayoutId() {
        return R.layout.toolbar_custom_contain_status_bar;
    }


    @Override
    protected int setRightLeftImg() {
        return 0;
    }

//    @Override
//    protected int getBodyLayoutId() {
//        return R.layout.fragment_all_circle_viewpager;
//    }


    @Override
    protected boolean setUseSatusbar() {
        return true;
    }

    @Override
    protected boolean showToolbar() {
        return true;
    }

    @Override
    protected boolean setUseStatusView() {
        return false;
    }


    @Override
    protected String setCenterTitle() {
        return getString(R.string.all_group);
    }

    @Override
    protected void setCenterTextColor(int resId) {
        super.setCenterTextColor(resId);
    }

    @Override
    protected List<String> initTitles() {
//        if (mTitle == null) {
//            mTitle = new ArrayList<>();
//            mTitle.add(getString(R.string.info_recommend));
//        }
//        return mTitle;
        mTitle = new ArrayList<>();
        mTitle.add(getString(R.string.info_recommend));
        return mTitle;
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
//        mStatusBarPlaceholder = rootView.findViewById(R.id.v_status_bar_placeholder);
//        mTsvToolbar.setBackgroundResource(R.drawable.common_statubar_bg_2);
        mTsvToolbar.showDivider(false);
        mTsvToolbar.setMyBackground(R.drawable.common_statubar_bg_2);
        mTsvToolbar.setDividerBackground(R.drawable.common_statubar_bg_2);
        mTsvToolbar.setVisibility(View.GONE);
    }

    private void initStatusBar() {
        // toolBar设置状态栏高度的marginTop
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DeviceUtils
                .getStatuBarHeight(getContext()));
        mStatusBarPlaceholder.setLayoutParams(layoutParams);
    }

    @Override
    public void setCategroiesList(List<CircleTypeBean> circleTypeList) {
//        for (CircleTypeBean circleTypeBean : circleTypeList) {
//            if (RECOMMEND_INFO.equals(circleTypeBean.getId().intValue() + "")) {
//                continue;
//            }
//            mTitle.add(circleTypeBean.getName());
//            mFragmentList.add(CircleListFragment.newInstance(circleTypeBean.getId() + ""));
//        }

//        mTsvToolbar.notifyDataSetChanged(mTitle);
//        tsViewPagerAdapter.bindData(mFragmentList, mTitle.toArray(new String[]{}));
//        mVpFragment.setOffscreenPageLimit(mTitle.size());
    }

    @Override
    protected void setRightClick() {
//        super.setRightClick();
//        // 发布提示 1、首先需要认证 2、需要付费
//        if (mPresenter.handleTouristControl()) {
//            return;
//        }
//        mPresenter.checkCertification();
        super.setRightClick();
        CircleSearchContainerActivity.startCircelSearchActivity(mActivity, CircleSearchContainerViewPagerFragment.PAGE_CIRCLE);

    }

    @Override
    protected void setRightLeftClick() {
//        super.setRightLeftClick();
//        CircleSearchContainerActivity.startCircelSearchActivity(mActivity, CircleSearchContainerViewPagerFragment.PAGE_CIRCLE);
    }

    @Override
    protected List<Fragment> initFragments() {
        if (mFragmentList == null) {
            mFragmentList = new ArrayList<>();
//          mFragmentList.add(CircleListFragment.newInstance(RECOMMEND_INFO));
            mFragmentList.add(CircleListFragment.newInstance(ALL_INFO));
        }
        return mFragmentList;
    }


    @Override
    protected View getContentView() {
        View contentView = super.getContentView();
        mStatusBarPlaceholder = contentView.findViewById(R.id.v_status_bar_placeholder);
        initStatusBar();
        return contentView;
    }

    @Override
    protected int setLeftImg() {
        return com.zhiyicx.baseproject.R.mipmap.ic_back;
    }

    protected void setToolBarTextColor() {
        // 如果toolbar背景是白色的，就将文字颜色设置成黑色
        if (showToolbar()) {
            mToolbarCenter.setTextColor(ContextCompat.getColor(getContext(), com.zhiyicx.baseproject.R.color.white));
            mToolbarRight.setTextColor(ContextCompat.getColorStateList(getContext(), com.zhiyicx.baseproject.R.color.white));
            mToolbarRightLeft.setTextColor(ContextCompat.getColorStateList(getContext(), com.zhiyicx.baseproject.R.color.white));
            mToolbarLeft.setTextColor(ContextCompat.getColorStateList(getContext(), com.zhiyicx.baseproject.R.color.white));
        }
    }

    @Override
    protected void initViewPager(View rootView) {

        mVpFragment = rootView.findViewById(com.zhiyicx.baseproject.R.id.vp_fragment);
        tsViewPagerAdapter = new TSViewPagerAdapter(getChildFragmentManager());
        tsViewPagerAdapter.bindData(initFragments());
        mVpFragment.setAdapter(tsViewPagerAdapter);
        mVpFragment.setOffscreenPageLimit(mFragmentList.size());

        mTsvToolbar = rootView.findViewById(com.zhiyicx.baseproject.R.id.tsv_toolbar);

//        mTsvToolbar.setRightImg(R.mipmap.sec_nav_arrow, R.color.white);
        mTsvToolbar.setRightImg(0, R.color.transparent);
        mTsvToolbar.setLeftImg(0);
        mTsvToolbar.setDefaultTabLinehegiht(R.integer.no_line_height);
        mTsvToolbar.setDefaultTabLeftMargin(com.zhiyicx.baseproject.R.integer.tab_margin_10);
        mTsvToolbar.setDefaultTabRightMargin(com.zhiyicx.baseproject.R.integer.tab_margin_10);
        mTsvToolbar.showDivider(false);
        mTsvToolbar.setIndicatorMatchWidth(true);
        mTsvToolbar.setIndicatorMode(LinePagerIndicator.MODE_MATCH_EDGE);
        mTsvToolbar.setTabSpacing(getResources().getDimensionPixelOffset(R.dimen.info_container_tab_spacing));
        mTsvToolbar.setNeedChooseItemToBig(true);
        mTsvToolbar.setAdjustMode(isAdjustMode());
        mTsvToolbar.initTabView(mVpFragment, initTitles());
        mTsvToolbar.setLeftClickListener(this, () -> setLeftClick());
        mTsvToolbar.setRightClickListener(this, () -> {
            Intent typeIntent = new Intent(getActivity(), CircleTyepsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putBoolean(BUNDLE_ALL_CIRCLE_CATEGORY, true);
            bundle.putString(BUNDLE_CIRCLE_CATEGORY_NAME, mTitle.get(mVpFragment.getCurrentItem()));
            typeIntent.putExtras(bundle);
            startActivityForResult(typeIntent, REQUST_CODE_CATEGORY);
        });
        mTsvToolbar.setBackgroundResource(R.drawable.common_statubar_bg_2);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUST_CODE_CATEGORY && data != null && data.getExtras() != null) {
            mCircleTypeBean = data.getExtras().getParcelable(CircleTypesFragment.BUNDLE_CIRCLE_CATEGORY);
            if (mCircleTypeBean != null) {
                mVpFragment.setCurrentItem(mTitle.indexOf(mCircleTypeBean.getName()));
            }
        }

    }

    @Override
    protected void initData() {
        mPresenter.getCategroiesList(0, 0);
    }


    /**
     * 认证检查回调
     *
     * @param userCertificationInfo
     */
    @Override
    public void setUserCertificationInfo(UserCertificationInfo userCertificationInfo) {
        mUserCertificationInfo = userCertificationInfo;
        mSystemConfigBean = mPresenter.getSystemConfigBean();
        if (mSystemConfigBean.getCircleGroup() != null && mSystemConfigBean.getCircleGroup()
                .isNeed_verified() && userCertificationInfo.getStatus() != UserCertificationInfo.CertifyStatusEnum.PASS.value) {
            showCerificationPopWindow();
        } else {
            CreateCircleActivity.startCreateActivity(mActivity);
        }

    }

    /**
     * 认证提示弹窗
     */
    private void showCerificationPopWindow() {

        if (mCertificationAlertPopWindow == null) {
            mCertificationAlertPopWindow = ActionPopupWindow.builder()
                    .item1Str(getString(R.string.info_publish_hint))
                    .item2Str(getString(R.string.certification_personage))
                    .item3Str(getString(R.string.certification_company))
                    .desStr(getString(R.string.circle_publish_hint_certification))
                    .bottomStr(getString(R.string.cancel))
                    .isOutsideTouch(true)
                    .isFocus(true)
                    .backgroundAlpha(CustomPopupWindow.POPUPWINDOW_ALPHA)
                    .with(getActivity())
                    .bottomClickListener(() -> mCertificationAlertPopWindow.hide())
                    .item2ClickListener(() -> {
                        // 个人认证
                        mCertificationAlertPopWindow.hide();
                        if (mUserCertificationInfo != null
                                // 待审核
                                && mUserCertificationInfo.getId() != 0
                                && mUserCertificationInfo.getStatus() != UserCertificationInfo.CertifyStatusEnum.REJECTED.value) {
                            Intent intentToDetail = new Intent(getActivity(), CertificationDetailActivity.class);
                            Bundle bundleData = new Bundle();
                            bundleData.putInt(BUNDLE_DETAIL_TYPE, 0);
                            bundleData.putParcelable(BUNDLE_DETAIL_DATA, mUserCertificationInfo);
                            intentToDetail.putExtra(BUNDLE_DETAIL_TYPE, bundleData);
                            startActivity(intentToDetail);
                        } else {
                            Intent intent = new Intent(getActivity(), CertificationInputActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt(BUNDLE_TYPE, 0);
                            intent.putExtra(BUNDLE_CERTIFICATION_TYPE, bundle);
                            startActivity(intent);
                        }
                    })
                    .item3ClickListener(() -> {
                        // 企业认证
                        mCertificationAlertPopWindow.hide();
                        if (mUserCertificationInfo != null
                                // 待审核
                                && mUserCertificationInfo.getId() != 0
                                && mUserCertificationInfo.getStatus() != UserCertificationInfo.CertifyStatusEnum.REJECTED.value) {

                            Intent intentToDetail = new Intent(getActivity(), CertificationDetailActivity.class);
                            Bundle bundleData = new Bundle();
                            bundleData.putInt(BUNDLE_DETAIL_TYPE, 1);
                            bundleData.putParcelable(BUNDLE_DETAIL_DATA, mUserCertificationInfo);
                            intentToDetail.putExtra(BUNDLE_DETAIL_TYPE, bundleData);
                            startActivity(intentToDetail);
                        } else {
                            Intent intent = new Intent(getActivity(), CertificationInputActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt(BUNDLE_TYPE, 1);
                            intent.putExtra(BUNDLE_CERTIFICATION_TYPE, bundle);
                            startActivity(intent);
                        }
                    })
                    .build();
        }
        mCertificationAlertPopWindow.show();

    }
}
