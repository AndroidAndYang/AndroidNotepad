package com.yjz.bookkeeping.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * author： YJZ
 * date:  2018/12/17
 * des:
 */

public class BookkeepingEditFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    private List<String> listTitle;

    public BookkeepingEditFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> listTitle) {
        super(fm);
        this.fragmentList = fragmentList;
        this.listTitle = listTitle;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return listTitle.size();
    }

    /**
     * 此方法用来显示tab上的名字
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return listTitle.get(position);
    }
}
