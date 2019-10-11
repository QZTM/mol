package com.mol.purchase.util;

import com.mol.purchase.entity.dingding.solr.fyPurchase;

public class StatusUtils {

    public static fyPurchase getStatusIntegerToString(fyPurchase pur){
        if (pur!=null){
            if (pur.getStatus().equals("1")){
                pur.setStatus("正在询价");
                return pur;
            }
//            if (pur.getStatus().equals("2")){
//                pur.setStatus("采购结束");
//            }
            if (pur.getStatus().equals("3")){
                pur.setStatus("采购废止");
                return pur;
            }
            if (pur.getStatus().equals("4")){
                pur.setStatus("正在审核");
                return pur;
            }else {
                pur.setStatus("采购结束");
                return pur;
            }

//            if (pur.getStatus().equals("6")){
//                pur.setStatus("等待审核结果");
//            }
//            if (pur.getStatus().equals("7")){
//                pur.setStatus("通过");
//            }
//            if (pur.getStatus().equals("8")){
//                pur.setStatus("淘汰");
//            }

//            }

        }else {
            return pur;
        }
    }
}
