package com.zhy.adapter.recyclerview;

import android.content.Context;

import java.util.List;

public class MyPostMultiItemTypeAdapter<T> extends MultiItemTypeAdapter<T> {
    public boolean isEditMode() {
        return isEditMode;
    }

    public void setEditMode(boolean editMode) {
        isEditMode = editMode;
    }

    private boolean isEditMode;

    public MyPostMultiItemTypeAdapter(Context context, List datas) {
        super(context, datas);
    }
}
