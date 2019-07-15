package com.zhiyicx.thinksnsplus.modules.video;

import com.zhiyicx.baseproject.config.ApiConfig;
import com.zhiyicx.common.dagger.scope.FragmentScoped;
import com.zhiyicx.thinksnsplus.base.BaseSubscribeForV2;
import com.zhiyicx.thinksnsplus.base.fordownload.AppListPresenterForDownload;
import com.zhiyicx.thinksnsplus.data.beans.AdListBeanV2;
import com.zhiyicx.thinksnsplus.data.beans.TopSuperStarBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoChannelBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoListBean;
import com.zhiyicx.thinksnsplus.data.source.repository.AdRepository;
import com.zhiyicx.thinksnsplus.data.source.repository.BaseDynamicRepository;
import com.zhiyicx.thinksnsplus.data.source.repository.VideoRepository2;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

@FragmentScoped
public class VideoHomePresenter extends AppListPresenterForDownload<VideoHomeCongract.View> implements VideoHomeCongract.Presenter {
    //    @Inject
    @Inject
    VideoRepository2 videoRepository2;
    @Inject
    AdRepository adRepository;
    @Inject
    public VideoHomePresenter(VideoHomeCongract.View rootView) {
        super(rootView);
    }

    @Override
    public void requestNetData(Long maxId, boolean isLoadMore) {
        VideoChannelBean videoChannelBean = mRootView.getVideoChannelBean();
        if (maxId == null) {
            maxId = 0l;
        }
        String keyWord = mRootView.getSearchKeyWord();
        Observable<List<VideoListBean>> observable = videoRepository2.getVideoList(videoChannelBean.getId(), 15, maxId.intValue(),keyWord,null,null,null);
        observable.subscribe(new BaseSubscribeForV2<List<VideoListBean>>() {
            @Override
            protected void onSuccess(List<VideoListBean> data) {
                List<VideoListBean> result = data;
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
    }

    @Override
    public void requestCacheData(Long maxId, boolean isLoadMore) {

    }

    @Override
    public boolean insertOrUpdateData(@NotNull List<VideoListBean> data, boolean isLoadMore) {
        return false;
    }

    @Override
    public void requestAdData(int position) {
        adRepository.getAd(position).subscribe(new BaseSubscribeForV2<List<AdListBeanV2>>() {
            @Override
            protected void onSuccess(List<AdListBeanV2> data) {
                mRootView.onAdDataResSuccessed(data);
            }
        });
    }
}
