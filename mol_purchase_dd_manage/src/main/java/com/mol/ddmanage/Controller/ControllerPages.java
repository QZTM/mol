package com.mol.ddmanage.Controller;

import com.mol.ddmanage.Service.Office.ReviewBargainingHistoryPageService;
import com.mol.ddmanage.Service.Permission.VerificationPermissionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/Home")
public class ControllerPages {

    @Resource
    VerificationPermissionService verificationPermissionService;//验证访问人权限
    @RequestMapping("/DingdingOaLogin")
    public String GetLoginCode()
    {
        return "GetLoginCode";
    }

    @RequestMapping("/new_file")//测试首页框架
    public String new_file(@RequestParam Map map , HttpSession httpSession)
    {
        httpSession.setAttribute("userid","083216482529129838");
        return "new_file";
    }

    @RequestMapping("/Home")//首页
    public String Home(HttpServletRequest httpServletRequest)
    {
        return "Workbench/Home";
    }

    @RequestMapping("/Statistics")//主页的统计图
    public String Statistics()
    {
        return "Workbench/Statistics";
    }

    @RequestMapping("/Announcement")//公告#
    public String Announcement(HttpServletRequest httpServletRequest)
    {
        HttpSession httpSession=httpServletRequest.getSession();
       if(verificationPermissionService.VerificationPermissionLogic(httpSession.getAttribute("userid").toString(),"notice"))//查询用户是否有进入这个页面的权限
       {
           return "Office/Announcement/AnnouncementList";
       }
       else
       {
           return "Permission/NotVerificationPage";
       }
    }
    @RequestMapping("/AnnouncementImag")//上传图片
    public void Announcement(/*HttpServletRequest httpServletRequest*/)
    {
       String mmm="";
    }

    @RequestMapping("/AnnouncementEditPage")//新增公告
    public String AnnouncementEditPage()
    {
            return "Office/Announcement/AnnouncementEditPage";
    }

    @RequestMapping("/Call_Records")//通话记录#
    public String Call_Records(HttpServletRequest httpServletRequest)
    {
        HttpSession httpSession=httpServletRequest.getSession();
        if(verificationPermissionService.VerificationPermissionLogic(httpSession.getAttribute("userid").toString(),"telephoneRecording"))//查询用户是否有进入这个页面的权限
        {
            return "Office/Call_Records/Call_Records";
        }
        else
        {
            return "Permission/NotVerificationPage";
        }
    }

    @RequestMapping("/Purchase_Contract")  //合同管理页#
    public String Purchase_Contract(HttpServletRequest httpServletRequest)
    {
        HttpSession httpSession=httpServletRequest.getSession();
        if(verificationPermissionService.VerificationPermissionLogic(httpSession.getAttribute("userid").toString(),"contract"))//查询用户是否有进入这个页面的权限
        {
            return "Office/Purchase_Contract/Purchase_Contract";
        }
        else
        {
            return "Permission/NotVerificationPage";
        }
    }


    @RequestMapping("/Contract")//合同管理详情
    public String Contract(@RequestParam Map map, Model model)
    {
        model.addAttribute("Oreder_number",map.get("Oreder_number").toString());//传一个订单编号
        return "Office/Purchase_Contract/Contract";
    }

    @RequestMapping("/ElectronicContractSigningListPage")//电子合同签署页面#
    public String ElectronicContractSigningListPage(HttpServletRequest httpServletRequest)
    {
        HttpSession httpSession=httpServletRequest.getSession();
        if(verificationPermissionService.VerificationPermissionLogic(httpSession.getAttribute("userid").toString(),"electronicContract"))//查询用户是否有进入这个页面的权限
        {
            return "Office/ElectronicContractSigning/ElectronicContractSigningListPage";
        }
        else
        {
            return "Permission/NotVerificationPage";
        }

    }

    @RequestMapping("/ElectronicContractSigninginforPage")//电子合同签署详情页面
    public String ElectronicContractSigninginforPage(@RequestParam Map map ,Model model)
    {
        model.addAttribute("Oreder_number","订单编号:"+map.get("Oreder_number").toString());//传一个订单编号
        model.addAttribute("user_name","申请人:"+map.get("user_name").toString());//申请人
        model.addAttribute("create_time","发布日期:"+map.get("create_time").toString());//创建日期
        model.addAttribute("goods_name",map.get("goods_name").toString());
        return "Office/ElectronicContractSigning/ElectronicContractSigninginforPage";
    }

    @RequestMapping("/Purchase_Grogress")//采购进度#
    public String Purchase_Grogress(HttpServletRequest httpServletRequest)
    {
        HttpSession httpSession=httpServletRequest.getSession();
        if(verificationPermissionService.VerificationPermissionLogic(httpSession.getAttribute("userid").toString(),"purchaseProgress"))//查询用户是否有进入这个页面的权限
        {
            return "Office/Purchase_Grogress/Purchase_Grogress";
        }
        else
        {
            return "Permission/NotVerificationPage";
        }
    }
    @RequestMapping("/Time_Process")  //采购进度时间轴流程详情
    public String Time_Process(@RequestParam Map map,Model model)
    {
        model.addAttribute("Oreder_number","订单编号:"+map.get("Oreder_number").toString());
        model.addAttribute("user_name","申请人:"+map.get("user_name").toString());
        model.addAttribute("goods_name",map.get("goods_name").toString());
        model.addAttribute("create_time","创建时间"+map.get("create_time").toString());
        return "Office/Purchase_Grogress/Time_Process";
    }

    @RequestMapping("/ReviewBargainingHistoryList")//审核议价历史列表#
    public String ReviewBargainingHistoryList(HttpServletRequest httpServletRequest)
    {
        HttpSession httpSession=httpServletRequest.getSession();
        if(verificationPermissionService.VerificationPermissionLogic(httpSession.getAttribute("userid").toString(),"bargaining"))//查询用户是否有进入这个页面的权限
        {
            return "Office/ReviewBargainingHistory/ReviewBargainingHistoryList";
        }
        else
        {
            return "Permission/NotVerificationPage";
        }
    }


    @Resource
    ReviewBargainingHistoryPageService titleDetailsService;
    @RequestMapping("/ReviewBargainingHistoryPage")//审核议价历史详情页
    public String Title_Details(@RequestParam Map map, Model model)
    {
        model.addAttribute("titl",map.get("titl").toString());//标题
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

    @RequestMapping("/DepartmentManagementPage")//部门管理#
    public String DepartmentManagementPage(HttpServletRequest httpServletRequest)
    {
        HttpSession httpSession=httpServletRequest.getSession();
        if(verificationPermissionService.VerificationPermissionLogic(httpSession.getAttribute("userid").toString(),"department"))//查询用户是否有进入这个页面的权限
        {
            return "MemberManagement/DepartmentManagement/DepartmentManagementPage";
        }
        else
        {
            return "Permission/NotVerificationPage";
        }
    }

    @RequestMapping("/jurisdictionManagementList")//岗位设置#
    public String JobManagement(HttpServletRequest httpServletRequest)
    {
        HttpSession httpSession=httpServletRequest.getSession();
        if(verificationPermissionService.VerificationPermissionLogic(httpSession.getAttribute("userid").toString(),"position"))//查询用户是否有进入这个页面的权限
        {
            return "MemberManagement/DepartmentManagement/jurisdictionManagementList";
        }
        else
        {
            return "Permission/NotVerificationPage";
        }
    }
    @RequestMapping("/AddStaffToPositionPage")//岗位的添加员工
    public String AddStaffToPositionPage(@RequestParam Map map,Model model)
    {
        model.addAttribute("jurisdictionId",map.get("jurisdictionId").toString());
        return "MemberManagement/DepartmentManagement/AddStaffToPositionPage";
    }


    /**
     * 岗位权限编辑
     * @param map
     * @param model
     * @return
     */
    @RequestMapping("/jurisdictionManagementEditPage")//修改角色权限的页面
    public String JobEditPage(@RequestParam Map map, ModelMap model)
    {
        model.addAttribute("JobName", map.get("JobName").toString());
        model.addAttribute("jurisdictionId",map.get("jurisdictionId").toString());
        return "MemberManagement/DepartmentManagement/jurisdictionManagementEditPage";
    }

    @RequestMapping("/AddJurisdictionPage")//添加角色
    public String AddJurisdictionPage()
    {
        return "MemberManagement/DepartmentManagement/AddJurisdictionPage";
    }

    @RequestMapping("/ExpertInforPageList")//专家信息管理#
    public String ExpertInforPageList(HttpServletRequest httpServletRequest)
    {
        HttpSession httpSession=httpServletRequest.getSession();
        if(verificationPermissionService.VerificationPermissionLogic(httpSession.getAttribute("userid").toString(),"expertInfor"))//查询用户是否有进入这个页面的权限
        {
            return "ExpertManagement/ExpertInfor/ExpertInforPageList";
        }
        else
        {
            return "Permission/NotVerificationPage";
        }
    }

    @RequestMapping("/EditExperInforPage")//专家信息编辑
    public String EditExperInforPage(@RequestParam Map map,Model model)
    {
        model.addAttribute("ExperId",map.get("ExperId").toString());
        return "ExpertManagement/ExpertInfor/EditExperInforPage";
    }

    @RequestMapping("/ExperApprovalListPage")//专家信息审核列表#
    public String ExperApprovalListPage(HttpServletRequest httpServletRequest)
    {
        HttpSession httpSession=httpServletRequest.getSession();
        if(verificationPermissionService.VerificationPermissionLogic(httpSession.getAttribute("userid").toString(),"expertApproval"))//查询用户是否有进入这个页面的权限
        {
            return "ExpertManagement/ExperApproval/ExperApprovalListPage";
        }
        else
        {
            return "Permission/NotVerificationPage";
        }
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

    @RequestMapping("/PurchasOrderListPage")//采购订单#
    public String PurchasOrderListPage(HttpServletRequest httpServletRequest)
    {
        HttpSession httpSession=httpServletRequest.getSession();
        if(verificationPermissionService.VerificationPermissionLogic(httpSession.getAttribute("userid").toString(),"purchase"))//查询用户是否有进入这个页面的权限
        {
            return "PurchasOrderManagement/PurchasOrder/PurchasOrderListPage";
        }
        else
        {
            return "Permission/NotVerificationPage";
        }
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

    @RequestMapping("/SupplierListPage")//供应商管理#
    public String SuperplierListPage(HttpServletRequest httpServletRequest)
    {
        HttpSession httpSession=httpServletRequest.getSession();
        if(verificationPermissionService.VerificationPermissionLogic(httpSession.getAttribute("userid").toString(),"supplierManage"))//查询用户是否有进入这个页面的权限
        {
            return "SupplierManagement/SupplierData/SupplierListPage";
        }
        else
        {
            return "Permission/NotVerificationPage";
        }
    }

    @RequestMapping("/SetSupplierinforPage")//查看供应商信息
    public String SetSupplierinforPage(@RequestParam Map map,Model model)
    {
        model.addAttribute("pk_supplier",map.get("pk_supplier").toString());
        return "SupplierManagement/SupplierData/SetSupplierinforPage";
    }

    @RequestMapping("/SupplierToExamineListPage")//供应商审核列表#
    public String SupplierToExamineListPage(HttpServletRequest httpServletRequest)
    {
        HttpSession httpSession=httpServletRequest.getSession();
        if(verificationPermissionService.VerificationPermissionLogic(httpSession.getAttribute("userid").toString(),"supplierApproval"))//查询用户是否有进入这个页面的权限
        {
            return "SupplierManagement/SupplierToExamine/SupplierToExamineListPage";
        }
        else
        {
            return "Permission/NotVerificationPage";
        }
    }

    @RequestMapping("/SupplierToExaminePage")//供应商审核内容
    public String SupplierToExaminePage(@RequestParam Map map ,Model model)
    {
        model.addAttribute("supplier_type",map.get("supplier_type").toString());
        model.addAttribute("pk_supplier",map.get("pk_supplier").toString());
        return "SupplierManagement/SupplierToExamine/SupplierToExaminePage";
    }

    @RequestMapping("/ApprovalProcessSettingListPage")//审批流程设置
    public String ApprovalProcessSettingListPage(HttpServletRequest httpServletRequest)
    {
        HttpSession httpSession=httpServletRequest.getSession();
        if(verificationPermissionService.VerificationPermissionLogic(httpSession.getAttribute("userid").toString(),"approvalSet"))//查询用户是否有进入这个页面的权限
        {
            return "Toots/ApprovalProcessSetting/ApprovalProcessSettingListPage";
        }
        else
        {
            return "Permission/NotVerificationPage";
        }
    }

    @RequestMapping("/ApprovalProcessinforPage")//审批流程信息编辑
    public String ApprovalProcessinforPage()
    {
        return "Toots/ApprovalProcessSetting/ApprovalProcessinforPage";
    }

    @RequestMapping("/BargainingProcessListPage")//议价流程设置
    public String BargainingProcessListPage(HttpServletRequest httpServletRequest)
    {
        HttpSession httpSession=httpServletRequest.getSession();
        if(verificationPermissionService.VerificationPermissionLogic(httpSession.getAttribute("userid").toString(),"bargainingSet"))//查询用户是否有进入这个页面的权限
        {
            return "Toots/BargainingProcessSetting/BargainingProcessListPage";
        }
        else
        {
            return "Permission/NotVerificationPage";
        }
    }

}
