package com.mol.quartz.bean;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mol.quartz.config.MolJob;

import lombok.Data;

@Data
@Table(name="job_entity")
public class AppQuartz {

	@Id
	@Column(name="id")
	private Integer quartzId;  //id  主键
	@Column(name="name")
    private String jobName;  //任务名称
    private Enum<MolJob> jobGroup;  //任务分组
    private String startTime;  //任务开始时间
    private String cronExpression;  //corn表达式
    private Map<String,Object> invokeParam;//需要传递的参数
}
