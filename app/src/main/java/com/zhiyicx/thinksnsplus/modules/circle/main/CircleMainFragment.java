package com.zhiyicx.thinksnsplus.modules.circle.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhiyicx.baseproject.base.TSListFragment;
import com.zhiyicx.baseproject.widget.InputPasswordView;
import com.zhiyicx.baseproject.widget.popwindow.ActionPopupWindow;
import com.zhiyicx.common.utils.ConvertUtils;
import com.zhiyicx.common.utils.recycleviewdecoration.LinearDecoration;
import com.zhiyicx.common.widget.popwindow.CustomPopupWindow;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.base.AppApplication;
import com.zhiyicx.thinksnsplus.data.beans.CircleInfo;
import com.zhiyicx.thinksnsplus.data.beans.CircleJoinedBean;
import com.zhiyicx.thinksnsplus.data.beans.UserCertificationInfo;
import com.zhiyicx.thinksnsplus.modules.certification.detail.CertificationDetailActivity;
import com.zhiyicx.thinksnsplus.modules.certification.input.CertificationInputActivity;
import com.zhiyicx.thinksnsplus.modules.circle.all_circle.container.AllCircleContainerActivity;
import com.zhiyicx.thinksnsplus.modules.circle.create.CreateCircleActivity;
import com.zhiyicx.thinksnsplus.modules.circle.detailv2.CircleDetailActivity;
import com.zhiyicx.thinksnsplus.modules.circle.main.adapter.BaseCircleItem;
import com.zhiyicx.thinksnsplus.modules.circle.main.adapter.CircleListItem;
import com.zhiyicx.thinksnsplus.modules.circle.main.adapter.CircleTypeItem;
import com.zhiyicx.thinksnsplus.modules.circle.mine.joined.MyJoinedCircleActivity;
import com.zhiyicx.thinksnsplus.modules.circle.pre.PreCircleActivity;
import com.zhiyicx.thinksnsplus.modules.circle.search.container.CircleSearchContainerActivity;
import com.zhiyicx.thinksnsplus.modules.circle.search.container.CircleSearchContainerViewPagerFragment;
import com.zhiyicx.thinksnsplus.modules.password.findpassword.FindPasswordActivity;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;


import static com.zhiyicx.thinksnsplus.modules.certification.detail.CertificationDetailActivity.BUNDLE_DETAIL_DATA;
import static com.zhiyicx.thinksnsplus.modules.certification.detail.CertificationDetailActivity.BUNDLE_DETAIL_TYPE;
import static com.zhiyicx.thinksnsplus.modules.certification.input.CertificationInputActivity.BUNDLE_CERTIFICATION_TYPE;
import static com.zhiyicx.thinksnsplus.modules.certification.input.CertificationInputActivity.BUNDLE_TYPE;

/**
 * @author Jliuer
 * @Date 2017/11/14/11:28
 * @Email Jliuer@aliyun.com
 * @Description 圈子首页
 */
public class CircleMainFragment extends TSListFragment<CircleMainContract.Presenter, CircleInfo>
        implements CircleMainContract.View, BaseCircleItem.CircleItemItemEvent {

    public static final int DATALIMIT = 5;
    public static final int TITLEVOUNT = 2;

    private CircleMainHeader mCircleMainHeader;
    private List<CircleInfo> mJoinedCircle;
    private List<CircleInfo> mRecommendCircle;

    private UserCertificationInfo mUserCertificationInfo;
    private ActionPopupWindow mCertificationAlertPopWindow; // 提示需要认证的

    private CircleInfo mCircleInfo;
    /**
     * 仅用于构造
     */
    @Inject
    CircleMainPresenter mCircleMainPresenter;


    @Override
    protected boolean setUseCenterLoading() {
        return true;
    }

    @Override
    protected String setCenterTitle() {
        return getString(R.string.group);
    }

    @Override
    protected int getstatusbarAndToolbarHeight() {
        return 0;
    }

    @Override
    protected boolean isLoadingMoreEnable() {
        return false;
    }

    @Override
    protected boolean isNeedRefreshAnimation() {
        return false;
    }

    public static CircleMainFragment newInstance() {
        return new CircleMainFragment();
    }

    @Override
    public List<CircleInfo> getJoinedCircles() {
        return mJoinedCircle;
    }

    @Override
    protected int getBodyLayoutId() {
        return R.layout.fragment_list_with_input;
    }

    @Override
    protected boolean showNoMoreData() {
        return false;
    }

    @Override
    public void setJoinedCircles(List<CircleInfo> circles) {
        mJoinedCircle = circles;
    }

    @Override
    public void setRecommendCircles(List<CircleInfo> circles) {
        mRecommendCircle = circles;
    }

    @Override
    public List<CircleInfo> getRecommendCircles() {
        return mRecommendCircle;
    }

    @Override
    protected int setRightImg() {
        return R.mipmap.ico_createcircle;
    }

    @Override
    protected int setRightLeftImg() {
        return R.mipmap.ico_search;
    }

    @Override
    public void updateCircleCount(int count) {
        mActivity.runOnUiThread(() -> mCircleMainHeader.updateCircleCount(count));
    }


    @Override
    protected void setRightClick() {
        super.setRightClick();

        // 发布提示 1、首先需要认证 2、需要付费
        if (mPresenter.handleTouristControl()) {
            return;
        }
        mPresenter.checkCertification();

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
    protected boolean showToolBarDivider() {
        return false;
    }

    @Override
    public void loadAllError() {
        setLoadViewHolderImag(R.mipmap.img_default_internet);
        showLoadViewLoadError();
    }

    @Override
    protected void setLoadingViewHolderClick() {
        super.setLoadingViewHolderClick();
        mPresenter.requestNetData(0L, false);
    }

    @Override
    protected void setRightLeftClick() {
        super.setRightLeftClick();
        CircleSearchContainerActivity.startCircelSearchActivity(mActivity, CircleSearchContainerViewPagerFragment.PAGE_CIRCLE);
    }

    @Override
    protected void setLeftClick() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        mActivity.finish();
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        MultiItemTypeAdapter adapter = new MultiItemTypeAdapter<>(getContext(), mListDatas);
        adapter.addItemViewDelegate(new CircleListItem(false, mActivity, this, mPresenter));
        adapter.addItemViewDelegate(new CircleTypeItem(this));
        return adapter;
    }

    @Override
    protected boolean setUseInputPsdView() {
        return true;
    }

    @Override
    public void onSureClick(View v, String text, InputPasswordView.PayNote payNote) {
        mPresenter.dealCircleJoinOrExit(payNote.id.intValue(), mCircleInfo, payNote.psd);
    }

    @Override
    public void onForgetPsdClick() {
        showInputPsdView(false);
        startActivity(new Intent(getActivity(), FindPasswordActivity.class));
    }


    @Override
    public void onCancle() {
        dismissSnackBar();
        mPresenter.canclePay();
        showInputPsdView(false);
    }

    @Override
    protected void initData() {
        if (mPresenter == null) {
            mPresenter = mCircleMainPresenter;
        }
        if (mPresenter != null) {
            mCircleMainHeader = new CircleMainHeader(mActivity, mPresenter.getCircleTopAdvert(), 2341);
            mHeaderAndFooterWrapper.addHeaderView(mCircleMainHeader.getCircleMainHeader());
            mPresenter.requestNetData(0L, false);
        }
    }

    @Override
    protected boolean setUseShadowView() {
        return true;
    }

    @Override
    protected void onShadowViewClick() {
        showInputPsdView(false);
    }

    @Override
    public void onNetResponseSuccess(@NotNull List<CircleInfo> data, boolean isLoadMore) {
        super.onNetResponseSuccess(data, isLoadMore);
        closeLoadingView();
    }

    @Override
    public void onResponseError(Throwable throwable, boolean isLoadMore) {
        super.onResponseError(throwable, isLoadMore);
        mCircleMainHeader.getAdvertHeader().hideAdvert();
    }

    /**
     * 查看我加入的
     *
     * @param groupInfoBean
     */
    @Override
    public void toAllJoinedCircle(CircleInfo groupInfoBean) {
        if (mJoinedCircle.size() <= TITLEVOUNT) {
            // 查看全部
            startActivity(new Intent(mActivity, AllCircleContainerActivity.class));
            return;
        }
        if (mJoinedCircle.size() >= DATALIMIT + 1) {
            Intent intent = new Intent(mActivity, MyJoinedCircleActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void changeRecommend() {
        if (mListDatas.size() <= TITLEVOUNT) {
            return;
        }
        mPresenter.getRecommendCircle();
    }

    @Override
    public void dealCircleJoinOrExit(int position, CircleInfo circleInfo) {
        mCircleInfo = circleInfo;
        boolean isJoined = circleInfo.getJoined() != null && circleInfo.getJoined().getAudit() == CircleJoinedBean.AuditStatus.PASS.value;
        boolean isJoinedWateReview = circleInfo.getJoined() != null && circleInfo.getJoined().getAudit() == CircleJoinedBean.AuditStatus.REVIEWING.value;

        if (isJoinedWateReview) {
            showSnackErrorMessage(getString(R.string.reviewing_join_circle));
            return;
        }

        boolean isPaid = CircleInfo.CirclePayMode.PAID.value.equals(circleInfo.getMode());

        if (mPresenter.usePayPassword() && !isJoined && isPaid) {
            mIlvPassword.setPayNote(new InputPasswordView.PayNote(null, (long) position));
            showInputPsdView(true);
        } else {
            mPresenter.dealCircleJoinOrExit(position, circleInfo, null);
        }

    }

    @Override
    public void toCircleDetail(CircleInfo circleInfo) {
        boolean isClosedCircle = CircleInfo.CirclePayMode.PAID.value.equals(circleInfo.getMode())
                || CircleInfo.CirclePayMode.PRIVATE.value.equals(circleInfo.getMode());
        boolean isJoined = circleInfo.getJoined() != null && circleInfo.getJoined().getAudit() == CircleJoinedBean.AuditStatus.PASS.value;


        if (isClosedCircle && !isJoined) {
            PreCircleActivity.startPreCircleActivity(mActivity, circleInfo.getId());
//          showSnackErrorMessage(getString(R.string.circle_blocked));
            return;
        }

        CircleDetailActivity.startCircleDetailActivity(mActivity, circleInfo.getId());
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
                        // 待审核
                        if (mUserCertificationInfo != null
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
                        // 待审核
                        if (mUserCertificationInfo != null
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dismissPop(mCertificationAlertPopWindow);
    }


    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        DaggerCircleMainPresenterComponent
                .builder()
                .appComponent(AppApplication.AppComponentHolder.getAppComponent())
                .circleMainPresenterModule(new CircleMainPresenterModule(this))
                .build().inject(CircleMainFragment.this);
    }

    @Override
    protected RecyclerView.ItemDecoration getItemDecoration() {
//        return new LinearDecoration(0, ConvertUtils.dp2px(getContext(), getItemDecorationSpacing()), 0, 0);
        return new LinearDecoration(0, ConvertUtils.dp2px(getContext(), getItemDecorationSpacing()), 0, 0);
    }

    @Override
    protected float getItemDecorationSpacing() {
        return 0;
    }
}
