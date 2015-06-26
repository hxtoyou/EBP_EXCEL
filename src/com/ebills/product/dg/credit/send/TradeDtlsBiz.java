package com.ebills.product.dg.credit.send;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.bussprocess.context.Context;
import org.apache.commons.lang.StringUtils;

import com.ebills.util.EbillsException;
import com.ebills.product.component.BalanceInfo;
import com.ebills.product.dg.commons.Commons;
import com.ebills.product.dg.credit.dao.BatchDao;
import com.ebills.product.dg.credit.dao.SendCreditNoticeDao;

public class TradeDtlsBiz {
   
	/**
	 *保存发送失败的交易明细到批量接口表 
	 */
	public void saveTradeDtls() {
		SendCreditNoticeDao noticeDao = new SendCreditNoticeDao();
		try {
			List<Map<String, Object>> list = noticeDao.getAllNotice();
			if (list != null && list.size() > 0) {
				for (Map<String, Object> result : list) {
					String txnno = (String) result.get("txnno");
					String feeno = (String) result.get("jjhmno");
					String eydate = (String) result.get("eydate");
					String leamts = (String) result.get("leamts");
					String trtype = (String) result.get("trtype");
					String trancur = "";
					String tranorg = "";
					List<Map<String, Object>> tranDataList = noticeDao
							.getButxnarAndOrg(txnno);
					if (tranDataList != null && tranDataList.size() > 0) {
						Map tranDate = tranDataList.get(0);
						trancur = (String) tranDate.get("trancur");
						tranorg = (String) tranDate.get("orgcode");
					}
					BatchDao batchDao = new BatchDao();
					Map<String, Object> param = new HashMap<String, Object>();
					SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
					String workDate=format.format(Commons.getWorkDate());
					param.put("systemdate", workDate);
					param.put("txnno", txnno);
					param.put("debtno", feeno);
					param.put("tradedate", eydate);
					param.put("cur", trancur);
					param.put("amt", leamts);
					param.put("org", tranorg);
					param.put("tradedetail", trtype);
					batchDao.saveTradeDtls(param);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 保存信用证开立到交易明细表
	 **/
	public void saveLcIssueDtls(Context context){
		String txnNo=(String)context.get("txnNo");
		String feeno=(String)context.get("IMLCISSUE_feeNo");
		String cur=(String)context.get("IMLCISSUE_lcCursign");
		String leamts=(String)context.get("IMLCISSUE_lcAmt");
		String tranorg=(String)context.get("butxn_tranOrgNo");
		BatchDao batchDao = new BatchDao();
		Map<String, Object> param = new HashMap<String, Object>();
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
		String workDate=format.format(Commons.getWorkDate());
		param.put("systemdate", workDate);
		param.put("txnno", txnNo);
		param.put("debtno", feeno);
		param.put("tradedate", workDate);
		param.put("cur", cur);
		param.put("amt", leamts);
		param.put("org", tranorg);
		param.put("tradedetail", "02");
		batchDao.saveTradeDtls(param);
	}
	
	/**
	 * 保存信用证开立修改交易明细表
	 **/
	public void saveLcAmendDtls(Context context){
		String txnNo=(String)context.get("txnNo");
		String feeno=(String)context.get("IMLCISSUE_feeNo");
		String cur=(String)context.get("IMLCISSUE_lcCursign");
		String leamts=(String)context.get("IMLCISSUE_lcAmt");
		String varAmt=(String)context.get("IMLCAMEND_varAmt");
		String tranorg=(String)context.get("butxn_tranOrgNo");
		String varFlag=(String)context.get("IMLCAMEND_varFlag");
		//if("+".equals(varFlag)){
			BatchDao batchDao = new BatchDao();
			Map<String, Object> param = new HashMap<String, Object>();
			SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
			String workDate=format.format(Commons.getWorkDate());
			param.put("systemdate", workDate);
			param.put("txnno", txnNo);
			param.put("debtno", feeno);
			param.put("tradedate", workDate);
			param.put("cur", cur);//代取
			param.put("amt", varAmt);
			param.put("org", tranorg);
			param.put("tradedetail", "43");
			batchDao.saveTradeDtls(param);
		//}

	}
	/**
	 * 保存信用证开立修改拒绝交易明细表
	 **/
	public void saveLcAmendAffirmDtls(Context context){
		String txnNo=(String)context.get("txnNo");
		String lcno = (String) context.get("IMLCAMEND_lcNo");
		String debtno="";
		String cur="";
		SendCreditNoticeDao dao = new SendCreditNoticeDao();
		List<Map<String, Object>> lcList = dao.getLcList(lcno);
		if(lcList.size()>0){
			debtno=(String)lcList.get(0).get("feeno");
			cur=(String)lcList.get(0).get("lccursign");
		}
		String leamts=(String)context.get("IMLCISSUE_lcAmt");
		String tranorg=(String)context.get("butxn_tranOrgNo");
		String  date=(String)context.get("IMLCAMEND_affirmDate");
		BatchDao batchDao = new BatchDao();
		Map<String, Object> param = new HashMap<String, Object>();
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
		String workDate=format.format(Commons.getWorkDate());
		param.put("systemdate", workDate);
		param.put("txnno", txnNo);
		param.put("debtno", debtno);
		param.put("tradedate", workDate);
		param.put("cur", cur);
		param.put("amt", null);
		param.put("org", tranorg);
		param.put("tradedetail", "03");
		batchDao.saveTradeDtls(param);
	}
	
	/**
	 * 保存循环信用证激活到交易明细表
	 **/
	public void saveLcRevoDtls(Context context){
		String txnNo=(String)context.get("txnNo");
		String tranorg=(String)context.get("butxn_tranOrgNo");
		String cur=(String)context.get("IMLCREVO_lcCursign");
		String lcAmt=(String)context.get("IMLCREVO_revolingAmt");
		String lcno = (String) context.get("IMLCISSUE_lcNo");
		String debtno="";
		SendCreditNoticeDao dao = new SendCreditNoticeDao();
		List<Map<String, Object>> lcList = dao.getLcList(lcno);
		if(lcList.size()>0){
			debtno=(String)lcList.get(0).get("feeno");
		}
		BatchDao batchDao = new BatchDao();
		Map<String, Object> param = new HashMap<String, Object>();
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
		String workDate=format.format(Commons.getWorkDate());
		param.put("systemdate", workDate);
		param.put("txnno", txnNo);
		param.put("debtno", debtno);
		param.put("tradedate", workDate);
		param.put("cur", cur);
		param.put("amt", lcAmt);
		param.put("org", tranorg);
		param.put("tradedetail", "02");
		batchDao.saveTradeDtls(param);
	}
	
	/**
	 * 保存信用证信用证撤销确认到交易明细表
	 **/
	public void saveLcCancelDtls(Context context){
		String txnNo=(String)context.get("txnNo");
		String lcno = (String) context.get("IMLCISSUE_lcNo");
		String cur=(String)context.get("IMLCISSUE_lcCursign");
		String leamts=(String)context.get("IMLCISSUE_lcAmt");
		String tranorg=(String)context.get("butxn_tranOrgNo");
		String debtno="";
		SendCreditNoticeDao dao = new SendCreditNoticeDao();
		List<Map<String, Object>> lcList = dao.getLcList(lcno);
		if(lcList.size()>0){
			debtno=(String)lcList.get(0).get("feeno");
		}
		BatchDao batchDao = new BatchDao();
		Map<String, Object> param = new HashMap<String, Object>();
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
		String workDate=format.format(Commons.getWorkDate());
		String tranDate=(String)context.get("PBCLOSE_cancelDate");
		tranDate=dateToDate(tranDate);
		param.put("systemdate", workDate);
		param.put("txnno", txnNo);
		param.put("debtno", debtno);
		param.put("tradedate", tranDate);
		param.put("cur", cur);
		param.put("amt", leamts);
		param.put("org", tranorg);
		param.put("tradedetail", "03");
		batchDao.saveTradeDtls(param);
	}
	
	/**
	 * 保存信用证注销到交易明细表
	 * @throws EbillsException 
	 **/
	public void saveLcCloselDtls(Context context) throws EbillsException{
		String txnNo=(String)context.get("txnNo");
		String lcno = (String) context.get("IMLCISSUE_lcNo");
		String feeno=(String)context.get("IMLCISSUE_feeNo");
		String cur=(String)context.get("IMLCISSUE_lcCursign");
		String leamts=(String)context.get("IMLCISSUE_lcAmt");
		String tranorg=(String)context.get("butxn_tranOrgNo");
		leamts = String.valueOf(BalanceInfo.getBalance(lcno, "LCACTBAL"));// 表外余额
		String debtno="";
		SendCreditNoticeDao dao = new SendCreditNoticeDao();
		List<Map<String, Object>> lcList = dao.getLcList(lcno);
		if(lcList.size()>0){
			debtno=(String)lcList.get(0).get("feeno");
		}
		BatchDao batchDao = new BatchDao();
		Map<String, Object> param = new HashMap<String, Object>();
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
		String workDate=format.format(Commons.getWorkDate());
		String tranDate=(String)context.get("PBCLOSE_cancelDate");
		tranDate=dateToDate(tranDate);
		param.put("systemdate", workDate);
		param.put("txnno", txnNo);
		param.put("debtno", debtno);
		param.put("tradedate", tranDate);
		param.put("cur", cur);
		param.put("amt", leamts);
		param.put("org", tranorg);
		param.put("tradedetail", "03");
		batchDao.saveTradeDtls(param);
	}
	
	/**
	 * 保存信用证注销恢复到交易明细表PBCLOSE_recoverDate
	 **/
	public void saveLcRecoverDtls(Context context){
		String txnNo=(String)context.get("txnNo");
		String feeno=(String)context.get("IMLCISSUE_feeNo");
		String lcno = (String) context.get("IMLCISSUE_lcNo");
		String cur=(String)context.get("IMLCISSUE_lcCursign");
		String leamts=(String)context.get("IMLCISSUE_lcAmt");
		String tranorg=(String)context.get("butxn_tranOrgNo");
		String debtno="";
		SendCreditNoticeDao dao = new SendCreditNoticeDao();
		List<Map<String, Object>> lcList = dao.getLcList(lcno);
		if(lcList.size()>0){
			debtno=(String)lcList.get(0).get("feeno");
		}
		BatchDao batchDao = new BatchDao();
		Map<String, Object> param = new HashMap<String, Object>();
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
		String workDate=format.format(Commons.getWorkDate());
		String tranDate=(String)context.get("PBCLOSE_recoverDate");
		tranDate=dateToDate(tranDate);
		param.put("systemdate", workDate);
		param.put("txnno", txnNo);
		param.put("debtno", debtno);
		param.put("tradedate", tranDate);
		param.put("cur", cur);
		param.put("amt", leamts);
		param.put("org", tranorg);
		param.put("tradedetail", "03");
		batchDao.saveTradeDtls(param);
	}
	
	/**
	 * 保存信用证承兑到交易明细表
	 **/
	public void saveLcAcptDtls(Context context){
		String txnNo=(String)context.get("txnNo");
		String feeno=(String)context.get("IMLCISSUE_feeNo");
		String lcno = (String) context.get("IMLCISSUE_lcNo");
		String cur=(String)context.get("PBACPT_acptCur");
		String leamts=(String)context.get("PBACPT_acptAmt");
		String tranorg=(String)context.get("butxn_tranOrgNo");
		String debtno="";
		SendCreditNoticeDao dao = new SendCreditNoticeDao();
		List<Map<String, Object>> lcList = dao.getLcList(lcno);
		if(lcList.size()>0){
			debtno=(String)lcList.get(0).get("feeno");
		}
		BatchDao batchDao = new BatchDao();
		Map<String, Object> param = new HashMap<String, Object>();
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
		String workDate=format.format(Commons.getWorkDate());
		String tranDate=(String)context.get("PBACPT_acptDate");
		tranDate=dateToDate(tranDate);
		param.put("systemdate", workDate);
		param.put("txnno", txnNo);
		param.put("debtno", debtno);
		param.put("tradedate", tranDate);
		param.put("cur", cur);
		param.put("amt", leamts);
		param.put("org", tranorg);
		param.put("tradedetail", "03");
		batchDao.saveTradeDtls(param);
	}
	
	/**
	 * 保存信用证承兑变更到交易明细表
	 **/
	public void saveLcAcptexDtls(Context context){
		String txnNo=(String)context.get("txnNo");
		String feeno=(String)context.get("IMLCISSUE_feeNo");
		String cur=(String)context.get("PBACPT_acptCur");
		String leamts=(String)context.get("PBACPT_newAcptAmt");
		String tranorg=(String)context.get("butxn_tranOrgNo");
		String lcno = (String) context.get("IMLCISSUE_lcNo");
		String debtno="";
		SendCreditNoticeDao dao = new SendCreditNoticeDao();
		List<Map<String, Object>> lcList = dao.getLcList(lcno);
		if(lcList.size()>0){
			debtno=(String)lcList.get(0).get("feeno");
		}
		BatchDao batchDao = new BatchDao();
		Map<String, Object> param = new HashMap<String, Object>();
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
		String workDate=format.format(Commons.getWorkDate());
		String tranDate=(String)context.get("PBACPT_acptDate");
		tranDate=dateToDate(tranDate);
		param.put("systemdate", workDate);
		param.put("txnno", txnNo);
		param.put("debtno", debtno);
		param.put("tradedate", tranDate);
		param.put("cur", cur);
		param.put("amt", leamts);
		param.put("org", tranorg);
		param.put("tradedetail", "03");
		batchDao.saveTradeDtls(param);
	}
	
	/**
	 * 保存提货担保到交易明细表
	 **/
	public void saveThdbDtls(Context context){
		String txnNo=(String)context.get("txnNo");
		String feeno=(String)context.get("IMTHDB_feeNo");
		String cur=(String)context.get("IMTHDB_assCursign");
		String leamts=(String)context.get("IMTHDB_assAmt");
		String tranorg=(String)context.get("butxn_tranOrgNo");
		BatchDao batchDao = new BatchDao();
		Map<String, Object> param = new HashMap<String, Object>();
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
		String workDate=format.format(Commons.getWorkDate());
		String tranDate=(String)context.get("IMTHDB_assDate");
		tranDate=dateToDate(tranDate);
		param.put("systemdate", workDate);
		param.put("txnno", txnNo);
		param.put("debtno", feeno);
		param.put("tradedate", tranDate);
		param.put("cur", cur);
		param.put("amt", leamts);
		param.put("org", tranorg);
		param.put("tradedetail", "02");
		batchDao.saveTradeDtls(param);
	}
	
	/**
	 * 保存提货担保注销销到交易明细表
	 **/
	public void saveThdbCalDtls(Context context){
		String txnNo=(String)context.get("txnNo");
		String feeno=(String)context.get("IMTHDB_feeNo");
		String cur=(String)context.get("IMTHDB_assCursign");
		String leamts=(String)context.get("IMTHDB_assAmt");
		String tranorg=(String)context.get("butxn_tranOrgNo");
		String billNo = (String) context.get("IMTHDB_ladBillNo");
		String payedAmt = null;
		double assamt = 0;// 提货担保金额
		double payAmt = 0;
		SendCreditNoticeDao dao = new SendCreditNoticeDao();
		List<Map<String, Object>> lcList = dao.getTHDBList(billNo);
		String dlrfno="";
		List<Map<String, Object>> thdbpedList = dao.getTHDBPEDList(billNo);
		if (lcList.size() > 0) {
			dlrfno = (String) lcList.get(0).get("feeno");
		}
		if (thdbpedList.size() > 0) {
			payedAmt = (String) thdbpedList.get(0).get("payedamt");
			if (!StringUtils.isEmpty(leamts)) {
				payAmt = Double.valueOf(payedAmt);
			}
			if (payAmt > assamt) {
				leamts = "0";
			} else {
				leamts = String.valueOf(assamt - payAmt);
			}
		}
		BatchDao batchDao = new BatchDao();
		Map<String, Object> param = new HashMap<String, Object>();
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
		String workDate=format.format(Commons.getWorkDate());
		String tranDate=(String)context.get("PBCLOSE_cancelDate");
		tranDate=dateToDate(tranDate);
		param.put("systemdate", workDate);
		param.put("txnno", txnNo);
		param.put("debtno", dlrfno);
		param.put("tradedate", tranDate);
		param.put("cur", cur);
		param.put("amt", leamts);
		param.put("org", tranorg);
		param.put("tradedetail", "03");
		batchDao.saveTradeDtls(param);
	}
	
	/**
	 * 保存提货担保赔付到交易明细表
	 **/
	public void saveThdbPedDtls(Context context){
		String txnNo=(String)context.get("txnNo");
		String billNo = (String) context.get("IMTHDB_ladBillNo");
		String cur=(String)context.get("IMTHDB_lcCursign");
		String leamts=(String)context.get("IMTHDBPED_payedAmt");
		String tranorg=(String)context.get("butxn_tranOrgNo");
		String payedAmt = (String) context.get("IMTHDBPED_payedAmt");
		double assamt = 0;// 提货担保金额
		double payAmt = 0;
		String dlrfno="";
		SendCreditNoticeDao dao = new SendCreditNoticeDao();
		List<Map<String, Object>> lcList = dao.getTHDBList(billNo);
		if (lcList.size() > 0) {
			dlrfno = (String) lcList.get(0).get("feeno");
		}
		BatchDao batchDao = new BatchDao();
		Map<String, Object> param = new HashMap<String, Object>();
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
		String workDate=format.format(Commons.getWorkDate());
		String tranDate=(String)context.get("IMTHDBPED_payedDate");
		tranDate=dateToDate(tranDate);
		if (lcList.size() > 0) {
			leamts = (String) lcList.get(0).get("assamt");
			if (!StringUtils.isEmpty(leamts)) {
				assamt = Double.valueOf(leamts);
			}
		}
		if (!StringUtils.isEmpty(payedAmt)) {
			payAmt = Double.valueOf(payedAmt);
		}
		if (payAmt <= assamt) {
			leamts = payedAmt;
		}
		param.put("systemdate", workDate);
		param.put("txnno", txnNo);
		param.put("debtno", null);
		param.put("tradedate", tranDate);
		param.put("cur", cur);
		param.put("amt", leamts);
		param.put("org", tranorg);
		param.put("tradedetail", "02");
		batchDao.saveTradeDtls(param);
	}
	
	/**
	 * 保存保函开立到交易明细表
	 **/
	public void saveLGIssueDtls(Context context){
		String txnNo=(String)context.get("txnNo");
		String feeno=(String)context.get("IMLGISSUE_creditNo");
		String cur=(String)context.get("IMLGISSUE_lgCur");
		String leamts=(String)context.get("IMLGISSUE_lgAmt");
		String tranorg=(String)context.get("butxn_tranOrgNo");
		BatchDao batchDao = new BatchDao();
		Map<String, Object> param = new HashMap<String, Object>();
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
		String workDate=format.format(Commons.getWorkDate());
		String tranDate=(String)context.get("IMLGISSUE_lgIssueDate");
		tranDate=dateToDate(tranDate);
		param.put("systemdate", workDate);
		param.put("txnno", txnNo);
		param.put("debtno", feeno);
		param.put("tradedate", tranDate);
		param.put("cur", cur);
		param.put("amt", leamts);
		param.put("org", tranorg);
		param.put("tradedetail", "02");
		batchDao.saveTradeDtls(param);
	}
	
	/**
	 * 保存保函撤销到交易明细表
	 **/
	public void saveLGCancelDtls(Context context){
		String txnNo=(String)context.get("txnNo");
		String feeno=(String)context.get("IMLGISSUE_creditNo");
		String lgNo = (String) context.get("IMLGISSUE_lgNo");
		String cur=(String)context.get("IMLGISSUE_lgCur");
		String leamts=(String)context.get("IMLGISSUE_LGBAL");
		String tranorg=(String)context.get("butxn_tranOrgNo");
		String dlrfno="";
		SendCreditNoticeDao dao = new SendCreditNoticeDao();
		List<Map<String, Object>> lcList = dao.getLgList(lgNo);
		if (lcList.size() > 0) {
			dlrfno = (String) lcList.get(0).get("creditno");
		}
		BatchDao batchDao = new BatchDao();
		Map<String, Object> param = new HashMap<String, Object>();
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
		String workDate=format.format(Commons.getWorkDate());
		String tranDate=(String)context.get("PBCLOSE_dealDate");
		tranDate=dateToDate(tranDate);
		param.put("systemdate", workDate);
		param.put("txnno", txnNo);
		param.put("debtno", dlrfno);
		param.put("tradedate", tranDate);
		param.put("cur", cur);
		param.put("amt", leamts);
		param.put("org", tranorg);
		param.put("tradedetail", "03");
		batchDao.saveTradeDtls(param);
	}
	private String dateToDate(String date){
		String tranDates[]=date.split("-");
		return tranDates[0]+tranDates[1]+tranDates[2];
	}
}
