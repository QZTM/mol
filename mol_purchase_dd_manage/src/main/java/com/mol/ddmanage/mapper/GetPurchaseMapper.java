package com.mol.ddmanage.mapper;

import com.mol.ddmanage.Ben.App_user_table;
import com.mol.ddmanage.Ben.Purchase_track_ben;
import com.mol.ddmanage.Ben.Supplier_Review_ben;

import java.util.ArrayList;

public interface GetPurchaseMapper {
    String Get_Purchase_bar(String status, String history_time);//订单状态数
    String Get_Supplier(String history_time);

    ArrayList<Purchase_track_ben> Purchase_track_mapper(String status);//按订单编号在采购订单表里查基本信息

    App_user_table  Get_Purchase_staff(String dd_user_id);//获取采购人员信息

    ArrayList<Supplier_Review_ben>  Get_Supplier_Review_mapper(String supstate);//传入供应商审核状态，获取所有正在审核的供应商

    ArrayList<Supplier_Review_ben> Get_this_month_Supplier_number_mapper(String time_start, String time_end);
}
