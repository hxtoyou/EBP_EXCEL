package com.ebills.product.dg.credit.send;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.ebills.util.EbillsException;
import com.ebills.util.db.ConnectionManager;
import com.ebills.util.db.ConnectionManagerFactory;
import com.ebills.product.component.BalanceInfo;
import com.ebills.product.dg.commons.Commons;
import com.ebills.product.dg.credit.dao.BatchDao;
import com.ebills.utils.CommonUtil;
import com.ebills.utils.EbpDao;

public class LcOutAcct {

	/**
	 * 查询所有的信用证开立信息
	 */
	public List getLcList(){
		List<Map<String, Object>> result=null;
		try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			try {
				EbpDao dao = new EbpDao();
				StringBuffer sql = new StringBuffer();
				List<Object> inputList=new LinkedList<Object>();
				sql.append("select t.feeno,");
				sql.append("t.lcno,");
				sql.append("t.lccursign,");
				sql.append("t.lcamt,");
				sql.append("(select orgcode from paorg where orgno = b.tranorgno) as tranorgno,");
				sql.append("to_char(b.trandate,'yyyyMMdd') as trandate, ");
				sql.append("t.draftdays,");
				sql.append("t.isusance,");
				sql.append("to_char(t.lcissuedate,'yyyyMMdd') as lcissuedate,");
				sql.append("t.abtimes,");
				sql.append("c.amdtimes,");
				sql.append("to_char(pbc.canceldate,'yyyyMMdd') as canceldate,");
				sql.append("to_char(t.expirydate,'yyyyMMdd') as expirydate ");
				sql.append("from imlcissuefo t ");
				sql.append("left join butxnar b ");
				sql.append("on t.txnno = b.txnno ");
				sql.append("left join  IMLCAMENDfo c ");
				sql.append("on t.lcno=c.lcno ");
				sql.append("left join pbstafo pb ");
				sql.append("on t.lcno=pb.bizno ");
				sql.append("left join pbclosefo pbc ");
				sql.append("on t.lcno=pbc.bizno ");
				sql.append("where pb.isclose<>'Y'");
				StringBuffer item = new StringBuffer();
				//LCBAL 信用证余额	
				item.append("feeno,lcno,lccursign,lcamt,tranorgno, trandate, draftdays,isusance,lcissuedate,abtimes,amdtimes,expirydate,canceldate");
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
	 *查询提货担保信息
	 **/
	public List getTHDBList(){
		List<Map<String, Object>> result=null;
		try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			try {
				EbpDao dao = new EbpDao();
				StringBuffer sql = new StringBuffer();
				List<Object> inputList=new LinkedList<Object>();
				sql.append("select t.ladbillno,t.feeno,t.lcno,t.asscursign,t.assamt,(select orgcode from paorg where ");
				sql.append("orgno=b.tranorgno)as tranorgno,to_char(t.assdate,'yyyyMMdd') as assdate,to_char(t.duedate,'yyyyMMdd') as duedate,to_char(b.trandate,'yyyyMMdd') as trandate,to_char(pb.canceldate,'yyyyMMdd') as canceldate " +
						" from imthdbfo t left join butxnar b on t.txnno=b.txnno left join pbclosefo pb on t.txnno=pb.txnno");
				StringBuffer item = new StringBuffer();
				//LCBAL 信用证余额	
				item.append("ladbillno,feeno,lcno,asscursign,assamt,tranorgno, assdate, duedate,trandate");
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
	
	public void saveOutAcct() throws EbillsException{
		List<Map<String, Object>> lgList=this.getLcList();//所有保函开立信息
		if(lgList!=null&&lgList.size()>0){
			for(Map<String,Object> lc:lgList){
				//信用证号码
				String lcNo=(String)lc.get("lcno");
				//手续费 开立+邮费
			    double total =0;
			    //根据信用证号码获取开立和邮费
				List<Map<String,Object>> feeList=this.getChargeFee(lcNo);
				if(feeList!=null&&feeList.size()>0){
			        //第一条记录的币种用于不币种折算
					String curF=(String) feeList.get(0).get("fcur");
					for(Map<String,Object> fee:feeList){
						String cur=(String)fee.get("fcur");//币种
						double famt=(Double)fee.get("famt");
						if(curF.equals(cur)){
							total=total+famt;
						}else{
							
						}
					}
				}
				
				double balance=BalanceInfo.getBalance(lcNo, "LCBAL");
				Map<String,Object> param=new HashMap<String,Object>();
				SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
				String workDate=format.format(Commons.getWorkDate());
			    param.put("systemdate",workDate);
			    param.put("txnno",null);
			    param.put("debtno",lc.get("feeno"));
			    param.put("lcno",lc.get("lcno"));
			    param.put("cur",lc.get("lccursign"));
			    param.put("amt",lc.get("lcamt"));
			    param.put("balance",balance);
			    param.put("state","1");
			    param.put("tradedate",lc.get("trandate"));
			    if(lc.get("canceldate")!=null){
			    	 param.put("state","8");
			    	 param.put("tradedate",lc.get("canceldate"));
			    }
			    param.put("org",lc.get("tranorgno"));
			    param.put("lctyp",lc.get(""));
			    param.put("startdate",lc.get("lcissuedate"));
			    param.put("enddate",lc.get("expirydate"));
			    param.put("feecur",lc.get(""));
			    param.put("feeamt",lc.get(""));
			    param.put("abtimes",lc.get("abtimes"));
			    param.put("amdtimes",lc.get("amdtimes"));
                BatchDao dao=new BatchDao();
                dao.saveAcct(param);
			}
		}
	}
	
	public void saveOutTHDBAcc() throws EbillsException{
		List<Map<String, Object>> thdbList=this.getTHDBList();//所有提货担保信息
		if(thdbList!=null&&thdbList.size()>0){
			for(Map<String,Object> thdb:thdbList){
				String thdbNo=(String)thdb.get("ladbillno");
				double balance=BalanceInfo.getBalance(thdbNo, "LCBAL");
				Map<String,Object> param=new HashMap<String,Object>();
				SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
				String workDate=format.format(Commons.getWorkDate());
			    param.put("systemdate",workDate);
			    param.put("txnno",null);
			    param.put("debtno",thdb.get("feeno"));
			    param.put("lcno",thdb.get("lcno"));
			    param.put("cur",thdb.get("asscursign"));
			    param.put("amt",thdb.get("assamt"));
			    param.put("balance",balance);
			    param.put("state","1");
			    param.put("tradedate",thdb.get("trandate"));
			    if(thdb.get("canceldate")!=null){
			    	 param.put("state","9");
			    	 param.put("tradedate",thdb.get("canceldate"));
			    }
			    param.put("org",thdb.get("tranorgno"));
			    param.put("lctyp",thdb.get(""));
			    param.put("startdate",thdb.get("assdate"));
			    param.put("enddate",thdb.get("duedate"));
			    param.put("feecur",null);
			    param.put("feeamt",null);
			    param.put("abtimes",null);
			    param.put("amdtimes",null);
                BatchDao dao=new BatchDao();
                dao.saveAcct(param);
			}
		}
	}
   
	/**
	 *获取信用证开立的手续费 
	 **/
	public List<Map<String,Object>> getChargeFee(String lcno){
		List<Map<String, Object>> result=null;
		try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			try {
				EbpDao dao = new EbpDao();
				StringBuffer sql = new StringBuffer();
				List<Object> inputList=new LinkedList<Object>();
				sql.append("select bizno,fcur,famt from buchargefo ");
				sql.append("where ttypeno in('FD00000002','FB00000016') and bizno=?");
				StringBuffer item = new StringBuffer();
				inputList.add(lcno);
				//LCBAL 信用证余额	
				item.append("bizno,fcur,famt");
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
