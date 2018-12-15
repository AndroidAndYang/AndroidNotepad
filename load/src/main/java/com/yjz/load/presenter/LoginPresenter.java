package com.yjz.load.presenter;

import com.seabig.common.base.BasePresenter;
import com.seabig.common.base.rx.RxHttpResponseCompat;
import com.seabig.common.base.rx.subscribe.ProgressDialogSubscribe;
import com.seabig.common.http.RetrofitUtil;
import com.seabig.common.util.Md5Helper;
import com.yjz.load.api.ApiService;
import com.yjz.load.presenter.contract.LoginContract;

/**
 * author： YJZ
 * date:  2018/10/17
 * des:
 */

public class LoginPresenter extends BasePresenter<LoginContract.View> {

    public LoginPresenter(LoginContract.View view)
    {
        super(view);
    }

    public void login(String userName, String pwd)
    {
        // 没有封装之前
        // Subscriber<Test> subscribe = new Subscriber<Test>() {
        //     @Override
        //     public void onCompleted() {
        //         mView.dismissDialog();
        //     }
        //     @Override
        //     public void onError(Throwable throwable) {
        //         mView.showErrorDialog(throwable.getMessage());
        //         LogUtils.e("throwable = " + throwable.getMessage());
        //     }
        //     @Override
        //     public void onNext(Test baseBean) {
        //         LogUtils.e("loginSuccess = " + baseBean.toString());
        //     }
        //     @Override
        //     public void onStart() {
        //         super.onStart();
        //         mView.showLoadingDialog();
        //     }
        // };

        // RetrofitUtil.getApi(ApiService.class)
        //         .getUserLists()
        //         .subscribeOn(Schedulers.io())
        //         .unsubscribeOn(Schedulers.io())
        //         .observeOn(AndroidSchedulers.mainThread())
        //         .subscribe(subscribe);

        RetrofitUtil.getApi(ApiService.class)
                .getLoginBean(userName, Md5Helper.MD5(pwd))
                .compose(RxHttpResponseCompat.<Long>compatResult())
                .subscribe(new ProgressDialogSubscribe<Long>(mContext) {
                    @Override
                    public void onNext(Long baseBean)
                    {
                        if (baseBean > 0)
                        {
                            mView.loginSuccess(baseBean);
                        }
                    }
                });
    }
}
