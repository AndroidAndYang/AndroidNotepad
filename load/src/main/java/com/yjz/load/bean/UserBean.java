package com.yjz.load.bean;

/**
 * author： YJZ
 * date:  2018/10/25
 * des:
 */

public class UserBean {

    /**
     * id : 1
     * username : YJZ
     * phone : 17621859608
     * register_time : 1539162373000
     */

    private long id;
    private String username;
    private String phone;
    private long register_time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getRegister_time() {
        return register_time;
    }

    public void setRegister_time(long register_time) {
        this.register_time = register_time;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", register_time=" + register_time +
                '}';
    }
}
