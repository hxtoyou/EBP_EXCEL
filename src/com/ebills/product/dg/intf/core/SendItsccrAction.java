package com.ebills.product.dg.intf.core;

import org.apache.commons.bussprocess.context.Context;
import org.apache.commons.bussprocess.exception.BPException;

import com.eap.flow.EAPAction;

public class SendItsccrAction extends EAPAction{

	public String execute(Context cxt) throws BPException {
		try {
			ItsccrBiz biz=new ItsccrBiz();
			 biz.callItsccr(cxt);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.execute(cxt);
	}

}
