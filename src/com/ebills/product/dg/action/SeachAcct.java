package com.ebills.product.dg.action;

import java.util.ArrayList;
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

public class SeachAcct extends EAPAction {
	
	
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
			
			
			String bkNo = request.getParameter("bkNo");
			String cur=request.getParameter("cur");
			List<Object> paramList  = new ArrayList<Object>();
			paramList.add(0,bkNo);
			EbpDao gjjsDao = new EbpDao();
			String sql = "select acctno,OUTERACCTNO from paaccfo where acctTypeNo in ('ZA00000051', 'ZA00000052') and custbankno=?";
			if(cur!=null&&!"".equals(cur)){
				paramList.add(1,cur);
				sql=sql+" and cursign=?";
			}
	        List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
	        System.out.println(queryRet.size());
	        OutPutBean output = new OutPutBean(CommonUtil.ListToJson(queryRet));
	        output.writeOutPut(context);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
						
		}
		return "0";
	}
}
