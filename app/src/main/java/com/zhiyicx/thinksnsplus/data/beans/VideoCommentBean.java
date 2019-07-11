package com.zhiyicx.thinksnsplus.data.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.zhiyicx.baseproject.base.BaseListBean;

import java.util.List;

public class VideoCommentBean extends BaseListBean{

    public UserInfoBean getUser() {
        return user;
    }

    public void setUser(UserInfoBean user) {
        this.user = user;
    }

    public UserInfoBean getReply() {
        return reply;
    }

    public void setReply(UserInfoBean reply) {
        this.reply = reply;
    }

    /**
     * id : 11
     * user_id : 1
     * target_user : 0
     * reply_user : 7
     * body : 我是评论内容
     * commentable_type : videos
     * commentable_id : 3
     * created_at : 2019-06-18 05:39:29
     * updated_at : 2019-06-18 05:39:29
     */

    private int id;
    private int user_id;
    private int target_user;
    private int reply_user;
    private String body;
    private String commentable_type;
    private int commentable_id;
    private int comment_children_count;

    private int comment_like_count;

    private String created_at;
    private String updated_at;

    private boolean has_like;

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

    @SerializedName("user")
//    @Convert(converter = UserInfoBeanConvert.class, columnType = String.class)
    private UserInfoBean user;
    private UserInfoBean reply;// 被评论的用户信息

    public boolean isHas_like() {
        return has_like;
    }
    public int getComment_like_count() {
        return comment_like_count;
    }

    public void setComment_like_count(int comment_like_count) {
        this.comment_like_count = comment_like_count;
    }
    public void setHas_like(boolean has_like) {
        this.has_like = has_like;
    }
    public int getComment_children_count() {
        return comment_children_count;
    }

    public void setComment_children_count(int comment_children_count) {
        this.comment_children_count = comment_children_count;
    }
    public List<VideoCommentBean> getComment_children() {
        return comment_children;
    }

    public void setComment_children(List<VideoCommentBean> comment_children) {
        this.comment_children = comment_children;
    }

    private List<VideoCommentBean> comment_children;
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

    public int getTarget_user() {
        return target_user;
    }

    public void setTarget_user(int target_user) {
        this.target_user = target_user;
    }

    public int getReply_user() {
        return reply_user;
    }

    public void setReply_user(int reply_user) {
        this.reply_user = reply_user;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCommentable_type() {
        return commentable_type;
    }

    public void setCommentable_type(String commentable_type) {
        this.commentable_type = commentable_type;
    }

    public int getCommentable_id() {
        return commentable_id;
    }

    public void setCommentable_id(int commentable_id) {
        this.commentable_id = commentable_id;
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
        dest.writeInt(this.id);
        dest.writeInt(this.user_id);
        dest.writeInt(this.target_user);
        dest.writeInt(this.reply_user);
        dest.writeString(this.body);
        dest.writeString(this.commentable_type);
        dest.writeInt(this.commentable_id);
        dest.writeString(this.created_at);
        dest.writeString(this.updated_at);
        dest.writeParcelable(this.user, flags);
        dest.writeParcelable(this.reply, flags);
    }

    public VideoCommentBean() {
    }

    protected VideoCommentBean(Parcel in) {
        this.id = in.readInt();
        this.user_id = in.readInt();
        this.target_user = in.readInt();
        this.reply_user = in.readInt();
        this.body = in.readString();
        this.commentable_type = in.readString();
        this.commentable_id = in.readInt();
        this.created_at = in.readString();
        this.updated_at = in.readString();
        this.user = in.readParcelable(UserInfoBean.class.getClassLoader());
        this.reply = in.readParcelable(UserInfoBean.class.getClassLoader());
    }

    public static final Creator<VideoCommentBean> CREATOR = new Creator<VideoCommentBean>() {
        @Override
        public VideoCommentBean createFromParcel(Parcel source) {
            return new VideoCommentBean(source);
        }

        @Override
        public VideoCommentBean[] newArray(int size) {
            return new VideoCommentBean[size];
        }
    };
}
