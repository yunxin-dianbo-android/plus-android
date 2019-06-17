//package com.zhiyicx.thinksnsplus.data.beans;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//
//import com.bumptech.glide.load.model.GlideUrl;
//import com.google.gson.annotations.SerializedName;
//import com.klinker.android.link_builder.Link;
//import com.zhiyicx.baseproject.base.BaseListBean;
//import com.zhiyicx.baseproject.config.ApiConfig;
//import com.zhiyicx.common.config.MarkdownConfig;
//import com.zhiyicx.common.utils.TimeUtils;
//import com.zhiyicx.thinksnsplus.base.AppApplication;
//import com.zhiyicx.thinksnsplus.data.beans.DynamicDetailBeanV2.Video;
//import com.zhiyicx.thinksnsplus.data.source.local.data_convert.BaseConvert;
//import com.zhiyicx.thinksnsplus.data.source.local.data_convert.LetterConvert;
//import com.zhiyicx.thinksnsplus.data.source.local.data_convert.PaidNoteConverter;
//import com.zhiyicx.thinksnsplus.data.source.local.data_convert.TopicListConvert;
//import com.zhiyicx.thinksnsplus.data.source.local.data_convert.UserInfoBeanConvert;
//import com.zhiyicx.thinksnsplus.utils.ImageUtils;
//
//import org.greenrobot.greendao.annotation.Convert;
//import org.greenrobot.greendao.annotation.Entity;
//import org.greenrobot.greendao.annotation.Generated;
//import org.greenrobot.greendao.annotation.Id;
//import org.greenrobot.greendao.annotation.Keep;
//import org.greenrobot.greendao.annotation.Transient;
//import org.greenrobot.greendao.annotation.Unique;
//
//import java.io.Serializable;
//import java.util.List;
//
//import static com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListBaseItem.DEFALT_IMAGE_HEIGHT;
//import static com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListBaseItem.DEFALT_IMAGE_WITH;
//
///**
// * @author Jliuer
// * @Date 2017/11/29/15:44
// * @Email Jliuer@aliyun.com
// * @Description
// */
//@Entity
//public class CirclePostListBean2 extends BaseListBean implements Serializable, Cloneable {
//
//    public static final int SEND_ERROR = 0;
//    public static final int SEND_ING = 1;
//    public static final int SEND_SUCCESS = 2;
//    public static final long serialVersionUID = 9154485538884327047L;
//
//    /**
//     * id : 88
//     * group_id : 1
//     * user_id : 1
//     * title : 内容标题
//     * summary : 帖子介绍
//     * likes_count : 0
//     * comments_count : 0
//     * views_count : 0
//     * liked : true
//     * collected : true
//     * created_at : 2017-11-28 07:12:20
//     * updated_at : 2017-11-28 07:12:20
//     * images : [{"id":113,"size":"397x246"},{"id":115,"size":"397x246"}]
//     * user : {"id":1,"name":"admin","bio":null,"sex":2,"location":"四川省 巴中市 南江县","created_at":"2017-10-23 01:17:34","updated_at":"2017-11-15
//     * 07:36:17","avatar":"http://thinksns-plus.dev/api/v2/users/1/avatar","bg":null,"verified":{"type":"user","icon":"http://thinksns-plus
//     * .dev/storage/certifications/000/000/0us/er.png","description":"1111"},"extra":{"user_id":1,"likes_count":5,"comments_count":3,
//     * "followers_count":0,"followings_count":6,"updated_at":"2017-11-27 07:25:04","feeds_count":8,"questions_count":2,"answers_count":0,
//     * "checkin_count":7,"last_checkin_count":1}}
//     */
//    @Id
//    public Long id;
//    @Unique
//    public Long post_mark;
//    public long group_id;
//    public Long user_id;
//    public String title;
//    public String summary;
//
//    public int likes_count;
//
//    public int like_count;
//
//    public String feed_content;//": "\u6211\u662f\u771f\u7684\u597d",
//
//    public int feed_from;//": 1,
//
//    public int comments_count;
//
//    public int feed_comment_count;
//
//    public String feed_latitude;
//
//    public String feed_longtitude;
//
//    public int feed_view_count;
//
//    public String feed_geohash;
//
//    public int audit_status;
//
//    public long feed_mark;
//
//    /**
//     * 转发资源类型标识
//     */
//    public String repostable_type;
//
//    public int repostable_id;
//
//    public int hot;
//
//
//    public int views_count;
//
//    public boolean liked;
//
//    public boolean has_like;
//
//    public boolean collected;
//
//    public boolean has_collect;
//    public String created_at;
//    public String updated_at;
//    public String excellent_at; //如果存在，则表示精华
//    @Convert(converter = UserInfoBeanConvert.class, columnType = String.class)
//    public UserInfoBean user;
//    @Convert(converter = CirclePostImageConvert.class, columnType = String.class)
//    public List<ImagesBean> images;
//    @Convert(converter = CirclePostCommentConvert.class, columnType = String.class)
//    public List<CirclePostCommentBean> comments;
//    public int state = SEND_ING;
//
//    public int reward_amount;
//    public int reward_number;
//    public String body;
//    @Convert(converter = CircleInfoConvert.class, columnType = String.class)
//    public CircleInfo group;
//    public boolean pinned;
//    @Convert(converter = PostDigListConvert.class, columnType = String.class)
//    public List<PostDigListBean> digs;
//
//    @Transient
//    public String friendlyTime;
//    @Transient
//    public String friendlyContent;
//
//
//
//
//
//
//
//
//
//
//
//    public static final int DYNAMIC_LIST_CONTENT_MAX_SHOW_SIZE = 140;
//
//    public static final int TOP_SUCCESS = 1;
//    public static final int TOP_NONE = 0;
//    public static final int TOP_REVIEW = 2;
//
//
//
//    public String deleted_at;
//    @SerializedName("like_count")
//    public int feed_digg_count;
//
//
//
//
//
//
//
//    @SerializedName("has_like")
//    public boolean has_digg;
//    public long amount;
//    @Convert(converter = DynamicDetailBeanV2.LikeBeanConvert.class, columnType = String.class)
//    public List<DynamicLikeBean> likes;
//    public boolean paid;
//
//    @Convert(converter = DynamicDetailBeanV2.IntegerParamsConverter.class, columnType = String.class)
//    public List<Integer> diggs;
//    @Convert(converter = PaidNoteConverter.class, columnType = String.class)
//    public PaidNote paid_node;
//
//    @SerializedName("user")
//    @Convert(converter = UserInfoBeanConvert.class, columnType = String.class)
//    public UserInfoBean userInfoBean;
//    // DynamicBean 的 feed_mark 与 DynamicCommentBean 的 feed_mark 关联
////    @ToMany(joinProperties = {@JoinProperty(name = "feed_mark", referencedName = "feed_mark")})
//
//    public Long hot_creat_time;// 标记热门，已及创建时间，用户数据库查询
//    public boolean isFollowed;// 是否关注了该条动态（用户）
//    public String sendFailMessage;
//    public int top = TOP_NONE;// 置顶状态 0：无置顶状态 1：置顶成功 -1：置顶审核中
//
//    @Convert(converter = DynamicDetailBeanV2.DynamicDigListBeanConverter.class, columnType = String.class)
//    public List<DynamicDigListBean> digUserInfoList;// 点赞用户的信息列表
//
//
//    @Convert(converter = DynamicDetailBeanV2.RewardCountBeanConverter.class, columnType = String.class)
//    public RewardsCountBean reward;// 打赏总额
//
//    @Convert(converter = DynamicDetailBeanV2.VideoConverter.class, columnType = String.class)
//    public Video video;
//
//    /**
//     * 话题里的动态专属属性，数据查询定位值
//     */
//    public int index;
//    /**
//     * 话题里的动态属性，动态所属话题
//     */
//    @Convert(converter = TopicListConvert.class, columnType = String.class)
//    public List<TopicListBean> topics;
//
//
//
//
//
//    /**
//     * 轉發內容
//     */
//    @Convert(converter = LetterConvert.class, columnType = String.class)
//    public Letter mLetter;
//
//
//    @Transient
//    public int startPosition;
//
//    @Transient
//    public String userCenterFriendlyTimeUp;
//    @Transient
//    public String userCenterFriendlyTimeDonw;
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//    public String getGroup_name() {
//        return group_name;
//    }
//
//    public void setGroup_name(String group_name) {
//        this.group_name = group_name;
//    }
//
//    @Transient
//    public String group_name;
//
//    @Override
//    public Long getMaxId() {
//        return id;
//    }
//
//    public String getBody() {
//        return body;
//    }
//
//    public void setBody(String body) {
//        this.body = body;
//    }
//
//    public int getReward_amount() {
//        return reward_amount;
//    }
//
//    public void setReward_amount(int reward_amount) {
//        this.reward_amount = reward_amount;
//    }
//
//    public int getReward_number() {
//        return reward_number;
//    }
//
//    public void setReward_number(int reward_number) {
//        this.reward_number = reward_number;
//    }
//
//    public List<PostDigListBean> getDigList() {
//        return digs;
//    }
//
//    public void setDigList(List<PostDigListBean> digs) {
//        this.digs = digs;
//    }
//
//    public boolean hasPinned() {
//        return pinned;
//    }
//
//    public void setPinned(boolean pinned) {
//        this.pinned = pinned;
//    }
//
//    public CircleInfo getGroup() {
//        return group;
//    }
//
//    public void setGroup(CircleInfo group) {
//        this.group = group;
//    }
//
//    public Long getPost_mark() {
//        return post_mark;
//    }
//
//    public void setPost_mark(Long post_mark) {
//        this.post_mark = post_mark;
//    }
//
//    public int getState() {
//        return state;
//    }
//
//    public void setState(int state) {
//        this.state = state;
//    }
//
//    public List<CirclePostCommentBean> getComments() {
//        return comments;
//    }
//
//    public void setComments(List<CirclePostCommentBean> comments) {
//        this.comments = comments;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public long getGroup_id() {
//        return group_id;
//    }
//
//    public void setGroup_id(long group_id) {
//        this.group_id = group_id;
//    }
//
//    public Long getUser_id() {
//        return user_id;
//    }
//
//    public void setUser_id(Long user_id) {
//        this.user_id = user_id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getSummary() {
//        return summary;
//    }
//
//    public void setSummary(String summary) {
//        this.summary = summary;
//    }
//
//    public int getLikes_count() {
//        return likes_count;
//    }
//
//    public void setLikes_count(int likes_count) {
//        this.likes_count = likes_count;
//    }
//
//    public int getComments_count() {
//        return comments_count;
//    }
//
//    public void setComments_count(int comments_count) {
//        this.comments_count = comments_count;
//    }
//
//    public int getViews_count() {
//        return views_count;
//    }
//
//    public void setViews_count(int views_count) {
//        this.views_count = views_count;
//    }
//
//    public boolean hasLiked() {
//        return liked;
//    }
//
//    public void setLiked(boolean liked) {
//        this.liked = liked;
//    }
//
//    public boolean hasCollected() {
//        return collected;
//    }
//
//    public void setCollected(boolean collected) {
//        this.collected = collected;
//    }
//
//    public String getCreated_at() {
//        return created_at;
//    }
//
//    public void setCreated_at(String created_at) {
//        this.created_at = created_at;
//    }
//
//    public String getUpdated_at() {
//        return updated_at;
//    }
//
//    public void setUpdated_at(String updated_at) {
//        this.updated_at = updated_at;
//    }
//
//    @Keep
//    public UserInfoBean getUserInfoBean() {
//        return user;
//    }
//
//    @Keep
//    public void setUserInfoBean(UserInfoBean user) {
//        this.user = user;
//    }
//
//    public List<ImagesBean> getImages() {
//        return images;
//    }
//
//    public void setImages(List<ImagesBean> images) {
//        this.images = images;
//    }
//
//    public String getFriendlyTime() {
//        return friendlyTime;
//    }
//
//    public void setFriendlyTime(String friendlyTime) {
//        this.friendlyTime = friendlyTime;
//    }
//
//    public String getFriendlyContent() {
//        return friendlyContent;
//    }
//
//    public void setFriendlyContent(String friendlyContent) {
//        this.friendlyContent = friendlyContent;
//    }
//
//    public static class ImagesBean implements Serializable, Parcelable {
//        public static final long serialVersionUID = -2450120806619198355L;
//        public static final String FILE_MIME_TYPE_GIF = "image/gif";
//        /**
//         * raw : 2
//         * size : 1200x800
//         * file_id : 3
//         */
//
//        public String raw;
//        public String size;
//        public int width;
//        public int propPart;
//        public int height;
//        @SerializedName("id")
//        public int file_id;
//        public String imgUrl;
//        /**
//         * 图片类型
//         */
//        @SerializedName(value = "type", alternate = {"mime", "imgMimeType"})
//        public String type;
//
//
//        public int currentWith;
//        /**
//         * imageViewWidth、imageViewHeight 单张图宽高使用
//         */
//        public int imageViewWidth;
//        public int imageViewHeight;
//        public boolean isLongImage;
//        public String netUrl;
//        public transient GlideUrl glideUrl;
//
//
//
//
//
//        /**
//         * file : 4
//         * size : null
//         * amount : 100
//         * type : download
//         * paid : false
//         */
//        @SerializedName(value = "file", alternate = {"file_id"})
//        public int file;
//        public int paid_node;
//        public long amount;
//        public Boolean paid;
//        /**
//         * 图片类型
//         */
//        @SerializedName(value = "imgMimeType", alternate = {"mime"})
//        public String imgMimeType;
//        /**
//         * imageViewWidth、imageViewHeight 单张图宽高使用
//         */
//        public boolean canLook;
//
//
//        @Override
//        public String toString() {
//            return "ImagesBean{" +
//                    "raw='" + raw + '\'' +
//                    ", size='" + size + '\'' +
//                    ", width=" + width +
//                    ", propPart=" + propPart +
//                    ", height=" + height +
//                    ", file_id=" + file_id +
//                    ", imgUrl='" + imgUrl + '\'' +
//                    ", type='" + type + '\'' +
//                    ", currentWith=" + currentWith +
//                    ", imageViewWidth=" + imageViewWidth +
//                    ", imageViewHeight=" + imageViewHeight +
//                    ", isLongImage=" + isLongImage +
//                    ", netUrl='" + netUrl + '\'' +
//                    ", glideUrl=" + glideUrl +
//                    '}';
//        }
//
//        public int getPropPart() {
//            return propPart;
//        }
//
//        public void setPropPart(int propPart) {
//            this.propPart = propPart;
//        }
//
//        public String getSize() {
//            return size;
//        }
//
//        public void setWidth(int width) {
//            this.width = width;
//        }
//
//        public void setHeight(int height) {
//            this.height = height;
//        }
//
//        public String getImgUrl() {
//            return imgUrl;
//        }
//
//
//        public String getRealPicUrl() {
//            return ApiConfig.APP_DOMAIN_FORMAL + "api/v2/files/" + file_id;
//        }
//
//        public void setImgUrl(String imgUrl) {
//            this.imgUrl = imgUrl;
//        }
//
//        public String getImgMimeType() {
//            return type;
//        }
//
//        public String getType() {
//            return type;
//        }
//
//        public void setType(String type) {
//            this.type = type;
//        }
//
//        public void setImgMimeType(String type) {
//            this.type = type;
//        }
//
//        public String getRaw() {
//            return raw;
//        }
//
//        public void setRaw(String raw) {
//            this.raw = raw;
//        }
//
//        public boolean praseSize() {
//            try {
//                if (size != null && size.length() > 0) {
//                    String[] sizes = size.split("x");
//                    this.width = Integer.parseInt(sizes[0]);
//                    this.height = Integer.parseInt(sizes[1]);
//                    if (width <= 0) {
//                        width = DEFALT_IMAGE_WITH;
//                    }
//                    if (height <= 0) {
//                        height = DEFALT_IMAGE_HEIGHT;
//                    }
//
//                    return true;
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return false;
//        }
//
//        public void setSize(String size) {
//            this.size = size;
//            praseSize();
//
//        }
//
//        public int getWidth() {
//            if (width > 0) {
//                return width;
//            }
//            if (praseSize()) {
//                return width;
//            }
//            return DEFALT_IMAGE_WITH;
//
//        }
//
//        public int getHeight() {
//            if (height > 0) {
//                return height;
//            }
//            if (praseSize()) {
//                return height;
//            }
//            return DEFALT_IMAGE_HEIGHT;
//
//        }
//
//        public int getFile_id() {
//            return file_id;
//        }
//
//        public void setFile_id(int file_id) {
//            this.file_id = file_id;
//        }
//
//        public int getCurrentWith() {
//            return currentWith;
//        }
//
//        public void setCurrentWith(int currentWith) {
//            this.currentWith = currentWith;
//        }
//
//        public int getImageViewWidth() {
//            return imageViewWidth;
//        }
//
//        public void setImageViewWidth(int imageViewWidth) {
//            this.imageViewWidth = imageViewWidth;
//        }
//
//        public int getImageViewHeight() {
//            return imageViewHeight;
//        }
//
//        public void setImageViewHeight(int imageViewHeight) {
//            this.imageViewHeight = imageViewHeight;
//        }
//
//        public boolean hasLongImage() {
//            return isLongImage;
//        }
//
//        public void setLongImage(boolean longImage) {
//            isLongImage = longImage;
//        }
//
//        public String getNetUrl() {
//            return netUrl;
//        }
//
//        public void setNetUrl(String netUrl) {
//            this.netUrl = netUrl;
//        }
//
//        public GlideUrl getGlideUrl() {
//            return glideUrl;
//        }
//
//        public void setGlideUrl(GlideUrl glideUrl) {
//            this.glideUrl = glideUrl;
//        }
//
//        @Override
//        public int describeContents() {
//            return 0;
//        }
//
//        @Override
//        public void writeToParcel(Parcel dest, int flags) {
//            dest.writeString(this.raw);
//            dest.writeString(this.size);
//            dest.writeInt(this.width);
//            dest.writeInt(this.propPart);
//            dest.writeInt(this.height);
//            dest.writeInt(this.file_id);
//            dest.writeString(this.imgUrl);
//            dest.writeString(this.type);
//            dest.writeInt(this.currentWith);
//            dest.writeInt(this.imageViewWidth);
//            dest.writeInt(this.imageViewHeight);
//            dest.writeByte(this.isLongImage ? (byte) 1 : (byte) 0);
//            dest.writeString(this.netUrl);
//        }
//
//        public ImagesBean() {
//        }
//
//        protected ImagesBean(Parcel in) {
//            this.raw = in.readString();
//            this.size = in.readString();
//            this.width = in.readInt();
//            this.propPart = in.readInt();
//            this.height = in.readInt();
//            this.file_id = in.readInt();
//            this.imgUrl = in.readString();
//            this.type = in.readString();
//            this.currentWith = in.readInt();
//            this.imageViewWidth = in.readInt();
//            this.imageViewHeight = in.readInt();
//            this.isLongImage = in.readByte() != 0;
//            this.netUrl = in.readString();
//        }
//
//        public static final Creator<ImagesBean> CREATOR = new Creator<ImagesBean>() {
//            @Override
//            public ImagesBean createFromParcel(Parcel source) {
//                return new ImagesBean(source);
//            }
//
//            @Override
//            public ImagesBean[] newArray(int size) {
//                return new ImagesBean[size];
//            }
//        };
//    }
//
//    public boolean getLiked() {
//        return this.liked;
//    }
//
//    public boolean getCollected() {
//        return this.collected;
//    }
//
//    public UserInfoBean getUser() {
//        return this.user;
//    }
//
//    public void setUser(UserInfoBean user) {
//        this.user = user;
//    }
//
//    public boolean getPinned() {
//        return this.pinned;
//    }
//
//    public List<PostDigListBean> getDigs() {
//        return this.digs;
//    }
//
//    public void setDigs(List<PostDigListBean> digs) {
//        this.digs = digs;
//    }
//
//    public CirclePostListBean2() {
//    }
//
//    @Generated(hash = 1718835547)
//    public CirclePostListBean2(Long id, Long post_mark, long group_id, Long user_id, String title, String summary, int likes_count, int like_count,
//                               String feed_content, int feed_from, int comments_count, int feed_comment_count, String feed_latitude, String feed_longtitude, int feed_view_count,
//                               String feed_geohash, int audit_status, long feed_mark, String repostable_type, int repostable_id, int hot, int views_count, boolean liked,
//                               boolean has_like, boolean collected, boolean has_collect, String created_at, String updated_at, String excellent_at, UserInfoBean user,
//                               List<ImagesBean> images, List<CirclePostCommentBean> comments, int state, int reward_amount, int reward_number, String body, CircleInfo group,
//                               boolean pinned, List<PostDigListBean> digs, String deleted_at, int feed_digg_count, boolean has_digg, long amount, List<DynamicLikeBean> likes,
//                               boolean paid, List<Integer> diggs, PaidNote paid_node, UserInfoBean userInfoBean, Long hot_creat_time, boolean isFollowed, String sendFailMessage,
//                               int top, List<DynamicDigListBean> digUserInfoList, RewardsCountBean reward, Video video, int index, List<TopicListBean> topics,
//                               Letter mLetter) {
//        this.id = id;
//        this.post_mark = post_mark;
//        this.group_id = group_id;
//        this.user_id = user_id;
//        this.title = title;
//        this.summary = summary;
//        this.likes_count = likes_count;
//        this.like_count = like_count;
//        this.feed_content = feed_content;
//        this.feed_from = feed_from;
//        this.comments_count = comments_count;
//        this.feed_comment_count = feed_comment_count;
//        this.feed_latitude = feed_latitude;
//        this.feed_longtitude = feed_longtitude;
//        this.feed_view_count = feed_view_count;
//        this.feed_geohash = feed_geohash;
//        this.audit_status = audit_status;
//        this.feed_mark = feed_mark;
//        this.repostable_type = repostable_type;
//        this.repostable_id = repostable_id;
//        this.hot = hot;
//        this.views_count = views_count;
//        this.liked = liked;
//        this.has_like = has_like;
//        this.collected = collected;
//        this.has_collect = has_collect;
//        this.created_at = created_at;
//        this.updated_at = updated_at;
//        this.excellent_at = excellent_at;
//        this.user = user;
//        this.images = images;
//        this.comments = comments;
//        this.state = state;
//        this.reward_amount = reward_amount;
//        this.reward_number = reward_number;
//        this.body = body;
//        this.group = group;
//        this.pinned = pinned;
//        this.digs = digs;
//        this.deleted_at = deleted_at;
//        this.feed_digg_count = feed_digg_count;
//        this.has_digg = has_digg;
//        this.amount = amount;
//        this.likes = likes;
//        this.paid = paid;
//        this.diggs = diggs;
//        this.paid_node = paid_node;
//        this.userInfoBean = userInfoBean;
//        this.hot_creat_time = hot_creat_time;
//        this.isFollowed = isFollowed;
//        this.sendFailMessage = sendFailMessage;
//        this.top = top;
//        this.digUserInfoList = digUserInfoList;
//        this.reward = reward;
//        this.video = video;
//        this.index = index;
//        this.topics = topics;
//        this.mLetter = mLetter;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//
//        CirclePostListBean2 that = (CirclePostListBean2) o;
//
//        if (group_id != that.group_id) {
//            return false;
//        }
//        return id.equals(that.id);
//    }
//
//    @Override
//    public int hashCode() {
//        int result = id.hashCode();
//        result = 31 * result + (int) (group_id ^ (group_id >>> 32));
//        return result;
//    }
//
//    public static class CirclePostImageConvert extends BaseConvert<List<ImagesBean>> {
//    }
//
//    public static class CirclePostCommentConvert extends BaseConvert<List<CirclePostCommentBean>> {
//    }
//
//    public static class PostDigListConvert extends BaseConvert<List<PostDigListBean>> {
//    }
//
//    public static class CircleInfoConvert extends BaseConvert<CircleInfo> {
//    }
//
//    @Override
//    public Object clone() {
//        try {
//            return super.clone();
//        } catch (CloneNotSupportedException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public void handleData() {
//        if (images != null) {
//            int imageCount = images.size();
//            for (int i = 0; i < imageCount; i++) {
//                dealImageBean(images.get(i), i, imageCount);
//            }
//        }
//
//        if (created_at != null) {
//            friendlyTime = TimeUtils.getTimeFriendlyNormal(created_at);
//        }
//        if (summary != null) {
//            friendlyContent = summary.replaceAll(MarkdownConfig.NETSITE_FORMAT, Link.DEFAULT_NET_SITE);
//            friendlyContent = friendlyContent.replaceAll(MarkdownConfig.IMAGE_FORMAT, "");
//        }
//    }
//
//    public void dealImageBean(ImagesBean imageBean, int i, int imageCount) {
//        if (imageBean.getWidth() == 0) {
//            imageBean.setWidth(DEFALT_IMAGE_WITH);
//        }
//        if (imageBean.getHeight() == 0) {
//            imageBean.setHeight(DEFALT_IMAGE_HEIGHT);
//        }
//
//        // 计算宽高，从 size 中分离
//        int netWidth = imageBean.getWidth();
//        int netHeight = imageBean.getHeight();
//
//
//        int currenCloums;
//        int part;
//        switch (imageCount) {
//            case 1:
//                currenCloums = part = 1;
//                break;
//            case 9:
//                currenCloums = 3;
//                part = 1;
//                break;
//            case 2:
//                part = 1;
//                currenCloums = 2;
//                break;
//            case 3:
//                part = 1;
//                currenCloums = 3;
//                break;
//            case 4:
//                part = 1;
//                currenCloums = 2;
//                break;
//            case 5:
//                currenCloums = 3;
//                if (i == 0) {
//                    part = 2;
//                } else if (i == 1 || i == 2) {
//                    part = 1;
//                } else {
//                    currenCloums = 2;
//                    part = 1;
//                }
//                break;
//            case 6:
//                part = i == 0 ? 2 : 1;
//                currenCloums = 3;
//                break;
//            case 7:
//                if (i == 0 || i == 3 || i == 4) {
//                    part = 2;
//                } else {
//                    part = 1;
//                }
//                currenCloums = 3;
//                break;
//            case 8:
//                if (i == 3 || i == 4) {
//                    part = 2;
//                } else {
//                    part = 1;
//                }
//                currenCloums = 3;
//                break;
//            default:
//                currenCloums = 1;
//                part = 1;
//                break;
//        }
//        int currentWith = (ImageUtils.getmImageContainerWith() - (currenCloums - 1) * ImageUtils.getmDiverwith()) / currenCloums * part;
//        int proportion;
//        int with = netWidth > currentWith ? currentWith : netWidth;
//        float quality = (float) with / (float) netWidth;
//        proportion = (int) (quality * 100);
//        proportion = proportion > 100 ? 100 : proportion;
//        imageBean.setCurrentWith(currentWith);
//
//        imageBean.setGlideUrl(ImageUtils.imagePathConvertV2(true, imageBean.getFile_id(), currentWith, currentWith,
//                proportion, AppApplication.getTOKEN()));
//        if (imageCount == 1) {
//            with = currentWith;
//            int height = (with * netHeight / netWidth);
//            int mImageMaxHeight = ImageUtils.getmImageMaxHeight();
//            height = height > mImageMaxHeight ? mImageMaxHeight : height;
//            // 单张图最小高度
//            height = height < DEFALT_IMAGE_HEIGHT ? DEFALT_IMAGE_HEIGHT : height;
//            // 这个不知道好久才有用哎
//            proportion = with * 100 / netWidth;
//            imageBean.setGlideUrl(ImageUtils.imagePathConvertV2(true, imageBean.getFile_id(), 0, 0
//                    , proportion, AppApplication.getTOKEN()));
//            imageBean.setImageViewWidth(with);
//            imageBean.setImageViewHeight(height);
//        }
//        imageBean.setPropPart(proportion);
//        imageBean.setLongImage(ImageUtils.isLongImage(netHeight, netWidth));
//    }
//
//    public String getExcellent_at() {
//        return this.excellent_at;
//    }
//
//    public void setExcellent_at(String excellent_at) {
//        this.excellent_at = excellent_at;
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        super.writeToParcel(dest, flags);
//        dest.writeValue(this.id);
//        dest.writeValue(this.post_mark);
//        dest.writeLong(this.group_id);
//        dest.writeValue(this.user_id);
//        dest.writeString(this.title);
//        dest.writeString(this.summary);
//        dest.writeInt(this.likes_count);
//        dest.writeInt(this.comments_count);
//        dest.writeInt(this.views_count);
//        dest.writeByte(this.liked ? (byte) 1 : (byte) 0);
//        dest.writeByte(this.collected ? (byte) 1 : (byte) 0);
//        dest.writeString(this.created_at);
//        dest.writeString(this.updated_at);
//        dest.writeString(this.excellent_at);
//        dest.writeParcelable(this.user, flags);
//        dest.writeTypedList(this.images);
//        dest.writeTypedList(this.comments);
//        dest.writeInt(this.state);
//        dest.writeInt(this.reward_amount);
//        dest.writeInt(this.reward_number);
//        dest.writeString(this.body);
//        dest.writeParcelable(this.group, flags);
//        dest.writeByte(this.pinned ? (byte) 1 : (byte) 0);
//        dest.writeTypedList(this.digs);
//        dest.writeString(this.friendlyTime);
//        dest.writeString(this.friendlyContent);
//        dest.writeString(this.group_name);
//    }
//
//    public int getLike_count() {
//        return this.like_count;
//    }
//
//    public void setLike_count(int like_count) {
//        this.like_count = like_count;
//    }
//
//    public String getFeed_content() {
//        return this.feed_content;
//    }
//
//    public void setFeed_content(String feed_content) {
//        this.feed_content = feed_content;
//    }
//
//    public int getFeed_from() {
//        return this.feed_from;
//    }
//
//    public void setFeed_from(int feed_from) {
//        this.feed_from = feed_from;
//    }
//
//    public int getFeed_comment_count() {
//        return this.feed_comment_count;
//    }
//
//    public void setFeed_comment_count(int feed_comment_count) {
//        this.feed_comment_count = feed_comment_count;
//    }
//
//    public String getFeed_latitude() {
//        return this.feed_latitude;
//    }
//
//    public void setFeed_latitude(String feed_latitude) {
//        this.feed_latitude = feed_latitude;
//    }
//
//    public String getFeed_longtitude() {
//        return this.feed_longtitude;
//    }
//
//    public void setFeed_longtitude(String feed_longtitude) {
//        this.feed_longtitude = feed_longtitude;
//    }
//
//    public int getFeed_view_count() {
//        return this.feed_view_count;
//    }
//
//    public void setFeed_view_count(int feed_view_count) {
//        this.feed_view_count = feed_view_count;
//    }
//
//    public String getFeed_geohash() {
//        return this.feed_geohash;
//    }
//
//    public void setFeed_geohash(String feed_geohash) {
//        this.feed_geohash = feed_geohash;
//    }
//
//    public int getAudit_status() {
//        return this.audit_status;
//    }
//
//    public void setAudit_status(int audit_status) {
//        this.audit_status = audit_status;
//    }
//
//    public long getFeed_mark() {
//        return this.feed_mark;
//    }
//
//    public void setFeed_mark(long feed_mark) {
//        this.feed_mark = feed_mark;
//    }
//
//    public String getRepostable_type() {
//        return this.repostable_type;
//    }
//
//    public void setRepostable_type(String repostable_type) {
//        this.repostable_type = repostable_type;
//    }
//
//    public int getRepostable_id() {
//        return this.repostable_id;
//    }
//
//    public void setRepostable_id(int repostable_id) {
//        this.repostable_id = repostable_id;
//    }
//
//    public int getHot() {
//        return this.hot;
//    }
//
//    public void setHot(int hot) {
//        this.hot = hot;
//    }
//
//    public boolean getHas_like() {
//        return this.has_like;
//    }
//
//    public void setHas_like(boolean has_like) {
//        this.has_like = has_like;
//    }
//
//    public boolean getHas_collect() {
//        return this.has_collect;
//    }
//
//    public void setHas_collect(boolean has_collect) {
//        this.has_collect = has_collect;
//    }
//
//    public String getDeleted_at() {
//        return this.deleted_at;
//    }
//
//    public void setDeleted_at(String deleted_at) {
//        this.deleted_at = deleted_at;
//    }
//
//    public int getFeed_digg_count() {
//        return this.feed_digg_count;
//    }
//
//    public void setFeed_digg_count(int feed_digg_count) {
//        this.feed_digg_count = feed_digg_count;
//    }
//
//    public boolean getHas_digg() {
//        return this.has_digg;
//    }
//
//    public void setHas_digg(boolean has_digg) {
//        this.has_digg = has_digg;
//    }
//
//    public long getAmount() {
//        return this.amount;
//    }
//
//    public void setAmount(long amount) {
//        this.amount = amount;
//    }
//
//    public List<DynamicLikeBean> getLikes() {
//        return this.likes;
//    }
//
//    public void setLikes(List<DynamicLikeBean> likes) {
//        this.likes = likes;
//    }
//
//    public boolean getPaid() {
//        return this.paid;
//    }
//
//    public void setPaid(boolean paid) {
//        this.paid = paid;
//    }
//
//    public List<Integer> getDiggs() {
//        return this.diggs;
//    }
//
//    public void setDiggs(List<Integer> diggs) {
//        this.diggs = diggs;
//    }
//
//    public PaidNote getPaid_node() {
//        return this.paid_node;
//    }
//
//    public void setPaid_node(PaidNote paid_node) {
//        this.paid_node = paid_node;
//    }
//
//    public Long getHot_creat_time() {
//        return this.hot_creat_time;
//    }
//
//    public void setHot_creat_time(Long hot_creat_time) {
//        this.hot_creat_time = hot_creat_time;
//    }
//
//    public boolean getIsFollowed() {
//        return this.isFollowed;
//    }
//
//    public void setIsFollowed(boolean isFollowed) {
//        this.isFollowed = isFollowed;
//    }
//
//    public String getSendFailMessage() {
//        return this.sendFailMessage;
//    }
//
//    public void setSendFailMessage(String sendFailMessage) {
//        this.sendFailMessage = sendFailMessage;
//    }
//
//    public int getTop() {
//        return this.top;
//    }
//
//    public void setTop(int top) {
//        this.top = top;
//    }
//
//    public List<DynamicDigListBean> getDigUserInfoList() {
//        return this.digUserInfoList;
//    }
//
//    public void setDigUserInfoList(List<DynamicDigListBean> digUserInfoList) {
//        this.digUserInfoList = digUserInfoList;
//    }
//
//    public RewardsCountBean getReward() {
//        return this.reward;
//    }
//
//    public void setReward(RewardsCountBean reward) {
//        this.reward = reward;
//    }
//
//    public Video getVideo() {
//        return this.video;
//    }
//
//    public void setVideo(Video video) {
//        this.video = video;
//    }
//
//    public int getIndex() {
//        return this.index;
//    }
//
//    public void setIndex(int index) {
//        this.index = index;
//    }
//
//    public List<TopicListBean> getTopics() {
//        return this.topics;
//    }
//
//    public void setTopics(List<TopicListBean> topics) {
//        this.topics = topics;
//    }
//
//    public Letter getMLetter() {
//        return this.mLetter;
//    }
//
//    public void setMLetter(Letter mLetter) {
//        this.mLetter = mLetter;
//    }
//
//    protected CirclePostListBean2(Parcel in) {
//        super(in);
//        this.id = (Long) in.readValue(Long.class.getClassLoader());
//        this.post_mark = (Long) in.readValue(Long.class.getClassLoader());
//        this.group_id = in.readLong();
//        this.user_id = (Long) in.readValue(Long.class.getClassLoader());
//        this.title = in.readString();
//        this.summary = in.readString();
//        this.likes_count = in.readInt();
//        this.comments_count = in.readInt();
//        this.views_count = in.readInt();
//        this.liked = in.readByte() != 0;
//        this.collected = in.readByte() != 0;
//        this.created_at = in.readString();
//        this.updated_at = in.readString();
//        this.excellent_at = in.readString();
//        this.user = in.readParcelable(UserInfoBean.class.getClassLoader());
//        this.images = in.createTypedArrayList(ImagesBean.CREATOR);
//        this.comments = in.createTypedArrayList(CirclePostCommentBean.CREATOR);
//        this.state = in.readInt();
//        this.reward_amount = in.readInt();
//        this.reward_number = in.readInt();
//        this.body = in.readString();
//        this.group = in.readParcelable(CircleInfo.class.getClassLoader());
//        this.pinned = in.readByte() != 0;
//        this.digs = in.createTypedArrayList(PostDigListBean.CREATOR);
//        this.friendlyTime = in.readString();
//        this.friendlyContent = in.readString();
//        this.group_name = in.readString();
//    }
//
//    public static final Creator<CirclePostListBean2> CREATOR = new Creator<CirclePostListBean2>() {
//        @Override
//        public CirclePostListBean2 createFromParcel(Parcel source) {
//            return new CirclePostListBean2(source);
//        }
//
//        @Override
//        public CirclePostListBean2[] newArray(int size) {
//            return new CirclePostListBean2[size];
//        }
//    };
//}
