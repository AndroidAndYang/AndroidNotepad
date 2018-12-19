package com.yjz.bookkeeping.ui.fragment;

import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.seabig.common.base.AbstractDelayLoadFragment;
import com.seabig.common.base.BaseRecyclerAdapter;
import com.seabig.common.datamgr.AppConstant;
import com.seabig.common.ui.widget.dialogfragment.BaseDialogFragment;
import com.seabig.common.ui.widget.dialogfragment.ViewConvertListener;
import com.seabig.common.ui.widget.dialogfragment.ViewHolder;
import com.seabig.common.util.SPUtils;
import com.yjz.bookkeeping.BookkeepingApplication;
import com.yjz.bookkeeping.R;
import com.yjz.bookkeeping.adapter.BookkeepingEditAdapter;
import com.yjz.bookkeeping.db.Type;
import com.yjz.bookkeeping.db.TypeDao;

import java.util.List;

/**
 * author： YJZ
 * date:  2018/12/17
 * des: 账目编辑页
 */

public class BookkeepingEditFragment extends AbstractDelayLoadFragment {

    private Long type = 0L;
    private RecyclerView mRecyclerView;

    public static BookkeepingEditFragment newInstance(Long type) {
        BookkeepingEditFragment fragment = new BookkeepingEditFragment();
        fragment.setType(type);
        return fragment;
    }

    @Override
    protected int onSettingUpContentViewResourceID() {
        return R.layout.bookkeeping_fragment_edit;
    }

    @Override
    protected void onSettingUpView() {
        dismissDialog();
        mRecyclerView = findViewById(R.id.recycler_view);
    }

    @Override
    protected void onSettingUpData() {
        TypeDao typeDao = BookkeepingApplication.getInstance().getSession().getTypeDao();
        Long userId = (Long) SPUtils.get(getActivity(), AppConstant.USER_ID, 0L);

        List<Type> typeList = typeDao.queryBuilder()
                .where(TypeDao.Properties.Uid.eq(userId), TypeDao.Properties.Type.eq(type))
                .orderAsc(TypeDao.Properties.Index)
                .list();

        final BookkeepingEditAdapter adapter = new BookkeepingEditAdapter(getActivity(), typeList);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mRecyclerView.setNestedScrollingEnabled(false);
        adapter.setOnRecyclerViewItemClickListen(new BaseRecyclerAdapter.OnRecyclerViewItemClickListen() {
            @Override
            public void onItemClickListen(View view, int position) {
                adapter.setClickPosition(position);
                adapter.notifyDataSetChanged();

                BaseDialogFragment.getInstance()
                        .setDialogLayout(R.layout.bookkeeping_dialog_edit)
                        .setConvertListener(new ViewConvertListener() {
                            @Override
                            public void convertView(ViewHolder holder, BaseDialogFragment dialog) {

                            }
                        }).isOutCancel(false)
                        .show(getChildFragmentManager());
            }
        });
        mRecyclerView.setAdapter(adapter);
    }

    public void setType(Long type) {
        this.type = type;
    }
}
