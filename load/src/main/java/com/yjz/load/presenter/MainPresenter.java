package com.yjz.load.presenter;

import com.seabig.common.base.BasePresenter;
import com.seabig.common.base.rx.RxHttpResponseCompat;
import com.seabig.common.base.rx.subscribe.ProgressSubscribe;
import com.seabig.common.http.RetrofitUtil;
import com.seabig.common.util.Md5Helper;
import com.yjz.load.api.ApiService;
import com.yjz.load.presenter.contract.MainContract;

/**
 * author： YJZ
 * date:  2018/11/19
 * des: main
 */

public class MainPresenter extends BasePresenter<MainContract.View> {

    public MainPresenter(MainContract.View view) {
        super(view);
    }

    public void getBookkeepingData(Long userId) {
        RetrofitUtil.getApi(ApiService.class)
                .getLoginBean("123456", Md5Helper.MD5("123456"))
                .compose(RxHttpResponseCompat.<Long>compatResult())
                .subscribe(new ProgressSubscribe<Long>(mContext, mView) {
                    @Override
                    public void onNext(Long baseBean) {
                        if (baseBean > 0) {
                            // mView.register(baseBean);
                        }
                    }
                });
    }
}
