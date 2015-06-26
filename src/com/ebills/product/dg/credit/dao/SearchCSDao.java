package com.ebills.product.dg.credit.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.commons.bussprocess.context.Context;
import org.apache.commons.bussprocess.exception.InvalidArgumentException;
import org.apache.commons.bussprocess.exception.ObjectNotFoundException;
import org.apache.commons.lang.StringUtils;

import com.eap.exception.EAPException;
import com.eap.session.sso.SSOEntryConfig;
import com.ebills.util.EbillsException;
import com.ebills.utils.CommonUtil;
import com.ebills.utils.EbpDao;
import com.ebills.utils.OutPutBean;
import com.ebills.workflow.IWorkflowComponent;
import com.ebills.workflow.WorkflowComponent;

import javax.servlet.http.HttpServletRequest;

/**
 * 参数管理
 * @author zhouxf
 * /
 */

public class SearchCSDao {

	public void searchCSorgno(Context context, HttpServletRequest request,
			OutPutBean output) throws EAPException, EbillsException {
		String orgno = request.getParameter("orgno");
		String Cursign = request.getParameter("Cursign");
		String sql ="delete from PACCYPRI where curSign=? and orgno=?";
		List<Object> paramList=new LinkedList<Object>();
		paramList.add(Cursign);
		paramList.add(orgno);		
		EbpDao gjjsDao = new EbpDao();
		 try{
			 gjjsDao.execute(sql, paramList);						 
		 }catch(EbillsException e){
			 e.printStackTrace();
		 }		
	}
	
	
	@SuppressWarnings("unchecked")
	public void clientparities(Context context, HttpServletRequest request,
			OutPutBean output) throws EAPException, EbillsException {
		EbpDao gjjsDao = new EbpDao();
		String txnno = request.getParameter("txnno");
		String tradeno = request.getParameter("tradeno");
		String sql = "select paramCtrlId,txnNo,tradeNo,keyInfo,fldCtx from bupamctl where tradeNo =? and txnNo <> ? ";
		List<Object> inputList = new LinkedList<Object>();
		//inputList.add((String) context.get(GjjsConstants.TRADENO));
		//inputList.add((String) context.get(GjjsConstants.TXNSERIALNO));
		inputList.add(tradeno);
		inputList.add(txnno);	
		List<Map<String,Object>> list = gjjsDao.queryBySql(sql, "", inputList);	
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		for (Map<String, Object> mInfo : list) {
			String fldCtxInfo = (String) mInfo.get("fldCtx");
//			existTxnNo = (String) mInfo.get("txnNo");
			if(StringUtils.isEmpty(fldCtxInfo)){
				continue;
			}
			Object[]  fldCtxInfoArr = CommonUtil.JsonToList(fldCtxInfo);
			Map<String, Object> mPa = new HashMap<String,Object>();
			for (int k = 0; k < fldCtxInfoArr.length; k++) {
				 mPa = (Map<String, Object>) fldCtxInfoArr[k];
				 result.add(mPa);
			}
		
			
		}
		output = new OutPutBean(CommonUtil.ListToJson(result));
		output.writeOutPut(context);	
	}
	public void serverdate(Context context, HttpServletRequest request,
			OutPutBean output) throws EAPException, EbillsException {
	        EbpDao gjjsDao=new EbpDao();
	        String  systemdate=request.getParameter("date");
	        List<Object> paramList  = new LinkedList<Object>();
	        String updatesql= "update  PACCYPRI a set  a.impdate=?";
	        //String deletesql= "delete from paccypriar a where a.impdate=to_date(?,'yyyy-mm-dd hh24:mi:ss')";
	        String insertsql= "insert into paccypriar  select * from PACCYPRI";
	        paramList.add(systemdate);
	        System.out.println("systemdate-------------->>>"+systemdate);
	        try{
				 gjjsDao.execute(updatesql, paramList);
				 //gjjsDao.execute(deletesql, paramList);	
				 gjjsDao.execute(insertsql, null);	
			 }catch(EbillsException e){
				 e.printStackTrace();
			 }	
	}
	public void paritiesrenew(Context context, HttpServletRequest request,
			OutPutBean output) throws EAPException, EbillsException {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			String serverdate = sdf.format(date);
			Map<String, String> map = new HashMap<String, String>();
			map.put("serverdate", serverdate);
	        output = new OutPutBean(CommonUtil.MapToJson(map));
	        output.writeOutPut(context);
	}
	public void Loginstate(Context context, HttpServletRequest request,
			OutPutBean output) throws EAPException, EbillsException {
	        EbpDao gjjsDao=new EbpDao();
	        String  usercode=request.getParameter("usercode");
	        List<Object> paramList  = new LinkedList<Object>();
	        String sql= "update pausr  set USERSTATEE='1' where usercode=?";
	        paramList.add(usercode);
	        System.out.println("systemdate-------------->>>"+usercode);
	        try{
				 gjjsDao.execute(sql, paramList);	
			 }catch(EbillsException e){
				 e.printStackTrace();
			 }	
	}
	
	public void Logoutstate(Context context, HttpServletRequest request,
			OutPutBean output) throws EAPException, EbillsException {
	        EbpDao gjjsDao=new EbpDao();
	        String  usercode=request.getParameter("usercode");
	        List<Object> paramList  = new LinkedList<Object>();
	        String sql= "update pausr  set USERSTATEE='0' where usercode=?";
	        paramList.add(usercode);
	        System.out.println("systemdate-------------->>>"+usercode);
	        try{
				 gjjsDao.execute(sql, paramList);	
			 }catch(EbillsException e){
				 e.printStackTrace();
			 }	
	}
	public void paritiescom(Context context, HttpServletRequest request,
			OutPutBean output) throws EAPException, EbillsException, NumberFormatException, ObjectNotFoundException, InvalidArgumentException {
	    	int userId  = -1;
			userId = Integer.parseInt((String) context.getValue(SSOEntryConfig.GET_USER_SESSION_KEY(context)));			
			IWorkflowComponent component = WorkflowComponent.getInstance();
			Map<String,String> orgMap = component.getOrgNoForPer(userId);
			String ALLOrgNo = "''";
			if( orgMap != null && orgMap.size() > 0 ){
				ALLOrgNo = "";
				for(String orgNo : orgMap.keySet()){
					ALLOrgNo += "'" + orgNo + "',";
				}
				ALLOrgNo = ALLOrgNo.substring(0, ALLOrgNo.length() -1);
			}
			//context.put("ALLOrgNo", ALLOrgNo);
			String sql="select orgno val,orgname name from PAORG where ISPARITIES='Y' and  ORGNO in("+ALLOrgNo+")";
			EbpDao gjjsDao = new EbpDao();
			List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, null);
			System.out.println("queryRet-------------------->>>"+queryRet); 
		    output = new OutPutBean(CommonUtil.ListToJson(queryRet));
		    output.writeOutPut(context);
	}
	}
	

