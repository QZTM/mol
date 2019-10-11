package com.mol.purchase.entity.dingding.purchase.workBench.toBeNegotiated;

import lombok.Data;

/**
 * ClassName:MaterIdToSupplierId
 * Package:com.purchase.entity.dingding.purchase.workBench
 * Description
 * 待议价页面物料与供应商对应的bean
 * @date:2019/8/29 13:49
 * @author:yangjiangyan
 */
@Data
public class MaterIdToSupplierId {
    //物料id
    private String materId;
    //供应商id
    private String supplierId;
}
