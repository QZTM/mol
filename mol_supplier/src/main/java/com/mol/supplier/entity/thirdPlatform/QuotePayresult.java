package com.mol.supplier.entity.thirdPlatform;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Classname QuotePayresult
 * @Description TODO
 * @Date 2019/11/5 11:43
 * @Created by Lenovo
 */
@Data
@Table(name = "fy_quote_payresult")
public class QuotePayresult {
    @Id
    private String id;
    private String purchaseId;
    private String supplierId;
    private String payResult;
}
