package com.ebills.product.dg.action;

import java.util.ArrayList;
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
import com.ebills.utils.EbpDao;
import com.ebills.utils.OutPutBean;

public class GetIPPayAction extends EAPAction {

	protected HttpServletRequest request = null;
	private final Log log = LogFactory.getLog(getClass());
	public String execute(Context context) throws EAPException	{
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
			
			
			String ipno=request.getParameter("ipno");
			log.debug("代付业务编号:"+ipno);
			List<Object> paramList  = new ArrayList<Object>();
			String sql="select agentPayNo from IMLCPAYFO where agentPayNo=? and agentPayNo is not null " +
					"union all select agentPayNo from IMLCPAYTP WHERE agentPayNo=? and agentPayNo is not null " +
					"union all select replacepaynumber from pbpayfo where REPLACEPAYNUMBER=? and replacepaynumber is not null "+
			        "union all select replacepaynumber from pbpaytp where REPLACEPAYNUMBER=? and replacepaynumber is not null ";
			paramList.add(ipno);
			paramList.add(ipno);
			paramList.add(ipno);
			paramList.add(ipno);
			EbpDao gjjsDao = new EbpDao();
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
