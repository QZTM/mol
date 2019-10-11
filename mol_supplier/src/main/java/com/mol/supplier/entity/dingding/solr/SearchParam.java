package com.mol.supplier.entity.dingding.solr;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class SearchParam  implements Serializable {
    private String keyword;
}
