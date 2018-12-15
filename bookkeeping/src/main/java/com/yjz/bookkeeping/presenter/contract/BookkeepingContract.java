package com.yjz.bookkeeping.presenter.contract;

import com.seabig.common.base.BaseView;
import com.yjz.bookkeeping.bean.BookkeepingBean;

/**
 * @author YJZ
 * date 2018/12/13
 * description: 首页记账本
 */
public interface BookkeepingContract {

    interface View extends BaseView {
        void setBookkeepingData(BookkeepingBean dataBean);
    }
}
