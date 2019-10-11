package com.mol.ddmanage.Service;

import com.mol.ddmanage.Ben.Purchase_track_ben;
import com.mol.ddmanage.Ben.Supplier_Review_ben;
import com.mol.ddmanage.Util.DataUtil;
import com.mol.ddmanage.mapper.GetPurchaseMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class Get_Purchase_inforService
{
    @Resource
    GetPurchaseMapper get_purchase;
    public Map Purchase_bar_infor_service(String number)
    {
        Map map =new HashMap();
        ArrayList<String> bar=new ArrayList<>();

        String history_time="1999-00-00 00:00:00";
        if (number.equals("0"))
        {
            history_time= DataUtil.getHistoryTime(7);
        }
        else if (number.equals("1"))
        {
            history_time= DataUtil.getHistoryTime(30);
        }
        else if (number.equals("2"))
        {
            history_time= DataUtil.getHistoryTime(365);
        }

        for (int n=1;n<4;n++)
        {
            if (n<3)
            {
                bar.add(get_purchase.Get_Purchase_bar(String.valueOf(n),history_time));
            }
            else
            {
                bar.add(String.valueOf(Integer.parseInt(get_purchase.Get_Purchase_bar(String.valueOf(n),history_time))+Integer.parseInt(get_purchase.Get_Purchase_bar(String.valueOf(n+1),history_time))));

                bar.add(get_purchase.Get_Supplier("1999-00-00 00:00:00"));//所有的供应商
                bar.add(get_purchase.Get_Supplier(history_time));//一定日期的供应商
            }
        }
        map.put("bar",bar);

        return map;
    }

    public ArrayList<Purchase_track_ben> Purchase_track_Service()
    {
        ArrayList<Purchase_track_ben> bens=get_purchase.Purchase_track_mapper("1");//进行中的订单
        for (int n=0;n<bens.size();n++)
        {
           if (bens.get(n).getBuy_channel_id().equals("3"))
           {
               bens.get(n).setBuy_channel_id("战略采购");
           }
           else if (bens.get(n).getBuy_channel_id().equals("4"))
           {
               bens.get(n).setBuy_channel_id("询价采购");
           }
           else if (bens.get(n).getBuy_channel_id().equals("5"))
           {
               bens.get(n).setBuy_channel_id("单一采购");
           }
           else if (bens.get(n).getBuy_channel_id().equals("6"))
           {
               bens.get(n).setBuy_channel_id("加工,维修");
           }

           if (get_purchase.Get_Purchase_staff(bens.get(n).getStaff_id())!=null )//数据表中必须有对应的人员
           {
               bens.get(n).setStaff_id(get_purchase.Get_Purchase_staff(bens.get(n).getStaff_id()).getUser_name());
           }
           else
           {
               bens.get(n).setStaff_id("没有找到");
           }

        }

       return bens;
    }
    public ArrayList<Supplier_Review_ben> Get_Supplier_Review_Service()
    {
        ArrayList<Supplier_Review_ben> review_bens=new ArrayList<>();
        review_bens = get_purchase.Get_Supplier_Review_mapper("2");
        for (int i=0;i<review_bens.size();i++)
        {
           if (review_bens.get(i).getSupplier_attr().equals("0"))
           {
               review_bens.get(i).setSupplier_attr("暂未设置");
           }
            else if (review_bens.get(i).getSupplier_attr().equals("1"))
            {
                review_bens.get(i).setSupplier_attr("基础供应商");
            }
           else if (review_bens.get(i).getSupplier_attr().equals("2"))
           {
               review_bens.get(i).setSupplier_attr("战略采购供应商");
           }
           else if (review_bens.get(i).getSupplier_attr().equals("3"))
           {
               review_bens.get(i).setSupplier_attr("单一供应商");
           }
        }
        return review_bens;
    }

    public ArrayList<Integer> Get_this_month_Supplier_number()
    {
        ArrayList<Integer> integers=new ArrayList<>();
        String time_start= (DataUtil.GetNowSytemTime().split(" "))[0].substring(0,8)+"01 00:00:00";
        String time_end= (DataUtil.GetNowSytemTime().split(" "))[0].substring(0,8)+"01 00:00:00";
      get_purchase.Get_this_month_Supplier_number_mapper("","");

      return integers;
    }
}
