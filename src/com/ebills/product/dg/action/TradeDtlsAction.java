package com.ebills.product.dg.action;

import org.apache.commons.bussprocess.context.Context;

import com.eap.flow.EAPAction;

public class TradeDtlsAction extends EAPAction {
	private String className = TradeDtlsAction.class.getName();

	public String execute(Context context){
          //查询发送失败的数据
		//把失败的数据存入批量表
		return "0";
	}


}
