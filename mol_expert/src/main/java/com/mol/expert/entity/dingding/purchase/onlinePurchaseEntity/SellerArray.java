package com.mol.expert.entity.dingding.purchase.onlinePurchaseEntity;

import lombok.Data;

import java.util.List;

@Data
public class SellerArray {

    private String name;
    //图片路径集合
    private List<String> inquiryUrl;
    //是否系统推荐
    private Boolean autoRec;
    //是否被选择
    private Boolean checked;
    //商家报价
    private Double quote;
    //商品网络路径
    private String link;
    //商家钉钉号码
    private String ddUserId;
    //销量
    private Double salesVolume;
    //商品名称
    private String goodsName;
    //0代表天猫，1代表淘宝，2代表京东，3代表拼多多
    private String dataChannel;


}
