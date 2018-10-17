package com.seabig.common.base.rx;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.JsonParseException;
import com.seabig.common.base.BaseException;
import com.seabig.common.exception.ApiException;
import com.seabig.common.exception.ErrorMessageFactory;
import com.seabig.common.util.ToastUtils;

import java.net.SocketException;
import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;

/**
 * <pre>
 *     author: YJZ
 *     time  : 2017/3/12  13:11
 *     desc  : 异常处理
 * </pre>
 */
public class RxErrorHandler {


    private Context mContext;

    public RxErrorHandler(Context context) {
        this.mContext = context;
    }

    public BaseException handleError(Throwable e) {
        BaseException exception = new BaseException();
        if (e instanceof ApiException) {
            exception.setCode(((ApiException) e).getCode());
        } else if (e instanceof JsonParseException) {
            exception.setCode(BaseException.JSON_ERROR);
        } else if (e instanceof HttpException) {
            exception.setCode(((HttpException) e).code());
        } else if (e instanceof SocketTimeoutException) {
            exception.setCode(BaseException.SOCKET_TIMEOUT_ERROR);
        } else if (e instanceof SocketException) {
            exception.setCode(BaseException.SOCKET_ERROR);
        } else {
            exception.setCode(BaseException.UNKNOWN_ERROR);
        }
        exception.setDisplayMsg(ErrorMessageFactory.create(mContext, exception.getCode()));
        return exception;
    }

    public void showErrorMessage(BaseException e) {
        ToastUtils.getInstance().showToast(mContext, e.getDisplayMsg());

    }

}
