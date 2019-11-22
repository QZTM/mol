package com.mol.expert.controller.my;

import com.mol.expert.controller.microApp.ExpertLoginController;
import com.mol.expert.entity.dingding.solr.fyPurchase;
import com.mol.expert.entity.expert.ExpertRecommend;
import com.mol.expert.entity.expert.ExpertUser;
import com.mol.expert.mapper.newMysql.expert.ExpertRecomendMapper;
import com.mol.expert.service.expert.MyService;
import com.mol.expert.service.expert.SchuleService;
import com.mol.expert.util.ServiceResult;
import entity.BdMarbasclass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(MyController.class);

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

    /**
     * 查询专家个人信息
     * @param session
     * @param map
     * @return
     */
    @GetMapping("/one")
    public String getMyMessage(HttpSession session, ModelMap map){
        ExpertUser user = (ExpertUser) session.getAttribute("user");
        logger.info("method:one describe:session中获取专家信息  result:"+user);

        //查询参与评审
        int reviewCount=myService.selectReviewRecommend(user.getId());
        //查询成功的
        int successCount=myService.selectSuccessRecommend(user.getId());
        //查询参与的所有已完成的订单
        int suAndFaCount=myService.selectSuccessAndFailRecommend(user.getId());
        Double rate=(double)successCount/suAndFaCount*100;
        if (Integer.valueOf(user.getReviewNumber())!=reviewCount){
            logger.info("method:one describe:更新数据库专家推荐成功数量和已完成的订单数量  result:"+user.getReviewNumber());
            //保存到数据库
            myService.updataExpertUser(user.getId(),reviewCount,successCount,suAndFaCount);


            user.setReviewNumber(reviewCount+"");
            user.setPass(successCount+"");
            user.setPassRate(rate+"");
            logger.info("method:one describe:更新session中专家的个人信息  result:"+user);
            session.setAttribute("user",user);
        }

        map.addAttribute("my",user);
        logger.info("method:one describe:回传专家的个人信息  result:"+user);
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
            logger.info("method:authenticate    describe:session中获取专家信息失败  result:"+user);
            throw new RuntimeException("用户信息加载失败，请重试！");
        }
        //第一级的行业类比
        List<BdMarbasclass> firstBdMar = myService.getFirstBdMar();
        logger.info("method:authenticate    describe:第一级的行业类比  result:"+firstBdMar);
        map.addAttribute("bdMar",firstBdMar);
        logger.info("method:authenticate    describe:session中获取专家信息  result:"+user);
        map.addAttribute("my",user);
        return "expert_authenticate";
    }

    /**
     * 专家认证--保存图片
     * @param file
     * @param whichImg
     * @param session
     * @return
     */
    @PostMapping("/uploadImage")
    @ResponseBody
    public ServiceResult uploadImage(MultipartFile file, String whichImg, HttpSession session){
        if (file ==null){
            logger.info("method:uploadImage    describe:接受认证图片失败  result:"+file);
            throw new RuntimeException("没有图片上传");
        }
        ExpertUser user = (ExpertUser) session.getAttribute("user");
        logger.info("method:uploadImage    describe:session中获取专家信息  result:"+user);

        switch (whichImg){
            case FRONT_OF_IDCARD:
                try {
                    user.setFrontOfId(file.getBytes());
                } catch (IOException e) {
                    logger.error("method:uploadImage  设置身份证正面失败！");
                    e.printStackTrace();
                }
                break;
            case REVERSE_OF_IDCARD:
                try {
                    user.setReverseOfId(file.getBytes());
                } catch (IOException e) {
                    logger.error("method:uploadImage  设置身份证反面失败！");
                    e.printStackTrace();
                }
                break;
            case FRONT_OF_BUSINESS:
                try {
                    user.setFrontOfBusiness(file.getBytes());
                } catch (IOException e) {
                    logger.error("method:uploadImage  设置名片正面失败！");
                    e.printStackTrace();
                }
                break;
            case REVERSE_OF_BUSINESS:
                try {
                    user.setReverseOfBusiness(file.getBytes());
                } catch (IOException e) {
                    logger.error("method:uploadImage  设置名片反面失败！");
                    e.printStackTrace();
                }
                break;
            case OTHER_DOCUMENT_ONE:
                try {
                    user.setOtherDocumentsOne(file.getBytes());
                } catch (IOException e) {
                    logger.error("method:uploadImage  设置其他证件1失败！");
                    e.printStackTrace();
                }
                break;
            case OTHER_DOCUMENT_TWO:
                try {
                    user.setOtherDocumentsTwo(file.getBytes());
                } catch (IOException e) {
                    logger.error("method:uploadImage  设置其他证件2失败！");
                    e.printStackTrace();
                }
                break;
        }
        int result=myService.updataImge(user);
        if (result != 1) {
            logger.error("method:uploadImage  describe:保存照片失败！  result:"+result);
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
        logger.info("method:subMessage    describe:session中获取专家信息  result:"+user);

        if (eu!=null){
            eu.setId(user.getId());
//            eu.setAuthentication(0+"");
            int result=myService.updataMyMessage(eu);
            if (result!=1){
                logger.info("method:subMessage    describe:认证信息的文字信息失败  result:"+result);
                return ServiceResult.failure("认证失败");
            }
            //查询数据库
            ExpertUser  userNew=myService.getMyInfo(user.getId());

            logger.info("method:subMessage    describe:session中更新专家信息  result:"+userNew);
            session.setAttribute("user",userNew);
        }
        return ServiceResult.success("资料上传成功，请等待人工审核结果！");
    }

    /**
     * 查询奖励细节
     * @param session
     * @param map
     * @return
     */
    @GetMapping("/getMoneySumDetail")
    public String getMoneySumDetail(HttpSession session,ModelMap map){
        ExpertUser user = (ExpertUser) session.getAttribute("user");
        logger.info("method:getMoneySumDetail    describe:session中获取专家信息  result:"+user);

        List<fyPurchase> list=myService.getExpertRecommendByExpertIdAndAdopt(user.getId());
        list=schuleService.changeOrgNameToZhongwen(list);
        logger.info("method:getMoneySumDetail    describe:获取获得奖励的订单list  result:"+list);

        map.addAttribute("list",list);
        return "expert_moneyDetail";
    }

    /**
     * 获取评审历史
     * @param session
     * @param map
     * @return
     */
    @GetMapping("/reHistory")
    public String reHistory(HttpSession session,ModelMap map){
        ExpertUser user = (ExpertUser) session.getAttribute("user");
        logger.info("method:reHistory    describe:session中获取专家信息  result:"+user);

        String expertId = user.getId();

        List<fyPurchase> list=myService.getHistroy(expertId);
        list=schuleService.changeOrgNameToZhongwen(list);
        logger.info("method:reHistory    describe:获取评审历史的订单list  result:"+list);

        map.addAttribute("list",list);
        return "expert_history";
    }
}
