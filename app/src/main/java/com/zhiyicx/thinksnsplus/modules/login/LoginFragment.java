package com.zhiyicx.thinksnsplus.modules.login;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhiyicx.baseproject.base.TSFragment;
import com.zhiyicx.baseproject.config.ApiConfig;
import com.zhiyicx.baseproject.config.SystemConfig;
import com.zhiyicx.baseproject.impl.share.UmengSharePolicyImpl;
import com.zhiyicx.baseproject.widget.button.LoadingButton;
import com.zhiyicx.common.utils.ActivityHandler;
import com.zhiyicx.common.utils.ConvertUtils;
import com.zhiyicx.common.utils.UIUtils;
import com.zhiyicx.imsdk.utils.common.DeviceUtils;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.data.beans.AccountBean;
import com.zhiyicx.thinksnsplus.data.beans.ThridInfoBean;
import com.zhiyicx.thinksnsplus.modules.home.HomeActivity;
import com.zhiyicx.thinksnsplus.modules.password.findpassword.FindPasswordActivity;
import com.zhiyicx.thinksnsplus.modules.register.RegisterActivity;
import com.zhiyicx.thinksnsplus.modules.third_platform.choose_bind.ChooseBindActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.zhiyicx.common.config.ConstantConfig.JITTER_SPACING_TIME;
import static com.zhiyicx.common.config.ConstantConfig.MOBILE_PHONE_NUMBER_LENGHT;
import static com.zhiyicx.thinksnsplus.modules.login.LoginActivity.BUNDLE_TOURIST_LOGIN;
import static com.zhiyicx.thinksnsplus.modules.third_platform.choose_bind.ChooseBindActivity.BUNDLE_THIRD_INFO;

/**
 * @author LiuChao
 * @describe
 * @date 2016/12/23
 * @contact email:450127106@qq.com
 */

public class LoginFragment extends TSFragment<LoginContract.Presenter> implements LoginContract.View, AccountAdapter.OnItemSelectListener {

    @BindView(R.id.et_login_phone)
    EditText mEtLoginPhone;
    @BindView(R.id.et_login_password)
    EditText mEtLoginPassword;
    @BindView(R.id.bt_login_login)
    LoadingButton mBtLoginLogin;
    @BindView(R.id.tv_error_tip)
    TextView mTvErrorTip;
    @BindView(R.id.tv_look_around)
    TextView mTvLookAround;
    @BindView(R.id.tv_account)
    TextView mTvAccount;
    @BindView(R.id.tv_forget_password)
    TextView mTvForgetPassword;
    @BindView(R.id.iv_clear)
    ImageView mIvClear;
    @BindView(R.id.et_complete_input)
    AppCompatAutoCompleteTextView mEtCompleteInput;
    @BindView(R.id.tv_login_by_qq)
    TextView mTvLoginByQq;
    @BindView(R.id.tv_login_by_weibo)
    TextView mTvLoginByWeibo;
    @BindView(R.id.tv_login_by_wechat)
    TextView mTvLoginByWechat;
    @BindView(R.id.tv_verify_login)
    TextView mTvVerifyLogin;
    @BindView(R.id.fl_placeholder)
    View mFlPlaceholder;
    @BindView(R.id.rl_login_by_vertify)
    RelativeLayout mRlLoginByVertify;
    @BindView(R.id.et_login_vertify_code)
    EditText mEtLoginVertifyCode;
    @BindView(R.id.bt_login_send_vertify_code)
    TextView mBtLoginSendVertifyCode;
    @BindView(R.id.iv_vertify_loading)
    ImageView mIvVertifyLoading;
    @BindView(R.id.rl_touristor_container)
    RelativeLayout mRlTouristorContainer;
    @BindView(R.id.ll_psd_container)
    LinearLayout mLlPsdContainer;

    private boolean mIsPhoneEdited;
    private boolean mIsPasswordEdited;
    private boolean mIsVertifyCodeEdited;

    private boolean mIsToourist;
    /**
     * 历史的账号
     */
    private List<AccountBean> mAccountList;
    /**
     * 当前登录的账号
     */
    private AccountBean mAccountBean;

    private AccountAdapter mAccountAdapter;

    private AnimationDrawable mVertifyAnimationDrawable;

    UmengSharePolicyImpl mUmengSharePolicy;

    AnimationDrawable mLoginAnimationDrawable;

    private boolean mIsVertifyCodeEnalbe = true;

    View mStatusBarPlaceholder;

    public static LoginFragment newInstance(boolean isTourist) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putBoolean(BUNDLE_TOURIST_LOGIN, isTourist);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            mIsToourist = getArguments().getBoolean(BUNDLE_TOURIST_LOGIN);
        }
        mSystemConfigBean = mPresenter.getSystemConfigBean();
        super.onCreate(savedInstanceState);
    }

    /**
     * 根据toolbar的背景设置它的文字颜色
     */
    protected void setToolBarTextColor() {
        // 如果toolbar背景是白色的，就将文字颜色设置成黑色
        if (showToolbar()) {
            mToolbarCenter.setTextColor(ContextCompat.getColor(getContext(), com.zhiyicx.baseproject.R.color.white));
            mToolbarRight.setTextColor(ContextCompat.getColorStateList(getContext(), com.zhiyicx.baseproject.R.color.white));
            mToolbarRightLeft.setTextColor(ContextCompat.getColorStateList(getContext(), com.zhiyicx.baseproject.R.color.white));
            mToolbarLeft.setTextColor(ContextCompat.getColor(getContext(), getLeftTextColor()));
        }
    }

    @Override
    protected void initView(View rootView) {
        boolean openRegister = mSystemConfigBean.getRegisterSettings() == null
                || mSystemConfigBean.getRegisterSettings() != null && !SystemConfig
                .REGITER_MODE_THIRDPART.equals(mSystemConfigBean.getRegisterSettings().getType());
        mToolbarRight.setVisibility(openRegister ? View.VISIBLE : View.GONE);
        mEtCompleteInput.setDropDownWidth(UIUtils.getWindowWidth(getContext()));
        mEtCompleteInput.setDropDownBackgroundResource(R.color.transparent);
        mEtCompleteInput.setElevation(0);
        initListener();
        // 游客判断
        mTvLookAround.setVisibility((!mIsToourist && mPresenter.isTourist()) ? View.VISIBLE : View.GONE);
        if (mIsToourist || !mPresenter.isTourist()) {
            setLeftTextColor(R.color.white);
        }
        //是否需要日志
        mRxPermissions.setLogging(true);
        mRxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(aBoolean -> {
                });
        mUmengSharePolicy = new UmengSharePolicyImpl(getContext());
        mVertifyAnimationDrawable = (AnimationDrawable) mIvVertifyLoading.getDrawable();
        setOneKeyLoginShow(true);
    }


    private void initListener() {
        // 手机号码输入框观察
        RxTextView.textChanges(mEtLoginPhone)
                .compose(this.bindToLifecycle())
                .subscribe(charSequence -> {
                    mIsPhoneEdited = !TextUtils.isEmpty(charSequence.toString().trim());
                    setConfirmEnable();
                });
        RxTextView.textChanges(mEtCompleteInput)
                .compose(this.bindToLifecycle())
                .subscribe(charSequence -> {
                    if (mIsVertifyCodeEnalbe) {
                        mBtLoginSendVertifyCode.setEnabled(charSequence.length() ==
                                MOBILE_PHONE_NUMBER_LENGHT);
                    }
                    mIsPhoneEdited = !TextUtils.isEmpty(charSequence.toString().trim());
                    setConfirmEnable();
                    mIvClear.setVisibility(TextUtils.isEmpty(charSequence.toString()) ? View.GONE : View.VISIBLE);
                });
        // 密码输入框观察
        RxTextView.textChanges(mEtLoginPassword)
                .compose(this.bindToLifecycle())
                .subscribe(charSequence -> {
                    mIsPasswordEdited = !TextUtils.isEmpty(charSequence.toString().trim());
                    setConfirmEnable();
                });
        // 验证码输入框观察
        RxTextView.textChanges(mEtLoginVertifyCode)
                .compose(this.bindToLifecycle())
                .subscribe(charSequence -> {
                    mIsVertifyCodeEdited = !TextUtils.isEmpty(charSequence.toString().trim());
                    setConfirmEnable();
                });
        // 点击发送验证码
        RxView.clicks(mBtLoginSendVertifyCode)
                .throttleFirst(JITTER_SPACING_TIME, TimeUnit.SECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(aVoid -> {
                    mPresenter.getVertifyCode(mEtCompleteInput.getText().toString().trim());
                    mEtLoginVertifyCode.requestFocus();
                });
        // 点击登录按钮
        RxView.clicks(mBtLoginLogin)
                .throttleFirst(JITTER_SPACING_TIME, TimeUnit.SECONDS)
                .compose(this.bindToLifecycle())
                .compose(mRxPermissions.ensure(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest
                        .permission.READ_PHONE_STATE))
                .subscribe(aBoolean -> {
                    // 获取到了权限
                    if (aBoolean && mEtCompleteInput != null) {
                        mAccountBean.setId(System.currentTimeMillis());
                        mAccountBean.setAccountName(mEtCompleteInput.getText().toString().trim());
                        boolean isOneKeyLogin = mRlLoginByVertify.getVisibility() == View.VISIBLE;
                        mPresenter.login(mEtCompleteInput.getText().toString().trim(),
                                mEtLoginPassword.getText().toString().trim(),
                                isOneKeyLogin ? mEtLoginVertifyCode.getText().toString() : null);
                    } else {// 拒绝权限，但是可以再次请求
                        showErrorTips(getString(R.string.permisson_refused));
                    }
                });
        RxView.clicks(mIvClear)
                .throttleFirst(JITTER_SPACING_TIME, TimeUnit.SECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(aVoid -> mEtCompleteInput.setText(""));

        RxView.clicks(mTvVerifyLogin)
                .throttleFirst(JITTER_SPACING_TIME, TimeUnit.SECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(aVoid -> setOneKeyLoginShow(mRlLoginByVertify.getVisibility() != View.VISIBLE));


        RxView.clicks(mTvLoginByQq)
                .throttleFirst(JITTER_SPACING_TIME, TimeUnit.SECONDS)
                .compose(this.bindToLifecycle())
                .compose(mRxPermissions.ensure(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest
                        .permission.READ_PHONE_STATE))
                .subscribe(aBoolean -> {
                    // 获取到了权限
                    if (aBoolean && mEtCompleteInput != null) {
                        // QQ 和微信 该版本不提供网页支持，故提示安装应用
                        if (UMShareAPI.get(getContext()).isInstall(getActivity(), SHARE_MEDIA.QQ)) {
                            showSnackLoadingMessage(getString(R.string.loading_state));
                            thridLogin(SHARE_MEDIA.QQ);
                        } else {
                            showSnackErrorMessage(getString(R.string.please_install_app));
                        }
                    } else {// 拒绝权限，但是可以再次请求
                        showErrorTips(getString(R.string.permisson_refused));
                    }

                });
        RxView.clicks(mTvLoginByWeibo)
                .throttleFirst(JITTER_SPACING_TIME, TimeUnit.SECONDS)
                .compose(this.bindToLifecycle())
                .compose(mRxPermissions.ensure(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest
                        .permission.READ_PHONE_STATE))
                .subscribe(aBoolean -> {
                    // 获取到了权限
                    if (aBoolean && mEtCompleteInput != null) {
//                    if (UMShareAPI.get(getContext()).isInstall(getActivity(), SHARE_MEDIA.SINA)) {

                        showSnackLoadingMessage(getString(R.string.loading_state));
                        thridLogin(SHARE_MEDIA.SINA);
//                    } else {
//                        showSnackErrorMessage(getString(R.string.please_install_app));
//                    }
                    } else {// 拒绝权限，但是可以再次请求
                        showErrorTips(getString(R.string.permisson_refused));
                    }
                });
        RxView.clicks(mTvLoginByWechat)
                .throttleFirst(JITTER_SPACING_TIME, TimeUnit.SECONDS)
                .compose(this.bindToLifecycle())
                .compose(mRxPermissions.ensure(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest
                        .permission.READ_PHONE_STATE))
                .subscribe(aBoolean -> {
                    // 获取到了权限
                    if (aBoolean && mEtCompleteInput != null) {
                        if (UMShareAPI.get(getContext()).isInstall(getActivity(), SHARE_MEDIA.WEIXIN)) {

                            showSnackLoadingMessage(getString(R.string.loading_state));
                            thridLogin(SHARE_MEDIA.WEIXIN);
                        } else {
                            showSnackErrorMessage(getString(R.string.please_install_app));
                        }
                    } else {// 拒绝权限，但是可以再次请求
                        showErrorTips(getString(R.string.permisson_refused));
                    }
                });
    }

    /**
     * 三方登录
     *
     * @param type 三方标识
     */
    public void thridLogin(SHARE_MEDIA type) {
        UMShareAPI mShareAPI = UMShareAPI.get(getActivity());
        mShareAPI.getPlatformInfo(getActivity(), type, authListener);
    }

    @Override
    protected void initData() {
        mAccountList = new ArrayList<>();
        mAccountBean = new AccountBean();
        initAccount();
    }

    @Override
    protected int getToolBarLayoutId() {
        return R.layout.toolbar_custom_contain_status_bar;
    }

    @Override
    protected boolean usePermisson() {
        return true;
    }

    @Override
    protected int getBodyLayoutId() {
        return R.layout.fragment_login;
    }

//    @Override
//    protected int setToolBarBackgroud() {
//        return R.color.white;
//    }


    @Override
    protected boolean setUseSatusbar() {
        return true;
    }

    @Override
    protected boolean setUseStatusView() {
        return false;
    }

    @Override
    protected String setCenterTitle() {
        return getString(R.string.bt_login);
    }

    @Override
    protected String setRightTitle() {
        return getString(R.string.regist);
    }


    @Override
    protected boolean showToolBarDivider() {
        return false;
    }

    @Override
    protected void setRightClick() {
        super.setRightClick();
        Intent intent = new Intent(getActivity(), RegisterActivity.class);
        intent.putExtra(BUNDLE_TOURIST_LOGIN, mIsToourist);
        startActivity(intent);
    }

    @Override
    protected int setLeftImg() {
        return R.mipmap.ic_back;
    }

    @Override
    protected String setLeftTitle() {
//        return mIsToourist ? getString(R.string.cancel) : "";
        return "";
    }


    @Override
    public void setLogining() {
        mFlPlaceholder.setVisibility(View.VISIBLE);
        mToolbarRight.setEnabled(false);
        mBtLoginLogin.handleAnimation(true);
        mBtLoginLogin.setEnabled(false);
    }


    @Override
    protected View getContentView() {
        View contentView = super.getContentView();
        mStatusBarPlaceholder = contentView.findViewById(R.id.v_status_bar_placeholder);
        initStatusBar();
        return contentView;
    }


    private void initStatusBar() {
        // toolBar设置状态栏高度的marginTop
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, com.zhiyicx.common.utils.DeviceUtils
                .getStatuBarHeight(getContext()));
        mStatusBarPlaceholder.setLayoutParams(layoutParams);
    }

    @Override
    public void setLoginState(boolean loginState) {

        if (loginState) {
            mLoginAnimationDrawable = mBtLoginLogin.getAnimationDrawable();
            DeviceUtils.hideSoftKeyboard(getContext(), mEtLoginPassword);
            if (mIsToourist) {
                getActivity().setResult(RESULT_OK);
                getActivity().finish();
            } else {
                goHome();
            }
        } else {
            // 失败立马停止，成功的话 ondestroy 中处理
            mBtLoginLogin.handleAnimation(false);
            mToolbarRight.setEnabled(true);
            mFlPlaceholder.setVisibility(View.GONE);
            mBtLoginLogin.setEnabled(true);
        }
    }

    @Override
    public void showErrorTips(String message) {
        if (TextUtils.isEmpty(message)) {
            mTvErrorTip.setVisibility(View.INVISIBLE);
        } else {
            mTvErrorTip.setVisibility(View.VISIBLE);
            mTvErrorTip.setText(message);
        }
    }

    @Override
    public void showMessage(String message) {
        showErrorTips(message);
    }

    @Override
    public AccountBean getAccountBean() {
        return mAccountBean;
    }

    @Override
    public void setVertifyCodeBtText(String text) {
        mBtLoginSendVertifyCode.setText(text);
    }

    @Override
    public void setVertifyCodeBtEnabled(boolean isEnable) {
        mIsVertifyCodeEnalbe = isEnable;
        mBtLoginSendVertifyCode.setEnabled(isEnable);
    }

    @Override
    public void setVertifyCodeLoadin(boolean isEnable) {
        if (isEnable) {
            mIvVertifyLoading.setVisibility(View.VISIBLE);
            mBtLoginSendVertifyCode.setVisibility(View.INVISIBLE);
            mVertifyAnimationDrawable.start();
        } else {
            mVertifyAnimationDrawable.stop();
            mIvVertifyLoading.setVisibility(View.INVISIBLE);
            mBtLoginSendVertifyCode.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置登录按钮是否可点击
     */
    private void setConfirmEnable() {
        showErrorTips(null);
        if (mIsPhoneEdited && (mIsPasswordEdited || mIsVertifyCodeEdited)) {
            mBtLoginLogin.setEnabled(true);
        } else {
            mBtLoginLogin.setEnabled(false);
        }
    }

    @OnClick({R.id.tv_look_around, R.id.tv_forget_password})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_look_around:
                goHome();
                break;
            case R.id.tv_forget_password:
                startActivity(new Intent(getActivity(), FindPasswordActivity.class));
                break;
            default:
        }
    }

    private void goHome() {
        // 清除 homeAcitivity 重新加载
        ActivityHandler.getInstance().finishAllActivityEcepteCurrent();
        startActivity(new Intent(getActivity(), HomeActivity.class));
        getActivity().finish();
    }

    private void initAccount() {
        List<String> list = new ArrayList<>();

        mAccountList.addAll(mPresenter.getAllAccountList());
        if (mAccountAdapter == null) {
            mAccountAdapter = new AccountAdapter(getContext(), mAccountList, this);
        } else {
            mAccountAdapter.notifyDataSetChanged();
        }

        for (AccountBean accountBean : mAccountList) {
            list.add(accountBean.getAccountName());
        }
        setAccountListPopHeight(mAccountList.size());
//        mArrayAdapter = new ArrayAdapter(getContext(), R.layout.item_account, R.id.tv_account_name, list);
        mEtCompleteInput.setAdapter(mAccountAdapter);
    }

    @Override
    public void onItemSelect(AccountBean accountBean) {
        // 设置填充数据，收起下拉框
        mEtCompleteInput.setText(accountBean.getAccountName());
        mEtCompleteInput.setSelection(accountBean.getAccountName().length());
        mEtCompleteInput.dismissDropDown();
    }

    @Override
    public void onDataChange(int size) {
        setAccountListPopHeight(size);
    }

    private void setAccountListPopHeight(int size) {
        if (size > 3) {
            mEtCompleteInput.setDropDownHeight((int) DeviceUtils.dpToPixel(getContext(), 140));
        } else {
            mEtCompleteInput.setDropDownHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }


    private String mThridName;


    private String mAccessToken;

    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            dismissSnackBar();
            String providerQq = ApiConfig.PROVIDER_QQ;
            switch (platform) {
                case QQ:
                    providerQq = ApiConfig.PROVIDER_QQ;
                    break;
                case SINA:
                    providerQq = ApiConfig.PROVIDER_WEIBO;
                    break;

                case WEIXIN:
                    providerQq = ApiConfig.PROVIDER_WECHAT;
                    break;
                default:

            }
            mThridName = data.get("screen_name");
            mAccessToken = data.get("accessToken");
            mPresenter.checkBindOrLogin(providerQq, mAccessToken);
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            showErrorTips(getString(R.string.login_fail));
            showSnackErrorMessage(getString(R.string.login_fail));
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            showSnackWarningMessage(getString(R.string.login_cancel));
        }
    };

    @Override
    protected void setRightText(String rightText) {
        super.setRightText(rightText);
    }

    /**
     * @param provider
     * @param access_token
     */
    @Override
    public void registerByThrid(String provider, String access_token) {
        Intent intent = new Intent(getActivity(), ChooseBindActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_THIRD_INFO, new ThridInfoBean(provider, access_token, mThridName));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLoginAnimationDrawable != null && mLoginAnimationDrawable.isRunning()) {
            mLoginAnimationDrawable.stop();
        }
    }

    public boolean backPressed() {
        boolean isShow = mRlLoginByVertify.getVisibility() != View.VISIBLE;
        if (!isShow) {
            setOneKeyLoginShow(false);
        } else {
            com.zhiyicx.common.utils.DeviceUtils.hideSoftKeyboard(mActivity, mRootView);
            getActivity().finish();
        }
        return isShow;
    }

    @Override
    protected void setLeftClick() {
        backPressed();
    }

    private void setOneKeyLoginShow(boolean isShow) {
//        setToolBarLeftImage(isShow ? R.mipmap.topbar_back : 0);
//        setToolBarLeftImage(isShow ? R.mipmap.topbar_back : 0);
        setToolBarLeftImage(R.mipmap.ic_back);
        setCenterText(getString(isShow ? R.string.onekey_login : R.string.bt_login));
        mTvAccount.setText(isShow ? R.string.phone_number : R.string.login_account);
        mTvAccount.setPadding(0, 0, ConvertUtils.dp2px(mActivity, isShow ? 10 : 25), 0);
        mEtCompleteInput.setHint(isShow ? R.string.phone_number_input_hint : R.string.login_account_hint);
        mTvVerifyLogin.setText(isShow ? R.string.account_login : R.string.phone_verify_login);
        mRlTouristorContainer.setVisibility(isShow ? View.INVISIBLE : View.VISIBLE);
        mRlLoginByVertify.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mLlPsdContainer.setVisibility(isShow ? View.GONE : View.VISIBLE);
        mEtLoginVertifyCode.setText("");
        if (mIsVertifyCodeEnalbe) {
            mBtLoginSendVertifyCode.setEnabled(mEtCompleteInput.getText().length() ==
                    MOBILE_PHONE_NUMBER_LENGHT);
        }
        setVertifyCodeBtText(getString(R.string.send_vertify_code));
        mPresenter.closeTimer();
    }

    @Override
    protected void setToolBarLeftImage(int resImg) {
        super.setToolBarLeftImage(resImg);
        mToolbarLeft.setVisibility(resImg > 0 ? View.VISIBLE : View.GONE);
    }
}
