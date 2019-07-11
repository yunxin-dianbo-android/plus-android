package com.zhiyicx.thinksnsplus.data.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class SuperStarClassifyBean implements Parcelable {
    public String key;
    public List<SuperStarBean> star;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeList(this.star);
    }

    public SuperStarClassifyBean() {
    }

    protected SuperStarClassifyBean(Parcel in) {
        this.key = in.readString();
        this.star = new ArrayList<SuperStarBean>();
        in.readList(this.star, SuperStarBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<SuperStarClassifyBean> CREATOR = new Parcelable.Creator<SuperStarClassifyBean>() {
        @Override
        public SuperStarClassifyBean createFromParcel(Parcel source) {
            return new SuperStarClassifyBean(source);
        }

        @Override
        public SuperStarClassifyBean[] newArray(int size) {
            return new SuperStarClassifyBean[size];
        }
    };
}
