package com.zhiyicx.thinksnsplus.modules.home;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zhiyi.emoji.ViewUtils;
import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.data.beans.DynamicDetailBeanV2;

/**
 * 直播间顶部的一些控件的详情显示
 */

public class EditDeletePopUpWindow extends PopupWindow implements View.OnClickListener {
    private Context mContext;
    public View mView;
    private TextView tvEdite;
    private TextView tvDelete;
    DynamicDetailBeanV2 dynamicBean;
    EditDeleteClickCallBack editDeleteClickCallBack;

    public EditDeletePopUpWindow(Context context) {
        super(context);
        mContext = context;
        initView();
        initListener();
    }


    private void initListener() {
        tvEdite.setOnClickListener(this);
        tvDelete.setOnClickListener(this);
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.pop_edit_delete_layout, null);
        this.setContentView(mView);
        this.setWidth(ViewUtils.dip2px(mContext, 60));
        this.setHeight(ViewUtils.dip2px(mContext, 90));
        this.setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(mContext.getResources().getColor(android.R.color.transparent)));
        //获取自身的长宽高
        mView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        tvEdite = (TextView) mView.findViewById(R.id.tv_edit);
        tvDelete = (TextView) mView.findViewById(R.id.tv_delete);
    }

    public View getContentView() {
        return mView;
    }


    public void bindData(DynamicDetailBeanV2 dynamicBean) {
        this.dynamicBean = dynamicBean;
    }

    /**
     * 设置显示在v上方（以v的中心位置为开始位置）
     *
     * @param v
     */
    public void showLeft(View v, EditDeleteClickCallBack editDeleteClickCallBack) {
        this.editDeleteClickCallBack = editDeleteClickCallBack;
        //获取需要在其上方显示的控件的位置信息
        int[] location = new int[2];
//        v.getLocationInWindow(location);
        v.getLocationOnScreen(location);
        //在控件上方显示
//        if (isAlreadyLike) {
//            tvLike.setText("已赞");
//        } else {
//            tvLike.setText("点赞");
//        }
        showAtLocation(v, Gravity.TOP | Gravity.LEFT, location[0] - ViewUtils.dip2px(mContext, 48), location[1] + ViewUtils.dip2px(mContext, 22));//- ScreenUtil.getCurrentScreenHeight1(mContext) / 2
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_edit:
                if (editDeleteClickCallBack != null) {
                    editDeleteClickCallBack.onEditeClick(dynamicBean);
                }
                dismiss();
                break;
            case R.id.tv_delete:
                if (editDeleteClickCallBack != null) {
                    editDeleteClickCallBack.onDeleteClick(dynamicBean);
                }
                dismiss();
                break;
        }
    }

    public interface EditDeleteClickCallBack {
        void onEditeClick(DynamicDetailBeanV2 dynamicBean);

        void onDeleteClick(DynamicDetailBeanV2 dynamicBean);
    }
}
