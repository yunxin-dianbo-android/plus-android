package com.zhiyicx.thinksnsplus.modules.circle.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhiyicx.baseproject.base.SystemConfigBean;
import com.zhiyicx.baseproject.base.TSFragment;
import com.zhiyicx.baseproject.config.ApiConfig;
import com.zhiyicx.baseproject.impl.share.UmengSharePolicyImpl;
import com.zhiyicx.baseproject.share.OnShareCallbackListener;
import com.zhiyicx.baseproject.share.Share;
import com.zhiyicx.baseproject.share.ShareContent;
import com.zhiyicx.baseproject.share.SharePolicy;
import com.zhiyicx.common.dagger.scope.FragmentScoped;
import com.zhiyicx.common.utils.ConvertUtils;
import com.zhiyicx.common.utils.SharePreferenceUtils;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.base.AppBasePresenter;
import com.zhiyicx.thinksnsplus.base.BaseSubscribeForV2;
import com.zhiyicx.thinksnsplus.base.fordownload.AppListPresenterForDownload;
import com.zhiyicx.thinksnsplus.data.beans.CirclePostListBean;
import com.zhiyicx.thinksnsplus.data.beans.DynamicDetailBeanV2;
import com.zhiyicx.thinksnsplus.data.beans.TopSuperStarBean;
import com.zhiyicx.thinksnsplus.data.source.local.AllAdvertListBeanGreenDaoImpl;
import com.zhiyicx.thinksnsplus.data.source.local.CircleInfoGreenDaoImpl;
import com.zhiyicx.thinksnsplus.data.source.local.UserCertificationInfoGreenDaoImpl;
import com.zhiyicx.thinksnsplus.data.source.repository.BaseCircleRepository;
import com.zhiyicx.thinksnsplus.data.source.repository.MessageReviewRepository;
import com.zhiyicx.thinksnsplus.data.source.repository.UserInfoRepository;
import com.zhiyicx.thinksnsplus.utils.TSShareUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.jzvd.JZUtils;
import rx.Observable;
import rx.Subscription;

import static com.zhiyicx.thinksnsplus.data.beans.DynamicListAdvert.DEFAULT_ADVERT_FROM_TAG;

@FragmentScoped
public class CircleHotPresenter extends AppListPresenterForDownload<CircleHotContract.View> implements CircleHotContract.Presenter, OnShareCallbackListener {
//extends AppListPresenterForDownload<V>
    @Inject
    CircleInfoGreenDaoImpl mCircleInfoGreenDao;
    @Inject
    UserCertificationInfoGreenDaoImpl mUserCertificationInfoDao;
    @Inject
    UserInfoRepository mCertificationDetailRepository;
    @Inject
    AllAdvertListBeanGreenDaoImpl mAdvertListBeanGreenDao;
    @Inject
    BaseCircleRepository mBaseCircleRepository;

    @Inject
    MessageReviewRepository mMessageReviewRepository;
    private Subscription subscribe;

    protected SharePolicy mSharePolicy;
    @Inject
    public CircleHotPresenter(CircleHotContract.View rootView/*, AllAdvertListBeanGreenDaoImpl allAdvertListBeanGreenDao*/) {
        super(rootView);
//        this.mAllAdvertListBeanGreenDao = allAdvertListBeanGreenDao;

    }

    @Override
    protected boolean useEventBus() {
        return true;
    }


    @Override
    public void requestCacheData(Long maxId, boolean isLoadMore) {

    }

    @Override
    public boolean insertOrUpdateData(@NotNull List<DynamicDetailBeanV2> data, boolean isLoadMore) {
        return false;
    }

    @Override
    public boolean isTourist() {
        return false;
    }

    @Override
    public boolean isLogin() {
        return false;
    }

    @Override
    public boolean usePayPassword() {
        return false;
    }

    @Override
    public boolean handleTouristControl() {
        return false;
    }

    @Override
    public SystemConfigBean getSystemConfigBean() {
        return null;
    }

    @Override
    public String getGoldName() {
        return null;
    }

    @Override
    public String getGoldUnit() {
        return null;
    }

    @Override
    public int getRatio() {
        return 0;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public boolean userHasPassword() {
        return false;
    }

    @Override
    public void requestNetData(Long maxId, final boolean isLoadMore) {
        // 10 个明星头
        if (maxId == null || maxId.intValue() == 0) {
            Observable observableSuperStar = mMessageReviewRepository.getPostHotSuperStar();
            Subscription commentSubSuperStar = observableSuperStar.subscribe(new BaseSubscribeForV2() {
                @Override
                protected void onSuccess(Object data) {
                    List<TopSuperStarBean> result = (List<TopSuperStarBean>) data;
                    mRootView.onNetSuccessHotSuperStar(result);
//                  mRootView.onNetResponseSuccess(result, isLoadMore);
                }

                @Override
                protected void onFailure(String message, int code) {
                    mRootView.showMessage(message);
                    mRootView.onResponseError(null, isLoadMore);
                }

                @Override
                protected void onException(Throwable throwable) {
                    mRootView.showMessage(throwable.getMessage());
                    mRootView.onResponseError(throwable, isLoadMore);
                }
            });
            addSubscrebe(commentSubSuperStar);
        }

        Observable observable = mMessageReviewRepository.getHotPost(null, maxId == null ? 0 : maxId.intValue(),"hot");
        Subscription commentSub = observable.subscribe(new BaseSubscribeForV2() {
            @Override
            protected void onSuccess(Object data) {

                List<DynamicDetailBeanV2> result = (List<DynamicDetailBeanV2>) data;
//                CirclePostBean circlePostBean = (CirclePostBean) data;
//                List<CirclePostListBean> result = new ArrayList<>();
//                if (circlePostBean.getPinned() != null) {
//                    result.addAll(circlePostBean.getPinned());
//                }
//                if (circlePostBean.getFeeds() != null) {
//                    result.addAll(circlePostBean.getFeeds());
//                }
                mRootView.onNetResponseSuccess(result, isLoadMore);
            }

            @Override
            protected void onFailure(String message, int code) {
                mRootView.showMessage(message);
                mRootView.onResponseError(null, isLoadMore);
            }

            @Override
            protected void onException(Throwable throwable) {
                mRootView.showMessage(throwable.getMessage());
                mRootView.onResponseError(throwable, isLoadMore);
            }
        });
        addSubscrebe(commentSub);


    }

    /**
     * 分享
     * @param dynamicBean
     * @param bitmap
     */
    public void sharePost(DynamicDetailBeanV2 dynamicBean, Bitmap bitmap) {
        if (mSharePolicy == null) {
            if (mRootView instanceof Fragment) {
                mSharePolicy = new UmengSharePolicyImpl(((Fragment) mRootView).getActivity());
            } else {
                return;
            }
        }
        ((UmengSharePolicyImpl) mSharePolicy).setOnShareCallbackListener(this);
        ShareContent shareContent = new ShareContent();
        shareContent.setTitle(mContext.getString(R.string.share_dynamic, mContext.getString(R.string.app_name)));
        shareContent.setContent(TextUtils.isEmpty(dynamicBean.getFeed_content()) ? mContext.getString(R.string
                .share_default, mContext.getString(R.string.app_name)) : dynamicBean.getFeed_content());
        if (bitmap != null) {
            shareContent.setBitmap(bitmap);
        } else {
            shareContent.setBitmap(ConvertUtils.drawBg4Bitmap(Color.WHITE, BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.icon)));
        }
        if (dynamicBean.getFeed_from() == DEFAULT_ADVERT_FROM_TAG) {
            //广告的连接放在了 deleted_at 中
            shareContent.setUrl(dynamicBean.getDeleted_at());
        } else {
            shareContent.setUrl(TSShareUtils.convert2ShareUrl(String.format(ApiConfig.APP_PATH_SHARE_DYNAMIC, dynamicBean.getId()
                    == null ? "" : dynamicBean.getId())));
        }
        mSharePolicy.setShareContent(shareContent);
        mSharePolicy.showShare(((TSFragment) mRootView).getActivity());
    }

    public void sharePost(DynamicDetailBeanV2 dynamicBean, Bitmap bitmap, SHARE_MEDIA type) {
        if (mSharePolicy == null) {
            if (mRootView instanceof Fragment) {
                mSharePolicy = new UmengSharePolicyImpl(((Fragment) mRootView).getActivity());
            } else {
                return;
            }
        }
        ShareContent shareContent = new ShareContent();
        shareContent.setTitle(mContext.getString(R.string.share_dynamic, mContext.getString(R.string.app_name)));
        shareContent.setContent(TextUtils.isEmpty(dynamicBean.getFeed_content()) ? mContext.getString(R.string
                .share_default, mContext.getString(R.string.app_name)) : dynamicBean.getFeed_content());
        if (bitmap != null) {
            shareContent.setBitmap(bitmap);
        } else {
            shareContent.setBitmap(ConvertUtils.drawBg4Bitmap(Color.WHITE, BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.icon)));
        }
        shareContent.setUrl(TSShareUtils.convert2ShareUrl(String.format(ApiConfig.APP_PATH_SHARE_DYNAMIC, dynamicBean
                .getId()
                == null ? "" : dynamicBean.getId())));
        mSharePolicy.setShareContent(shareContent);
        switch (type) {
            case QQ:
                mSharePolicy.shareQQ(((TSFragment) mRootView).getActivity(), this);
                break;
            case QZONE:
                mSharePolicy.shareZone(((TSFragment) mRootView).getActivity(), this);
                break;
            case WEIXIN:
                mSharePolicy.shareWechat(((TSFragment) mRootView).getActivity(), this);
                break;
            case WEIXIN_CIRCLE:
                mSharePolicy.shareMoment(((TSFragment) mRootView).getActivity(), this);
                break;
            case SINA:
                mSharePolicy.shareWeibo(((TSFragment) mRootView).getActivity(), this);
                break;
            case MORE:
                String videoUrl = String.format(ApiConfig.APP_DOMAIN + ApiConfig.FILE_PATH,
                        dynamicBean.getVideo().getVideo_id());
                downloadFile(videoUrl);
                break;
            default:
        }

    }

    public void downloadFile(String url) {
        if (!JZUtils.isWifiConnected(mContext) && !SharePreferenceUtils.getBoolean(mContext, ALLOW_GPRS)) {
            initWarningDialog(url);
        } else {
            download(url);
        }
    }

    @Override
    public void cancelDownload(String url) {

    }

    @Override
    public void onStart(Share share) {
    }

    @Override
    public void onSuccess(Share share) {
        mRootView.showSnackSuccessMessage(mContext.getString(R.string.share_sccuess));
    }

    @Override
    public void onError(Share share, Throwable throwable) {
        mRootView.showSnackErrorMessage(mContext.getString(R.string.share_fail));
    }

    @Override
    public void onCancel(Share share) {
        mRootView.showSnackSuccessMessage(mContext.getString(R.string.share_cancel));
    }

//    @Override
//    public List<RealAdvertListBean> getBannerAdvert() {
//        if (!com.zhiyicx.common.BuildConfig.USE_ADVERT || mAllAdvertListBeanGreenDao.getDynamicBannerAdvert() == null) {
//            return new ArrayList<>();
//        }
//        AllAdverListBean allAdverListBean = mAllAdvertListBeanGreenDao.getDynamicBannerAdvert();
//        if (allAdverListBean == null) {
//            return null;
//        }
//        return allAdverListBean.getMRealAdvertListBeen();
//    }


//    @Override
//    public List<RealAdvertListBean> getListAdvert() {
//        if (!com.zhiyicx.common.BuildConfig.USE_ADVERT || mAllAdvertListBeanGreenDao.getDynamicListAdvert() == null) {
//            return new ArrayList<>();
//        }
//        if (mAllAdvertListBeanGreenDao.getDynamicListAdvert() == null) {
//            return new ArrayList<>();
//        }
//        return mAllAdvertListBeanGreenDao.getDynamicListAdvert().getMRealAdvertListBeen();
//    }
}
