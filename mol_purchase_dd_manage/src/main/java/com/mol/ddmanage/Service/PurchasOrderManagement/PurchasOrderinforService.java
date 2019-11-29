package com.mol.ddmanage.Service.PurchasOrderManagement;

import com.mol.ddmanage.Ben.PurchasOrderManagement.PlannedPurchaseben;
import com.mol.ddmanage.Ben.PurchasOrderManagement.PurchasOrderinforben;
import com.mol.ddmanage.mapper.PurchasOrderManagement.PurchasOrderinforMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class PurchasOrderinforService
{
    @Resource
    PurchasOrderinforMapper purchasOrderinforMapper;
    public ArrayList<ArrayList<PurchasOrderinforben>> OrderDetailedinforLogic(String PurchasId)
    {
        ArrayList<ArrayList<PurchasOrderinforben>> Orderss=new ArrayList<>();//一个分组

        ArrayList<PurchasOrderinforben> purchasOrderinforben=purchasOrderinforMapper.OrderDetailedinfor(PurchasId);

        Map<String,String> map_corp=new HashMap();
        for (int n=0;n<purchasOrderinforben.size();n++)//遍历出有多少公司报价
        {
            if (purchasOrderinforben.get(n).getCorp_name()!=null)
            {
                if (map_corp.get(purchasOrderinforben.get(n).getCorp_name())==null)
                {
                    map_corp.put(purchasOrderinforben.get(n).getCorp_name(),purchasOrderinforben.get(n).getCorp_name());
                }
            }
            if (purchasOrderinforben.get(n).getSign_status()!=null)//查看供应商合同状态
            {
                if (purchasOrderinforben.get(n).getSign_status().equals("1"))
                {
                    purchasOrderinforben.get(n).setSign_status("未签署合同");
                }
                else if (purchasOrderinforben.get(n).getSign_status().equals("2"))
                {
                    purchasOrderinforben.get(n).setSign_status("等待签署合同");
                }
                else if (purchasOrderinforben.get(n).getSign_status().equals("3"))
                {
                    purchasOrderinforben.get(n).setSign_status("已签署合同");
                }
            }
            else
            {
                purchasOrderinforben.get(n).setSign_status("未签署合同");
            }
        }

        for (String value :map_corp.values())//相同公司的报价归为同一组
        {
            ArrayList<PurchasOrderinforben> Orders=new ArrayList<>();//一个单列
            for (int n=0;n< purchasOrderinforben.size();n++)
            {
                if (value.equals(purchasOrderinforben.get(n).getCorp_name()))//有公司名字
                {
                    Orders.add(purchasOrderinforben.get(n));
                }
            }
            Orderss.add(Orders);
        }

        ArrayList<PurchasOrderinforben> Orders=new ArrayList<>();//一个单列
        for (int n=0;n< purchasOrderinforben.size();n++)
        {
           if (purchasOrderinforben.get(n).getCorp_name()==null)//有公司名字
            {
                purchasOrderinforben.get(n).setCorp_name("公司名称未知");
                Orders.add(purchasOrderinforben.get(n));
            }
        }
        if (Orders.size()!=0)
        {
            Orderss.add(Orders);
        }


         for (int n=0;n<Orderss.size();n++)//为分组添加序号
         {
             for (int n_1=0;n_1<Orderss.get(n).size();n_1++)
             {
                 Orderss.get(n).get(n_1).setNumber(String.valueOf(n_1));
             }
         }
        return Orderss;
    }
    public ArrayList<PlannedPurchaseben> plannedPurchasebensLogic(String PurchasId)
    {
        ArrayList<PlannedPurchaseben> plannedPurchasebens= purchasOrderinforMapper.plannedPurchasebens(PurchasId);
        for (int n=0;n<plannedPurchasebens.size();n++)
        {
          plannedPurchasebens.get(n).setNumber(String.valueOf(n));
        }
        return plannedPurchasebens;
    }

}
