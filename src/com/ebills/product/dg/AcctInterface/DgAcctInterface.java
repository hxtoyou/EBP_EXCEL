package com.ebills.product.dg.AcctInterface;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.bussprocess.context.Context;
import org.apache.commons.lang.StringUtils;

import com.ebills.util.EbillsException;
import com.ebills.util.EbillsLog;
import com.ebills.util.db.ConnectionManager;
import com.ebills.util.db.ConnectionManagerFactory;
import com.ebills.product.dg.commons.Commons;
import com.ebills.intf.spi.InterfaceManager;
import com.ebills.utils.EbpConstants;
import com.ebills.utils.EbpDao;

public class DgAcctInterface {
		private static String className = DgAcctInterface.class.getName();
		public static Commons commons = new Commons();
		private static EbillsLog log = new EbillsLog(className);
	    private static String eciSeverId="BODHST0000";//ECI服务接口ID
	    private static String transCode="regflw";//交易代码
	    private static String sysId="01";//系统标识
	    private static String prcscd="itskns";//渠道处理码
		/**
		 * 接收记帐节点调用上下文信息
		 * @param context
		 * @return
		 * @throws Exception
		 */
		public void beforeCallItskns(Context context) throws Exception{
			try {
					//从上下文提取需要的数据(常用数据提取可用到常量)
					String txnNo = (String) context.get(EbpConstants.TXNSERIALNO);
					
					AcctUtilDataInfo acctUtilDataInfo = DgAcctInterface.getAcctUtil(txnNo);
					List buAcctDataInfoList = DgAcctInterface.getBuAcctDataInfoList(txnNo);
					List fundList = DgAcctInterface.getBuFundList(txnNo);
					//初始化公共报文头
					CommXmlHead xmlHead = this.initXmlHead(acctUtilDataInfo,context,"itskns");				
					//组报文体
					String trantp = "TR";
					String bizno = acctUtilDataInfo.getCurtBizNo();
					String ltprid = acctUtilDataInfo.getTradeNo();					
					
					int listnm = buAcctDataInfoList.size();
					context.put("trantp", trantp);
					context.put("torfno", txnNo);
					context.put("tsrfno", bizno);
					context.put("ltprid", ltprid);
					context.put("listnm", String.valueOf(listnm));
					List list = new ArrayList();
					for(int i=0;i<listnm;i++){
						//BuAcctDataInfo buAcctDataInfo = (BuAcctDataInfo)buAcctDataInfoList.get(i);
						Map map=(Map)buAcctDataInfoList.get(i);
						
						map.put("accono",(String) map.get("acctNo"));
						map.put("dcflag", (String) map.get("dcFlag"));
						map.put("tramts",new BigDecimal((String) map.get("amount")));
						map.put("tscode", "0001");
						map.put("crcycd", commons.getCurSignCode((String) map.get("ccy"),""));
						map.put("chtype", "0");
						map.put("cheque", "");
						String accvouName = (String)map.get("accvouName");
						String dcFlag = (String) map.get("dcFlag");
						String ccy = (String) map.get("ccy");
						String acctTypeNo = (String)map.get("acctTypeNo");
						
						//结售套汇时需要在循环体中把汇率和结售汇类型传给核心，结售汇时传入汇率，套汇时传入买入价和卖出价
						if(fundList.size()>0){
							Map fundMap=(Map)fundList.get(0);
							String ybCur = (String)fundMap.get("YBCUR");
							String jzCur = (String)fundMap.get("JZCUR");
							String jsthFlag = (String)fundMap.get("JSTHFLAG");
							String thMidHr = (String)fundMap.get("THMIDHR");
							String realHr = (String)fundMap.get("REALHR");
							/*String sysHr = (String)fundMap.get("SYSHR");
							String csHr = (String)fundMap.get("CSHR");
							String ppHr = (String)fundMap.get("PPHR");*/
							if(!"charge".equals(accvouName) || !"P".equals(dcFlag) || !"R".equals(dcFlag)){//首先排除手续费和表外的分录
								if("1".equals(jsthFlag)){
									map.put("foretp", "1");
									map.put("exrate", new BigDecimal(realHr));
								}else if("2".equals(jsthFlag)){
									map.put("foretp", "2");
									map.put("exrate", new BigDecimal(realHr));
								}else if("3".equals(jsthFlag)){	//套汇有6条分录：D 外币1 C 外币1	D 人民币 C 人民币	D 外币2 C 外币2，前三条取买入价，后三条取卖出价						
									if(ybCur.equals(ccy)){
										map.put("byrate", new BigDecimal(thMidHr));//买入价
									}else if(jzCur.equals(ccy)){
										map.put("slrate", Double.valueOf(thMidHr)/Double.valueOf(realHr));//汇卖价=套汇中间价/成交价
									}else if(("ZA00000013".equals(acctTypeNo) || "ZA00000007".equals(acctTypeNo))  && "CNY".equals(ccy) && "D".equals(dcFlag)){//买入价
										map.put("byrate", new BigDecimal(thMidHr));
									}else if(("ZA00000013".equals(acctTypeNo) || "ZA00000007".equals(acctTypeNo)) && "CNY".equals(ccy) && "C".equals(dcFlag)){//汇卖价
										map.put("slrate", Double.valueOf(thMidHr)/Double.valueOf(realHr));
									}
									map.put("foretp", "3");
								}
							}
						}
						list.add(map);
					}
					
					context.put("bodrcd", list);
			} catch (Exception ex) {
				ex.printStackTrace();
			}	
		}
		
		
		/**
		 * 接收记帐节点调用上下文信息
		 * @param context
		 * @return
		 * @throws Exception
		 */
		public Map<String,Object> afterCallItskns(Context context) throws Exception{
			Map<String, Object> recMap = new HashMap<String, Object>();
			try {
				Map  map = (Map)context.get(InterfaceManager.RESULT_KEY);
				
				System.out.println("返回的 原始数据>>>>>>>>>>:"+map);
				String txnNo = (String) context.get("txnNo");
				String tellerno = (String) context.get("tellerno");
				String errorCode = (String) map.get("errorCode");	
				String errorMsg = (String) map.get("errorMsg");	
				String workdate = (String) map.get("workdate");	
				String worktime = (String) map.get("worktime");	
				String serialno = (String) map.get("serialno");	
				String systdt = (String) map.get("systdt");	
				String transq = (String) map.get("transq");			
				
				if("00000000".equals(errorCode)){
					DgAcctInterface.insertBuTallyercordInfo(txnNo,transq,systdt,"EBP01");//插入BUTALLYRECORDINFO表，当日冲帐用到TCCM流水号
					recMap.put("desc", errorMsg);
					recMap.put("status", errorCode);
					recMap.put("errCode", errorCode);
				}else{
					context.put(InterfaceManager.ERROR_CODE, errorCode);
					context.put(InterfaceManager.ERROR_MSG, errorMsg);
				}				
			} catch (Exception ex) {
				ex.printStackTrace();
			}	
			return recMap;
		}
		
		/**
		 * 冲账接口发送数据
		 * @param context
		 * @return
		 * @throws Exception
		 */
		public void beforeCallItsrea(Context context) throws EbillsException{
			try {
					//从上下文提取需要的数据(常用数据提取可用到常量)
					String txnNo = (String) context.get(EbpConstants.TXNSERIALNO);
					AcctUtilDataInfo acctUtilDataInfo = DgAcctInterface.getAcctUtil(txnNo);
					List buAcctDataInfoList = DgAcctInterface.getBuAcctDataInfoList(txnNo);
					TallyRecordInfo tallyRecordInfo = DgAcctInterface.getTallyInfo(txnNo);//获取冲帐核心流水号BUTALLYRECORDINFO
					
					//初始化公共报文头
					CommXmlHead xmlHead = this.initXmlHead(acctUtilDataInfo,context,"itsrea");					
					//组报文体
					SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
					String sttsdt = "";
					String sttssq = "";
					String torfno = "";
					String stfcsq = "";
					if("".equals(tallyRecordInfo.getTxnSerialNo()) || tallyRecordInfo.getTxnSerialNo() ==null){
						//throw new com.ebills.product.dg.AcctInterface.domain.EbillsException( "调用冲账接口失败，未查询到核心记账信息!");
						//throw new EbillsException(className,1);
						Map map = this.getTxn(txnNo);
						sttsdt = sd.format(new Date(System.currentTimeMillis()));
						torfno = (String)map.get("SRHTXNNO");						
					}else{
						sttsdt = sd.format(tallyRecordInfo.getTallyDate());
						sttssq = tallyRecordInfo.getHxFlserialNo();
						torfno = tallyRecordInfo.getTxnSerialNo();
					}
					
					
					List list = DgAcctInterface.getBizNoList(txnNo);
					if(list.size()>0){
						Map map = (Map)list.get(0);
						stfcsq = (String)map.get("CURTBIZNO");
					}
					context.put("sttsdt", sttsdt);
					context.put("sttssq", sttssq);
					context.put("torfno", torfno);
					context.put("stfcsq", stfcsq);
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new EbillsException(null,"调用冲账接口失败，未查询到核心记账信息!");
			}	
		}
		
		/**
		 * 冲账接口接收数据
		 * @param context
		 * @return
		 * @throws Exception
		 */
		public Map<String,Object> afterCallItsrea(Context context) throws Exception{
			Map<String, Object> recMap = new HashMap<String, Object>();
			try {
				Map  map = (Map)context.get(InterfaceManager.RESULT_KEY);
				
				System.out.println("返回的 原始数据>>>>>>>>>>:"+map);
				String txnNo = (String) context.get("txnNo");
				String tellerno = (String) context.get("tellerno");
				String errorCode = (String) map.get("errorCode");	
				String errorMsg = (String) map.get("errorMsg");	
				String workdate = (String) map.get("workdate");	
				String worktime = (String) map.get("worktime");	
				String serialno = (String) map.get("serialno");	
				String trandt = (String) map.get("trandt");	
				String transq = (String) map.get("transq");			
				
				if("00000000".equals(errorCode)){
					DgAcctInterface.insertBuTallyercordInfo(txnNo,transq,trandt,"EBP01");//冲账交易也需要插入BUTALLYRECORDINFO表
					recMap.put("desc", errorMsg);
					recMap.put("status", errorCode);
					recMap.put("errCode", errorCode);
				}else{
					context.put(InterfaceManager.ERROR_CODE, errorCode);
					context.put(InterfaceManager.ERROR_MSG, errorMsg);
				}				
			} catch (Exception ex) {
				ex.printStackTrace();
			}	
			return recMap;
		}
		
		
		/**
		 * 通过流水号查询核心是否记账成功接口
		 * @param context
		 * @return
		 * @throws Exception
		 */
		public void afterCallItsase(Context context) throws Exception{
			try {
				Map  map = (Map)context.get(InterfaceManager.RESULT_KEY);
				System.out.println("返回的 原始数据>>>>>>>>>>:"+map);
				List list = new ArrayList();
				list.add(map);
				context.put("rows", list);
				
				String txnNo = (String) context.get("txnNo");
				String tellerno = (String) context.get("tellerno");
				String errorCode = (String) map.get("errorCode");	
				/*String errorMsg = (String) map.get("errorMsg");	
				String workdate = (String) map.get("workdate");	
				String worktime = (String) map.get("worktime");	
				String serialno = (String) map.get("serialno");*/	
				//响应报文
				String sucflg = (String) map.get("sucflg");	//是否记账成功标志（C－记帐成功，其他－没有记帐）
				String acrfer = (String) map.get("acrfer");	//综合业务系统记帐流水号
				String eydate = (String) map.get("eydate");	//综合业务系统记帐日期
				
				if("00000000".equals(errorCode)){
					if("C".equals(sucflg)){//核心记账成功，国结需要判断是否记录了核心流水号，如果没有记录，需要插入BUTALLYRECORDINFO表
						TallyRecordInfo tallyRecordInfo = DgAcctInterface.getTallyInfoByTxnNo(txnNo);
						if(tallyRecordInfo==null){
							DgAcctInterface.insertBuTallyercordInfo(txnNo,acrfer,eydate,"EBP01");
						}						
					}					
				}				
			} catch (Exception ex) {
				ex.printStackTrace();
			}	
		}
		
		/**
		 * 牌价接口发送数据
		 * @param context
		 * @return
		 * @throws Exception
		 */
		public void beforeCallItsrat(Context context) throws Exception{
			try {
					String orgNo = (String) context.get("butxn_tranOrgNo");
					
					//初始化公共报文头
					CommXmlHead xmlHead = this.initXmlHead(null,context,"itsrat");					
					//组报文体
					SimpleDateFormat sd1 = new SimpleDateFormat("yyyyMMdd");
					SimpleDateFormat sd2 = new SimpleDateFormat("HH:mm:ss");
					String stfcsq = "";
					List list = DgAcctInterface.getCcyPriList(orgNo);
					List listBody = new ArrayList();
					int listnm = list.size();
					context.put("listnm", String.valueOf(listnm));
					for(int i=0;i<listnm;i++){
						Map map=(Map)list.get(i);	
						String impdate = (String)map.get("impdate");
						Timestamp tst = Timestamp.valueOf(impdate);
						Date tmpDate = new Date(tst.getTime());
						String timeStr = sd2.format(tmpDate);
						String dateStr = sd1.format(tmpDate);
						map.put("rfdate", dateStr);
						map.put("rftime", timeStr);						
						map.put("cucyno", (String) map.get("standardcode"));
						map.put("cucycd", (String) map.get("cursign"));
						map.put("exunit", "100");
						map.put("cucyby", (String) map.get("billbuyprice"));
						map.put("cucysl" ,(String) map.get("billsaleprice"));
						map.put("moneby", (String) map.get("buyprice"));
						map.put("monesl", (String) map.get("saleprice"));
						map.put("midlpr", (String) map.get("middleprice"));
						map.put("textpc",( (String)map.get("cnname")).trim());
						listBody.add(map);
					}
					
					context.put("bodrcd", listBody);
			} catch (Exception ex) {
				ex.printStackTrace();
			}	
		}
		
		/**
		 * 牌价接口解析数据
		 * @param context
		 * @return
		 * @throws Exception
		 */
		public void afterCallItsrat(Context context) throws Exception{
			try {
				Map  map = (Map)context.get(InterfaceManager.RESULT_KEY);
				System.out.println("返回的 原始数据>>>>>>>>>>:"+map);
				List list = new ArrayList();
				list.add(map);
				context.put("rows", list);
						
			} catch (Exception ex) {
				ex.printStackTrace();
			}	
		}
		
		/**
		 * 折算率接口发送数据
		 * @param context
		 * @return
		 * @throws Exception
		 */
		public void beforeCallItscus(Context context) throws Exception{
			try {
					//初始化公共报文头
					CommXmlHead xmlHead = this.initXmlHead(null,context,"itscus");
					
					//组报文体
					SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd");
					List list = DgAcctInterface.getCrRateList();
					List listBody = new ArrayList();
					int listnm = list.size();
					context.put("listnm", String.valueOf(listnm));
					for(int i=0;i<listnm;i++){
						Map map=(Map)list.get(i);	
						String mdate = (String)map.get("mdate");
						
						java.util.Date d = (java.util.Date)sd1.parseObject(mdate);
						String rfdate = new SimpleDateFormat("yyyyMMdd").format(d);
						
						map.put("rfdate", rfdate);
						map.put("cucyno", commons.getCurSignCode((String) map.get("cursign"),""));
						map.put("cucycd", (String) map.get("cursign"));
						map.put("cucymo", (String) map.get("converrate"));
						listBody.add(map);
					}
					
					context.put("bodrcd", listBody);
			} catch (Exception ex) {
				ex.printStackTrace();
			}	
		}
		
		/**
		 * 折算率接口接收数据
		 * @param context
		 * @return
		 * @throws Exception
		 */
		public void afterCallItscus(Context context) throws Exception{
			try {
				Map  map = (Map)context.get(InterfaceManager.RESULT_KEY);
				System.out.println("返回的 原始数据>>>>>>>>>>:"+map);
				List list = new ArrayList();
				list.add(map);
				context.put("rows", list);
						
			} catch (Exception ex) {
				ex.printStackTrace();
			}	
		}
		
		/**
		 * 根据帐号列表，查询账号信息
		 * @param context
		 * @return
		 * @throws Exception
		 */
		public void beforeCallItsacl(Context context) throws Exception{
			try {
				/*ps.setValue("eciSeverId","BODHST0000");
				ps.setValue("transCode","regflw");
				ps.setValue("prcscd","itsacl");
				var cxt=ps.getCxt();
				var intfno=cxt.get('intfno');
				ps.setValue("channelserno",intfno);
				var bodrcd = ps.getValue("bodrcd");
				ps.printLog("bodrcd="+bodrcd);
				ps.setValue("bodrcd",bodrcd);*/
					String intfno=(String)context.get("intfno");
					context.put("eciSeverId", "BODHST1070");
					context.put("transCode", "regflw");
					context.put("prcscd", "itsacl");
					context.put("channelserno",intfno);
					String bodrcd = (String) context.get("bodrcd1");
					String acctList[]=bodrcd.split(";");
					List list = new ArrayList();
					for(int i=0;i<acctList.length;i++){
						Map map= new HashMap();
						map.put("acctno", acctList[i]);
						list.add(map);
					}
					context.put("bodrcd", list);
			} catch (Exception ex) {
				ex.printStackTrace();
			}	
		}
		
		/**
		 * 根据帐号列表，查询账号信息
		 * @param context
		 * @return
		 * @throws Exception
		 */
		public void afterCallItsacl(Context context) throws Exception{
			try {
				Map  map = (Map)context.get(InterfaceManager.RESULT_KEY);
				System.out.println("返回的 原始数据>>>>>>>>>>:"+map);
				List list = new ArrayList();
				list.add(map);
				context.put("rows", list);						
			} catch (Exception ex) {
				ex.printStackTrace();
			}	
		}
		
		
		/**
		 * 初始化公共报文头
		 * @param context
		 * @return
		 * @throws Exception
		 */
		public CommXmlHead initXmlHead(AcctUtilDataInfo acctUtilDataInfo,Context context,String prcscd){
			CommXmlHead xmlHead = new CommXmlHead();
			try {
					String tranOrgNo = (String) context.get(EbpConstants.TRANSACTION_TRANORGNO);
					String orgCode = commons.getOrgCodeInfo(tranOrgNo);
					if("itskns".equals(prcscd)){
						xmlHead.setEciSeverId("BODHST1072");
					}else if("itsrea".equals(prcscd)){
						xmlHead.setEciSeverId("BODHST1073");
					}else if("itsrat".equals(prcscd)){
						xmlHead.setEciSeverId("BODHST1075");
					}else if("itscus".equals(prcscd)){
						xmlHead.setEciSeverId("BODHST1076");
					}else{
						xmlHead.setEciSeverId(eciSeverId);
					}					
					xmlHead.setXmlflag("1");
					xmlHead.setTemplateCodeName("params");
					xmlHead.setTransCode(transCode);
					xmlHead.setSysId(sysId);
					xmlHead.setChannelCode("011");
					xmlHead.setSubchannelCode("EBP");
					xmlHead.setTradeFlag("0");
					xmlHead.setCheckFlag("0");
					/*SerialNoFactory snf =  new SerialNoFactory();
					String serialNo = snf.getSerialNo("DGINTERFACE");*/
					xmlHead.setChannelserno((String)context.get("intfno"));								
					xmlHead.setPrcscd(prcscd);
					/*if(acctUtilDataInfo != null){
						String mainUserNo = commons.getMainUserNo(acctUtilDataInfo.getHandlerId());
						xmlHead.setTellerno(mainUserNo);
						context.put("tellerno", xmlHead.getTellerno());
					}*/
					
					
					context.put("eciSeverId", xmlHead.getEciSeverId());
					context.put("xmlflag", xmlHead.getXmlflag());
					context.put("templateCodeName", xmlHead.getTemplateCodeName());
					context.put("transCode", xmlHead.getTransCode());
					context.put("sysId", xmlHead.getSysId());
					context.put("subchannelCode", xmlHead.getSubchannelCode());
					context.put("tradeFlag", xmlHead.getTradeFlag());
					context.put("checkFlag", xmlHead.getCheckFlag());
					context.put("channelserno", xmlHead.getChannelserno());
					context.put("prcscd", xmlHead.getPrcscd());
					//context.put("brno", orgCode);
					
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return xmlHead;
		}
		
		/**
		 * 2014-06-14 
		 * 获取记帐公共信息，并保存到新对象中
		 * @return
		 * @throws Exception
		 */
		public static AcctUtilDataInfo getAcctUtil(String txnNo) throws Exception{
			AcctUtilDataInfo acctUtilDataInfo = new AcctUtilDataInfo();
			ConnectionManager cm = null;
			try {
				cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);				
				EbpDao gjDao = new EbpDao();			
				if(!StringUtils.isEmpty(txnNo)){
					String butxnSql = "select a.TRADENO,a.TRANORGNO,a.CURTBIZNO,a.PREBIZNO,a.HANDLERID,a.CHECKERID," 
							+"(SELECT MAINCORPNO FROM PAUSR WHERE USERID =a.HANDLERID) AS maincorpno," 
							+"(SELECT ORGCODE FROM PAORG WHERE ORGNO = a.TRANORGNO) AS ORGCODE, " 
							+"(SELECT CORPTYPE FROM PACRP WHERE CORPNO = a.CUSTID) AS CORPTYPE from BUTXNTP a WHERE TXNNO='"+ txnNo + "'";
					//System.out.println("sql return ==>"+butxnSql);				
					List<Map<String,Object>> butxnList = gjDao.queryBySql(butxnSql, null,null);
					if(null != butxnList && !butxnList.isEmpty() 
							&& butxnList != null && butxnList.size() > 0){					
						for (int i = 0; i < butxnList.size(); i++) {
							Map<String,Object> map =  butxnList.get(i);
							acctUtilDataInfo.setTradeNo((String) map.get("TRADENO"));
							acctUtilDataInfo.setTradOrgNo((String) map.get("TRANORGNO"));
							acctUtilDataInfo.setCurtBizNo((String) map.get("CURTBIZNO"));
							acctUtilDataInfo.setPreBizNo((String) map.get("PREBIZNO"));
							acctUtilDataInfo.setHandlerId((String) map.get("HANDLERID"));
							acctUtilDataInfo.setCheckerId((String) map.get("CHECKERID"));
							acctUtilDataInfo.setUserTallyId((String) map.get("maincorpno"));
							acctUtilDataInfo.setOrgCode((String) map.get("ORGCODE"));
							acctUtilDataInfo.setCorpType((String) map.get("CORPTYPE"));
							
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
			return acctUtilDataInfo;
		}
		
		/**
		 * 2014-06-14 
		 * 获取记帐分录信息，并保存到List中
		 * @return
		 * @throws Exception
		 */
		public static List getBuAcctDataInfoList(String txnNo) throws Exception{		
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			ConnectionManager cm = null;
			try {
				cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);				
				EbpDao gjDao = new EbpDao();			
				if(!StringUtils.isEmpty(txnNo)){
					String buAcctSql = "select a.accvouName,a.accvouNo,a.txnNo,a.dcFlag,a.ccy,a.amount,a.acctNo,a.acctNoName,a.subJect,a.acctTypeNo" 
							+" from BUACCVOUTP a WHERE TXNNO='"+ txnNo + "' ORDER BY ACCVOUNO";
					//System.out.println("sql return ==>"+buAcctSql);	,a.localAmount,a.buyPrice			
					List<Map<String,Object>> buAcctList = gjDao.queryBySql(buAcctSql, null,null);
					if(null != buAcctList && !buAcctList.isEmpty() 
							&& buAcctList != null && buAcctList.size() > 0){					
						for (int i = 0; i < buAcctList.size(); i++) {
							Map<String,Object> map =  buAcctList.get(i);
							list.add(map);
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
			return list;
		}
		
		/**
		 * 2014-06-14 
		 * 获取资金流向信息，并保存到List中
		 * @return
		 * @throws Exception
		 */
		public static List getBuFundList(String txnNo) throws Exception{		
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			ConnectionManager cm = null;
			try {
				cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);				
				EbpDao gjDao = new EbpDao();			
				if(!StringUtils.isEmpty(txnNo)){
					String buAcctSql = "select a.YBCUR,a.JZCUR,a.JSTHFLAG,a.THMIDHR,a.REALHR,a.SYSHR,a.CSHR,a.PPHR" 
							+" from bufndtp a WHERE TXNNO='"+ txnNo + "'";
					//System.out.println("sql return ==>"+buAcctSql);	,a.localAmount,a.buyPrice			
					List<Map<String,Object>> buAcctList = gjDao.queryBySql(buAcctSql, null,null);
					if(null != buAcctList && !buAcctList.isEmpty() 
							&& buAcctList != null && buAcctList.size() > 0){					
						for (int i = 0; i < buAcctList.size(); i++) {
							Map<String,Object> map =  buAcctList.get(i);
							list.add(map);
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
			return list;
		}
		
		/**
		 * 2014-06-14
		 * 获取记帐公共信息，并保存到新对象中
		 * @return
		 * @throws Exception
		 */
		public static TallyRecordInfo getTallyInfo(String txnNo) throws Exception{
			TallyRecordInfo tallyRecordInfo = new TallyRecordInfo();
			ConnectionManager cm = null;
			try {
				cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);				
				EbpDao gjDao = new EbpDao();			
				if(!StringUtils.isEmpty(txnNo)){
					String butallySql = "select a.TXNSERIALNO,a.TALLYDATE,a.HXFLSERIALNO,a.TALLYUSER from BUTALLYRECORDINFO a,buremdebttp b WHERE b.TXNNO='"+ txnNo + "' AND a.TXNSERIALNO = b.SRHTXNNO";
					//System.out.println("sql return ==>"+butallySql);				
					List<Map<String,Object>> butallyList = gjDao.queryBySql(butallySql, null,null);
					if(null != butallyList && !butallyList.isEmpty() 
							&& butallyList != null && butallyList.size() > 0){					
						for (int i = 0; i < butallyList.size(); i++) {
							Map<String,Object> map =  butallyList.get(i);
							tallyRecordInfo.setTxnSerialNo((String) map.get("TXNSERIALNO"));
							tallyRecordInfo.setTallyDate(Date.valueOf(((String) map.get("TALLYDATE")).substring(0, 10)));
							tallyRecordInfo.setHxFlserialNo((String) map.get("HXFLSERIALNO"));
							tallyRecordInfo.setTallyUser((String) map.get("TALLYUSER"));
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
			return tallyRecordInfo;
		}
		
		/**
		 * 2014-06-14
		 * 获取冲账流水号
		 * @return
		 * @throws Exception
		 */
		public static Map getTxn(String txnNo) throws Exception{
			Map<String,Object> map = new HashMap<String,Object>();
			ConnectionManager cm = null;
			try {
				cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);				
				EbpDao gjDao = new EbpDao();			
				if(!StringUtils.isEmpty(txnNo)){
					String butallySql = "select a.SRHTXNNO from buremdebttp a WHERE a.TXNNO='"+ txnNo + "'";
					//System.out.println("sql return ==>"+butallySql);				
					List<Map<String,Object>> butallyList = gjDao.queryBySql(butallySql, null,null);
					if(null != butallyList && !butallyList.isEmpty() 
							&& butallyList != null && butallyList.size() > 0){					
						for (int i = 0; i < butallyList.size(); i++) {
							map =  butallyList.get(i);
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
			return map;
		}
		
		/**
		 * 2014-06-14
		 * 获取记帐公共信息，并保存到新对象中
		 * @return
		 * @throws Exception
		 */
		public static TallyRecordInfo getTallyInfoByTxnNo(String txnNo) throws Exception{
			TallyRecordInfo tallyRecordInfo = new TallyRecordInfo();
			ConnectionManager cm = null;
			try {
				cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);				
				EbpDao gjDao = new EbpDao();			
				if(!StringUtils.isEmpty(txnNo)){
					String butallySql = "select a.TXNSERIALNO,a.TALLYDATE,a.HXFLSERIALNO,a.TALLYUSER from BUTALLYRECORDINFO a WHERE a.TXNSERIALNO='"+ txnNo + "'";
					//System.out.println("sql return ==>"+butallySql);				
					List<Map<String,Object>> butallyList = gjDao.queryBySql(butallySql, null,null);
					if(null != butallyList && !butallyList.isEmpty() 
							&& butallyList != null && butallyList.size() > 0){					
						for (int i = 0; i < butallyList.size(); i++) {
							Map<String,Object> map =  butallyList.get(i);
							tallyRecordInfo.setTxnSerialNo((String) map.get("TXNSERIALNO"));
							tallyRecordInfo.setTallyDate(Date.valueOf(((String) map.get("TALLYDATE")).substring(0, 10)));
							tallyRecordInfo.setHxFlserialNo((String) map.get("HXFLSERIALNO"));
							tallyRecordInfo.setTallyUser((String) map.get("TALLYUSER"));
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
			return tallyRecordInfo;
		}
		
		
		/**
		 * 2014-06-14 
		 * 通过冲账交易流水获取被冲账交易的业务编号
		 * @return
		 * @throws Exception
		 */
		public static List getBizNoList(String txnNo) throws Exception{		
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			ConnectionManager cm = null;
			try {
				cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);				
				EbpDao gjDao = new EbpDao();			
				if(!StringUtils.isEmpty(txnNo)){
					String Sql = "select c.CURTBIZNO from buremdebttp b,butxntp c WHERE b.TXNNO='"+ txnNo + "' AND  b.SRHTXNNO = c.TXNNO";
					//System.out.println("sql return ==>"+buAcctSql);	,a.localAmount,a.buyPrice			
					List<Map<String,Object>> buAcctList = gjDao.queryBySql(Sql, null,null);
					if(null != buAcctList && !buAcctList.isEmpty() 
							&& buAcctList != null && buAcctList.size() > 0){					
						for (int i = 0; i < buAcctList.size(); i++) {
							Map<String,Object> map =  buAcctList.get(i);
							list.add(map);
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
			return list;
		}
		
		/**
		 * 2014-06-20 
		 * 插入核心记帐信息,BUTALLYRECORDINFO
		 * @return
		 * @throws Exception
		 */
		public static void insertBuTallyercordInfo(String txnNo,String service_sn,String service_time,String tallyUser) throws Exception{		
			ConnectionManager cm = null;
			try {
				cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);				
				EbpDao gjDao = new EbpDao();			
				if(!StringUtils.isEmpty(txnNo)){
					List<Object> params=new LinkedList<Object>();	
				    params.add(txnNo);
				    DateFormat format = new SimpleDateFormat("yyyyMMdd");
				    params.add(format.parse(service_time));
				    params.add(service_sn);			    
				    params.add(tallyUser);			    
					String insertSql = "insert into BUTALLYRECORDINFO " 
							+" (TXNSERIALNO,TALLYDATE,HXFLSERIALNO,TALLYUSER) values (?,?,?,?)";
					//System.out.println("sql return ==>"+insertSql);				
					gjDao.execute(insertSql,params);
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
		 * 
		 * 获取牌价信息，并保存到List中
		 * @return
		 * @throws Exception
		 */
		public static List getCcyPriList(String orgNo) throws Exception{		
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			ConnectionManager cm = null;
			try {
				cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);				
				EbpDao gjDao = new EbpDao();			
				if(!StringUtils.isEmpty(orgNo)){
					String buAcctSql = "select a.impdate,b.standardcode,a.cursign,a.buyprice,a.saleprice,a.middleprice,a.billbuyprice,a.billsaleprice,b.cnname " 
							+" from paccypri a,paccy b where a.cursign=b.cursign and a.orgno ='"+ orgNo + "'";	
					List<Map<String,Object>> buAcctList = gjDao.queryBySql(buAcctSql, null,null);
					if(null != buAcctList && !buAcctList.isEmpty() 
							&& buAcctList != null && buAcctList.size() > 0){					
						for (int i = 0; i < buAcctList.size(); i++) {
							Map<String,Object> map =  buAcctList.get(i);
							list.add(map);
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
			return list;
		}
		
		/**
		 * 
		 * 获取折算率信息，并保存到List中
		 * @return
		 * @throws Exception
		 */
		public static List getCrRateList() throws Exception{		
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			ConnectionManager cm = null;
			try {
				cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);				
				EbpDao gjDao = new EbpDao();
				SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
				String str = sd.format(new Date(System.currentTimeMillis()));
				String year = str.substring(0, 4);
				String month = str.substring(4, 6);
				
				String crRateSql = "select a.year,a.month,a.cursign,a.converrate,a.mdate  " 
						+" from paotcrate a where  a.year ='"+ year + "' and a.month ='"+ month + "'";	
				List<Map<String,Object>> crRateList = gjDao.queryBySql(crRateSql, null,null);
				if(null != crRateList && !crRateList.isEmpty() 
						&& crRateList != null && crRateList.size() > 0){					
					for (int i = 0; i < crRateList.size(); i++) {
						Map<String,Object> map =  crRateList.get(i);
						list.add(map);
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
			return list;
		}
		
		/**
		 * 
		 * 根据币种查询外管局折算率
		 * @return
		 * @throws Exception
		 */
		public static List getCrRateByCur(String cur) throws Exception{		
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			ConnectionManager cm = null;
			try {
				cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);				
				EbpDao gjDao = new EbpDao();
				SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
				String str = sd.format(Commons.getWorkDate());
				String year = str.substring(0, 4);
				String month = str.substring(4, 6);
				
				String crRateSql = "select a.year,a.month,a.cursign,a.converrate,a.mdate  " 
						+" from paotcrate a where  a.year ='"+ year + "' and a.month ='"+ month + "' and a.cursign='"+cur+"'";	
				List<Map<String,Object>> crRateList = gjDao.queryBySql(crRateSql, null,null);
				if(null != crRateList && !crRateList.isEmpty() 
						&& crRateList != null && crRateList.size() > 0){					
						Map<String,Object> map =  crRateList.get(0);
						list.add(map);
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
			return list;
		}
		
}
