package com.mol.purchase.service.dingding.reptile;

import com.mol.purchase.entity.dingding.purchase.onlinePurchaseEntity.SellerArray;
import com.mol.purchase.util.robot.*;
import entity.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReptileService {

    private Logger logger = LoggerFactory.getLogger(ReptileService.class);

    /**
     * 爬取网络数据的排序规则
     */
    public static final String orderByPrice = "OrderByPrice";
    public static final String orderBySalesVolume = "OrderBySalesVolume";

    /**
     * 0代表天猫，1代表淘宝，2代表京东，3代表拼多多
     */
    public static final String dataFromTianMao = "TianMao";
    public static final String dataFromTaoBao = "TaoBao";
    public static final String dataFromJD = "JingDong";
    public static final String dataFromPinDuoDuo = "PinDuoDuo";


    @Async
    public ListenableFuture<List<SellerArray>> getNetGoods(String goodsName, String itemType, String orderRule, int count, String dataChannel) throws Exception{
        List<SellerArray> goodsList = new ArrayList<>();
        switch(dataChannel){
            case dataFromTianMao: goodsList = TianMao.getGoods(goodsName,itemType);
                break;
            case dataFromTaoBao: goodsList = TaoBao.getGoods(goodsName,itemType);
                break;
            case dataFromJD:
                int orderInt = 3;
                if(orderByPrice.equals(orderRule)){
                    orderInt = 2;
                }
                goodsList = JD.getGoods(goodsName,itemType,orderInt);
                goodsList = Currency.getGoodsWithCount(goodsList,count);
                return new AsyncResult<>(goodsList);
            case dataFromPinDuoDuo: goodsList = PinDuoDuo.getGoods(goodsName,itemType);
                break;
            default:goodsList = TianMao.getGoods(goodsName,itemType);
        }
        //System.out.println(goodsList.size());
        Currency.getSortedListByRule(goodsList,orderRule);
        goodsList = Currency.getGoodsWithCount(goodsList,count);
        return new AsyncResult<>(goodsList);
    }


    public ServiceResult getNetItemList2(String goodsName, String itemType, String orderRule, int count, String dataChannel) throws IOException {
        List<SellerArray> goodsList = Currency.getGoods(goodsName,itemType);
        Currency.getSortedListByRule(goodsList,orderRule);
        goodsList = Currency.getGoodsWithCount(goodsList,count);
        return ServiceResult.success(goodsList);
    }

}
