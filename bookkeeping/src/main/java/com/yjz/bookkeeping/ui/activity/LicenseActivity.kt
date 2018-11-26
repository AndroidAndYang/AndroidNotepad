package com.yjz.bookkeeping.ui.activity

import android.widget.TextView

import com.seabig.common.base.BaseActivity
import com.yjz.bookkeeping.R

/**
 * author： YJZ
 * date:  2018/11/22
 * des:   开发源代码许可
 */

class LicenseActivity : BaseActivity() {

    override fun onSettingUpContentViewResourceID(): Int {
        return R.layout.bookkeeping_activity_license
    }

    override fun onSettingUpView() {
        val textView = findViewById(R.id.tv_license) as TextView
        textView.text = BaseActivity.getRawString(this, R.raw.open_source)
    }
}
