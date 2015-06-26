package com.ebills.product.dg.action;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.bussprocess.context.Context;
import com.eap.exception.EAPException;
import com.ebills.util.EbillsException;
import com.eap.core.EAPConstance;
import com.eap.flow.EAPAction;
import com.ebills.utils.CommonUtil;
import com.ebills.utils.EbpConstants;
import com.ebills.utils.EbpDao;
import com.ebills.utils.OutPutBean;

public class QueryAcctNoAction extends EAPAction {
	protected HttpServletRequest request = null;
	
	public String execute(Context context)
	{
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
			System.out.println("method-------------->>>"+method);
			if("queryAcctNo".equals(method)){
				queryAcctNo(context,request);
			}
			else if("queryAcctBailPer".equals(method))
			{
				queryAcctBailPer(context,request);
			}
			else if("getHistoryInfo".equals(method))
			{
				getHistoryInfo(context,request);
			}
			else if("queryAcctBySwiftCode".equals(method))
			{
				queryAcctBySwiftCode(context,request);
			}
		}catch(Exception e)
		{
			e.printStackTrace();		
		}
		return "0";
	}
	
	public void queryAcctNo(Context context,HttpServletRequest request)throws EbillsException, EAPException{
		String cursign = request.getParameter("cursign");
		String cbSwiftCode = request.getParameter("cbSwiftCode");
		String sql ="select CBEACCOUNT,CBIACCOUNT   from  PACONTRACTBANK where CURRENCY=? and CBSWIFTCODE =?";
		List<Object> paramList  = new ArrayList<Object>();
		paramList.add(cursign);
		paramList.add(cbSwiftCode);
		EbpDao gjjsDao = new EbpDao();
        List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
        OutPutBean output = new OutPutBean(CommonUtil.ListToJson(queryRet));
        output.writeOutPut(context);
	}
	
	public void queryAcctBailPer(Context context,HttpServletRequest request)throws EbillsException, EAPException{
		String corpNo = request.getParameter("corpNo");
		String sql ="select HBBZJBL from pacrp where corpno =?";
		List<Object> paramList  = new ArrayList<Object>();
		paramList.add(corpNo);
		EbpDao gjjsDao = new EbpDao();
        List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
        OutPutBean output = new OutPutBean(CommonUtil.ListToJson(queryRet));
        output.writeOutPut(context);
	}
	
	public void getHistoryInfo(Context context,HttpServletRequest request)throws EbillsException, EAPException{
		String applyNo = request.getParameter("applyNo");
		StringBuffer strBuff = new StringBuffer();
		strBuff.append("select case when lan.val is null then p.tradeDesc else lan.val end as tradename, ");
		strBuff.append("b.txnno,b.curtbizno,b.trancur,b.tranamt,b.transtate from butxnar b ");
		strBuff.append("left join patrdtyp p on b.tradeno = p.tradeno left join palan lan on p.tradeNo = lan.keyVal ");
		strBuff.append("and lan.tabName = 'PATRDTYP.TRADEDESC' and lang = 'zh_CN' inner join butxnctxar t on t.txnno = b.txnno ");
		strBuff.append("and t.state in ('0', '2', '6') where (b.prebizno = ? or b.curtbizno = ?) ");
		strBuff.append("union all select case when lan.val is null then p.tradeDesc else lan.val end as tradename, ");
		strBuff.append("b.txnno,b.curtbizno,b.trancur,b.tranamt,b.transtate from butxntp b inner join butxnctxtp t ");
		strBuff.append("on b.txnno = t.txnno and t.state = '0' left join patrdtyp p on b.tradeno = p.tradeno ");
		strBuff.append("left join palan lan on p.tradeNo = lan.keyVal and lan.tabName = 'PATRDTYP.TRADEDESC' and lang = 'zh_CN' ");
		strBuff.append("where (b.prebizno = ? or b.curtbizno = ?)");
		String sql =new String(strBuff);
		List<Object> paramList  = new ArrayList<Object>();
		paramList.add(applyNo);
		paramList.add(applyNo);
		paramList.add(applyNo);
		paramList.add(applyNo);
		EbpDao gjjsDao = new EbpDao();
        List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
        OutPutBean output = new OutPutBean(CommonUtil.ListToJson(queryRet));
        output.writeOutPut(context);
	}
	
	public void queryAcctBySwiftCode(Context context,HttpServletRequest request)throws EbillsException, EAPException{
		String swiftCode = request.getParameter("swiftCode");
		String curSign = request.getParameter("curSign");
		String sql ="select b.acctno from PABANK a, PAACCFO b where a.bankNo = b.custBankNo " +
				"and b.acctTypeNo in ('ZA00000051', 'ZA00000052') and a.bankswiftcode = upper(?) " +
				"and b.cursign = ?";
		List<Object> paramList  = new ArrayList<Object>();
		paramList.add(swiftCode);
		paramList.add(curSign);
		EbpDao gjjsDao = new EbpDao();
        List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
        OutPutBean output = new OutPutBean(CommonUtil.ListToJson(queryRet));
        output.writeOutPut(context);
	}
}

