package com.zhiyicx.thinksnsplus.modules.circle.main;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zhiyicx.baseproject.widget.button.CombinationButton;
import com.zhiyicx.common.utils.SkinUtils;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.data.beans.RealAdvertListBean;
import com.zhiyicx.thinksnsplus.modules.circle.all_circle.container.AllCircleContainerActivity;
import com.zhiyicx.thinksnsplus.modules.dynamic.detail.DynamicDetailAdvertHeader;
import com.zhiyicx.thinksnsplus.modules.settings.aboutus.CustomWEBActivity;

import java.util.List;
import java.util.Locale;

/**
 * @author Jliuer
 * @Date 2017/11/14/10:33
 * @Email Jliuer@aliyun.com
 * @Description
 */
public class CircleMainHeader {
    private DynamicDetailAdvertHeader mDynamicDetailAdvertHeader;
    private CombinationButton mCircleCount;
    private View mCircleMainHeader;
    private View mAdvertTag;

    public View getCircleMainHeader() {
        return mCircleMainHeader;
    }

    public CircleMainHeader(Context context, List<RealAdvertListBean> adverts, int count) {
        String circleCount = String.format(Locale.getDefault(), context.getString(R.string.group_count), count);
//        int lengh = (count + "").length();
//        SpannableStringBuilder countSpan = new SpannableStringBuilder(circleCount);
//        countSpan.setSpan(new RelativeSizeSpan(1.66f), 0, lengh, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//        countSpan.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.important_for_note)), 0, lengh, Spanned
//                .SPAN_INCLUSIVE_INCLUSIVE);
        mCircleMainHeader = LayoutInflater.from(context).inflate(R.layout.circle_main_header, null);

        LinearLayout advertLinearLayout = (LinearLayout) mCircleMainHeader.findViewById(R.id.ll_advert);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) advertLinearLayout.getLayoutParams();
        params.setMargins(0, 0, 0, 0);
        mCircleCount = (CombinationButton) mCircleMainHeader.findViewById(R.id.tv_circle_count);
        mCircleCount.setLeftImageResource(R.mipmap.ic_join_circle_left);
        mCircleCount.setRightImage(R.mipmap.ic_arrow_right_white);
        mAdvertTag = mCircleMainHeader.findViewById(R.id.ll_advert_tag);
        mCircleCount.setBackgroundResource(R.drawable.common_statubar_bg);

        mAdvertTag.setVisibility(View.GONE);
        mCircleCount.setLeftTextSize(13f);
        mCircleCount.setLeftTextColor(SkinUtils.getColor(R.color.white));
        mCircleCount.setLeftText(circleCount);
        mCircleCount.setOnClickListener(v -> context.startActivity(new Intent(context, AllCircleContainerActivity.class)));
        initAdvert(context, adverts);
    }

    private void initAdvert(Context context, List<RealAdvertListBean> adverts) {

        if (!com.zhiyicx.common.BuildConfig.USE_ADVERT || adverts == null || adverts.isEmpty()) {
            mCircleMainHeader.findViewById(R.id.ll_advert).setVisibility(View.GONE);
            return;
        }
        mDynamicDetailAdvertHeader = new DynamicDetailAdvertHeader(context, mCircleMainHeader
                .findViewById(R.id.ll_advert));
        mDynamicDetailAdvertHeader.setTitle("广告");
        mDynamicDetailAdvertHeader.setAdverts(adverts);
        mDynamicDetailAdvertHeader.setOnItemClickListener((v, position1, url) ->
                toAdvert(context, adverts.get(position1).getAdvertFormat().getImage().getLink(), adverts.get(position1).getTitle())
        );
    }

    public void updateCircleCount(int count) {
        Context context = mCircleCount.getContext();
        String circleCount = String.format(Locale.getDefault(), context.getString(R.string.group_count), count);
//        int lengh = (count + "").length();
//        SpannableStringBuilder countSpan = new SpannableStringBuilder(circleCount);
//        countSpan.setSpan(new RelativeSizeSpan(1.66f), 0, lengh, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//        countSpan.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.important_for_note)), 0, lengh, Spanned
//                .SPAN_INCLUSIVE_INCLUSIVE);
        mCircleCount.setLeftText(circleCount);
    }

    public DynamicDetailAdvertHeader getAdvertHeader() {
        return mDynamicDetailAdvertHeader;
    }

    private void toAdvert(Context context, String link, String title) {
        CustomWEBActivity.startToWEBActivity(context, link, title);
    }
}
