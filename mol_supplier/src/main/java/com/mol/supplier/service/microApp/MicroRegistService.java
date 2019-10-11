package com.mol.supplier.service.microApp;

import com.mol.supplier.entity.MicroApp.Salesman;
import com.mol.supplier.entity.MicroApp.Supplier;
import com.mol.supplier.mapper.newMysql.microApp.MicroSalesmanMapper;
import com.mol.supplier.mapper.newMysql.microApp.MicroSupplierMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import util.IdWorker;
import util.TimeUtil;

@Service
public class MicroRegistService {


    private Logger logger = LoggerFactory.getLogger(MicroRegistService.class);

    @Autowired
    private MicroSupplierMapper microSupplierMapper;
    @Autowired
    private MicroSalesmanMapper microSalesmanMapper;

    @Autowired
    private IdWorker idWorker;


    /*供应商注册*/
    public String suppliseRegist(Supplier supplier) {
        String newId = idWorker.nextId() + "";
        supplier.setPkSupplier(newId);
        String nowTime = TimeUtil.getNowDateTime();
        supplier.setRegistTime(nowTime);
        supplier.setLastUpdateTime(nowTime);
        try {
            microSupplierMapper.insert(supplier);
            return newId;
        } catch (Exception e) {
            logger.info("插入供应商数据时出错");
            e.printStackTrace();
            return "";
        }

    }

    public String salesmanRegist(Salesman salesman) {
        String newId = idWorker.nextId() + "";
        salesman.setId(newId);
        String nowTime = TimeUtil.getNowDateTime();
        salesman.setCreationTime(nowTime);
        salesman.setLastLoginTime(nowTime);
        try {
            microSalesmanMapper.insert(salesman);
            return newId;
        } catch (Exception e) {
            logger.info("插入业务员数据时出错");
            e.printStackTrace();
            return "";
        }
    }


    public Supplier getSupplierByName(String supplierName) {
        //查询该企业的注册id:
        Example orgExample = new Example(Supplier.class);
        orgExample.and().andEqualTo("name", supplierName);
        return microSupplierMapper.selectOneByExample(orgExample);
    }


}
