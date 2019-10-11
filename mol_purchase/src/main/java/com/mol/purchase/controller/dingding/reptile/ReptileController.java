package com.mol.purchase.controller.dingding.reptile;

import com.mol.purchase.entity.dingding.purchase.onlinePurchaseEntity.SellerArray;
import com.mol.purchase.service.dingding.reptile.ReptileService;
import com.mol.purchase.util.robot.*;
import entity.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class ReptileController {

    @Autowired
    private ReptileService reptileService;

    /**
     * 销量排序
     *
     * @param goodsName 商品名称
     * @param itemType  商品分类
     * @param orderRule 排序规则   OrderByPrice代表按照价格升序，OrderBySalesVolume代表按照销量降序  service中的dataChannel为商品获取渠道（0代表天猫，1代表淘宝，2代表京东，3代表拼多多）
     * @param count     商品个数
     * @return
     */
    @RequestMapping(value = "/getNetItemList")
    public ServiceResult getNetItemList(String goodsName, String itemType, String orderRule, int count) throws Exception {
        List<SellerArray> goodsList = new ArrayList<>();
        //开启多线程获取多渠道商品：
        List<SellerArray> tianMaoList = reptileService.getNetGoods(goodsName,itemType,orderRule,count,ReptileService.dataFromTianMao).get();
        List<SellerArray> jdList = reptileService.getNetGoods(goodsName,itemType,orderRule,count,ReptileService.dataFromJD).get();
        List<SellerArray> pinDuoDuoList = reptileService.getNetGoods(goodsName,itemType,orderRule,count,ReptileService.dataFromPinDuoDuo).get();
        if(tianMaoList != null){
            goodsList.addAll(tianMaoList);
        }
        if(jdList != null){
            goodsList.addAll(jdList);
        }
        if(pinDuoDuoList != null){
            goodsList.addAll(pinDuoDuoList);
        }
        goodsList = Currency.getSortedListByRule(goodsList,orderRule);
        return ServiceResult.success(goodsList);
    }

    @RequestMapping("/getTianMapItemList")
    public ServiceResult getTianMapItemList(String goodsName, String itemType, String orderRule, int count) throws Exception{
        List<SellerArray> tianMaoList = reptileService.getNetGoods(goodsName,itemType,orderRule,count,ReptileService.dataFromTianMao).get();
        return ServiceResult.success(tianMaoList);
    }

}

