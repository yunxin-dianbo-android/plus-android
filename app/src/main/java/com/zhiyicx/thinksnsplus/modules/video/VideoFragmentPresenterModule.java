package com.zhiyicx.thinksnsplus.modules.video;

import com.zhiyicx.thinksnsplus.modules.dynamic.list.DynamicContract;

import dagger.Module;
import dagger.Provides;

/**
 * @Describe
 * @Author Jungle68
 * @Date 2017//13
 * @Contact master.jungle68@gmail.com
 */
@Module
public class VideoFragmentPresenterModule {
    private final VideoHomeCongract.View mView;

    public VideoFragmentPresenterModule(VideoHomeCongract.View view) {
        mView = view;
    }

    @Provides
    VideoHomeCongract.View provideVideoHomeCongractView() {
        return mView;
    }
}
