package com.seabig.common.base.rx.subscribe;

import android.content.Context;
import android.util.Log;

import com.seabig.common.base.BaseException;
import com.seabig.common.base.rx.RxErrorHandler;


/**
 * <pre>
 *     author: YJZ
 *     time  : 2017/3/12  13:10
 *     desc  : 对请求时错误的处理封装
 * </pre>
 */
public abstract class ErrorHandlerSubscribe<T> extends BaseSubscribe<T> {

    protected RxErrorHandler mRxErrorHandler;
    protected Context mContext;

    public ErrorHandlerSubscribe(Context context) {
        this.mContext = context;
        mRxErrorHandler = new RxErrorHandler(mContext);
    }

    @Override
    public void onError(Throwable e) {
        Log.e("TAG", " 错误信息: " +  e.getMessage());
        BaseException baseException = mRxErrorHandler.handleError(e);
        mRxErrorHandler.showErrorMessage(baseException);
    }
}
