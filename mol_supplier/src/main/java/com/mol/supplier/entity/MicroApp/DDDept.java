package com.mol.supplier.entity.MicroApp;

import lombok.Data;

import java.io.Serializable;

@Data
public class DDDept implements Serializable {
    private Boolean autoAddUser;
    private Boolean createDeptGroup;
    private Boolean deptHiding;
    private String deptManagerUseridList;
    private String deptPerimits;
    private String deptPermits;
    private Long errcode;
    private String errmsg;
    private Boolean groupContainSubDept;
    private Long id;
    private String name;
    private Long order;
    private String orgDeptOwner;
    private Boolean outerDept;
    private String outerPermitDepts;
    private String outerPermitUsers;
    private Long parentid;
    private String sourceIdentifier;
    private String userPerimits;
    private String userPermits;

}
