package com.mol.ddmanage.Controller.Office;

import com.mol.ddmanage.Ben.Office.Title_Details_Material;
import com.mol.ddmanage.Ben.Office.Title_Details_suppelier_quote_ben;
import com.mol.ddmanage.Service.Office.Title_Details_Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/Office/Title_Details_contorller")
public class Title_Details_contorller
{
    @Resource
    Title_Details_Service title_details_service;
    @RequestMapping("/MaterialList")
    public List<Title_Details_Material> MaterialList(@RequestParam String purchase_id)
    {
        List<Title_Details_Material> title_details_materials=title_details_service.MaterialListService(purchase_id);
        return title_details_materials;
    }

    @RequestMapping("/SuppelierQuote")
    public List<Title_Details_suppelier_quote_ben> SuppelierQuote(@RequestParam String purchase_id)
    {
        List<Title_Details_suppelier_quote_ben> title_details_suppelier_quote_bens=title_details_service.SuppelierquoteService(purchase_id);
        return title_details_suppelier_quote_bens;
    }
}
