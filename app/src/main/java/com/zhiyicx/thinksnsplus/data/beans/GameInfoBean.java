package com.zhiyicx.thinksnsplus.data.beans;

import android.os.Parcel;

import com.zhiyicx.baseproject.base.BaseListBean;

public class GameInfoBean extends BaseListBean {
//    {"url":"http:\/\/47.102.117.143\/utvjjm\/"}
    public String url;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.url);
    }

    public GameInfoBean() {
    }

    protected GameInfoBean(Parcel in) {
        super(in);
        this.url = in.readString();
    }

    public static final Creator<GameInfoBean> CREATOR = new Creator<GameInfoBean>() {
        @Override
        public GameInfoBean createFromParcel(Parcel source) {
            return new GameInfoBean(source);
        }

        @Override
        public GameInfoBean[] newArray(int size) {
            return new GameInfoBean[size];
        }
    };
}
