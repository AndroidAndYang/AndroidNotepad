package com.yjz.load.presenter;

import com.seabig.common.base.BasePresenter;
import com.seabig.common.http.RetrofitUtil;
import com.seabig.common.util.LogUtils;
import com.yjz.load.api.ApiService;
import com.yjz.load.bean.BaseBean;
import com.yjz.load.presenter.contract.LoginContract;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * author： YJZ
 * date:  2018/10/17
 * des:
 */

public class LoginPresenter extends BasePresenter<LoginContract.View> {

    public LoginPresenter(LoginContract.View view) {
        super(view);
    }

    public void register(String mobile, String code, String pwd) {

        Subscriber<BaseBean> subscribe = new Subscriber<BaseBean>() {
            @Override
            public void onCompleted() {
                mView.dismissDialog();
            }

            @Override
            public void onError(Throwable throwable) {
                mView.showErrorDialog(throwable.getMessage());
                LogUtils.e("throwable = " + throwable.getMessage());
            }

            @Override
            public void onNext(BaseBean baseBean) {
                LogUtils.e("register = " + baseBean.toString());
            }

            @Override
            public void onStart() {
                super.onStart();
                mView.showLoadingDialog();
            }
        };

        RetrofitUtil.getApi(ApiService.class)
                .getRegisterBean(mobile, code, pwd)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscribe);
    }
}
