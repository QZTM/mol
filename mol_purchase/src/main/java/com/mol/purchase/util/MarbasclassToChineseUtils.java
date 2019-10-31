package com.mol.purchase.util;

import com.mol.purchase.entity.Supplier;

import java.util.List;

public class MarbasclassToChineseUtils {
    public static Supplier getSupplierChinese(Supplier su){
        String industryFirst = su.getIndustryFirst();
        if (industryFirst.equals("1001A1100000000024H3")){
            su.setIndustryFirst("产品类");
        }
        if (industryFirst.equals("1001A11000000000280U")){
            su.setIndustryFirst("设备类");
        }
        if (industryFirst.equals("1001A110000000002HSB")){
            su.setIndustryFirst("配件类");
        }
        if (industryFirst.equals("1001A110000000009S3Y")){
            su.setIndustryFirst("材料类");
        }
        if (industryFirst.equals("1001A11000000005GM78")){
            su.setIndustryFirst("酒店类");
        }
        if (industryFirst.equals("1001A110000000061NY3")){
            su.setIndustryFirst("应税劳务");
        }
        return su;
    }
}
