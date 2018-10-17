package com.seabig.common.base;

/**
 * Created by Administrator on 2017/2/25.
 * View的基类
 */

public interface BaseView {
    /**
     * 加载中
     */
    void showLoadingDialog();

    /**
     * 错误
     *
     * @param msg 错误信息
     */
    void showErrorDialog(String msg);

    /**
     * RealView
     */
    void dismissDialog();
}
