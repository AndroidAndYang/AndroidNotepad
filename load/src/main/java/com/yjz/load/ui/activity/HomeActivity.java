package com.yjz.load.ui.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.seabig.common.base.BaseActivity;
import com.seabig.common.util.BottomNavigationViewHelper;
import com.seabig.common.util.LogUtils;
import com.yjz.load.R;
import com.yjz.load.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * author： YJZ
 * date:  2018/11/26
 * des:  主页
 */

@Route(path = "/load/activity/home")
public class HomeActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;
    private MenuItem menuItem;

    @Override
    protected int onSettingUpContentViewResourceID() {
        return R.layout.load_activity_home;
    }

    @Override
    protected void onSettingUpView() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
    }

    @Override
    protected void onSettingUpData() {
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        viewPager.setOffscreenPageLimit(0);
        viewPager.addOnPageChangeListener(this);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        Fragment bookkeepingFragment = (Fragment) ARouter.getInstance().build("/bookkeeping/fragment/home").navigation();
        Fragment memorandumFragment = (Fragment) ARouter.getInstance().build("/memorandum/fragment/home").navigation();
        List<Fragment> list = new ArrayList<>();
        list.add(bookkeepingFragment);
        list.add(memorandumFragment);
        viewPagerAdapter.setList(list);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            menuItem = item;
            int i = item.getItemId();
            if (i == R.id.navigation_bookkeeping) {
                viewPager.setCurrentItem(0);
                return true;
            } else if (i == R.id.navigation_memorandum) {
                viewPager.setCurrentItem(1);
                return true;
            }
            return false;
        }
    };

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (menuItem != null) {
            menuItem.setChecked(false);
        } else {
            bottomNavigationView.getMenu().getItem(0).setChecked(false);
        }
        menuItem = bottomNavigationView.getMenu().getItem(position);
        menuItem.setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}