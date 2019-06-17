package com.zhiyicx.thinksnsplus.modules.circle.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhiyicx.baseproject.base.TSFragment;
import com.zhiyicx.baseproject.base.TSListFragment;
import com.zhiyicx.baseproject.config.ApiConfig;
import com.zhiyicx.baseproject.config.TouristConfig;
import com.zhiyicx.baseproject.impl.photoselector.ImageBean;
import com.zhiyicx.baseproject.impl.share.UmengSharePolicyImpl;
import com.zhiyicx.baseproject.share.ShareContent;
import com.zhiyicx.common.utils.ConvertUtils;
import com.zhiyicx.common.utils.UIUtils;
import com.zhiyicx.common.widget.badgeview.DisplayUtil;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.base.AppApplication;
import com.zhiyicx.thinksnsplus.base.fordownload.TSListFragmentForDownload;
import com.zhiyicx.thinksnsplus.data.beans.AnimationRectBean;
import com.zhiyicx.thinksnsplus.data.beans.CirclePostListBean;
import com.zhiyicx.thinksnsplus.data.beans.DynamicDetailBeanV2;
import com.zhiyicx.thinksnsplus.data.beans.TopPostListBean;
import com.zhiyicx.thinksnsplus.data.beans.TopSuperStarBean;
import com.zhiyicx.thinksnsplus.data.beans.UserInfoBean;
import com.zhiyicx.thinksnsplus.data.source.repository.BaseCircleRepository;
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
import com.zhiyicx.thinksnsplus.modules.circle.detailv2.post.CirclePostDetailActivity;
import com.zhiyicx.thinksnsplus.modules.circle.main.adapter.CircleHotAdapter;
import com.zhiyicx.thinksnsplus.modules.circle.main.adapter.CircleHotItem;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListItemForShorVideo;
import com.zhiyicx.thinksnsplus.modules.gallery.GalleryActivity;
import com.zhiyicx.thinksnsplus.modules.personal_center.PersonalCenterFragment;
import com.zhiyicx.thinksnsplus.modules.shortvideo.helper.ZhiyiVideoView;
import com.zhiyicx.thinksnsplus.utils.TSShareUtils;
import com.zhiyicx.thinksnsplus.widget.comment.CirclePostListCommentView;
import com.zhiyicx.thinksnsplus.widget.comment.CommentBaseRecycleView;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.zhiyicx.thinksnsplus.data.beans.DynamicListAdvert.DEFAULT_ADVERT_FROM_TAG;

//CirclePostListBean
public class CircleHotFragment extends TSListFragmentForDownload<CircleHotContract.Presenter, CirclePostListBean> implements CircleHotContract.View, MultiItemTypeAdapter.OnItemClickListener, CirclePostListBaseItem.OnImageClickListener, OnUserInfoClickListener, CirclePostListBaseItem.OnMenuItemClickLisitener, CirclePostListBaseItem.OnReSendClickListener, CirclePostListCommentView.OnMoreCommentClickListener, CirclePostListCommentView.OnCommentClickListener, CirclePostListBaseItem.OnPostFromClickListener, CommentBaseRecycleView.OnCommentStateClickListener, ZhiyiVideoView.ShareInterface/*, DynamicBannerHeader.DynamicBannerHeadlerClickEvent*/ {

    @Inject
    CircleHotPresenter mPresenter;
    private String mDynamicType = "topic_dynamic";

    @Override
    protected RecyclerView.Adapter getAdapter() {
        MultiItemTypeAdapter adapter = new MultiItemTypeAdapter<>(getContext(), mListDatas);
//        CircleHotItem circleHotItem = new CircleHotItem(mActivity);
//        adapter.addItemViewDelegate(circleHotItem);
        setAdapter(adapter, new CirclePostListItemForZeroImage(getContext()));
        setAdapter(adapter, new CirclePostListItemForOneImage(getContext()));
        setAdapter(adapter, new CirclePostListItemForTwoImage(getContext()));
        setAdapter(adapter, new CirclePostListItemForThreeImage(getContext()));
        setAdapter(adapter, new CirclePostListItemForFourImage(getContext()));
        setAdapter(adapter, new CirclePostListItemForFiveImage(getContext()));
        setAdapter(adapter, new CirclePostListItemForSixImage(getContext()));
        setAdapter(adapter, new CirclePostListItemForSevenImage(getContext()));
        setAdapter(adapter, new CirclePostListItemForEightImage(getContext()));
        setAdapter(adapter, new CirclePostListItemForNineImage(getContext()));
        setAdapter(adapter, new CirclePostListItemForShorVideo(getContext(), this) {
            @Override
            protected String videoFrom() {
                return mDynamicType;
            }
        });
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

    public static CircleHotFragment newInstance() {
        CircleHotFragment circleMainFragment = new CircleHotFragment();
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
        CirclePostDetailActivity.startCirclePostDetailActivity(getActivity(), mListDatas.get(position)
                .getGroup_id(), mListDatas.get(position).getId(), false, canGotoCircle());
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
        PersonalCenterFragment.startToPersonalCenter(getContext(), userInfoBean);
    }

    @Override
    public void onMenuItemClick(View view, int dataPosition, int viewPosition) {

    }

    @Override
    public void onReSendClick(int position) {

    }

    @Override
    public void onMoreCommentClick(View view, CirclePostListBean dynamicBean) {

    }

    @Override
    public void onCommentUserInfoClick(UserInfoBean userInfoBean) {

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


}
