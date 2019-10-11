package com.mol.ddmanage.mapper.Office;

import com.mol.ddmanage.Ben.Office.Title_Details_Material;
import com.mol.ddmanage.Ben.Office.Title_Details_suppelier_quote_ben;

import java.util.List;
import java.util.Map;

public interface Title_Details_Mapper
{
    List<Title_Details_Material> MaterialListMapper(String purchase_id);//需要采购的物料明细
    List<Title_Details_suppelier_quote_ben> SupplierQuoteMapper(String purchase_id);//供应商报价表
    Map Purchase(String purchase_id);
}
