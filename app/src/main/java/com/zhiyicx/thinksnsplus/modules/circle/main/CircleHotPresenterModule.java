package com.zhiyicx.thinksnsplus.modules.circle.main;

import dagger.Module;
import dagger.Provides;

/**
 * @author Jliuer
 * @Date 2017/11/14/11:37
 * @Email Jliuer@aliyun.com
 * @Description
 */
@Module
public class CircleHotPresenterModule {
    CircleHotContract.View mView;

    public CircleHotPresenterModule(CircleHotContract.View view) {
        mView = view;
    }

    @Provides
    CircleHotContract.View providesCircleMainContractView() {
        return mView;
    }

}
