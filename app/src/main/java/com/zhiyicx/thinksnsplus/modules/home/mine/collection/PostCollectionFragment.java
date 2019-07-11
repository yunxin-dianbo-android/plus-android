package com.zhiyicx.thinksnsplus.modules.home.mine.collection;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhiyicx.baseproject.base.TSListFragment;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

public class PostCollectionFragment extends TSListFragment/*<PostCollectionContract.Presenter,DynamicDetailBeanV2>*/ implements MultiItemTypeAdapter.OnItemClickListener {
    @Override
    protected RecyclerView.Adapter getAdapter() {

        return null;
    }


    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        return false;
    }
}
