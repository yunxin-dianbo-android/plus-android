package com.zhiyicx.thinksnsplus.data.source.repository;

import android.util.SparseArray;

import com.zhiyicx.baseproject.base.BaseListBean;
import com.zhiyicx.baseproject.base.TSListFragment;
import com.zhiyicx.common.base.BaseJsonV2;
import com.zhiyicx.thinksnsplus.data.beans.CirclePostCommentBean;
import com.zhiyicx.thinksnsplus.data.beans.CirclePostListBean;
import com.zhiyicx.thinksnsplus.data.beans.TopCircleJoinReQuestBean;
import com.zhiyicx.thinksnsplus.data.beans.TopDynamicCommentBean;
import com.zhiyicx.thinksnsplus.data.beans.TopNewsCommentListBean;
import com.zhiyicx.thinksnsplus.data.beans.TopPostCommentListBean;
import com.zhiyicx.thinksnsplus.data.beans.TopPostListBean;
import com.zhiyicx.thinksnsplus.data.beans.TopSuperStarBean;
import com.zhiyicx.thinksnsplus.data.beans.UserInfoBean;
import com.zhiyicx.thinksnsplus.data.beans.circle.CirclePostBean;
import com.zhiyicx.thinksnsplus.data.source.local.UserInfoBeanGreenDaoImpl;
import com.zhiyicx.thinksnsplus.data.source.remote.CircleClient;
import com.zhiyicx.thinksnsplus.data.source.remote.DynamicClient;
import com.zhiyicx.thinksnsplus.data.source.remote.InfoMainClient;
import com.zhiyicx.thinksnsplus.data.source.remote.ServiceManager;
import com.zhiyicx.thinksnsplus.data.source.repository.i.IMessageRepository;
import com.zhiyicx.thinksnsplus.data.source.repository.i.IMessageReviewRepository;
import com.zhiyicx.thinksnsplus.modules.circle.detailv2.adapter.PostTypeChoosePopAdapter;
import com.zhiyicx.thinksnsplus.modules.home.message.messagereview.MessageReviewContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @Author Jliuer
 * @Date 2017/07/05
 * @Email Jliuer@aliyun.com
 * @Description
 */
public class MessageReviewRepository implements IMessageReviewRepository {

    DynamicClient mDynamicClient;
    InfoMainClient mInfoMainClient;
    CircleClient mCircleClient;

    @Inject
    UserInfoRepository mUserInfoRepository;

    @Inject
    public MessageReviewRepository(ServiceManager serviceManager) {
        mDynamicClient = serviceManager.getDynamicClient();
        mInfoMainClient = serviceManager.getInfoMainClient();
        mCircleClient = serviceManager.getCircleClient();
    }

    @Override
    public Observable<List<TopDynamicCommentBean>> getDynamicReviewComment(int after) {
        return dealDynamicCommentBean(mDynamicClient.getDynamicReviewComment(after, TSListFragment.DEFAULT_PAGE_SIZE));
    }

    @Override
    public Observable<List<TopNewsCommentListBean>> getNewsReviewComment(int after) {
        return dealNewsCommentBean(mInfoMainClient.getNewsReviewComment(after, TSListFragment.DEFAULT_PAGE_SIZE));
    }

    @Override
    public Observable<List<TopPostCommentListBean>> getPostReviewComment(int after) {
        return mCircleClient.getPostReviewComment(after, TSListFragment.DEFAULT_PAGE_SIZE, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<TopPostListBean>> getPostReview(Long circleId, int after) {
        return mCircleClient.getPostReview(after, TSListFragment.DEFAULT_PAGE_SIZE, circleId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //type "hot"
    @Override
    public Observable<List<CirclePostListBean>> getHotPost(Long circleId, int after,String type) {


//        return dealWithPostList(mCircleClient.getPostListFromCircleV2(circleId, TSListFragment.DEFAULT_PAGE_SIZE, (int) maxId, "group")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .map(circlePostBean -> {
//                    List<CirclePostListBean> pinnedData = circlePostBean.getPinned();
//                    List<CirclePostListBean> data = new ArrayList<>(circlePostBean.getFeeds());
//                    // 仅最新发布显示置顶内容
//                    if (excellent == null && pinnedData != null && type.equals(PostTypeChoosePopAdapter.MyPostTypeEnum.LATEST_POST.value)) {
//                        for (CirclePostListBean postListBean : pinnedData) {
//                            for (CirclePostListBean post : data) {
//                                // 删除置顶重复的
//                                if (postListBean.getId().equals(post.getId())) {
//                                    circlePostBean.getFeeds().remove(post);
//                                }
//                            }
//                            postListBean.setPinned(true);
//                        }
//                        pinnedData.addAll(circlePostBean.getFeeds());
//                        return pinnedData;
//                    } else {
//                        return circlePostBean.getFeeds();
//                    }
//                }));
        //circleId, TSListFragment.DEFAULT_PAGE_SIZE, (int) maxId, "group"
        return dealWithPostList(mCircleClient.getHotPostV2(0l, TSListFragment.DEFAULT_PAGE_SIZE, after, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(circlePostBean -> {
            List<CirclePostListBean> pinnedData = circlePostBean.getPinned();
            List<CirclePostListBean> data = new ArrayList<>(circlePostBean.getFeeds());
            // 仅最新发布显示置顶内容
            if (pinnedData != null) {
                for (CirclePostListBean postListBean : pinnedData) {
                    for (CirclePostListBean post : data) {
                        // 删除置顶重复的
                        if (postListBean.getId().equals(post.getId())) {
                            circlePostBean.getFeeds().remove(post);
                        }
                    }
                    postListBean.setPinned(true);
                }
                pinnedData.addAll(circlePostBean.getFeeds());
                return pinnedData;
            } else {
                return circlePostBean.getFeeds();
            }
        }));
    }


    private Observable<List<CirclePostListBean>> dealWithPostList(Observable<List<CirclePostListBean>> observable) {

        return observable
                .observeOn(Schedulers.io())
                .flatMap(postListBeans -> {
                    final List<Object> user_ids = new ArrayList<>();
                    List<CirclePostCommentBean> comments = new ArrayList<>();
                    for (CirclePostListBean circlePostListBean : postListBeans) {
                        circlePostListBean.handleData();
                        user_ids.add(circlePostListBean.getUser_id());
                        if (circlePostListBean.getComments() == null || circlePostListBean.getComments().isEmpty()) {
                            continue;
                        }
                        comments.addAll(circlePostListBean.getComments());
                        for (CirclePostCommentBean commentListBean : circlePostListBean.getComments()) {
                            user_ids.add(commentListBean.getUser_id());
                            user_ids.add(commentListBean.getReply_to_user_id());
                        }
                    }
//                    mCirclePostCommentBeanGreenDao.saveMultiData(comments);
                    if (user_ids.isEmpty()) {
                        return Observable.just(postListBeans);
                    }
                    return mUserInfoRepository.getUserInfo(user_ids)
                            .map(userinfobeans -> {
                                SparseArray<UserInfoBean> userInfoBeanSparseArray = new SparseArray<>();
                                for (UserInfoBean userInfoBean : userinfobeans) {
                                    userInfoBeanSparseArray.put(userInfoBean.getUser_id().intValue(), userInfoBean);
                                }
                                for (CirclePostListBean circlePostListBean : postListBeans) {
                                    circlePostListBean.setUserInfoBean(userInfoBeanSparseArray.get(circlePostListBean
                                            .getUser_id().intValue()));
                                    if (circlePostListBean.getComments() == null || circlePostListBean.getComments()
                                            .isEmpty()) {
                                        continue;
                                    }
                                    for (int i = 0; i < circlePostListBean.getComments().size(); i++) {
                                        UserInfoBean tmpUserinfo = userInfoBeanSparseArray.get((int) circlePostListBean.getComments()
                                                .get(i).getUser_id());
                                        if (tmpUserinfo != null) {
                                            circlePostListBean.getComments().get(i).setCommentUser(tmpUserinfo);
                                        }
                                        if (circlePostListBean.getComments().get(i).getReply_to_user_id() == 0) {
                                            // 如果 reply_user_id = 0 回复动态
                                            UserInfoBean userInfoBean = new UserInfoBean();
                                            userInfoBean.setUser_id(0L);
                                            circlePostListBean.getComments().get(i).setReplyUser(userInfoBean);
                                        } else {
                                            if (userInfoBeanSparseArray.get((int) circlePostListBean.getComments()
                                                    .get(i).getReply_to_user_id()) != null) {
                                                circlePostListBean.getComments().get(i).setReplyUser
                                                        (userInfoBeanSparseArray.get((int) circlePostListBean.getComments()
                                                                .get(i).getReply_to_user_id()));
                                            }
                                        }
                                    }

                                }
                                return postListBeans;
                            });
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

//    @Override
//    public Observable<List<TopPostListBean>> getHotPost(Long circleId, int after) {
//        //circleId, TSListFragment.DEFAULT_PAGE_SIZE, (int) maxId, "group"
//        return mCircleClient.getHotPostV2(0l, TSListFragment.DEFAULT_PAGE_SIZE, after, "hot")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//    }

    /**
     * 获取热名明星
     *
     * @return
     */
    @Override
    public Observable<List<TopSuperStarBean>> getPostHotSuperStar() {
        return mCircleClient.getPostHotSuperStar()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<TopCircleJoinReQuestBean>> getCircleJoinRequest(int after) {
        return mCircleClient.getCircleJoinRequest(after, TSListFragment.DEFAULT_PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseJsonV2> approvedTopComment(Long feed_id, int comment_id, int pinned_id) {
        return mDynamicClient.approvedDynamicTopComment(feed_id, comment_id, pinned_id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseJsonV2> refuseTopComment(int pinned_id) {
        return mDynamicClient.refuseDynamicTopComment(pinned_id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseJsonV2> approvedNewsTopComment(Long feed_id, int comment_id, int pinned_id) {
        return mInfoMainClient.approvedNewsTopComment(feed_id, comment_id, pinned_id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseJsonV2> refuseNewsTopComment(int news_id, Long comment_id, int pinned_id) {
        return mInfoMainClient.refuseNewsTopComment(news_id, comment_id, pinned_id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseJsonV2> approvedPostTopComment(Integer comment_id) {
        return mCircleClient.approvedPostTopComment(comment_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseJsonV2> refusePostTopComment(Integer comment_id) {
        return mCircleClient.refusePostTopComment(comment_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseJsonV2> approvedPostTop(Long psotId) {
        return mCircleClient.approvedPostTop(psotId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseJsonV2> refusePostTop(Long psotId) {
        return mCircleClient.refusePostTop(psotId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseJsonV2> refuseCircleJoin(BaseListBean result) {
        TopCircleJoinReQuestBean data = (TopCircleJoinReQuestBean) result;
        return mCircleClient.dealCircleJoin(TopCircleJoinReQuestBean.TOP_REFUSE, data.getGroup_id(), data.getMember_info().getId().intValue())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseJsonV2> approvedCircleJoin(Long circleId, int memberId) {
        return mCircleClient.dealCircleJoin(TopCircleJoinReQuestBean.TOP_SUCCESS, circleId.intValue(), memberId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseJsonV2> deleteTopComment(Long feed_id, int comment_id) {
        return null;
    }

    private Observable<List<TopDynamicCommentBean>> dealDynamicCommentBean(Observable<List<TopDynamicCommentBean>> data) {
        return data.flatMap(rechargeListBeen -> {
            final List<Object> user_ids = new ArrayList<>();
            for (TopDynamicCommentBean TopDynamicCommentBean : rechargeListBeen) {
                user_ids.add(TopDynamicCommentBean.getUser_id());
            }
            return mUserInfoRepository.getUserInfo(user_ids).map(userinfobeans -> {
                SparseArray<UserInfoBean> userInfoBeanSparseArray = new SparseArray<>();
                for (UserInfoBean userInfoBean : userinfobeans) {
                    userInfoBeanSparseArray.put(userInfoBean.getUser_id().intValue(), userInfoBean);
                }
                for (int i = 0; i < rechargeListBeen.size(); i++) {
                    rechargeListBeen.get(i).setUserInfoBean(userInfoBeanSparseArray.get(rechargeListBeen.get(i).getUser_id().intValue()));
                }
                return rechargeListBeen;
            });
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<List<TopNewsCommentListBean>> dealNewsCommentBean(Observable<List<TopNewsCommentListBean>> data) {
        return data.flatMap((Func1<List<TopNewsCommentListBean>, Observable<List<TopNewsCommentListBean>>>) rechargeListBeen -> {
            final List<Object> userIds = new ArrayList<>();
            for (TopNewsCommentListBean topdynamiccommentbean : rechargeListBeen) {
                userIds.add(topdynamiccommentbean.getUser_id());
            }
            return mUserInfoRepository.getUserInfo(userIds).map(userinfobeans -> {
                SparseArray<UserInfoBean> userInfoBeanSparseArray = new SparseArray<>();
                for (UserInfoBean userInfoBean : userinfobeans) {
                    userInfoBeanSparseArray.put(userInfoBean.getUser_id().intValue(), userInfoBean);
                }
                for (int i = 0; i < rechargeListBeen.size(); i++) {
                    rechargeListBeen.get(i).setCommentUser(userInfoBeanSparseArray.get((int) rechargeListBeen.get(i).getUser_id()));
                    rechargeListBeen.get(i).setReplyUser(userInfoBeanSparseArray.get((int) rechargeListBeen.get(i).getTarget_user()));
                }
                return rechargeListBeen;
            });
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
