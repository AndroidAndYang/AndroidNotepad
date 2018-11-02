package com.yjz.load.presenter;


import com.seabig.common.base.BasePresenter;
import com.seabig.common.base.rx.RxHttpResponseCompat;
import com.seabig.common.base.rx.subscribe.ProgressSubscribe;
import com.seabig.common.http.RetrofitUtil;
import com.seabig.common.util.LogUtils;
import com.yjz.load.api.ApiService;
import com.yjz.load.bean.UserBean;
import com.yjz.load.presenter.contract.LoginContract;

import java.util.List;

/**
 * author： YJZ
 * date:  2018/10/17
 * des:
 */

public class LoginPresenter extends BasePresenter<LoginContract.View> {

    public LoginPresenter(LoginContract.View view) {
        super(view);
    }

    public void registerUser() {

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
        //         LogUtils.e("register = " + baseBean.toString());
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
                 .getRegisterBean("xxx", "13687936131")
                 .compose(RxHttpResponseCompat.<Long>compatResult())
                 .subscribe(new ProgressSubscribe<Long>(mContext, mView) {
                     @Override
                     public void onNext(Long baseBean) {
                         LogUtils.e("baen = " + baseBean);
                     }
                 });
    }

    public void getList() {

        RetrofitUtil.getApi(ApiService.class)
                .getUserList()
                .compose(RxHttpResponseCompat.<List<UserBean>>compatResult())
                .subscribe(new ProgressSubscribe<List<UserBean>>(mContext, mView) {
                    @Override
                    public void onNext(List<UserBean> baseBean) {
                        for (UserBean userBean : baseBean) {
                            LogUtils.e("baen = " + userBean.toString());
                        }
                    }
                });

    }
}
