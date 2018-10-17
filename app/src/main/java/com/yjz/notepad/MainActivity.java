package com.yjz.notepad;

import com.alibaba.android.arouter.launcher.ARouter;
import com.seabig.common.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected int onSettingUpContentViewResourceID() {
        return R.layout.activity_main;
    }

    @Override
    protected void onSettingUpView() {
        ARouter.getInstance().build("/load/activity/login").navigation();
    }
}
