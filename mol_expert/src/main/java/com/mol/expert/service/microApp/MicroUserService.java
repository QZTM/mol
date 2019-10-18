package com.mol.expert.service.microApp;

import com.mol.expert.entity.MicroApp.Salesman;
import com.mol.expert.entity.MicroApp.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class MicroUserService {

    private Logger logger = LoggerFactory.getLogger(MicroUserService.class);



    public Salesman getUserFromSession(HttpSession session){
        Object salesmanObj = session.getAttribute("user");
        if(salesmanObj == null){
            throw new RuntimeException("session中没有用户信息");
        }
        return (Salesman)salesmanObj;
    }


    public Supplier getSupplierFromSession(HttpSession session){
        Object supplierObj = session.getAttribute("supplier");
        if(supplierObj == null){
            throw new RuntimeException("session中没有用户信息");
        }
        return (Supplier)supplierObj;
    }

}
