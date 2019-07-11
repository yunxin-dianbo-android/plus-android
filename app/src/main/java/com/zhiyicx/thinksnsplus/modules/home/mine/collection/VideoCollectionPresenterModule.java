package com.zhiyicx.thinksnsplus.modules.home.mine.collection;

import com.zhiyicx.thinksnsplus.modules.home.mine.VideoRecordContract;

import dagger.Module;
import dagger.Provides;

/**
 * @Describe
 * @Author Jungle68
 * @Date 2017//13
 * @Contact master.jungle68@gmail.com
 */
@Module
public class VideoCollectionPresenterModule {
    private final VideoCollectionContract.View mView;

    public VideoCollectionPresenterModule(VideoCollectionContract.View view) {
        mView = view;
    }

    @Provides
    VideoCollectionContract.View provideVideoCollectionContractView() {
        return mView;
    }
}
