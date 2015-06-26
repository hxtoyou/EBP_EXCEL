/**
 * 
 */
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
import com.ebills.declare.util.GeneralCalc;
import com.ebills.util.EbillsException;
import com.ebills.utils.CommonUtil;
import com.ebills.utils.EbpDao;
import com.ebills.utils.OutPutBean;

/**
 * @author Administrator
 *
 */
public class DclParamQueryAction extends EAPAction {
	
private OutPutBean output = null;
	
	public HttpServletRequest request = null;
	
	/***
	 * Action 默认执行方法
	 */
	public String execute(Context context) throws EAPException
	{
		try
		{
			if(context !=null)
			{
				Object object = context.getValue(EAPConstance.SERVLET_REQUEST);
			    
				if(null!=object)
				{
					request = (HttpServletRequest)object;
				}
				else
				{
					 throw new EAPException("1", "获取请求信息(HttpServletRequest)失败!");
				}
				request.getParameter("sysDataParamInfo");
			}
			else
			{
				throw new EAPException("1", "获取上下文(Context)信息失败!");
			}
			
			EbpDao gjjsDao = new EbpDao();
			String method = request.getParameter("method");
			List<Map<String,Object>> queryRet =null;
		
			if("dclParamQuery".equals(method))
			{
				queryRet =	queryDataInfo(request,gjjsDao);
			}
			
			if(null!=queryRet)
			{
				output = new OutPutBean(CommonUtil.ListToJson(queryRet));
		        output.writeOutPut(context);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		    throw new EAPException("1", e.getMessage());
		}
		
		return "0";
	}

	
	/***
	 * 查询参数信息
	 * @param request
	 * @param gjjsDao
	 * @return
	 * @throws EbillsException
	 */
	private List<Map<String,Object>> queryDataInfo(HttpServletRequest request,EbpDao gjjsDao) throws EbillsException
	{
		List<Object> paramList  = new LinkedList<Object>();
		List<Map<String,Object>> queryRet = new ArrayList<Map<String,Object>>();
		String sql = "";
		
		String tabName = request.getParameter("tabName");
		if(GeneralCalc.isNotNull(tabName)){
			if("pazhxz".equals(tabName)){
				String zhxzcode = request.getParameter("zhxzcode");
			
				if(GeneralCalc.isNotNull(zhxzcode)){
					paramList.add(zhxzcode);
					sql = "select count(*) as count from pazhxz where zhxzcode=?";
					queryRet  = gjjsDao.sqlQuery(sql, paramList);
				}
			}
		}
		
        return queryRet;
	}
}
