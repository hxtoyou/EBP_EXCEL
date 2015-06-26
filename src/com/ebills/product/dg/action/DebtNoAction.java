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

public class DebtNoAction extends EAPAction  {
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
			
			
			String debtno=request.getParameter("DEBTNO");
			String bizno=request.getParameter("BIZNO");
			log.debug("业务编号:"+bizno);
			String sql1="SELECT DEBTNO FROM BULOANSTP WHERE DEBTNO=?";
			String sqlbiz="AND DEBTNO NOT IN(SELECT DEBTNO FROM BULOANSTP T WHERE T.LOANBIZNO=?)";
			String sql2="UNION ALL SELECT DEBTNO FROM BULOANSFO WHERE DEBTNO=? UNION ALL SELECT DEBTNO FROM BULOANSAR WHERE DEBTNO=?";
			List<Object> paramList  = new ArrayList<Object>();
			String sql="";
			paramList.add(debtno);
			if(bizno!=null&&!"".equals(bizno)){
				paramList.add(bizno);
				 sql=sql1+sqlbiz+sql2;
			}else{
				 sql=sql1+sql2;
			}
			paramList.add(debtno);
			paramList.add(debtno);

			EbpDao gjjsDao = new EbpDao();
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
