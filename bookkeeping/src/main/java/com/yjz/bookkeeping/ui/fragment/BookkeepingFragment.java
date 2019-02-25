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
import com.seabig.common.datamgr.ARoutPath;
import com.seabig.common.datamgr.AppConstant;
import com.seabig.common.util.LogUtils;
import com.seabig.common.util.SPUtils;
import com.yjz.bookkeeping.R;
import com.yjz.bookkeeping.event.BookkeepingEditEvent;
import com.yjz.bookkeeping.ui.activity.AboutActivity;
import com.yjz.bookkeeping.ui.activity.MsgActivity;
import com.yjz.bookkeeping.ui.activity.SetActivity;
import com.yjz.bookkeeping.adapter.BookkeepingAdapter;
import com.yjz.bookkeeping.bean.BookkeepingAllBean;
import com.yjz.bookkeeping.datamgr.BookkeepingType;
import com.yjz.bookkeeping.presenter.BookkeepingPresenter;
import com.yjz.bookkeeping.presenter.contract.BookkeepingContract;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * author： YJZ
 * date:  2018/11/19
 * des:
 */
@Route(path = ARoutPath.BOOKKEEPING_FRAGMENT)
public class BookkeepingFragment extends ProgressBaseFragment implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, BookkeepingContract.View {

    private DrawerLayout mDrawerLayout;
    private MenuItem mMsgBadgeMenuItem;
    private MenuItem mMsgMenuItem;
    private Long userId;
    private TextView toolbarTitle;
    private RecyclerView mRecyclerView;
    private TextView mMoneyInTv;
    private TextView mMoneyOutTv;
    private TextView mEmptyHintTv;
    private BookkeepingAdapter mBookkeepingAdapter;

    @Override
    protected void onPrepareOpt() {
        setHasOptionsMenu(true);
    }

    @Override
    protected int onSettingUpContentViewResourceID() {
        EventBus.getDefault().register(this);
        return R.layout.bookkeeping_fragment_bookkeeping;
    }

    @Override
    protected void onSettingUpView() {
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

        mEmptyHintTv = findViewById(R.id.empty_hint_tv);
        mRecyclerView = findViewById(R.id.recycler_view);
    }

    @Override
    protected void onSettingUpData() {
        Calendar calendar = Calendar.getInstance();

        int yearStr = calendar.get(Calendar.YEAR);//获取年份
        int month = calendar.get(Calendar.MONTH) + 1;//获取月份

        toolbarTitle.setText(String.format(Locale.CHINA, "%s年%s月", yearStr, month));

        userId = (Long) SPUtils.get(getActivity(), AppConstant.USER_ID, 0L);

        BookkeepingPresenter presenter = new BookkeepingPresenter(this);
        presenter.getBookkeepingData(userId, BookkeepingType.LIFE, String.format(Locale.CHINA, "%s-%s", yearStr, month));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        (getActivity()).getMenuInflater().inflate(R.menu.bookkeeping_fragment_main_menu, menu);
        mMsgBadgeMenuItem = menu.findItem(R.id.action_edit);
        mMsgMenuItem = menu.findItem(R.id.msg);
        // TODO 根据消息控制 消息图片的显示与隐藏
        if (userId > 0) {
            mMsgBadgeMenuItem.setVisible(true);
            mMsgMenuItem.setVisible(false);
        }
        // 设置menu点击事件
        mMsgBadgeMenuItem.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MsgActivity.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.msg) {
            startActivity(new Intent(getActivity(), MsgActivity.class));
        }
        return true;
    }

    /**
     * 侧滑栏的点击事件
     *
     * @param item menuItem
     * @return isClick
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.nav_account_books_type) {
            showToast("nav_account_books");
        } else if (i == R.id.nav_count) {
            showToast("nav_count");
        } else if (i == R.id.nav_about) {
            startActivity(new Intent(getActivity(), AboutActivity.class));
        } else if (i == R.id.nav_setting) {
            startActivity(new Intent(getActivity(), SetActivity.class));
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.toolbar_title) {
            showToast("显示日期选择器");
        }
    }

    @Override
    public void setBookkeepingData(BookkeepingAllBean dataBean) {
        mMoneyInTv.setText(String.valueOf(dataBean.getAllMonthIn()));
        mMoneyOutTv.setText(String.valueOf(dataBean.getAllMonthOut()));
        if (dataBean.getDayData() == null || dataBean.getDayData().size() < 0) {
            mEmptyHintTv.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mBookkeepingAdapter = new BookkeepingAdapter(getActivity(), dataBean.getDayData());
            mRecyclerView.setAdapter(mBookkeepingAdapter);
        }
    }

    /**
     * 记账页 确认是接受的Event的事件
     * @param editEvent 记账的收入或支出
     */
    @Subscribe
    public void onEvent(BookkeepingEditEvent editEvent) {
        BookkeepingAllBean.DayDataBean.UserBookkeepingBeansBean userBookkeepingBeansBean = editEvent.getUserBookkeepingBeansBean();

        if (userBookkeepingBeansBean != null) {
            // 更新总额
            switch (userBookkeepingBeansBean.getMoneyType()) {
                // 0 支出
                case 0:
                    mMoneyOutTv.setText(String.valueOf(userBookkeepingBeansBean.getMoney() + Float.parseFloat(mMoneyOutTv.getText().toString())));
                    break;
                // 1 收入
                case 1:
                    mMoneyInTv.setText(String.valueOf(userBookkeepingBeansBean.getMoney() + Float.parseFloat(mMoneyInTv.getText().toString())));
                    break;
            }

            // 不为空表示当月有记录数据
            if (mBookkeepingAdapter != null) {
                // 查询当前记录的是否是之前存在
                int containsPosition = -1;
                for (int i = 0; i < mBookkeepingAdapter.getAll().size(); i++) {
                    if (mBookkeepingAdapter.getAll().get(i).getExactTimes().contains(userBookkeepingBeansBean.getExactTime())) {
                        containsPosition = i;
                        break;
                    }
                }
                // containsPosition 大于 -1 表示之前存在该日期的数据
                if (containsPosition > -1) {
                    // 当月有记账记录，查询某日的数据
                    BookkeepingAllBean.DayDataBean oldDayDataBean = mBookkeepingAdapter.getItem(containsPosition);
                    // 设置总金额
                    if (userBookkeepingBeansBean.getMoneyType() == 0) {
                        oldDayDataBean.setAllOut(oldDayDataBean.getAllOut() + userBookkeepingBeansBean.getMoney());
                    } else {
                        oldDayDataBean.setAllIn(oldDayDataBean.getAllIn() + userBookkeepingBeansBean.getMoney());
                    }
                    // 添加需要更新的数据到已有的数据中
                    oldDayDataBean.getUserBookkeepingBeans().add(0,userBookkeepingBeansBean);
                    // 更新数据
                    mBookkeepingAdapter.notifyItemChanged(containsPosition);
                } else {
                    // 记录插入的位置
                    int insertPosition = -1;
                    // 之前没有存在该日期的数据
                    String maxDate = mBookkeepingAdapter.getItem(0).getExactTimes();
                    int maxIndexOf = maxDate.lastIndexOf("-");
                    // 获取当前记录的日期
                    String currentDate = userBookkeepingBeansBean.getExactTime();
                    int currentIndexOf = currentDate.lastIndexOf("-");
                    // 获取已经记录了的最大日期
                    String max = maxDate.substring(maxIndexOf + 1);
                    String current = currentDate.substring(currentIndexOf + 1);

                    // 当前记录的日期大于最大的记录时间表示记录的是当月最新的一天。
                    if (Integer.parseInt(current) > Integer.parseInt(max)) {
                        insertPosition = 0;
                    } else {
                        // 当前记录的日期小于最大的记录时间表示是补录的数据，应该插入到数据中间
                        for (int i = 1; i < mBookkeepingAdapter.getAll().size(); i++) {
                            String exactTimes = mBookkeepingAdapter.getAll().get(i).getExactTimes();
                            int indexOf = exactTimes.lastIndexOf("-");
                            String str = exactTimes.substring(indexOf + 1);
                            if (Integer.parseInt(current) > Integer.parseInt(str)) {
                                insertPosition = i;
                                break;
                            }
                        }
                    }
                    // 设置当天的详细数据
                    BookkeepingAllBean.DayDataBean newDayDataBean = new BookkeepingAllBean.DayDataBean();
                    // 设置总金额
                    if (userBookkeepingBeansBean.getMoneyType() == 0) {
                        newDayDataBean.setAllOut(userBookkeepingBeansBean.getMoney());
                    } else {
                        newDayDataBean.setAllIn(userBookkeepingBeansBean.getMoney());
                    }
                    // 设置插入的时间
                    newDayDataBean.setExactTimes(userBookkeepingBeansBean.getExactTime());
                    // 设置当天详细的记录数据
                    List<BookkeepingAllBean.DayDataBean.UserBookkeepingBeansBean> userBookkeepingBeansBeanList = new ArrayList<>();
                    userBookkeepingBeansBeanList.add(userBookkeepingBeansBean);
                    newDayDataBean.setUserBookkeepingBeans(userBookkeepingBeansBeanList);
                    mBookkeepingAdapter.add(insertPosition, newDayDataBean);
                }
            } else {
                // 表示当月没有记账记录
                mEmptyHintTv.setVisibility(View.GONE);

                List<BookkeepingAllBean.DayDataBean> dayDataBeanList = new ArrayList<>();
                BookkeepingAllBean.DayDataBean dayDataBean = new BookkeepingAllBean.DayDataBean();
                // 设置总金额
                if (userBookkeepingBeansBean.getMoneyType() == 0) {
                    dayDataBean.setAllOut(userBookkeepingBeansBean.getMoney());
                } else {
                    dayDataBean.setAllIn(userBookkeepingBeansBean.getMoney());
                }
                // 设置添加的时间
                dayDataBean.setExactTimes(userBookkeepingBeansBean.getExactTime());

                List<BookkeepingAllBean.DayDataBean.UserBookkeepingBeansBean> userBookkeepingBeansBeanList = new ArrayList<>();

                userBookkeepingBeansBeanList.add(userBookkeepingBeansBean);

                dayDataBean.setUserBookkeepingBeans(userBookkeepingBeansBeanList);

                dayDataBeanList.add(dayDataBean);

                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mBookkeepingAdapter = new BookkeepingAdapter(getActivity(), dayDataBeanList);
                mRecyclerView.setAdapter(mBookkeepingAdapter);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
