package com.mol.purchase.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "bd_supplier_salesman")
public class SupplierSalesman {

    @Id
    private String id;
    private String name;
    private String phone;
    private Integer gender;
    private String ddUserId;
    private String wxUserId;
    private String pkSupplier;
    private String creationTime;
    private String lastLoginTime;
    private String lastLogoutTime;
    private String lastLoginWay;

}
