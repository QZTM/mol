package com.mol.ddmanage.Service.ExperManagement;

import com.mol.ddmanage.Ben.ExperManagement.ExperApprovalListben;
import com.mol.ddmanage.mapper.ExperManagement.ExperApprovalListMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class ExperApprovalListService
{
    @Resource
    ExperApprovalListMapper experApprovalListMapper;
    public ArrayList<ExperApprovalListben> ShowList(String authentication)
    {
        ArrayList<ExperApprovalListben> experApprovalListbens=experApprovalListMapper.ExperApprovalListShow(authentication);
        for(int n=0;n<experApprovalListbens.size();n++)
        {
            experApprovalListbens.get(n).setNumber(String.valueOf(n));
        }
        return experApprovalListbens;
    }
}
