package com.mol.expert.entity.dingding.purchase.enquiryPurchaseEntity;

import lombok.Data;

@Data
public class SubObj {

    private PageArray pageArray;
    //公司id
    private String orgId;
    //发起人id
    private String staffId;
    //区分加工维修
    private String type;

}
