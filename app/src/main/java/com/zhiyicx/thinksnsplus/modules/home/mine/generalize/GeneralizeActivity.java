package com.zhiyicx.thinksnsplus.modules.home.mine.generalize;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.zhiyicx.baseproject.base.TSActivity;

public class GeneralizeActivity extends TSActivity {

    public static void startGeneralizeActivity(Context context) {
        Intent intent = new Intent(context, GeneralizeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected Fragment getFragment() {
        return GeneralizeFragment.newInstance();
    }

    @Override
    protected void componentInject() {

    }
}
