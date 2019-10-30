package com.mol.purchase.entity.dingding.purchase.workBench.toBeNegotiated;

import com.mol.purchase.entity.ExpertUser;
import lombok.Data;

import java.util.List;

@Data
public class SupplierIdToExpertId {
    //选中的供应商
    private String suId;
    //选中的专家
    private String exId;

}
