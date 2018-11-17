package com.yjz.load.ui.activity;

import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.seabig.common.base.BaseActivity;
import com.seabig.common.base.rx.RxHttpResponseCompat;
import com.seabig.common.base.rx.subscribe.ProgressDialogSubscribe;
import com.seabig.common.http.RetrofitUtil;
import com.seabig.common.util.LogUtils;
import com.seabig.common.util.Md5Helper;
import com.yjz.load.R;
import com.yjz.load.api.ApiService;
import com.yjz.load.ui.widget.CaptchaUtil;

import java.util.Date;

/**
 * author： YJZ
 * date:  2018/10/31
 * des: 注册
 */

@Route (path = "/load/activity/register")
public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mCaptchaImg;
    private CheckBox mUseReadBox;
    private EditText mPwdEdt;
    private EditText mMobileEdt;
    private EditText mCodeEdt;
    private TextView mAgreementTv;

    @Override
    protected int onSettingUpContentViewResourceID()
    {
        return R.layout.load_activity_register;
    }

    @Override
    public int getStatusColor(int mStatusColor)
    {
        return R.color.black_alpha_20;
    }

    @Override
    protected void onSettingUpView()
    {
        mCaptchaImg = (ImageView) findViewById(R.id.captcha_img);
        mCaptchaImg.setOnClickListener(this);
        mMobileEdt = (EditText) findViewById(R.id.mobile_edt);
        mCodeEdt = (EditText) findViewById(R.id.code_edt);
        mPwdEdt = (EditText) findViewById(R.id.pwd_code);
        findViewById(R.id.confirm_register).setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
        mUseReadBox = (CheckBox) findViewById(R.id.checkbox);

        mAgreementTv = (TextView) findViewById(R.id.agreement);
    }

    @Override
    protected void onSettingUpData()
    {
        getCaptcha();
        String str = "同意<font color='#37b97d'>《产品使用许可协议》</font>";
        mAgreementTv.setText(Html.fromHtml(str));
    }

    @Override
    public void onClick(View v)
    {
        int id = v.getId();

        if (id == R.id.confirm_register)
        {
            String codeStr = mCodeEdt.getText().toString();
            String mobileStr = mMobileEdt.getText().toString();
            String pwdStr = mPwdEdt.getText().toString();

            if (TextUtils.isEmpty(codeStr) || TextUtils.isEmpty(mobileStr) || TextUtils.isEmpty(pwdStr))
            {
                showToast(getStringByResId(R.string.register_error));
                return;
            }

            // 图形验证码
            String realCode = CaptchaUtil.build().getCode();

            if (!codeStr.equals(realCode))
            {
                showToast(getStringByResId(R.string.code_err));
                return;
            }

            if (!mUseReadBox.isChecked())
            {
                showToast(getStringByResId(R.string.read_err));
                return;
            }

            RetrofitUtil.getApi(ApiService.class)
                    .getRegisterBean(mobileStr, Md5Helper.MD5(pwdStr))
                    .compose(RxHttpResponseCompat.<Long>compatResult())
                    .subscribe(new ProgressDialogSubscribe<Long>(this) {
                        @Override
                        public void onNext(Long baseBean)
                        {
                            showToast("注册成功");
                            finish();
                            LogUtils.e("baen = " + baseBean);
                        }
                    });
        } else if (id == R.id.captcha_img)
        {
            getCaptcha();
        } else if (id == R.id.back)
        {
            finish();
        }
    }

    private void getCaptcha()
    {
        CaptchaUtil.build()
                .backColor(0xffffff)
                .codeLength(4)
                .fontSize(60)
                .lineNumber(5)
                .size(200, 70)
                .type(CaptchaUtil.TYPE.NUMBER)
                .into(mCaptchaImg);
    }
}
