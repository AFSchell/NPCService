package com.mtapps.service;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ServiceResponse<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3226341815194548019L;

	private T data;
	
	private String msg;
	
	public ServiceResponse(T inData, String inMsg) {
		this.data = inData;
		this.msg = inMsg;
		
		
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
