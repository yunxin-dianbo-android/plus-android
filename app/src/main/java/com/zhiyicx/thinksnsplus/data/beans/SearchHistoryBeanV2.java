package com.zhiyicx.thinksnsplus.data.beans;

import android.os.Parcel;

import com.zhiyicx.baseproject.base.BaseListBean;

public class SearchHistoryBeanV2 extends BaseListBean {

    /**
     * id : 2
     * user_id : 1
     * keyword : 喜剧
     * isShow : 1
     * created_at : 2019-06-18 13:00:19
     * updated_at : 2019-06-18 13:00:19
     */

    private int id;
    private int user_id;
    private String keyword;
    private int isShow;
    private String created_at;
    private String updated_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getIsShow() {
        return isShow;
    }

    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.id);
        dest.writeInt(this.user_id);
        dest.writeString(this.keyword);
        dest.writeInt(this.isShow);
        dest.writeString(this.created_at);
        dest.writeString(this.updated_at);
    }

    public SearchHistoryBeanV2() {
    }

    protected SearchHistoryBeanV2(Parcel in) {
        super(in);
        this.id = in.readInt();
        this.user_id = in.readInt();
        this.keyword = in.readString();
        this.isShow = in.readInt();
        this.created_at = in.readString();
        this.updated_at = in.readString();
    }

    public static final Creator<SearchHistoryBeanV2> CREATOR = new Creator<SearchHistoryBeanV2>() {
        @Override
        public SearchHistoryBeanV2 createFromParcel(Parcel source) {
            return new SearchHistoryBeanV2(source);
        }

        @Override
        public SearchHistoryBeanV2[] newArray(int size) {
            return new SearchHistoryBeanV2[size];
        }
    };
}
