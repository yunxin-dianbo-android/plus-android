package com.zhiyicx.thinksnsplus.data.beans;

import android.os.Parcel;

import com.zhiyicx.baseproject.base.BaseListBean;

public class AdListBeanV2 extends BaseListBean {

    /**
     * id : 1
     * space_id : 1
     * title : 111
     * type : image
     * data : {"image":"http://47.97.249.200/api/v2/files/11","link":"http://www.baidu.com","duration":1}
     * sort : 0
     * created_at : 2019-05-27 08:17:46
     * updated_at : 2019-05-27 08:22:40
     * is_list_type : 0
     */

    private int id;
    private int space_id;
    private String title;
    private String type;
    private DataBean data;
    private int sort;
    private String created_at;
    private String updated_at;
    private int is_list_type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSpace_id() {
        return space_id;
    }

    public void setSpace_id(int space_id) {
        this.space_id = space_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
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

    public int getIs_list_type() {
        return is_list_type;
    }

    public void setIs_list_type(int is_list_type) {
        this.is_list_type = is_list_type;
    }

    public static class DataBean implements android.os.Parcelable {
        /**
         * image : http://47.97.249.200/api/v2/files/11
         * link : http://www.baidu.com
         * duration : 1
         */

        private String image;
        private String link;
        private int duration;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.image);
            dest.writeString(this.link);
            dest.writeInt(this.duration);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.image = in.readString();
            this.link = in.readString();
            this.duration = in.readInt();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.id);
        dest.writeInt(this.space_id);
        dest.writeString(this.title);
        dest.writeString(this.type);
        dest.writeParcelable(this.data, flags);
        dest.writeInt(this.sort);
        dest.writeString(this.created_at);
        dest.writeString(this.updated_at);
        dest.writeInt(this.is_list_type);
    }

    public AdListBeanV2() {
    }

    protected AdListBeanV2(Parcel in) {
        super(in);
        this.id = in.readInt();
        this.space_id = in.readInt();
        this.title = in.readString();
        this.type = in.readString();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
        this.sort = in.readInt();
        this.created_at = in.readString();
        this.updated_at = in.readString();
        this.is_list_type = in.readInt();
    }

    public static final Creator<AdListBeanV2> CREATOR = new Creator<AdListBeanV2>() {
        @Override
        public AdListBeanV2 createFromParcel(Parcel source) {
            return new AdListBeanV2(source);
        }

        @Override
        public AdListBeanV2[] newArray(int size) {
            return new AdListBeanV2[size];
        }
    };
}
