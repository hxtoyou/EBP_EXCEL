package com.ebills.product.script;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.bussprocess.exception.InvalidArgumentException;
import org.apache.commons.bussprocess.exception.ObjectNotFoundException;

import com.eap.core.EAPConstance;
import com.ebills.product.action.clear.ClearMsgInfoAction;
import com.ebills.product.action.clear.TallyMsgInfoAction;
import com.ebills.product.component.BalanceInfo;
import com.ebills.product.component.MatureHintInfo;
import com.ebills.product.component.SmsInfo;
import com.ebills.product.component.voucher.AccountVoucherInfo;
import com.ebills.script.ExtendScript;
import com.ebills.util.EbillsException;
import com.ebills.utils.CommonUtil;
import com.ebills.utils.EbpConstants;
import com.ebills.utils.EbpDao;
import com.ebills.utils.StaticCommon;
import com.ebills.wf.util.AuthHelp;

/**
 * <code>ps</code>从BaseScript或者ExtendScript中移植出来的脚本
 * @author stuu
 *
 */
public class ps extends ExtendScript {
	/**
	 * 创建短信
	 * @param name 断线模版名称
	 * @param phoneNo 手机号
	 * @throws EbillsException
	 */
	public void createSms(String name, String phoneNo) throws EbillsException{
		try {
			SmsInfo smsInfo = new SmsInfo();
			smsInfo.init(StaticCommon.ComponentConfig_CFGINFO.get(SmsInfo.CFG_NAME));
			smsInfo.saveSMS(context,name, phoneNo );
		} catch (EbillsException e) {
			throw e;
		}
		
	}
	/**
	 * 获取客户手机号
	 * @param context
	 * @return
	 * @throws EbillsException
	 */
	public void createSMS2Corp(String name) throws EbillsException{
		String custid=(String) CommonUtil.getValFromContext(context,"butxn_custId");
		String phoneNo=getTableValue("MOBILENO","PACRP","CORPNO='"+custid+"'");
		String sffsdx=getTableValue("SFFSDX","PACRP","CORPNO='"+custid+"'");
		//东莞增加判断，如果手机为空则不需要生成短信2014-11-28
		//如果客户是否发送短信为“N”则不需要生成短信
		if(!"".equals(phoneNo) && phoneNo != null && "Y".equals(sffsdx)){
			createSms(name, phoneNo);
		}		
	}
	
	/**
	 * 获取客户经理手机号
	 * @param context
	 * @return
	 * @throws EbillsException
	 */
	public void createSMS2Mgr(String name) throws EbillsException{
		String custid=(String) CommonUtil.getValFromContext(context,"butxn_managerId");
		String phoneNo=getTableValue("MGRTEL","PAMANAGER","ID="+custid);
		createSms(name, phoneNo);
	}
	
	/**
	 * 获取柜员手机号
	 * @param context
	 * @return
	 * @throws EbillsException
	 */
	public void createSMS2User(String name) throws EbillsException{
		String custid=(String) CommonUtil.getValFromContext(context,"butxn_custId");
		String phoneNo=getTableValue("USERPHONE","PAUSR","USERID="+custid);
		createSms(name, phoneNo);
	}
	/**
	 * 
	 * @param name 模版名称
	 * @param date 日期  days 天数，若days>0；date为开始日期，否则为到期日；
	 * @param days 提示天数
	 * @param isWorkDay days是否按工作日
	 * @param startTradeNo 开始任务编号
	 * @throws EbillsException 
	 * @throws Exception
	 */
	public void createMatHint(String name, String date, int days,boolean isWorkDay, String tradeNo,String startBizNo) throws EbillsException{
		try {
			MatureHintInfo matureHintInfo = new MatureHintInfo();
			matureHintInfo.init(StaticCommon.ComponentConfig_CFGINFO.get(MatureHintInfo.CFG_NAME));
			matureHintInfo.saveMatHint(context,name, date, days, isWorkDay,tradeNo,startBizNo);
		} catch (EbillsException e) {
			throw e;
		}
	}
	/**
	 * 取消到期提示
	 * 
	 * @param bizNo 生成到期提示时的业务编号
	 * @throws EbillsException 
	 * @throws Exception
	 */
	public void cancelMatHint(String name,String bizNo) throws EbillsException{
		try {
			MatureHintInfo matureHintInfo = new MatureHintInfo();
			matureHintInfo.init(StaticCommon.ComponentConfig_CFGINFO.get(MatureHintInfo.CFG_NAME));
			matureHintInfo.cancelMatHint(context,name,bizNo);
		} catch (EbillsException e) {
			throw e;
		}
	}
	/**
	 * 取消到期提示
	 * 
	 * @param bizNo 生成到期提示时的业务编号
	 * @throws EbillsException 
	 * @throws Exception
	 */
	public void cancelMatHintByBizNo(String bizNo) throws EbillsException{
		try {
			MatureHintInfo matureHintInfo = new MatureHintInfo();
			matureHintInfo.init(StaticCommon.ComponentConfig_CFGINFO.get(MatureHintInfo.CFG_NAME));
			matureHintInfo.cancelMatHint(context,bizNo);
		} catch (EbillsException e) {
			throw e;
		}
	}
	
	/**
	 * 取消到期提示
	 * 
	 * @param startBizNo  相关业务编号
	 * @throws EbillsException 
	 */
	public void cancelMatHintByRalBizNo(String name,String startBizNo) throws EbillsException{
		try {
			MatureHintInfo.cancelMatHintByRalBizNo(context,name, startBizNo);
		} catch (EbillsException e) {
			throw e;
		}
	}
	/**
	 * 到期提示按自然日算
	 * @param context
	 * @param name
	 * @param date
	 * @param days
	 * @return
	 * @throws EbillsException
	 */
	public void createMatHint(String name,String date,int days) throws EbillsException{
		createMatHint(name, date, days, false, null, null);
	}
	
	/**
	 * 到期提示按工作日算
	 * @param context
	 * @param name
	 * @param date
	 * @param days
	 * @return
	 * @throws EbillsException
	 */
	public void createMatHintByWork(String name,String date,int days) throws EbillsException{
		createMatHint(name, date, days, true, null, null);
	}
	
	public void createMatHint(String name,String date,int days,String tradeNo) throws EbillsException{
		createMatHint(name, date, days, false, tradeNo, null);
	}
	
	public void createMatHintByWork(String name,String date,int days,String tradeNo) throws EbillsException{
		createMatHint(name, date, days, true, tradeNo, null);
	}
	
	public void createMatHint(String name,String date,int days,String tradeNo,String startBizNo) throws EbillsException{
		createMatHint(name, date, days, false, tradeNo, startBizNo);
	}
	
	public void createMatHintByWork(String name,String date,int days,String tradeNo,String startBizNo) throws EbillsException{
		createMatHint(name, date, days, true, tradeNo, startBizNo);
	}
	
	public void createAccount(String names) throws EbillsException {
		try {
			AccountVoucherInfo AccountVoucherInfo = new AccountVoucherInfo(); 
			AccountVoucherInfo.init(StaticCommon.ComponentConfig_CFGINFO.get("module_accountVoucher"));
			AccountVoucherInfo.createAccountVoucher(context, names);
		} catch (EbillsException e) {
			throw e;
		}
	}
	public void createRollBackVoucher(String txnNo) throws EbillsException {
		try {
			AccountVoucherInfo AccountVoucherInfo = new AccountVoucherInfo(); 
			AccountVoucherInfo.init(StaticCommon.ComponentConfig_CFGINFO.get("module_accountVoucher"));
			AccountVoucherInfo.createRollBackVoucher(context, txnNo);
		} catch (EbillsException e) {
			throw e;
		}
	}
	
	public Double getBalance(String bizNo,String fldName) throws EbillsException{
		try {
			Double dValue = BalanceInfo.getBalance(bizNo, fldName);
			if(null == dValue )dValue = 0d ;
			return dValue;
		} catch (EbillsException e) {
			throw e;
		}
	}
	
	/**
	 * 报文清算
	 * @throws EbillsException
	 */
	@SuppressWarnings("unchecked")
	public void loadWaitClearPacket() throws EbillsException{
		try {
			HttpServletRequest request = (HttpServletRequest)context.getValue(EAPConstance.SERVLET_REQUEST);
			String messageClearPakctes = (String)request.getParameter(EbpConstants.MESSAGE_CLEAR_PAKCTES);  //msgId
	//		String messageSquarePakctes = (String)context.get(GjjsConstants.MESSAGE_SQUARE_PAKCTES);  //msgId
			String messageBound = (String)context.get(EbpConstants.MESSAGE_CLEAR_BOUND); //boundMsgId
			List<Map<String,Object>> ret = ClearMsgInfoAction.getClearMsgInfo(messageClearPakctes);  //根据msgId获取报文信息
			List<Map<String,Object>> bounds = ClearMsgInfoAction.getManualBoundMsg(messageBound);  //根据boundMsgId获取报文信息（可能存在多个）
			if(bounds != null && !bounds.isEmpty()){
				ClearMsgInfoAction.mergePacketsList(ret, bounds);  //msgId
			}
			if(ret == null || ret.isEmpty()){
				return ;
			}
			//将报文类型放置在list中并计算下标前缀
			List<String> list = new ArrayList<String>();
			for(Map<String,Object> map : ret){
				String msgType = (String) map.get("msgType");
				msgType = ClearMsgInfoAction.computePrefix(msgType, list); //MSGTYPE_0..._N排序
				String swift = (String)map.get("content");
				String channel = (String)map.get("channel");
				String version = (String)map.get("version");
				initPackets(swift, msgType, channel, true, version); //content.put(MULTIPLE_INIT,报文信息)
			}
			if(this.context.containsKey(MULTIPLE_INIT)){
				Map<String,Object> outMap = (Map<String, Object>) this.context.get(MULTIPLE_INIT);
				this.context.put(EbpConstants.MESSAGE_OUTPUT, CommonUtil.MapToJson(outMap));
				this.context.removeElement(MULTIPLE_INIT);
			}
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 记账管理
	 * @throws EbillsException
	 */
	@SuppressWarnings("unchecked")
	public void loadWaitTallyPacket() throws EbillsException{
		try {
			String txnNo = (String) this.context.get(EbpConstants.TXNSERIALNO);
			//获取记账信息
			String tallySql=" select acctbank,bankinneracct,currency,amount,valuedate,relateno,bizno,fileNo from clmsgtally c ,butxntp b where b.txnno='' and b.fileno=c.msgid";
			EbpDao jsDao=new EbpDao();
			List<Object> inputList = new LinkedList<Object>();
			inputList.add(txnNo);
			List<Map<String,Object>> tlist = jsDao.queryBySql(tallySql,"acctbank,bankinneracct,currency,amount,valuedate,relateno,bizno,fileNo", inputList);
			String msgId="";
			for (Map<String, Object> map : tlist) {
				msgId=(String) map.get("fileNo"); //获取MSGID
				context.put("packetsNa_acctbank", map.get("acctbank"));
				context.put("packetsNa_bankinneracct", map.get("bankinneracct"));
				context.put("packetsNa_currency", map.get("currency"));
				context.put("packetsNa_amount", map.get("amount"));
				context.put("packetsNa_valuedate", map.get("valuedate"));
				context.put("packetsNa_relateno", map.get("relateno"));
				context.put("packetsNa_bizno", map.get("bizno"));
			}
			
			//获取MSGID获取绑定报文id
			String cSql=" select boundmsgId from clmsgquefo where msgId=? ";
			inputList=new LinkedList<Object>();
			inputList.add(msgId);
			List<Map<String,Object>> clist=jsDao.queryBySql(cSql,"boundmsgId", inputList);
			String boundMsgId=(String) clist.get(0).get("boundmsgId");
			
			//获取报文信息与绑定的报文信息
			List<Map<String,Object>> ret = TallyMsgInfoAction.getTallyMsgInfo(msgId);  //根据msgId获取报文信息
			List<Map<String,Object>> bounds = TallyMsgInfoAction.getManualBoundMsg(boundMsgId);  //根据boundMsgId获取报文信息（可能存在多个）
			if(bounds != null && !bounds.isEmpty()){
				ClearMsgInfoAction.mergePacketsList(ret, bounds);  //msgId
			}
			if(ret == null || ret.isEmpty()){
				return ;
			}
			//将报文类型放置在list中并计算下标前缀
			List<String> list = new ArrayList<String>();
			for(Map<String,Object> map : ret){
				String msgType = (String) map.get("msgType");
				msgType = ClearMsgInfoAction.computePrefix(msgType, list); //MSGTYPE_0..._N排序
				String swift = (String)map.get("content");
				String channel = (String)map.get("channel");
				String version = (String)map.get("version");
				initPackets(swift, msgType, channel, true, version); //content.put(MULTIPLE_INIT,报文信息)
			}
			if(this.context.containsKey(MULTIPLE_INIT)){
				Map<String,Object> outMap = (Map<String, Object>) this.context.get(MULTIPLE_INIT);
				this.context.put(EbpConstants.MESSAGE_OUTPUT, CommonUtil.MapToJson(outMap));
				this.context.removeElement(MULTIPLE_INIT);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public  boolean isLastAuth() throws Exception{
		String txnNo = (String) context.getValue(EbpConstants.TXNSERIALNO);
		if (txnNo == null || "".equals(txnNo)){
			return false;
		}
		String userId = null;
		try {
			userId = (String) context.getValue("userId");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Map<String, String> authNext = AuthHelp.getNext(txnNo, userId);
		String authNextStep = authNext.get(AuthHelp.AUTH_NEXTSTEP);
		
		if (authNextStep == null || "".equals(authNextStep.trim()) ){
			return true;
		}else{
			return false;
		}
	}
	
}
