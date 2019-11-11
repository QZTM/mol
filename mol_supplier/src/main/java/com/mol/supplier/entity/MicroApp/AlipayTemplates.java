package com.mol.supplier.entity.MicroApp;

import com.mol.pay.entity.AlipayCreateInfo;
import com.mol.pay.entity.AlipayCreateInfoBuilder;
import com.mol.supplier.entity.dingding.Pay.PuiSupplierDeposit;
import lombok.Data;
import util.TimeUtil;

/**
 * 支付宝支付模板
 */
@Data
public class AlipayTemplates {

    public static AlipayCreateInfo TemplateOfStrategySupplierService(){
        return AlipayCreateInfoBuilder.getBuilder().builder("申请成为战略供应商服务费", "申请成为战略供应商", TimeUtil.getNow(TimeUtil.payOrderFormat) + "", "30m", "", "0.01", PuiSupplierDeposit.ORDER_PAY_FOR_STRATEGY_SUPPLIER_SERVICE);
    }

    public static AlipayCreateInfo TemplateOfSingleSupplierService(){
        return AlipayCreateInfoBuilder.getBuilder().builder("申请成为单一供应商服务费", "申请成为单一供应商", TimeUtil.getNow(TimeUtil.payOrderFormat) + "", "30m", "", "0.01",PuiSupplierDeposit.ORDER_PAY_FOR_SINGON_SUPPLIER_SERVICE);
    }

}
