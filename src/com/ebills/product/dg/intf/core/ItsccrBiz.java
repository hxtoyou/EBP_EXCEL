package com.ebills.product.dg.intf.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.bussprocess.context.Context;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ebills.util.EbillsException;
import com.ebills.util.db.ConnectionManager;
import com.ebills.util.db.ConnectionManagerFactory;
import com.ebills.product.dg.credit.dao.SendCreditNoticeDao;
import com.ebills.product.dg.intf.IErrorCode;
import com.ebills.intf.spi.InterfaceManager;
import com.ebills.utils.EbpDao;

public class ItsccrBiz {
  private final Log log = LogFactory.getLog(getClass());
  public void saveItsccr(Context context){
	  EbpDao gjjsDao = new EbpDao();
	  String custno=(String) context.get("BULOANS_LOANAPPNO");//客户编号
	  String accono=(String) context.get("BULOANS_LOANACCTNO");//存款账号	
	  String cmitno=(String) context.get("BULOANS_CONTRACTNO");//贸易合同号码	
	  String torfno=(String) context.get("txnNo");//交易流水	
	  String dbitno=(String) context.get("BULOANS_DEBTNO");//贷款拮据号码	
	  String cucyno=(String) context.get("BULOANS_LOANCUR");//币别	
	  String tramts=(String) context.get("BULOANS_LOANAMT");//金额
	//  String remark=(String) context.get("");//摘要
	  //String latype=(String) context.get("BULOANS_INTTMODE");//贷款类型	
	  String lnrate=(String) context.get("BULOANS_REALRATE");//年利率
	  String mudate=(String) context.get("BULOANS_MATUREDATE");//到期日
	 // String inrcyc=(String) context.get("");//计息周期
	  String retnfs=(String) context.get("BULOANS_INTTMODE");//还款方式
	  String fnpdid=(String) context.get("BULOANS_LOANPRD");//产品代码
	  String tradeNo=(String)context.get("butxn_tradeNo");
	  String bizno=(String) context.get("butxn_curtBizNo");
	  //String sql="insert into"
	  List<Object> params=new LinkedList<Object>();
	  params.add(torfno);
	  params.add(bizno);
	  List<Object> paramList  = new ArrayList<Object>();
	  String sql="select maincorpno from  pacrp where CORPNO=?";
	  paramList.add(custno);
      List<Map<String, Object>> queryRet;
	  try {
		queryRet = gjjsDao.sqlQuery(sql, paramList);
	    if(queryRet.size()>0){
	       params.add((String)queryRet.get(0).get("MAINCORPNO"));  
	     }else{
	       params.add(null);  
	     }
	  } catch (EbillsException e3) {
		  params.add(null);
	  }

	  params.add(accono);
	  params.add(cmitno);
	  params.add(torfno);
	  params.add(dbitno);
	//  params.add(cucyno);
	  List<Object> paramCur  = new ArrayList<Object>();
	  String cursql="select STANDARDCODE from  PACCY where CURSIGN=?";
	  paramCur.add(cucyno);
      List<Map<String, Object>> curResult;
	  try {
		  curResult = gjjsDao.sqlQuery(cursql, paramCur);
	    if(curResult.size()>0){
	       params.add((String)curResult.get(0).get("STANDARDCODE"));  
	     }else{
	       params.add(null);  
	     }
	  } catch (EbillsException e3) {
		  params.add(null);
	  }
	  if(tramts!=null&&!"".equals(tramts)){
	     if(Double.valueOf(tramts)==0){
		    params.add(null);
	     }else{
		    params.add(Double.valueOf(tramts));
	     }
	  }else{
		  params.add(null);
	  }
	  params.add(null);
	  params.add(parseLatType(fnpdid));
	  if(lnrate!=null&&!"".equals(lnrate)){
		  if(Double.valueOf(lnrate)==0){
				params.add(null);
			  }else{
				params.add(Double.valueOf(lnrate));
			  }
	  }else{
		  params.add(null);
	  }

	  if(mudate!=null){
		  try {
				params.add(new SimpleDateFormat("yyyy-MM-dd").parse(mudate));
			  } catch (ParseException e2) {
				params.add(null);
			  }
	  }else{
		  params.add(null);
	  }
	  params.add(null);
	  if(("1".equals(retnfs))){
		  params.add("D"); 
	  }else if("2".equals(retnfs)){
		  params.add("A");
	  }else if("2".equals(retnfs)){
		  params.add("F");
	  }else{
		  params.add(null); 
	  }

	  params.add(fnpdid);
	  params.add(tradeNo);
		try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			try {
				log.debug("参数"+params);
				String insql="insert into "+"TPITSCCR"+"TP"+
						"(txnNo,BIZNO,custno,accono,cmitno,torfno,dbitno,cucyno,tramts,remark,latype,lnrate,mudate,inrcyc,retnfs,fnpdid,"
								+"tradeno) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				gjjsDao.execute(insql,params);
			} catch (Exception e) {
				cm.setRollbackOnly();
				e.printStackTrace();
			}finally{
				cm.commit();
			}
			
		} catch (EbillsException e1) {
			e1.printStackTrace();
		}
	}
  
   public String parseLatType(String fnpdid){
		String result = "";
		if("10001199".equals(fnpdid)) {//信用证项下出口押汇
			result="";
		} else if("10001200".equals(fnpdid)) {//托收项下出口押汇
			result="200";
		}else if("10001201".equals(fnpdid)){//信用证项下进口押汇
			result="201";
		}else if("10001202".equals(fnpdid)){//代收项下进口押汇
			result="202";
		}else if("10001203".equals(fnpdid)){//出口议付（即期）
			result="203";
		}else if("10001204".equals(fnpdid)){//出口议付（远期）
			result="204";
		}else if("10001205".equals(fnpdid)){//远期信用证项下汇票贴现
			result="205";
		}else if("10001206".equals(fnpdid)){//延期信用证项下应收款买入
			result="206";
		}else if("10001207".equals(fnpdid)){//出口商业发票融资
			result="207";
		}else if("10001209".equals(fnpdid)){//短期出口信用保险项下贷款
			
		}else if("10001210".equals(fnpdid)){//国内代付
			
		}else if("10001211".equals(fnpdid)){//海外代付
			
		}else if("10001214".equals(fnpdid)){//打包贷款
			result="214";
		}
		return result;
   }
   
   public void callItsccr(Context context) throws Exception{
	   ItsccrDao dao=new ItsccrDao();
	   List<Map<String,Object>> list=null;
	   list=dao.getAllItsccr();
      if(list!=null&&list.size()>0){ 
   		for(Map<String, Object> map:list){
   			if(map.get("txnno")!=null){
   				context.put("txnno", (String)map.get("txnno"));
   			}
   			if(map.get("custno")!=null){
   				context.put("custno",(String)map.get("custno"));
   			}
   			if(map.get("accono")!=null){
   				context.put("acctno",(String)map.get("accono"));
   			}
   			if(map.get("cmitno")!=null){
   				context.put("loancn",(String)map.get("cmitno"));
   			}
   			if(map.get("torfno")!=null){
   				context.put("torfno", (String)map.get("torfno"));
   			}
   			if(map.get("dbitno")!=null){
   				context.put("lncfno",(String)map.get("dbitno"));
   			}
   			if(map.get("cucyno")!=null){
   				context.put("crcycd", (String)map.get("cucyno"));
   			}
   			if(map.get("tramts")!=null){
   				context.put("loanam",(String)map.get("tramts"));
   			}
   			if(map.get("remark")!=null){
   				context.put("remark",(String)map.get("remark"));
   			}
   			if(map.get("latype")!=null){
   				context.put("loantp",(String)map.get("latype"));
   			}
   			if(map.get("lnrate")!=null){
   				context.put("cntrir",(String)map.get("lnrate"));
   			}
   			if(map.get("mudate")!=null){
   				context.put("matudt",(String)map.get("mudate"));
   			}
//   			if(map.get("lnrate")!=null){
//   				context.put("intrvl",(String)map.get("lnrate"));
//   			}
   			if(map.get("retnfs")!=null){
   				context.put("retnfs",(String)map.get("retnfs"));
   			}
   			if(map.get("fnpdid")!=null){
   				context.put("fnpdid",(String)map.get("fnpdid"));
   			}
//   			if(map.get("cmsqno")!=null){
//   				context.put("cmsqno",(String)map.get("cmsqno"));
//   			}
   			String txnno=(String)map.get("txnno");
   			log.debug("=================>>>ITSCCR接口执行前<<<=================");
   	    	context=InterfaceManager.execute("ITSCCR", context);
   	    	log.debug("=================>>>ITSCCR接口执行执行后<<<=================");
   	    	Map data=(Map) context.get(InterfaceManager.RESULT_KEY);
   			if (IErrorCode.INTF_SUCCESS.equals((String) data.get("errorCode"))) {
   		    	dao.moveData(txnno);
   			}
   		}
   	}

   }
}
