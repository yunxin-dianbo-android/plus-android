package com.zhiyicx.thinksnsplus.modules.search.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhiyicx.thinksnsplus.R;

import java.util.List;


public class SearchHotAdapter extends RecyclerView.Adapter {


    public void setDatas(List<String> datas) {
        this.datas = datas;
    }

    List<String> datas;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_hot_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(contentView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.tvPosition.setText("" + (position + 1));
        if (position < 3) {
            myViewHolder.tvPosition.setTextColor(Color.parseColor("#EA3378"));
        } else {
            myViewHolder.tvPosition.setTextColor(Color.parseColor("#ffffff"));
        }
        myViewHolder.tvSearchHistory.setText(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvPosition;
        TextView tvSearchHistory;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvPosition = itemView.findViewById(R.id.tv_position);
            tvSearchHistory = itemView.findViewById(R.id.tv_search_history);
        }
    }
}
