package com.zhiyicx.thinksnsplus.widget.dragview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhiyicx.baseproject.R;
import com.zhiyicx.thinksnsplus.data.beans.VideoChannelBean;
import com.zhiyicx.thinksnsplus.widget.dragview.listener.OnDragItemClickLisnter;

import java.util.List;

//import android.content.SharedPreferences;

/**
 * Created by guolei on 16-3-14.
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 * |        没有神兽，风骚依旧！          |
 * |        QQ:1120832563             |
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 */

public class DragAdapter2 extends BaseDragAdapter {

    private static final String TAG = "DragAdapter";

    private Context context;
    private int dropPosition = -1;

    private List<VideoChannelBean> provinceList;
    private boolean isModifyMode = false;
    private boolean isModify4Delete = true;


    public void setOnDragItemClickLisnter(OnDragItemClickLisnter onDragItemClickLisnter) {
        this.onDragItemClickLisnter = onDragItemClickLisnter;
    }

    OnDragItemClickLisnter onDragItemClickLisnter;

    public boolean isModifyMode() {
        return isModifyMode;
    }

    public void setModifyMode(boolean isModifyMode, boolean isModify4Delete) {
        this.isModifyMode = isModifyMode;
        this.isModify4Delete = isModify4Delete;
    }

    //    private SharedPreferences mShared;
//    private SharedPreferences.Editor mEditor;
    private Object selectItem;


    //    public final static String SP_KEY_4_VIDEO_TAGS ="VIDEO_TAGS";
    private int mHidePosition = -1;

    public DragAdapter2(Context context, List<VideoChannelBean> provinceList) {
        this.context = context;
        this.provinceList = provinceList;
        if (provinceList != null && provinceList.size() > 0) {
            selectItem = provinceList.get(0);
        }
    }

    @Override
    public int getCount() {
        return provinceList == null ? 0 : provinceList.size();
    }

    @Override
    public Object getItem(int position) {
        if (null != provinceList && provinceList.size() != 0) {
            return provinceList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO: 16-3-26 控件的ｂｕｇ 不能使用convertView and holder
//        if(convertView == null)
        convertView = LayoutInflater.from(context).inflate(R.layout.item_video_tag, parent, false);
        TextView textView = convertView.findViewById(R.id.tv_tag_name);
        ImageView ivAddOrDeleteVideoChannel = convertView.findViewById(R.id.iv_add_or_delete_video_channel);
        final VideoChannelBean item = provinceList.get(position);
        textView.setText(item.getName() + "");
        if (isModifyMode) {
            ivAddOrDeleteVideoChannel.setVisibility(View.VISIBLE);
            if (isModify4Delete) {
                ivAddOrDeleteVideoChannel.setImageResource(R.mipmap.ic_delete_video_channel);
            } else {
                ivAddOrDeleteVideoChannel.setImageResource(R.mipmap.ic_add_video_channel);
            }
        } else {
            ivAddOrDeleteVideoChannel.setVisibility(View.GONE);
        }

        if (dropPosition == position) {
            convertView.setVisibility(View.GONE);
        }
//        if (selectItem.toString().equals(provinceList.get(position).toString())) {
//            textView.setBackgroundResource(R.drawable.video_channel_fixed_bg);
//        } else {
        textView.setBackgroundResource(R.drawable.video_channel_bg);
//        }
//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (onDragItemClickLisnter != null) {
//                    onDragItemClickLisnter.onItemClickListener(item, position);
//                }
//            }
//        });
        return convertView;

    }

    @Override
    public void addItem(Object item) {
        provinceList.add((VideoChannelBean) item);
        notifyDataSetChanged();
    }

    @Override
    public void exchange(int dragPosition, int dropPosition) {
        // TODO: 16-3-22 互换位置
        this.dropPosition = dropPosition;
        VideoChannelBean dragItem = (VideoChannelBean) getItem(dragPosition);
        if (dragPosition < dropPosition) {
            provinceList.add(dropPosition + 1, dragItem);
            provinceList.remove(dragPosition);
        } else {
            provinceList.add(dropPosition, dragItem);
            provinceList.remove(dragPosition + 1);
        }
        this.mHidePosition = dropPosition;
//        mEditor.putString(SP_KEY_4_VIDEO_TAGS, ListToJson.toJson(provinceList).toString());
//        mEditor.commit();
        notifyDataSetChanged();
    }

    @Override
    public void removeItem(Object item) {
        if (provinceList.contains((Object) item)) {
            provinceList.remove((Object) item);
            dropPosition = -1;
            notifyDataSetChanged();
        }
    }

    @Override
    public void removePosition(int position) {
        if (position >= 0 && position < provinceList.size()) {
            provinceList.remove(position);
//            mEditor.putString("", ListToJson.toJson(provinceList).toString());
//            mEditor.commit();
            notifyDataSetChanged();
        }
    }

    @Override
    public void dragEnd() {
        // TODO: 16-3-26 拖动完成的回调
        int position = 0;
        for (int i = 0; i < provinceList.size(); i++) {
            if (selectItem.toString().equals(provinceList.get(i).toString())) {
                position = i;
                break;
            }
        }

        this.dropPosition = -1;
        if (null != listener) {
            listener.exchangeOtherAdapter(provinceList, position);
        }
        this.mHidePosition = -1;
        notifyDataSetChanged();
    }

    private changeListener listener;

    public void setListener(changeListener listener) {
        this.listener = listener;
    }

    public interface changeListener {

        public void exchangeOtherAdapter(List<VideoChannelBean> data, int position);

        public void setCurrentPosition();
    }

    private class ViewHolder {
        private View view;
        private TextView textView;
    }

    @Override
    public void hidePosition(int position) {
        this.mHidePosition = position;
        notifyDataSetChanged();
    }

    @Override
    public void showAll() {
        this.mHidePosition = -1;
        notifyDataSetChanged();
    }
}
