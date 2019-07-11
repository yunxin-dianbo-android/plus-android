package com.zhiyicx.thinksnsplus.modules.video;

import com.zhiyicx.thinksnsplus.base.fordownload.ITSListPresenterForDownload;
import com.zhiyicx.thinksnsplus.base.fordownload.ITSListViewForDownload;
import com.zhiyicx.thinksnsplus.data.beans.RecommandVideoBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoChannelBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoCommentBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoDetailBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoListBean;

import java.util.List;

public class VideoDetailCongract {
    public interface View<P extends Presenter> extends ITSListViewForDownload<VideoCommentBean, P> {
        /**
         * 获取频道
         *
         * @return
         */
        VideoListBean getVideoListBean();


        void onGetVideoDetailSuccess(VideoDetailBean videoDetailBean);

        void onGetRecommandVideoSuccess(List<RecommandVideoBean> recommandVideoBeans);

        void onCollectSuccess(boolean isCollected);

        void onPublishCommentsSuccess(VideoCommentBean comment);

        /**
         * 点赞结果的回调
         * @param isLike
         * @param position
         */
        void onCommentLikeStatuChanged(boolean isLike,int position);
    }

    public interface Presenter extends ITSListPresenterForDownload<VideoCommentBean> {
        void getVideoDetailInfo();

        void getRecommandVideo();

        void uploadVideoRecord(String videoId,String progress);

        void collectVideo(String videoId,boolean isCollected);

        void publishComment(String id, String body, String reply_user, String reply_comment_id);

        void handleLike4Comment(Long comment_id, boolean isLike,int position);
    }
}
