package com.seabig.common.base.rx.subscribe;

import android.content.Context;

import com.seabig.common.base.rx.ProgressDialogHandler;

/**
 * <pre>
 *     author: YJZ
 *     time  : 2017/3/12  13:58
 *     desc  : 对请求时的对话框进行封装
 * </pre>
 */
public abstract class ProgressDialogSubscribe<T> extends ErrorHandlerSubscribe<T> implements ProgressDialogHandler.OnProgressCancelListener {

    private ProgressDialogHandler mProgressDialogHandler;

    public ProgressDialogSubscribe(Context context) {
        super(context);
        mProgressDialogHandler = new ProgressDialogHandler(context, false, this);
    }

    protected boolean isShowProgressDialog() {
        return true;
    }

    @Override
    public void onCompleted() {
        if (isShowProgressDialog()) {
            this.mProgressDialogHandler.dismissDialog();
        }
    }

    @Override
    public void onStart() {
        if (isShowProgressDialog()) {
            this.mProgressDialogHandler.showProgressDialog();
        }
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        if (isShowProgressDialog()) {
            this.mProgressDialogHandler.dismissDialog();
        }
    }

    @Override
    public void onCancelProgress() {
        unsubscribe();
    }
}
