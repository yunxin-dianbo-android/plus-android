package com.zhiyicx.thinksnsplus.modules.home.mine;

import com.zhiyicx.baseproject.base.ITSListPresenter;
import com.zhiyicx.baseproject.base.ITSListView;
import com.zhiyicx.thinksnsplus.data.beans.AdListBeanV2;
import com.zhiyicx.thinksnsplus.data.beans.VideoListBean;

import java.util.List;

public class VideoRecordContract {
    public interface View<P extends VideoRecordContract.Presenter> extends ITSListView<VideoListBean, P> {
        void onAdDataResSuccessed(final List<AdListBeanV2> listBeanV2s);
    }
    public interface Presenter  extends ITSListPresenter<VideoListBean> {
         void deleteVideoRecord(String ids);
         void getAdData();
    }
}
