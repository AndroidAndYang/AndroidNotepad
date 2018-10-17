package com.seabig.common.base.rx.subscribe;

import android.content.Context;

import com.seabig.common.base.BaseException;
import com.seabig.common.base.BaseView;


/**
 * author: YJZ
 * time  : 2017/3/13  16:36
 * desc  : Fragment中内置加载时的请求对话框以及错误为空和错误的情况下
 */
public abstract class ProgressSubscribe<T> extends ErrorHandlerSubscribe<T> {

    private BaseView mBaseView;

    public ProgressSubscribe(Context context, BaseView baseView) {
        super(context);
        this.mBaseView = baseView;
    }

    protected boolean isShowProgress() {
        return true;
    }

    @Override
    public void onError(Throwable e) {
        BaseException baseException = mRxErrorHandler.handleError(e);
        mBaseView.showErrorDialog(baseException.getDisplayMsg());
    }

    @Override
    public void onStart() {
        if (isShowProgress()) {
            mBaseView.showLoadingDialog();
        }
    }

    @Override
    public void onCompleted() {
        if (isShowProgress()) {
            mBaseView.dismissDialog();
        }
    }
}
