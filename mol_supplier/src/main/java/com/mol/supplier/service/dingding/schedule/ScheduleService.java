package com.mol.supplier.service.dingding.schedule;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.dingtalk.api.response.OapiProcessinstanceGetResponse;
import com.mol.supplier.entity.dingding.login.DDUser;
import com.mol.supplier.entity.dingding.purchase.underlinePurchasePageEntity.PageContent;
import com.mol.supplier.entity.dingding.purchase.underlinePurchasePageEntity.PageObj;
import com.mol.supplier.entity.dingding.approve.PurchaseApprove;
import com.mol.supplier.entity.dingding.approve.approveDetail.ApproveDetailOfFormComponentValues;
import com.mol.supplier.entity.dingding.approve.approveDetail.RowValue;
import com.mol.supplier.mapper.dingding.schedule.ScheduleMapper;
import com.mol.supplier.service.dingding.approve.ApproveService;
import entity.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional
public class ScheduleService {

    @Resource
    private ScheduleMapper scheduleMapper;

    @Autowired
    private ApproveService approveService;

    private static final Logger bizLogger = LoggerFactory.getLogger(ScheduleService.class);


    public ServiceResult getProgress(String userId, String state) {
        Map paraMap = new HashMap();
        paraMap.put("userId",userId);
        List<String> states = Arrays.asList(state.split(","));
        paraMap.put("states",states);
        List<PurchaseApprove> pas = scheduleMapper.selectByUserIdAndState(paraMap);
        return ServiceResult.success(pas);
    }




    public String getProgressDetail(String progressId,String userId) {

        //根据id获取审批实例详情（钉钉提供的接口）
//        ServiceResult<OapiProcessinstanceGetResponse.ProcessInstanceTopVo> sr = approveService.getProcessinstanceById(progressId);
//        OapiProcessinstanceGetResponse.ProcessInstanceTopVo pito = (OapiProcessinstanceGetResponse.ProcessInstanceTopVo)sr.getResult();
//        pitoTo(pito);

        PurchaseApprove pa = getPurchaseApproveById(progressId,userId);
        String pageObj = pa.getGoodsDetail();
        System.out.println(pa);
        System.out.println(pageObj);
        pageObj=pageObj.substring(0,pageObj.length()-1);
        pageObj += ",\"status\":\""+pa.getStatus()+"\"}";
        System.out.println(pageObj);
        return pageObj;
    }


    /**
     * 根据审批提交人和审批实例ID获取审批详情
     * @param progressId
     * @param userId
     * @return
     */
    public PurchaseApprove getPurchaseApproveById(String progressId,String userId){
        //去数据库查找
        Map paraMap = new HashMap();
        paraMap.put("progressInstanceId",progressId);
        paraMap.put("userId",userId);
        PurchaseApprove pa = scheduleMapper.selectDetailByIds(paraMap);
        return pa;
    }



    /**
     * 把钉钉返回的数据对象转换为前端页面需要的对象
     * @param pito
     */
    public static void pitoTo(OapiProcessinstanceGetResponse.ProcessInstanceTopVo pito){

        PageContent pc = new PageContent();
        List<PageObj> pageObjs = pc.getPageObj();
        List<DDUser> users = pc.getApproves();
        String remarks = pc.getRemarks();


        List<OapiProcessinstanceGetResponse.FormComponentValueVo> fvos = pito.getFormComponentValues();
        for(int i=0;i<fvos.size();i++){
            OapiProcessinstanceGetResponse.FormComponentValueVo afcvo = fvos.get(i);

            System.out.println("fvos.get"+i);

            String name = afcvo.getName();
            System.out.println("afcvo.name:"+name);


                String value = afcvo.getValue();
                System.out.println("afcvo.value:"+value);

                if(value.startsWith("[")){
                    System.out.println("value.startWith  [ ");
                    JSONArray jsonArray=JSONArray.parseArray(value);
                    ApproveDetailOfFormComponentValues as = JSONArray.toJavaObject((JSON) jsonArray.get(0),ApproveDetailOfFormComponentValues.class);
                    List<RowValue> vals = as.getRowValue();
                }
        }

//                if(jsonArray != null && jsonArray.size()>0){
//
//                }
//
//
//                if(vals != null){
//                    System.out.println("vals.size()..."+vals.size());
//                    for(RowValue rv:vals){
//                        System.out.println(rv.getLabel()+" : "+rv.getValue());
//                    }
//                }


//        List<String> approverUserids = pito.getApproverUserids();
//        String approveUserIds = "";
//        for(int i=0;i<approverUserids.size();i++){
//            approveUserIds+=approverUserids.get(i);
//            if(i!=approverUserids.size()-1){
//                approveUserIds+=",";
//            }
//        }

//        String state = pito.getStatus();
//        bizLogger.info("pito.state:"+state);
//
//        List<OapiProcessinstanceGetResponse.OperationRecordsVo> orvos = pito.getOperationRecords();
//        for(OapiProcessinstanceGetResponse.OperationRecordsVo orvo:orvos){
//            String operationType = orvo.getOperationType();
//            bizLogger.info("orvo.operationType:"+operationType);
//
//            String operationResult = orvo.getOperationResult();
//            bizLogger.info("orvo.operationResult:"+operationResult);
//
//            Date date = orvo.getDate();
//            bizLogger.info("orvo.date:"+date);
//
//            String remark = orvo.getRemark();
//            bizLogger.info("orvo.remark:"+remark);
//
//            String userId = orvo.getUserid();
//            bizLogger.info("orvo.userId:"+userId);
//        }
//
//
//        String originatorUserid = pito.getOriginatorUserid();
//        bizLogger.info("pito.originatorUserid:"+originatorUserid);
//
//        String result = pito.getResult();
//        bizLogger.info("pito.result:"+result);
//
//        List<String> approverUserids = pito.getApproverUserids();
//        for(String str:approverUserids){
//            bizLogger.info("循环approveUserIds:..str:"+str);
//        }
//        List<String> attachedProcessInstanceIds = pito.getAttachedProcessInstanceIds();
//        for(String str:attachedProcessInstanceIds){
//            bizLogger.info("循环attachedProcessInstanceIds。。。str："+str);
//        }
//
//        String bizAction = pito.getBizAction();
//        bizLogger.info("pito.bizAction:"+bizAction);
//
//        String bussinessId = pito.getBusinessId();
//        bizLogger.info("pito.bussinessId:"+bussinessId);
//
//        List<String> ccUserIds = pito.getCcUserids();
//        if(ccUserIds != null && ccUserIds.size()!=0){
//            for(String str:ccUserIds){
//                bizLogger.info("循环ccUserIds:"+str);
//            }
//        }
//
//
//
//
//        List<OapiProcessinstanceGetResponse.TaskTopVo> ttvos = pito.getTasks();
//        for(int i=0;i<ttvos.size();i++){
//            bizLogger.info("循环TaskTopVoList...");
//            OapiProcessinstanceGetResponse.TaskTopVo ttvo = ttvos.get(i);
//
//            String taskId = ttvo.getTaskid();
//            bizLogger.info("ttvo.taskId:"+taskId);
//
//            String taskStatus = ttvo.getTaskStatus();
//            bizLogger.info("ttvo.taskStatus:"+taskStatus);
//
//            String taskResult = ttvo.getTaskResult();
//            bizLogger.info("ttvo.taskResult:"+taskResult);
//
//            Date createTime = ttvo.getCreateTime();
//            bizLogger.info("ttvo.createTime:"+createTime);
//
//            Date finishTime = ttvo.getFinishTime();
//            bizLogger.info("ttvo.finishTime:"+finishTime);
//
//            String userId = ttvo.getUserid();
//            bizLogger.info("ttvo.userId:"+userId);
//            bizLogger.info("输出结束！！！！！");
//
//        }
    }
}
