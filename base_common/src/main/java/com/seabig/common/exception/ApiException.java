package com.seabig.common.exception;

import com.seabig.common.base.BaseException;

/**
 * author： YJZ
 * date:  2018/10/17
 * des: 自定义异常处理类
 */

public class ApiException extends BaseException {
    public ApiException(int code, String displayMsg) {
        super(code, displayMsg);
    }
}
