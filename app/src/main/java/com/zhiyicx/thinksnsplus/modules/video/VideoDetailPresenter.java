package com.zhiyicx.thinksnsplus.modules.video;

import com.zhiyicx.common.dagger.scope.FragmentScoped;
import com.zhiyicx.thinksnsplus.base.BaseSubscribeForV2;
import com.zhiyicx.thinksnsplus.base.fordownload.AppListPresenterForDownload;
import com.zhiyicx.thinksnsplus.data.beans.AddSearchKeyResInfo;
import com.zhiyicx.thinksnsplus.data.beans.RecommandVideoBean;
import com.zhiyicx.thinksnsplus.data.beans.UploadVideoCommentResInfo;
import com.zhiyicx.thinksnsplus.data.beans.VideoCommentBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoCommentListBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoDetailBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoListBean;
import com.zhiyicx.thinksnsplus.data.source.repository.BaseDynamicRepository;
import com.zhiyicx.thinksnsplus.data.source.repository.VideoRepository2;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

@FragmentScoped
public class VideoDetailPresenter extends AppListPresenterForDownload<VideoDetailCongract.View> implements VideoDetailCongract.Presenter {
    //    @Inject
    @Inject
    VideoRepository2 videoRepository2;
    @Inject
    BaseDynamicRepository mBaseDynamicRepository;
    @Inject
    public VideoDetailPresenter(VideoDetailCongract.View rootView) {
        super(rootView);
//        this.videoRepository2 = videoRepository2;
    }

    @Override
    public void requestNetData(Long maxId, boolean isLoadMore) {
        int videoId = mRootView.getVideoListBean().getId();
        Observable<VideoCommentListBean> observable = videoRepository2.getVideoComments(videoId + "");
        observable.subscribe(new BaseSubscribeForV2<VideoCommentListBean>() {
            @Override
            protected void onSuccess(VideoCommentListBean data) {
                List<VideoCommentBean> result = data.comments;
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
        getRecommandVideo();

    }

    @Override
    public void requestCacheData(Long maxId, boolean isLoadMore) {

    }


    @Override
    public boolean insertOrUpdateData(@NotNull List<VideoCommentBean> data, boolean isLoadMore) {
        return false;
    }

    @Override
    public void getVideoDetailInfo() {
        VideoListBean videoListBean = mRootView.getVideoListBean();
        Observable<VideoDetailBean> videoDetailBeanObservable = videoRepository2.getVideoDetail(videoListBean.getId() + "");
        videoDetailBeanObservable.subscribe(new BaseSubscribeForV2<VideoDetailBean>() {
            @Override
            protected void onSuccess(VideoDetailBean data) {
                mRootView.onGetVideoDetailSuccess(data);
            }
        });
    }

    @Override
    public void getRecommandVideo() {
        int videoId = mRootView.getVideoListBean().getId();
        Observable<List<RecommandVideoBean>> observableRecommand = videoRepository2.getRecommandVideo(videoId + "");
        observableRecommand.subscribe(new BaseSubscribeForV2<List<RecommandVideoBean>>() {
            @Override
            protected void onSuccess(List<RecommandVideoBean> data) {
                mRootView.onGetRecommandVideoSuccess(data);
            }
        });
    }

    @Override
    public void uploadVideoRecord(String videoId, String progress) {
        Observable<AddSearchKeyResInfo> observable = videoRepository2.uploadVideoRecord(videoId, progress);
        observable.subscribe(new BaseSubscribeForV2<AddSearchKeyResInfo>() {
            @Override
            protected void onSuccess(AddSearchKeyResInfo data) {

            }
        });
    }

    @Override
    public void collectVideo(String videoId,boolean isCollected) {
        String collections;
        if(isCollected){
            collections = "collections";
        }else{
            collections = "uncollect";
        }
        Observable<AddSearchKeyResInfo> observable = videoRepository2.collectVideo(videoId,collections);
        observable.subscribe(new BaseSubscribeForV2<AddSearchKeyResInfo>() {
            @Override
            protected void onSuccess(AddSearchKeyResInfo data) {
                mRootView.onCollectSuccess(isCollected);
            }
        });
    }

    @Override
    public void publishComment(String id, String body, String reply_user, String reply_comment_id){
        Observable<UploadVideoCommentResInfo> observable = videoRepository2.uploadVideoComments(id,body,reply_user,reply_comment_id);
        observable.subscribe(new BaseSubscribeForV2<UploadVideoCommentResInfo>() {
            @Override
            protected void onSuccess(UploadVideoCommentResInfo data) {
                mRootView.onPublishCommentsSuccess(data.comment);
            }
        });
    }

    @Override
    public void handleLike4Comment(Long comment_id, boolean isLike, int position) {
        if (isLike) {
            mBaseDynamicRepository.handleLike4Comment(comment_id).subscribe(new BaseSubscribeForV2<AddSearchKeyResInfo>() {
                @Override
                protected void onSuccess(AddSearchKeyResInfo data) {
                    mRootView.onCommentLikeStatuChanged(isLike, position);
                }
            });
        } else {
            mBaseDynamicRepository.handleLike4Comment(comment_id).subscribe(new BaseSubscribeForV2<AddSearchKeyResInfo>() {
                @Override
                protected void onSuccess(AddSearchKeyResInfo data) {
                    mRootView.onCommentLikeStatuChanged(isLike, position);
                }
            });
        }
    }


}
