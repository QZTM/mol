package com.mol.expert.mapper.newMysql.dingding.Pay;

import com.mol.expert.entity.dingding.Pay.pui_supplier_deposit;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan
@Mapper
public interface AlipayMapper
{
      void add_order(pui_supplier_deposit deposit);//insert
      pui_supplier_deposit select_order(String order_id,String payType);
      List<pui_supplier_deposit> select_supplier(String supplier_id);
      void update_order(String order_id,String return_money,String return_money_time);//订单id 退款状态 退款日期
}
