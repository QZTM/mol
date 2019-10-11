package com.mol.purchase.util;

import com.mol.purchase.entity.dingding.solr.fyPurchase;

/**
 * ClassName:StatusScheUtils
 * Package:com.purchase.util
 * Description
 *  进度  状态中文显示
 * @date:2019/9/9 13:10
 * @author:yangjiangyan
 */
public class StatusScheUtils {
    public static fyPurchase getStatusIntegerToString(fyPurchase pur){
        if (pur!=null){
            if (pur.getStatus().equals("1")){
                pur.setStatus("正在采集报价");
                return pur;
            }
//            if (pur.getStatus().equals("2")){
//                pur.setStatus("采购结束");
//            }
            if (pur.getStatus().equals("3")){
                pur.setStatus("废止");
                return pur;
            }
            if (pur.getStatus().equals("4")){
                pur.setStatus("等待审核结果");
                return pur;
            }

            if (pur.getStatus().equals("6")){
                pur.setStatus("等待审核结果");
            }
            if (pur.getStatus().equals("7")){
                pur.setStatus("通过");
            }
            if (pur.getStatus().equals("8")){
                pur.setStatus("淘汰");
            }

//            }

        }
        return pur;
    }
}
