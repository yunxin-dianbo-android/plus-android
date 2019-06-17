package com.zhiyicx.thinksnsplus.widget.dragview;

import android.widget.BaseAdapter;

/**
 * Created by guolei on 16-3-14.
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 * |        没有神兽，风骚依旧！          |
 * |        QQ:1120832563             |
 * ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
 */

public abstract class BaseDragAdapter extends BaseAdapter {

    public abstract void addItem(Object item);
    public abstract void exchange(int dragPosition,int dropPosition);
    public abstract void removeItem(Object item);
    public abstract void removePosition(int position);
    public abstract void dragEnd();
    public abstract void hidePosition(int position);
    public abstract void showAll();
}
