package com.zhiyicx.thinksnsplus.service.backgroundtask;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.zhiyicx.common.utils.log.LogUtils;
import com.zhiyicx.thinksnsplus.data.beans.BackgroundRequestTaskBean;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import static com.zhiyicx.thinksnsplus.config.EventBusTagConfig.EVENT_BACKGROUND_TASK;
import static com.zhiyicx.thinksnsplus.config.EventBusTagConfig.EVENT_BACKGROUND_TASK_START_SUCCESS;
import static com.zhiyicx.thinksnsplus.config.EventBusTagConfig.EVENT_START_BACKGROUND_TASK;
import static com.zhiyicx.thinksnsplus.config.EventBusTagConfig.EVENT_STOP_BACKGROUND_TASK;

/**
 * @Describe 后台任务处理服务
 * @Author Jungle68
 * @Date 2017/1/22
 * @Contact master.jungle68@gmail.com
 */

public class BackgroundTaskHandleService extends Service {
    private static final String TAG = BackgroundTaskHandleService.class.getSimpleName();
    private static final int SERVICE_ID = 1994;
    private BackgroundTaskHandler mBackgroundTaskHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 这个id不要和应用内的地方一样
            startForeground(SERVICE_ID, new Notification());
        }
        EventBus.getDefault().register(this);
        init();
        LogUtils.d(TAG, "##BackgroundTaskHandleService## 后台服务已启动");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.d(TAG, "onDestroy");
        BackgroundTaskManager.getInstance(getApplicationContext()).
                updateBackGroundServiceState(BackgroundTaskManager.BackGroundServiceState.STOP);
        mBackgroundTaskHandler.stopTask();
        EventBus.getDefault().unregister(this);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        EventBus.getDefault().post(true, EVENT_BACKGROUND_TASK_START_SUCCESS);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Subscriber(tag = EVENT_BACKGROUND_TASK, mode = ThreadMode.POST)
    public boolean addBackgroundRequestTask(BackgroundRequestTaskBean backgroundRequestTaskBean) {
        if (mBackgroundTaskHandler == null) {
            init();
        }
        return mBackgroundTaskHandler.addBackgroundRequestTask(backgroundRequestTaskBean);
    }

    @Subscriber(tag = EVENT_STOP_BACKGROUND_TASK, mode = ThreadMode.POST)
    public void stopBackgroundRequestTask() {
        if (mBackgroundTaskHandler == null) {
            return;
        }
        mBackgroundTaskHandler.stopTask();
    }

    @Subscriber(tag = EVENT_START_BACKGROUND_TASK, mode = ThreadMode.POST)
    public void startBackgroundRequestTask() {
        init();
    }

    private void init() {
        mBackgroundTaskHandler = new BackgroundTaskHandler();
    }

}
