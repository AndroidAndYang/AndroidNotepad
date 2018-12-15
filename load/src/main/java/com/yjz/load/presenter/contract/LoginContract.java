package com.yjz.load.presenter.contract;

import com.seabig.common.base.BaseView;

/**
 * author： YJZ
 * date:  2018/10/17
 * des:
 */

public interface LoginContract {

    interface View extends BaseView {
        /**
         * 登录成功
         *
         * @param userID 用户id
         */
        void loginSuccess(Long userID);
    }
}
