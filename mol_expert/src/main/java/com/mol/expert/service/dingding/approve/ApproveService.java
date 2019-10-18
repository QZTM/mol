package com.mol.expert.service.dingding.approve;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiProcessinstanceCreateRequest;
import com.dingtalk.api.request.OapiProcessinstanceGetRequest;
import com.dingtalk.api.response.OapiProcessinstanceCreateResponse;
import com.dingtalk.api.response.OapiProcessinstanceGetResponse;
import com.mol.expert.entity.dingding.approve.PurchaseApprove;
import com.mol.expert.config.URLConstant;
import com.mol.expert.mapper.newMysql.dingding.approve.ApproveMapper;
import com.mol.expert.service.dingding.login.TokenService;
import com.mol.expert.util.*;
import com.purchase.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 审批接口：
 */
@Service
public class ApproveService{

    private static final Logger bizLogger = LoggerFactory.getLogger(ApproveService.class);

    @Resource
    private ApproveMapper approveMapper;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private IdWorker idWorker;


    /**
     *
     * @param processCode               审批模板唯一识别码
     * @param originatorUserId          发起审批的人id
     * @param deptId                    部门ID
     * @param approveContent            整理好的适应模板的集合
     * @param approvers                 审批人（第一审批人id,第二审批人id......）
     * @param ccList                    抄送人
     * @param pageValue                 页面传送过来的数据对象转换为json
     * @param pa                        数据库对应实体类
     * @return
     */
    public ServiceResult<String> approve(String processCode, String originatorUserId, Long deptId, String approveContent, String approvers, String ccList, String pageValue, PurchaseApprove pa){
        try {
            DefaultDingTalkClient client = new DefaultDingTalkClient(URLConstant.URL_PROCESSINSTANCE_START);
            OapiProcessinstanceCreateRequest request = new OapiProcessinstanceCreateRequest();
            request.setProcessCode(processCode);
            request.setFormComponentValues(approveContent);
            /**
             * 如果想复用审批固定流程，使用或签会签的话，可以不传审批人，具体请参考文档： https://open-doc.dingtalk.com/microapp/serverapi2/ebkwx8
             * 本次quickstart，演示不传审批人的场景266752374326324047
             */
            if(approvers != null){
                request.setApprovers(approvers);
            }
            request.setOriginatorUserId(originatorUserId);
            request.setDeptId(deptId);
            if(ccList != null){
                request.setCcList(ccList);
            }
//            request.setCcPosition("FINISH");
            String token = tokenService.getToken("token");
            System.out.println(token);
            OapiProcessinstanceCreateResponse response = client.execute(request, tokenService.getToken("token"));
            System.out.println(response.getErrcode().longValue());

            if (response.getErrcode().longValue() != 0) {
                return ServiceResult.failure(String.valueOf(response.getErrorCode()), response.getErrmsg());
            }else{
                //存入数据库
                pa.setId(idWorker.nextId()+"");
                pa.setProcessInstanceId(response.getProcessInstanceId());
                pa.setStaffId(originatorUserId);
                pa.setGoodsDetail(pageValue);
                pa.setAuditor(approvers);
                pa.setProcessCode(processCode);
                pa.setStatus(0);
                pa.setCreateTime(TimeUtil.getNow());
                //System.out.println("pa:");
                //System.out.println(pa);
                int saveOrUpdateResult = approveMapper.insert(pa);
                System.out.println("saveOrUpdateResult:"+saveOrUpdateResult);

            }
            return ServiceResult.success(response.getProcessInstanceId());

        } catch (Exception e) {
            String errLog = LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("processInstance", JSON.toJSONString(approveContent)));
            bizLogger.info(errLog,e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }




    /**
     * 根据审批实例id获取审批详情
     * @param instanceId
     * @return
     */
    public ServiceResult<OapiProcessinstanceGetResponse.ProcessInstanceTopVo> getProcessinstanceById(String instanceId) {
        try {
            DingTalkClient client = new DefaultDingTalkClient(URLConstant.URL_PROCESSINSTANCE_GET);
            OapiProcessinstanceGetRequest request = new OapiProcessinstanceGetRequest();
            request.setProcessInstanceId(instanceId);
            OapiProcessinstanceGetResponse response = client.execute(request, tokenService.getToken("token"));

            if (response.getErrcode().longValue() != 0) {
                return ServiceResult.failure(String.valueOf(response.getErrorCode()), response.getErrmsg());
            }
            return  ServiceResult.success(response.getProcessInstance());
        } catch (Exception e) {
            String errLog = LogFormatter.getKVLogData(LogFormatter.LogEvent.END,
                    LogFormatter.KeyValue.getNew("instanceId", instanceId));
            bizLogger.info(errLog,e);
            return ServiceResult.failure(ServiceResultCode.SYS_ERROR.getErrCode(),ServiceResultCode.SYS_ERROR.getErrMsg());
        }
    }


    /**
     * 保存采购审批流
     * @param pa
     * @return
     */
    public ServiceResult saveApprove(PurchaseApprove pa){
        int result = approveMapper.insertSelective(pa);
        if(result == 1){
            return ServiceResult.success(pa.getProcessInstanceId());
        }
        return ServiceResult.failure("");
    }


    /**
     * 根据实例ID更新状态
     * @param pa
     * @return
     */
    public ServiceResult updateApprove(PurchaseApprove pa){
        int updatedCount = approveMapper.updateStatus(pa);
        if(updatedCount == 1){
            System.out.println("更新成功");
            return ServiceResult.success("");
        }
        return ServiceResult.failure("");
    }




}
