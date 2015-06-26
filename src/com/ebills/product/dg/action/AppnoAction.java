package com.ebills.product.dg.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.bussprocess.context.Context;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eap.core.EAPConstance;
import com.eap.exception.EAPException;
import com.eap.flow.EAPAction;
import com.ebills.utils.CommonUtil;
import com.ebills.utils.EbpConstants;
import com.ebills.utils.EbpDao;
import com.ebills.utils.OutPutBean;

public class AppnoAction extends EAPAction  {
	private OutPutBean output = null;
	protected HttpServletRequest request = null;
	private final Log log = LogFactory.getLog(getClass());
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
			
			
			String custno=request.getParameter("appno");
			log.debug("客户编号:"+custno);
			List<Object> paramList  = new ArrayList<Object>();
			String sql="select maincorpno from  pacrp where CORPNO=?";
			paramList.add(custno);
			EbpDao gjjsDao = new EbpDao();
	        List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
	        log.debug(queryRet.size());
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
