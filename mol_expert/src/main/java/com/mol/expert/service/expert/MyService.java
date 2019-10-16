package com.mol.expert.service.expert;

import com.mol.expert.entity.dingding.solr.fyPurchase;
import com.mol.expert.entity.expert.ExpertRecommend;
import com.mol.expert.entity.expert.ExpertUser;
import com.mol.expert.mapper.newMysql.expert.ExpertRecomendMapper;
import com.mol.expert.mapper.newMysql.expert.ExpertUserMapper;
import com.mol.expert.mapper.newMysql.third.BdMarbasclassMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * ClassName:MyService
 * Package:com.purchase.service.expert
 * Description
 *
 * @date:2019/10/10 10:44
 * @author:yangjiangyan
 */
@Service
public class MyService {

    @Autowired
    private BdMarbasclassMapper bdMarbasclassMapper;

    @Autowired
    private ExpertUserMapper expertUserMapper;

    @Autowired
    ExpertRecomendMapper expertRecomendMapper;
    /**
     * 获取第一级物料分类
     * @return
     */
    public List<BdMarbasclass> getFirstBdMar(){
        Example o = new Example(BdMarbasclass.class);
        o.and().andEqualTo("pkParent","~");
        return bdMarbasclassMapper.selectByExample(o);
    }

    public int updataImge(ExpertUser user) {
        return expertUserMapper.updateByPrimaryKeySelective(user);
    }

    public int updataMyMessage(ExpertUser eu) {
        return expertUserMapper.updateByPrimaryKeySelective(eu);
    }

    public List<fyPurchase> getExpertRecommendByExpertIdAndAdopt(ExpertRecommend er) {
        return expertRecomendMapper.findPurAndAdopt(er.getAdopt(),er.getExpertId());
    }

    public List<fyPurchase> getHistroy(ExpertRecommend er) {
        return expertRecomendMapper.findPurAndAdopt(null,er.getExpertId());
    }
}
