package com.zhiyicx.thinksnsplus.modules.home.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

//import com.wcy.overscroll.OverScrollLayout;
import com.zhiyi.emoji.ViewUtils;
import com.zhiyicx.baseproject.base.TSFragment;
import com.zhiyicx.baseproject.config.ApiConfig;
import com.zhiyicx.baseproject.utils.glide.GlideManager;
import com.zhiyicx.baseproject.widget.BadgeView;
import com.zhiyicx.baseproject.widget.UserAvatarView;
import com.zhiyicx.baseproject.widget.button.CombinationButton;
import com.zhiyicx.common.utils.ConvertUtils;
import com.zhiyicx.common.utils.DeviceUtils;
import com.zhiyicx.common.utils.ToastUtils;
import com.zhiyicx.common.widget.popwindow.CustomPopupWindow;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.base.AppApplication;
import com.zhiyicx.thinksnsplus.data.beans.UserCertificationInfo;
import com.zhiyicx.thinksnsplus.data.beans.UserInfoBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoListBean;
import com.zhiyicx.thinksnsplus.modules.certification.input.CertificationInputActivity;
import com.zhiyicx.thinksnsplus.modules.edit_userinfo.UserInfoActivity;
import com.zhiyicx.thinksnsplus.modules.feedback.FeedBackActivity;
import com.zhiyicx.thinksnsplus.modules.home.MyPostListActivity;
import com.zhiyicx.thinksnsplus.modules.home.mine.collection.CollectionActivity;
import com.zhiyicx.thinksnsplus.modules.home.mine.generalize.GeneralizeActivity;
import com.zhiyicx.thinksnsplus.modules.home.mine.mycode.MyCodeActivity;
import com.zhiyicx.thinksnsplus.modules.settings.SettingsActivity;
import com.zhiyicx.thinksnsplus.modules.settings.aboutus.CustomWEBActivity;
import com.zhiyicx.thinksnsplus.modules.video.VideoDetailActivity;
import com.zhiyicx.thinksnsplus.utils.ImageUtils;
import com.zhiyicx.thinksnsplus.utils.MyUtils;
import com.zhiyicx.thinksnsplus.widget.CertificationTypePopupWindow;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Observable;
import rx.schedulers.Schedulers;

import static com.zhiyicx.baseproject.config.ApiConfig.URL_ABOUT_US;
import static com.zhiyicx.thinksnsplus.modules.certification.input.CertificationInputActivity.BUNDLE_CERTIFICATION_TYPE;
import static com.zhiyicx.thinksnsplus.modules.certification.input.CertificationInputActivity.BUNDLE_TYPE;

/**
 * @Describe 我的页面
 * @Author Jungle68
 * @Date 2017/1/5
 * @Contact master.jungle68@gmail.com
 */
public class MineFragment2 extends TSFragment<MineContract.Presenter> implements MineContract.View,
        CertificationTypePopupWindow.OnTypeSelectListener {
    @BindView(R.id.iv_head_icon)
    UserAvatarView ivHeadIcon;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.rl_userinfo_container)
    RelativeLayout rlUserinfoContainer;
    @BindView(R.id.tv_fans_count)
    TextView tvFansCount;
    @BindView(R.id.bv_fans_new_count)
    BadgeView bvFansNewCount;
    @BindView(R.id.ll_fans_container)
    RelativeLayout llFansContainer;
    @BindView(R.id.tv_follow_count)
    TextView tvFollowCount;
    @BindView(R.id.ll_follow_container)
    LinearLayout llFollowContainer;
    @BindView(R.id.tv_friends_count)
    TextView tvFriendsCount;
    @BindView(R.id.bv_friends_new_count)
    BadgeView bvFriendsNewCount;
    @BindView(R.id.ll_friends_container)
    RelativeLayout llFriendsContainer;
    @BindView(R.id.bt_watch_history)
    CombinationButton btWatchHistory;
    @BindView(R.id.bt_mine_invitation)
    CombinationButton btMineInvitation;
    @BindView(R.id.bt_mine_download)
    CombinationButton btMineDownload;
    @BindView(R.id.bt_mine_collection)
    CombinationButton btMineCollection;
    @BindView(R.id.bt_feed_back)
    CombinationButton btFeedBack;
    @BindView(R.id.bt_about_us)
    CombinationButton btAboutUs;
//    @BindView(R.id.overscroll)
//    OverScrollLayout overscroll;
    @BindView(R.id.rv_video_history_record)
    RecyclerView rvVideoHistoryRecord;
    Unbinder unbinder;

    /**
     * 选择认证人类型的弹窗
     */
    private CertificationTypePopupWindow mCertificationWindow;

    @Inject
    public MinePresenter mMinePresenter;

    private UserInfoBean mUserInfoBean;
    private UserCertificationInfo mUserCertificationInfo;

    public static MineFragment2 newInstance() {
        MineFragment2 fragment = new MineFragment2();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Observable.create(subscriber -> {
            DaggerMinePresenterComponent.builder()
                    .appComponent(AppApplication.AppComponentHolder.getAppComponent())
                    .minePresenterModule(new MinePresenterModule(MineFragment2.this))
                    .build().inject(MineFragment2.this);
            subscriber.onCompleted();
        }).subscribeOn(Schedulers.io())
                .subscribe(o -> {
                }, Throwable::printStackTrace);
    }

    @Override
    protected void initView(View rootView) {
//        mBtCertification.setEnabled(false);
//        mVvFansNewCount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
//        mBvFriendsNewCount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
    }


    @Override
    protected void initData() {
        rvVideoHistoryRecord.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.HORIZONTAL, false));
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        reLoadUserInfo(isVisibleToUser);
    }

    private void reLoadUserInfo(boolean isVisibleToUser) {
        if (isVisibleToUser && mPresenter != null) {
            mPresenter.getUserInfoFromDB();
            mPresenter.updateUserNewMessage();
            mPresenter.updateUserInfo();
            mPresenter.getCertificationInfo();
            mSystemConfigBean = mPresenter.getSystemConfigBean();
//            mBtMineQA.setVisibility(mSystemConfigBean.getQuestionConfig().isStatus() ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        reLoadUserInfo(getUserVisibleHint());
        mPresenter.updateVideoRecord();
    }

    @Override
    protected String setCenterTitle() {
//        return getString(R.string.mine);
        return "";
    }

    @Override
    protected int setRightImg() {
        return R.mipmap.ic_setting;
    }

    @Override
    protected int setRightLeftImg() {
        return R.mipmap.ic_msg;
    }

    @Override
    protected String setRightTitle() {
        return "";
    }

    @Override
    protected int setLeftImg() {
        return R.mipmap.app_icon;
    }


    @Override
    protected boolean showToolBarDivider() {
        return false;
    }

    @Override
    protected boolean setUseSatusbar() {
        return true;
    }

    @Override
    protected boolean setUseStatusView() {
        return true;
    }

    @Override
    protected int getBodyLayoutId() {
        return R.layout.fragment_mine;
    }


    @Override
    protected int setToolBarBackgroud() {
        return R.color.white;
    }

    @Override
    protected void setRightClick() {
        super.setRightClick();
//      startActivity(new Intent(mActivity, MyCodeActivity.class));
        startActivity(new Intent(mActivity, SettingsActivity.class));
    }

    @Override
    protected void setLeftClick() {
//        super.setLeftClick();
    }

    @Override
    public void setUserInfo(UserInfoBean userInfoBean) {
//        btMineIntegration.setLeftText(getString(R.string.my_integration_name, mPresenter.getGoldName()));
        if (userInfoBean == null) {
            return;
        }
        if (mUserInfoBean == null) {
            // 设置用户头像
            ImageUtils.loadCircleUserHeadPic(userInfoBean, ivHeadIcon);
        } else {
            boolean imageAvatarIsChanged = userInfoBean.getAvatar() != null && (mUserInfoBean.getAvatar() == null || !userInfoBean.getAvatar()
                    .equals(mUserInfoBean.getAvatar()));
            boolean verifiedIsChanged = userInfoBean.getVerified() != null && userInfoBean.getVerified().getType() != null && (mUserInfoBean
                    .getVerified() == null ||
                    !userInfoBean.getVerified().getType().equals(mUserInfoBean
                            .getVerified()
                            .getType()));
            if (imageAvatarIsChanged || verifiedIsChanged) {
                // 设置用户头像
                ImageUtils.loadCircleUserHeadPic(userInfoBean, ivHeadIcon);
            }
        }
        // 设置用户名
        tvUserName.setText(userInfoBean.getName());
        // 设置简介
//        mTvUserSignature.setText(TextUtils.isEmpty(userInfoBean.getIntro()) ? getString(R.string.intro_default) : userInfoBean.getIntro());
        // 设置粉丝数
        String followedCount = String.valueOf(userInfoBean.getExtra().getFollowers_count());
        tvFansCount.setText(ConvertUtils.numberConvert(Integer.parseInt(followedCount)));
        // 设置关注数
        String followingCount = String.valueOf(userInfoBean.getExtra().getFollowings_count());
        tvFollowCount.setText(ConvertUtils.numberConvert(Integer.parseInt(followingCount)));
//        double myMoney = 0;
//        if (userInfoBean.getWallet() != null) {
//            myMoney = userInfoBean.getWallet().getBalance();
//        }
//        mBtWallet.setRightText(getString(R.string.money_format_with_unit, PayConfig.realCurrencyFen2Yuan(myMoney)
//                , ""));
//        btMineIntegration.setRightText(String.valueOf(userInfoBean.getFormatCurrencyNum()));
        this.mUserInfoBean = userInfoBean;
        // 设置好友数
//        String friendsCount = String.valueOf(userInfoBean.getFriends_count());
//        tvFriendsCount.setText(friendsCount);
        tvFriendsCount.setText("");
    }

    @Override
    public void setNewFollowTip(int count) {
        bvFansNewCount.setBadgeCount(Integer.parseInt(ConvertUtils.messageNumberConvert(count)));
    }


    @Override
    public void setNewFriendsTip(int count) {
        bvFriendsNewCount.setBadgeCount(Integer.parseInt(ConvertUtils.messageNumberConvert(count)));
    }


    @Override
    public void setNewSystemInfo(boolean isShow) {
//        setToolBarRightImage(isShow ? ico_me_message_remind : ico_me_message_normal);
    }

    @Override
    public void updateCertification(UserCertificationInfo data) {
        if (mCertificationWindow != null) {
            mCertificationWindow.dismiss();
        }
    }

    @Override
    public void onHistoryRecordResponseSuccess(List<VideoListBean> listBeans) {
        if (listBeans != null && listBeans.size() == 0) {
            rvVideoHistoryRecord.setVisibility(View.GONE);
        } else {
            rvVideoHistoryRecord.setVisibility(View.VISIBLE);
            CommonAdapter commonAdapter = new CommonAdapter<VideoListBean>(getContext(), R.layout.item_video_record_horizontal_layout, listBeans) {
                @Override
                protected void convert(ViewHolder holder, VideoListBean videoListBean, int position) {
                    holder.getTextView(R.id.tv_video_name).setText(videoListBean.getVideo().getName() + "");
                    String url = MyUtils.getImagePath(videoListBean.getVideo().getCover(), ViewUtils.dip2px(getContext(), 90));
                    GlideManager.glide(mContext, holder.getImageViwe(R.id.iv_video_bg), url);
                    if (videoListBean != null && videoListBean.getVideo() != null && videoListBean.getVideo().getDuration() != null) {
                        String timeFormat = MyUtils.timeFormat(videoListBean.getVideo().getDuration().intValue() * 1000);
                        holder.getTextView(R.id.tv_video_duration).setText(timeFormat);
                        holder.getTextView(R.id.tv_video_duration).setVisibility(View.VISIBLE);
                    } else {
                        holder.getTextView(R.id.tv_video_duration).setText("00:00");
                    }
                }
            };
            commonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    VideoDetailActivity.starVideoDetailActivity(getContext(), listBeans.get(position).getVideo());
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
            rvVideoHistoryRecord.setAdapter(commonAdapter);
        }
    }

    private void initCertificationTypePop() {
        if (mCertificationWindow == null) {
            mCertificationWindow = CertificationTypePopupWindow.Builder()
                    .with(mActivity)
                    .alpha(CustomPopupWindow.POPUPWINDOW_ALPHA)
                    .setListener(this)
                    .build();
        }
        mCertificationWindow.show();
    }

    @Override
    public void onTypeSelected(int position) {
        mCertificationWindow.dismiss();
        Intent intent = new Intent(mActivity, CertificationInputActivity.class);
        Bundle bundle = new Bundle();
        if (position == 0) {
            // 跳转个人认证
            bundle.putInt(BUNDLE_TYPE, 0);
        } else {
            // 跳转企业认证
            bundle.putInt(BUNDLE_TYPE, 1);
        }
        intent.putExtra(BUNDLE_CERTIFICATION_TYPE, bundle);
        startActivity(intent);
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


    @OnClick({R.id.bv_friends_new_count, R.id.bt_watch_history, R.id.bt_mine_invitation, R.id.bt_mine_download, R.id.bt_mine_collection, R.id.bt_feed_back,
            R.id.bt_about_us, R.id.iv_head_icon, R.id.tv_user_name, R.id.ll_generalize_parent})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bv_friends_new_count:
                break;
            case R.id.bt_watch_history:
                VideoRecordActivity.starVideoRecordActivity(getContext());
                break;
            case R.id.bt_mine_invitation:
                MyPostListActivity.starMyPostListActivityActivity(getContext());
                break;
            case R.id.bt_mine_download:
                ToastUtils.showToast("敬请期待");
                break;
            case R.id.bt_mine_collection:
                CollectionActivity.starCollectionActivity(getContext());
                break;
            case R.id.bt_feed_back:
                getContext().startActivity(new Intent(mActivity, FeedBackActivity.class));
                break;
            case R.id.bt_about_us:
                // TODO: 2019/7/2 先屏蔽掉
//                String aboutUsUrl = mPresenter.getSystemConfigBean().getSite().getAbout_url();
//                String defaultAboutUsUrl = ApiConfig.APP_DOMAIN + URL_ABOUT_US;
//                CustomWEBActivity.startToWEBActivity(getContext(), TextUtils.isEmpty(aboutUsUrl) ? defaultAboutUsUrl : aboutUsUrl, getString(R.string.about_us));
                break;

            case R.id.iv_head_icon:
            case R.id.tv_user_name:
                startActivity(new Intent(mActivity, UserInfoActivity.class));
                break;
            case R.id.ll_generalize_parent:
                GeneralizeActivity.startGeneralizeActivity(getContext());
                break;
        }
    }
}
