package com.mol.purchase.entity;

import lombok.Data;

/**
 * ClassName:BdMaterialRepair
 * Package:com.purchase.entity.thirdPlatform
 * Description
 *  物料表   （加工维修的物料）
 * @date:2019/9/11 14:04
 * @author:yangjiangyan
 */
@Data
public class BdMaterialRepair {

    private String pkMaterial;
    private String name;
    private String code;
    private String materialspec;
    private String materialtype;
    private String measdoc;
    private String brand;
    private String marbasclass;
    private String creationtime;
    private String creator;
    private String pk_org;
    private String pk_group;
    private Integer enablestate;
    private Integer type;

}
