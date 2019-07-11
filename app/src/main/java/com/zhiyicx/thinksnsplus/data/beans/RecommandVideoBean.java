package com.zhiyicx.thinksnsplus.data.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class RecommandVideoBean implements Parcelable {
    public int taggable_id;//": 3,
    public VideoListBean video;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.taggable_id);
        dest.writeParcelable(this.video, flags);
    }

    public RecommandVideoBean() {
    }

    protected RecommandVideoBean(Parcel in) {
        this.taggable_id = in.readInt();
        this.video = in.readParcelable(VideoListBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<RecommandVideoBean> CREATOR = new Parcelable.Creator<RecommandVideoBean>() {
        @Override
        public RecommandVideoBean createFromParcel(Parcel source) {
            return new RecommandVideoBean(source);
        }

        @Override
        public RecommandVideoBean[] newArray(int size) {
            return new RecommandVideoBean[size];
        }
    };
}
