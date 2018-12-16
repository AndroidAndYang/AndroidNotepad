package com.yjz.bookkeeping.ui.activity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.seabig.common.base.BaseActivity;
import com.seabig.common.datamgr.ARoutPath;
import com.seabig.common.datamgr.AppConstant;
import com.seabig.common.util.LogUtils;
import com.seabig.common.util.SPUtils;
import com.yjz.bookkeeping.BookkeepingApplication;
import com.yjz.bookkeeping.R;
import com.yjz.bookkeeping.datamgr.BookkeepingType;
import com.yjz.bookkeeping.db.Type;
import com.yjz.bookkeeping.db.TypeDao;

import java.util.List;

/**
 * @author YJZ
 * date 2018/12/15
 * description
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
        LogUtils.e("list = " + list.size());
        if (list.size() <= 0)
        {
            saveTypeData(userId, dao, BookkeepingType.TYPE_OUT);
//            saveTypeData(uid, dao, AppConfig.TYPE_INCOME);
        }

        List<Type> mList = dao.queryBuilder().where(TypeDao.Properties.Uid.eq(userId)).list();
        showToast("mList = " + mList.size());

    }

    private void saveTypeData(Long uid, TypeDao dao, Long type)
    {
        String[] types;
        if (type.equals(BookkeepingType.TYPE_OUT))
        {
            types = getResources().getStringArray(R.array.bookkeeping_out_type);
        } else
        {
            types = getResources().getStringArray(R.array.bookkeeping_income_type);
        }
        for (int i = 0; i < types.length; i++)
        {
            Type t = new Type();
            t.setUid(uid);
            t.setIndex(i);
            t.setType(type);
            t.setName(types[i]);
            t.setIcon(type.equals(BookkeepingType.TYPE_OUT) ? "bookkeeping_out_type_".concat(String.valueOf(i))
                    : "bookkeeping_in_type_".concat(String.valueOf(i)));
            dao.insert(t);
        }
    }
}
