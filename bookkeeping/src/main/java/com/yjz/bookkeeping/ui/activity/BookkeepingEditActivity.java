package com.yjz.bookkeeping.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

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
import com.yjz.bookkeeping.ui.fragment.BookkeepingEditInFragment;
import com.yjz.bookkeeping.ui.fragment.BookkeepingEditOutFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author YJZ
 *         date 2018/12/15
 *         description
 */
@Route(path = ARoutPath.BOOKKEEPING_ACTIVITY)
public class BookkeepingEditActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private final String[] TITLE_Arr = new String[]{"支出", "收入"};
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    public ImageView mSaveImg;
    public int position;
    private BookkeepingEditOutFragment outFragment;
    private BookkeepingEditInFragment inFragment;

    @Override
    protected int onSettingUpContentViewResourceID() {
        return R.layout.bookkeeping_activity_bookkeeping;
    }

    @Override
    protected void onSettingUpView() {
        findViewById(R.id.back).setOnClickListener(this);
        mSaveImg = (ImageView) findViewById(R.id.save);
        mSaveImg.setOnClickListener(this);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setOffscreenPageLimit(0);
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    protected void onSettingUpData() {

        Long userId = (Long) SPUtils.get(this, AppConstant.USER_ID, 0L);
        TypeDao dao = BookkeepingApplication.getInstance().getSession().getTypeDao();
        List<Type> list = dao.queryBuilder().where(TypeDao.Properties.Uid.eq(userId)).list();
        LogUtils.e("list = " + list.size());
        if (list.size() <= 0) {
            saveOutTypeData(userId, dao, BookkeepingType.TYPE_OUT);
            saveOutTypeData(userId, dao, BookkeepingType.TYPE_INCOME);
        }

        List<Fragment> fragmentList = new ArrayList<>(2);
        outFragment = new BookkeepingEditOutFragment();
        inFragment = new BookkeepingEditInFragment();
        fragmentList.add(outFragment);
        fragmentList.add(inFragment);
        BookkeepingEditFragmentAdapter adapter = new BookkeepingEditFragmentAdapter(getSupportFragmentManager(), fragmentList, Arrays.asList(TITLE_Arr));
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }


    private void saveOutTypeData(Long uid, TypeDao dao, Long type) {
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
        if (i == R.id.back) {
            finish();
        } else if (i == R.id.save) {
            if (position == 0) {
                outFragment.post();
            } else {
                inFragment.post();
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.position = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
