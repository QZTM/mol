package util;

import java.math.BigDecimal;

public class BigDecimalUtils {

    /**
     * 加法
     * @param s1
     * @param s2
     * @return
     */
    public static double add(String s1,String s2){
        BigDecimal bd1=new BigDecimal(s1);
        BigDecimal bd2=new BigDecimal(s2);
        return bd1.add(bd2).doubleValue();
    }

    /**
     * 减法
     * @param s1
     * @param s2
     * @return
     */
    public static double subtraction(String s1,String s2){
        BigDecimal bd1=new BigDecimal(s1);
        BigDecimal bd2=new BigDecimal(s2);
        return bd1.subtract(bd2).doubleValue();
    }

    /**
     * 乘法
     * @param s1
     * @param s2
     * @return
     */
    public static double multiply(String s1,String s2){
        BigDecimal bd1=new BigDecimal(s1);
        BigDecimal bd2=new BigDecimal(s2);
        return bd1.multiply(bd2).doubleValue();
    }

    /**
     * 除法
     * @param s1
     * @param s2
     * @return
     */
    public static double divide(String s1,String s2){
        int sal=2;
        BigDecimal bd1=new BigDecimal(s1);
        BigDecimal bd2=new BigDecimal(s2);
        return bd1.divide(bd2,sal,BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }
}
