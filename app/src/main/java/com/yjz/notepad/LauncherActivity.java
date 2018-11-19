package com.yjz.notepad;

import android.os.Handler;

import com.alibaba.android.arouter.launcher.ARouter;
import com.seabig.common.base.BaseActivity;
import com.seabig.common.datamgr.AppConstant;
import com.seabig.common.util.SPUtils;

public class LauncherActivity extends BaseActivity {

    @Override
    protected boolean onSettingFullScreen() {
        return true;
    }

    @Override
    protected int onSettingUpContentViewResourceID() {
        return R.layout.app_activity_launcher;
    }

    @Override
    protected void onSettingUpView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Long userId = (Long) SPUtils.get(LauncherActivity.this, AppConstant.USER_ID, 0L);
                String path;
                if (userId != null && userId > 0) {
                    path = "/load/activity/main";
                } else {
                    path = "/load/activity/login";
                }
                ARouter.getInstance()
                        .build(path)
                        .navigation();

                finish();
            }
        }, 1000);
    }
}
