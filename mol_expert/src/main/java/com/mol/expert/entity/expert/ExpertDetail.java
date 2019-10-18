package com.mol.expert.entity.expert;

import lombok.Data;

/**
 * ClassName:ExpertDetail
 * Package:com.purchase.entity.expert
 * Description
 *  expert_detail页面传输数据使用
 * @date:2019/10/7 10:38
 * @author:yangjiangyan
 */
@Data
public class ExpertDetail {
    private String supplierId;
    private String fyPurchaseId;
    private String materialId;
    private String typeName;
    private String brandName;
    private String itemName;
    private String norms;
    private int count;
    private String unit;
    //报价
    private String quote;
    //总价
    private String sum;
}
