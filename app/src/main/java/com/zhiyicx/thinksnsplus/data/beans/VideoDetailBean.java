package com.zhiyicx.thinksnsplus.data.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class VideoDetailBean implements Parcelable {

    /**
     * id : 2
     * name : 本地视频1
     * summary : null
     * channel_id : 4
     * sort : 0
     * view_count : 20
     * collect_count : 0
     * cover : 7
     * play_url : 3
     * upload_type : cloud
     * created_at : 2019-06-03 20:02:58
     * updated_at : 2019-06-18 08:51:07
     * deleted_at : null
     * cover_size : null
     * video_size : null
     * user_id : 0
     * comment_count : 0
     * comment_updated_at : null
     */

    private int id;
    private String name;
    private String summary;
    private int channel_id;
    private int sort;
    private int view_count;
    private int collect_count;
    private String cover;
    private String play_url;
    private Float duration;
    private Float progress;


    public boolean isIs_collect() {
        return is_collect;
    }

    public void setIs_collect(boolean is_collect) {
        this.is_collect = is_collect;
    }

    private boolean is_collect;//是否收藏
    public Float getProgress() {
        return progress;
    }

    public void setProgress(Float progress) {
        this.progress = progress;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public void setCover_size(String cover_size) {
        this.cover_size = cover_size;
    }

    public void setVideo_size(String video_size) {
        this.video_size = video_size;
    }

    public void setComment_updated_at(String comment_updated_at) {
        this.comment_updated_at = comment_updated_at;
    }

    private String upload_type;
    private String created_at;
    private String updated_at;
    private String deleted_at;
    private String cover_size;
    private String video_size;
    private int user_id;
    private int comment_count;
    private String comment_updated_at;

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

    public Object getDeleted_at() {
        return deleted_at;
    }



    public Object getCover_size() {
        return cover_size;
    }



    public Object getVideo_size() {
        return video_size;
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

    public Object getComment_updated_at() {
        return comment_updated_at;
    }


    public List<VideoListBean.TagsBean> getTags() {
        return tags;
    }

    public void setTags(List<VideoListBean.TagsBean> tags) {
        this.tags = tags;
    }

    private List<VideoListBean.TagsBean> tags;

//     "tags": [
//    {
//        "id": 3,
//            "name": "喜剧",
//            "tag_category_id": 2,
//            "weight": 0
//    },
//    {
//        "id": 2,
//            "name": "相声",
//            "tag_category_id": 2,
//            "weight": 0
//    },
//    {
//        "id": 5,
//            "name": "言情",
//            "tag_category_id": 2,
//            "weight": 0
//    },
//    {
//        "id": 4,
//            "name": "历史",
//            "tag_category_id": 2,
//            "weight": 0
//    }
//    ]

    public VideoDetailBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.summary);
        dest.writeInt(this.channel_id);
        dest.writeInt(this.sort);
        dest.writeInt(this.view_count);
        dest.writeInt(this.collect_count);
        dest.writeString(this.cover);
        dest.writeString(this.play_url);
        dest.writeValue(this.duration);
        dest.writeValue(this.progress);
        dest.writeString(this.upload_type);
        dest.writeString(this.created_at);
        dest.writeString(this.updated_at);
        dest.writeString(this.deleted_at);
        dest.writeString(this.cover_size);
        dest.writeString(this.video_size);
        dest.writeInt(this.user_id);
        dest.writeInt(this.comment_count);
        dest.writeString(this.comment_updated_at);
        dest.writeTypedList(this.tags);
    }

    protected VideoDetailBean(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.summary = in.readString();
        this.channel_id = in.readInt();
        this.sort = in.readInt();
        this.view_count = in.readInt();
        this.collect_count = in.readInt();
        this.cover = in.readString();
        this.play_url = in.readString();
        this.duration = (Float) in.readValue(Float.class.getClassLoader());
        this.progress = (Float) in.readValue(Float.class.getClassLoader());
        this.upload_type = in.readString();
        this.created_at = in.readString();
        this.updated_at = in.readString();
        this.deleted_at = in.readString();
        this.cover_size = in.readString();
        this.video_size = in.readString();
        this.user_id = in.readInt();
        this.comment_count = in.readInt();
        this.comment_updated_at = in.readString();
        this.tags = in.createTypedArrayList(VideoListBean.TagsBean.CREATOR);
    }

    public static final Creator<VideoDetailBean> CREATOR = new Creator<VideoDetailBean>() {
        @Override
        public VideoDetailBean createFromParcel(Parcel source) {
            return new VideoDetailBean(source);
        }

        @Override
        public VideoDetailBean[] newArray(int size) {
            return new VideoDetailBean[size];
        }
    };
}
