package com.mol.expert.service.dingding.order;

import com.mol.expert.entity.dingding.purchaseOrder.PurchaseOrderDetail;
import com.mol.expert.mapper.fyOracle.dingding.order.PurchaseOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PurchaseOrderService {

    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;

    public List<PurchaseOrderDetail> getPurchaseOrderDetailListByItemId(String itemId) {
        return purchaseOrderMapper.getPurchaseOrderDetailListByItemId(itemId);
    }
}
