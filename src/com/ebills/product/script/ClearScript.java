package com.ebills.product.script;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebills.clear.achieve.impl.ClearPubAbstract;
import com.ebills.clear.aop.ClearAopFactory;
import com.ebills.clear.context.WaitSendMsg;
import com.ebills.clear.util.ClearConstants;
import com.ebills.clear.util.ClearUtils;
import com.ebills.param.datainfo.DataFactory;
import com.ebills.param.myDocument.MessageExtend;
import com.ebills.param.myDocument.MessageExtend.ClauseTemplate;
import com.ebills.param.newtask.NewTaskFactory;
import com.ebills.product.action.clear.ClearMsgInfoAction;
import com.ebills.script.BaseScript;
import com.ebills.script.impl.ScriptTemplate;
import com.ebills.util.EbillsCfg;
import com.ebills.util.EbillsException;
import com.ebills.util.EbillsMsg;
import com.ebills.utils.CommonUtil;
import com.ebills.utils.EbpConstants;
import com.ebills.utils.EbpDao;
import com.gjjs.swift.SwiftFactory;

public class ClearScript extends ScriptTemplate {
	
	private String className = this.getClass().getName();
	
	@SuppressWarnings("unchecked")
	public void loadHandClearPacket(String txnno) throws EbillsException{
		//[{msgType:MT700_1,sendType:0},{msgType:MT700_1,sendType:1}]
		BaseScript bs = new BaseScript();
		try {
			String msgId = (String) CommonUtil.getValFromContext(context, "msgId");
			List<Map<String,Object>> ret = null;
			List<Map<String,Object>> rets = new LinkedList<Map<String,Object>>();
			EbpDao jsDao = new EbpDao();
			if(StringUtils.isEmpty(msgId)){
				String sql="select t.txnno,t.msgId,t.msgtype,t.content,t.channel  from "+DataFactory.getDataInfoFile(EbpConstants.TPSENDMSGID).getTablename() +" t , butxntp b where t.msgid=b.fileno and b.txnno=?";
				List<Object> inputList = new LinkedList<Object>();
				inputList.add(txnno);
				ret = jsDao.queryBySql(sql, "txnno,msgId,msgtype,content,channel", "", inputList);
			} else {
				String sql = "select t.txnno,t.msgId,t.msgtype,t.content,t.channel  from "+DataFactory.getDataInfoFile(EbpConstants.TPSENDMSGID).getTablename() +" t where t.msgId = ?";
				List<Object> inputList = new LinkedList<Object>();
				inputList.add(msgId);
				ret = jsDao.queryBySql(sql, "txnno,msgId,msgtype,content,channel", "", inputList);
			}
			if(ret == null || ret.isEmpty()){
				return ;
			}
			//循环遍历找到MT103报文
			for(Map<String,Object> retMap : ret){
				String msgType = (String) retMap.get("msgtype");
				rets.add(retMap);
				
				if(!msgType.startsWith("MT103")){
					String rtxnno = (String) retMap.get("txnno");
					String sql1="select t.txnno,t.msgId,t.msgtype,t.content,t.channel  from "+DataFactory.getDataInfoFile(EbpConstants.TPSENDMSGID).getTablename() +" t where t.txnno=? and t.msgType like 'MT103%'";
					List<Object> inputList1 = new LinkedList<Object>();
					inputList1.add(rtxnno);
					List<Map<String,Object>> ret1 = jsDao.queryBySql(sql1, "txnno,msgId,msgtype,content,channel", "", inputList1);
					if(ret1 != null && ret1.size()>0){
						rets.addAll(ret1);
					}
				}
			}
			
			List<String> msgIds = new ArrayList<String>();
			for(Map<String,Object> map : rets){
				msgIds.add((String) map.get("msgId"));
			}
			this.context.put("handClearMsgIds", CommonUtil.ListToJson(msgIds));
			
			//messageInfoList
			List<Map<String,Object>> list1 = new ArrayList<Map<String,Object>>();
			for (Map<String, Object> map : rets) {
				Map<String,Object> map1=new HashMap<String, Object>();
				map1.put("msgType", (String) map.get("msgtype"));
				map1.put("sendType", 0);
				list1.add(map1);
			}
			
			//将报文类型放置在list中并计算下标前缀
			List<String> list = new ArrayList<String>();
			for(Map<String,Object> map : rets){
				String msgType = (String) map.get("msgtype");
				msgType = ClearMsgInfoAction.computePrefix(msgType, list); //MSGTYPE_0..._N排序
				String swift = (String)map.get("content");
				String channel = (String)map.get("channel");
//				String version = (String)map.get("version");
				bs.initPackets(swift, msgType, channel, true, EbillsCfg.getProperty("swift.version","1105")); //content.put(MULTIPLE_INIT,报文信息)
			}
			if(this.context.containsKey(BaseScript.MULTIPLE_INIT)){
				Map<String,Object> outMap = (Map<String, Object>) this.context.get(BaseScript.MULTIPLE_INIT);
				this.context.put(EbpConstants.MESSAGE_OUTPUT, CommonUtil.MapToJson(outMap));
				this.context.put(EbpConstants.MESSAGEINFOLIST, CommonUtil.ListToJson(list1));
				this.context.removeElement(BaseScript.MULTIPLE_INIT);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	//检查报文是否带头寸
	public String checkCashMsg(String msgId) throws EbillsException{
		String isCashMsg = "N";
		try {			
			//String msgId = (String) CommonUtil.getValFromContext(context, "CLRCVHD_msgId");
			List<Map<String,Object>> ret = null;
			EbpDao jsDao = new EbpDao();
			if(!StringUtils.isEmpty(msgId)){
				String sql="select t.msgid,t.content,t.msgtype  from mssdmsgfo t,clcashmsg b where t.msgtype = b.msgtype and b.iscash='Y' t.msgid=?";
				List<Object> inputList = new LinkedList<Object>();
				inputList.add(msgId);
				ret = jsDao.queryBySql(sql, "msgid,content,msgtype", "", inputList);
			} 
			if(ret.size()>0){
				Map<String,Object> map = ret.get(0);
				String msgType = (String) map.get("msgtype");
				String swift = (String)map.get("content");
				//Map<String,Object> msgMap = null;
				//msgMap = SwiftFactory.initPacketContext(swift, msgType, EbillsCfg.getProperty("swift.version","1105"));
				isCashMsg = this.isCashMsg(swift, msgType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return isCashMsg;
	}
	
	//检查MT103是否带头寸
		public String isCashMsg(String swift,String msgType) throws EbillsException{
			String isCashMsg = "N";
			try {
					Map<String,String> relateMap = SwiftFactory.parseFieldMap(swift, msgType, EbillsCfg.getProperty("swift.version","1105"));
					String sender = SwiftFactory.getSender(swift);
					String F54A = (String) relateMap.get("F54A_IdentifierCode");
					String F53A = (String) relateMap.get("F53A_IdentifierCode");
					if(StringUtils.isEmpty(F54A) && StringUtils.isEmpty(F53A)){ //如果都为空 则是带头寸的报文
						isCashMsg = "Y";					
					} else if(StringUtils.isNotEmpty(F54A)){ //如果54A的BIC不为空
						if(sender.equals(F54A)){
							isCashMsg = "Y";	
						}else{
							isCashMsg = "N";	
						}
					} else if(StringUtils.isNotEmpty(F53A)){
						if(sender.equals(F53A)){
							isCashMsg = "Y";	
						}else{
							isCashMsg = "N";	
						}
					}
			} catch (Exception e) {
				e.printStackTrace();
			} 
			return isCashMsg;
		}
		
		/**
		 * "来报手工清算/手工勾对/账单确认"——退汇迁移时插入pbpayfo表
		 * @param txnno
		 * @throws EbillsException
		 */
		public void addPapay() throws EbillsException{
			EbpDao jsDao = new EbpDao();
			String txnNo = (String) CommonUtil.getValFromContext(context, "butxn_txnNo");
			String tradeNo = (String) CommonUtil.getValFromContext(context,EbpConstants.TRADENO);
			SimpleDateFormat sdf = new  SimpleDateFormat("yyyy/MM/dd");
			Date payDate = new Date();
			String cur = "";
			double amt = 0;
			String acctBankSwf = "";
			if("110201".equals(tradeNo)){//来报清算
				cur = (String) CommonUtil.getValFromContext(context,"CLRCVHD_cur");
				amt = Double.valueOf((String) CommonUtil.getValFromContext(context,"CLRCVHD_amt"));
				acctBankSwf = (String) CommonUtil.getValFromContext(context,"acctBankSwf");
				String tranDate = (String) CommonUtil.getValFromContext(context,"CLRCVHD_tranDate");			
				try {
					payDate = sdf.parse(tranDate);
				} catch (ParseException e) {
				}		
			}else if("110302".equals(tradeNo)){//手工勾对
				cur = (String) CommonUtil.getValFromContext(context,"clhandcheck_currency");
				amt = Double.valueOf((String) CommonUtil.getValFromContext(context,"clhandcheck_amount"));
				acctBankSwf = (String) CommonUtil.getValFromContext(context,"clhandcheck_sender");
				String tranDate = (String) CommonUtil.getValFromContext(context,"clhandcheck_valueDate");	
				try {
					payDate = sdf.parse(tranDate);
				} catch (ParseException e) {
				}		
			}else if("110301".equals(tradeNo)){//账单确认
				cur = (String) CommonUtil.getValFromContext(context,"clBillConfirm_currency");
				amt = Double.valueOf((String) CommonUtil.getValFromContext(context,"clBillConfirm_amount"));
				acctBankSwf = (String) CommonUtil.getValFromContext(context,"clBillConfirm_sender");
				String tranDate = (String) CommonUtil.getValFromContext(context,"clBillConfirm_valueDate");	
				try {
					payDate = sdf.parse(tranDate);
				} catch (ParseException e) {
				}		
			}				
			try {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("txnNo", txnNo);
				map.put("bizNo", txnNo);
				map.put("curBizNo", txnNo);
				map.put("tradeNo", tradeNo);
				map.put("valueDate", payDate);
				map.put("payDate", payDate);
				map.put("settlementWay", "1");
				map.put("payCur", cur);
				map.put("payAmt", amt);
				map.put("acctBkSwift",acctBankSwf);
				//map.put("PAYNATURE","1");
				map.put("isPacket","N");
				map.put("isCheck","N");
				jsDao.insertRow("PBPAY", "fo", map); //入库
			} catch (EbillsException e) {
				throw new EbillsException(new Exception("插入付汇表失败"), BaseScript.class.getName());
			}
		}
	
	/**
	 * "往报手工清算"——经办提交时更新报文状态
	 * @param txnno
	 * @throws EbillsException
	 */
	public void updateStateByhandle(String  btxnno) throws EbillsException{
		EbpDao jsDao = new EbpDao();
		String msgId="";
		try {
			String sql="select fileno  from butxntp b where b.txnno=?";
			List<Object> inputList = new LinkedList<Object>();
			inputList.add(btxnno);
			List<Map<String,Object>> ret = jsDao.queryBySql(sql, "fileno", "", inputList);
			for (Map<String, Object> map : ret) {
				msgId=(String)map.get("fileno");
			}
			
			String usql = "update "+ DataFactory.getDataInfoFile(EbpConstants.TPSENDMSGID).getTablename()+" set state='CI' where msgId=?";
			inputList = new LinkedList<Object>();
			inputList.add(msgId);
			jsDao.execute(usql, inputList);
			ClearUtils.arvMsSdMsg(msgId);   //记录更新tpsendmsg表的修改
		} catch (EbillsException e) {
			this.errMsg = new  EbillsException(className,8,new String[]{btxnno}).getMessage(language);
		}
	}
	
	/**
	 * "往报手工清算"——清算
	 * @param txnno
	 */
	public void handClearByClear(String btxnno) throws EbillsException{
		try{
			updateHandClearState(btxnno,ClearConstants.SEND_SUCCESS);
		}catch(Exception e){
			this.errMsg = new  EbillsException(className,8,new String[]{btxnno}).getMessage(language);
		}
		
	}
	
	/**
	 * "往报手工清算"——清算退回
	 * @param txnNo
	 * @throws EbillsException 
	 */
	public void handClearByBack(String txnNo) throws EbillsException{
		try {
			updateSdMsgStateByTxn(txnNo,"FD");
			String msgId="";
			EbpDao jsDao = new EbpDao();
			String sql="select fileno  from butxntp b where b.txnno=?";
			List<Object> inputList = new LinkedList<Object>();
			inputList.add(txnNo);
			List<Map<String,Object>> ret = jsDao.queryBySql(sql, "fileno", "", inputList);
			for (Map<String, Object> map : ret) {
				msgId=(String)map.get("fileno");
			}
			
			/*ClearUtils.updateMsgByMsgId(msgId, "FD");
			ClearUtils.arvMsSdMsg(msgId);*/
			//
			
			//发起报文修改交易
			startModifyTradeByMsgId(msgId, "手工清算退回");
			
		} catch (Exception e) {
			this.errMsg = new  EbillsException(className,8,new String[]{txnNo}).getMessage(language);
		}
	}
	
	public void updateSdMsgStateByTxn(String txnNo, String state) throws EbillsException{
		try{
			String txnno="";
			String msgId="";
			EbpDao jsDao = new EbpDao();
			String sql="select fileno  from butxntp b where b.txnno=?";
			List<Object> inputList = new LinkedList<Object>();
			inputList.add(txnNo);
			List<Map<String,Object>> ret = jsDao.queryBySql(sql, "fileno", "", inputList);
			for (Map<String, Object> map : ret) {
				msgId=(String)map.get("fileno");
			}
			WaitSendMsg wsm=new WaitSendMsg();
			Map<String,Object> mapParam = new HashMap<String, Object>();
			mapParam.put("msgId", msgId);
			List<Map<String,Object>> list = jsDao.queryByDataId(EbpConstants.TPSENDMSGID, "", mapParam);
			for(Map<String,Object> temp : list){
				wsm=ClearUtils.mapToWaitSendMsg(temp);
				//ClearAopFactory.doCashMsgEntry(wsm, false);
				txnno = (String) temp.get("txnNo");
			}
			ClearUtils.updateMsgByMsgId(wsm.getMsgId(), state);
			ClearUtils.arvMsSdMsg(wsm.getMsgId());
			if(!wsm.getMsgType().startsWith("MT103")){
				String sql1="select t.msgId,t.txnno,t.msgtype,t.content,t.channel  from "+DataFactory.getDataInfoFile(EbpConstants.TPSENDMSGID).getTablename() +" t where t.txnno=? and t.msgType like 'MT103%'";
				inputList = new LinkedList<Object>();
				inputList.add(txnno);
				List<Map<String,Object>> ret1 = jsDao.queryBySql(sql1, "msgId,txnno,msgtype,content,channel", "", inputList);
				for (Map<String, Object> map : ret1) {
					ClearUtils.updateMsgByMsgId((String)map.get("msgId"), state);
					ClearUtils.arvMsSdMsg((String)map.get("msgId"));   //记录更新tpsendmsg表的修改
				}
			}
			/*ClearUtils.updateMsgByMsgId(wsm.getMsgId(), state);
			ClearUtils.arvMsSdMsg(wsm.getMsgId());*/
		}catch(Exception e){
			this.errMsg = new  EbillsException(className,8,new String[]{txnNo}).getMessage(language);
		}
	}
	
	/**
	 * "往报手工清算"_数据迁移时更新状态
	 * @param txnNo
	 * @param state
	 * @throws EbillsException
	 */
	private void updateHandClearState(String txnNo,String state) throws EbillsException{
		try{
			String txnno="";
			String msgId="";
			EbpDao jsDao = new EbpDao();
			String sql="select fileno  from butxntp b where b.txnno=?";
			List<Object> inputList = new LinkedList<Object>();
			inputList.add(txnNo);
			List<Map<String,Object>> ret = jsDao.queryBySql(sql, "fileno", "", inputList);
			for (Map<String, Object> map : ret) {
				msgId=(String)map.get("fileno");
			}
			WaitSendMsg wsm=new WaitSendMsg();
			Map<String,Object> mapParam = new HashMap<String, Object>();
			mapParam.put("msgId", msgId);
			List<Map<String,Object>> list = jsDao.queryByDataId(EbpConstants.TPSENDMSGID, "", mapParam);
			for(Map<String,Object> temp : list){
				wsm=ClearUtils.mapToWaitSendMsg(temp);
				ClearAopFactory.doCashMsgEntry(wsm, false);
				txnno = (String) temp.get("txnNo");
			}
			ClearUtils.updateMsgByMsgId(wsm.getMsgId(), state);
			ClearUtils.arvMsSdMsg(wsm.getMsgId());
			if(!wsm.getMsgType().startsWith("MT103")){
				String sql1="select t.msgId,t.txnno,t.msgtype,t.content,t.channel  from "+DataFactory.getDataInfoFile(EbpConstants.TPSENDMSGID).getTablename() +" t where t.txnno=? and t.msgType like 'MT103%'";
				inputList = new LinkedList<Object>();
				inputList.add(txnno);
				List<Map<String,Object>> ret1 = jsDao.queryBySql(sql1, "msgId,txnno,msgtype,content,channel", "", inputList);
				for (Map<String, Object> map : ret1) {
					ClearUtils.updateMsgByMsgId((String)map.get("msgId"), state);
					ClearUtils.arvMsSdMsg((String)map.get("msgId"));   //记录更新tpsendmsg表的修改
				}
			}
		}catch(Exception e){
			this.errMsg = new  EbillsException(className,8,new String[]{txnNo}).getMessage(language);
		}
	}
	
	/**
	 * "报文修改"_获取需要修改的报文信息
	 * @param txnno
	 */
	public void loadModifyPackets(){
		try {
			String txnNo = (String) context.get(EbpConstants.TXNSERIALNO);
			String msgWaitPackets = (String)context.get(EbpConstants.MESSAGE_WAIT_PAKCTES);
			EbpDao jsDao = new EbpDao();
			if(!"".equals(txnNo)){
				String sql="select fileNo  from  butxntp where  txnno=?";
				List<Object> inputList = new LinkedList<Object>();
				inputList.add(txnNo);
				List<Map<String,Object>> ret = jsDao.queryBySql(sql, "fileNo", "", inputList);
				String fileNo="";
				for (Map<String, Object> map : ret) {
					fileNo=(String)map.get("fileNo");
				}
				//FileNo不为空则取tpsendmsg 表里面查找数据
				if(!"".equals(fileNo) && fileNo!=null && fileNo!=""){
					getSendMsgByTp(fileNo);
				}else{
					getWaitPackets(msgWaitPackets);
				}
			}else{
				getWaitPackets(msgWaitPackets);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	/**
	 * "报文修改"_根据butxn.fileNo获取报文信息
	 * @param msgId
	 */
	@SuppressWarnings("unchecked")
	private void getSendMsgByTp(String msgId){
		BaseScript bs = new BaseScript();
		try {
			List<Map<String,Object>> slist ;
			EbpDao jsDao = new EbpDao();
			List<Map<String,Object>> rets = new LinkedList<Map<String,Object>>();
			Map<String,Object> mapParam = new HashMap<String, Object>();
			mapParam.put("msgId", msgId);
			slist = jsDao.queryByDataId(EbpConstants.TPSENDMSGID, "", mapParam);
			
			//循环遍历找到MT103报文
			for(Map<String,Object> retMap : slist){
				String msgType = (String) retMap.get("msgType");
				rets.add(retMap);
				
				if(!msgType.startsWith("MT103")){
					String rtxnno = (String) retMap.get("txnNo");
					String sql="select t.msgType,t.content,t.channel  from "+DataFactory.getDataInfoFile(EbpConstants.TPSENDMSGID).getTablename() +" t where t.txnno=? and t.msgType like 'MT103%'";
					List<Object> inputList = new LinkedList<Object>();
					inputList.add(rtxnno);
					List<Map<String,Object>> ret1 = jsDao.queryBySql(sql, "msgType,content,channel", "", inputList);
					if(ret1 != null && ret1.size()>0){
						rets.addAll(ret1);
					}
				}
			}
			//messageInfoList
			List<Map<String,Object>> list1 = new ArrayList<Map<String,Object>>();
			for (Map<String, Object> map : rets) {
				Map<String,Object> map1=new HashMap<String, Object>();
				map1.put("msgType", (String) map.get("msgType"));
				map1.put("sendType", 0);
				list1.add(map1);
			}
			
			MessageExtend extend = new MessageExtend();
			List<String> nakUpdate = new ArrayList<String>();
			//将报文类型放置在list中并计算下标前缀
			List<String> list = new ArrayList<String>();
			for(Map<String,Object> map : rets){
				String msgType = (String) map.get("msgType");
				msgType = ClearMsgInfoAction.computePrefix(msgType, list); //MSGTYPE_0..._N排序
				String swift = (String)map.get("content");
				String channel = (String)map.get("channel");
				String version = EbillsCfg.getProperty("swift.version","1105");
				bs.initPackets(swift, msgType, channel, true, version); //content.put(MULTIPLE_INIT,报文信息)
				String tempMsgType = msgType;
				if(msgType.indexOf(SwiftFactory.MESSAGE_SUFFIX) > -1){
					tempMsgType = msgType.substring(0, msgType.indexOf(SwiftFactory.MESSAGE_SUFFIX));
				}
				List<ClauseTemplate> nakUpdateList = extend.getClauseTemplate(tempMsgType);
				if(nakUpdateList != null && !nakUpdateList.isEmpty()){
					for(ClauseTemplate cla : nakUpdateList){
						if("1".equals(cla.getNAKupdate())){
							nakUpdate.add(msgType + "_" + cla.getTarget());
						}
					}
				}
			}
			this.context.put("nakUpdateJson", CommonUtil.ListToJson(nakUpdate));
			
			if(this.context.containsKey(BaseScript.MULTIPLE_INIT)){
				Map<String,Object> outMap = (Map<String, Object>) this.context.get(BaseScript.MULTIPLE_INIT);
				this.context.put(EbpConstants.MESSAGE_OUTPUT, CommonUtil.MapToJson(outMap));
				this.context.put(EbpConstants.MESSAGEINFOLIST, CommonUtil.ListToJson(list1));
				this.context.removeElement(BaseScript.MULTIPLE_INIT);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * "报文修改"_根据msackmsg.msgId获取报文信息
	 * @param messageWaitPakctes
	 * @throws EbillsException
	 */
	private void getWaitPackets(String messageWaitPakctes) throws EbillsException{
		BaseScript bs = new BaseScript();
		List<Object> inputList = new LinkedList<Object>();
		inputList.add(messageWaitPakctes);
		EbpDao jsDao = new EbpDao();
		String fields = jsDao.getFieldsByDataId(EbpConstants.MSACKMSGID);
		String selectFields = "t." + fields.replaceAll(",", ", t.") + ",t1.content, t1.version";
		String sql = "select " + selectFields + " from " + DataFactory.getDataInfoFile(EbpConstants.MSACKMSGID).getTablename();
		sql += " t, MSMSGINFO t1 where t.msgId = t1.msgId  and t.msgId = ?";
		List<Map<String,Object>> list = jsDao.queryBySql(sql, fields + ",content,version", "", inputList);
		if(list == null || list.isEmpty()){
			fields = jsDao.getFieldsByDataId(EbpConstants.TPSENDMSGID);
			sql = "select " + fields + ", case channel when 'Edi' then '"+ EbillsCfg.getProperty("edi.version", "1_0")+"' else '" + EbillsCfg.getProperty("swift.version", "1105")+ "' end as version from " + DataFactory.getDataInfoFile(EbpConstants.TPSENDMSGID).getTablename() + " where msgId = ?";
			list = jsDao.queryBySql(sql, fields + ",version", "", inputList);
		}
		if(list != null && !list.isEmpty()){
			Map<String,Object> map = list.get(0);
			String swift = (String) map.get("content");
			String channel = (String) map.get("channel");
			String version = (String) map.get("version");
			bs.initPackets(swift, channel, true, version);
		}
	}
	
	/**
	 * ”报文修改“交易_手动经办提交处理 
	 * @param relateNo
	 * @param txnNo
	 * @throws EbillsException
	 */
	public void updatePacketsModifyState(String relateNo,String txnNo,String state) throws EbillsException{
		EbpDao jsDao = new EbpDao();
		try {
			String msgId="";
			String sql ="";
			if(relateNo!=null && !"".equals(relateNo)){
				msgId=relateNo;
				ClearUtils.updateMsgByMsgId(msgId, state);
				ClearUtils.arvMsSdMsg(msgId);
			}else{
				sql="select fileNo  from  butxntp where  txnno=?";
				List<Object> inputList = new LinkedList<Object>();
				inputList.add(txnNo);
				List<Map<String,Object>> ret = jsDao.queryBySql(sql, "fileNo", "", inputList);
				Map<String,Object> map=ret.get(0);
				msgId=(String)map.get("fileNo");
				updateStateByMsgId(msgId,state); // HF
			}
		} catch (Exception e) {
			this.errMsg = EbillsMsg.getMessage(className, null, 8, null, new String[]{relateNo,txnNo});
		}
	}
	
	/**
	 * "报文修改"_重新发送处理（迁移时更新报文状态）
	 * @throws EbillsException 
	 */
	public void updateModifyByResend(String relateNo,String txnNo,String state) throws EbillsException{
		EbpDao jsDao = new EbpDao();
		try {
			String msgId="";
			String sql ="";
			if(relateNo!=null && !"".equals(relateNo)){
				msgId=relateNo;
				ClearUtils.updateMsgByMsgId(msgId, state);
				ClearUtils.arvMsSdMsg(msgId);
			}else{
				sql="select fileNo  from  butxntp where  txnno=?";
				List<Object> inputList = new LinkedList<Object>();
				inputList.add(txnNo);
				List<Map<String,Object>> ret = jsDao.queryBySql(sql, "fileNo", "", inputList);
				Map<String,Object> map=ret.get(0);
				msgId=(String)map.get("fileNo");
				updateStateByMsgId(msgId,state); //"FD"
			}
		} catch (Exception e) {
			this.errMsg = EbillsMsg.getMessage(className, null, 8, null, new String[]{relateNo,txnNo});
		}
	}
	/**
	 * "报文修改"_废弃处理（迁移时更新报文状态）
	 * @throws EbillsException 
	 */
	public void updateModifyByAbandon(String relateNo,String txnNo,String state) throws EbillsException{
		EbpDao jsDao = new EbpDao();
		try {
			String msgId="";
			String sql ="";
			if(relateNo!=null && !"".equals(relateNo)){
				msgId=relateNo;
				ClearUtils.updateMsgByMsgId(msgId, state);
				ClearUtils.arvMsSdMsg(msgId);
			}else{
				sql="select fileNo  from  butxntp where  txnno=?";
				List<Object> inputList = new LinkedList<Object>();
				inputList.add(txnNo);
				List<Map<String,Object>> ret = jsDao.queryBySql(sql, "fileNo", "", inputList);
				Map<String,Object> map=ret.get(0);
				msgId=(String)map.get("fileNo");
				updateStateByMsgId(msgId,state); //"AB"
			}
		} catch (Exception e) {
			//throw new  EbillsException(className,8,new String[]{relateNo,txnNo});
			this.errMsg = EbillsMsg.getMessage(className, null, 8, null, new String[]{relateNo,txnNo});
		}
	}
	
	/**
	 * "往报清算"_更新报文的起息日
	 * @param msgId
	 * @param state
	 * @throws EbillsException
	 */
	public void updateDateByMsgId(String msgId,String workDate) throws EbillsException{
		try {
			EbpDao jsDao = new EbpDao();
			String sql = "update "+DataFactory.getDataInfoFile(EbpConstants.TPSENDMSGID).getTablename().toUpperCase()+" set valueDate = ? where msgId = ?";
			List<Object> input = new LinkedList<Object>();
			SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM-dd");
			Date vauleDate = new Date();
			try {
				vauleDate = sdf.parse(workDate);
			} catch (ParseException e) {
			}
			input.add(vauleDate);
			input.add(msgId);
			jsDao.execute(sql, input);
		} catch (Exception e) {
			this.errMsg = EbillsMsg.getMessage(className, null, 8, null, new String[]{msgId});
		}
	}
	
	/**
	 * "报文修改"_更新报文的状态
	 * @param msgId
	 * @param state
	 * @throws EbillsException
	 */
	private void updateStateByMsgId(String msgId,String state) throws EbillsException{
		try {
			EbpDao jsDao = new EbpDao();
			String sql = "update "+DataFactory.getDataInfoFile(EbpConstants.TPSENDMSGID).getTablename().toUpperCase()+" set state = ? where msgId = ?";
			List<Object> input = new LinkedList<Object>();
			input.add(state);
			input.add(msgId);
			jsDao.execute(sql, input);
			ClearUtils.arvMsSdMsg(msgId);   //记录更新tpsendmsg表的修改
			Map<String,Object> mapParam = new HashMap<String, Object>();
			mapParam.put("msgId", msgId);
			List<Map<String,Object>> slist = jsDao.queryByDataId(EbpConstants.TPSENDMSGID, "", mapParam);
			
			//循环遍历找到MT103报文
			for(Map<String,Object> retMap : slist){
				String msgType = (String) retMap.get("msgType");
	
				if(!msgType.startsWith("MT103")){
					String rtxnno = (String) retMap.get("txnNo");
					sql="select t.msgId  from "+DataFactory.getDataInfoFile(EbpConstants.TPSENDMSGID).getTablename() +" t where t.txnno=? and t.msgType like 'MT103%'";
					List<Object> inputList = new LinkedList<Object>();
					inputList.add(rtxnno);
					List<Map<String,Object>> ret = jsDao.queryBySql(sql, "msgId", "", inputList);
					for (Map<String, Object> map : ret) {
						sql = "update "+DataFactory.getDataInfoFile(EbpConstants.TPSENDMSGID).getTablename().toUpperCase()+" set state = ? where msgId = ? ";
						input = new LinkedList<Object>();
						input.add(state);
						input.add(map.get("msgId"));
						jsDao.execute(sql, input);
						ClearUtils.arvMsSdMsg((String)map.get("msgId"));   //记录更新tpsendmsg表的修改
					}
				}
			}
		} catch (Exception e) {
			this.errMsg = EbillsMsg.getMessage(className, null, 8, null, new String[]{msgId});
		}
	}
	
	public void handCheck() throws EbillsException{
		String tradeType=(String) CommonUtil.getValFromContext(context,EbpConstants.TRADENO);
		String commStr = "clhandcheck";
		if("110302".equals(tradeType)){//手工勾对
			commStr = "clhandcheck";
		}else if("110301".equals(tradeType)){//账单确认
			commStr = "clBillConfirm";
		}
		String msgId = (String) this.context.get(commStr+"_rltMsgId");
		String dcFlag = (String) this.context.get("dcFlag");
		String reckonNo = (String) this.context.get(commStr+"_reckonNo");
		if(StringUtils.isNotEmpty(msgId)){
			String sql = "update " + DataFactory.getDataInfoFile(EbpConstants.TPSENDMSGID).getTablename() + " set chkFlg = 'Y' where msgId = ?";
			if("C".equals(dcFlag)){
				sql = "update " + ClearConstants.MSG_QUEUE + " set chkFlg = 'Y' where msgId = ?";
			}
			List<Object> inputList = new LinkedList<Object>();
			inputList.add(msgId);
			EbpDao jsDao = new EbpDao();
			jsDao.execute(sql, inputList);
			sql = "update clrknrcd set dealtyp = 'SUCCESS' where reckonNo = ?";
			inputList.clear();
			inputList.add(reckonNo);
			jsDao.execute(sql, inputList);
		}
	}
	
	
	public void updateRknrcd(String state) throws EbillsException{
		EbpDao jsDao = new EbpDao();
		String tradeType=(String) CommonUtil.getValFromContext(context,EbpConstants.TRADENO);
		String commStr = "clhandcheck";
		if("110302".equals(tradeType)){//手工勾对
			commStr = "clhandcheck";
		}else if("110301".equals(tradeType)){//账单确认
			commStr = "clBillConfirm";
		}
		String reckonNo = (String) this.context.get(commStr+"_reckonNo");
		String msgId = (String) this.context.get(commStr+"_rltMsgId");
		String sql = "update clrknrcd set dealtyp = ?,rltmsgid = ? where reckonNo = ?";
		List<Object> inputList = new LinkedList<Object>();
		inputList.add(state);
		inputList.add(msgId);
		inputList.add(reckonNo);
		jsDao.execute(sql, inputList);
	}
	
	public void freezeBalance(boolean unfreeze) throws EbillsException{
		try{
			String txnNo = (String) this.context.get("butxn_txnNo");
			EbpDao jsDao = new EbpDao();
			String sql="select t.txnno,t.msgId,t.msgtype,t.recivercode,t.currency,t.amount,t.tranDate,orgNo,valueDate  from "+DataFactory.getDataInfoFile(EbpConstants.TPSENDMSGID).getTablename() +" t , butxntp b where t.msgid=b.fileno and b.txnno=?";
			List<Object> inputList = new LinkedList<Object>();
			inputList.add(txnNo);
			List<Map<String,Object>> ret = jsDao.queryBySql(sql, "txnno,msgId,msgtype,receiver,currency,amount,tranDate,orgNo,valueDate", "", inputList);
			if(ret == null || ret.isEmpty()){
				return ;
			}
			Map<String,Object> map  = ret.get(0);
			String currency = (String) map.get("currency");
			String receiver = (String) map.get("receiver");
			String msgType = (String) map.get("msgtype");
			receiver = StringUtils.isEmpty((String)CommonUtil.getValFromContext(context, msgType + "_Receiver")) ? receiver : (String) CommonUtil.getValFromContext(context, msgType + "_Receiver");
			Double amount = Double.parseDouble((String) map.get("amount"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date tranDate = null;
			try {
				tranDate = sdf.parse((String) map.get("tranDate"));
			} catch (ParseException e) {
				throw new EbillsException(new Exception("获取交易日期失败"), BaseScript.class.getName());
			}
			String msgId = (String) map.get("msgId");
			String orgNo = (String) map.get("orgNo");
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date valueDate = null;
			try {
				String tmp = (String) map.get("valueDate");
				String dateFiled = SwiftFactory.getValueDateField(msgType, EbillsCfg.getProperty("swift.version", "1105"));
				tmp = (String)CommonUtil.getValFromContext(context, msgType + "_" + dateFiled) ;
				tmp = StringUtils.isEmpty(tmp) ? (String) map.get("valueDate") : tmp;
				valueDate = sdf.parse(tmp);
			} catch (ParseException e) {
				throw new EbillsException(new Exception("获取起息日失败"), BaseScript.class.getName());
			}
			String tempRec = receiver;
			if(StringUtils.isNotEmpty(receiver) && receiver.toLowerCase().equals("xxx")){
				tempRec = receiver.substring(0,8);
			}
			
			if(!unfreeze){
				freezeCheck(amount, receiver, orgNo, currency, valueDate, msgId);
			}
			
			
			String workDay = CommonUtil.getSysFld(EbpConstants.WORK_DATE);
			String clearingTime = getClearingTime(currency, receiver);
			
			boolean hasFree = false;
			
			sql = "select msgId,amount from pafreeze where msgId = ?";
			inputList.clear();
			inputList.add(msgId);
			List<Map<String,Object>> hasFreeze = jsDao.queryBySql(sql, "", inputList);
			Double oldAmt = 0d;
			if(hasFreeze != null && !hasFreeze.isEmpty()){
				hasFree = true;
				Map<String,Object> freezeMap = hasFreeze.get(0);
				oldAmt = Double.parseDouble((String) freezeMap.get("amount"));
			}
			
			if(StringUtils.isNotEmpty(clearingTime)){
				Date checkTime = getCheckTime(workDay, clearingTime);
				if(tranDate.getTime() > checkTime.getTime()){ //过了清算时间点的手工交易需要冻结头寸
					if(hasFree){
						Double unfreezeAmt = oldAmt > 0 ? oldAmt : amount;
						unfreeze(unfreezeAmt, receiver, orgNo, currency, valueDate, msgId);
						unFreezeRecode(msgId);
					}
					if(!unfreeze){
						freeze(amount, receiver, orgNo, currency, valueDate, msgId);
						freezeRecode(msgId, amount);
					}
				}
			} else {
				if(hasFree){
					unFreezeRecode(msgId);
				} else {
					freezeRecode(msgId, amount);
				}
			}
			inputList.clear();
			if(hasFree && unfreeze){ //已冻结过并且是解冻操作
				sql = "update CLCASHFO set frzbal = nvl(frzbal,0) - ?, fileno = ?, updatetime = ?, TRANAMT = ? where (accbkno = ? or accbkno = ?) and cashccy = ? and VALUEDATE = ?";
				Double unfreezeAmt = oldAmt > 0 ? oldAmt : amount;
				inputList.add(unfreezeAmt); //解冻原始金额
				
			} else if(!hasFree && !unfreeze) { //未冻结并且是冻结操作
				sql = "update CLCASHFO set frzbal = nvl(frzbal,0) + ?, fileno = ?, updatetime = ?, TRANAMT = ? where (accbkno = ? or accbkno = ?) and cashccy = ? and VALUEDATE = ?";
				inputList.add(amount);
				
			} else if (hasFree && !unfreeze){ //已经冻结并且再次冻结时需要先解冻
				sql = "update CLCASHFO set frzbal = nvl(frzbal,0) - ?, fileno = ?, updatetime = ?, TRANAMT = ? where (accbkno = ? or accbkno = ?) and cashccy = ? and VALUEDATE = ?";
				Double unfreezeAmt = oldAmt > 0 ? oldAmt : amount;
				inputList.clear();
				inputList.add(unfreezeAmt);
				inputList.add(msgId);
				inputList.add(new Date());
				inputList.add(0 - amount);
				inputList.add(receiver);
				inputList.add(tempRec);
				inputList.add(currency);
				inputList.add(valueDate);
				jsDao.execute(sql, inputList);
				ClearUtils.arvCash(tempRec, currency); //解冻完成
				
				inputList.clear();
				inputList.add(amount);
				sql = "update CLCASHFO set frzbal = nvl(frzbal,0) + ?, fileno = ?, updatetime = ?, TRANAMT = ? where (accbkno = ? or accbkno = ?) and cashccy = ? and VALUEDATE = ?";
			} else {
				return ;
			}
			//inputList.clear();
			//inputList.add(amount);
			inputList.add(msgId);
			inputList.add(new Date());
			inputList.add(0 - amount);
			inputList.add(receiver);
			inputList.add(tempRec);
			inputList.add(currency);
			inputList.add(valueDate);
			jsDao.execute(sql, inputList);
			ClearUtils.arvCash(tempRec, currency);
		}catch(EbillsException e){
			this.errMsg = e.getMessage();
		}
	}
	
	private void unfreeze(Double amount, String receiver, String orgNo, String currency, Date valueDate, String msgId) throws EbillsException{
		EbpDao jsDao = new EbpDao();
		List<Object> inputList = new LinkedList<Object>();
		String tempRec = receiver;
		if(StringUtils.isNotEmpty(receiver) && receiver.toLowerCase().equals("xxx")){
			tempRec = receiver.substring(0,8);
		}
		String sql = "update clprecashfo set frzbal = nvl(frzbal,0) - ?, fileno = ?, updatetime = ? where ORGNO = ? and CASHCCY = ? and (accbkswiftcode = ? or accbkswiftcode = ?) and VALUEDATE = ?";
		inputList.clear();
		inputList.add(amount);
		inputList.add(msgId);
		inputList.add(new Date());
		inputList.add(orgNo);
		inputList.add(currency);
		inputList.add(receiver);
		inputList.add(tempRec);
		inputList.add(valueDate);
		jsDao.execute(sql, inputList);
		ClearUtils.arvPreCash(receiver, currency, valueDate, orgNo);
	}
	
	private void freeze(Double amount, String receiver, String orgNo, String currency, Date valueDate, String msgId) throws EbillsException{
		EbpDao jsDao = new EbpDao();
		List<Object> inputList = new LinkedList<Object>();
		String tempRec = receiver;
		if(StringUtils.isNotEmpty(receiver) && receiver.toLowerCase().equals("xxx")){
			tempRec = receiver.substring(0,8);
		}
		String sql = "update clprecashfo set frzbal = nvl(frzbal,0) + ?, fileno = ?, updatetime = ? where ORGNO = ? and CASHCCY = ? and (accbkswiftcode = ? or accbkswiftcode = ?) and VALUEDATE = ?";
		inputList.clear();
		inputList.add(amount);
		inputList.add(msgId);
		inputList.add(new Date());
		inputList.add(orgNo);
		inputList.add(currency);
		inputList.add(receiver);
		inputList.add(tempRec);
		inputList.add(valueDate);
		jsDao.execute(sql, inputList);
		ClearUtils.arvPreCash(receiver, currency, valueDate, orgNo);
	}
	
	private void freezeRecode(String msgId, Double amt) throws EbillsException{
		String sql = "insert into pafreeze (msgid, amount) values (?, ?)";
		EbpDao jsDao = new EbpDao();
		List<Object> inputList = new LinkedList<Object>();
		inputList.clear();
		inputList.add(msgId);
		inputList.add(amt);
		jsDao.execute(sql, inputList);
	}
	
	private void unFreezeRecode(String msgId) throws EbillsException{
		EbpDao jsDao = new EbpDao();
		List<Object> inputList = new LinkedList<Object>();
		String sql = "delete from pafreeze where msgid = ?";
		inputList.clear();
		inputList.add(msgId);
		jsDao.execute(sql, inputList);
	}
	
	/**
	 * 手工清算，提交时检查头寸是否足额
	 * @param tallyMsgId
	 * @param state
	 * @param operate
	 * @throws EbillsException
	 */
	public void clRcvcheckCash(String state, String operate)throws EbillsException{
		boolean onlyhas199 = false;
		//获取txnNo
		String mt199stu = (String) CommonUtil.getValFromContext(context, "mt199stu");
		String mt202stu = (String) CommonUtil.getValFromContext(context, "mt202stu");
		String mt202covstu = (String) CommonUtil.getValFromContext(context, "mt202covstu");
		if("1".equals(mt199stu) && !"1".equals(mt202stu) && !"1".equals(mt202covstu)){
			onlyhas199 = true;
		}
		if("Refix,Suspend".indexOf(state)!=-1){ //退回清算或挂账状态，减头寸的都要检查头寸是否足额
			if("abandon".equals(operate)){
				//清算过的报文废弃时需回滚头寸
				checkClrcvCash();
			}else if("return".equals(operate) && onlyhas199){
				//清算过的报文退汇时需扣减退汇头寸(如果退汇发送202报文，在往报清算时会处理该头寸)
				checkClrcvCash();
			}
		}
	}
	
	public void checkClrcvCash() throws EbillsException{
		try{
			String currency = (String) CommonUtil.getValFromContext(context, "CLRCVHD_cur");
			Double flAmount =  Double.parseDouble((String) CommonUtil.getValFromContext(context, "flAmount"));
			String receiver =  (String) CommonUtil.getValFromContext(context, "MT199Sender");
			clRcvCheckCashfo(flAmount, receiver,  currency);
		}catch(EbillsException e){
			this.errMsg = e.getMessage();
		}
	}
	
	private void freezeCheck(Double amount, String receiver, String orgNo, String currency, Date valueDate, String msgId) throws EbillsException{
		EbpDao jsDao = new EbpDao();
		List<Object> inputList = new LinkedList<Object>();
		String tempRec = receiver;
		if(StringUtils.isNotEmpty(receiver) && receiver.toLowerCase().equals("xxx")){
			tempRec = receiver.substring(0,8);
		}
		String sql = "select count(0) from CLCASHFO where (accbkno = ? or accbkno = ?) and cashccy = ? and VALUEDATE = ? and nvl(CASHBAL,0) - nvl(FRZBAL,0) >= ?";
		inputList.clear();
		//inputList.add(orgNo);
		inputList.add(receiver);
		inputList.add(tempRec);
		inputList.add(currency);
		inputList.add(valueDate);
		inputList.add(amount);
		List<Map<String,Object>> list = jsDao.queryBySql(sql, "cnt", "", inputList);
		if(list != null && !list.isEmpty()){
			Map<String,Object> map = list.get(0);
			String cnt = (String) map.get("cnt");
			if("0".equals(cnt)){
				this.errMsg = new EbillsException(new Exception("账户行头寸不足,请检查!"), this.getClass().getName()).getMessage(language);
			}
		}
	}
	
	private void clRcvCheckCashfo(Double amount, String receiver, String currency) throws EbillsException{
		EbpDao jsDao = new EbpDao();
		List<Object> inputList = new LinkedList<Object>();
		String tempRec = receiver;
		try {
			if(StringUtils.isNotEmpty(receiver) && receiver.toLowerCase().equals("xxx")){
				tempRec = receiver.substring(0,8);
			}
			String sql = "select count(0) from CLCASHFO where (accbkno = ? or accbkno = ?) and cashccy = ?  and nvl(CASHBAL,0) - nvl(FRZBAL,0) >= ?";
			inputList.clear();
			inputList.add(receiver);
			inputList.add(tempRec);
			inputList.add(currency);
			inputList.add(amount);
			List<Map<String,Object>> list = jsDao.queryBySql(sql, "cnt", "", inputList);
			if(list != null && !list.isEmpty()){
				Map<String,Object> map = list.get(0);
				String cnt = (String) map.get("cnt");
				if("0".equals(cnt)){
					throw new Exception("账户行头寸不足,请检查!");					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	private Date getCheckTime(String workDay, String clearTime) throws EbillsException{
		SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String today = workDay + " " + clearTime;
		Date checkTime = null;
		try {
			checkTime = sdf.parse(today);
		} catch (ParseException e) {
			this.errMsg = new EbillsException(new Exception("参数清算时间点设置错误"), this.getClass().getName()).getMessage(language);
		}
		return checkTime;
	}
	
	private String getClearingTime(String ccy, String receiver) throws EbillsException {
		EbpDao jsDao = new EbpDao();
		String sql = "select ccy, acctBkswift, clearingTime from paclrtim where ccy = ? and acctBkswift = ?";
		List<Object> inputList = new LinkedList<Object>();
		inputList.add(ccy);
		if(receiver.toLowerCase().endsWith("xxx")){
			receiver = receiver.substring(0,8);
		}
		inputList.add(receiver);
		List<Map<String,Object>> list = jsDao.queryBySql(sql, "ccy,acctBkswift,clearingTime", "", inputList);
		if(list != null && !list.isEmpty()){
			String clearingTime = (String) list.get(0).get("clearingTime");
			return clearingTime;
		}
		return null;
	}
	
	public void handCheckByOperate(String operate) throws EbillsException{
		String tradeType=(String) CommonUtil.getValFromContext(context,EbpConstants.TRADENO);
		String commStr = "clhandcheck";
		if("110302".equals(tradeType)){//手工勾对
			commStr = "clhandcheck";
		}else if("110301".equals(tradeType)){//账单确认
			commStr = "clBillConfirm";
		}
		String msgId = (String) this.context.get(commStr+"_rltMsgId");
		String reckonNo = (String) this.context.get(commStr+"_reckonNo");
		String orgNo = (String) this.context.get(commStr+"_clearOrgNo");
		//String dcFlag = (String) this.context.get("dcFlag");
		String sql = "";
		EbpDao jsDao = new EbpDao();
		List<Object> inputList = new LinkedList<Object>();
		if("INWARD".equals(operate)) { //汇入
			if("".equals(msgId)){
				String currency = (String) this.context.get(commStr+"_currency");
				String amount = (String) this.context.get(commStr+"_amount");
				Map<String,Object> newTaskInfo = new HashMap<String,Object>();
				newTaskInfo.put("orgNo",orgNo);
				newTaskInfo.put("tranCur",currency);
				newTaskInfo.put("tranAmt",amount);
				String startDate = CommonUtil.getSysFld(EbpConstants.WORK_DATE);
				newTaskInfo.put("startDate",CommonUtil.formatDate(startDate));
				newTaskInfo.put("tradeNo","070101");//汇入汇款
				newTaskInfo.put("fileNo",reckonNo);
				newTaskInfo.put("memo","对账单发起");
				newTaskInfo.put("startModeId","11");
				NewTaskFactory.createNewTask(newTaskInfo);
			}else{
				sql = "update " + ClearConstants.MSG_QUEUE + " set chkFlg = 'Y', clearstate = '" + ClearConstants.CLEAR_ASSIGNED + "' where msgId = ?";
				inputList.clear();
				inputList.add(msgId);
				jsDao.execute(sql, inputList);
				ClearUtils.arvClearRcvInfo(msgId);
				ClearUtils.assignCashToEbp(msgId, EbpConstants.SWIFT_CHANNEL, "Y");
				//删除被关联报文产生的手工任务
				ClearPubAbstract.delTask(msgId, ClearConstants.HANDL_CLEAR_TRADENO);
			}
			
		} else if("SETTLEMENT".equals(operate) || "REBILL".equals(operate)){
			String tradeNo = (String) this.context.get(commStr+"_tradeNo");
			String bizNo = (String) this.context.get(commStr+"_bizNo");
			String currency = (String) this.context.get(commStr+"_currency");
			String amount = (String) this.context.get(commStr+"_amount");
			Map<String,Object> newTaskInfo = new HashMap<String,Object>();
			newTaskInfo.put("orgNo",orgNo);
			newTaskInfo.put("tranCur",currency);
			newTaskInfo.put("tranAmt",amount);
			String startDate = CommonUtil.getSysFld(EbpConstants.WORK_DATE);
			newTaskInfo.put("startDate",CommonUtil.formatDate(startDate));
			newTaskInfo.put("tradeNo",tradeNo);
			newTaskInfo.put("fileNo",reckonNo);
			newTaskInfo.put("bizNo",bizNo);
			/*String valDate = CommonUtil.unFormatDate(bField.getValueDate(),"yyyy-MM-dd");
			if(null == valDate)valDate = "";*/
			newTaskInfo.put("memo","对账单发起");
			newTaskInfo.put("startModeId","11");
			NewTaskFactory.createNewTask(newTaskInfo);
		}
	}
	
	public void handRegByTradeNo() throws EbillsException{
		String isExchg = (String) this.context.get("CLRCVREG_isExchg");
		String tradeNo = (String) this.context.get("CLRCVREG_tradeNo");
		String relateNo = (String) this.context.get("CLRCVREG_relateNo");
		String orgNo = (String) this.context.get("CLRCVREG_txnorg");
		String currency = (String) this.context.get("CLRCVREG_inCur");
		String amount = (String) this.context.get("CLRCVREG_inAmt");
		String txnNo = (String) this.context.get("CLRCVREG_txnNo");
		if(",070101,070105,070111".indexOf(tradeNo)>0) { //汇入
			if("Y".equals(isExchg)){
				Map<String,Object> newTaskInfo = new HashMap<String,Object>();
				newTaskInfo.put("orgNo",orgNo);
				newTaskInfo.put("tranCur",currency);
				newTaskInfo.put("tranAmt",amount);
				String startDate = CommonUtil.getSysFld(EbpConstants.WORK_DATE);
				newTaskInfo.put("startDate",CommonUtil.formatDate(startDate));
				newTaskInfo.put("tradeNo",tradeNo);
				newTaskInfo.put("bizNo",relateNo);
				newTaskInfo.put("memo","来报手工登记发起");
				newTaskInfo.put("startModeId","12");
				NewTaskFactory.createNewTask(newTaskInfo);
			}else{
				Map<String,Object> newTaskInfo = new HashMap<String,Object>();
				newTaskInfo.put("orgNo",orgNo);
				newTaskInfo.put("tranCur",currency);
				newTaskInfo.put("tranAmt",amount);
				String startDate = CommonUtil.getSysFld(EbpConstants.WORK_DATE);
				newTaskInfo.put("startDate",CommonUtil.formatDate(startDate));
				newTaskInfo.put("tradeNo",tradeNo);
				newTaskInfo.put("memo","来报手工登记发起");
				newTaskInfo.put("startModeId","12");
				newTaskInfo.put("fileNo",txnNo);//特殊处理，以便发起的汇入汇款通过fileNo查询到该笔手工登记的数据之后，带出账户行等信息
				NewTaskFactory.createNewTask(newTaskInfo);
			}			
		} else if(",050905,050621".indexOf(tradeNo)>0){
			Map<String,Object> newTaskInfo = new HashMap<String,Object>();
			newTaskInfo.put("orgNo",orgNo);
			newTaskInfo.put("tranCur",currency);
			newTaskInfo.put("tranAmt",amount);
			String startDate = CommonUtil.getSysFld(EbpConstants.WORK_DATE);
			newTaskInfo.put("startDate",CommonUtil.formatDate(startDate));
			newTaskInfo.put("tradeNo",tradeNo);
			newTaskInfo.put("bizNo",relateNo);
			newTaskInfo.put("memo","来报手工登记发起");
			newTaskInfo.put("startModeId","12");
			NewTaskFactory.createNewTask(newTaskInfo);
		}
	}
	
	private boolean updateWaitApproveState(String msgId, String fromState, String toState) throws EbillsException{
		EbpDao jsDao = new EbpDao();
		String sql = "update " + DataFactory.getDataInfoFile(EbpConstants.TPSENDMSGID).getTablename() + " set state = ? where state = ?  and msgId = ?";
		List<Object> inputList = new LinkedList<Object>();
		inputList.add(toState);
		inputList.add(fromState);
		inputList.add(msgId);
		int count = jsDao.execute(sql, inputList);
		if(count > 0){
			return true;
		} else {
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public void invokeWaitApprove() throws EbillsException{
		String operateRecord = (String) this.context.get("CLWAITAPPROVE_operateRecord");
		if(StringUtils.isEmpty(operateRecord)){
			return ;
		}
		Map<String,Object> map = CommonUtil.JsonToMap(operateRecord);
		
		JSONArray record = (JSONArray) map.get("rows");
		for(int i=0; i < record.size(); i++){
			JSONObject json = (JSONObject)record.get(i);
			Map<String,Object> oneChg = CommonUtil.JsonToMap(json.toString()) ;
			String operate = (String) oneChg.get("operate");
			String msgId = (String) oneChg.get("msgId");
			boolean flag = false;
			if("1".equals(operate)){
				flag = updateWaitApproveState(msgId, "WA", ClearConstants.SEND_APPROVED);
			} else if("0".equals(operate)){
				flag = updateWaitApproveState(msgId, "WA", "FD");
				//发起报文修改交易
				startModifyTradeByMsgId(msgId, "报文落地,管理员退回");
				
			}else {
				continue;
			}
			
			if(!flag){
				this.errMsg = "报文编号:"+ msgId + "可能已由别的用户通过或退回";
				return ;
				//throw new EbillsException(new Exception("报文编号:"+ msgId + "可能已由别的用户通过或退回"), this.className);
			}
			arvMssdMsg(msgId);
		}
	}
	
	public void arvMssdMsg(String msgId) throws EbillsException{
		ClearUtils.arvMsSdMsg(msgId);
	}
	
	
	public void rollBackSendCash(String msgId) throws EbillsException{
		EbpDao jsDao = new EbpDao();
		WaitSendMsg wsm= null;
		Map<String,Object> mapParam = new HashMap<String, Object>();
		mapParam.put("msgId", msgId);
		List<Map<String,Object>> list = jsDao.queryByDataId(EbpConstants.TPSENDMSGID, "", mapParam);
		for(Map<String,Object> temp : list){
			wsm=ClearUtils.mapToWaitSendMsg(temp);
		}
		if(wsm == null){
			return ;
		}
		String msgType = wsm.getMsgType();
		if(!ClearUtils.isCashMsg(msgType)){
			return ;
		}
		if(msgType.startsWith("MT103")){
			String sql = "select count(0) as cnt from ( " +
						"select t.msgtype from mssdmsgfo t," +
						"(select txnno from mssdmsgfo where msgId = ?) t1 where t.txnno = t1.txnno and t.msgid <> ?" +
						") a, clcashmsg b where a.msgtype = b.msgtype "; //判断是否有其他头寸电
			List<Object> inputList = new LinkedList<Object>();
			inputList.add(msgId);
			inputList.add(msgId);
			List<Map<String,Object>> cashList = jsDao.queryBySql(sql, "cnt", "", inputList);
			if(cashList != null && !cashList.isEmpty()){
				Map<String,Object> temp = cashList.get(0);
				int cnt = Integer.parseInt((String)temp.get("cnt"));
				if(cnt > 0) {//找到其他头寸电103 不恢复头寸
					return ;
				}
			}
		}
		String receiver = wsm.getReceiverCode();
		String currency = wsm.getCurrency();
		Double amount = wsm.getAmount();
		Date valueDate = wsm.getValueDate();
		Date tranDate = wsm.getTranDate();
		String workDay = CommonUtil.getSysFld(EbpConstants.WORK_DATE);
		String clearingTime = getClearingTime(wsm);
		
		SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try {
			date = sdf.parse(workDay);
		} catch (ParseException e) {
		}
		if(valueDate.getTime() - date.getTime() != 0){ //起息日不在当天不处理
			return ;
		}
		
		String accountNo = getAccountNoByBic(receiver, currency);
		
		String sql = "update CLCASHFO set CASHCCYAMT = nvl(CASHCCYAMT,0) + ?, CASHBAL = nvl(CASHBAL,0) + ?";
		sql += ", TRANAMT = ?, FILENO = ?, UPDATETIME = ? where (ACCBKNO = ? or ACCBKNO = ?) and ACCTNO = ? and CASHCCY = ? and valuedate = ?";
		List<Object> inputList = new LinkedList<Object>();
		inputList.add(amount);
		inputList.add(amount);
		inputList.add(amount);
		inputList.add(wsm.getMsgId());
		inputList.add(new Date());
		inputList.add(receiver);
		inputList.add(parseBic(receiver));
		inputList.add(accountNo);
		inputList.add(currency);
		inputList.add(valueDate);
		jsDao.execute(sql, inputList);
		ClearUtils.arvCash(parseBic(receiver), currency);
		
		if(StringUtils.isNotEmpty(clearingTime)){
			Date checkTime = getCheckTime(workDay, clearingTime);
			if(tranDate.getTime() > checkTime.getTime()){
				//手工头寸预报
			} else {
				//自动头寸预报
			}
		}
	}
	
	private String parseBic(String bankBic) throws EbillsException{
		if(StringUtils.isNotEmpty(bankBic) && bankBic.toUpperCase().endsWith("XXX")){
			bankBic = bankBic.substring(0,8);
		}
		return bankBic;
	}
	
	private String getAccountNoByBic(String bic, String currency) throws EbillsException{
		EbpDao jsDao = new EbpDao();
		bic = parseBic(bic);
		String sql = "select t.acctNo from PAACCFO t inner join pabank t1 on t1.bankNo = t.custBankNo where t.accttypeno in('ZA00000051','ZA00000052') and t1.bankswiftcode = ?  and cursign = ?";
		List<Object> inputList = new LinkedList<Object>();
		inputList.add(bic);
		inputList.add(currency);
		List<Map<String,Object>> list = jsDao.queryBySql(sql, "acctNo", "", inputList);
		if(list != null && !list.isEmpty()){
			Map<String,Object> map = list.get(0);
			return (String) map.get("acctNo");
		}
		return "";
	}
	
	
	private String getClearingTime(WaitSendMsg wsm) throws EbillsException {
		EbpDao jsDao = new EbpDao();
		String sql = "select ccy, acctBkswift, clearingTime from paclrtim where ccy = ? and acctBkswift = ?";
		List<Object> inputList = new LinkedList<Object>();
		inputList.add(wsm.getCurrency());
		String receiver = wsm.getReceiverCode();
		if(receiver.toLowerCase().endsWith("xxx")){
			receiver = receiver.substring(0,8);
		}
		inputList.add(receiver);
		List<Map<String,Object>> list = jsDao.queryBySql(sql, "ccy,acctBkswift,clearingTime", "", inputList);
		if(list != null && !list.isEmpty()){
			String clearingTime = (String) list.get(0).get("clearingTime");
			return clearingTime;
		}
		return null;
	}
	
	public void hasModified(String relateNo, String txnNo) throws EbillsException{
		EbpDao jsDao = new EbpDao();
		try {
			String msgId="";
			if(StringUtils.isNotEmpty(relateNo)){
				msgId=relateNo;
			}else{
				String sql ="select fileNo  from  butxntp where  txnno=?";
				List<Object> inputList = new LinkedList<Object>();
				inputList.add(txnNo);
				List<Map<String,Object>> ret = jsDao.queryBySql(sql, "fileNo", "", inputList);
				Map<String,Object> map=ret.get(0);
				msgId=(String)map.get("fileNo");
			}
			String sql = "select count(0) from " + DataFactory.getDataInfoFile(EbpConstants.TPSENDMSGID).getTablename() + " where msgId = ? and state in ('HF','FD')";
			List<Object> inputList = new LinkedList<Object>();
			inputList.add(msgId);
			List<Map<String,Object>> list = jsDao.queryBySql(sql, "cnt", "", inputList);
			if(list != null && !list.isEmpty()){
				Map<String,Object> map = list.get(0);
				String cnt = (String) map.get("cnt");
				if(Integer.parseInt(cnt) == 0){
					throw new Exception("报文已由其他渠道或其他业务人员修改");
				}
			}
			
			//报文修改需要检测是否做过手工冲账、或则退回处理
			String sql1 ="select count(*)  from butxnar a"+ 
					" where a.curtbizno = (select distinct t.bizno from MSSDMSGFO t where t.msgid =?)" +
					" and a.tradeno in('070203','031004') and a.transtate ='4'";
			List<Map<String,Object>> list1 = jsDao.queryBySql(sql1, "cnt1", "", inputList);
			if(list1 != null && !list1.isEmpty()){
				Map<String,Object> map1 = list1.get(0);
				String cnt1 = (String) map1.get("cnt1");
				if(Integer.parseInt(cnt1) > 0){
					throw new Exception("请注意：报文修改已被退回处理或手工冲账处理！");
				}
			}
			
			//报文修改需要检测是否手工冲账、或则退回处理处理中
			String sql2 ="select count(*)  from butxntp a"+ 
						" where a.curtbizno = (select distinct t.bizno from MSSDMSGFO t where t.msgid =?)" +
						" and a.tradeno in('070203','031004')";
			List<Map<String,Object>> list2 = jsDao.queryBySql(sql2, "cnt2", "", inputList);
			if(list2 != null && !list2.isEmpty()){
				Map<String,Object> map2 = list2.get(0);
				String cnt2 = (String) map2.get("cnt2");
				if(Integer.parseInt(cnt2) > 0){
					throw new Exception("请注意：报文修改正在被退回处理或手工冲账处理！");
				}
			}
		} catch (Exception e) {
			this.errMsg = e.getMessage();
		}
		
	}
	
	public void hasCheck(String relateNo, String txnNo) throws EbillsException{
		EbpDao jsDao = new EbpDao();
		try {
			String msgId="";
			if(StringUtils.isNotEmpty(relateNo)){
				msgId=relateNo;
			}else{
				String sql ="select fileNo  from  butxntp where  txnno=?";
				List<Object> inputList = new LinkedList<Object>();
				inputList.add(txnNo);
				List<Map<String,Object>> ret = jsDao.queryBySql(sql, "fileNo", "", inputList);
				Map<String,Object> map=ret.get(0);
				msgId=(String)map.get("fileNo");
			}
			List<Object> inputList = new LinkedList<Object>();
			inputList.add(msgId);

			
			//报文修改需要检测是否做过手工冲账、或则退回处理
			String sql1 ="select count(*)  from butxnar a"+ 
					" where a.curtbizno = (select distinct t.bizno from MSSDMSGFO t where t.msgid =?)" +
					" and a.tradeno in('070203','031004') and a.transtate ='4'";
			List<Map<String,Object>> list1 = jsDao.queryBySql(sql1, "cnt1", "", inputList);
			if(list1 != null && !list1.isEmpty()){
				Map<String,Object> map1 = list1.get(0);
				String cnt1 = (String) map1.get("cnt1");
				if(Integer.parseInt(cnt1) > 0){
					throw new Exception("请注意：报文修改已被退回处理或手工冲账处理！");
				}
			}
			
			//报文修改需要检测是否手工冲账、或则退回处理处理中
			String sql2 ="select count(*)  from butxntp a"+ 
						" where a.curtbizno = (select distinct t.bizno from MSSDMSGFO t where t.msgid =?)" +
						" and a.tradeno in('070203','031004')";
			List<Map<String,Object>> list2 = jsDao.queryBySql(sql2, "cnt2", "", inputList);
			if(list2 != null && !list2.isEmpty()){
				Map<String,Object> map2 = list2.get(0);
				String cnt2 = (String) map2.get("cnt2");
				if(Integer.parseInt(cnt2) > 0){
					throw new Exception("请注意：报文修改正在被退回处理或手工冲账处理！");
				}
			}
		} catch (Exception e) {
			this.errMsg = e.getMessage();
		}
		
	}
	
	public void startModifyTradeByMsgId(String msgId, String memo){
		try{
			EbpDao jsDao = new EbpDao();
			Map<String,Object> mapParam = new HashMap<String, Object>();
			mapParam.put("msgId", msgId);
			jsDao.deleteRow("CLAUTOCASHFO", "", mapParam); //删除自动头寸预报
			List<Map<String,Object>> list = jsDao.queryByDataId(EbpConstants.TPSENDMSGID, "", mapParam);
			for(Map<String,Object> temp : list){
				Map<String,Object> newTaskInfo = new HashMap<String,Object>();
				newTaskInfo.put("orgNo",(String) temp.get("orgNo"));
				newTaskInfo.put("tranCur",(String) temp.get("currency"));
				newTaskInfo.put("tranAmt",(Double) temp.get("amount"));
				String startDate = CommonUtil.getSysFld(EbpConstants.WORK_DATE);
				newTaskInfo.put("startDate",CommonUtil.formatDate(startDate));
				newTaskInfo.put("tradeNo",ClearConstants.PACKETS_MODIFY_TRADENO);
				newTaskInfo.put("fileNo",(String) temp.get("msgId"));
	//			String valDate = CommonUtil.unFormatDate(wsm.getValueDate(),"yyyy-MM-dd");
	//			if(null == valDate)valDate = "";
				newTaskInfo.put("memo",memo);
				newTaskInfo.put("startModeId","2");
				NewTaskFactory.createNewTask(newTaskInfo);
			}
		}catch(Exception e){
			this.errMsg = e.getMessage();
		}
	}
	
	public void doClearCash(String msgid, boolean isAddCash) throws EbillsException{
		this.doClearCash(msgid, isAddCash, null);
	}
	
	public void doClearCash(String msgid, boolean isAddCash, Double transAmt) throws EbillsException{
		if(StringUtils.isEmpty(msgid))return;
		EbpDao jsDao = new EbpDao();
		Map<String,Object> mapParam = new HashMap<String, Object>();
		mapParam.put("msgId", msgid);
		List<Map<String,Object>> list = jsDao.queryByDataId(EbpConstants.CLMSGQUEFO, "", mapParam);
		if(list!=null && list.size()>0){
			Map<String,Object> map = list.get(0);
			String cur = (String)map.get("currency");
			Double amount = (Double)map.get("amount");
			if(transAmt!=null)amount = transAmt;
			String acctBkNo = (String)map.get("senderCode");
			String account = getAccountNoByBic(acctBkNo, cur);
			//更新账户行头寸
			String sql = "update CLCASHFO set CASHCCYAMT = nvl(CASHCCYAMT,0)+?, CASHBAL = nvl(CASHBAL,0)+?, TRANAMT = ? " +
					", UPDATETIME = ?, FILENO = ? where (ACCBKNO = ? or ACCBKNO = ?) and ACCTNO = ? and CASHCCY = ?";
			
			List<Object> inputList = new LinkedList<Object>();
			if(!isAddCash){ //如果是扣减头寸，金额转换为负值
				amount = 0-amount;
			}
			inputList.add(amount);
			inputList.add(amount);
			inputList.add(amount);
			inputList.add(new Date());
			inputList.add(msgid);
			inputList.add(acctBkNo);
			inputList.add(parseBic(acctBkNo));
			inputList.add(account);
			inputList.add(cur);
			jsDao.execute(sql, inputList);
			ClearUtils.arvCash(acctBkNo, cur);
		}
	}
	
	public void updateHandCheckCash(String acctBkNo,String cur, Double amount, boolean isHandClear) throws EbillsException{
			if(StringUtils.isEmpty(acctBkNo))return;
			EbpDao jsDao = new EbpDao();
			String account = getAccountNoByBic(acctBkNo, cur);
			//更新账户行头寸
			String sql = "update CLCASHFO set CASHCCYAMT = nvl(CASHCCYAMT,0)+?, CASHBAL = nvl(CASHBAL,0)+?, TRANAMT = ? " +
					", UPDATETIME = ? where (ACCBKNO = ? or ACCBKNO = ?) and ACCTNO = ? and CASHCCY = ?";
			
			List<Object> inputList = new LinkedList<Object>();
			if(!isHandClear){ //如果是扣减头寸，金额转换为负值
				amount = 0-amount;
			}
			inputList.add(amount);
			inputList.add(amount);
			inputList.add(amount);
			inputList.add(new Date());
			//inputList.add(msgid);
			inputList.add(acctBkNo);
			inputList.add(parseBic(acctBkNo));
			inputList.add(account);
			inputList.add(cur);
			jsDao.execute(sql, inputList);
			ClearUtils.arvCash(acctBkNo, cur);
	}
	
	/**
	 * 手工冲账提交验证是否被报文修改、退回处理处理在处理
	 * @param oldTxnNo
	 * @throws EbillsException
	 */
	public void hasDealByMB(String bizNo) throws EbillsException{
		EbpDao jsDao = new EbpDao();
		try {
			//手工冲账检测是否被报文修改处理中
			String sql ="select t.state from wftsk t " +
					" where t.txnno = (select distinct t.txnno from butxntp t,MSSDMSGFO f" +
					" where t.fileno = f.msgid and tradeno ='030609' and f.bizno =?)";
			List<Object> inputList = new LinkedList<Object>();
			inputList.add(bizNo);
			List<Map<String,Object>> list = jsDao.queryBySql(sql, "ste", "", inputList);
			if(list != null && !list.isEmpty()){
				Map<String,Object> map = list.get(0);
				String ste = (String) map.get("ste");
				if(Integer.parseInt(ste) != 1){
					throw new Exception("请注意：手工冲账正在被报文修改处理！");
				}
			}
		
			//手工冲账是否正在被退回处理处理中
			String sql1 ="select count(*) from butxntp a"+ 
						" where a.curtbizno = ? and a.tradeno ='070203'";
			List<Map<String,Object>> list1 = jsDao.queryBySql(sql1, "cnt", "", inputList);
			if(list1 != null && !list1.isEmpty()){
				Map<String,Object> map1 = list1.get(0);
				String cnt = (String) map1.get("cnt");
				if(Integer.parseInt(cnt) > 0){
					throw new Exception("请注意：报文修改正在被退回处理交易处理！");
				}
			}
		} catch (Exception e) {
			this.errMsg = e.getMessage();
		}
		
	}
	
	/**
	 *通过流水号更新fileno，通过更新fileno，可以用来在非报文发起的交易也能看到前序报文。
	 *汇入汇款发起的后续解付，或者退汇/不落地结汇中可以查看汇入汇款的报文
	 * @param txnno
	 * @throws EbillsException
	 */
	public void updateFilenoBytxn(String txnno,String fileNo) throws EbillsException{
		EbpDao jsDao = new EbpDao();
		try {
			List<Object> inputList = new LinkedList<Object>();			
			String usql = "update butxntp set fileNo=? where txnNo=?";
			inputList = new LinkedList<Object>();
			inputList.add(fileNo);
			inputList.add(txnno);			
			jsDao.execute(usql, inputList);
		} catch (EbillsException e) {
			this.errMsg = new  EbillsException(className,8,new String[]{txnno}).getMessage(language);
		}
	}
}
