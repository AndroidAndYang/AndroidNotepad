package com.seabig.common.base;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by Administrator on 2017/2/25.
 * Presenter的基类
 */

public class BasePresenter<V extends BaseView> {

    protected V mView;
    protected Context mContext;

    public BasePresenter(V v) {
        this.mView = v;
        initContext();
    }

    private void initContext() {
        if (mView instanceof Fragment) {
            mContext = ((Fragment) mView).getActivity();
        } else {
            mContext = (Activity) mView;
        }
    }
}
