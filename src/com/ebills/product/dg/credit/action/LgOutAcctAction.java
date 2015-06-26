package com.ebills.product.dg.credit.action;

import org.apache.commons.bussprocess.context.Context;

import com.eap.flow.EAPAction;
import com.ebills.util.EbillsLog;
import com.ebills.product.dg.credit.send.LgOutAcct;

public class LgOutAcctAction extends EAPAction{

	private static String className = LgOutAcctAction.class.getName();
	private static EbillsLog log = new EbillsLog(className);
	public String execute(Context context){
		log.debug("===========================>>保函开立台账启动<<===========================");
		LgOutAcct outAcct=new LgOutAcct();
		outAcct.saveOutAcct();
		log.debug("===========================>>保函开立台账结束<<===========================");
		return "0";
	}

}
