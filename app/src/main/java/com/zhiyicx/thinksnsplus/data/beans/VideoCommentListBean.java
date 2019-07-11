package com.zhiyicx.thinksnsplus.data.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class VideoCommentListBean implements Parcelable {
   public List<VideoCommentBean> comments;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.comments);
    }

    public VideoCommentListBean() {
    }

    protected VideoCommentListBean(Parcel in) {
        this.comments = in.createTypedArrayList(VideoCommentBean.CREATOR);
    }

    public static final Parcelable.Creator<VideoCommentListBean> CREATOR = new Parcelable.Creator<VideoCommentListBean>() {
        @Override
        public VideoCommentListBean createFromParcel(Parcel source) {
            return new VideoCommentListBean(source);
        }

        @Override
        public VideoCommentListBean[] newArray(int size) {
            return new VideoCommentListBean[size];
        }
    };
}
