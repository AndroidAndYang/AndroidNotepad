package com.seabig.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * 延时加载数据的Fragment(在设置可见性时加载数据)
 */
public abstract class DelayLoadFragment extends BaseFragment
{
    protected boolean mIsVisible;
    private boolean mIsActivityCreated;
    private boolean mIsFirstLoad = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint())
        {
            mIsVisible = true;
            delayLoad();
        }
        else
        {
            mIsVisible = false;
            onChangedToInVisible();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        mIsActivityCreated = true;
        delayLoad();

    }

    @Override
    protected boolean onDelaySettingUp()
    {
        return true;
    }

    @Override
    protected final void onBeforeSettingUpView(Bundle savedInstanceState)
    {
        super.onBeforeSettingUpView(savedInstanceState);
    }

    //延时加载数据
    protected void delayLoad()
    {
        if (!mIsActivityCreated || !mIsVisible || !mIsFirstLoad)
            return;

        onSettingUpView();
        onSettingUpData();
        onSettingUpListener();

        mIsFirstLoad = false;
    }

    //切换到不可见状态，子类可处理相关事件
    protected void onChangedToInVisible()
    {

    }
}