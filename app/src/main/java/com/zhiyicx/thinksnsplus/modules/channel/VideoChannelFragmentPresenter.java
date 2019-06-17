package com.zhiyicx.thinksnsplus.modules.channel;

import com.zhiyicx.baseproject.base.SystemConfigBean;
import com.zhiyicx.common.dagger.scope.FragmentScoped;
import com.zhiyicx.thinksnsplus.base.AppBasePresenter;
import com.zhiyicx.thinksnsplus.data.beans.DynamicDetailBeanV2;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@FragmentScoped
public class VideoChannelFragmentPresenter extends AppBasePresenter<VideoChannelFragmentContract.View> implements VideoChannelFragmentContract.Presenter{

    @Inject
    public VideoChannelFragmentPresenter(VideoChannelFragmentContract.View rootView) {
        super(rootView);
    }

    @Override
    public void requestNetData(Long maxId, boolean isLoadMore) {
        List<DynamicDetailBeanV2> temps = new ArrayList<>();
        temps.add(new DynamicDetailBeanV2());
        temps.add(new DynamicDetailBeanV2());
        temps.add(new DynamicDetailBeanV2());
        temps.add(new DynamicDetailBeanV2());
        temps.add(new DynamicDetailBeanV2());
        temps.add(new DynamicDetailBeanV2());
        temps.add(new DynamicDetailBeanV2());
        temps.add(new DynamicDetailBeanV2());
        temps.add(new DynamicDetailBeanV2());
        temps.add(new DynamicDetailBeanV2());
        temps.add(new DynamicDetailBeanV2());
        temps.add(new DynamicDetailBeanV2());
        mRootView.onNetResponseSuccess(temps,true);

    }

    @Override
    public void requestCacheData(Long maxId, boolean isLoadMore) {

    }

    @Override
    public boolean insertOrUpdateData(@NotNull List<DynamicDetailBeanV2> data, boolean isLoadMore) {
        return false;
    }

    @Override
    public boolean isTourist() {
        return false;
    }

    @Override
    public boolean isLogin() {
        return false;
    }

    @Override
    public boolean usePayPassword() {
        return false;
    }

    @Override
    public boolean handleTouristControl() {
        return false;
    }

    @Override
    public SystemConfigBean getSystemConfigBean() {
        return null;
    }

    @Override
    public String getGoldName() {
        return null;
    }

    @Override
    public String getGoldUnit() {
        return null;
    }

    @Override
    public int getRatio() {
        return 0;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public boolean userHasPassword() {
        return false;
    }
}
