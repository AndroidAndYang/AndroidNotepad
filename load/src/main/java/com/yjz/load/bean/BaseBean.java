package com.yjz.load.bean;

/**
 * author： YJZ
 * date:  2018/10/17
 * des:
 */

public class BaseBean {

    private boolean status;
    private String message;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
