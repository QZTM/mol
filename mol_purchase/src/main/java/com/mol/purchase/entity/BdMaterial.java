package com.mol.purchase.entity;

import lombok.Data;

@Data
public class BdMaterial {
    private String pkMaterial;
    private String name;
    private String code;
    private String materialspec;
    private String materialtype;
    private String pkMeasdoc;
    private String pkBrand;
    private String pkMarbasclass;
    private String creationtime;
    private String creator;
    private String pkOrg;
    private String pkGroup;
    private Integer enablestate;
}
