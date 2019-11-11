package com.mol.supplier.mapper.Pay;

import com.mol.supplier.entity.dingding.Pay.PuiSupplierDeposit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WeixinPayMapper
{
    void add_order(PuiSupplierDeposit deposit);//insert
    PuiSupplierDeposit select_order(String order_id, String payType);
    List<PuiSupplierDeposit> select_supplier(String supplier_id);//查找供应商
    void update_order(PuiSupplierDeposit supplier_deposit);//订单id 退款状态 退款日期
    void Delete_Pay(String supplierId, String Money);//商户id 支付金额
}
