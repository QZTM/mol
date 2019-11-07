package com.mol.supplier.config;

import util.TimeUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 钉钉微应用参数
 */
public class MicroAttr {
    public static final String CROPID = "ding6ef23b66fc0611a335c2f4657eb6378f";
//    public static final String APP_KEY = "dingm6ltqohyp8oevxrp";
//    public static final String APP_SECRET = "tLJc6hTfKKStJwZJtAvQ8omtC7TLqifQs8ZUvc9YClPB0SFMEt7ggYBQYXzVqlbL";
//    public static final String AGENTID = "277589845";


    public static final String APP_KEY = "dingahkyew1sw286fzdb";
    public static final String APP_SECRET = "8L568tbt80tvR8VXFHqs-adOn7vI5WciHEnapOKlUHeyDTOEvWCtw4M3qOBJrKxr";
    public static final String AGENTID = "280301722";


    //服务器上测试版本使用：
//    public static final String APP_KEY = "dingwgy9ilfon7ovxl9r";
//    public static final String APP_SECRET = "FxoA6whPb7LyXgIN9oOa4oyyZUqr2k5rpjNkjHSKyg-PLY2_nx-LA2AwmYPJNxHM";
//    public static final String AGENTID = "280533794";


    //供应商认证完成
    public static final Integer  SUPSTATE_SUCCESS = 1;
    //供应商认证正在审核
    public static final Integer  SUPSTATE_LOADING = 2;
    //供应商认证失败
    public static final Integer  SUPSTATE_FAIL = 4;
    //供应商未认证
    public static final Integer WITHOUT_SUPSTATE=0;
    //供应商未付款，未形成订单
    public static final Integer SUPSTATE_BEFORE_CREATE_PAY=5;
    //供应商未付款，已形成订单
    public static final Integer SUPSTATE_BEFORE_PAYOVER=6;


}
