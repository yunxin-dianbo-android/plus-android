package com.zhiyicx.thinksnsplus.modules.circle.main.adapter;

import android.content.Context;
import android.widget.TextView;

import com.zhiyicx.baseproject.utils.glide.GlideManager;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.data.beans.CirclePostListBean;
import com.zhiyicx.thinksnsplus.data.beans.TopPostListBean;
import com.zhiyicx.thinksnsplus.data.beans.UserInfoBean;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;


/**
 * @Author Jliuer
 * @Date 2017/11/14/13:40
 * @Email Jliuer@aliyun.com
 * @Description
 */
public class CircleHotItem implements ItemViewDelegate<TopPostListBean> {
    private Context mContext;

    public CircleHotItem(Context context) {
        mContext = context;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_hot_circle_layout;
    }

    @Override
    public boolean isForViewType(TopPostListBean item, int position) {
//        CircleInfo circleInfo = (CircleInfo) item;
        return true;
    }

    @Override
    public void convert(ViewHolder holder, TopPostListBean current, TopPostListBean lastT, int position, int itemCounts) {
        TextView tvUserName = holder.getView(R.id.tv_user_name);

        TextView tvCircleContent = holder.getView(R.id.tv_circle_content);
        TextView tvTime = holder.getView(R.id.tv_time);
        TextView tvComeFrom = holder.getView(R.id.tv_come_from);
        TextView tvShareCount = holder.getView(R.id.tv_share_count);
        TextView tvLikeCount = holder.getView(R.id.tv_like_count);
        TextView tvCommentCount = holder.getView(R.id.tv_comment_count);
        UserInfoBean userInfoBean = current.getUser();
        tvUserName.setText(userInfoBean == null ? "名字字段还没给" : userInfoBean.getName());
        GlideManager.glideCircle(holder.itemView.getContext(), holder.getView(R.id.iv_user_head), userInfoBean == null || userInfoBean.getAvatar() == null ? "" : userInfoBean.getAvatar().getUrl(), R.mipmap.ic_default_user_head_circle);
        tvTime.setText(current.getCreated_at());

        CirclePostListBean circlePostListBean = current.getPost();
        if (circlePostListBean != null) {
            tvCircleContent.setText(circlePostListBean.getBody() + "");
            tvShareCount.setText(circlePostListBean.getViews_count() + "");
            tvLikeCount.setText(circlePostListBean.getLikes_count() + "");
            tvCommentCount.setText(circlePostListBean.getComments_count() + "");
            tvComeFrom.setText(circlePostListBean.getGroup_name()+"");
        }


        //        RecyclerView rvSuperStarHead = holder.getView(R.id.rv_super_star_head);
//        List<String> datas = new ArrayList<>();
//        datas.add("1111");
//        datas.add("2222");
//        datas.add("3333");
//        datas.add("4444");
//        datas.add("4444");
//        datas.add("4444");
//        datas.add("4444");
//        datas.add("4444");
//        datas.add("4444");
//        datas.add("4444");
//        datas.add("4444");
//        datas.add("4444");
//        MultiItemTypeAdapter multiItemTypeAdapter = new MultiItemTypeAdapter(mContext, datas);
//        SuperStarHeadItem superStarHeadItem = new SuperStarHeadItem();
//        multiItemTypeAdapter.addItemViewDelegate(superStarHeadItem);
//        rvSuperStarHead.setAdapter(multiItemTypeAdapter);
    }
}
