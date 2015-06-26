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

public class BFCPQueryOrg extends EAPAction {
	
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
			
			
			String orgNo = request.getParameter("orgNo");
			List<Object> paramList  = new ArrayList<Object>();
			paramList.add(0,orgNo);
			paramList.add(1,orgNo);
			EbpDao gjjsDao = new EbpDao();
			String sql = " select t.orgno,t.orgname from paorg t where t.orgno=? or t.parentorgno=?";
			
	        List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
	        
	        output = new OutPutBean(CommonUtil.ListToJson(queryRet));
	        output.writeOutPut(context);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
		    throw new EAPException("1", e.getMessage());
			
		}
		return "0";
	}
}
