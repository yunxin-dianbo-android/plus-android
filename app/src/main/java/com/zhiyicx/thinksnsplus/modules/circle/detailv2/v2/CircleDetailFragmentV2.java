package com.zhiyicx.thinksnsplus.modules.circle.detailv2.v2;

import android.Manifest;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.hyphenate.easeui.EaseConstant;
import com.jakewharton.rxbinding.view.RxView;
import com.nineoldandroids.view.ViewHelper;
import com.yalantis.ucrop.UCrop;
import com.zhiyicx.baseproject.base.TSViewPagerAdapter;
import com.zhiyicx.baseproject.base.TSViewPagerFragment;
import com.zhiyicx.baseproject.config.PayConfig;
import com.zhiyicx.baseproject.impl.imageloader.glide.transformation.GlideStokeTransform;
import com.zhiyicx.baseproject.impl.photoselector.DaggerPhotoSelectorImplComponent;
import com.zhiyicx.baseproject.impl.photoselector.ImageBean;
import com.zhiyicx.baseproject.impl.photoselector.PhotoSelectorImpl;
import com.zhiyicx.baseproject.impl.photoselector.PhotoSeletorImplModule;
import com.zhiyicx.baseproject.impl.share.UmengSharePolicyImpl;
import com.zhiyicx.baseproject.share.Share;
import com.zhiyicx.baseproject.utils.glide.GlideManager;
import com.zhiyicx.baseproject.widget.InputPasswordView;
import com.zhiyicx.baseproject.widget.button.CombinationButton;
import com.zhiyicx.baseproject.widget.popwindow.PayPopWindow;
import com.zhiyicx.common.utils.AndroidBug5497Workaround;
import com.zhiyicx.common.utils.ConvertUtils;
import com.zhiyicx.common.utils.DeviceUtils;
import com.zhiyicx.common.utils.FastBlur;
import com.zhiyicx.common.utils.FileUtils;
import com.zhiyicx.common.utils.SharePreferenceUtils;
import com.zhiyicx.common.utils.recycleviewdecoration.LinearDecoration;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.base.AppApplication;
import com.zhiyicx.thinksnsplus.base.EmptySubscribe;
import com.zhiyicx.thinksnsplus.config.EventBusTagConfig;
import com.zhiyicx.thinksnsplus.data.beans.CircleInfo;
import com.zhiyicx.thinksnsplus.data.beans.CircleJoinedBean;
import com.zhiyicx.thinksnsplus.data.beans.CircleMembers;
import com.zhiyicx.thinksnsplus.data.beans.SendDynamicDataBean;
import com.zhiyicx.thinksnsplus.data.beans.TopicListBean;
import com.zhiyicx.thinksnsplus.data.beans.UserInfoBean;
import com.zhiyicx.thinksnsplus.data.beans.report.ReportResourceBean;
import com.zhiyicx.thinksnsplus.data.source.repository.BaseCircleRepository;
import com.zhiyicx.thinksnsplus.modules.aaaat.AtUserActivity;
import com.zhiyicx.thinksnsplus.modules.aaaat.AtUserListFragment;
import com.zhiyicx.thinksnsplus.modules.chat.ChatActivity;
import com.zhiyicx.thinksnsplus.modules.circle.create.CreateCircleActivity;
import com.zhiyicx.thinksnsplus.modules.circle.create.CreateCircleFragment;
import com.zhiyicx.thinksnsplus.modules.circle.detailv2.CircleDetailContract;
import com.zhiyicx.thinksnsplus.modules.circle.manager.earning.CircleEarningActivity;
import com.zhiyicx.thinksnsplus.modules.circle.manager.members.MemberListFragment;
import com.zhiyicx.thinksnsplus.modules.circle.manager.members.MembersListActivity;
import com.zhiyicx.thinksnsplus.modules.circle.manager.members.attorn.AttornCircleActivity;
import com.zhiyicx.thinksnsplus.modules.circle.manager.members.attorn.AttornCircleFragment;
import com.zhiyicx.thinksnsplus.modules.circle.manager.members.blacklist.BlackListActivity;
import com.zhiyicx.thinksnsplus.modules.circle.manager.members.blacklist.BlackListFragment;
import com.zhiyicx.thinksnsplus.modules.circle.manager.permission.PermissionActivity;
import com.zhiyicx.thinksnsplus.modules.circle.manager.permission.PermissionFragment;
import com.zhiyicx.thinksnsplus.modules.circle.manager.report.ReporReviewFragment;
import com.zhiyicx.thinksnsplus.modules.circle.manager.report.ReportReviewActivity;
import com.zhiyicx.thinksnsplus.modules.circle.pre.PreCircleActivity;
import com.zhiyicx.thinksnsplus.modules.circle.search.onlypost.CirclePostSearchActivity;
import com.zhiyicx.thinksnsplus.modules.dynamic.send.SendDynamicActivity;
import com.zhiyicx.thinksnsplus.modules.dynamic.send.SendDynamicFragment;
import com.zhiyicx.thinksnsplus.modules.markdown_editor.BaseMarkdownActivity;
import com.zhiyicx.thinksnsplus.modules.password.findpassword.FindPasswordActivity;
import com.zhiyicx.thinksnsplus.modules.report.ReportActivity;
import com.zhiyicx.thinksnsplus.modules.report.ReportType;
import com.zhiyicx.thinksnsplus.modules.shortvideo.videostore.VideoSelectActivity;
import com.zhiyicx.thinksnsplus.widget.ExpandableTextView;
import com.zhiyicx.thinksnsplus.widget.coordinatorlayout.AppBarLayoutOverScrollViewBehavior;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

import static android.app.Activity.RESULT_OK;
import static com.zhiyicx.baseproject.widget.popwindow.ActionPopupWindow.POPUPWINDOW_ALPHA;
import static com.zhiyicx.common.config.ConstantConfig.JITTER_SPACING_TIME;
import static me.iwf.photopicker.PhotoPicker.DEFAULT_REQUST_ALBUM;

/**
 * @author Jliuer
 * @Date 2017/11/21/9:50
 * @Email Jliuer@aliyun.com
 * @Description
 */
public class CircleDetailFragmentV2 extends TSViewPagerFragment<CircleDetailContract.PresenterV2>
        implements CircleDetailContract.ViewV2, PostListFragment.OnEventListener, PhotoSelectorImpl.IPhotoBackListener {

    public static final String CIRCLE_ID = "circle_id";

    /**
     * 动态还是圈子动态
     */
    private int mType = SendDynamicDataBean.GROUP_DYNAMIC;

    public static final String GROUP_ID = "group_id";

    private TopicListBean mTopicBean;
    /**
     * 加入圈子
     */
    @BindView(R.id.tv_circle_subscrib)
    TextView mTvCircleSubscrib;

    /**
     * 退出圈子
     */
    @BindView(R.id.tv_exit_circle)
    TextView mTvExitCircle;

    /**
     * 圈子收益
     */
    @BindView(R.id.ll_earnings_container)
    CombinationButton mLlEarningsContainer;

    /**
     * 举报圈子
     */
    @BindView(R.id.bt_report_circle)
    CombinationButton mBtReportCircle;

    /**
     * 权限管理
     */
    @BindView(R.id.ll_permission_container)
    CombinationButton mLlPermissionContainer;

    /**
     * 黑名单
     */
    @BindView(R.id.ll_black_container)
    CombinationButton mLlBlackContainer;

    /**
     * 举报管理
     */
    @BindView(R.id.ll_report_container)
    CombinationButton mLlReportContainer;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @BindView(R.id.circle_appbar_layout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.iv_refresh)
    ImageView mIvRefresh;
    @BindView(R.id.uc_zoomiv)
    ImageView mIvCircleHeadBg;
    @BindView(R.id.iv_circle_head)
    ImageView mIvCircleHead;
    @BindView(R.id.tv_dynamic_count)
    TextView mTvCirclePostCount;
    @BindView(R.id.tv_circle_tag)
    TextView mTvCircleFounder;
    @BindView(R.id.tv_circle_name)
    TextView mTvCircleName;
    @BindView(R.id.tv_circle_title)
    TextView mTvCircleTitle;
    @BindView(R.id.tv_circle_member)
    TextView mTvCircleMember;
    @BindView(R.id.tv_circle_dec)
    TextView mTvCircleDec;
    @BindView(R.id.tv_circle_owner)
    TextView mTvOwnerName;
    @BindView(R.id.tv_introduce_content)
    ExpandableTextView mTvCircleIntroduce;

    @BindView(R.id.iv_share)
    ImageView mIvShare;
    @BindView(R.id.iv_setting)
    ImageView mIvSetting;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.btn_send_post)
    ImageView mBtnSendPost;
    @BindView(R.id.ll_member_container)
    CombinationButton mLlMemberContainer;
    @BindView(R.id.ll_circle_navigation_container)
    LinearLayout mLlCircleNavigationContainer;
    @BindView(R.id.ll_dynamic_count_container)
    LinearLayout mLlDynamicCountContainer;


    @BindView(R.id.rv_circle_chat_member)
    RecyclerView mRvCircleChatMember;
    @BindView(R.id.tv_circle_chat_tag)
    TextView mTvCircleChatTag;
    @BindView(R.id.tv_circle_feed_count)
    TextView tvCircleFeedCount;
    @BindView(R.id.tv_circle_follow_count)
    TextView tvCircleFollowCount;

    @BindView(R.id.ll_intro_container)
    LinearLayout mLlIntroCountContainer;
    @BindView(R.id.v_line)
    View mLine;

    @BindView(R.id.ll_group_container)
    LinearLayout mLlGroupContainer;
    @BindView(R.id.v_group_line)
    View mGroupLine;

    View mStatusBarPlaceholder;

    protected PayPopWindow mPayPopWindow;
    private AppBarLayoutOverScrollViewBehavior myAppBarLayoutBehavoir;

    private CircleInfo mCircleInfo;

    private Subscription showComment;

    private boolean childDataLoaded;

    private PhotoSelectorImpl mPhotoSelector;

    public static CircleDetailFragmentV2 newInstance(long circle_id) {
        CircleDetailFragmentV2 circleDetailFragment = new CircleDetailFragmentV2();
        Bundle bundle = new Bundle();
        bundle.putLong(CIRCLE_ID, circle_id);
        circleDetailFragment.setArguments(bundle);
        return circleDetailFragment;
    }

    private void initStatusBar() {
        // toolBar设置状态栏高度的marginTop
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, com.zhiyicx.common.utils.DeviceUtils
                .getStatuBarHeight(getContext()));
        mStatusBarPlaceholder.setLayoutParams(layoutParams);
    }

    @Override
    protected boolean setUseInputCommentView() {
        return true;
    }

    @Override
    protected boolean setUseInputPsdView() {
        return true;
    }

    @Override
    public void onSureClick(View v, String text, InputPasswordView.PayNote payNote) {
        mPresenter.dealCircleJoinOrExit(mCircleInfo, payNote.psd);
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
    protected int setRightImg() {
        return R.mipmap.ic_show_more_menu;
    }

    @Override
    protected int setRightLeftImg() {
        return R.mipmap.ic_share_white;
    }

    @Override
    protected void setRightClick() {
//        super.setRightClick();
//        boolean isOpen = mDrawer.isDrawerOpen(mLlCircleNavigationContainer);
//        if (isOpen) {
//            return;
//        }
//        mDrawer.openDrawer(Gravity.RIGHT);
        CreateCircleActivity.startUpdateActivity(mActivity, mCircleInfo);
    }

    @Override
    protected void setRightLeftClick() {
        if (mPresenter.isTourist()) {
            showLoginPop();
            return;
        }
        UmengSharePolicyImpl.ShareBean forward = new UmengSharePolicyImpl.ShareBean(R.mipmap.detail_share_forwarding, getString(R.string.share_forward), Share.FORWARD);
        UmengSharePolicyImpl.ShareBean letter = new UmengSharePolicyImpl.ShareBean(R.mipmap.detail_share_sent, getString(R.string.share_letter), Share.LETTER);
        List<UmengSharePolicyImpl.ShareBean> data = new ArrayList<>();
        data.add(forward);
        data.add(letter);
        mPresenter.shareCircle(mCircleInfo,
                ConvertUtils.drawable2BitmapWithWhiteBg(mActivity, mIvCircleHead.getDrawable(), R.mipmap.icon), data);
    }

    @Override
    protected int getToolBarLayoutId() {
        return R.layout.toolbar_custom_contain_status_bar;
    }

    @Override
    protected int getBodyLayoutId() {
        return R.layout.fragment_circle_detail;
    }

    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Override
    protected boolean setUseSatusbar() {
        return true;
    }

    @Override
    protected boolean setStatusbarGrey() {
        return false;
    }

    @Override
    protected boolean setUseStatusView() {
        return false;
    }


    @Override
    protected boolean showToolBarDivider() {
        return false;
    }

    @Override
    protected boolean showToolbar() {
        return true;
    }


    @Override
    protected boolean setUseCenterLoading() {
        return true;
    }

    @Override
    protected int getstatusbarAndToolbarHeight() {
        return 0;
    }

    @Override
    protected View getRightViewOfMusicWindow() {
        return mIvSetting;
    }

    @Override
    protected void setLoadingViewHolderClick() {
        super.setLoadingViewHolderClick();
        mPresenter.getCircleInfo();
    }

    @Override
    public Long getCircleId() {
        return getArguments().getLong(CIRCLE_ID);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PermissionFragment.PERMISSION_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            String permissions = data.getStringExtra(PermissionFragment.PERMISSION);
            mCircleInfo.setPermissions(permissions);
        } else if (requestCode == AttornCircleFragment.ATTORNCIRCLECODE && resultCode == Activity.RESULT_OK && data != null) {
            CircleMembers circleMembers = data.getExtras().getParcelable(AttornCircleFragment.CIRCLE_OWNER);
            mCircleInfo.setUser(circleMembers.getUser());
            mCircleInfo.setUser_id((int) circleMembers.getUser_id());
            CircleJoinedBean joinedBean = mCircleInfo.getJoined();
            joinedBean.setRole(CircleMembers.MEMBER);
            joinedBean.setAudit(CircleJoinedBean.AuditStatus.PASS.value);
            mCircleInfo.setJoined(joinedBean);
            mCircleInfo.getFounder().setUser(circleMembers.getUser());
            mCircleInfo.getFounder().setUser_id((int) circleMembers.getUser_id());
            mTvOwnerName.setText(mCircleInfo.getFounder().getUser().getName());
            setVisiblePermission(mCircleInfo);
        } else if (requestCode == CreateCircleFragment.REQUST_CODE_UPDATE && resultCode == Activity.RESULT_OK && data != null) {
            CircleInfo circleInfo = data.getExtras().getParcelable(CreateCircleFragment.CIRCLEINFO);
            if (circleInfo == null) {
                return;
            }
            mCircleInfo = circleInfo;
            setCircleData(mCircleInfo);
        } else if (requestCode == MemberListFragment.MEMBER_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            int blackListCount = data.getIntExtra(MemberListFragment.BLACK_COUNT, 0);
            int memberListCount = data.getIntExtra(MemberListFragment.MEMBER_COUNT, 0);
            mCircleInfo.setBlacklist_count(blackListCount);
            mCircleInfo.setUsers_count(memberListCount);
            mLlBlackContainer.setRightText("" + mCircleInfo.getBlacklist_count());
            mTvCircleMember.setText(String.format(Locale.getDefault(), getString(R.string.circle_detail_user_posts_count), mCircleInfo
                    .getUsers_count(), mCircleInfo.getPosts_count()));
            mLlMemberContainer.setRightText(String.valueOf(mCircleInfo.getUsers_count() - mCircleInfo.getBlacklist_count()));
        } else if (requestCode == BlackListFragment.BLACKLISTCODE && resultCode == Activity.RESULT_OK && data != null) {
            int blackListCount = data.getIntExtra(MemberListFragment.BLACK_COUNT, 0);
            int memberListCount = data.getIntExtra(MemberListFragment.MEMBER_COUNT, 0);
            // 重新统计成员数量
            mCircleInfo.setUsers_count(memberListCount);
            mTvCircleMember.setText(String.format(Locale.getDefault(), getString(R.string.circle_detail_user_posts_count), mCircleInfo
                    .getUsers_count(), mCircleInfo.getPosts_count()));
            if (blackListCount == 0) {
                mLlMemberContainer.setRightText(String.valueOf(mCircleInfo.getUsers_count() - mCircleInfo.getBlacklist_count()));
                mCircleInfo.setBlacklist_count(blackListCount);
                mLlBlackContainer.setRightText("" + mCircleInfo.getBlacklist_count());
            } else {
                mCircleInfo.setBlacklist_count(blackListCount);
                mLlBlackContainer.setRightText("" + mCircleInfo.getBlacklist_count());
                mLlMemberContainer.setRightText(String.valueOf(mCircleInfo.getUsers_count() - mCircleInfo.getBlacklist_count()));
            }
        } else if (requestCode == AtUserListFragment.REQUES_USER && resultCode == Activity.RESULT_OK) {
            // @ 选人返回
            if (data != null && data.getExtras() != null) {
                UserInfoBean userInfoBean = data.getExtras().getParcelable(AtUserListFragment.AT_USER);
                if (userInfoBean != null) {
                    mIlvComment.appendAt(userInfoBean.getName());
                }
            }
            showComment = Observable.timer(200, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aLong -> showCommentView());
        } else if ((requestCode == PhotoSelectorImpl.CAMERA_PHOTO_CODE || requestCode == UCrop.REQUEST_CROP || requestCode == DEFAULT_REQUST_ALBUM) && resultCode == Activity.RESULT_OK) {
            if (mPhotoSelector != null) {
                mPhotoSelector.onActivityResult(requestCode, resultCode, data);
            }
        }

//        if (mPhotoSelector != null) {
//            mPhotoSelector.onActivityResult(requestCode, resultCode, data);
//        }
    }

    @Subscriber(tag = EventBusTagConfig.EVENT_SEND_DYNAMIC_PHOT_FIRST_OPEN_SEND_DYNAMIC_PAGE)
    public void sendDynamicPhotFirstOpenSendDynamicPage(Intent data) {
        // 获取图片选择器返回结果
        if (mPhotoSelector != null) {
            mPhotoSelector.onActivityResult(DEFAULT_REQUST_ALBUM, RESULT_OK, data);
        }
    }

    @Override
    protected void initData() {
        mPresenter.getCircleInfo();


        mPhotoSelector = DaggerPhotoSelectorImplComponent
                .builder()
                .photoSeletorImplModule(new PhotoSeletorImplModule(this, this, PhotoSelectorImpl
                        .NO_CRAFT))
                .build().photoSelectorImpl();
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        initToolBar();
        initLisener();

        mStatusBarPlaceholder = rootView.findViewById(R.id.v_status_bar_placeholder);
        initStatusBar();
        // 适配手机无法显示输入焦点
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            AndroidBug5497Workaround.assistActivity(mActivity);
        }
    }

    @Override
    public void onChildNetResponseSuccess(boolean isLoadMore) {
        childDataLoaded = true;
        if (mCircleInfo != null) {
            closeLoadingView();
        }
    }

    @Override
    public void onChildNetResponseFailed(boolean isLoadMore) {
        if (!isLoadMore) {
            loadAllError();
        }
    }

    @Override
    public void updateCircleInfo(CircleInfo circleInfo, boolean needFinish) {
        mCircleInfo = circleInfo;
        boolean isJoined = circleInfo.getJoined() != null && circleInfo.getJoined().getAudit() == CircleJoinedBean.AuditStatus.PASS.value;

        boolean isPaid = CircleInfo.CirclePayMode.PAID.value.equals(circleInfo.getMode());

        if (!isJoined && isPaid) {
            PreCircleActivity.startPreCircleActivity(mActivity, circleInfo.getId());
            mActivity.finish();
            return;
        }

        mTvCircleMember.setText(String.format(Locale.getDefault(), getString(R.string.circle_detail_user_posts_count), mCircleInfo
                .getUsers_count(), mCircleInfo.getPosts_count()));
        mLlMemberContainer.setRightText(String.valueOf(circleInfo.getUsers_count() - mCircleInfo.getBlacklist_count()));
        setVisiblePermission(circleInfo);
        if (circleInfo.getJoined() == null && needFinish) {
            mActivity.finish();
            return;
        }

        if (circleInfo.getCircleMembers() != null && circleInfo.getCircleMembers().size() > 1) {

            LinearLayoutManager selectManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            mRvCircleChatMember.setLayoutManager(selectManager);
            ChatMemberAdapter chatMemberAdapter = new ChatMemberAdapter(getContext(), circleInfo.getCircleMembers());
            mRvCircleChatMember.setAdapter(chatMemberAdapter);
            mRvCircleChatMember.addItemDecoration(new LinearDecoration(0, 0, getResources().getDimensionPixelOffset(R.dimen.spacing_small), 0));
        } else {
            mLlGroupContainer.setVisibility(View.GONE);
            mGroupLine.setVisibility(View.GONE);
        }
        if (childDataLoaded) {
            closeLoadingView();
        }
        ((AnimationDrawable) mIvRefresh.getDrawable()).stop();
        mIvRefresh.setVisibility(View.INVISIBLE);
        setCircleData(mCircleInfo);
    }

    private void clickSendPhotoTextDynamic() {
        mPhotoSelector.getPhotoListFromSelector(SendDynamicFragment.MAX_PHOTOS, null);
    }


    private void showCommentView() {
        // 评论
        mIlvComment.setVisibility(View.VISIBLE);
        mIlvComment.setSendButtonVisiable(true);
        mIlvComment.getFocus();
        mVShadow.setVisibility(View.VISIBLE);
        DeviceUtils.showSoftKeyboard(mActivity, mIlvComment.getEtContent());
    }

    /**
     * 是否需要使用权限验证
     *
     * @return
     */
    @Override
    protected boolean usePermisson() {
        return true;
    }

    private void initLisener() {
        mDrawer.setClipToPadding(false);
        mDrawer.setClipChildren(false);
        mDrawer.setScrimColor(Color.TRANSPARENT);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), mDrawer,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                View mContent = mDrawer.getChildAt(0);
                ViewHelper.setTranslationX(mContent,
                        -drawerView.getMeasuredWidth() * slideOffset);
            }
        };

        /*
         * 发帖
         */
        RxView.clicks(mBtnSendPost)
                .throttleFirst(JITTER_SPACING_TIME, TimeUnit.SECONDS)
                .filter(aVoid -> !mPresenter.handleTouristControl())
                .compose(this.bindToLifecycle())
                .subscribe(aVoid -> {
                    // TODO: 2017/12/14
                    // 未申请加入
                    if (mCircleInfo.getJoined() == null) {
                        showAuditTipPopupWindow(getString(R.string.please_join_circle_first));
                        // 已经申请了加入，但被拒绝了
                    } else if (mCircleInfo.getJoined().getAudit() == CircleJoinedBean.AuditStatus.REJECTED.value) {
                        showAuditTipPopupWindow(getString(R.string.circle_join_rejected));
                        // 已经申请了加入，审核中
                    } else if (mCircleInfo.getJoined().getAudit() == CircleJoinedBean.AuditStatus.REVIEWING.value) {
                        showAuditTipPopupWindow(getString(R.string.circle_join_reviewing));
                        // 通过了
                    } else {
                        // 当前角色可用
                        if (mCircleInfo.getPermissions().contains(mCircleInfo.getJoined().getRole())) {
                            if (mCircleInfo.getJoined()
                                    .getDisabled() == CircleJoinedBean.DisableStatus.NORMAL.value) {
                                ValueAnimator valueAnimator = ObjectAnimator.ofFloat(mBtnSendPost, "rotation", 0f, 90f);
                                valueAnimator.setDuration(500);
                                valueAnimator.start();
                                valueAnimator.addListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animator) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animator) {
                                        if (mBtnSendPost != null) {
                                            mBtnSendPost.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    PublishPostMenuDialog publishPostMenuDialog = new PublishPostMenuDialog(getContext());
                                                    publishPostMenuDialog.showDialog(new PublishPostMenuDialog.OnPublishListener() {
                                                        @Override
                                                        public void ondetachFromWindow() {
                                                            ValueAnimator valueAnimator = ObjectAnimator.ofFloat(mBtnSendPost, "rotation", 90f, 0);
                                                            valueAnimator.setDuration(500);
                                                            valueAnimator.start();
                                                        }

                                                        @Override
                                                        public void onUploadPicClick() {
//                                                            BaseMarkdownActivity.startActivityForPublishPostInCircle(mActivity, mCircleInfo);
                                                            clickSendPhotoTextDynamic();
                                                        }

                                                        @Override
                                                        public void onUploadVideoClick() {
                                                            mRxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
                                                                    .subscribe(aBoolean -> {
                                                                        if (aBoolean) {
                                                                            if (DeviceUtils.getSDCardAvailableSize() >= 100) {
                                                                                SendDynamicDataBean sendDynamicDataBean = SharePreferenceUtils.getObject(mActivity, SharePreferenceUtils
                                                                                        .VIDEO_DYNAMIC);
                                                                                if (checkVideoDraft(sendDynamicDataBean)) {
                                                                                    sendDynamicDataBean.setDynamicChannlId(mCircleInfo.getId());
                                                                                    SendDynamicActivity.startToSendDynamicActivity(getContext(), sendDynamicDataBean);/*mTopicBean*/
                                                                                } else {
                                                                                    TopicListBean topicListBean = new TopicListBean();
                                                                                    topicListBean.setId(mCircleInfo.getId());
                                                                                    topicListBean.setName(mCircleInfo.getName());
                                                                                    VideoSelectActivity.startVideoSelectActivity(mActivity, false, topicListBean);/*mTopicBean*/
                                                                                }
//                                                                                closeActivity();
                                                                            } else {
                                                                                showSnackErrorMessage(getString(R.string.storage_no_free));
                                                                            }
                                                                        } else {
                                                                            showSnackWarningMessage(getString(R.string.please_open_camera_and_mic_permisssion));

                                                                        }
                                                                    });
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animator) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animator) {

                                    }
                                });

//                                BaseMarkdownActivity.startActivityForPublishPostInCircle(mActivity, mCircleInfo);
                            }
                        } else if (CircleMembers.BLACKLIST.equals(mCircleInfo.getJoined().getRole())) {
                            //被拉到了黑名单
                            showAuditTipPopupWindow(getString(R.string.circle_member_added_blacklist));
                        } else {
                            // 没有权限发帖
                            if (mCircleInfo.getPermissions().contains(CircleMembers.ADMINISTRATOR)) {
                                showAuditTipPopupWindow(getString(R.string.publish_circle_post_format, mCircleInfo.getName(), getString(R
                                        .string.circle_master_manager)));
                            } else {
                                showAuditTipPopupWindow(getString(R.string.publish_circle_post_format, mCircleInfo.getName(), getString(R
                                        .string.circle_master)));
                            }

                        }
                    }

                });

        RxView.clicks(mIvShare)
                .throttleFirst(JITTER_SPACING_TIME, TimeUnit.SECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(aVoid -> {
                    UmengSharePolicyImpl.ShareBean forward = new UmengSharePolicyImpl.ShareBean(R.mipmap.detail_share_forwarding, getString(R.string.share_forward), Share.FORWARD);
                    UmengSharePolicyImpl.ShareBean letter = new UmengSharePolicyImpl.ShareBean(R.mipmap.detail_share_sent, getString(R.string.share_letter), Share.LETTER);

                    List<UmengSharePolicyImpl.ShareBean> data = new ArrayList<>();
                    data.add(forward);
                    data.add(letter);

                    mPresenter.shareCircle(mCircleInfo,
                            ConvertUtils.drawable2BitmapWithWhiteBg(mActivity, mIvCircleHead.getDrawable(), R.mipmap.icon), data);
                });

        RxView.clicks(mTvCircleFounder)
                .throttleFirst(JITTER_SPACING_TIME, TimeUnit.SECONDS)
                .filter(aVoid -> !mPresenter.handleTouristControl())
                .subscribe(aVoid -> ChatActivity.startChatActivity(mActivity, mCircleInfo.getFounder().getUser().getUser_id() + "",
                        EaseConstant.CHATTYPE_SINGLE));

        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        myAppBarLayoutBehavoir = (AppBarLayoutOverScrollViewBehavior)
                ((CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams()).getBehavior();

        myAppBarLayoutBehavoir.setOnRefreshChangeListener(new AppBarLayoutOverScrollViewBehavior.onRefreshChangeListener() {
            @Override
            public void onRefreshShow() {
                mIvRefresh.setVisibility(View.VISIBLE);
                ((AnimationDrawable) mIvRefresh.getDrawable()).start();
            }

            @Override
            public void doRefresh() {
                CircleDetailFragmentV2.this.doRefresh();
            }

            @Override
            public void alphaChange(float a) {
                mTvCircleName.setAlpha(a);
            }
        });

        mTvCircleIntroduce.setExpandListener(new ExpandableTextView.OnExpandListener() {
            @Override
            public void onExpand(ExpandableTextView view) {
                Observable.empty()
                        .delay(100, TimeUnit.MILLISECONDS)
                        .subscribe(new EmptySubscribe<Object>() {
                            @Override
                            public void onCompleted() {
                                if (myAppBarLayoutBehavoir != null) {
                                    myAppBarLayoutBehavoir.initial(mAppBarLayout);
                                    return;
                                }
                                unsubscribe();
                            }
                        });
            }

            @Override
            public void onShrink(ExpandableTextView view) {
                Observable.empty()
                        .delay(100, TimeUnit.MILLISECONDS)
                        .subscribe(new EmptySubscribe<Object>() {
                            @Override
                            public void onCompleted() {
                                if (myAppBarLayoutBehavoir != null) {
                                    myAppBarLayoutBehavoir.initial(mAppBarLayout);
                                    return;
                                }
                                unsubscribe();
                            }
                        });
            }
        });


    }

    private void doRefresh() {
        Fragment fragment = mFragmentList.get(mVpFragment.getCurrentItem());
        if (fragment != null && fragment instanceof PostListFragment) {
            ((PostListFragment) fragment).requestNetData();
        }
    }

    @Override
    protected boolean setUseShadowView() {
        return true;
    }

    @Override
    protected void onShadowViewClick() {
        closeInputView();
    }

    private void initToolBar() {
        if (setUseStatusView()) {
            // toolBar 设置状态栏高度的 marginTop
            int height = getResources().getDimensionPixelSize(R.dimen.spacing_large);
            CollapsingToolbarLayout.LayoutParams layoutParams = (CollapsingToolbarLayout.LayoutParams) mToolbar.getLayoutParams();
            layoutParams.setMargins(0, height, 0, 0);
            mToolbar.setLayoutParams(layoutParams);
        }
        DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) mLlCircleNavigationContainer.getLayoutParams();
        params.width = DeviceUtils.getScreenWidth(getActivity()) / 2;
        mLlCircleNavigationContainer.setLayoutParams(params);

    }

    private void closeInputView() {
        if (mIlvComment.getVisibility() == View.VISIBLE) {
            mIlvComment.setVisibility(View.GONE);
            DeviceUtils.hideSoftKeyboard(mActivity, mIlvComment.getEtContent());
        }
        mVShadow.setVisibility(View.GONE);
        Fragment fragment = mFragmentList.get(mVpFragment.getCurrentItem());
        if (fragment != null && fragment instanceof PostListFragment) {
            ((PostListFragment) fragment).closeInputView();
        }
    }

    private void setCircleData(CircleInfo detail) {
        mTvCircleTitle.setText(detail.getName());
        mTvCircleName.setText(detail.getName());
        String postCount = String.format("帖子:%d", detail.getPosts_count());
        tvCircleFeedCount.setText(postCount);
        String memberCount = String.format("成员:%d", detail.getUsers_count());
        tvCircleFollowCount.setText(memberCount);
        mLlMemberContainer.setRightText(String.valueOf(detail.getUsers_count() - mCircleInfo.getBlacklist_count()));
        String location = detail.getLocation();
        if (TextUtils.isEmpty(location)) {
            String[] defaultLocation = mActivity.getResources().getStringArray(R.array
                    .default_location_array);
            Random random = new Random();
            location = defaultLocation[random.nextInt(defaultLocation.length - 1) % (defaultLocation.length - 1)];
        }
        mTvCircleDec.setText(String.format(Locale.getDefault(), getString(R.string.circle_detail_location), location));
        mTvCircleMember.setText(String.format(Locale.getDefault(), getString(R.string.circle_detail_user_posts_count), mCircleInfo
                .getUsers_count(), mCircleInfo.getPosts_count()));
        mTvCirclePostCount.setText(String.format(Locale.getDefault(), getString(R.string.circle_detail_postcount), detail.getPosts_count()));
        mLlBlackContainer.setRightText("" + mCircleInfo.getBlacklist_count());
        mTvOwnerName.setText(detail.getFounder().getUser().getName());
        if (!TextUtils.isEmpty(detail.getSummary())) {
            mTvCircleIntroduce.setText(detail.getSummary() + "");
        } else {
            mTvCircleIntroduce.setText("暂无简介");
        }

//        mLlIntroCountContainer.setVisibility(TextUtils.isEmpty(detail.getSummary()) ? View.GONE : View.VISIBLE);
//        mLine.setVisibility(TextUtils.isEmpty(detail.getSummary()) ? View.GONE : View.VISIBLE);
        mLine.setVisibility(View.GONE);

        mLlGroupContainer.setVisibility(View.GONE);
        mGroupLine.setVisibility(View.GONE);

//        mLlIntroCountContainer.setVisibility(TextUtils.isEmpty(detail.getSummary()) ? View.GONE : View.VISIBLE);
//        mLine.setVisibility(mLlIntroCountContainer.getVisibility());
        Observable.empty()
                .delay(100, TimeUnit.MILLISECONDS)
                .subscribe(new EmptySubscribe<Object>() {
                    @Override
                    public void onCompleted() {
                        if (myAppBarLayoutBehavoir != null) {
                            myAppBarLayoutBehavoir.initial(mAppBarLayout);
                            return;
                        }
                        unsubscribe();
                    }
                });

        GlideManager.glideCircle(mActivity, mIvCircleHead, detail.getAvatar() != null ? detail.getAvatar().getUrl() : "", R.mipmap.ic_default_user_head_circle);
//        Glide.with(mActivity)
//                .load(detail.getAvatar() != null ? detail.getAvatar().getUrl() : "")
//                .transform(new GlideStokeTransform(getActivity(), 5))
//                .error(R.drawable.shape_default_image)
//                .placeholder(R.drawable.shape_default_image)
//                .listener(new RequestListener<String, GlideDrawable>() {
//                    @Override
//                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                        mIvCircleHeadBg.setImageResource(R.mipmap.default_pic_personal);
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean
//                            isFromMemoryCache, boolean isFirstResource) {
//                        Bitmap bitmap = FastBlur.blurBitmap(ConvertUtils.drawable2Bitmap(resource), resource.getIntrinsicWidth(), resource
//                                .getIntrinsicHeight());
//                        mIvCircleHeadBg.setImageBitmap(bitmap);
//                        return false;
//                    }
//                })
//                .into(mIvCircleHead);

    }

    private void setVisiblePermission(CircleInfo detail) {
        boolean isJoined = detail.getJoined() != null;
        boolean isNormalMember = isJoined && CircleMembers.MEMBER.equals(detail.getJoined().getRole());
        boolean isBlackList = isJoined && CircleMembers.BLACKLIST.equals(detail.getJoined().getRole());
        boolean isManager = isJoined && CircleMembers.ADMINISTRATOR.equals(detail.getJoined().getRole());
        boolean isOwner = isJoined && CircleMembers.FOUNDER.equals(detail.getJoined().getRole());
        boolean canReport = !(isManager || isOwner);
        if (isJoined && detail.getJoined().getDisabled() == 1) {
            detail.getJoined().setRole(CircleMembers.BLACKLIST);
        }
        mTvCircleSubscrib.setVisibility(isJoined ? View.GONE : View.VISIBLE);
        mTvExitCircle.setVisibility(!isJoined ? View.GONE : View.VISIBLE);
        mTvExitCircle.setText(R.string.circle_exit);
        if (isOwner && detail.getUsers_count() > 1) {
            mTvExitCircle.setText(R.string.circle_transfer);
        } else if (isOwner && detail.getUsers_count() <= 1) {
            mTvExitCircle.setVisibility(View.GONE);
        }
        mLlEarningsContainer.setVisibility(!isOwner ? View.GONE : View.VISIBLE);
        mBtReportCircle.setVisibility((canReport || mPresenter.isTourist()) ? View.VISIBLE : View.GONE);

        mLlPermissionContainer.setVisibility(!isOwner ? View.GONE : View.VISIBLE);
        mLlReportContainer.setVisibility(!isOwner && !isManager ? View.GONE : View.VISIBLE);
        mLlBlackContainer.setVisibility(!isOwner && !isManager ? View.GONE : View.VISIBLE);
        mTvCircleFounder.setVisibility(detail.getFounder().getUser_id() == AppApplication.getMyUserIdWithdefault() ? View.GONE : View.VISIBLE);
    }

    @OnClick({R.id.ll_member_container, R.id.ll_detail_container, R.id.ll_earnings_container, R.id.ll_black_container,
            R.id.ll_permission_container, R.id.ll_report_container, R.id.iv_back, R.id.iv_serach,
            R.id.iv_share, R.id.iv_setting, R.id.tv_circle_subscrib, R.id.tv_exit_circle, R.id.bt_report_circle})
    public void onViewClicked(View view) {
        boolean isJoing = mCircleInfo.getJoined() != null && mCircleInfo.getJoined().getAudit() == CircleJoinedBean.AuditStatus.PASS.value;

        boolean isBlackList = isJoing && CircleMembers.BLACKLIST.equals(mCircleInfo.getJoined().getRole());
        switch (view.getId()) {
            case R.id.ll_member_container:
//                if (isBlackList) {
//                    showAuditTipPopupWindow(getString(R.string.circle_member_added_blacklist));
//                    return;
//                }
                Intent intent = new Intent(mActivity, MembersListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(MemberListFragment.MEMBER_COUNT, mCircleInfo.getUsers_count());
                bundle.putInt(MemberListFragment.BLACK_COUNT, mCircleInfo.getBlacklist_count());
                bundle.putLong(MemberListFragment.CIRCLEID, mCircleInfo.getId());
                bundle.putString(MemberListFragment.ROLE, isJoing ? mCircleInfo.getJoined().getRole() : "");
                intent.putExtras(bundle);
                mActivity.startActivityForResult(intent, MemberListFragment.MEMBER_REQUEST);
                break;
            case R.id.ll_detail_container:
//                if (isBlackList) {
//                    showAuditTipPopupWindow(getString(R.string.circle_member_added_blacklist));
//                    return;
//                }
                CreateCircleActivity.startUpdateActivity(mActivity, mCircleInfo);
                break;
            case R.id.ll_earnings_container:
                CircleEarningActivity.startActivity(mActivity, mCircleInfo);
                break;
            case R.id.ll_permission_container:
                PermissionActivity.startActivity(mActivity, mCircleInfo.getId(), mCircleInfo.getPermissions());
                break;
            case R.id.ll_report_container:

                Intent intent1 = new Intent(mActivity, ReportReviewActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putLong(ReporReviewFragment.SOURCEID, mCircleInfo.getId());
                intent1.putExtras(bundle1);
                startActivity(intent1);
                break;

            case R.id.iv_back:
                setLeftClick();
                break;
            case R.id.iv_serach:
                CirclePostSearchActivity.startCircelPostSearchActivity(mActivity, mCircleInfo);
                break;
            case R.id.iv_share:
                break;
            case R.id.iv_setting:
                boolean isOpen = mDrawer.isDrawerOpen(mLlCircleNavigationContainer);
                if (isOpen) {
                    return;
                }
                mDrawer.openDrawer(Gravity.RIGHT);
                break;
            case R.id.tv_circle_subscrib:
                boolean isPaid = CircleInfo.CirclePayMode.PAID.value.equals(mCircleInfo.getMode());
                if (isPaid) {
                    initPayPopWindow(mActivity, mCircleInfo.getMoney(), mPresenter.getRatio(), mPresenter.getGoldName()
                            , R.string.buy_pay_circle_desc);
                } else {
                    mPresenter.dealCircleJoinOrExit(mCircleInfo, null);
                }
                break;
            case R.id.tv_exit_circle:
                boolean isOwner = CircleMembers.FOUNDER.equals(mCircleInfo.getJoined().getRole());
                if (isOwner) {
                    Intent intent2 = new Intent(mActivity, AttornCircleActivity.class);
                    Bundle bundle2 = new Bundle();
                    bundle2.putLong(AttornCircleFragment.CIRCLEID, mCircleInfo.getId());
                    bundle2.putString(AttornCircleFragment.ROLE, isJoing ? mCircleInfo.getJoined().getRole() : "");
                    bundle2.putString(AttornCircleFragment.CIRCLE_NAME, mCircleInfo.getName());
                    intent2.putExtras(bundle2);
                    mActivity.startActivityForResult(intent2, AttornCircleFragment.ATTORNCIRCLECODE);
                } else {
                    mPresenter.dealCircleJoinOrExit(mCircleInfo, null);
                }
                break;
            /*
             * 举报圈子
             */
            case R.id.bt_report_circle:
                if (mPresenter.handleTouristControl()) {
                    return;
                }
                ReportActivity.startReportActivity(mActivity, new ReportResourceBean(mCircleInfo.getFounder().getUser(), mCircleInfo.getId().toString
                        (), mCircleInfo.getName(), mCircleInfo.getAvatar(), mCircleInfo.getSummary(), ReportType.CIRCLE));

                break;
            // 黑名单管理
            case R.id.ll_black_container:
                Intent intent3 = new Intent(mActivity, BlackListActivity.class);
                Bundle bundle3 = new Bundle();
                bundle3.putLong(BlackListFragment.CIRCLEID, mCircleInfo.getId());
                bundle3.putInt(BlackListFragment.MEMBER_COUNT, mCircleInfo.getUsers_count());
                bundle3.putInt(BlackListFragment.BLACK_COUNT, mCircleInfo.getBlacklist_count());
                bundle3.putString(BlackListFragment.ROLE, isJoing ? mCircleInfo.getJoined().getRole() : "");
                intent3.putExtras(bundle3);
                mActivity.startActivityForResult(intent3, BlackListFragment.BLACKLISTCODE);
                break;
            default:
        }
    }

    @Override
    public CircleInfo getCircleInfo() {
        return mCircleInfo;
    }

    @Override
    public void loadAllError() {
        setLoadViewHolderImag(R.mipmap.img_default_internet);
        showLoadViewLoadError();
    }

    /**
     *
     */
    @Override
    public void onDestroyView() {
        if (showComment != null && !showComment.isUnsubscribed()) {
            showComment.unsubscribe();
        }
        super.onDestroyView();
    }

    /**
     * @param context
     * @param amout    金额
     * @param ratio    转换率
     * @param goldName 单位名称
     * @param strRes   描述文字
     */
    protected void initPayPopWindow(Activity context, long amout, int ratio, String goldName, int strRes) {

        mPayPopWindow = PayPopWindow.builder()
                .with(context)
                .isWrap(true)
                .isFocus(true)
                .isOutsideTouch(true)
                .buildLinksColor1(R.color.themeColor)
                .buildLinksColor2(R.color.important_for_content)
                .contentView(R.layout.ppw_for_center)
                .backgroundAlpha(POPUPWINDOW_ALPHA)
                .buildDescrStr(String.format(context.getString(strRes) + context.getString(R
                        .string.buy_pay_member), PayConfig.realCurrency2GameCurrencyStr(amout, ratio), goldName))
                .buildLinksStr(context.getString(R.string.buy_pay_member))
                .buildTitleStr(context.getString(R.string.buy_pay))
                .buildItem1Str(context.getString(R.string.buy_pay_in))
                .buildItem2Str(context.getString(R.string.buy_pay_out))
                .buildMoneyStr(String.format(context.getString(R.string.buy_pay_integration), PayConfig.realCurrency2GameCurrencyStr(amout, ratio)))
                .buildCenterPopWindowItem1ClickListener(() -> {
                    if (mPresenter.usePayPassword()) {
                        mIlvPassword.setPayNote(new InputPasswordView.PayNote(null));
                        showInputPsdView(true);
                    } else {
                        mPresenter.dealCircleJoinOrExit(mCircleInfo, null);
                    }
                    mPayPopWindow.hide();
                })
                .buildCenterPopWindowItem2ClickListener(() -> mPayPopWindow.hide())
                .buildCenterPopWindowLinkClickListener(new PayPopWindow
                        .CenterPopWindowLinkClickListener() {
                    @Override
                    public void onLongClick() {

                    }

                    @Override
                    public void onClicked() {

                    }
                })
                .build();
        mPayPopWindow.show();
    }

    @Override
    protected List<String> initTitles() {
        return Arrays.asList(mActivity.getResources().getStringArray(R.array
                .post_typpe_array));
    }

    @Override
    protected void initViewPager(View rootView) {
        mTsvToolbar = rootView.findViewById(com.zhiyicx.baseproject.R.id.tsv_toolbar);
        mTsvToolbar.setLeftImg(0);
        mTsvToolbar.setDefaultTabLinehegiht(R.integer.no_line_height);
        mVpFragment = rootView.findViewById(com.zhiyicx.baseproject.R.id.vp_fragment);
        tsViewPagerAdapter = new TSViewPagerAdapter(getChildFragmentManager());

        mVpFragment.setAdapter(tsViewPagerAdapter);
        mTsvToolbar.setAdjustMode(isAdjustMode());
        mTsvToolbar.setTabSpacing(tabSpacing());
        mTsvToolbar.setIndicatorMode(setIndicatorMode());
        mTsvToolbar.initTabView(mVpFragment, initTitles());

        tsViewPagerAdapter.bindData(initFragments());
        mTsvToolbar.setLeftClickListener(this, this::setLeftClick);
        mVpFragment.setOffscreenPageLimit(getOffsetPage());
    }

    @Override
    protected boolean isAdjustMode() {
        return true;
    }

    @Override
    protected List<Fragment> initFragments() {
        if (mFragmentList == null) {
            mFragmentList = new ArrayList();
            PostListFragment fragment1 = PostListFragment.newInstance(BaseCircleRepository.CircleMinePostType.LATEST_POST, getCircleId());
            PostListFragment fragment2 = PostListFragment.newInstance(BaseCircleRepository.CircleMinePostType.LATEST_REPLY, getCircleId());
            PostListFragment fragment3 = PostListFragment.newInstance(BaseCircleRepository.CircleMinePostType.EXCELLENT, getCircleId());
            fragment1.setOnEventListener(this);
            fragment2.setOnEventListener(this);
            fragment3.setOnEventListener(this);
            mFragmentList.add(fragment1);
            mFragmentList.add(fragment2);
            mFragmentList.add(fragment3);
        }
        return mFragmentList;
    }

    @Override
    public void onSendClick(View v, String text) {
        DeviceUtils.hideSoftKeyboard(getContext(), v);
        mIlvComment.setVisibility(View.GONE);
        mVShadow.setVisibility(View.GONE);
        if (mFragmentList != null) {
            if (mFragmentList.get(mVpFragment.getCurrentItem()) instanceof PostListFragment) {
                ((PostListFragment) mFragmentList.get(mVpFragment.getCurrentItem())).onSendClick(v, text);
            }
        }
    }

    @Override
    public void onAtTrigger() {
        AtUserActivity.startAtUserActivity(this);
    }

    @Override
    public void showComment() {
        mIlvComment.setVisibility(View.VISIBLE);
        mIlvComment.setSendButtonVisiable(true);

        mIlvComment.getFocus();
        mVShadow.setVisibility(View.VISIBLE);
        DeviceUtils.showSoftKeyboard(getActivity(), mIlvComment.getEtContent());
    }

    @Override
    public void hideComment() {

    }


    @Override
    public void hideRefresh() {
        ((AnimationDrawable) mIvRefresh.getDrawable()).stop();
        mIvRefresh.setVisibility(View.INVISIBLE);
        myAppBarLayoutBehavoir.setRefreshing(false);
    }

    @Override
    public CircleInfo getCurrentCircleInfo() {
        return mCircleInfo;
    }

    private boolean checkVideoDraft(SendDynamicDataBean sendDynamicDataBean) {

        if (sendDynamicDataBean != null) {
            boolean hasVideo = FileUtils.isFileExists(sendDynamicDataBean.getVideoInfo().getPath());
//            boolean hasCover = FileUtils.isFileExists(sendDynamicDataBean.getVideoInfo().getCover());
            return hasVideo;
//            return hasCover && hasVideo;
        }
        return false;
    }

    public void closeActivity() {
        getActivity().finish();
//        getActivity().overridePendingTransition(R.anim.animate_noting, R.anim.send_type_colse_fade_out);
    }

    @Override
    public void getPhotoSuccess(List<ImageBean> photoList) {
        // 跳转到发送动态页面
        SendDynamicDataBean sendDynamicDataBean = new SendDynamicDataBean();
        sendDynamicDataBean.setDynamicBelong(0);
        sendDynamicDataBean.setDynamicPrePhotos(photoList);
        if (getArguments() != null) {
            sendDynamicDataBean.setDynamicChannlId(getArguments().getLong(CIRCLE_ID));
        }
        sendDynamicDataBean.setDynamicType(SendDynamicDataBean.PHOTO_TEXT_DYNAMIC);
        SendDynamicActivity.startToSendDynamicActivity(getContext(), sendDynamicDataBean, mTopicBean);
//        closeActivity();
    }

    @Override
    public void getPhotoFailure(String errorMsg) {

    }

}
