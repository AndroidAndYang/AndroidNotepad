package com.yjz.bookkeeping.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seabig.common.util.DateUtils;
import com.yjz.bookkeeping.R;
import com.yjz.bookkeeping.ui.bean.BookkeepingBean;

import java.util.List;
import java.util.Locale;

/**
 * author： YJZ
 * date:  2018/11/27
 * des:
 */

public class BookkeepingAdapter extends BaseQuickAdapter<BookkeepingBean.DayDataBean, BaseViewHolder> {

    public BookkeepingAdapter(@Nullable List<BookkeepingBean.DayDataBean> data)
    {
        super(R.layout.bookkeeping_adapter_bookkeeping, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, BookkeepingBean.DayDataBean dayDataBean)
    {
//        RecyclerView recyclerView = baseViewHolder.getView(R.id.recycler_view);
        baseViewHolder.setText(R.id.week, String.format("%s %s", dayDataBean.getExactTimes(), DateUtils.getWeek(dayDataBean.getExactTimes())));
        baseViewHolder.setText(R.id.money_detail, String.format(Locale.CHINA, "收入: %.2f 支出: %.2f", dayDataBean.getAllOut(), dayDataBean.getAllIn()));
    }
}
