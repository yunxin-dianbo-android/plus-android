package com.zhiyicx.thinksnsplus.modules.channel;

import com.zhiyicx.baseproject.base.IBaseTouristPresenter;
import com.zhiyicx.baseproject.base.ITSListPresenter;
import com.zhiyicx.baseproject.base.ITSListView;
import com.zhiyicx.common.mvp.i.IBaseView;
import com.zhiyicx.thinksnsplus.data.beans.DynamicDetailBeanV2;
import com.zhiyicx.thinksnsplus.data.beans.VideoChannelListBean;

import java.util.List;

//import com.zhiyicx.common.mvp.i.IBaseView;


public class VideoChannelFragmentContract {

    interface View extends ITSListView <DynamicDetailBeanV2, Presenter>{
        void onNetResponseSuccess(List<DynamicDetailBeanV2> first);
    }

    interface Presenter extends IBaseTouristPresenter, ITSListPresenter<DynamicDetailBeanV2> {
    }
}
