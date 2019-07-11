package com.zhiyicx.thinksnsplus.modules.home.mine.collection;

import com.zhiyicx.thinksnsplus.base.AppBasePresenter;
import com.zhiyicx.thinksnsplus.base.BaseSubscribeForV2;
import com.zhiyicx.thinksnsplus.data.beans.VideoListBean;
import com.zhiyicx.thinksnsplus.data.source.repository.VideoRepository2;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

public class VideoCollectionPresenter extends AppBasePresenter<VideoCollectionContract.View> implements VideoCollectionContract.Presenter {

    @Inject
    VideoRepository2 videoRepository2;

    @Inject
    public VideoCollectionPresenter(VideoCollectionContract.View rootView) {
        super(rootView);
    }

    @Override
    public void requestNetData(Long maxId, boolean isLoadMore) {
        videoRepository2.getVideoColletion(null, maxId.intValue()).subscribe(new BaseSubscribeForV2<List<VideoListBean>>() {
            @Override
            protected void onSuccess(List<VideoListBean> data) {
                mRootView.onNetResponseSuccess(data,isLoadMore);
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
}
