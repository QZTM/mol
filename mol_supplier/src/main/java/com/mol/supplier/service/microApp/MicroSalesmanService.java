package com.mol.supplier.service.microApp;

import com.mol.supplier.entity.MicroApp.Salesman;
import com.mol.supplier.mapper.newMysql.microApp.MicroSalesmanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

@Service
public class MicroSalesmanService {

    private Logger logger = LoggerFactory.getLogger(MicroSalesmanService.class);

    @Resource
    private MicroSalesmanMapper microSalesmanMapper;


    public Salesman getSalesman(Example example) {
        return microSalesmanMapper.selectOneByExample(example);
    }

}
