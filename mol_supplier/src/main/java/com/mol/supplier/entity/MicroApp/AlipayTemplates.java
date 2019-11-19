package com.mol.supplier.entity.MicroApp;

import com.mol.pay.entity.AlipayCreateInfo;
import com.mol.pay.entity.AlipayCreateInfoBuilder;
import com.mol.supplier.entity.dingding.Pay.PuiSupplierDeposit;
import lombok.Data;
import util.TimeUtil;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付宝支付模板
 */
@Data
public class AlipayTemplates {

    public static AlipayCreateInfo TemplateOfStrategySupplierService(String supplierId){
        Map paraMap = new HashMap();
        paraMap.put("payFor", PuiSupplierDeposit.ORDER_PAY_FOR_STRATEGY_SUPPLIER_SERVICE);
        paraMap.put("supplierId",supplierId);
        return AlipayCreateInfoBuilder.getBuilder().builder("申请成为战略供应商服务费", "申请成为战略供应商", TimeUtil.getNow(TimeUtil.payOrderFormat), "30m", "", "0.01",paraMap);
    }

    public static AlipayCreateInfo TemplateOfSingleSupplierService(String supplierId){
        Map paraMap = new HashMap();
        paraMap.put("payFor", PuiSupplierDeposit.ORDER_PAY_FOR_SINGON_SUPPLIER_SERVICE);
        paraMap.put("supplierId",supplierId);
        return AlipayCreateInfoBuilder.getBuilder().builder("申请成为单一供应商服务费", "申请成为单一供应商", TimeUtil.getNow(TimeUtil.payOrderFormat), "30m", "", "0.01",paraMap);
    }



    public static AlipayCreateInfo TemplateOfExpertReviewAndContractService(String supplierId,String purchaseId){
        Map paraMap = new HashMap();
        paraMap.put("payFor", PuiSupplierDeposit.ORDER_PAY_FOR_EXPERT_REVIEW_AND_CONTRACT_FEE);
        paraMap.put("supplierId",supplierId);
        paraMap.put("purchaseId",purchaseId);
        return AlipayCreateInfoBuilder.getBuilder().builder("服务费", "茉尔易购信息服务费", TimeUtil.getNow(TimeUtil.payOrderFormat), "30m", "", "0.01",paraMap);
    }



}
