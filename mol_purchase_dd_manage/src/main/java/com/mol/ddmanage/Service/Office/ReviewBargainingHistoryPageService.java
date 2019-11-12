package com.mol.ddmanage.Service.Office;

import com.mol.ddmanage.Ben.Office.ReviewPeolpesben;
import com.mol.ddmanage.mapper.Office.ReviewBargainingHistoryPageMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;

@Service
public class ReviewBargainingHistoryPageService
{
    @Resource
    ReviewBargainingHistoryPageMapper title_details_mapper;
/*    public List<Title_Details_Material> MaterialListService(String purchase_id)
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
    }*/
    public Map Purchase(String purchase_id)
    {
        return title_details_mapper.Purchase(purchase_id);
    }

    public ArrayList<ReviewPeolpesben> ReviewPeolpes(String CorpId)
    {
        ArrayList<ReviewPeolpesben> reviewPeolpesbens=new ArrayList<>();
        String user=title_details_mapper.ReviewPeolpesMapper(CorpId);
        String []ids=user.split(",");
        for (int n=0;n<ids.length;n++)
        {
            Map map=title_details_mapper.GetUserName(ids[n]);
            ReviewPeolpesben reviewPeolpesben=new ReviewPeolpesben();
            reviewPeolpesben.setName(map.get("user_name").toString());
            reviewPeolpesbens.add(reviewPeolpesben);
        }
        return reviewPeolpesbens;
    }

}
