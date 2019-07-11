package com.zhiyicx.thinksnsplus.modules.home.mine.collection;


import com.zhiyicx.baseproject.base.ITSListPresenter;
import com.zhiyicx.baseproject.base.ITSListView;
import com.zhiyicx.thinksnsplus.data.beans.VideoListBean;

public class VideoCollectionContract {

    public interface View<P extends VideoCollectionContract.Presenter> extends ITSListView<VideoListBean, P> {

    }
    public interface Presenter  extends ITSListPresenter<VideoListBean> {

    }

//    public interface View<P extends VideoRecordContract.Presenter> extends ITSListView<VideoListBean, P> {
//
//    }
//    public interface Presenter  extends ITSListPresenter<VideoListBean> {
//
//    }
}
