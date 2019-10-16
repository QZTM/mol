package com.mol.expert.entity.dingding.ncBase;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 行政区划
 */
@Data
@Table(name = "bd_region@ncdb")
public class Region {

    @Id
    @Column(name = "pk_region")
    private String id;

    //行政区域编码
    private String code;

    //内部编码
    private String innercode;

    //行政区划名称
    private String name;

    //国家地区ID
    @Column(name = "pk_country")
    private String pkCountryId;

    //上级行政区划
    private String pkFather;

    //简称
    private String shortname;

    //邮编
    private String zipcode;
}
