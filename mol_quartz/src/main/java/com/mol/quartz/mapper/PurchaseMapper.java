package com.mol.quartz.mapper;

import com.mol.base.BaseMapper;
import com.mol.quartz.entity.Purchase;

public interface PurchaseMapper extends BaseMapper<Purchase> {

    public Integer compareQuoteSellerNumAndSellerCountById(String id);
}
