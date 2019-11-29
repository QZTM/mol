package com.mol.supplier.mapper.microApp;

import java.util.List;
import java.util.Map;

public interface MicroContractMapper {
    List<Map> selectPurchaseAndContractList(String salesmanId);
}
