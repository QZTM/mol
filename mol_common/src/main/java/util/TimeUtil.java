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

    /**
     * 把时间戳转换为日期时间格式
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
     * 转换时间格式
     * @param ldt           需要转换的时间
     * @param formatType    想要转换成啥样
     * @return              转换好的时间字符串
     */
    public static String formatLDT(LocalDateTime ldt,String formatType){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatType);
        return ldt.format(formatter);
    }


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



    public static String getNowDateTime(){
        return getNow();
    }

    public static String getNowOnlyDate(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormaterOnlyDate);
        return now.format(formatter);
    }

    public static String get_last_year_date()//获取去年同月日期
    {
        SimpleDateFormat format = new SimpleDateFormat(dateFormaterOnlyDate);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, -1);
        Date y = c.getTime();
         return format.format(y);
    }


}
