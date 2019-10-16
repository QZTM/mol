package com.mol.expert.controller.my;

import com.mol.expert.entity.dingding.solr.fyPurchase;
import com.mol.expert.entity.expert.ExpertRecommend;
import com.mol.expert.entity.expert.ExpertUser;
import com.mol.expert.service.expert.MyService;
import com.mol.expert.service.expert.SchuleService;
import com.mol.expert.util.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * ClassName:MyController
 * Package:com.purchase.controller.my
 * Description
 *
 * @date:2019/10/8 10:11
 * @author:yangjiangyan
 */
@Controller
@RequestMapping("/my")
public class MyController {
    @Autowired
    private MyService myService;

    @Autowired
    private SchuleService schuleService;

    //身份证正面
    private static final String FRONT_OF_IDCARD="frontOfId";

    //身份证反面
    private static final String REVERSE_OF_IDCARD="reverseOfId";

    //名片正面
    private static final String FRONT_OF_BUSINESS="frontOfBusiness";

    //名片反面
    private static final String REVERSE_OF_BUSINESS="reverseOfBusiness";

    //其他证件1Other documents
    private static final String OTHER_DOCUMENT_ONE="otherDocumentsOne";

    //其他证件2Other documents
    private static final String OTHER_DOCUMENT_TWO="otherDocumentsTwo";

    @GetMapping("/one")
    public String getMyMessage(HttpSession session, ModelMap map){
        ExpertUser user = (ExpertUser) session.getAttribute("user");
        map.addAttribute("my",user);
        return "expert_my";
    }

    /**
     * 前往认证
     * @param map
     * @param session
     * @return
     */
    @GetMapping("/authenticate")
    public String authenticate(ModelMap map,HttpSession session){

        ExpertUser user = (ExpertUser) session.getAttribute("user");
        if(user==null){
            throw new RuntimeException("用户信息加载失败，请重试！");
        }
        //第一级的行业类比
        List<BdMarbasclass> firstBdMar = myService.getFirstBdMar();

        map.addAttribute("bdMar",firstBdMar);
        map.addAttribute("my",user);
        return "expert_authenticate";
    }

    /**
     * 认证--保存图片
     * @param file
     * @param whichImg
     * @param session
     * @return
     */
    @PostMapping("/uploadImage")
    @ResponseBody
    public ServiceResult uploadImage(MultipartFile file, String whichImg, HttpSession session){
        if (file ==null){
            throw new RuntimeException("没有图片上传");
        }
        ExpertUser user = (ExpertUser) session.getAttribute("user");
        switch (whichImg){
            case FRONT_OF_IDCARD:
                try {
                    user.setFrontOfId(file.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case REVERSE_OF_IDCARD:
                try {
                    user.setReverseOfId(file.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case FRONT_OF_BUSINESS:
                try {
                    user.setFrontOfBusiness(file.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case REVERSE_OF_BUSINESS:
                try {
                    user.setReverseOfBusiness(file.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case OTHER_DOCUMENT_ONE:
                try {
                    user.setOtherDocumentsOne(file.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case OTHER_DOCUMENT_TWO:
                try {
                    user.setOtherDocumentsTwo(file.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
        int result=myService.updataImge(user);
        if (result != 1) {
            throw new RuntimeException("保存照片时出错");
        }
        return ServiceResult.success("保存成功");
    }

    /**
     * 认证-保存文字信息
     * @param eu
     * @param session
     * @return
     */
    @PostMapping("/subMessage")
    @ResponseBody
    public ServiceResult subMessage(ExpertUser eu,HttpSession session){
        ExpertUser user = (ExpertUser) session.getAttribute("user");
        if (eu!=null){
            eu.setId(user.getId());
            eu.setAuthentication(1+"");
            int result=myService.updataMyMessage(eu);
            if (result!=1){
                return ServiceResult.failure("认证失败");
            }
        }
        return ServiceResult.success("资料上传成功，请等待人工审核结果！");
    }

    @GetMapping("/getMoneySumDetail")
    public String getMoneySumDetail(HttpSession session,ModelMap map){
        ExpertUser user = (ExpertUser) session.getAttribute("user");

        ExpertRecommend er = new ExpertRecommend();
        er.setExpertId(user.getId());
        er.setAdopt(1+"");

        List<fyPurchase> list=myService.getExpertRecommendByExpertIdAndAdopt(er);
        list=schuleService.changeOrgNameToZhongwen(list);
        map.addAttribute("list",list);
        return "expert_moneyDetail";
    }

    @GetMapping("/reHistory")
    public String reHistory(HttpSession session,ModelMap map){
        ExpertUser user = (ExpertUser) session.getAttribute("user");

        ExpertRecommend er = new ExpertRecommend();
        er.setExpertId(user.getId());

        List<fyPurchase> list=myService.getHistroy(er);
        list=schuleService.changeOrgNameToZhongwen(list);
        map.addAttribute("list",list);
        return "expert_history";
    }
}
