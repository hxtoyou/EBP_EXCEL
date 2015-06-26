package com.ebills.product.dg.intf.ocif;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eap.resource.jdbc.EAPJDBCException;
import com.eap.resource.jdbc.dao.DAOFactory;
import com.eap.resource.jdbc.dao.IDao;
import com.ebills.util.EbillsException;
import com.ebills.util.EbillsLog;
import com.ebills.util.db.ConnectionManager;
import com.ebills.util.db.ConnectionManagerFactory;
import com.ebills.product.dg.AcctInterface.DgAcctInterface;
import com.ebills.product.dg.AcctInterface.dao.DaoUtils;
import com.ebills.product.dg.AcctInterface.domain.Corp;
import com.ebills.product.dg.commons.utils.ApplicationException;
import com.ebills.product.dg.commons.Commons;
import com.ebills.product.dg.credit.utils.UtilTools;
import com.ebills.param.newtask.NewTaskFactory;
import com.ebills.utils.CommonUtil;
import com.ebills.utils.EbpDao;

public class OcifInterfaceDao {
	private static String className = OcifInterfaceDao.class.getName();
	private static EbillsLog log = new EbillsLog(className);
	/**
	 *合并客户账号，将账户表中mergno的客户号更新为custno，并将被合并客户的状态改成已合并状态
	 */
	public void updateAcctNoInfo(String custno,String mergno){
    	List<Object> acctParams=new LinkedList<Object>();
    	List<Object> corpParams=new LinkedList<Object>();
    	DaoUtils utils = new DaoUtils();
    	String gjCustno = utils.getCorpByMaincorp(custno);
    	String gjMergno = utils.getCorpByMaincorp(mergno);
    	acctParams.add(gjCustno);
    	acctParams.add(gjMergno);
    	corpParams.add("2");//把被合并客户的状态改成已合并状态
    	corpParams.add(mergno);
    	String acctSql="update paaccfo set custbankno=? where custbankno=?";
    	String corpSql="update PACRP set KHZT=? where MAINCORPNO=?";
    	try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			
			try {
				EbpDao dao = new EbpDao();
				dao.execute(acctSql,acctParams);
				dao.execute(corpSql,corpParams);
				
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
	
	public boolean isAppnoExist(String appno) {
		boolean result = false;
		List<Object> params = new LinkedList<Object>();
		params.add(appno);
		try {
			EbpDao dao = new EbpDao();
			String sql="select count(0) as count from pacrp where maincorpno=?";
			List list=dao.queryBySql(sql, null, params);
			Map total=(Map) list.get(0);
			if (Integer.valueOf((String)total.get("count")) > 0) {
				result = true;
			}
		} catch (EbillsException e) {
			e.printStackTrace();
		} 

		return result;
	}
	
	public String getCorpNumber(String appno) {
		String result = "0";
		List<Object> params = new LinkedList<Object>();
		params.add(appno);
		try {
			EbpDao dao = new EbpDao();
			String sql="select count(0) as count from pacrp where maincorpno=?";
			List list=dao.queryBySql(sql, null, params);
			Map total=(Map) list.get(0);
			result =(String)total.get("count");
		} catch (EbillsException e) {
			e.printStackTrace();
		} 

		return result;
	}
	
	/**
	 * 查询客户状态
	 * @return
	 * @throws Exception
	 */
	public String getCorpState(String maincorpno) throws Exception{		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		ConnectionManager cm = null;
		String corpState = "";
		try {
			cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);				
			EbpDao gjDao = new EbpDao();
				String buAcctSql = "select a.khzt  from pacrp a WHERE maincorpno='"+ maincorpno + "'";	
				list = gjDao.queryBySql(buAcctSql, null,null);
				if(list.size() > 0){
						Map<String,Object> map =  list.get(0);
						corpState = (String)map.get("khzt");
				}			
		} catch (Exception ex) {
			if(cm != null){
				cm.setRollbackOnly();
			}
			ex.printStackTrace();
		}finally{
    		try {
    			if(cm != null){
    				cm.commit();
    			}
			} catch (EbillsException e) {
				log.error("释放连接失败"+e.getMessage());
			}
    	}
		return corpState;
	}
	
	/**
	 * 备份被合并的账户信息
	 * @return
	 * @throws Exception
	 */
	public void create(String sverserialno, String custno, String mergno) throws Exception{		
		ConnectionManager cm = null;
		try {
			cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);				
			EbpDao gjDao = new EbpDao();
	    	DaoUtils utils = new DaoUtils();
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			Date currDate = new Date(System.currentTimeMillis());
	    	String gjMergno = utils.getCorpByMaincorp(mergno);
			String sql = "select a.acctno from paaccfo a where a.custbankno = '"+gjMergno+"'";
			list = gjDao.queryBySql(sql, null,null);
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				String acctno = (String)map.get("acctno");
				if(!StringUtils.isEmpty(acctno)){
					List<Object> params=new LinkedList<Object>();	
				    params.add(sverserialno);
				    params.add(custno);
				    params.add(mergno);
				    params.add(acctno);
				    params.add(currDate);			    
					String insertSql = "insert into BUMERGERTP " 
							+" (INTFNO,CORPNO,MERGNO,ACCTNO,TRANDT) values (?,?,?,?,?)";
					gjDao.execute(insertSql,params);
				}		
			}
				
		} catch (Exception ex) {
			if(cm != null){
				cm.setRollbackOnly();
			}
			ex.printStackTrace();
		}finally{
    		try {
    			if(cm != null){
    				cm.commit();
    			}
			} catch (EbillsException e) {
				log.error("释放连接失败"+e.getMessage());
			}
    	}
	}
	
	/**
	 *更新账号表，将账号表对应的客户号恢复为“被合并客户”
	 */
	public void updateAcct(String custno,String mergno) throws Exception{
//	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	ConnectionManager cm = null;
	try {
		cm = ConnectionManagerFactory.getConnectionManager();
		cm.startTransaction(true);				
		EbpDao gjDao = new EbpDao();
		List<Object> acctParams=new LinkedList<Object>();
    	List<Object> corpParams=new LinkedList<Object>();
    	acctParams.add(custno);
    	acctParams.add(mergno);
    	acctParams.add(custno);
    	acctParams.add(mergno);
    	corpParams.add("1");//把被合并客户的状态改成正常状态
    	corpParams.add(mergno);
		String acctSql = "UPDATE paaccfo a SET a.custbankno = (SELECT c.corpno  FROM bumergertp b,pacrp c  WHERE b.mergno = c.maincorpno and b.corpno = ? " +
				"and b.mergno =? AND a.acctno = b.acctno) where EXISTS (SELECT c.corpno  FROM bumergertp b,pacrp c  WHERE b.mergno = c.maincorpno and b.corpno = ?" +
				" and b.mergno =? AND a.acctno = b.acctno)";
		String corpSql = "update pacrp a set a.khzt = ? where a.maincorpno = ?";
		gjDao.execute(acctSql, acctParams);
		gjDao.execute(corpSql, corpParams);
	} catch (Exception ex) {
		if(cm != null){
			cm.setRollbackOnly();
		}
		ex.printStackTrace();
	}finally{
		try {
			if(cm != null){
				cm.commit();
			}
		} catch (EbillsException e) {
			log.error("释放连接失败"+e.getMessage());
		}
	}
	}
	
	/**
	 *合并反冲销时把合并的信息备份至ar表并删除tp表
	 */
	public void processFinish(String custno,String mergno) throws Exception{
//	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	ConnectionManager cm = null;
	try {
		cm = ConnectionManagerFactory.getConnectionManager();
		cm.startTransaction(true);				
		EbpDao gjDao = new EbpDao();
		List<Object> params=new LinkedList<Object>();
		params.add(custno);
		params.add(mergno);
    	String insertSql = "insert into bumergerar (INTFNO,CORPNO,MERGNO,ACCTNO,TRANDT) select *  from bumergertp b where  b.corpno = ? and b.mergno =?";
		String delSql = "delete from bumergertp b where  b.corpno = ? and b.mergno =?";
		
		gjDao.execute(insertSql, params);
		gjDao.execute(delSql, params);
	} catch (Exception ex) {
		if(cm != null){
			cm.setRollbackOnly();
		}
		ex.printStackTrace();
	}finally{
		try {
			if(cm != null){
				cm.commit();
			}
		} catch (EbillsException e) {
			log.error("释放连接失败"+e.getMessage());
		}
	}
	}
	
	/**
	 *更新客户信息
	 */
	public void updateCorpInfo(Corp corp) throws Exception{
//			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			ConnectionManager cm = null;
			try {
				cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);				
				EbpDao gjDao = new EbpDao();
				String sql = "update pacrp a set";
				if(!"".equals(corp.getCnName()) && corp.getCnName()!=null){
					sql = sql + " a.cnname = '"+corp.getCnName()+"'";
				}
				if(!"".equals(corp.getIdCard()) && corp.getIdCard()!=null){
					if(("update pacrp a set").equals(sql)){
						sql = sql + " a.idcard = '"+corp.getIdCard()+"'";
					}else{
						sql = sql + ",a.idcard = '"+corp.getIdCard()+"'";
					}
				}
				if(!"".equals(corp.getCorpOrgCode())  && corp.getCorpOrgCode()!=null){
					if(("update pacrp a set").equals(sql)){
						sql = sql + " a.corporgcode = '"+corp.getCorpOrgCode()+"'";
					}else{
						sql = sql + ",a.corporgcode = '"+corp.getCorpOrgCode()+"'";
					}
				}
				sql = sql + " where a.maincorpno = '"+corp.getMainCorpNo()+"'";
				System.out.println("corpSql==="+sql);
				gjDao.execute(sql,null);
			} catch (Exception ex) {
				if(cm != null){
					cm.setRollbackOnly();
				}
				ex.printStackTrace();
			}finally{
				try {
					if(cm != null){
						cm.commit();
					}
				} catch (EbillsException e) {
					log.error("释放连接失败"+e.getMessage());
				}
			}
	}
}
