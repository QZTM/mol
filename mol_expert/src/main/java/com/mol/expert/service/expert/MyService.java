package com.mol.expert.service.expert;

import com.mol.expert.entity.dingding.solr.fyPurchase;
import com.mol.expert.entity.expert.ExpertRecommend;
import com.mol.expert.entity.expert.ExpertUser;
import com.mol.expert.mapper.newMysql.expert.ExpertRecomendMapper;
import com.mol.expert.mapper.newMysql.expert.ExpertUserMapper;
import com.mol.expert.mapper.newMysql.third.BdMarbasclassMapper;
import entity.BdMarbasclass;
import org.apache.commons.codec.binary.Base64;
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

    public List<fyPurchase> getExpertRecommendByExpertIdAndAdopt(String expertId) {
        return expertRecomendMapper.findPur(expertId);
    }

    public List<fyPurchase> getHistroy(String expertId) {
        return expertRecomendMapper.findPurAndAdoptSuccessOrFail(expertId);
    }

    public ExpertUser getMyInfo(String id) {
        return expertUserMapper.selectByPrimaryKey(id);
    }

    public int selectReviewRecommend(String id) {
        ExpertRecommend t=new ExpertRecommend();
        t.setExpertId(id);
        return expertRecomendMapper.selectCount(t);
    }

    public int selectSuccessRecommend(String id) {
        ExpertRecommend t=new ExpertRecommend();
        t.setExpertId(id);
        t.setAdopt(1+"");
        return expertRecomendMapper.selectCount(t);
    }

    public int selectSuccessAndFailRecommend(String id) {
        Example o=new Example(ExpertRecommend.class);
        o.and().andEqualTo("expertId",id);
        o.or().andEqualTo("adopt",1).andEqualTo("adopt",0);
        return expertRecomendMapper.selectCountByExample(o);
    }

    public void updataExpertUser(String id,int reviewCount, int successCount, int suAndFaCount) {

        expertUserMapper.updataReviewAndPassAndRateById(id,reviewCount+"",successCount+"",suAndFaCount+"");
    }

    public ExpertUser setMarIdToChine(ExpertUser user, List<BdMarbasclass> firstBdMar) {
        if (user.getMarId()!=null){
            for (BdMarbasclass bdMarbasclass : firstBdMar) {
                if (bdMarbasclass.getPkMarbasclass().equals(user.getMarId())){
                    user.setMarId(bdMarbasclass.getName());
                    break;
                }
            }
        }
        return user;
    }

    public ExpertUser decodeBase64ToImage(ExpertUser user) {
        //Base64.decodeBase64(user.getFrontOfId().re)
        return null;
    }

    public BdMarbasclass getMarbasclassByMarbasclassId(String marId) {
        BdMarbasclass t =new BdMarbasclass();
        t.setPkMarbasclass(marId);
        return bdMarbasclassMapper.selectOne(t);
    }
}
