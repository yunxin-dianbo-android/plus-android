package com.zhiyicx.thinksnsplus.modules.home.mine.collection;

import com.zhiyicx.baseproject.impl.share.ShareModule;
import com.zhiyicx.common.dagger.scope.FragmentScoped;
import com.zhiyicx.thinksnsplus.base.AppComponent;
import com.zhiyicx.thinksnsplus.base.InjectComponent;
import com.zhiyicx.thinksnsplus.modules.home.mine.VideoRecordActivity;
import com.zhiyicx.thinksnsplus.modules.home.mine.VideoRecordPresenterModule;

import dagger.Component;

/**
 * @Describe
 * @Author Jungle68
 * @Date 2017/2/13
 * @Contact master.jungle68@gmail.com
 */
@FragmentScoped
@Component(dependencies = AppComponent.class, modules = {ShareModule.class, VideoCollectionPresenterModule.class})
public interface VideoCollectionComponent extends InjectComponent<VideoCollectionFragment> {
}
