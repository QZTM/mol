package com.mol.supplier.controller.microApp;

import com.mol.supplier.entity.MicroApp.Salesman;
import com.mol.supplier.entity.MicroApp.Supplier;
import com.mol.supplier.mapper.microApp.MicroSalesmanMapper;
import com.mol.supplier.mapper.microApp.MicroSupplierMapper;
import entity.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpSession;

@Controller
@CrossOrigin
@RequestMapping("/login")
public class MicroLoginController {

    @Autowired
    private MicroSalesmanMapper microSalesmanMapper;

    @Autowired
    private MicroSupplierMapper microSupplierMapper;

    @RequestMapping("/checklogin")
    @ResponseBody
    public ServiceResult normalLogin(String phone, HttpSession session){
        //根据手机号码去数据库查询用户数据：
        Example example = new Example(Salesman.class);
        example.and().andEqualTo("phone",phone);
        Salesman salesman = microSalesmanMapper.selectOneByExample(example);
        if(salesman != null){
            Example example1 = new Example(Supplier.class);
            example1.and().andEqualTo("pkSupplier",salesman.getPkSupplier());
            Supplier supplier = microSupplierMapper.selectOneByExample(example1);
            if(supplier != null){
                session.setAttribute("supplier",supplier);
                session.setAttribute("salesman",salesman);
                return ServiceResult.successMsg("登录成功");
            }
        }
        return ServiceResult.failureMsg("登录失败");
    }

}
