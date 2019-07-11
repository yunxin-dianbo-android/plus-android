package com.zhiyicx.thinksnsplus.modules.home.mine.adapter;

import android.view.View;

import com.zhiyi.emoji.ViewUtils;
import com.zhiyicx.baseproject.utils.glide.GlideManager;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.data.beans.VideoListBean;
import com.zhiyicx.thinksnsplus.utils.MyUtils;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * @Describe
 * @Author Jungle68
 * @Date 2017/3/10
 * @Contact master.jungle68@gmail.com
 */

public class VideoRecordItem implements ItemViewDelegate<VideoListBean> {


    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_video_record_layout;
    }

    @Override
    public boolean isForViewType(VideoListBean videoListBean, int position) {
        return true;
    }

    @Override
    public void convert(ViewHolder holder, VideoListBean videoListBean, VideoListBean lastT, final int position, int itemCounts) {
        holder.getView(R.id.rl_7_day_parent).setVisibility(View.GONE);
        holder.getView(R.id.v_line_divider).setVisibility(View.GONE);
        if (position == 0 && videoListBean.isSeven_time()) {
            holder.getView(R.id.rl_7_day_parent).setVisibility(View.VISIBLE);
            holder.getTextView(R.id.tv_7_day).setText("7天内");
        } else if (lastT != null && lastT.isSeven_time() && !videoListBean.isSeven_time()) {
            holder.getView(R.id.rl_7_day_parent).setVisibility(View.VISIBLE);
            holder.getTextView(R.id.tv_7_day).setText("更早");
            holder.getView(R.id.v_line_divider).setVisibility(View.VISIBLE);
        }
        holder.getTextView(R.id.tv_video_name).setText(videoListBean.getVideo().getName() + "");
        String url = MyUtils.getImagePath(videoListBean.getVideo().getCover(), ViewUtils.dip2px(holder.getmContext(), 90));
        GlideManager.glide(holder.getmContext(), holder.getImageViwe(R.id.iv_video_bg), url);
        if (videoListBean != null) {
            String timeFormat = MyUtils.timeFormat(videoListBean.getProgress().intValue() * 1000);

            holder.getTextView(R.id.tv_video_duration).setText("观看至 " + timeFormat);
            holder.getTextView(R.id.tv_video_duration).setVisibility(View.VISIBLE);
        } else {
            holder.getTextView(R.id.tv_video_duration).setText("观看至 " + "00:00");
        }
        if(videoListBean.isEditMode){
            holder.getView(R.id.rl_iv_checked_parent).setVisibility(View.VISIBLE);
            holder.getView(R.id.ll_watch_tip).setVisibility(View.GONE);
        }else{
            holder.getView(R.id.rl_iv_checked_parent).setVisibility(View.GONE);
            holder.getView(R.id.ll_watch_tip).setVisibility(View.VISIBLE);
        }
        if(videoListBean.isChecked){
            holder.getImageViwe(R.id.iv_checked_status).setImageResource(R.mipmap.ic_video_record_checked);
        }else{
            holder.getImageViwe(R.id.iv_checked_status).setImageResource(R.mipmap.ic_video_record_unchecked);
        }
    }

}
