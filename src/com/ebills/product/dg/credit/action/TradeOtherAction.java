package com.ebills.product.dg.credit.action;

import org.apache.commons.bussprocess.context.Context;

import com.eap.flow.EAPAction;
import com.ebills.util.EbillsLog;

public class TradeOtherAction extends EAPAction{

	private static String className = TradeOtherAction.class.getName();
	private static EbillsLog log = new EbillsLog(className);
	public String execute(Context context){
		return "0";
	}
}
