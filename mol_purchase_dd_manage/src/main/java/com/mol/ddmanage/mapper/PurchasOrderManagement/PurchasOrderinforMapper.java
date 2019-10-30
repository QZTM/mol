package com.mol.ddmanage.mapper.PurchasOrderManagement;

import com.mol.ddmanage.Ben.PurchasOrderManagement.PlannedPurchaseben;
import com.mol.ddmanage.Ben.PurchasOrderManagement.PurchasOrderinforben;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

public interface PurchasOrderinforMapper
{
    ArrayList<PurchasOrderinforben> OrderDetailedinfor(@Param(value = "PurchasId") String PurchasId);
    ArrayList<PlannedPurchaseben>plannedPurchasebens(@Param(value = "PurchasId") String PurchasId);
}
