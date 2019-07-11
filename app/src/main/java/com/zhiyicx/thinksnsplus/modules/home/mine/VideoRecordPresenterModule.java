package com.zhiyicx.thinksnsplus.modules.home.mine;

import com.zhiyicx.thinksnsplus.modules.video.VideoDetailCongract;

import dagger.Module;
import dagger.Provides;

/**
 * @Describe
 * @Author Jungle68
 * @Date 2017//13
 * @Contact master.jungle68@gmail.com
 */
@Module
public class VideoRecordPresenterModule {
    private final VideoRecordContract.View mView;

    public VideoRecordPresenterModule(VideoRecordContract.View view) {
        mView = view;
    }

    @Provides
    VideoRecordContract.View provideVideoRecordContractView() {
        return mView;
    }
}
