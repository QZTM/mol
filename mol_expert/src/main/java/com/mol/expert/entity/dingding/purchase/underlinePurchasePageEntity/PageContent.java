package com.mol.expert.entity.dingding.purchase.underlinePurchasePageEntity;
import com.mol.expert.entity.dingding.login.DDUser;
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
