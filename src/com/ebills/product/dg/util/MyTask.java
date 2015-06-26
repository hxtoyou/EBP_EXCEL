package com.ebills.product.dg.util;

import java.util.Map;

import com.ebills.wf.task.Task;



public class MyTask extends Task {
	private String msgtype;
	private String bizno;
	private String sendercode;

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public String getBizno() {
		return bizno;
	}

	public void setBizno(String bizno) {
		this.bizno = bizno;
	}

	public String getSendercode() {
		return sendercode;
	}

	public void setSendercode(String sendercode) {
		this.sendercode = sendercode;
	}
	
	/* 
	 * 向任务信息中设置扩展属性，有几个属性设置几个。
	 * @see com.ebills.wf.task.Task#initProperties(java.util.Map)
	 */


	public void initProperties(Map<String, Object> row){
		this.setMsgtype( (String)row.get("msgtype") ); //msgtype大小写必须和视图中的一样
		this.setBizno( (String)row.get("bizno") ); //msgtype大小写必须和视图中的一样
		this.setSendercode( (String)row.get("sendercode") ); //msgtype大小写必须和视图中的一样
	}
	
	/* 向methodMapping中注册属性及对应的get方法，有几个属性就要注册几个
	 * @see com.ebills.wf.task.Task#initPropertiesMapping(java.util.Map)
	 */
	public void initPropertiesMapping(Map<String, String> methodMapping){
		methodMapping.put("msgtype", "getMsgtype"); //msgtype对应视图的大小写
		methodMapping.put("bizno", "getBizno"); 
		methodMapping.put("sendercode", "getSendercode"); 
	}
}