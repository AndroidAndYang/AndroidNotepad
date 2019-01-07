package com.yjz.bookkeeping.ui.fragment;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.seabig.common.base.BaseRecyclerAdapter;
import com.seabig.common.base.DelayLoadFragment;
import com.seabig.common.datamgr.AppConstant;
import com.seabig.common.util.MoneyTextWatcher;
import com.seabig.common.util.ResourceUtils;
import com.seabig.common.util.SPUtils;
import com.yjz.bookkeeping.BookkeepingApplication;
import com.yjz.bookkeeping.R;
import com.yjz.bookkeeping.adapter.BookkeepingEditAdapter;
import com.yjz.bookkeeping.datamgr.BookkeepingType;
import com.yjz.bookkeeping.db.Type;
import com.yjz.bookkeeping.db.TypeDao;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * author： YJZ
 * date:  2018/12/17
 * des: 账目编辑页
 */

public class BookkeepingEditInFragment extends DelayLoadFragment {

    private RecyclerView mRecyclerView;
    private TextView mTypeNameTv;
    private ImageView mTypeImg;
    private EditText mMoneyEdt;
    private int clickPosition;
    private EditText mNoteEdt;
    private TextView mTimeTv;

    @Override
    protected int onSettingUpContentViewResourceID() {
        return R.layout.bookkeeping_fragment_edit_in;
    }

    @Override
    protected void onSettingUpView() {
        mTypeNameTv = findViewById(R.id.type_name);
        mTypeImg = findViewById(R.id.type_img);
        mMoneyEdt = findViewById(R.id.money_tv);
        mNoteEdt = findViewById(R.id.edt_note);
        mRecyclerView = findViewById(R.id.recycler_view);
        mTimeTv = findViewById(R.id.time);
        //默认两位小数
        mMoneyEdt.addTextChangedListener(new MoneyTextWatcher(mMoneyEdt));
    }

    @Override
    protected void onSettingUpData() {
        final TypeDao typeDao = BookkeepingApplication.getInstance().getSession().getTypeDao();
        Long userId = (Long) SPUtils.get(getActivity(), AppConstant.USER_ID, 0L);

        //获取系统的日期
        Calendar calendar = Calendar.getInstance();
        // 月
        int month = calendar.get(Calendar.MONTH)+1;
        // 日
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        mTimeTv.setText(String.format(Locale.CHINA,"%s月%s日",month,day));

        List<Type> typeList = typeDao.queryBuilder()
                .where(TypeDao.Properties.Uid.eq(userId), TypeDao.Properties.Type.eq(BookkeepingType.TYPE_INCOME))
                .orderAsc(TypeDao.Properties.Index)
                .list();

        final BookkeepingEditAdapter adapter = new BookkeepingEditAdapter(getActivity(), typeList,"bookkeeping_in_type_select_");
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mRecyclerView.setNestedScrollingEnabled(false);
        adapter.setOnRecyclerViewItemClickListen(new BaseRecyclerAdapter.OnRecyclerViewItemClickListen() {
            @Override
            public void onItemClickListen(View view, int position) {
                clickPosition = position;
                adapter.setClickPosition(position);
                adapter.notifyDataSetChanged();

                mTypeImg.setImageDrawable(ContextCompat.getDrawable(getActivity(), ResourceUtils.getImageResIdByName(getActivity(), "bookkeeping_in_type_select_" + position, "drawable")));
                mTypeNameTv.setText(adapter.getItem(position).getName());
            }
        });
        mRecyclerView.setAdapter(adapter);
    }

    public void post() {
        String typeNameStr = mTypeNameTv.getText().toString();
        String moneyStr = mMoneyEdt.getText().toString();
        String noteStr = mNoteEdt.getText().toString();
        showToast("typeName = " + typeNameStr + " money = " + moneyStr + " noteStr = " + noteStr);
    }

}
