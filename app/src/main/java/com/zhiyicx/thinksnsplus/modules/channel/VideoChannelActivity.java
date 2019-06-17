package com.zhiyicx.thinksnsplus.modules.channel;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.zhiyicx.baseproject.base.TSActivity;
import com.zhiyicx.baseproject.impl.share.ShareModule;
import com.zhiyicx.thinksnsplus.base.AppApplication;
import com.zhiyicx.thinksnsplus.modules.circle.detailv2.post.CirclePostDetailPresenterModuleNew;
import com.zhiyicx.thinksnsplus.modules.circle.detailv2.post.DaggerCirclePostDetailComponentNew;


/**
 * @Describe
 * @Author Jungle68
 * @Date 2016/12/22
 * @Contact master.jungle68@gmail.com
 */
//<CirclePostDetailPresenterNew, CirclePostDetailFragmentNew>
public class VideoChannelActivity extends TSActivity<VideoChannelFragmentPresenter, VideoChannelFragment2> {

    @Override
    protected boolean useEventBus() {
        return true;
    }




    @Override
    protected void componentInject() {

        DaggerVideoChannelFragmentComponent.builder()
                .appComponent(AppApplication.AppComponentHolder.getAppComponent())
                .shareModule(new ShareModule(this))
                .videoChannelFragmentPresenterModule(new VideoChannelFragmentPresenterModule(mContanierFragment))
                .build().inject(this);
    }

    @Override
    protected VideoChannelFragment2 getFragment() {
        return VideoChannelFragment2.newInstance(getIntent().getExtras());
    }

    @Override
    protected void initData() {
        super.initData();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

}
