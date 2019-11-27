package com.mol.supplier.controller.microApp;

import com.mol.fadada.handler.RegistAndAuthHandler;
import com.mol.fadada.pojo.AuthRecord;
import com.mol.fadada.pojo.RegistRecord;
import com.mol.supplier.entity.MicroApp.Salesman;
import com.mol.supplier.entity.MicroApp.Supplier;
import com.mol.supplier.mapper.microApp.FadadaAuthRecordMapper;
import com.mol.supplier.service.microApp.MicroContractService;
import com.mol.supplier.service.microApp.MicroUserService;
import entity.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 合同管理控制器
 */
@Controller
@RequestMapping("/contract")
public class ContractController {

    @Autowired
    private MicroContractService microContractService;

    @Autowired
    private MicroUserService microUserService;

    @Autowired
    private FadadaAuthRecordMapper fadadaAuthRecordMapper;

    @RequestMapping("/index")
    public String showContractIndex(Model model){
        List<Map> dataList = microContractService.getPurchaseAndContractList("266752374326324047");
        model.addAttribute("list",dataList);
        return "contract_index";
    }


    @RequestMapping("/showCheck")
    public String showCheckPage(){
        return "e_contract_auth";
    }


    @RequestMapping("/checkRegist")
    @ResponseBody
    public ServiceResult checkRegist(HttpSession session) throws InterruptedException {
        Supplier supplier = microUserService.getSupplierFromSession(session);
        Salesman salesman = microUserService.getUserFromSession(session);
        //判断该单位是否注册了：
        RegistRecord rr = new RegistRecord();
        ServiceResult registResult = RegistAndAuthHandler.checkIfRegisted(supplier.getPkSupplier(),"2");
        if(!registResult.isSuccess()){
            return ServiceResult.failure("没有注册");
        }else{
            return ServiceResult.success(registResult);
        }
    }


    @RequestMapping("/checkAuth")
    @ResponseBody
    public ServiceResult checkAuth(HttpSession session,@RequestParam String customerId) throws InterruptedException {
        Supplier supplier = microUserService.getSupplierFromSession(session);
        Salesman salesman = microUserService.getUserFromSession(session);
            //验证是否认证了：
            ServiceResult authResult = RegistAndAuthHandler.checkIfAuthedByCustomerId(customerId,"2");
            if(!authResult.isSuccess()){
                    return ServiceResult.failureMsg("没有认证");
            }else{
                return ServiceResult.success(authResult);
            }
    }


    /**
     * 供应商业务员签署合同
     * @param contractId
     */
    @RequestMapping("/sign")
    public void signContract(@RequestParam String contractId, HttpSession session) throws InterruptedException {
        Supplier supplier = microUserService.getSupplierFromSession(session);
        Salesman salesman = microUserService.getUserFromSession(session);
        //首先判断该供应商是否已经实名认证了：
        RegistRecord rr = new RegistRecord();
        ServiceResult registResult = RegistAndAuthHandler.checkIfRegisted(supplier.getPkSupplier(),"2");
        int registCount = 0;
        boolean newRegistSuccess = false;
        while((!registResult.isSuccess()) && registCount <5 && !newRegistSuccess){
            //调用注册接口：
            ServiceResult newRegistResult = RegistAndAuthHandler.regAccount(supplier.getPkSupplier(),"2");
            if(newRegistResult.isSuccess()){
                newRegistSuccess = true;
            }
            registCount ++;
            Thread.sleep(1000);
            registResult = RegistAndAuthHandler.checkIfRegisted(supplier.getPkSupplier(),"2");
        }
        if(!registResult.isSuccess() && !newRegistSuccess){
            //法大大注册异常
            throw new RuntimeException("法大大注册异常！");
        }else{

        }
        rr = (RegistRecord)registResult.getResult();
        ServiceResult authResult = RegistAndAuthHandler.checkIfRegisted(supplier.getPkSupplier(),"2");
        if(!authResult.isSuccess()){
            //调用认证接口获得认证链接，然后重定向到认证页面。
            ServiceResult authCompanyurl = RegistAndAuthHandler.getAuthCompanyurl(rr.getCustomerId(), RegistAndAuthHandler.CALLBACK_ORG_AUTH, null);
        }


        Example example = new Example(AuthRecord.class);
        example.and().andEqualTo("customerId",supplier.getPkSupplier()).andEqualTo("status","2");
        AuthRecord authRecord = fadadaAuthRecordMapper.selectOneByExample(example);
        if(authRecord == null){
            //查询是否已经注册，如果没有注册，去注册：

        }

    }

}
