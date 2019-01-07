package com.yjz.bookkeeping.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.seabig.common.base.BaseRecyclerAdapter;
import com.seabig.common.util.LogUtils;
import com.seabig.common.util.ResourceUtils;
import com.yjz.bookkeeping.R;
import com.yjz.bookkeeping.db.Type;

import java.util.List;

/**
 * author： YJZ
 * date:  2018/12/18
 * des:
 */

public class BookkeepingEditAdapter extends BaseRecyclerAdapter<Type> {

    private int clickPosition = -1;
    private String imgName;

    public int getClickPosition() {
        return clickPosition;
    }

    public void setClickPosition(int clickPosition) {
        this.clickPosition = clickPosition;
    }

    public BookkeepingEditAdapter(@NonNull Context context, List<Type> data,String imgName) {
        super(context, R.layout.bookkeeping_adapter_bookkeeping_edit, data, true);
        this.imgName=imgName;
    }

    @Override
    public void convert(ViewHolder holder, Type type, int position) {
        holder.setText(R.id.type_name, type.getName());
        ImageView imageView = (ImageView) holder.getView(R.id.type_icon);
        LogUtils.e("type icon " + type.getIcon());
        int drawable;
        if (getClickPosition() == position) {
            drawable = ResourceUtils.getImageResIdByName(mContext, imgName + clickPosition, "drawable");
        } else {
            drawable = ResourceUtils.getImageResIdByName(mContext, type.getIcon(), "drawable");
        }
        imageView.setImageDrawable(ContextCompat.getDrawable(mContext, drawable));
    }
}
