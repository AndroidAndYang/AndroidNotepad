package com.yjz.bookkeeping.bean;

import java.util.List;

/**
 * @author YJZ
 * date 2018/12/13
 * description: 记账本首页数据
 */
public class BookkeepingAllBean {
    /**
     * allMonthOut : 2222.7
     * dayData : [{"allIn":2000,"allOut":0,"userBookkeepingBeans":[{"userId":2,"des":"餐饮","name":"日常记账本","moneyType":0,"content":"外快","money":1000,"exactTime":"2018-11-14"},{"userId":2,"des":"餐饮","name":"日常记账本","moneyType":0,"content":"外快3","money":1000,"exactTime":"2018-11-14"}]},{"allIn":1224.4,"allOut":2222.7,"userBookkeepingBeans":[{"userId":2,"des":"旅游","name":"日常记账本","moneyType":1,"content":"人情红包","money":1000,"exactTime":"2018-11-28"},{"userId":2,"des":"餐饮","name":"日常记账本","moneyType":0,"content":"外快1","money":1224.4,"exactTime":"2018-11-28"},{"userId":2,"des":"餐饮","name":"日常记账本","moneyType":1,"content":"支出2","money":1222.7,"exactTime":"2018-11-28"}]}]
     * allMonthIn : 3224.4
     */

    private double allMonthOut;
    private double allMonthIn;
    private List<DayDataBean> dayData;

    public double getAllMonthOut()
    {
        return allMonthOut;
    }

    public void setAllMonthOut(double allMonthOut)
    {
        this.allMonthOut = allMonthOut;
    }

    public double getAllMonthIn()
    {
        return allMonthIn;
    }

    public void setAllMonthIn(double allMonthIn)
    {
        this.allMonthIn = allMonthIn;
    }

    public List<DayDataBean> getDayData()
    {
        return dayData;
    }

    public void setDayData(List<DayDataBean> dayData)
    {
        this.dayData = dayData;
    }

    public static class DayDataBean {
        /**
         * allIn : 2000.0
         * allOut : 0.0
         * userBookkeepingBeans : [{"userId":2,"name":"日常记账本","moneyType":0,"content":"外快","money":1000,"exactTime":"2018-11-14"},{"userId":2,"des":"餐饮","name":"日常记账本","moneyType":0,"content":"外快3","money":1000,"exactTime":"2018-11-14"}]
         */

        private double allIn;
        private double allOut;
        private String exactTimes;
        private List<UserBookkeepingBeansBean> userBookkeepingBeans;

        public double getAllIn()
        {
            return allIn;
        }

        public void setAllIn(double allIn)
        {
            this.allIn = allIn;
        }

        public double getAllOut()
        {
            return allOut;
        }

        public void setAllOut(double allOut)
        {
            this.allOut = allOut;
        }

        public String getExactTimes()
        {
            return exactTimes;
        }

        public void setExactTimes(String exactTimes)
        {
            this.exactTimes = exactTimes;
        }

        public List<UserBookkeepingBeansBean> getUserBookkeepingBeans()
        {
            return userBookkeepingBeans;
        }

        public void setUserBookkeepingBeans(List<UserBookkeepingBeansBean> userBookkeepingBeans)
        {
            this.userBookkeepingBeans = userBookkeepingBeans;
        }

        public static class UserBookkeepingBeansBean {
            /**
             * userId : 2
             * name : 日常记账本
             * moneyType : 0
             * content : 外快
             * money : 1000.0
             * exactTime : 2018-11-14
             */

            private int userId;
            private String name;
            private int moneyType;
            private String content;
            private double money;
            private String exactTime;

            public int getUserId()
            {
                return userId;
            }

            public void setUserId(int userId)
            {
                this.userId = userId;
            }

            public String getName()
            {
                return name;
            }

            public void setName(String name)
            {
                this.name = name;
            }

            public int getMoneyType()
            {
                return moneyType;
            }

            public void setMoneyType(int moneyType)
            {
                this.moneyType = moneyType;
            }

            public String getContent()
            {
                return content;
            }

            public void setContent(String content)
            {
                this.content = content;
            }

            public double getMoney()
            {
                return money;
            }

            public void setMoney(double money)
            {
                this.money = money;
            }

            public String getExactTime()
            {
                return exactTime;
            }

            public void setExactTime(String exactTime)
            {
                this.exactTime = exactTime;
            }

            @Override
            public String toString()
            {
                return "UserBookkeepingBeansBean{" +
                        "userId=" + userId +
                        ", name='" + name + '\'' +
                        ", moneyType=" + moneyType +
                        ", content='" + content + '\'' +
                        ", money=" + money +
                        ", exactTime='" + exactTime + '\'' +
                        '}';
            }
        }

        @Override
        public String toString()
        {
            return "DayDataBean{" +
                    "allIn=" + allIn +
                    ", allOut=" + allOut +
                    ", exactTimes=" + exactTimes +
                    ", userBookkeepingBeans=" + userBookkeepingBeans +
                    '}';
        }
    }

    @Override
    public String toString()
    {
        return "BookkeepingAllBean{" +
                "allMonthOut=" + allMonthOut +
                ", allMonthIn=" + allMonthIn +
                ", dayData=" + dayData +
                '}';
    }
}
