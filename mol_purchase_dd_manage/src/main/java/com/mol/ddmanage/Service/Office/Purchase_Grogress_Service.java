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
    public List<Purchase_Grogress_list_ben> PurchaseGrogressList(String time1,String time2, String status)
    {
        List<Purchase_Grogress_list_ben> purchase_grogress_list_bens=purchaseGrogressMapper.PurchaseGrogressList(time1,time2,status);
        for (int n=0;n<purchase_grogress_list_bens.size();n++)
        {
            purchase_grogress_list_bens.get(n).setNumber(String.valueOf(n));

            if (purchase_grogress_list_bens.get(n).getBuy_channel_id()!=null)
            {
                //采购类型 1=线下，2=线上，3=战略采购  4=询价采购 5=单一采购 6=加工,维修
                if (purchase_grogress_list_bens.get(n).getBuy_channel_id().equals("1"))
                {
                    purchase_grogress_list_bens.get(n).setBuy_channel_id("线下");
                }
                else if (purchase_grogress_list_bens.get(n).getBuy_channel_id().equals("2"))
                {
                    purchase_grogress_list_bens.get(n).setBuy_channel_id("线上");
                }
                else if (purchase_grogress_list_bens.get(n).getBuy_channel_id().equals("3"))
                {
                    purchase_grogress_list_bens.get(n).setBuy_channel_id("战略采购");
                }
                else if (purchase_grogress_list_bens.get(n).getBuy_channel_id().equals("4"))
                {
                    purchase_grogress_list_bens.get(n).setBuy_channel_id("询价采购");
                }
                else if (purchase_grogress_list_bens.get(n).getBuy_channel_id().equals("5"))
                {
                    purchase_grogress_list_bens.get(n).setBuy_channel_id("单一采购");
                }
                else if (purchase_grogress_list_bens.get(n).getBuy_channel_id().equals("6"))
                {
                    purchase_grogress_list_bens.get(n).setBuy_channel_id("加工,维修");
                }
            }
            else
            {
                purchase_grogress_list_bens.get(n).setBuy_channel_id("未知");
            }

            if (purchase_grogress_list_bens.get(n).getStatus()!=null)
            {
                //采购类型 1 =正在询价 2=采购结束，3=采购废止 4 =采购部议价，5=专家推荐，6，等待审核结果，7=订单审核后通过，8=订单审核后拒绝
                if (purchase_grogress_list_bens.get(n).getStatus().equals("1"))
                {
                    purchase_grogress_list_bens.get(n).setStatus("正在询价");
                }
                else if (purchase_grogress_list_bens.get(n).getStatus().equals("2"))
                {
                    purchase_grogress_list_bens.get(n).setStatus("采购结束");
                }
                else if (purchase_grogress_list_bens.get(n).getStatus().equals("3"))
                {
                    purchase_grogress_list_bens.get(n).setStatus("采购废止");
                }
                else if (purchase_grogress_list_bens.get(n).getStatus().equals("4"))
                {
                    purchase_grogress_list_bens.get(n).setStatus("采购部议价中");
                }
                else if (purchase_grogress_list_bens.get(n).getStatus().equals("5"))
                {
                    purchase_grogress_list_bens.get(n).setStatus("专家推荐中");
                }
                else if (purchase_grogress_list_bens.get(n).getStatus().equals("6"))
                {
                    purchase_grogress_list_bens.get(n).setStatus("等待审核结果");
                }
                else if (purchase_grogress_list_bens.get(n).getStatus().equals("7"))
                {
                    purchase_grogress_list_bens.get(n).setStatus("订单审核通过");
                }
                else if (purchase_grogress_list_bens.get(n).getStatus().equals("8"))
                {
                    purchase_grogress_list_bens.get(n).setStatus("订单审核拒绝");
                }
            }


        }
        return  purchase_grogress_list_bens;
    }
}
