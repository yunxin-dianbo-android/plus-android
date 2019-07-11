package com.zhiyicx.thinksnsplus.modules.circle.detailv2.post.adapter;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.klinker.android.link_builder.Link;
import com.zhiyicx.common.config.MarkdownConfig;
import com.zhiyicx.common.utils.ConvertUtils;
import com.zhiyicx.common.utils.RegexUtils;
import com.zhiyicx.common.utils.TimeUtils;
import com.zhiyicx.common.utils.ToastUtils;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.data.beans.CirclePostCommentBean;
import com.zhiyicx.thinksnsplus.data.beans.UserInfoBean;
import com.zhiyicx.thinksnsplus.i.OnUserInfoClickListener;
import com.zhiyicx.thinksnsplus.i.OnUserInfoLongClickListener;
import com.zhiyicx.thinksnsplus.utils.ImageUtils;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @Author Jliuer
 * @Date 2017/03/30
 * @Email Jliuer@aliyun.com
 * @Description
 */
public class PostDetailCommentItem implements ItemViewDelegate<CirclePostCommentBean> {

    private OnCommentItemListener mOnCommentItemListener;

    private OnUserInfoClickListener mOnUserInfoClickListener;
    private OnUserInfoLongClickListener mOnUserInfoLongClickListener;

    public void setOnUserInfoClickListener(OnUserInfoClickListener onUserInfoClickListener) {
        mOnUserInfoClickListener = onUserInfoClickListener;
    }

    public void setOnUserInfoLongClickListener(OnUserInfoLongClickListener onUserInfoLongClickListener) {
        mOnUserInfoLongClickListener = onUserInfoLongClickListener;
    }

    public PostDetailCommentItem(OnCommentItemListener onCommentItemListener) {
        mOnCommentItemListener = onCommentItemListener;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_dynamic_detail_comment;
    }

    @Override
    public boolean isForViewType(CirclePostCommentBean item, int position) {
        return position == 0 || !TextUtils.isEmpty(item.getContent());
    }

    @Override
    public void convert(final ViewHolder holder, CirclePostCommentBean circlePostCommentBean,
                        CirclePostCommentBean lastT, final int position, int itemCounts) {

        ImageUtils.loadCircleUserHeadPic(circlePostCommentBean.getCommentUser(), holder.getView(R.id.iv_headpic));

        holder.setText(R.id.tv_name, circlePostCommentBean.getCommentUser().getName());
        holder.setText(R.id.tv_time, TimeUtils.getTimeFriendlyNormal(circlePostCommentBean
                .getCreated_at()));
        holder.setText(R.id.tv_content, setShowText(circlePostCommentBean, position));
        holder.setOnClickListener(R.id.tv_content, v -> {
            if (mOnCommentItemListener != null) {
                mOnCommentItemListener.onItemClick(v, holder, position);
            }
        });

        holder.setOnClickListener(R.id.iv_reply_comment, v -> {
            // TODO: 2019/6/12 回复
            ToastUtils.showToast("回复评论");
        });
        holder.setOnClickListener(R.id.iv_like_comment, v -> {
            ToastUtils.showToast("点赞评论");
        });
        holder.setOnLongClickListener(R.id.tv_content, v -> {
            if (mOnCommentItemListener != null) {
                mOnCommentItemListener.onItemLongClick(v, holder, position);
            }
            return true;
        });
        TextView topFlag = holder.getView(R.id.tv_top_flag);
        topFlag.setVisibility(!circlePostCommentBean.isPinned() ? View.GONE : View.VISIBLE);
        topFlag.setText(topFlag.getContext().getString(R.string.dynamic_top_flag));

        List<Link> links = setLiknks(holder, circlePostCommentBean, position);
        if (!links.isEmpty()) {
            ConvertUtils.stringLinkConvert(holder.getView(R.id.tv_content), links, false);
        }
        holder.itemView.setOnClickListener(v -> {
            if (mOnCommentItemListener != null) {
                mOnCommentItemListener.onItemClick(v, holder, position);
            }
        });
        holder.itemView.setOnLongClickListener(v -> {
            if (mOnCommentItemListener != null) {
                mOnCommentItemListener.onItemLongClick(v, holder, position);
            }
            return true;
        });


        setUserInfoClick(holder.getView(R.id.tv_name), circlePostCommentBean.getCommentUser());
        setUserInfoClick(holder.getView(R.id.iv_headpic), circlePostCommentBean.getCommentUser());

        LinearLayout llCommentParent = holder.getView(R.id.ll_comment_parent);
        llCommentParent.removeAllViews();
        List<CirclePostCommentBean> reply = circlePostCommentBean.getTestReply();

        if (false/*reply == null || reply.size() == 0*/) {
            llCommentParent.setVisibility(View.GONE);
        } else {
            llCommentParent.setVisibility(View.VISIBLE);
            for (int i = 0; i < 3/*reply.size()*/; i++) {

                TextView replyCommentView = (TextView) LayoutInflater.from(holder.getmContext()).inflate(R.layout.item_reply_comment_layout, llCommentParent, false);
                String myName = "张三";
                String replyName = "李四";
                String replyContent = "这只是一个测试的回复评论";
                llCommentParent.addView(replyCommentView);
                //回复
                String content = myName + " 回复 " + replyName + " : " + replyContent;
                SpannableStringBuilder sb = new SpannableStringBuilder(content);


//                sb.setSpan(new ClickableSpan() {
//                    @Override
//                    public void onClick(View widget) {
//                        int[] location = new int[2];
//                        shuoshuoCommentView.getLocationOnScreen(location);
//                        int yPosition = location[1] + shuoshuoCommentView.getMeasuredHeight();
//                        StarCircleCommentInputDialog.showDialog(mContext, shuoShuoData, yPosition, 1, commentInfo);
//                    }
//                }, (commentInfo.ReplyUserName + "").length() + 4 + (commentInfo.UserName + "").length() + 2, content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


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
            if (true/*reply.size()>3*/) {
                View moreContentView = LayoutInflater.from(holder.getmContext()).inflate(R.layout.item_reply_comment_see_more, llCommentParent, false);
                TextView tvMoreInfoTip = moreContentView.findViewById(R.id.tv_more_info_tip);
                tvMoreInfoTip.setTextColor(holder.getmContext().getResources().getColor(R.color.color_EA3378));
                tvMoreInfoTip.setText("查看全部55条回复");
//                tvMoreInfoTip.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_arrow_down, 0, 0, 0);
                llCommentParent.addView(moreContentView);
            }
        }
    }

    private void setUserInfoClick(View v, final UserInfoBean userInfoBean) {
        RxView.clicks(v).subscribe(aVoid -> {
            if (mOnCommentItemListener != null) {
                mOnCommentItemListener.onUserInfoClick(userInfoBean);
            }
        });
    }

    protected String setShowText(CirclePostCommentBean circlePostCommentBean, int position) {
        return handleName(circlePostCommentBean);
    }

    protected List<Link> setLiknks(ViewHolder holder, final CirclePostCommentBean circlePostCommentBean, int position) {
        List<Link> links = new ArrayList<>();
        if (circlePostCommentBean.getReplyUser() != null && circlePostCommentBean.getReply_to_user_id() != 0 && circlePostCommentBean.getReplyUser().getName() != null) {
            Link replyNameLink = new Link(circlePostCommentBean.getReplyUser().getName())
                    .setTextColor(ContextCompat.getColor(holder.getConvertView().getContext(), R.color.important_for_content))                  // optional, defaults to holo blue
                    .setTextColorOfHighlightedLink(ContextCompat.getColor(holder.getConvertView().getContext(), R.color.general_for_hint)) // optional, defaults to holo blue
                    .setHighlightAlpha(.5f)                                     // optional, defaults to .15f
                    .setUnderlined(false)                                       // optional, defaults to true
                    .setOnLongClickListener((clickedText, linkMetadata) -> {
                        if (mOnUserInfoLongClickListener != null) {
                            mOnUserInfoLongClickListener.onUserInfoLongClick(circlePostCommentBean.getReplyUser());
                        }
                    })
                    .setOnClickListener((clickedText, linkMetadata) -> {
                        // single clicked
                        if (mOnUserInfoClickListener != null) {
                            mOnUserInfoClickListener.onUserInfoClick(circlePostCommentBean.getReplyUser());
                        }
                    });
            links.add(replyNameLink);
        }
        Link link = new Link(Pattern.compile(MarkdownConfig.AT_FORMAT))
                .setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.important_for_theme))
                .setUnderlined(false)
                .setOnClickListener((clickedText, linkMetadata) -> {
                    if (mOnCommentItemListener != null) {
                        mOnCommentItemListener.onUserInfoClick(new UserInfoBean(RegexUtils.replaceAllAt(clickedText)));
                    }
                })
                .setTextColorOfHighlightedLink(ContextCompat.getColor(holder.itemView.getContext(), R.color.important_for_content));
        links.add(link);

        return links;
    }

    private String handleName(CirclePostCommentBean circlePostCommentBean) {
        String content = "";
        // 当没有回复者时，就是回复评论
        if (circlePostCommentBean.getReply_to_user_id() != 0) {
            content += " 回复 " + circlePostCommentBean.getReplyUser().getName() + ": " +
                    circlePostCommentBean.getContent();
        } else {
            content = circlePostCommentBean.getContent();
        }
        return content;
    }

    public void setOnCommentItemListener(OnCommentItemListener onCommentItemListener) {
        mOnCommentItemListener = onCommentItemListener;
    }

    public interface OnCommentItemListener {
        void onItemClick(View view, RecyclerView.ViewHolder holder, int position);

        void onItemLongClick(View view, RecyclerView.ViewHolder holder, int position);

        void onUserInfoClick(UserInfoBean userInfoBean);
    }
}
