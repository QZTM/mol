package com.mol.ddmanage.Service.Office;

import com.mol.ddmanage.Ben.Office.Title_Details_Material;
import com.mol.ddmanage.Ben.Office.Title_Details_suppelier_quote_ben;
import com.mol.ddmanage.mapper.Office.Title_Details_Mapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class Title_Details_Service
{
    @Resource
    Title_Details_Mapper title_details_mapper;
    public List<Title_Details_Material> MaterialListService(String purchase_id)
    {
        List<Title_Details_Material> title_details_materials=title_details_mapper.MaterialListMapper(purchase_id);
        for (int n=0;n<title_details_materials.size();n++)
        {
           title_details_materials.get(n).setNumber(String.valueOf(n));
        }
        return title_details_materials;
    }

    public List<Title_Details_suppelier_quote_ben> SuppelierquoteService(String purchase_id)
    {
        List<Title_Details_suppelier_quote_ben> title_details_suppelier_quote_bens=title_details_mapper.SupplierQuoteMapper(purchase_id);
        for (int n=0;n<title_details_suppelier_quote_bens.size();n++)
        {
           title_details_suppelier_quote_bens.get(n).setNumber(String.valueOf(n));
        }
        return title_details_suppelier_quote_bens;
    }
    public Map Purchase(String purchase_id)
    {
        return title_details_mapper.Purchase(purchase_id);
    }

}
