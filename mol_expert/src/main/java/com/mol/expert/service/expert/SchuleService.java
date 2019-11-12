package com.mol.expert.service.expert;

import com.mol.expert.entity.dingding.login.AppAuthOrg;
import com.mol.expert.entity.dingding.solr.fyPurchase;
import com.mol.expert.entity.expert.ExpertRecommend;
import com.mol.expert.mapper.newMysql.dingding.purchase.fyPurchaseMapper;
import com.mol.expert.mapper.newMysql.expert.ExpertRecomendMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:SchuleService
 * Package:com.purchase.service.expert
 * Description
 *
 * @date:2019/10/8 10:39
 * @author:yangjiangyan
 */
@Service
public class SchuleService {

    @Autowired
    ExpertRecomendMapper expertRecomendMapper;

    @Autowired
    fyPurchaseMapper fyPurchaseMapper;

    @Autowired
    ExpertService expertService;

    /**
     * 查询推荐
     * @param adopt 推荐的状态
     * @param id 专家id
     * @return
     */
    public List<fyPurchase> findRecommendByAdopt(String adopt, String id) {
       return expertRecomendMapper.findPurAndAdopt(adopt,id);
    }

    /**
     * 通过专家推荐表中实例的订单id，查询订单实例
     * @param reList
     * @return
     */
    public List<fyPurchase> getPurList(List<ExpertRecommend> reList) {
        List<fyPurchase> list=new ArrayList<>();
        if (reList!=null){
            for (ExpertRecommend expertRecommend : reList) {
                fyPurchase oneById = fyPurchaseMapper.findOneById(expertRecommend.getPurchaseId());
                list.add(oneById);
            }
        }
        return list;
    }

    /**
     * 公司id换中文，时间截取年月日
     * @param purList
     * @return
     */
    public List<fyPurchase> changeOrgNameToZhongwen(List<fyPurchase> purList) {
        if (purList==null ){
            return purList;
        }
        for (fyPurchase pur : purList) {
            //公司名称
            AppAuthOrg appAuthOrg = expertService.getOrg(pur.getOrgId());
            if (appAuthOrg!=null){
                pur.setOrgId(appAuthOrg.getOrgName());
                //时间
                pur.setCreateTime(pur.getCreateTime().split(" ")[0]);
            }

        }
        return purList;
    }
}
