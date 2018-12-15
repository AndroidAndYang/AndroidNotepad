package com.yjz.load.api;

import com.seabig.common.bean.BaseBean;
import com.yjz.load.bean.UserBean;

import java.util.Date;
import java.util.List;

import retrofit2.http.GET;
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

    /**
     * 注册
     *
     * @param phone    phone
     * @param password pwd
     * @return userId
     */
    // @FormUrlEncoded
    @POST ("api/user/loginSuccess")
    Observable<BaseBean<Long>> getRegisterBean(@Query ("phone") String phone,
                                               @Query ("password") String password);

    /**
     * 登录
     *
     * @param phone    phone
     * @param password pwd
     * @return userID
     */
    @POST ("api/user/login")
    Observable<BaseBean<Long>> getLoginBean(@Query ("phone") String phone,
                                            @Query ("password") String password);

}
