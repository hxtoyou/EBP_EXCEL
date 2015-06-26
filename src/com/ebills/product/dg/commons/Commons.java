package com.ebills.product.dg.commons;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.ebills.commons.SerialNoFactory;
import com.ebills.product.dg.AcctInterface.AcctUtilDataInfo;
import com.ebills.product.dg.AcctInterface.Currency;
import com.ebills.product.dg.AcctInterface.DgAcctInterface;
import com.ebills.product.dg.AcctInterface.dao.DaoUtils;
import com.ebills.product.dg.AcctInterface.domain.AcctList;
import com.ebills.product.dg.AcctInterface.domain.Corp;
import com.ebills.product.dg.AcctInterface.domain.CreditInfo;
import com.ebills.product.dg.AcctInterface.domain.DFApply;
import com.ebills.product.dg.AcctInterface.domain.DFPayment;
import com.ebills.product.dg.AcctInterface.domain.LoanInfo;
import com.ebills.product.dg.AcctInterface.domain.LoanRepayInfo;
import com.ebills.product.dg.AcctInterface.domain.Transaction;
import com.ebills.product.dg.commons.utils.ApplicationException;
import com.ebills.util.EbillsException;
import com.ebills.util.EbillsLog;
import com.ebills.util.db.ConnectionManager;
import com.ebills.util.db.ConnectionManagerFactory;
import com.ebills.utils.CommonUtil;
import com.ebills.utils.EbpConstants;
import com.ebills.utils.EbpDao;

public class Commons {
	public static String CORP_ACT_TYPE=",ZA00000001,ZA00000002,ZA00000082,ZA00000003,ZA00000083,ZC00000001,ZC00000002";//客户账户类型
	public static String LOAN_TRADE_DEF=",180101,180201,180301,180401,180502,180601,180403";//融资业务交易码
	public static String PAY_LOAN_TRADE_DEF=",180102,180202,180302,180402,180501,180602,180407";//还融资业务交易码
	public static String JSHSUBJECT= ",30212500,30212501,30212502,30213000,30213001,30213500,30213501,30214000,30214001,30214002,30214500,30214501,";
	public static String WHSUBJECT= ",30210501,30210502,30211000,30211001,30211500,30211501,30212000,30212001,";
	public static String SUBJECT=",30212500,30212501,30212502,30213000,30213001,30213500,30213501,30214000,30214001,30214002,30214500,30214501,30210501,30210502,30211000,30211001,30211500,30211501,30212000,30212001,";
	public static String MAY_PAY_LOAN_TRADE=",050621,050205,";//收汇
	final static String PACCYPRI_TABLENAME = "PACCYPRI";
	final static String PACCYPRIAR_TABLENAME = "PACCYPRIAR";
	final static String PAACCFO_TABLENAME = "PAACCFO";
	private static String HanDigiStr[] = new String[] { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };

	private static String HanDiviStr[] = new String[] { "", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "万","拾", "佰", "仟", "亿", "拾", "佰", "仟", "万", "拾", "佰", "仟" };
	private static String className = Commons.class.getName();
	private static DaoUtils dbutils =new DaoUtils();
	private static EbillsLog log = new EbillsLog(className);
	/**
	 * 2014-6-14 LT 
	 * 产生ESB交易流水号，每只交易都有唯一流水号，需要国结系统产生
	 * 产生service_sn,19(ESB编号)=1(固定填1)+2(地市编号 56 )+4(应用系统编号 0015 )+2(保留 00 )+1(冗余,现在用于组合服务中的多次CBOD请求 0 )+9(流水号，第3位固定填写为9)
	 * 1560015000009000001
	 */
	public static String bringSerialNo() throws EbillsException{ 
		String serialNo_sn ="1560015000009";
		SerialNoFactory serialNoFactory =  new SerialNoFactory();
		String serialNo = serialNoFactory.getSerialNo("ESB_SERVICE_SN",6);	
		return serialNo_sn+serialNo;
	}
	
	/**
	 * 2014-06-14 LT
	 * 获取核心机构号
	 * @return
	 * @throws Exception
	 */
	public static String getOrgCodeInfo(String orgNo) throws Exception{	
		String orgCode = "";
		ConnectionManager cm = null;
		try {
			cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);				
			EbpDao gjDao = new EbpDao();	
			if(!StringUtils.isEmpty(orgNo)){
				String getOrgCodeSql = "select ORGCODE from PAORG  WHERE ORGNO='"+ orgNo + "'";
//				System.out.println("sql return ==>"+getOrgCodeSql);				
				List<Map<String,Object>> orgCodeList = gjDao.queryBySql(getOrgCodeSql, null,null);
				if(null != orgCodeList && !orgCodeList.isEmpty() 
						&& orgCodeList != null && orgCodeList.size() > 0){
					Map<String,Object> map =  orgCodeList.get(0);
					orgCode = (String) map.get("ORGCODE");
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
		return orgCode;
	}
	
	/**
	 * 2014-06-14 LT
	 * 获取核心记帐虚拟柜员号
	 * @return
	 * @throws Exception
	 */
	public static String getUserTallyIdInfo(String userId) throws Exception{	
		String userTallyId = "";
		ConnectionManager cm = null;
		try {
			cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);				
			EbpDao gjDao = new EbpDao();	
			if(!StringUtils.isEmpty(userId)){
				String getUserTallyIdSql = "select USERNAME from PAUSR  WHERE USERID="+ Integer.valueOf(userId) + "";			
				List<Map<String,Object>> userTallyIdList = gjDao.queryBySql(getUserTallyIdSql, null,null);
				if(null != userTallyIdList && !userTallyIdList.isEmpty() 
						&& userTallyIdList != null && userTallyIdList.size() > 0){
					Map<String,Object> map =  userTallyIdList.get(0);
					userTallyId = (String) map.get("USERNAME");
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
		return userTallyId;
	}
	
	/**
	 * 2014-06-14 LT
	 * 获取核心记帐虚拟柜员号
	 * @return
	 * @throws Exception
	 */
	public static String getMainUserNo(String userId) throws Exception{	
		String userCode = "";
		ConnectionManager cm = null;
		try {
			cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);				
			EbpDao gjDao = new EbpDao();	
			if(!StringUtils.isEmpty(userId)){
				String getUserTallyIdSql = "select MAINCORPNO from PAUSR  WHERE USERID="+ Integer.valueOf(userId) + "";			
				List<Map<String,Object>> userTallyIdList = gjDao.queryBySql(getUserTallyIdSql, null,null);
				if(null != userTallyIdList && !userTallyIdList.isEmpty() 
						&& userTallyIdList != null && userTallyIdList.size() > 0){
					Map<String,Object> map =  userTallyIdList.get(0);
					userCode = (String) map.get("MAINCORPNO");
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
		return userCode;
	}
	
	/**
	 * 2014-6-14 LT
	 * 专门用于获取<key>.*</key>值
	 */
	public String findStrValue(String key,String bodyStr){
	   	String reg = "<" + key + ">.*</" + key + ">";
			Pattern pattern = Pattern.compile(reg);
			Matcher match = pattern.matcher(bodyStr);
			if (match.find()) {
				String ret= match.group(0);
				//System.out.println(key+":"+ret+" bodyStr:"+bodyStr);
				ret=ret.replaceFirst("<"+key+">","");
				if (ret.length()>2){
					ret = ret.substring(0,ret.indexOf("</" + key + ">"));
				}
				return ret;
			}
			return "";
		}
	/**
	 * 2014-6-14 LT
	 * 专门用于FORM ID信息查询取<FORM ID="SSA10000">字段
	 */
    public String findQBValue(String key,String bodyStr){
    	String reg = "<FORM ID=\""+ key +"\">.*</FORM>";
		Pattern pattern = Pattern.compile(reg);
		Matcher match = pattern.matcher(bodyStr);
		if (match.find()) {
			String ret= match.group(0);
			//System.out.println(key+":"+ret+" bodyStr:"+bodyStr);
			ret=ret.replaceFirst("<FORM ID=\""+ key +"\">","");
			if (ret.length()>2){
				ret = ret.substring(0,ret.indexOf("</FORM>"));
			}
			return ret;
		}
		return "";
	  }
    /**
	 *当日传票套号(6位会计)
	 * 
	 * @param txn
	 * @return
	 */
    public String getCrntNo(String txn) {
		String crntNo = "00000" + txn;
		return crntNo.substring(crntNo.length() - 6);
	}
    /**
	 * 传票内序号转换
	 * 
	 * @param i
	 * @return
	 */
    public String getSqNo(int i) {
		String sqNo = "00" + String.valueOf(i);
		return sqNo.substring(sqNo.length() - 3);
	}
    
    /**
	 * 获取核心借贷标志
	 * 
	 * @param ebillsDCFlag
	 * @return
	 */
    public String getCoreDCFlag(String ebillsDCFlag) {
		if ("R".equals(ebillsDCFlag)) {
			ebillsDCFlag = "D";
		} else if ("P".equals(ebillsDCFlag)) {
			ebillsDCFlag = "C";
		}
		return ebillsDCFlag;
	}
	
    /**
	 * 只别是否客户账
	 * 
	 * @param acctType
	 * @return
	 */
    public boolean isCorpAcct(String acctType) {
		if (acctType == null || "".equals(acctType))
			return false;
		if (CORP_ACT_TYPE.indexOf(acctType) > 0)
			return true;
		return false;
	}
    
    /**
	 * 获得结算方式 1:信用证 2:托收 4:信汇 3:电汇 5:票汇 6:其他
	 * 
	 * @param trans
	 * @return
	 */
    public String getJsMethod(AcctUtilDataInfo trans) {
		String rs = "6";
		String tradeTypeNo = trans == null ? ""
				: (trans.getTradeNo() == null ? "" : trans.getTradeNo());
		if (tradeTypeNo.equals("030221") || tradeTypeNo.equals("030004"))
			rs = "1";
		if ("030219:030220:030222:030223".indexOf(tradeTypeNo) >= 0)
			rs = "2";
		if ("010001:010002:010006".indexOf(tradeTypeNo) >= 0)
			rs = "3";
		if (tradeTypeNo.equals("020010"))
			rs = "1";
		if (tradeTypeNo.equals("020023"))
			rs = "2";
		return rs;
	}
    
    /**
	 * 结售汇取交易类型
	 * 
	 * @param transaction
	 * @return
	 * @throws EbillsException
	 */
    public String getSpfeType(AcctUtilDataInfo transaction) throws EbillsException {
		String transCode = "";
//		DealSpfe dealSpfe = new DealSpfe();
//		dealSpfe = dealSpfeTmpManager.getDealSpfeByTxnSerialNo(transaction
//				.getTxnSerialNo());
//		transCode = dealSpfe.getTxCode();
		return transCode;

	}
	
	/**
	 * 获取申报号码
	 * 
	 * @param trans
	 * @return
	 */
    public String getSbNo(AcctUtilDataInfo trans) {
//		try {
//			return declarePublicManagerLocal.getRptNoByTxnSerialNo(trans
//					.getTxnSerialNo());
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("获取申报号码失败 " + e);
//		}
		return "";
	}
	/**
	 * 获取交易编码
	 */
    public String[] getTradeCode(String rptNo, AcctUtilDataInfo trans) {
		String tranco = "";
		String[] ret = { "", "" };
//		try {
//			if (rptNo != null && rptNo.length() > 0) {
//				String tmp = declarePublicManagerLocal.getTxCodeByRPTNO(rptNo);
//				if (tmp != null) {
//					String[] tmpArry = tmp.split(";");
//					if (tmpArry.length == 2) {
//						if (tmpArry[0] != null && !tmpArry[0].equals("")){
//							tranco = tmpArry[0];
//							ret[0]=tmpArry[0];
//							ret[1]=tmpArry[1];
//						}else{
//							tranco = tmpArry[1] == null ? "" : tmpArry[1];
//							
//						}
//						
//					} else {
//						tranco = tranco.length() > 6 ? tmp.substring(0, 6)
//								: tmp;
//					}
//				}
//			} else {
//				String tmp="";
//					this.declarePublicManagerLocal.getTxCodeByNonRpt(trans.getTxnSerialNo());
//				ret[0]=tmp;
//				tranco=tmp;
//				Country country = countryManager.getCountry(trans
//						.getCountryCode());
//				if (country != null)
//					ret[1] = country.getSAFECode();
//			}
//
//		} catch (Exception e) {
//			tranco = "";
//			e.printStackTrace();
//			System.out.println("获取交易编码错误" + e);
//			log.error("getTradeCode获取交易编码错误:", e);
//		}
//		if (tranco == null || "".equals(tranco))
//			tranco = "121010";//2014年外管国际收支代码更改		将原来的编码101010更改为121010	update by LT 2014-06-12
//		ret[0] = tranco;
//		if (ret[1] == null || "".equals(ret[1]))
//			ret[1] = "Z00";
//		if (EN_NAT_CODE.equals(ret[1]) || NN_NAT_CODE.equals(ret[1])) {
//			ret[1] = "Z00";
//		}
		return ret;
	}
    
    /**
	 * 是否是跨境交易(1-境外,2-境内,3-结售汇)
	 */
    public String isKjTrade(AcctUtilDataInfo trans,String acctNo,String dcFlag) throws EbillsException {
		String tmp = "2";// 默认都是境内
//		try {
//			List spfeList = new ArrayList();
//			spfeList = acctListManager.getSpfeDetailTmpByTxnSerialNo(trans
//					.getTxnSerialNo());
//			String countryNo = trans.getCountryCode();
//			if (countryNo == null || countryNo == "") {
//				countryNo = "156";
//			}
//			if (null!=trans.getMidAcct()&&!"".equals(trans.getMidAcct())){//有过账
//				if (trans.getMidAcct().equals(acctNo)&&"C".equals(dcFlag)){//有过账，且是贷记
//					if (!"156".equals(countryNo)&& !"CHN".equals(countryNo)) {
//						return  "1";
//					} else{
//						return "2";
//					}
//				}else if (trans.getMidAcct().equals(acctNo)&&"D".equals(dcFlag)){//有过账，且是借记
//					if (spfeList != null && spfeList.size() > 0) {//借记有记售汇时，报3
//						return  "3";
//					}else{
//						return "2";
//					}
//				}else if (!trans.getMidAcct().equals(acctNo)&&"C".equals(dcFlag)){//有过账，且是贷记,且账号不是过账账号
//					return "2";
//				}
//			}else{//无过账
//				//纯结售汇应当报3
//				if (",010003,010004,".indexOf(trans.getTradeNo())>0 && spfeList != null && spfeList.size()>0){//张赐发现BUG，修正 2011-01-25
//					return "3";
//				}
//				if (!"156".equals(countryNo)&& !"CHN".equals(countryNo)) {
//					return  "1";
//				} else{
//					return "2";
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return tmp;
	}
    
    /*
	 * 根据交易编码得到贷款产品代码
	 */
    /*
	 * 根据交易编码得到贷款产品代码
	 */
	public String getProductCode(String fileNo,String tradeNo) throws EbillsException
	{
		return dbutils.getProductCode(fileNo, tradeNo);
	}
	/**
	 * 获取货币的记账代号
	 * 
	 * @param cursign
	 * @return
	 * @throws Exception 
	 */
	public static String getCurSignCode(String cursign,String tallyCode){
		String curSignCode = "";
		if ((cursign == null || "".equals(cursign)) 
				&& (tallyCode == null || "".equals(tallyCode)))
			return curSignCode;
		try {			
			Currency cur = Commons.getCurrency(cursign,tallyCode);
			if (cur != null){
				if (!StringUtils.isEmpty(cursign)){
					curSignCode = cur.getTallyCode();
				}
				if (!StringUtils.isEmpty(tallyCode)){
					curSignCode = cur.getCursign();
				}	
			}
			
		} catch (EbillsException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return curSignCode;
	}
	/**
	 * 2014-06-15 LT
	 * 获取币别信息，并保存到新对象中
	 * @return
	 * @throws Exception
	 */
	public static Currency getCurrency(String cursign,String tallyCode) throws Exception{
		Currency currency = new Currency();
		ConnectionManager cm = null;
		try {
			cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);				
			EbpDao gjDao = new EbpDao();			
			if(!StringUtils.isEmpty(cursign)){
				String cursignSql = "select cursign,tallyCode from PACCY WHERE CURSIGN='"+ cursign + "'";
				System.out.println("sql return ==>"+cursignSql);				
				List<Map<String,Object>> cursignList = gjDao.queryBySql(cursignSql, null,null);
				if(null != cursignList && !cursignList.isEmpty() 
						&& cursignList != null && cursignList.size() > 0){					
					for (int i = 0; i < cursignList.size(); i++) {
						Map<String,Object> map =  cursignList.get(i);
						currency.setCursign((String) map.get("cursign"));
						currency.setTallyCode((String) map.get("tallyCode"));						
						//BigDecimal tranAmt = new BigDecimal((String) map.get("TRANAMT"));
					}
				}
			}
			
			if(!StringUtils.isEmpty(tallyCode)){
				String cursignSql = "select cursign,tallyCode from PACCY WHERE STANDARDCODE='"+ tallyCode + "'";
				System.out.println("sql return ==>"+cursignSql);				
				List<Map<String,Object>> cursignList = gjDao.queryBySql(cursignSql, null,null);
				if(null != cursignList && !cursignList.isEmpty() 
						&& cursignList != null && cursignList.size() > 0){					
					for (int i = 0; i < cursignList.size(); i++) {
						Map<String,Object> map =  cursignList.get(i);
						currency.setCursign((String) map.get("cursign"));
						currency.setTallyCode((String) map.get("tallyCode"));						
						//BigDecimal tranAmt = new BigDecimal((String) map.get("TRANAMT"));
					}
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
		return currency;
	}
	
	public static int getChineseStart(String str) {	 
		 int   l=str.length();  
	     int count=0;
		 for(int i=0;i<l;i++)
	     {
			 if(str.charAt(i)<0||str.charAt(i)>255)
				count=count+1;
			 if((str.charAt(i)<0||str.charAt(i)>255) && count==1) 
	    		return i;
	     }
		 return -1;
	   }
	
	public static int getChineseEnd(String str) {
		 int   l=str.length();  
	     int count=0;
	     int spaceFlag=0;
		 for(int i=0;i<l;i++)
	     {
			 if(str.charAt(i)<0||str.charAt(i)>255)
					count=count+1;
			 if((str.charAt(i)<0||str.charAt(i)>255) && (str.charAt(i+1)>=0 && str.charAt(i+1)<=255) && spaceFlag==0) 
			 {
				 spaceFlag=1;
			 }
			 if(spaceFlag==1 && str.substring(i,i+1).equals(" ") && !str.substring(i+1,i+2).equals(" ") && i+2<l )
				 return i;
	     }
		 return -1;
	     
	   }
	
	/**
	 * 2014-06-18 LT
	 * 获取核心客户号信息MAINCORPNO
	 * @return
	 * @throws Exception
	 */
	public static String getMainCorpNo(String corpNo) throws Exception{
		String mainCorpNo = "";
		ConnectionManager cm = null;
		try {
			cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);				
			EbpDao gjDao = new EbpDao();			
			if(!StringUtils.isEmpty(corpNo)){
				String mainCorpNoSql = "select MAINCORPNO from PACRP WHERE CORPNO='"+ corpNo + "'";
//				System.out.println("sql return ==>"+mainCorpNoSql);				
				List<Map<String,Object>> mainCorpNoList = gjDao.queryBySql(mainCorpNoSql, null,null);
				if(null != mainCorpNoList && !mainCorpNoList.isEmpty() 
						&& mainCorpNoList != null && mainCorpNoList.size() > 0){
					Map<String,Object> map =  mainCorpNoList.get(0);												
					mainCorpNo = (String) map.get("MAINCORPNO");
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
		return mainCorpNo;
	}
	
	/*
	 * LT 2014-06-20 专门用于工作流判断是否需要走记帐节点，烟台专用，针对于有记帐分录与融资融资交易需记帐
	 */
	public static boolean isCallAcctIntface(String txnNo) throws Exception{
		boolean flag = false;
		try {
			String tradeNo = "";
			List tradeNoList = new ArrayList(); 
			
			if(!StringUtils.isEmpty(txnNo)){
				tradeNoList = DgAcctInterface.getBuAcctDataInfoList(txnNo);
				tradeNo = Commons.getButxnTradeNo(txnNo);
			}			
			if (Commons.LOAN_TRADE_DEF.indexOf(tradeNo) > -1) {//除去贸易融资发放时自有时无账务的情况
				flag = true;
			}
			if (Commons.PAY_LOAN_TRADE_DEF.indexOf(tradeNo) > -1) {//除去贸易融资还款时自有时无账务的情况
				flag = true;
			}
			if(null != tradeNoList && !tradeNoList.isEmpty() 
					&& tradeNoList != null && tradeNoList.size() > 0){
				flag = true;
			}
			System.out.println("msgVale return ==>"+flag);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return flag;
	}
	/**
	 * 2014-06-14 LT
	 * 获取BUTXNTP.tradeNo
	 * @return
	 * @throws Exception
	 */
	public static String getButxnTradeNo(String txnNo) throws Exception{		
		String tradeNo = "";
		ConnectionManager cm = null;
		try {
			cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);				
			EbpDao gjDao = new EbpDao();			
			if(!StringUtils.isEmpty(txnNo)){
				String tradeNoSql = "select a.TRADENO from BUTXNTP a WHERE TXNNO='"+ txnNo + "'";
				//System.out.println("sql return ==>"+buAcctSql);				
				List<Map<String,Object>> tradeNoList = gjDao.queryBySql(tradeNoSql, null,null);
				if(null != tradeNoList && !tradeNoList.isEmpty() 
						&& tradeNoList != null && tradeNoList.size() > 0){
					Map<String,Object> map =  tradeNoList.get(0);
					tradeNo = (String) map.get("TRADENO");
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
		return tradeNo;
	}
	 /**
     * 数字进行格式化，四舍五入
     * @param inNum
     * @param pLen 保留小数位长度
     * */
    public static double roundFormat(double inNum,int pLen){
    	BigDecimal big=new BigDecimal(inNum);   	
    	big =big.divide(new BigDecimal(1),pLen,BigDecimal.ROUND_HALF_UP);
    	return big.doubleValue();
    }
	

    
	public static void importRate(List<Object> list,String orgNo) throws Exception {
		
			try {
				EbpDao dao = new EbpDao();
				//先根据机构将牌价从PACCYPRI 迁移到PACCYPRIAR
				String sql="insert into "+PACCYPRIAR_TABLENAME+
						"(select * from "+PACCYPRI_TABLENAME+" t where t.orgNo = '"+ orgNo+"')";
				dao.execute(sql,null);
				//在将之前的牌价数据从PACCYPRI删除
				sql= "delete from "+PACCYPRI_TABLENAME + " where ORGNO = '"+orgNo+"'";
				dao.execute(sql,null);
				//将新的牌价数据插入到PACCYPRI表中
				for(int i=0 ;i < list.size(); i++){
					//List<Object> params=new LinkedList<Object>();	
					Map<String,Object> params=new HashMap<String,Object>();	
					Paccypri paccypri =(Paccypri)list.get(i);
						
					    params.put("orgNo",paccypri.getOrgNo());
						params.put("impDate",getTimestamp());//paccypri.getImpDate()
						params.put("curSign",paccypri.getCurSign());
						params.put("buyPrice",paccypri.getBuyPrice());
						params.put("salePrice",paccypri.getSalePrice());
						params.put("middlePrice",paccypri.getMiddlePrice());
						params.put("billbuyPrice",paccypri.getBillBuyPrice());
						
						params.put("billsalePrice",paccypri.getBillSalePrice());
						params.put("innerbuyPrice",paccypri.getInnerbuyPrice());
						params.put("innersalePrice",paccypri.getInnersalePrice());
						params.put("zybuyprice",paccypri.getZyBuyPrice());
						
						params.put("zysalePrice",paccypri.getZySalePrice());
						params.put("basePrice",paccypri.getBasePrice());
						params.put("closingRate",paccypri.getClosingRate());
					log.debug(params.toString());
					//dao.execute(sql,params);
					dao.insertRow("PACCYPRI", "", params);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new ApplicationException(e.getMessage());
			}
		
	}
	
/********************************融资记账接口的公共方法start majiangbao 2014-7-12*******************************************/
    
	// 海外代付
	public DFApply getDFApply(String txnNo) throws Exception {
		return dbutils.getDFApply(txnNo);
	}
	
	// 海外代付
	public DFApply getDFApplybyBizNO(String bizNo) throws Exception {
		return dbutils.getDFApplybyBizNo(bizNo);
	}
	//获得贷款信息
	public LoanInfo getLoanInfo(String txnNo) throws Exception {
		return dbutils.getLoanInfo(txnNo);
	}
	

	//获得信贷信息
	public CreditInfo getCreditInfo(String fileNo) throws Exception {
		return dbutils.getCreditInfo(fileNo);
	}
	

	//获得信贷信息
	public CreditInfo getCreditInfoARbytxnNo(String txnNo) throws Exception {
		CreditInfo creditInfo=new CreditInfo();
		 LoanRepayInfo myinfo=getLoanRepayTmpByTxnSerialNo(txnNo);
		 if(myinfo.getPrimaryBizNo()!=null && !"".equals(myinfo.getPrimaryBizNo()) ){
				LoanInfo loanInfomy = getLoanInfoByBizNo(myinfo.getPrimaryBizNo());
				if(loanInfomy.getDebtNo()!=null && !"".equals(loanInfomy.getDebtNo())){
					creditInfo=dbutils.getCreditInfoAR(loanInfomy.getDebtNo());
				 }
		 }
		 
		return creditInfo;
	}
	// 获取交易对象
	public Transaction getTransaction(String txnSerialNo) throws Exception {
		return  dbutils.getTransaction(txnSerialNo);
	}
	
	public Corp getCorp(String corpNo) throws Exception {
		return dbutils.getCorp(corpNo);
	}
	/**
	 * 据机构编号和帐务类型编号取得帐号信息
	 * @param orgNo 机构编号
	 * @param acctTypeNo 帐务类型编号
	 */
	public List getOrgAcctInfos(String orgNo, String acctTypeNo)
			throws EbillsException {
		return dbutils.getOrgAcctInfos(orgNo, acctTypeNo);
	}	
    /**
     * 根据还款交易流水号获取还款临时信息
     * @param bizNo 还款业务编号
     * @throws EbillsException
     */
    public LoanRepayInfo getLoanRepayTmpByTxnSerialNo(String txnSerialNo) throws EbillsException {
            return dbutils.getLoanRepayInfo(txnSerialNo);
       
    }
    
    /**
     * 根据还款交易流水号获取还款临时信息
     * @param bizNo 还款业务编号
     * @throws EbillsException
     */
    public LoanRepayInfo getLoanRepayInfobyBizno(String bizno) throws EbillsException {
            return dbutils.getLoanRepayInfobyBizNo(bizno);
       
    }
    
    
    /**
     * 根据交易业务编号获取贷款档案信息
     * @param bizNo
     * @return
     */
    public LoanInfo getLoanInfoByBizNo(String bizNo) throws EbillsException {
        
            return dbutils.getLoanInfobyBizno(bizNo);
       
    }
    /**
     * 根据交易业务编号获取贷款档案信息
     * @param bizNo
     * @return
     */
    public DFPayment getDFPaymentByTxnSerialNo(String txnNo) throws EbillsException {
            return dbutils.getDFPaymentByTxnSerialNo(txnNo);
       
    }
    
    public void createAcctListTmp(AcctList acctList) {
      try {
		dbutils.createAcctListTmp(acctList);
	} catch (Exception e) {
		e.printStackTrace();
	}
    	
    }
    
    public void updateLoaninfo(LoanInfo loaninfo) {
    	 try {
    			dbutils.updateLoaninfo(loaninfo);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	
    }
    
    public void updatecreditInfo(CreditInfo creditInfo) {
   	 try {
			dbutils.updatecreditInfo(creditInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    
 /********************************融资记账接口的公共方法end majiangbao 2014-7-12*******************************************/
  
	 
		public static Map importAcctno(List<Object> list,String orgNo) throws Exception {
			String message="";
			int insetflag=0;
			
			int updateflag=0;
			Map flagMap =new HashMap();
				try {
					EbpDao dao = new EbpDao();
					//先根据机构将牌价从PACCYPRI 迁移到PACCYPRIAR
					
					//将新的牌价数据插入到PACCYPRI表中
					for(int i=0 ;i < list.size(); i++){
						Map<String,Object> params=new HashMap<String,Object>();	
						Map<String,Object> paramsValue=new HashMap<String,Object>();
					    List<Map<String, Object>> acctidlist =new ArrayList <Map<String, Object>>();
						Paaccfo paaccfo =(Paaccfo)list.get(i);
						String sql="select ACCTID from "+PAACCFO_TABLENAME+" t where t.ACCTNO = '"+ paaccfo.getAcctno()+"' AND t.CURSIGN = '"+ paaccfo.getCurSign() +"'";
						acctidlist= dao.queryBySql(sql, null,null);
						//params.put("acctId", paaccfo.getAcctid());
						params.put("acctTypeNo",paaccfo.getAccttyPeno());
						params.put("acctNo", paaccfo.getAcctno());
						params.put("curSign", paaccfo.getCurSign());
						params.put("acctOrgNo", paaccfo.getAcctOrgno());
//						params.put("otherCur", paaccfo.getOtherCur());
//						params.put("otherOrgNo", paaccfo.getOtherOrgno());
						params.put("custBankNo", paaccfo.getCustbankNo());
						params.put("acctObject", paaccfo.getAcctObject());
//						params.put("otherAcctno", paaccfo.getOtherAcctno());
//						params.put("otherAcctno", paaccfo.getOtherAcctno());
						params.put("subject", paaccfo.getSubject());
    					params.put("acctDesc", paaccfo.getAcctDesc());
//						params.put("coreAcctName", paaccfo.getCoreAcctName());
      					params.put("orgCode", paaccfo.getOrgCode());
//						params.put("demondFlag", paaccfo.getDemondFlag());
						params.put("inoutFlag", paaccfo.getInoutFlag());
//						params.put("acctNoRule", paaccfo.getAcctnoRule());
						params.put("coreAcctNo", paaccfo.getCoreAcctno());
						
						if(null != acctidlist && !acctidlist.isEmpty()&& acctidlist.size() > 0){
							for (Map<String, Object> paaccfoinfo : acctidlist) {
								String acctid = (String) paaccfoinfo.get("ACCTID");
								params.put("acctId",acctid);
							}
							
								paramsValue.put("acctNo", paaccfo.getAcctno());
								paramsValue.put("curSign", paaccfo.getCurSign());
								dao.updateByDataId("PAACCFO", " ", params, paramsValue);
								updateflag++;
							
						}else{
							  SerialNoFactory snf =  new SerialNoFactory();
							  String serialNo = snf.getSerialNo("PAACCFO");
							  params.put("acctId", serialNo);
							  log.debug(params.toString());
							  dao.insertRow("PAACCFO", " ", params);
							  insetflag++;
						}
					}
				 message ="导入客户账号成功， 新增"+insetflag+"条记录,更新"+updateflag+"条记录,请到客户账户信息查看!";
				 flagMap.put("insetflag",insetflag);
				 flagMap.put("updateflag",updateflag);
				} catch (Exception e) {
					e.printStackTrace();
					throw new ApplicationException(e.getMessage());
				}
				
			return flagMap;
		}
	
    
	public static void main(String[] args) {
		try {
			Commons commons = new Commons();
//			System.out.println("optSendMsg return ==>"+commons.getMainCorpNo("00000649"));
			System.out.println(Commons.LOAN_TRADE_DEF.indexOf("180201"));
			if (Commons.LOAN_TRADE_DEF.indexOf("180101") > -1) {//除去贸易融资发放时自有时无账务的情况
				System.out.println("optSendMsg return ==>");
			}
			//180101,180201
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<Map<String, Object>> getCorpByMainCorpNo(String cust_no) throws EbillsException {
		
		List<Map<String, Object>> list =new ArrayList <Map<String, Object>>();
		try {
			EbpDao dao = new EbpDao();
			String sql = "select corpNo from PACRP where MAINCORPNO ='"+cust_no+"'";
			list=dao.sqlQuery(sql, null);
		} catch (EbillsException e) {		
			e.printStackTrace();
			throw new EbillsException(e,e.getMessage());
		}
		
		return list;
	}

	public static String getNameBySubject(String acctyPeno) {
		
		String acctypeName = "";
		try {
			EbpDao gjDao = new EbpDao();			
			if(!StringUtils.isEmpty(acctyPeno)){
				String acctypeNameSql = "select ACCTTYPECNNAME from PAACCTTYP where ACCTTYPENO='"+ acctyPeno + "'";			
				List<Map<String,Object>> mainCorpNoList = gjDao.queryBySql(acctypeNameSql, null,null);
				if(null != mainCorpNoList && !mainCorpNoList.isEmpty() 
						&& mainCorpNoList != null && mainCorpNoList.size() > 0){
					Map<String,Object> map =  mainCorpNoList.get(0);												
					acctypeName = (String) map.get("ACCTTYPECNNAME");
				}
			}		
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return acctypeName;
	}
	
	public static Date getWorkDate() {
		Date workDate = null;
	   	 try {
	   		String transDateStr = dbutils.fromdateParse(CommonUtil.getSysFld("workDate"));
	   		workDate = java.sql.Date.valueOf(transDateStr);
	   		} catch (Exception e) {
	   			e.printStackTrace();
	   		}
		return workDate;
   }
	public static Timestamp getTimestamp() {
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		String tsStr = "2014-08-04 11:49:45";   
		try {   
			  String tsStr1 = dbutils.fromdateParse(CommonUtil.getSysFld("workDate"));
			  String tsStr2 = new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis())).toString();
			  tsStr = tsStr1 + " " + tsStr2;
		       ts = Timestamp.valueOf(tsStr);   
		       System.out.println(ts);   
		    } catch (Exception e) {   
		     e.printStackTrace();   
		  } 
		return ts;
   }
	
	public static String PositiveIntegerToHanStr(String NumStr) { // 输入字符串必须正整数，只允许前导空格(必须右对齐)，不宜有前导零
		String RMBStr = "";
		boolean lastzero = false;
		boolean hasvalue = false; // 亿、万进位前有数值标记
		int len, n;
		len = NumStr.length();
		if (len > 15)
			return "数值过大!";
		for (int i = len - 1; i >= 0; i--) {
			if (NumStr.charAt(len - i - 1) == ' ')
				continue;
			n = NumStr.charAt(len - i - 1) - '0';
			if (n < 0 || n > 9)
				return "输入含非数字字符!";

			if (n != 0) {
				if (lastzero)
					RMBStr += HanDigiStr[0]; // 若干零后若跟非零值，只显示一个零
				// 除了亿万前的零不带到后面
				// if( !( n==1 && (i%4)==1 && (lastzero || i==len-1) ) ) //
				// 如十进位前有零也不发壹音用此行
				if (!(n == 1 && (i % 4) == 1 && i == len - 1)) // 十进位处于第一位不发壹音
					RMBStr += HanDigiStr[n];
				RMBStr += HanDiviStr[i]; // 非零值后加进位，个位为空
				hasvalue = true; // 置万进位前有值标记

			} else {
				if ((i % 8) == 0 || ((i % 8) == 4 && hasvalue)) // 亿万之间必须有非零值方显示万
					RMBStr += HanDiviStr[i]; // “亿”或“万”
			}
			if (i % 8 == 0)
				hasvalue = false; // 万进位前有值标记逢亿复位
			lastzero = (n == 0) && (i % 4 != 0);
		}

		if (RMBStr.length() == 0)
			return HanDigiStr[0]; // 输入空字符或"0"，返回"零"
		return RMBStr;
	}

	/** 将金额转换成大写 */
	public static String doubleToMoneyStr(double val) {
		String SignStr = "";
		String TailStr = "";
		long fraction, integer;
		int jiao, fen;

		if (val < 0) {
			val = -val;
			SignStr = "负";
		}
		if (val > 999999999999999.99 || val < -9999999999999999.99)
			return "数值位数过大!";
		// 四舍五入到分
		long temp = Math.round(val * 100);
		integer = temp / 100;
		fraction = temp % 100;
		jiao = (int) fraction / 10;
		fen = (int) fraction % 10;
		if (jiao == 0 && fen == 0) {
			TailStr = "整";
		} else {
			TailStr = HanDigiStr[jiao];
			if (jiao != 0)
				TailStr += "角";
			if (integer == 0 && jiao == 0) // 零元后不写零几分
				TailStr = "";
			if (fen != 0)
				TailStr += HanDigiStr[fen] + "分";
		}

		// 下一行可用于非正规金融场合，0.03只显示“叁分”而不是“零元叁分”
		// if( !integer ) return SignStr+TailStr;

		return SignStr + PositiveIntegerToHanStr(String.valueOf(integer)) + "元" + TailStr;
	}
	
	/**
	 * 获取数据库的时间 格式为'yyyy-MM-dd hh24:mi:ss
	 * @throws EbillsException 
	 */
	 public static String getSysDateHMS() throws EbillsException{
		  java.util.Date date=new java.util.Date();
		  SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  String datestr= format.format(date);
		  String hms=datestr.split(" ")[1];
		  String workdate=null;
		  workdate=CommonUtil.getSysFld(EbpConstants.WORK_DATE)+" "+hms;
		  return workdate;
	 }
	 
	 public static String getWorkDateYMD() throws EbillsException{
		  String workdate=CommonUtil.getSysFld(EbpConstants.WORK_DATE);
		  String datestr[]= workdate.split("-");
		  if(datestr.length==3){
			  return datestr[0]+datestr[1]+datestr[2];
		  }
		  return "";
	 }

}
