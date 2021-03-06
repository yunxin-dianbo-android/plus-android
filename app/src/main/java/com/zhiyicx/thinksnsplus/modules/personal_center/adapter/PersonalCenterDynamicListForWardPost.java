package com.zhiyicx.thinksnsplus.modules.personal_center.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.view.RxView;
import com.zhiyicx.baseproject.em.manager.util.TSEMConstants;
import com.zhiyicx.baseproject.widget.textview.SpanTextViewWithEllipsize;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.data.beans.DynamicDetailBeanV2;
import com.zhiyicx.thinksnsplus.data.beans.Letter;
import com.zhiyicx.thinksnsplus.modules.circle.detailv2.post.CirclePostDetailActivity;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.concurrent.TimeUnit;

import static com.zhiyicx.common.config.ConstantConfig.JITTER_SPACING_TIME;
import static com.zhiyicx.thinksnsplus.data.beans.DynamicListAdvert.DEFAULT_ADVERT_FROM_TAG;

/**
 * @author LiuChao
 * @describe
 * @date 2017/3/10
 * @contact email:450127106@qq.com
 */

public class PersonalCenterDynamicListForWardPost extends PersonalCenterDynamicListBaseItem {
    public PersonalCenterDynamicListForWardPost(Context context) {
        super(context);
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_personal_center_dynamic_list_forward_post;
    }

    @Override
    public boolean isForViewType(DynamicDetailBeanV2 item, int position) {
        Letter letter = item.getMLetter();
        return item.getFeed_mark() != null &&
                item.getFeed_from() != DEFAULT_ADVERT_FROM_TAG &&
                (item.getImages() == null || item.getImages().isEmpty()) &&
                item.getVideo() == null &&
                letter != null &&
                TSEMConstants.BUNDLE_CHAT_MESSAGE_FORWARD_TYPE_POST.equals(letter.getType());
    }

    @Override
    public void convert(ViewHolder holder, final DynamicDetailBeanV2 dynamicBean, DynamicDetailBeanV2 lastT, int position, int itemCounts) {
        super.convert(holder, dynamicBean, lastT, position, itemCounts);
        holder.setText(R.id.tv_forward_name, dynamicBean.getMLetter().getName());
        SpanTextViewWithEllipsize forwardContentView = holder.getView(R.id.tv_forward_content);
        forwardContentView.setText(dynamicBean.getMLetter().getContent());
        forwardContentView.post(() -> forwardContentView.updateForRecyclerView(forwardContentView.getText(),
                forwardContentView.getWidth()));

        forwardContentView.setVisibility(TextUtils.isEmpty(dynamicBean.getMLetter().getContent()) ? View.GONE : View.VISIBLE);
        ImageView imageView = holder.getImageViwe(R.id.iv_forward_image);
        String path = dynamicBean.getMLetter().getImage();
        imageView.setVisibility(TextUtils.isEmpty(path) ? View.GONE : View.VISIBLE);

        boolean isPre = TSEMConstants.BUNDLE_CHAT_MESSAGE_LETTER_TYPE_POST_CIRCLE_TYPE.equals(dynamicBean.getMLetter().getCircle_type());
        RxView.clicks(holder.getView(R.id.ll_forward_container))
                .throttleFirst(JITTER_SPACING_TIME, TimeUnit.SECONDS)
                .subscribe(aVoid -> CirclePostDetailActivity.startCirclePostDetailActivity(mContext, Long.parseLong(dynamicBean.getMLetter().getCircle_id()),
                        Long.parseLong(dynamicBean.getMLetter().getId()), isPre));

        RxView.clicks(holder.getView(R.id.tv_forward_name))
                .throttleFirst(JITTER_SPACING_TIME, TimeUnit.SECONDS)
                .subscribe(aVoid -> CirclePostDetailActivity.startCirclePostDetailActivity(mContext, Long.parseLong(dynamicBean.getMLetter().getCircle_id()),
                        Long.parseLong(dynamicBean.getMLetter().getId()), isPre));
        RxView.clicks(holder.getView(R.id.tv_forward_content))
                .throttleFirst(JITTER_SPACING_TIME, TimeUnit.SECONDS)
                .subscribe(aVoid -> CirclePostDetailActivity.startCirclePostDetailActivity(mContext, Long.parseLong(dynamicBean.getMLetter().getCircle_id()),
                        Long.parseLong(dynamicBean.getMLetter().getId()), isPre));

        if (TextUtils.isEmpty(path)) {
            return;
        }
        Glide.with(mContext)
                .load(path)
                .placeholder(R.drawable.shape_default_image)
                .error(R.drawable.shape_default_image)
                .into(imageView);
    }
}
