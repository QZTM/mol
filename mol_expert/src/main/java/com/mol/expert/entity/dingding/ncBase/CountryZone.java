package com.mol.expert.entity.dingding.ncBase;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 国家地区
 */
@Data
@Table(name = "bd_countryzone")
public class CountryZone {

    @Id
    @Column(name = "pk_country")
    private String id;

    //编码
    private String code;

    //三位代码
    private String codeth;

    //名称
    private String name;

    //电话代码
    private String phonecode;

    //全称
    private String wholename;
}
