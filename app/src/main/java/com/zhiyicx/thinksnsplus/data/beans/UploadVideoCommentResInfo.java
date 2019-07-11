package com.zhiyicx.thinksnsplus.data.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class UploadVideoCommentResInfo implements Parcelable {

   public VideoCommentBean comment;
   public List<String> message;

    public UploadVideoCommentResInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.comment, flags);
        dest.writeStringList(this.message);
    }

    protected UploadVideoCommentResInfo(Parcel in) {
        this.comment = in.readParcelable(VideoCommentBean.class.getClassLoader());
        this.message = in.createStringArrayList();
    }

    public static final Creator<UploadVideoCommentResInfo> CREATOR = new Creator<UploadVideoCommentResInfo>() {
        @Override
        public UploadVideoCommentResInfo createFromParcel(Parcel source) {
            return new UploadVideoCommentResInfo(source);
        }

        @Override
        public UploadVideoCommentResInfo[] newArray(int size) {
            return new UploadVideoCommentResInfo[size];
        }
    };
}
