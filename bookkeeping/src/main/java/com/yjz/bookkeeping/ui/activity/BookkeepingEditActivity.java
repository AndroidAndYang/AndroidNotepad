package com.yjz.bookkeeping.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.seabig.common.base.BaseActivity;
import com.seabig.common.datamgr.ARoutPath;
import com.seabig.common.datamgr.AppConstant;
import com.seabig.common.util.LogUtils;
import com.seabig.common.util.SPUtils;
import com.yjz.bookkeeping.BookkeepingApplication;
import com.yjz.bookkeeping.R;
import com.yjz.bookkeeping.adapter.BookkeepingEditFragmentAdapter;
import com.yjz.bookkeeping.datamgr.BookkeepingType;
import com.yjz.bookkeeping.db.Type;
import com.yjz.bookkeeping.db.TypeDao;
import com.yjz.bookkeeping.ui.fragment.BookkeepingEditFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author YJZ
 *         date 2018/12/15
 *         description
 */
@Route(path = ARoutPath.BOOKKEEPING_ACTIVITY)
public class BookkeepingEditActivity extends BaseActivity implements View.OnClickListener {

    private final String[] TITLE_Arr = new String[]{"支出", "收入"};

    @Override
    protected int onSettingUpContentViewResourceID() {
        return R.layout.bookkeeping_activity_bookkeeping;
    }

    @Override
    protected void onSettingUpView() {
        findViewById(R.id.cancel).setOnClickListener(this);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(0);

        List<Fragment> fragmentList = new ArrayList<>(2);
        BookkeepingEditFragment outFragment = BookkeepingEditFragment.newInstance(BookkeepingType.TYPE_OUT);
        BookkeepingEditFragment inFragment = BookkeepingEditFragment.newInstance(BookkeepingType.TYPE_INCOME);
        fragmentList.add(outFragment);
        fragmentList.add(inFragment);
        BookkeepingEditFragmentAdapter adapter = new BookkeepingEditFragmentAdapter(getSupportFragmentManager(), fragmentList, Arrays.asList(TITLE_Arr));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    protected void onSettingUpData() {
        Long userId = (Long) SPUtils.get(this, AppConstant.USER_ID, 0L);
        TypeDao dao = BookkeepingApplication.getInstance().getSession().getTypeDao();
        List<Type> list = dao.queryBuilder().where(TypeDao.Properties.Uid.eq(userId)).list();
        LogUtils.e("list = " + list.size());
        if (list.size() <= 0) {
            saveTypeData(userId, dao, BookkeepingType.TYPE_OUT);
            // TODO 收入数据
            // saveTypeData(uid, dao, AppConfig.TYPE_INCOME);
        }
    }

    private void saveTypeData(Long uid, TypeDao dao, Long type) {
        String[] types;
        if (type.equals(BookkeepingType.TYPE_OUT)) {
            types = getResources().getStringArray(R.array.bookkeeping_out_type);
        } else {
            types = getResources().getStringArray(R.array.bookkeeping_income_type);
        }
        for (int i = 0; i < types.length; i++) {
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

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.cancel) {
            finish();
        }
    }
}
