package com.ebills.product.dg.credit.dao;

import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ebills.util.EbillsException;
import com.ebills.util.db.ConnectionManager;
import com.ebills.util.db.ConnectionManagerFactory;
import com.ebills.product.dg.commons.Commons;
import com.ebills.utils.EbpDao;

public class BatchDao {

	private final Log log = LogFactory.getLog(getClass());
	private static String className = BatchDao.class.getName();
	/**
	 *保存保函开立数据 
	 * @throws EbillsException 
	 * */
	public void saveLgData(Map<String,Object> param)  {
		try {
			ConnectionManager cm = ConnectionManagerFactory
					.getConnectionManager();
			cm.startTransaction(true);
			try {
				EbpDao gjjsDao = new EbpDao();
				List<Object> params = new LinkedList<Object>();
			    params.add(param.get("debtno"));
			    params.add(param.get("cur"));
			    params.add(param.get("appname"));
			    params.add(param.get("amt"));
			    params.add(param.get("startdate"));
			    params.add(param.get("enddate"));
			    params.add(param.get("cleardate"));
			    params.add(param.get("state"));
			    params.add(param.get("org"));
				String insql="insert into "+"TPLGDATA"+
						"(debtno,cur,appname,amt,startdate,enddate,cleardate"+
	                      ",state,org" +
							") values(?,?,?,?,to_date(?,'yyyyMMdd'),to_date(?,'yyyyMMdd'),to_date(?,'yyyyMMdd'),?,?)";
				gjjsDao.execute(insql, params);
			} catch (Exception e) {
				cm.setRollbackOnly();
			} finally {
				cm.commit();
			}
		} catch (EbillsException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 *保存台账信息 
	 * @throws EbillsException 
	 * */
	public void saveAcct(Map<String, Object> param) {
		try {
			ConnectionManager cm = ConnectionManagerFactory
					.getConnectionManager();
			cm.startTransaction(true);
			try {
				EbpDao gjjsDao = new EbpDao();
				List<Object> params = new LinkedList<Object>();
				params.add(param.get("systemdate"));
				params.add(param.get("txnno"));
				params.add(param.get("debtno"));
				params.add(param.get("lcno"));
				params.add(param.get("cur"));
				params.add(param.get("amt"));
				params.add(param.get("balance"));
				params.add(param.get("state"));
				params.add(param.get("org"));
				params.add(param.get("tradedate"));
				params.add(param.get("lctyp"));
				params.add(param.get("startdate"));
				params.add(param.get("enddate"));
				params.add(param.get("feecur"));
				params.add(param.get("feeamt"));
				params.add(param.get("abtimes"));
				params.add(param.get("amdtimes"));
				params.add(param.get("cancledate"));
				String insql = "insert into "
						+ "TPOUTACCT"
						+ "(systemdate,txnno,debtno,lcno,cur,amt,balance"
						+ ",state,org,tradedate,lctyp,startdate,enddate,feecur,"
						+ "feeamt,abtimes,amdtimes,cancledate "
						+ ") values(to_date(?,'yyyyMMdd'),?,?,?,?,?,?,?,?,to_date(?,'yyyyMMdd'),?,to_date(?,'yyyyMMdd'),to_date(?,'yyyyMMdd'),?,?,?,?,to_date(?,'yyyyMMdd'))";

				gjjsDao.execute(insql, params);
			} catch (Exception e) {
				cm.setRollbackOnly();
			} finally {
				cm.commit();
			}
		} catch (EbillsException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 *保存其他明细 
	 * @throws EbillsException 
	 * */
	public void saveOtherDtls(Map<String,Object> param) {
		try {
			ConnectionManager cm = ConnectionManagerFactory
					.getConnectionManager();
			cm.startTransaction(true);
			try {
				EbpDao gjjsDao = new EbpDao();
				List<Object> params = new LinkedList<Object>();
			    params.add(param.get("serno"));
			    params.add(param.get("billno"));
			    params.add(param.get("detailtyp"));
			    params.add(param.get("detailno"));
			    params.add(param.get("detaildate"));
			    params.add(param.get("paydate"));
			    params.add(param.get("detailcurtyp"));
			    params.add(param.get("detailamount"));
			    params.add(param.get("detailremarks"));
			    params.add(param.get("otheramount"));
				String insql="insert into "+"TPOUTOTHERDTLS"+
						"(serno,billno,detailtyp,detailno,detaildate,paydate,detailcurtyp"+
	                      ",detailamount,detailremarks,otheramount" +
							") values(?,?,?,?,to_date(?,'yyyyMMdd'),to_date(?,'yyyyMMdd'),?,?,?,?)";
				gjjsDao.execute(insql, params);
			} catch (Exception e) {
				cm.setRollbackOnly();
			} finally {
				cm.commit();
			}
		} catch (EbillsException e1) {
			e1.printStackTrace();
		}
	}
	/**
	 *保存交易明细 
	 * @throws EbillsException 
	 * */
	public void saveTradeDtls(Map<String,Object> param)  {
		try {
			ConnectionManager cm = ConnectionManagerFactory
					.getConnectionManager();
			cm.startTransaction(true);
			try {
				EbpDao gjjsDao = new EbpDao();
				List<Object> params = new LinkedList<Object>();
			    params.add(param.get("systemdate"));
			    params.add(param.get("txnno"));
			    params.add(param.get("debtno"));
			    params.add(param.get("tradedate"));
			    params.add(param.get("cur"));
			    params.add(param.get("amt"));
			    params.add(Commons.getOrgCodeInfo((String)param.get("org")));
			    params.add(param.get("tradedetail"));
				String insql="insert into "+"TPOUTTRADEDTLS"+
						"(systemdate,txnno,debtno,tradedate,cur,amt,org"+
	                      ",tradedetail" +
							") values(to_date(?,'yyyyMMdd'),?,?,to_date(?,'yyyyMMdd'),?,?,?,?)";
				gjjsDao.execute(insql, params);
			} catch (Exception e) {
				cm.setRollbackOnly();
			} finally {
				cm.commit();
			}
		} catch (EbillsException e1) {
			e1.printStackTrace();
		}
	}
	public static void main(String[] args) {
		BatchDao dao=new BatchDao();
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("systemdate",Commons.getWorkDate());
		param.put("txnno","123412342143124");
		param.put("debtno","debtno1231234124");
		param.put("lcno","lcklajdlfaj1341");
		param.put("cur","CNY");
		param.put("amt",12341.23);
		param.put("balance",1234.12);
		param.put("state","1");
		param.put("org","1000000000");
		param.put("tradedate",Commons.getWorkDate());
		param.put("lctyp","Y");
		param.put("startdate",Commons.getWorkDate());
		param.put("enddate",Commons.getWorkDate());
		param.put("feecur","CNY");
		param.put("feeamt",123.23);
		param.put("abtimes",1);
		param.put("amdtimes",3);
		param.put("cancledate",Commons.getWorkDate());
	    dao.saveAcct(param);
	}
}
