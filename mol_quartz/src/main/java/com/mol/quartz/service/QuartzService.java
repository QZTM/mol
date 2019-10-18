package com.mol.quartz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mol.quartz.entity.Quartz;
import com.mol.quartz.mapper.QuartzMapper;

import tk.mybatis.mapper.entity.Example;

@Service
public class QuartzService {
	
	@Autowired
	private QuartzMapper appQuartzMapper;
	
	public void insertAppQuartzSer(Quartz quartz) {
		appQuartzMapper.insert(quartz);
	}
	
	public List<Quartz> selectAppQuartzByIdSer(Integer id){
		Example example = new Example(Quartz.class);
		example.and().andEqualTo("quartzId", id);
		return appQuartzMapper.selectByExample(example);
	}
	
	public void deleteAppQuartzByIdSer(Integer id) {
		Example example = new Example(Quartz.class);
		example.and().andEqualTo("quartzId", id);
		appQuartzMapper.deleteByExample(example);
	}
	
	public void updateAppQuartzSer(Quartz quartz) {
		appQuartzMapper.updateByPrimaryKeySelective(quartz);
	}

}
