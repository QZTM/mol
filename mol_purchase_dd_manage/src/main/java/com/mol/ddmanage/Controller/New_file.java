package com.mol.ddmanage.Controller;

import com.mol.ddmanage.Service.Office.ReviewBargainingHistoryPageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/Home")
public class New_file {
    @RequestMapping("/new_file")
    public String new_file(@RequestParam Map map , HttpSession httpSession)
    {
        httpSession.setAttribute("CorpId","ding6ef23b66fc0611a335c2f4657eb6378f");

        return "New_file";
    }

    @RequestMapping("/Home")
    public String Home()
    {
        return "Workbench/Home";
    }

    @RequestMapping("/Statistics")
    public String Statistics()
    {
        return "Workbench/Statistics";
    }

    @RequestMapping("/Announcement")
    public String Announcement()
    {
        return "Office/Announcement/Announcement";
    }

    @RequestMapping("/Call_Records")
    public String Call_Records()
    {
        return "Office/Call_Records/Call_Records";
    }

    @RequestMapping("/Purchase_Contract")  //合同管理页
    public String Purchase_Contract(HttpServletRequest httpServletRequest)
    {
        HttpSession session=httpServletRequest.getSession();
        return "Office/Purchase_Contract/Purchase_Contract";
    }

    @RequestMapping("/Contract")//合同管理的当个合同详情
    public String Contract(@RequestParam Map map,Model model)
    {
        model.addAttribute("Oreder_number",map.get("Oreder_number").toString());//传一个订单编号
        return "Office/Purchase_Contract/Contract";
    }

    @RequestMapping("/Purchase_Grogress")//采购进度
    public String Purchase_Grogress()
    {
        return "Office/Purchase_Grogress/Purchase_Grogress";
    }
    @RequestMapping("/Time_Process")  //采购审核历史流程详情
    public String Time_Process(@RequestParam Map map,Model model)
    {
        model.addAttribute("Oreder_number","订单编号:"+map.get("Oreder_number").toString());
        model.addAttribute("user_name","申请人:"+map.get("user_name").toString());
        model.addAttribute("goods_name",map.get("goods_name").toString());
        model.addAttribute("create_time","创建时间"+map.get("create_time").toString());
        return "Office/Purchase_Grogress/Time_Process";
    }

    @RequestMapping("/ReviewBargainingHistoryList")//审核议价历史列表
    public String ReviewBargainingHistoryList()
    {
        return "Office/ReviewBargainingHistory/ReviewBargainingHistoryList";
    }


    @Resource
    ReviewBargainingHistoryPageService titleDetailsService;
    @RequestMapping("/ReviewBargainingHistoryPage")//审核议价历史详情页
    public String Title_Details(@RequestParam Map map,Model model)
    {
        model.addAttribute("titl","查看审批记录:"+map.get("titl").toString());//标题
        model.addAttribute("Order","订单编号:"+map.get("Order").toString());//订单编号
        model.addAttribute("people","采购申请人:"+map.get("people").toString());//申请人
        model.addAttribute("create_time","发布日期:"+map.get("create_time").toString());//
        model.addAttribute("supplier_name","公司名称:"+map.get("supplier_name").toString());
        model.addAttribute("quoteSellerNum","已报价商家数"+ titleDetailsService.Purchase(map.get("Order").toString()).get("quoteSellerNum"));
        return "Office/ReviewBargainingHistory/ReviewBargainingHistoryPage";
    }

    @RequestMapping("/History_call")//订单的历史通话记录页
    public String History_call()
    {
        return "Office/Call_Records/History_call";
    }

    @RequestMapping("/Company_Call")//供应商的通话记录页
    public String Company_Call(@RequestParam Map map, Model model)
    {
        model.addAttribute("Company_name",map.get("Company_name").toString());
        return "Office/Call_Records/Company_Call";
    }

    @RequestMapping("/DepartmentManagementPage")//人员组织架构
    public String DepartmentManagementPage(/*@RequestParam Map map,*/ Model model)
    {
        return "MemberManagement/DepartmentManagement/DepartmentManagementPage";
    }

    @RequestMapping("/JobManagement")//岗位管理
    public String JobManagement(/*@RequestParam Map map, Model model*/)
    {
       // model.addAttribute("Company_name",map.get("Company_name").toString());
        return "MemberManagement/DepartmentManagement/JobManagement";
    }

    @RequestMapping("/JobEditPage")//岗位编辑
    public String JobEditPage(@RequestParam Map map, Model model)
    {
         model.addAttribute("JobName","岗位名称:"+map.get("JobName").toString());
        return "MemberManagement/DepartmentManagement/JobEditPage";
    }

    @RequestMapping("/ExpertInforPageList")//专家信息管理
    public String ExpertInforPageList()
    {
        return "ExpertManagement/ExpertInfor/ExpertInforPageList";
    }

    @RequestMapping("/EditExperInforPage")//专家信息编辑
    public String EditExperInforPage(@RequestParam Map map,Model model)
    {
        model.addAttribute("ExperId",map.get("ExperId").toString());
        return "ExpertManagement/ExpertInfor/EditExperInforPage";
    }

    @RequestMapping("/ExperApprovalListPage")//专家信息审核列表
    public String ExperApprovalListPage()
    {
        return "ExpertManagement/ExperApproval/ExperApprovalListPage";
    }

    @RequestMapping("/SetExperApprovalPage")//专家资料审核
    public String SetExperApprovalPage(@RequestParam Map map,Model model)
    {
        model.addAttribute("ExperId",map.get("ExperId").toString());//传出去专家id
        return "ExpertManagement/ExperApproval/SetExperApprovalPage";
    }

    @RequestMapping("/AddExperTableinfor")//添加专家信息
    public String AddExperTableinfor()
    {
        return "ExpertManagement/ExpertInfor/AddExperTableInforPage";
    }

    @RequestMapping("/PurchasOrderListPage")//采购订单
    public String PurchasOrderListPage()
    {
        return "PurchasOrderManagement/PurchasOrder/PurchasOrderListPage";
    }

    @RequestMapping("/PurchasOrderinforPage")//订单信息详情
    public String PurchasOrderinforPage(@RequestParam Map map,Model model)
    {
        model.addAttribute("Oreder_number","订单编号:"+map.get("Oreder_number").toString());//传一个订单编号
        model.addAttribute("user_name","申请人:"+map.get("user_name").toString());//申请人
        model.addAttribute("create_time","发布日期:"+map.get("create_time").toString());//创建日期
        model.addAttribute("goods_name",map.get("goods_name").toString());
        return "PurchasOrderManagement/PurchasOrder/PurchasOrderinforPage";
    }

    @RequestMapping("/SupplierListPage")//供应商管理
    public String SuperplierListPage()
    {
        return "SupplierManagement/SupplierData/SupplierListPage";
    }

    @RequestMapping("/SetSupplierinforPage")//查看供应商信息
    public String SetSupplierinforPage(@RequestParam Map map,Model model)
    {
        model.addAttribute("pk_supplier",map.get("pk_supplier").toString());
        return "SupplierManagement/SupplierData/SetSupplierinforPage";
    }

    @RequestMapping("/SupplierToExamineListPage")//供应商审核列表
    public String SupplierToExamineListPage()
    {
        return "SupplierManagement/SupplierToExamine/SupplierToExamineListPage";
    }

    @RequestMapping("/SupplierToExaminePage")//供应商审核内容
    public String SupplierToExaminePage(@RequestParam Map map ,Model model)
    {
        model.addAttribute("pk_supplier",map.get("pk_supplier").toString());
        return "SupplierManagement/SupplierToExamine/SupplierToExaminePage";
    }

    @RequestMapping("/ApprovalProcessSettingListPage")//审批流程设置
    public String ApprovalProcessSettingListPage()
    {
        return "Toots/ApprovalProcessSetting/ApprovalProcessSettingListPage";
    }

    @RequestMapping("/ApprovalProcessinforPage")//审批流程信息编辑
    public String ApprovalProcessinforPage()
    {
        return "Toots/ApprovalProcessSetting/ApprovalProcessinforPage";
    }

    @RequestMapping("/BargainingProcessListPage")//议价流程设置
    public String BargainingProcessListPage()
    {
        return "Toots/BargainingProcessSetting/BargainingProcessListPage";
    }

}
