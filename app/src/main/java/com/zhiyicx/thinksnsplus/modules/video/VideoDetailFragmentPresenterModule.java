package com.zhiyicx.thinksnsplus.modules.video;

import dagger.Module;
import dagger.Provides;

/**
 * @Describe
 * @Author Jungle68
 * @Date 2017//13
 * @Contact master.jungle68@gmail.com
 */
@Module
public class VideoDetailFragmentPresenterModule {
    private final VideoDetailCongract.View mView;

    public VideoDetailFragmentPresenterModule(VideoDetailCongract.View view) {
        mView = view;
    }

    @Provides
    VideoDetailCongract.View provideVideoDetailCongractView() {
        return mView;
    }
}
