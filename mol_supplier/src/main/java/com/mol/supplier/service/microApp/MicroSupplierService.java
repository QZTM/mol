package com.mol.supplier.service.microApp;

import com.mol.supplier.entity.MicroApp.Supplier;
import com.mol.supplier.mapper.microApp.MicroSupplierMapper;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

@Service
public class MicroSupplierService {

    @Resource
    private MicroSupplierMapper microSupplierMapper;

    public Supplier getSupplierByExample(Example example) {
        return microSupplierMapper.selectOneByExample(example);
    }


}
