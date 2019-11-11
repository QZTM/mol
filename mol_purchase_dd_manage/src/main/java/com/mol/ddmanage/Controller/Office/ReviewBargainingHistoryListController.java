package com.mol.ddmanage.Controller.Office;

import com.mol.ddmanage.Ben.Office.Push_history_list_ben;
import com.mol.ddmanage.Service.Office.ReviewBargainingHistoryListService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@RestController
@RequestMapping("/Office/Push_history")//审核议价历史列表
public class ReviewBargainingHistoryListController
{
    @Resource
    private ReviewBargainingHistoryListService push_history_service;
    @RequestMapping("/ShowList")
    public ArrayList<Push_history_list_ben> ShowList(@RequestParam String state, HttpServletRequest httpServletRequest)
    {
        HttpSession httpSession= httpServletRequest.getSession();
        ArrayList<Push_history_list_ben> push_history_bens=push_history_service.Push_history_list(state);
        return push_history_bens;
    }
}
