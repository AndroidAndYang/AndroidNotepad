package com.yjz.load.ui;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.seabig.common.base.ProgressBaseActivity;
import com.seabig.common.util.ToastUtils;
import com.yjz.load.R;
import com.yjz.load.presenter.LoginPresenter;
import com.yjz.load.presenter.contract.LoginContract;

/**
 * author： YJZ
 * date:  2018/10/17
 * des:
 */

@Route(path = "/load/activity/login")
public class LoginActivity extends ProgressBaseActivity implements LoginContract.View {

    @Override
    protected int onSettingUpContentViewResourceID() {
        return R.layout.load_activity_loading;
    }

    @Override
    protected void onSettingUpView() {
        LoginPresenter loginPresenter = new LoginPresenter(this);
        // loginPresenter.getList();
        loginPresenter.registerUser();
    }

    @Override
    public void register() {
        ToastUtils.getInstance().showToast(this, "注册成功");
    }
}
