package com.mol.purchase.controller.dingding.workBench;

import com.mol.purchase.entity.ExpertRecommend;
import com.mol.purchase.entity.ExpertUser;
import com.mol.purchase.entity.FyQuote;
import com.mol.purchase.entity.dingding.login.AppAuthOrg;
import com.mol.purchase.entity.dingding.login.AppUser;
import com.mol.purchase.entity.dingding.purchase.enquiryPurchaseEntity.PurchaseDetail;
import com.mol.purchase.entity.dingding.purchase.workBench.Ucharts;
import com.mol.purchase.entity.dingding.purchase.workBench.toBeNegotiated.NegotiatIng;
import com.mol.purchase.entity.dingding.solr.fyPurchase;
import com.mol.purchase.service.dingding.login.LoginService;
import com.mol.purchase.service.dingding.purchase.StrategyPurchaseService;
import com.mol.purchase.service.dingding.workBean.TobeNegotiatedService;
import entity.ServiceResult;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * ClassName:TobeNegotiatedController
 * Package:com.purchase.controller.dingding.workBench
 * Description
 *      E应用待议价controller
 * @date:2019/8/12 13:19
 * @author:yangjiangyan
 */
@RestController
@CrossOrigin
@RequestMapping("/negotiateding")
@Log
public class TobeNegotiatedController {

    @Autowired
    private TobeNegotiatedService negotiatedService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private StrategyPurchaseService strategyPurchaseService;


    /**
     * 待议价list
     * @param orgId
     * @param status negotiateding
     * @return
     */
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public List<fyPurchase> getList(String orgId,String status,String secondStatus,String userId ,int pageNum,int pageSize){

        List<fyPurchase> list = new ArrayList<>();

        //判断userid 是否是管理员，
        AppAuthOrg appAuthOrg=loginService.AppAuthOrgByOrgId(orgId);
        String mainPersonId = appAuthOrg.getPurchaseMainPerson();

        AppUser appUser = loginService.one(mainPersonId);//不测试可删除


        if (userId.equals(appUser.getId())){
            log.info("登录信息属于采购负责人:"+appUser.getUserName());
            //是 展示所有状态，公司符合的order
            list=negotiatedService.findListByOrgId(orgId,status,secondStatus,pageNum,pageSize);
        }else {
            log.info("登录信息属于不采购负责人:"+appUser.getUserName());
            //查询全部，遍历
            list=negotiatedService.findListIfOk(orgId,status,secondStatus,userId,pageNum,pageSize);
        }
         return list;
    }

    /**
     * 待审批List
     * 当前登录人参与的审批个人任务
     * @param arr  个人任务中的业务id 集合
     * @return
     */
    @RequestMapping(value = "/getTaskList", method = RequestMethod.GET)
    public List<fyPurchase> getList(@RequestParam(value = "arr",required = false) List<String> arr){
        List<fyPurchase> list= new ArrayList<>();
        if (arr.size()>0 && arr!=null){
            list=negotiatedService.findFyPurchaseByIdArr(arr);
        }else {
            list=null;
        }
        return list;

    }
    /**
     * 通过订单id查询订单与相关报价 信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/getPur",method = RequestMethod.GET)
    public Map<String,Object> getFypurchaseById(String id){
        Map<String,Object> map = new HashMap<>();

        //订单信息
        fyPurchase pur=negotiatedService.findFypurchaseById(id);

        map.put("fyPurchase",pur);

        //报价信息
        Map<String, List> quoteMap = negotiatedService.findQuoteById(id);
        map.put("map",quoteMap);

        //查询订单详情表中具体物料选中的具体公司
        List<PurchaseDetail> detailList=negotiatedService.findFyPurchaseDetailById(id);
        for (PurchaseDetail purchaseDetail : detailList) {//查看是否完成议价
            if (purchaseDetail.getQuoteId()!=null&&purchaseDetail.getQuoteId()!=""){
                map.put("detailList",detailList);
                break;
            }
        }
        return map;

    }

    /**
     * 通过企业id查询企业负责人列表
     * @param orgId
     * @return
     */
    @RequestMapping(value = "/getAppUser",method = RequestMethod.GET)
    public ServiceResult getAppUserByOrgId(String orgId){

        //查询企业信息
        AppAuthOrg appAuthOrg=loginService.AppAuthOrgByOrgId(orgId);
        String purchaseMainPerson = appAuthOrg.getPurchaseMainPerson();
        AppUser appUser=loginService.one(purchaseMainPerson);
        //AppUser appUser = loginService.one(purchaseMainPerson.getId());
        return ServiceResult.success(appUser);
    }


    /**
     * 在议价页面发起电话动作，将相关人员（除了管理人员）保存到 订单表中
     * @param id
     * @param callId
     * @return
     */
    @RequestMapping(value = "/saveNagotiaPersonList",method = RequestMethod.GET)
    public ServiceResult saveNagotiatePersonList(String id,String [] callId){
        negotiatedService.updataAppUserListById(id,callId);
        return ServiceResult.success(null);

    }

    /**
     * 通过订单id 获取当事参与电话议价人员列表，编辑权限
     * @param id
     * @return
     */
    @RequestMapping(value = "/getNagotiaPersonList",method = RequestMethod.GET)
    public ServiceResult getNagotiaPersonList(String id){
        List<AppUser> list =negotiatedService.findAppUserListById(id);
        return ServiceResult.success(list);
    }

    /**
     * 跳转大数据页面，根据公司id，订单id获取相关过往信息
     * @param supplierId
     * @param pkMaterialId
     * @return
     */
    @RequestMapping(value = "/getBigDate",method = RequestMethod.GET)
    public ServiceResult getBigDate(String supplierId,String pkMaterialId){

        //测试  可删
//        if (supplierId!=null){
//            supplierId="1001A110000000002857";
//        }
//        if (pkMaterialId!=null){
//            pkMaterialId="1001A11000000000AIOH";
//        }
        //测试

        Ucharts u=negotiatedService.getBigData(supplierId,pkMaterialId);
        return ServiceResult.success(u);
    }


    /**
     * 审批中查看订单相关的推荐专家
     * @param purId  订单id
     * @param supplierId  供应商id
     * @return
     */
    @GetMapping("/getExpert")
    public ServiceResult getExpert(String purId,String supplierId){
        List<ExpertRecommend> erList=negotiatedService.findExpertList(purId,supplierId);
        List<ExpertUser> euList=null;
        //查询报价id
        List<FyQuote > quote=negotiatedService.getFyQuoteByPurIdAndSupplierId(purId,supplierId);
        //查询订单详情
        PurchaseDetail detail=negotiatedService.getPurchaseDetailByPurIdAndQuoteId(purId,quote);
        if (erList!=null && erList.size()>0){
            //判断订单详情表中是否有确认专家id
            if (detail!=null){
                //已完成议价
                euList=negotiatedService.findExpertUserList(erList);
                String expertId = detail.getExpertId();
                String[] split = expertId.split(",");
                for (int i=0;i<euList.size();i++){
                    for (int j=0;j<split.length;j++){
                        if (euList.get(i).getId().equals(split[j])){
                            euList.get(i).setChecked(true);
                        }
                    }
                }
                return ServiceResult.success(euList);
            }else {
                //未议价
                euList=negotiatedService.findExpertUserList(erList);
                return ServiceResult.success(euList);
            }
        }else {
            return ServiceResult.success("没有专家推荐该报价",euList);
        }
    }


    /**
     * 保存待议价界面提交的对应数据
     * @param negotiatIng
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public ServiceResult saveTobeNeg(@RequestBody NegotiatIng negotiatIng){
            negotiatedService.save(negotiatIng);
            return ServiceResult.success("保存成功");

    }

    /**
     * 保存待审批界面审批人建议以及订单是否通过审批的状态
     * @param purId
     * @param passOrNot
     * @param approverProposal
     * @return
     */
    @RequestMapping(value = "/saveToBeApproved",method = RequestMethod.GET)
    public ServiceResult saveToBeApproved(String purId,String passOrNot,String approverProposal){
        System.out.println(purId);
        System.out.println(passOrNot);
        System.out.println(approverProposal);
        negotiatedService.saveApproverProlosalAndStatus(purId,passOrNot,approverProposal);
        return ServiceResult.success("成功");
    }


}
