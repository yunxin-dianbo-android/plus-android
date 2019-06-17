package com.zhiyicx.thinksnsplus.modules.circle.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhiyicx.baseproject.utils.glide.GlideManager;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.data.beans.TopSuperStarBean;
import com.zhiyicx.thinksnsplus.modules.circle.detailv2.v2.CircleDetailActivityV2;

import java.util.List;

public class CircleHotAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;

    public CircleHotAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setDatas(List datas) {
        this.datas = datas;
    }

    private List datas;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_super_star_head_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(contentView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TopSuperStarBean item = (TopSuperStarBean) datas.get(position);
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        GlideManager.glideCircle(myViewHolder.itemView.getContext(), myViewHolder.ivUserHead, item.getAvatar().getUrl(), R.mipmap.ic_default_user_head_circle);
        myViewHolder.tvUserName.setText(item.getName() + "");
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CircleDetailActivityV2.startCircleDetailActivity(mContext, (long)item.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivUserHead;
        TextView tvUserName;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivUserHead = itemView.findViewById(R.id.iv_user_head);
            tvUserName = itemView.findViewById(R.id.tv_user_name);
        }
    }
}
