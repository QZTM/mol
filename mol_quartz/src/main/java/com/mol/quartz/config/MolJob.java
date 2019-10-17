package com.mol.quartz.config;

public enum MolJob {

	QUOTEENDJOB("报价截止事件"),
	EXPERTREVIEWJOB("专家评审开始事件");
	
	private String desc;
	
	private MolJob(String desc){
        this.desc=desc;
    }
	
	public String getDesc(){
        return desc;
    }

}
