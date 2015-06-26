package com.ebills.product.dg.credit.send;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.bussprocess.context.Context;

import com.ebills.util.EbillsException;
import com.ebills.product.component.BalanceInfo;
import com.ebills.product.dg.credit.dao.BatchDao;
import com.ebills.product.dg.credit.dao.SendCreditNoticeDao;
import com.ebills.utils.CommonUtil;

public class TradeOtherDtlsBiz {

	/**
	 *保存信用证到单交易明细
	 **/
	public void saveLcAbDtls(Context context) throws EbillsException{
		SendCreditNoticeDao dao=new SendCreditNoticeDao();
		String txnNo=(String)context.get("txnNo");
		String abNo=(String)context.get("IMLCAB_abNo");
		String lcNo=(String)context.get("IMLCAB_lcNo");
		List<Map<String,Object>> lcList=dao.getLcList(lcNo);
		String feeno="";
		if(lcList!=null&&lcList.size()>0){
			feeno=(String)lcList.get(0).get("feeno");
		}
		String detailType="0";
		String abDate=(String)context.get("IMLCAB_abDate");
		abDate=dateToDate(abDate);
		String abCur=(String)context.get("IMLCAB_abCur");
		String abAmt=(String)context.get("IMLCAB_abAmt");
		Map<String,Object> param=new HashMap<String,Object>();
	    param.put("serno",txnNo);
	    param.put("billno",feeno);
	    param.put("detailtyp",detailType);
	    param.put("detailno",abNo);
	    param.put("detaildate",abDate);
	    param.put("paydate",null);
	    param.put("detailcurtyp",abCur);
	    param.put("detailamount",abAmt);
	    param.put("detailremarks",null);
	    param.put("otheramount",null);
	    BatchDao batchDao = new BatchDao();
	    batchDao.saveOtherDtls(param);
	}
	
	/**
	 *保存信用证到单修改交易明细
	 **/
	public void saveLcAbAmdDtls(Context context) throws EbillsException{
		SendCreditNoticeDao dao=new SendCreditNoticeDao();
		String txnNo=(String)context.get("txnNo");
		String abNo=(String)context.get("IMLCAB_abNo");
		String lcNo=(String)context.get("IMLCAB_lcNo");
		List<Map<String,Object>> lcList=dao.getLcList(lcNo);
		String feeno="";
		if(lcList!=null&&lcList.size()>0){
			feeno=(String)lcList.get(0).get("feeno");
		}
		String detailType="1";
		String abDate=(String)context.get("IMLCAB_abDate");
		abDate=dateToDate(abDate);
		String abCur=(String)context.get("IMLCAB_abCur");
		String abAmt=(String)context.get("IMLCAB_abAmt");
		Map<String,Object> param=new HashMap<String,Object>();
	    param.put("serno",txnNo);
	    param.put("billno",feeno);
	    param.put("detailtyp",detailType);
	    param.put("detailno",abNo);
	    param.put("detaildate",abDate);
	    param.put("paydate",null);
	    param.put("detailcurtyp",abCur);
	    param.put("detailamount",abAmt);
	    param.put("detailremarks",null);
	    param.put("otheramount",null);
	    BatchDao batchDao = new BatchDao();
	    batchDao.saveOtherDtls(param);
	}
	
	/**
	 *保存信用证二次到单交易明细
	 **/
	public void saveLcAbSecDtls(Context context) throws EbillsException{
		SendCreditNoticeDao dao=new SendCreditNoticeDao();
		String txnNo=(String)context.get("txnNo");
		String abNo=(String)context.get("IMLCAB_abNo");
		String lcNo=(String)context.get("IMLCAB_lcNo");
		List<Map<String,Object>> lcList=dao.getLcList(lcNo);
		String feeno="";
		if(lcList!=null&&lcList.size()>0){
			feeno=(String)lcList.get(0).get("feeno");
		}
		String detailType="4";
		String abDate=(String)context.get("IMLCAB_twoAbDate");
		abDate=dateToDate(abDate);
		String abCur=(String)context.get("IMLCAB_abCur");
		String abAmt=(String)context.get("IMLCAB_abAmt");
		Map<String,Object> param=new HashMap<String,Object>();
	    param.put("serno",txnNo);
	    param.put("billno",feeno);
	    param.put("detailtyp",detailType);
	    param.put("detailno",abNo);
	    param.put("detaildate",abDate);
	    param.put("paydate",null);
	    param.put("detailcurtyp",abCur);
	    param.put("detailamount",abAmt);
	    param.put("detailremarks",null);
	    param.put("otheramount",null);
	    BatchDao batchDao = new BatchDao();
	    batchDao.saveOtherDtls(param);
	}
	
	/**
	 *保存信用退放单交易明细
	 **/
	public void saveLcReturnDtls(Context context) throws EbillsException{
		SendCreditNoticeDao dao=new SendCreditNoticeDao();
		String txnNo=(String)context.get("txnNo");
		String abNo=(String)context.get("IMLCAB_abNo");
		String lcNo=(String)context.get("IMLCAB_lcNo");
		List<Map<String,Object>> lcList=dao.getLcList(lcNo);
		String feeno="";
		if(lcList!=null&&lcList.size()>0){
			feeno=(String)lcList.get(0).get("feeno");
		}
		String detailType="6";
		String abDate=(String)context.get("PBREJ_returnDate");
		abDate=dateToDate(abDate);
		String abCur=(String)context.get("PBREJ_returnCur");
		String abAmt=(String)context.get("PBREJ_returnAmt");
		Map<String,Object> param=new HashMap<String,Object>();
	    param.put("serno",txnNo);
	    param.put("billno",feeno);
	    param.put("detailtyp",detailType);
	    param.put("detailno",abNo);
	    param.put("detaildate",abDate);
	    param.put("paydate",null);
	    param.put("detailcurtyp",abCur);
	    param.put("detailamount",abAmt);
	    param.put("detailremarks",null);
	    param.put("otheramount",null);
	    BatchDao batchDao = new BatchDao();
	    batchDao.saveOtherDtls(param);
	}
//	
//	/**
//	 *保存持单拒付交易明细
//	 **/
//	public void saveLcRejDtls(Context context) throws EbillsException{
//		SendCreditNoticeDao dao=new SendCreditNoticeDao();
//		String txnNo=(String)context.get("txnNo");
//		String abNo=(String)context.get("IMLCAB_abNo");
//		String lcNo=(String)context.get("IMLCAB_lcNo");
//		List<Map<String,Object>> lcList=dao.getLcList(lcNo);
//		String feeno="";
//		if(lcList!=null&&lcList.size()>0){
//			feeno=(String)lcList.get(0).get("feeno");
//		}
//		String detailType="2";
//		String rejDate=(String)context.get("PBREJ_rejDate");
//		String rejCur=(String)context.get("PBREJ_rejAmt");
//		String rejAmt=(String)context.get("PBREJ_rejAmt");
//		String rejReson=(String)context.get("PBREJ_memo");
//		Map<String,Object> param=new HashMap<String,Object>();
//	    param.put("serno",txnNo);
//	    param.put("billno",feeno);//借据号 待查
//	    param.put("detailtyp",detailType);
//	    param.put("detailno",abNo);
//	    param.put("detaildate",rejDate);
//	    param.put("paydate",null);
//	    param.put("detailcurtyp",rejCur);
//	    param.put("detailamount",rejAmt);
//	    param.put("detailremarks",rejReson);
//	    param.put("otheramount",null);
//	}
	
//	/**
//	 *保存信用证承兑交易明细
//	 **/
//	public void saveLcAcptDtls(Context context) throws EbillsException{
//		SendCreditNoticeDao dao=new SendCreditNoticeDao();
//		String txnNo=(String)context.get("txnNo");
//		String abNo=(String)context.get("IMLCAB_abNo");
//		String lcNo=(String)context.get("IMLCAB_lcNo");
//		List<Map<String,Object>> lcList=dao.getLcList(lcNo);
//		String feeno="";
//		if(lcList!=null&&lcList.size()>0){
//			feeno=(String)lcList.get(0).get("feeno");
//		}
//		String detailType="3";
//		String acptDate=(String)context.get("PBACPT_acptDate");
//		String maturyDate=(String)context.get("PBACPT_maturyDate");
//		String acptCur=(String)context.get("PBACPT_acptCur");
//		String acptAmt=(String)context.get("PBACPT_acptAmt");
//		Map<String,Object> param=new HashMap<String,Object>();
//	    param.put("serno",txnNo);
//	    param.put("billno",feeno);//借据号 待查
//	    param.put("detailtyp",detailType);
//	    param.put("detailno",abNo);
//	    param.put("detaildate",acptDate);
//	    param.put("paydate",maturyDate);
//	    param.put("detailcurtyp",acptCur);
//	    param.put("detailamount",acptAmt);
//	    param.put("detailremarks",null);
//	    param.put("otheramount",null);
//	}
//	
//	/**
//	 *保存信用开立交易明细
//	 **/
//	public void saveLcIssueDtls(Context context) throws EbillsException{
//		SendCreditNoticeDao dao=new SendCreditNoticeDao();
//		String txnNo=(String)context.get("txnNo");
//		String lcNo=(String)context.get("IMLCISSUE_lcNo");
//		String feeNo=(String)context.get("IMLCISSUE_feeNo");
//		String detailType="4";
//		String issueDate=(String)context.get("IMLCISSUE_lcIssueDate");
//		String expiryDate=(String)context.get("IMLCISSUE_expiryDate");
//		String lcCur=(String)context.get("IMLCISSUE_lcCursign");
//		String lcAmt=(String)context.get("IMLCISSUE_lcAmt");
//		String addAmt=(String)context.get("IMLCISSUE_lcAddiAmt");
//		Map<String,Object> param=new HashMap<String,Object>();
//	    param.put("serno",txnNo);
//	    param.put("billno",feeNo);//借据号 待查
//	    param.put("detailtyp",detailType);
//	    param.put("detailno",null);//到单号
//	    param.put("detaildate",issueDate);
//	    param.put("paydate",expiryDate);
//	    param.put("detailcurtyp",lcCur);
//	    param.put("detailamount",lcAmt);
//	    param.put("detailremarks",null);
//	    param.put("otheramount",addAmt);
//	}
//	
//	/**
//	 *保存信用开立修改交易明细
//	 **/
//	public void saveLcIssueCamDtls(Context context) throws EbillsException{
//		SendCreditNoticeDao dao=new SendCreditNoticeDao();
//		String txnNo=(String)context.get("txnNo");
//		String lcNo=(String)context.get("IMLCISSUE_lcNo");
//		List<Map<String,Object>> lcList=dao.getLcList(lcNo);
//		String feeNo=(String)context.get("IMLCISSUE_feeNo");
//		String lcCur="";
//		if(lcList!=null&&lcList.size()>0){
//			lcCur=(String)lcList.get(0).get("lccursign");
//		}
//		String detailType="5";
//		String amdDate=(String)context.get("IMLCAMEND_amdDate");
//		//String lcCur=(String)context.get("IMLCISSUE_lcCursign");
//		String lcAmt=(String)context.get("IMLCISSUE_lcAmt");
//		Map<String,Object> param=new HashMap<String,Object>();
//	    param.put("serno",txnNo);
//	    param.put("billno",feeNo);//借据号 待查
//	    param.put("detailtyp",detailType);
//	    param.put("detailno",null);//到单号
//	    param.put("detaildate",amdDate);
//	    param.put("paydate",null);
//	    param.put("detailcurtyp",lcCur);
//	    param.put("detailamount",lcAmt);
//	    param.put("detailremarks",null);
//	    param.put("otheramount",null);
//	}
	
	/**
	 *保存信用付汇交易明细
	 **/
	public void saveLcPayDtls(Context context) throws EbillsException{
		SendCreditNoticeDao dao=new SendCreditNoticeDao();
		String txnNo=(String)context.get("txnNo");
		String abNo=(String)context.get("IMLCAB_abNo");
		String lcNo=(String)context.get("IMLCAB_lcNo");
		List<Map<String,Object>> lcList=dao.getLcList(lcNo);
		String feeno="";
		if(lcList!=null&&lcList.size()>0){
			feeno=(String)lcList.get(0).get("feeno");
		}
		String detailType="9";
		String payDate=(String)context.get("PBPAY_payDate");
		payDate=dateToDate(payDate);
		String abCur=(String)context.get("IMLCAB_abCur");
		String payAmt=(String)context.get("PBPAY_payAmt");
		Map<String,Object> param=new HashMap<String,Object>();
	    param.put("serno",txnNo);
	    param.put("billno",feeno);
	    param.put("detailtyp",detailType);
	    param.put("detailno",abNo);
	    param.put("detaildate",payDate);
	    param.put("paydate",payDate);
	    param.put("detailcurtyp",abCur);
	    param.put("detailamount",payAmt);
	    param.put("detailremarks",null);
	    param.put("otheramount",null);
	    BatchDao batchDao = new BatchDao();
	    batchDao.saveOtherDtls(param);
	}
	
//	/**
//	 *保存信用证撤销交易明细
//	 **/
//	public void saveLcCancelDtls(Context context) throws EbillsException{
//		SendCreditNoticeDao dao=new SendCreditNoticeDao();
//		String txnNo=(String)context.get("txnNo");
//		String lcNo=(String)context.get("IMLCISSUE_lcNo");
//		List<Map<String,Object>> lcList=dao.getLcList(lcNo);
//		String feeno="";
//		if(lcList!=null&&lcList.size()>0){
//			feeno=(String)lcList.get(0).get("feeno");
//		}
//		String detailType="7";
//		String cancelDate=(String)context.get("PBCLOSE_cancelDate");
//		String lcCur=(String)context.get("IMLCISSUE_lcCursign");
//		String lcAmt=(String)context.get("IMLCISSUE_lcAmt");
//		Map<String,Object> param=new HashMap<String,Object>();
//		param.put("serno",txnNo);
//		param.put("billno",feeno);//借据号 待查
//	    param.put("detailtyp",detailType);
//	    param.put("detailno",null);//到单号
//		param.put("detaildate",cancelDate);
//		param.put("paydate",null);
//		param.put("detailcurtyp",lcCur);
//		param.put("detailamount",lcAmt);
//		param.put("detailremarks",null);
//		param.put("otheramount",null);
//	}
//	
//	/**
//	 *保存信用证撤销交易明细
//	 **/
//	public void saveLcCloseDtls(Context context) throws EbillsException{
//		SendCreditNoticeDao dao=new SendCreditNoticeDao();
//		String txnNo=(String)context.get("txnNo");
//		String lcNo=(String)context.get("IMLCISSUE_lcNo");
//		List<Map<String,Object>> lcList=dao.getLcList(lcNo);
//		String feeno="";
//		if(lcList!=null&&lcList.size()>0){
//			feeno=(String)lcList.get(0).get("feeno");
//		}
//		String detailType="8";
//		String closeDate=(String)context.get("PBCLOSE_cancelDate");
//		String lcCur=(String)context.get("IMLCISSUE_lcCursign");
//		String lcAmt=(String)context.get("IMLCISSUE_lcAmt");
//		String resonkeyval=(String)context.get("PBCLOSE_dealReson");
//		String closeReson=CommonUtil.getI18NValue("RETIRE", "CAUSE", resonkeyval, "zh_CN");
//		Map<String,Object> param=new HashMap<String,Object>();
//		param.put("serno",txnNo);
//		param.put("billno",feeno);//借据号 待查
//	    param.put("detailtyp",detailType);
//	    param.put("detailno",null);//到单号
//		param.put("detaildate",closeDate);
//		param.put("paydate",null);
//		param.put("detailcurtyp",lcCur);
//		param.put("detailamount",lcAmt);
//		param.put("detailremarks",closeReson);
//		param.put("otheramount",null);
//	}
	
	/**
	 *保存保函赔付交易明细
	 **/
	public void saveLGPayDtls(Context context) throws EbillsException{
		SendCreditNoticeDao dao=new SendCreditNoticeDao();
		String txnNo=(String)context.get("txnNo");
		String claimNo=(String)context.get("IMLGCLAIM_claimNo");
		String lgNo=(String)context.get("IMLGISSUE_lgNo");
		List<Map<String,Object>> lgList=dao.getLgList(lgNo);
		String feeno="";
		if(lgList!=null&&lgList.size()>0){
			feeno=(String)lgList.get(0).get("creditno");
		}
		String detailType="6";
		String payDate=(String)context.get("PBPAY_payDate");
		payDate=dateToDate(payDate);
		String payCur=(String)context.get("PBPAY_payCur");
		String payAmt=(String)context.get("PBPAY_payAmt");
		Map<String,Object> param=new HashMap<String,Object>();
	    param.put("serno",txnNo);
	    param.put("billno",feeno);
	    param.put("detailtyp",detailType);
	    param.put("detailno",claimNo);
	    param.put("detaildate",payDate);
	    param.put("paydate",payDate);
	    param.put("detailcurtyp",payCur);
	    param.put("detailamount",payAmt);
	    param.put("detailremarks",null);
	    param.put("otheramount",null);
	    BatchDao batchDao = new BatchDao();
	    batchDao.saveOtherDtls(param);
	}
	
	/**
	 *保存保函索赔闭卷交易明细
	 **/
	public void saveLGCloseDtls(Context context) throws EbillsException{
		SendCreditNoticeDao dao=new SendCreditNoticeDao();
		String txnNo=(String)context.get("txnNo");
		String claimNo=(String)context.get("IMLGCLAIM_claimNo");
		List<Map<String,Object>> lgcList=dao.getLgList(claimNo);
		String lgNo="";
		if(lgcList!=null&&lgcList.size()>0){
			lgNo=(String)lgcList.get(0).get("lgno");
		}
		List<Map<String,Object>> lgList=dao.getLgList(lgNo);
		String feeno="";
		if(lgList!=null&&lgList.size()>0){
			feeno=(String)lgList.get(0).get("creditno");
		}
		String detailType="6";
		String dealDate=(String)context.get("PBCLOSE_dealDate");
		dealDate=dateToDate(dealDate);
		String claimCur=(String)context.get("IMLGCLAIM_claimCur");
		String claimAmt=(String)context.get("IMLGCLAIM_claimAmt");
		Map<String,Object> param=new HashMap<String,Object>();
	    param.put("serno",txnNo);
	    param.put("billno",feeno);
	    param.put("detailtyp",detailType);
	    param.put("detailno",claimNo);
	    param.put("detaildate",dealDate);
	    param.put("paydate",null);
	    param.put("detailcurtyp",claimCur);
	    param.put("detailamount",claimAmt);
	    param.put("detailremarks",null);
	    param.put("otheramount",null);
	    BatchDao batchDao = new BatchDao();
	    batchDao.saveOtherDtls(param);
	}
	private String dateToDate(String date){
		String tranDates[]=date.split("-");
		return tranDates[0]+tranDates[1]+tranDates[2];
	}
}
