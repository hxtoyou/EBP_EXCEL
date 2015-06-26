package com.ebills.product.dg.credit.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.ebills.product.dg.AcctInterface.domain.Corp;
import com.ebills.product.dg.AcctInterface.domain.GeneralCalc;
import com.ebills.product.dg.AcctInterface.domain.LoanInfo;
import com.ebills.product.dg.AcctInterface.domain.Org;
import com.ebills.product.dg.AcctInterface.domain.Transaction;
import com.ebills.product.dg.commons.utils.ApplicationException;
import com.ebills.util.EbillsException;
import com.ebills.util.db.ConnectionManager;
import com.ebills.util.db.ConnectionManagerFactory;
import com.ebills.commons.SerialNoFactory;
import com.ebills.utils.EbpDao;

public class UtilTools {
	
	public static String bringSerialNo(String serialNo_sn) throws EbillsException{ 
		SerialNoFactory serialNoFactory =  new SerialNoFactory();
		String serialNo = serialNoFactory.getSerialNo("TPCREDITINFO",8);	
		return serialNo_sn+serialNo;
	}
	
	
	 /***
	  * 获得butxn中的数据
	  */
	public Transaction getTransaction(String txnno){
		Transaction tx=new Transaction();
		String sql="select txnno,tranorgno,curtbizno,trancur,tranamt,handlerid,checkerid,managerid,fileno,custid,tradeno,launchmodeno from butxntp where txnno=?";
		List<Object> params=new LinkedList<Object>();
		params.add(txnno);
		try {
			List list=this.openSQL(sql, params);
			if(list.size()>0){
				Map map=(Map)list.get(0);
				tx.setTxnSerialNo((String)map.get("txnno"));
				tx.setTransactOrgNo((String)map.get("tranorgno"));
				tx.setCurrentBizNo((String)map.get("curtbizno"));
				tx.setCurSign((String)map.get("trancur"));
				tx.setAmount(Double.parseDouble((String)map.get("tranamt")==null? "0": (String)map.get("tranamt")));
				tx.setHandleOperId(Integer.parseInt((String)map.get("handlerid")==null ?"0":(String)map.get("handlerid")));
				tx.setCheckOperId(Integer.parseInt((String)map.get("checkerid")==null ? "0":(String)map.get("checkerid")));
				tx.setManagerId((String)map.get("managerid"));
				tx.setFileNo((String)map.get("fileno"));
				tx.setTradeNo((String)map.get("tradeno"));
				tx.setCorpNo((String)map.get("custid"));
				tx.setLaunchModeNo((String)map.get("launchmodeno"));
			}
			
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		return tx;
		
	}
	
	 /***
	  * 获得butxn中的数据
	  */
	public Transaction getTransactionar(String txnno){
		Transaction tx=new Transaction();
		String sql="select txnno,tranorgno,curtbizno,trancur,tranamt,handlerid,checkerid,managerid,fileno,custid,tradeno,launchmodeno from butxnar where txnno=?";
		List<Object> params=new LinkedList<Object>();
		params.add(txnno);
		try {
			List list=this.openSQL(sql, params);
			if(list.size()>0){
				Map map=(Map)list.get(0);
				tx.setTxnSerialNo((String)map.get("txnno"));
				tx.setTransactOrgNo((String)map.get("tranorgno"));
				tx.setCurrentBizNo((String)map.get("curtbizno"));
				tx.setCurSign((String)map.get("trancur"));
				tx.setAmount(Double.parseDouble((String)map.get("tranamt")==null? "0": (String)map.get("tranamt")));
				tx.setHandleOperId(Integer.parseInt((String)map.get("handlerid")==null ?"0":(String)map.get("handlerid")));
				tx.setCheckOperId(Integer.parseInt((String)map.get("checkerid")==null ? "0":(String)map.get("checkerid")));
				tx.setManagerId((String)map.get("managerid"));
				tx.setFileNo((String)map.get("fileno"));
				tx.setTradeNo((String)map.get("tradeno"));
				tx.setCorpNo((String)map.get("custid"));
				tx.setLaunchModeNo((String)map.get("launchmodeno"));
			}
			
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		return tx;
		
	}
	
	
	 /***
	  * 获得buloans中的数据
	  */
	public LoanInfo getLoanInfo(String debtno,String constractno){
		LoanInfo loanInfo=new LoanInfo();
		String sql="select m.txnno,m.loanbizno,m.contractno,m.debtno,m.loancur,m.loanamt,m.interestdate,m.maturedate, m.realrate,m.loanacctno,m.ltdact,m.contiinttmode "+
" from buloansfo m,tpcreditinfoar n,butxnar b where  b.txnno=m.txnno and b.fileno=n.putoutserialno and  n.debtno=? and n.constractno=?";
		String balsql="select amount from bubalfo where fieldname in('LCPTRDFINBAL','EXPTRDFINBAL','ICPTRDFINBAL','OCPTRDFINBAL','INVPTRDFINBAL','CREPTRDFINBAL') and bizno=?";
		List<Object> params=new LinkedList<Object>();
		params.add(debtno);
		params.add(constractno);
		try {
			List list=this.openSQL(sql, params);
		
			if(list.size()>0){
				Map map=(Map)list.get(0);
				loanInfo.setTxnSerialNo((String)map.get("txnno"));
				loanInfo.setBizNo((String)map.get("loanbizno"));
				loanInfo.setConstractNo((String)map.get("contractno"));
				loanInfo.setDebtNo((String)map.get("debtno"));
				loanInfo.setLoanCur((String)map.get("loancur"));
				loanInfo.setLoanAmount(Double.parseDouble((String)map.get("loanamt")==null?"0":(String)map.get("loanamt")));
				loanInfo.setInterestDate(GeneralCalc.strToSQLDate(this.fromdate((String)map.get("interestdate"))));
				loanInfo.setMatureDate(GeneralCalc.strToSQLDate(this.fromdate((String)map.get("maturedate"))));
				loanInfo.setInterestRate(Double.parseDouble((String)map.get("realrate")==null?"0":(String)map.get("realrate")));
				loanInfo.setLoanAcctNo((String)map.get("loanacctno"));
				loanInfo.setLtdact((String)map.get("ltdact"));
				loanInfo.setIsExt((String)map.get("contiinttmode"));
			}
			params=new LinkedList<Object>();
			params.add(loanInfo.getBizNo());
			list=this.openSQL(balsql, params);
			if(list.size()>0){
				Map map=(Map)list.get(0);
				loanInfo.setLoanBalanceAmt(Double.parseDouble((String)map.get("amount")));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loanInfo;
		
	}
	
	public String fromdate(String date){
		if(date==null || "".equals(date)){
			return "";
		}
		 SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		 SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			String newDate="";
		try {
			java.util.Date  utiledate = df1.parse(date);
			newDate=df.format(utiledate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return newDate;
			
	}
	
	 /***
	  * 获得butxn中的数据
	  */
	public Transaction getTransactionbyfileno(String fileno){
		Transaction tx=new Transaction();
		String sql="select txnno,tranorgno,curtbizno,trancur,tranamt,handlerid,checkerid,managerid,fileno,custid,tradeno,launchmodeno from butxntp where fileno=?";
		List<Object> params=new LinkedList<Object>();
		params.add(fileno);
		try {
			List list=this.openSQL(sql, params);
			if(list.size()>0){
				Map map=(Map)list.get(0);
				tx.setTxnSerialNo((String)map.get("txnno"));
				tx.setTransactOrgNo((String)map.get("tranorgno"));
				tx.setCurrentBizNo((String)map.get("curtbizno"));
				tx.setCurSign((String)map.get("trancur"));
				tx.setAmount(Double.parseDouble((String)map.get("tranamt")==null? "0": (String)map.get("tranamt")));
				tx.setHandleOperId(Integer.parseInt((String)map.get("handlerid")==null ?"0":(String)map.get("handlerid")));
				tx.setCheckOperId(Integer.parseInt((String)map.get("checkerid")==null ? "0":(String)map.get("checkerid")));
				tx.setManagerId((String)map.get("managerid"));
				tx.setFileNo((String)map.get("fileno"));
				tx.setTradeNo((String)map.get("tradeno"));
				tx.setCorpNo((String)map.get("custid"));
				tx.setLaunchModeNo((String)map.get("launchmodeno"));
			}
			
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		return tx;
		
	}
	
	public static String getPasys() throws Exception{
		String str="";
		try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			try {
				EbpDao dao = new EbpDao();
				String sql="select sysval from PASYS";
				List<Object> params=new LinkedList<Object>();
				List list=dao.queryBySql(sql, null, params);
				Map map=(Map)list.get(2);
				String str1=(String) map.get("sysval");
				str=str1.replace("-", "");
				
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
		return str;
		
	}
	
	public static String getOrgNo(String orgCode) throws Exception{
		String str="";
		try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			try {
				EbpDao dao = new EbpDao();
				String sql="select orgno from paorg where orgcode=?";
				List<Object> params=new LinkedList<Object>();
				params.add(orgCode);
				List list=dao.queryBySql(sql, null, params);
				if(list.size()>0){
				Map map=(Map)list.get(0);
				str=(String) map.get("orgno");
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
		return str;
		
	}
	
	public static Map getFileNo(String txnno) throws ApplicationException{
		 Map data=new HashMap();
		try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			try {
				EbpDao dao = new EbpDao();
				String sql="select fileno from butxntp where txnno=?";
				List<Object> params=new LinkedList<Object>();
				params.add(txnno);
				List list=dao.queryBySql(sql, null, params);
				if(list.size()>0){
					data=(Map)list.get(0);
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
		return data;
		
	}
	
	public static Map getlcNo(String abNo) throws ApplicationException{
		 Map data=new HashMap();
		try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			try {
				EbpDao dao = new EbpDao();
				String sql="select m.lcno,m.negobankswift,m.negobkname,m.abamt,t.maincorpno,n.lccursign,t.cnname from imlcabfo m,imlcissuefo n,pacrp t  where m.lcno=n.lcno and n.appno=t.corpno and  abno=?";
				List<Object> params=new LinkedList<Object>();
				params.add(abNo);
				List list=dao.queryBySql(sql, null, params);
				if(list.size()>0){
				 data=(Map)list.get(0);
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
		return data;
		
	}
	
	public static Map getlcInfo(String lcNo) throws ApplicationException{
		 Map data=new HashMap();
		try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			try {
				EbpDao dao = new EbpDao();
				String sql="select n.txnno,n.lcno,n.lccursign,n.lcamt ,n.tenortype,n.draftdays from imlcissuefo n,pacrp t  where n.appno=t.corpno and  n.lcno=?";
				List<Object> params=new LinkedList<Object>();
				params.add(lcNo);
				List list=dao.queryBySql(sql, null, params);
				if(list.size()>0){
				 data=(Map)list.get(0);
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
		return data;
		
	}
	
	public static Map getAdvNo(String abNo) throws ApplicationException{
		 Map data=new HashMap();
		try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			try {
				EbpDao dao = new EbpDao();
				String sql="select t.maincorpno,n.lccur,t.cnname from exlcbpfo m,exlcadvfo n,pacrp t  where m.advno=n.advno and n.benefno=t.corpno and m.bpno=?";
				List<Object> params=new LinkedList<Object>();
				params.add(abNo);
				List list=dao.queryBySql(sql, null, params);
				if(list.size()>0){
				 data=(Map)list.get(0);
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
		return data;
		
	}
	
	
	public static Map getbank(String swiftcode) throws ApplicationException{
		 Map data=new HashMap();
		try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			try {
				EbpDao dao = new EbpDao();
				String sql="select enname from pabank where bankswiftcode=?";
				List<Object> params=new LinkedList<Object>();
				params.add(swiftcode);
				List list=dao.queryBySql(sql, null, params);
				if(list.size()>0){
				 data=(Map)list.get(0);
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
		return data;
		
	}
	
	
	public static Map getDolNo(String abNo) throws ApplicationException{
		 Map data=new HashMap();
		try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			try {
				EbpDao dao = new EbpDao();
				String sql="select n.maincorpno,m.collcur,n.cnname from exdoccollfo m, pacrp n where m.princino=n.corpno and m.bizno=?";
				List<Object> params=new LinkedList<Object>();
				params.add(abNo);
				List list=dao.queryBySql(sql, null, params);
				if(list.size()>0){
				 data=(Map)list.get(0);
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
		return data;
		
	}
	
	
	public static Map getColNo(String abNo) throws ApplicationException{
		 Map data=new HashMap();
		try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			try {
				EbpDao dao = new EbpDao();
				String sql="select n.maincorpno,m.remitcur,n.cnname from imcollfo m, pacrp n where m.draweeno=n.corpno and m.icno=?";
				List<Object> params=new LinkedList<Object>();
				params.add(abNo);
				List list=dao.queryBySql(sql, null, params);
				if(list.size()>0){
				 data=(Map)list.get(0);
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
		return data;
		
	}
	

	public static void doProcessFinish(String putoutserialno ,String tabtp,String tabar) throws Exception {
		try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			
			try {
				EbpDao dao = new EbpDao();
				List<Object> params=new LinkedList<Object>();	
				String insertsql="insert into "+tabar +" select * from  "+tabtp+" where putoutserialno='"+putoutserialno+"'";
				dao.execute(insertsql,params);
				String deletesql="delete from "+tabtp+" where putoutserialno='"+putoutserialno+"'";
				dao.execute(deletesql,params);
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
		
		
	}
	
	
	public static void ProcessCreditFinish(String date ,String tabtp,String tabar,String outDate) throws Exception {
		try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			
			try {
				EbpDao dao = new EbpDao();
				List<Object> params=new LinkedList<Object>();	
				String insertsql="insert into "+tabar +" select * from  "+tabtp+" where "+outDate+"='"+date+"'";
				dao.execute(insertsql,params);
				String deletesql="delete from "+tabtp+" where "+ outDate+"='"+date+"'";
				dao.execute(deletesql,params);
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
		
		
	}
	
	
	public static Map getCropNo(String abNo) throws ApplicationException{
		 Map data=new HashMap();
		try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			try {
				EbpDao dao = new EbpDao();
				String sql="select corpno from pacrp where maincorpno=?";
				List<Object> params=new LinkedList<Object>();
				params.add(abNo);
				List list=dao.queryBySql(sql, null, params);
				if(list.size()>0){
				 data=(Map)list.get(0);
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
		return data;
		
	}
	
	public static String getCorpNo(String maincorpno) throws ApplicationException{
		String corpNo="";
		try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			try {
				EbpDao dao = new EbpDao();
				String sql="select corpno from pacrp where maincorpno=?";
				List<Object> params=new LinkedList<Object>();
				params.add(maincorpno);
				List list=dao.queryBySql(sql, null, params);
				if(list.size()>0){
				 Map map=(Map)list.get(0);
				 corpNo=(String)map.get("corpno");
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
		return corpNo;
		
	}
	
	public  Corp getCorpInfo(String maincorpno ) throws ApplicationException{
		  Corp corpInfo=new Corp();
		try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			try {
				EbpDao dao = new EbpDao();
				String sql="select corpno,maincorpno,corpkind,orgno,servorgno,cnname,enname,enaddr from pacrp  where maincorpno=?";
				List<Object> params=new LinkedList<Object>();
				params.add(maincorpno);
				List list=dao.queryBySql(sql, null, params);
				if(list.size()>0){
				 Map map=(Map)list.get(0);
				 corpInfo.setCorpNo((String)map.get("corpno"));
				 corpInfo.setMainCorpNo((String)map.get("maincorpno"));
				 corpInfo.setCorpKind((String)map.get("corpkind"));
				 corpInfo.setOrgNo((String)map.get("orgno"));
				 corpInfo.setServOrgNo((String)map.get("servorgno"));
				 corpInfo.setCnName((String)map.get("cnname"));
				 corpInfo.setEnName((String)map.get("enname"));
				 corpInfo.setEnAddr((String)map.get("enaddr"));
				 
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
		return corpInfo;
		
	}
	
	public static boolean copyFile(File source, File dest) throws ApplicationException {
		try {
			if (!source.exists())
				throw new ApplicationException("源文件不存在");
			if (!source.isFile())
				throw new ApplicationException("源文件是目录");
			if (dest.exists())
				throw new ApplicationException("目标文件已存在");
			if (!dest.createNewFile())
				throw new ApplicationException("不能建立文件:" + dest);
		} catch (Exception e) {
			throw new ApplicationException(e.getMessage(), e);
		}
		try {
			FileInputStream sourceStream = new FileInputStream(source);
			FileOutputStream destStream = new FileOutputStream(dest);
			byte[] buf = new byte[1024];
			int len = 0;
			len = sourceStream.read(buf);
			while (len > 0) {
				destStream.write(buf, 0, len);
				len = sourceStream.read(buf);
			}
			sourceStream.close();
			destStream.flush();
			destStream.close();
		} catch (Exception e) {
			throw new ApplicationException(e);
		}
		return true;
	}
	
	
	/*查询有机构权限的机构*/
	public  Org queryOrgInfo(String corpNo) throws ApplicationException{
		Org orginfo=new Org();
		if(corpNo!=null && !"".equals(corpNo)){
			Corp corp=this.getCorpInfo(corpNo);
			try {
				ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);
				try {
					EbpDao dao = new EbpDao();
					String sql="select orgno,orgcode,swiftcode,parentorgno,isbussorg from paorg where orgno=?";
					 List<Object> params=new LinkedList<Object>();
					 params.add(corp.getOrgNo());
					List list=dao.queryBySql(sql, null, params);
					for(Iterator it=list.iterator();it.hasNext();){
					 Map map=(Map)it.next();
					 orginfo.setOrgNo((String)map.get("orgno"));
					 orginfo.setParentOrgNo((String)map.get("parentorgno"));
					 orginfo.setOrgCode((String)map.get("orgcode"));
					 if("Y".equals((String)map.get("isbussorg"))){
						 return orginfo;
					 }
					 if(!"Y".equals((String)map.get("isbussorg"))){
						 params=new LinkedList<Object>();
						 params.add(orginfo.getParentOrgNo());
						 System.out.println(orginfo.getParentOrgNo());
						 List list1=dao.queryBySql(sql, null, params);
						 for( Iterator it1=list1.iterator();it1.hasNext();){
							 map=(Map)it1.next();
							 orginfo=new Org();
							 orginfo.setOrgNo((String)map.get("orgno"));
							 orginfo.setParentOrgNo((String)map.get("parentorgno"));
							 orginfo.setOrgCode((String)map.get("orgcode"));
							 if("Y".equals((String)map.get("isbussorg"))){
								 return orginfo;
							 }
						 } 
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
		}
		if(orginfo.getOrgNo()==null||"".equals(orginfo.getOrgNo())){
			 orginfo=new Org();
			 orginfo.setOrgNo("1000000000");
			 orginfo.setParentOrgNo("0000000000");
			 orginfo.setOrgCode("816000009");
			
		 }
		return orginfo;
		
		
	}
	
	public List getOrgList(String orgNo) throws ApplicationException{
		List list=new ArrayList();
		try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			try {
				EbpDao dao = new EbpDao();
				String sql="SELECT orgno,orgcode,swiftcode,parentorgno,isbussorg FROM PAORG WHERE ORGNO=?";
				 List<Object> params=new LinkedList<Object>();
				 params.add(orgNo);
				 list=dao.queryBySql(sql, null, params);
				if(list.size()>0){
				 Map map=(Map)list.get(0);
				 
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
		return list;
	}
	
	public static String initPageData(String pagedata,String Data ){
		String str="txnNo:"+
				";bizNo:"+
				";putOutSerialNo:"+
				";constractNo:"+
				";debtNo:"+
				";corpCode:"+
				";tradeCode:"+
				";cursign:"+
				";lcAmt:"+
				";termdt:"+
				";tenordays:"+
				";lcType:"+
				";lcRegisterType:"+
				";orgNo:"+
				";bailRatio:"+
				";depCursign:"+
				";depAmt:"+
				";depAcctNo:"+
				";lgInitDay:"+
				";lgType:"+
				";tyCustName:"+
				";sgType:"+
				";ourNo:"+
				";addAmout:"+
				";amendtenorDays:"+
				";newTerMdt:"+
				";amt:"+
				";autHdt:"+
				";acctNo:"+
				";intr:"+
				";duedt:"+
				";gjiNtMode:"+
				";addDays:";
		StringBuffer bf=new StringBuffer(str);
		
		return bf.toString();
	}
	
	public static List openSQL(String sql,List<Object> params)
			throws ApplicationException {
		List list=null;
		try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			
			try {
				EbpDao dao = new EbpDao();
				if(params==null){
					params=new LinkedList<Object>();	
				}
				 list=dao.queryBySql(sql,null, params);
				
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
		return list;
	}
	
	/*
	 * 查找修改的状态
	 *
	 * */
	public static boolean  isLCAmendStauts(String tradeNo,String lcno){
		String sql="select txnno from butxntp where tradeno=? and curtbizno=? "+
	    " union all "+
		"select txnno from imlcamendfo where lcno=? and AFFIRMFLAG='S'";
		System.out.println(sql);
		List<Object> params=new LinkedList<Object>();
		params.add(tradeNo);
		params.add(lcno);
		params.add(lcno);
		try {
			List list=openSQL(sql,params);
			if(list==null||list.size()<=0){
				return false;
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		return true;
		
	}
	
	public static String getPaCcyPri(String Curigsn) throws ApplicationException{
		StringBuffer info=new StringBuffer();
		try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			try {
				EbpDao dao = new EbpDao();
				String sql="select baseprice,buyprice,saleprice from paccypri where cursign=?";
				List<Object> params=new LinkedList<Object>();
				params.add(Curigsn);
				List list=dao.queryBySql(sql, null, params);
				if(list.size()>0){
				 Map map=(Map)list.get(0);
				 info.append((String)map.get("baseprice")+";");
				 info.append((String)map.get("buyprice")+";");
				 info.append((String)map.get("saleprice"));
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
		return info.toString();
		
	}

	

	
	

	/** 执行存储过程* */
	public List executeStoreProc(String storeName, List inParam, List outParam)
			throws EbillsException {
		ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
		Connection   m_Connection = cm.getConnection();
		CallableStatement callStatement =null; ;
		String sql = "{ call " + storeName + "( ";
		String typeStr = null;
		int paramCount = 0;
		List retList = new ArrayList();
		try {
			if (inParam != null)
				paramCount += inParam.size();
			if (outParam != null)
				paramCount += outParam.size();
			if (inParam != null) {
				for (int i = 0; i < inParam.size(); i++) {
					sql += "?,";
				}
			}
			if (outParam != null) {
				for (int i = 0; i < outParam.size(); i++)
					sql += "?,";
			}
			if (sql.endsWith(","))
				sql = sql.substring(0, sql.length() - 1);
			sql += ")}";
			System.out.println(sql);
			 callStatement=m_Connection.prepareCall(sql);
			System.out.println(inParam);
			int inParamSize = 0;
			if (inParam != null) {
				inParamSize = inParam.size();
				for (int i = 0; i < inParam.size(); i++) {

					typeStr = inParam.get(i).getClass().getName();
					if (typeStr.equals(String.class.getName()))
						callStatement.setString(i + 1, (String) inParam.get(i));
					else if (typeStr.equals(java.sql.Date.class.getName()))
						callStatement.setDate(i + 1, (java.sql.Date) inParam.get(i));
					else if (typeStr.equals(Integer.class.getName()))
						callStatement.setInt(i + 1, ((Integer) inParam.get(i))
								.intValue());
					else if (typeStr.equals(Double.class.getName()))
						callStatement.setDouble(i + 1,
								((Double) inParam.get(i)).doubleValue());
					else
						throw new EbillsException(null,"invalid input param type "
								+ typeStr);
				}
			}
			if (outParam != null)
				for (int i = 0; i < outParam.size(); i++) {
					Object param = outParam.get(i);
					if (param instanceof Double)
						callStatement.registerOutParameter(inParamSize + i + 1,
								Types.DOUBLE);
					else if (param instanceof String)
						callStatement.registerOutParameter(inParamSize + i + 1,
								Types.VARCHAR);
					else if (param instanceof Integer)
						callStatement.registerOutParameter(inParamSize + i + 1,
								Types.INTEGER);
					else if (param instanceof Date)
						callStatement.registerOutParameter(inParamSize + i + 1,
								Types.DATE);
					else
						throw new EbillsException(null,
								"output parameter type error "
										+ param.getClass().getName());

				}

			callStatement.execute();
			System.out.println("outParam:" + outParam);
			if (outParam != null)
				for (int i = 0; i < outParam.size(); i++) {
					Object param = outParam.get(i);
					if (param instanceof Double)
						retList.add(new Double(callStatement
								.getDouble(inParamSize + i + 1)));
					else if (param instanceof String)
						retList.add(callStatement
								.getString(inParamSize + i + 1));
					else if (param instanceof Integer)
						retList.add(new Integer(callStatement
								.getInt(inParamSize + i + 1)));
					else if (param instanceof Date)
						retList.add(callStatement.getDate(inParamSize + i + 1));
				}
			return retList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EbillsException(null,"Execute storeProc " + storeName
					+ " error:" + e.getMessage());
		} finally {
				try {
					if(callStatement != null){
						callStatement.close();
					}
					m_Connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

    
    public void deleteBailInfo(String Lcno ) throws Exception {
    	List<Object> params=new LinkedList<Object>();	
    	String sql="delete from bailinfo where lcno = ?";
    	params.add(Lcno); 	
    	try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			
			try {
				EbpDao dao = new EbpDao();
				dao.execute(sql,params);
				
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
    }

    
    public void updateLoaninfobyDZtype(String type,String loanBizNo) throws ApplicationException  {
    	List<Object> params=new LinkedList<Object>();	
    	String sql="update buloansfo set contiinttmode =? where loanbizno = ?";
    	params.add(type);
    	params.add(loanBizNo);
    	try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			
			try {
				EbpDao dao = new EbpDao();
				dao.execute(sql,params);
				
			} catch (Exception e) {
				e.printStackTrace();
				cm.setRollbackOnly();
				throw new ApplicationException(e.getMessage());
			}finally{
				cm.commit();
			}
			
		} catch (EbillsException e1) {
			e1.printStackTrace();
			throw new ApplicationException(e1.getMessage());
		}
    	
    }
    
    public void updateLoaninfobyMUDATE(String Date,String rate, String loanBizNo) throws ApplicationException  {
    	List<Object> params=new LinkedList<Object>();	
    	String sql="update buloansfo set maturedate = to_date (?, 'yyyy-mm-dd'),realrate =? where loanbizno =?";
    	params.add(Date);
    	params.add(rate);
    	params.add(loanBizNo);
    	try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			
			try {
				EbpDao dao = new EbpDao();
				dao.execute(sql,params);
				
			} catch (Exception e) {
				e.printStackTrace();
				cm.setRollbackOnly();
				throw new ApplicationException(e.getMessage());
			}finally{
				cm.commit();
			}
			
		} catch (EbillsException e1) {
			e1.printStackTrace();
			throw new ApplicationException(e1.getMessage());
		}
    	
    }
	
	

		
		
}
