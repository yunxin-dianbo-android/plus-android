package com.zhiyicx.thinksnsplus.modules.circle.detailv2.post.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.view.RxView;
import com.zhiyicx.baseproject.impl.photoselector.ImageBean;
import com.zhiyicx.baseproject.impl.photoselector.Toll;
import com.zhiyicx.baseproject.utils.glide.GlideManager;
import com.zhiyicx.common.config.MarkdownConfig;
import com.zhiyicx.common.utils.ConvertUtils;
import com.zhiyicx.common.utils.ToastUtils;
import com.zhiyicx.common.utils.log.LogUtils;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.base.BaseWebLoad;
import com.zhiyicx.thinksnsplus.data.beans.AnimationRectBean;
import com.zhiyicx.thinksnsplus.data.beans.CircleInfo;
import com.zhiyicx.thinksnsplus.data.beans.CircleJoinedBean;
import com.zhiyicx.thinksnsplus.data.beans.CircleMembers;
import com.zhiyicx.thinksnsplus.data.beans.CirclePostListBean;
import com.zhiyicx.thinksnsplus.data.beans.RealAdvertListBean;
import com.zhiyicx.thinksnsplus.data.beans.RewardsCountBean;
import com.zhiyicx.thinksnsplus.data.beans.RewardsListBean;
import com.zhiyicx.thinksnsplus.modules.circle.detailv2.CircleDetailActivity;
import com.zhiyicx.thinksnsplus.modules.circle.detailv2.dig.DigListActivity;
import com.zhiyicx.thinksnsplus.modules.circle.detailv2.dig.adapter.BaseDigItem;
import com.zhiyicx.thinksnsplus.modules.circle.pre.PreCircleActivity;
import com.zhiyicx.thinksnsplus.modules.dynamic.detail.DynamicDetailAdvertHeader;
import com.zhiyicx.thinksnsplus.modules.gallery.GalleryActivity;
import com.zhiyicx.thinksnsplus.modules.settings.aboutus.CustomWEBActivity;
import com.zhiyicx.thinksnsplus.modules.wallet.reward.RewardType;
import com.zhiyicx.thinksnsplus.utils.MarkDownRule;
import com.zhiyicx.thinksnsplus.widget.DynamicHorizontalStackIconView;
import com.zhiyicx.thinksnsplus.widget.ReWardView;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zycx.shortvideo.utils.BitmapUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.tiagohm.markdownview.MarkdownView;
import rx.Observable;
import rx.schedulers.Schedulers;

import static android.view.View.GONE;
import static com.zhiyicx.baseproject.config.ApiConfig.API_VERSION_2;
import static com.zhiyicx.baseproject.config.ApiConfig.APP_DOMAIN;
import static com.zhiyicx.common.config.ConstantConfig.JITTER_SPACING_TIME;
import static com.zhiyicx.common.config.MarkdownConfig.IMAGE_FORMAT;

/**
 * @author Catherine
 * @describe
 * @date 2017/8/9
 * @contact email:648129313@qq.com
 */

public class PostDetailHeaderViewNew extends BaseWebLoad {
    private MarkdownView mContent;
    private MarkdownView mContentSubject;
    private TextView mTitle;
    private TextView mChannel;
    private TextView mFrom;
    private DynamicHorizontalStackIconView mDigListView;
    private ReWardView mReWardView;
    private FrameLayout mCommentHintView;
    private TextView mCommentCountView;
    private FrameLayout mInfoRelateList;
    private TagFlowLayout mFtlRelate;
    private RecyclerView mRvRelateInfo;
    private View mInfoDetailHeader;
    private Context mContext;
    private int screenWidth;
    private int picWidth;
    private Bitmap sharBitmap;
    private List<ImageBean> mImgList;
    private ImageView mIvDetail;
    private boolean isReviewIng;
    private boolean canGotoCircle;
    private CirclePostListBean mCirclePostListBean;

    private DynamicDetailAdvertHeader mDynamicDetailAdvertHeader;
    private ArrayList<AnimationRectBean> animationRectBeanArrayList;
    private boolean isEmpty;


    //新的圈子详情
    RelativeLayout rlOnlyOneImgContent;
    TextView tvCircleContent;
    ImageView ivVideoBg;
    TextView tvCollectionCount;
    TextView tvSeeCount;

     TextView tvPublishTime;

    public View getInfoDetailHeader() {
        return mInfoDetailHeader;
    }


    public PostDetailHeaderViewNew(Context context, List<RealAdvertListBean> adverts) {
        this.mContext = context;
        mImgList = new ArrayList<>();

        animationRectBeanArrayList = new ArrayList<>();
        mInfoDetailHeader = LayoutInflater.from(context).inflate(R.layout
                .item_post_comment_head, null);
        mInfoDetailHeader.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout
                .LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        mTitle = mInfoDetailHeader.findViewById(R.id.tv_info_title);
        mTitle.setTextColor(mContext.getResources().getColor(R.color.important_for_content));
        mChannel = mInfoDetailHeader.findViewById(R.id.tv_from_channel);
        mFrom = mInfoDetailHeader.findViewById(R.id.item_info_timeform);
        mContent = mInfoDetailHeader.findViewById(R.id.info_detail_content);
        mContentSubject = mInfoDetailHeader.findViewById(R.id.info_content_subject);
        mDigListView = mInfoDetailHeader.findViewById(R.id.detail_dig_view);
        mReWardView = mInfoDetailHeader.findViewById(R.id.v_reward);
        mCommentHintView = mInfoDetailHeader.findViewById(R.id.info_detail_comment);
        mCommentCountView = mInfoDetailHeader.findViewById(R.id.tv_comment_count);
        mInfoRelateList = mInfoDetailHeader.findViewById(R.id.info_relate_list);
        mFtlRelate = mInfoDetailHeader.findViewById(R.id.fl_tags);
        mRvRelateInfo = mInfoDetailHeader.findViewById(R.id.rv_relate_info);
        mIvDetail = mInfoDetailHeader.findViewById(R.id.iv_detail);
        rlOnlyOneImgContent = mInfoDetailHeader.findViewById(R.id.rl_only_one_img_content);
        tvCircleContent = mInfoDetailHeader.findViewById(R.id.tv_circle_content);
        ivVideoBg = mInfoDetailHeader.findViewById(R.id.iv_video_bg);
        tvSeeCount = mInfoDetailHeader.findViewById(R.id.tv_see_count);
        tvCollectionCount = mInfoDetailHeader.findViewById(R.id.tv_collection_count);
        tvPublishTime = mInfoDetailHeader.findViewById(R.id.tv_publish_time);
        initAdvert(context, adverts);


    }

    public void setDetail(CirclePostListBean circlePostDetailBean) {
        if (circlePostDetailBean != null) {
            mCirclePostListBean = circlePostDetailBean;
            mTitle.setText(circlePostDetailBean.getTitle());
            boolean isJoined = circlePostDetailBean.getGroup().getJoined() != null && circlePostDetailBean.getGroup().getJoined().getAudit() == CircleJoinedBean.AuditStatus.PASS.value;
            mChannel.setText(mContext.getText(R.string.from));
            CircleInfo circleInfo = circlePostDetailBean.getGroup();
            String from = circleInfo.getName();
            tvCircleContent.setText(circlePostDetailBean.getSummary() + "");
            if (circlePostDetailBean.getImages() == null || circlePostDetailBean.getImages().size() == 0) {
                rlOnlyOneImgContent.setVisibility(View.GONE);
            } else if (circlePostDetailBean.getImages().size() == 1) {
                rlOnlyOneImgContent.setVisibility(View.VISIBLE);
                GlideManager.glide(mContext, ivVideoBg, circlePostDetailBean.getImages().get(0).getImgUrl());
            }
            tvPublishTime.setText(circlePostDetailBean.getCreated_at()+"");
            if (!TextUtils.isEmpty(from)) {
                mFrom.setText(from);
                RxView.clicks(mFrom)
                        .filter(aVoid -> canGotoCircle)
                        .throttleFirst(JITTER_SPACING_TIME, TimeUnit.SECONDS)
                        .subscribe(aVoid -> {
                            boolean isClosedCircle = CircleInfo.CirclePayMode.PAID.value.equals(circleInfo.getMode())
                                    || CircleInfo.CirclePayMode.PRIVATE.value.equals(circleInfo.getMode());


                            if (isClosedCircle && !isJoined) {
//                                showSnackErrorMessage(mContext.getString(R.string.circle_blocked));
                                PreCircleActivity.startPreCircleActivity(mContext, circleInfo.getId());
                                return;
                            }
                            CircleDetailActivity.startCircleDetailActivity(mContext, circlePostDetailBean.getGroup_id());
                        });
            }
            mContentSubject.setVisibility(GONE);
            tvSeeCount.setText(circlePostDetailBean.getViews_count()+"");
            tvCollectionCount.setText(circlePostDetailBean.getLikes_count()+"");
            if (!TextUtils.isEmpty(circlePostDetailBean.getBody())) {
                mContent.addStyleSheet(MarkDownRule.generateStandardStyle());
                mContent.loadMarkdown(dealPic(circlePostDetailBean.getBody()));
                mContent.setWebChromeClient(mWebChromeClient);
                mContent.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        CustomWEBActivity.startToOutWEBActivity(mContext, url);
                        return true;
                    }
                });
                mContent.setOnElementListener(new MarkdownView.OnElementListener() {
                    @Override
                    public void onButtonTap(String s) {

                    }

                    @Override
                    public void onCodeTap(String s, String s1) {

                    }

                    @Override
                    public void onHeadingTap(int i, String s) {

                    }

                    @Override
                    public void onImageTap(String s, int width, int height) {
                        //  杨大佬说先暂时不做，如果后期要做的话 要做画廊 多张
                        LogUtils.d("Cathy", "onImageTap // " + s);
                        int position = 0;
                        if (isEmpty) {
                            // 适配原生 md 格式图片
                            mImgList.clear();
                            animationRectBeanArrayList.clear();
                        }
                        isEmpty = mImgList.isEmpty();
                        if (isEmpty) {
                            ImageBean imageBean = new ImageBean();
                            imageBean.setImgUrl(s);
                            imageBean.setFeed_id(-1L);// -1,标识来自网页内容
                            Toll toll = new Toll();
                            toll.setPaid(true);
                            toll.setToll_money(0);
                            toll.setToll_type_string("");
                            toll.setPaid_node(0);
                            imageBean.setToll(toll);
                            mImgList.add(imageBean);
                        }
                        for (int i = 0; i < mImgList.size(); i++) {
                            if (mImgList.get(i).getImgUrl().equals(s)) {
                                position = i;
                            }
                        }
                        GalleryActivity.startToGallery(mContext, position, mImgList, null);

                    }

                    @Override
                    public void onLinkTap(String s, String s1) {
//                        CustomWEBActivity.startToOutWEBActivity(mContext, s1);
                    }

                    @Override
                    public void onKeystrokeTap(String s) {

                    }

                    @Override
                    public void onMarkTap(String s) {

                    }
                });
            }
            // 评论信息
            updateCommentView(circlePostDetailBean);
        }
    }

    private void showSnackErrorMessage(String string) {
        if (mShowMessageListener != null) {
            mShowMessageListener.showErrorMessage(string);
        } else {
            ToastUtils.showToast(string);
        }
    }

    private void initAdvert(Context context, List<RealAdvertListBean> adverts) {
        mDynamicDetailAdvertHeader = new DynamicDetailAdvertHeader(context, mInfoDetailHeader
                .findViewById(R.id.ll_advert));
        if (!com.zhiyicx.common.BuildConfig.USE_ADVERT || adverts == null || adverts != null && adverts.isEmpty()) {
            mDynamicDetailAdvertHeader.hideAdvert();
            return;
        }
        mDynamicDetailAdvertHeader.setTitle("广告");
        mDynamicDetailAdvertHeader.setAdverts(adverts);
        mDynamicDetailAdvertHeader.setOnItemClickListener((v, position1, url) ->
                toAdvert(adverts.get(position1).getAdvertFormat().getImage().getLink(), adverts.get(position1).getTitle())
        );
    }

    private void toAdvert(String link, String title) {
        CustomWEBActivity.startToWEBActivity(mContext, link, title);
    }

    private String dealPic(String markDownContent) {
        // 替换图片id 为地址
        Pattern pattern = Pattern.compile(IMAGE_FORMAT);
        Matcher matcher = pattern.matcher(markDownContent);
        String sharImage = "";
        while (matcher.find()) {
            String imageMarkDown = matcher.group(0);
            String id = matcher.group(1);

            String imgPath = APP_DOMAIN + "api/" + API_VERSION_2 + "/files/" + id;
            // 用原图，不进行质量压缩，不然 gif 不了
//            String imgPath = APP_DOMAIN + "api/" + API_VERSION_2 + "/files/" + id + "?q=80";
            if (TextUtils.isEmpty(sharImage)) {
                sharImage = imgPath;
            }
            String iamgeTag = imageMarkDown.replaceAll("\\(\\d+\\)", "(" + imgPath + ")").replace("@", "");

            markDownContent = markDownContent.replace(imageMarkDown, iamgeTag);
            dealImageList(imgPath, id);
        }
        if (!TextUtils.isEmpty(sharImage)) {
            Observable.just(sharImage)
                    .observeOn(Schedulers.io())
                    .subscribe(url -> {
                        try {
                            File cacheFile = Glide.with(mContext)
                                    .load(url)
                                    .downloadOnly(200, 200)
                                    .get();
                            sharBitmap = BitmapUtils.getBitmapFromFileDescriptor(cacheFile, 200, 200, true);
                        } catch (Exception e) {
                            LogUtils.d(e.getMessage());
                        }
                    });

        }
        if (markDownContent.startsWith("<blockquote>")) {
            markDownContent = "&nbsp;" + markDownContent;
        }
        Matcher matcherA = Pattern.compile(MarkdownConfig.FILTER_A_FORMAT).matcher(markDownContent);
        while (matcherA.find()) {
            markDownContent = matcherA.replaceFirst(matcherA.group(1));
            matcherA = Pattern.compile(MarkdownConfig.FILTER_A_FORMAT).matcher(markDownContent);
        }
        return markDownContent;
    }

    private void dealImageList(String imgPath, String id) {
        for (ImageBean item : mImgList) {
            if (item.getImgUrl().equals(imgPath)) {
                return;
            }
        }
        ImageBean imageBean = new ImageBean();
        imageBean.setImgUrl(imgPath);// 本地地址，也许有
        imageBean.setFeed_id(-1L);// -1,标识来自网页内容
        Toll toll = new Toll(); // 收费信息
        toll.setPaid(true);// 是否已經付費
        toll.setToll_money(0);// 付费金额
        toll.setToll_type_string("");// 付费类型
        toll.setPaid_node(0);// 付费节点
        imageBean.setToll(toll);
        imageBean.setStorage_id(Integer.parseInt(id));// 图片附件id
        mImgList.add(imageBean);
        try {
            AnimationRectBean rect = AnimationRectBean.buildFromImageView(mIvDetail);// 动画矩形
            animationRectBeanArrayList.add(rect);
        } catch (Exception e) {
            LogUtils.d("Cathy", e.toString());
        }

    }

    public void updateDigList(CirclePostListBean circlePostDetailBean) {
        if (circlePostDetailBean == null) {
            return;
        }

        mDigListView.setDigCount(circlePostDetailBean.getLikes_count());
        mDigListView.setPublishTime(circlePostDetailBean.getCreated_at());
        mDigListView.setViewerCount(circlePostDetailBean.getViews_count());
        // 设置点赞头像
        mDigListView.setDigUserHeadIconPost(circlePostDetailBean.getDigList());

        // 点赞信息
        if (circlePostDetailBean.getDigList() != null
                && circlePostDetailBean.getDigList().size() > 0) {
            // 设置跳转到点赞列表
            mDigListView.setDigContainerClickListener(digContainer -> {
                DigListActivity.startDigActivity(mContext, circlePostDetailBean.getId(), BaseDigItem.DigTypeEnum.POST);
            });
        }
    }

    /**
     * 更新评论页面
     */
    public void updateCommentView(CirclePostListBean circlePostDetailBean) {
        // 评论信息
        if (circlePostDetailBean.getComments_count() != 0) {
            mCommentHintView.setVisibility(View.VISIBLE);
            mCommentCountView.setText(mContext.getString(R.string.dynamic_comment_count, circlePostDetailBean.getComments_count() + ""));
        } else {
            mCommentHintView.setVisibility(View.GONE);
        }
    }

    /**
     * 更新打赏内容
     *
     * @param sourceId         source id  for this reward
     * @param data             reward's users
     * @param rewardsCountBean all reward data
     * @param rewardType       reward type
     */
    public void updateReward(long sourceId, List<RewardsListBean> data, RewardsCountBean rewardsCountBean, RewardType rewardType, String moneyName) {
        mReWardView.initData(sourceId, data, rewardsCountBean, rewardType, moneyName);
        mReWardView.setOnRewardsClickListener(() -> {
            if (mCirclePostListBean == null) {
                return true;
            }
            boolean isJoined = mCirclePostListBean.getGroup().getJoined() != null && mCirclePostListBean.getGroup().getJoined().getAudit() == CircleJoinedBean.AuditStatus.PASS.value;
            boolean isBlackList = isJoined && CircleMembers.BLACKLIST.equals(mCirclePostListBean.getGroup().getJoined().getRole());
            if (mRewardClickListener != null) {
                mRewardClickListener.reward(isBlackList);
            }
            return !isBlackList;
        });
    }

    public void setReWardViewVisible(int visible) {
        mReWardView.setVisibility(visible);
    }

    public ReWardView getReWardView() {
        return mReWardView;
    }

    public void setAdvertViewVisible(int visible) {
        if (visible == View.GONE || !com.zhiyicx.common.BuildConfig.USE_ADVERT) {
            mDynamicDetailAdvertHeader.hideAdvert();
        } else if (visible == View.VISIBLE && com.zhiyicx.common.BuildConfig.USE_ADVERT
                && mDynamicDetailAdvertHeader.getAdvertListBeans() != null && !mDynamicDetailAdvertHeader.getAdvertListBeans().isEmpty()) {
            mDynamicDetailAdvertHeader.showAdvert();
        }
    }

    public void destroyedWeb() {
        destryWeb(mContent);
        destryWeb(mContentSubject);

    }

    public int scrollCommentToTop() {
        return mReWardView.getBottom();
    }

    public MarkdownView getContentWebView() {
        return mContent;
    }

    public MarkdownView getContentSubWebView() {
        return mContentSubject;
    }

    public boolean isCanGotoCircle() {
        return canGotoCircle;
    }

    public void setCanGotoCircle(boolean canGotoCircle) {
        this.canGotoCircle = canGotoCircle;
    }

    public Bitmap getSharBitmap() {
        if (sharBitmap == null) {
            sharBitmap = ConvertUtils.drawBg4Bitmap(ContextCompat.getColor(mContext, R.color.white), BitmapFactory.decodeResource(mContent
                    .getResources(), R.mipmap.icon).copy(Bitmap.Config.RGB_565, true));
        }
        return sharBitmap;
    }

    public interface ShowMessageListener {
        void showErrorMessage(String s);
    }

    public interface RewardClickListener {
        void reward(boolean isBlackList);
    }

    private ShowMessageListener mShowMessageListener;
    private RewardClickListener mRewardClickListener;

    public void setRewardClickListener(RewardClickListener rewardClickListener) {
        mRewardClickListener = rewardClickListener;
    }

    public void setShowMessageListener(ShowMessageListener showMessageListener) {
        mShowMessageListener = showMessageListener;
    }
}