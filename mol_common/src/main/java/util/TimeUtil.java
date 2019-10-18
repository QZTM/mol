package util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {


    public static String dateFormaterDateTime = "yyyy-MM-dd HH:mm:ss";
    public static String dateFormaterOnlyDate = "yyyy-MM-dd";
    public static String payOrderFormat = "yyyyMMddHHmmssSSS";

    /**
     * 把时间戳转换为LocalDateTime类型的日期时间格式
     * @param timestamp
     * @return
     */
    public static LocalDateTime stampToDate(Long timestamp){
        Instant instant = Instant.ofEpochMilli(timestamp);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    };


    /**
     * 把日期转换为时间戳
     * @param ldt   需要转换的日期
     * @return      转换好的时间戳（Long)
     */
    public static Long dateToStamp(LocalDateTime ldt){
        Instant instant = ldt.atZone(ZoneId.systemDefault()).toInstant();
        return instant.toEpochMilli();
    }


    /**
     * LocalDateTime  ==>   String
     * @param ldt           需要转换的时间
     * @param formatType    想要转换成啥样
     * @return              转换好的时间字符串
     */
    public static String formatLDT(LocalDateTime ldt,String formatType){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatType);
        return ldt.format(formatter);
    }


    /**
     * 获取当前年月日时分秒"yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static String getNow(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormaterDateTime);
        return now.format(formatter);
    }

    public static String getNow(String format){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return now.format(formatter);
    }


    /**
     *
     * 获取当前年月日时分秒"yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static String getNowDateTime(){
        return getNow();
    }

    /**
     * 获取当前日期   XXXX-XX-XX
     * @return
     */
    public static String getNowOnlyDate(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormaterOnlyDate);
        return now.format(formatter);
    }

    /**
     * 获取去年同月日期
     * @return
     */
    public static String get_last_year_date()
    {
        SimpleDateFormat format = new SimpleDateFormat(dateFormaterOnlyDate);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, -1);
        Date y = c.getTime();
         return format.format(y);
    }

    /**
     * String   ===>   LocalDateTime
     * @param timeStr               字符串型的日期、时间
     * @param format                转换的格式
     * @return
     */
    public static LocalDateTime getLocalDataTimeFromStr(String timeStr,String format){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(timeStr,dateTimeFormatter);
    }




    /**
     * 通过输入指定日期时间生成cron表达式
     * @param date
     * @return cron表达式
     */
    public static String getCron(Date date) {
        String dateFormat = "ss mm HH dd MM ? yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    public static String getCron(String time){
        LocalDateTime timeL = getLocalDataTimeFromStr(time,dateFormaterDateTime);
        return getCron(LocalDateTimeToDate(timeL));
    }


    /**
     * java.time.LocalDateTime   -->   java.util.Date
     */
    public static Date LocalDateTimeToDate(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }


    /**
     * java.util.Date --> java.time.LocalDateTime
     */
    public static LocalDateTime dateToLocalDateTime() {
        java.util.Date date = new java.util.Date();
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime;
    }

}
