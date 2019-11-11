package com.mol.ddmanage.Service.Office;

import com.mol.ddmanage.Ben.Office.TimeProcessExperreCommendben;
import com.mol.ddmanage.Ben.PurchasOrderManagement.PurchasOrderinforben;
import com.mol.ddmanage.mapper.Office.TimeProcessMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class TimeProcessService
{
    @Resource
    TimeProcessMapper timeProcessMapper;
    public ArrayList<TimeProcessExperreCommendben>ExperreCommendLogic(String PurchasId,String supplier_id)
    {
        ArrayList<TimeProcessExperreCommendben> timeProcessExperreCommendbens=timeProcessMapper.ExperreCommend(PurchasId,supplier_id);
        for (int n=0;n<timeProcessExperreCommendbens.size();n++)
        {
            timeProcessExperreCommendbens.get(n).setNumber(String.valueOf(n));
        }
        return timeProcessExperreCommendbens;
    }

    public ArrayList<ArrayList<PurchasOrderinforben>> SupperlierQuoteFinallyLogic(String fy_purchase_id)
    {
        ArrayList<ArrayList<PurchasOrderinforben>> Orderss=new ArrayList<>();//一个分组

        ArrayList<PurchasOrderinforben> purchasOrderinforben=timeProcessMapper.SupperlierQuoteFinally(fy_purchase_id);

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

    public ArrayList<ArrayList<PurchasOrderinforben>> PurchasOverLogic(String fy_purchase_id)
    {
        ArrayList<ArrayList<PurchasOrderinforben>> Orderss=new ArrayList<>();//一个分组

        ArrayList<PurchasOrderinforben> purchasOrderinforben=timeProcessMapper.PurchasOver(fy_purchase_id);

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
}
