package com.mol.ddmanage.mapper.Office;

import com.mol.ddmanage.Ben.Office.Push_history_list_ben;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

public interface ReviewBargainingHistoryListMapper
{
    ArrayList<Push_history_list_ben> Set_Push_history_list(@Param(value = "status") String status);
}
