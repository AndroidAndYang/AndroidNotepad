package com.yjz.bookkeeping.ui.activity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.seabig.common.base.BaseActivity;
import com.seabig.common.datamgr.ARoutPath;
import com.seabig.common.datamgr.AppConstant;
import com.seabig.common.util.SPUtils;
import com.yjz.bookkeeping.BookkeepingApplication;
import com.yjz.bookkeeping.R;
import com.yjz.bookkeeping.db.Type;
import com.yjz.bookkeeping.db.TypeDao;

import java.util.List;

/**
 * @author YJZ
 * @date 2018/12/15
 * @description
 */
@Route (path = ARoutPath.BOOKKEEPING_ACTIVITY)
public class BookkeepingActivity extends BaseActivity {

    @Override
    protected int onSettingUpContentViewResourceID()
    {
        return R.layout.bookkeeping_activity_bookkeeping;
    }

    @Override
    protected void onSettingUpView()
    {
        Long userId = (Long) SPUtils.get(this, AppConstant.USER_ID, 0L);

        TypeDao dao = BookkeepingApplication.getInstance().getSession().getTypeDao();
        List<Type> list = dao.queryBuilder().where(TypeDao.Properties.Uid.eq(userId)).list();
        showToast("list size = " + list.size());
    }
}
