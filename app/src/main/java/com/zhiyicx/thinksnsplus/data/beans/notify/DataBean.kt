package com.zhiyicx.thinksnsplus.data.beans.notify

import android.os.Parcel
import android.os.Parcelable
import android.text.TextUtils
import com.google.gson.annotations.SerializedName
import com.zhiyicx.baseproject.config.ApiConfig.APP_LIKE_FEED
import com.zhiyicx.thinksnsplus.data.beans.UserInfoBean
import com.zhiyicx.thinksnsplus.data.source.repository.i.INotificationRepository
import java.io.Serializable

class DataBean() : Parcelable, Serializable {
    /**
     * type : reward
     * sender : {"id":16,"name":"用户9527"}
     * amount : 1
     * unit : settings.currency_name
     */

    var type: String? = null
    @SerializedName(alternate = arrayOf("contents"), value = "content")
    private var content: String? = null
    var state: String? = null
    var sender: SenderBean? = null
    var group: SenderBean? = null
    var user: SenderBean? = null
    var question: SenderBean? = null
    var answer: SenderBean? = null
    var news: SenderBean? = null
    val comment: SenderBean? = null
    val post: SenderBean? = null
    val feed: SenderBean? = null
    var amount: Int = 0
    var feed_id: Long? = null
    val group_id: Long? = null
    var unit: String? = null
    private var hasReply: Boolean = false

    @SerializedName(value = "commentable", alternate = arrayOf("resource"))
    var resource: ResourceableBean? = null

    constructor(parcel: Parcel) : this() {
        type = parcel.readString()
        content = parcel.readString()
        state = parcel.readString()
        sender = parcel.readParcelable(SenderBean::class.java.classLoader)
        group = parcel.readParcelable(SenderBean::class.java.classLoader)
        user = parcel.readParcelable(SenderBean::class.java.classLoader)
        question = parcel.readParcelable(SenderBean::class.java.classLoader)
        answer = parcel.readParcelable(SenderBean::class.java.classLoader)
        news = parcel.readParcelable(SenderBean::class.java.classLoader)
        amount = parcel.readInt()
        feed_id = parcel.readValue(Long::class.java.classLoader) as? Long
        unit = parcel.readString()
        hasReply = parcel.readByte() != 0.toByte()
        resource = parcel.readParcelable(ResourceableBean::class.java.classLoader)
    }

    fun getContent(): String? {
        if (!TextUtils.isEmpty(type)) {
            when (type) {
                INotificationRepository.REWARD_FEEDS -> content = sender?.name + "打赏了你的动态"
                INotificationRepository.GROUP_POST_REWARD -> content = sender?.name + "打赏了你的帖子「" + post?.name + "」"
                INotificationRepository.REWARD_NEWS -> content = "你的资讯《" + news?.name + "》被" + sender?.name + "打赏了" + amount + unit
                INotificationRepository.REWARD_USER -> content = sender?.name + "打赏了你"
                INotificationRepository.PINNED_FEED_COMMENT -> content = if (INotificationRepository.CERTIFICATION_REJECTED == state) {
                    "你的动态评论「" + comment?.name + "」的置顶请求被拒绝"
                } else {
                    "你的动态评论「" + comment?.name + "」的置顶请求已通过"
                }
                INotificationRepository.PINNED_NEWS_COMMENT -> content = if (INotificationRepository.CERTIFICATION_REJECTED == state) {
                    "你的资讯《" + news?.name + "》评论「" + comment?.name + "」的置顶请求被拒绝"
                } else {
                    "你的资讯《" + news?.name + "》评论「" + comment?.name + "」的置顶请求已通过"
                }
                INotificationRepository.GROUP_COMMENT_PINNED -> content = if (INotificationRepository.CERTIFICATION_REJECTED == state) {
                    "你的评论置顶请求被拒绝"
                } else {
                    "你的评论置顶请求已通过"
                }
                INotificationRepository.GROUP_SEND_COMMENT_PINNED -> content = "用户在你的帖子「" + post?.name + "」下申请评论置顶"
                INotificationRepository.GROUP_POST_PINNED -> content = if (INotificationRepository.CERTIFICATION_REJECTED == state) {
                    "你的帖子「" + post?.name + "」的置顶请求已通过"
                } else {
                    "你的帖子「" + post?.name + "」的置顶请求被拒绝"
                }
                INotificationRepository.QA_ANSWER_REWARD -> content = answer?.userInfoBean?.name + "打赏了你的回答"
                INotificationRepository.GROUP_JOIN -> content = if (INotificationRepository.CERTIFICATION_REJECTED == state) {
                    "你被拒绝加入「" + group?.name + "」圈子"
                } else if(INotificationRepository.CERTIFICATION_PASSED == state){
                    "你被同意加入「" + group?.name + "」圈子"
                }else{
                    user?.name + "请求加入「" + group?.name + "」圈子"
                }
                INotificationRepository.QA_ANSWER_ADOPTION, INotificationRepository.QA_ANSWER_ADOPTION_1 -> content = "你提交的问题回答被采纳"
                INotificationRepository.QA_INVITATION -> content = sender?.name + "邀请你回答问题「" + question?.name + "」"
                INotificationRepository.USER_CERTIFICATION -> content = if (INotificationRepository.CERTIFICATION_REJECTED == state) {
                    "你申请的身份认证已被驳回，驳回理由：" + content
                } else {
                    "你申请的身份认证已通过"
                }
                else -> if (null != resource) {
                    when (resource?.type) {
                        APP_LIKE_FEED -> content = if (hasReply) {
                            sender?.name + "回复你：" + content
                        } else {
                            sender?.name + "评论了你的动态：" + content
                        }
                    }//                            case APP_LIKE_GROUP_POST:
                    //                            case APP_LIKE_MUSIC:
                    //                            case APP_LIKE_MUSIC_SPECIALS:
                    //                            case APP_LIKE_NEWS:
                    //                            case ApiConfig.APP_QUESTIONS:
                    //                            case ApiConfig.APP_QUESTIONS_ANSWER:
                }
            }
        }
        return if (TextUtils.isEmpty(content)) "" else content
    }

    fun hasReply(): Boolean {
        return hasReply
    }



    fun setHasReply(hasReply: Boolean) {
        this.hasReply = hasReply
    }

    fun setContent(content: String) {
        this.content = content
    }

    class SenderBean() : Serializable, Parcelable {


        /**
         * id : 16
         * name : 用户9527
         */

        var id: Long? = null
        @SerializedName(alternate = arrayOf("subject", "contents", "body", "title"), value = "name")
        var name: String? = null
        var text_body: String? = null
        @SerializedName(alternate = arrayOf("user"), value = "mUserInfoBean")
        var userInfoBean: UserInfoBean? = null

        constructor(parcel: Parcel) : this() {
            id = parcel.readValue(Long::class.java.classLoader) as? Long
            name = parcel.readString()
            text_body = parcel.readString()
            userInfoBean = parcel.readParcelable(UserInfoBean::class.java.classLoader)
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeValue(id)
            parcel.writeString(name)
            parcel.writeString(text_body)
            parcel.writeParcelable(userInfoBean, flags)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<SenderBean> {
            override fun createFromParcel(parcel: Parcel): SenderBean {
                return SenderBean(parcel)
            }

            override fun newArray(size: Int): Array<SenderBean?> {
                return arrayOfNulls(size)
            }
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type)
        parcel.writeString(content)
        parcel.writeString(state)
        parcel.writeParcelable(sender, flags)
        parcel.writeParcelable(group, flags)
        parcel.writeParcelable(user, flags)
        parcel.writeParcelable(question, flags)
        parcel.writeParcelable(answer, flags)
        parcel.writeParcelable(news, flags)
        parcel.writeInt(amount)
        parcel.writeValue(feed_id)
        parcel.writeString(unit)
        parcel.writeByte(if (hasReply) 1 else 0)
        parcel.writeParcelable(resource, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DataBean> {
        override fun createFromParcel(parcel: Parcel): DataBean {
            return DataBean(parcel)
        }

        override fun newArray(size: Int): Array<DataBean?> {
            return arrayOfNulls(size)
        }
    }
}