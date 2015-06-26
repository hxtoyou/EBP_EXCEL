package com.ebills.product.dg.credit.action;

import org.apache.commons.bussprocess.context.Context;
import org.apache.commons.bussprocess.exception.BPException;

import com.eap.flow.EAPAction;
import com.ebills.product.dg.credit.send.SendCreditNoticeBiz;

public class SendCROUAction extends EAPAction {

	SendCreditNoticeBiz noticBiz=new SendCreditNoticeBiz();
	public String execute(Context cxt) throws BPException {
		try {
			noticBiz.callCrou(cxt);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.execute(cxt);
	}
}
