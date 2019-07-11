package com.zhiyicx.thinksnsplus.modules.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.zhiyicx.baseproject.base.TSActivity;
import com.zhiyicx.thinksnsplus.base.AppApplication;
import com.zhiyicx.thinksnsplus.data.beans.VideoListBean;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.DynamicFragment;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.DynamicFragment4MyPostList;
import com.zhiyicx.thinksnsplus.modules.video.DaggerVideoDetailComponent;
import com.zhiyicx.thinksnsplus.modules.video.VideoDetailFragment;
import com.zhiyicx.thinksnsplus.modules.video.VideoDetailFragmentPresenterModule;
import com.zhiyicx.thinksnsplus.modules.video.VideoDetailPresenter;


/**
 * @Describe
 * @Author Jungle68
 * @Date 2016/12/22
 * @Contact master.jungle68@gmail.com
 */

public class MyPostListActivity extends TSActivity {

    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Override
    protected void componentInject() {

    }


    public static void starMyPostListActivityActivity(Context context) {
        Intent intent = new Intent(context, MyPostListActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putParcelable(VideoListBean.class.getSimpleName(), videoListBean);
//        intent.putExtras(bundle);
        context.startActivity(intent);
    }


//    @Override
//    protected void componentInject() {
//        DaggerVideoDetailComponent
//                .builder()
//                .appComponent(AppApplication.AppComponentHolder.getAppComponent())
//                .videoDetailFragmentPresenterModule(new VideoDetailFragmentPresenterModule(mContanierFragment))
//                .build().inject(this);
//    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected DynamicFragment4MyPostList getFragment() {
        return DynamicFragment4MyPostList.newInstance("users", new DynamicFragment.OnCommentClickListener() {
            @Override
            public void onButtonMenuShow(boolean isShow) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

}
