package com.zhiyicx.thinksnsplus.data.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.zhiyicx.baseproject.base.BaseListBean;
import com.zhiyicx.baseproject.cache.CacheBean;

public class VideoChannelBean extends BaseListBean implements Parcelable {

    /**
     * id : 2
     * name : 短视频
     * sort : 0
     * created_at : 2019-05-13 06:21:57
     * updated_at : 2019-05-13 06:21:57
     * deleted_at : null
     */

    private int id;
    private String name;
    private int sort;
    private String created_at;


//         "id": 1,
//                 "name": "精选",
//                 "sort": 0,
//                 "created_at": "2019-05-27 06:24:03",
//                 "updated_at": "2019-05-27 06:24:03",
//                 "deleted_at": null,
    private int weight;//": 1,
    private int type;//": 1
    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private String updated_at;
    private String deleted_at;
//    private int weight;//": 1,
//    private int type;//": 1

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.sort);
        dest.writeString(this.created_at);
        dest.writeString(this.updated_at);
        dest.writeString(this.deleted_at);
        dest.writeInt(this.weight);
        dest.writeInt(this.type);
    }

    public VideoChannelBean() {
    }

    protected VideoChannelBean(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.sort = in.readInt();
        this.created_at = in.readString();
        this.updated_at = in.readString();
        this.deleted_at = in.readString();
        this.weight = in.readInt();
        this.type = in.readInt();
    }

    public static final Parcelable.Creator<VideoChannelBean> CREATOR = new Parcelable.Creator<VideoChannelBean>() {
        @Override
        public VideoChannelBean createFromParcel(Parcel source) {
            return new VideoChannelBean(source);
        }

        @Override
        public VideoChannelBean[] newArray(int size) {
            return new VideoChannelBean[size];
        }
    };
}
