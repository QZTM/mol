package com.mol.quartz.entity;

import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import com.mol.quartz.config.MolJob;
import lombok.Data;

@Data
@Table(name="job_entity")
public class Quartz {

	@Id
	@Column(name="id")
	private String quartzId;  //id  主键
	@Column(name="name")
    private String jobName;  //任务名称
    private Enum<MolJob> jobGroup;  //任务分组
    private String startTime;  //任务开始时间
    private String cronExpression;  //corn表达式
    private Map<String,Object> invokeParam;//需要传递的参数

    public Quartz setQuartzId(String quartzId){
        this.quartzId = quartzId;
        return this;
    }

    public Quartz setJobGroup(Enum<MolJob> jobGroup){
        this.jobGroup = jobGroup;
        return this;
    }

    public Quartz setStartTime(String startTime){
        this.startTime = startTime;
        return this;
    }

    public Quartz setCronExpression(String cronExpression){
        this.cronExpression = cronExpression;
        return this;
    }

    public Quartz setInvokeParam(Map<String,Object> invokeParam){
        this.invokeParam = invokeParam;
        return this;
    }

    public Quartz setJobName(String jobName){
        this.jobName = jobName;
        return this;
    }
}
