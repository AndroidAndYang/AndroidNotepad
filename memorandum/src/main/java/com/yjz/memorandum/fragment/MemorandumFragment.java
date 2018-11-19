package com.yjz.memorandum.fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.seabig.common.base.ProgressBaseFragment;
import com.yjz.memorandum.R;

/**
 * author： YJZ
 * date:  2018/11/19
 * des:
 */

@Route(path = "/memorandum/fragment/memorandum")
public class MemorandumFragment extends ProgressBaseFragment {

    @Override
    protected int onSettingUpContentViewResourceID() {
        return R.layout.fragment_memorandum;
    }

    @Override
    protected void onSettingUpView() {

    }
}
