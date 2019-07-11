package com.zhiyicx.thinksnsplus.modules.shortvideo.helper;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.zhiyicx.thinksnsplus.R;
import com.zhiyicx.thinksnsplus.modules.login.LoginActivity;

import cn.jzvd.JZUserActionStandard;
import cn.jzvd.JZUtils;
import skin.support.widget.SkinCompatImageView;

public class ZhiyiVideoView4VideoDetail extends ZhiyiVideoView {

    private boolean isTourist = false;

    public void setTourist(boolean isTourist) {
        this.isTourist = isTourist;
    }

    public void setiVideoPlayStatusChangedLisnter(IVideoPlayStatusChangedLisnter iVideoPlayStatusChangedLisnter) {
        this.iVideoPlayStatusChangedLisnter = iVideoPlayStatusChangedLisnter;
    }

    private IVideoPlayStatusChangedLisnter iVideoPlayStatusChangedLisnter;

    public ZhiyiVideoView4VideoDetail(Context context) {
        super(context);
    }

    public ZhiyiVideoView4VideoDetail(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onStatePause() {
        super.onStatePause();
        if (iVideoPlayStatusChangedLisnter != null) {
            iVideoPlayStatusChangedLisnter.onStatePause();
        }
    }

    @Override
    public void onAutoCompletion() {
        super.onAutoCompletion();
        if (iVideoPlayStatusChangedLisnter != null) {
            iVideoPlayStatusChangedLisnter.onAutoCompletion();
        }
    }

    //    onPau
    @Override
    public void onStatePrepared() {
        super.onStatePrepared();
        if (iVideoPlayStatusChangedLisnter != null) {
            iVideoPlayStatusChangedLisnter.onStatePrepared();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.thumb || v instanceof SkinCompatImageView) {
            if (isTourist) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                getContext().startActivity(intent);
                return;
            }
            if (dataSourceObjects == null || JZUtils.getCurrentFromDataSource(dataSourceObjects, currentUrlMapIndex) == null) {
                Toast.makeText(getContext(), getResources().getString(R.string.no_url), Toast.LENGTH_SHORT).show();
                return;
            }
            if (currentState == CURRENT_STATE_NORMAL) {
                if (!JZUtils.getCurrentFromDataSource(dataSourceObjects, currentUrlMapIndex).toString().startsWith("file") &&
                        !JZUtils.getCurrentFromDataSource(dataSourceObjects, currentUrlMapIndex).toString().startsWith("/") &&
                        !JZUtils.isWifiConnected(getContext()) && !WIFI_TIP_DIALOG_SHOWED) {
                    showWifiDialog();
                    return;
                }
                onEvent(JZUserActionStandard.ON_CLICK_START_THUMB);
                startVideo();
            } else if (currentState == CURRENT_STATE_AUTO_COMPLETE) {
                onClickUiToggle();
            }
        }
        super.onClick(v);
    }

    @Override
    public void onPrepared() {
        super.onPrepared();
        if (iVideoPlayStatusChangedLisnter != null) {
            iVideoPlayStatusChangedLisnter.onPrepared();
        }
    }

    public long getProgerss() {
        try {
            return progressBar.getProgress() * getDuration() / 100;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0l;
    }
//    on

    public interface IVideoPlayStatusChangedLisnter {
        void onStatePause();

        void onAutoCompletion();


        void onStatePrepared();

        void onPrepared();
    }
}
