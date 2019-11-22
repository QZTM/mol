package com.mol.fadada.pojo;

import lombok.Data;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "fadada_contract_upload_record")
public class ContractUploadRecord implements Serializable {

    @Id
    private String id;
    private String contractId;
    private String uploadTime;

}
