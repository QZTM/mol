package com.mol.ddmanage.Service.Office;

import com.mol.ddmanage.Ben.Office.ElectronicContractSigningListben;
import com.mol.ddmanage.mapper.Office.ElectronicContractSigningListMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class ElectronicContractSigningListService
{
    @Resource
    ElectronicContractSigningListMapper electronicContractSigningListMapper;
    public ArrayList<ElectronicContractSigningListben>GetElectronicContractSigningListLogic(String Contract_statu)
    {
        ArrayList<ElectronicContractSigningListben> contractSigningListbens=electronicContractSigningListMapper.GetElectronicContractSigningList();
        ArrayList<ElectronicContractSigningListben> contractSigningListbenss=new ArrayList<>();

        for (int n=0;n<contractSigningListbens.size();n++)//筛选签署状态
        {
            if (!Contract_statu.equals("0"))//选择非显示全部内容
            {
                if ( contractSigningListbens.get(n).getContract_statu()!=null)//状态不能为空
                {
                    if (contractSigningListbens.get(n).getContract_statu().equals(Contract_statu))//拿出对应的状态值
                    {
                        contractSigningListbenss.add(contractSigningListbens.get(n));
                    }
                }
                else //如果为空代表未签署
                {
                    if (Contract_statu.equals("1"))//未签署时才赋值
                    {
                        contractSigningListbenss.add(contractSigningListbens.get(n));
                    }
                }
            }
            else //选择显示全部内容
            {
                contractSigningListbenss=contractSigningListbens;
                break;
            }
        }

        for (int n=0;n<contractSigningListbenss.size();n++)//状态值转换
        {
            contractSigningListbenss.get(n).setNumber(String.valueOf(n));

            if (contractSigningListbenss.get(n).getSuplier_name()!=null)//去除重复的公司名
            {
                String []copyNames=contractSigningListbenss.get(n).getSuplier_name().split(",");
                if (copyNames.length>1)
                {
                    String copyNamestr="";
                    Set<String> set=new HashSet<String>(Arrays.asList(copyNames));
                    int n_1=0;
                    for (String str : set)
                    {
                        if (n_1!=0)
                        {
                            copyNamestr=copyNamestr+"，"+str;
                        }
                        else if (n_1==0)
                        {
                            copyNamestr=str;
                        }
                        n_1++;
                    }
                    contractSigningListbenss.get(n).setSuplier_name(copyNamestr);
                }
            }
            if (contractSigningListbenss.get(n).getBuy_channel_id().equals("3"))
            {
                contractSigningListbenss.get(n).setBuy_channel_id("战略采购");
            }
            else if (contractSigningListbenss.get(n).getBuy_channel_id().equals("4"))
            {
                contractSigningListbenss.get(n).setBuy_channel_id("询价采购");
            }
            else if (contractSigningListbenss.get(n).getBuy_channel_id().equals("5"))
            {
                contractSigningListbenss.get(n).setBuy_channel_id("单一采购");
            }
            else if (contractSigningListbenss.get(n).getBuy_channel_id().equals("6"))
            {
                contractSigningListbenss.get(n).setBuy_channel_id("加工,维修");
            }

            if (contractSigningListbenss.get(n).getContract_statu()!=null)//合同签署状态不为空
            {
                if (contractSigningListbenss.get(n).getContract_statu().equals("1"))//未签署
                {
                    contractSigningListbenss.get(n).setContract_statu("未签署");
                }
                else if (contractSigningListbenss.get(n).getContract_statu().equals("2"))//等待供应商签署
                {
                    contractSigningListbenss.get(n).setContract_statu("等待签署");
                }
                else if (contractSigningListbenss.get(n).getContract_statu().equals("3"))//签署完成 供应商签署完成
                {
                    contractSigningListbenss.get(n).setContract_statu("签署完成");
                }
            }
            else//合同签署状态为空
            {
                contractSigningListbenss.get(n).setContract_statu("未签署");
            }
        }
        return contractSigningListbenss;
    }
}
