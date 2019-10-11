package com.mol.supplier.entity.dingding.purchase.workBench;

import lombok.Data;

import java.util.List;

/**
 * ClassName:Ucharts
 * Package:com.mol.supplier.entity.dingding.purchase.workBench
 * Description
 *  Ucharts内容demo
 * @date:2019/8/22 13:16
 * @author:yangjiangyan
 */
@Data
public class Ucharts {

    private String[] categories;

    private List<UchartsSeries> series;

}
