package com.yjz.bookkeeping.presenter.contract;

import com.yjz.bookkeeping.bean.BookkeepingBean;

/**
 * author： YJZ
 * date:  2019/2/21
 * des: 记收入
 */

public interface BookkeepingCommitContract {

    interface View {
        /**
         * 新增的记账数据
         * @param bookkeepingBean 收入数据
         * @param isSuccess 是否成功
         */
        void onSuccess(BookkeepingBean bookkeepingBean, boolean isSuccess);
    }
}
