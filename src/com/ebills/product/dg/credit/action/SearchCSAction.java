package com.ebills.product.dg.credit.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.bussprocess.context.Context;
import com.eap.core.EAPConstance;
import com.eap.flow.EAPAction;
import com.ebills.product.dg.credit.dao.SearchCSDao;
import com.ebills.utils.EbpConstants;
import com.ebills.utils.OutPutBean;

/**
 * 参数管理
 * @author zhouxf
 * /
 */

public class SearchCSAction extends EAPAction {
	private OutPutBean output = new OutPutBean("");
	private SearchCSDao searchCSDao = null;
	protected HttpServletRequest request = null;
	
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
			
			if(context !=null)
			{
				object = context.getValue(EAPConstance.SERVLET_REQUEST);
			}
			
			if(null!=object)
			{
				request = (HttpServletRequest)object;
			}
			searchCSDao = new SearchCSDao();
			String method = request.getParameter("method");//方法名
			System.out.println("method-------------->>>"+method);
			if("searchCSorgno".equals(method)){
				searchCSDao.searchCSorgno(context, request, output);
			}
			else if("clientparities".equals(method)){
				searchCSDao.clientparities(context, request, output);
			}
			else if("paritiesrenew".equals(method)){
				searchCSDao.paritiesrenew(context, request, output);
			}
			else if("serverdate".equals(method)){
				searchCSDao.serverdate(context, request, output);
			}
			else if("Loginstate".equals(method)){
				searchCSDao.Loginstate(context, request, output);
			}
			else if("Logoutstate".equals(method)){
				searchCSDao.Logoutstate(context, request, output);
			}
			else if("paritiescom".equals(method)){
				searchCSDao.paritiescom(context, request, output);
			}
		}catch(Exception e)
		{
			e.printStackTrace();		
		}
		return "0";
	}
}
