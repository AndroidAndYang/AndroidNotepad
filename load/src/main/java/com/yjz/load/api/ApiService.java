package com.yjz.load.api;

import com.yjz.load.bean.BaseBean;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * author： YJZ
 * date:  2018/10/17
 * des: Api
 */

public interface ApiService {

    /*
            当使用post提交的时候，如果用的是@Field 则需要在加上@FormUrlEncoded注解
        不然会出现@Field parameters can only be used with form encoding异常。
     */

    // 注册
    // @FormUrlEncoded
    @POST("index.php?m=user&a=savePost")
    Observable<BaseBean> getRegisterBean(@Query("mobile") String mobile,
                                         @Query("msgcode") String msgcode,
                                         @Query("password") String password);
}
