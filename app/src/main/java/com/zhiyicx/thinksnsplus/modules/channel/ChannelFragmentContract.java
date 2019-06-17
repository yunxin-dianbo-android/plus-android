package com.zhiyicx.thinksnsplus.modules.channel;

import com.zhiyicx.baseproject.base.IBaseTouristPresenter;
//import com.zhiyicx.common.mvp.i.IBaseView;
import com.zhiyicx.common.mvp.i.IBaseView;
import com.zhiyicx.thinksnsplus.data.beans.VideoChannelBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoChannelListBean;

import java.util.List;


public class ChannelFragmentContract {
    interface View extends IBaseView /*extends IBaseView<Presenter> */ {
        void onNetResponseSuccess(VideoChannelListBean first);

        void showMsg(String msg);
    }

    interface Presenter extends IBaseTouristPresenter {
        void requestNetData();

        void deleteVideoChannel(String channel_id);

        void addVideoChannel(String channel_id);
    }
}
