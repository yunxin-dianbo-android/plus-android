package com.zhiyicx.thinksnsplus.modules.channel;
import dagger.Module;
import dagger.Provides;

/**
 * @author Jliuer
 * @Date 2017/12/05/10:46
 * @Email Jliuer@aliyun.com
 * @Description
 */
@Module
public class VideoChannelFragmentPresenterModule {
    VideoChannelFragmentContract.View mView;

    public VideoChannelFragmentPresenterModule(VideoChannelFragmentContract.View view) {
        mView = view;
    }

    @Provides
    VideoChannelFragmentContract.View provideCirclePostDetailContractView(){
        return mView;
    }

}
