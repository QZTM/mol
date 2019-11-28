package com.mol.purchase.controller.activiti;

import com.mol.config.Constant;
import com.mol.notification.SendNotification;
import com.mol.notification.SendNotificationImp;
import com.mol.purchase.entity.ExpertUser;
import com.mol.purchase.entity.FyQuote;
import com.mol.purchase.entity.Supplier;
import com.mol.purchase.entity.SupplierSalesman;
import com.mol.purchase.entity.activiti.ActHiProcinst;
import com.mol.purchase.entity.dingding.purchase.enquiryPurchaseEntity.PurchaseDetail;
import com.mol.purchase.entity.dingding.solr.fyPurchase;
import com.mol.purchase.service.activiti.ActService;
import com.mol.purchase.entity.dingding.login.AppAuthOrg;
import com.mol.purchase.entity.dingding.login.AppUser;
import com.mol.purchase.service.token.TokenService;
import com.mol.purchase.util.JWTUtil;
import com.mol.sms.SendMsmHandler;
import com.mol.sms.XiaoNiuMsm;
import com.mol.sms.XiaoNiuMsmTemplate;
import entity.ServiceResult;
import entity.dd.DDUser;
import lombok.extern.java.Log;
import org.activiti.bpmn.model.BpmnModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * ClassName:ActController
 * Package:com.purchase.controller.activiti
 * Description
 *
 * @date:2019/9/19 15:54
 * @author:yangjiangyan
 */
@RestController
@RequestMapping("/ac")
@Log
public class
ActController {
    private static final Logger logger=LoggerFactory.getLogger(ActController.class);

    @Autowired
    ActService actService;
    @Autowired
    SendNotification sendNotificationImp;
    @Autowired
    private TokenService tokenService;

    private SendMsmHandler sendMsmHandler = SendMsmHandler.getSendMsmHandler();


    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    @ResponseBody
    public String hello(){
        return "hello world?";
    }

    /**
     * 部署流程实例
     * @param name  自定义实例名称
     * @param processId  流程id（key）
     * @param processName   流程name
     * @param orgId   公司id
     * @return
     */
    @RequestMapping(value = "/deploy",method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult deploy(String name, String processId, String processName, String orgId){
        //数据库查询出审核人员，用来构建bpmnMode对象
        if (orgId == null){
            return ServiceResult.failure("公司不得为空");
        }
        AppAuthOrg org=actService.findappAuthOrgByOrgId(orgId);

        List<String> list= new ArrayList<>();
        String aproveString = org.getPurchaseApproveList();
        if (aproveString!=null){
            String[] split = aproveString.split(",");
            for (String s : split) {
                list.add(s);
            }
        }
        log.info("流程部署参与人员list："+list);
        BpmnModel model = actService.getModel(processId, processName, list);


        //addBpmnModel方式部署工作流
        actService.deployByModel(model,name);
        return ServiceResult.success("流程实例已部署");
    }

    /**
     * 启动流程实例
     * @param processKey 流程实例Key
     * @param businessKey 业务对象id(订单id)
     * @return
     */
    @PostMapping("/start")
    public ServiceResult start(@RequestParam String processKey,@RequestParam String businessKey,HttpServletRequest request){

        actService.startProcessInstance(processKey,businessKey);
        //设置订单的进入审批环节的时间
        actService.updataPurchaseApprovalStartTime(businessKey);

        //发送通知
        DDUser user = JWTUtil.getUserByRequest(request);
        sendNotificationImp.sendOaFromE(user.getUserid(),user.getName(),tokenService.getToken(), Constant.AGENTID);

        //发送短信通知
        sendMsmHandler.sendMsm(XiaoNiuMsm.SIGNNAME_MEYG, XiaoNiuMsmTemplate.提醒领导审批订单模板(),user.getMobile());
        log.info("----审批流程已经启动----");

        return ServiceResult.success("流程实例已启动");
    }

    //查询当前个人任务
    @RequestMapping(value = "/task",method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult taskQuery(@RequestParam String assignee,int pageNum,int pageSize){
        //获取当前用户，
        logger.info("查询个人任务的id："+assignee);
        return ServiceResult.success(actService.getTask(assignee,pageNum,pageSize));
    }

    //完成个人任务
    @PostMapping("/complete")
    public ServiceResult complite(@RequestParam String taskId,@RequestParam String processInsId, @RequestParam String result, @RequestParam String comment, HttpServletRequest request){
        Map<String, Object> variables=new HashMap<>();
        variables.put("result",result);
        //获取登录人姓名

        DDUser user = JWTUtil.getUserByRequest(request);
        String name = user.getName();
        logger.info("登录人："+user);
        actService.completeTask(taskId,processInsId,variables,comment,name);



        if (result.equals("pass")){
            logger.info("当前任务通过");
            //去获取下一个审批人的id
            String sendUserId=actService.getNextSendUserId(user);



            if (sendUserId!="" || sendUserId==null){
                logger.info("当前任务完成，向下一审批人发通知:"+sendUserId);
                //给下个任务处理人发通知和短信
                AppUser appUserById = actService.findAppUserById(sendUserId);
                //发送钉钉通知
                sendNotificationImp.sendOaFromE(appUserById.getDdUserId(),appUserById.getUserName(),tokenService.getToken(),Constant.AGENTID);
                //发送短信通知
                sendMsmHandler.sendMsm(XiaoNiuMsm.SIGNNAME_MEYG, XiaoNiuMsmTemplate.提醒领导审批订单模板(),appUserById.getMobile());
            }else {
                logger.info("审批通过");

                //审批任务完成
                //查询订单相关信息
                //1.订单ID
                ActHiProcinst hiProcinst=actService.getActHiprocinstByProcInstId(processInsId);
                //2.订单
                fyPurchase pur=actService.findPurchaseById(hiProcinst.getBusinessKey());
                //3.订单详情
                List<PurchaseDetail> detailList =actService.findPurchaseDetailListByPurId(pur.getId());
                //修改选中的专家推荐表中的采纳状态
                actService.updataExpertRecommendChecked(pur.getId(),detailList);
                //修改未选中的专家推荐表中的采纳状态
                actService.updataExpertRecommendNotChecked(pur.getId());



                //保存订单，选中的供应商，是否支付
                actService.saveQuotePayresult(pur);
                //设置订单结束审批的时间
                actService.updataPurchaseApprovalEndTime(pur.getId());


                //1.给采购部门主管发短信，通知

                //2.给选中的专家发短信，通知
                ListenableFuture<Integer> expertSendMessage = actService.getExpertSendMessage(detailList, sendMsmHandler, XiaoNiuMsmTemplate.给专家推送评审成功结果模板());
                //3.给发起采购的采购人员发短信，通知
                ListenableFuture<Integer> auSendMessage = actService.getAuSendMessage(pur.getStaffId(), sendMsmHandler, XiaoNiuMsmTemplate.推送中标结果模板());
                //4.给供应商下的报价人发短信，通知
                ListenableFuture<Integer> saleManSendMessage = actService.getSaleManSendMessage(detailList, sendMsmHandler, XiaoNiuMsmTemplate.推送中标结果模板());
            }
        }else{
            //审批拒绝
            logger.info("审批拒绝");
            //查询订单相关信息
            //1.订单ID
            ActHiProcinst hiProcinst=actService.getActHiprocinstByProcInstId(processInsId);
            //2.订单
            fyPurchase pur=actService.findPurchaseById(hiProcinst.getBusinessKey());
            //3.订单详情
            List<PurchaseDetail> detailList =actService.findPurchaseDetailListByPurId(pur.getId());
            //修改未选中的专家推荐表中的adopt
            actService.updataExpertRecommendNotChecked(pur.getId());
            //1.给采购部门主管发短信，通知
            //2.给选中的专家发短信，通知
            ListenableFuture<Integer> expertSendMessage = actService.getExpertSendMessage(detailList, sendMsmHandler, XiaoNiuMsmTemplate.给专家发送评审失败结果模板());
            //3.给发起采购的采购人员发短信，通知
            ListenableFuture<Integer> auSendMessage = actService.getAuSendMessage(pur.getStaffId(), sendMsmHandler, XiaoNiuMsmTemplate.推送未中标结果模板());
            //4.给供应商下的报价人发短信，通知
            ListenableFuture<Integer> saleManSendMessage = actService.getSaleManSendMessage(detailList, sendMsmHandler, XiaoNiuMsmTemplate.推送未中标结果模板());

        }


        return ServiceResult.success("任务已完成");
    }

    //查询该实例的所有批注以及批注人
    @GetMapping("/comments")
    public ServiceResult comments(@RequestParam String taskId){
        return ServiceResult.success(actService.getCommentList(taskId));
    }
    //查询历史任务
    @GetMapping("/his")
    public ServiceResult getHisTask(String assignee, String processId){
        return ServiceResult.success(actService.getHisTask(assignee,processId));
    }

    //查询流程状态
    @GetMapping("/status")
    public ServiceResult status(@RequestParam String processInstanceId){
        String status = actService.getStatus(processInstanceId);
        return ServiceResult.success(status);
    }

    //查询当前人的组任务
    @GetMapping("/group")
    public ServiceResult getGroupTask(@RequestParam String assignee){
        return ServiceResult.success(actService.getGroupTask(assignee));
    }

    //查询组任务的候选者
    @GetMapping("/canditate")
    public ServiceResult getAll(@RequestParam String taskId){
        return ServiceResult.success(actService.getAllAssignee(taskId));
    }

    //拾取组任务
    @PostMapping("/claim")
    public ServiceResult claim(@RequestParam String taskId, @RequestParam String assignee){
        actService.claim(taskId,assignee);
        return ServiceResult.success("任务已拾取");
    }


}
