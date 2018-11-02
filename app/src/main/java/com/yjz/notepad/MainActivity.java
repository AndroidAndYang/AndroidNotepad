package com.yjz.notepad;

import android.os.Handler;

import com.alibaba.android.arouter.launcher.ARouter;
import com.seabig.common.base.BaseActivity;


public class MainActivity extends BaseActivity {

    @Override
    protected boolean onSettingFullScreen() {
        return true;
    }

    @Override
    protected int onSettingUpContentViewResourceID() {
        return R.layout.activity_main;
    }

    @Override
    protected void onSettingUpView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ARouter.getInstance().build("/load/activity/register").navigation();
                finish();
            }
        }, 1000);
    }
}
