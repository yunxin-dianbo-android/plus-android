package com.zhiyicx.thinksnsplus.modules.superstar.dapter;
import android.support.v7.widget.RecyclerView;

import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.data.beans.SuperStarClassifyBean;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;
/**
 * @Describe
 * @Author Jungle68
 * @Date 2017/3/10
 * @Contact master.jungle68@gmail.com
 */

public class AllStarListItem implements ItemViewDelegate<SuperStarClassifyBean> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_allstar_layout;
    }

    @Override
    public boolean isForViewType(SuperStarClassifyBean item, int position) {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, SuperStarClassifyBean VideoCommentBean, SuperStarClassifyBean lastT, final int position, int itemCounts) {


    }
}
