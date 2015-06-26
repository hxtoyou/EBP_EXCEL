package com.ebills.product.dg.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.bussprocess.context.Context;

import com.eap.core.EAPConstance;
import com.eap.exception.EAPException;
import com.eap.flow.EAPAction;
import com.ebills.product.dg.commons.UsrLogUtil;

public class UsrLogAction extends EAPAction{

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
			
			String userId=request.getParameter("userId");
			String opflag=request.getParameter("opflag");
			UsrLogUtil.saveUsrLog(userId, opflag,userId, "");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
						
		}
		return "0";
	}

}
