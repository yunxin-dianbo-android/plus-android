package com.zhiyicx.thinksnsplus.modules.home.mine;

import com.zhiyicx.baseproject.base.ITSListPresenter;
import com.zhiyicx.baseproject.base.ITSListView;
import com.zhiyicx.thinksnsplus.data.beans.VideoListBean;

public class VideoRecordContract {
    public interface View<P extends VideoRecordContract.Presenter> extends ITSListView<VideoListBean, P> {

    }
    public interface Presenter  extends ITSListPresenter<VideoListBean> {
         void deleteVideoRecord(String ids);
    }
}
