package com.zhiyicx.thinksnsplus.modules.home.mine;


import android.util.Log;

import com.zhiyicx.thinksnsplus.base.AppBasePresenter;
import com.zhiyicx.thinksnsplus.base.BaseSubscribeForV2;
import com.zhiyicx.thinksnsplus.data.beans.AddSearchKeyResInfo;
import com.zhiyicx.thinksnsplus.data.beans.VideoListBean;
import com.zhiyicx.thinksnsplus.data.source.repository.VideoRepository2;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class ViewRecordPresenter extends AppBasePresenter<VideoRecordContract.View> implements VideoRecordContract.Presenter {
    @Inject
    VideoRepository2 videoRepository2;

    @Inject
    public ViewRecordPresenter(VideoRecordContract.View rootView) {
        super(rootView);
    }

    @Override
    public void requestNetData(Long maxId, boolean isLoadMore) {
        Observable<List<VideoListBean>> observable = videoRepository2.getVideoRecord(null, maxId.intValue());
        observable.subscribe(new BaseSubscribeForV2<List<VideoListBean>>() {
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

    @Override
    public void deleteVideoRecord(String ids) {
        videoRepository2.deleteVideoRecord(ids).subscribe(new BaseSubscribeForV2<AddSearchKeyResInfo>() {
            @Override
            protected void onSuccess(AddSearchKeyResInfo data) {
                Log.e("wulianshu","deleteVideoRecord onSuccess");
            }
        });
    }
}
