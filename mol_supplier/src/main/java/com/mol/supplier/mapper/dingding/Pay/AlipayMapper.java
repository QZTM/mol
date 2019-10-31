package com.mol.supplier.mapper.dingding.Pay;

import com.mol.supplier.entity.dingding.Pay.pui_supplier_deposit;
import tk.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan
public interface AlipayMapper
{
      void add_order(pui_supplier_deposit deposit);//insert
      pui_supplier_deposit select_order(String order_id, String payType);
      List<pui_supplier_deposit> select_supplier(String supplier_id);
      void update_order(String order_id, String return_money, String return_money_time);//订单id 退款状态 退款日期
}
