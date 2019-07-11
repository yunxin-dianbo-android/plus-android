package com.zhiyicx.thinksnsplus.modules.channel.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.data.beans.VideoListBean;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;


public class FilterChannelTagAdapter extends CommonAdapter<VideoListBean.TagsBean> {
    public FilterChannelTagAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, VideoListBean.TagsBean tagsBean, int position) {
        TextView tvTagName = holder.getTextView(R.id.tv_tag_name);
//        Object item = getDatas().get(position);
        tvTagName.setText(tagsBean.getName());
        if (tagsBean.isChoosed) {
            tvTagName.setTextColor(Color.parseColor("#EA3378"));
            tvTagName.setBackgroundResource(R.drawable.video_channel_bg);
        } else {
            tvTagName.setTextColor(Color.parseColor("#ffffff"));
            tvTagName.setBackgroundResource(R.drawable.transparent_bg);
        }

    }

}
