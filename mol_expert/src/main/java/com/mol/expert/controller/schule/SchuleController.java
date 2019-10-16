package com.mol.expert.controller.schule;

import com.mol.expert.entity.dingding.solr.fyPurchase;
import com.mol.expert.entity.expert.ExpertUser;
import com.mol.expert.service.expert.SchuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * ClassName:SchuleController
 * Package:com.purchase.controller.schule
 * Description
 *  专家进度
 * @date:2019/10/8 10:14
 * @author:yangjiangyan
 */

@RequestMapping("/schule")
@Controller
public class SchuleController {

    @Autowired
    SchuleService schuleService;

    /**
     * 进度
     * @param adopt
     * @param map
     * @return
     */
    @GetMapping("/findList")
    public String find(String adopt , ModelMap map, HttpSession session){
        ExpertUser user = (ExpertUser) session.getAttribute("user");
        if(user==null){
            throw new RuntimeException("用户信息加载失败，请重试！");
        }
        //推荐
        List<fyPurchase> reList= schuleService.findRecommendByAdopt(adopt,user.getId());

        reList=schuleService.changeOrgNameToZhongwen(reList);
        map.addAttribute("list",reList);

        String sta="";
        if (adopt!=null){
            sta=adopt;
        }
        map.addAttribute("status",sta);
        return "expert_schedule";
    }
}
