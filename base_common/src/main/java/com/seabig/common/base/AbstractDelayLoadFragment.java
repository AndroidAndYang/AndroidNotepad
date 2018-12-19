package com.seabig.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.seabig.common.util.LogUtils;

/**
 * @author YJZ
 *         Date 2017/12/11
 *         Description 延时加载Fragment
 */
public abstract class AbstractDelayLoadFragment extends ProgressBaseFragment {

    protected boolean mIsVisible;
    private boolean mIsActivityCreated;
    private boolean mIsFirstLoad = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            mIsVisible = true;
            delayLoad();
        } else {
            mIsVisible = false;
            onChangedToInVisible();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mIsActivityCreated = true;
        delayLoad();
    }

    @Override
    protected final void onBeforeSettingUpView(Bundle savedInstanceState) {
        super.onBeforeSettingUpView(savedInstanceState);
    }

    /**
     * 延时加载数据
     */
    public void delayLoad() {
        if (!mIsActivityCreated || !mIsVisible || !mIsFirstLoad) {
            return;
        }
        onSettingUpView();
        onSettingUpData();
        onSettingUpListener();
        mIsFirstLoad = false;
    }

    /**
     * 切换到不可见状态，子类可处理相关事件
     */
    protected void onChangedToInVisible() {
        LogUtils.e(" onChangedToInVisible ");
    }
}