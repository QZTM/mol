package com.mol.ddmanage.mapper.Office;

import java.util.Map;

public interface ReviewBargainingHistoryPageMapper
{
/*    List<Title_Details_Material> MaterialListMapper(String purchase_id);//需要采购的物料明细
    List<Title_Details_suppelier_quote_ben> SupplierQuoteMapper(String purchase_id);//供应商报价表*/
    Map Purchase(String purchase_id);
    String ReviewPeolpesMapper(String CorpId);
    Map GetUserName(String id);
}
