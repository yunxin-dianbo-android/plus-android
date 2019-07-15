package com.zhiyicx.thinksnsplus.modules.video;

import com.zhiyicx.thinksnsplus.base.fordownload.ITSListPresenterForDownload;
import com.zhiyicx.thinksnsplus.base.fordownload.ITSListViewForDownload;
import com.zhiyicx.thinksnsplus.data.beans.AdListBeanV2;
import com.zhiyicx.thinksnsplus.data.beans.VideoChannelBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoCommentBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoListBean;

import java.util.List;

public class VideoHomeCongract {
    public interface View<P extends Presenter> extends ITSListViewForDownload<VideoListBean, P> {
        /**
         * 获取频道
         *
         * @return
         */
        VideoChannelBean getVideoChannelBean();

        String getSearchKeyWord();

        void onAdDataResSuccessed(List<AdListBeanV2> listBeanV2s);
    }

    public interface Presenter extends ITSListPresenterForDownload<VideoListBean> {
        void requestAdData(int position);
    }
}
