package com.mol.expert.mapper.fyOracle.dingding.order;
import com.mol.expert.base.BaseMapper;
import com.mol.expert.entity.dingding.purchaseOrder.PurchaseOrderDetail;

import java.util.List;
public interface PurchaseOrderMapper extends BaseMapper<PurchaseOrderDetail> {
    List<PurchaseOrderDetail> getPurchaseOrderDetailListByItemId(String itemId);
}
