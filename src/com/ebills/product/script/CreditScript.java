
package com.ebills.product.script;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.bussprocess.context.Context;
import org.apache.commons.lang.StringUtils;

import com.ebills.clear.achieve.impl.ClearPubAbstract;
import com.ebills.clear.util.ClearConstants;
import com.ebills.commons.RollBack;
import com.ebills.commons.SerialNoFactory;
import com.ebills.param.datainfo.DataFactory;
import com.ebills.param.datainfo.DataInfo;
import com.ebills.product.commons.GeneralConstants;
import com.ebills.product.component.TraceInfo;
import com.ebills.product.dg.AcctInterface.domain.Transaction;
import com.ebills.product.dg.commons.Commons;
import com.ebills.product.dg.commons.UsrLogUtil;
import com.ebills.product.dg.commons.utils.ApplicationException;
import com.ebills.product.dg.credit.dao.ForexDao;
import com.ebills.product.dg.credit.send.SendCreditNoticeBiz;
import com.ebills.product.dg.credit.utils.UtilTools;
import com.ebills.product.dg.intf.core.ItsccrBiz;
import com.ebills.product.dg.intf.credit.CreditInterfaceDao;
import com.ebills.product.dg.intf.credit.InterfaceCreditRequst;
import com.ebills.script.BaseScript;
import com.ebills.util.EbillsException;
import com.ebills.util.EbillsLog;
import com.ebills.util.db.ConnectionManager;
import com.ebills.util.db.ConnectionManagerFactory;
import com.ebills.utils.CommonUtil;
import com.ebills.utils.EbpConstants;
import com.ebills.utils.EbpDao;
import com.ebills.wf.util.WFParam;
import com.union.xyHsmAPI.DgOperPwd_Soft;

public class CreditScript extends BaseScript {
	public static Commons commons = new Commons();
	private static UtilTools tool = new UtilTools();
	private static String className = CreditScript.class.getName();
	private static EbillsLog log = new EbillsLog(className);

	

	/**
	 * 进出口账
	 */
	public void initChargeoff() {
		String txnserialno = (String) context.get(EbpConstants.TXNSERIALNO);
		Transaction tx = tool.getTransaction(txnserialno);
		String erroMsg = "";
		if ("5".equals(tx.getLaunchModeNo())) {
			String fileno = tx.getFileNo();
			CreditInterfaceDao dao = new CreditInterfaceDao();

			try {
				InterfaceCreditRequst request = dao.getInfoByPutOutSerialNo(fileno);
				if (request == null) {
					erroMsg = "找不到信贷流水号:" + fileno + "信贷数据";
					this.context.put("error_msg", erroMsg);
					return;
				} else {
					if (request.getLccyno() != null) {
						this.context.put("IMLCISSUE_lcCursign", request.getLccyno() );
					}
					if (request.getLcamts() != null) {
						 this.context.put("IMLCISSUE_lcAmt", request.getLcamts() );
					}
					if (request.getTolep1() != null) {
						 this.context.put("IMLCISSUE_lcAmtTolerUp", request.getTolep1());
					}
					if (request.getTolep2() != null) {
						 this.context.put("IMLCISSUE_lcAmtTolerDown", request.getTolep2());
					}
					if (request.getEpdate() != null) {
						 this.context.put("IMLCISSUE_expiryDate", request.getEpdate());
					}
					if (request.getSutype() != null) {
						 this.context.put("IMLCISSUE_isUsance",request.getSutype() );
					}
					if (request.getAppno() != null) {
						 this.context.put("IMLGISSUE_applicantNo",request.getAppno() );
					}
				}
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * 提货担保
	 */
	public void initTHDB() {
		String txnserialno = (String) context.get(EbpConstants.TXNSERIALNO);
		Transaction tx = tool.getTransaction(txnserialno);
		String erroMsg = "";
		if ("5".equals(tx.getLaunchModeNo())) {
			String fileno = tx.getFileNo();
			CreditInterfaceDao dao = new CreditInterfaceDao();

			try {
				InterfaceCreditRequst request = dao.getInfoByPutOutSerialNo(fileno);
				if (request == null) {
					erroMsg = "找不到信贷流水号:" + fileno + "信贷数据";
					this.context.put("error_msg", erroMsg);
					return;
				} else {
					//提货担保申请人和起始日没有对应的页面字段
					if (request.getLccyno() != null) {
						this.context.put("IMTHDB_assCursign", request.getLccyno() );
					}
					if (request.getLcamts() != null) {
						 this.context.put("IMTHDB_assAmt", request.getLcamts() );
					}
					if (request.getJjhmno() != null) {
						 this.context.put("IMTHDB_feeNo",request.getJjhmno() );
					}
				}
			} catch (ApplicationException e) {
				//throw new EbillsException(e, className, 2, null, new String[]{e.getMessage()});
			}
		}

	}
	/**
	 * 保函开立
	 */
	public void initImLgIssue() {
		String txnserialno = (String) context.get(EbpConstants.TXNSERIALNO);
		Transaction tx = tool.getTransaction(txnserialno);
		String erroMsg = "";
		if ("5".equals(tx.getLaunchModeNo())) {
			String fileno = tx.getFileNo();
			CreditInterfaceDao dao = new CreditInterfaceDao();

			try {
				InterfaceCreditRequst request = dao.getInfoByPutOutSerialNo(fileno);
				if (request == null) {
					erroMsg = "找不到信贷流水号:" + fileno + "信贷数据";
					this.context.put("error_msg", erroMsg);
					return;
				} else {
					//提货担保申请人和起始日没有对应的页面字段
					if (request.getLccyno() != null) {
						this.context.put("IMLGISSUE_lgCur", request.getLccyno() );
					}
					if (request.getLcamts() != null) {
						 this.context.put("IMLGISSUE_lgAmt", request.getLcamts() );
					}
					if (request.getAppno() != null) {
						 this.context.put("IMLGISSUE_applicantNo",request.getAppno() );
					}
					if (request.getJjhmno() != null) {
						 this.context.put("IMLGISSUE_creditNo",request.getJjhmno() );
					}
					if (request.getApdate() != null) {
						 this.context.put("IMLGISSUE_effectDate", request.getApdate());
					}
					if (request.getEpdate() != null) {
						 this.context.put("IMLGISSUE_failrueDate", request.getEpdate());
					}
					if (request.getMgrate() != null) {
						 this.context.put("IMLGISSUE_DEPOSITPCT", request.getMgrate());
					}
				}
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * 信用证修改
	 */
	public void initLcMend() {
		String txnserialno = (String) context.get(EbpConstants.TXNSERIALNO);
		Transaction tx = tool.getTransaction(txnserialno);
		String erroMsg = "";
		if ("5".equals(tx.getLaunchModeNo())) {
			String fileno = tx.getFileNo();
			CreditInterfaceDao dao = new CreditInterfaceDao();

			try {
				InterfaceCreditRequst request = dao.getInfoByPutOutSerialNo(fileno);
				if (request == null) {
					erroMsg = "找不到信贷流水号:" + fileno + "信贷数据";
					this.context.put("error_msg", erroMsg);
					return;
				} else {
					if (request.getLcamts() != null) {
						 this.context.put("IMLCISSUE_lcAmt", request.getLcamts() );
					}
					if (request.getAppno() != null) {
						 this.context.put("IMLGISSUE_applicantNo",request.getAppno() );
					}
					if (request.getLcopno() != null) {
						 this.context.put("IMLGISSUE_creditNo",request.getLcopno() );
					}
					if (request.getEpdate() != null) {
						 this.context.put("expiryDateNew", request.getEpdate());
					}
				}
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
		}

	}
	
	public void loadPreNote(String bizNo){
		List<Object> paramList  = new ArrayList<Object>();
		paramList.add(0,bizNo);
		EbpDao gjjsDao = new EbpDao();
		String sql = "select * from notefo where bizno=?";
		try {
			List<Map<String, Object>> list = gjjsDao.sqlQuery(sql, paramList);
			System.out.println(list);
			this.context.put("xcpNote", CommonUtil.ListToJson(list));
		} catch (EbillsException e) {
			e.printStackTrace();
		};
	}
	
	public boolean validDB(String draftno){
		List<Object> paramList  = new ArrayList<Object>();
		paramList.add(0,draftno);
		EbpDao gjjsDao = new EbpDao();
		String sql = "select * from notefo where draftno=?";
		try {
			List<Map<String, Object>> list = gjjsDao.sqlQuery(sql, paramList);
			System.out.println(list.size());
			if(list.size()>0){
				return true;
			}
		} catch (EbillsException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**东莞新增  获取流水号*/
	public String getSerialNo() throws EbillsException{
		String spino= WFParam.getTxnNo("itstu");
		return spino;
	}
	/**
	 * 从busmsar\busmstp表中获取短信信息并组成东莞的短信报文体
	 * @param txnNo 原流水号
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveMsm(String txnNo) throws Exception{
		ConnectionManager cm = null;
		try {
			cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);	
			EbpDao dao = new EbpDao();
			Map<String,Object> mapParam = new HashMap<String,Object>();
			Map map = null;//平台生成的短信
			Map corpMap = null;
			List corpList = this.getCorpByTxn(txnNo);
			if(corpList.size()>0){
				corpMap = (Map)corpList.get(0);
				if("Y".equals(corpMap.get("SFFSDX"))){//客户为需要发送短信的客户
					mapParam.put("txnNo", txnNo);
					List<Map<String, Object>>  listtp = dao.queryByDataId("busms",EbpConstants.TABLE_TP, mapParam);
					List<Map<String, Object>>  listar = dao.queryByDataId("busms",EbpConstants.TABLE_AR, mapParam);
					if(listtp.size()>0){
						map = (HashMap)listtp.get(0);
					}else if(listar.size()>0){
						map = (HashMap)listar.get(0);
					}
					//组短信报文体
					Map msmMap = new HashMap();//东莞的短信报文体
					if(map != null){
						msmMap.put("txnNo", txnNo);
						String smsno =  (String)map.get("smsNo");
						String srno = smsno.substring(8);
						msmMap.put("srno","07"+srno);//序号，渠道控制当日不要重复一般格式：sysid(2位)+8位序号
						msmMap.put("phone", (String)map.get("mPhoneNo"));//收短信号码
						msmMap.put("msgtxt", (String)map.get("msgInfo"));//短信内容
						msmMap.put("sendtp", "1");//固定短信类型为入账短信
						if("0".equals(corpMap.get("CORPKIND"))){
							msmMap.put("accttp", "2");//账户类型为对公户
						}else{
							msmMap.put("accttp", "1");//账户类型为个人户
						}
						msmMap.put("signtp", "1");//默认为签约短信，待修改
						msmMap.put("isfree", "1");//默认为免费，待修改 
						java.util.Date dateStr = (java.util.Date)map.get("createDate");
						//SimpleDateFormat sd1 = new SimpleDateFormat("yyyy/MM/dd");
						//java.util.Date d = (java.util.Date)sd1.parseObject(dateStr);
						String rfdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateStr);
						msmMap.put("gntime", rfdate);//短信生成时间
						msmMap.put("userid", "EBP01");//操作柜员默认为报文头的操作柜员
						msmMap.put("acctty", "0");//0：存款账户 1：贷款账户2：其他
						msmMap.put("trantp", "3");//0：现金交易	 1：转帐交易	 2：还款 	3：放款	  4：催收		5：还款提示
						msmMap.put("dcmttp", "000");//凭证类型
						msmMap.put("trbrno", "5901");//交易网点默认为报文头的操作网点
						msmMap.put("febrno", "5901");//统计网点默认为报文头的操作网点
						msmMap.put("fezone", "5901");//所属分行号默认为报文头的操作网点
						dao.insertRow("TPSENDMSM", EbpConstants.TABLE_TP, msmMap);
					}
				}				
				
			}
			
			
		}  catch (EbillsException e) {
			if(cm != null){
				cm.setRollbackOnly();
			}
			throw e;
		} finally {
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
	 * 通过流水号获取客户信息
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List getCorpByTxn(String txnNo) throws Exception{		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		ConnectionManager cm = null;
		try {
			cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);				
			EbpDao gjDao = new EbpDao();			
			if(!StringUtils.isEmpty(txnNo)){
				String Sql = "select a.CORPKIND,a.SFFSDX from pacrp a,butxntp b WHERE b.TXNNO='"+ txnNo + "' AND  b.CUSTID = a.CORPNO";
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
     * 东莞银行提供加密方法
     * */
	public String getPwd(String pwd) throws Exception {
		DgOperPwd_Soft _dgoperpwd = new DgOperPwd_Soft();
		if(context.get("strtellerno")==null){
			String userId=this.getValue("userId");
			String usercode=this.getTableValue("usercode","pausr","userId = '"+userId+"'");
			this.setValue("strtellerno",usercode);
		}
		log.debug("用户名字"+context.get("strtellerno"));
		log.debug("软加密前的密文:"+pwd);
		String softpwd=	_dgoperpwd.EncPwd("07",(String)context.get("strtellerno"), pwd);
		log.debug("软加密后的密文:"+softpwd);
		return softpwd;
	}
	
	/**
	 * 根据信用证号查询出其保证金信息
	 * @param lcno 信用证号
	 */
	public void loadDeposit(String lcno){
		List<Object> paramList  = new ArrayList<Object>();
		paramList.add(0,lcno);
		EbpDao gjjsDao = new EbpDao();
		String sql = "select * from dgmgrinfoar where bizno=?";
		try {
			List<Map<String, Object>> list = gjjsDao.sqlQuery(sql, paramList);
			log.debug("result--->"+list);
			this.context.put("deposit_commit_hid", CommonUtil.ListToJson(list));
		} catch (EbillsException e) {
			e.printStackTrace();
		};
	}
	/**
	 *  付汇未发报交易初始化
	 * @param jspDiv 对应dataGrid id
	 */
	public void loadPayCheckDatas(String jspDiv){	
		EbpDao gjjsDao = new EbpDao();
		String sql = "select a.tradeno tradeNo,a.txnno txnno,a.curbizno curBizNo, a.ACCTBKSWIFT SWIFTCODE,"
				+ " c.orgname tranOrgNo,a.paycur payCur,a.payamt payAmt,a.paydate payDate,d.username"
				+ " oparater,a.ischeck isCheck "
				+ " from pbpayfo a left join butxnar b on a.txnno = b.txnno "
				+ " left join PAORG c on b.tranorgno = c.orgno  left join pausr d on d.userid = b.checkerid"
				+ " where a.isPacket ='N'"
				+ " and not exists (select txnno from pbpayfo t where t.ISREPLACEPAY = 'Y' and t.tradeno = '070202' and a.txnno =t.txnno) "
				+ " and a.tradeno !='110205'"
				+ " order by payDate desc";
		try {
			List<Map<String, Object>> list = gjjsDao.sqlQuery(sql, null);
			String canChange = "";
			//如果已经勾对的需要增加一个标志，使得其不能再页面上去掉其勾对状态
			for(int i = 0; i<list.size();i++){
				Map<String, Object> map = list.get(i);
				String isCheck = (String) map.get("ISCHECK");
				log.info("isCheck ---------->>>" + isCheck);
				if("Y".equals(isCheck)){
					canChange = "N";
				}else{
					canChange = "Y";
				}
				map.put("CANCHANGE", canChange);
			}
			log.debug(jspDiv);
			log.debug(CommonUtil.ListToJson(list));
			this.context.put(jspDiv, CommonUtil.ListToJson(list));
		} catch (EbillsException e) {
			e.printStackTrace();
		};
	}
	
	
	/**
	 * 根据发票业务号查询发票信息
	 * @param
	 */
	public void loadInvoice(String bizno){
		EbpDao gjjsDao = new EbpDao();
		List<Object> paramList = new LinkedList<Object>(); 
		paramList.add(bizno);
		StringBuffer sql = new StringBuffer();
		sql.append("select myhtno,to_char(tranDate,'YYYY-MM-DD') as tranDate,indexId,invoiceCur,invoiceAmt,state,bizNo,invoiceNo,to_char(enddate,'yyyy-MM-DD') as enddate,txnNo from INVOICEINFOfo  where bizNo =?");
		StringBuffer item = new StringBuffer();
		item.append("myhtno, tranDate,indexId,invoiceCur,invoiceAmt,state,bizNo,invoiceNo, enddate,txnNo");
		try {
			List<Map<String, Object>>  list = gjjsDao.queryBySql(sql.toString(), item.toString(), null, paramList);
			log.debug("result--->"+list);
			this.context.put("invoice_commit_hid", CommonUtil.ListToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		};
	}
	
	public void callNotice(Context context){
		SendCreditNoticeBiz biz=new SendCreditNoticeBiz();
		biz.sendNotice(context);
	}
	
/**
 *参数自定义脚本
 *@param userid
 * @throws EbillsException 
 * @throws SQLException 
 *
 */
	@SuppressWarnings("unchecked")
	public void deuserid(String sysPraINfo,String tablename) throws EbillsException, SQLException{
		if("pausr".equalsIgnoreCase(tablename)){
		  Object[] paramObj=CommonUtil.JsonToList(sysPraINfo);
		  for (int j = 0; j < paramObj.length; j++) {
			  			EbpDao gjjsDao = new EbpDao();
						Map<String,Object> mParam = (Map<String,Object>) paramObj[j];
						String  operateType = (String) mParam.get("status");
						String  userid = (String) mParam.get("PAUSR_userId");
						String  userState = (String) mParam.get("PAUSR_userState");
						String opuserid = (String) mParam.get("userId");
						log.info("操作用户为" + opuserid);
						log.info("queryRet-------------------->>>"+operateType);
						if("update".equalsIgnoreCase(operateType)){
						log.info("queryRet-------------------->>>"+userState);
							if("2".equals(userState)){
								List<Object> paramList  = new LinkedList<Object>();
								paramList.add(userid);
								String sql="delete from pausrrol where USERID=?";
								String sqlid="delete from pausrper where USERID=?";
								try { 	
									gjjsDao.execute(sql, paramList);
									gjjsDao.execute(sqlid, paramList);
									//List<Map<String, Object>> list = gjjsDao.sqlQuery(sql, paramList);
									//List<Map<String, Object>> listt = gjjsDao.sqlQuery(sqlid, paramList);
									//System.out.println(list);
									//System.out.println(listt);
								} catch (EbillsException e) {
									e.printStackTrace();
								};
								
							}
						}
		  }
		  }	
		  else if("paorg".equalsIgnoreCase(tablename)){
			  Object[] paramObj=CommonUtil.JsonToList(sysPraINfo);
			  for (int j = 0; j < paramObj.length; j++) {
					Map<String,Object> mParam = (Map<String,Object>) paramObj[j];
					String  operateType = (String) mParam.get("status");
					String  orgNo = (String) mParam.get("PAORG_orgNo");
					String  DQDM = (String) mParam.get("DQDM");
					String  JGXH = (String) mParam.get("JGXH");
					String  JGDM = (String) mParam.get("JGDM");
					String  sifisid=(String) mParam.get("PAORG_sifisid");
					if("add".equalsIgnoreCase(operateType)&&!"".equals(sifisid)||
							"update".equalsIgnoreCase(operateType)&&!"".equals(sifisid)){
						List<Object> paramList  = new LinkedList<Object>();
						paramList.add(0,orgNo);
						paramList.add(1,DQDM);
						paramList.add(2,JGXH);
						paramList.add(3,JGDM);
						List<Object> paramListt=new LinkedList<Object>();
						paramListt.add(0,orgNo);
						EbpDao gjjsDao = new EbpDao();
						String sql1="delete from paorgcd where bankno=?";
						String sql="insert into paorgcd (BANKNO, DQDM, JGXH, JGDM)"
								+"values (?,?,?,?)";
						try {
							gjjsDao.execute(sql1, paramListt);	
							gjjsDao.execute(sql, paramList);
						} catch (EbillsException e) {
							e.printStackTrace();
						};
					}
				   if("delete".equalsIgnoreCase(operateType)||
						   "update".equalsIgnoreCase(operateType)&&"".equals(sifisid)){
					 List<Object> paramList=new LinkedList<Object>();
					 paramList.add(0,orgNo);
					 EbpDao gjjsDao=new EbpDao();
					 String sql="delete from paorgcd where bankno=?";
					 try{
						 gjjsDao.execute(sql, paramList);						 
					 }catch(EbillsException e){
						 e.printStackTrace();
					 }				 
				 }
		/*		   if("update".equalsIgnoreCase(operateType)){
					   List<Object> paramList=new LinkedList<Object>();
					   paramList.add(0,DQDM);
						paramList.add(1,JGXH);
						paramList.add(2,JGDM);
						paramList.add(3,orgNo);
						EbpDao gjjsDao=new EbpDao();
						String  sql="update paorgcd set DQDM=?,JGXH=?,JGDM=? where bankno=? ";
						try{
							gjjsDao.execute(sql, paramList);	
						}catch(EbillsException e){
							e.printStackTrace();
						};
				   }*/
			  }
		  }
		
		  else if("parol".equalsIgnoreCase(tablename)){
			  Object[] paramObj=CommonUtil.JsonToList(sysPraINfo);
			  for (int j = 0; j < paramObj.length; j++) {
				  			EbpDao gjjsDao = new EbpDao();
							Map<String,Object> mParam = (Map<String,Object>) paramObj[j];
							String  operateType = (String) mParam.get("status");
							if("delete".equalsIgnoreCase(operateType)){
								int  userid =(Integer) mParam.get("parol_roleId");
								List<Object> paramList  = new LinkedList<Object>();
								paramList.add(userid);
								String sql="delete from pausrrol where USERID=?";
								String sqlid="delete from pausrper where USERID=?";
								try { 	
									gjjsDao.execute(sql, paramList);
									gjjsDao.execute(sqlid, paramList);
									
									//删除角色机构表
									Map<String,Object> rolOrgParam = new HashMap<String, Object>();
									rolOrgParam.put("roleid", mParam.get("parol_roleId"));
									gjjsDao.deleteRow("parolorg", null, rolOrgParam);
									
								} catch (EbillsException e) {
									throw  e;
								};
							}
			  }
			  }	
		  else if("userRoleAuth".equalsIgnoreCase(tablename)){
			  Object[] paramObj=CommonUtil.JsonToList(sysPraINfo);
			  for (int j = 0; j < paramObj.length; j++) {
		  			EbpDao gjjsDao = new EbpDao();
					Map<String,Object> mParam = (Map<String,Object>) paramObj[j];
					String  operateType = (String) mParam.get("status");
					String  userid = (String) mParam.get("userId");
					String  orgno = (String) mParam.get("orgNo");
					if("delete".equalsIgnoreCase(operateType)){
					System.out.print("用户id==》"+userid+"机构编号==》"+orgno);
					if(!"".equals(userid)&&userid!=null){
					List<Object> paramList  = new LinkedList<Object>();
					paramList.add(userid);
					String sql="delete from pausrcfg where USERID=?";	
					try { 	
						gjjsDao.execute(sql, paramList);
					} catch (EbillsException e) {
						e.printStackTrace();
					};	
					}
				}
			}

		}
		else if ("paorgrules".equalsIgnoreCase(tablename)) {
			Object[] paramObj = CommonUtil.JsonToList(sysPraINfo);
			for (int j = 0; j < paramObj.length; j++) {
				EbpDao gjjsDao = new EbpDao();
				Map<String, Object> mParam = (Map<String, Object>) paramObj[j];
				String operateType = (String) mParam.get("status");
				String orgno = (String) mParam.get("PAORGRULES_orgNo");
				String rolOrgNo = (String) mParam.get("PAORGRULES_rolOrgNo");
				if ("update".equalsIgnoreCase(operateType)&&orgno!=rolOrgNo||
						"add".equalsIgnoreCase(operateType)&&orgno!=rolOrgNo) {
					LinkedList<Object> paramLisst = new LinkedList<Object>();
					paramLisst.add(orgno);
					String sqlrole = "delete  from parolorg a where a.orgno=?";
					try {
						gjjsDao.execute(sqlrole, paramLisst);
					} catch (EbillsException e) {
						throw e;
					}
					String sql ="select roleid from parolorg r where r.orgno=?";
                 	List<Object> paramList  = new ArrayList<Object>();
					paramList.add(rolOrgNo);
					List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
					log.info("queryRet-------------------->>>"+queryRet);
					for(int i=0;i<queryRet.size();i++){ 
						log.info("queryRet-------------------->>>"+queryRet.get(i).get("ROLEID"));
			         	LinkedList<Object> paramListt = new LinkedList<Object>();
			         	paramListt.add(0, queryRet.get(i).get("ROLEID"));
			         	paramListt.add(1, orgno);
						String sql1="insert into parolorg (ROLEID, ORGNO)"
								+ "values (?,?)";
						try {
							gjjsDao.execute(sql1, paramListt);
						} catch (EbillsException e) {
							e.printStackTrace();
						}
					 }
					}
				
				if ("delete".equalsIgnoreCase(operateType)) {
					LinkedList<Object> paramLisst = new LinkedList<Object>();
					paramLisst.add(orgno);
					String sqlrole = "delete  from parolorg a where a.orgno=?";
					try {
						gjjsDao.execute(sqlrole, paramLisst);
					} catch (EbillsException e) {
						throw e;
					}
				}
			}

		}
		else if ("paaccfo".equalsIgnoreCase(tablename)) {
			Object[] paramObj = CommonUtil.JsonToList(sysPraINfo);
			for (int j = 0; j < paramObj.length; j++) {
				EbpDao gjjsDao = new EbpDao();
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				String serverdate = sdf.format(date);
				Map<String, Object> mParam = (Map<String, Object>) paramObj[j];
				String operateType = (String) mParam.get("status");
				String acctNo = (String) mParam.get("PAACCFO_acctNo");
				String curSign = (String) mParam.get("PAACCFO_curSign");
				String custBankNoName = (String) mParam.get("PAACCFO_custBankNoName");
				if ("add".equalsIgnoreCase(operateType)) {
					String sql ="select * from CLCASHFO B WHERE B.ACCTNO=?";
                 	List<Object> paramList  = new ArrayList<Object>();
					paramList.add(acctNo);
					List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
					log.info("queryRet-------------------->>>"+queryRet.size());
					if(!(queryRet!=null&&queryRet.size()>0)){
						log.info("queryRet-------------------->>>"+queryRet.size());
			         	LinkedList<Object> paramListt = new LinkedList<Object>();
			         	paramListt.add(0,custBankNoName);
			         	paramListt.add(1, acctNo);
			         	paramListt.add(2, curSign);
			         	paramListt.add(3, serverdate);
			         	paramListt.add(4, date);
						String sql1="insert into CLCASHFO (ACCBKNO, ACCTNO, CASHCCY, VALUEDATE, CASHCCYAMT, CASHBAL, FRZBAL, TRANAMT, UPDATETIME, FILENO)"
								+ "values (?,?,?,to_date(?, 'dd-mm-yyyy'),'0.00','0.00','0.00','0.00',?,'')";
						try {
							gjjsDao.execute(sql1, paramListt);
						} catch (EbillsException e) {
							e.printStackTrace();
						}	
						
					}
					
					String sql3 ="select * from pastatementno B WHERE B.ACCOUNT=?";
					List<Object> paramListss  = new ArrayList<Object>();
					paramListss.add(acctNo);
					List<Map<String,Object>> queryRett = gjjsDao.sqlQuery(sql3, paramListss);
					if(!(queryRett!=null&&queryRett.size()>0)){
					LinkedList<Object>paramLists=new LinkedList<Object>();
					paramLists.add(acctNo);
					String sql4="insert into pastatementno (ACCOUNT, STATEMENTNO, SEQUENCENO)"
							 +"values(?,'1','1')";
					try {
						gjjsDao.execute(sql4, paramLists);
					} catch (EbillsException e) {
						e.printStackTrace();
					}	
					}
				}
			}

		}
		 else if ("parolorg".equalsIgnoreCase(tablename)) {
				Object[] paramObj = CommonUtil.JsonToList(sysPraINfo);
				for (int j = 0; j < paramObj.length; j++) {
					EbpDao gjjsDao = new EbpDao();
					Map<String, Object> mParam = (Map<String, Object>) paramObj[j];
					String operateType = (String) mParam.get("status");
					String roleid = (String) mParam.get("roleid");
					String orgno = (String) mParam.get("orgno");
					if ("delete".equalsIgnoreCase(operateType)) {
							LinkedList<Object> paramList = new LinkedList<Object>();
							paramList.add(orgno);
							paramList.add(roleid);
							String sql = "delete from pausrrol where ORGNO=? and ROLEID=?";
							try {
								gjjsDao.execute(sql, paramList);
							} catch (EbillsException e) {
								e.printStackTrace();
							}
					}
				}
			}
	}

	@SuppressWarnings("unchecked")
	public void setRolOrgDataBase(String sysPraINfo,String roleId) throws EbillsException, SQLException{
		  EbpDao gjjsDao = new EbpDao();
		  Map<String,Object> mParam = new HashMap<String, Object>();
		  mParam.put("roleid", roleId);
		  gjjsDao.deleteRow("parolorg", null, mParam);
		  
		  Object[] paramObj=CommonUtil.JsonToList(sysPraINfo);
		  for (int j = 0; j < paramObj.length; j++) {
			  Map<String,Object> marParam = (Map<String,Object>) paramObj[j];
			  if(marParam.get("status").equals("delete")){
				  marParam.put("roleid", roleId);
				  gjjsDao.deleteRow("parolorg", null, marParam);
			  }
		  }
	}
	/**
	 * 贸易融资贷款放款交易
	 */
	public void callItsccr(Context context){
		ItsccrBiz biz=new ItsccrBiz();
		biz.saveItsccr(context);
	}
	/**
	 * 保存通知交易
	 * @throws EbillsException 
	 */
	public void saveNotice(Context context) throws EbillsException{
		SendCreditNoticeBiz biz=new SendCreditNoticeBiz();
		biz.saveNotice(context);
	}
	
	/**
	 *统计结售汇信息 
	 */
	
	public void saveForex(Context context){
		ForexDao forexDao=new ForexDao();
		forexDao.saveForex(context);
	}

	
	/**
	 * 手工冲账对报文、申报信息处理
	 * @throws Exception 
	 */
	public void buremdebtPass() throws Exception{
		
		EbpDao gjjsDao = new EbpDao();
		List<Object> paramList  = new LinkedList<Object>();
		String srhTxnNo = (String)CommonUtil.getValFromContext(context,"BUREMDEBT_srhTxnNo");//流水号
		//手工冲账复核通过的时候将所发报文废弃
		paramList.clear();
		String sql = "update MSSDMSGFO set STATE='AB' where TXNNO=?";
		paramList.add(srhTxnNo);
		gjjsDao.execute(sql, paramList);
		
		//更新申报信息  查询该笔交易索做的申报类型 将A类申报 转为D类申报  
		String querySql = "select DCLKIND DCLKIND from dldclbscfo where txnno =?";
		List<Map<String,Object>> dclKindList = gjjsDao.sqlQuery(querySql, paramList);
		for(int i =0;i<dclKindList.size(); i++){
				String mdEnName = (String) dclKindList.get(i).get("DCLKIND");
				String sysDate = CommonUtil.getSysFld("workDate");//当前系统日期
				if(mdEnName.length()>0){
					paramList.clear();
					//更新申报信息表信息
					String upDateDldclbsc ="update dldclbscfo set bscstate='5',dclstate='5',mgestate='5',TRANDATE = to_date(?,'yyyy-mm-dd'),INITDATE = to_date(?,'yyyy-mm-dd') where TXNNO=?";
					paramList.add(sysDate);
					paramList.add(sysDate);
					paramList.add(srhTxnNo);
					gjjsDao.execute(upDateDldclbsc, paramList);
					paramList.clear();
					String sql2 ="select FILETYPE FILETYPE from dldclmd where MDENNAME =?";
					paramList.add(mdEnName);
					//查询出申报类型对应的表名称  SB+FILETYPE+FO
					List<Map<String,Object>> fileTypeList = gjjsDao.sqlQuery(sql2, paramList);
					paramList.clear();
					paramList.add(srhTxnNo);
					for(int j =0 ;j<fileTypeList.size(); j++){
						String fileType = (String) fileTypeList.get(j).get("FILETYPE");
						fileType ="SB"+fileType+"FO";
						String updateSql = "update " + fileType + " set ACTIONTYPE='D' where TXNNO=?";
						gjjsDao.execute(updateSql, paramList);
					}
				}
		}
	
		//删除报文修改任务队列 20150408
		String bizNo =(String) CommonUtil.getValFromContext(context,"BUREMDEBT_srhBizNo");
		String msgId = getTableValue("f.msgId", "butxntp t,MSSDMSGFO f", "f.bizNo ='"+bizNo+"' and t.tradeno ='030609' and t.fileno = f.msgid");
		String mtxnNo = getTableValue("f.txnNo", "butxntp t,MSSDMSGFO f", "f.bizNo ='"+bizNo+"' and t.tradeno ='030609' and t.fileno = f.msgid");
	    ClearPubAbstract.delTask(msgId, mtxnNo);
		
		// 回滚业务信息
		String txnNo = (String) context.get(EbpConstants.TXNSERIALNO);
		RollBack.doRollBack(txnNo, srhTxnNo);
		

	}
	
	/**
	 * 付汇未发报勾对生成账务
	 * @param txnNoArr
	 * @throws Exception 
	 */
	public void createPayCheckVoucher(String txnNoArr){
		try {
			Boolean bPreview = (Boolean)context.get(GeneralConstants.PREVIEWFLAG);
			log.info("bPreview-------------------->>>"+bPreview);
			String[] stringArr = txnNoArr.split(";");//将前台传入字符串拆分
			log.info("txnNo-------------------->>>"+txnNoArr);
			String tranOrgNo = stringArr[0];//第一个参数是当前执行机构
			String erroTxnno ="";
			String valueTxnno="";
			List<Map<String,Object>> acctVouList = new LinkedList<Map<String,Object>>();//账务list
			EbpDao dao = new EbpDao();
			for(int i = 1; i<stringArr.length ; i++){
				String txnNo = stringArr[i];
				Map<String,Object> mParam = new HashMap<String, Object>();
				mParam.put(EbpConstants.TXNSERIALNO, txnNo);
				mParam.put("state", "0");
				List<Map<String,Object> > acctVouListTemp  = dao.queryByDataId(GeneralConstants.ACCVOUID, EbpConstants.TABLE_AR, mParam);
				if(null!=acctVouListTemp&&acctVouListTemp.size()>0){
					for(int j =0; j<acctVouListTemp.size(); j++){
						Map<String,Object> mapTemp = acctVouListTemp.get(j);
						valueTxnno =(String) mapTemp.get("txnNo");
						log.info("mapTemp-------------------->>>"+mapTemp.toString());
						if("ZA00000041".equals(mapTemp.get("acctTypeNo"))){//如果存在汇出汇款这条分录
							String dcFlag = (String) mapTemp.get("dcFlag");
							String oldBizNo = (String) mapTemp.get("bizNo");
							if("C".equals(dcFlag)){
								mapTemp.put("dcFlag", "D");										//将贷 变为借
								mapTemp.put("orgNo", tranOrgNo);								//机构号
								mapTemp.put("accVouNo", getVourSerialNo());	
								mapTemp.put("versionNo", context.get(EbpConstants.VERSIONNO));	
								mapTemp.put("txnNo", context.get(EbpConstants.TXNSERIALNO));	
								mapTemp.put("state",EbpConstants.STATE_HANDLE);
								mapTemp.put("bizNo", context.get("BUPAYCHECK_bizNo"));	
								mapTemp.put("accVouName", "BUPAYCHECK");
								
								String sql = "select ACCTBKSWIFT from pbpayfo where txnno =?";
								List<Object> paramList  = new LinkedList<Object>();
								paramList.add(txnNo);
								List<Map<String,Object>> querySet = dao.sqlQuery(sql, paramList);
								if(null!=querySet&&querySet.size() >0){//走swift清算途径时候产生分录
									String swiftCode = (String) querySet.get(0).get("ACCTBKSWIFT");
									double amt =  (Double) mapTemp.get("amount");	//金额
									String cur = (String) mapTemp.get("ccy");		//币种
									String indexNo = (String) mapTemp.get("indexNo");//顺序号
									
									Map<String,Object> mapTemp_1 = new HashMap<String,Object>();//第二条分录 贷 166
									mapTemp_1.put("amount", amt);
									mapTemp_1.put("ccy", cur);
									mapTemp_1.put("dcFlag", "C");									
									mapTemp_1.put("orgNo", tranOrgNo);								
									mapTemp_1.put("accVouNo", getVourSerialNo());							
									mapTemp_1.put("versionNo", context.get(EbpConstants.VERSIONNO));	
									mapTemp_1.put("txnNo", context.get(EbpConstants.TXNSERIALNO));
									mapTemp_1.put("indexNo", indexNo);
									mapTemp_1.put("state",EbpConstants.STATE_HANDLE);
									mapTemp_1.put("bizNo", context.get("BUPAYCHECK_bizNo"));
									mapTemp_1.put("accVouName", "BUPAYCHECK");
									
									String sql2 = "select a.custBankNoName, a.acctNo, a.subject ,b.accttypeenname "
											 	  + "from PAACCFO a ,paaccttyp b "
											 	  +	"where a.accttypeno = b.accttypeno and a.custBankNoName = ? and a.cursign =?";
									paramList.clear();
									querySet.clear();
									paramList.add(swiftCode);
									paramList.add(cur);
									querySet = dao.sqlQuery(sql2, paramList);
									if(null!= querySet&&querySet.size()>0){
										String subject = (String) querySet.get(0).get("SUBJECT");//科目
										String acctNo = (String) querySet.get(0).get("ACCTNO");//账号
										String accttypeenname = (String) querySet.get(0).get("ACCTTYPEENNAME");//账务类型
										if(acctNo.length() ==0)
											throw new EbillsException(className, 1,new String[]{oldBizNo,swiftCode,cur});
										mapTemp_1.put("subject", subject);
										mapTemp_1.put("acctNo", acctNo);
										mapTemp_1.put("acctNoName", accttypeenname);
										
										acctVouList.add(mapTemp);	//添加第一条分录 借  汇出汇款
										acctVouList.add(mapTemp_1); //添加第二条分录 贷  116
									}else{
										throw new EbillsException(className, 1,new String[]{oldBizNo,swiftCode,cur});
									}
								}
							}
							
						}else{
							if(!valueTxnno.equals(mapTemp.get("txnNo"))){
								erroTxnno =(String) mapTemp.get("txnNo");
							}else{
								erroTxnno = "";
							}
								
						}

					}
				}
				if (null == acctVouList || acctVouList.size() == 0||erroTxnno.length()>0) {
					String erroBizNo = "";
					String erroSwiftcode = "";
					String erroCur = "";
					if (stringArr.length > 1&&erroTxnno.length()==0) {
						String txnno = stringArr[i];
						erroBizNo = getTableValue("curBizNo", "pbpayfo",
								"txnno ='" + txnno + "'");
						erroSwiftcode = getTableValue("acctBkSwift", "pbpayfo",
								"txnno ='" + txnno + "'");
						erroCur = getTableValue("payCur", "pbpayfo", "txnno ='"
								+ txnno + "'");
					}else{
						erroBizNo = getTableValue("curBizNo", "pbpayfo",
								"txnno ='" + erroTxnno + "'");
						erroSwiftcode = getTableValue("acctBkSwift", "pbpayfo",
								"txnno ='" + erroTxnno + "'");
						erroCur = getTableValue("payCur", "pbpayfo", "txnno ='"
								+ erroTxnno + "'");
					}
					throw new EbillsException(className, 1,new String[]{erroBizNo,erroSwiftcode,erroCur});
				}
			}
			
			if(null==acctVouList||acctVouList.size()==0){
				String erroBizNo = "";
				String erroSwiftcode = "";
				String erroCur = "";
				if(stringArr.length>1){
					String  txnno = stringArr[1];
					erroBizNo = getTableValue("curBizNo", "pbpayfo", "txnno ='"+ txnno +"'");
					erroSwiftcode = getTableValue("acctBkSwift", "pbpayfo", "txnno ='"+ txnno +"'");
					erroCur = getTableValue("payCur", "pbpayfo", "txnno ='"+ txnno +"'");
				}
				throw new EbillsException(className,1,new String[]{erroBizNo,erroSwiftcode,erroCur});
			}
			formatVouList(acctVouList);
			context.put(GeneralConstants.LOAD_ACCOUNT_VOUCHER_FORUPDATE, acctVouList);
			if(null == bPreview || !bPreview){
				for(int i =0 ; i<acctVouList.size();i++){
					Map<String,Object> map = acctVouList.get(i);
					map.put("bizNo", context.get("BUPAYCHECK_bizNo"));
					map.put("state",EbpConstants.STATE_HANDLE);
					log.info("map-------------------->>>"+map.toString());
				}
				dao.insertRow(GeneralConstants.ACCVOUID, EbpConstants.TABLE_TP, acctVouList);
			}
		} catch (EbillsException e) {
			errMsg = e.getMessage(language);
		} 
	}
	
	private static String getVourSerialNo()throws EbillsException {
		SerialNoFactory snf =  new SerialNoFactory();
		DataInfo di = DataFactory.getDataInfoFile(GeneralConstants.ACCVOUID);
		String serialNo = snf.getSerialNo(di.getTablename(),10);
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		String ymd = Integer.toString(year * 10000 + month * 100 + day);
		return "AC"+ymd+serialNo;
	}
	
	/**
	 * 付汇未发报勾对提交更新
	 * @throws EbillsException 
	 */
	public void updateCheckBizNo() throws EbillsException{
		String bizNoListToY = (String) context.get("BUPAYCHECK_bizNoListToY");
		String bizNoListtoN = (String) context.get("BUPAYCHECK_bizNoListToN");
		EbpDao dao = new EbpDao();
		//勾对
		if(null!=bizNoListToY&&bizNoListToY.length()>0){
			String [] bizNos = bizNoListToY.split(";");
			String sql = "update pbpayFO set ISCHECK ='Y' where bizNo in (";
			for(int i =0 ; i < bizNos.length; i++){
				if(i == bizNos.length -1){
					sql +=  bizNos[0] + ")";
				}else{					
					sql +=  bizNos[0] + ",";
				}
			}
			dao.execute(sql, null);
		}
		//取消勾对
		if(null!=bizNoListtoN&&bizNoListtoN.length()>0){
			String [] bizNos = bizNoListtoN.split(";");
			String sql = "update pbpayFO set ISCHECK ='N' where bizNo in (";
			for(int i =0 ; i < bizNos.length; i++){
				if(i == bizNos.length -1){
					sql += bizNos[0] + ")";
				}else{					
					sql += bizNos[0] + ",";
				}
			}
			dao.execute(sql, null);
		}
	}
	
	public void retdeal() throws Exception{
		EbpDao gjjsDao = new EbpDao();
		List<Object> paramList  = new LinkedList<Object>();
		String bizno = (String)CommonUtil.getValFromContext(context,"butxn_curtBizNo");//流水号
		String txnNo=this.getTableValue("txnno","butxnar","curtbizno = '"+bizno+"'");
		paramList.add(txnNo);
		//更新申报信息  查询该笔交易索做的申报类型 将A类申报 转为D类申报  
				String querySql = "select DCLKIND DCLKIND from dldclbscfo where txnNo =?";
				List<Map<String,Object>> dclKindList = gjjsDao.sqlQuery(querySql, paramList);
				for(int i =0;i<dclKindList.size(); i++){
						String mdEnName = (String) dclKindList.get(i).get("DCLKIND");
						String sysDate = CommonUtil.getSysFld("workDate");//当前系统日期
						if(mdEnName.length()>0){
							paramList.clear();
							//更新申报信息表信息
							String upDateDldclbsc ="update dldclbscfo set bscstate='5',dclstate='5',mgestate='5',TRANDATE = to_date(?,'yyyy-mm-dd'),INITDATE = to_date(?,'yyyy-mm-dd') where TXNNO=?";
							paramList.add(sysDate);
							paramList.add(sysDate);
							paramList.add(txnNo);
							gjjsDao.execute(upDateDldclbsc, paramList);
							paramList.clear();
							String sql2 ="select FILETYPE FILETYPE from dldclmd where MDENNAME =?";
							paramList.add(mdEnName);
							//查询出申报类型对应的表名称  SB+FILETYPE+FO
							List<Map<String,Object>> fileTypeList = gjjsDao.sqlQuery(sql2, paramList);
							paramList.clear();
							paramList.add(txnNo);
							for(int j =0 ;j<fileTypeList.size(); j++){
								String fileType = (String) fileTypeList.get(j).get("FILETYPE");
								fileType ="SB"+fileType+"FO";
								String updateSql = "update " + fileType + " set ACTIONTYPE='D' where TXNNO=?";
								gjjsDao.execute(updateSql, paramList);
							}
						}
				}
	}
	public String formatAmt(String ccy,String amt){
		DecimalFormat myformat;
		if("JPY".equals(ccy)){
			myformat=new DecimalFormat("#,###");
		}else{
			myformat=new DecimalFormat("#,###.00");
			if(Double.parseDouble(amt)<1){
				myformat=new DecimalFormat("0.00");
			}
		}
		amt=myformat.format(Double.parseDouble(amt));
		System.out.println("amt===="+amt);
		return amt;
	}
	public String formatPoint(String point){
		DecimalFormat myformat=new DecimalFormat("#.00%");
		String amt=myformat.format(Double.parseDouble(point));
		System.out.println("amt===="+amt);
		return amt;
	}
	/**
	 * 退回处理特有
	 * @throws Exception 
	 */
	public void thcldy() throws Exception{
		EbpDao jsDao=new EbpDao();
		BaseScript bs = new BaseScript();
		String bizno = (String)CommonUtil.getValFromContext(context,"butxn_curtBizNo");//业务编号
		String txnNo=bs.getTableValue("txnno", "butxnar", "curtbizno ='"+bizno+"'");
		String sql="select msgId from "+DataFactory.getDataInfoFile(EbpConstants.TPSENDMSGID).getTablename()+" where txnno=?";
		List<Object> inputList = new LinkedList<Object>();
		inputList.add(txnNo);
		List<Map<String,Object>> list=jsDao.queryBySql(sql, "msgId", "",inputList);
		if(list!=null&&!list.isEmpty()){
			for(Map<String,Object> map:list){
				String msgId = (String)map.get("msgId");
				ClearPubAbstract.delTask(msgId, ClearConstants.PACKETS_MODIFY_TRADENO);
			}
		}
	}
	/**
	 * 开启trc新任务
	 * 
	 * @param txnNo
	 * @throws EbillsException
	 */
	public void startTrcNewTask(String txnNo) throws EbillsException {
		TraceInfo.updateTrcState(txnNo);
	}
	
	/**
	 * 对产生账务进行格式化
	 * @param acctVouList
	 * @throws EbillsException 
	 */
	private void formatVouList(List<Map<String, Object>> acctVouList) throws EbillsException {
		//根据东莞银行要求，需要将账务名称显示为核心开户户名
		for(int i =0;i<acctVouList.size();i++){
			String acctNo = (String) acctVouList.get(i).get("acctNo");
			String coreName = getTableValue("coreacctname", "paaccfo", "acctNo ='" + acctNo +"'");
			if(null!=coreName&&!"".equals(coreName)){
				acctVouList.get(i).put("acctNoName", coreName);
			}
		}
		//还未确定是否需要合并账务
	}
	
	@SuppressWarnings("unchecked")
	public void saveUsrLog(String sysPraINfo, String opUserId, Context context,String opstate)
			throws EbillsException, SQLException {
		Object[] paramObj = CommonUtil.JsonToList(sysPraINfo);
		for (int j = 0; j < paramObj.length; j++) {
			Map<String, Object> mParam = (Map<String, Object>) paramObj[j];
			String operateType = (String) mParam.get("status");
			String userid = (String) mParam.get("PAUSR_userId");
			String userCode = (String) mParam.get("PAUSR_userCode");
			String userState = (String) mParam.get("PAUSR_userState");
			String userName = (String) mParam.get("PAUSR_userName");
			String userLevel = (String) mParam.get("PAUSR_userLevel");
			String belongOrgno = (String) mParam.get("PAUSR_belongOrgno");
			String userPhone = (String) mParam.get("PAUSR_userPhone");
			String opuserid = (String) mParam.get("userId");
			String userSex=(String) mParam.get("PAUSR_userSex");
			UsrLogUtil usrLogUtil = new UsrLogUtil();
			List<Map<String, Object>> userList = usrLogUtil.getLcList(userid);
			String dbuserName = "";
			String dbbelongOrgno = "";
			String dbuserLevel = "";
			String dbuserState = "";
			String dbuserPhone = "";
			String dbuserSex = "";
			if (userList.size() > 0) {
				dbuserName = (String) userList.get(0).get("userName");
				dbbelongOrgno = (String) userList.get(0).get("belongOrgno");
				dbuserLevel = (String) userList.get(0).get("userLevel");
				dbuserState = (String) userList.get(0).get("userState");
				dbuserPhone = (String) userList.get(0).get("userPhone");
				dbuserSex = (String) userList.get(0).get("userSex");
			}

			log.info("操作用户为" + opuserid);
			log.info("queryRet-------------------->>>" + operateType);
			StringBuffer operatedtls = new StringBuffer();
			String opUser = "";
			String stepNo = (String) context.get("stepNo");
			if ("1001".equals(stepNo)) {
				opUser = "经办人:";
			} else if ("3001".equals(stepNo)) {
				opUser = "经办更正人:";
			} else if ("2001".equals(stepNo)) {
				String txnNo = (String) context.get("txnNo");
				List<Map<String, Object>> txnList = UsrLogUtil
						.getCheckUser(txnNo);
				String handleid = "";
				if (txnList.size() > 0) {
					handleid = (String) txnList.get(0).get("handlerid");
				}
				if (handleid != null && handleid != "") {
					if("pass".equals(opstate)){
						opUser = "复核人:" + UsrLogUtil.getUserNameById(opUserId)
								+ "复核通过了经办人:" + UsrLogUtil.getUserNameById(handleid) + "经办的信息,详情如下</br>";
						opUser = opUser + "经办人:" + UsrLogUtil.getUserNameById(handleid);
					}else if("fault".equals(opstate)){
						opUser = "复核人:" + UsrLogUtil.getUserNameById(opUserId)
								+ "复核打回了经办人:" + UsrLogUtil.getUserNameById(handleid) + "经办的信息,详情如下</br>";
						opUser = opUser + "经办人:" + UsrLogUtil.getUserNameById(handleid);
					}
				} else {
					opUser = "复核人:";
				}

			}
			if ("add".equals(operateType)) {
				operatedtls.append(opUser);
				operatedtls.append(UsrLogUtil.getUserNameById(opUserId)
						+ "添加了用户</br>用户编码为:" + userCode + "用户名称为:" + userName
						+ "</br>");
				UsrLogUtil.saveUsrLog(opUserId, "1", userid,
						operatedtls.toString());
			} else if ("update".equals(operateType)) {
				operatedtls.append(opUser);
				if("2001".equals(stepNo)){
					operatedtls.append( "修改了用户编码为:" + userCode + "用户名称为：" + dbuserName
							+ "的用户信息" + "</br>");
				}else{
					operatedtls.append(UsrLogUtil.getUserNameById(opUserId)
							+ "修改了用户编码为:" + userCode + "用户名称为：" + dbuserName
							+ "的用户信息" + "</br>");
				}

				if (!userState.equals(dbuserState)) {
					operatedtls.append("将原状态:");
					operatedtls.append(CommonUtil.getI18NValue("PAUSR",
							"USERSTATE", dbuserState, "zh_CN"));
					operatedtls.append("改为:");
					operatedtls.append(CommonUtil.getI18NValue("PAUSR",
							"USERSTATE", userState, "zh_CN"));
					operatedtls.append("</br>");
				}
				if (!userName.equals(dbuserName)) {
					operatedtls.append("将原名称:");
					operatedtls.append(dbuserName);
					operatedtls.append("改为:");
					operatedtls.append(userName);
					operatedtls.append("</br>");
				}
				if (!userLevel.equals(dbuserLevel)) {
					operatedtls.append("将原级别:");
					operatedtls.append(CommonUtil.getI18NValue("PAUSR",
							"USERLEVEL", dbuserLevel, "zh_CN"));
					operatedtls.append("改为:");
					operatedtls.append(CommonUtil.getI18NValue("PAUSR",
							"USERLEVEL", userLevel, "zh_CN"));
					operatedtls.append("</br>");
				}
				if (!belongOrgno.equals(dbbelongOrgno)) {
					operatedtls.append("将原机构:");
					operatedtls.append(UsrLogUtil.getOrgById(dbbelongOrgno));
					operatedtls.append("改为:");
					operatedtls.append(UsrLogUtil.getOrgById(belongOrgno));
					operatedtls.append("</br>");
				}
				if (!userPhone.equals(dbuserPhone)) {
					if("".equals(dbuserPhone)||dbuserPhone==null){
						operatedtls.append("原电话为空,");
						operatedtls.append("改为:");
						if("".equals(userPhone)||userPhone==null){
							operatedtls.append("空</br>");
						}else{
							operatedtls.append(userPhone);
							operatedtls.append("</br>");
						}

					}else{
						operatedtls.append("原电话:");
						operatedtls.append(dbuserPhone);
						operatedtls.append("改为:");
						if("".equals(userPhone)||userPhone==null){
							operatedtls.append("空</br>");
						}else{
							operatedtls.append(userPhone);
							operatedtls.append("</br>");
						}
					}

				}
				if (!userSex.equals(dbuserSex)) {
					operatedtls.append("将原性别:");
					operatedtls.append(CommonUtil.getI18NValue("PAUSR",
							"USERSEX", userSex, "zh_CN"));
					operatedtls.append("改为:");
					operatedtls.append(CommonUtil.getI18NValue("PAUSR",
							"USERSEX", dbuserSex, "zh_CN"));
					operatedtls.append("</br>");
				}
				UsrLogUtil.saveUsrLog(opUserId, "7", userid,
						operatedtls.toString());
			} else if ("delete".equals(operateType)) {
				if ("2".equals(userState)) {
					operatedtls.append(opUser);
					operatedtls.append(opUserId);
					;
					operatedtls.append("删除了");
					operatedtls.append(userid);
					UsrLogUtil.saveUsrLog(opUserId, "10", userid,
							operatedtls.toString());
				}
			}
		}

	}	
}
