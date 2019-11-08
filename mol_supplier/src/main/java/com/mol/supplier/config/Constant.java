package com.mol.supplier.config;

/**
 * 项目中的常量定义类
 */
public class Constant {
    /**
     * 域名
     */
    //public static final String domain = "fyycg1.vaiwan.com";
    //public static final String domain = "140.249.22.202";
    public static final String domain = "fyycg88.vaiwan.com";
    /**
     * 企业corpid, 需要修改成开发者所在企业
     */
    public static final String CORP_ID = "ding6ef23b66fc0611a335c2f4657eb6378f";
    /**
     * 开发者后台->企业自建应用->选择您创建的E应用->查看->AppKey
     */
    public static final String APP_KEY = "dingihxujkflumjcssad";
    /**
     * 开发者后台->企业自建应用->选择您创建的E应用->查看->AppSecret
     */
    public static final String APP_SECRET="xkThrr4omCZiswyF1NzlOtl0tfaL5fRMZ5wR8Pe6cUAVmSu08ewxH6p9RW_CHjum";
    /**
     * 数据加密密钥。用于回调数据的加密，长度固定为43个字符，从a-z, A-Z, 0-9共62个字符中选取,您可以随机生成
     */
    public static final String ENCODING_AES_KEY = "68PJEQx4AXwe0EPqpdk6x493nRFdR233ON35xakZK63";

    /**
     * 加解密需要用到的token，企业可以随机填写。如 "12345"
     */
    public static final String TOKEN = "12345";

    /**
     * 应用的agentdId，登录开发者后台可查看
     */
    public static final Long AGENTID = 272636313L;

    /**
     * 审批模板唯一标识，可以在审批管理后台找到PROC-2C58A0E4-4248-4246-B02C-A13CBB4EDF51
     */
    //public static final String PROCESS_CODE = "PROC-2C58A0E4-4248-4246-B02C-A13CBB4EDF51";
    public static final String UNDERLINEPUR_PROCESS_CODE = "PROC-F0EF1DDD-66A1-48BF-9934-66BB18F4A1AC";
    //线上审批模板id   processCode=PROC-18D734DC-C7A2-4984-9226-050C06E61147
    public static final String ONLINEPUR_PROCESS_CODE="PROC-18D734DC-C7A2-4984-9226-050C06E61147";

    /**
     * 回调host
     */
    public static final String CALLBACK_URL_HOST = domain;

    /**
     * 采购渠道常量
     */
    public static final Integer 线下采购 = 1;
    public static final Integer 线上采购 = 2;
    public static final Integer 战略采购 = 3;
    public static final Integer 询价采购 = 4;
    public static final Integer 单一来源 = 5;
    public static final Integer 在线招标 = 6;
    public static final Integer 生产原料 = 7;
    public static final Integer 零配件 = 8;


    public static String ROOT_DIR = System.getProperty("user.dir");
    public static Long orederStartNum =1L;

    public static Long  orderorederStartNum = 1L;


    /**
     * 订单号
     */


    //钉钉第三方微应用参数：
    //public static final String THIRD_SUITEKEY="suiteh8cipkjtnch8swif";
    public static final String THIRD_SUITEKEY="suiteccyxibo6rik9arvd";
    //public static final String THIRD_SUITE_SECRET="hoEKlM6r2fxIRadZCHRlwdNNfFaiCXkaGDedg00n-iDobG6Iyp_jiMkR3_82sAu-";
    public static final String THIRD_SUITE_SECRET="SiKUvjzEDwLoQNYCvC_PZke-4MLG3l1wKu1vUGhUj8uRkNELsmTTb63ka2T6G8_k";
    public static final String THIRD_TOKEN="fengyuan";
    public static final String THIRD_ENCODING_AES_KEY="92nlztbfnuhvmuyfdikqvgrjoh7iucfb6mf34u7q3pd";
    public static String THIRD_SUITE_TICKET = "";






}
