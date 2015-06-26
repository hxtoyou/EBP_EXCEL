package com.ebills.product.dg.credit.send;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.ebills.util.EbillsException;
import com.ebills.util.db.ConnectionManager;
import com.ebills.util.db.ConnectionManagerFactory;
import com.ebills.product.dg.credit.dao.BatchDao;
import com.ebills.utils.EbpDao;

public class LgOutAcct {

	/**
	 * 查询所有的保函开立信息
	 */
	public List getLgList(){
		List<Map<String, Object>> result=null;
		try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			try {
				EbpDao dao = new EbpDao();
				StringBuffer sql = new StringBuffer();
				List<Object> inputList=new LinkedList<Object>();
				sql.append("select t.creditno,");
				sql.append("t.beniname,");
				sql.append(" t.lgcur,");
				sql.append("t.lgamt,");
				sql.append("to_char(t.effectdate,'yyyyMMdd')as effectdate,");
				sql.append("to_char(t.failruedate,'yyyyMMdd')as failruedate,");
				sql.append("t.lgno,");
				sql.append("(select p.orgcode from paorg p where p.orgno = b.tranorgno) as tranorgno ");
				sql.append("from imlgissuefo t ");
				sql.append("left join butxnar b ");
				sql.append("on t.txnno = b.txnno");
				StringBuffer item = new StringBuffer();
				item.append("creditno,beniname,lgcur,lgamt,effectdate, failruedate,lgno, tranorgno");
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
	/**
	 * 查询所有保函注销的交易
	 */
	public List getClsLgList(){
		List<Map<String, Object>> result=null;
		try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			try {
				EbpDao dao = new EbpDao();
				StringBuffer sql = new StringBuffer();
				List<Object> inputList=new LinkedList<Object>();
				sql.append("select bizno,to_char(reorcandate,'yyyyMMdd')reorcandate from pbruglfo where  rmflag='1'");
				StringBuffer item = new StringBuffer();
				item.append("bizno,reorcandate");
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
	
	/**
	 * 查询所有保函撤销的交易
	 */
	public List getCnlLgList(){
		List<Map<String, Object>> result=null;
		try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			try {
				EbpDao dao = new EbpDao();
				StringBuffer sql = new StringBuffer();
				List<Object> inputList=new LinkedList<Object>();
				sql.append("select bizno,to_char(dealdate,'yyyyMMdd') dealdate from pbclosefo WHERE CANCELFLAG IN('0','1') AND TRADENO ='170110'");
				StringBuffer item = new StringBuffer();
				item.append("bizno,dealdate");
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
	
	/**
	 * 查询所有保函赔付交易
	 */
	public List getLgPayList(){
		List<Map<String, Object>> result=null;
		try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			try {
				EbpDao dao = new EbpDao();
				StringBuffer sql = new StringBuffer();
				List<Object> inputList=new LinkedList<Object>();
				sql.append(" select bizno,to_char(paydate,'yyyymmdd') paydate from pbpayfo where  tradeno='170106'");
				StringBuffer item = new StringBuffer();
				item.append("bizno,paydate");
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
	
	public void saveOutAcct(){
		List<Map<String, Object>> lgList=this.getLgList();//所有保函开立信息
		List<Map<String,Object>> clsLgList=this.getClsLgList();//所有的保函注销信息
		List<Map<String,Object>> cnlgList=this.getCnlLgList();//所有的保函注销信息
		List<Map<String,Object>> lgPayList=this.getLgPayList();//所有的保函注销信息
		if(lgList!=null&&lgList.size()>0){
			for(Map<String,Object> lg:lgList){
				String lgNo=(String)lg.get("lgno");
				Map<String,Object> param=new HashMap<String,Object>();
			    param.put("debtno",lg.get("creditno"));
			    param.put("cur",lg.get("lgcur"));
			    param.put("appname",lg.get("beniname"));
			    param.put("amt",lg.get("lgamt"));
			    param.put("startdate",lg.get("effectdate"));
			    param.put("enddate",lg.get("failruedate"));
			    param.put("cleardate",null);
			    param.put("state","1");
			    param.put("org",lg.get("tranorgno"));
				//判断是否做了撤销
				Map<String,Object> cnlLg=this.isCls(lgNo, cnlgList);
				if(cnlLg!=null){
					 param.put("state","8");
				}
				//判断是否做了赔付
				Map<String,Object> lgPay=this.isCls(lgNo, lgPayList);
				if(lgPay!=null){
					
				}
				//判断是否做了索赔闭卷
				//判断是否做了结清
				Map<String,Object> clsLg=this.isCls(lgNo, clsLgList);
				if(clsLg!=null){
					param.put("cleardate",clsLg.get("reorcandate"));
					param.put("state","9");
				}
                BatchDao dao=new BatchDao();
                dao.saveLgData(param);
			}
		}
	}
    /**
     *判断是否做过保函注销 
     */
	private Map<String,Object> isCls(String lgNo,List<Map<String,Object>> clsLgList){
		if(clsLgList!=null&&clsLgList.size()>0){
			for(Map<String,Object> clsLg:clsLgList){
				if(clsLg.get("bizno")!=null){
					String bizno=(String)clsLg.get("bizno");
					if(bizno.equals("lgNo")){
						return clsLg;
					}
				}
			}
		}
		return null;
	}
	
    /**
     *判断是否做过保函撤销
     */
	private Map<String,Object> isCnl(String lgNo,List<Map<String,Object>> cnlgList){
		if(cnlgList!=null&&cnlgList.size()>0){
			for(Map<String,Object> cnlLg:cnlgList){
				if(cnlLg.get("bizno")!=null){
					String bizno=(String)cnlLg.get("bizno");
					if(bizno.equals("lgNo")){
						return cnlLg;
					}
				}
			}
		}
		return null;
	}
	
    /**
     *判断是否做过保函赔付
     */
	private Map<String,Object> isLgPay(String lgNo,List<Map<String,Object>> lgPayList){
		if(lgPayList!=null&&lgPayList.size()>0){
			for(Map<String,Object> lgPay:lgPayList){
				if(lgPay.get("bizno")!=null){
					String bizno=(String)lgPay.get("bizno");
					if(bizno.equals("lgNo")){
						return lgPay;
					}
				}
			}
		}
		return null;
	}
}
