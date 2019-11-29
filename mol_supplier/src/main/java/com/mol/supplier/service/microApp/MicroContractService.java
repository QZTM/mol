package com.mol.supplier.service.microApp;
import com.mol.supplier.mapper.microApp.MicroContractMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 合同管理业务处理器
 */
@Service
public class MicroContractService {


    @Autowired
    private MicroContractMapper microContractMapper;


    public List<Map> getPurchaseAndContractList(String salesmanId){
        return microContractMapper.selectPurchaseAndContractList(salesmanId);
    }


}
