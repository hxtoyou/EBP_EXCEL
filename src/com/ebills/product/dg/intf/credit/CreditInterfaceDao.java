package com.ebills.product.dg.intf.credit;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ebills.product.dg.commons.utils.ApplicationException;
import com.ebills.product.dg.credit.utils.UtilTools;
import com.ebills.util.EbillsException;
import com.ebills.util.db.ConnectionManager;
import com.ebills.util.db.ConnectionManagerFactory;
import com.ebills.param.newtask.NewTaskFactory;
import com.ebills.utils.CommonUtil;
import com.ebills.utils.EbpDao;

public class CreditInterfaceDao {
	private final Log log = LogFactory.getLog(getClass());
	final static String TABLENAME = "TPCREDINTF";

	public CreditInterfaceDao(){
		
	}
	public String getTableName() {
		return TABLENAME;
	}

	public InterfaceCreditRequst create(InterfaceCreditRequst request) throws Exception {
		String ourno="";
		String str=UtilTools.getPasys();
		if(request.getTxnno()==null){
			String txnNo;
			try {
				txnNo = UtilTools.bringSerialNo(str);
				request.setTxnno(txnNo);
			} catch (EbillsException e) {
				e.printStackTrace();
				throw new ApplicationException(e.getMessage());
			}
			
			}
		
		List<Object> params=new LinkedList<Object>();	
	    params.add(request.getTxnno());
		params.add(request.getBizno());
		params.add(request.getFileno());
		params.add(request.getAppno());
		if(request.getLcamts()!=null&&!"".equals(request.getLcamts())){
			params.add(Double.valueOf(request.getLcamts()));
		}else{
			params.add(null);
		}

		params.add(request.getLccyno());
		params.add(request.getSutype());
		if(request.getTolep1()!=null&&!"".equals(request.getTolep1())){
			params.add(Double.valueOf(request.getTolep1()));
		}else{
			params.add(null);
		}
		if(request.getTolep2()!=null&&!"".equals(request.getTolep2())){
			params.add(Double.valueOf(request.getTolep2()));
		}else{
			params.add(null);
		}
		if(request.getMgrate()!=null&&!"".equals(request.getMgrate())){
			params.add(Double.valueOf(request.getMgrate()));
		}else{
			params.add(null);
		}
		params.add(request.getEpdate());
		params.add(request.getJjhmno());
		params.add(request.getApdate());
		params.add(request.getLcopno());
		params.add(request.getAddlcamts());
		params.add(request.getBiztyp());
		params.add(request.getOldjylsh());
		params.add(request.getIntrefaceid());
		params.add(request.getStatus());

		try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			try {
				EbpDao dao = new EbpDao();
				String sql="insert into "+TABLENAME+
						"(TXNNO,BIZNO,FILENO,APPNO,LCAMTS ,LCCYNO,SUTYPE,TOLEP1,TOLEP2,MGRATE,EPDATE,JJHMNO,APDATE,LCOPNO,"
								+"ADDLCAMTS,BIZTYP,OLDJYLSH,INTREFACEID,status) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				dao.execute(sql,params);
				
			} catch (Exception e) {
				cm.setRollbackOnly();
				e.printStackTrace();
				throw new ApplicationException(e.getMessage());
			}finally{
				cm.commit();
			}
			
		} catch (EbillsException e1) {
			e1.printStackTrace();
			throw new ApplicationException(e1.getMessage());
		}
		return request;
		
	}
	/**
	 * 用于检索是否有重复数据发来
	 * @param outAcctSerialNo
	 * @return
	 * @throws ApplicationException
	 */
	public InterfaceCreditRequst getInfoByPutOutSerialNo(String fielno)
			throws ApplicationException {
		InterfaceCreditRequst request=null;
		try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			
			try {
				EbpDao dao = new EbpDao();
				String sql="select txnno,bizno,fileno,appno,lcamts ,lccyno,sutype,tolep1,tolep2,mgrate,epdate,jjhmno,apdate,lcopno,"
						+"addlcamts,biztyp,oldjylsh,intrefaceid from "+TABLENAME+" where fileno=?";
				List<Object> params=new LinkedList<Object>();	
				params.add(fielno);
				List list=dao.queryBySql(sql, null, params);
				if(list.size()>0){
					Map map=(Map)list.get(0);
					request=new InterfaceCreditRequst();
					if(map.get("txnno")!=null){
				         log.debug("txnno"+map.get("txnno"));
						request.setTxnno(String.valueOf(map.get("txnno")));
					}
					if(map.get("bizno")!=null){
						request.setBizno((String)map.get("bizno"));
					}
					if(map.get("fileno")!=null){
						request.setFileno((String)map.get("fileno"));
					}
					if(map.get("appno")!=null){
						request.setAppno((String)map.get("appno"));
					}
					
					if(map.get("lcamts")!=null){
						request.setLcamts((String)map.get("lcamts"));	
					}
					if(map.get("lccyno")!=null){
						request.setLccyno((String)map.get("lccyno"));
					}
					if(map.get("sutype")!=null){
						request.setSutype((String)map.get("sutype"));
					}
					if(map.get("tolep1")!=null){
						request.setTolep1((String)map.get("tolep1"));
					}
					if(map.get("tolep2")!=null){
						request.setTolep2((String)map.get("tolep2"));
					}
					if(map.get("mgrate")!=null){
						request.setMgrate((String)map.get("mgrate"));
					}
					if(map.get("epdate")!=null){
						request.setEpdate(new SimpleDateFormat("yyyy-MM-dd").parse((String)map.get("epdate")));
					}
					request.setJjhmno((String)map.get("jjhmno"));
					if(map.get("apdate")!=null){
						request.setApdate(new SimpleDateFormat("yyyy-MM-dd").parse((String)map.get("apdate")));
					}
					request.setLcopno((String)map.get("lcopno"));
					if(map.get("addlcamts")!=null){
						request.setAddlcamts((String)map.get("addlcamts"));
					}
					if(map.get("biztyp")!=null){
						request.setOldjylsh((String)map.get("oldjylsh"));
					}
					if(map.get("oldjylsh")!=null){
						request.setOldjylsh((String)map.get("oldjylsh"));
					}
					if(map.get("intrefaceid")!=null){
						request.setIntrefaceid((String)map.get("intrefaceid"));
					}
				}
			} catch (Exception e) {
				cm.setRollbackOnly();
				throw new ApplicationException(e.getMessage());
			}finally{
				cm.commit();
				
			}
			
		} catch (EbillsException e1) {
			e1.printStackTrace();
			throw new ApplicationException(e1.getMessage());
		}
		return request;
	}
	public void inserNewTask(Map<String, Object> newTaskInfo,InterfaceCreditRequst request) throws EbillsException {

		newTaskInfo.put("tranCur",request.getLccyno());
		newTaskInfo.put("tranAmt",  Double.valueOf(request.getLcamts()));
		newTaskInfo.put("startDate", CommonUtil.formatDate(CommonUtil.getSysFld("workDate")));
		newTaskInfo.put("epdate", request.getEpdate());
		newTaskInfo.put("fileNo", request.getFileno());
		newTaskInfo.put("startModeId","5");// 信贷发发起
		ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
		cm.startTransaction(true);
		NewTaskFactory.createNewTask(newTaskInfo);
	    cm.commit();
	}
	
	/**
	 * 用于检索是否有重复数据发来
	 * @param outAcctSerialNo
	 * @return
	 * @throws ApplicationException
	 */
	public InterfaceCreditRequst queryInterfaceByJjhmno(String jjhmno)
			throws ApplicationException {
		InterfaceCreditRequst request=null;
		try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			
			try {
				EbpDao dao = new EbpDao();
				String sql="select txnno,bizno,fileno,appno,lcamts ,lccyno,sutype,tolep1,tolep2,mgrate,epdate,jjhmno,apdate,lcopno,"
						+"addlcamts,biztyp,oldjylsh,intrefaceid from "+TABLENAME+" where jjhmno=?";
				List<Object> params=new LinkedList<Object>();	
				params.add(jjhmno);
				List list=dao.queryBySql(sql, null, params);
				if(list.size()>0){
					Map map=(Map)list.get(0);
					request=new InterfaceCreditRequst();
					if(map.get("txnno")!=null){
				         log.debug("txnno"+map.get("txnno"));
						request.setTxnno(String.valueOf(map.get("txnno")));
					}
					if(map.get("bizno")!=null){
						request.setBizno((String)map.get("bizno"));
					}
					if(map.get("fileno")!=null){
						request.setFileno((String)map.get("fileno"));
					}
					if(map.get("appno")!=null){
						request.setAppno((String)map.get("appno"));
					}
					
					if(map.get("lcamts")!=null){
						request.setLcamts((String)map.get("lcamts"));	
					}
					if(map.get("lccyno")!=null){
						request.setLccyno((String)map.get("lccyno"));
					}
					if(map.get("sutype")!=null){
						request.setSutype((String)map.get("sutype"));
					}
					if(map.get("tolep1")!=null){
						request.setTolep1((String)map.get("tolep1"));
					}
					if(map.get("tolep2")!=null){
						request.setTolep2((String)map.get("tolep2"));
					}
					if(map.get("mgrate")!=null){
						request.setMgrate((String)map.get("mgrate"));
					}
					if(map.get("epdate")!=null){
						request.setEpdate(new SimpleDateFormat("yyyy-MM-dd").parse((String)map.get("epdate")));
					}
					request.setJjhmno((String)map.get("jjhmno"));
					if(map.get("apdate")!=null){
						request.setApdate(new SimpleDateFormat("yyyy-MM-dd").parse((String)map.get("apdate")));
					}
					request.setLcopno((String)map.get("lcopno"));
					if(map.get("addlcamts")!=null){
						request.setAddlcamts((String)map.get("addlcamts"));
					}
					if(map.get("biztyp")!=null){
						request.setOldjylsh((String)map.get("oldjylsh"));
					}
					if(map.get("oldjylsh")!=null){
						request.setOldjylsh((String)map.get("oldjylsh"));
					}
					if(map.get("intrefaceid")!=null){
						request.setIntrefaceid((String)map.get("intrefaceid"));
					}
				}
			} catch (Exception e) {
				cm.setRollbackOnly();
				throw new ApplicationException(e.getMessage());
			}finally{
				cm.commit();
				
			}
			
		} catch (EbillsException e1) {
			e1.printStackTrace();
			throw new ApplicationException(e1.getMessage());
		}
		return request;
	}
	/**
	 *拮据号都相同已经存在  
	 */
	public boolean isExistJjno(String oldjylsh,String interfaceid) {
		ConnectionManager connection = null;
		boolean result = false;
		List<Object> params = new LinkedList<Object>();
		params.add(oldjylsh);
		params.add(interfaceid);
		try {
			EbpDao dao = new EbpDao();
			String sql="select count(0) as count from "+TABLENAME +" where status='0' and jjhmno=? and INTREFACEID=?";
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
	
	/**
	 *交易流水号和拮据号都相同 重发数据情况 
	 */
	
	public boolean isExistSameJjno(String jylsh,String jjhmno,String interfaceid) {
		ConnectionManager connection = null;
		boolean result = false;
		List<Object> params = new LinkedList<Object>();
		params.add(jylsh);
		params.add(jjhmno);
		params.add(interfaceid);
		try {
			EbpDao dao = new EbpDao();
			String sql="select count(0) as count from "+TABLENAME +" where status='0' and FILENO=? and jjhmno=? and INTREFACEID=?";
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
	/**
	 *交易流水号是否存在 
	 */
	public boolean isExistJylsh(String oldjylsh) {
		ConnectionManager connection = null;
		boolean result = false;
		List<Object> params = new LinkedList<Object>();
		params.add(oldjylsh);
		try {
			EbpDao dao = new EbpDao();
			String sql="select count(0) as count from "+TABLENAME +" where status='0' and FILENO=?";
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
	/**
	 *删除数据 
	 */
	public void deleteByjylsh(String oldjylsh){
    	List<Object> params=new LinkedList<Object>();
    	params.add("4");//删除状态
    	params.add(oldjylsh);
    	String sql="update "+TABLENAME +" set STATUS=? where fileno=?";
    	try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			
			try {
				EbpDao dao = new EbpDao();
				dao.execute(sql,params);
				
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
		ConnectionManager connection = null;
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
}
