package com.mol.ddmanage.Service.Office;

import com.mol.ddmanage.Ben.Office.Push_history_list_ben;
import com.mol.ddmanage.mapper.Office.PushHistoryMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class Push_history_Service
{
    @Resource
    private PushHistoryMapper push_history_mapper;
    public ArrayList<Push_history_list_ben> Push_history_list(String status)
    {
        ArrayList<Push_history_list_ben> push_history_bens=push_history_mapper.Set_Push_history_list(status);
        for (int n=0;n<push_history_bens.size();n++)//添加编号
        {
            push_history_bens.get(n).setNumber(String.valueOf(n));
            if (push_history_bens.get(n).getStatus().equals("4"))
            {
                push_history_bens.get(n).setStatus("议价中");
            }
            else if (push_history_bens.get(n).getStatus().equals("6"))
            {
                push_history_bens.get(n).setStatus("审批中");
            }
            else if (push_history_bens.get(n).getStatus().equals("7"))
            {
                push_history_bens.get(n).setStatus("已通过");
            }
            else if (push_history_bens.get(n).getStatus().equals("8"))
            {
                push_history_bens.get(n).setStatus("已拒绝");
            }
            else//其他状态
            {

            }
        }

        return push_history_bens;
    }
}
