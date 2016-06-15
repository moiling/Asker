package com.moinut.asker.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

    public static final int SECOND = 60;
    public static final int HOUR = 3600;
    public static final int DAY = 86400;
    public static final int WEEK = 604800;

    public static final String TAG = TimeUtils.class.getName();

    /**
     * 2000-00-00 00:00:00 转 Date
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition position = new ParsePosition(0);
        return format.parse(strDate, position);
    }

    /**
     * 返回unix时间戳 (1970年至今的秒数)
     */
    public static long getUnixStamp() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 得到昨天的日期
     */
    public static String getYesterdayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String yesterday = sdf.format(calendar.getTime());
        return yesterday;
    }

    /**
     * 得到今天的日期
     */
    public static String getTodayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());
        return date;
    }

    /**
     * 时间戳转化为时间格式
     */
    public static String timeStampToStr(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(timeStamp * 1000);
        return date;
    }

    /**
     * 得到日期   yyyy-MM-dd
     */
    public static String formatDate(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(timeStamp * 1000);
        return date;
    }

    /**
     * 得到时间  HH:mm:ss
     */
    public static String getTime(long timeStamp) {
        String time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(timeStamp * 1000);
        String[] split = date.split("\\s");
        if (split.length > 1) {
            time = split[1];
        }
        return time;
    }


    public static String getTimeDetail(String time) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(time);
            return convertTimeToFormat(date, date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String convertTimeToFormat(Date date) {
        return convertTimeToFormat(date, date.getTime());
    }

    /**
     * 将一个时间戳转换成提示性时间字符串，如刚刚，1秒前
     */
    public static String convertTimeToFormat(Date date, long timeStamp) {
        long curTime = new Date().getTime();
        long delta = (curTime - timeStamp) / 1000;
        if (delta > 0) {
            if (delta / TimeUtils.SECOND < 1) {
                //return delta + "秒前";
                return "刚刚";
            } else if (delta / TimeUtils.HOUR < 1) {
                return delta / TimeUtils.SECOND + "分钟前";
            } else if (delta / TimeUtils.DAY < 2 && new Date().getDay() == date.getDay()) {
                return delta / TimeUtils.HOUR + "小时前";
            } else if (delta / TimeUtils.DAY < 3 && new Date().getDay() == new Date(date.getTime() + TimeUtils.DAY * 1000).getDay()) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                return "昨天 " + sdf.format(date);
            } else if (delta / TimeUtils.DAY < 4 && new Date().getDay() == new Date(date.getTime() + TimeUtils.DAY * 1000 * 2).getDay()) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                return "前天 " + sdf.format(date);
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                return sdf.format(date);
            }
        } else {
            delta = -delta;
            if (delta / TimeUtils.SECOND < 1) {
                return delta + "秒后";
            } else if (delta / TimeUtils.HOUR < 1) {
                return delta / TimeUtils.SECOND + "分钟后";
            } else if (delta / TimeUtils.DAY > -2 && new Date().getDay() == date.getDay()) {
                return delta / TimeUtils.HOUR + "小时后";
            } else if (delta / TimeUtils.DAY > -3 && new Date().getDay() == new Date(date.getTime() - TimeUtils.DAY).getDay()) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                return "明天 " + sdf.format(date);
            } else if (delta / TimeUtils.DAY > -4 && new Date().getDay() == new Date(date.getTime() - TimeUtils.DAY * 2).getDay()) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                return "后天 " + sdf.format(date);
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                return sdf.format(date);
            }
        }
    }

    /**
     * 将一个时间戳转换成提示性时间字符串，(多少分钟)
     */
    public static String timeStampToFormat(long timeStamp) {
        long curTime = System.currentTimeMillis() / (long) 1000;
        long time = curTime - timeStamp;
        return time / 60 + "";
    }


}
