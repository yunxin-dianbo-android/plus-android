package com.zhiyicx.thinksnsplus.modules.settings

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import butterknife.BindView
import com.hyphenate.chat.EMClient
import com.hyphenate.easeui.EaseConstant
import com.jakewharton.rxbinding.view.RxView
import com.jakewharton.rxbinding.widget.RxRadioGroup
import com.zhiyicx.appupdate.AppUpdateManager
import com.zhiyicx.appupdate.AppUtils
import com.zhiyicx.appupdate.AppVersionBean
import com.zhiyicx.baseproject.base.TSFragment
import com.zhiyicx.baseproject.config.ApiConfig
import com.zhiyicx.baseproject.config.ApiConfig.URL_ABOUT_US
import com.zhiyicx.baseproject.widget.button.CombinationButton
import com.zhiyicx.baseproject.widget.popwindow.ActionPopupWindow
import com.zhiyicx.common.base.BaseFragment
import com.zhiyicx.common.config.ConstantConfig.JITTER_SPACING_TIME
import com.zhiyicx.common.utils.DeviceUtils
import com.zhiyicx.common.utils.SharePreferenceUtils
import com.zhiyicx.common.utils.SkinUtils
import com.zhiyicx.common.widget.popwindow.CustomPopupWindow
import com.zhiyicx.common.widget.popwindow.CustomPopupWindow.POPUPWINDOW_ALPHA
import com.zhiyicx.thinksnsplus.R
import com.zhiyicx.thinksnsplus.base.AppApplication
import com.zhiyicx.thinksnsplus.base.fordownload.IPresenterForDownload
import com.zhiyicx.thinksnsplus.config.EventBusTagConfig.EVENT_LOG_OUT
import com.zhiyicx.thinksnsplus.data.beans.UserInfoBean
import com.zhiyicx.thinksnsplus.modules.chat.ChatActivity
import com.zhiyicx.thinksnsplus.modules.feedback.FeedBackActivity
import com.zhiyicx.thinksnsplus.modules.guide.GuideActivity
import com.zhiyicx.thinksnsplus.modules.home.HomeActivity
import com.zhiyicx.thinksnsplus.modules.login.LoginActivity
import com.zhiyicx.thinksnsplus.modules.password.changepassword.ChangePasswordActivity
import com.zhiyicx.thinksnsplus.modules.settings.aboutus.CustomWEBActivity
import com.zhiyicx.thinksnsplus.modules.settings.account.AccountManagementActivity
import com.zhiyicx.thinksnsplus.modules.settings.bind.AccountBindActivity.*
import com.zhiyicx.thinksnsplus.modules.settings.bind.AccountBindFragment.DEAL_TYPE_PSD
import com.zhiyicx.thinksnsplus.modules.settings.blacklist.BlackListActivity
import com.zhiyicx.thinksnsplus.modules.settings.init_password.InitPasswordActivity
import com.zhiyicx.thinksnsplus.utils.NotificationUtil
import org.simple.eventbus.EventBus
import java.util.concurrent.TimeUnit

/**
 * @Describe
 * @author Jungle68
 * @Date 2017/1/9
 * @Contact master.jungle68@gmail.com
 */
class SettingsFragment : TSFragment<SettingsContract.Presenter>(), SettingsContract.View {

    @BindView(R.id.bt_blacklis)
    @JvmField
    var mBtBlackList: CombinationButton? = null
    @BindView(R.id.bt_login_out)
    @JvmField
    var mBtLoginOut: CombinationButton? = null
    @BindView(R.id.bt_set_vertify)
    @JvmField
    var mBtSetVertify: CombinationButton? = null
    @BindView(R.id.bt_change_password)
    @JvmField
    var mBtChangePassword: CombinationButton? = null
    @BindView(R.id.bt_clean_cache)
    @JvmField
    var mBtCleanCache: CombinationButton? = null
    @BindView(R.id.bt_about_us)
    @JvmField
    var mBtAboutUs: CombinationButton? = null
    @BindView(R.id.bt_account_manager)
    @JvmField
    var mBtAccountManager: CombinationButton? = null
    @BindView(R.id.bt_feedbak)
    @JvmField
    var mBtFeedBack: CombinationButton? = null
    @BindView(R.id.bt_check_version)
    @JvmField
    var mBtCheckVersion: CombinationButton? = null
    // 服务器切换使用
    @BindView(R.id.rb_one)
    @JvmField
    var mRbOne: RadioButton? = null
    @BindView(R.id.rb_two)
    @JvmField
    var mRbTwo: RadioButton? = null
    @BindView(R.id.rb_three)
    @JvmField
    var mRbThree: RadioButton? = null
    @BindView(R.id.rb_days_group)
    @JvmField
    var mRbDaysGroup: RadioGroup? = null
    @BindView(R.id.tv_choose_tip)
    @JvmField
    var mTvChooseTip: TextView? = null

    var mStatusBarPlaceholder: View? = null
    private var mIsDefualtCheck = true

    private var mLoginoutPopupWindow: ActionPopupWindow? = null// 退出登录选择弹框
    private var mCleanCachePopupWindow: ActionPopupWindow? = null// 清理缓存选择弹框

    override fun getBodyLayoutId(): Int {
        return R.layout.fragment_settings
    }


    override fun setCenterTitle(): String {
        return getString(R.string.setting)
    }

    override fun setToolBarBackgroud(): Int {
        return R.color.white
    }

    override fun showToolBarDivider(): Boolean {
        return false
    }

    override fun getToolBarLayoutId(): Int {
        return R.layout.toolbar_custom_contain_status_bar
    }

    override fun setUseStatusView(): Boolean {
        return false
    }

    override fun setUseSatusbar(): Boolean {
        return true
    }


    override fun initView(rootView: View) {
        initListener()
        if (com.zhiyicx.common.BuildConfig.USE_DOMAIN_SWITCH) {
//            mRbDaysGroup!!.visibility = View.VISIBLE
            mRbDaysGroup!!.visibility = View.GONE
            mRbOne!!.visibility = View.VISIBLE
            mRbOne!!.text = getString(R.string.domain_formal)
            mRbTwo!!.visibility = View.VISIBLE
            mRbTwo!!.text = getString(R.string.domain_test)
            mRbThree!!.visibility = View.VISIBLE
            mRbThree!!.text = getString(R.string.domain_dev)
            when (ApiConfig.APP_DOMAIN) {
                ApiConfig.APP_DOMAIN_FORMAL -> mRbOne!!.isChecked = true

                ApiConfig.APP_DOMAIN_TEST -> mRbTwo!!.isChecked = true

                ApiConfig.APP_DOMAIN_DEV -> mRbThree!!.isChecked = true
            }
            mTvChooseTip!!.setText(R.string.domain_swith)

            RxRadioGroup.checkedChanges(mRbDaysGroup!!)
                    .subscribe { checkedId ->
                        if (mIsDefualtCheck) {
                            mIsDefualtCheck = false
                        } else {
                            var domain: String? = null
                            when (checkedId) {
                                R.id.rb_one -> domain = ApiConfig.APP_DOMAIN_FORMAL
                                R.id.rb_two -> domain = ApiConfig.APP_DOMAIN_TEST
                                R.id.rb_three -> domain = ApiConfig.APP_DOMAIN_DEV
                            }
                            if (!TextUtils.isEmpty(domain) && mPresenter != null && context != null) {
                                SharePreferenceUtils.saveString(context, SharePreferenceUtils.SP_DOMAIN, domain)
                                mPresenter.loginOut()
                                val mStartActivity = Intent(context, GuideActivity::class.java)
                                val mPendingIntentId = 123456
                                val mPendingIntent = PendingIntent.getActivity(context, mPendingIntentId, mStartActivity,
                                        PendingIntent
                                                .FLAG_CANCEL_CURRENT)
                                val mgr = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                                mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent)
                                System.exit(0)
                            }

                        }
                    }

        } else {
            mRbDaysGroup!!.visibility = View.GONE
            mTvChooseTip!!.visibility = View.GONE
        }
        mStatusBarPlaceholder = rootView.findViewById(R.id.v_status_bar_placeholder)
        initStatusBar()
    }

    fun initStatusBar() {
        // toolBar设置状态栏高度的marginTop
        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, com.zhiyicx.common.utils.DeviceUtils
                .getStatuBarHeight(context))
        mStatusBarPlaceholder!!.setLayoutParams(layoutParams)
    }

    override fun initData() {
        mPresenter.getDirCacheSize()// 获取缓存大小
        mBtCheckVersion!!.rightText = "V" + DeviceUtils.getVersionName(context)
    }

    override fun setCacheDirSize(size: String) {
        mBtCleanCache!!.rightText = size
    }

    override fun getAppNewVersionSuccess(appVersionBean: List<AppVersionBean>?) {
        if (appVersionBean != null
                && !appVersionBean.isEmpty()
                && AppUtils.getVersionCode(context) < appVersionBean[0].version_code) {
            SharePreferenceUtils.saveObject<Any>(context, AppUpdateManager.SHAREPREFERENCE_TAG_ABORD_VERION, null)
            AppUpdateManager.getInstance(context, ApiConfig.APP_DOMAIN + ApiConfig.APP_PATH_GET_APP_VERSION + "?version_code=" + DeviceUtils.getVersionCode(context) + "&type=android")
                    .startVersionCheck()
        } else {
            showSnackSuccessMessage(getString(R.string.no_new_version))
        }
    }

    private fun initListener() {
        // 认证
        RxView.clicks(mBtSetVertify!!)
                .throttleFirst(JITTER_SPACING_TIME.toLong(), TimeUnit.SECONDS)   //两秒钟之内只取一个点击事件，防抖操作
                .compose(this.bindToLifecycle())
                .subscribe { showSnackSuccessMessage("vertify") }
        // 黑名单
        RxView.clicks(mBtBlackList!!)
                .throttleFirst(JITTER_SPACING_TIME.toLong(), TimeUnit.SECONDS)
                .compose(this.bindToLifecycle())
                .subscribe {
                    startActivity(Intent(activity, BlackListActivity::class.java))
                }
        // 意见反馈
        RxView.clicks(mBtFeedBack!!)
                .throttleFirst(JITTER_SPACING_TIME.toLong(), TimeUnit.SECONDS)
                .compose(this.bindToLifecycle())
                .subscribe {
                    // 意见反馈跳转 ts+ 小助手 2018-3-12 11:47:12 by tym
                    val tsHlepers = mPresenter.imHelper
                    var isNeedFeedBack = false
                    try {
                        isNeedFeedBack = tsHlepers == null || tsHlepers.isEmpty() || AppApplication.getMyUserIdWithdefault() == tsHlepers[0].uid.toLong() || !EMClient.getInstance().isConnected
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    if (isNeedFeedBack) {
                        startActivity(Intent(mActivity, FeedBackActivity::class.java))
                    } else {
                        ChatActivity.startChatActivity(mActivity, tsHlepers[0].uid.toString(),
                                EaseConstant.CHATTYPE_SINGLE)
                    }
                }
        // 账户管理页面
        RxView.clicks(mBtAccountManager!!)
                .throttleFirst(JITTER_SPACING_TIME.toLong(), TimeUnit.SECONDS)
                .compose(this.bindToLifecycle())
                .subscribe {
                    // 跳转账户管理页面
                    startActivity(Intent(activity, AccountManagementActivity::class.java))
                }
        // 修改密码
        RxView.clicks(mBtChangePassword!!)
                .throttleFirst(JITTER_SPACING_TIME.toLong(), TimeUnit.SECONDS)   //两秒钟之内只取一个点击事件，防抖操作
                .compose(this.bindToLifecycle())
                .subscribe {
                    var userInfoBean: UserInfoBean? = null
                    var phone: String? = null
                    var hasPsd = false
                    if (AppApplication.getmCurrentLoginAuth() != null && AppApplication.getmCurrentLoginAuth().user != null) {
                        hasPsd = AppApplication.getmCurrentLoginAuth().user.initial_password
                    }
                    if (!hasPsd) {
                        val intent = Intent(activity, InitPasswordActivity::class.java)
                        val bundle = Bundle()
                        bundle.putInt(BUNDLE_BIND_TYPE, DEAL_TYPE_PSD)
                        bundle.putBoolean(BUNDLE_BIND_STATE, !TextUtils.isEmpty(phone))
                        bundle.putParcelable(BUNDLE_BIND_DATA, userInfoBean)
                        intent.putExtras(bundle)
                        activity?.startActivity(intent)
                    } else {
                        startActivity(Intent(activity, ChangePasswordActivity::class.java))
                    }
                }
        // 清理缓存
        RxView.clicks(mBtCleanCache!!)
                .throttleFirst(JITTER_SPACING_TIME.toLong(), TimeUnit.SECONDS)   //两秒钟之内只取一个点击事件，防抖操作
                .compose(this.bindToLifecycle())
                .subscribe {
                    initCleanCachePopupWindow()
                    mCleanCachePopupWindow!!.show()
                }
        // 关于我们
        RxView.clicks(mBtAboutUs!!)
                .throttleFirst(JITTER_SPACING_TIME.toLong(), TimeUnit.SECONDS)   //两秒钟之内只取一个点击事件，防抖操作
                .compose(this.bindToLifecycle())
                .subscribe {
                    val aboutUs = mPresenter.systemConfigBean.site.about_url
                    CustomWEBActivity.startToWEBActivity(context, if (TextUtils.isEmpty(aboutUs)) {
                        ApiConfig.APP_DOMAIN + URL_ABOUT_US
                    } else {
                        aboutUs
                    }, getString(R.string.about_us))
                }
        // 退出登录
        RxView.clicks(mBtLoginOut!!)
                .throttleFirst(JITTER_SPACING_TIME.toLong(), TimeUnit.SECONDS)   //两秒钟之内只取一个点击事件，防抖操作
                .compose(this.bindToLifecycle())
                .subscribe {
                    initLoginOutPopupWindow()
                    mLoginoutPopupWindow!!.show()
                }
        // 检查版本是否有更新
        RxView.clicks(mBtCheckVersion!!)
                .throttleFirst(JITTER_SPACING_TIME.toLong(), TimeUnit.SECONDS)
                .compose(this.bindToLifecycle())
                .subscribe {
                    mPresenter.checkUpdate()
                }
    }


    /**
     * 初始化清理缓存选择弹框
     */
    private fun initCleanCachePopupWindow() {

        mCleanCachePopupWindow = ActionPopupWindow.builder()
                .item1Str(String.format(getString(R.string.is_sure_clean_cache), mBtCleanCache!!.rightText))
                .item2Str(getString(R.string.determine))
                .bottomStr(getString(R.string.cancel))
                .isOutsideTouch(true)
                .isFocus(true)
                .backgroundAlpha(CustomPopupWindow.POPUPWINDOW_ALPHA)
                .with(activity)
                .item2ClickListener {
                    mPresenter.cleanCache()
                    mCleanCachePopupWindow!!.hide()
                }
                .bottomClickListener { mCleanCachePopupWindow!!.hide() }
                .build()

    }

    /**
     * 初始化登录选择弹框
     */
    private fun initLoginOutPopupWindow() {
        if (mLoginoutPopupWindow != null) {
            return
        }
        mLoginoutPopupWindow = ActionPopupWindow.builder()
                .item1Str(getString(R.string.is_sure_login_out))
                .item2Str(getString(R.string.login_out_sure))
                .item2Color(SkinUtils.getColor(R.color.important_for_note))
                .bottomStr(getString(R.string.cancel))
                .isOutsideTouch(true)
                .isFocus(true)
                .backgroundAlpha(POPUPWINDOW_ALPHA)
                .with(activity)
                .item2ClickListener {
                    if (mPresenter.loginOut()) {
                        SharePreferenceUtils.saveBoolean(mActivity, IPresenterForDownload.ALLOW_GPRS, false)
                        NotificationUtil.cancelAllNotification(context)
//                      startActivity(Intent(activity, LoginActivity::class.java))
                        var intent = Intent(activity, HomeActivity::class.java)
//                        var bundle = Bundle()
//                        bundle.putBoolean("isFromLogOut",true)
//                        intent.putExtras(bundle)
                        EventBus.getDefault().post(true,EVENT_LOG_OUT)
                        startActivity(intent)
                    }
                    mLoginoutPopupWindow!!.hide()
                }
                .bottomClickListener { mLoginoutPopupWindow!!.hide() }.build()


    }

    companion object {

        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }


}
