package com.yjz.bookkeeping.presenter;

import android.content.Context;

import com.seabig.common.base.rx.RxHttpResponseCompat;
import com.seabig.common.base.rx.subscribe.ProgressDialogSubscribe;
import com.seabig.common.http.RetrofitUtil;
import com.yjz.bookkeeping.api.ApiService;
import com.yjz.bookkeeping.bean.BookkeepingBean;
import com.yjz.bookkeeping.presenter.contract.BookkeepingCommitContract;

/**
 * author： YJZ
 * date:  2019/2/21
 * des:
 */

public class BookkeepingCommitPresenter {

    private BookkeepingCommitContract.View mView;
    private Context mContext;

    public BookkeepingCommitPresenter( Context context,BookkeepingCommitContract.View view) {
        this.mView= view;
        this.mContext = context;
    }

    public void commit(final BookkeepingBean bookkeepingBean){
        RetrofitUtil.getApi(ApiService.class)
                .commitBookkeepingData("bs",bookkeepingBean)
                .compose(RxHttpResponseCompat.<Boolean>compatResult())
                .subscribe(new ProgressDialogSubscribe<Boolean>(mContext) {
                    @Override
                    public void onNext(Boolean isSuccess) {
                        mView.onSuccess(bookkeepingBean,isSuccess);
                    }
        });
    }

}
