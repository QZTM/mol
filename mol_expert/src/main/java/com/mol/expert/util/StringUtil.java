package com.mol.expert.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {


    public static String toChinese(String str) {
        String[] s1 = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
        String[] s2 = { "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千" };
        String result = "";
        int n = str.length();
        for (int i = 0; i < n; i++) {
            int num = str.charAt(i) - '0';
            if (i != n - 1 && num != 0) {
                result += s1[num] + s2[n - 2 - i];
            } else {
                result += s1[num];
            }
        }
        return result;
    }


    public static String madandd(String str){
        StringBuilder sb = new StringBuilder(str);
        List<Integer> indexs = new ArrayList<>();
        StringBuilder compareSb = new StringBuilder();
        for(int i=0;i<sb.length();i++){
            char thisChar = sb.charAt(i);
            if(compareSb.length()<2){
                compareSb.append(thisChar);
            }else{
                compareSb.replace(0,1,"");
                compareSb.append(thisChar);
            }
            if("图片".equals(compareSb.toString())){
                indexs.add(i);
            }
        }
        for(int i=indexs.size()-1;i>=0;i--){
            int right = sb.indexOf("]",indexs.get(i));
            sb.deleteCharAt(right+1);
            int left = sb.indexOf("[",indexs.get(i));
            sb.deleteCharAt(left-1);
        }

        return sb.toString();

    }

}



