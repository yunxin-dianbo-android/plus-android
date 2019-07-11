package com.zhiyicx.thinksnsplus.modules.channel;

import com.zhiyicx.baseproject.base.SystemConfigBean;
import com.zhiyicx.common.dagger.scope.FragmentScoped;
import com.zhiyicx.thinksnsplus.base.AppBasePresenter;
import com.zhiyicx.thinksnsplus.base.BaseSubscribeForV2;
import com.zhiyicx.thinksnsplus.data.beans.DeleteOrAddVideoChannelResInfo;
import com.zhiyicx.thinksnsplus.data.beans.VideoChannelBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoChannelListBean;
import com.zhiyicx.thinksnsplus.data.source.repository.VideoChannelRepository;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
@FragmentScoped
public class ChannelFragmentPresenter extends AppBasePresenter<ChannelFragmentContract.View>
        implements ChannelFragmentContract.Presenter {
    ChannelFragmentContract.View mView;
    @Inject
    VideoChannelRepository videoChannelRepository;

    @Inject
    public ChannelFragmentPresenter(ChannelFragmentContract.View rootView) {
        super(rootView);
        mView = rootView;
    }

    @Override
    public void requestNetData() {
        videoChannelRepository.getVideoChannel().observeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscribeForV2<VideoChannelListBean>() {
                    @Override
                    protected void onSuccess(VideoChannelListBean data) {
                        mView.onNetResponseSuccess(data);
                    }

                    @Override
                    protected void onFailure(String message, int code) {
                        mView.showMsg(message);
//                      mRootView.showMessage(message);
                    }

                    @Override
                    protected void onException(Throwable throwable) {
                    }
                });
    }

    @Override
    public void deleteVideoChannel(String channel_id) {
        videoChannelRepository.addOrDeleteVideoChannel(channel_id,"del").observeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscribeForV2<DeleteOrAddVideoChannelResInfo>() {
                    @Override
                    protected void onSuccess(DeleteOrAddVideoChannelResInfo data) {
//                        mView.onNetResponseSuccess(data);
                    }
                    @Override
                    protected void onFailure(String message, int code) {
                        mView.showMsg(message);
                    }

                    @Override
                    protected void onException(Throwable throwable) {
                    }
                });
    }

    @Override
    public void addVideoChannel(String channel_id) {
        videoChannelRepository.addOrDeleteVideoChannel(channel_id,"add").observeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscribeForV2<DeleteOrAddVideoChannelResInfo>() {
                    @Override
                    protected void onSuccess(DeleteOrAddVideoChannelResInfo data) {
//                        mView.onNetResponseSuccess(data);
                    }

                    @Override
                    protected void onFailure(String message, int code) {
                        mView.showMsg(message);
                    }

                    @Override
                    protected void onException(Throwable throwable) {
                    }
                });
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
}
