package com.mol.expert.entity.dingding.purchase.workBench.toBeNegotiated;

import lombok.Data;

import java.util.List;

/**
 * ClassName:NegotiatIng
 * Package:com.purchase.entity.dingding.purchase.workBench
 * Description
 *  待议价页面提交的数据对象
 * @date:2019/8/29 13:46
 * @author:yangjiangyan
 */
@Data
public class NegotiatIng {
    //订单id
    private String purId;
    //负责人说明
    private String explain;
    //物料对应供应商集合
    private List<MaterIdToSupplierId> materIdToSupplierId;

}
