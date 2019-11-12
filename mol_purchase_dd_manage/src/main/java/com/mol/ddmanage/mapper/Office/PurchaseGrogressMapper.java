package com.mol.ddmanage.mapper.Office;

import com.mol.ddmanage.Ben.Office.Purchase_Grogress_list_ben;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PurchaseGrogressMapper
{
    List<Purchase_Grogress_list_ben> PurchaseGrogressList(@Param(value = "time1") String time1, @Param(value = "time2")String time2 ,@Param(value = "status") String status);
}
