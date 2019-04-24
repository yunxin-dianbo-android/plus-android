package com.zhiyicx.thinksnsplus.service.backgroundtask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.zhiyicx.thinksnsplus.data.beans.BackgroundRequestTaskBean;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;

import static com.zhiyicx.thinksnsplus.config.EventBusTagConfig.EVENT_BACKGROUND_TASK;
import static com.zhiyicx.thinksnsplus.config.EventBusTagConfig.EVENT_BACKGROUND_TASK_START_SUCCESS;
import static com.zhiyicx.thinksnsplus.config.EventBusTagConfig.EVENT_STOP_BACKGROUND_TASK;

/**
 * @Describe 任务管理器，使用后台任务只需使用此类即可
 * @Author Jungle68
 * @Date 2017/1/23
 * @Contact master.jungle68@gmail.com
 */

public class BackgroundTaskManager {

    /**
     * 后台任务状态
     */
    enum BackGroundServiceState {
        /**
         * 开启成功
         */
        START,
        /**
         * 已经停止
         */
        STOP,
        /**
         * 正在开启
         */
        STARTING
    }

    /**
     * 如果10s内没有成功，那么就算开启超时了
     */
    private static final int BACKGROUND_START_TIME_OUT = 10_000;

    @SuppressLint("StaticFieldLeak")
    private static volatile BackgroundTaskManager sBackgroundTaskManager; // context 必须使用 application 否者会造成内存泄漏
    private Context mContext;
    private BackGroundServiceState mIsServiceStart = BackGroundServiceState.STOP;// Service 是否开启

    /**
     * 后台任务启动的开始时间
     */
    private long mStartServiceTime;

    /**
     * 用户缓存未启动后台任务时的内容
     */
    private List<BackgroundRequestTaskBean> mBackgroundRequestTaskBeans = new ArrayList<>();

    private BackgroundTaskManager(Context context) {
        this.mContext = context.getApplicationContext();
        EventBus.getDefault().register(this);
    }

    public static BackgroundTaskManager getInstance(Context context) {

        if (sBackgroundTaskManager == null) {
            synchronized (BackgroundTaskManager.class) {
                if (sBackgroundTaskManager == null) {
                    sBackgroundTaskManager = new BackgroundTaskManager(context);
                }
            }
        }
        return sBackgroundTaskManager;
    }

    /**
     * 加入任务队列
     *
     * @param backgroundRequestTaskBean 任务
     */
    public void addBackgroundRequestTask(final BackgroundRequestTaskBean backgroundRequestTaskBean) {

        switch (mIsServiceStart) {

            case START:
                EventBus.getDefault().post(backgroundRequestTaskBean, EVENT_BACKGROUND_TASK);

                break;
            case STARTING:
                mBackgroundRequestTaskBeans.add(backgroundRequestTaskBean);
                if (System.currentTimeMillis() - mStartServiceTime > BACKGROUND_START_TIME_OUT) {
                    startBackgroundTask();
                }
                break;
            case STOP:
                mBackgroundRequestTaskBeans.add(backgroundRequestTaskBean);
                startBackgroundTask();
                break;
            default:

        }


    }

    /**
     * 开启后台任务，主要处理缓存任务
     */
    public void startBackgroundTask() {
        if (mIsServiceStart != BackGroundServiceState.START) {
            mIsServiceStart = BackGroundServiceState.STARTING;
            mStartServiceTime = System.currentTimeMillis();
            // Android 8.0 适配
            // Android 8.0 还对特定函数做出了以下变更：
            //
            //（1）如果针对 Android 8.0 的应用尝试在不允许其创建后台服务的情况下使用 startService() 函数，
            //     则该函数将引发一个 IllegalStateException。新的 Context.startForegroundService() 函数将启动一个前台服务。
            //
            //（2）即使应用在后台运行，系统也允许其调用 Context.startForegroundService()。不过，
            //    应用必须在创建服务后的五秒内调用该服务的 startForeground() 函数。
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mContext.startForegroundService(new Intent(mContext, BackgroundTaskHandleService.class));
            } else {
                mContext.startService(new Intent(mContext, BackgroundTaskHandleService.class));
            }
        }
    }

    /**
     * 关闭后台任务
     */
    public void closeBackgroundTask() {
        if (mIsServiceStart == BackGroundServiceState.START && mContext.stopService(new Intent(mContext, BackgroundTaskHandleService.class))) {
            mIsServiceStart = BackGroundServiceState.STOP;
        }
    }

    public void updateBackGroundServiceState(BackGroundServiceState state){
        mIsServiceStart = state;
    }


    /**
     * 启动后台任务服务承成功回调
     */
    @Subscriber(tag = EVENT_BACKGROUND_TASK_START_SUCCESS, mode = ThreadMode.POST)
    public void receivedBackgroundRequestTaskSuccess(boolean isServiceStartSuccess) {
        mIsServiceStart = BackGroundServiceState.START;
        List<BackgroundRequestTaskBean> tmp = new ArrayList<>(mBackgroundRequestTaskBeans);
        mBackgroundRequestTaskBeans.clear();
        for (BackgroundRequestTaskBean backgroundRequestTaskBean : tmp) {
            EventBus.getDefault().post(backgroundRequestTaskBean, EVENT_BACKGROUND_TASK);
        }

    }
}
