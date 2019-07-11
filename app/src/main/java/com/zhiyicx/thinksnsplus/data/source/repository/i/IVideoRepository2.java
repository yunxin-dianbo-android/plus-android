package com.zhiyicx.thinksnsplus.data.source.repository.i;

import com.zhiyicx.thinksnsplus.data.beans.AddSearchKeyResInfo;
import com.zhiyicx.thinksnsplus.data.beans.RecommandVideoBean;
import com.zhiyicx.thinksnsplus.data.beans.SearchHistoryBean;
import com.zhiyicx.thinksnsplus.data.beans.SearchHistoryBeanV2;
import com.zhiyicx.thinksnsplus.data.beans.SuperStarClassifyBean;
import com.zhiyicx.thinksnsplus.data.beans.UploadVideoCommentResInfo;
import com.zhiyicx.thinksnsplus.data.beans.VideoCommentListBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoDetailBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoListBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoTagListBean;

import java.util.List;

import rx.Observable;

/**
 * @Describe 视频频道
 * @Author Jungle68
 * @Date 2017/1/19
 * @Contact master.jungle68@gmail.com
 */

public interface IVideoRepository2 {
    /**
     * 获取对话列表信息
     *
     * @return
     */
    Observable<List<VideoListBean>> getVideoList(int channel_id,Integer limit,Integer after,String keyword,Integer star_id,String subTags,String fistTags);


    Observable<VideoDetailBean> getVideoDetail(String id);
    Observable<VideoCommentListBean> getVideoComments(String id);
    Observable<List<RecommandVideoBean>> getRecommandVideo(String id);
    Observable<List<SuperStarClassifyBean>> getAllSuperStar();

    Observable<List<VideoTagListBean>> getAllVideoTags();

    Observable<List<SearchHistoryBeanV2>> getSearchHistory(String type);

    Observable<AddSearchKeyResInfo> addSearchKey(String keyword);

    Observable<AddSearchKeyResInfo> clearSearchHistory();

    Observable<List<VideoListBean>> getVideoRecord(Integer limit, Integer after);

    Observable<AddSearchKeyResInfo> deleteVideoRecord(String ids);

    Observable<AddSearchKeyResInfo> uploadVideoRecord(String videoId, String progress);

    Observable<List<VideoListBean>> getVideoColletion(Integer limit, Integer after);

    Observable<AddSearchKeyResInfo> collectVideo(String videoId,String collections);

    Observable<UploadVideoCommentResInfo> uploadVideoComments(String id, String body, String reply_user, String reply_comment_id);

   }
