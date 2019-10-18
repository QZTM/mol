package com.mol.quartz.service;

import com.mol.quartz.mapper.PurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseMapper purchaseMapper;


    public Integer compareQuoteSellerNumAndSellerCountById(String id){
        return purchaseMapper.compareQuoteSellerNumAndSellerCountById(id);
    }
}
