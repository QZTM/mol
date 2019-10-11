package com.mol.supplier.entity.dingding.purchase.underlinePurchasePageEntity;
import lombok.Data;

import java.util.List;

@Data
public class SellerArray {
    //商家名称
    private String name;
    //图片路径集合
    private List<String> inquiryUrl;
    //录像路径
    private String videoUrl;
    //是否系统推荐
    private Boolean autoRec;
    //是否采购员选中
    private Boolean checked;
    //商家报价
    private Double quote;
    //商家钉钉id
    private String ddUserId;

    private String phone;
}
