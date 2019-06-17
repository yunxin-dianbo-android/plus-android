package com.zhiyicx.thinksnsplus.modules.channel;

import com.zhiyicx.common.dagger.scope.FragmentScoped;
import com.zhiyicx.thinksnsplus.base.AppComponent;
import com.zhiyicx.thinksnsplus.base.InjectComponent;
import com.zhiyicx.thinksnsplus.modules.circle.main.CircleMainFragment;
import com.zhiyicx.thinksnsplus.modules.circle.main.CircleMainPresenterModule;

import dagger.Component;

/**
 * @author Jliuer
 * @Date 2017/11/14/11:33
 * @Email Jliuer@aliyun.com
 * @Description
 */
@FragmentScoped
@Component(dependencies = AppComponent.class, modules = ChannelPresenterModule.class)
public interface ChannelPresenterComponent extends InjectComponent<ChannelFragment> {
}
