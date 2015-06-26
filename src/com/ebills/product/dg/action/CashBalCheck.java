package com.ebills.product.dg.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.bussprocess.context.Context;

import com.eap.core.EAPConstance;
import com.eap.exception.EAPException;
import com.eap.flow.EAPAction;
import com.ebills.product.dg.credit.dao.SearchIMCreditDao;
import com.ebills.util.EbillsException;
import com.ebills.util.EbillsLog;
import com.ebills.utils.CommonUtil;
import com.ebills.utils.EbpConstants;
import com.ebills.utils.EbpDao;
import com.ebills.utils.OutPutBean;


public class CashBalCheck extends EAPAction{
	private static String className = SearchIMCreditDao.class.getName();
	private static EbillsLog log = new EbillsLog(className);
	protected HttpServletRequest request = null;
	private OutPutBean output = null;
	public String execute(Context context) throws EAPException
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
			
			if(context !=null)
			{
				object = context.getValue(EAPConstance.SERVLET_REQUEST);
			}
			
			if(null!=object)
			{
				request = (HttpServletRequest)object;
			}
			String method = request.getParameter("method");
			log.info("method-------------->>>"+method);
			if(method.equals("queryData")){
				queryData(context,request,output);
			}else if(method.equals("queryDairyBal")){
				queryDairyBal(context,request,output);
			}
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
						
		}
		return "0";
	}
	
	/**
	 * 查询账户行日初余额和日初余额
	 * @param context
	 * @param request2
	 * @param output2
	 * @throws EbillsException 
	 * @throws EAPException 
	 */
	private void queryDairyBal(Context context, HttpServletRequest 账单余额,
			OutPutBean output2) throws EbillsException, EAPException {
		String swiftCode = request.getParameter("swiftCode");
		String cur = request.getParameter("cur");
		String date = request.getParameter("date");
		String sql ="select t.bal BAL from buacctbkbal t where t.bankswifcode = ? " +
				"and t.cursign =? and to_char(t.updatedate,'YYYY-MM-DD') =?";
		List<Object> paramList  = new ArrayList<Object>();
		paramList.add(0,swiftCode);
		paramList.add(1,cur);
		paramList.add(2,date);
		EbpDao gjjsDao = new EbpDao();
		List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
		double bal1 = 0;
		if(queryRet!=null&&queryRet.size()>0){
			BigDecimal bal = (BigDecimal) queryRet.get(0).get("BAL");
			bal1 =bal.doubleValue();
		}
		//查询账单余额
		String sql2 ="select a.amount AMOUNT from clmsgquefo a  where a.msgid =" +
				" (select rgd from" +
				" (select t.rknmsgid rgd,rownum rn from clrknrcd t " +
				" where t.sendcode =? and t.currency = ? and to_char(t.valuedate,'YYYY-MM-DD') =?)" +
				" where rn<2)";
		List<Map<String,Object>> queryRet2 = gjjsDao.sqlQuery(sql2, paramList);
		double amt1 = 0;
		if(queryRet2!=null&&queryRet2.size()>0){
			BigDecimal amt = (BigDecimal) queryRet2.get(0).get("AMOUNT");
			amt1 =amt.doubleValue();
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("BAL", bal1);
		map.put("ZDBAL", amt1);
		List<Map<String,Object>> queryRet3 = new ArrayList<Map<String,Object>>();
		queryRet3.add(map);
		log.info("queryRet-------------------->>>"+queryRet);
		output = new OutPutBean(CommonUtil.ListToJson(queryRet3));
	    output.writeOutPut(context);
	}
	/**
	 * 查询账单明细
	 * @param context
	 * @param request
	 * @param output
	 * @throws EbillsException 
	 * @throws EAPException 
	 */
	private void queryData(Context context, HttpServletRequest request,
			OutPutBean output) throws EbillsException, EAPException {
		String swiftCode = request.getParameter("swiftCode");
		String cur = request.getParameter("cur");
		String date = request.getParameter("date");
		
		String sql = "select t.reckonno RECKONNO,t.dcflag DCFLAG,t.bizno BIZNO," 
				+ "t.currency CURRENCY,t.amount AMOUNT,t.dealtyp DEALTYP,t.valuedate VALEDATE,t.sendcode SENDCODE"
				+ " from clrknrcd t where  ";
		sql += " upper(t.sendcode)= upper(?)";
		sql += " and to_char(t.valuedate,'YYYY-MM-DD') = ?";
		sql += " and upper(t.currency) = upper(?)";
		List<Object> paramList  = new ArrayList<Object>();
		paramList.add(0,swiftCode);
		paramList.add(1,date);
		paramList.add(2,cur);
		
		EbpDao gjjsDao = new EbpDao();
		List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
		for(int i = 0; i<queryRet.size();i++){
					Map<String, Object> map = queryRet.get(i);
					String dealTyp = (String) map.get("DEALTYP");//处理类型
					if("SETTLEMENT".equals(dealTyp)){//针对收汇处理
						if(!canChange(map)){
							map.put("CANCHANGE", "Y");
							map.put("ISCHECK", "Y");
						}else{
							map.put("CANCHANGE", "N");
							map.put("ISCHECK", "N");
						}
					}else if("ABANDON".equals(dealTyp)||"INVESTMENT".equals(dealTyp)){
							map.put("CANCHANGE", "Y");
							map.put("ISCHECK", "N");
					}else{
						map.put("CANCHANGE", "Y");
						map.put("ISCHECK", "Y");
					}
			}
		log.info("queryRet-------------------->>>"+queryRet);
		output = new OutPutBean(CommonUtil.ListToJson(queryRet));
	    output.writeOutPut(context);
	}
	
	/**
	 * 是否能勾选判断
	 * 起息日当天已经清算的收汇，灰显
	 * @param map
	 * @return
	 * @throws EbillsException 
	 */
	private boolean canChange(Map<String, Object> map) throws EbillsException {
		boolean flag = true;
		String bizNo = (String) map.get("BIZNO");
		EbpDao gjjsDao = new EbpDao();
		String sql = "select count(*) COUNTS from clmsgquefo t"
					   + " where t.bizno = ? and t.clearstate not in ('Suspend','Assigned','Finish','Refix')";
		List<Object> paramList  = new ArrayList<Object>();
		paramList.add(0,bizNo);
		List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
		if(queryRet.size()>0){
			Map<String,Object> tempMap = queryRet.get(0);
			BigDecimal counts = (BigDecimal) tempMap.get("COUNTS");
			int count = counts.intValue();
			if(count>0){
				flag = false;
			}
		}
			return flag;
	}

	

}
