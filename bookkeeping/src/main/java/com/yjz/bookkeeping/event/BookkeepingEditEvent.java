package com.yjz.bookkeeping.event;

import com.yjz.bookkeeping.bean.BookkeepingAllBean;

/**
 * author： YJZ
 * date:  2019/2/22
 * des: 记账数据添加时的更新
 */

public class BookkeepingEditEvent {

    private BookkeepingAllBean.DayDataBean.UserBookkeepingBeansBean userBookkeepingBeansBean;

    public BookkeepingEditEvent(BookkeepingAllBean.DayDataBean.UserBookkeepingBeansBean userBookkeepingBeansBean) {
        this.userBookkeepingBeansBean = userBookkeepingBeansBean;
    }

    public BookkeepingAllBean.DayDataBean.UserBookkeepingBeansBean getUserBookkeepingBeansBean() {
        return userBookkeepingBeansBean;
    }
}
