package com.zhiyicx.thinksnsplus.modules.video;

import com.zhiyicx.thinksnsplus.base.fordownload.ITSListPresenterForDownload;
import com.zhiyicx.thinksnsplus.base.fordownload.ITSListViewForDownload;
import com.zhiyicx.thinksnsplus.data.beans.VideoChannelBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoCommentBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoListBean;

public class VideoHomeCongract {
    public interface View<P extends Presenter> extends ITSListViewForDownload<VideoListBean, P> {
        /**
         * 获取频道
         *
         * @return
         */
        VideoChannelBean getVideoChannelBean();

        String getSearchKeyWord();

    }

    public interface Presenter extends ITSListPresenterForDownload<VideoListBean> {

    }
}
