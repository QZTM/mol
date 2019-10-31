package com.mol.purchase.util;


import com.mol.purchase.entity.BdMaterial;
import com.mol.purchase.mapper.newMysql.BdMaterialMapper;
import com.mol.purchase.mapper.newMysql.dingding.purchase.BdMarbasclassMapper;
import entity.BdMarbasclass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 通过订单中的物料id，查询所属的最顶级分类id
 */
@Component
public class FindFirstMarbasclassByMaterialUtils {

    @Autowired
    private BdMarbasclassMapper bdMarbasclassMapper;

    @Autowired
    private BdMaterialMapper bdMaterialMapper;

    public  BdMarbasclass getFirstMarbasclass(String pkMaterial){
        BdMaterial bm=new BdMaterial();
        bm.setPkMaterial(pkMaterial);
        BdMaterial bdMaterial = bdMaterialMapper.selectOne(bm);

        BdMarbasclass t =new BdMarbasclass();
        t.setPkMarbasclass(bdMaterial.getPkMarbasclass());
        BdMarbasclass bdMarbasclass = bdMarbasclassMapper.selectOne(t);
        BdMarbasclass bm1 = getBm(bdMarbasclass);
        return bm1;
    }

    public BdMarbasclass getBm(BdMarbasclass bm){
        BdMarbasclass bms=new BdMarbasclass();
        bms.setPkMarbasclass(bm.getPkParent());
         BdMarbasclass bdMarbasclass = bdMarbasclassMapper.selectOne(bms);
        if (bdMarbasclass.getPkParent().equals("~")){
            return bdMarbasclass;
        }else {
            BdMarbasclass bmss= getBm(bdMarbasclass);
            return bmss;
        }

    }

}
