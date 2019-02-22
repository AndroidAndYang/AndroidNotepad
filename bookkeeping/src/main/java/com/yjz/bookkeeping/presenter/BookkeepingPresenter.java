package com.yjz.bookkeeping.presenter;

import com.seabig.common.base.BasePresenter;
import com.seabig.common.base.rx.RxHttpResponseCompat;
import com.seabig.common.base.rx.subscribe.ProgressSubscribe;
import com.seabig.common.http.RetrofitUtil;
import com.seabig.common.util.LogUtils;
import com.yjz.bookkeeping.api.ApiService;
import com.yjz.bookkeeping.bean.BookkeepingAllBean;
import com.yjz.bookkeeping.presenter.contract.BookkeepingContract;

/**
 * @author YJZ
 * @date 2018/12/13
 * @description
 */
public class BookkeepingPresenter extends BasePresenter<BookkeepingContract.View> {

    public BookkeepingPresenter(BookkeepingContract.View view)
    {
        super(view);
    }

    public void getBookkeepingData(Long userId, Long type, String yearAndMonth)
    {
        RetrofitUtil.getApi(ApiService.class)
                .getBookkeepingData(userId, type, yearAndMonth)
                .compose(RxHttpResponseCompat.<BookkeepingAllBean>compatResult())
                .subscribe(new ProgressSubscribe<BookkeepingAllBean>(mContext, mView) {
                    @Override
                    public void onNext(BookkeepingAllBean bookkeepingBean)
                    {
                        LogUtils.e("bookkeepingBean = " + bookkeepingBean.toString());
                        mView.setBookkeepingData(bookkeepingBean);
                    }
                });
    }

}
