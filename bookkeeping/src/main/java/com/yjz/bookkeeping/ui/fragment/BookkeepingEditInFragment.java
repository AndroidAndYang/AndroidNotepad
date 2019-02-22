package com.yjz.bookkeeping.ui.fragment;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.seabig.common.base.BaseRecyclerAdapter;
import com.seabig.common.base.DelayLoadFragment;
import com.seabig.common.datamgr.AppConstant;
import com.seabig.common.util.LogUtils;
import com.seabig.common.util.MoneyTextWatcher;
import com.seabig.common.util.ResourceUtils;
import com.seabig.common.util.SPUtils;
import com.yjz.bookkeeping.BookkeepingApplication;
import com.yjz.bookkeeping.R;
import com.yjz.bookkeeping.adapter.BookkeepingEditAdapter;
import com.yjz.bookkeeping.bean.BookkeepingAllBean;
import com.yjz.bookkeeping.bean.BookkeepingBean;
import com.yjz.bookkeeping.datamgr.BookkeepingType;
import com.yjz.bookkeeping.db.Type;
import com.yjz.bookkeeping.db.TypeDao;
import com.yjz.bookkeeping.event.BookkeepingEditEvent;
import com.yjz.bookkeeping.presenter.BookkeepingCommitPresenter;
import com.yjz.bookkeeping.presenter.contract.BookkeepingCommitContract;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * author： YJZ
 * date:  2018/12/17
 * des: 账目编辑页
 */

public class BookkeepingEditInFragment extends DelayLoadFragment implements BookkeepingCommitContract.View {

    private RecyclerView mRecyclerView;
    private TextView mTypeNameTv;
    private ImageView mTypeImg;
    private EditText mMoneyEdt;
    private int clickPosition;
    private EditText mNoteEdt;
    private TextView mTimeTv;
    private BookkeepingCommitPresenter commitPresenter;

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
        int month = calendar.get(Calendar.MONTH) + 1;
        // 日
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        mTimeTv.setText(String.format(Locale.CHINA, "%s月%s日", month, day));

        List<Type> typeList = typeDao.queryBuilder()
                .where(TypeDao.Properties.Uid.eq(userId), TypeDao.Properties.Type.eq(BookkeepingType.TYPE_INCOME))
                .orderAsc(TypeDao.Properties.Index)
                .list();

        final BookkeepingEditAdapter adapter = new BookkeepingEditAdapter(getActivity(), typeList, "bookkeeping_in_type_select_");
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

    /**
     * 提交收入数据到服务器
     */
    public void post() {
        String typeNameStr = mTypeNameTv.getText().toString();
        String moneyStr = mMoneyEdt.getText().toString();
        String noteStr = mNoteEdt.getText().toString();

        if (TextUtils.isEmpty(moneyStr)){
            showToast("请输入金额");
            return;
        }

        if (commitPresenter == null) {
            commitPresenter = new BookkeepingCommitPresenter(getActivity(), this);
        }

        BookkeepingBean bookkeepingBean = new BookkeepingBean();

        Calendar calendar = Calendar.getInstance();
        int yearStr = calendar.get(Calendar.YEAR);//获取年份
        int month = calendar.get(Calendar.MONTH) + 1;//获取月份

        bookkeepingBean.setUserId((Long) SPUtils.get(getActivity(), AppConstant.USER_ID, 0L));
        bookkeepingBean.setBookTypeId(1L);
        // 18 为支出type的内容长度
        bookkeepingBean.setClassificationId((long) (18 + clickPosition + 1));
        bookkeepingBean.setMoneyType(1L);
        bookkeepingBean.setContent(TextUtils.isEmpty(noteStr) ? typeNameStr : noteStr);
        bookkeepingBean.setMoney(Float.parseFloat(moneyStr));
        bookkeepingBean.setAddTime(String.format(Locale.CHINA, "%s-%s", yearStr, month));

        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        bookkeepingBean.setExactAddTime(formatDate.format(new Date()));

        commitPresenter.commit(bookkeepingBean);
    }

    @Override
    public void onSuccess(BookkeepingBean bookkeepingBean,boolean isSuccess) {
        showToast(isSuccess ? "新增成功" : "新增失败");
        if (isSuccess){
            BookkeepingAllBean.DayDataBean.UserBookkeepingBeansBean userBookkeepingBeansBean = new BookkeepingAllBean.DayDataBean.UserBookkeepingBeansBean();
            userBookkeepingBeansBean.setMoney(bookkeepingBean.getMoney());
            userBookkeepingBeansBean.setContent(bookkeepingBean.getContent());
            userBookkeepingBeansBean.setExactTime(bookkeepingBean.getExactAddTime());
            userBookkeepingBeansBean.setMoneyType(1);
            userBookkeepingBeansBean.setName("日常记账本");
            EventBus.getDefault().post(new BookkeepingEditEvent(userBookkeepingBeansBean));
            commitPresenter = null;
            (getActivity()).finish();
        }
    }
}
