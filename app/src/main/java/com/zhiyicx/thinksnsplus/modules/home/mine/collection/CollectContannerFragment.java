package com.zhiyicx.thinksnsplus.modules.home.mine.collection;

import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;

import com.zhiyicx.baseproject.base.TSFragment;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.DynamicFragment;

import static com.zhiyicx.baseproject.config.ApiConfig.DYNAMIC_TYPE_MY_COLLECTION;


public class CollectContannerFragment extends TSFragment implements View.OnClickListener {

    //    @BindView(R.id.ll_post_tab)
    LinearLayout llPostTab;
    //    @BindView(R.id.ll_video_tab)
    LinearLayout llVideoTab;

    View vStatusBarPlaceholder;


    VideoCollectionFragment videoCollectionFragment;

    DynamicFragment postCollectionFragment;

//    TSFragment currentFragment;

    public static CollectContannerFragment newInstance() {
        CollectContannerFragment collectContannerFragment = new CollectContannerFragment();
        return collectContannerFragment;
    }

    @Override
    protected int getToolBarLayoutId() {
        return R.layout.toolbar_custom_contain_status_bar;
    }

    @Override
    protected boolean showToolBarDivider() {
        return false;
    }

    @Override
    protected boolean showToolbar() {
        return true;
    }

    @Override
    protected boolean setUseStatusView() {
        return false;
    }

    @Override
    protected boolean setUseSatusbar() {
        return true;
    }

    @Override
    protected int getBodyLayoutId() {
        return R.layout.fragment_mine_collect_layout;
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        vStatusBarPlaceholder = rootView.findViewById(R.id.v_status_bar_placeholder);
        llPostTab = rootView.findViewById(R.id.ll_post_tab);
        llVideoTab = rootView.findViewById(R.id.ll_video_tab);
        llPostTab.setOnClickListener(this);
        llVideoTab.setOnClickListener(this);
        initStatusBar(vStatusBarPlaceholder);
    }

    @Override
    protected String setCenterTitle() {
        return "我的收藏";
    }

    @Override
    protected void initData() {
        super.initData();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
//      ActivityUtils.addFragmentToActivity();
        videoCollectionFragment = new VideoCollectionFragment();

        postCollectionFragment = DynamicFragment.newInstance(DYNAMIC_TYPE_MY_COLLECTION, new DynamicFragment.OnCommentClickListener() {
            @Override
            public void onButtonMenuShow(boolean isShow) {

            }
        });
//        fragmentTransaction.add(R.id.fl_fragment_container, videoCollectionFragment);
        fragmentTransaction.add(R.id.fl_fragment_container, videoCollectionFragment).commitAllowingStateLoss();
//        fragmentTransaction.hide(postCollectionFragment).show(videoCollectionFragment).commitAllowingStateLoss();
//        fragmentTransaction.commitAllowingStateLoss();
//        currentFragment = videoCollectionFragment;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_post_tab:
                if (!postCollectionFragment.isAdded()) {
//                    fragmentTransaction.remove(currentFragment);
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fl_fragment_container, postCollectionFragment).commitAllowingStateLoss();
//                    fragmentTransaction.commitAllowingStateLoss();
                llPostTab.setBackgroundColor(getColor(R.color.color_4C4C4E));
                llVideoTab.setBackgroundResource(R.mipmap.ic_bg_collection_menu_checked);
                }
                break;
            case R.id.ll_video_tab:
                if (!videoCollectionFragment.isAdded()) {
//                    fragmentTransaction.remove(currentFragment);
//                    fragmentTransaction.add(R.id.fl_fragment_container, videoCollectionFragment);
//                    fragmentTransaction.commitAllowingStateLoss();
                FragmentTransaction fragmentTransaction2 = getChildFragmentManager().beginTransaction();
//              fragmentTransaction2.hide(postCollectionFragment).show(videoCollectionFragment)/*.commitAllowingStateLoss()*/;
                fragmentTransaction2.replace(R.id.fl_fragment_container, videoCollectionFragment).commitAllowingStateLoss();
                llPostTab.setBackgroundResource(R.mipmap.ic_bg_collection_menu_checked);
                llVideoTab.setBackgroundColor(getColor(R.color.color_4C4C4E));
//                currentFragment = videoCollectionFragment;
              }
                break;
        }
    }
}
