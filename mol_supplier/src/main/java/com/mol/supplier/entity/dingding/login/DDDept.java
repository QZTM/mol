package com.mol.supplier.entity.dingding.login;

import lombok.Data;

@Data
public class DDDept{

    private Long id;
    private Long parentid;
    private String name;
    /**
     * 部门的主管列表
     */
    private String deptManagerUseridList;
    /**
     * 企业群群主
     */
    private String orgDeptOwner;

}
