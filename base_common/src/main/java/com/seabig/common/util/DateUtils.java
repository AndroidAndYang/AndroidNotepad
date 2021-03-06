package com.seabig.common.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author YJZ
 * date: 2017/11/15
 * description：日期处理相关工具类
 */


public class DateUtils {

    public static SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    public static SimpleDateFormat formatDay = new SimpleDateFormat("d", Locale.CHINA);
    public static SimpleDateFormat formatMonthDay = new SimpleDateFormat("M-d", Locale.CHINA);
    public static SimpleDateFormat formatDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    /**
     * 获得当前的日期   格式yyyy-MM-dd
     */
    public static String getNowDate()
    {
        return formatDate.format(new Date());
    }

    /**
     * 获得当前的日期时间   格式yyyy-MM-dd HH:mm:ss
     */
    public static String getNowDateTime()
    {
        return formatDateTime.format(new Date());
    }

    /**
     * 按指定的格式进行格式化
     */
    public static String format(Date date, String formatStr)
    {
        return new SimpleDateFormat(formatStr, Locale.CHINA).format(date);
    }

    /**
     * 格式化日期
     */
    public static String formatDate(Date date)
    {
        return formatDate.format(date);
    }

    /**
     * 格式化日期
     */
    public static String formatDateTime(Date date)
    {
        return formatDateTime.format(date);
    }

    /**
     * 将时间戳解析成日期
     */
    public static String parseDate(long timeInMillis)
    {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(new Date(timeInMillis * 1000l));
    }

    /**
     * 将时间戳解析成日期
     */
    public static String parseDateTime(long timeInMillis)
    {

        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date(timeInMillis * 1000l));
    }

    /**
     * 将时间转换为时间戳
     */
    public static long dateTimeToStamp(String s)
    {
        try
        {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).parse(s).getTime() / 1000;
        }
        catch (ParseException ex)
        {
            return 0;
        }
    }

    /**
     * 将时间转换为时间戳
     */
    public static long dateToStamp(String s)
    {
        try
        {
            return new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).parse(s).getTime() / 1000;
        }
        catch (Exception ex)
        {
            return 0;
        }
    }

    /**
     * 解析日期
     */
    public static Date parseDate(String date)
    {
        Date mDate = null;
        try
        {
            mDate = formatDate.parse(date);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        return mDate;
    }

    /**
     * 解析日期
     */
    public static Date parseDateTime(String datetime)
    {
        Date mDate = null;
        try
        {
            mDate = formatDateTime.parse(datetime);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return mDate;
    }

    /**
     * 以友好的方式显示时间
     */
    public static String friendlyTime(String sdate)
    {
        Date time = parseDateTime(sdate);
        if (time == null)
        {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance(Locale.CHINA);

        // 判断是否是同一天
        String curDate = formatDate.format(cal.getTime());
        String paramDate = formatDate.format(time);
        if (curDate.equals(paramDate))
        {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
            {
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            } else
            {
                ftime = hour + "小时前";
            }
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0)
        {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
            {
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            } else
            {
                ftime = hour + "小时前";
            }
        } else if (days == 1)
        {
            ftime = "昨天";
        } else if (days == 2)
        {
            ftime = "前天";
        } else if (days > 2 && days <= 10)
        {
            ftime = days + "天前";
        } else if (days > 10)
        {
            ftime = formatDate.format(time);
        }
        return ftime;
    }

    /**
     * 计算两个时间相隔分钟
     */
    public static long getDateTimeIntervalMinutes(String startTime, String endTime)
    {
        if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime))
        {
            try
            {
                final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
                final Date startDate = format.parse(startTime);
                final Calendar start = Calendar.getInstance(Locale.CHINA);
                start.clear();
                start.setTime(startDate);

                final Date endDate = format.parse(endTime);
                final Calendar end = Calendar.getInstance(Locale.CHINA);
                end.clear();
                end.setTime(endDate);

                // 把时间转成毫秒
                final long time1 = start.getTimeInMillis();
                final long time2 = end.getTimeInMillis();

                // 计算两个时间相差多少毫秒
                final long diff = time1 - time2;

                // 把相差的毫秒转成分钟
                final long diffMin = diff / (60 * 1000);

                {/*
                // 相差小时
                long diffHours = diff / (60 * 60 * 1000);
                System.out.println("Difference in hours " + diffHours);

                // 相差天
                long diffDays = diff / (24 * 60 * 60 * 1000);
                */
                }

                return diffMin;
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }

        }
        return 0;
    }

    /**
     * 计算两个时间相隔分钟
     */
    public static long getDateIntervalMinutes(String startTime, String endTime)
    {
        if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime))
        {
            try
            {
                final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
                final Date startDate = format.parse(startTime);
                final Calendar start = Calendar.getInstance(Locale.CHINA);
                start.clear();
                start.setTime(startDate);

                final Date endDate = format.parse(endTime);
                final Calendar end = Calendar.getInstance(Locale.CHINA);
                end.clear();
                end.setTime(endDate);

                // 把时间转成毫秒
                final long time1 = start.getTimeInMillis();
                final long time2 = end.getTimeInMillis();

                // 计算两个时间相差多少毫秒
                final long diff = time1 - time2;

                // 把相差的毫秒转成分钟
                final long diffMin = diff / (60 * 1000);

                {/*
                // 相差小时
                long diffHours = diff / (60 * 60 * 1000);
                System.out.println("Difference in hours " + diffHours);

                // 相差天
                long diffDays = diff / (24 * 60 * 60 * 1000);
                */
                }

                return diffMin;
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }

        }
        return 0;
    }

    /**
     * 获取指定年月日是星期几
     *
     * @param ymd 具体的时间 例如：2018-11-14
     * @return 星期几
     */
    public static String getWeek(String ymd)
    {
        String week = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINESE);
        Calendar c = Calendar.getInstance();
        try
        {
            c.setTime(format.parse(ymd));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        int wek = c.get(Calendar.DAY_OF_WEEK);

        if (wek == 1)
        {
            week += "星期日";
        }
        if (wek == 2)
        {
            week += "星期一";
        }
        if (wek == 3)
        {
            week += "星期二";
        }
        if (wek == 4)
        {
            week += "星期三";
        }
        if (wek == 5)
        {
            week += "星期四";
        }
        if (wek == 6)
        {
            week += "星期五";
        }
        if (wek == 7)
        {
            week += "星期六";
        }
        return week;
    }

    /**
     * 获取月中的第一天
     * @return 第一天
     */
    public static int getFirstDayOfMonth() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DAY_OF_MONTH, 1);
        return a.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取当前月的最后一天
     *
     * @return int
     */
    public static int getCurrentMonthLastDay() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }
}