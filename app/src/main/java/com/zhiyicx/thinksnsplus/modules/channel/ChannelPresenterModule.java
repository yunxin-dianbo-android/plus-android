package com.zhiyicx.thinksnsplus.modules.channel;


import dagger.Module;
import dagger.Provides;

/**
 * @author Jliuer
 * @Date 2017/11/14/11:37
 * @Email Jliuer@aliyun.com
 * @Description
 */
@Module
public class ChannelPresenterModule {
    ChannelFragmentContract.View mView;

    public ChannelPresenterModule(ChannelFragmentContract.View view) {
        mView = view;
    }

    @Provides
    ChannelFragmentContract.View providesChannelFragmentContractView() {
        return mView;
    }

}
