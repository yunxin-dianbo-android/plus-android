package com.zhiyicx.thinksnsplus.modules.circle.main.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhiyicx.baseproject.utils.glide.GlideManager;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.data.beans.CircleInfo;
import com.zhiyicx.thinksnsplus.data.beans.CirclePostListBean;
import com.zhiyicx.thinksnsplus.data.beans.TopPostListBean;
import com.zhiyicx.thinksnsplus.data.beans.UserInfoBean;
import com.zhiyicx.thinksnsplus.modules.circle.detailv2.adapter.CirclePostListBaseItem;
import com.zhiyicx.thinksnsplus.modules.circle.detailv2.post.CirclePostDetailActivity;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;


/**
 * @Author Jliuer
 * @Date 2017/11/14/13:40
 * @Email Jliuer@aliyun.com
 * @Description
 */
public class CircleHotItem implements ItemViewDelegate<CirclePostListBean> {
    private Context mContext;

    public CircleHotItem(Context context) {
        mContext = context;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_hot_circle_layout;
    }

    @Override
    public boolean isForViewType(CirclePostListBean item, int position) {
//        CircleInfo circleInfo = (CircleInfo) item;
        return true;
    }

    @Override
    public void convert(ViewHolder holder, CirclePostListBean current, CirclePostListBean lastT, int position, int itemCounts) {
        TextView tvUserName = holder.getView(R.id.tv_user_name);

        RelativeLayout rlOnlyOneImgContent = holder.getView(R.id.rl_only_one_img_content);
        RelativeLayout rlImgLineOne = holder.getView(R.id.rl_img_line_one);
        RelativeLayout rlImgLineTwo = holder.getView(R.id.rl_img_line_two);
        RelativeLayout rlImgLineThree = holder.getView(R.id.rl_img_line_three);
        TextView tvCircleContent = holder.getView(R.id.tv_circle_content);
        TextView tvTime = holder.getView(R.id.tv_time);
        TextView tvComeFrom = holder.getView(R.id.tv_come_from);
        TextView tvShareCount = holder.getView(R.id.tv_share_count);
        TextView tvLikeCount = holder.getView(R.id.tv_like_count);
        TextView tvCommentCount = holder.getView(R.id.tv_comment_count);

        ImageView ivPic1 = holder.getView(R.id.iv_pic_1);
        ImageView ivPic2 = holder.getView(R.id.iv_pic_2);
        ImageView ivPic3 = holder.getView(R.id.iv_pic_3);
        ImageView ivPic4 = holder.getView(R.id.iv_pic_4);
        ImageView ivPic5 = holder.getView(R.id.iv_pic_5);
        ImageView ivPic6 = holder.getView(R.id.iv_pic_6);
        ImageView ivPic7 = holder.getView(R.id.iv_pic_7);
        ImageView ivPic8 = holder.getView(R.id.iv_pic_8);
        ImageView ivPic9 = holder.getView(R.id.iv_pic_9);
        ImageView ivVideoBg = holder.getView(R.id.iv_video_bg);
        ivPic1.setVisibility(View.INVISIBLE);
        ivPic2.setVisibility(View.INVISIBLE);
        ivPic3.setVisibility(View.INVISIBLE);
        ivPic4.setVisibility(View.INVISIBLE);
        ivPic5.setVisibility(View.INVISIBLE);
        ivPic6.setVisibility(View.INVISIBLE);
        ivPic7.setVisibility(View.INVISIBLE);
        ivPic8.setVisibility(View.INVISIBLE);
        ivPic9.setVisibility(View.INVISIBLE);
        UserInfoBean userInfoBean = current.getUser();
        tvUserName.setText(userInfoBean == null ? "名字字段还没给" : userInfoBean.getName());
        GlideManager.glideCircle(holder.itemView.getContext(), holder.getView(R.id.iv_user_head), userInfoBean == null || userInfoBean.getAvatar() == null ? "" : userInfoBean.getAvatar().getUrl(), R.mipmap.ic_default_user_head_circle);
        tvTime.setText(current.getCreated_at());
        tvCircleContent.setText(current.getSummary() + "");
        tvShareCount.setText(current.getViews_count() + "");
        tvLikeCount.setText(current.getLikes_count() + "");
        tvCommentCount.setText(current.getComments_count() + "");
        tvComeFrom.setText(current.getGroup().getName() + "");
        if (current.getImages() == null || current.getImages().size() == 0) {
            rlOnlyOneImgContent.setVisibility(View.GONE);
            rlImgLineOne.setVisibility(View.GONE);
            rlImgLineTwo.setVisibility(View.GONE);
            rlImgLineThree.setVisibility(View.GONE);
        } else if (current.getImages().size() == 1) {
            rlOnlyOneImgContent.setVisibility(View.VISIBLE);
            rlImgLineOne.setVisibility(View.GONE);
            rlImgLineTwo.setVisibility(View.GONE);
            GlideManager.glideWrapContentHasDefault(mContext, ivVideoBg, current.getImages().get(0).getRealPicUrl() + "", R.drawable.shape_default_image, 640, 640);
        } else {
            if (current.getImages().size() >= 2 && current.getImages().size() <= 3) {
                rlOnlyOneImgContent.setVisibility(View.GONE);
                rlImgLineOne.setVisibility(View.VISIBLE);
                rlImgLineTwo.setVisibility(View.GONE);
                rlImgLineThree.setVisibility(View.GONE);
            } else if (current.getImages().size() > 3 && current.getImages().size() <= 6) {
                rlOnlyOneImgContent.setVisibility(View.GONE);
                rlImgLineOne.setVisibility(View.VISIBLE);
                rlImgLineTwo.setVisibility(View.VISIBLE);
                rlImgLineThree.setVisibility(View.GONE);
            } else {
                rlOnlyOneImgContent.setVisibility(View.GONE);
                rlImgLineOne.setVisibility(View.VISIBLE);
                rlImgLineTwo.setVisibility(View.VISIBLE);
                rlImgLineThree.setVisibility(View.VISIBLE);
            }
            for (int i = 0; i < current.getImages().size(); i++) {
                if (i == 0) {
                    ivPic1.setVisibility(View.VISIBLE);
                    GlideManager.glide(mContext, ivPic1, current.getImages().get(i).getRealPicUrl());
                } else if (i == 1) {
                    ivPic2.setVisibility(View.VISIBLE);
                    GlideManager.glide(mContext, ivPic2, current.getImages().get(i).getRealPicUrl());
                } else if (i == 2) {
                    ivPic3.setVisibility(View.VISIBLE);
                    GlideManager.glide(mContext, ivPic3, current.getImages().get(i).getRealPicUrl());
                } else if (i == 3) {
                    ivPic4.setVisibility(View.VISIBLE);
                    GlideManager.glide(mContext, ivPic4, current.getImages().get(i).getRealPicUrl());
                } else if (i == 4) {
                    ivPic5.setVisibility(View.VISIBLE);
                    GlideManager.glide(mContext, ivPic5, current.getImages().get(i).getRealPicUrl());
                } else if (i == 5) {
                    ivPic6.setVisibility(View.VISIBLE);
                    GlideManager.glide(mContext, ivPic6, current.getImages().get(i).getRealPicUrl());
                } else if (i == 6) {
                    ivPic7.setVisibility(View.VISIBLE);
                    GlideManager.glide(mContext, ivPic7, current.getImages().get(i).getRealPicUrl());
                } else if (i == 7) {
                    ivPic8.setVisibility(View.VISIBLE);
                    GlideManager.glide(mContext, ivPic8, current.getImages().get(i).getRealPicUrl());
                } else if (i == 8) {
                    ivPic9.setVisibility(View.VISIBLE);
                    GlideManager.glide(mContext, ivPic9, current.getImages().get(i).getRealPicUrl());
                }
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CirclePostDetailActivity.startCirclePostDetailActivity(mContext, current.getGroup_id(), current.getId(), false, true);
            }
        });
    }
}
