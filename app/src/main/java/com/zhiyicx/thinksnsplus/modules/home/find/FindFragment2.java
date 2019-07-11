package com.zhiyicx.thinksnsplus.modules.home.find;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zhiyicx.baseproject.base.TSFragment;
import com.zhiyicx.baseproject.config.ApiConfig;
import com.zhiyicx.baseproject.utils.CommonUtil;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.base.AppApplication;
import com.zhiyicx.thinksnsplus.config.EventBusTagConfig;
import com.zhiyicx.thinksnsplus.data.beans.VideoChannelBean;
import com.zhiyicx.thinksnsplus.data.source.repository.AuthRepository;
import com.zhiyicx.thinksnsplus.modules.circle.main.CircleHotFragment;
import com.zhiyicx.thinksnsplus.modules.circle.main.CircleMainFragment;
import com.zhiyicx.thinksnsplus.modules.circle.main.adapter.DiscoveryFragmentPagerAdapter;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.DynamicFragment;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.DynamicFragment4Find;
import com.zhiyicx.thinksnsplus.modules.video.VideoHomeFragment;

import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * @Describe 发现页面
 * @Author Jungle68
 * @Date 2017/1/5
 * @Contact master.jungle68@gmail.com
 */
public class FindFragment2 extends TSFragment implements DynamicFragment.OnCommentClickListener {

    @Inject
    AuthRepository mAuthRepository;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.vp_discovery)
    ViewPager vpDiscovery;
    @BindView(R.id.overscroll)
    LinearLayout overscroll;
    Unbinder unbinder;
    List<String> datas = new ArrayList<>();
    List<Fragment> fragments = new ArrayList<>();
//    CircleMainPresenter

    public FindFragment2() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Observable.create(subscriber -> {
            AppApplication.AppComponentHolder.getAppComponent().inject(FindFragment2.this);
            subscriber.onCompleted();
        })
                .subscribeOn(Schedulers.io())
                .subscribe(o -> {
                }, Throwable::printStackTrace);
    }

    public static FindFragment2 newInstance() {
        FindFragment2 fragment = new FindFragment2();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView(View rootView) {

//        mMusic.setVisibility(BuildConfig.USE_MUSIC ? View.VISIBLE : View.GONE);
    }

    @Override
    protected View getContentView() {
        return super.getContentView();
//        if (!showToolbar()) {
//            params.setMargins(0, getstatusbarAndToolbarHeight(), 0, 0);
//        }
    }

    @Override
    protected boolean setUseSatusbar() {
        return true;
    }

    @Override
    protected boolean setUseStatusView() {
        return true;
    }


    @Override
    protected boolean showToolbar() {
        return false;
    }


    @Override
    protected int getToolBarLayoutId() {
        return super.getToolBarLayoutId();
    }

    @Override
    protected void initData() {
//        SystemConfigBean systemConfigBean = null;
//        try {
//            systemConfigBean = SharePreferenceUtils.getObject(getContext().getApplicationContext(), SharePreferenceTagConfig
//                    .SHAREPREFERENCE_TAG_SYSTEM_BOOTSTRAPPERS);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        mQAButton.setVisibility(systemConfigBean != null && !systemConfigBean.getQuestionConfig().isStatus() ? View.GONE : View.VISIBLE);
        datas.clear();
        fragments.clear();
        datas.add("热门");
        datas.add("圈子");
        tablayout.removeAllTabs();
        for (int i = 0; i < datas.size(); i++) {
            tablayout.addTab(tablayout.newTab().setText(datas.get(i)));
        }
        fragments.add(DynamicFragment4Find.newMyInstance(ApiConfig.DYNAMIC_TYPE_FIND, this));
        fragments.add(CircleMainFragment.newInstance());
        DiscoveryFragmentPagerAdapter discoveryFragmentPagerAdapter = new DiscoveryFragmentPagerAdapter(getActivity().getSupportFragmentManager(), fragments, datas);
        vpDiscovery.setAdapter(discoveryFragmentPagerAdapter);
        tablayout.setupWithViewPager(vpDiscovery);
        tablayout.setTabsFromPagerAdapter(discoveryFragmentPagerAdapter);
        CommonUtil.setTabLayoutIndicaterWidth(tablayout);
//        CircleMainFragment
    }

    @Override
    protected int getBodyLayoutId() {
        return R.layout.fragment_find2;
    }

    @Override
    protected int setToolBarBackgroud() {
        return R.color.white;
    }

    @Override
    protected boolean showToolBarDivider() {
        return false;
    }

    @Override
    protected String setCenterTitle() {
        return getString(R.string.find);
    }

    @Override
    protected int setLeftImg() {
        return 0;
    }

    @Override
    protected boolean usePermisson() {
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    public void onButtonMenuShow(boolean isShow) {

    }


    //    @OnClick({R.id.find_info, R.id.find_chanel, R.id.find_active, R.id.find_music, R.id.find_buy,
//            R.id.find_person, R.id.find_nearby, R.id.find_qa, R.id.find_rank, R.id.find_topic})
//    public void onClick(View view) {
//        switch (view.getId()) {
//                /*
//                  资讯
//                 */
//            case R.id.find_info:
//                if (TouristConfig.INFO_LIST_CAN_LOOK || !mAuthRepository.isTourist()) {
//                    startActivity(new Intent(getActivity(), InfoActivity.class));
//
//                } else {
//                    showLoginPop();
//                }
//                break;
//                /*
//                  圈子
//                 */
//            case R.id.find_chanel:
//                if (TouristConfig.CHENNEL_LIST_CAN_LOOK || !mAuthRepository.isTourist()) {
//                    startActivity(new Intent(getActivity(), CircleMainActivity.class));
//                } else {
//                    showLoginPop();
//                }
//                break;
//                /*
//                活动
//                 */
//            case R.id.find_active:
//                break;
//
//            /*
//             *话题
//             */
//            case R.id.find_topic:
//                startActivity(new Intent(getActivity(), TopicActivity.class));
//                break;
//                /*
//                 音乐
//                 */
//            case R.id.find_music:
//                if (TouristConfig.MUSIC_LIST_CAN_LOOK || !mAuthRepository.isTourist()) {
//                    startActivity(new Intent(getActivity(), MusicListActivity.class));
//                } else {
//                    showLoginPop();
//                }
//                break;
//                /*
//                 极铺
//                 */
//            case R.id.find_buy:
//                if (TouristConfig.JIPU_SHOP_CAN_LOOK || !mAuthRepository.isTourist()) {
//                    CustomWEBActivity.startToWEBActivity(getContext(), ApiConfig.URL_JIPU_SHOP);
//                } else {
//                    showLoginPop();
//                }
//                break;
//                /*
//                 找人
//                 */
//            case R.id.find_person:
//
//                Intent itFollow = new Intent(getActivity(), FindSomeOneContainerActivity.class);
//                Bundle bundleFollow = new Bundle();
//                itFollow.putExtras(bundleFollow);
//                startActivity(itFollow);
//                break;
//            case R.id.find_nearby:
//                break;
//                /*
//                 问答
//                 */
//            case R.id.find_qa:
//                if (TouristConfig.MORE_QUESTION_LIST_CAN_LOOK || !mAuthRepository.isTourist()) {
//                    Intent intent = new Intent(getActivity(), QA_Activity.class);
//                    Bundle bundle = new Bundle();
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                } else {
//                    showLoginPop();
//                }
//                break;
//                /*
//                 排行榜
//                 */
//            case R.id.find_rank:
//                startActivity(new Intent(getActivity(), RankIndexActivity.class));
//                break;
//            default:
//                break;
//        }
//    }
    @Override
    protected boolean useEventBus() {
        return true;
    }

    /**
     * 添加或者删除频道
     *
     * @param
     */
    @Subscriber(tag = EventBusTagConfig.EVENT_LOG_IN, mode = ThreadMode.MAIN)
    public void login(boolean isLogin) {
        //刷新数据  重新登录
        initData();
    }


}
