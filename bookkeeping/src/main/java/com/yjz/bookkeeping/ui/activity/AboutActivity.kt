package com.yjz.bookkeeping.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View

import com.seabig.common.base.BaseActivity
import com.seabig.common.datamgr.AppConstant
import com.seabig.common.ui.activity.WebViewActivity
import com.seabig.common.util.StringUtils
import com.seabig.common.util.SystemUtils
import com.yjz.bookkeeping.R

/**
 * author： YJZ
 * date:  2018/11/22
 * des: 关于
 */

class AboutActivity : BaseActivity(), View.OnClickListener {

    override fun onSettingUpContentViewResourceID(): Int {
        return R.layout.bookkeeping_activity_about
    }

    override fun onSettingFullScreenAndStatusText(): Boolean {
        return true
    }

    override fun onSettingUpView() {
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        initToolBar(toolbar, "关于")
        findViewById(R.id.btn_open_source_url).setOnClickListener(this)
        findViewById(R.id.btn_about_author).setOnClickListener(this)
        findViewById(R.id.btn_advice_feedback).setOnClickListener(this)
        findViewById(R.id.btn_open_source_license).setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val i = v.id
        when (i) {
            R.id.btn_open_source_url -> {
                val bundle = Bundle()
                bundle.putString(AppConstant.WEB_VIEW_TITLE, "AndroidAndYang")
                bundle.putString(AppConstant.WEB_VIEW_URL, getStringByResId(R.string.open_source_url_content))
                val intent = Intent(this, WebViewActivity::class.java)
                intent.putExtra("bundle", bundle)
                startActivity(intent)
            }
            R.id.btn_about_author -> {
                val bundle = Bundle()
                bundle.putString(AppConstant.WEB_VIEW_TITLE, "AndroidAndYang")
                bundle.putString(AppConstant.WEB_VIEW_URL, getStringByResId(R.string.about_author_content))
                val intent = Intent(this, WebViewActivity::class.java)
                intent.putExtra("bundle", bundle)
                startActivity(intent)
            }
            R.id.btn_advice_feedback -> SystemUtils.openEmail(this,
                    "yjz0607@gmail.com",
                    "来自 AndroidStudy-\$version 的客户端反馈",
                    StringUtils.buffer("设备信息：Android ", Build.VERSION.RELEASE, Build.MANUFACTURER, " - ", Build.MODEL))
            R.id.btn_open_source_license -> startActivity(Intent(this, LicenseActivity::class.java))
        }
    }
}
