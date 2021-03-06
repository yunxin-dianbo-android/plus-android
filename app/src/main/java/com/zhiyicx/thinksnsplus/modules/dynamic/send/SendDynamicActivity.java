package com.zhiyicx.thinksnsplus.modules.dynamic.send;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.zhiyicx.baseproject.base.TSActivity;
import com.zhiyicx.thinksnsplus.base.AppApplication;
import com.zhiyicx.thinksnsplus.data.beans.Letter;
import com.zhiyicx.thinksnsplus.data.beans.SendDynamicDataBean;
import com.zhiyicx.thinksnsplus.data.beans.TopicListBean;
import com.zhiyicx.thinksnsplus.modules.topic.search.SearchTopicFragment;


public class SendDynamicActivity extends TSActivity<SendDynamicPresenter, SendDynamicFragment> {
    public static final String SEND_DYNAMIC_DATA = "send_dynamic_data";// 发送动态需要的一些数据，从上一个页面发送过来
    public static final String SEND_LETTER_DATA = "send_letter_data";// 转发发送动态需要的一些数据，从上一个页面发送过来

    @Override
    protected SendDynamicFragment getFragment() {
        return SendDynamicFragment.initFragment(getIntent().getExtras());
    }

    @Override
    protected void componentInject() {
        DaggerSendDynamicPresenterComponent.builder()
                .appComponent(AppApplication.AppComponentHolder.getAppComponent())
                .sendDynamicPresenterModule(new SendDynamicPresenterModule(mContanierFragment))
                .build().inject(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mContanierFragment.onActivityResult(RESULT_OK, RESULT_OK, intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mContanierFragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        mContanierFragment.onBackPressed();
    }

    public static void startToSendDynamicActivity(Context context, SendDynamicDataBean sendDynamicDataBean) {
        // 跳转到发送动态页面
        Intent it = new Intent(context, SendDynamicActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(SendDynamicActivity.SEND_DYNAMIC_DATA, sendDynamicDataBean);
        it.putExtras(bundle);
        context.startActivity(it);
    }

    public static void startToSendDynamicActivity(Context context, SendDynamicDataBean sendDynamicDataBean,
                                                  Letter letter) {
        // 跳转到发送动态页面
        Intent it = new Intent(context, SendDynamicActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(SendDynamicActivity.SEND_DYNAMIC_DATA, sendDynamicDataBean);
        bundle.putParcelable(SendDynamicActivity.SEND_LETTER_DATA, letter);
        it.putExtras(bundle);
        context.startActivity(it);
    }

    public static void startToSendDynamicActivity(Context context, SendDynamicDataBean sendDynamicDataBean,
                                                  TopicListBean topicListBean) {
        // 跳转到发送动态页面
        Intent it = new Intent(context, SendDynamicActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(SearchTopicFragment.TOPIC, topicListBean);
        bundle.putParcelable(SendDynamicActivity.SEND_DYNAMIC_DATA, sendDynamicDataBean);
        it.putExtras(bundle);
        context.startActivity(it);
    }

}
