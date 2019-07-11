package com.zhiyicx.thinksnsplus.modules.dynamic.list;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.klinker.android.link_builder.Link;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhiyi.rxdownload3.RxDownload;
import com.zhiyi.rxdownload3.core.Failed;
import com.zhiyi.rxdownload3.core.Status;
import com.zhiyi.rxdownload3.helper.UtilsKt;
import com.zhiyicx.baseproject.base.TSFragment;
import com.zhiyicx.baseproject.config.ApiConfig;
import com.zhiyicx.baseproject.impl.share.UmengSharePolicyImpl;
import com.zhiyicx.baseproject.share.OnShareCallbackListener;
import com.zhiyicx.baseproject.share.Share;
import com.zhiyicx.baseproject.share.ShareContent;
import com.zhiyicx.baseproject.share.SharePolicy;
import com.zhiyicx.common.base.BaseJsonV2;
import com.zhiyicx.common.config.MarkdownConfig;
import com.zhiyicx.common.dagger.scope.FragmentScoped;
import com.zhiyicx.common.utils.ConvertUtils;
import com.zhiyicx.common.utils.TimeUtils;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.base.AppApplication;
import com.zhiyicx.thinksnsplus.base.AppBasePresenter;
import com.zhiyicx.thinksnsplus.base.BaseSubscribeForV2;
import com.zhiyicx.thinksnsplus.base.fordownload.AppListPresenterForDownload;
import com.zhiyicx.thinksnsplus.config.BackgroundTaskRequestMethodConfig;
import com.zhiyicx.thinksnsplus.config.EventBusTagConfig;
import com.zhiyicx.thinksnsplus.data.beans.AllAdverListBean;
import com.zhiyicx.thinksnsplus.data.beans.BackgroundRequestTaskBean;
import com.zhiyicx.thinksnsplus.data.beans.DynamicCommentBean;
import com.zhiyicx.thinksnsplus.data.beans.DynamicDetailBeanV2;
import com.zhiyicx.thinksnsplus.data.beans.PurChasesBean;
import com.zhiyicx.thinksnsplus.data.beans.RealAdvertListBean;
import com.zhiyicx.thinksnsplus.data.beans.TopSuperStarBean;
import com.zhiyicx.thinksnsplus.data.beans.UserInfoBean;
import com.zhiyicx.thinksnsplus.data.source.local.AllAdvertListBeanGreenDaoImpl;
import com.zhiyicx.thinksnsplus.data.source.local.DynamicCommentBeanGreenDaoImpl;
import com.zhiyicx.thinksnsplus.data.source.local.DynamicDetailBeanV2GreenDaoImpl;
import com.zhiyicx.thinksnsplus.data.source.local.SendDynamicDataBeanV2GreenDaoImpl;
import com.zhiyicx.thinksnsplus.data.source.local.TopDynamicBeanGreenDaoImpl;
import com.zhiyicx.thinksnsplus.data.source.repository.BaseDynamicRepository;
import com.zhiyicx.thinksnsplus.modules.shortvideo.helper.ZhiyiVideoView;
import com.zhiyicx.thinksnsplus.service.backgroundtask.BackgroundTaskManager;
import com.zhiyicx.thinksnsplus.utils.ImageUtils;
import com.zhiyicx.thinksnsplus.utils.TSShareUtils;

import org.jetbrains.annotations.NotNull;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import cn.jzvd.JZMediaManager;
import cn.jzvd.JZVideoPlayerManager;
import io.reactivex.disposables.Disposable;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static cn.jzvd.JZVideoPlayer.URL_KEY_DEFAULT;
import static com.zhiyicx.thinksnsplus.data.beans.DynamicDetailBeanV2.DYNAMIC_LIST_CONTENT_MAX_SHOW_SIZE;
import static com.zhiyicx.thinksnsplus.data.beans.DynamicListAdvert.DEFAULT_ADVERT_FROM_TAG;
import static com.zhiyicx.thinksnsplus.data.beans.TopDynamicBean.TYPE_HOT;
import static com.zhiyicx.thinksnsplus.data.beans.TopDynamicBean.TYPE_NEW;
import static com.zhiyicx.thinksnsplus.modules.dynamic.detail.DynamicDetailFragment.DYNAMIC_DETAIL_DATA;
import static com.zhiyicx.thinksnsplus.modules.dynamic.detail.DynamicDetailFragment.DYNAMIC_LIST_NEED_REFRESH;
import static com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListBaseItem.DEFALT_IMAGE_WITH;

/**
 * ThinkSNS Plus
 * Copyright (c) 2018 Chengdu ZhiYiChuangXiang Technology Co., Ltd.
 *
 * @author Jliuer
 * @Date 18/07/26 15:36
 * @Email Jliuer@aliyun.com
 * @Description 动态 Presenter 基类
 */
@FragmentScoped
public abstract class BaseDynamicPresenter<V extends DynamicContract.View<P>, P extends DynamicContract.Presenter> extends AppListPresenterForDownload<V>
        implements DynamicContract.Presenter, OnShareCallbackListener {


    protected DynamicDetailBeanV2GreenDaoImpl mDynamicDetailBeanV2GreenDao;
    protected DynamicCommentBeanGreenDaoImpl mDynamicCommentBeanGreenDao;

    protected SendDynamicDataBeanV2GreenDaoImpl mSendDynamicDataBeanV2GreenDao;
    protected TopDynamicBeanGreenDaoImpl mTopDynamicBeanGreenDao;

    protected SharePolicy mSharePolicy;
    protected AllAdvertListBeanGreenDaoImpl mAllAdvertListBeanGreenDao;
    protected BaseDynamicRepository mDynamicRepository;
    private Subscription dynamicLisSub;

    public DynamicDetailBeanV2 getmShareDynamic() {
        return mShareDynamic;
    }

    public void setmShareDynamic(DynamicDetailBeanV2 mShareDynamic) {
        this.mShareDynamic = mShareDynamic;
    }

    protected DynamicDetailBeanV2 mShareDynamic;
    private Subscription subscribe;


    protected BaseDynamicPresenter(V rootView
            , AllAdvertListBeanGreenDaoImpl allAdvertListBeanGreenDao
            , DynamicDetailBeanV2GreenDaoImpl dynamicDetailBeanV2GreenDao
            , DynamicCommentBeanGreenDaoImpl dynamicCommentBeanGreenDao
            , SendDynamicDataBeanV2GreenDaoImpl sendDynamicDataBeanV2GreenDao
            , TopDynamicBeanGreenDaoImpl topDynamicBeanGreenDao
            , BaseDynamicRepository baseDynamicRepository
    ) {
        super(rootView);
        mAllAdvertListBeanGreenDao = allAdvertListBeanGreenDao;
        mDynamicDetailBeanV2GreenDao = dynamicDetailBeanV2GreenDao;
        mDynamicCommentBeanGreenDao = dynamicCommentBeanGreenDao;
        mSendDynamicDataBeanV2GreenDao = sendDynamicDataBeanV2GreenDao;
        mTopDynamicBeanGreenDao = topDynamicBeanGreenDao;
        mDynamicRepository = baseDynamicRepository;
    }


    @Override
    protected boolean useEventBus() {
        return true;
    }

    /**
     * 数据加载，上拉和下拉
     *
     * @param maxId      当前获取到数据的最大 id
     * @param isLoadMore 加载状态
     */
    @Override
    public void requestNetData(Long maxId, final boolean isLoadMore) {

        if (ApiConfig.DYNAMIC_TYPE_EMPTY.equals(mRootView.getDynamicType())) {
            mRootView.onNetResponseSuccess(new ArrayList<>(), isLoadMore);
            return;
        }
        if (dynamicLisSub != null && !dynamicLisSub.isUnsubscribed()) {
            dynamicLisSub.unsubscribe();
        }
        if (ApiConfig.DYNAMIC_TYPE_FIND.equals(mRootView.getDynamicType())) {
            if (maxId == null || maxId.intValue() == 0) {
//                mDynamicRepository.getPostHotSuperStar();
                Observable observableSuperStar = mDynamicRepository.getPostHotSuperStar();
                Subscription commentSubSuperStar = observableSuperStar.subscribe(new BaseSubscribeForV2() {
                    @Override
                    protected void onSuccess(Object data) {
                        List<TopSuperStarBean> result = (List<TopSuperStarBean>) data;
                        mRootView.onNetSuccessHotSuperStar(result);
//                  mRootView.onNetResponseSuccess(result, isLoadMore);
                    }

                    @Override
                    protected void onFailure(String message, int code) {
                        mRootView.showMessage(message);
                        mRootView.onResponseError(null, isLoadMore);
                    }

                    @Override
                    protected void onException(Throwable throwable) {
                        mRootView.showMessage(throwable.getMessage());
                        mRootView.onResponseError(throwable, isLoadMore);
                    }
                });
                addSubscrebe(commentSubSuperStar);
            }
            dynamicLisSub = mDynamicRepository.getHotDynamicV2(mRootView.getDynamicType(), maxId, mRootView.getKeyWord(), isLoadMore, null)
                    .observeOn(Schedulers.io())
                    .map(listBaseJson -> {
                        List<DynamicDetailBeanV2> data;
                        // 更新数据库
                        insertOrUpdateDynamicDBV2(listBaseJson);
                        // 如果是刷新，并且获取到了数据，更新发布的动态 ,把发布的动态信息放到请求数据的前面
                        if (!isLoadMore) {
                            if (ApiConfig.DYNAMIC_TYPE_NEW.equals(mRootView.getDynamicType()) || ApiConfig.DYNAMIC_TYPE_FOLLOWS.equals(mRootView.getDynamicType())) {
                                data = getDynamicBeenFromDBV2();
                                data.addAll(listBaseJson);
                            } else {
                                data = new ArrayList<>(listBaseJson);
                            }
                        } else {
                            data = new ArrayList<>(listBaseJson);
                        }
                        // 把自己发的评论加到评论列表的前面
                        for (int i = 0; i < listBaseJson.size(); i++) {
                            // 处理友好显示数据
                            listBaseJson.get(i).handleData();
                            List<DynamicCommentBean> dynamicCommentBeen = mDynamicCommentBeanGreenDao.getMySendingComment(listBaseJson.get(i)
                                    .getFeed_mark());
                            if (!dynamicCommentBeen.isEmpty()) {
                                dynamicCommentBeen.addAll(listBaseJson.get(i).getComments());
                                listBaseJson.get(i).getComments().clear();
                                listBaseJson.get(i).getComments().addAll(dynamicCommentBeen);
                            }
                        }

                        return data;
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseSubscribeForV2<List<DynamicDetailBeanV2>>() {
                        @Override
                        protected void onSuccess(List<DynamicDetailBeanV2> data) {
                            mRootView.onNetResponseSuccess(data, isLoadMore);
                        }

                        @Override
                        protected void onFailure(String message, int code) {
                            mRootView.showMessage(message);
                        }

                        @Override
                        protected void onException(Throwable throwable) {
                            mRootView.onResponseError(throwable, isLoadMore);
                        }
                    });
        }else {
            //String type, Long after, String search, /*Long userId, */final boolean isLoadMore,String chooseType/*, String id*/
            dynamicLisSub = mDynamicRepository.getDynamicListV2(mRootView.getDynamicType(), maxId,
                    mRootView.getKeyWord(), null, isLoadMore, null, null)
                    .observeOn(Schedulers.io())
                    .map(listBaseJson -> {
                        List<DynamicDetailBeanV2> data;
                        // 更新数据库
                        insertOrUpdateDynamicDBV2(listBaseJson);
                        // 如果是刷新，并且获取到了数据，更新发布的动态 ,把发布的动态信息放到请求数据的前面
                        if (!isLoadMore) {
                            if (ApiConfig.DYNAMIC_TYPE_NEW.equals(mRootView.getDynamicType()) || ApiConfig.DYNAMIC_TYPE_FOLLOWS.equals(mRootView.getDynamicType())) {
                                data = getDynamicBeenFromDBV2();
                                data.addAll(listBaseJson);
                            } else {
                                data = new ArrayList<>(listBaseJson);
                            }
                        } else {
                            data = new ArrayList<>(listBaseJson);
                        }
                        // 把自己发的评论加到评论列表的前面
                        for (int i = 0; i < listBaseJson.size(); i++) {
                            // 处理友好显示数据
                            listBaseJson.get(i).handleData();
                            List<DynamicCommentBean> dynamicCommentBeen = mDynamicCommentBeanGreenDao.getMySendingComment(listBaseJson.get(i)
                                    .getFeed_mark());
                            if (!dynamicCommentBeen.isEmpty()) {
                                dynamicCommentBeen.addAll(listBaseJson.get(i).getComments());
                                listBaseJson.get(i).getComments().clear();
                                listBaseJson.get(i).getComments().addAll(dynamicCommentBeen);
                            }
                        }

                        return data;
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseSubscribeForV2<List<DynamicDetailBeanV2>>() {
                        @Override
                        protected void onSuccess(List<DynamicDetailBeanV2> data) {
                            mRootView.onNetResponseSuccess(data, isLoadMore);
                        }

                        @Override
                        protected void onFailure(String message, int code) {
                            mRootView.showMessage(message);
                        }

                        @Override
                        protected void onException(Throwable throwable) {
                            mRootView.onResponseError(throwable, isLoadMore);
                        }
                    });
        }

        addSubscrebe(dynamicLisSub);
    }

    @Override
    public void requestCacheData(Long maxId, boolean isLoadMore) {
        Subscription subscribe = Observable.just(1)
                .observeOn(Schedulers.io())
                .map(aLong -> {
                    List<DynamicDetailBeanV2> datas;
                    switch (mRootView.getDynamicType()) {
                        // 最新 这个分类下未发送成功的动态 需要处理，排列到 置顶动态之后
                        case ApiConfig.DYNAMIC_TYPE_FOLLOWS:
                            if (!isLoadMore) {
                                datas = getDynamicBeenFromDBV2();
                                datas.addAll(mDynamicDetailBeanV2GreenDao.getFollowedDynamicList(maxId));
                            } else {
                                datas = mDynamicDetailBeanV2GreenDao.getFollowedDynamicList(maxId);
                            }
                            break;
                        case ApiConfig.DYNAMIC_TYPE_HOTS:
                            datas = mDynamicDetailBeanV2GreenDao.getHotDynamicList(maxId);
                            List<DynamicDetailBeanV2> topHotDynamics = mTopDynamicBeanGreenDao.getTopDynamicByType(TYPE_HOT);
                            if (topHotDynamics != null) {
                                datas.addAll(0, topHotDynamics);
                            }
                            break;
                        case ApiConfig.DYNAMIC_TYPE_NEW:
                            // 刷新
                            if (!isLoadMore) {
                                datas = getDynamicBeenFromDBV2();
                                List<DynamicDetailBeanV2> cache = mDynamicDetailBeanV2GreenDao.getNewestDynamicList(maxId);
                                List<DynamicDetailBeanV2> topNewDynamics = mTopDynamicBeanGreenDao.getTopDynamicByType(TYPE_NEW);
                                int lastTopIndex = 0;
                                if (topNewDynamics != null) {
                                    lastTopIndex = topNewDynamics.size();
                                    cache.addAll(0, topNewDynamics);
                                }
                                cache.addAll(lastTopIndex, datas);
                                datas.clear();
                                datas.addAll(cache);
                            } else {
                                datas = mDynamicDetailBeanV2GreenDao.getNewestDynamicList(maxId);
                            }
                            break;
                        case ApiConfig.DYNAMIC_TYPE_MY_COLLECTION:
                            datas = mDynamicDetailBeanV2GreenDao.getMyCollectDynamic();
                            break;
                        default:
                            datas = new ArrayList<>();
                    }
                    for (int i = 0; i < datas.size(); i++) {
                        // 处理友好显示数据
                        datas.get(i).handleData();
                        if (datas.get(i).getFeed_mark() != null) {
                            datas.get(i).setComments(mDynamicCommentBeanGreenDao.getLocalComments(datas.get(i).getFeed_mark()));
                        }
                    }
                    return datas;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dynamicDetailBeanV2s -> mRootView.onCacheResponseSuccess(dynamicDetailBeanV2s, isLoadMore), Throwable::printStackTrace);
        addSubscrebe(subscribe);

    }

    protected void insertOrUpdateDynamicDBV2(@NotNull List<DynamicDetailBeanV2> data) {
        Observable.just(data)
                .observeOn(Schedulers.io())
                .subscribe(dynamicDetailBeanV2s -> mDynamicRepository.updateOrInsertDynamicV2(data, mRootView.getDynamicType()),
                        Throwable::printStackTrace);
    }

    /**
     * 此处需要先存入数据库，方便处理动态的状态，故此处不需要再次更新数据库
     *
     * @param data
     * @param isLoadMore
     * @return
     */
    @Override
    public boolean insertOrUpdateData(@NotNull List<DynamicDetailBeanV2> data, boolean isLoadMore) {
        return true;
    }

    /**
     * 列表中是否有了
     *
     * @param dynamicBean
     * @return
     */
    protected int[] hasDynamicContanied(DynamicDetailBeanV2 dynamicBean) {
        int size = mRootView.getListDatas().size();
        // 0 , 置顶数量；1，位置
        int count[] = new int[2];
        for (int i = 0; i < size; i++) {
            if (mRootView.getListDatas().get(i).getTop() == DynamicDetailBeanV2.TOP_SUCCESS) {
                count[0]++;
            }
            if (mRootView.getListDatas().get(i).getFeed_mark().equals(dynamicBean.getFeed_mark())) {
                mRootView.getListDatas().get(i).setState(dynamicBean.getState());
                mRootView.getListDatas().get(i).setSendFailMessage(dynamicBean.getSendFailMessage());
                mRootView.getListDatas().get(i).setId(dynamicBean.getId());
                count[1] = i;
                return count;
            }
        }
        count[1] = -1;
        return count;
    }

    /**
     * @return 我正在发布，或者发布失败的动态
     */
    @NonNull
    protected List<DynamicDetailBeanV2> getDynamicBeenFromDBV2() {
        if (AppApplication.getmCurrentLoginAuth() == null) {
            return new ArrayList<>();
        }
        return mDynamicDetailBeanV2GreenDao.getMySendingUnSuccessDynamic(AppApplication.getMyUserIdWithdefault());
    }

    /**
     * @return 我正在发布，或者发布失败的动态
     */
    @NonNull
    protected List<DynamicDetailBeanV2> getDynamicBeenFromDBWithTopic(Long topic) {
        if (AppApplication.getmCurrentLoginAuth() == null) {
            return new ArrayList<>();
        }
        return mDynamicDetailBeanV2GreenDao.getMySendingUnSuccessDynamicWithTopic(AppApplication.getMyUserIdWithdefault(), topic);
    }

    /**
     * handle like or cancle like in background
     *
     * @param isLiked true,do like ,or  cancle like
     * @param feed_id dynamic id
     * @param postion current item position
     */
    @Override
    public void handleLike(boolean isLiked, final Long feed_id, final int postion) {
        if (feed_id == null || feed_id == 0) {
            return;
        }
        mDynamicDetailBeanV2GreenDao.insertOrReplace(mRootView.getListDatas().get(postion));
        mDynamicRepository.handleLike(isLiked, feed_id);
    }

    @Override
    public void handleViewCount(Long feed_id, int position) {
        if (feed_id == null || feed_id == 0) {
            return;
        }
        mRootView.getListDatas().get(position).setFeed_view_count(mRootView.getListDatas().get(position).getFeed_view_count() + 1);
        mDynamicDetailBeanV2GreenDao.insertOrReplace(mRootView.getListDatas().get(position));
    }

    @Override
    public void reSendDynamic(int position) {
        // 将动态信息存入数据库

        mDynamicDetailBeanV2GreenDao.insertOrReplace(mRootView.getListDatas().get(position));
        // 发送动态
        BackgroundRequestTaskBean backgroundRequestTaskBean = new BackgroundRequestTaskBean();
        backgroundRequestTaskBean.setMethodType(BackgroundTaskRequestMethodConfig.SEND_DYNAMIC_V2);
        HashMap<String, Object> params = new HashMap<>();
        // feed_mark作为参数
        params.put("params", mRootView.getListDatas().get(position).getFeed_mark());
        params.put("sendDynamicDataBean", mSendDynamicDataBeanV2GreenDao.getSendDynamicDataBeanV2ByFeedMark
                (String.valueOf(mRootView.getListDatas().get(position).getFeed_mark())));
        backgroundRequestTaskBean.setParams(params);
        BackgroundTaskManager.getInstance(mContext).addBackgroundRequestTask(backgroundRequestTaskBean);
    }

    @Override
    public void deleteCommentV2(DynamicDetailBeanV2 dynamicBean, int dynamicPositon, long comment_id, int commentPosition) {
        if (comment_id > 0) {
            mRootView.getListDatas().get(dynamicPositon).setFeed_comment_count(dynamicBean.getFeed_comment_count() - 1);
        }

        mDynamicDetailBeanV2GreenDao.insertOrReplace(mRootView.getListDatas().get(dynamicPositon));
        if (!dynamicBean.getComments().isEmpty()) {
            mDynamicCommentBeanGreenDao.deleteSingleCache(dynamicBean.getComments().get(commentPosition));
            mRootView.getListDatas().get(dynamicPositon).getComments().remove(commentPosition);
        }
        mRootView.refreshData(dynamicPositon);
        if (comment_id > 0) {
            mDynamicRepository.deleteCommentV2(dynamicBean.getId(), comment_id);
        }
    }

    @Override
    public void reSendComment(DynamicCommentBean commentBean, long feed_id) {
        commentBean.setState(DynamicCommentBean.SEND_ING);
        mDynamicRepository.sendCommentV2(commentBean.getComment_content(), feed_id, commentBean.getReply_to_user_id(),
                commentBean.getComment_mark());
        mRootView.refreshData();
    }

    /**
     * 删除动态
     *
     * @param dynamicBean
     * @param position
     */
    @Override
    public void deleteDynamic(DynamicDetailBeanV2 dynamicBean, int position) {
        if (position == -1) {
            return;
        }
        deleteDynamic(dynamicBean);
    }

    @Override
    public void sendCommentV2(int mCurrentPostion, long replyToUserId, String commentContent) {
        DynamicCommentBean creatComment = new DynamicCommentBean();
        creatComment.setState(DynamicCommentBean.SEND_ING);
        creatComment.setComment_content(commentContent);
        creatComment.setFeed_mark(mRootView.getListDatas().get(mCurrentPostion).getFeed_mark());
        String commentMark = AppApplication.getmCurrentLoginAuth().getUser_id() + "" + System.currentTimeMillis();
        creatComment.setComment_mark(Long.parseLong(commentMark));
        creatComment.setReply_to_user_id(replyToUserId);
        //当回复动态的时候
        if (replyToUserId == 0) {
            UserInfoBean userInfoBean = new UserInfoBean();
            userInfoBean.setUser_id(replyToUserId);
            creatComment.setReplyUser(userInfoBean);
        } else {
            creatComment.setReplyUser(mUserInfoBeanGreenDao.getSingleDataFromCache(replyToUserId));
        }
        creatComment.setUser_id(AppApplication.getmCurrentLoginAuth().getUser_id());
        creatComment.setCommentUser(mUserInfoBeanGreenDao.getSingleDataFromCache(AppApplication.getmCurrentLoginAuth().getUser_id()));
        creatComment.setCreated_at(TimeUtils.getCurrenZeroTimeStr());

        List<DynamicCommentBean> commentBeanList = new ArrayList<>();
        if (mRootView.getListDatas().get(mCurrentPostion).getComments() != null) {
            commentBeanList.addAll(mRootView.getListDatas().get(mCurrentPostion).getComments());
        }
        int lastTopIndex = 0;
        for (DynamicCommentBean commentBean : commentBeanList) {
            if (!commentBean.getPinned()) {
                lastTopIndex = commentBeanList.indexOf(commentBean);
                break;
            }
        }
        commentBeanList.add(lastTopIndex, creatComment);
        if (mRootView.getListDatas().get(mCurrentPostion).getComments() != null) {
            mRootView.getListDatas().get(mCurrentPostion).getComments().clear();
            mRootView.getListDatas().get(mCurrentPostion).getComments().addAll(commentBeanList);
        }
        mRootView.getListDatas().get(mCurrentPostion).setFeed_comment_count(mRootView.getListDatas().get(mCurrentPostion).getFeed_comment_count() +
                1);
        mRootView.refreshData();

        mDynamicDetailBeanV2GreenDao.insertOrReplace(mRootView.getListDatas().get(mCurrentPostion));
        mDynamicCommentBeanGreenDao.insertOrReplace(creatComment);
        mDynamicRepository.sendCommentV2(commentContent, mRootView.getListDatas().get(mCurrentPostion)
                .getId(), replyToUserId, creatComment.getComment_mark());
    }

    /**
     * 通过 feedMark 获取当前数据的位置
     *
     * @param feedMark
     * @return
     */
    @Override
    public int getCurrenPosiotnInDataList(long feedMark) {
        int position = -1;
        int size = mRootView.getListDatas().size();
        for (int i = 0; i < size; i++) {
            if (mRootView.getListDatas().get(i).getFeed_mark() != null && feedMark == mRootView.getListDatas().get(i).getFeed_mark()) {
                position = i;
                break;
            }
        }
        return position;
    }

    @Override
    public void handleCollect(DynamicDetailBeanV2 dynamicBean) {
        // 收藏
        // 修改数据
        // 旧状态
        boolean is_collection = dynamicBean.isHas_collect();
        dynamicBean.setHas_collect(!is_collection);
        boolean newCollectState = !is_collection;
        // 更新数据库
        mDynamicDetailBeanV2GreenDao.insertOrReplace(dynamicBean);
        // 通知服务器
        BackgroundRequestTaskBean backgroundRequestTaskBean;
        HashMap<String, Object> params = new HashMap<>();
        params.put("feed_id", dynamicBean.getId());
        // 后台处理
        if (newCollectState) {
            backgroundRequestTaskBean = new BackgroundRequestTaskBean(BackgroundTaskRequestMethodConfig.POST_V2, params);
            backgroundRequestTaskBean.setPath(String.format(ApiConfig.APP_PATH_HANDLE_COLLECT_V2_FORMAT,
                    dynamicBean.getId()));
        } else {
            backgroundRequestTaskBean = new BackgroundRequestTaskBean(BackgroundTaskRequestMethodConfig.DELETE_V2, params);
            backgroundRequestTaskBean.setPath(String.format(ApiConfig.APP_PATH_HANDLE_UNCOLLECT_V2_FORMAT,
                    dynamicBean.getId()));
        }

        BackgroundTaskManager.getInstance(mContext).addBackgroundRequestTask(backgroundRequestTaskBean);
        EventBus.getDefault().post(dynamicBean, EventBusTagConfig.EVENT_COLLECT_DYNAMIC);
    }

    @Override
    public void shareDynamic(DynamicDetailBeanV2 dynamicBean, Bitmap bitmap) {
        if (mSharePolicy == null) {
            if (mRootView instanceof Fragment) {
                mSharePolicy = new UmengSharePolicyImpl(((Fragment) mRootView).getActivity());
            } else {
                return;
            }
        }
        ((UmengSharePolicyImpl) mSharePolicy).setOnShareCallbackListener(this);
        ShareContent shareContent = new ShareContent();
        shareContent.setTitle(mContext.getString(R.string.share_dynamic, mContext.getString(R.string.app_name)));
        shareContent.setContent(TextUtils.isEmpty(dynamicBean.getFeed_content()) ? mContext.getString(R.string
                .share_default, mContext.getString(R.string.app_name)) : dynamicBean.getFeed_content());
        if (bitmap != null) {
            shareContent.setBitmap(bitmap);
        } else {
            shareContent.setBitmap(ConvertUtils.drawBg4Bitmap(Color.WHITE, BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.icon)));
        }
        if (dynamicBean.getFeed_from() == DEFAULT_ADVERT_FROM_TAG) {
            //广告的连接放在了 deleted_at 中
            shareContent.setUrl(dynamicBean.getDeleted_at());
        } else {
            shareContent.setUrl(TSShareUtils.convert2ShareUrl(String.format(ApiConfig.APP_PATH_SHARE_DYNAMIC, dynamicBean.getId()
                    == null ? "" : dynamicBean.getId())));
        }
        mSharePolicy.setShareContent(shareContent);
        mSharePolicy.showShare(((TSFragment) mRootView).getActivity());
    }

    @Override
    public void shareDynamic(DynamicDetailBeanV2 dynamicBean, Bitmap bitmap, List<UmengSharePolicyImpl.ShareBean> extraData) {
        mShareDynamic = dynamicBean;
        if (mSharePolicy == null) {
            if (mRootView instanceof Fragment) {
                mSharePolicy = new UmengSharePolicyImpl(((Fragment) mRootView).getActivity());
            } else {
                return;
            }
        }
        ((UmengSharePolicyImpl) mSharePolicy).setOnShareCallbackListener(this);
        ShareContent shareContent = new ShareContent();
        shareContent.setTitle(mContext.getString(R.string.share_dynamic, mContext.getString(R.string.app_name)));
        shareContent.setContent(TextUtils.isEmpty(dynamicBean.getFeed_content()) ? mContext.getString(R.string
                .share_default, mContext.getString(R.string.app_name)) : dynamicBean.getFeed_content());
        if (bitmap != null) {
            shareContent.setBitmap(bitmap);
        } else {
            shareContent.setBitmap(ConvertUtils.drawBg4Bitmap(Color.WHITE, BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.icon)));
        }
        if (dynamicBean.getFeed_from() == DEFAULT_ADVERT_FROM_TAG) {
            //广告的连接放在了 deleted_at 中
            shareContent.setUrl(dynamicBean.getDeleted_at());
        } else {
            shareContent.setUrl(TSShareUtils.convert2ShareUrl(String.format(ApiConfig.APP_PATH_SHARE_DYNAMIC, dynamicBean.getId()
                    == null ? "" : dynamicBean.getId())));
        }
        mSharePolicy.setShareContent(shareContent);
        mSharePolicy.showShare(((TSFragment) mRootView).getActivity(), extraData);
    }

    @Override
    public void shareDynamic(DynamicDetailBeanV2 dynamicBean, Bitmap bitmap, SHARE_MEDIA type) {
        if (mSharePolicy == null) {
            if (mRootView instanceof Fragment) {
                mSharePolicy = new UmengSharePolicyImpl(((Fragment) mRootView).getActivity());
            } else {
                return;
            }
        }
        ShareContent shareContent = new ShareContent();
        shareContent.setTitle(mContext.getString(R.string.share_dynamic, mContext.getString(R.string.app_name)));
        shareContent.setContent(TextUtils.isEmpty(dynamicBean.getFeed_content()) ? mContext.getString(R.string
                .share_default, mContext.getString(R.string.app_name)) : dynamicBean.getFeed_content());
        if (bitmap != null) {
            shareContent.setBitmap(bitmap);
        } else {
            shareContent.setBitmap(ConvertUtils.drawBg4Bitmap(Color.WHITE, BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.icon)));
        }
        shareContent.setUrl(TSShareUtils.convert2ShareUrl(String.format(ApiConfig.APP_PATH_SHARE_DYNAMIC, dynamicBean
                .getId()
                == null ? "" : dynamicBean.getId())));
        mSharePolicy.setShareContent(shareContent);
        switch (type) {
            case QQ:
                mSharePolicy.shareQQ(((TSFragment) mRootView).getActivity(), this);
                break;
            case QZONE:
                mSharePolicy.shareZone(((TSFragment) mRootView).getActivity(), this);
                break;
            case WEIXIN:
                mSharePolicy.shareWechat(((TSFragment) mRootView).getActivity(), this);
                break;
            case WEIXIN_CIRCLE:
                mSharePolicy.shareMoment(((TSFragment) mRootView).getActivity(), this);
                break;
            case SINA:
                mSharePolicy.shareWeibo(((TSFragment) mRootView).getActivity(), this);
                break;
            case MORE:
                String videoUrl = String.format(ApiConfig.APP_DOMAIN + ApiConfig.FILE_PATH,
                        dynamicBean.getVideo().getVideo_id());
                downloadFile(videoUrl);
                break;
            default:
        }

    }

    @Override
    public List<RealAdvertListBean> getBannerAdvert() {
        if (!com.zhiyicx.common.BuildConfig.USE_ADVERT || mAllAdvertListBeanGreenDao.getDynamicBannerAdvert() == null) {
            return new ArrayList<>();
        }
        AllAdverListBean allAdverListBean = mAllAdvertListBeanGreenDao.getDynamicBannerAdvert();
        if (allAdverListBean == null) {
            return null;
        }
        return allAdverListBean.getMRealAdvertListBeen();
    }

    @Override
    public List<RealAdvertListBean> getListAdvert() {
        if (!com.zhiyicx.common.BuildConfig.USE_ADVERT || mAllAdvertListBeanGreenDao.getDynamicListAdvert() == null) {
            return new ArrayList<>();
        }
        if (mAllAdvertListBeanGreenDao.getDynamicListAdvert() == null) {
            return new ArrayList<>();
        }
        return mAllAdvertListBeanGreenDao.getDynamicListAdvert().getMRealAdvertListBeen();
    }

    @Override
    public void checkNote(int note) {
        mCommentRepository.checkNote(note)
                .subscribe(new BaseSubscribeForV2<PurChasesBean>() {
                    @Override
                    protected void onSuccess(PurChasesBean data) {

                    }
                });
    }

    @Override
    public void payNote(final int dynamicPosition, final int imagePosition, int note, final boolean isImage, String psd) {
        if (handleTouristControl()) {
            return;
        }

        double amount;
        if (isImage) {
            amount = mRootView.getListDatas().get(dynamicPosition).getImages().get(imagePosition).getAmount();
        } else {
            amount = mRootView.getListDatas().get(dynamicPosition).getPaid_node().getAmount();
        }

        subscribe = handleIntegrationBlance((long) amount)
                .doOnSubscribe(() -> mRootView.showSnackLoadingMessage(mContext.getString(R
                        .string.ts_pay_check_handle_doing)))
                .flatMap(o -> mCommentRepository.paykNote(note, psd))
                .flatMap(stringBaseJsonV2 -> {
                    if (isImage) {
                        return Observable.just(stringBaseJsonV2);
                    }
                    return mDynamicRepository.getDynamicDetailBeanV2(mRootView.getListDatas().get(dynamicPosition).getId())
                            .flatMap(detailBeanV2 -> {
                                stringBaseJsonV2.setData(detailBeanV2.getFeed_content());
                                return Observable.just(stringBaseJsonV2);
                            });
                })
                .subscribe(new BaseSubscribeForV2<BaseJsonV2<String>>() {
                    @Override
                    protected void onSuccess(BaseJsonV2<String> data) {
                        mRootView.hideCenterLoading();
                        mRootView.paySuccess();
                        if (isImage) {
                            DynamicDetailBeanV2.ImagesBean imageBean = mRootView.getListDatas().get(dynamicPosition).getImages().get(imagePosition);
                            imageBean.setPaid(true);
                            int imageWith = imageBean.getCurrentWith();
                            if (imageWith == 0) {
                                imageWith = DEFALT_IMAGE_WITH;
                            }
                            // 重新给图片地址赋值 ,没付费的图片 w h 都是 0
                            imageBean.setGlideUrl(ImageUtils.imagePathConvertV2(true, imageBean.getFile(), imageWith, imageWith,
                                    imageBean.getPropPart(), AppApplication.getTOKEN()));
                        } else {
                            mRootView.getListDatas().get(dynamicPosition).getPaid_node().setPaid(true);
                            mRootView.getListDatas().get(dynamicPosition).setFeed_content(data.getData());
                            if (data.getData() != null) {
                                String friendlyContent = data.getData().replaceAll(MarkdownConfig.NETSITE_FORMAT, Link
                                        .DEFAULT_NET_SITE);
                                mRootView.getListDatas().get(dynamicPosition).setFriendlyContent(friendlyContent);
                            }
                        }
                        mRootView.refreshData(dynamicPosition);
                        mDynamicDetailBeanV2GreenDao.insertOrReplace(mRootView.getListDatas().get(dynamicPosition));
                        mRootView.showSnackSuccessMessage(mContext.getString(R.string.transaction_success));

                        Bundle bundle = new Bundle();
                        DynamicDetailBeanV2 dynamicDetailBeanV2 = mRootView.getListDatas().get(dynamicPosition);

                        bundle.putParcelable(DYNAMIC_DETAIL_DATA, dynamicDetailBeanV2);
                        bundle.putBoolean(DYNAMIC_LIST_NEED_REFRESH, true);
                        EventBus.getDefault().post(bundle, EventBusTagConfig.EVENT_UPDATE_DYNAMIC);
                    }

                    @Override
                    protected void onFailure(String message, int code) {
                        super.onFailure(message, code);
                        if (usePayPassword()) {
                            mRootView.payFailed(message);
                            return;
                        }
                        mRootView.showSnackErrorMessage(message);
                    }

                    @Override
                    protected void onException(Throwable throwable) {
                        super.onException(throwable);
                        if (isIntegrationBalanceCheck(throwable)) {
                            mRootView.paySuccess();
                            return;
                        }
                        if (usePayPassword()) {
                            mRootView.payFailed(mContext.getString(R.string.transaction_fail));
                            return;
                        }
                        mRootView.showSnackErrorMessage(mContext.getString(R.string.transaction_fail));
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        mRootView.hideCenterLoading();
                    }
                });
        addSubscrebe(subscribe);
    }


    @Override
    public void canclePay() {
        if (subscribe != null && !subscribe.isUnsubscribed()) {
            subscribe.unsubscribe();
        }
    }

    @Override
    public void onStart(Share share) {
    }

    @Override
    public void onSuccess(Share share) {
        mRootView.showSnackSuccessMessage(mContext.getString(R.string.share_sccuess));
    }

    @Override
    public void onError(Share share, Throwable throwable) {
        mRootView.showSnackErrorMessage(mContext.getString(R.string.share_fail));
    }

    @Override
    public void onCancel(Share share) {
        mRootView.showSnackSuccessMessage(mContext.getString(R.string.share_cancel));
    }

    /**
     * 处理发送评论数据
     *
     * @param dynamicCommentBean
     */
    @Subscriber(tag = EventBusTagConfig.EVENT_SEND_COMMENT_TO_DYNAMIC_LIST)
    public void handleSendComment(DynamicCommentBean dynamicCommentBean) {
        Subscription subscribe = Observable.just(dynamicCommentBean)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .map(dynamicCommentBean1 -> {
                    int size = mRootView.getListDatas().size();
                    int dynamicPosition = -1;
                    for (int i = 0; i < size; i++) {
                        if (dynamicCommentBean1.getFeed_mark().equals(mRootView.getListDatas().get(i).getFeed_mark())) {
                            dynamicPosition = i;
                            break;
                        }
                    }// 如果列表有当前评论
                    if (dynamicPosition != -1) {
                        int commentSize = mRootView.getListDatas().get(dynamicPosition).getComments().size();
                        for (int i = 0; i < commentSize; i++) {
                            if (mRootView.getListDatas().get(dynamicPosition).getComments().get(i).getFeed_mark().equals
                                    (dynamicCommentBean1.getFeed_mark())) {
                                mRootView.getListDatas().get(dynamicPosition).getComments().get(i).setState(dynamicCommentBean1.getState());
                                mRootView.getListDatas().get(dynamicPosition).getComments().get(i).setComment_id(dynamicCommentBean1.getComment_id());
                                mRootView.getListDatas().get(dynamicPosition).getComments().get(i).setComment_mark
                                        (dynamicCommentBean1.getComment_mark());
                                break;
                            }
                        }
                    }
                    return dynamicPosition;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    if (integer != -1) {
                        mRootView.refreshData();
                    }

                }, Throwable::printStackTrace);
        addSubscrebe(subscribe);

    }

    /**
     * 处理发送动态数据
     *
     * @param dynamicBean
     */
    @Subscriber(tag = EventBusTagConfig.EVENT_SEND_DYNAMIC_TO_LIST)
    public void handleSendDynamic(DynamicDetailBeanV2 dynamicBean) {
//
//        if (mRootView.getDynamicType().equals(ApiConfig.DYNAMIC_TYPE_NEW)
//                || mRootView.getDynamicType().equals(ApiConfig.DYNAMIC_TYPE_FOLLOWS)) {
//            Subscription subscribe = Observable.just(dynamicBean)
//                    .observeOn(Schedulers.computation())
//                    .map(dynamicDetailBeanV2 -> hasDynamicContanied(dynamicBean))
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(position -> {
//                        // 如果列表有当前数据
//                        if (position[1] != -1) {
////                            mRootView.showNewDynamic(position[1], dynamicBean.getMLetter() != null);
//                        } else {
//                            List<DynamicDetailBeanV2> temps = new ArrayList<>(mRootView.getListDatas());
//                            temps.add(position[0], dynamicBean);
//                            mRootView.getListDatas().clear();
//                            mRootView.getListDatas().addAll(temps);
//                            temps.clear();
//                            mRootView.showNewDynamic(position[0], dynamicBean.getMLetter() != null);
//                        }
//                    }, Throwable::printStackTrace);
//            addSubscrebe(subscribe);
//        }
    }

    /**
     * 动态详情处理了数据
     * 处理更新动态数据
     *
     * @param data
     */
    @Subscriber(tag = EventBusTagConfig.EVENT_UPDATE_DYNAMIC)
    public void updateDynamic(Bundle data) {
        Subscription subscribe = Observable.just(data)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.computation())
                .map(bundle -> {
                    boolean isNeedRefresh = bundle.getBoolean(DYNAMIC_LIST_NEED_REFRESH);
                    int dynamicPosition = -1;
                    if (isNeedRefresh) {
                        DynamicDetailBeanV2 dynamicBean = bundle.getParcelable(DYNAMIC_DETAIL_DATA);
                        if (dynamicBean == null) {
                            return dynamicPosition;
                        }
                        dynamicBean.handleData();
                        dynamicPosition = dynamicBean.getFeed_mark() == null ? -1 : getCurrenPosiotnInDataList(dynamicBean.getFeed_mark());
                        // 如果列表有当前评论
                        if (dynamicPosition != -1) {
                            mRootView.getListDatas().set(dynamicPosition, dynamicBean);
                        }
                    }
                    return isNeedRefresh ? dynamicPosition : -1;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    if (integer != -1) {
                        mRootView.refreshData(integer);
                    }

                }, Throwable::printStackTrace);
        addSubscrebe(subscribe);
    }

    @Subscriber(tag = EventBusTagConfig.DYNAMIC_LIST_DELETE_UPDATE)
    public void deleteDynamic(DynamicDetailBeanV2 dynamicBean) {
        if (dynamicBean == null) {
            return;
        }
        if (dynamicBean.getVideo() != null && JZVideoPlayerManager.getFirstFloor() != null) {
            String url = "";
            String videoUrl;
            if (TextUtils.isEmpty(dynamicBean.getVideo().getUrl())) {
                videoUrl = String.format(ApiConfig.APP_DOMAIN + ApiConfig.FILE_PATH,
                        dynamicBean.getVideo().getVideo_id());
            } else {
                videoUrl = dynamicBean.getVideo().getUrl();
            }
            LinkedHashMap map = (LinkedHashMap) JZVideoPlayerManager.getFirstFloor().dataSourceObjects[0];
            if (map != null) {
                url = map.get(URL_KEY_DEFAULT).toString();
            }
            if (url.equals(videoUrl)) {
                if (JZVideoPlayerManager.getCurrentJzvd() != null) {
                    if (JZVideoPlayerManager.getCurrentJzvd().currentState == ZhiyiVideoView.CURRENT_STATE_PREPARING
                            || JZVideoPlayerManager.getCurrentJzvd().currentState == ZhiyiVideoView.CURRENT_STATE_PREPARING_CHANGING_URL) {
                        ZhiyiVideoView.releaseAllVideos();
                    }
                }
                ZhiyiVideoView.goOnPlayOnPause();
            }
        }
        Observable.just(dynamicBean)
                .observeOn(Schedulers.io())
                .map(dynamicDetailBeanV2 -> {
                    int size = mRootView.getListDatas().size();
                    int dataPosition = -1;
                    boolean hasFeedMark = dynamicDetailBeanV2.getFeed_mark() != null && dynamicDetailBeanV2.getFeed_mark() != 0;
                    for (int i = 0; i < size; i++) {
                        if (hasFeedMark) {
                            if (mRootView.getListDatas().get(i) != null && dynamicDetailBeanV2.getFeed_mark().equals(mRootView.getListDatas()
                                    .get(i)
                                    .getFeed_mark())) {
                                dataPosition = i;
                                return dataPosition;
                            }
                        } else {
                            if (mRootView.getListDatas().get(i) != null && dynamicDetailBeanV2.getId() != null && dynamicDetailBeanV2.getId()
                                    .equals
                                            (mRootView.getListDatas()
                                                    .get(i)
                                                    .getId())) {
                                dataPosition = i;

                                return dataPosition;
                            }
                        }

                    }
                    return dataPosition;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscribeForV2<Integer>() {
                    @Override
                    protected void onSuccess(Integer dataPositon) {
                        if (dataPositon == -1) {
                            return;
                        }
                        try {
                            mDynamicDetailBeanV2GreenDao.deleteSingleCache(dynamicBean);
                            mRootView.getListDatas().remove(dataPositon.intValue());
                            mRootView.refreshData();
                        } catch (Exception e) {
                            e.printStackTrace();
                            return;
                        }
                        if (dynamicBean.getId() != null && dynamicBean.getId() != 0) {
                            mDynamicRepository.deleteDynamic(dynamicBean.getId());
                        }
                    }
                });
    }

    @Subscriber(tag = EventBusTagConfig.EVENT_NETSTATE_CHANGE)
    public void netstateChange(boolean hasWifi) {
        if (JZVideoPlayerManager.getCurrentJzvd() != null && JZMediaManager.isPlaying() && !hasWifi) {
            JZVideoPlayerManager.getCurrentJzvd().showWifiDialog();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}