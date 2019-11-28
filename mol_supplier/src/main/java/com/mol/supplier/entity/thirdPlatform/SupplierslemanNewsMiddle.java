package com.mol.supplier.entity.thirdPlatform;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "suppliersaleman_news_middle")
public class SupplierslemanNewsMiddle {
    @Id
    private Integer id;
    private String supplierSalemanId;
    private String suppNewsId;
    private String creationTime;
}
