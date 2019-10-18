package com.mol.quartz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mol.quartz.bean.AppQuartz;
import com.mol.quartz.mapper.AppQuartzMapper;

import tk.mybatis.mapper.entity.Example;

@Service
public class AppQuartzService {
	
	@Autowired
	private AppQuartzMapper appQuartzMapper;
	
	public void insertAppQuartzSer(AppQuartz appQuartz) {
		appQuartzMapper.insert(appQuartz);
	}
	
	public List<AppQuartz> selectAppQuartzByIdSer(Integer id){
		Example example = new Example(AppQuartz.class);
		example.and().andEqualTo("quartzId", id);
		return appQuartzMapper.selectByExample(example);
	}
	
	public void deleteAppQuartzByIdSer(Integer id) {
		Example example = new Example(AppQuartz.class);
		example.and().andEqualTo("quartzId", id);
		appQuartzMapper.deleteByExample(example);
	}
	
	public void updateAppQuartzSer(AppQuartz appQuartz) {
		appQuartzMapper.updateByPrimaryKeySelective(appQuartz);
	}

}
