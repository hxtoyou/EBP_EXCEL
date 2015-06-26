package com.ebills.product.dg.intf;

import java.text.SimpleDateFormat;

import org.apache.commons.bussprocess.context.Context;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ebills.product.dg.intf.InterfacePrcscd;
import com.ebills.product.dg.intf.ocif.OcifInterface;
import com.ebills.product.dg.intf.credit.CreditInterfaceBiz;
import com.ebills.product.dg.intf.credit.InterfaceCreditRequst;
import com.ebills.product.script.CreditScript;

public class ServerInterface {
	private final Log log = LogFactory.getLog(getClass());
	public void recevice(Context context) {
	  
	  InterfaceRequestHead head= this.parseHead(context);
	  this.setResponseHead(context);
      log.debug("context中取出报文头的信息=============="+context.get("prcscd"));
      if(head.getPrcscd()==null){
			context.put("errorCode", IErrorCode.GJYW_1);
			context.put("errorMsg", IErrorCode.GJYW_MSG_1);
			context.put("sverretcod", IErrorCode.GJYW_1);
			context.put("sverretmsg", IErrorCode.GJYW_MSG_1);
      }else{
    	  CreditInterfaceBiz biz=new CreditInterfaceBiz();
    	  OcifInterface ocif = new OcifInterface();
  		if (InterfacePrcscd.CRIN_01.equals(head.getPrcscd())) {
  			InterfaceCreditRequst creidtrequest =biz.parse(context);
  			creidtrequest.setStatus("0");
  			biz.callCRIN01(creidtrequest, context);
		} else if (InterfacePrcscd.CRIN_02.equals(head.getPrcscd())) {
			InterfaceCreditRequst creidtrequest =biz.parse(context);
			creidtrequest.setStatus("0");
			biz.callCRIN02(creidtrequest, context);
		} else if (InterfacePrcscd.CRIN_03.equals(head.getPrcscd())){
			InterfaceCreditRequst creidtrequest =biz.parse(context);
			creidtrequest.setStatus("0");
			biz.callCRIN03(creidtrequest, context);
		} else if (InterfacePrcscd.CRIN_04.equals(head.getPrcscd())){
			InterfaceCreditRequst creidtrequest =biz.parse(context);
			creidtrequest.setStatus("0");
			biz.callCRIN04(creidtrequest, context);
		} else if (InterfacePrcscd.CRIN_05.equals(head.getPrcscd())) {
			InterfaceCreditRequst creidtrequest =biz.parse(context);
			creidtrequest.setStatus("0");
			biz.callCRIN05(creidtrequest, context);
		} else if (InterfacePrcscd.CROU_01.equals(head.getPrcscd())) {
			InterfaceCreditRequst creidtrequest =biz.parse(context);
			creidtrequest.setStatus("0");
			biz.callCROU01(creidtrequest, context);
		} else if (InterfacePrcscd.CROU_02.equals(head.getPrcscd())) {
			InterfaceCreditRequst creidtrequest =biz.parse(context);
			creidtrequest.setStatus("0");
			biz.callCROU02(creidtrequest, context);
		} else if (InterfacePrcscd.CROU_03.equals(head.getPrcscd())) {
			InterfaceCreditRequst creidtrequest =biz.parse(context);
			creidtrequest.setStatus("0");
			biz.callCROU03(creidtrequest, context);
		} else if ("cmmerg".equals(head.getPrcscd())){//以下接口是OCIF作为客户端发给国结的报文
			ocif.callCmmerg(head, context);
		} else if ("unmerg".equals(head.getPrcscd())) {
			ocif.callUnmerg(head, context);
		} else if ("upprsn".equals(head.getPrcscd())) {
			ocif.callUpprsn(head, context);
		} else if ("upcorp".equals(head.getPrcscd())) {
			ocif.callUpcorp(head, context);
		} else if ("qucust".equals(head.getPrcscd())) {
			ocif.callQucust(head, context);
		}  else {
			context.put("errorCode", IErrorCode.GJYW_1);
			context.put("errorMsg", IErrorCode.GJYW_MSG_1);
			context.put("sverretcod", IErrorCode.GJYW_1);
			context.put("sverretmsg", IErrorCode.GJYW_MSG_1);
		}
      }     
	}
    
	private InterfaceRequestHead parseHead(Context context){
		InterfaceRequestHead head=new InterfaceRequestHead();
		head.setEciSeverId((String)context.get("eciSeverId"));
		head.setXmlflag((String)context.get("xmlflag"));
		head.setTemplateCodeName((String)context.get("templateCodeName"));
		head.setTransCode((String)context.get("transCode"));
		head.setSysId((String)context.get("sysId"));
		head.setChannelCode((String)context.get("channelCode"));
		head.setSubchannelCode((String)context.get("subchannelCode"));
		head.setTradeFlag((String)context.get("tradeFlag"));
		head.setCheckFlag((String)context.get("checkFlag"));
		head.setChannelserno((String)context.get("channelserno"));
        head.setSessid((String)context.get("sessid"));
        head.setPrcscd((String)context.get("prcscd"));
        head.setZoneno((String)context.get("zoneno"));
        head.setMbrno((String)context.get("mbrno"));
        head.setBrno((String)context.get("brno"));
        head.setTellerno((String)context.get("tellerno"));
        head.setTellertp((String)context.get("tellertp"));
        head.setCsbxno((String)context.get("csbxno"));
        head.setDutytp((String)context.get("dutytp"));
        head.setDutyno((String)context.get("dutyno"));
        head.setAuthno((String)context.get("authno"));
        head.setAuthce((String)context.get("authpw"));
        head.setAuthmanfttype((String)context.get("authmg"));
        head.setAuthce((String)context.get("authce"));
        head.setAuthmanfttype((String)context.get("authmanfttype"));
        head.setReplyquery((String)context.get("replyquery"));
		return head;
	}	
public void setResponseHead(Context context){
	java.util.Date date=new java.util.Date();
	context.put("sverworkdate", new SimpleDateFormat("yyyyMMdd").format(date).toString());
	context.put("sverworktime", new SimpleDateFormat("HHmmss").format(date).toString());
	context.put("sverserialno", context.get("intfno"));
}
}
