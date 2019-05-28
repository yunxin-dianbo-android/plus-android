package com.zhiyicx.thinksnsplus.modules.circle.main.adapter;

import android.widget.TextView;

import com.zhiyicx.baseproject.widget.imageview.FilterImageView;
import com.zhiyicx.thinksnsplus.R;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

public class SuperStarHeadItem implements ItemViewDelegate {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_super_star_head_layout;
    }

    @Override
    public boolean isForViewType(Object item, int position) {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, Object o, Object lastT, int position, int itemCounts) {
        FilterImageView filterImageView = holder.getView(R.id.iv_user_head);
        TextView tvUserName = holder.getView(R.id.tv_user_name);
        tvUserName.setText("您好"+position);
    }
}
