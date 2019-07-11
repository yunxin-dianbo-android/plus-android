package com.zhiyicx.thinksnsplus.modules.guide;

import com.zhiyicx.baseproject.base.SystemConfigBean;
import com.zhiyicx.common.mvp.BasePresenter;
import com.zhiyicx.thinksnsplus.base.AppApplication;
import com.zhiyicx.thinksnsplus.base.BaseSubscribeForV2;
import com.zhiyicx.thinksnsplus.config.BackgroundTaskRequestMethodConfig;
import com.zhiyicx.thinksnsplus.data.beans.AllAdverListBean;
import com.zhiyicx.thinksnsplus.data.beans.BackgroundRequestTaskBean;
import com.zhiyicx.thinksnsplus.data.beans.GameInfoBean;
import com.zhiyicx.thinksnsplus.data.beans.RealAdvertListBean;
import com.zhiyicx.thinksnsplus.data.beans.UserPermissions;
import com.zhiyicx.thinksnsplus.data.beans.VideoChannelBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoChannelListBean;
import com.zhiyicx.thinksnsplus.data.source.local.AllAdvertListBeanGreenDaoImpl;
import com.zhiyicx.thinksnsplus.data.source.local.RealAdvertListBeanGreenDaoImpl;
import com.zhiyicx.thinksnsplus.data.source.repository.AuthRepository;
import com.zhiyicx.thinksnsplus.data.source.repository.BillRepository;
import com.zhiyicx.thinksnsplus.data.source.repository.CommonRepository;
import com.zhiyicx.thinksnsplus.data.source.repository.SystemRepository;
import com.zhiyicx.thinksnsplus.data.source.repository.UserInfoRepository;
import com.zhiyicx.thinksnsplus.data.source.repository.VideoChannelRepository;
import com.zhiyicx.thinksnsplus.modules.home.HomeActivity;
import com.zhiyicx.thinksnsplus.modules.login.LoginActivity;
import com.zhiyicx.thinksnsplus.preset.PresetManager;
import com.zhiyicx.thinksnsplus.service.backgroundtask.BackgroundTaskManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @Describe
 * @Author Jungle68
 * @Date 2017/2/10
 * @Contact master.jungle68@gmail.com
 */

public class GuidePresenter extends BasePresenter<GuideContract.View>
        implements GuideContract.Presenter {
    @Inject
    AuthRepository mIAuthRepository;
    @Inject
    SystemRepository mSystemRepository;
    @Inject
    BillRepository mWalletRepository;
    @Inject
    AllAdvertListBeanGreenDaoImpl mAllAdvertLIstBeanGreendo;

    @Inject
    UserInfoRepository mUserInfoRepository;


    @Inject
    VideoChannelRepository videoChannelRepository;

//    GameInfoBean

    @Inject
    public GuidePresenter(GuideContract.View rootView) {
        super(rootView);
    }

    @Override
    public void initConfig() {
        // 系统扩展配置信息处理
        mSystemRepository.getBootstrappersInfoFromServer();
        if (mIAuthRepository.isLogin()) {
            // 刷新 token
            mIAuthRepository.refreshToken();
            // 获取用户权限信息
            mUserInfoRepository.getCurrentLoginUserPermissions()
                    .observeOn(Schedulers.io())
                    .subscribe(new BaseSubscribeForV2<List<UserPermissions>>() {
                        @Override
                        protected void onSuccess(List<UserPermissions> data) {
                            mIAuthRepository.getAuthBean().setUserPermissions(data);
                        }
                    });
        } else {
            videoChannelRepository.getMyVideoChannel().subscribe(new BaseSubscribeForV2<List<VideoChannelBean>>() {
                @Override
                protected void onSuccess(List<VideoChannelBean> data) {
//                    List<VideoChannelBean> videoChannelBeanList = new ArrayList<>();
                    if (data != null) {
//                        videoChannelBeanList.addAll(data.default_channels);
//                        videoChannelBeanList.addAll(data.user_channels);
//                        videoChannelBeanList.addAll(data.other_channels);
                        AppApplication.setVideoChannelListBeans(data);
                    }
                }
            });

        }
        mSystemRepository.getGameInfo().subscribe(new BaseSubscribeForV2<GameInfoBean>() {
            @Override
            protected void onSuccess(GameInfoBean data) {
                PresetManager.getInstance().gameInfoBean = data;
            }
        });


    }

    @Override
    public void checkLogin() {
//        mRootView.startActivity(mIAuthRepository.isLogin() ? HomeActivity.class : LoginActivity.class);
        mRootView.startActivity(HomeActivity.class);
    }


    @Override
    @Deprecated
    public void getLaunchAdverts() {
        // 这个地方，后台服务还没有启动起来，但是判断条件已执行，所以没用,迁移至 homepresenter.initAdvert();
        if (AppApplication.getMyUserIdWithdefault() < 0) {
            return;
        }
        BackgroundRequestTaskBean backgroundRequestTaskBean = new BackgroundRequestTaskBean();
        backgroundRequestTaskBean.setUser_id(AppApplication.getMyUserIdWithdefault());
        backgroundRequestTaskBean.setMethodType(BackgroundTaskRequestMethodConfig.GET_ADVERT_INFO);
        BackgroundTaskManager.getInstance(mContext).addBackgroundRequestTask(backgroundRequestTaskBean);
    }

    @Override
    public List<RealAdvertListBean> getBootAdvert() {
        AllAdverListBean boot = mAllAdvertLIstBeanGreendo.getBootAdvert();
        try {
            if (boot != null && boot.getMRealAdvertListBeen() != null && !boot.getMRealAdvertListBeen().isEmpty()) {
                return boot.getMRealAdvertListBeen();
            }
        } catch (Exception ignore) {

        }
        return null;
    }

    @Override
    public SystemConfigBean getAdvert() {
        return mSystemRepository.getBootstrappersInfoFromLocal();
    }
}

