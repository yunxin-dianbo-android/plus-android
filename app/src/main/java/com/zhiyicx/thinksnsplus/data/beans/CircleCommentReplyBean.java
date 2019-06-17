package com.zhiyicx.thinksnsplus.data.beans;

import android.os.Parcel;

import com.zhiyicx.baseproject.base.BaseListBean;

/**
 * 回复评论这里随便先写着
 */
public class CircleCommentReplyBean extends BaseListBean {
    private Long comment_mark;
    private int circle_id;
    private int post_id;
    private long user_id;// 谁发的这条评论
    private UserInfoBean commentUser;
    private String content;
    private UserInfoBean replyUser;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeValue(this.comment_mark);
        dest.writeInt(this.circle_id);
        dest.writeInt(this.post_id);
        dest.writeLong(this.user_id);
        dest.writeParcelable(this.commentUser, flags);
        dest.writeString(this.content);
        dest.writeParcelable(this.replyUser, flags);
    }

    public CircleCommentReplyBean() {
    }

    protected CircleCommentReplyBean(Parcel in) {
        super(in);
        this.comment_mark = (Long) in.readValue(Long.class.getClassLoader());
        this.circle_id = in.readInt();
        this.post_id = in.readInt();
        this.user_id = in.readLong();
        this.commentUser = in.readParcelable(UserInfoBean.class.getClassLoader());
        this.content = in.readString();
        this.replyUser = in.readParcelable(UserInfoBean.class.getClassLoader());
    }

    public static final Creator<CircleCommentReplyBean> CREATOR = new Creator<CircleCommentReplyBean>() {
        @Override
        public CircleCommentReplyBean createFromParcel(Parcel source) {
            return new CircleCommentReplyBean(source);
        }

        @Override
        public CircleCommentReplyBean[] newArray(int size) {
            return new CircleCommentReplyBean[size];
        }
    };
}
