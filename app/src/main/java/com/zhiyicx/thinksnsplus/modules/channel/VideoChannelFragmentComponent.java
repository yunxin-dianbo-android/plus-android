package com.zhiyicx.thinksnsplus.modules.channel;

import com.zhiyicx.baseproject.impl.share.ShareModule;
import com.zhiyicx.common.dagger.scope.FragmentScoped;
import com.zhiyicx.thinksnsplus.base.AppComponent;
import com.zhiyicx.thinksnsplus.base.InjectComponent;
import com.zhiyicx.thinksnsplus.modules.circle.detailv2.post.CirclePostDetailActivity;
import com.zhiyicx.thinksnsplus.modules.circle.detailv2.post.CirclePostDetailPresenterModuleNew;

import dagger.Component;

/**
 * @author Jliuer
 * @Date 2017/12/05/10:53
 * @Email Jliuer@aliyun.com
 * @Description
 */
@FragmentScoped
@Component(dependencies = AppComponent.class, modules = {VideoChannelFragmentPresenterModule.class, ShareModule.class})
public interface VideoChannelFragmentComponent extends InjectComponent<VideoChannelActivity> {

}
