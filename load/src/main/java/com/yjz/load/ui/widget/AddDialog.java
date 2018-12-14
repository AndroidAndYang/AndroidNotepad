package com.yjz.load.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yjz.load.R;

/**
 * author： YJZ
 * date:  2018/12/14
 * des: 记一记 弹出dialog
 */

public class AddDialog extends Dialog {

    private RelativeLayout mMainRl;
    private LinearLayout mBookkeepingLl;
    private LinearLayout mMemorandumLl;
    private ImageView mCloseImg;
    private Handler mHandler;

    private Context mContext;

    public AddDialog(@NonNull Context context) {
        this(context, R.style.home_add_dialog_style);
    }

    private AddDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getWindow() != null) {
            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            getWindow().setAttributes(params);
        }
    }

    private void init() {
        setContentView(R.layout.load_home_dialog_add);
        // 主布局
        mMainRl = (RelativeLayout) findViewById(R.id.home_main_add);
        // 记账本
        mBookkeepingLl = (LinearLayout) findViewById(R.id.bookkeeping_ll);
        // 备忘录
        mMemorandumLl = (LinearLayout) findViewById(R.id.memorandum_ll);
        // 退出按钮
        mCloseImg = (ImageView) findViewById(R.id.close);
        mHandler = new Handler();
    }

    @Override
    public void show() {
        super.show();
        showAddDialog();
    }

    /**
     * 显示Dialog
     */
    private void showAddDialog() {
        //首先把记账本，备忘录 控件设置为不可见
        mBookkeepingLl.setVisibility(View.INVISIBLE);
        mMemorandumLl.setVisibility(View.INVISIBLE);

        // 然后设置主布局的动画
        mMainRl.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.add_go_in));
        // 这里设置底部退出按钮的动画 这里是用了一个rotate动画
        mCloseImg.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.add_close_btn_open));
        // 底部按钮动画执行过之后把发布设置为可见
        mBookkeepingLl.setVisibility(View.VISIBLE);
        // 然后让他执行add_show_bottom_in动画这个动画里定义的是平移动画
        // 在这里设置之后如果你同时设置其他两个评估和回收动画着这三个动画会同时从屏幕的底部向上平移
        // 而我们想实现的效果是挨个向上平移这里 使用到了定时器handler开启一个线程定时100毫秒启动这个线程
        // 这样就可以达到挨个向上平移的效果
        mBookkeepingLl.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.add_show_bottom_in));

        // mHandler.postDelayed开启一个定时任务
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMemorandumLl.setVisibility(View.VISIBLE);
                mMemorandumLl.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.add_show_bottom_in));
                mCloseImg.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.close));
            }
        }, 100);
    }

    /**
     * 退出Dialog
     */
    public void closeDialog() {

        // 设置退出按钮从右向左旋转
        mCloseImg.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.add_close_btn_close));
        mCloseImg.setVisibility(View.INVISIBLE);

        // 这里设置了一个定时500毫秒的定时器来执行dismiss();来关闭Dialog 我们需要在500毫秒的时间内完成对控件动画的设置
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, 500);

        // 备忘录按钮先执行动画
        mMemorandumLl.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.add_show_bottom_out));
        mMemorandumLl.setVisibility(View.INVISIBLE);

        // 将其设置为不可见
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 然后设置发布从上向下平移动画
                mBookkeepingLl.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.add_show_bottom_out));
                mBookkeepingLl.setVisibility(View.INVISIBLE);
            }
        }, 100);

        mMainRl.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.add_go_out));
    }

    /**
     * 记账本 点击
     *
     * @param clickListener listener
     * @return this
     */
    public AddDialog setBookkeepingClickListener(View.OnClickListener clickListener) {
        mBookkeepingLl.setOnClickListener(clickListener);
        return this;
    }

    /**
     * 备忘录 点击
     *
     * @param clickListener listener
     * @return this
     */
    public AddDialog setMemorandumClickListener(View.OnClickListener clickListener) {
        mMemorandumLl.setOnClickListener(clickListener);
        return this;
    }

    /**
     * 关闭按钮点击
     *
     * @param clickListener listener
     */
    public void setCloseClickListener(View.OnClickListener clickListener) {
        mCloseImg.setOnClickListener(clickListener);
    }
}
