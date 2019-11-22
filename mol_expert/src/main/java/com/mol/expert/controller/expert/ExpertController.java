package com.mol.expert.controller.expert;

import com.alibaba.fastjson.JSON;
import com.mol.expert.config.BuyChannelResource;
import com.mol.expert.config.OrderStatus;
import com.mol.expert.entity.dingding.login.AppAuthOrg;
import com.mol.expert.entity.dingding.purchase.enquiryPurchaseEntity.PageArray;
import com.mol.expert.entity.dingding.purchase.enquiryPurchaseEntity.PurchaseArray;
import com.mol.expert.entity.dingding.solr.fyPurchase;
import com.mol.expert.entity.expert.ExpertDetail;
import com.mol.expert.entity.expert.ExpertEnter;
import com.mol.expert.entity.expert.ExpertRecommend;
import com.mol.expert.entity.expert.ExpertUser;
import com.mol.expert.entity.thirdPlatform.FyQuote;
import com.mol.expert.service.dingding.workBean.TobeNegotiatedService;
import com.mol.expert.service.expert.ExpertService;
import com.mol.expert.service.expert.SchuleService;
import com.mol.expert.util.ServiceResult;
import com.mol.expert.util.StatusUtils;
import entity.BdMarbasclass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import util.TimeUtil;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ClassName:ExpertController
 * Package:com.purchase.controller.expert
 * Description
 *  专家工作台
 * @date:2019/9/29 14:36
 * @author:yangjiangyan
 */
@Controller
@RequestMapping("/expert")
public class ExpertController {

    private static final Logger logger = LoggerFactory.getLogger(ExpertController.class);

    @Autowired
    private ExpertService es;

    @Autowired
    private TobeNegotiatedService tobeNegotiatedService;

    @Autowired
    private SchuleService schuleService;

    /**
     * 专家首页
     * @param modelMap
     * @return
     */
    @GetMapping("/findAll")
    public String findAll(ModelMap modelMap){

        List<ExpertEnter>  eeList=es.findEnter();
        logger.info("method:findAll  describe:查询专家客户端首页物料分类入口    result:"+eeList);
        modelMap.addAttribute("enterList",eeList);

        //查询最新的5个需求
        List<fyPurchase>list=es.findPurListByPageBean(OrderStatus.EXPERTREVIEW+"","true");
        list=schuleService.changeOrgNameToZhongwen(list);
        //预算
        list=es.findSelectionList(list);
        logger.info("method:findAll describe:查询专家客户端首页最新五个需求  result:"+list);

        modelMap.addAttribute("purList",list);
        return "expert_index";
    }

    /**
     * 前往具体类型专家评审的list页面
     * @param pageName
     * @param map
     * @return
     */
    @GetMapping("/findUrlList")
    public String findList(String pageName,  ModelMap map) {
        if (pageName.equals("酒店")){
            pageName="东方怡源";
        }

        List<fyPurchase> purList=new ArrayList<>();
        String status = OrderStatus.EXPERTREVIEW+"";
        String  exper="true";
        if(pageName.equals("加工维修")){
            purList= es.findJWPur(BuyChannelResource.MACHINING,status,exper);
            logger.info("method:findUrlList describe:查询加工维修的订单list  result:"+purList);
        }else{
            //查询最高级分类，获取id
            List<BdMarbasclass> marclassList=es.findMarbasclassTopLevel();
            logger.info("method:findUrlList describe:查询物料最高级分类  result:"+marclassList);

            String pkMarbasclasss="";
            //同pageName 匹配，分配 code ,模糊查询 code like "code%"
            for (BdMarbasclass bdMarbasclass : marclassList) {
                if (pageName.equals(bdMarbasclass.getName())){
                    pkMarbasclasss=bdMarbasclass.getPkMarbasclass();
                    break;
                }
            }
            logger.info("method:findUrlList describe:点击的物料id  result:"+pkMarbasclasss);

            //查询订单，状态为4 专家评审为true
            //根据订单中第一个物料，那个物料id，查询出 对应的pkmaterclass

            purList=es.findPurList(pkMarbasclasss,status,exper);
            logger.info("method:findUrlList describe:点击的物料id查询出订单List  result:"+purList);

        }


        
        purList=schuleService.changeOrgNameToZhongwen(purList);
        purList=es.findSelectionList(purList);
        logger.info("method:findUrlList describe:该物料分类下的订单list  result:"+purList);
        map.addAttribute("pList",purList);
        return "expert_list";
    }

    /**
     * 前往具体订单进行推荐操作
     * @param purId
     * @param map
     * @return
     */
    @GetMapping("/findOne")
    public String findOne(String purId,ModelMap map,HttpSession session ){

        fyPurchase purchase=es.findPurById(purId);

        AppAuthOrg org = es.getOrg(purchase.getOrgId());
        purchase.setOrgId(org.getOrgName());
        purchase = StatusUtils.getStatusIntegerToString(purchase);
        logger.info("method:findOne describe:查询具体订单  result:"+purchase);

        map.addAttribute("pur",purchase);


        //定义最后要传递到页面的list
        List<PurchaseArray> purList = new ArrayList<>();
        //解析json字段
        PageArray pageArray = JSON.parseObject(purchase.getGoodsDetail(), PageArray.class);
        //json字段中的详情集合
        List<PurchaseArray> purchaseArrayList = pageArray.getPurchaseArray();
        for (int i = 0; i < purchaseArrayList.size(); i++) {
            PurchaseArray pur = new PurchaseArray();
            pur.setMaterialId(purchaseArrayList.get(i).getMaterialId());
            pur.setCount(purchaseArrayList.get(i).getCount());
            pur.setItemName(purchaseArrayList.get(i).getItemName());
            pur.setTypeName(purchaseArrayList.get(i).getTypeName());
            pur.setUnit(purchaseArrayList.get(i).getUnit());
            pur.setNorms(purchaseArrayList.get(i).getNorms());
            purList.add(pur);
        }
        logger.info("method:findOne describe:查询具体订单中物料List  result:"+purList);

        map.addAttribute("purList",purList);

        //公司报价
        Map<String, List> quoteMap = tobeNegotiatedService.findQuoteById(purId);
        Map<String ,List<ExpertDetail>> mapa =es.getMap(purList,quoteMap);
        logger.info("method:findOne describe:查询具体订单中公司报价  result:"+mapa);
        map.addAttribute("quoteMap",mapa);


        //是否已经报价
        Boolean reBoolean=false;

        ExpertUser user = (ExpertUser) session.getAttribute("user");
        //查询有没有报价
        ExpertRecommend exre = es.findRecommendByPurIdAndUserId(purId, user.getId());
        logger.info("method:findOne describe:查询有没有报价  result:"+exre);

        //认证状态
        String authentication = user.getAuthentication();
        logger.info("method:findOne describe:查询具体订单中认证状态  result:"+authentication);

        map.addAttribute("authen",authentication);



        if (exre!=null){

            //不为空已经报价
           reBoolean=true;
           //回写报价和选择的信息
            logger.info("method:findOne describe:已经报价，回写报价和选择的信息  result:"+exre);
            map.addAttribute("exre",exre);
        }
        map.addAttribute("isRe",reBoolean);
        return "expert_detail";
    }

    @PostMapping("/save")
    @ResponseBody
    public ServiceResult save(ExpertRecommend er, HttpSession session){
        ExpertUser user = (ExpertUser) session.getAttribute("user");
        logger.info("method:save describe:session中获取专家信息  result:"+user);

        String purId = er.getPurchaseId();
        fyPurchase pur = es.findPurById(purId);
        String status = pur.getStatus();
        if (Integer.parseInt(status) != OrderStatus.EXPERTREVIEW){
            logger.info("method:save describe:订单状态改变，阻止提交推荐  result:"+status);
            return ServiceResult.failureMsg("本次订单推荐已经截止了");
        }


        //保存推荐
        er = es.saveExRecommend(er, user);
        logger.info("method:save describe:保存推荐  result:"+er);

        //该公司报价专家推荐设置 1
        es.changeExpertRecommend(er);
        logger.info("method:save describe:该公司报价专家推荐设置  ");

        //报价中专家数量加 一
        es.addCiShu(er.getPurchaseId(),er.getSupplierId());
        logger.info("method:save describe:报价中专家数量加 一  ");

        return ServiceResult.successMsg("推荐成功");
    }

}
