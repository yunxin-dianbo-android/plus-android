package com.zhiyicx.thinksnsplus.modules.channel;

import com.zhiyicx.baseproject.base.IBaseTouristPresenter;
import com.zhiyicx.baseproject.base.ITSListPresenter;
import com.zhiyicx.baseproject.base.ITSListView;
import com.zhiyicx.thinksnsplus.data.beans.SuperStarBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoListBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoTagListBean;

import java.util.List;



public class VideoChannelFragmentContract {

    interface View extends ITSListView <VideoListBean, Presenter>{
        SuperStarBean getSuperStarBean();
        void onAllVideoTagResponseSuccess(List<VideoTagListBean> videoTagListBeans);
        List<VideoListBean.TagsBean> getChoosedVideoTags();
    }

    interface Presenter extends ITSListPresenter<VideoListBean> {
        /**
         * 获取视频所有的标签
         */
         void requestAllVideoTags();
    }
}
