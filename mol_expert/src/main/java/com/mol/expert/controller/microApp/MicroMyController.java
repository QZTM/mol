//package com.purchase.controller.microApp;
//
//import Salesman;
//import Supplier;
//import com.purchase.service.itemAnditemType.ItemAndTypeService;
//import MicroUserService;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import javax.servlet.http.HttpSession;
//import java.util.concurrent.ExecutionException;
//
//@Controller
//@RequestMapping("/microApp/my")
//public class MicroMyController {
//
//    private Logger logger = LoggerFactory.getLogger(MicroMyController.class);
//
//
//    @Autowired
//    private ItemAndTypeService itemAndTypeService;
//
//    @Autowired
//    private MicroUserService microUserService;
//
//    /**
//     * 显示我的页面
//     * 根据供应商的属性及各种属性的认证状态构造提示语句，并存入model中
//     * 构造所属行业提示语句存入model中
//     *
//     * @return
//     */
//    @RequestMapping("/show")
//    public String showMyInfoPage(Model model, HttpSession session) {
//        Object salesmanObj = microUserService.getUserFromSession(session);
//        Object supplierObj = microUserService.getSupplierFromSession(session);
//        if(salesmanObj == null || supplierObj == null){
//            throw new RuntimeException("session中没有用户信息");
//        }
//
//
//        Salesman salesman = (Salesman)salesmanObj;
//        Supplier supplier = (Supplier)supplierObj;
//
//
//
//        String supplierAttrNormalStr = "基础供应商";
//        String normalStateStr = "";
//        normalStateStr = getSupstateStr(supplier.getSupstateNormal());
//        model.addAttribute("supplierattrnormal",supplierAttrNormalStr+"  "+normalStateStr);
//
//
//
//        if(supplier.getIfAttrStrategy() == 1){
//            String supplierAttrStrategyStr = "战略采购供应商";
//            String strategyStateStr = getSupstateStr(supplier.getSupstateStrategy());
//            model.addAttribute("supplierattrstrategy",supplierAttrStrategyStr+"  "+strategyStateStr);
//        }
//
//
//        if(supplier.getIfAttrSingle() == 1){
//            String supplierAttrSingleStr = "单一来源供应商";
//            String singleStateStr = getSupstateStr(supplier.getSupstateSingle());
//            model.addAttribute("supplierattrsingle",supplierAttrSingleStr+"  "+singleStateStr);
//        }
//
//        /**
//         * 供应商所属行业
//         *
//         */
//        String industryStr = getSupplierIndustryStr(supplier);
//        System.out.println("industryStr:"+industryStr);
//        model.addAttribute("industrystr",industryStr);
//
//        return "my";
//    }
//
//
//    public String getSupstateStr(Integer supstate){
//        String supstateStr = "";
//        switch(supstate){
//            case 1:
//                supstateStr = "已完成认证";
//                break;
//            case 2:
//                supstateStr = "正在认证中";
//                break;
//            case 4:
//                supstateStr = "认证未通过";
//                break;
//                default:
//                    supstateStr = "未认证";
//        }
//        logger.info("supstateStr:"+supstateStr);
//        return supstateStr;
//    }
//
//
//
//
//    public String getSupplierIndustryStr(Supplier supplier){
//        String supplierIndustryStr = "";
//        String industry1 = supplier.getIndustryFirst();
//        String industry2 = supplier.getIndustrySecond();
//        String industry3 = supplier.getIndustryThird();
//        System.out.println("industry1:"+industry1+",industry2:"+industry2+",industry3:"+industry3);
//        String industryStr1 = "";
//        String industryStr2 = "";
//        String industryStr3 = "";
//        if(!StringUtils.isEmpty(industry1)){
//            System.out.println("industry1 != '',");
//            try {
//                industryStr1 = itemAndTypeService.getItemTypeNameByKey(industry1).get();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        }
//
//        if(!StringUtils.isEmpty(industry2)){
//            System.out.println("industry2 != '',");
//            try {
//                industryStr2 = itemAndTypeService.getItemTypeNameByKey(industry2).get();
//                if(!StringUtils.isEmpty(industryStr2)){
//                    industryStr2 = "--"+industryStr2;
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        }
//
//        if(!StringUtils.isEmpty(industry3)){
//            System.out.println("industry3 != '',");
//            try {
//                industryStr3 = itemAndTypeService.getItemTypeNameByKey(industry3).get();
//                if(!StringUtils.isEmpty(industryStr3)){
//                    industryStr3 = "--"+industryStr3;
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        }
//
//        System.out.println("1:"+industryStr1+",2:"+industryStr2+",3:"+industryStr3);
//        supplierIndustryStr = industryStr1+industryStr2+industryStr3;
//        System.out.println(supplierIndustryStr);
//        return supplierIndustryStr;
//    }
//
//}
