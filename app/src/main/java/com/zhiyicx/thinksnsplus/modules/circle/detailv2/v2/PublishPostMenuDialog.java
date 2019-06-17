package com.zhiyicx.thinksnsplus.modules.circle.detailv2.v2;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.zhiyicx.thinksnsplus.R;

public class PublishPostMenuDialog extends Dialog implements View.OnClickListener {

    private OnPublishListener onDetachFromWindowListener;

    public PublishPostMenuDialog(Context context) {
        super(context, R.style.MyDialog);
//        super(context, R.style.MyDialog, R.layout.dialog_publish_post_menu_layout);
    }

    public void showDialog(OnPublishListener onDetachFromWindowListener) {
        this.onDetachFromWindowListener = onDetachFromWindowListener;
        show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBeforView();
        initView();

    }

    protected void initDataBeforView() {
        Window win = getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);//间距为0
        win.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.dimAmount = 0;
        //不可获得焦点
//        win.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//        win.addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        win.setAttributes(lp);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (onDetachFromWindowListener != null){
            onDetachFromWindowListener.ondetachFromWindow();
        }

    }

    protected void initView() {
        setContentView(R.layout.dialog_publish_post_menu_layout);
        findViewById(R.id.rl_content).setOnClickListener(this);
        findViewById(R.id.iv_upload_pic_post).setOnClickListener(this);
        findViewById(R.id.iv_upload_video_post).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_content:
                dismiss();
                break;
            case R.id.iv_upload_pic_post:
                onDetachFromWindowListener.onUploadPicClick();
                dismiss();
                break;
            case R.id.iv_upload_video_post:
                onDetachFromWindowListener.onUploadVideoClick();
                dismiss();
                break;
        }
    }

    public interface OnPublishListener {
        void ondetachFromWindow();
        void onUploadPicClick();
        void onUploadVideoClick();
    }
}
