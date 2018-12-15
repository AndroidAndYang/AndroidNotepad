package com.seabig.common.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.seabig.common.callback.IApplicationDelegate;
import com.seabig.common.util.LogUtils;
import com.seabig.common.util.Utils;

/**
 * @author YJZ
 * date 2018/5/3
 * description
 */

public class BaseApplication extends Application {

    private static final String[] MODULES_LIST =
            {"com.yjz.notepad.NotepadApplication",
                    "com.yjz.bookkeeping.BookkeepingApplication"};

    private static BaseApplication sInstance;

    public static BaseApplication getInstance()
    {
        return sInstance;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        sInstance = this;

        Utils.init(getApplicationContext());
        // ARouter相关
        if (Utils.isAppDebug())
        {
            //开启InstantRun之后，一定要在ARouter.init之前调用openDebug
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);

        //Module类的APP初始化
        modulesApplicationInit();
    }

    /**
     * 反射获取各个module下实现IApplicationDelegate接口的Application类
     * 并调用IApplicationDelegate下的init方法初始化
     */
    private void modulesApplicationInit()
    {
        for (String moduleImpl : MODULES_LIST)
        {
            try
            {
                Class<?> clazz = Class.forName(moduleImpl);
                Object obj = clazz.newInstance();
                if (obj instanceof IApplicationDelegate)
                {
                    ((IApplicationDelegate) obj).init(BaseApplication.getInstance());
                }
            }
            catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
            catch (InstantiationException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(base);
        // dex突破65535的限制
        MultiDex.install(this);
    }
}
