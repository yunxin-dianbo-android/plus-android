package com.zhiyicx.thinksnsplus.modules.video;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.zhiyicx.baseproject.base.TSActivity;
import com.zhiyicx.thinksnsplus.base.AppApplication;
import com.zhiyicx.thinksnsplus.data.beans.VideoListBean;
import com.zhiyicx.thinksnsplus.modules.dynamic.detail.DynamicDetailActivity;


/**
 * @Describe
 * @Author Jungle68
 * @Date 2016/12/22
 * @Contact master.jungle68@gmail.com
 */

public class VideoDetailActivity extends TSActivity<VideoDetailPresenter, VideoDetailFragment> {

    @Override
    protected boolean useEventBus() {
        return true;
    }


    public static void starVideoDetailActivity(Context context, VideoListBean videoListBean) {
        Intent intent = new Intent(context, VideoDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(VideoListBean.class.getSimpleName(), videoListBean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    @Override
    protected void componentInject() {
        DaggerVideoDetailComponent
                .builder()
                .appComponent(AppApplication.AppComponentHolder.getAppComponent())
                .videoDetailFragmentPresenterModule(new VideoDetailFragmentPresenterModule(mContanierFragment))
                .build().inject(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected VideoDetailFragment getFragment() {
        return VideoDetailFragment.newInstance(getIntent().getExtras());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

}
