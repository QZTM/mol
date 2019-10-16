package com.mol.expert.entity.itemAndItemType;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;


@Data
@Table(name = "bd_marbasclass")
public class ItemType {

    /**
     * pk_marbasclass,name,code,creationtime,creator,enablestate,innercode,pk_group,pk_org,pk_parent
     */
    /**
     * 主键
     */
    @Id()
    @Column(name = "pk_marbasclass")
    private String id;                   //主键
    private String name;                            //物料基本分类名称
    private String code;                            //物料基本分类编码
    private String creationtime;                    //创建时间
    private String creator;                         //创建人 (对应用友用户ID)
    private Integer enablestate;                    //启用状态 （1=未启用，2=已启用，3=已停用）
    private String innercode;                       //内部码
    private String pkGroup;                         //所属集团 (对应用友集团)
    private String pkOrg;                           //所属组织 (对应用友业务单元-组织)
    private String pkParent;                        //上级物料分类
    @Transient
    private Boolean open;                           //前台页面需要，是否打开，数据库查询后默认为false
    @Transient
    private List<ItemType> list = new ArrayList<>();
    @Transient
    private Boolean canOpen;
}
