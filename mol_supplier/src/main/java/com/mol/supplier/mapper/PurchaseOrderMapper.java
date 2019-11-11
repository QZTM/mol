package com.mol.supplier.mapper;
import com.mol.base.BaseMapper;
import com.mol.supplier.entity.dingding.purchaseOrder.PurchaseOrderDetail;

import java.util.List;
public interface PurchaseOrderMapper extends BaseMapper<PurchaseOrderDetail> {
    List<PurchaseOrderDetail> getPurchaseOrderDetailListByItemId(String itemId);
}
