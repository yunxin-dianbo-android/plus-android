package com.zhiyicx.thinksnsplus.data.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class UploadPostCommentResInfo implements Parcelable {

//   public VideoCommentBean comment;
   public DynamicCommentBean comment;
   public String message;

    public UploadPostCommentResInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.comment, flags);
        dest.writeString(this.message);
    }

    protected UploadPostCommentResInfo(Parcel in) {
        this.comment = in.readParcelable(DynamicCommentBean.class.getClassLoader());
        this.message = in.readString();
    }

    public static final Creator<UploadPostCommentResInfo> CREATOR = new Creator<UploadPostCommentResInfo>() {
        @Override
        public UploadPostCommentResInfo createFromParcel(Parcel source) {
            return new UploadPostCommentResInfo(source);
        }

        @Override
        public UploadPostCommentResInfo[] newArray(int size) {
            return new UploadPostCommentResInfo[size];
        }
    };
}
