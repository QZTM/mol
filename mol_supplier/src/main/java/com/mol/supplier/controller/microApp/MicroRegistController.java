package com.mol.supplier.controller.microApp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mol.supplier.config.MicroAttr;
import com.mol.supplier.entity.MicroApp.DDDept;
import com.mol.supplier.entity.MicroApp.DDUser;
import com.mol.supplier.entity.MicroApp.Salesman;
import com.mol.supplier.entity.MicroApp.Supplier;
import com.mol.supplier.service.microApp.MicroRegistService;
import entity.ServiceResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 供应商、业务员注册控制器
 */
@RequestMapping("/microApp/regist")
@RestController
public class MicroRegistController {

    private Logger logger = LoggerFactory.getLogger(MicroRegistController.class);

    @Autowired
    private MicroRegistService microRegistService;


    /**
     * 注册页面点击绑定（供应商或者业务员注册）
     * 逻辑：
     * 1.根据前段传过来的公司名称去数据库查询，
     * （1）如果有该公司记录，那么业务为：把该用户添加到业务员表中，并绑定到该公司下
     * （2）如果没有该公司记录，那么把该公司信息添加入供应商表，然后把该用户添加到业务员表中，并绑定到该公司下
     */

    @RequestMapping("/reg")
    public ServiceResult supplierOrSalesmanReg(@RequestParam String orgName, String phone, String deptName, String username, HttpSession session) {
        logger.info("orgName:" + orgName + ",phone:" + phone + ",deptName:" + deptName + ",username:" + username);

        //验证必要参数，orgName,username,phone不能为空
        if (StringUtils.isEmpty(orgName) || StringUtils.isEmpty(phone) || StringUtils.isEmpty(username)) {
            throw new RuntimeException("注册参数异常");
        }

        /**
         * 业务流程：
         * 1.根据公司名称去供应商表中查询数据
         */

        Supplier exitedSupplier = microRegistService.getSupplierByName(orgName);
        System.out.println("exitedSupplier");
        System.out.println(exitedSupplier);
        if (exitedSupplier != null) {
            Salesman salesman = new Salesman();
            salesman.setPkSupplier(exitedSupplier.getPkSupplier());
            salesman.setName(username);
            salesman.setPhone(phone);
            if (session.getAttribute("ddUser") != null) {
                DDUser ddUser = (DDUser) session.getAttribute("ddUser");
                salesman.setLastLoginWay(1);
                salesman.setDdUserId(ddUser.getUserid());
            }
            /*!!!!!!!!!!!!!         通过微信登录的情况                !!!!!!!!!!!!!!*/
            if (session.getAttribute("wxUser") != null) {
                salesman.setLastLoginWay(2);
            }

            System.out.println(salesman);
            String insertResult = microRegistService.salesmanRegist(salesman);
            if (StringUtils.isEmpty(insertResult)) {
                return ServiceResult.failureMsg("注册失败，请稍后再试");
            }
            return ServiceResult.successMsg("注册成功");
        } else {
            Supplier supplier = new Supplier();
            supplier.setName(orgName);
            supplier.setIfAttrNormal(1);
            supplier.setIfAttrStrategy(0);
            supplier.setIfAttrSingle(0);
            supplier.setSupstateNormal(MicroAttr.WITHOUT_SUPSTATE);
            supplier.setSupstateStrategy(MicroAttr.WITHOUT_SUPSTATE);
            supplier.setSupstateSingle(MicroAttr.WITHOUT_SUPSTATE);
            Object ddDeptObj = session.getAttribute("ddOrg");
            if (ddDeptObj != null) {
                DDDept ddDept = (DDDept) ddDeptObj;
                supplier.setDdOrgId(ddDept.getId() + "");
            }

            String insertResult = microRegistService.suppliseRegist(supplier);
            if (StringUtils.isEmpty(insertResult)) {
                return ServiceResult.failureMsg("注册失败，请稍后再试");
            } else {
                Salesman salesman = new Salesman();
                salesman.setPkSupplier(insertResult);
                salesman.setName(username);
                salesman.setPhone(phone);
                if (session.getAttribute("ddUser") != null) {
                    DDUser ddUser = (DDUser) session.getAttribute("ddUser");
                    salesman.setLastLoginWay(1);
                    salesman.setDdUserId(ddUser.getUserid());
                }
                /*!!!!!!!!!!!!!         通过微信登录的情况                !!!!!!!!!!!!!!*/
                if (session.getAttribute("wxUser") != null) {
                    salesman.setLastLoginWay(2);
                }

                String salesmanInsertResult = microRegistService.salesmanRegist(salesman);
                if (StringUtils.isEmpty(salesmanInsertResult)) {
                    return ServiceResult.failureMsg("注册失败，请稍后再试");
                }
                return ServiceResult.successMsg("注册成功");
            }
        }
    }


    /**
     * 供应商及其业务员同时注册
     *
     * @param regData
     * @return
     */
    @RequestMapping(value = "/supplierAndSalesman", method = RequestMethod.POST)
    public ServiceResult supplierAndSalesmanReg(String regData) {
        Map maps = (Map) JSON.parse(regData);
        Supplier supplier = JSONObject.parseObject(JSON.toJSONString(maps.get("supplier")), Supplier.class);
        Salesman salesman = JSONObject.parseObject(JSON.toJSONString(maps.get("salesman")), Salesman.class);
        if (supplier == null || salesman == null) {
            return ServiceResult.failure("供应商或业务员信息不完整");
        }
        String supplierNewId = microRegistService.suppliseRegist(supplier);
        if (StringUtils.isEmpty(supplierNewId)) {
            return ServiceResult.failure("注册失败");
        }
        salesman.setPkSupplier(supplierNewId);
        String newSalesmanId = microRegistService.salesmanRegist(salesman);
        if (StringUtils.isEmpty(newSalesmanId)) {
            return ServiceResult.failure("注册失败");
        }
        return ServiceResult.success("注册成功");
    }


    /**
     * 某公司业务员注册
     *
     * @param regData
     * @return
     */
    @RequestMapping(value = "/salesman", method = RequestMethod.POST)
    public ServiceResult salesmanReg(String regData) {
        Map maps = (Map) JSON.parse(regData);
        Supplier supplier = JSONObject.parseObject(JSON.toJSONString(maps.get("supplier")), Supplier.class);
        Salesman salesman = JSONObject.parseObject(JSON.toJSONString(maps.get("salesman")), Salesman.class);
        if (supplier == null || salesman == null) {
            return ServiceResult.failure("供应商或业务员信息不完整");
        }
        //查询该企业的注册id:
        supplier = microRegistService.getSupplierByName(supplier.getName());
        if (supplier == null) {
            throw new RuntimeException("注册业务员时没找到其公司");
        }
        salesman.setPkSupplier(supplier.getPkSupplier());
        String newId = microRegistService.salesmanRegist(salesman);
        logger.info("某公司业务员注册。。。newId:" + newId);
        return ServiceResult.success("注册成功");
    }


}
