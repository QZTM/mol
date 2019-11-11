package com.mol.ddmanage.mapper.Office;

import com.mol.ddmanage.Ben.Office.TimeProcessExperreCommendben;
import com.mol.ddmanage.Ben.PurchasOrderManagement.PurchasOrderinforben;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

public interface TimeProcessMapper
{
    ArrayList<TimeProcessExperreCommendben> ExperreCommend(@Param(value = "purchase_id") String purchase_id,@Param(value = "supplier_id") String supplier_id);
    ArrayList<PurchasOrderinforben> SupperlierQuoteFinally(@Param(value = "PurchasId") String PurchasId);
    ArrayList<PurchasOrderinforben> PurchasOver(@Param(value = "PurchasId")String PurchasId);
}
