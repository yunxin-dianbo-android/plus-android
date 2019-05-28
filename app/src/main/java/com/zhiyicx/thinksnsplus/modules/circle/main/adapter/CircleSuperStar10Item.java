package com.zhiyicx.thinksnsplus.modules.circle.main.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.zhiyicx.common.widget.badgeview.DisplayUtil;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.data.beans.CircleInfo;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Jliuer
 * @Date 2017/11/14/13:40
 * @Email Jliuer@aliyun.com
 * @Description
 */
public class CircleSuperStar10Item implements ItemViewDelegate {
    private Context mContext;

    public CircleSuperStar10Item(Context context) {
        mContext = context;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_circle_hot_star_layout;
    }

    @Override
    public boolean isForViewType(Object item, int position) {
        CircleInfo circleInfo = (CircleInfo) item;
        return circleInfo.getId() != null && circleInfo.getId() == -1;
    }

    @Override
    public void convert(ViewHolder holder, Object o, Object lastT, int position, int itemCounts) {
        RecyclerView rvSuperStarHead = holder.getView(R.id.rv_super_star_head);
        rvSuperStarHead.setLayoutManager(new GridLayoutManager(mContext, 5));
        List<String> datas = new ArrayList<>();
        datas.add("1111");
        datas.add("2222");
        datas.add("3333");
        datas.add("4444");
        datas.add("4444");
        datas.add("4444");
        datas.add("4444");
        datas.add("4444");
        datas.add("4444");
        datas.add("4444");

        CircleHotAdapter circleHotAdapter = new CircleHotAdapter();
        circleHotAdapter.setDatas(datas);
//        MultiItemTypeAdapter multiItemTypeAdapter = new MultiItemTypeAdapter(mContext, datas);
//        SuperStarHeadItem superStarHeadItem = new SuperStarHeadItem();
//        multiItemTypeAdapter.addItemViewDelegate(superStarHeadItem);
        rvSuperStarHead.setAdapter(circleHotAdapter);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) rvSuperStarHead.getLayoutParams();
        if (datas.size() <= 5) {
            layoutParams.height = DisplayUtil.dp2px(mContext, 80);
        } else {
            layoutParams.height = DisplayUtil.dp2px(mContext, 170);
        }
    }


}
