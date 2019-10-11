package com.mol.supplier.entity.dingding.ncBase;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "bd_address@ncdb")
public class Address {

    @Id
    @Column(name = "pk_address")
    private String id;

    //城市
    @Column(name = "city")
    private String cityId;

    //编码
    private String code;

    //省份
    @Column(name = "province")
    private String provinceId;

    //县区
    @Column(name = "vsection")
    private String vsectionId;

    //地址详址
    private String detailinfo;

    //邮政编码
    private String postcode;





}
