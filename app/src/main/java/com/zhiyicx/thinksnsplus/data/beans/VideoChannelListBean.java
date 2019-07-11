package com.zhiyicx.thinksnsplus.data.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class VideoChannelListBean implements Parcelable {
    public List<VideoChannelBean> user_channels;//": [],
    public List<VideoChannelBean> other_channels;//":
    public List<VideoChannelBean> default_channels;

    public VideoChannelListBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.user_channels);
        dest.writeTypedList(this.other_channels);
        dest.writeTypedList(this.default_channels);
    }

    protected VideoChannelListBean(Parcel in) {
        this.user_channels = in.createTypedArrayList(VideoChannelBean.CREATOR);
        this.other_channels = in.createTypedArrayList(VideoChannelBean.CREATOR);
        this.default_channels = in.createTypedArrayList(VideoChannelBean.CREATOR);
    }

    public static final Creator<VideoChannelListBean> CREATOR = new Creator<VideoChannelListBean>() {
        @Override
        public VideoChannelListBean createFromParcel(Parcel source) {
            return new VideoChannelListBean(source);
        }

        @Override
        public VideoChannelListBean[] newArray(int size) {
            return new VideoChannelListBean[size];
        }
    };
}
