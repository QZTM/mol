package com.mol.purchase.entity.dingding.purchase.underlinePurchasePageEntity;
import entity.dd.DDUser;
import lombok.Data;

import java.util.List;
@Data
public class PageContent
{
    private List<PageObj> pageObj;

    private String remarks;

    private List<DDUser> approves;

    private String applyCause;
}
