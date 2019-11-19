package com.mol.supplier.entity.thirdPlatform;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "supp_news")
public class SuppNews {
    @Id
    private String id;
    private String title;
    private String introduction;
    private String mainText;
    private Integer numberOfReaders;
    private String creationTime;
    private String img;
}
