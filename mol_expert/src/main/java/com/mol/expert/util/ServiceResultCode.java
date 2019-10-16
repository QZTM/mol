package com.mol.expert.util;

/**
 * 全局错误码
 * 如果业务方有自己的业务错误码,可以重新定义
 */
public enum ServiceResultCode {
    SYS_ERROR("-1","系统繁忙")
    ,SYS_OK("20000","成功")
    ,SYS_FAIL("20001","失败")
    ,SYS_LOGINERROR("20002","登陆异常")
    ,SYS_ACCESSERROR("20003","权限不足")
    ,SYS_REMOTEERROR("20004","远程调用失败")
    ,SYS_REPERROR("20005","重复操作")
    ;


    private String errCode;
    private String errMsg;

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    private ServiceResultCode(String errCode, String errMsg){
        this.errCode = errCode;
        this.errMsg = errMsg;
    }
}
