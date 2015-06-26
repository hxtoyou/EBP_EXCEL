package com.ebills.product.dg.credit.dao;

import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.ebills.util.EbillsException;
import com.ebills.util.db.ConnectionManager;
import com.ebills.util.db.ConnectionManagerFactory;
import com.ebills.product.dg.commons.utils.ApplicationException;
import com.ebills.param.datainfo.DataFactory;
import com.ebills.utils.EbpConstants;
import com.ebills.utils.EbpDao;

public class SendCreditNoticeDao {
	public final static String NOTIC_ID="TPCREDNOTIC";
	/**
	 * 
	 * */
	public  List getAllNotice(String interfaceid) throws Exception{
		List<Map<String, Object>> result=null;
		try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			try {
				EbpDao dao = new EbpDao();
				String sql="select txnno,dlrfno,jjhmno,leamts,eydate,prodcd,trtype,status,mgrate,vldate,madate,inrate,interfaceid,lcno from tpcrednotictp where interfaceid=?";
				List<Object> params=new LinkedList<Object>();
				params.add(interfaceid);
				result=dao.queryBySql(sql, null, params);
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
		return result;
		
	}
	
	public  List getAllNotice() throws Exception{
		List<Map<String, Object>> result=null;
		try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			try {
				EbpDao dao = new EbpDao();
				StringBuffer sql = new StringBuffer();
//				List<Object> inputList=new LinkedList<Object>();
				sql.append("select txnno,dlrfno,jjhmno,leamts,to_char(eydate,'yyyyMMdd') as eydate,");
				sql.append("prodcd,trtype,status,mgrate,to_char(vldate,'yyyyMMdd') as vldate,");
				sql.append("to_char(madate,'yyyyMMdd') madate,inrate,interfaceid,lcno from ");
				sql.append("tpcrednotictp");
				StringBuffer item = new StringBuffer();
				item.append("txnno,dlrfno,jjhmno,leamts,eydate, prodcd, trtype,status,mgrate,vldate,madate,inrate,interfaceid,lcno");
				result= dao.queryBySql(sql.toString(), item.toString(), null, null);
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
		return result;
		
	}
	/**
	 *把数据迁移
	 **/
	public  List moveData(String  txnno,String intfno) throws Exception{
		List<Map<String, Object>> result=null;
		try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			try {
				EbpDao dao = new EbpDao();
				Map<String,Object> mapParam=new HashMap<String,Object>();
				Map<String,Object> condition=new HashMap<String,Object>();
				List<Object> inputList=new LinkedList<Object>();
				mapParam.put("dlrfno", intfno);
				condition.put("txnNo", txnno);
				inputList.add(txnno);
			    String infosql="insert into tpcrednoticfo (select * from tpcrednoticTP where TXNNO=?) ";
			    String inARsql="insert into tpcrednoticar (select * from tpcrednoticTP where TXNNO=?) ";
				dao.execute(infosql, inputList);
				dao.updateByDataId(NOTIC_ID, EbpConstants.TABLE_INFO, mapParam, condition);
				dao.execute(inARsql, inputList);
				dao.updateByDataId(NOTIC_ID, EbpConstants.TABLE_AR, mapParam, condition);
				dao.deleteRow(NOTIC_ID, EbpConstants.TABLE_TP, condition);
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
		return result;
		
	}
     /**
      *将需要发送的数据存入数据表
      */
	 public void saveTpCredNotic(String txnno,
			   String dlrfno,
			   String jjhmno,
			   String leamts,
			   Date eydate,
			   String prodcd,     
			   String trtype,      
			   String status,     
			   String mgrate,      
			   Date vldate,     
			   Date madate,     
			   String inrate,      
			   String interfaceid, 
			   String lcno){
		try {
				ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);
				try {
					EbpDao dao = new EbpDao();
					List<Object> inputList=new LinkedList<Object>();
					inputList.add(txnno);
					inputList.add(dlrfno);
					inputList.add(jjhmno);
					inputList.add(leamts);
					inputList.add(eydate);
					inputList.add(prodcd);
					inputList.add(trtype);
					inputList.add(status);
					if(mgrate!=null&&!"".equals(mgrate.trim())){
						inputList.add(Double.valueOf(mgrate));
					}else{
						inputList.add(null);
					}
					inputList.add(vldate);
					inputList.add(madate);
					if(inrate!=null&&!"".equals(inrate.trim())){
						inputList.add(Double.valueOf(inrate));
					}else{
						inputList.add(null);
					}
					inputList.add(interfaceid);
					inputList.add(lcno);
				    String intpsql="insert into TPCREDNOTICTP " +
				    		"(TXNNO, DLRFNO, JJHMNO, LEAMTS, EYDATE, PRODCD, TRTYPE, STATUS, MGRATE, VLDATE, MADATE, INRATE, INTERFACEID, LCNO)" +
				    		"values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
					dao.execute(intpsql, inputList);
				} catch (Exception e) {
					cm.setRollbackOnly();
				}finally{
					cm.commit();
				}
				
			} catch (EbillsException e1) {
				e1.printStackTrace();
			}
	   }
	 /**
	  *根据流水号查询信用证信息 
	  */
	 public List getLcListByTxnno(String txnno){
			List<Map<String, Object>> result=null;
			try {
				ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);
				try {
					EbpDao dao = new EbpDao();
					StringBuffer sql = new StringBuffer();
					List<Object> inputList=new LinkedList<Object>();
					inputList.add(txnno);
					sql.append("select  to_char(b.trandate,'yyyyMMdd') trandate,t.lccursign,t.lcamt,t.lcno,t.feeno, t.mgrrate, t.lcamt,to_char(t.lcissuedate,'yyyyMMdd') lcissuedate,to_char(t.expirydate,'yyyyMMdd') expirydate from IMLCISSUEFO t left join butxnar b on t.txnno=b.txnno where txnno=?");
					StringBuffer item = new StringBuffer();
					item.append("trandate,lccursign,lcamt,lcno,feeno, mgrrate, lcamt,lcissuedate,expirydate");
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
	  *根据信用证号码查询信用证信息 
	  */
	 public List getLcList(String lcno){
			List<Map<String, Object>> result=null;
			try {
				ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);
				try {
					EbpDao dao = new EbpDao();
					StringBuffer sql = new StringBuffer();
					List<Object> inputList=new LinkedList<Object>();
					inputList.add(lcno);
					sql.append("select lcno,lccursign,feeno, mgrrate, lcamt,lcissuedate,expirydate from IMLCISSUEFO  where lcno=?");
					StringBuffer item = new StringBuffer();
					item.append("lcno,lccursign,feeno, mgrrate, lcamt,lcissuedate,expirydate");
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
	  *根据信用证号码查询信用证信息 
	  */
	 public List getLcInfoList(String lcno){
			List<Map<String, Object>> result=null;
			try {
				ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);
				try {
					EbpDao dao = new EbpDao();
					StringBuffer sql = new StringBuffer();
					List<Object> inputList=new LinkedList<Object>();
					inputList.add(lcno);
					sql.append("select lcno,lccursign,feeno, mgrrate, lcamt,lcissuedate,expirydate,draftdays,isusance from IMLCISSUEFO  where lcno=?");
					StringBuffer item = new StringBuffer();
					item.append("lcno,lccursign,feeno, mgrrate, lcamt,lcissuedate,expirydate,draftdays,isusance");
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
	  *根据信用证到单号查询承兑信息 
	  */
	 public List getLcAcptList(String abno){
			List<Map<String, Object>> result=null;
			try {
				ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);
				try {
					EbpDao dao = new EbpDao();
					StringBuffer sql = new StringBuffer();
					List<Object> inputList=new LinkedList<Object>();
					inputList.add(abno);
					sql.append("SELECT bizno,curbizno,acptamt FROM PBACPTFO where curbizno=?");
					StringBuffer item = new StringBuffer();
					item.append("bizno,curbizno,acptamt");
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
	  *根据流水号查询提货担保信息
	  */
	 public List getTHDBListByTxnno(String txnno){
			List<Map<String, Object>> result=null;
			try {
				ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);
				try {
					EbpDao dao = new EbpDao();
					StringBuffer sql = new StringBuffer();
					List<Object> inputList=new LinkedList<Object>();
					inputList.add(txnno);
					sql.append("select  t.lcno,b.trandate,t.feeno, t.mgrrate, t.assamt,t.assdate,t.duedate from IMTHDBFO t left join butxnar b on t.txnno=b.txnno where t.txnno=?");
					StringBuffer item = new StringBuffer();
					item.append("lcno,trandate,feeno, mgrrate, assamt,assdate,duedate");
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
	  *根据提单编号查询提货担保信息
	  */
	 public List getTHDBList(String billNo){
			List<Map<String, Object>> result=null;
			try {
				ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);
				try {
					EbpDao dao = new EbpDao();
					StringBuffer sql = new StringBuffer();
					List<Object> inputList=new LinkedList<Object>();
					inputList.add(billNo);
					sql.append("select feeno, mgrrate, assamt,assdate,duedate from IMTHDBFO where ladbillno=?");
					StringBuffer item = new StringBuffer();
					item.append("feeno, mgrrate, assamt,assdate,duedate");
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
	  *根据提单编号查询提货担保赔付信息
	  */
	 public List getTHDBPEDList(String billNo){
			List<Map<String, Object>> result=null;
			try {
				ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);
				try {
					EbpDao dao = new EbpDao();
					StringBuffer sql = new StringBuffer();
					List<Object> inputList=new LinkedList<Object>();
					inputList.add(billNo);
					sql.append("select payedamt from IMTHDBPEDfo where ladbillno=?");
					StringBuffer item = new StringBuffer();
					item.append("payedamt");
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
	  * 根据保函流水号查询保函信息
	  */
	 public List getLgListByTxnno(String txnno){
			List<Map<String, Object>> result=null;
			try {
				ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);
				try {
					EbpDao dao = new EbpDao();
					StringBuffer sql = new StringBuffer();
					List<Object> inputList=new LinkedList<Object>();
					inputList.add(txnno);
					sql.append("select to_char(b.trandate,'yyyyMMdd') trandate,t.creditno, t.depositpct, t.lgamt,to_char(t.effectdate,'yyyyMMdd') effectdate,to_char(t.failruedate,'yyyyMMdd') from IMLGISSUEFO t left join butxnar b on t.txnno=b.txnno where t.txnno=?");
					StringBuffer item = new StringBuffer();
					item.append("trandate,creditno, depositpct, lgamt,effectdate,failruedate");
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
	  * 根据保函编号查询保函信息
	  */
	 public List getLgList(String lgNo){
			List<Map<String, Object>> result=null;
			try {
				ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);
				try {
					EbpDao dao = new EbpDao();
					StringBuffer sql = new StringBuffer();
					List<Object> inputList=new LinkedList<Object>();
					inputList.add(lgNo);
					sql.append("select creditno, depositpct, lgamt,effectdate,failruedate from IMLGISSUEFO where LGNO=?");
					StringBuffer item = new StringBuffer();
					item.append("creditno, depositpct, lgamt,effectdate,failruedate");
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
	  * 根据保函索赔编号查询保函信息
	  */
	 public List getLgNoList(String lgNo){
			List<Map<String, Object>> result=null;
			try {
				ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);
				try {
					EbpDao dao = new EbpDao();
					StringBuffer sql = new StringBuffer();
					List<Object> inputList=new LinkedList<Object>();
					inputList.add(lgNo);
					sql.append("sselect lgno from IMLGCLAIMFO where CLAIMNO=?");
					StringBuffer item = new StringBuffer();
					item.append("lgno");
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
	  * 根据原流水号查出信息
	  */
	 public List<Map<String,Object>> getButxnar(String oldtxno){
			List<Map<String, Object>> result=null;
			try {
				ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);
				try {
					EbpDao dao = new EbpDao();
					StringBuffer sql = new StringBuffer();
					List<Object> inputList=new LinkedList<Object>();
					inputList.add(oldtxno);
					sql.append("select txnno,tradeno,curtbizno from butxnar where txnno=?");
					StringBuffer item = new StringBuffer();
					item.append("txnno,tradeno,curtbizno");
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
	  * 根据原流水号查出信息
	  */
	 public List<Map<String,Object>> getButxnarAndOrg(String txnno){
			List<Map<String, Object>> result=null;
			try {
				ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);
				try {
					EbpDao dao = new EbpDao();
					StringBuffer sql = new StringBuffer();
					List<Object> inputList=new LinkedList<Object>();
					inputList.add(txnno);
					sql.append("select txnno,b.trandate,b.trancur,tradeno,curtbizno,o.orgcode from butxnar b left join paorg o on b.tranorgno=o.orgno where txnno=?");
					StringBuffer item = new StringBuffer();
					item.append("txnno,trandate,trancur,tradeno,curtbizno,orgcode");
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
	  *插叙信用证注销的冲账数据
	  */
	 public List getLcCloseByTxnno(String txnno){
			List<Map<String, Object>> result=null;
			try {
				ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);
				try {
					EbpDao dao = new EbpDao();
					StringBuffer sql = new StringBuffer();
					List<Object> inputList=new LinkedList<Object>();
					inputList.add(txnno);
					sql.append("select i.lcno,i.feeno,i.lcamt,to_char(t.trandate,'yyyyMMdd') trandate,i.mgrrate,to_char(i.lcissuedate,'yyyyMMdd') lcissuedate,to_char(i.expirydate,'yyyyMMdd') expirydate from butxnar t left join imlcissuefo i on t.curtbizno=i.lcno where t.txnno=?");
					StringBuffer item = new StringBuffer();
					item.append("select lcno,feeno,lcamt,trandate,mgrrate,lcissuedate,expirydate ");
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
	  *插叙信用证撤销的冲账数据
	  */
	 public List getLcCancelByTxnno(String txnno){
			List<Map<String, Object>> result=null;
			try {
				ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);
				try {
					EbpDao dao = new EbpDao();
					StringBuffer sql = new StringBuffer();
					List<Object> inputList=new LinkedList<Object>();
					inputList.add(txnno);
					sql.append("select i.lcno,i.feeno,i.lcamt,to_char(t.trandate,'yyyyMMdd') trandate,i.mgrrate,to_char(i.lcissuedate,'yyyyMMdd') lcissuedate,to_char(i.expirydate,'yyyyMMdd') expirydate from butxnar t left join imlcissuefo i on t.prebizno=i.lcno where t.txnno=?");
					StringBuffer item = new StringBuffer();
					item.append("select lcno,feeno,lcamt,trandate,mgrrate,lcissuedate,expirydate ");
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
	  *插叙信用证退单的冲账数据
	  */
	 public List getLcReturnByTxnno(String txnno){
			List<Map<String, Object>> result=null;
			try {
				ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);
				try {
					EbpDao dao = new EbpDao();
					StringBuffer sql = new StringBuffer();
					List<Object> inputList=new LinkedList<Object>();
					inputList.add(txnno);
					sql.append("select i.lcno,i.feeno,i.lcamt,to_char(t.trandate,'yyyyMMdd') trandate," +
							"i.mgrrate,to_char(i.lcissuedate,'yyyyMMdd') lcissuedate,to_char(i.expirydate,'yyyyMMdd') expirydate ");
					sql.append("from (select i.lcno, t.trandate from butxnar t left join p" +
							"brejfo p on t.txnno = p.txnno left join imlcabfo i on t.prebizno = " +
							"i.abno where p.dealtype = '0' and t.txnno = ?) t ");
					sql.append("left join imlcissuefo i on t.lcno = i.lcno");
					StringBuffer item = new StringBuffer();
					item.append("select lcno,feeno,lcamt,trandate,mgrrate,lcissuedate,expirydate ");
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
	  *插叙信用证承兑的冲账数据
	  */
	 public List getLcAcptReturnByTxnno(String txnno){
			List<Map<String, Object>> result=null;
			try {
				ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);
				try {
					EbpDao dao = new EbpDao();
					StringBuffer sql = new StringBuffer();
					List<Object> inputList=new LinkedList<Object>();
					inputList.add(txnno);
					sql.append("select i.lcno,i.feeno,i.lcamt,to_char(t.trandate,'yyyyMMdd') trandate," +
							"i.mgrrate,to_char(i.lcissuedate,'yyyyMMdd') lcissuedate,to_char(i.expirydate,'yyyyMMdd') expirydate ");
					sql.append("  from (select i.lcno, t.trandate from butxnar t " +
							"left join imlcabfo i on t.curtbizno = i.abno where t.txnno = ?) t ");
					sql.append("left join imlcissuefo i on t.lcno = i.lcno");
					StringBuffer item = new StringBuffer();
					item.append("select lcno,feeno,lcamt,trandate,mgrrate,lcissuedate,expirydate ");
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
	  *插叙信用证付汇的冲账数据
	  */
	 public List getLcPayReturnByTxnno(String txnno){
			List<Map<String, Object>> result=null;
			try {
				ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);
				try {
					EbpDao dao = new EbpDao();
					StringBuffer sql = new StringBuffer();
					List<Object> inputList=new LinkedList<Object>();
					inputList.add(txnno);
					sql.append("select i.lcno,i.feeno,i.lcamt,to_char(t.trandate, 'yyyyMMdd') " +
							"trandate,i.mgrrate,to_char(i.lcissuedate, 'yyyyMMdd') " +
							"lcissuedate,to_char(i.expirydate, 'yyyyMMdd') expirydate ");
					sql.append("  from (select i.lcno, t.trandate from butxnar t " +
							"left join imlcabfo i on t.prebizno = i.abno where t.txnno = ?) t ");
					sql.append("left join imlcissuefo i on t.lcno = i.lcno");
					StringBuffer item = new StringBuffer();
					item.append("select lcno,feeno,lcamt,trandate,mgrrate,lcissuedate,expirydate ");
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
	  *插叙提货担保撤销冲账数据
	  */
	 public List getThdbCalRetrunByTxnno(String txnno){
			List<Map<String, Object>> result=null;
			try {
				ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);
				try {
					EbpDao dao = new EbpDao();
					StringBuffer sql = new StringBuffer();
					List<Object> inputList=new LinkedList<Object>();
					inputList.add(txnno);
					sql.append("select i.feeno, i.assamt, to_char(t.trandate, 'yyyyMMdd') trandate,i.mgrrate,to_char(i.assdate, 'yyyyMMdd') assdate,to_char(i.duedate, 'yyyyMMdd') duedate ");
					sql.append(" from butxnar t left join imthdbfo i on t.prebizno = i.ladbillno");
					sql.append("where t.txnno = ?");
					StringBuffer item = new StringBuffer();
					item.append("select feeno,assamt,trandate,mgrrate,assdate,duedate ");
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
	  *保函注销冲账数据
	  */
	 public List getLgRfguRetrunByTxnno(String txnno){
			List<Map<String, Object>> result=null;
			try {
				ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);
				try {
					EbpDao dao = new EbpDao();
					StringBuffer sql = new StringBuffer();
					List<Object> inputList=new LinkedList<Object>();
					inputList.add(txnno);
					sql.append("select i.creditno,i.lgamt,to_char(t.trandate,'yyyyMMdd') trandate,i.depositpct, to_char(i.effectdate,'yyyyMMdd') effectdate, to_char(i.failruedate,'yyyyMMdd') failruedate ");
					sql.append(" from butxnar t left join pbruglfo p on t.txnno = p.txnno left join imlgissuefo i on t.prebizno = i.lgno");
					sql.append("where p.rmflag = '1' and t.txnno=?");
					StringBuffer item = new StringBuffer();
					item.append("select creditno,lgamt,trandate,depositpct,effectdate,failruedate ");
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
	  *保函撤销冲账数据
	  */
	 public List getLgCancelRetrunByTxnno(String txnno){
			List<Map<String, Object>> result=null;
			try {
				ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);
				try {
					EbpDao dao = new EbpDao();
					StringBuffer sql = new StringBuffer();
					List<Object> inputList=new LinkedList<Object>();
					inputList.add(txnno);
					sql.append("select i.creditno,i.lgamt,to_char(t.trandate,'yyyyMMdd') trandate,i.depositpct, to_char(i.effectdate,'yyyyMMdd') effectdate, to_char(i.failruedate,'yyyyMMdd') failruedate ");
					sql.append(" from butxnar t left join imlgissuefo i on t.prebizno = i.lgno");
					sql.append("where  t.txnno=?");
					StringBuffer item = new StringBuffer();
					item.append("select creditno,lgamt,trandate,depositpct,effectdate,failruedate ");
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
	  *保函赔付冲账数据
	  */
	 public List getLgPayRetrunByTxnno(String txnno){
			List<Map<String, Object>> result=null;
			try {
				ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);
				try {
					EbpDao dao = new EbpDao();
					StringBuffer sql = new StringBuffer();
					List<Object> inputList=new LinkedList<Object>();
					inputList.add(txnno);
					sql.append("select i.creditno,i.lgamt,to_char(t.trandate,'yyyyMMdd') trandate,i.depositpct, to_char(i.effectdate,'yyyyMMdd') effectdate, to_char(i.failruedate,'yyyyMMdd') failruedate ");
					sql.append(" from (select t2.lgno, t1.trandate from butxnar t1 left join imlgclaimfo t2 on t1.prebizno = t2.claimno where t2.txnno = ?) t");
					sql.append("left join imlgissuefo i on t.lgno = i.lgno");
					StringBuffer item = new StringBuffer();
					item.append("select creditno,lgamt,trandate,depositpct,effectdate,failruedate ");
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
	 
	 
}
