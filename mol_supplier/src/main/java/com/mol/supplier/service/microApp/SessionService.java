package com.mol.supplier.service.microApp;

import com.mol.supplier.entity.MicroApp.Supplier;
import com.mol.supplier.mapper.microApp.MicroSupplierMapper;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;

/**
 * 根据数据库版本号更新session中的supplier
 */
@Service
@Log
public class SessionService {

    @Autowired
    private MicroSupplierMapper microSupplierMapper;


    public void flushSupplier(HttpSession session){
        Object supplierObj = session.getAttribute("supplier");
        if(supplierObj == null){
            return ;
        }
        Supplier supplier = (Supplier)supplierObj;
        Integer supplierBdVersion = microSupplierMapper.getVersionByPkSupplier(supplier.getPkSupplier());
        if(supplierBdVersion != supplier.getVersion()){
            Supplier newSupplier = microSupplierMapper.selectByPrimaryKey(supplier);
            log.info("更新session中的supplier，，，，旧版本号："+supplier.getVersion()+",,,新版本号："+supplierBdVersion);
            session.setAttribute("supplier",newSupplier);
        }
    }
}
