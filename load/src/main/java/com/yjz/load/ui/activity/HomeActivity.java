package com.yjz.load.ui.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.seabig.common.base.BaseActivity;
import com.seabig.common.datamgr.ARoutPath;
import com.seabig.common.util.BottomNavigationViewHelper;
import com.yjz.load.R;
import com.yjz.load.adapter.ViewPagerAdapter;
import com.yjz.load.ui.widget.AddDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * author： YJZ
 * date:  2018/11/26
 * des:  主页
 */

@Route (path = ARoutPath.LOAD_HOME_ACTIVITY)
public class HomeActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;
    private MenuItem menuItem;
    private AddDialog addDialog;

    @Override
    protected int onSettingUpContentViewResourceID()
    {
        return R.layout.load_activity_home;
    }

    @Override
    protected void onSettingUpView()
    {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
    }

    @Override
    protected void onSettingUpData()
    {
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        viewPager.setOffscreenPageLimit(0);
        viewPager.addOnPageChangeListener(this);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        Fragment bookkeepingFragment = (Fragment) ARouter.getInstance().build(ARoutPath.BOOKKEEPING_FRAGMENT).navigation();
        Fragment memorandumFragment = (Fragment) ARouter.getInstance().build(ARoutPath.MEMORANDUM_FRAGMENT).navigation();
        List<Fragment> list = new ArrayList<>();
        list.add(bookkeepingFragment);
        list.add(memorandumFragment);
        viewPagerAdapter.setList(list);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            menuItem = item;
            int i = item.getItemId();
            if (i == R.id.navigation_bookkeeping)
            {
                viewPager.setCurrentItem(0);
                return true;
            } else if (i == R.id.navigation_add)
            {
                addDialog = new AddDialog(HomeActivity.this);
                addDialog.setBookkeepingClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        addDialog.closeDialog();
                        ARouter.getInstance().build(ARoutPath.BOOKKEEPING_ACTIVITY).navigation();
                    }
                }).setMemorandumClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        addDialog.closeDialog();
                        showToast("备忘录");
                    }
                }).setCloseClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        addDialog.closeDialog();
                    }
                });
                addDialog.show();
            } else if (i == R.id.navigation_memorandum)
            {
                viewPager.setCurrentItem(1);
                return true;
            }
            return false;
        }
    };

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {

    }

    @Override
    public void onPageSelected(int position)
    {
        if (menuItem != null)
        {
            menuItem.setChecked(false);
        } else
        {
            bottomNavigationView.getMenu().getItem(0).setChecked(false);
        }
        menuItem = bottomNavigationView.getMenu().getItem(position);
        menuItem.setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state)
    {

    }

    private long lastBackKeyDownTick = 0;

    //获取退出程序按下时间间隔(单位：毫秒)
    protected long onGetExitAppPressMSecs()
    {
        return 1500;
    }

    // 返回键监听
    @Override
    public void onBackPressed()
    {
        long currentTick = System.currentTimeMillis();
        if (currentTick - lastBackKeyDownTick > onGetExitAppPressMSecs())
        {
            if (addDialog.isShowing())
            {
                addDialog.closeDialog();
            }
            showToast(getStringByResId(R.string.press_again_app_exit));
            lastBackKeyDownTick = currentTick;
        } else
        {
            finish();
            System.exit(0);
        }
    }
}
