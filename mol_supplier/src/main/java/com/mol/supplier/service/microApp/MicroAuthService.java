package com.mol.supplier.service.microApp;

import com.mol.supplier.entity.MicroApp.Supplier;
import com.mol.supplier.mapper.microApp.MicroSupplierMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MicroAuthService {


    private Logger logger = LoggerFactory.getLogger(MicroAuthService.class);

    @Autowired
    private MicroSupplierMapper microSupplierMapper;


    public void saveBussinessLicenceImg(Supplier supplier) {

        int a = microSupplierMapper.updateByPrimaryKey(supplier);
        if (a != 1) {
            logger.error("保存营业执照时出错");
            throw new RuntimeException("保存营业执照时出错");
        }


    }

}
