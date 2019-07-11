package com.zhiyicx.thinksnsplus.modules.superstar;


import android.content.Context;
import android.content.Intent;

import com.zhiyicx.baseproject.base.TSActivity;
import com.zhiyicx.thinksnsplus.base.AppApplication;

public class AllStarActivity extends TSActivity<AllStarPresenter, AllStarFragment> {
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public static void startAllStarActivity(Context context) {
        Intent itent = new Intent(context, AllStarActivity.class);
        context.startActivity(itent);
    }

    @Override
    protected AllStarFragment getFragment() {
        return AllStarFragment.getInstance();
    }

    @Override
    protected void componentInject() {
        DaggerAllStarActivityComponent
                .builder()
                .appComponent(AppApplication.AppComponentHolder.getAppComponent())
                .allStarFragmentPresenterModule(new AllStarFragmentPresenterModule(mContanierFragment))
                .build()
                .inject(this);
//        DaggerVideoDetailComponent
//                .builder()
//                .appComponent(AppApplication.AppComponentHolder.getAppComponent())
//                .videoDetailFragmentPresenterModule(new VideoDetailFragmentPresenterModule(mContanierFragment))
//                .build().inject(this);
    }
}
