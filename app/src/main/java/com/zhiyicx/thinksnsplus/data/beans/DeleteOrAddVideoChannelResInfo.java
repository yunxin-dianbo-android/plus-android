package com.zhiyicx.thinksnsplus.data.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class DeleteOrAddVideoChannelResInfo implements Parcelable {
    public String message;//": "操作成功"

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.message);
    }

    public DeleteOrAddVideoChannelResInfo() {
    }

    protected DeleteOrAddVideoChannelResInfo(Parcel in) {
        this.message = in.readString();
    }

    public static final Parcelable.Creator<DeleteOrAddVideoChannelResInfo> CREATOR = new Parcelable.Creator<DeleteOrAddVideoChannelResInfo>() {
        @Override
        public DeleteOrAddVideoChannelResInfo createFromParcel(Parcel source) {
            return new DeleteOrAddVideoChannelResInfo(source);
        }

        @Override
        public DeleteOrAddVideoChannelResInfo[] newArray(int size) {
            return new DeleteOrAddVideoChannelResInfo[size];
        }
    };
}
