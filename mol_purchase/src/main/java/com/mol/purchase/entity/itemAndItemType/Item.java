package com.mol.purchase.entity.itemAndItemType;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@Table(name="bd_material")
public class Item {

    @Id
    private String pkMaterial;                  //主键

    private String name;                        //名称

    private String code;                        //物料编码

    private String creationtime;                //创建时间

    private String creator;                     //创建人

    private Integer enablestate;                //启用状态（1=未启用，2=已启用，3=已停用）

    private String ename;                       //英语名称

    private String materialspec;                //规格

    private String materialtype;                //型号

    private String pkGroup;                     //所属集团（集团）

    private String pkMarbasclass;               //物料分类

    private String pkMattaxes;                  //物料税类

    private String pkMeasdoc;                   //主计量单位

    private String pkOrg;                       //所属组织（组织-业务单元）

    private String pkSource;                    //原始版本

    private String version;                     //版本号

    private String pkBrand;                      //品牌
    @Transient
    private String historyHigh;                //历史高价格
    @Transient
    private String historyLevel;                //历史均价
    @Transient
    private String historyLow;                 //历史低价
    @Transient
    private String countOfLastYearBuy;


}
