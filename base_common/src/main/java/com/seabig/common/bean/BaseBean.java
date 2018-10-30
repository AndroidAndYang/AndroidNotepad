package com.seabig.common.bean;

import java.io.Serializable;

/**
 * <pre>
 *     author: YJZ
 *     time  : 2017/3/12  10:20
 *     desc  :
 * </pre>
 */
public class BaseBean<T> implements Serializable {

    private int status;

    private String message;

    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
