package com.zhiyicx.thinksnsplus.modules.dynamic;

import com.zhiyicx.common.base.BaseJsonV2;
import com.zhiyicx.thinksnsplus.data.beans.AddSearchKeyResInfo;
import com.zhiyicx.thinksnsplus.data.beans.CommentedBean;
import com.zhiyicx.thinksnsplus.data.beans.DigedBean;
import com.zhiyicx.thinksnsplus.data.beans.TopSuperStarBean;
import com.zhiyicx.thinksnsplus.data.beans.UploadPostCommentResInfo;
import com.zhiyicx.thinksnsplus.data.beans.notify.AtMeaasgeBean;
import com.zhiyicx.thinksnsplus.data.beans.DynamicCommentBean;
import com.zhiyicx.thinksnsplus.data.beans.DynamicCommentToll;
import com.zhiyicx.thinksnsplus.data.beans.DynamicDetailBeanV2;
import com.zhiyicx.thinksnsplus.data.beans.DynamicDigListBean;
import com.zhiyicx.thinksnsplus.data.beans.SendDynamicDataBeanV2;

import java.util.List;

import rx.Observable;

/**
 * @Describe
 * @Author Jungle68
 * @Date 2017/2/24
 * @Contact master.jungle68@gmail.com
 */

public interface IDynamicReppsitory {
    /**
     * publish dynamic V2
     *
     * @param dynamicDetailBean dynamic content
     * @return basejson, object is null
     */
    Observable<BaseJsonV2<Object>> sendDynamicV2(SendDynamicDataBeanV2 dynamicDetailBean);


    Observable<List<TopSuperStarBean>> getPostHotSuperStar();

    /**
     * get dynamic list
     *
     * @param type       "" 代表最新；follows 代表关注 ； hots 代表热门
     * @param after      用来翻页的记录id(对应数据体里的 feed_id ,最新和关注选填)
     * @param user_id    动态所属人
     * @param isLoadMore 是否是刷新
     * @param screen     type = users 时可选，paid-付费动态 pinned - 置顶动态
     * @param id     可选，多个动态 ID 使用 , 进行分割；如果存在本参数，除了 direction 外，其他参数均失效。
     * @return dynamic list
     */
    Observable<List<DynamicDetailBeanV2>> getDynamicListV2(String type, Long after, String keyWord,Long user_id, boolean isLoadMore,
                                                           String screen,String id);

    /**
     * get dynamic list
     *
     * @param comment_id       评论id
     * @return dynamic list
     */
    Observable<AddSearchKeyResInfo> handleLike4Comment(Long comment_id);
    /**
     * get dynamic list
     *
     * @param comment_id       评论id
     * @return dynamic list
     */
    Observable<AddSearchKeyResInfo> handleDeleteLike4Comment(Long comment_id);

    Observable<List<DynamicDetailBeanV2>> getHotDynamicV2(String type, Long after, String search, /*Long userId, */final boolean isLoadMore,
                                                          String chooseType/*, String id*/);
    /**
     * 动态点赞
     *
     * @param feed_id
     * @return
     */
    void handleLike(boolean isLiked, final Long feed_id);

    /**
     * 获取用户收到的@
     *
     * @param index
     * @param limit
     * @param direction
     * @return
     */
    Observable<List<AtMeaasgeBean>> getAtMessages(int index, Integer limit, String direction);

    Observable<List<AtMeaasgeBean>> getAtMessages(String type, Integer page);
    Observable<List<CommentedBean>> getCommentMessages(String type, Integer page);
    Observable<List<DigedBean>> getLikeMessages(String type, Integer page);

    /**
     * 删除动态
     *
     * @param feed_id
     */
    void deleteDynamic(final Long feed_id);

    /**
     * 删除评论
     *
     * @param feed_id
     * @param comment_id
     */
    void deleteCommentV2(final Long feed_id, Long comment_id);

    /**
     * 发送评论
     *
     * @param commentContent
     * @param feed_id
     * @param reply_to_user_id
     * @param comment_mark
     */
    void sendCommentV2(String commentContent, final Long feed_id, Long reply_to_user_id, Long comment_mark);

    /**
     * 发布评论
     * @param feedId
     * @param body
     * @param reply_user
     * @param reply_comment_id
     */
    Observable<UploadPostCommentResInfo> sendCommentV3(Integer feedId, String body, Integer reply_user, Integer reply_comment_id);

    void updateOrInsertDynamicV2(List<DynamicDetailBeanV2> datas, String type);


    Observable<List<DynamicDigListBean>> getDynamicDigListV2(Long feed_id, Long max_id);

    /**
     * 一条动态的评论列表
     *
     * @param feed_mark dyanmic feed mark
     * @param feed_id   dyanmic detail id
     * @param max_id    max_id
     * @return
     */
    Observable<List<DynamicCommentBean>> getDynamicCommentListV2(Long feed_mark, Long feed_id, Long max_id);


    /**
     * 获取动态详情 V2
     *
     * @param feed_id 动态id
     * @return
     */
    Observable<DynamicDetailBeanV2> getDynamicDetailBeanV2(Long feed_id);

    /**
     * 设置动态评论收费 V2
     *
     * @param feed_id 动态id
     * @param amout   收费金额
     * @return
     */
    Observable<DynamicCommentToll> setDynamicCommentToll(Long feed_id, int amout);

    Observable<BaseJsonV2<Integer>> stickTop(long feed_id, double amount, int day,String psd);

    Observable<BaseJsonV2<Integer>> commentStickTop(long feed_id, long comment_id, double amount, int day,String psd);

    Observable<DynamicCommentToll> tollDynamicComment(Long feed_id, int amount);

    /**
     * 获取某个人的动态列表
     */
    Observable<List<DynamicDetailBeanV2>> getDynamicListForSomeone(Long user_id, Long max_id, String screen);




}