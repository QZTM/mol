package com.mol.ddmanage.Service.Office;

import com.mol.ddmanage.Ben.Office.Purchase_Grogress_list_ben;
import com.mol.ddmanage.mapper.Office.PurchaseGrogressMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class Purchase_Grogress_Service
{
    @Resource
    PurchaseGrogressMapper purchaseGrogressMapper;
    public List<Purchase_Grogress_list_ben> PurchaseGrogressList()
    {
        List<Purchase_Grogress_list_ben> purchase_grogress_list_bens=purchaseGrogressMapper.PurchaseGrogressList();
        for (int n=0;n<purchase_grogress_list_bens.size();n++)
        {
            purchase_grogress_list_bens.get(n).setNumber(String.valueOf(n));
        }
        return  purchase_grogress_list_bens;
    }
}
