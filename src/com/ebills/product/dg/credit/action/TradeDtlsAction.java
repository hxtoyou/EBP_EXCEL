package com.ebills.product.dg.credit.action;

import org.apache.commons.bussprocess.context.Context;

import com.eap.flow.EAPAction;
import com.ebills.util.EbillsLog;
import com.ebills.product.dg.credit.send.TradeDtlsBiz;

public class TradeDtlsAction extends EAPAction{

	private static String className = TradeDtlsAction.class.getName();
	private static EbillsLog log = new EbillsLog(className);
	public String execute(Context context){
		log.debug("===========================>>交易明细台账启动<<===========================");
//		TradeDtlsBiz dtlsBiz=new TradeDtlsBiz();
		//dtlsBiz.saveTradeDtls();
		log.debug("===========================>>交易明细账结束<<===========================");
		return "0";
	}

}
