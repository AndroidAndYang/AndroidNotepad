package com.yjz.bookkeeping.ui.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.seabig.common.base.ProgressBaseFragment;
import com.seabig.common.datamgr.AppConstant;
import com.seabig.common.util.SPUtils;
import com.yjz.bookkeeping.R;
import com.yjz.bookkeeping.ui.activity.AboutActivity;
import com.yjz.bookkeeping.ui.activity.MsgActivity;
import com.yjz.bookkeeping.ui.activity.SetActivity;
import com.yjz.bookkeeping.ui.adapter.BookkeepingAdapter;
import com.yjz.bookkeeping.ui.bean.BookkeepingBean;
import com.yjz.bookkeeping.ui.datamgr.BookkeepingType;
import com.yjz.bookkeeping.ui.presenter.BookkeepingPresenter;
import com.yjz.bookkeeping.ui.presenter.contract.BookkeepingContract;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * author： YJZ
 * date:  2018/11/19
 * des:
 */
@Route (path = "/bookkeeping/fragment/home")
public class BookkeepingFragment extends ProgressBaseFragment implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, BookkeepingContract.View {

    private DrawerLayout mDrawerLayout;
    private MenuItem mMsgBadgeMenuItem;
    private MenuItem mMsgMenuItem;
    private Long userId;
    private TextView toolbarTitle;
    private RecyclerView mRecyclerView;
    private TextView mMoneyInTv;
    private TextView mMoneyOutTv;

    @Override
    protected void onPrepareOpt()
    {
        setHasOptionsMenu(true);
    }

    @Override
    protected int onSettingUpContentViewResourceID()
    {
        return R.layout.bookkeeping_fragment_bookkeeping;
    }

    @Override
    protected void onSettingUpView()
    {
        mDrawerLayout = findViewById(R.id.draw_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);

        mMoneyInTv = findViewById(R.id.money_in_tv);
        mMoneyOutTv = findViewById(R.id.money_out_tv);

        // Toolbar
        Toolbar toolbar = initToolbar(R.id.toolbar, R.id.toolbar_title, R.string.app_name);
        toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setOnClickListener(this);

        // 设置侧滑导航
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, toolbar
                , R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        mRecyclerView = findViewById(R.id.recycler_view);
    }

    @Override
    protected void onSettingUpDate()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月", Locale.CHINA);
        toolbarTitle.setText(dateFormat.format(calendar.getTime()));

        userId = (Long) SPUtils.get(getActivity(), AppConstant.USER_ID, 0L);
        showToast("userId" + userId);

        BookkeepingPresenter presenter = new BookkeepingPresenter(this);
        presenter.getBookkeepingData(userId, BookkeepingType.LIFE, "2018-11");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        (getActivity()).getMenuInflater().inflate(R.menu.bookkeeping_fragment_main_menu, menu);
        mMsgBadgeMenuItem = menu.findItem(R.id.action_edit);
        mMsgMenuItem = menu.findItem(R.id.msg);
        // TODO 根据消息控制 消息图片的显示与隐藏
        if (userId > 0)
        {
            mMsgMenuItem.setVisible(false);
        }
        // 设置menu点击事件
        mMsgBadgeMenuItem.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getActivity(), MsgActivity.class));
            }
        });
    }

    /**
     * 侧滑栏的点击事件
     *
     * @param item menuItem
     * @return isClick
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        int i = item.getItemId();
        if (i == R.id.nav_account_books_type)
        {
            showToast("nav_account_books");
        } else if (i == R.id.nav_count)
        {
            showToast("nav_count");
        } else if (i == R.id.nav_about)
        {
            startActivity(new Intent(getActivity(), AboutActivity.class));
        } else if (i == R.id.nav_setting)
        {
            startActivity(new Intent(getActivity(), SetActivity.class));
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v)
    {
        int i = v.getId();
        if (i == R.id.toolbar_title)
        {
            showToast("显示日期选择器");
        }
    }

    @Override
    public void setBookkeepingData(BookkeepingBean dataBean)
    {
        mMoneyInTv.setText(String.valueOf(dataBean.getAllMonthIn()));
        mMoneyOutTv.setText(String.valueOf(dataBean.getAllMonthOut()));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new BookkeepingAdapter(dataBean.getDayData()));
    }
}
