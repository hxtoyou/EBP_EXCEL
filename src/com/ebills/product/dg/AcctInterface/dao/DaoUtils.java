package com.ebills.product.dg.AcctInterface.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.ebills.commons.SerialNoFactory;
import com.ebills.product.dg.AcctInterface.domain.AcctList;
import com.ebills.product.dg.AcctInterface.domain.Corp;
import com.ebills.product.dg.AcctInterface.domain.CreditInfo;
import com.ebills.product.dg.AcctInterface.domain.DFApply;
import com.ebills.product.dg.AcctInterface.domain.DFPayment;
import com.ebills.product.dg.AcctInterface.domain.GeneralCalc;
import com.ebills.product.dg.AcctInterface.domain.LoanInfo;
import com.ebills.product.dg.AcctInterface.domain.LoanRepayInfo;
import com.ebills.product.dg.AcctInterface.domain.OrgAcct;
import com.ebills.product.dg.AcctInterface.domain.Transaction;
import com.ebills.product.dg.AcctInterface.domain.User;
import com.ebills.product.dg.commons.utils.ApplicationException;
import com.ebills.util.EbillsException;
import com.ebills.util.db.ConnectionManager;
import com.ebills.util.db.ConnectionManagerFactory;
import com.ebills.utils.CommonUtil;
import com.ebills.utils.EbpDao;
import com.ibm.icu.util.Calendar;

public class DaoUtils {
	
	
	 /***
	  * 获得butxn中的数据
	  */
	public Transaction getTransaction(String txnno){
		Transaction tx=new Transaction();
		String sql="select txnno,tranorgno,curtbizno,trancur,tranamt,handlerid,checkerid,managerid,fileno,custid,tradeno from butxntp where txnno=?";
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
			}
			
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		return tx;
		
	}
	
	 /***
	  * 获得pacrp中的数据
	  */
	public Corp getCorp(String corpNo){
		Corp corp=new Corp();
		String sql="select maincorpno from pacrp where corpno=?";
		List<Object> params=new LinkedList<Object>();
		params.add(corpNo);
		try {
			List list=this.openSQL(sql, params);
			if(list.size()>0){
				Map map=(Map)list.get(0);
				corp.setMainCorpNo((String)map.get("maincorpno"));
			}
			
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		return corp;
		
	}
	
	 /***
	  * 获得pacrp中的数据
	  */
	public String getCorpByMaincorp(String maincorpno){
		String corpno = "";
		String sql="select corpno from pacrp where maincorpno=?";
		List<Object> params=new LinkedList<Object>();
		params.add(maincorpno);
		try {
			List list=this.openSQL(sql, params);
			if(list.size()>0){
				Map map=(Map)list.get(0);
				corpno =(String)map.get("corpno");
			}
			
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		return corpno;
		
	}
	
	
	 /***
	  * 获得buloans中的数据
	  */
	public LoanInfo getLoanInfo(String txnNo){
		LoanInfo loanInfo=new LoanInfo();
		String sql="select txnno,loanbizno,contractno,debtno,loancur,loanamt,interestdate,maturedate, realrate,loanacctno,ltdact,contiinttmode from buloanstp m where txnno=?";
		String balsql="select amount from bubalfo where fieldname in('LCPTRDFINBAL','EXPTRDFINBAL','ICPTRDFINBAL','OCPTRDFINBAL','INVPTRDFINBAL','CREPTRDFINBAL') and bizno=?";
		List<Object> params=new LinkedList<Object>();
		params.add(txnNo);
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
	public String fromdateParse(String date){
		if(date==null || "".equals(date)){
			return "";
		}
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//		 SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		 String newDate="";
		try {
			java.util.Date  utiledate = df.parse(date);
			newDate=df.format(utiledate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return newDate;	
	}
	
	
	 /***
	  * 获得buLoanRepay中的数据
	  */
	public LoanRepayInfo getLoanRepayInfo(String txnNo){
		LoanRepayInfo loanRepayInfo=new LoanRepayInfo();
		String sql="select txnno,tradeno,bizno ,primarybizno,loancur ,preintamt,issendmail,repayamt,repaydate,paymentamt,factloandays,payimes,interesttotalamt,payacctno,dzcacctno from buloanrepaytp where txnno=?";
		List<Object> params=new LinkedList<Object>();
		params.add(txnNo);
		try {
			List list=this.openSQL(sql, params);
			if(list.size()>0){
				Map map=(Map)list.get(0);
				loanRepayInfo.setBizNo((String)map.get("bizno"));
				loanRepayInfo.setPrimaryBizNo((String)map.get("primarybizno"));
				loanRepayInfo.setLoanCur((String)map.get("loancur"));
				loanRepayInfo.setFactIntAmt(Double.parseDouble((String)map.get("preintamt")==null?"0":(String)map.get("preintamt")));
				loanRepayInfo.setRepayAmt(Double.parseDouble((String)map.get("repayamt")==null ?"0":(String)map.get("repayamt")));
				loanRepayInfo.setRepayDate(GeneralCalc.strToSQLDate(this.fromdate((String)map.get("repaydate"))));
				loanRepayInfo.setPayacctno((String)map.get("payacctno"));
				loanRepayInfo.setDzcacctno((String)map.get("dzcacctno"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loanRepayInfo;
		
	}
	
	 /***
	  * 获得buLoanRepay中的数据
	  */
	public LoanRepayInfo getLoanRepayInfobyBizNo(String BizNo){
		LoanRepayInfo loanRepayInfo=new LoanRepayInfo();
		String sql="select txnno,tradeno,bizno ,primarybizno,loancur ,preintamt,issendmail,repayamt,repaydate,paymentamt,factloandays,payimes,interesttotalamt,,payacctno,dzcacctno from buloanrepaytp where bizno=?";
		List<Object> params=new LinkedList<Object>();
		params.add(BizNo);
		try {
			List list=this.openSQL(sql, params);
			if(list.size()>0){
				Map map=(Map)list.get(0);
				loanRepayInfo.setBizNo((String)map.get("bizno"));
				loanRepayInfo.setPrimaryBizNo((String)map.get("primarybizno"));
				loanRepayInfo.setLoanCur((String)map.get("loancur"));
				loanRepayInfo.setFactIntAmt(Double.parseDouble((String)map.get("preintamt")==null?"0":(String)map.get("preintamt")));
				loanRepayInfo.setRepayAmt(Double.parseDouble((String)map.get("repayamt")==null ?"0":(String)map.get("repayamt")));
				loanRepayInfo.setRepayDate(GeneralCalc.strToSQLDate(this.fromdate((String)map.get("repaydate"))));
				loanRepayInfo.setPayacctno((String)map.get("payacctno"));
				loanRepayInfo.setDzcacctno((String)map.get("dzcacctno"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loanRepayInfo;
		
	}
	
	
	 /***
	  * 获得PAACCTFO中的数据
	  */
	public List getOrgAcctInfos(String orgNO,String acctype ){
		List returnlist=new ArrayList();
		
		String sql="select acctno,cursign,subject,custbankno,accttypeno from paaccfo where acctorgno=? and accttypeno=?";
		List<Object> params=new LinkedList<Object>();
		params.add(orgNO);
		params.add(acctype);
		try {
			List list=this.openSQL(sql, params);
				for(Iterator it=list.iterator();it.hasNext();){
				Map map=(Map)it.next();
				OrgAcct orgAcctInfo=new OrgAcct();
				orgAcctInfo.setAcctNo((String)map.get("acctno"));
				orgAcctInfo.setCurSign((String)map.get("cursign"));
				orgAcctInfo.setAcctObject((String)map.get("subject"));
				orgAcctInfo.setCustBankNo((String)map.get("custbankno"));
				orgAcctInfo.setAcctTypeNo((String)map.get("accttypeno"));
				returnlist.add(orgAcctInfo);
				}
			
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		return returnlist;
		
	}
	
	 /***
	  * 获得buloans中的数据
	  */
	public LoanInfo getLoanInfobyBizno(String Bizno){
		LoanInfo loanInfo=null;
		String sql="select txnno,contractno,debtno,loancur,loanamt,interestdate,maturedate, realrate,loanacctno,ltdact,loanbizno,inttmode,specoutacctno,contiinttmode from buloansfo m where loanbizno=?";
		String balsql="select amount from bubalfo where fieldname in('LCPTRDFINBAL','EXPTRDFINBAL','ICPTRDFINBAL','OCPTRDFINBAL','INVPTRDFINBAL','CREPTRDFINBAL') and bizno=?";
		List<Object> params=new LinkedList<Object>();
		params.add(Bizno);
		try {
			List list=this.openSQL(sql, params);
		
			if(list.size()>0){
				 loanInfo=new LoanInfo();
				Map map=(Map)list.get(0);
				loanInfo.setTxnSerialNo((String)map.get("txnno"));
				loanInfo.setConstractNo((String)map.get("contractno"));
				loanInfo.setDebtNo((String)map.get("debtno"));
				loanInfo.setLoanCur((String)map.get("loancur"));
				loanInfo.setLoanAmount(Double.parseDouble((String)map.get("loanamt")==null?"0":(String)map.get("loanamt")));
				loanInfo.setInterestDate(GeneralCalc.strToSQLDate(this.fromdate((String)map.get("interestdate"))));
				loanInfo.setMatureDate(GeneralCalc.strToSQLDate(this.fromdate((String)map.get("maturedate"))));
				loanInfo.setInterestRate(Double.parseDouble((String)map.get("realrate")));
				loanInfo.setLoanAcctNo((String)map.get("loanacctno"));
				loanInfo.setLtdact((String)map.get("ltdact"));
				loanInfo.setInttMode((String)map.get("inttmode"));
				loanInfo.setLoanAccount((String)map.get("specoutacctno"));
				loanInfo.setIsExt((String)map.get("contiinttmode"));
				
			}
			params=new LinkedList<Object>();
			params.add(Bizno);
			list=this.openSQL(balsql, params);
			if(list.size()>0){
				Map map=(Map)list.get(0);
				loanInfo.setLoanBalanceAmt(Double.parseDouble((String)map.get("amount")==null?"0":(String)map.get("amount")));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loanInfo;
		
	}
	
	 /***
	  * 获得BUFUND中的数据
	  */
	public List getAccountInfoTmpByTxnSerialNo(String txnNo ){
		List returnlist=new ArrayList();
//		OrgAcct orgAcctInfo=new OrgAcct();
		String sql="";
		List<Object> params=new LinkedList<Object>();
		try {
			List list=this.openSQL(sql, params);
			if(list.size()>0){
				Map map=(Map)list.get(0);

			}
			
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		return returnlist;
		
	}
	
	
	
	 /***
	  * 获得IMDFAPPLYFO中的数据
	  */
	public DFApply getDFApply(String txnNo ){
		
		DFApply dfApply=new DFApply();
		String sql="select putoutserialno,affirmdate from imdfapplytp where txnno=? ";
		List<Object> params=new LinkedList<Object>();
		params.add(txnNo);
		try {
			List list=this.openSQL(sql, params);
			if(list.size()>0){
				Map map=(Map)list.get(0);
				dfApply.setAffirmDate(GeneralCalc.strToSQLDate(this.fromdate((String)map.get("affirmdate"))));
				dfApply.setCreditFileNo((String)map.get("putoutserialno"));

			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dfApply;
		
	}
	
	 /***
	  * 获得IMDFAPPLYFO中的数据
	  */
	public DFApply getDFApplybyBizNo(String BizNo ){
		
		DFApply dfApply=new DFApply();
		String sql="select putoutserialno,affirmdate from imdfapplytp where ipno=? ";
		List<Object> params=new LinkedList<Object>();
		params.add(BizNo);
		try {
			List list=this.openSQL(sql, params);
			if(list.size()>0){
				Map map=(Map)list.get(0);
				dfApply.setAffirmDate(GeneralCalc.strToSQLDate(this.fromdate((String)map.get("affirmdate"))));
				dfApply.setCreditFileNo((String)map.get("putoutserialno"));

			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dfApply;
		
	}
	
	  public DFPayment getDFPaymentByTxnSerialNo(String txnNo){
		  DFPayment dfPayment=new DFPayment();
			String sql="select ipno from imdfpaymenttp where txnno=? ";
			List<Object> params=new LinkedList<Object>();
			params.add(txnNo);
			try {
				List list=this.openSQL(sql, params);
				if(list.size()>0){
					Map map=(Map)list.get(0);
					dfPayment.setIpNo((String)map.get("ipno"));

				}
				
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
			return dfPayment;
		  
	  }
	
	
	 /***
	  * 获得TPCREDITINFO中的数据
	  */
	public CreditInfo getCreditInfo(String PutOutSerialNo ){
		
		CreditInfo creditInfo=new CreditInfo();
		String sql="select txnno,bizno,constractno,debtno,putoutserialno ,corpcode,tradecode,lcamt,termdt,tenordays,lctype,lcregistertype,orgno,bailratio,"
				+"depcursign,depamt,depacctno,lginitday,lgtype,tycustname,sgtype,ourno,addamount,amendtenordays,newtermdt,cursign,amt,authdt,acctno,"
				+"intr,duedt,gjintmode,adddays,depamt1, depcursign1, depacctno1,dzcAcctNo,dzctype,loanacctno,duebillNo,overRate,mainProNo,creditorgno,BailContractSerialno,BailContractSerialno1 from tpcreditinfotp where putoutserialno=?";
		List<Object> params=new LinkedList<Object>();	
		params.add(PutOutSerialNo);
		try {
			List list=this.openSQL(sql, params);
			if(list.size()>0){
				Map map=(Map)list.get(0);
				creditInfo.setConstractNo((String)map.get("constractno"));
				creditInfo.setAmt(Double.parseDouble((String)map.get("amt")==null?"0":(String)map.get("amt")));
				creditInfo.setCurSign((String)map.get("cursign"));
				creditInfo.setOutAcctSerialNo((String)map.get("debtno"));
				creditInfo.setMatureDate(GeneralCalc.strToSQLDate(this.fromdate((String)map.get("duedt"))));
				creditInfo.setYearRate(Double.parseDouble((String)map.get("intr")==null?"0":(String)map.get("intr")));
				creditInfo.setInAcctNo((String)map.get("acctno"));
				creditInfo.setOutAcctSerialNo((String)map.get("putoutserialno"));
				creditInfo.setLoanAcctNo((String)map.get("loanacctno"));
				creditInfo.setDuebillNo((String)map.get("duebillNo"));
				creditInfo.setOverRate(Double.parseDouble((String)map.get("overRate")==null?"0":(String)map.get("overRate")));
				creditInfo.setMainProNo((String)map.get("mainProNo"));
				creditInfo.setDzcAcctNo((String)map.get("dzcAcctNo"));
				creditInfo.setDzctype((String)map.get("dzctype"));
				creditInfo.setCreditorgNo((String)map.get("creditorgno"));
				creditInfo.setBailAccount((String)map.get("BailContractSerialno"));
				creditInfo.setBailAccount((String)map.get("BailContractSerialno1"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return creditInfo;
		
	}
	
	 /***
	  * 获得TPCREDITINFO中的数据
	  */
	public CreditInfo getCreditInfoAR(String debtno ){
		
		CreditInfo creditInfo=new CreditInfo();
		String sql="select txnno,bizno,constractno,debtno,putoutserialno ,corpcode,tradecode,lcamt,termdt,tenordays,lctype,lcregistertype,orgno,bailratio,"
				+"depcursign,depamt,depacctno,lginitday,lgtype,tycustname,sgtype,ourno,addamount,amendtenordays,newtermdt,cursign,amt,authdt,acctno,"
				+"intr,duedt,gjintmode,adddays,depamt1, depcursign1, depacctno1,dzcAcctNo,dzctype,loanacctno,duebillNo,overRate,mainProNo,creditorgno,BailContractSerialno,BailContractSerialno1 from tpcreditinfoar where debtno=?";
		List<Object> params=new LinkedList<Object>();	
		params.add(debtno);
		try {
			List list=this.openSQL(sql, params);
			if(list.size()>0){
				Map map=(Map)list.get(0);
				creditInfo.setConstractNo((String)map.get("constractno"));
				creditInfo.setAmt(Double.parseDouble((String)map.get("amt")==null?"0":(String)map.get("amt")));
				creditInfo.setCurSign((String)map.get("cursign"));
				creditInfo.setOutAcctSerialNo((String)map.get("debtno"));
				creditInfo.setMatureDate(GeneralCalc.strToSQLDate(this.fromdate((String)map.get("duedt"))));
				creditInfo.setYearRate(Double.parseDouble((String)map.get("intr")==null?"0":(String)map.get("intr")));
				creditInfo.setInAcctNo((String)map.get("acctno"));
				creditInfo.setOutAcctSerialNo((String)map.get("putoutserialno"));
				creditInfo.setLoanAcctNo((String)map.get("loanacctno"));
				creditInfo.setDuebillNo((String)map.get("duebillNo"));
				creditInfo.setOverRate(Double.parseDouble((String)map.get("overRate")==null?"0":(String)map.get("overRate")));
				creditInfo.setMainProNo((String)map.get("mainProNo"));
				creditInfo.setDzcAcctNo((String)map.get("dzcAcctNo"));
				creditInfo.setDzctype((String)map.get("dzctype"));
				creditInfo.setCreditorgNo((String)map.get("creditorgno"));
				creditInfo.setBailAccount((String)map.get("BailContractSerialno"));
				creditInfo.setBailAccount((String)map.get("BailContractSerialno1"));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return creditInfo;
		
	}
	
	
	 /***
	  * 获得DFApply中的数据
	  */
	public String getProductCode(String fileNo,String tradeNo ){
		String ProductCode="";
		String sql="select mainProNo from tpcreditinfotp where putoutserialno=? and tradecode=?";
		List<Object> params=new LinkedList<Object>();
		params.add(fileNo);
		params.add(tradeNo);
		try {
			List list=this.openSQL(sql, params);
			if(list.size()>0){
				Map map=(Map)list.get(0);
				ProductCode=(String)map.get("mainProNo");

			}
			
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		return ProductCode;
		
	}

	public  String getServion(String txnno) throws Exception{
		String str="001";
		try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			try {
				EbpDao dao = new EbpDao();
				String sql="select versionno from butxnctxtp where state=0 and txnno=?  ";
				List<Object> params=new LinkedList<Object>();
				params.add(txnno);
				List list=dao.queryBySql(sql, null, params);
				if(list.size()>0){
				Map map=(Map)list.get(0);
				str=(String) map.get("versionno");
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




	/**
	 * 获得数据库中的数据公共方法
	 * 
	 */
	
	public  List openSQL(String sql,List<Object> params)
			throws ApplicationException {
		List list=new ArrayList();;
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
	
    public void createAcctListTmp(AcctList acctList) throws Exception {
    	double amt=0.00;
		String ourno="";
		SerialNoFactory serialNoFactory =  new SerialNoFactory();
		String serialNo = serialNoFactory.getSerialNo("bunewtask",10);	
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		String ymd = Integer.toString(year * 10000 + month * 100 + day);
	    String newtaskno="AC"+ymd+serialNo;
	    System.out.println();
	    Date date=null;
	    date=CommonUtil.formatDate(CommonUtil.getSysFld("workDate"));
	    //date= CommonUtil.formatDate("2014-07-21");
	    
    	List<Object> params=new LinkedList<Object>();	
    	String sql="insert into buaccvoutp (accvouno, indexno, accvouname, txnno, bizno, dcflag, ccy, amount, valuedate, " +
    			"acctno, acctnoname, orgno, subject," +
    			"versionno, accttypeno, state, hxserialno,buyprice) "+
         " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
    	params.add(newtaskno);
    	params.add(acctList.getGroupNo());
    	params.add("credit");
    	params.add(acctList.getTxnSerialNo());
    	params.add(acctList.getCurrentBizNo());
    	params.add(acctList.getDCFlag());
    	params.add(acctList.getCurSign());
    	params.add(acctList.getAmount());
    	params.add(date);
    	params.add(acctList.getAcctNo());
    	params.add(acctList.getAcctTypeName());
    	params.add(acctList.getAcctOrgNo());
    	params.add(acctList.getAcctSubject());
    	params.add(getServion(acctList.getTxnSerialNo()));
    	params.add(acctList.getAcctTypeNo());
    	params.add("0");
    	params.add(acctList.getCoreSerNo());
    	params.add("1");
    	
    	
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
    
    public void updateLoaninfo(LoanInfo loaninfo) throws ApplicationException  {
    	List<Object> params=new LinkedList<Object>();	
    	String sql="update buloanstp set specoutacctno=? where txnno=?";
    	System.out.println("更正贷款账号"+sql+";"+loaninfo.getLoanAccount()+";"+loaninfo.getTxnSerialNo());
    	params.add(loaninfo.getLoanAccount());
    	params.add(loaninfo.getTxnSerialNo());
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
    
    public void updatebailinfo(String amt,String lcno) throws ApplicationException  {
    	List<Object> params=new LinkedList<Object>();	
    	String sql="update buloansfo set contiinttmode =? where loanbizno = ?";
    	params.add(amt);
    	params.add(lcno);
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
    
    public void updatecreditInfo(CreditInfo creditInfo)throws ApplicationException {
    	List<Object> params=new LinkedList<Object>();	
    	String sql="update tpcreditinfotp set loanacctno = ? where putoutserialno =? ";
    	params.add(creditInfo.getLoanAcctNo());
    	params.add(creditInfo.getOutAcctSerialNo());
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
    
    public User getUserInfo(String userId)throws ApplicationException {
    	List<Object> params=new LinkedList<Object>();	
    	User user=new User();
    	String sql="select userid, usercode, username, password, belongorgno, userlevel, userstate, lastlogindate, lastmodifypwddate, logintimes, usersex, userphone, localname, maincorpno from pausr " +
    			" where userid = ? and userstate='0'";
    	params.add(userId);
    	try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			
			try {
				EbpDao dao = new EbpDao();
				List list=dao.queryBySql(sql, null, params);
				if(list.size()>0){
				Map map=(Map)list.get(0);
				user.setUserid((String)map.get("userid"));
				user.setUsercode((String)map.get("usercode"));
				user.setMaincorpno((String)map.get("maincorpno"));
				user.setUsername((String)map.get("username"));
				user.setBelongorgno((String)map.get("belongorgno"));
				
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
    	return user;
    }
	
    
	
	public static void main(String[] args) {
		DaoUtils dbutils=new DaoUtils();
		LoanInfo loaninfoU = new LoanInfo();
		loaninfoU = dbutils.getLoanInfo("2014072800021059");
		loaninfoU.setLoanAccount("81600000901431000025");
		try {
			dbutils.updateLoaninfo(loaninfoU);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
