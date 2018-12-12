package com.yjz.bookkeeping.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * author： YJZ
 * date:  2018/11/27
 * des:
 */

public class BookkeepingAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public BookkeepingAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String s) {

    }
}
