package com.yjz.bookkeeping.bean;


import java.io.Serializable;

/**
 * author： YJZ
 * date:  2019/2/21
 * des:  添加记账数据bean
 */

public class BookkeepingBean implements Serializable{

    private Long id;
    // 用户ID
    private Long userId;
    // 账本类型
    private Long bookTypeId;
    // 记账类型（例餐饮，出行）
    private Long classificationId;
    // 支出還是收入
    private Long moneyType;
    // 内容
    private String content;
    // 金额
    private float money;
    // 年月数据
    private String addTime;
    // 记账详细时间
    private String exactAddTime;

    public BookkeepingBean() {
    }

    public BookkeepingBean(Long id, Long userId, Long bookTypeId, Long classificationId, Long moneyType, String content, float money, String addTime, String exactAddTime) {
        this.id = id;
        this.userId = userId;
        this.bookTypeId = bookTypeId;
        this.classificationId = classificationId;
        this.moneyType = moneyType;
        this.content = content;
        this.money = money;
        this.addTime = addTime;
        this.exactAddTime = exactAddTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookTypeId() {
        return bookTypeId;
    }

    public void setBookTypeId(Long bookTypeId) {
        this.bookTypeId = bookTypeId;
    }

    public Long getClassificationId() {
        return classificationId;
    }

    public void setClassificationId(Long classificationId) {
        this.classificationId = classificationId;
    }

    public Long getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(Long moneyType) {
        this.moneyType = moneyType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getExactAddTime() {
        return exactAddTime;
    }

    public void setExactAddTime(String exactAddTime) {
        this.exactAddTime = exactAddTime;
    }

    @Override
    public String toString() {
        return "BookkeepingBean{" +
                "userId=" + userId +
                ", bookTypeId=" + bookTypeId +
                ", classificationId=" + classificationId +
                ", moneyType=" + moneyType +
                ", content='" + content + '\'' +
                ", money=" + money +
                ", addTime='" + addTime + '\'' +
                ", exactAddTime=" + exactAddTime +
                '}';
    }
}
