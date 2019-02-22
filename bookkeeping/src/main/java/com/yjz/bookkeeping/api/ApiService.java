package com.yjz.bookkeeping.api;


import com.seabig.common.bean.BaseBean;
import com.yjz.bookkeeping.bean.BookkeepingAllBean;
import com.yjz.bookkeeping.bean.BookkeepingBean;

import org.greenrobot.greendao.annotation.Entity;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author YJZ
 *         date 2018/12/13
 *         description: 记账本接口
 */
public interface ApiService {

    /**
     * 记账本首页数据
     *
     * @param userId       用户id
     * @param bookType     记账本类型
     * @param yearAndMonth 记账的年月
     * @return BookkeepingAllBean data
     */
    @POST("api/bookkeeping/month_list")
    Observable<BaseBean<BookkeepingAllBean>> getBookkeepingData(@Query("userId") Long userId,
                                                                @Query("bookType") Long bookType,
                                                                @Query("yearAndMonth") String yearAndMonth);

    /**
     * 提交记账数据
     */
    @POST("api/bookkeeping/add")
    Observable<BaseBean<Boolean>> commitBookkeepingData(
            @Query("as") String as,
            @Body BookkeepingBean bookkeepingBean);
}


