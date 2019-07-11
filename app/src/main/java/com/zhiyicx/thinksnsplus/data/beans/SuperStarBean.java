package com.zhiyicx.thinksnsplus.data.beans;

import android.os.Parcel;

import com.zhiyicx.baseproject.base.BaseListBean;

public class SuperStarBean extends BaseListBean {

    /**
     * id : 3
     * name : 陈伟霆
     * sort : 0
     * created_at : 2019-05-13 09:17:52
     * updated_at : 2019-05-14 09:27:13
     * deleted_at :
     * avatar : http://local.lin-plus.com/api/v2/files/15
     */

    private int id;
    private String name;
    private int sort;
    private String created_at;
    private String updated_at;
    private String deleted_at;
    private String avatar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
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

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.sort);
        dest.writeString(this.created_at);
        dest.writeString(this.updated_at);
        dest.writeString(this.deleted_at);
        dest.writeString(this.avatar);
    }

    public SuperStarBean() {
    }

    protected SuperStarBean(Parcel in) {
        super(in);
        this.id = in.readInt();
        this.name = in.readString();
        this.sort = in.readInt();
        this.created_at = in.readString();
        this.updated_at = in.readString();
        this.deleted_at = in.readString();
        this.avatar = in.readString();
    }

    public static final Creator<SuperStarBean> CREATOR = new Creator<SuperStarBean>() {
        @Override
        public SuperStarBean createFromParcel(Parcel source) {
            return new SuperStarBean(source);
        }

        @Override
        public SuperStarBean[] newArray(int size) {
            return new SuperStarBean[size];
        }
    };
}
