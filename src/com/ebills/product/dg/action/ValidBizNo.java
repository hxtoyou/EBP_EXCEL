package com.ebills.product.dg.action;

import java.util.ArrayList;
import java.util.HashMap;
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

public class ValidBizNo extends EAPAction {
	
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
			
			
			String bizno = request.getParameter("bizno");
			String tradeno=request.getParameter("tradeno");
			List<Object> paramList  = new ArrayList<Object>();
			String sql;
			List<Map<String,Object>> queryRet=new ArrayList<Map<String,Object>>();
			Map<String,Object> info=new HashMap<String,Object>();
			paramList.add(0,bizno);
			EbpDao gjjsDao = new EbpDao();
			sql = "select m.tradedesc from butxntp p,patrdtyp m where p.curtbizno=? and p.tradeno=m.tradeno";
	        queryRet = gjjsDao.sqlQuery(sql, paramList);
	        if(queryRet.size()>0){
	        	info.put("info", "1");
	        	System.out.println(queryRet);
	        	String tradedesc=(String)queryRet.get(0).get("TRADEDESC");
	        	info.put("tradedesc", tradedesc);
	        	queryRet.clear();
	        	queryRet.add(info);
	        }else{
	        	sql="select * from butxnar where curtbizno=?";
	        	queryRet = gjjsDao.sqlQuery(sql, paramList);
	        	if(queryRet.size()>0){
	        		sql="select * from BUREMDEBTFO where srhBizNo=?";
	        		queryRet = gjjsDao.sqlQuery(sql, paramList);
	        		if(queryRet.size()>0){
	        			info.put("info", "2");
	        			queryRet.clear();
	        			queryRet.add(info);
	        		}else if("070101".equals(tradeno)||"070106".equals(tradeno)){
	        			sql="select remitCorpNo,remitCorpNameAddr,to_char(remitDate,'yyyy-MM-dd') remitDate from NTOUTWARDfo where outNo=?";
	        			queryRet.clear();
		        		queryRet = gjjsDao.sqlQuery(sql, paramList);
		        		info.put("info", "3");
		        		queryRet.add(info);
	        		}else{
	        			sql="select benefcorpno,benefcorpname,to_char(interetdate,'yyyy-MM-dd') interetdate,remitcorpno,remitcorpnameaddr,remitcorpcountry,opppartyclass,remitcur,remitamount,remitbankswiftcode,remitbankname,bigremitbankno,bigremitbankname,sendtype from NTINWARDfo where inwardno=?";
	        			queryRet.clear();
		        		queryRet = gjjsDao.sqlQuery(sql, paramList);
		        		info.put("info", "3");
		        		queryRet.add(info);
	        		}
	        	}else{
	        		info.put("info", "4");
	        		queryRet.clear();
	        		queryRet.add(info);
	        	}
	        }
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
