package com.seabig.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.seabig.common.R;
import com.seabig.common.util.ToastUtils;

/**
 * <pre>
 *     author: YJZ
 *     time  : 2017/3/13  15:46
 *     desc  : 封装了开始加载以及加载错误和加载时数据为空的处理,如果不想使用内置的则Fragment直接
 *             继承BaseFragment即可
 * </pre>
 */
public abstract class ProgressBaseActivity extends AppCompatActivity implements BaseView {

    private FrameLayout mRootView;
    private FrameLayout mContentView;
    private TextView mErrorTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mRootView = (FrameLayout) LayoutInflater.from(this).inflate(R.layout.common_activity_progress, null, false);
        mRootView.findViewById(R.id.progress_view);
        mContentView = (FrameLayout) mRootView.findViewById(R.id.content_view);
        mRootView.findViewById(R.id.empty_view);
        mErrorTv = (TextView) mRootView.findViewById(R.id.error_tv);
        mErrorTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                onEmptyViewClick();
            }
        });
        mContentView.addView(LayoutInflater.from(this).inflate(onSettingUpContentViewResourceID(), mContentView, false));
        setContentView(mRootView);
        onSettingUpView();
        onSettingUpData();
    }

    protected void onSettingUpData()
    {
    }

    protected abstract int onSettingUpContentViewResourceID();

    protected abstract void onSettingUpView();

    @Override
    public void showLoadingDialog()
    {
        showProgressDialog(R.id.progress_view);
    }

    @Override
    public void showErrorDialog(String msg)
    {
        showProgressDialog(R.id.empty_view);
        mErrorTv.setText(msg);
    }

    @Override
    public void dismissDialog()
    {
        // 显示 内容
        showContentView();
    }

    public void showContentView()
    {
        showProgressDialog(R.id.content_view);
    }

    public void showProgressDialog(int resId)
    {
        // 遍历布局下的所有view
        for (int i = 0; i < mRootView.getChildCount(); i++)
        {
            // 如果子view 等于当前view则进行显示
            if (mRootView.getChildAt(i).getId() == resId)
            {
                mRootView.getChildAt(i).setVisibility(View.VISIBLE);
            } else
            {
                mRootView.getChildAt(i).setVisibility(View.GONE);
            }
        }
    }

    protected void showToast(String msg)
    {
        ToastUtils.getInstance().showToast(this, msg);
    }

    // 子类实现
    protected void onEmptyViewClick()
    {

    }

    public Toolbar initToolbar(int id, int titleId, int titleString) {
        Toolbar toolbar = (Toolbar) findViewById(id);
//        toolbar.setTitle("");
        TextView textView = (TextView) findViewById(titleId);
        textView.setText(titleString);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        return toolbar;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }
}
