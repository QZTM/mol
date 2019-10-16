package com.mol.expert.service.microApp;

import com.mol.expert.entity.MicroApp.DDDept;
import com.mol.expert.entity.MicroApp.DDUser;
import com.mol.expert.entity.MicroApp.Salesman;
import com.mol.expert.entity.expert.ExpertUser;
import com.mol.expert.mapper.newMysql.expert.ExpertUserMapper;
import com.mol.expert.mapper.newMysql.microApp.MicroSalesmanMapper;
import com.mol.expert.util.IdWorker;
import com.mol.expert.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

@Service
public class MicroSalesmanService {

    private Logger logger = LoggerFactory.getLogger(MicroSalesmanService.class);

    @Resource
    private MicroSalesmanMapper microSalesmanMapper;

    @Autowired
    private ExpertUserMapper expertUserMapper;


    public Salesman getSalesman(Example example) {
        return microSalesmanMapper.selectOneByExample(example);
    }

    public ExpertUser getExpertUser(Example example){
        return expertUserMapper.selectOneByExample(example);
    }

    public void save(DDUser ddUser, DDDept org) {

        ExpertUser t =new ExpertUser();
        t.setId(new IdWorker().nextId()+"");
        t.setName(ddUser.getName());
        t.setAvatar(ddUser.getAvatar());
        t.setMobile(ddUser.getMobile());
        t.setOrgId(org.getName());
        t.setCreateTime(TimeUtil.getNowDateTime());
        t.setLastUpdateTime(TimeUtil.getNowDateTime());
        t.setLastSigninTime(TimeUtil.getNowDateTime());
        t.setAuthentication(0+"");
        t.setReviewNumber(0+"");
        t.setPass(0+"");
        t.setPassRate(0+"");
        t.setAward(0+"");
        expertUserMapper.insert(t);
    }

    public void updataSignInTime(ExpertUser expertUser) {
        ExpertUser u=new ExpertUser();
        u.setId(expertUser.getId());
        u.setLastSigninTime(TimeUtil.getNowDateTime());
        expertUserMapper.updateByPrimaryKeySelective(u);
    }
}
