package com.zhiyicx.thinksnsplus.modules.home.main;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.zhiyicx.baseproject.base.ITSListView;
import com.zhiyicx.baseproject.base.TSViewPagerFragment;
import com.zhiyicx.baseproject.config.ApiConfig;
import com.zhiyicx.baseproject.config.TouristConfig;
import com.zhiyicx.common.utils.DeviceUtils;
import com.zhiyicx.common.utils.SharePreferenceUtils;
import com.zhiyicx.common.utils.StatusBarUtils;
import com.zhiyicx.common.utils.ToastUtils;
import com.zhiyicx.common.utils.gson.JsonUtil;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.base.AppApplication;
import com.zhiyicx.thinksnsplus.base.BaseSubscribeForV2;
import com.zhiyicx.thinksnsplus.config.EventBusTagConfig;
import com.zhiyicx.thinksnsplus.data.beans.DeleteOrAddVideoChannelResInfo;
import com.zhiyicx.thinksnsplus.data.beans.VideoChannelBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoChannelListBean;
import com.zhiyicx.thinksnsplus.data.source.local.DynamicBeanGreenDaoImpl;
import com.zhiyicx.thinksnsplus.data.source.repository.AuthRepository;
import com.zhiyicx.thinksnsplus.modules.channel.ChannelManagerActivity;
import com.zhiyicx.thinksnsplus.modules.channel.VideoChannelActivity;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.DynamicContract;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.DynamicFragment;
import com.zhiyicx.thinksnsplus.modules.search.container.SearchContainerActivity;
import com.zhiyicx.thinksnsplus.modules.shortvideo.helper.ZhiyiVideoView;
import com.zhiyicx.thinksnsplus.modules.superstar.AllStarActivity;
import com.zhiyicx.thinksnsplus.modules.video.VideoHomeCongract;
import com.zhiyicx.thinksnsplus.modules.video.VideoHomeFragment;

import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import cn.jzvd.JZVideoPlayerManager;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.zhiyicx.common.config.ConstantConfig.JITTER_SPACING_TIME;

/**
 * @Describe 主页 MainFragment
 * @Author Jungle68
 * @Date 2017/1/5
 * @Contact master.jungle68@gmail.com
 */
public class MainFragment extends TSViewPagerFragment implements DynamicFragment.OnCommentClickListener {
    public static final int PAGER_NEW_DYNAMIC_LIST_POSITION = 0;
    @BindView(R.id.v_status_bar_placeholder)
    View mStatusBarPlaceholder;
    @BindView(R.id.rl_search_and_classify_content)
    RelativeLayout llSearchAndClassifyContent;
    @BindView(R.id.ll_search_parent)
    LinearLayout llSearchParent;
    @BindView(R.id.tv_video_classification)
    TextView tvVideoClassification;
    @BindView(R.id.tv_video_classification_2)
    TextView tvVideoClassification2;
    @BindView(R.id.tv_all_video_classification)
    TextView tvAllVideoClassification;
    @BindView(R.id.ll_channel_parent)
    LinearLayout llChannelParent;

    @Inject
    AuthRepository mIAuthRepository;

    //    @Inject
//    VideoChannelRepository videoChannelRepository;
    @Inject
    DynamicBeanGreenDaoImpl mDynamicBeanGreenDao;

    VideoChannelBean currentVideoChannelBean;

    public void setOnImageClickListener(DynamicFragment.OnCommentClickListener onCommentClickListener) {
        mOnCommentClickListener = onCommentClickListener;
    }

    DynamicFragment.OnCommentClickListener mOnCommentClickListener;

    public static MainFragment newInstance(DynamicFragment.OnCommentClickListener l) {
        MainFragment fragment = new MainFragment();
        fragment.setOnImageClickListener(l);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getBodyLayoutId() {
        return R.layout.fragment_main_viewpager;
    }

    @Override
    protected boolean setUseSatusbar() {
        return false;
    }

    @Override
    protected boolean showToolbar() {
        return false;
    }

    @Override
    protected boolean setUseStatusView() {
        return false;
    }

    @Override
    protected int getOffsetPage() {
        return 0;
    }

    @Override
    protected void initView(View rootView) {
        // 需要在 initview 之前，应为在 initview 中使用了 dagger 注入的数据
        AppApplication.AppComponentHolder.getAppComponent().inject(this);
        super.initView(rootView);
        initToolBar();
    }

    private void initToolBar() {
        // toolBar设置状态栏高度的marginTop
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, DeviceUtils
                .getStatuBarHeight(getContext()));
        mStatusBarPlaceholder.setLayoutParams(layoutParams);
        // 适配非6.0以上、非魅族系统、非小米系统状态栏
        if (getActivity() != null && StatusBarUtils.intgetType(getActivity().getWindow()) == 0) {
            mStatusBarPlaceholder.setBackgroundResource(R.color.themeColor);
        }
        //不需要返回键
//        mTsvToolbar.setLeftImg(0);
        mTsvToolbar.setLeftImg(R.mipmap.app_icon);
        // TODO: 2019/7/2 这一期先去掉
//        mTsvToolbar.setRightImg(R.mipmap.ic_home_show_more_channel, R.color.transparent);
//        mTsvToolbar.setRightClickListener(this, () -> startActivity(new Intent(mActivity, ChannelManagerActivity.class)));
        llSearchAndClassifyContent.setVisibility(View.VISIBLE);
        RxView.clicks(llSearchParent)
                .throttleFirst(JITTER_SPACING_TIME, TimeUnit.SECONDS)
                .compose(this.<Void>bindToLifecycle())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        startActivity(new Intent(mActivity, SearchContainerActivity.class));
                    }
                });
//        RxView.clicks(tvVideoClassification)
//                .throttleFirst(JITTER_SPACING_TIME, TimeUnit.SECONDS)
//                .compose(this.<Void>bindToLifecycle())
//                .subscribe(new Action1<Void>() {
//                    @Override
//                    public void call(Void aVoid) {
//                        // TODO: 2019/5/3 分类页面
//                        startActivity(new Intent(mActivity, VideoChannelActivity.class));
//                    }
//                });
//        RxView.clicks(tvVideoClassification2)
//                .throttleFirst(JITTER_SPACING_TIME, TimeUnit.SECONDS)
//                .compose(this.<Void>bindToLifecycle())
//                .subscribe(new Action1<Void>() {
//                    @Override
//                    public void call(Void aVoid) {
//                        // TODO: 2019/5/3 分类页面
//                        startActivity(new Intent(mActivity, VideoChannelActivity.class));
//                    }
//                });
//        RxView.clicks(tvAllVideoClassification)
//                .throttleFirst(JITTER_SPACING_TIME, TimeUnit.SECONDS)
//                .compose(this.<Void>bindToLifecycle())
//                .subscribe(new Action1<Void>() {
//                    @Override
//                    public void call(Void aVoid) {
//                        // TODO: 2019/5/3 分类页面
//                        startActivity(new Intent(mActivity, VideoChannelActivity.class));
//                    }
//                });

        RxView.clicks(llChannelParent)
                .throttleFirst(JITTER_SPACING_TIME, TimeUnit.SECONDS)
                .compose(this.<Void>bindToLifecycle())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        // TODO: 2019/5/3 分类页面
                        if (currentVideoChannelBean != null && currentVideoChannelBean.getId() == 3) {
                            AllStarActivity.startAllStarActivity(getContext());
                        } else {
//                            startActivity(new Intent(mActivity, VideoChannelActivity.class));
                            VideoChannelActivity.starVideoChannelActivity(mActivity, null);
                        }
                    }
                });

//        RxView.clicks();
    }

    @Override
    protected View getRightViewOfMusicWindow() {
        return mTsvToolbar.getRightTextView();
    }

    @Override
    protected void initData() {
//        if (videoChannelRepository != null) {
//            videoChannelRepository.getMyVideoChannel().observeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new BaseSubscribeForV2<List<VideoChannelBean>>() {
//                        @Override
//                        protected void onSuccess(List<VideoChannelBean> data) {
//                            //频道
////                        mView.onNetResponseSuccess(data);
//                        }
//
//                        @Override
//                        protected void onFailure(String message, int code) {
////                        mView.showMsg(message);
//                            ToastUtils.showToast(message);
//                        }
//
//                        @Override
//                        protected void onException(Throwable throwable) {
//                            ToastUtils.showToast("频道信息获取失败");
//                        }
//                    });
//        }
        mVpFragment.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // 停掉当前播放
                if (JZVideoPlayerManager.getCurrentJzvd() != null) {
                    if (JZVideoPlayerManager.getCurrentJzvd().currentState == ZhiyiVideoView.CURRENT_STATE_PREPARING
                            || JZVideoPlayerManager.getCurrentJzvd().currentState == ZhiyiVideoView.CURRENT_STATE_PREPARING_CHANGING_URL) {
                        ZhiyiVideoView.releaseAllVideos();
                    }
                }
                ZhiyiVideoView.goOnPlayOnPause();
                // 游客处理
//                if (!TouristConfig.FOLLOW_CAN_LOOK && position == mVpFragment.getChildCount() - 1 && !mIAuthRepository.isLogin()) {
//                    showLoginPop();
//                    // 转回热门
//                    mVpFragment.setCurrentItem(0);
//                }
                currentVideoChannelBean = AppApplication.videoChannelListBeans.get(position);
                setChannelInfo();
            }


            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
//                    ((DynamicContract.View) mFragmentList.get(mVpFragment.getCurrentItem())).closeInputView();
//                    ((VideoHomeCongract.View) mFragmentList.get(mVpFragment.getCurrentItem())).closeInputView();
                }
            }
        });

        // 启动 app，如果本地没有最新数据，应跳到“热门”页面 关联 github  #113  #366
        try {
//            if (mDynamicBeanGreenDao.getNewestDynamicList(System.currentTimeMillis()).size() == 0) {
            mVpFragment.setCurrentItem(0);
//            }
        } catch (SQLiteException ignored) {
        }

    }

    private void setChannelInfo() {
        if (currentVideoChannelBean != null && currentVideoChannelBean.getId() == 3) {
            tvVideoClassification.setText("明星1");
            tvVideoClassification2.setText("明星2");
        } else {
            tvVideoClassification.setText("频道1");
            tvVideoClassification2.setText("频道2");
        }
    }

    @Override
    protected List<String> initTitles() {

        List<String> title = new ArrayList<>();
        for (VideoChannelBean videoChannelBean : AppApplication.videoChannelListBeans) {
            title.add(videoChannelBean.getName() + "");
        }
        return title;
//        return Arrays.asList(getString(R.string.the_last)
//                , getString(R.string.hot)
//                , getString(R.string.follow), getString(R.string.follow), getString(R.string.follow),
//                getString(R.string.follow), getString(R.string.follow), getString(R.string.follow));
    }

    @Override
    protected boolean setNeedShadowViewClick() {
        return false;
    }

    @Override
    protected List<Fragment> initFragments() {
        if (mFragmentList == null) {
            mFragmentList = new ArrayList();
//            mFragmentList.add(DynamicFragment.newInstance(ApiConfig.DYNAMIC_TYPE_NEW, this));
            if (AppApplication.videoChannelListBeans == null) {
                String json = SharePreferenceUtils.getString(getContext(), VideoChannelBean.class.getSimpleName());
                AppApplication.videoChannelListBeans =  JsonUtil.parseToList(json,VideoChannelBean.class);
            }

            if (AppApplication.videoChannelListBeans == null) {
                AppApplication.videoChannelListBeans = new ArrayList<>();
                VideoChannelBean item1 = new VideoChannelBean();
                item1.setId(1);
                item1.setName("精选");
                VideoChannelBean item2 = new VideoChannelBean();
                item2.setId(2);
                item2.setName("短视频");
                AppApplication.videoChannelListBeans.add(item1);
                AppApplication.videoChannelListBeans.add(item2);
            }
            currentVideoChannelBean = AppApplication.videoChannelListBeans.get(0);
            setChannelInfo();
            for (int i = 0; i < AppApplication.videoChannelListBeans.size(); i++) {
                mFragmentList.add(VideoHomeFragment.newInstance(AppApplication.videoChannelListBeans.get(i)));
            }
//            mFragmentList.add(DynamicFragment.newInstance(ApiConfig.DYNAMIC_TYPE_HOTS, this));
//            mFragmentList.add(DynamicFragment.newInstance(ApiConfig.DYNAMIC_TYPE_HOTS, this));
//            mFragmentList.add(DynamicFragment.newInstance(ApiConfig.DYNAMIC_TYPE_HOTS, this));
//            mFragmentList.add(DynamicFragment.newInstance(ApiConfig.DYNAMIC_TYPE_HOTS, this));
//            mFragmentList.add(DynamicFragment.newInstance(ApiConfig.DYNAMIC_TYPE_HOTS, this));
//            mFragmentList.add(DynamicFragment.newInstance(ApiConfig.DYNAMIC_TYPE_HOTS, this));
//            mFragmentList.add(DynamicFragment.newInstance(ApiConfig.DYNAMIC_TYPE_HOTS, this));
            // 游客处理
//            if (TouristConfig.FOLLOW_CAN_LOOK || mIAuthRepository.isLogin()) {
//                mFragmentList.add(DynamicFragment.newInstance(ApiConfig.DYNAMIC_TYPE_FOLLOWS, this));
//            } else {
            // 用于viewpager 占位
//                mFragmentList.add(DynamicFragment.newInstance(ApiConfig.DYNAMIC_TYPE_EMPTY, this));
//            }
        }
        return mFragmentList;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        try {
            ((DynamicContract.View) mFragmentList.get(mVpFragment.getCurrentItem())).hiddenChanged(isVisibleToUser);
        } catch (Exception ignore) {
        }
    }

    public boolean backPressed() {
        return ((VideoHomeCongract.View) mFragmentList.get(mVpFragment.getCurrentItem())).backPressed();
    }

    @Override
    public void onButtonMenuShow(boolean isShow) {
//        mVShadow.setVisibility(isShow ? View.GONE : View.VISIBLE);
//        if (mOnCommentClickListener != null) {
//            mOnCommentClickListener.onButtonMenuShow(isShow);
//        }
    }

    /**
     * viewpager页面切换公开方法
     */
    public void setPagerSelection(int position) {
        mVpFragment.setCurrentItem(position, true);
    }

    /**
     * 刷新当前页
     */
    public void refreshCurrentPage() {
        ((ITSListView) mFragmentList.get(mVpFragment.getCurrentItem())).startRefrsh();
    }

    @Override
    protected boolean useEventBus() {
        return true;
    }



    /**
     * 切换频道
     *
     * @param
     */
    @Subscriber(tag = EventBusTagConfig.MAIN_FRAGMENT_CHANG_CHANNEL, mode = ThreadMode.MAIN)
    public void changChannel(VideoChannelBean videoChannelBean) {
        if (videoChannelBean != null) {
            for (int i = 0; i < AppApplication.videoChannelListBeans.size(); i++) {
                if (AppApplication.videoChannelListBeans.get(i).getId() == videoChannelBean.getId()) {
                    mVpFragment.setCurrentItem(i);
                    break;
                }
            }
        }
    }

    /**
     * 添加或者删除频道
     *
     * @param
     */
    @Subscriber(tag = EventBusTagConfig.MAIN_FRAGMENT_ADD_DELETE__CHANNEL, mode = ThreadMode.MAIN)
    public void addOrDeleteChannel(List<VideoChannelBean> videoChannelBeans) {
//        if (mFragmentList == null) {
        mFragmentList.clear();
        int currentPosition = 0;
        for (int i = 0; i < AppApplication.videoChannelListBeans.size(); i++) {
            if (AppApplication.videoChannelListBeans.get(i).getId() == currentVideoChannelBean.getId()) {
                currentPosition = i;
            }
            mFragmentList.add(VideoHomeFragment.newInstance(AppApplication.videoChannelListBeans.get(i)));
        }
        tsViewPagerAdapter.bindData(mFragmentList);
        mTsvToolbar.initTabView(mVpFragment, initTitles());
        currentVideoChannelBean = AppApplication.videoChannelListBeans.get(currentPosition);
        mVpFragment.setCurrentItem(currentPosition);
        setChannelInfo();
    }
//    }
}
