package com.zhiyicx.thinksnsplus.data.source.repository;

import com.zhiyicx.thinksnsplus.data.beans.AddSearchKeyResInfo;
import com.zhiyicx.thinksnsplus.data.beans.RecommandVideoBean;
import com.zhiyicx.thinksnsplus.data.beans.SearchHistoryBean;
import com.zhiyicx.thinksnsplus.data.beans.SearchHistoryBeanV2;
import com.zhiyicx.thinksnsplus.data.beans.SuperStarClassifyBean;
import com.zhiyicx.thinksnsplus.data.beans.UploadVideoCommentResInfo;
import com.zhiyicx.thinksnsplus.data.beans.VideoCommentBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoCommentListBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoDetailBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoListBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoTagListBean;
import com.zhiyicx.thinksnsplus.data.source.remote.ServiceManager;
import com.zhiyicx.thinksnsplus.data.source.remote.VideoClient2;
import com.zhiyicx.thinksnsplus.data.source.repository.i.IVideoRepository2;

import java.util.List;

import javax.inject.Inject;

import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author wulianshu
 * @describe 上传文件的实现类, 通过dagger注入
 * @date 2017/1/22
 * @contact email:450127106@qq.com
 */

public class VideoRepository2 implements IVideoRepository2 {
    private VideoClient2 videoClient2;

    @Inject
    public VideoRepository2(ServiceManager serviceManager) {
        videoClient2 = serviceManager.getVideoClient2();
    }

    @Override
    public Observable<List<VideoListBean>> getVideoList(int channel_id, Integer limit, Integer after, String keyword, Integer star_id, String subTags, String firstTags) {
        return videoClient2.getVideoList(channel_id, limit, after, keyword, star_id, subTags, firstTags)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<VideoDetailBean> getVideoDetail(String id) {
        return videoClient2.getVideoDetail(id)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


    @Override
    public Observable<VideoCommentListBean> getVideoComments(String id) {
        return videoClient2.getVideoComments(id)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<RecommandVideoBean>> getRecommandVideo(String id) {
        return videoClient2.getVideoRecommands(id)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<SuperStarClassifyBean>> getAllSuperStar() {
        return videoClient2.getAllSuperStar()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<VideoTagListBean>> getAllVideoTags() {
        return videoClient2.getAllVideoTags().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<SearchHistoryBeanV2>> getSearchHistory(String type) {
        return videoClient2.getSearchHistory(type).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<AddSearchKeyResInfo> addSearchKey(String keywords) {
        return videoClient2.addSearchKey(keywords).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<AddSearchKeyResInfo> clearSearchHistory() {
        return videoClient2.clearHistorySearchKey().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<VideoListBean>> getVideoRecord(Integer limit, Integer after) {
        return videoClient2.getVideoRecord(limit, after).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<AddSearchKeyResInfo> deleteVideoRecord(String ids) {
        return videoClient2.deleteVideoRecord(ids).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<AddSearchKeyResInfo> uploadVideoRecord(String videoId, String progress) {
        return videoClient2.uploadVideoRecord(videoId, progress).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<VideoListBean>> getVideoColletion(Integer limit, Integer after) {
        return videoClient2.getVideoCollection(limit, after).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<AddSearchKeyResInfo> collectVideo(String videoId,String collections) {
        return videoClient2.collectVideo(videoId,collections).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
    @Override
    public Observable<UploadVideoCommentResInfo> uploadVideoComments(String id, String body, String reply_user, String reply_comment_id) {
        return videoClient2.uploadVideoComments(id,body,reply_user,reply_comment_id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

}
