package com.ebills.product.dg.action;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.bussprocess.context.Context;

import com.eap.core.EAPConstance;
import com.eap.exception.EAPException;
import com.eap.flow.EAPAction;
import com.ebills.product.dg.AcctInterface.DgAcctInterface;
import com.ebills.utils.CommonUtil;
import com.ebills.utils.EbpConstants;
import com.ebills.utils.OutPutBean;

public class QueryCrRateByCur extends EAPAction {
	
	private OutPutBean output = null;
	
	protected HttpServletRequest request = null;
	private DgAcctInterface dgAcctInterface=null;
	

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
				String cur=request.getParameter("invoiceCur");
				dgAcctInterface=new DgAcctInterface();
		        List<Map<String,Object>> queryRet =dgAcctInterface.getCrRateByCur(cur);
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
