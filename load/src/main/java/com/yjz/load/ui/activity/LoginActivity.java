package com.yjz.load.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.seabig.common.base.BaseActivity;
import com.seabig.common.datamgr.ARoutPath;
import com.seabig.common.datamgr.AppConstant;
import com.seabig.common.util.SPUtils;
import com.yjz.load.R;
import com.yjz.load.presenter.LoginPresenter;
import com.yjz.load.presenter.contract.LoginContract;

/**
 * author： YJZ
 * date:  2018/10/17
 * des: 登录
 */

@Route(path = ARoutPath.LOAD_LOGIN_ACTIVITY)
public class LoginActivity extends BaseActivity implements LoginContract.View, View.OnClickListener {

    private EditText mMobileEdt;
    private EditText mPwdEdt;

    @Override
    protected int onSettingUpContentViewResourceID() {
        return R.layout.load_activity_loading;
    }

    @Override
    protected void onSettingUpView() {
        findViewById(R.id.forget_pwd).setOnClickListener(this);
        findViewById(R.id.register_now).setOnClickListener(this);
        findViewById(R.id.login).setOnClickListener(this);
        mMobileEdt = (EditText) findViewById(R.id.mobile_edt);
        mPwdEdt = (EditText) findViewById(R.id.pwd_edt);
    }

    @Override
    public void loginSuccess(Long userID) {
        SPUtils.put(this, AppConstant.USER_ID, userID);
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.forget_pwd) {
            showToast("忘记密码");
        } else if (i == R.id.register_now) {
            startActivity(new Intent(this, RegisterActivity.class));
        } else if (i == R.id.login) {
            String mobileStr = mMobileEdt.getText().toString();
            String pwdStr = mPwdEdt.getText().toString();
            if (TextUtils.isEmpty(mobileStr) || TextUtils.isEmpty(pwdStr)) {
                showToast("信息不能为空");
                return;
            }
            LoginPresenter loginPresenter = new LoginPresenter(this,this);
            loginPresenter.login(mobileStr, pwdStr);
        }
    }
}
