package com.zhiyicx.thinksnsplus.data.beans;

import android.os.Parcel;

import com.zhiyicx.baseproject.base.BaseListBean;

public class AddSearchKeyResInfo extends BaseListBean {
     public String message;//": "操作成功"

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.message);
    }

    public AddSearchKeyResInfo() {
    }

    protected AddSearchKeyResInfo(Parcel in) {
        super(in);
        this.message = in.readString();
    }

    public static final Creator<AddSearchKeyResInfo> CREATOR = new Creator<AddSearchKeyResInfo>() {
        @Override
        public AddSearchKeyResInfo createFromParcel(Parcel source) {
            return new AddSearchKeyResInfo(source);
        }

        @Override
        public AddSearchKeyResInfo[] newArray(int size) {
            return new AddSearchKeyResInfo[size];
        }
    };
}
