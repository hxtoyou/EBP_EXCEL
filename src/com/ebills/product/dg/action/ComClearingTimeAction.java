package com.ebills.product.dg.action;
//无效类，代码整合到ClearingDealAction.java中
/*package com.gjjs.ebills.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.bussprocess.context.Context;

import com.eap.core.EAPConstance;
import com.eap.exception.EAPException;
import com.eap.flow.EAPAction;
import com.gjjs.util.CommonUtil;
import com.gjjs.util.GjjsConstants;
import com.gjjs.util.GjjsDao;
import com.gjjs.util.OutPutBean;
import com.ibm.icu.text.DateFormat;

public class ComClearingTimeAction extends EAPAction {
	
private OutPutBean output = null;
	
	protected HttpServletRequest request = null;
	
	public String execute(Context context) throws EAPException
	{
		String language = "";
		
		try{
			try {
				language = (String) context.getValue(GjjsConstants.USER_LANGUAGE);
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
			
			//取清算时间参数表中的清算时间
			String acctBkSwift = request.getParameter("acctBkSwift");
			String ccy = request.getParameter("ccy");
			List<Object> paramList  = new ArrayList<Object>();
			paramList.add(0,acctBkSwift);
			paramList.add(1,ccy);
			GjjsDao gjjsDao = new GjjsDao();
			String sql = "SELECT A.CLEARINGTIME FROM PACLRTIM A WHERE A.ACCTBKSWIFT = ? AND A.CCY= ? ";
	        List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
	        System.out.println(queryRet.size());
	        
	        String flag = "";
	        if(queryRet.size()>0){
		        Date date = new Date();
		        
		        //将起息日转换为日期格式
		        String valueDate = request.getParameter("valueDate");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date valueDate1 = sdf.parse(valueDate);
				System.out.println("起息日期:" + valueDate1.toString());
		        //获取服务器日期
				Date date1 = sdf.parse(sdf.format(date));
				System.out.println("提交日期:" + date1.toString());
				
				if(date1.compareTo(valueDate1)>0){//提交日期大于起息日(清算日期)，不在清算时间内
					flag = "out";
				}else if(date1.compareTo(valueDate1)<0){//提交日期小于起息日，在清算时间之内
					flag = "in";
				}else{//提交日期等于起息日(清算日期)
			        int t1 = date.getHours()*3600 + date.getMinutes()*60 + date.getSeconds();
			        String clearingTime = (String) queryRet.get(0).get("CLEARINGTIME");
			        System.out.println("清算时间:" + clearingTime);
			        System.out.println("提交时间:" + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds());
			        String[] timeElem = clearingTime.split(":");
			        int t2 = Integer.parseInt(timeElem[0])*3600 + Integer.parseInt(timeElem[1])*60 + Integer.parseInt(timeElem[2]);
			        if(t1 > t2){//提交时间大于清算时间
			        	flag = "out";
			        }else if(t1 == t2){
			        	flag = "equal";
			        }else{
			        	flag = "in";
			        }
				}
	        }
	        Map<String, String> map = new HashMap<String, String>();
	        map.put("flag", flag);
	        
	        output = new OutPutBean(CommonUtil.MapToJson(map));
	        output.writeOutPut(context);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
						
		}
		return "0";
	}

}
*/