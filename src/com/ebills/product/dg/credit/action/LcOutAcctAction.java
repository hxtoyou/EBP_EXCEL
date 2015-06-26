package com.ebills.product.dg.credit.action;

import org.apache.commons.bussprocess.context.Context;

import com.eap.flow.EAPAction;
import com.ebills.util.EbillsException;
import com.ebills.util.EbillsLog;
import com.ebills.product.dg.credit.send.LcOutAcct;

public class LcOutAcctAction extends EAPAction{

	private static String className = LcOutAcctAction.class.getName();
	private static EbillsLog log = new EbillsLog(className);
	public String execute(Context context){
		log.debug("===========================>>信用证台账启动<<===========================");
		LcOutAcct outAcct=new LcOutAcct();
		try {
			outAcct.saveOutAcct();
			log.debug("===========================>>信用证台账结束<<===========================");
			outAcct.saveOutTHDBAcc();
			log.debug("===========================>>提货担保台账结束<<===========================");
		} catch (EbillsException e) {
			e.printStackTrace();
		}
		return "0";
	}

}
