package com.zhiyicx.thinksnsplus.modules.home.mine;

import android.content.Context;
import android.content.Intent;

import com.zhiyicx.baseproject.base.TSActivity;
import com.zhiyicx.thinksnsplus.base.AppApplication;

public class VideoRecordActivity extends TSActivity<ViewRecordPresenter, VideoRecordFragment> {

    public static void starVideoRecordActivity(Context context) {
        Intent itent = new Intent(context, VideoRecordActivity.class);
        context.startActivity(itent);
    }

    @Override
    protected VideoRecordFragment getFragment() {
        return VideoRecordFragment.newInstance();
    }

    @Override
    protected void componentInject() {
        DaggerVideoRecordComponent
                .builder()
                .appComponent(AppApplication.AppComponentHolder.getAppComponent())
                .videoRecordPresenterModule(new VideoRecordPresenterModule(mContanierFragment))
                .build().inject(this);
//          DaggerVideoRecordComponent.builder().
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
