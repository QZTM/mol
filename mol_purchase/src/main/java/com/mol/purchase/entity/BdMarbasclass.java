package com.mol.purchase.entity;

import lombok.Data;

/**
 * ClassName:BdMarbasclass
 * Package:com.purchase.entity.thirdPlatform
 * Description
 *物料表
 * @date:2019/8/1 16:51
 * @author:yangjiangyan
 */
@Data
public class BdMarbasclass {

    private String pkMarbasclass;
    private String name;
    private String code;
    private String creationtime;
    private String creator;
    private Integer enablestate;
    private String innercode;
    private String pkGroup;
    private String pkOrg;
    private String pkParent;


}
