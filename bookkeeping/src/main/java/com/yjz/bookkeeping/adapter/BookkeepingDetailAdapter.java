package com.yjz.bookkeeping.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.seabig.common.base.BaseRecyclerAdapter;
import com.seabig.common.util.LogUtils;
import com.seabig.common.util.ResourceUtils;
import com.yjz.bookkeeping.R;
import com.yjz.bookkeeping.bean.BookkeepingAllBean;

import java.util.List;

/**
 * author： YJZ
 * date:  2019/2/25
 * des:
 */

public class BookkeepingDetailAdapter extends BaseRecyclerAdapter<BookkeepingAllBean.DayDataBean.UserBookkeepingBeansBean> {

    public BookkeepingDetailAdapter(@NonNull Context context, List<BookkeepingAllBean.DayDataBean.UserBookkeepingBeansBean> data) {
        super(context, R.layout.bookkeeping_adapter_bookkeeping_detail, data,true);
    }

    @Override
    public void convert(ViewHolder holder, BookkeepingAllBean.DayDataBean.UserBookkeepingBeansBean userBookkeepingBeansBean, int position) {
        ImageView typeIcon = (ImageView) holder.getView(R.id.type_icon);
        holder.setText(R.id.detail,userBookkeepingBeansBean.getContent());
        Drawable drawable;
        LogUtils.e("id = " + userBookkeepingBeansBean.getClassificationId());
        // 0 支出 1 收入
        if (userBookkeepingBeansBean.getMoneyType() == 0){
            holder.setText(R.id.money,"-" + userBookkeepingBeansBean.getMoney());
            int resId = userBookkeepingBeansBean.getClassificationId() - 1;
            LogUtils.e("0 支出 resID = " + resId);
            drawable = ContextCompat.getDrawable(mContext, ResourceUtils.getImageResIdByName(mContext, "bookkeeping_out_type_select_" + Math.abs(resId), "drawable"));
        }else {
            holder.setText(R.id.money, String.valueOf(userBookkeepingBeansBean.getMoney()));
            // 收入typeIcon索引需要减去支出typeIcon的索引
            int resId = userBookkeepingBeansBean.getClassificationId() - 19;
            LogUtils.e("1 收入 resID = " + resId);
            drawable = ContextCompat.getDrawable(mContext, ResourceUtils.getImageResIdByName(mContext, "bookkeeping_in_type_select_" + resId , "drawable"));
        }

        typeIcon.setImageDrawable(drawable);
    }
}
