package com.mol.quartz.entity;

import lombok.Data;

@Data
public class ReturnMsg <T>{

	private T t;
	private String code;
	private String message;
	
	public ReturnMsg() {
		
	}
	
	public ReturnMsg(String code,String message) {
		this.code = code;
		this.message = message;
	}
	
	public ReturnMsg(String code,String message,T t) {
		this.code = code;
		this.message = message;
		this.t = t;
	}
	
	
}
