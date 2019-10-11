package com.mol.supplier.entity.dingding.login;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.response.OapiUserGetResponse;
import lombok.Data;

import java.util.List;

/**
 * 钉钉用户对象
 */
@Data
public class DDUser {

    private String userid;
    private String unionid;
    private String dingId;
    private String name;
    private String avatar;
    private String mobile;
    private String email;
    private String stateCode;
    private List<Long> department;

}
