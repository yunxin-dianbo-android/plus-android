package com.zhiyicx.thinksnsplus.modules.home.mine.collection;


import com.zhiyicx.baseproject.base.ITSListPresenter;
import com.zhiyicx.baseproject.base.ITSListView;
import com.zhiyicx.thinksnsplus.data.beans.VideoListBean;

public class PostCollectionContract {

    public interface View<P extends PostCollectionContract.Presenter> extends ITSListView<VideoListBean, P> {

    }
    public interface Presenter  extends ITSListPresenter<VideoListBean> {

    }
}
