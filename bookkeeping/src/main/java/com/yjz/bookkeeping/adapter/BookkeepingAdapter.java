package com.yjz.bookkeeping.adapter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.seabig.common.base.BaseRecyclerAdapter;
import com.seabig.common.util.DateUtils;
import com.yjz.bookkeeping.R;
import com.yjz.bookkeeping.bean.BookkeepingBean;

import java.util.List;
import java.util.Locale;

/**
 * author： YJZ
 * date:  2018/11/27
 * des:
 */

public class BookkeepingAdapter extends BaseRecyclerAdapter<BookkeepingBean.DayDataBean> {


    public BookkeepingAdapter(@NonNull Context context, List<BookkeepingBean.DayDataBean> data) {
        super(context, R.layout.bookkeeping_adapter_bookkeeping, data, true);
    }

    @Override
    public void convert(ViewHolder holder, BookkeepingBean.DayDataBean dayDataBean, int position) {
        holder.setText(R.id.week, String.format("%s %s", dayDataBean.getExactTimes(), DateUtils.getWeek(dayDataBean.getExactTimes())));
        holder.setText(R.id.money_detail, String.format(Locale.CHINA, "收入: %.2f 支出: %.2f", dayDataBean.getAllOut(), dayDataBean.getAllIn()));
    }
}
