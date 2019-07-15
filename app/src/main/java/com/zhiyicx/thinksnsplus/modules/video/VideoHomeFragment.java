package com.zhiyicx.thinksnsplus.modules.video;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.base.AppApplication;
import com.zhiyicx.thinksnsplus.base.fordownload.TSListFragmentForDownload;
import com.zhiyicx.thinksnsplus.data.beans.AdListBeanV2;
import com.zhiyicx.thinksnsplus.data.beans.RealAdvertListBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoChannelBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoListBean;
import com.zhiyicx.thinksnsplus.data.source.repository.AuthRepository;
import com.zhiyicx.thinksnsplus.menum.AdType;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.DynamicFragment;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicBannerHeader;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListBaseItem4Video;
import com.zhiyicx.thinksnsplus.modules.login.LoginActivity;
import com.zhiyicx.thinksnsplus.modules.settings.aboutus.CustomWEBActivity;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class VideoHomeFragment extends TSListFragmentForDownload<VideoHomeCongract.Presenter, VideoListBean> implements VideoHomeCongract.View<VideoHomeCongract.Presenter>, MultiItemTypeAdapter.OnItemClickListener {

    VideoChannelBean videoChannelBean;
    @Inject
    VideoHomePresenter videoHomePresenter;
    @Inject
    AuthRepository mIAuthRepository;

    private DynamicBannerHeader mDynamicBannerHeader;

    @Override
    protected int getBodyLayoutId() {
        return R.layout.fragment_list_with_input;
    }

    @Override
    protected void initData() {
        super.initData();
        if (videoHomePresenter != null) {
            videoHomePresenter.requestNetData(DEFAULT_PAGE_MAX_ID, false);
            if (videoChannelBean.getId() == 1) {
                videoHomePresenter.requestAdData(AdType.RECOMMEND_SELECT.getValue());
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mDynamicBannerHeader != null) {
            mDynamicBannerHeader.startBanner();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoChannelBean = getArguments().getParcelable(VideoChannelBean.class.getSimpleName());
        DaggerVideoHomeComponent
                .builder()
                .appComponent(AppApplication.AppComponentHolder.getAppComponent())
                .videoFragmentPresenterModule(new VideoFragmentPresenterModule(VideoHomeFragment.this))
                .build().inject(VideoHomeFragment.this);
    }

    public static VideoHomeFragment newInstance(VideoChannelBean videoChannelBean) {
        VideoHomeFragment fragment = new VideoHomeFragment();
//      fragment.setOnCommentClickListener(l);
        Bundle args = new Bundle();
        args.putParcelable(VideoChannelBean.class.getSimpleName(), videoChannelBean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected MultiItemTypeAdapter getAdapter() {
        MultiItemTypeAdapter adapter = new MultiItemTypeAdapter<>(getContext(), mListDatas);
        DynamicListBaseItem4Video dynamicListBaseItem4Video = new DynamicListBaseItem4Video(getContext());
        dynamicListBaseItem4Video.setVideoChannelBean(videoChannelBean);
        setAdapter(adapter, dynamicListBaseItem4Video);
        adapter.setOnItemClickListener(this);
        return adapter;
    }

    protected void setAdapter(MultiItemTypeAdapter adapter, ItemViewDelegate
            dynamicListBaseItem) {
        adapter.addItemViewDelegate(dynamicListBaseItem);
    }

    /**
     * 获取toolbar的布局文件,如果需要返回自定义的toolbar布局，重写该方法；否则默认返回缺省的toolbar
     */
    @Override
    protected boolean setUseStatusView() {
        return false;
    }


    @Override
    protected boolean setUseSatusbar() {
        return true;
    }

    @Override
    protected boolean showToolbar() {
        return false;
    }

    @Override
    protected boolean showToolBarDivider() {
        return false;
    }

    @Override
    public VideoChannelBean getVideoChannelBean() {
        return videoChannelBean;
    }

    @Override
    public String getSearchKeyWord() {
        return null;
    }


    private void toAdvert(String link, String title) {
        CustomWEBActivity.startToWEBActivity(getActivity(), link, title);
    }

    @Override
    public void onAdDataResSuccessed(final List<AdListBeanV2> listBeanV2s) {
        if (mHeaderAndFooterWrapper != null && listBeanV2s != null && listBeanV2s.size() > 0) {
            List<String> advertTitle = new ArrayList<>();
            List<String> advertUrls = new ArrayList<>();
            List<String> advertLinks = new ArrayList<>();

            for (AdListBeanV2 advert : listBeanV2s) {
                advertTitle.add(advert.getTitle());
                advertUrls.add(advert.getData().getImage());
                advertLinks.add(advert.getData().getLink());
//                 if ("html".equals(advert.getType())) {
//                     showStickyHtmlMessage((String) advert.getData().);
//                 }
            }
            if (advertUrls.isEmpty()) {
                return;
            }
            mDynamicBannerHeader = new DynamicBannerHeader(mActivity);
            mDynamicBannerHeader.setHeadlerClickEvent(new DynamicBannerHeader.DynamicBannerHeadlerClickEvent() {
                @Override
                public void headClick(int position) {
                    toAdvert(listBeanV2s.get(position).getData().getLink(), listBeanV2s.get(position).getTitle());
                }
            });
            DynamicBannerHeader.DynamicBannerHeaderInfo headerInfo = mDynamicBannerHeader.new
                    DynamicBannerHeaderInfo();
            headerInfo.setTitles(advertTitle);
            headerInfo.setLinks(advertLinks);
            headerInfo.setUrls(advertUrls);
            headerInfo.setDelay(4000);
            headerInfo.setOnBannerListener(position -> {

            });
            mDynamicBannerHeader.setHeadInfo(headerInfo);
            mHeaderAndFooterWrapper.addHeaderView(mDynamicBannerHeader.getDynamicBannerHeader());
//             mLinearDecoration.setHeaderCount(mHeaderAndFooterWrapper.getHeadersCount());
//             mLinearDecoration.setFooterCount(mHeaderAndFooterWrapper.getFootersCount());

        }
    }

    @Override
    public void onNetResponseSuccess(List<VideoListBean> data, boolean isLoadMore) {
        super.onNetResponseSuccess(data, isLoadMore);
    }

    @Override
    public void onCacheResponseSuccess(List<VideoListBean> data, boolean isLoadMore) {
        super.onCacheResponseSuccess(data, isLoadMore);
    }

    @Override
    public void refreshData(List datas) {
        if (mHeaderAndFooterWrapper != null) {
            setEmptyViewVisiable(mListDatas.isEmpty() && mHeaderAndFooterWrapper.getHeadersCount() <= 0);
            try {
                mHeaderAndFooterWrapper.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
        if (mIAuthRepository.isLogin()) {
            if (mListDatas.get(position).getVideo() != null) {
                VideoDetailActivity.starVideoDetailActivity(getContext(), mListDatas.get(position).getVideo());
            } else {
                VideoDetailActivity.starVideoDetailActivity(getContext(), mListDatas.get(position));
            }
        } else {
            startActivity(new Intent(mActivity, LoginActivity.class));
        }
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        return false;
    }

    @Override
    protected float getItemDecorationSpacing() {
        return 0f;
    }

    /**
     * @return 是否开启 游客数据限制
     */
    @Override
    protected boolean isUseTouristLoadLimit() {
        return false;
    }
}
