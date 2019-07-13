package com.zhiyicx.thinksnsplus.modules.channel;

import com.zhiyicx.baseproject.base.SystemConfigBean;
import com.zhiyicx.common.dagger.scope.FragmentScoped;
import com.zhiyicx.thinksnsplus.base.AppBasePresenter;
import com.zhiyicx.thinksnsplus.base.BaseSubscribeForV2;
import com.zhiyicx.thinksnsplus.data.beans.DynamicDetailBeanV2;
import com.zhiyicx.thinksnsplus.data.beans.VideoListBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoTagListBean;
import com.zhiyicx.thinksnsplus.data.source.repository.VideoRepository2;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@FragmentScoped
public class VideoChannelFragmentPresenter extends AppBasePresenter<VideoChannelFragmentContract.View> implements VideoChannelFragmentContract.Presenter {

    //
    @Inject
    VideoRepository2 videoRepository2;

    @Inject
    public VideoChannelFragmentPresenter(VideoChannelFragmentContract.View rootView) {
        super(rootView);
    }

    @Override
    public void requestNetData(Long maxId, boolean isLoadMore) {
        //
        String subTagStr = "";
        String firstTagStr = "";
        List<Integer> firstTags = new ArrayList<>();
        List<Integer> subTags = new ArrayList<>();
        if (mRootView.getChoosedVideoTags() == null || mRootView.getChoosedVideoTags().size() == 0) {

        } else {


            for (VideoListBean.TagsBean tagsBean : mRootView.getChoosedVideoTags()) {
                if (tagsBean.isFirst) {
                    firstTags.add(tagsBean.getId());
                } else {
                    subTags.add(tagsBean.getId());
                }
            }
            if (firstTags.size() > 0) {
                firstTagStr = firstTags.toString();
            }
            if (subTags.size() > 0) {
                subTagStr = subTags.toString();
            }
        }
        int starId = 0;
        if (mRootView.getSuperStarBean() != null) {
            starId = mRootView.getSuperStarBean().getId();
        }
        int channelId = 0;
        if (mRootView.getVideoChannelBean() != null) {
            channelId = mRootView.getVideoChannelBean().getId();
        }

        videoRepository2.getVideoList(channelId, 15, maxId.intValue(), "", starId, subTagStr, firstTagStr)
                .subscribe(new BaseSubscribeForV2<List<VideoListBean>>() {
                    @Override
                    protected void onSuccess(List<VideoListBean> data) {
                        mRootView.onNetResponseSuccess(data, isLoadMore);
                    }
                });
    }

    @Override
    public void requestCacheData(Long maxId, boolean isLoadMore) {

    }

    @Override
    public boolean insertOrUpdateData(@NotNull List<VideoListBean> data, boolean isLoadMore) {
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

    @Override
    public void requestAllVideoTags() {
        videoRepository2.getAllVideoTags().subscribe(new BaseSubscribeForV2<List<VideoTagListBean>>() {
            @Override
            protected void onSuccess(List<VideoTagListBean> data) {
                mRootView.onAllVideoTagResponseSuccess(data);
            }
        });
    }
}
