package com.yjz.bookkeeping;

import android.app.Application;
import android.content.Context;

import com.seabig.common.callback.IApplicationDelegate;
import com.seabig.common.datamgr.AppConstant;
import com.seabig.common.util.LogUtils;
import com.seabig.common.util.SPUtils;
import com.seabig.common.util.ToastUtils;
import com.yjz.bookkeeping.db.DaoMaster;
import com.yjz.bookkeeping.db.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * @author YJZ
 * @date 2018/12/15
 * @description
 */
public class BookkeepingApplication implements IApplicationDelegate {

    private Context mContext;
    private DaoSession mSession;
    private static BookkeepingApplication mInstance;

    /**
     * 获取 Application 实例
     *
     * @return BaseApplication
     */
    public static BookkeepingApplication getInstance()
    {
        return mInstance;
    }

    @Override
    public void init(Application application)
    {
        mInstance = this;
        this.mContext = application.getApplicationContext();
        initGreenDao();
        getSession();
    }

    /**
     * 初始化 greenDAO
     */
    private void initGreenDao()
    {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(mContext, "account-db");
        Database db = helper.getWritableDb();
        mSession = new DaoMaster(db).newSession();
    }

    /**
     * 获取 DaoSession 对象
     *
     * @return DaoSession
     */
    public DaoSession getSession()
    {
        return mSession;
    }
}
