package com.zhiyicx.thinksnsplus.data.beans;

import android.os.Parcel;

import com.zhiyicx.baseproject.base.BaseListBean;

import java.util.List;

public class VideoListBean extends BaseListBean {

    @Override
    public Long getMaxId() {
//        if (mListDatas.size() > 0) {
//            return mListDatas.get(mListDatas.size() - 1).getMaxId();
////            return mListDatas.size() * 1l;
//        }
//        return DEFAULT_PAGE_MAX_ID;
//        return super.getMaxId();
        return id * 1l;
    }

    /**
     * id : 2
     * name : 测试一下
     * summary : 膳魔师
     * channel_id : 4
     * sort : 0
     * view_count : 0
     * collect_count : 0
     * cover : 90
     * play_url : 91
     * upload_type : cloud
     * created_at : 2019-06-15 10:24:03
     * updated_at : 2019-06-18 07:00:47
     * deleted_at : null
     * cover_size : 0
     * video_size : 0
     * user_id : 0
     * comment_count : 3
     * comment_updated_at : 2019-06-18 07:00:47
     * tags : [{"id":1,"name":"默认标签","tag_category_id":1,"weight":0}]
     */


    private int id;
    private String name;

    public Integer getVideo_id() {
        return video_id;
    }

    public void setVideo_id(Integer video_id) {
        this.video_id = video_id;
    }

    private String summary;
    private int channel_id;
    private Integer video_id;
    private int sort;
    private int view_count;
    private int collect_count;
    private String cover;
    private String play_url;
    private String upload_type;
    private String created_at;
    private String updated_at;
    private String deleted_at;
    private int cover_size;
    private int video_size;
    private int user_id;
    private int comment_count;

    //自定字段
    public boolean isEditMode = false;


    public boolean isChecked = false;

    public Float getProgress() {
        return progress;
    }

    public void setProgress(Float progress) {
        this.progress = progress;
    }

    private Float progress;

    private String comment_updated_at;
    private List<TagsBean> tags;

    private SuperStarBean star;

    private VideoListBean video;

    public boolean isSeven_time() {
        return seven_time;
    }

    public void setSeven_time(boolean seven_time) {
        this.seven_time = seven_time;
    }

    private boolean seven_time;


    public Float getDuration() {
        return duration;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

    private Float duration;// 影片时长

    public SuperStarBean getStar() {
        return star;
    }

    public void setStar(SuperStarBean star) {
        this.star = star;
    }

    public VideoListBean getVideo() {
        return video;
    }

    public void setVideo(VideoListBean video) {
        this.video = video;
    }


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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(int channel_id) {
        this.channel_id = channel_id;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getView_count() {
        return view_count;
    }

    public void setView_count(int view_count) {
        this.view_count = view_count;
    }

    public int getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(int collect_count) {
        this.collect_count = collect_count;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getPlay_url() {
        return play_url;
    }

    public void setPlay_url(String play_url) {
        this.play_url = play_url;
    }

    public String getUpload_type() {
        return upload_type;
    }

    public void setUpload_type(String upload_type) {
        this.upload_type = upload_type;
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

    public int getCover_size() {
        return cover_size;
    }

    public void setCover_size(int cover_size) {
        this.cover_size = cover_size;
    }

    public int getVideo_size() {
        return video_size;
    }

    public void setVideo_size(int video_size) {
        this.video_size = video_size;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public String getComment_updated_at() {
        return comment_updated_at;
    }

    public void setComment_updated_at(String comment_updated_at) {
        this.comment_updated_at = comment_updated_at;
    }

    public List<TagsBean> getTags() {
        return tags;
    }

    public void setTags(List<TagsBean> tags) {
        this.tags = tags;
    }

    public static class TagsBean implements android.os.Parcelable {
        /**
         * id : 1
         * name : 默认标签
         * tag_category_id : 1
         * weight : 0
         */


        private int id;
        private String name;
        private int tag_category_id;
        private int weight;
        //自定义字段 用于判断是否是全部的分类
        public boolean isFirst;

        public boolean isChoosed;

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

        public int getTag_category_id() {
            return tag_category_id;
        }

        public void setTag_category_id(int tag_category_id) {
            this.tag_category_id = tag_category_id;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.name);
            dest.writeInt(this.tag_category_id);
            dest.writeInt(this.weight);
        }

        public TagsBean() {
        }

        protected TagsBean(Parcel in) {
            this.id = in.readInt();
            this.name = in.readString();
            this.tag_category_id = in.readInt();
            this.weight = in.readInt();
        }

        public static final Creator<TagsBean> CREATOR = new Creator<TagsBean>() {
            @Override
            public TagsBean createFromParcel(Parcel source) {
                return new TagsBean(source);
            }

            @Override
            public TagsBean[] newArray(int size) {
                return new TagsBean[size];
            }
        };


        @Override
        public boolean equals(Object obj) {
            if (obj instanceof TagsBean) {
                TagsBean tagsBean = (TagsBean) obj;
                if (tagsBean.getId() == id && tagsBean.getName().equals(name)) {
                    return true;
                }
            }
            return false;
        }
    }

    public VideoListBean() {
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
        dest.writeString(this.summary);
        dest.writeInt(this.channel_id);
        dest.writeInt(this.sort);
        dest.writeInt(this.view_count);
        dest.writeInt(this.collect_count);
        dest.writeString(this.cover);
        dest.writeString(this.play_url);
        dest.writeString(this.upload_type);
        dest.writeString(this.created_at);
        dest.writeString(this.updated_at);
        dest.writeString(this.deleted_at);
        dest.writeInt(this.cover_size);
        dest.writeInt(this.video_size);
        dest.writeInt(this.user_id);
        dest.writeInt(this.comment_count);
        dest.writeString(this.comment_updated_at);
        dest.writeTypedList(this.tags);
        dest.writeParcelable(this.star, flags);
        dest.writeParcelable(this.video, flags);
        dest.writeByte(this.seven_time ? (byte) 1 : (byte) 0);
        dest.writeValue(this.duration);
    }

    protected VideoListBean(Parcel in) {
        super(in);
        this.id = in.readInt();
        this.name = in.readString();
        this.summary = in.readString();
        this.channel_id = in.readInt();
        this.sort = in.readInt();
        this.view_count = in.readInt();
        this.collect_count = in.readInt();
        this.cover = in.readString();
        this.play_url = in.readString();
        this.upload_type = in.readString();
        this.created_at = in.readString();
        this.updated_at = in.readString();
        this.deleted_at = in.readString();
        this.cover_size = in.readInt();
        this.video_size = in.readInt();
        this.user_id = in.readInt();
        this.comment_count = in.readInt();
        this.comment_updated_at = in.readString();
        this.tags = in.createTypedArrayList(TagsBean.CREATOR);
        this.star = in.readParcelable(SuperStarBean.class.getClassLoader());
        this.video = in.readParcelable(VideoListBean.class.getClassLoader());
        this.seven_time = in.readByte() != 0;
        this.duration = (Float) in.readValue(Float.class.getClassLoader());
    }

    public static final Creator<VideoListBean> CREATOR = new Creator<VideoListBean>() {
        @Override
        public VideoListBean createFromParcel(Parcel source) {
            return new VideoListBean(source);
        }

        @Override
        public VideoListBean[] newArray(int size) {
            return new VideoListBean[size];
        }
    };
}
