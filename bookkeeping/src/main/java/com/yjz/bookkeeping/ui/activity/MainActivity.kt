package com.yjz.bookkeeping.ui.activity

import com.seabig.common.base.BaseActivity
import com.yjz.bookkeeping.R
import com.yjz.bookkeeping.ui.fragment.BookkeepingFragment

/**
 * author： YJZ
 * date:  2018/11/26
 * des:  首页
 */

class MainActivity : BaseActivity() {

    override fun onSettingUpContentViewResourceID(): Int {
        return R.layout.bookkeeping_fragment_main
    }

    override fun onSettingUpView() {
        supportFragmentManager
                .beginTransaction()
                // 此处的R.id.fragment_container是要盛放fragment的父容器
                .add(R.id.fragment_container, BookkeepingFragment())
                .commit()
    }
}
