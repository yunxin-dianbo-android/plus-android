package com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.jakewharton.rxbinding.view.RxView;
import com.trycatch.mysnackbar.ScreenUtil;
import com.zhiyi.emoji.ViewUtils;
import com.zhiyicx.baseproject.impl.imageloader.glide.transformation.GlideStokeTransform;
import com.zhiyicx.baseproject.utils.glide.GlideManager;
import com.zhiyicx.baseproject.widget.imageview.FilterImageView;
import com.zhiyicx.common.utils.DeviceUtils;
import com.zhiyicx.common.utils.DrawableProvider;
import com.zhiyicx.common.utils.TextViewUtils;
import com.zhiyicx.common.utils.ToastUtils;
import com.zhiyicx.common.utils.log.LogUtils;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.data.beans.DynamicCommentBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoChannelBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoListBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoListBean;
import com.zhiyicx.thinksnsplus.i.OnUserInfoClickListener;
import com.zhiyicx.thinksnsplus.modules.channel.VideoChannelActivity;
import com.zhiyicx.thinksnsplus.modules.superstar.AllStarActivity;
import com.zhiyicx.thinksnsplus.utils.ImageUtils;
import com.zhiyicx.thinksnsplus.utils.MyUtils;
import com.zhiyicx.thinksnsplus.widget.comment.DynamicListCommentView;
import com.zhiyicx.thinksnsplus.widget.comment.CirclePostListTopicView;
import com.zhiyicx.thinksnsplus.widget.comment.DynamicNoPullRecycleView;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.concurrent.TimeUnit;

import static com.zhiyicx.common.config.ConstantConfig.JITTER_SPACING_TIME;

/**
 * @Describe 动态列表适配器基类
 * requirement document :{@see https://github
 * .com/zhiyicx/thinksns-plus-document/blob/master/document/%E5%8A%A8%E6%80%81%E6%A8%A1%E5%9D%97.md}
 * design document  {@see https://github
 * .com/zhiyicx/thinksns-plus-document/blob/master/document/TS%2B%E8%A7%86%E8%A7%89%E8%A7%84%E8
 * %8C%83%202.0/TS%2B%E8%A7%86%E8%A7%89%E8%A7%84%E8%8C%83%202.0.pdf}
 * @Author Jungle68
 * @Date 2017/1/6
 * @Contact master.jungle68@gmail.com
 */

public class DynamicListBaseItem4Video implements ItemViewDelegate<VideoListBean> {
    protected final String TAG = this.getClass().getSimpleName();
    private static final int CURREN_CLOUMS = 0;
    public static final int DEFALT_IMAGE_HEIGHT = 280;
    public static final int DEFALT_IMAGE_WITH = 360;
    protected final int mHightPixels; // 屏幕高度
    protected final int mDiverwith; // 分割先的宽高
    protected int mImageContainerWith; // 图片容器最大宽度
    protected int mImageMaxHeight; // 单张图片最大高度
    protected Context mContext;

    protected boolean showToolMenu = true;// 是否显示工具栏:默认显示
    //    protected boolean showTopicTags = true;// 是否显示话题标签:默认显示
    protected boolean showCommentList = true;// 是否显示评论内容:默认显示
    protected boolean showReSendBtn = true;// 是否显示重发按钮
    protected long start;
    protected int mDrawableList;
    protected FilterImageView mFilterImageView;


    //主要是用于明星
    private VideoChannelBean videoChannelBean;

    public void setVideoChannelBean(VideoChannelBean videoChannelBean) {
        this.videoChannelBean = videoChannelBean;
    }


    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        mOnImageClickListener = onImageClickListener;
    }

    /**
     * 图片点击监听
     */
    protected OnImageClickListener mOnImageClickListener;

    public void setOnUserInfoClickListener(OnUserInfoClickListener onUserInfoClickListener) {
        mOnUserInfoClickListener = onUserInfoClickListener;
    }

    /**
     * 用户信息点击监听
     */
    protected OnUserInfoClickListener mOnUserInfoClickListener;

    protected TextViewUtils.OnSpanTextClickListener mOnSpanTextClickListener;

    public void setOnMenuItemClickLisitener(OnMenuItemClickLisitener onMenuItemClickLisitener) {
        mOnMenuItemClickLisitener = onMenuItemClickLisitener;
    }

    /**
     * 工具栏被点击
     */
    protected OnMenuItemClickLisitener mOnMenuItemClickLisitener;


    public void setOnReSendClickListener(OnReSendClickListener onReSendClickListener) {
        mOnReSendClickListener = onReSendClickListener;
    }

    protected OnReSendClickListener mOnReSendClickListener;

    public void setOnCommentClickListener(DynamicListCommentView.OnCommentClickListener
                                                  onCommentClickListener) {
        mOnCommentClickListener = onCommentClickListener;
    }

    protected DynamicListCommentView.OnCommentClickListener mOnCommentClickListener;

    private CirclePostListTopicView.OnTopicClickListener mOnTopicClickListener;

    protected DynamicListCommentView.OnMoreCommentClickListener mOnMoreCommentClickListener;

    public void setOnTopicClickListener(CirclePostListTopicView.OnTopicClickListener onTopicClickListener) {
        mOnTopicClickListener = onTopicClickListener;
    }

    public void setOnCommentStateClickListener(DynamicNoPullRecycleView.OnCommentStateClickListener<DynamicCommentBean>
                                                       onCommentStateClickListener) {
        mOnCommentStateClickListener = onCommentStateClickListener;
    }

    protected DynamicNoPullRecycleView.OnCommentStateClickListener<DynamicCommentBean> mOnCommentStateClickListener;

    public void setOnMoreCommentClickListener(DynamicListCommentView.OnMoreCommentClickListener
                                                      onMoreCommentClickListener) {
        mOnMoreCommentClickListener = onMoreCommentClickListener;
    }

    public DynamicListBaseItem4Video(Context context) {
        mContext = context;
        mHightPixels = DeviceUtils.getScreenHeight(context);
        int margin = 2 * context.getResources().getDimensionPixelSize(R.dimen
                .dynamic_list_image_marginright);
        mDiverwith = context.getResources().getDimensionPixelSize(R.dimen.spacing_small);
        mImageContainerWith = DeviceUtils.getScreenWidth(context) - margin;
        mImageContainerWith = mImageContainerWith > context.getResources().getDimensionPixelSize(R.dimen
                .dynamic_one_image_max_with) ? context.getResources().getDimensionPixelSize(R.dimen
                .dynamic_one_image_max_with) : mImageContainerWith;
        // 最大高度是最大宽度的4/3 保持 宽高比 3：4
        mImageMaxHeight = mImageContainerWith * 4 / 3;

    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_dynamic_list_4_video;
    }

    @Override
    public boolean isForViewType(VideoListBean item, int position) {
        // 当本地和服务器都没有图片的时候，使用
        return true;
    }

    /**
     * 获取图片数量
     *
     * @return
     */
    protected int getImageCounts() {
        return CURREN_CLOUMS;
    }

    /**
     * @param holder
     * @param dynamicBean
     * @param lastT       android:descendantFocusability
     * @param position
     */
    @Override
    public void convert(ViewHolder holder, VideoListBean dynamicBean, VideoListBean
            lastT, final int position, int itemCounts) {
        if (dynamicBean.getVideo() != null && dynamicBean.getStar() != null) {
            dynamicBean.getVideo().setTags(dynamicBean.getTags());
            bindView(holder, dynamicBean.getVideo());
            //绑定明星
            holder.getTextView(R.id.tv_star_name).setText(dynamicBean.getStar().getName() + "");
            GlideManager.glideCircle(mContext, holder.getImageViwe(R.id.iv_star_head), dynamicBean.getStar().getAvatar(), R.mipmap.ic_default_user_head_circle);

            RxView.clicks(holder.getView(R.id.rl_star_name_head))
                    .throttleFirst(JITTER_SPACING_TIME, TimeUnit.SECONDS)
                    .subscribe(aVoid -> {
                        VideoChannelActivity.starVideoChannelActivity(mContext, dynamicBean.getStar());
//                        AllStarActivity.startAllStarActivity(mContext);
//                        ToastUtils.showToast("去明星塞选页面");
                    });
        } else {
            bindView(holder, dynamicBean);
        }
    }


    private void bindView(ViewHolder holder, VideoListBean dynamicBean) {
        start = System.currentTimeMillis();
        mDrawableList = 0;
        try {
            // 防止个人中心没后头像错误
            GlideManager.glide(mContext, holder.getImageViwe(R.id.iv_video_bg), MyUtils.getImagePath(dynamicBean.getCover(), DeviceUtils.getScreenWidth(mContext) - ViewUtils.dip2px(mContext, 20)));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        LinearLayout llVideoInfoContanner = holder.getView(R.id.ll_video_info_contanner);
        holder.getTextView(R.id.tv_video_title).setText(dynamicBean.getName() + "");
        holder.getTextView(R.id.tv_video_des).setText(dynamicBean.getSummary() + "");
        TextView tvTag1 = holder.getTextView(R.id.tv_tag_1);
        TextView tvTag2 = holder.getTextView(R.id.tv_tag_2);
        TextView tvTag3 = holder.getTextView(R.id.tv_tag_3);
        TextView tvTag4 = holder.getTextView(R.id.tv_tag_4);
        TextView tvTag5 = holder.getTextView(R.id.tv_tag_5);
        ImageView ivTagMore = holder.getImageViwe(R.id.iv_tag_more);
        tvTag1.setVisibility(View.GONE);
        tvTag2.setVisibility(View.GONE);
        tvTag3.setVisibility(View.GONE);
        tvTag4.setVisibility(View.GONE);
        tvTag5.setVisibility(View.GONE);
        ivTagMore.setVisibility(View.GONE);
        if (dynamicBean.getDuration() != null) {
            String timeFormat = MyUtils.timeFormat(dynamicBean.getDuration().intValue() * 1000);
            holder.getTextView(R.id.tv_time).setText(timeFormat);
            holder.getTextView(R.id.tv_time).setVisibility(View.VISIBLE);
        } else {
            holder.getTextView(R.id.tv_time).setText("00:00");
        }
        if (dynamicBean.getTags() != null) {
            for (int i = 0; i < dynamicBean.getTags().size(); i++) {
                VideoListBean.TagsBean item = dynamicBean.getTags().get(i);
                if (i == 0) {
                    tvTag1.setText(item.getName());
                    tvTag1.setVisibility(View.VISIBLE);
                } else if (i == 1) {
                    tvTag2.setText(item.getName());
                    tvTag2.setVisibility(View.VISIBLE);
                } else if (i == 2) {
                    tvTag3.setText(item.getName());
                    tvTag3.setVisibility(View.VISIBLE);
                } else if (i == 3) {
                    tvTag4.setText(item.getName());
                    tvTag4.setVisibility(View.VISIBLE);
                } else if (i == 4) {
                    tvTag5.setText(item.getName());
                    tvTag5.setVisibility(View.VISIBLE);
                }
            }
            if (dynamicBean.getTags().size() > 5) {
                ivTagMore.setVisibility(View.VISIBLE);
            }
        }
        //表示是明星的视频
        View starHeadContentView = holder.getView(R.id.rl_star_name_head);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.getTextView(R.id.tv_video_title).getLayoutParams();
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) llVideoInfoContanner.getLayoutParams();
        if (videoChannelBean != null && videoChannelBean.getId() == 3) {
            starHeadContentView.setVisibility(View.VISIBLE);
            layoutParams.topMargin = ViewUtils.dip2px(mContext, 36);
            layoutParams2.topMargin = ViewUtils.dip2px(mContext, 24);

        } else {
            starHeadContentView.setVisibility(View.GONE);
            layoutParams.topMargin = ViewUtils.dip2px(mContext, 12);
            layoutParams2.topMargin = ViewUtils.dip2px(mContext, 10);
        }
        holder.getTextView(R.id.tv_video_title).setLayoutParams(layoutParams);
        llVideoInfoContanner.setLayoutParams(layoutParams2);
    }

    /**
     * 用户信息被点击
     *
     * @param view
     * @param dynamicBean
     */
    private void setUserInfoClick(View view, final VideoListBean dynamicBean) {
        RxView.clicks(view)
                .throttleFirst(JITTER_SPACING_TIME, TimeUnit.SECONDS)
                .subscribe(aVoid -> {
                    if (mOnUserInfoClickListener != null) {
//                        mOnUserInfoClickListener.onUserInfoClick(dynamicBean.getUserInfoBean());
                    }
                });
    }


    /**
     * 设置 imageview 点击事件，以及显示
     *
     * @param view        the target
     * @param dynamicBean item data
     * @param positon     image item position
     * @param part        this part percent of imageContainer
     */
    protected void initImageView(final ViewHolder holder, FilterImageView view,
                                 final VideoListBean dynamicBean, final int positon, int part) {
//        if (dynamicBean.getImages() != null && dynamicBean.getImages().size() > 0) {
//            if (mFilterImageView == null) {
//                mFilterImageView = view;
//            }
//            VideoListBean.ImagesBean imageBean = dynamicBean.getImages().get(positon);
//            view.setLoaded(false);
//            if (TextUtils.isEmpty(imageBean.getImgUrl())) {
//                // 是否是 gif
//                boolean isGif = ImageUtils.imageIsGif(imageBean.getImgMimeType());
//                view.setIshowGifTag(isGif);
//                if (isGif) {
//                    holder.addGifView(view);
//                }
//                // 是否是长图
//                view.showLongImageTag(imageBean.hasLongImage());
//                Glide.with(view.getContext())
//                        .load(imageBean.getGlideUrl())
//                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                        .dontAnimate()
//                        .transform(new GlideStokeTransform(mContext, 1, Color.LTGRAY))
//                        .placeholder(R.drawable.shape_default_image)
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .error(R.drawable.shape_default_image)
//                        .into(new GlideDrawableImageViewTarget(view, 0));
//            } else {
//                BitmapFactory.Options option = DrawableProvider.getPicsWHByFile(imageBean.getImgUrl());
//                boolean isGif = ImageUtils.imageIsGif(option.outMimeType);
//                view.setIshowGifTag(isGif);
//                if (isGif) {
//                    holder.addGifView(view);
//                }
//                view.showLongImageTag(isLongImage(option.outHeight, option.outWidth));
//
//                Glide.with(view.getContext())
//                        .load(imageBean.getImgUrl())
//                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                        .dontAnimate()
//                        .transform(new GlideStokeTransform(mContext, 1, Color.LTGRAY))
//                        .override(imageBean.getCurrentWith(), imageBean.getCurrentWith())
//                        .placeholder(R.drawable.shape_default_image)
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .error(R.drawable.shape_default_image)
//                        .into(new GlideDrawableImageViewTarget(view, 0));
//            }
//        }
//
//        RxView.clicks(view)
//                .throttleFirst(JITTER_SPACING_TIME, TimeUnit.SECONDS)
//                .subscribe(aVoid -> {
//                    if (mOnImageClickListener != null) {
//                        mOnImageClickListener.onImageClick(holder, dynamicBean, positon);
//                    }
//                });
    }

    /**
     * 获取当前item 的宽
     *
     * @param part
     * @return
     */
    protected int getCurrenItemWith(int part) {
        try {
            return (mImageContainerWith - (getCurrenCloums() - 1) * mDiverwith) / getCurrenCloums() * part;
        } catch (Exception e) {
            LogUtils.d("获取当前 item 的宽 = 0");
        }
        return 0;
    }

    protected int getCurrenCloums() {
        return CURREN_CLOUMS;
    }

    /**
     * 是否是长图
     *
     * @param imageHeight 需要判断的图片的高
     * @param imageWith   需要判断的图片的宽
     * @return
     */
    public boolean isLongImage(int imageHeight, int imageWith) {
//        float a = (float) imageHeight * mHightPixels / ((float) imageWith * mHightPixels);
//
//        return a > 3 || a < .3f;
        return ImageUtils.isLongImage(imageHeight, imageWith);
    }

    /**
     * image interface
     */
    public interface OnImageClickListener {
        void onImageClick(ViewHolder holder, VideoListBean dynamicBean, int position);
    }

    /**
     * like interface
     */
    public interface OnMenuItemClickLisitener {
        void onMenuItemClick(View view, int dataPosition, int viewPosition);
    }

    /**
     * resend interface
     */
    public interface OnReSendClickListener {
        void onReSendClick(int position);
    }

    public DynamicListBaseItem4Video setShowToolMenu(boolean showToolMenu) {
        this.showToolMenu = showToolMenu;
        return this;
    }

    public DynamicListBaseItem4Video setShowCommentList(boolean showCommentList) {
        this.showCommentList = showCommentList;
        return this;
    }

    public DynamicListBaseItem4Video setShowReSendBtn(boolean showReSendBtn) {
        this.showReSendBtn = showReSendBtn;
        return this;
    }

    public void setOnSpanTextClickListener(TextViewUtils.OnSpanTextClickListener
                                                   onSpanTextClickListener) {
        mOnSpanTextClickListener = onSpanTextClickListener;
    }

//    protected int[] getToolImages() {
//        return null;
//    }

//    /**
//     * 工具栏第几个是否显示
//     *
//     * @return
//     */
//    protected int getVisibleOne() {
//        return View.VISIBLE;
//    }
//
//    protected int getVisibleTwo() {
//        return View.VISIBLE;
//    }
//
//    protected int getVisibleThree() {
//        return View.VISIBLE;
//    }
//
//    protected int getVisibleFour() {
//        return View.VISIBLE;
//    }

//    /**
//     * 网页链接
//     *
//     * @param VideoListBean
//     * @param content
//     * @return
//     */
//    protected List<Link> setLiknks(final VideoListBean VideoListBean, String content) {
//        List<Link> links = new ArrayList<>();
//        if (content.contains(Link.DEFAULT_NET_SITE)) {
//            Link commentNameLink = new Link(Link.DEFAULT_NET_SITE)
//                    .setTextColor(ContextCompat.getColor(mContext, R.color
//                            .themeColor))
//                    .setLinkMetadata(LinkMetadata.builder()
//                            .putSerializableObj(LinkMetadata.METADATA_KEY_COTENT, new NetUrlHandleBean(VideoListBean.getFeed_content()))
//                            .putSerializableObj(LinkMetadata.METADATA_KEY_TYPE, LinkMetadata.SpanType.NET_SITE)
//                            .build())
//                    .setTextColorOfHighlightedLink(ContextCompat.getColor(mContext, R.color
//                            .general_for_hint))
//                    .setHighlightAlpha(CustomPopupWindow.POPUPWINDOW_ALPHA)
//                    .setOnClickListener((clickedText, linkMetadata) -> CustomWEBActivity.startToOutWEBActivity(mContext, clickedText))
//                    .setOnLongClickListener((clickedText, linkMetadata) -> {
//
//                    })
//                    .setUnderlined(false);
//            links.add(commentNameLink);
//        }
//        Link link = new Link(Pattern.compile(MarkdownConfig.AT_FORMAT))
//                .setTextColor(ContextCompat.getColor(mContext, R.color.important_for_theme))
//                .setUnderlined(false)
//                .setOnClickListener((clickedText, linkMetadata) -> {
//                    if (mOnUserInfoClickListener != null) {
//                        mOnUserInfoClickListener.onUserInfoClick(new UserInfoBean(RegexUtils.replaceAllAt(clickedText)));
//                    }
//                })
//                .setTextColorOfHighlightedLink(ContextCompat.getColor(mContext, R.color.important_for_content));
//        links.add(link);
//        return links;
//    }

}

