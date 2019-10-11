package com.mol.purchase.util.robot;
import com.mol.purchase.entity.dingding.purchase.onlinePurchaseEntity.SellerArray;
import com.mol.purchase.service.dingding.reptile.ReptileService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 爬虫通用方法
 */
public class Currency {

    /**
     * 通用的爬数据方法
     * @param goodsName
     * @param itemType
     * @return
     */
    public static List<SellerArray> getGoods(String goodsName, String itemType) {
        List<SellerArray> goodsList = new ArrayList<>();
        return goodsList;
    }


    /**
     * 销量降序序排列
     * @param goods
     * @return
     */
    public static void getSalesVolumeSort(List<SellerArray> goods){
        Collections.sort(goods, (Comparator) (a, b) -> {
            SellerArray a1 = (SellerArray)a;
            SellerArray b1 = (SellerArray)b;
            if(a1.getSalesVolume() == null){
                a1.setSalesVolume(0.0);
            }
            if(b1.getSalesVolume() == null){
                b1.setSalesVolume(0.0);
            }
            return (int)(b1.getSalesVolume()-a1.getSalesVolume());
        });
    }

    /**
     * 价格升序排列
     * @param goods
     * @return
     */
    public static void getPriceSort(List<SellerArray> goods){
        Collections.sort(goods, (Comparator) (a, b) -> {
            SellerArray a1 = (SellerArray)a;
            SellerArray b1 = (SellerArray)b;
            if(a1.getQuote() == null){
                a1.setQuote(0.0);
            }
            if(b1.getQuote() == null){
                b1.setQuote(0.0);
            }
            return (int)(a1.getQuote()-b1.getQuote());
        });
    }

    /**
     * 根据排序规则排序
     * @param goodsList     需要排序的集合
     * @param orderRule     排序规则
     */
    public static List<SellerArray> getSortedListByRule(List<SellerArray> goodsList, String orderRule){
        switch(orderRule){
            case ReptileService.orderByPrice: getPriceSort(goodsList);
                break;
            case ReptileService.orderBySalesVolume:
                getSalesVolumeSort(goodsList);
        }
        return goodsList;
    }


    /**
     * 按照指定数量截取
     * @param goodsList
     * @param count
     * @return
     */
    public static List<SellerArray> getGoodsWithCount(List<SellerArray> goodsList,int count){
        if(goodsList.size()<count){
            return goodsList;
        }
        return goodsList.subList(0,count);
    }
}
