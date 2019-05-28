package com.zhiyicx.thinksnsplus.modules.circle.main;

import com.zhiyicx.baseproject.base.BaseListBean;
import com.zhiyicx.baseproject.base.SystemConfigBean;
import com.zhiyicx.common.dagger.scope.FragmentScoped;
import com.zhiyicx.thinksnsplus.base.AppBasePresenter;
import com.zhiyicx.thinksnsplus.base.BaseSubscribeForV2;
import com.zhiyicx.thinksnsplus.data.beans.CircleInfo;
import com.zhiyicx.thinksnsplus.data.beans.TopPostListBean;
import com.zhiyicx.thinksnsplus.data.beans.TopSuperStarBean;
import com.zhiyicx.thinksnsplus.data.source.local.AllAdvertListBeanGreenDaoImpl;
import com.zhiyicx.thinksnsplus.data.source.local.CircleInfoGreenDaoImpl;
import com.zhiyicx.thinksnsplus.data.source.local.UserCertificationInfoGreenDaoImpl;
import com.zhiyicx.thinksnsplus.data.source.repository.BaseCircleRepository;
import com.zhiyicx.thinksnsplus.data.source.repository.MessageReviewRepository;
import com.zhiyicx.thinksnsplus.data.source.repository.UserInfoRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;

@FragmentScoped
public class CircleHotPresenter extends AppBasePresenter<CircleHotContract.View> implements CircleHotContract.Presenter {

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
    public boolean insertOrUpdateData(@NotNull List<TopPostListBean> data, boolean isLoadMore) {
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
//                mRootView.onNetResponseSuccess(result, isLoadMore);
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

        Observable observable = mMessageReviewRepository.getHotPost(null, maxId == null ? 0 : maxId.intValue());
        Subscription commentSub = observable.subscribe(new BaseSubscribeForV2() {
            @Override
            protected void onSuccess(Object data) {
                List<TopPostListBean> result = (List<TopPostListBean>) data;
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
