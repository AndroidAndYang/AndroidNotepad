package com.yjz.load.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.seabig.common.base.ProgressBaseActivity;
import com.seabig.common.datamgr.AppConstant;
import com.seabig.common.util.SPUtils;
import com.yjz.load.R;
import com.yjz.load.presenter.MainPresenter;
import com.yjz.load.presenter.contract.MainContract;


/**
 * author： YJZ
 * date:  2018/11/19
 * des: 记账本首页
 */

@Route(path = "/load/activity/main")
public class MainActivity extends ProgressBaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, MainContract.View {

    private DrawerLayout mDrawerLayout;
    private MenuItem mMsgBadgeMenuItem;
    private MenuItem mMsgMenuItem;
    private Long userId;

    @Override
    protected int onSettingUpContentViewResourceID() {
        return R.layout.load_activity_main;
    }

    @Override
    protected void onSettingUpView() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.draw_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        // Toolbar
        Toolbar toolbar = initToolbar(R.id.toolbar, R.id.toolbar_title, R.string.app_name);
        TextView toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        toolbarTitle.setOnClickListener(this);

        // 设置侧滑导航
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar
                , R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        userId = (Long) SPUtils.get(MainActivity.this, AppConstant.USER_ID, 0L);
        showToast("userId" + userId);

        MainPresenter mainPresenter = new MainPresenter(this);
        mainPresenter.getBookkeepingData(userId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 设置 Toolbar menu icon
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.load_activity_main_menu, menu);
        mMsgBadgeMenuItem = menu.findItem(R.id.action_edit);
        mMsgMenuItem = menu.findItem(R.id.msg);

        if (userId > 0) {
            mMsgMenuItem.setVisible(false);
        }

        // 设置menu点击事件
        mMsgBadgeMenuItem.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MsgActivity.class));
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_edit) {
            startActivity(new Intent(this, MsgActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.nav_account_books) {
            showToast("nav_account_books");
        } else if (i == R.id.nav_count) {
            showToast("nav_count");
        } else if (i == R.id.nav_feedback) {
            showToast("nav_feedback");
        } else if (i == R.id.nav_setting) {
            showToast("nav_setting");
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.toolbar_title) {
            showToast("toolbar_title");
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void setBookkeepingData() {

    }
}
