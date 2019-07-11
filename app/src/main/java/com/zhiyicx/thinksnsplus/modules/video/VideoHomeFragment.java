package com.zhiyicx.thinksnsplus.modules.video;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.base.AppApplication;
import com.zhiyicx.thinksnsplus.base.fordownload.TSListFragmentForDownload;
import com.zhiyicx.thinksnsplus.data.beans.VideoChannelBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoListBean;
import com.zhiyicx.thinksnsplus.data.source.repository.AuthRepository;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListBaseItem4Video;
import com.zhiyicx.thinksnsplus.modules.login.LoginActivity;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;

import java.util.List;

import javax.inject.Inject;

public class VideoHomeFragment extends TSListFragmentForDownload<VideoHomeCongract.Presenter, VideoListBean> implements VideoHomeCongract.View<VideoHomeCongract.Presenter>, MultiItemTypeAdapter.OnItemClickListener {

    VideoChannelBean videoChannelBean;
    @Inject
    VideoHomePresenter videoHomePresenter;
    @Inject
    AuthRepository mIAuthRepository;

    @Override
    protected int getBodyLayoutId() {
        return R.layout.fragment_list_with_input;
    }

    @Override
    protected void initData() {
        super.initData();
        if (videoHomePresenter != null) {
            videoHomePresenter.requestNetData(DEFAULT_PAGE_MAX_ID, false);
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
