package com.zhiyicx.thinksnsplus.modules.superstar;

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
public class AllStarFragmentPresenterModule {
    private final AllStarContract.View mView;

    public AllStarFragmentPresenterModule(AllStarContract.View view) {
        mView = view;
    }

    @Provides
    AllStarContract.View provideAllStarContractView() {
        return mView;
    }
}
