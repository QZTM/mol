package com.mol.purchase.service.activiti;

import com.github.pagehelper.PageHelper;
import com.mol.config.Constant;
import com.mol.notification.SendNotification;
import com.mol.purchase.config.ExecutorConfig;
import com.mol.purchase.entity.ExpertUser;
import com.mol.purchase.entity.FyQuote;
import com.mol.purchase.entity.QuotePayresult;
import com.mol.purchase.entity.SupplierSalesman;
import com.mol.purchase.entity.activiti.ActHiProcinst;
import com.mol.purchase.entity.activiti.TaskDTO;
import com.mol.purchase.entity.dingding.login.AppAuthOrg;
import com.mol.purchase.entity.dingding.login.AppUser;
import com.mol.purchase.entity.dingding.purchase.enquiryPurchaseEntity.PurchaseDetail;
import com.mol.purchase.entity.dingding.solr.fyPurchase;
import com.mol.purchase.mapper.newMysql.*;
import com.mol.purchase.mapper.newMysql.dingding.activiti.ActHiCommentMapper;
import com.mol.purchase.mapper.newMysql.dingding.activiti.ActHiProcinstMapper;
import com.mol.purchase.mapper.newMysql.dingding.purchase.fyPurchaseDetailMapper;
import com.mol.purchase.mapper.newMysql.dingding.purchase.fyPurchaseMapper;
import com.mol.purchase.service.token.TokenService;
import com.mol.sms.SendMsmHandler;
import com.mol.sms.XiaoNiuMsm;
import com.mol.sms.XiaoNiuMsmTemplate;
import entity.dd.DDUser;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.validation.ProcessValidator;
import org.activiti.validation.ProcessValidatorFactory;
import org.activiti.validation.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import com.mol.purchase.mapper.newMysql.dingding.org.AppOrgMapper;
import com.mol.purchase.mapper.newMysql.dingding.user.AppUserMapper;
import org.springframework.util.concurrent.ListenableFuture;
import util.IdWorker;
import util.TimeUtil;


import java.util.*;

/**
 * ClassName:ActService
 * Package:com.purchase.service.activiti
 * Description
 *
 * @date:2019/9/19 15:57
 * @author:yangjiangyan
 */
@Service
public class ActService {
    //注入为我们自动配置好的服务
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    HistoryService historyService;
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    ProcessEngineConfiguration processEngineConfiguration;
    @Autowired
    ProcessEngineFactoryBean processEngine;
    @Autowired
    ProcessEngine processEngines;
    @Autowired
    private AppOrgMapper appOrgMapper;
    @Autowired
    private AppUserMapper appUserMapper;
    @Autowired
    private ActHiProcinstMapper actHiProcinstMapper;
    @Autowired
    private fyPurchaseMapper purchaseMapper;
    @Autowired
    private fyPurchaseDetailMapper purchaseDetailMapper;
    @Autowired
    private ExpertUserMapper expertUserMapper;
    @Autowired
    private ExpertRecommendMapper expertRecommendMapper;
    @Autowired
    FyQuoteMapper quoteMapper;
    @Autowired
    BdSupplierSalesmanMapper supplierSalesmanMapper;
    @Autowired
    SendNotification sendNotificationImp;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private QuotePayresultMapper quotePayresultMapper;



    private  Logger actLogger = LoggerFactory.getLogger(ActService.class);
    /**
     * 部署流程实例
     * @param model 流程模型
     */
    public void deployByModel(BpmnModel model,String name) {
        Deployment deploy = repositoryService.createDeployment().addBpmnModel(name+"bpmn20.xml", model).deploy();
        System.out.println("部署："+deploy.getKey());
        System.out.println("部署："+deploy.getId());
        System.out.println("部署："+deploy.getName());
    }
    /**
     * 部署流程实例(按照 文件部署)
     * @param name
     * @param bpmnPath
     * @param pngPath
     */
    public void deploy(String name,String bpmnPath,String pngPath) {
        processEngines.getRepositoryService()
                .createDeployment()
                .name(name)
                .addClasspathResource(bpmnPath)
                .addClasspathResource(pngPath)
                .deploy();
    }

    /**
     * 启动流程实例
     * @param key 流程的Id
     */
    public void startProcessInstance(String key,String businessKey) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key, businessKey);
        System.out.println("buskey:"+businessKey);
        System.out.println("流程实例 id:"+processInstance.getId());
        System.out.println("ProcessInstanceId:"+processInstance.getProcessInstanceId());
        System.out.println("流程定义 id: "+processInstance.getDeploymentId());
    }

    /**
     * 查询个人任务
     * @param assignee 办理人
     * @return
     */
    public List<TaskDTO> getTask(String assignee,int pageNum,int pageSize) {
        List<Task> list=taskService.createTaskQuery().taskAssignee(assignee).list();
        System.out.println("任务："+list);
        List<TaskDTO> list1 = new ArrayList<>();
        for (Task task : list){
            //查询执行实例对应的业务id
            ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
            String businessKey = pi.getBusinessKey();

            list1.add(new TaskDTO(task.getId(),task.getName(),task.getProcessInstanceId(),task.getAssignee(),businessKey,task.getCreateTime()));

        }
        return list1;
    }

    /**
     * 完成任务
     * @param map 流程变量可选
     * @param taskId 任务Id
     */
    public void completeTask(String taskId,String processInsId,Map map,String commment,String username) {
        System.out.println("taskId:"+taskId);
        //在session中获取处理人的id，存入
        Authentication.setAuthenticatedUserId(username);//批注人的名称  一定要写，不然查看的时候不知道人物信息

        taskService.addComment(taskId,processInsId,commment);

            taskService.complete(taskId,map);
    }

    /**
     * 个人历史任务查询
     * @param assignee  办理人
     * @param processId 流程实例Id
     * @return
     */
    public List<TaskDTO> getHisTask(String assignee, String processId) {
        List<HistoricTaskInstance> list=null;
        if (assignee == null && processId != null)
            list=  historyService.createHistoricTaskInstanceQuery().processInstanceId(processId).list();
        if(assignee != null && processId == null)
            list= historyService.createHistoricTaskInstanceQuery().taskAssignee(assignee).list();
        if (assignee != null && processId != null)
            list=historyService.createHistoricTaskInstanceQuery().taskAssignee(assignee).processInstanceId(processId).list();


        List<TaskDTO> list1 = new ArrayList<>();
        for (HistoricTaskInstance task : list)
            list1.add(new TaskDTO(task.getId(),task.getName(),task.getProcessInstanceId(),task.getAssignee(),null,task.getCreateTime(),task.getEndTime()));
        return list1;
    }

    /**
     * 查询流程状态
     * @param processInstanceId 流程实例Id
     */
    public String getStatus(String processInstanceId) {
        ProcessInstance pi=runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if(pi!=null){
            return "流程还在执行";
        }
        else return "流程已结束";
    }

    /**
     * 查询当前人的组任务
     * @param assignee
     * @return
     */
    public List<TaskDTO> getGroupTask(String assignee) {
        List<Task> list=  taskService.createTaskQuery().taskCandidateUser(assignee).list();
        List<TaskDTO> list1 = new ArrayList<>();
        for (Task task : list)
            list1.add(new TaskDTO(task.getId(),task.getName(),task.getProcessInstanceId(),task.getAssignee(),null,task.getCreateTime()));
        return list1;

    }

    /**
     * 查询任务的候选者
     * @param taskId
     * @return
     */
    public List getAllAssignee(String taskId) {
        List<IdentityLink> allAssignee=taskService.getIdentityLinksForTask(taskId);
        List list=new ArrayList();
        for (IdentityLink identityLink:allAssignee) {
            list.add(identityLink.getUserId());
        }
        return list;
    }

    /**
     * 拾取组任务
     * @param taskId   指定任务Id
     * @param assignee  指定办理人
     */
    public void claim(String taskId, String assignee) {
        taskService.claim(taskId,assignee);
    }


    //生成model实例
    public BpmnModel getModel(String _processId,String _processName,List<String> _list) {
        String processId=_processId;
        String processName=_processName;
        List<String> list=_list;
        if (processId==null || processName==null|| list==null ){
            return null;
        }
        //记录线的数量
        int count=1;

        BpmnModel bpmnModel = new BpmnModel();

        //设置process
        Process  process=new Process();
        process.setId(processId);
        process.setName(processName);

        // 创建开始结束节点 并添加至process
        StartEvent startEvent= new StartEvent();
        startEvent.setId("startevent1");
        startEvent.setName("Start");

        EndEvent endEvent=new EndEvent();
        endEvent.setId("endevent1");
        endEvent.setName("End");

        process.addFlowElement(startEvent);
        process.addFlowElement(endEvent);

        //创建审核结果节点
        ServiceTask serviceTask1 = new ServiceTask();
        serviceTask1.setId("servicetask1");
        serviceTask1.setName("审核通过");

        String imp ="expression";
        serviceTask1.setImplementationType(imp);

        serviceTask1.setImplementation("#{leaveService.changeStatus(execution,'7')}");


        ServiceTask serviceTask2 = new ServiceTask();
        serviceTask2.setId("servicetask2");
        serviceTask2.setName("审核未通过");
        serviceTask2.setImplementationType("expression");
        serviceTask2.setImplementation("#{leaveService.changeStatus(execution,'8')}");




        process.addFlowElement(serviceTask1);
        process.addFlowElement(serviceTask2);



        //创建任务节点 并添加至process
        if (list.size()==0 || list==null){
            return null;
        }
        for (int i=0 ;i<list.size();i++){
            //人物节点
            UserTask userTask = genarateUserTask("usertask"+(i+1), "审核人"+(i+1));
            userTask.setAssignee(list.get(i));
            userTask.setFormKey("result");
            process.addFlowElement(userTask);

            //排他网管
            ExclusiveGateway exclusiveGateway = genarateExclusiveGateway("exclusivegateway"+(i+1), "Exclusive Gateway"+(i+1));
            List<SequenceFlow> out=new ArrayList<>();
            SequenceFlow se =new SequenceFlow();
            se.setId("sf"+i);
            se.setName("sf"+i);
            out.add(se);
            exclusiveGateway.setOutgoingFlows(out);
            process.addFlowElement(exclusiveGateway);

            //线
            if(i==0){
                SequenceFlow sequenceFlow1 = genarateSequenceFlow("flow" + count++, "", startEvent.getId(), "usertask"+(i+1), "");
                SequenceFlow sequenceFlow2 = genarateSequenceFlow("flow" + count++, "", "usertask"+(i+1), exclusiveGateway.getId(), "");
                SequenceFlow sequenceFlow3 = genarateSequenceFlow("flow" + count++, "",  exclusiveGateway.getId(),serviceTask2.getId(), "${result=='refuse'}");
                process.addFlowElement(sequenceFlow1);
                process.addFlowElement(sequenceFlow2);
                process.addFlowElement(sequenceFlow3);
            }
            if (i==list.size()-1){
                SequenceFlow sequenceFlow1 = genarateSequenceFlow("flow" + count++, "", "exclusivegateway"+i, "usertask"+(i+1), "${result=='pass'}");
                SequenceFlow sequenceFlow2 = genarateSequenceFlow("flow" + count++, "", "usertask"+(i+1),"exclusivegateway"+(i+1), "");
                SequenceFlow sequenceFlow3 = genarateSequenceFlow("flow" + count++, "", "exclusivegateway"+(i+1),serviceTask1.getId(), "${result=='pass'}");
                SequenceFlow sequenceFlow4 = genarateSequenceFlow("flow" + count++, "", "exclusivegateway"+(i+1),serviceTask2.getId(), "${result=='refuse'}");
                process.addFlowElement(sequenceFlow1);
                process.addFlowElement(sequenceFlow2);
                process.addFlowElement(sequenceFlow3);
                process.addFlowElement(sequenceFlow4);
            }
            if (i>0 && i<list.size()-1){
                SequenceFlow sequenceFlow1 = genarateSequenceFlow("flow" + count++, "", "exclusivegateway" + i , "usertask"+(i+1), "${result=='pass'}");
                SequenceFlow sequenceFlow2 = genarateSequenceFlow("flow" + count++, "", "usertask"+(i+1), "exclusivegateway" +(i+1), "");
                SequenceFlow sequenceFlow3 = genarateSequenceFlow("flow" + count++, "",  "exclusivegateway" + (i+1),serviceTask2.getId(), "${result=='refuse'}");
                process.addFlowElement(sequenceFlow1);
                process.addFlowElement(sequenceFlow2);
                process.addFlowElement(sequenceFlow3);
            }

        }

        //线  审核结果-- 结束event
        SequenceFlow s1 = genarateSequenceFlow("flow" + count++, "", serviceTask1.getId(), endEvent.getId(), "");
        SequenceFlow s2 = genarateSequenceFlow("flow" + count++, "", serviceTask2.getId(), endEvent.getId(), "");
        process.addFlowElement(s1);
        process.addFlowElement(s2);

        bpmnModel.addProcess(process);

        //验证model对象
        BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
        byte[] bytes = bpmnXMLConverter.convertToXML(bpmnModel);
        String s = new String(bytes);
        System.out.println(s);
        ProcessValidatorFactory factory = new ProcessValidatorFactory();
        ProcessValidator va = factory.createDefaultProcessValidator();
        List<ValidationError> validate = va.validate(bpmnModel);
        if (validate.size()>0){
            throw new RuntimeException("创建流程模型出错");
        }

        return bpmnModel;
    }

    /**
     * 创建用户人物节点
     */
    private UserTask genarateUserTask(String id, String name) {
        UserTask userTask = new UserTask();
        userTask.setId(id);
        userTask.setName(name);
        return userTask;
    }
    /**
     * 创建排他网关
     */
    private ExclusiveGateway genarateExclusiveGateway(String id, String name) {
        ExclusiveGateway exclusiveGateway = new ExclusiveGateway();
        exclusiveGateway.setId(id);
        exclusiveGateway.setName(name);
        return exclusiveGateway;
    }
    /**
     * 创建连线
     */
    private SequenceFlow genarateSequenceFlow(String id, String name,String sourceRef, String tartgetRef, String conditionExpression) {
        SequenceFlow sequenceFlow = new SequenceFlow(sourceRef,tartgetRef);
        sequenceFlow.setId(id);
        if(name != null && name != ""){
            sequenceFlow.setName(name);
        }

        if(conditionExpression !=null && conditionExpression != ""){
            sequenceFlow.setConditionExpression(conditionExpression);
        }
        return sequenceFlow;
    }

    //查询所有评论
    public List getCommentList(String taskId) {
        List list=new ArrayList();
        Task task = processEngines.getTaskService()
                .createTaskQuery()
                .taskId(taskId)
                .singleResult();
        String processInstanceId = task.getProcessInstanceId();
        List<HistoricTaskInstance> slist = processEngines.getHistoryService()
                .createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                .list();
        if (slist.size()>0 && slist!= null){
            for (HistoricTaskInstance hti : slist) {
                String id = hti.getId();
                List<Comment> taskComments = taskService.getTaskComments(id);
                list.add(taskComments);
            }
        }
        return list;
    }

    //查询公司信息
    public AppAuthOrg findappAuthOrgByOrgId(String orgId) {
        AppAuthOrg org=new AppAuthOrg();
        org.setId(orgId);
        AppAuthOrg appAuthOrg = appOrgMapper.selectOne(org);
        return appAuthOrg;
    }

    //查询user
    public AppUser getAppUserByUserDingId(String userid) {
        AppUser user = new AppUser();
        user.setDdUserId(userid);
        return  appUserMapper.selectOne(user);

    }

    //查询user
    public AppUser findAppUserById(String sendUserId) {
        AppUser user = new AppUser();
        user.setId(sendUserId);
        return  appUserMapper.selectOne(user);
    }

    public ActHiProcinst getActHiprocinstByProcInstId(String processInsId) {
        return actHiProcinstMapper.getProByProId(processInsId);
    }

    public fyPurchase findPurchaseById(String businessKey) {
        return purchaseMapper.findOneById(businessKey);
    }

    public List<PurchaseDetail> findPurchaseDetailListByPurId(String id) {
        PurchaseDetail t=new PurchaseDetail();
        t.setFyPurchaseId(id);
        return purchaseDetailMapper.select(t);
    }

    public ExpertUser findExpertById(String id) {

        return expertUserMapper.findExpertUserById(id);
    }

    public FyQuote findQuoteById(String s) {
        FyQuote t=new FyQuote();
        t.setId(s);
        return quoteMapper.selectOne(t);
    }

    public SupplierSalesman findSupplierById(String supplierSalesmanId) {
        SupplierSalesman t=new SupplierSalesman();
        t.setId(supplierSalesmanId);
        return supplierSalesmanMapper.selectOne(t);
    }

    public String getNextSendUserId(DDUser user) {
        AppUser appUser=getAppUserByUserDingId(user.getUserid());
        AppAuthOrg org =findappAuthOrgByOrgId(appUser.getAppAuthOrgId());
        String approveString = org.getPurchaseApproveList();
        String sendUserId="";
        //String userid=user.getUserid();
        if (approveString !=null){
            String[] split = approveString.split(",");
            for(int i=0;i<split.length;i++){
                if (split[i].equals(appUser.getId())){
                    if (i ==split.length-1){
                        break;
                    }else {
                        sendUserId=split[i+1];
                        break;
                    }

                }
            }
        }
        return sendUserId;
    }

    //4.专家
    @Async
    public ListenableFuture<Integer> getExpertSendMessage(List<PurchaseDetail> detailList,SendMsmHandler sendMsmHandler,XiaoNiuMsmTemplate templateName) {
        if (detailList.get(0).getExpertId()==null){
            return new AsyncResult<>(0);
        }
        Set<String> expetSet = new HashSet<>();
        for (PurchaseDetail purchaseDetail : detailList) {
            String[] split = purchaseDetail.getExpertId().split(",");
            for (String s : split) {
                expetSet.add(s);
            }
        }
        List <ExpertUser> userList=new ArrayList<>();
        for (String s : expetSet) {
            ExpertUser exertUser=findExpertById(s);
            userList.add(exertUser);
        }
        try{
            for (ExpertUser expertUser : userList) {
                //发送钉钉通知
                sendNotificationImp.sendOaFromExpert(expertUser.getId(), Constant.AGENTID_EXPERT,tokenService.getToken());
                //发送短信通知
                sendMsmHandler.sendMsm(XiaoNiuMsm.SIGNNAME_MEYG, templateName,expertUser.getMobile());
            }
            return new AsyncResult<>(1);
        }catch (Exception e){
            e.printStackTrace();
            return new AsyncResult<>(0);
        }

    }

    //报价人员
    @Async
    public ListenableFuture<Integer> getSaleManSendMessage(List<PurchaseDetail> detailList, SendMsmHandler sendMsmHandler, XiaoNiuMsmTemplate templateName) {

        Set<String> supplierSet=new HashSet<>();
        for (PurchaseDetail purchaseDetail : detailList) {
            supplierSet.add(purchaseDetail.getQuoteId());
        }
        List<SupplierSalesman> saleManList=new ArrayList<>();
        for (String s : supplierSet) {
            FyQuote quo=findQuoteById(s);
            SupplierSalesman salesman =findSupplierById(quo.getSupplierSalesmanId());
            saleManList.add(salesman);
        }
        try{
            for (SupplierSalesman salesman : saleManList) {
                //发送钉钉通知
                sendNotificationImp.sendOaFromThird(salesman.getDdUserId(),Constant.AGENTID_THIRDPLAT,tokenService.getToken());
                //发送短信通知
                sendMsmHandler.sendMsm(XiaoNiuMsm.SIGNNAME_MEYG, XiaoNiuMsmTemplate.推送未中标结果模板(),salesman.getPhone());
            }
            return new AsyncResult<>(1);
        }catch (Exception e){
            e.printStackTrace();
            return new AsyncResult<>(0);
        }
    }

    @Async
    public ListenableFuture<Integer> getAuSendMessage(String staffId,SendMsmHandler sendMsmHandler,XiaoNiuMsmTemplate templateName) {
        AppUser au = findAppUserById(staffId);
        try{
            //发送钉钉通知
            sendNotificationImp.sendOaFromE(au.getId(),au.getUserName(),tokenService.getToken(),Constant.AGENTID_EXPERT);
            //发送短信通知
            sendMsmHandler.sendMsm(XiaoNiuMsm.SIGNNAME_MEYG, templateName,au.getMobile());
            return new AsyncResult<>(1);
        }catch (Exception e){
            e.printStackTrace();
            return new AsyncResult<>(0);
        }
    }

    public void updataExpertRecommendChecked(String  pur, List<PurchaseDetail> detailList) {
        if (detailList==null || detailList.size()==0){
            return;
        }
        String expertIdString = detailList.get(0).getExpertId();
        String[] expertIdArr = expertIdString.split(",");
        for (String expertId : expertIdArr) {
            expertRecommendMapper.updataAdoptByPurIdAndExpertId(pur,expertId);
        }
    }

    public void updataExpertRecommendNotChecked(String pur) {
        expertRecommendMapper.updataAdoptNotChecked(pur);
    }

    public void saveQuotePayresult(fyPurchase pur, List<PurchaseDetail> detailList) {
        if (detailList!=null && detailList.size()>0){
            String[] split = detailList.get(0).getExpertId().split(",");
            for (String s : split) {
                QuotePayresult t=new QuotePayresult();
                t.setId(new IdWorker(2,2).nextId()+"");
                t.setPurchaseId(pur.getId());
                t.setSupplierId(s);
                t.setPayResult(0+"");
                quotePayresultMapper.insert(t);
            }
        }
    }

    public void updataPurchaseApprovalStartTime(String purId) {
        actLogger.info("审批环节时间设定");
        String nowDateTime = TimeUtil.getNowDateTime();
        purchaseMapper.updataApprovalStartTime(purId,nowDateTime);
    }

    public void updataPurchaseApprovalEndTime(String purId) {
        actLogger.info("审批结束时间设定");
        String nowDateTime = TimeUtil.getNowDateTime();
        purchaseMapper.updataApprovalEndTime(purId,nowDateTime);
    }
}
