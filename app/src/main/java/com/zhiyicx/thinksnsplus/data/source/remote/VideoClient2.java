package com.zhiyicx.thinksnsplus.data.source.remote;

import com.zhiyicx.baseproject.config.ApiConfig;
import com.zhiyicx.thinksnsplus.data.beans.AddSearchKeyResInfo;
import com.zhiyicx.thinksnsplus.data.beans.DeleteOrAddVideoChannelResInfo;
import com.zhiyicx.thinksnsplus.data.beans.RecommandVideoBean;
import com.zhiyicx.thinksnsplus.data.beans.SearchHistoryBean;
import com.zhiyicx.thinksnsplus.data.beans.SearchHistoryBeanV2;
import com.zhiyicx.thinksnsplus.data.beans.SuperStarClassifyBean;
import com.zhiyicx.thinksnsplus.data.beans.UploadVideoCommentResInfo;
import com.zhiyicx.thinksnsplus.data.beans.VideoChannelBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoChannelListBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoCommentBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoCommentListBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoDetailBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoListBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoTagListBean;

import java.util.List;

import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author LiuChao
 * @describe
 * @date 2017/4/8
 * @contact email:450127106@qq.com
 */

public interface VideoClient2 {
    /**
     * 获取频道的动态列表
     *
     * @param tags [1,2]标签
     * @return
     */
    @GET(ApiConfig.APP_PATH_MY_VIDEOS_LIST)
    Observable<List<VideoListBean>> getVideoList(@Query("channel_id") int channel_id, @Query("limit") Integer limit, @Query("after") Integer after, @Query("keyword") String keyword, @Query("star_id") Integer star_id, @Query("tags") String tags, @Query("tag_cates") String tag_cates);

    /**
     * 获取频道的动态列表
     *
     * @return
     */
    @GET(ApiConfig.APP_PATH_MY_VIDEOS_DETAIL)
    Observable<VideoDetailBean> getVideoDetail(@Path("id") String id);

    /**
     * 获取频道的动态列表
     *
     * @return
     */
    @GET(ApiConfig.APP_PATH_MY_VIDEOS_COMMENTS)
    Observable<VideoCommentListBean> getVideoComments(@Path("id") String id);
    /**
     * 提交视频评论
     *body
     * string
     * 是
     * 评论内容
     * 3
     * reply_user
     * string
     * 是
     * 被回复人id，选填，当评论为子评论，且回复某用户时必填
     * 1
     * reply_comment_id
     * string
     * 是
     * 被回复的评论id（父级评论id)，选填，当评论为子评论时必填
     * @return
     */
    @POST(ApiConfig.APP_PATH_MY_VIDEOS_COMMENTS)
    Observable<UploadVideoCommentResInfo> uploadVideoComments(@Path("id") String id, @Query("body") String body, @Query("reply_user") String reply_user, @Query("reply_comment_id") String reply_comment_id);

    /**
     * 获取频道的动态列表
     *
     * @return
     */
    @GET(ApiConfig.APP_PATH_VIDEOS_RECOMMANDS)
    Observable<List<RecommandVideoBean>> getVideoRecommands(@Path("id") String id);

    /**
     * 获取所有明星
     *
     * @return
     */
    @GET(ApiConfig.APP_PATH_DIANBO_STARS)
    Observable<List<SuperStarClassifyBean>> getAllSuperStar();


    /**
     * 获取所视频标签
     *
     * @return
     */
    @GET(ApiConfig.APP_PATH_VIDEOS_ALL_TAGS)
    Observable<List<VideoTagListBean>> getAllVideoTags();

    /**
     * 获取所视频标签
     *
     * @return
     */
    @GET(ApiConfig.APP_PATH_VIDEOS_SEARCH_HISTORY)
    Observable<List<SearchHistoryBeanV2>> getSearchHistory(@Query("type") String type);

    /**
     * 获取所视频标签
     *
     * @return
     */
    @POST(ApiConfig.APP_PATH_VIDEOS_SEARCH_HISTORY)
    Observable<AddSearchKeyResInfo> addSearchKey(@Query("keyword") String keyword);

    /**
     * 获取所视频标签
     *
     * @return
     */
    @DELETE(ApiConfig.APP_PATH_VIDEOS_SEARCH_HISTORY)
    Observable<AddSearchKeyResInfo> clearHistorySearchKey();

    /**
     * 获取所视频标签
     *
     * @return
     */
    @GET(ApiConfig.APP_PATH_VIDEOS_RECORD)
    Observable<List<VideoListBean>> getVideoRecord(@Query("limit")Integer limit,@Query("after") Integer after);


    /**
     * 获取所视频标签
     *
     * @return
     */
    @DELETE(ApiConfig.APP_PATH_VIDEOS_RECORD)
    Observable<AddSearchKeyResInfo> deleteVideoRecord(@Query("video_ids")String videoId);

    /**
     * 获取所视频标签
     *
     * @return
     */
    @POST(ApiConfig.APP_PATH_VIDEOS_RECORD)
    Observable<AddSearchKeyResInfo> uploadVideoRecord(@Query("video_id") String video_id, @Query("progress") String progress);

    /**
     * 获取所视频标签
     *
     * @return
     */
    @GET(ApiConfig.APP_PATH_VIDEOS_COLLECTION)
    Observable<List<VideoListBean>> getVideoCollection(@Query("limit")Integer limit,@Query("after") Integer after);
    /**
     * 收藏视频
     *
     * @return
     */
    @POST(ApiConfig.APP_PATH_COLLECT_VIDEOS)
    Observable<AddSearchKeyResInfo> collectVideo(@Path("video_id") String videoId,@Path("collections") String collections);
}
