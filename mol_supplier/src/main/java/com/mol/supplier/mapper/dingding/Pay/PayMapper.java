package com.mol.supplier.mapper.dingding.Pay;

import com.mol.base.BaseMapper;
import com.mol.supplier.entity.dingding.Pay.PuiSupplierDeposit;

import java.util.List;

public interface PayMapper extends BaseMapper<PuiSupplierDeposit> {
      void add_order(PuiSupplierDeposit deposit);//insert
      PuiSupplierDeposit select_order(String order_id, String payType);
      List<PuiSupplierDeposit> select_supplier(String supplier_id);
      void update_order(String order_id, String return_money, String return_money_time);//订单id 退款状态 退款日期
}
