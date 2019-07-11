package com.zhiyicx.thinksnsplus.modules.dynamic.detail.adapter;

import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.klinker.android.link_builder.Link;
import com.zhiyicx.common.config.MarkdownConfig;
import com.zhiyicx.common.utils.ConvertUtils;
import com.zhiyicx.common.utils.RegexUtils;
import com.zhiyicx.common.utils.TimeUtils;
import com.zhiyicx.common.utils.ToastUtils;
import com.zhiyicx.common.utils.log.LogUtils;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.data.beans.CirclePostCommentBean;
import com.zhiyicx.thinksnsplus.data.beans.DynamicCommentBean;
import com.zhiyicx.thinksnsplus.data.beans.UserInfoBean;
import com.zhiyicx.thinksnsplus.i.OnCommentLikeClickListener;
import com.zhiyicx.thinksnsplus.i.OnCommentTextClickListener;
import com.zhiyicx.thinksnsplus.i.OnUserInfoClickListener;
import com.zhiyicx.thinksnsplus.i.OnUserInfoLongClickListener;
import com.zhiyicx.thinksnsplus.utils.ImageUtils;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static com.zhiyicx.common.config.ConstantConfig.JITTER_SPACING_TIME;

/**
 * @Describe
 * @Author Jungle68
 * @Date 2017/3/10
 * @Contact master.jungle68@gmail.com
 */

public class DynamicDetailCommentItem implements ItemViewDelegate<DynamicCommentBean> {
    private OnUserInfoClickListener mOnUserInfoClickListener;
    private OnUserInfoLongClickListener mOnUserInfoLongClickListener;
    private OnCommentTextClickListener mOnCommentTextClickListener;
    private OnCommentResendListener mOnCommentResendListener;

    //    private OnCommentLikeClickListener onCommentLikeClickListener;
    public void setOnCommentLikeClickListener(OnCommentLikeClickListener onCommentLikeClickListener) {
        this.onCommentLikeClickListener = onCommentLikeClickListener;
    }

    //点赞的回调
    private OnCommentLikeClickListener onCommentLikeClickListener;
//    private OnCommentTextClickListener onCommentTextClickListener;

    public void setmOnUserInfoClickListener(OnUserInfoClickListener mOnUserInfoClickListener) {
        this.mOnUserInfoClickListener = mOnUserInfoClickListener;
    }


    public void setOnCommentTextClickListener(OnCommentTextClickListener onCommentTextClickListener) {
        mOnCommentTextClickListener = onCommentTextClickListener;
    }

    public void setOnCommentResendListener(OnCommentResendListener onCommentResendListener) {
        mOnCommentResendListener = onCommentResendListener;
    }

    public void setOnUserInfoClickListener(OnUserInfoClickListener onUserInfoClickListener) {
        mOnUserInfoClickListener = onUserInfoClickListener;
    }

    public void setOnUserInfoLongClickListener(OnUserInfoLongClickListener onUserInfoLongClickListener) {
        mOnUserInfoLongClickListener = onUserInfoLongClickListener;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_dynamic_detail_comment;
    }

    @Override
    public boolean isForViewType(DynamicCommentBean item, int position) {
        return !TextUtils.isEmpty(item.getComment_content());
    }

    @Override
    public void convert(ViewHolder holder, DynamicCommentBean dynamicCommentBean, DynamicCommentBean lastT, final int position, int itemCounts) {
        holder.setText(R.id.tv_name, dynamicCommentBean.getCommentUser().getName());
        holder.setText(R.id.tv_time, TimeUtils.getTimeFriendlyNormal(dynamicCommentBean.getCreated_at()));
        holder.setText(R.id.tv_content, dynamicCommentBean.getComment_content()/* setShowText(dynamicCommentBean, position)*/);
        holder.setText(R.id.tv_like_count, ConvertUtils.numberConvert(dynamicCommentBean.getComment_like_count())/* setShowText(dynamicCommentBean, position)*/);
        holder.setVisible(R.id.fl_tip, dynamicCommentBean.getState() == DynamicCommentBean.SEND_ERROR ? View.VISIBLE : View.GONE);

        ImageView ivLikeComment = holder.getImageViwe(R.id.iv_like_comment);

        if (dynamicCommentBean.isHas_like()) {
            ivLikeComment.setImageResource(R.mipmap.ic_dianzan);
        } else {
            ivLikeComment.setImageResource(R.mipmap.ic_dianzan_grey);
        }
        RxView.clicks(holder.getView(R.id.fl_tip))
                .throttleFirst(JITTER_SPACING_TIME, TimeUnit.SECONDS)
                .subscribe(aVoid -> {
                    if (mOnCommentResendListener != null) {
                        mOnCommentResendListener.reSendComment(dynamicCommentBean);
                    }
                });

        holder.setOnClickListener(R.id.iv_reply_comment, v -> {
            // TODO: 2019/6/12 回复
            if (mOnCommentTextClickListener != null) {
                mOnCommentTextClickListener.onCommentTextClick(position);
            }
//            ToastUtils.showToast("回复评论");
        });

        RxView.clicks(holder.getView(R.id.iv_like_comment))
                .throttleFirst(JITTER_SPACING_TIME, TimeUnit.SECONDS)
                .subscribe(aVoid -> {
                    if (onCommentLikeClickListener != null) {
                        onCommentLikeClickListener.onCommentLikeClick(position);
                    }
                });
//        holder.setOnClickListener(R.id.iv_like_comment, v -> {
////            ToastUtils.showToast("点赞评论");
//            if (onCommentLikeClickListener != null) {
//                onCommentLikeClickListener.onCommentLikeClick(position - 1);
//            }
//        });

        TextView topFlag = holder.getView(R.id.tv_top_flag);
        topFlag.setVisibility(!dynamicCommentBean.getPinned() ? View.GONE : View.VISIBLE);
        topFlag.setText(topFlag.getContext().getString(R.string.dynamic_top_flag));
        topFlag.setVisibility(View.GONE);
//        List<Link> links = setLiknks(holder, dynamicCommentBean, position);
//        if (!links.isEmpty()) {
//            ConvertUtils.stringLinkConvert(holder.getView(R.id.tv_content), links, false);
//        }
//        holder.getView(R.id.tv_content).setOnClickListener(v -> {
//            if (mOnCommentTextClickListener != null) {
//                mOnCommentTextClickListener.onCommentTextClick(position);
//            }
//        });
//        holder.getView(R.id.tv_content).setOnLongClickListener(v -> {
//            if (mOnCommentTextClickListener != null) {
//                mOnCommentTextClickListener.onCommentTextLongClick(position);
//            }
//            return true;
//        });
        ImageUtils.loadCircleUserHeadPic(dynamicCommentBean.getCommentUser(), holder.getView(R.id.iv_headpic));
        setUserInfoClick(holder.getView(R.id.tv_name), dynamicCommentBean.getCommentUser());
        setUserInfoClick(holder.getView(R.id.iv_headpic), dynamicCommentBean.getCommentUser());


        LinearLayout llCommentParent = holder.getView(R.id.ll_comment_parent);
        RelativeLayout rlCommentRoot = holder.getView(R.id.rl_comment_root);
        llCommentParent.removeAllViews();
//        dynamicCommentBean.reply
        if (dynamicCommentBean == null || dynamicCommentBean.getComment_children() == null || dynamicCommentBean.getComment_children().size() == 0) {
            rlCommentRoot.setVisibility(View.GONE);
        } else {
            rlCommentRoot.setVisibility(View.VISIBLE);
            for (int i = 0; i < dynamicCommentBean.getComment_children().size(); i++) {
                if (i >= 3) {
                    break;
                }
                DynamicCommentBean item = dynamicCommentBean.getComment_children().get(i);
                TextView replyCommentView = (TextView) LayoutInflater.from(holder.getmContext()).inflate(R.layout.item_reply_comment_layout, llCommentParent, false);
                String myName = item.commentUser.getName() + "";
                String replyName = item.replyUser.getName() + "";
                String replyContent = item.getComment_content();
                llCommentParent.addView(replyCommentView);
                //回复
                //回复
                String content = String.format("%s 回复 %s : %s", myName, replyName, replyContent);
                SpannableStringBuilder sb = new SpannableStringBuilder(content);

                UnderlineSpan colorSpan0 = new UnderlineSpan() {
                    @Override
                    public void updateDrawState(TextPaint ds) {
                        ds.setColor(holder.getmContext().getResources().getColor(R.color.white));//设置颜色
                        ds.setUnderlineText(false);//去掉下划线
                    }
                };
                UnderlineSpan colorSpan1 = new UnderlineSpan() {
                    @Override
                    public void updateDrawState(TextPaint ds) {
                        ds.setColor(holder.getmContext().getResources().getColor(R.color.white));//设置颜色
                        ds.setUnderlineText(false);//去掉下划线
                    }
                };

                UnderlineSpan colorSpan2 = new UnderlineSpan() {
                    @Override
                    public void updateDrawState(TextPaint ds) {
                        ds.setColor(holder.getmContext().getResources().getColor(R.color.color_EA3378));//设置颜色
                        ds.setUnderlineText(false);//去掉下划线
                    }
                };

                UnderlineSpan colorSpan3 = new UnderlineSpan() {
                    @Override
                    public void updateDrawState(TextPaint ds) {
                        ds.setColor(holder.getmContext().getResources().getColor(R.color.color_cccccc));//设置颜色
                        ds.setUnderlineText(false);//去掉下划线
                    }
                };
                sb.setSpan(colorSpan0, 0, (myName + "").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                sb.setSpan(colorSpan2, (myName + "").length(), (myName + "").length() + 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                sb.setSpan(colorSpan1, (myName + "").length() + 4, (myName + "").length() + 4 + (replyName + "").length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                sb.setSpan(colorSpan3, (myName + "").length() + 4 + (replyName + "").length(), content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                replyCommentView.setText(sb);
                replyCommentView.setMovementMethod(LinkMovementMethod.getInstance());
            }
            if (dynamicCommentBean.getComment_children().size() > 3) {
                View moreContentView = LayoutInflater.from(holder.getmContext()).inflate(R.layout.item_reply_comment_see_more, llCommentParent, false);
                TextView tvMoreInfoTip = moreContentView.findViewById(R.id.tv_more_info_tip);
                tvMoreInfoTip.setTextColor(holder.getmContext().getResources().getColor(R.color.color_EA3378));
                tvMoreInfoTip.setText("查看全部"+dynamicCommentBean.getComment_children().size() +"条回复");
//                tvMoreInfoTip.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_arrow_down, 0, 0, 0);
                llCommentParent.addView(moreContentView);
            }
        }
        //todo 评论父控件 现在没数据 先隐藏
//        rlCommentRoot.setVisibility(View.GONE);
    }

    private void setUserInfoClick(View v, final UserInfoBean userInfoBean) {
        RxView.clicks(v).subscribe(aVoid -> {
            if (mOnUserInfoClickListener != null) {
                mOnUserInfoClickListener.onUserInfoClick(userInfoBean);
            }
        });
    }

    protected String setShowText(DynamicCommentBean dynamicCommentBean, int position) {
        return handleName(dynamicCommentBean);
    }

    protected List<Link> setLiknks(ViewHolder holder, final DynamicCommentBean dynamicCommentBean, int position) {
        List<Link> links = new ArrayList<>();
        if (dynamicCommentBean.getReplyUser() != null && dynamicCommentBean.getReply_to_user_id() != 0 && dynamicCommentBean.getReplyUser().getName
                () != null) {
            Link replyNameLink = new Link(dynamicCommentBean.getReplyUser().getName())
                    .setTextColor(ContextCompat.getColor(holder.getConvertView().getContext(), R.color.important_for_content))
                    // optional, defaults to holo blue
                    .setTextColorOfHighlightedLink(ContextCompat.getColor(holder.getConvertView().getContext(), R.color.general_for_hint))
                    // optional, defaults to .15f
                    .setHighlightAlpha(.5f)
                    .setBold(false)
                    // optional, defaults to true
                    .setUnderlined(false)
                    .setOnLongClickListener((clickedText, linkMetadata) -> {
                        if (mOnUserInfoLongClickListener != null) {
                            mOnUserInfoLongClickListener.onUserInfoLongClick(dynamicCommentBean.getReplyUser());
                        }
                    })
                    .setOnClickListener((clickedText, linkMetadata) -> {
                        LogUtils.d("-----dy------setOnClickListener----------------");
                        // single clicked
                        if (mOnUserInfoClickListener != null) {
                            mOnUserInfoClickListener.onUserInfoClick(dynamicCommentBean.getReplyUser());
                        }
                    });
            links.add(replyNameLink);
        }
        Link link = new Link(Pattern.compile(MarkdownConfig.AT_FORMAT))
                .setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.important_for_theme))
                .setUnderlined(false)
                .setOnClickListener((clickedText, linkMetadata) -> {
                    if (mOnUserInfoClickListener != null) {
                        mOnUserInfoClickListener.onUserInfoClick(new UserInfoBean(RegexUtils.replaceAllAt(clickedText)));
                    }
                })
                .setTextColorOfHighlightedLink(ContextCompat.getColor(holder.itemView.getContext(), R.color.important_for_content));
        links.add(link);

        return links;
    }

    /**
     * 处理名字的颜色与点击
     *
     * @param dynamicCommentBean
     * @return
     */
    private String handleName(DynamicCommentBean dynamicCommentBean) {
        String content = "";
        if (dynamicCommentBean.getReply_to_user_id() != 0) { // 当没有回复者时，就是回复评论
            content += "回复 " + dynamicCommentBean.getReplyUser().getName() + ": " + dynamicCommentBean.getComment_content();
        } else {
            content = dynamicCommentBean.getComment_content();
        }
        return content;
    }

    public interface OnCommentResendListener {
        void reSendComment(DynamicCommentBean dynamicCommentBean);
    }
}
