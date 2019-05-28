package com.zhiyicx.thinksnsplus.modules.channel;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.zhiyicx.baseproject.base.TSActivity;


/**
 * @Describe
 * @Author Jungle68
 * @Date 2016/12/22
 * @Contact master.jungle68@gmail.com
 */

public class VideoChannelActivity extends TSActivity {

    @Override
    protected boolean useEventBus() {
        return true;
    }




    @Override
    protected void componentInject() {
    }

    @Override
    protected Fragment getFragment() {
        return VideoChannelFragment2.newInstance(getIntent().getExtras());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

}
