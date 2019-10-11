package com.mol.supplier.entity.dingding.purchase.underlinePurchasePageEntity;
import com.mol.supplier.entity.dingding.login.DDUser;
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
