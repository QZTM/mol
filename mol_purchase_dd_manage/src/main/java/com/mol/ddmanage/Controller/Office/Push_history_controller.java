package com.mol.ddmanage.Controller.Office;

import com.mol.ddmanage.Ben.Office.Push_history_list_ben;
import com.mol.ddmanage.Service.Office.Push_history_Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;

@RestController
@RequestMapping("/Office/Push_history")
public class Push_history_controller
{
    @Resource
    private Push_history_Service push_history_service;
    @RequestMapping("/ShowList")
    public ArrayList<Push_history_list_ben> ShowList(@RequestParam String state)
    {
        ArrayList<Push_history_list_ben> push_history_bens=push_history_service.Push_history_list(state);
        return push_history_bens;
    }
}
