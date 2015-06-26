package com.ebills.product.dg.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.bussprocess.context.Context;

import com.eap.core.EAPConstance;
import com.eap.exception.EAPException;
import com.eap.flow.EAPAction;
import com.ebills.utils.CommonUtil;
import com.ebills.utils.EbpDao;
import com.ebills.utils.OutPutBean;

public class QueryAcctByTyp extends EAPAction  {
	private OutPutBean output = null;
	protected HttpServletRequest request = null;
	
	public String execute(Context context) throws EAPException
	{
		
		try{
			Object object = null;
			
			if(context !=null)
			{
				object = context.getValue(EAPConstance.SERVLET_REQUEST);
			}
			
			if(null!=object)
			{
				request = (HttpServletRequest)object;
			}
			
			
			String orgNO=request.getParameter("orgNo");
			String accttype=request.getParameter("acctTyp");
			String cursign=request.getParameter("curSign");
			String custbankno=request.getParameter("bankNo");
			Map<String,Object> paraMap=new HashMap<String,Object>();
			paraMap.put("acctTypeNo", accttype);
			paraMap.put("acctOrgNo", orgNO);
			paraMap.put("custBankNo", custbankno);
			paraMap.put("curSign", cursign);
			EbpDao gjjsDao = new EbpDao();
	        List<Map<String,Object>> queryRet = gjjsDao.queryByDataId("PAACCFO", null, paraMap);
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
