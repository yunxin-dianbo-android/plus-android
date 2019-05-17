package com.zhiyicx.thinksnsplus.modules.circle.main.adapter;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.jakewharton.rxbinding.view.RxView;
import com.zhiyicx.baseproject.widget.button.CombinationButton;
import com.zhiyicx.common.base.BaseApplication;
import com.zhiyicx.common.utils.ConvertUtils;
import com.zhiyicx.common.utils.SkinUtils;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.data.beans.CircleInfo;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.concurrent.TimeUnit;

import static com.zhiyicx.common.config.ConstantConfig.JITTER_SPACING_TIME;

/**
 * @Author Jliuer
 * @Date 2017/11/14/13:41
 * @Email Jliuer@aliyun.com
 * @Description
 */
public class CircleTypeItem extends BaseCircleItem {

    public CircleTypeItem(CircleItemItemEvent circleItemItemEvent) {
        super(circleItemItemEvent);
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.view_circle_type;
    }

    @Override
    public boolean isForViewType(CircleInfo item, int position) {
        return item.getId() < 0;
    }

    @Override
    public void convert(ViewHolder holder, CircleInfo circleInfo, CircleInfo lastT, int position, int itemCounts) {
        CombinationButton button = holder.getView(R.id.tv_circle_type);
        View viewTopDivider = holder.getView(R.id.v_circle_type_divider_top);
        View viewBottomDivider = holder.getView(R.id.v_circle_type_divider_bottom);
        button.setLeftTextColor(SkinUtils.getColor(R.color.white));
        button.setLeftText(circleInfo.getName());
        button.setRightText(circleInfo.getSummary());
        viewBottomDivider.setVisibility(View.GONE);
        if (circleInfo.getId() == RECOMMENDCIRCLE) {
            viewBottomDivider.setVisibility(View.VISIBLE);
            button.setRightImage(0);
            viewTopDivider.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) button.getCombinedButtonRightTextView().getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.addRule(1, 1);
            params.setMargins(button.getContext().getResources().getDimensionPixelOffset(R.dimen.spacing_normal),
                    0, button.getContext().getResources().getDimensionPixelOffset(R.dimen.spacing_mid), 0);
            Drawable change = button.getContext().getResources().getDrawable(R.mipmap.ic_exchang_circle);
            button.getCombinedButtonRightTextView().setCompoundDrawablePadding(ConvertUtils.dp2px(button.getContext(), 4));
            button.getCombinedButtonRightTextView().setCompoundDrawablesWithIntrinsicBounds(change, null, null, null);
            button.setRightTextColor(button.getContext().getResources().getColor(R.color.white));
            button.setLeftTextColor(button.getContext().getResources().getColor(R.color.color_EA3378));
        } else if (circleInfo.getId() == MYJOINEDCIRCLE) {
            viewTopDivider.setVisibility(View.GONE);
            button.setLeftTextColor(button.getContext().getResources().getColor(R.color.white));
            viewBottomDivider.setVisibility(View.VISIBLE);
            button.setRightImage(TextUtils.isEmpty(circleInfo.getSummary()) ? 0 : R.mipmap.ic_arrow_right_white);
            button.getCombinedButtonRightTextView().setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
        button.setBackgroundColor(BaseApplication.getContext().getResources().getColor(R.color.color_38383A));
        RxView.clicks(holder.getConvertView())
                .throttleFirst(JITTER_SPACING_TIME, TimeUnit.SECONDS)
                .subscribe(aVoid -> {
                    if (mCircleItemItemEvent == null) {
                        return;
                    }
                    if (circleInfo.getId() == BaseCircleItem.MYJOINEDCIRCLE) {
                        mCircleItemItemEvent.toAllJoinedCircle(circleInfo);
                    } else if (circleInfo.getId() == BaseCircleItem.RECOMMENDCIRCLE) {
                        mCircleItemItemEvent.changeRecommend();
                    }
                });
    }
}
