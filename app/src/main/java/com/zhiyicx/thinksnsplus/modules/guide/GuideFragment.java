package com.zhiyicx.thinksnsplus.modules.guide;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.zhiyicx.baseproject.base.TSFragment;
import com.zhiyicx.common.BuildConfig;
import com.zhiyicx.common.utils.DeviceUtils;
import com.zhiyicx.common.utils.log.LogUtils;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.data.beans.RealAdvertListBean;
import com.zhiyicx.thinksnsplus.modules.settings.aboutus.CustomWEBActivity;
import com.zhiyicx.thinksnsplus.utils.BannerImageLoaderUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;

import static com.zhiyicx.common.config.ConstantConfig.JITTER_SPACING_TIME;

/**
 * @author Jliuer
 * @Date 18/06/19 17:30
 * @Email Jliuer@aliyun.com
 * @Description
 */
public class GuideFragment extends TSFragment<GuideContract.Presenter> implements
        GuideContract.View, OnBannerListener, ViewPager.OnPageChangeListener, Banner.OnBannerTimeListener {

    private static final String ICON_V2 = ".GuideActivity";
    private static final String ICON = ".modules.guide.GuideActivity";

    @BindView(R.id.guide_banner)
    Banner mGuideBanner;
    @BindView(R.id.guide_text)
    TextView mGuideText;
    int mPosition;

    /**
     * 广告是否结束
     */
    boolean isFinish;

    /**
     * 是否点击了广告
     */
    boolean isClick;

    /**
     * 是否第一次进入该页面
     */
    boolean isFirst = true;

    public static final String ADVERT = "advert";

    private List<RealAdvertListBean> mBootAdverts;


    /**
     * Activity 手动调用处理
     *
     * @param intent
     */
    public void onNewIntent(Intent intent) {
        isClick = false;
        isFirst = false;
        if (isFinish || mPosition == mGuideBanner.getItemCount() - 1) {
            mPresenter.checkLogin();
        }
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
    protected int getBodyLayoutId() {
        return R.layout.fragment_guide_v2;
    }

    @Override
    protected void initView(View rootView) {
        RxView.clicks(mGuideText).throttleFirst(JITTER_SPACING_TIME, TimeUnit.SECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(aVoid -> mPresenter.checkLogin());

        if (com.zhiyicx.common.BuildConfig.USE_ADVERT) {
            mBootAdverts = mPresenter.getBootAdvert();
            if (mBootAdverts != null && !mBootAdverts.isEmpty()) {
                List<String> urls = new ArrayList<>();
                for (RealAdvertListBean realAdvertListBean : mBootAdverts) {
                    if (realAdvertListBean == null) {
                        continue;
                    }
                    if (realAdvertListBean.getAdvertFormat() == null) {
                        continue;
                    }
                    if (realAdvertListBean.getAdvertFormat().getImage() == null) {
                        continue;
                    }
                    if (TextUtils.isEmpty(realAdvertListBean.getAdvertFormat().getImage().getBase64Image())) {
                        // 没有下载完成的广告不显示
                        continue;
                    }
                    urls.add(realAdvertListBean.getAdvertFormat().getImage().getBase64Image());
                }
                if (urls.isEmpty()) {
                    mBootAdverts = null;
                    return;
                }
                int time = mBootAdverts.get(0).getAdvertFormat().getImage().getDuration() * 1000;
                time = time > 0 ? time : 3000;
                mGuideText.setVisibility(View.VISIBLE);

                mGuideBanner.setBannerStyle(BannerConfig.NOT_INDICATOR);
                mGuideBanner.setImageLoader(new BannerImageLoaderUtil());
                mGuideBanner.isBase64Image(true);
                mGuideBanner.setImages(urls);
                mGuideBanner.isAutoPlay(true);
                mGuideBanner.isDownStopAutoPlay(false);
                mGuideBanner.setViewPagerIsScroll(false);
                mGuideBanner.setDelayTime(time);
                mGuideBanner.setTimeListener(this);
                mGuideBanner.setOnBannerListener(this);
                mGuideBanner.setOnPageChangeListener(this);
            }
        }
    }

    @Override
    protected void initData() {
        mPresenter.initConfig();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirst) {
            if (com.zhiyicx.common.BuildConfig.USE_ADVERT && (mBootAdverts != null && !mBootAdverts.isEmpty())) {
                initAdvert();
            } else {
                mPresenter.checkLogin();
            }
            isFirst = false;
        }
    }


    @Override
    public void startActivity(Class aClass) {
        repleaseAdvert();
        if (mActivity != null && !isDetached() && isAdded()) {
            startActivity(new Intent(mActivity, aClass));
            mActivity.finish();
        }
    }

    private void repleaseAdvert() {
        if (!com.zhiyicx.common.BuildConfig.USE_ADVERT || mGuideBanner == null) {
            return;
        }
        mGuideBanner.setOnPageChangeListener(null);
        mGuideBanner.setTimeListener(null);
        mGuideBanner.stopAutoPlay();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (mGuideBanner == null) {
            return;
        }
        mPosition = mGuideBanner.getCurrentItem();

        if (mPosition > 0) {
            int time = mBootAdverts.get(position - 1).getAdvertFormat().getImage().getDuration() * 1000;
            time = time > 0 ? time : position * 3000;
            mGuideBanner.setDelayTime(time);
            mGuideBanner.setTimeListener(this);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTimeTick(String time) {
        if (mGuideText == null) {
            return;
        }
        mGuideText.setText(time);
    }

    @Override
    public void onFinish() {
        isFinish = true;
        if (isClick) {
            return;
        }
        mPresenter.checkLogin();
    }

    @Override
    public void onBannerClick(int position) {

        isClick = true;
        if (isFinish) {
            return;
        }
        CustomWEBActivity.startToWEBActivity(getActivity(), mBootAdverts.get(position)
                        .getAdvertFormat().getImage().getLink(),
                mBootAdverts.get(position).getTitle(), ADVERT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.checkLogin();
    }

    @Override
    public void initAdvert() {
        mGuideBanner.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mGuideBanner != null) {
            mGuideBanner.releaseBanner();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
