package com.yjz.bookkeeping.ui.api;


import com.seabig.common.bean.BaseBean;
import com.yjz.bookkeeping.ui.bean.BookkeepingBean;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author YJZ
 * date 2018/12/13
 * description: 记账本接口
 */
public interface ApiService {

    /**
     * 记账本首页数据
     * @param userId 用户id
     * @param bookType 记账本类型
     * @param yearAndMonth 记账的年月
     * @return BookkeepingBean data
     */
    @POST ("api/bookkeeping/month_list")
    Observable<BaseBean<BookkeepingBean>> getBookkeepingData(@Query ("userId") Long userId,
                                                             @Query ("bookType") Long bookType,
                                                             @Query ("yearAndMonth") String yearAndMonth);

}
