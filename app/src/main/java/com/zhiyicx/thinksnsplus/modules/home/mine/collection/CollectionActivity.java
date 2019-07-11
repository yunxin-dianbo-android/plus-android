package com.zhiyicx.thinksnsplus.modules.home.mine.collection;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.zhiyicx.baseproject.base.TSActivity;
import com.zhiyicx.baseproject.base.TSFragment;
import com.zhiyicx.common.utils.StatusBarUtils;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.base.AppApplication;
import com.zhiyicx.thinksnsplus.modules.home.mine.DaggerVideoRecordComponent;
import com.zhiyicx.thinksnsplus.modules.home.mine.VideoRecordFragment;
import com.zhiyicx.thinksnsplus.modules.home.mine.VideoRecordPresenterModule;
import com.zhiyicx.thinksnsplus.modules.home.mine.ViewRecordPresenter;

import butterknife.BindView;

public class CollectionActivity extends TSActivity implements View.OnClickListener {

//    @BindView(R.id.ll_post_tab)
//    LinearLayout llPostTab;
//    @BindView(R.id.ll_video_tab)
//    LinearLayout llVideoTab;
//    @BindView(R.id.root_view)
//    LinearLayout rootView;

    public static void starCollectionActivity(Context context) {
        Intent itent = new Intent(context, CollectionActivity.class);
        context.startActivity(itent);
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
//        StatusBarUtils.transparencyBar(this);
//        rootView.setFitsSystemWindows(false);
//        llPostTab.setOnClickListener(this);
//        llVideoTab.setOnClickListener(this);
    }


//    @Override
//    protected int getLayoutId() {
//        return R.layout.fragment_mine_collect_layout;
//    }

    @Override
    protected TSFragment getFragment() {
        return CollectContannerFragment.newInstance();
//        return VideoCollectionFragment.newInstance();
    }

    @Override
    protected void componentInject() {
//        DaggerVideoRecordComponent
//                .builder()
//                .appComponent(AppApplication.AppComponentHolder.getAppComponent())
//                .videoRecordPresenterModule(new VideoRecordPresenterModule(mContanierFragment))
//                .build().inject(this);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onClick(View view) {

    }
}
