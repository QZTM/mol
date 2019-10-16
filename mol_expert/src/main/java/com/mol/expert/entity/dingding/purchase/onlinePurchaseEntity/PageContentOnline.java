package com.mol.expert.entity.dingding.purchase.onlinePurchaseEntity;

import com.mol.expert.entity.dingding.login.DDUser;
import lombok.Data;

import java.util.List;
/*
* 线上页面传递的数据
* */
@Data
public class PageContentOnline {

    private List<PageObj> pageObj;

    private String  remarks;

    private String  applyCause;

    private List<DDUser> approves;

}
