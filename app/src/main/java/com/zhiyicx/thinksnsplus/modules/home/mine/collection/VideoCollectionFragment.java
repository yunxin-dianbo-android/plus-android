package com.zhiyicx.thinksnsplus.modules.home.mine.collection;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhiyicx.baseproject.base.TSListFragment;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.base.AppApplication;
import com.zhiyicx.thinksnsplus.data.beans.VideoListBean;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListBaseItem4Video;
import com.zhiyicx.thinksnsplus.modules.video.VideoDetailActivity;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import javax.inject.Inject;


public class VideoCollectionFragment extends TSListFragment<VideoCollectionContract.Presenter, VideoListBean> implements MultiItemTypeAdapter.OnItemClickListener, VideoCollectionContract.View<VideoCollectionContract.Presenter> {


    View vStatusBarPlaceholder;
    @Inject
    VideoCollectionPresenter videoCollectionPresenter;


    @Override
    public void setPresenter(VideoCollectionContract.Presenter presenter) {
        super.setPresenter(presenter);
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        MultiItemTypeAdapter adapter = new MultiItemTypeAdapter<>(getContext(), mListDatas);
        DynamicListBaseItem4Video dynamicListBaseItem4Video = new DynamicListBaseItem4Video(getContext());
        adapter.addItemViewDelegate(dynamicListBaseItem4Video);
        adapter.setOnItemClickListener(this);
        return adapter;
    }

    @Override
    protected int getToolBarLayoutId() {
        return R.layout.toolbar_custom_contain_status_bar;
    }

    @Override
    protected boolean setUseSatusbar() {
        return true;
    }

    @Override
    protected boolean setUseStatusView() {
        return false;
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
    protected void initView(View rootView) {
        super.initView(rootView);
        vStatusBarPlaceholder = rootView.findViewById(R.id.v_status_bar_placeholder);
        if (vStatusBarPlaceholder != null) {
            vStatusBarPlaceholder.setVisibility(View.GONE);
        }
    }

    public static VideoCollectionFragment newInstance() {
        VideoCollectionFragment videoCollectionFragment = new VideoCollectionFragment();
        return videoCollectionFragment;
    }

    @Override
    protected void initData() {
//        DaggerTopicDetailListComponent.builder()
//                .appComponent(AppApplication.AppComponentHolder.getAppComponent())
//                .topicDetailListPresenterModule(new TopicDetailListPresenterModule(this))
//                .build()
//                .inject(this);
//        mPresenter = videoCollectionPresenter;
        DaggerVideoCollectionComponent.builder()
                .appComponent(AppApplication.AppComponentHolder.getAppComponent())
                .videoCollectionPresenterModule(new VideoCollectionPresenterModule(this))
                .build().inject(this);
        super.initData();
        mPresenter.requestNetData(0l, false);
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
        VideoDetailActivity.starVideoDetailActivity(getContext(), mListDatas.get(position));
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        return false;
    }
}
