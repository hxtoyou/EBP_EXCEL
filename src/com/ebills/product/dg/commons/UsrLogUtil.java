package com.ebills.product.dg.commons;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.ebills.util.EbillsException;
import com.ebills.util.db.ConnectionManager;
import com.ebills.util.db.ConnectionManagerFactory;
import com.ebills.utils.EbpDao;

public class UsrLogUtil {

    /**
     *将需要发送的数据存入数据表
     */
	 public static void  saveUsrLog(
			   String usrid,
			   String operate,
			   String opuserid,
			   String operatedtls){
		try {
				ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);
				try {
					Date date=new Date();
					String txNo=usrid+String.valueOf(date.getTime())+opuserid;
					EbpDao dao = new EbpDao();
					LinkedList<Object> inputList=new LinkedList<Object>();
					
					inputList.add(txNo);
					inputList.add(usrid);
					inputList.add(operate);
					inputList.add(Commons.getSysDateHMS());
					inputList.add(operatedtls);
					inputList.add(opuserid);
//					Clob c=new SerialClob(operatedtls.toCharArray());
//					inputList.add(c);//rawtohex
				    String intpsql="insert into PAUSRLOG " +
				    		"(txnNo, usrid, operate, operatedate, operatedtls,operateduserid)" +
				    		"values (?, ?, ?, to_date(?,'yyyy-MM-dd hh24:mi:ss'), ?,?)";
					dao.execute(intpsql, inputList);
				} catch (Exception e) {
					e.printStackTrace();
					cm.setRollbackOnly();
				}finally{
					cm.commit();
				}
				
			} catch (EbillsException e1) {
				e1.printStackTrace();
			}
	   }
	 @SuppressWarnings("rawtypes")
	public  List getLcList(String userid){
			List<Map<String, Object>> result=null;
			try {
				ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);
				try {
					EbpDao dao = new EbpDao();
					StringBuffer sql = new StringBuffer();
					LinkedList<Object> inputList=new LinkedList<Object>();
					inputList.add(userid);
					sql.append("select userName,belongOrgno,userLevel,userState,userPhone,userSex from PAUSR t where t.userid=?");
					StringBuffer item = new StringBuffer();
					item.append("userName,belongOrgno,userLevel,userState,userPhone,userSex");
					result= dao.queryBySql(sql.toString(), item.toString(), null, inputList);
				} catch (Exception e) {
					cm.setRollbackOnly();
				}finally{
					cm.commit();
				}
				
			} catch (EbillsException e1) {
				e1.printStackTrace();
			}
			return result;
	 }
	 public static String getUserNameById(String userid){
			List<Map<String, Object>> result=null;
			String userName="";
			try {
				ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);
				try {
					EbpDao dao = new EbpDao();
					StringBuffer sql = new StringBuffer();
					LinkedList<Object> inputList=new LinkedList<Object>();
					inputList.add(userid);
					sql.append("select userName from PAUSR t where t.userid=?");
					StringBuffer item = new StringBuffer();
					item.append("userName");
					result= dao.queryBySql(sql.toString(), item.toString(), null, inputList);
					if(result.size()>0){
						userName=(String) result.get(0).get("userName");
					}
				} catch (Exception e) {
					cm.setRollbackOnly();
				}finally{
					cm.commit();
				}
				
			} catch (EbillsException e1) {
				e1.printStackTrace();
			}
			return userName;
	 }
	 
	 public static String getUserCodeById(String userid){
			List<Map<String, Object>> result=null;
			String userName="";
			try {
				ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);
				try {
					EbpDao dao = new EbpDao();
					StringBuffer sql = new StringBuffer();
					LinkedList<Object> inputList=new LinkedList<Object>();
					inputList.add(userid);
					sql.append("select userCode from PAUSR t where t.userid=?");
					StringBuffer item = new StringBuffer();
					item.append("userCode");
					result= dao.queryBySql(sql.toString(), item.toString(), null, inputList);
					if(result.size()>0){
						userName=(String) result.get(0).get("userCode");
					}
				} catch (Exception e) {
					cm.setRollbackOnly();
				}finally{
					cm.commit();
				}
				
			} catch (EbillsException e1) {
				e1.printStackTrace();
			}
			return userName;
	 }
	 
	 public static String getOrgById(String orgId){
			List<Map<String, Object>> result=null;
			String userName="";
			try {
				ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);
				try {
					EbpDao dao = new EbpDao();
					StringBuffer sql = new StringBuffer();
					LinkedList<Object> inputList=new LinkedList<Object>();
					inputList.add(orgId);
					sql.append("select orgname from paorg t where t.orgno=?");
					StringBuffer item = new StringBuffer();
					item.append("orgname");
					result= dao.queryBySql(sql.toString(), item.toString(), null, inputList);
					if(result.size()>0){
						userName=(String) result.get(0).get("orgname");
					}
				} catch (Exception e) {
					cm.setRollbackOnly();
				}finally{
					cm.commit();
				}
				
			} catch (EbillsException e1) {
				e1.printStackTrace();
			}
			return userName;
	 }
	 
	 public static List<Map<String, Object>> getCheckUser(String txnno){
			List<Map<String, Object>> result=null;
			try {
				ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);
				try {
					EbpDao dao = new EbpDao();
					StringBuffer sql = new StringBuffer();
					LinkedList<Object> inputList=new LinkedList<Object>();
					inputList.add(txnno);
					sql.append("select t.handlerid,checkerid from butxntp t where t.txnno=?");
					StringBuffer item = new StringBuffer();
					item.append("handlerid,checkerid");
					result= dao.queryBySql(sql.toString(), item.toString(), null, inputList);
					if(result.size()>0){
						//userName=(String) result.get(0).get("orgname");
					}
				} catch (Exception e) {
					cm.setRollbackOnly();
				}finally{
					cm.commit();
				}
				
			} catch (EbillsException e1) {
				e1.printStackTrace();
			}
			return result;
	 }
}
