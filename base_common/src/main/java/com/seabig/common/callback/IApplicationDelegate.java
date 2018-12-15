package com.seabig.common.callback;

import android.app.Application;

/**
 * @author YJZ 2017/9/20 22:23
 * des 用于初始化获取module类中的Application,防止在组件运行中找不到application类
 */

public interface IApplicationDelegate {
    /**
     * 多module下Application初始化方法
     *
     * @param application application
     */
    void init(Application application);
}
