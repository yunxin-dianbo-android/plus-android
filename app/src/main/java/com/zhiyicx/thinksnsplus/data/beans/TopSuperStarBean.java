package com.zhiyicx.thinksnsplus.data.beans;

import android.os.Parcel;

import com.zhiyicx.baseproject.base.BaseListBean;

/**
 * @Author Jliuer
 * @Date 2017/12/22/13:05
 * @Email Jliuer@aliyun.com
 * @Description
 */
public class TopSuperStarBean extends BaseListBean {

    public Avatar getAvatar() {
        return avatar;
    }

    public void setGroup_id(long group_id) {
        this.group_id = group_id;
    }

    public static Creator<TopSuperStarBean> getCREATOR() {
        return CREATOR;
    }

    /**
     * id : 1
     * group_id : 1
     * name : 陈伟霆
     * sort : 0
     * avatar : http://47.97.249.200/api/v2/files/6
     * created_at : 2019-05-18 05:26:58
     * updated_at : 2019-05-18 05:26:58
     * deleted_at : null
     */

    private int id;
    private long group_id;
    private String name;
    private int sort;
    private Avatar avatar;
    private String created_at;
    private String updated_at;
    protected Object deleted_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
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

    public Object getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(Object deleted_at) {
        this.deleted_at = deleted_at;
    }

    public TopSuperStarBean() {
    }

    public void setAvatar(Avatar avatar) {
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
        dest.writeLong(this.group_id);
        dest.writeString(this.name);
        dest.writeInt(this.sort);
        dest.writeParcelable(this.avatar, flags);
        dest.writeString(this.created_at);
        dest.writeString(this.updated_at);
//        dest.writeParcelable(this.deleted_at, flags);
    }

    protected TopSuperStarBean(Parcel in) {
        super(in);
        this.id = in.readInt();
        this.group_id = in.readLong();
        this.name = in.readString();
        this.sort = in.readInt();
        this.avatar = in.readParcelable(Avatar.class.getClassLoader());
        this.created_at = in.readString();
        this.updated_at = in.readString();
        this.deleted_at = in.readParcelable(Object.class.getClassLoader());
    }

    public static final Creator<TopSuperStarBean> CREATOR = new Creator<TopSuperStarBean>() {
        @Override
        public TopSuperStarBean createFromParcel(Parcel source) {
            return new TopSuperStarBean(source);
        }

        @Override
        public TopSuperStarBean[] newArray(int size) {
            return new TopSuperStarBean[size];
        }
    };
}
