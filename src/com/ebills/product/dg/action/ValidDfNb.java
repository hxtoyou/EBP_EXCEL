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

public class ValidDfNb extends EAPAction {
	
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
			
			
			String dfNb = request.getParameter("dfNb");
			String tranOrgNo=request.getParameter("tranOrgNo");
			List<Object> paramList  = new ArrayList<Object>();
			String sql;
			List<Map<String,Object>> queryRet=new ArrayList<Map<String,Object>>();
			paramList.add(0,dfNb);
			paramList.add(1,tranOrgNo);
			EbpDao gjjsDao = new EbpDao();
			sql = "select p.dfcur,p.dfamt,p.negoamt,p.appno,p.appname,p.benename,p.benencountry,p.benebankswiftcode,p.benebankswiftname,p.isi18n,p.fundflow,m.cnname from IMDFAPPLYfo p,butxnar n,pacrp m where m.corpno=p.appno and p.ipno=n.curtbizno and p.ipno=? and n.tranorgno=? and p.ownbusiz='2'";
	        queryRet = gjjsDao.sqlQuery(sql, paramList);
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
