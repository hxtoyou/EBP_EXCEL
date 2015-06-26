package com.ebills.product.dg.credit.action;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.bussprocess.context.Context;
import com.eap.core.EAPConstance;
import com.eap.flow.EAPAction;
import com.ebills.util.EbillsLog;
import com.ebills.product.dg.credit.dao.SearchIMCreditDao;
import com.ebills.utils.EbpConstants;
import com.ebills.utils.OutPutBean;

/**
 * 进口、公共交易ajax
 * @author zhangQi
 *
 */
public class SearchIMCreditAction extends EAPAction {
	private OutPutBean output = null;
	private SearchIMCreditDao searchIMCreditDao = null;
	protected HttpServletRequest request = null;
	private static String className = SearchIMCreditAction.class.getName();
	private static EbillsLog log = new EbillsLog(className);
	
	public String execute(Context context)
	{
		@SuppressWarnings("unused")
		String language = "";
		
		try{
			try {
				language = (String) context.getValue(EbpConstants.USER_LANGUAGE);
			} catch (Exception e) {
				language = "en_US";
			}
			
			Object object = null;
			
			if(context !=null){
				object = context.getValue(EAPConstance.SERVLET_REQUEST);
			}
			
			if(null!=object){
				request = (HttpServletRequest)object;
			}
			searchIMCreditDao = new SearchIMCreditDao();
			String method = request.getParameter("method");//方法名
			log.debug("method-------------->>>"+method);
			if("validInvoiceNo".equals(method)){
				searchIMCreditDao.validInvoiceNo(context,request,output);
			}else if("searchOrderNo".equals(method)){
				searchIMCreditDao.searchOrderNo(context, request, output);
			}else if("searchAgentPayAmt".equals(method)){
				searchIMCreditDao.searchAgentPayAmt(context, request, output);
			}else if("buSwiftNoValidate".equals(method)){
				searchIMCreditDao.buSwiftNoValidate(context, request, output);
			}else if("searchAppInfo".equals(method)){
				searchIMCreditDao.searchAppInfo(context, request, output);
			}else if("pbCanNoValidate".equals(method)){
				searchIMCreditDao.pbCanNoValidate(context, request, output);
			}else if("searchCalDate".equals(method)){
				searchIMCreditDao.searchCalDate(context, request, output);
			}else if("searchAcctno".equals(method)){
				searchIMCreditDao.searchAcctno(context, request, output);
			}else if("searchBziNoByTxnNo".equals(method)){
				searchIMCreditDao.searchBziNoByTxnNo(context, request, output);
			}else if("checkBigPayByBizNo".equals(method)){
				searchIMCreditDao.checkBigPayByBizNo(context, request, output);
			}else if("initDataLadBillNo".equals(method)){
				searchIMCreditDao.initDataLadBillNo(context, request, output);
			}else if("initAbNo".equals(method)){
				searchIMCreditDao.initAbNo(context, request, output);
			}else if("searchInfoByDiscNo".equals(method)){
				searchIMCreditDao.searchInfoByDiscNo(context, request, output);
			}else if("isOut".equals(method)){
				searchIMCreditDao.isOut(context, request, output);
			}else if("biNoNoValidate".equals(method)){
				searchIMCreditDao.biNoNoValidate(context, request, output);
			}else if("isUsedAcctNo".equals(method)){
				searchIMCreditDao.isUsedAcctNo(context, request, output);
			}else if("loadDepositInfo".equals(method)){
				searchIMCreditDao.loadDepositInfo(context, request, output);
			}else if("commitSuccess".equals(method)){
				searchIMCreditDao.commitSuccess(context, request, output);
			}else if("isDFFF".equals(method)){
				searchIMCreditDao.isDFFF(context, request, output);
			}else if("searchDsBal".equals(method)){
				searchIMCreditDao.searchDsBal(context, request, output);
			}else if("searchTradeNo".equals(method)){
				searchIMCreditDao.searchTradeNo(context, request, output);
			}else if("searchChargeInfo".equals(method)){
				searchIMCreditDao.searchChargeInfo(context, request, output);
			}else if("searchPayCheckInfo".equals(method)){
				searchIMCreditDao.searchPayCheckInfo(context, request, output);
			}else if("doLoadManualInfo".equals(method)){
				searchIMCreditDao.doLoadManualInfo(context, request, output);
			}else if("validateTxnNo".equals(method)){
				searchIMCreditDao.validateTxnNo(context, request, output);
			}else if("hasSendPackets".equals(method)){
				searchIMCreditDao.hasSendPackets(context, request, output);
			}else if("validateChargeBizno".equals(method)){
				searchIMCreditDao.validateChargeBizno(context, request, output);
			}else if("searchBziNoByCoreTxnNo".equals(method)){
				searchIMCreditDao.searchBziNoByCoreTxnNo(context, request, output);
			}
		}catch(Exception e)
		{
			e.printStackTrace();		
		}
		return "0";
	}

}

