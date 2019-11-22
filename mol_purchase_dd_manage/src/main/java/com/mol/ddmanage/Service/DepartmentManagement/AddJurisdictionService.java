package com.mol.ddmanage.Service.DepartmentManagement;

import com.mol.ddmanage.Ben.DepartmentManagement.AddJurisdictionben;
import com.mol.ddmanage.Util.DataUtil;
import com.mol.ddmanage.mapper.DepartmentMangement.AddJurisdictionMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Service
public class AddJurisdictionService
{
    @Resource
    AddJurisdictionMapper addJurisdictionMapper;
    public boolean AddJurisdictionLogic(AddJurisdictionben addJurisdictionben,HttpSession httpSession)
    {
        try
        {
            String userid= httpSession.getAttribute("userid").toString();//Token_hand.Review_My_Token(httpSession.getAttribute("token").toString());//获取token里的userid
            addJurisdictionben.setJurisdictionId(DataUtil.GetTimestamp());//id
            addJurisdictionben.setCreatTime(DataUtil.GetNowSytemTime());//获取系统时间
            addJurisdictionben.setCreadStaff(userid);//钉钉人员id
            addJurisdictionMapper.AddJurisdictionMapper(addJurisdictionben);

            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
