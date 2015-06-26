package com.ebills.product.dg.action;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.bussprocess.context.Context;

import com.eap.core.EAPConstance;
import com.eap.exception.EAPException;
import com.eap.flow.EAPAction;
import com.ebills.utils.CommonUtil;
import com.ebills.utils.EbpConstants;
import com.ebills.utils.EbpDao;
import com.ebills.utils.OutPutBean;

public class SearchAccBkAcctNo extends EAPAction {

private OutPutBean output = null;
	
	protected HttpServletRequest request = null;
	
	public String execute(Context context) throws EAPException
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
			
			
			String swiftcode = request.getParameter("swiftcode");
			String cur= request.getParameter("cur");
			List<Object> paramList  = new ArrayList<Object>();
			paramList.add(0,swiftcode);
			paramList.add(1,cur);
			EbpDao gjjsDao = new EbpDao();
			String sql = "select m.outeracctno acct from pabank p,paaccfo m where p.bankno=m.custbankno and p.bankswiftcode=? and m.acctTypeNo in('ZA00000051','ZA00000052') and m.cursign=?";
	        List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
	        System.out.println(queryRet.size());
	        output = new OutPutBean(CommonUtil.ListToJson(queryRet));
	        output.writeOutPut(context);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
						
		}
		return "0";
	}
}
