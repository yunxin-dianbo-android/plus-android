package com.zhiyicx.thinksnsplus.data.source.repository.i;

import com.zhiyicx.thinksnsplus.data.beans.notify.UserNotifyMsgBean;

import rx.Observable;

/**
 * @Describe 通知相关
 * @Author Jungle68
 * @Date 2017/12/25
 * @Contact master.jungle68@gmail.com
 */
public interface INotificationRepository {


    String SYSTEMMSG = "system";
    String AT = "at";
    String COMMENT = "comment";
    String LIKE = "like";
    String FOLLOW = "follow";


    /**
     * 打赏动态
     */
    String REWARD_FEEDS = "reward:feeds";

    /**
     * 打赏资讯
     */
    String REWARD_NEWS = "reward:news";

    /**
     * 打赏用户
     */
    String REWARD_USER = "reward";

    /**
     * 打赏帖子
     */
    String GROUP_POST_REWARD = "group:post-reward";
    /**
     * 用户认证（注意区分通过驳回）
     */
    String USER_CERTIFICATION = "user-certification";
    String CERTIFICATION_REJECTED = "rejected";
    String CERTIFICATION_PASSED = "passed";
    /**
     * 加圈申请
     */
    String GROUP_JOIN = "group:join";
    /**
     * 回答被采纳
     */
    String QA_ANSWER_ADOPTION = "qa:answer-adoption";
    String QA_ANSWER_ADOPTION_1 = "question:answer";

    /**
     * 问题邀请回答
     */
    String QA_INVITATION = "qa:invitation";

    /**
     * 回答被大赏
     */
    String QA_ANSWER_REWARD = "qa:reward";

    /**
     * 动态评论置顶审核
     */
    String PINNED_FEED_COMMENT = "pinned:feed/comment";

    /**
     * 资讯评论置顶审核
     */
    String PINNED_NEWS_COMMENT = "pinned:news/comment";

    /**
     * 帖子评论置顶审核
     */
    String GROUP_COMMENT_PINNED = "group:comment-pinned";
    String GROUP_SEND_COMMENT_PINNED = "group:send-comment-pinned";

    /**
     * 帖子置顶审核
     */
    String GROUP_POST_PINNED = "group:post-pinned";




    /**
     * 获取通知列表
     * @param type
     * @param page
     * @return
     */
    Observable<UserNotifyMsgBean> getSystemMsg(String type, int page);

    /**
     * 标记所有通知阅读
     *
     * @return
     */
    Observable<Object> makeNotificationAllReaded();

    Observable<Object> makeNotificationReaded(String notificationType);
}
