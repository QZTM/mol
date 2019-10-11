package com.mol.supplier.entity.MicroApp;

import lombok.Data;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "bd_supplier_salesman")
public class Salesman {

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

    //最后登录方式，0为未知，1为钉钉，2为微信
    private Integer lastLoginWay;


}
