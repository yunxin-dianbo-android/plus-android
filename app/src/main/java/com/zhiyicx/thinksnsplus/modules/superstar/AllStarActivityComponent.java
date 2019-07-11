package com.zhiyicx.thinksnsplus.modules.superstar;

import com.zhiyicx.baseproject.impl.share.ShareModule;
import com.zhiyicx.common.dagger.scope.FragmentScoped;
import com.zhiyicx.thinksnsplus.base.AppComponent;
import com.zhiyicx.thinksnsplus.base.InjectComponent;
import com.zhiyicx.thinksnsplus.modules.video.VideoDetailActivity;
import com.zhiyicx.thinksnsplus.modules.video.VideoDetailFragmentPresenterModule;

import dagger.Component;

/**
 * @Describe
 * @Author Jungle68
 * @Date 2017/2/13
 * @Contact master.jungle68@gmail.com
 */
@FragmentScoped
@Component(dependencies = AppComponent.class, modules = {ShareModule.class, AllStarFragmentPresenterModule.class})
public interface AllStarActivityComponent extends InjectComponent<AllStarActivity> {
}
