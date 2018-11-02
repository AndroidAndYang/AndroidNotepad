package com.seabig.common.base.rx;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.seabig.common.R;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * <pre>
 *     author: YJZ
 *     time  : 2017/3/12  16:19
 *     desc  : 对话框的封装处理
 * </pre>
 */
public class ProgressDialogHandler extends Handler {

    public static final int DISMISS_PROGRESS_DIALOG = 0;
    public static final int SHOW_PROGRESS_DIALOG = 1;

    private SweetAlertDialog mProgressDialog;
    private Context mContext;
    private boolean cancelable;
    private OnProgressCancelListener mProgressCancelListener;

    public ProgressDialogHandler(Context context) {
        this(context, false, null);
    }

    public ProgressDialogHandler(Context context, boolean cancelable,
                                 OnProgressCancelListener progressCancelListener) {
        super();
        this.mContext = context;
        this.cancelable = cancelable;
        this.mProgressCancelListener = progressCancelListener;
        initProgressDialog();
    }

    // 初始化 SweetAlertDialog
    public void initProgressDialog() {
        mProgressDialog = new SweetAlertDialog(mContext);
        mProgressDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        mProgressDialog.setTitleText(mContext.getResources().getString(R.string.loading));
        //判断是否关闭
        if (cancelable) {
            mProgressDialog.setCancelText(mContext.getResources().getString(R.string.close));
            mProgressDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    mProgressDialog.cancel();
                    if (mProgressCancelListener != null) {
                        mProgressCancelListener.onCancelProgress();
                    }
                }
            });
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                showProgressDialog();
                break;

            case DISMISS_PROGRESS_DIALOG:
                dismissDialog();
                break;
        }
    }


    public void showProgressDialog() {
        if (mProgressDialog != null && !mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    public void dismissDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    // 对话框结束监听
    public interface OnProgressCancelListener {
        void onCancelProgress();
    }
}
