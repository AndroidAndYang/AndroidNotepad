package com.seabig.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

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
public abstract class ProgressBaseFragment extends Fragment implements View.OnClickListener, BaseView {

    private FrameLayout mRootView;
    private FrameLayout mContentView;
    private TextView mErrorTv;
    private View contentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = (FrameLayout) inflater.inflate(R.layout.common_fragment_progress, container, false);
        mRootView.findViewById(R.id.progress_view);
        mContentView = (FrameLayout) mRootView.findViewById(R.id.content_view);
        mRootView.findViewById(R.id.empty_view);
        mErrorTv = (TextView) mRootView.findViewById(R.id.error_tv);
        mErrorTv.setOnClickListener(this);
        onPrepareOpt();
        return mRootView;
    }

    protected void onPrepareOpt() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 加载 内容
        setRealContentView();
        onSettingUpView();
    }

    private void setRealContentView() {
        /**
         *  为true表示将定义的试图加载到mContentView中
         */
        contentView = LayoutInflater.from(getActivity()).inflate(onSettingUpContentViewResourceID(), mContentView, true);
    }

    protected abstract int onSettingUpContentViewResourceID();

    protected abstract void onSettingUpView();


    @Override
    public void showLoadingDialog() {
        showDialog(R.id.progress_view);
    }

    @Override
    public void showErrorDialog(String msg) {
        showDialog(R.id.empty_view);
        mErrorTv.setText(msg);
    }

    @Override
    public void dismissDialog() {
        // 显示 内容
        showContentView();
    }

    private void showContentView() {
        showDialog(R.id.content_view);
    }

    private void showDialog(int resId) {
        // 遍历布局下的所有view
        for (int i = 0; i < mRootView.getChildCount(); i++) {
            // 如果子view 等于当前view则进行显示
            if (mRootView.getChildAt(i).getId() == resId) {
                mRootView.getChildAt(i).setVisibility(View.VISIBLE);
            } else {
                mRootView.getChildAt(i).setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.error_tv) {// 显示错误 重现请求网络
            onEmptyViewClick();
        }
    }

    /**
     * 重载来替代findViewById
     *
     * @param id  控件ID
     * @param <T> View
     * @return 具体的控件
     */
    protected <T extends View> T findViewById(int id) {
        return (T) contentView.findViewById(id);
    }

    protected void showToast(String msg) {
        ToastUtils.getInstance().showToast(getActivity(), msg);
    }

    // 子类实现
    protected void onEmptyViewClick() {

    }

    public Toolbar initToolbar(int id, int titleId, int titleString) {
        Toolbar toolbar = findViewById(id);
        TextView textView = findViewById(titleId);
        textView.setText(titleString);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        return toolbar;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
