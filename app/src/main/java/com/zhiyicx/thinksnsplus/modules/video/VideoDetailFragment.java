package com.zhiyicx.thinksnsplus.modules.video;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jakewharton.rxbinding.view.RxView;
import com.trycatch.mysnackbar.ScreenUtil;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhiyi.emoji.ViewUtils;
import com.zhiyicx.baseproject.config.ApiConfig;
import com.zhiyicx.baseproject.utils.glide.GlideManager;
import com.zhiyicx.common.utils.ConvertUtils;
import com.zhiyicx.common.utils.DeviceUtils;
import com.zhiyicx.common.utils.FastBlur;
import com.zhiyicx.common.utils.StatusBarUtils;
import com.zhiyicx.common.utils.UIUtils;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.base.AppApplication;
import com.zhiyicx.thinksnsplus.base.fordownload.TSListFragmentForDownload;
import com.zhiyicx.thinksnsplus.data.beans.RecommandVideoBean;
import com.zhiyicx.thinksnsplus.data.beans.SearchHistoryBeanV2;
import com.zhiyicx.thinksnsplus.data.beans.VideoCommentBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoDetailBean;
import com.zhiyicx.thinksnsplus.data.beans.VideoListBean;
import com.zhiyicx.thinksnsplus.i.OnCommentLikeClickListener;
import com.zhiyicx.thinksnsplus.i.OnCommentTextClickListener;
import com.zhiyicx.thinksnsplus.modules.dynamic.detail.adapter.DynamicDetailCommentItem;
import com.zhiyicx.thinksnsplus.modules.dynamic.list.adapter.DynamicListBaseItem4Video;
import com.zhiyicx.thinksnsplus.modules.shortvideo.helper.ZhiyiVideoView;
import com.zhiyicx.thinksnsplus.modules.shortvideo.helper.ZhiyiVideoView4VideoDetail;
import com.zhiyicx.thinksnsplus.modules.video.adapter.VideoDetailCommentItem;
import com.zhiyicx.thinksnsplus.utils.MyUtils;
import com.zhiyicx.thinksnsplus.widget.DynamicCommentEmptyItem;
import com.zhiyicx.thinksnsplus.widget.FluidLayout;
import com.zhiyicx.thinksnsplus.widget.VideoCommentEmptyItem;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import cn.jzvd.JZMediaManager;
import cn.jzvd.JZVideoPlayerManager;
import cn.jzvd.JZVideoPlayerStandard;

import static cn.jzvd.JZVideoPlayer.URL_KEY_DEFAULT;
import static com.zhiyicx.common.config.ConstantConfig.JITTER_SPACING_TIME;

public class VideoDetailFragment extends TSListFragmentForDownload<VideoDetailCongract.Presenter, VideoCommentBean> implements VideoDetailCongract.View<VideoDetailCongract.Presenter>, MultiItemTypeAdapter.OnItemClickListener {

    VideoListBean videoListBean;
    //    @Inject
//    VideoDetailPresenter videoDetailPresenter;
    @BindView(R.id.videoplayer)
    ZhiyiVideoView4VideoDetail videoView;
    @BindView(R.id.v_status_bar_placeholder)
    View vStatusBarPlaceHolder;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.tv_collection)
    TextView tvCollection;
    @BindView(R.id.tv_share)
    TextView tvShare;

    /**
     * Gif 是否直接播放
     */
    private boolean mIsGifPlay = false;
    private boolean isListToDetail = false;

    private Bitmap sharBitmap;

    View videoInfoView;
    TextView tvCommentCount;
    //评论回复的用户id
    private int mReplyUserId = 0;
    VideoDetailBean videoDetailBean;


    //是回复谁呢
    String reply_user = null;
    //被回复的评论
    String reply_comment_id = null;

    int replyCommentPosition = 0;

    VideoCommentBean emptyTag = new VideoCommentBean();

    @Override
    protected int getBodyLayoutId() {
        return R.layout.fragment_video_detial_layout;
    }

    @Override
    protected void initData() {
        super.initData();
        if (mPresenter != null) {
            mPresenter.requestNetData(DEFAULT_PAGE_MAX_ID, false);
            mPresenter.getVideoDetailInfo();
        }
        tvCommentCount.setText("评论（" + videoListBean.getComment_count() + "）");
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
//        int topCon, int bottomCon, int startBtn, int loadingPro, int thumbImg, int bottomPro, int retryLayout
        videoView.setAllControlsVisiblity(View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.VISIBLE, View.VISIBLE, View.INVISIBLE, View.INVISIBLE);
        videoView.setiVideoPlayStatusChangedLisnter(new ZhiyiVideoView4VideoDetail.IVideoPlayStatusChangedLisnter() {
            @Override
            public void onStatePause() {
                try {
                    if (JZMediaManager.instance().jzMediaInterface != null && JZMediaManager.isPlaying()) {
                        double time = JZMediaManager.getCurrentPosition();
                        double dTime = time / 1000;
                        mPresenter.uploadVideoRecord(videoDetailBean.getId() + "", dTime + "");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onAutoCompletion() {
                mPresenter.uploadVideoRecord(videoDetailBean.getId() + "", "0");
            }

            @Override
            public void onStatePrepared() {
                mPresenter.uploadVideoRecord(videoDetailBean.getId() + "", "0");
            }

            @Override
            public void onPrepared() {
//                mPresenter.uploadVideoRecord(videoDetailBean.getId() + "","0");
            }
        });
        videoInfoView = LayoutInflater.from(getContext()).inflate(R.layout.view_video_detail_header_video_info, mRvList, false);
        mHeaderAndFooterWrapper.addHeaderView(videoInfoView);
        mHeaderAndFooterWrapper.notifyDataSetChanged();
        initToolBar();
        tvCommentCount = videoInfoView.findViewById(R.id.tv_comment_count);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() != null) {
                    getActivity().finish();
                }

            }
        });
        TextView tvExchang = videoInfoView.findViewById(R.id.tv_exchang);
        tvExchang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.getRecommandVideo();
            }
        });
        tvComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reply_user = null;
                reply_comment_id = null;
                showCommentView();
                String contentHint = getString(R.string.default_input_hint);
                mIlvComment.setEtContentHint(contentHint);
                mReplyUserId = 0;
            }
        });

        RxView.clicks(tvCollection)
                .throttleFirst(JITTER_SPACING_TIME, TimeUnit.SECONDS)
                .subscribe(aVoid -> mPresenter.collectVideo(videoDetailBean.getId() + "", !videoDetailBean.isIs_collect()));
        RxView.clicks(tvShare)
                .throttleFirst(JITTER_SPACING_TIME, TimeUnit.SECONDS)
                .subscribe(aVoid -> share());


    }

    private void share() {

    }

    private void initToolBar() {
        // toolBar设置状态栏高度的marginTop
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DeviceUtils
                .getStatuBarHeight(getContext()));
        vStatusBarPlaceHolder.setLayoutParams(layoutParams);
        // 适配非6.0以上、非魅族系统、非小米系统状态栏
        if (getActivity() != null && StatusBarUtils.intgetType(getActivity().getWindow()) == 0) {
//            vStatusBarPlaceholder.setBackgroundResource(R.color.themeColor);
            vStatusBarPlaceHolder.setBackgroundResource(R.drawable.common_statubar_bg);
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoListBean = getArguments().getParcelable(VideoListBean.class.getSimpleName());
    }

    public static VideoDetailFragment newInstance(Bundle bundle) {
        VideoDetailFragment fragment = new VideoDetailFragment();
//        Bundle args = new Bundle();
//        args.putParcelable(VideoListBean.class.getSimpleName(), videoListBean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected MultiItemTypeAdapter getAdapter() {
        MultiItemTypeAdapter adapter = new MultiItemTypeAdapter<>(getContext(), mListDatas);
        VideoDetailCommentItem dynamicDetailCommentItem = new VideoDetailCommentItem();

        dynamicDetailCommentItem.setOnCommentLikeClickListener(new OnCommentLikeClickListener() {
            @Override
            public void onCommentLikeClick(int position) {
                int realPostion = position - mHeaderAndFooterWrapper.getHeadersCount();
                VideoCommentBean videoCommentBean = mListDatas.get(realPostion);
                long commentId = videoCommentBean.getId() * 1l;
                mPresenter.handleLike4Comment(commentId, !videoCommentBean.isHas_like(), realPostion);
            }
        });
        dynamicDetailCommentItem.setOnCommentTextClickListener(new OnCommentTextClickListener() {
            @Override
            public void onCommentTextClick(int position) {
                VideoCommentBean videoCommentBean = mListDatas.get(position);
                reply_comment_id = videoCommentBean.getId() + "";
                reply_user = videoCommentBean.getUser_id() + "";
                replyCommentPosition = position;
                showCommentView();
            }

            @Override
            public void onCommentTextLongClick(int position) {

            }
        });
        adapter.addItemViewDelegate(dynamicDetailCommentItem);

        VideoCommentEmptyItem dynamicCommentEmptyItem = new VideoCommentEmptyItem();
        adapter.addItemViewDelegate(dynamicCommentEmptyItem);
        adapter.setOnItemClickListener(this);
        return adapter;
    }

    protected void setAdapter(MultiItemTypeAdapter adapter, ItemViewDelegate
            dynamicListBaseItem) {
        adapter.addItemViewDelegate(dynamicListBaseItem);
    }
//    @Override
//    protected int getToolBarLayoutId() {
//        return R.layout.toolbar_custom_contain_status_bar;
//    }

    /**
     * 获取toolbar的布局文件,如果需要返回自定义的toolbar布局，重写该方法；否则默认返回缺省的toolbar
     */

    @Override
    protected boolean setUseStatusView() {
        return false;
    }


    @Override
    protected boolean setUseSatusbar() {
        return true;
    }

    @Override
    protected boolean showToolbar() {
        return false;
    }

    @Override
    protected boolean showToolBarDivider() {
        return false;
    }

    @Override
    public VideoListBean getVideoListBean() {
        return videoListBean;
    }

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    @Override
    public void onGetVideoDetailSuccess(VideoDetailBean videoDetailBean) {
        this.videoDetailBean = videoDetailBean;
        tvCommentCount.setText("评论（" + videoDetailBean.getComment_count() + "）");
        videoView.setShareInterface(new ZhiyiVideoView.ShareInterface() {
            @Override
            public void share(int position) {

            }

            @Override
            public void shareWihtType(int position, SHARE_MEDIA type) {

            }
        });
        setCollectView(videoDetailBean.isIs_collect());

        String videoUrl = String.format(ApiConfig.APP_DOMAIN + ApiConfig.FILE_PATH,
                videoDetailBean.getPlay_url());
        if (JZVideoPlayerManager.getFirstFloor() != null
                && !JZVideoPlayerManager.getCurrentJzvd().equals(videoView)) {

            LinkedHashMap<String, Object> map = (LinkedHashMap) JZVideoPlayerManager.getFirstFloor().dataSourceObjects[0];
            if (map != null) {
                isListToDetail = videoUrl.equals(map.get(URL_KEY_DEFAULT).toString());
            }

            if (isListToDetail) {
                videoView.setUp(videoUrl, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL);
                videoView.positionInList = 0;
                videoView.setState(ZhiyiVideoView.CURRENT_STATE_PAUSE);
                videoView.positionInList = JZVideoPlayerManager.getFirstFloor().positionInList;
                videoView.addTextureView();
                if (JZVideoPlayerManager.getFirstFloor() instanceof ZhiyiVideoView) {
                    ZhiyiVideoView firstFloor = (ZhiyiVideoView) JZVideoPlayerManager.getFirstFloor();
                    videoView.mVideoFrom = firstFloor.mVideoFrom;
                }
                JZVideoPlayerManager.setFirstFloor(videoView);
                videoView.startProgressTimer();
//                if (state == ZhiyiVideoView.CURRENT_STATE_PAUSE) {
                videoView.startButton.callOnClick();
//                }
            } else {
                videoView.setUp(videoUrl, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL);
                videoView.positionInList = 0;
            }

        } else {
            videoView.setUp(videoUrl, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL);
            videoView.positionInList = 0;
        }

        Glide.with(getContext())
                .load(MyUtils.getImagePath(videoDetailBean.getCover(), DeviceUtils.getScreenWidth(getContext())))
                .placeholder(R.drawable.shape_default_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.shape_default_image)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model,
                                               Target<GlideDrawable> target, boolean
                                                       isFirstResource) {
                        return false;
                    }

                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String
                            model, Target<GlideDrawable> target, boolean
                                                           isFromMemoryCache, boolean
                                                           isFirstResource) {
                        sharBitmap = ConvertUtils.drawable2BitmapWithWhiteBg(getContext(), resource, R.mipmap.icon);
                        Bitmap bitmap = FastBlur.blurBitmapForShortVideo(ConvertUtils.drawable2Bitmap(resource), resource
                                .getIntrinsicWidth(), resource
                                .getIntrinsicHeight());
                        videoView.setBackground(new BitmapDrawable(getContext().getResources(), bitmap));
                        return false;
                    }
                })
                .into(videoView.thumbImageView);
        Float progress = (videoDetailBean.getProgress() == null ? 0f : videoDetailBean.getProgress()) * 1000f;
        videoView.seekToInAdvance = progress.intValue();
//        JZMediaManager.seekTo(progress.intValue());
        TextView tvTitle = videoInfoView.findViewById(R.id.tv_title);
        TextView tvSummary = videoInfoView.findViewById(R.id.tv_video_summary);
        TextView tvTag1 = videoInfoView.findViewById(R.id.tv_tag_1);
        TextView tvTag2 = videoInfoView.findViewById(R.id.tv_tag_2);
        TextView tvTag3 = videoInfoView.findViewById(R.id.tv_tag_3);
        TextView tvTag4 = videoInfoView.findViewById(R.id.tv_tag_4);
//        TextView tvTag5 = videoInfoView.findViewById(R.id.tv_tag_5);

        FluidLayout fluidLayout = videoInfoView.findViewById(R.id.fluildlayout_tag);
        ImageView ivArrowDown = videoInfoView.findViewById(R.id.iv_arrow_down);
        LinearLayout llArrowDownUpParent = videoInfoView.findViewById(R.id.ll_arrow_down_up_parent);


        tvTag1.setVisibility(View.GONE);
        tvTag2.setVisibility(View.GONE);
        tvTag3.setVisibility(View.GONE);
        tvTag4.setVisibility(View.GONE);
//      TextView tvTag6 = videoInfoView.findViewById(R.id.tv_tag_6);
        LinearLayout llTagContainer = videoInfoView.findViewById(R.id.ll_tag_container);
        tvTitle.setText(videoDetailBean.getName() + "");
        tvSummary.setText(TextUtils.isEmpty(videoDetailBean.getSummary()) ? "" : videoDetailBean.getSummary() + "");

        tvSummary.post(new Runnable() {
            @Override
            public void run() {
                int pointCount = tvSummary.getLayout().getEllipsisCount(tvSummary.getLineCount() - 1);
                if (pointCount > 0) {
                    llArrowDownUpParent.setVisibility(View.VISIBLE);
                } else {
                    llArrowDownUpParent.setVisibility(View.GONE);
                }

            }
        });


        ivArrowDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvSummary.getMaxLines() == 2) {
                    ivArrowDown.setImageResource(R.mipmap.ic_arrow_up);
                    tvSummary.setMaxLines(99);
                } else {
                    ivArrowDown.setImageResource(R.mipmap.ic_arrow_down);
                    tvSummary.setMaxLines(2);
                }
            }
        });
        if (videoDetailBean.getTags() != null) {
            llTagContainer.setVisibility(View.GONE);

            fluidLayout.setVisibility(View.VISIBLE);
            FluidLayout.LayoutParams params = new FluidLayout.LayoutParams(
                    FluidLayout.LayoutParams.WRAP_CONTENT,
                    FluidLayout.LayoutParams.WRAP_CONTENT
            );
            for (int i = 0; i < videoDetailBean.getTags().size(); i++) {
                if (i > 7) {
                    break;
                }
                VideoListBean.TagsBean tagsBean = videoDetailBean.getTags().get(i);

                View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.item_fluid_video_detail_tag_layout, fluidLayout, false);
                ((TextView) contentView.findViewById(R.id.tv_search_history)).setText(tagsBean.getName() + "");
                fluidLayout.addView(contentView, params);
                contentView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // TODO: 2019/6/26  do tag click
                    }
                });
            }

        } else {
            llTagContainer.setVisibility(View.GONE);
            fluidLayout.setVisibility(View.GONE);
        }

    }


    @Override
    public void onGetRecommandVideoSuccess(List<RecommandVideoBean> recommandVideoBeans) {
        LinearLayout llRecommandContent = videoInfoView.findViewById(R.id.ll_recommand_content);
        LinearLayout llRecommandContent2 = videoInfoView.findViewById(R.id.ll_recommand_content_2);
        if (recommandVideoBeans != null) {
            llRecommandContent.setVisibility(View.VISIBLE);
            LinearLayout llVideoRecommand1 = videoInfoView.findViewById(R.id.ll_video_recommand_1);
            LinearLayout llVideoRecommand2 = videoInfoView.findViewById(R.id.ll_video_recommand_2);
            LinearLayout llVideoRecommand3 = videoInfoView.findViewById(R.id.ll_video_recommand_3);
            LinearLayout llVideoRecommand4 = videoInfoView.findViewById(R.id.ll_video_recommand_4);
            llVideoRecommand1.setVisibility(View.INVISIBLE);
            llVideoRecommand2.setVisibility(View.INVISIBLE);
            llVideoRecommand3.setVisibility(View.INVISIBLE);
            llVideoRecommand4.setVisibility(View.INVISIBLE);
            for (int i = 0; i < recommandVideoBeans.size(); i++) {
                RecommandVideoBean recommandVideoBean = recommandVideoBeans.get(i);
                if (i == 0) {
                    llVideoRecommand1.setVisibility(View.VISIBLE);
                    ImageView ivVideoBg1 = videoInfoView.findViewById(R.id.iv_video_bg_1);
                    TextView tvVideoName1 = videoInfoView.findViewById(R.id.tv_video_name_1);
                    GlideManager.glide(getContext(), ivVideoBg1, MyUtils.getImagePath(recommandVideoBean.video.getCover(), DeviceUtils.getScreenWidth(getContext()) / 2 - ViewUtils.dip2px(getContext(), 10)));
                    tvVideoName1.setText(recommandVideoBean.video.getName());
                    llVideoRecommand1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getActivity().finish();
                            VideoDetailActivity.starVideoDetailActivity(getContext(), recommandVideoBean.video);
                        }
                    });
                } else if (i == 1) {
                    llVideoRecommand2.setVisibility(View.VISIBLE);
                    ImageView ivVideoBg1 = videoInfoView.findViewById(R.id.iv_video_bg_2);
                    TextView tvVideoName1 = videoInfoView.findViewById(R.id.tv_video_name_2);
                    GlideManager.glide(getContext(), ivVideoBg1, MyUtils.getImagePath(recommandVideoBean.video.getCover(), DeviceUtils.getScreenWidth(getContext()) / 2 - ViewUtils.dip2px(getContext(), 10)));
                    tvVideoName1.setText(recommandVideoBean.video.getName());
                    llVideoRecommand2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getActivity().finish();
                            VideoDetailActivity.starVideoDetailActivity(getContext(), recommandVideoBean.video);
                        }
                    });
                } else if (i == 2) {
                    llRecommandContent2.setVisibility(View.VISIBLE);
                    llVideoRecommand3.setVisibility(View.VISIBLE);
                    ImageView ivVideoBg1 = videoInfoView.findViewById(R.id.iv_video_bg_3);
                    TextView tvVideoName1 = videoInfoView.findViewById(R.id.tv_video_name_3);
                    GlideManager.glide(getContext(), ivVideoBg1, MyUtils.getImagePath(recommandVideoBean.video.getCover(), DeviceUtils.getScreenWidth(getContext()) / 2 - ViewUtils.dip2px(getContext(), 10)));
                    tvVideoName1.setText(recommandVideoBean.video.getName());
                    llVideoRecommand3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getActivity().finish();
                            VideoDetailActivity.starVideoDetailActivity(getContext(), recommandVideoBean.video);
                        }
                    });
                } else if (i == 3) {
                    llVideoRecommand4.setVisibility(View.VISIBLE);
                    ImageView ivVideoBg1 = videoInfoView.findViewById(R.id.iv_video_bg_4);
                    TextView tvVideoName1 = videoInfoView.findViewById(R.id.tv_video_name_4);
                    GlideManager.glide(getContext(), ivVideoBg1, MyUtils.getImagePath(recommandVideoBean.video.getCover(), DeviceUtils.getScreenWidth(getContext()) / 2 - ViewUtils.dip2px(getContext(), 10)));
                    tvVideoName1.setText(recommandVideoBean.video.getName());
                    llVideoRecommand4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getActivity().finish();
                            VideoDetailActivity.starVideoDetailActivity(getContext(), recommandVideoBean.video);
                        }
                    });
                }
            }
        } else {
            llRecommandContent.setVisibility(View.GONE);
        }

    }

    @Override
    public void onCollectSuccess(boolean isCollected) {
        videoDetailBean.setIs_collect(isCollected);
        setCollectView(isCollected);
    }

    @Override
    public void onPublishCommentsSuccess(VideoCommentBean comment) {
        if (reply_comment_id != null) {
//            for (int i = 0; i < mListDatas.size(); i++) {
//                 mListDatas.get(i);
//                if(){
//
//                }
//            }
            if(mListDatas.get(replyCommentPosition).getComment_children() == null){
                mListDatas.get(replyCommentPosition).setComment_children(new ArrayList<>());
            }
            mListDatas.get(replyCommentPosition).getComment_children().add(0, comment);
            mListDatas.get(replyCommentPosition).setComment_children_count(mListDatas.get(replyCommentPosition).getComment_children_count() + 1);
        } else {
            mListDatas.add(0, comment);
        }
        videoDetailBean.setComment_count(videoDetailBean.getComment_count() + 1);
        tvCommentCount.setText("评论（" + videoDetailBean.getComment_count() + "）");
        mHeaderAndFooterWrapper.notifyDataSetChanged();
    }

    @Override
    public void onCommentLikeStatuChanged(boolean isLike, int position) {
        mListDatas.get(position).setHas_like(isLike);
        if (isLike) {
            int count = mListDatas.get(position).getComment_like_count() + 1;
            mListDatas.get(position).setComment_like_count(count);
        } else {
            int count = mListDatas.get(position).getComment_like_count() - 1;
            mListDatas.get(position).setComment_like_count(count < 0 ? 0 : count);
        }
        mHeaderAndFooterWrapper.notifyDataSetChanged();
    }

    private void setCollectView(boolean isCollected) {
        if (isCollected) {
            tvCollection.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_mine_collection, 0, 0);
            tvCollection.setTextColor(getColor(R.color.color_E73377));
        } else {
            tvCollection.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_collection_white, 0, 0);
            tvCollection.setTextColor(getColor(R.color.white));
        }
    }


    @Override
    public void refreshData(List datas) {
        if (mHeaderAndFooterWrapper != null) {
            setEmptyViewVisiable(mListDatas.isEmpty() && mHeaderAndFooterWrapper.getHeadersCount() <= 0);
            try {
                mHeaderAndFooterWrapper.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        return false;
    }

    @Override
    public void onPause() {
        videoView.onStatePause();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        videoView.releaseAllVideos();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     *
     */
    public void showCommentView() {
//        mLLBottomMenuContainer.setVisibility(View.INVISIBLE);
        // 评论
        mIlvComment.setVisibility(View.VISIBLE);
        mIlvComment.setSendButtonVisiable(true);
        mIlvComment.getFocus();
        mVShadow.setVisibility(View.VISIBLE);
        DeviceUtils.showSoftKeyboard(getActivity(), mIlvComment.getEtContent());
    }

    /**
     * 是否使用 评论界面
     *
     * @return
     */
    @Override
    protected boolean setUseInputCommentView() {
        return true;
    }

    @Override
    protected boolean setUseShadowView() {
        return true;
    }

    /**
     * comment send
     *
     * @param text
     */
    @Override
    public void onSendClick(View v, final String text) {
        com.zhiyicx.imsdk.utils.common.DeviceUtils.hideSoftKeyboard(getContext(), v);
        mIlvComment.setVisibility(View.GONE);
        mVShadow.setVisibility(View.GONE);

        mPresenter.publishComment(videoDetailBean.getId() + "", text, reply_user, reply_comment_id);
    }

    @Override
    protected void onShadowViewClick() {
        mIlvComment.setVisibility(View.GONE);
        mIlvComment.clearFocus();
        DeviceUtils.hideSoftKeyboard(getActivity(), mIlvComment.getEtContent());
//        mLLBottomMenuContainer.setVisibility(View.VISIBLE);
//        mLLBottomMenuContainer.setVisibility(View.GONE);
        mVShadow.setVisibility(View.GONE);
    }

    @Override
    public void onNetResponseSuccess(@NotNull List<VideoCommentBean> data, boolean isLoadMore) {
        if (data.size() == 0 && mListDatas.size() == 0) {
            mListDatas.add(emptyTag);
        } else if (data.size() > 0) {
            mListDatas.remove(emptyTag);
        }
        super.onNetResponseSuccess(data, isLoadMore);
    }
}
