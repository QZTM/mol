package com.mol.supplier.controller.dingding.order;

import com.mol.supplier.entity.dingding.purchaseOrder.PurchaseOrderDetail;
import com.mol.supplier.service.dingding.order.PurchaseOrderService;
import entity.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 获取nc数据库采购订单历史数据
 */
@RestController
@RequestMapping("order")
@CrossOrigin
public class PurchaseOrderController {

    private Logger logger = LoggerFactory.getLogger(PurchaseOrderController.class);

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @RequestMapping("/getHistoryOrderById")
    public ServiceResult getCompareDataByItemId(String itemId){
        //根据物料id去数据库查询一年内的历史数据，最低价，最高价，平均价，以及按照价格升序排序的三个含供应商信息的集合
        List<PurchaseOrderDetail> purchaseOrderDetailList = purchaseOrderService.getPurchaseOrderDetailListByItemId(itemId);
        for(PurchaseOrderDetail pod:purchaseOrderDetailList){
            System.out.println(pod);
        }
        return ServiceResult.success(purchaseOrderDetailList);
    }
}
