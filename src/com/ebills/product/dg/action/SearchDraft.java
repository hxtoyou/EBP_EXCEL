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
import com.ebills.utils.CommonUtil;
import com.ebills.utils.EbpConstants;
import com.ebills.utils.EbpDao;
import com.ebills.utils.OutPutBean;

public class SearchDraft extends EAPAction {
		
		private OutPutBean output = null;
		
		protected HttpServletRequest request = null;
		
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
				
				
				String type = request.getParameter("type");
				String ccy = request.getParameter("cur");
				String swift = request.getParameter("swift");
				List<Object> paramList  = new ArrayList<Object>();
				paramList.add(0,type);
				paramList.add(1,ccy);
				paramList.add(2,swift);
				EbpDao gjjsDao = new EbpDao();
				String sql = "select p.draftno draftNo,p.tscur Cur,p.tsamt Amt,p.drafttype draftType,p.piaojuno draftNum from draftregfo p,pbstafo n where p.debittype=? and p.tscur=? and p.dshswiftcode=? and p.draftno=n.bizno(+) and (n.isclose='N' or n.isclose is null) and p.draftno not in (select draftno from notefo) and p.draftno not in (select curtbizno from butxntp where curtbizno is not null)";
		        List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
		        //context.put("xcpNote", CommonUtil.ListToJson(queryRet));
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
