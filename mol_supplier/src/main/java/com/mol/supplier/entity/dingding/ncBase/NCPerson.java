package com.mol.supplier.entity.dingding.ncBase;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *  NC系统人员基本信息
 */
@Table(name = "bd_psndoc")
public class NCPerson {

    @Id
    @Column(name = "pk_psndoc ")
    private String id;


}
