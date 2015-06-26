package com.ebills.product.dg.action;

import java.text.ParseException;
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
import com.ebills.util.EbillsException;
import com.ebills.utils.CommonUtil;
import com.ebills.utils.EbpConstants;
import com.ebills.utils.EbpDao;
import com.ebills.utils.OutPutBean;

public class ClearingDealAction extends EAPAction {
	
	
	protected HttpServletRequest request = null;
	
	public String execute(Context context) throws EAPException {
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
			String method = request.getParameter("method");
			System.out.println("method-------------->>>"+method);
			if(method.equals("comClearingTime")){
				comClearingTime(context,request);
			}else if(method.equals("countClPreCash")){
				countClPreCash(context,request);
			}else if(method.equals("getCorpInfo")){
				getCorpInfo(context,request);
			}
			
			
		} catch(Exception e)	{
			e.printStackTrace();
						
		}
		return "0";
	}
	
	/**
	 * 提交时间与清算时间对比
	 * @param context
	 * @param request
	 * @param output
	 * @throws EbillsException
	 * @throws EAPException
	 */
	public void comClearingTime(Context context,HttpServletRequest request)throws EbillsException, EAPException{
		//取清算时间参数表中的清算时间
		String acctBkSwift = request.getParameter("acctBkSwift");
		String ccy = request.getParameter("ccy");
		List<Object> paramList  = new ArrayList<Object>();
		paramList.add(0,acctBkSwift);
		paramList.add(1,ccy);
		EbpDao gjjsDao = new EbpDao();
		String sql = "SELECT A.CLEARINGTIME FROM PACLRTIM A WHERE A.ACCTBKSWIFT = ? AND A.CCY = ? ";
        List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
        if(null != queryRet){
        	System.out.println(queryRet.size());
        }
        
        String flag = "";
        if(null!=queryRet && queryRet.size()>0){
	        try {
		        //将起息日转换为日期格式
		        String valueDate = request.getParameter("valueDate");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date valueDate1 = sdf.parse(valueDate);//起息日
				System.out.println("起息日:" + sdf.format(valueDate1));
				
				//获取系统跑批时间（提交日期）
				String workDay = CommonUtil.getSysFld(EbpConstants.WORK_DATE);
				Date workDay1 = sdf.parse(workDay);//提交日期
				System.out.println("提交日期:" + sdf.format(workDay1));
				if(workDay1.compareTo(valueDate1)>0){//提交日期大于起息日(清算日期)，不在清算时间内
					flag = "out";
				}else if(workDay1.compareTo(valueDate1)<0){//提交日期小于起息日，在清算时间之内
					flag = "in";
				}else{//提交日期等于起息日(清算日期),判断提交时间是否过清算时间
					Date date = new Date();
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
	        } catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("flag", flag);
        
        OutPutBean output = new OutPutBean(CommonUtil.MapToJson(map));
        output.writeOutPut(context);
	}
	
	/**
	 * 账户行、币种、起息日相同条件的数据条数
	 * @param context
	 * @param request
	 * @param output
	 * @throws EbillsException
	 * @throws EAPException
	 */
	public void countClPreCash(Context context,HttpServletRequest request)throws EbillsException, EAPException{
		String orgNo = request.getParameter("orgNo");
		String accbkswiftcode = request.getParameter("accbkswiftcode");
		String cashccy = request.getParameter("cashccy");
		String valueDate = request.getParameter("valueDate");
		List<Object> paramList  = new ArrayList<Object>();
		paramList.add(0,orgNo);
		paramList.add(1,accbkswiftcode);
		paramList.add(2,cashccy);
		paramList.add(3,valueDate);
		EbpDao gjjsDao = new EbpDao();
		String sql = "SELECT COUNT(*) COUNT FROM CLPRECASHFO A WHERE A.ORGNO = ? AND A.ACCBKSWIFTCODE = ? AND A.CASHCCY = ? AND A.VALUEDATE = to_date(?,'YYYY - MM -dd')";
        List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
        String sqlTp = "SELECT COUNT(*) COUNTTP FROM CLPRECASHTP A WHERE A.ORGNO = ? AND A.ACCBKSWIFTCODE = ? AND A.CASHCCY = ? AND A.VALUEDATE = to_date(?,'YYYY - MM -dd')";
        List<Map<String,Object>> queryRetTp = gjjsDao.sqlQuery(sqlTp, paramList);
        Map<String, String> map = new HashMap<String, String>();
        map.put("count", queryRet.get(0).get("COUNT").toString());
       	map.put("countTp", queryRetTp.get(0).get("COUNTTP").toString());
       	OutPutBean output = new OutPutBean(CommonUtil.MapToJson(map));
        output.writeOutPut(context);
	}
	
	/**
	 * 根据账号获取客户信息
	 * @param context
	 * @param request
	 * @param output
	 * @throws EbillsException
	 * @throws EAPException
	 */
	public void getCorpInfo(Context context,HttpServletRequest request)throws EbillsException, EAPException{
		String acctNo = request.getParameter("acctNo");
		List<Object> paramList  = new ArrayList<Object>();
		paramList.add(0,acctNo);
		EbpDao gjjsDao = new EbpDao();
		String sql = "select p2.corpno,p2.enname,p2.enaddr from paaccfo p1 left join pacrp p2 on p1.custbankno = p2.corpno where p1.acctno = ?";
        List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
        OutPutBean output = new OutPutBean(CommonUtil.ListToJson(queryRet));
        output.writeOutPut(context);
	}
}
