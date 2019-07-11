package com.zhiyicx.thinksnsplus.modules.circle.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhiyicx.baseproject.base.TSFragment;
import com.zhiyicx.baseproject.base.TSListFragment;
import com.zhiyicx.baseproject.config.ApiConfig;
import com.zhiyicx.baseproject.config.TouristConfig;
import com.zhiyicx.baseproject.impl.photoselector.ImageBean;
import com.zhiyicx.baseproject.impl.photoselector.Toll;
import com.zhiyicx.baseproject.impl.share.UmengSharePolicyImpl;
import com.zhiyicx.baseproject.share.ShareContent;
import com.zhiyicx.baseproject.widget.InputPasswordView;
import com.zhiyicx.baseproject.widget.popwindow.ActionPopupWindow;
import com.zhiyicx.baseproject.widget.popwindow.PayPopWindow;
import com.zhiyicx.common.BuildConfig;
import com.zhiyicx.common.utils.ConvertUtils;
import com.zhiyicx.common.utils.DeviceUtils;
import com.zhiyicx.common.utils.TextViewUtils;
import com.zhiyicx.common.utils.UIUtils;
import com.zhiyicx.common.widget.badgeview.DisplayUtil;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.base.AppApplication;
import com.zhiyicx.thinksnsplus.base.fordownload.TSListFragmentForDownload;
import com.zhiyicx.thinksnsplus.data.beans.AnimationRectBean;
import com.zhiyicx.thinksnsplus.data.beans.CirclePostListBean;
import com.zhiyicx.thinksnsplus.data.beans.DynamicCommentBean;
import com.zhiyicx.thinksnsplus.data.beans.DynamicDetailBeanV2;
import com.zhiyicx.thinksnsplus.data.beans.TopPostListBean;
import com.zhiyicx.thinksnsplus.data.beans.TopSuperStarBean;
import com.zhiyicx.thinksnsplus.data.beans.UserInfoBean;
import com.zhiyicx.thinksnsplus.i.OnUserInfoClickListener;
import com.zhiyicx.thinksnsplus.modules.circle.detailv2.adapter.CirclePostListBaseItem;
import com.zhiyicx.thinksnsplus.modules.circle.detailv2.adapter.CirclePostListBaseItem4Video;
import com.zhiyicx.thinksnsplus.modules.circle.detailv2.adapter.CirclePostListItemForEightImage;
import com.zhiyicx.thinksnsplus.modules.circle.detailv2.adapter.CirclePostListItemForFiveImage;
import com.zhiyicx.thinksnsplus.modules.circle.detailv2.adapter.CirclePostListItemForFourImage;
import com.zhiyicx.thinksnsplus.modules.circle.detailv2.adapter.CirclePostListItemForNineImage;
import com.zhiyicx.thinksnsplus.modules.circle.detailv2.adapter.CirclePostListItemForOneImage;
import com.zhiyicx.thinksnsplus.modules.circle.detailv2.adapter.CirclePostListItemForSevenImage;
import com.zhiyicx.thinksnsplus.modules.circle.detailv2.adapter.CirclePostListItemForShorVideo;
import com.zhiyicx.thinksnsplus.modules.circle.detailv2.adapter.CirclePostListItemForSixImage;
import com.zhiyicx.thinksnsplus.modules.circle.detailv2.adapter.CirclePostListItemForThreeImage;
import com.zhiyicx.thinksnsplus.modules.circle.detailv2.adapter.CirclePostListItemForTwoImage;
import com.zhiyicx.thinksnsplus.modules.circle.detailv2.adapter.CirclePostListItemForZeroImage;
import com.zhiyicx.thinksnsplus.modules.circle.main.adapter.CircleHotAdapter;
import com.zhiyicx.thinksnsplus.modules.dynamic.detail.DynamicDetailActivity;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.DynamicFragment;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListBaseItem;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListItemForAdvert;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListItemForEightImage;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListItemForFiveImage;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListItemForFourImage;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListItemForNineImage;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListItemForOneImage;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListItemForSevenImage;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListItemForShorVideo;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListItemForSixImage;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListItemForThreeImage;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListItemForTwoImage;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListItemForZeroImage;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListItemForwardAnswer;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListItemForwardCircle;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListItemForwardInfo;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListItemForwardMediaFeed;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListItemForwardPost;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListItemForwardQuestion;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListItemForwardWordFeed;
import com.zhiyicx.thinksnsplus.modules.gallery.GalleryActivity;
import com.zhiyicx.thinksnsplus.modules.personal_center.PersonalCenterFragment;
import com.zhiyicx.thinksnsplus.modules.settings.aboutus.CustomWEBActivity;
import com.zhiyicx.thinksnsplus.modules.shortvideo.helper.ZhiyiVideoView;
import com.zhiyicx.thinksnsplus.modules.wallet.sticktop.StickTopFragment;
import com.zhiyicx.thinksnsplus.widget.comment.CirclePostListCommentView;
import com.zhiyicx.thinksnsplus.widget.comment.CommentBaseRecycleView;
import com.zhiyicx.thinksnsplus.widget.comment.DynamicListCommentView;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.jzvd.JZMediaManager;
import cn.jzvd.JZVideoPlayerManager;

import static com.zhiyicx.baseproject.widget.popwindow.ActionPopupWindow.POPUPWINDOW_ALPHA;
import static com.zhiyicx.thinksnsplus.data.beans.DynamicListAdvert.DEFAULT_ADVERT_FROM_TAG;
import static com.zhiyicx.thinksnsplus.modules.dynamic.detail.DynamicDetailFragment.DYNAMIC_DETAIL_DATA;
import static com.zhiyicx.thinksnsplus.modules.dynamic.detail.DynamicDetailFragment.DYNAMIC_DETAIL_DATA_POSITION;
import static com.zhiyicx.thinksnsplus.modules.dynamic.detail.DynamicDetailFragment.DYNAMIC_DETAIL_DATA_TYPE;
import static com.zhiyicx.thinksnsplus.modules.dynamic.detail.DynamicDetailFragment.DYNAMIC_VIDEO_STATE;
import static com.zhiyicx.thinksnsplus.modules.dynamic.detail.DynamicDetailFragment.LOOK_COMMENT_MORE;

//CirclePostListBean
public class CircleHotFragment extends TSListFragmentForDownload<CircleHotContract.Presenter, DynamicDetailBeanV2> implements CircleHotContract.View, MultiItemTypeAdapter.OnItemClickListener, CirclePostListBaseItem.OnImageClickListener, OnUserInfoClickListener, CirclePostListBaseItem.OnMenuItemClickLisitener, CirclePostListBaseItem.OnReSendClickListener, CirclePostListCommentView.OnMoreCommentClickListener, CirclePostListCommentView.OnCommentClickListener, CirclePostListBaseItem.OnPostFromClickListener, CommentBaseRecycleView.OnCommentStateClickListener, ZhiyiVideoView.ShareInterface, DynamicListBaseItem.OnImageClickListener, TextViewUtils.OnSpanTextClickListener, DynamicListBaseItem.OnMenuItemClickLisitener, DynamicListBaseItem.OnReSendClickListener, DynamicListCommentView.OnMoreCommentClickListener, DynamicListCommentView.OnCommentClickListener/*, DynamicBannerHeader.DynamicBannerHeadlerClickEvent*/ {

    @Inject
    CircleHotPresenter mPresenter;
    private String mDynamicType = "topic_dynamic";
    private PayPopWindow mPayImagePopWindow;
    private ActionPopupWindow mReSendDynamicPopWindow;

    private ActionPopupWindow mDeletCommentPopWindow;
    /**
     * 当前评论的动态位置
     */
    private int mCurrentPostion;
    /**
     * 被评论者的 id
     */
    private long mReplyToUserId;
    /**
     * 标识置顶的那条评论
     */
    private DynamicCommentBean mDynamicCommentBean;
    DynamicFragment.OnCommentClickListener mOnCommentClickListener;
    @Override
    protected RecyclerView.Adapter getAdapter() {
        MultiItemTypeAdapter adapter = new MultiItemTypeAdapter<>(getContext(), mListDatas);
//        CircleHotItem circleHotItem = new CircleHotItem(mActivity);
//        adapter.addItemViewDelegate(circleHotItem);
//        setAdapter(adapter, new CirclePostListItemForZeroImage(getContext()));
//        setAdapter(adapter, new CirclePostListItemForOneImage(getContext()));
//        setAdapter(adapter, new CirclePostListItemForTwoImage(getContext()));
//        setAdapter(adapter, new CirclePostListItemForThreeImage(getContext()));
//        setAdapter(adapter, new CirclePostListItemForFourImage(getContext()));
//        setAdapter(adapter, new CirclePostListItemForFiveImage(getContext()));
//        setAdapter(adapter, new CirclePostListItemForSixImage(getContext()));
//        setAdapter(adapter, new CirclePostListItemForSevenImage(getContext()));
//        setAdapter(adapter, new CirclePostListItemForEightImage(getContext()));
//        setAdapter(adapter, new CirclePostListItemForNineImage(getContext()));
//        setAdapter(adapter, new CirclePostListItemForShorVideo(getContext(), this) {
//            @Override
//            protected String videoFrom() {
//                return mDynamicType;
//            }
//        });





        setAdapter(adapter, new DynamicListItemForZeroImage(getContext()));
        setAdapter(adapter, new DynamicListItemForOneImage(getContext()));
        setAdapter(adapter, new DynamicListItemForTwoImage(getContext()));
        setAdapter(adapter, new DynamicListItemForThreeImage(getContext()));
        setAdapter(adapter, new DynamicListItemForFourImage(getContext()));
        setAdapter(adapter, new DynamicListItemForFiveImage(getContext()));
        setAdapter(adapter, new DynamicListItemForSixImage(getContext()));
        setAdapter(adapter, new DynamicListItemForSevenImage(getContext()));
        setAdapter(adapter, new DynamicListItemForEightImage(getContext()));
        setAdapter(adapter, new DynamicListItemForNineImage(getContext()));
        setAdapter(adapter, new DynamicListItemForAdvert(getContext()));
        setAdapter(adapter, new DynamicListItemForwardWordFeed(mActivity));
        setAdapter(adapter, new DynamicListItemForwardMediaFeed(mActivity));
        setAdapter(adapter, new DynamicListItemForwardInfo(mActivity));
        setAdapter(adapter, new DynamicListItemForwardCircle(mActivity));
        setAdapter(adapter, new DynamicListItemForwardPost(mActivity));
        setAdapter(adapter, new DynamicListItemForwardQuestion(mActivity));
        setAdapter(adapter, new DynamicListItemForwardAnswer(mActivity));
        setAdapter(adapter, new DynamicListItemForShorVideo(getContext(), this) {
            @Override
            protected String videoFrom() {
                return mDynamicType;
            }
        });
        adapter.setOnItemClickListener(this);




        adapter.setOnItemClickListener(this);
        return adapter;
    }

    @Override
    protected boolean setUseSatusbar() {
        return false;
    }

    @Override
    protected boolean setUseStatusView() {
        return false;
    }
    protected void setAdapter(MultiItemTypeAdapter adapter, CirclePostListBaseItem4Video circlePostListBaseItem) {
//        circlePostListBaseItem.setOnImageClickListener(this);
//        circlePostListBaseItem.setOnUserInfoClickListener(this);
//        circlePostListBaseItem.setOnMenuItemClickLisitener(this);
//        circlePostListBaseItem.setOnReSendClickListener(this);
//        circlePostListBaseItem.setOnMoreCommentClickListener(this);
//        circlePostListBaseItem.setOnCommentClickListener(this);
//        circlePostListBaseItem.setOnCommentStateClickListener(this);
//        circlePostListBaseItem.setOnPostFromClickListener(this);
////      circlePostListBaseItem.setShowPostExcelentTag(!BaseCircleRepository.CircleMinePostType.EXCELLENT.equals(mCircleMinePostType));
//        circlePostListBaseItem.setShowPostExcelentTag(false);
//        circlePostListBaseItem.setShowPostFrom(showPostFrom());
//        circlePostListBaseItem.setShowCommentList(showCommentList());
//        circlePostListBaseItem.setShowToolMenu(showToolMenu());
        adapter.addItemViewDelegate(circlePostListBaseItem);
    }
    protected void setAdapter(MultiItemTypeAdapter adapter, CirclePostListBaseItem circlePostListBaseItem) {
        circlePostListBaseItem.setOnImageClickListener(this);
        circlePostListBaseItem.setOnUserInfoClickListener(this);
        circlePostListBaseItem.setOnMenuItemClickLisitener(this);
        circlePostListBaseItem.setOnReSendClickListener(this);
        circlePostListBaseItem.setOnMoreCommentClickListener(this);
        circlePostListBaseItem.setOnCommentClickListener(this);
        circlePostListBaseItem.setOnCommentStateClickListener(this);
        circlePostListBaseItem.setOnPostFromClickListener(this);
//      circlePostListBaseItem.setShowPostExcelentTag(!BaseCircleRepository.CircleMinePostType.EXCELLENT.equals(mCircleMinePostType));
        circlePostListBaseItem.setShowPostExcelentTag(false);
        circlePostListBaseItem.setShowPostFrom(showPostFrom());
        circlePostListBaseItem.setShowCommentList(showCommentList());
        circlePostListBaseItem.setShowToolMenu(showToolMenu());
        adapter.addItemViewDelegate(circlePostListBaseItem);
    }


    protected void setAdapter(MultiItemTypeAdapter adapter, DynamicListBaseItem
            dynamicListBaseItem) {
        dynamicListBaseItem.setOnImageClickListener(this);
        dynamicListBaseItem.setOnSpanTextClickListener(this);
        dynamicListBaseItem.setOnUserInfoClickListener(this);
        dynamicListBaseItem.setOnMenuItemClickLisitener(this);
        dynamicListBaseItem.setOnReSendClickListener(this);
        dynamicListBaseItem.setOnMoreCommentClickListener(this);
        dynamicListBaseItem.setOnCommentClickListener(this);
        dynamicListBaseItem.setOnCommentStateClickListener(this);
        adapter.addItemViewDelegate(dynamicListBaseItem);
    }


    protected boolean showCommentList() {
        return false;
    }

    /**
     * 是否显示帖子来原
     *
     * @return
     */
    protected boolean showPostFrom() {
        return false;
    }

    protected boolean showToolMenu() {
        return false;
    }
    //String type
    public static CircleHotFragment newInstance() {
        CircleHotFragment circleMainFragment = new CircleHotFragment();
//        circleMainFragment.setArguments();
        return circleMainFragment;
    }

    @Override
    protected boolean showToolbar() {
        return false;
    }

    @Override
    protected void initData() {
        super.initData();
        DaggerCircleHotFragmentPresenterComponent
                .builder()
                .appComponent(AppApplication.AppComponentHolder.getAppComponent())
                .circleHotPresenterModule(new CircleHotPresenterModule(this))
                .build().inject(CircleHotFragment.this);
        mPresenter.requestNetData(DEFAULT_PAGE_MAX_ID, false);
//        initAdvert();
    }

    @Override
    protected boolean showToolBarDivider() {
        return false;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return super.getLayoutManager();
    }

    @Override
    public void onNetSuccessHotSuperStar(List<TopSuperStarBean> topSuperStarBeans) {
        Log.e("wulianshu", "topSuperStarBeans:" + topSuperStarBeans.size());
        if (topSuperStarBeans != null && topSuperStarBeans.size() > 0)
            bindHotSuperStarView(topSuperStarBeans);

    }

    private void bindHotSuperStarView(List<TopSuperStarBean> topSuperStarBeans) {
        mHeaderAndFooterWrapper.clearHeaderView();
        View super10Star = LayoutInflater.from(getContext()).inflate(R.layout.item_circle_hot_star_layout, mRvList, false);
        mHeaderAndFooterWrapper.clearHeaderView();
        mHeaderAndFooterWrapper.addHeaderView(super10Star);
        mHeaderAndFooterWrapper.notifyDataSetChanged();
        RecyclerView rvSuperStarHead = super10Star.findViewById(R.id.rv_super_star_head);
        rvSuperStarHead.setLayoutManager(new GridLayoutManager(getContext(), 5));
        CircleHotAdapter circleHotAdapter = new CircleHotAdapter(getContext());
        circleHotAdapter.setDatas(topSuperStarBeans);
        rvSuperStarHead.setAdapter(circleHotAdapter);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) rvSuperStarHead.getLayoutParams();
        if (topSuperStarBeans.size() <= 5) {
            layoutParams.height = DisplayUtil.dp2px(getContext(), 80);
        } else {
            layoutParams.height = DisplayUtil.dp2px(getContext(), 170);
        }
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
//        CirclePostDetailActivity.startCirclePostDetailActivity(getActivity(), mListDatas.get(position)
//                .getGroup_id(), mListDatas.get(position).getId(), false, canGotoCircle());
//        DynamicDetailActivity.startDynamicDetailActivity(getActivity(),mListDatas.get(position).getId());


        position -= mHeaderAndFooterWrapper.getHeadersCount();
        if (!TouristConfig.DYNAMIC_DETAIL_CAN_LOOK && mPresenter.handleTouristControl()) { // 游客处理
            return;
        }
        DynamicDetailBeanV2 detailBeanV2 = mListDatas.get(position);
        // 是广告
        if (detailBeanV2.getFeed_from() == DEFAULT_ADVERT_FROM_TAG) {
            toAdvert(detailBeanV2.getDeleted_at(), detailBeanV2.getFeed_content());
            return;
        }
        boolean canNotLookWords = detailBeanV2.getPaid_node() != null &&
                !detailBeanV2.getPaid_node().isPaid()
                && detailBeanV2.getUser_id().intValue() != AppApplication.getMyUserIdWithdefault();
        if (canNotLookWords) {
            initImageCenterPopWindow(position, position,
                    detailBeanV2.getPaid_node().getAmount(),
                    detailBeanV2.getPaid_node().getNode(), R.string.buy_pay_words_desc, false);
            return;
        }

        goDynamicDetail(position, false, (ViewHolder) holder);


    }


    private void goDynamicDetail(int position, boolean isLookMoreComment, ViewHolder holder) {
        // 还未发送成功的动态列表不查看详情
        if (mListDatas.get(position).getId() == null || mListDatas.get(position).getId() <= 0) {
            return;
        }

        Intent intent = new Intent(getActivity(), DynamicDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(DYNAMIC_DETAIL_DATA, mListDatas.get(position));
        bundle.putString(DYNAMIC_DETAIL_DATA_TYPE, getDynamicType());
        bundle.putInt(DYNAMIC_DETAIL_DATA_POSITION, position);

        bundle.putBoolean(LOOK_COMMENT_MORE, isLookMoreComment);
//        mPresenter.handleViewCount(mListDatas.get(position).getId(), position);

        if (isLookMoreComment) {
            ZhiyiVideoView.releaseAllVideos();
            intent.putExtras(bundle);
            startActivity(intent);
            return;
        }
        ZhiyiVideoView playView = null;
        try {
            playView = holder.getView(R.id.videoplayer);
        } catch (Exception ignore) {

        }

        if (playView != null && JZVideoPlayerManager.getFirstFloor() != null) {
            playView.mVideoFrom = mDynamicType;
            if (playView.currentState == ZhiyiVideoView.CURRENT_STATE_PLAYING) {
                playView.startButton.callOnClick();
            }
            bundle.putInt(DYNAMIC_VIDEO_STATE, playView.currentState);
            playView.textureViewContainer.removeView(JZMediaManager.textureView);
            playView.onStateNormal();
        }

        intent.putExtras(bundle);
        startActivity(intent);

    }

    public String getDynamicType() {
        return mDynamicType;
    }
    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        return false;
    }

    /**
     * 点击 来自 xxx ，可以跳转到相应圈子
     *
     * @return
     */
    protected boolean canGotoCircle() {
        return true;
    }

    @Override
    public void onImageClick(ViewHolder holder, CirclePostListBean circlePostListBean, int position) {
        if (!TouristConfig.DYNAMIC_BIG_PHOTO_CAN_LOOK && mPresenter.handleTouristControl()) {
            return;
        }
        List<CirclePostListBean.ImagesBean> task = circlePostListBean.getImages();
        List<ImageBean> imageBeanList = new ArrayList<>();
        ArrayList<AnimationRectBean> animationRectBeanArrayList
                = new ArrayList<>();
        for (int i = 0; i < task.size(); i++) {
            if (i >= 9) {
                continue;
            }
            int id = UIUtils.getResourceByName("siv_" + i, "id", getContext());
            ImageView imageView = holder.getView(id);
            ImageBean imageBean = new ImageBean();
            imageBean.setStorage_id(task.get(i).getFile_id());
            imageBean.setListCacheUrl(task.get(i).getGlideUrl());
            imageBean.setWidth(task.get(i).getWidth());
            imageBean.setHeight(task.get(i).getHeight());
            imageBean.setImgMimeType(task.get(i).getImgMimeType());
            imageBeanList.add(imageBean);
            AnimationRectBean rect = AnimationRectBean.buildFromImageView(imageView);
            animationRectBeanArrayList.add(rect);
        }

        GalleryActivity.startToGallery(getContext(), position, imageBeanList,
                animationRectBeanArrayList);
    }

    @Override
    public void onUserInfoClick(UserInfoBean userInfoBean) {

        // 游客处理
        if (!TouristConfig.USER_INFO_CAN_LOOK && mPresenter.handleTouristControl()) {
            return;
        }
        // 广告
        if (userInfoBean.getUser_id() != null && userInfoBean.getUser_id().intValue() == -1) {
            return;
        }
        PersonalCenterFragment.startToPersonalCenter(getContext(), userInfoBean);
//        PersonalCenterFragment.startToPersonalCenter(getContext(), userInfoBean);
    }



    @Override
    public void share(int position) {
        position -= mHeaderAndFooterWrapper.getHeadersCount();
        if (mListDatas.get(position).getId() > 0) {
            Bitmap shareBitMap = getShareBitmap(position, R.id.thumb);
            mPresenter.sharePost(mListDatas.get(position), shareBitMap);
        }
    }


    /**
     * 获取分享动态需要的图片
     *
     * @param position
     * @param id
     * @return
     */
    private Bitmap getShareBitmap(int position, int id) {
        Bitmap shareBitMap = null;
        try {
            ImageView imageView = layoutManager.findViewByPosition
                    (position + mHeaderAndFooterWrapper.getHeadersCount()).findViewById(id);
            shareBitMap = ConvertUtils.drawable2BitmapWithWhiteBg(getContext(), imageView
                    .getDrawable(), R.mipmap.icon);
        } catch (Exception e) {
        }
        return shareBitMap;
    }

    @Override
    public void shareWihtType(int position, SHARE_MEDIA type) {
        position -= mHeaderAndFooterWrapper.getHeadersCount();
        if (mListDatas.get(position).getId() > 0) {
            mPresenter.sharePost(mListDatas.get(position), getShareBitmap(position, R.id.thumb),
                    type);
        }
    }


    @Override
    public void onImageClick(ViewHolder holder, DynamicDetailBeanV2 dynamicBean, int position) {

        long start = System.currentTimeMillis();
        if (!TouristConfig.DYNAMIC_BIG_PHOTO_CAN_LOOK && mPresenter.handleTouristControl()) {
            return;
        }
        // 广告
        if (dynamicBean.getFeed_from() == DEFAULT_ADVERT_FROM_TAG) {
            toAdvert(dynamicBean.getDeleted_at(), dynamicBean.getFeed_content());
            return;
        }
        int dynamicPosition = holder.getAdapterPosition() - mHeaderAndFooterWrapper
                .getHeadersCount();

        DynamicDetailBeanV2.ImagesBean img = dynamicBean.getImages().get(position);
        boolean canLook = !(img.isPaid() != null && !img.isPaid() && img.getType().equals(Toll
                .LOOK_TOLL_TYPE));
        if (!canLook) {
            initImageCenterPopWindow(dynamicPosition, position, dynamicBean
                            .getImages().get(position).getAmount(),
                    dynamicBean.getImages().get(position).getPaid_node(), R.string.buy_pay_desc,
                    true);
            return;
        }

        List<DynamicDetailBeanV2.ImagesBean> tasks = dynamicBean.getImages();
        List<ImageBean> imageBeanList = new ArrayList<>();
        ArrayList<AnimationRectBean> animationRectBeanArrayList
                = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            if (i >= 9) {
                continue;
            }
            DynamicDetailBeanV2.ImagesBean task = tasks.get(i);
            int id = UIUtils.getResourceByName("siv_" + i, "id", getContext());
            ImageView imageView = holder.getView(id);
            ImageBean imageBean = new ImageBean();
            imageBean.setImgUrl(task.getImgUrl());
            Toll toll = new Toll();
            toll.setPaid(task.isPaid());
            toll.setToll_money(task.getAmount());
            toll.setToll_type_string(task.getType());
            toll.setPaid_node(task.getPaid_node());
            imageBean.setToll(toll);
            imageBean.setDynamicPosition(dynamicPosition);
            imageBean.setFeed_id(dynamicBean.getId());
            imageBean.setWidth(task.getWidth());
            imageBean.setHeight(task.getHeight());
            imageBean.setListCacheUrl(task.getGlideUrl());
            imageBean.setStorage_id(task.getFile());
            imageBean.setImgMimeType(task.getImgMimeType());
            imageBeanList.add(imageBean);
            AnimationRectBean rect = AnimationRectBean.buildFromImageView(imageView);
            animationRectBeanArrayList.add(rect);
        }

        GalleryActivity.startToGallery(mActivity, position, imageBeanList, animationRectBeanArrayList);

    }

    @Override
    public void setSpanText(int position, int note, long amount, TextView view, boolean canNotRead) {
        position -= mHeaderAndFooterWrapper.getHeadersCount();
        initImageCenterPopWindow(position, position, amount,
                note, R.string.buy_pay_words_desc, false);
    }

    @Override
    public void onMoreCommentClick(View view, DynamicDetailBeanV2 dynamicBean) {

        if (!TouristConfig.MORE_COMMENT_CAN_LOOK && mPresenter.handleTouristControl()) {
            return;
        }
        int dataPosition = getCurrenPosiotnInDataList(dynamicBean.getFeed_mark());
        DynamicDetailBeanV2 detailBeanV2 = mListDatas.get(dataPosition);
        boolean canNotLookWords = detailBeanV2.getPaid_node() != null &&
                !detailBeanV2.getPaid_node().isPaid()
                && detailBeanV2.getUser_id().intValue() != AppApplication.getMyUserIdWithdefault();
        if (canNotLookWords) {
            initImageCenterPopWindow(dataPosition, dataPosition,
                    detailBeanV2.getPaid_node().getAmount(),
                    detailBeanV2.getPaid_node().getNode(), R.string.buy_pay_words_desc, false);
            return;
        }
        int viewPosition = dataPosition + mHeaderAndFooterWrapper.getHeadersCount();
        goDynamicDetail(dataPosition, true, (ViewHolder) mRvList.findViewHolderForAdapterPosition(viewPosition));
    }

    public int getCurrenPosiotnInDataList(long feedMark) {
        int position = -1;
        int size = mListDatas.size();
        for (int i = 0; i < size; i++) {
            if (mListDatas.get(i).getFeed_mark() != null && feedMark == mListDatas.get(i).getFeed_mark()) {
                position = i;
                break;
            }
        }
        return position;
    }

    @Override
    public void onMenuItemClick(View view, int dataPosition, int viewPosition) {

    }

    @Override
    public void onReSendClick(int position) {
        initReSendDynamicPopupWindow (position);
        mReSendDynamicPopWindow.show();
    }

    /**
     * 初始化重发动态选择弹框
     */
    private void initReSendDynamicPopupWindow(final int position) {
        mReSendDynamicPopWindow = ActionPopupWindow.builder()
                .item1Str(getString(R.string.resend))
                .item1Color(ContextCompat.getColor(getContext(), R.color.themeColor))
                .bottomStr(getString(R.string.cancel))
                .isOutsideTouch(true)
                .isFocus(true)
                .backgroundAlpha(POPUPWINDOW_ALPHA)
                .with(getActivity())
                .item1ClickListener(() -> {
                    mReSendDynamicPopWindow.hide();
                    mListDatas.get(position).setState(DynamicDetailBeanV2.SEND_ING);
                    refreshData();
                    // TODO: 2019/6/17  
//                    mPresenter.reSendDynamic(position);
                })
                .bottomClickListener(() -> mReSendDynamicPopWindow.hide())
                .build();
    }

    @Override
    public void onMoreCommentClick(View view, CirclePostListBean dynamicBean) {
        if (!TouristConfig.MORE_COMMENT_CAN_LOOK && mPresenter.handleTouristControl()) {
            return;
        }
        int dataPosition = getCurrenPosiotnInDataList(dynamicBean.getFeed_mark());
        DynamicDetailBeanV2 detailBeanV2 = mListDatas.get(dataPosition);
        boolean canNotLookWords = detailBeanV2.getPaid_node() != null &&
                !detailBeanV2.getPaid_node().isPaid()
                && detailBeanV2.getUser_id().intValue() != AppApplication.getMyUserIdWithdefault();
        if (canNotLookWords) {
            initImageCenterPopWindow(dataPosition, dataPosition,
                    detailBeanV2.getPaid_node().getAmount(),
                    detailBeanV2.getPaid_node().getNode(), R.string.buy_pay_words_desc, false);
            return;
        }
        int viewPosition = dataPosition + mHeaderAndFooterWrapper.getHeadersCount();
        goDynamicDetail(dataPosition, true, (ViewHolder) mRvList.findViewHolderForAdapterPosition(viewPosition));
    }

    @Override
    public void onCommentUserInfoClick(UserInfoBean userInfoBean) {
        onUserInfoClick(userInfoBean);
    }

    @Override
    public void onCommentContentClick(DynamicDetailBeanV2 dynamicBean, int position) {
        if (!TouristConfig.DYNAMIC_CAN_COMMENT && mPresenter.handleTouristControl()) {
            return;
        }
        mCurrentPostion =getCurrenPosiotnInDataList(dynamicBean.getFeed_mark());
        if (dynamicBean.getComments().get(position).getUser_id() == AppApplication
                .getMyUserIdWithdefault()) {
            initDeletCommentPopupWindow(dynamicBean, mCurrentPostion, position);
            mDeletCommentPopWindow.show();

        } else {
            showCommentView();
            mReplyToUserId = dynamicBean.getComments().get(position).getUser_id();
            String contentHint = getString(R.string.default_input_hint);
            if (dynamicBean.getComments().get(position).getUser_id() != AppApplication
                    .getMyUserIdWithdefault()) {
                contentHint = getString(R.string.reply, dynamicBean.getComments().get(position)
                        .getCommentUser().getName());
            }
            mIlvComment.setEtContentHint(contentHint);
        }
    }

    @Override
    public void onCommentContentLongClick(DynamicDetailBeanV2 dynamicBean, int position) {

    }

    @Override
    public void onCommentContentClick(CirclePostListBean dynamicBean, int position) {

    }

    @Override
    public void onCommentContentLongClick(CirclePostListBean dynamicBean, int position) {

    }

    @Override
    public void onPostFromClick(int position) {

    }

    @Override
    public void onCommentStateClick(Object dynamicCommentBean, int position) {
//        initReSendCommentPopupWindow(dynamicCommentBean, mListDatas.get(mPresenter
//                .getCurrenPosiotnInDataList(dynamicCommentBean.getFeed_mark())).getId());
//        mReSendCommentPopWindow.show();
    }


    private void toAdvert(String link, String title) {
        CustomWEBActivity.startToWEBActivity(getActivity(), link, title);
    }

    /**
     * @param dynamicPosition 动态位置
     * @param imagePosition   图片位置
     * @param amout           费用
     * @param note            支付节点
     * @param strRes          文字说明
     * @param isImage         是否是图片收费
     */
    private void initImageCenterPopWindow(final int dynamicPosition, final int imagePosition,
                                          long amout,
                                          final int note, int strRes, final boolean isImage) {

        mPayImagePopWindow = PayPopWindow.builder()
                .with(getActivity())
                .isWrap(true)
                .isFocus(true)
                .isOutsideTouch(true)
                .buildLinksColor1(R.color.themeColor)
                .buildLinksColor2(R.color.important_for_content)
                .contentView(R.layout.ppw_for_center)
                .backgroundAlpha(POPUPWINDOW_ALPHA)
                .buildDescrStr(String.format(getString(strRes) + getString(R
                        .string.buy_pay_member), amout, mPresenter.getGoldName()))
                .buildLinksStr(getString(R.string.buy_pay_member))
                .buildTitleStr(getString(R.string.buy_pay))
                .buildItem1Str(getString(R.string.buy_pay_in))
                .buildItem2Str(getString(R.string.buy_pay_out))
                .buildMoneyStr(String.format(getString(R.string.buy_pay_integration), "" + amout))
                .buildCenterPopWindowItem1ClickListener(() -> {
                    if (mPresenter.usePayPassword()) {
                        mIlvPassword.setPayNote(new InputPasswordView.PayNote(dynamicPosition, imagePosition, note, isImage, null));
                        showInputPsdView(true);
                    } else {
//                        mPresenter.payNote(dynamicPosition, imagePosition, note, isImage, null);
                    }
                    mPayImagePopWindow.hide();
                })
                .buildCenterPopWindowItem2ClickListener(() -> mPayImagePopWindow.hide())
                .buildCenterPopWindowLinkClickListener(new PayPopWindow
                        .CenterPopWindowLinkClickListener() {
                    @Override
                    public void onLongClick() {

                    }

                    @Override
                    public void onClicked() {

                    }
                })
                .build();
        mPayImagePopWindow.show();

    }



    /**
     * 初始化评论删除选择弹框
     *
     * @param dynamicBean     curent dynamic
     * @param dynamicPositon  dynamic comment position
     * @param commentPosition current comment position
     */
    private void initDeletCommentPopupWindow(final DynamicDetailBeanV2 dynamicBean, final int
            dynamicPositon, final int commentPosition) {
        boolean sourceIsMine = AppApplication.getMyUserIdWithdefault() == dynamicBean.getUser_id();
        mDeletCommentPopWindow = ActionPopupWindow.builder()
                .item1Str(BuildConfig.USE_TOLL && dynamicBean.getState() == DynamicDetailBeanV2
                        .SEND_SUCCESS && !dynamicBean
                        .getComments().get(commentPosition).getPinned() && dynamicBean.getComments().get(commentPosition).getComment_id() != null ?
                        getString(sourceIsMine ? R.string.dynamic_list_stick_top_comment : R.string.dynamic_list_top_comment) : null)
                .item2Str(getString(R.string.dynamic_list_delete_comment))
                .bottomStr(getString(R.string.cancel))
                .isOutsideTouch(true)
                .isFocus(true)
                .backgroundAlpha(POPUPWINDOW_ALPHA)
                .with(getActivity())
                .item1ClickListener(() -> {
                    mDeletCommentPopWindow.hide();
                    mDynamicCommentBean = dynamicBean.getComments().get(commentPosition);
                    StickTopFragment.startSticTopActivity(this, StickTopFragment.TYPE_DYNAMIC, dynamicBean.getId(), mDynamicCommentBean
                            .getComment_id(), sourceIsMine);
                    showBottomView(true);
                })
                .item2ClickListener(() -> {
                    mDeletCommentPopWindow.hide();
                    showDeleteTipPopupWindow(getString(R.string.delete_comment), () -> {
//                        mPresenter.deleteCommentV2(dynamicBean, dynamicPositon, dynamicBean
//                                        .getComments().get(commentPosition).getComment_id() != null ? dynamicBean
//                                        .getComments().get(commentPosition).getComment_id() : 0,
//                                commentPosition);
                        showBottomView(true);
                    }, true);

                })
                .bottomClickListener(() -> {
                    mDeletCommentPopWindow.hide();
                    showBottomView(true);
                })
                .build();
    }
    private void showCommentView() {
        showBottomView(false);
    }

    public void showBottomView(boolean isShow) {
        if (isShow) {
            mVShadow.setVisibility(View.GONE);
            mIlvComment.setVisibility(View.GONE);
            mIlvComment.clearFocus();
            mIlvComment.setSendButtonVisiable(false);
            DeviceUtils.hideSoftKeyboard(getActivity(), mIlvComment.getEtContent());
        } else {
            mVShadow.setVisibility(View.VISIBLE);
            mIlvComment.setVisibility(View.VISIBLE);
            mIlvComment.getFocus();
            mIlvComment.setSendButtonVisiable(true);
            DeviceUtils.showSoftKeyboard(getActivity(), mIlvComment.getEtContent());
        }
        if (mOnCommentClickListener != null) {
            mOnCommentClickListener.onButtonMenuShow(isShow);
        }
    }



}
