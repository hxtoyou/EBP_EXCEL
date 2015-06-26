package com.ebills.product.dg.credit.send;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.bussprocess.context.Context;

import com.ebills.util.EbillsException;
import com.ebills.product.dg.commons.Commons;
import com.ebills.product.dg.credit.dao.SendCreditNoticeDao;
import com.ebills.utils.CommonUtil;

public class ReturnAcctBiz {

	/**
	 * 保存信用证开立数据
	 **/
	public void saveLcIssueRetrun(String txnno, String newtxnno)
			throws EbillsException {
		SendCreditNoticeDao dao = new SendCreditNoticeDao();
		List<Map<String, Object>> list = dao.getLcListByTxnno(txnno);
		String lcno = "";// 信用证编号
		// 查询出拮据号
		String dlrfno = "";
		String jjhmno = "";
		String leamts = null;
		Date eydate = null;// 交易日期
		String prodcd = TradeNoConstant.Prodcd.PPODCD_IMLCISSUE_TRADENO;
		String trtype = TradeNoConstant.TradeTpe.TRADE_TYPE_14;
		String status = TradeNoConstant.PZStatus.PZ_STATUS_3;
		String mgrate="";
		Date vldate = null;
		Date madate = null;
		String inrate = "";
		String interfaceid = "1";
		if (list != null && list.size() > 0) {
			lcno = (String) list.get(0).get("lcno");
			dlrfno = (String) list.get(0).get("feeno");
			jjhmno = (String) list.get(0).get("feeno");// 借据号
			leamts = (String) list.get(0).get("lcamt");// 借据号
			eydate = CommonUtil.formatDate(
					(String) list.get(0).get("trandate"), "yyyyMMdd");
			;
			if (list.get(0).get("mgrate") != null) {
				mgrate = (String) list.get(0).get("mgrate");
			}
			vldate = CommonUtil.formatDate(
					(String) list.get(0).get("lcissuedate"), "yyyyMMdd");
			madate = CommonUtil.formatDate(
					(String) list.get(0).get("expirydate"), "yyyyMMdd");
			dao.saveTpCredNotic(newtxnno, dlrfno, jjhmno, leamts, eydate,
					prodcd, trtype, status, mgrate, vldate, madate, inrate,
					interfaceid, lcno);

		}
	}
	/**
	 *保存保函开立数据 
	 **/
	public void saveLgIssueRetrun(String txnno,String newtxnno) throws EbillsException {
		SendCreditNoticeDao dao = new SendCreditNoticeDao();
		List<Map<String, Object>> list = dao.getLgListByTxnno(txnno);
		String lcno = null;
		// 查询出拮据号
		String dlrfno = "";
		dlrfno = "";// 借据号
		String jjhmno = "";
		jjhmno = "";// 借据号
		String leamts = null;
		Date eydate = null;
		String prodcd = TradeNoConstant.Prodcd.PPODCD_IMLGISSUE;
		String trtype = TradeNoConstant.TradeTpe.TRADE_TYPE_14;
		String status = TradeNoConstant.PZStatus.PZ_STATUS_3;
		String mgrate="";
		Date vldate = null;
		Date madate = null;
		String interfaceid = "1";
		String inrate = "";
		if (list != null && list.size() > 0) {
			dlrfno = (String) list.get(0).get("creditno");
			jjhmno = (String) list.get(0).get("creditno");// 借据号
			leamts = (String) list.get(0).get("lgamt");// 借据号
			eydate = CommonUtil.formatDate(
					(String) list.get(0).get("trandate"), "yyyyMMdd");
			if (list.get(0).get("depositpct") != null) {
				mgrate = (String) list.get(0).get("depositpct");
			}
			vldate = CommonUtil.formatDate(
					(String) list.get(0).get("effectdate"), "yyyyMMdd");
			madate = CommonUtil.formatDate(
					(String) list.get(0).get("failruedate"), "yyyyMMdd");
			dao.saveTpCredNotic(newtxnno, dlrfno, jjhmno, leamts, eydate, prodcd,
					trtype, status, mgrate, vldate, madate, inrate,
					interfaceid, lcno);

		}

	}
	/**
	 *保存提货担保
	 **/
	public void saveThdbRetrun(String txnno,String newtxnno) throws EbillsException {
		SendCreditNoticeDao dao = new SendCreditNoticeDao();
		List<Map<String, Object>> list = dao.getTHDBListByTxnno(txnno);
		String lcno = "";// 信用证编号
		// 查询出拮据号
		String dlrfno = "";
		dlrfno = "";// 借据号
		String jjhmno = "";
		jjhmno = "";// 借据号
		String leamts = null;
		Date eydate = null;
		String prodcd = TradeNoConstant.Prodcd.PPODCD_IMTHDB;
		String trtype = TradeNoConstant.TradeTpe.TRADE_TYPE_14;
		String status = TradeNoConstant.PZStatus.PZ_STATUS_3;
		String mgrate="";
		Date vldate = null;
		Date madate = null;
		String inrate = "";
		String interfaceid = "1";
		if (list != null && list.size() > 0) {
			dlrfno = (String) list.get(0).get("feeno");
			jjhmno = (String) list.get(0).get("feeno");// 借据号
			leamts = (String) list.get(0).get("assamt");// 借据号
			eydate =  CommonUtil.formatDate((String) list.get(0).get("trandate"),"yyyyMMdd");
			if (list.get(0).get("mgrate") != null) {
				mgrate = (String) list.get(0).get("mgrate");
			}
			vldate = CommonUtil.formatDate((String) list.get(0).get("assdate"), "yyyyMMdd");
			madate =  CommonUtil.formatDate((String) list.get(0).get("duedate"), "yyyyMMdd");
			dao.saveTpCredNotic(newtxnno, dlrfno, jjhmno, leamts, eydate, prodcd,
					trtype, status, mgrate, vldate, madate, inrate,
					interfaceid, lcno);

		}

		}
	/**
	 *保存信用证开立修改
	 **/
	public void saveLcModifyRetrun(String txnno,String newtxnno) throws EbillsException{
		SendCreditNoticeDao dao=new SendCreditNoticeDao();
		List<Map<String,Object>> list=dao.getLcListByTxnno(txnno);
		String lcno="";//信用证编号
		//查询出拮据号
		String dlrfno="";
		dlrfno="";//借据号
		String jjhmno="";
		jjhmno="";//借据号
		String leamts=null;
		Date eydate=null;
		String prodcd=TradeNoConstant.Prodcd.PPODCD_IMLCISSUE_TRADENO;
		String trtype=TradeNoConstant.TradeTpe.TRADE_TYPE_14;
		String status=TradeNoConstant.PZStatus.PZ_STATUS_3;
		String mgrate="";
		Date vldate=null;
		Date madate=null;
		String inrate="";
		String interfaceid="1";
		if(list!=null&&list.size()>0){
			lcno=(String) list.get(0).get("lcno");
			dlrfno=(String) list.get(0).get("feeno");//借据号
			jjhmno=(String) list.get(0).get("feeno");//借据号
			leamts=(String) list.get(0).get("lcamt");
			eydate=CommonUtil.formatDate((String) list.get(0).get("trandate"),"yyyyMMdd");
			if (list.get(0).get("mgrate") != null) {
				mgrate = (String) list.get(0).get("mgrate");
			}
			vldate=CommonUtil.formatDate((String) list.get(0).get("lcissuedate"),"yyyyMMdd");
			madate=CommonUtil.formatDate((String)list.get(0).get("expirydate"),"yyyyMMdd");
			dao.saveTpCredNotic(newtxnno, dlrfno, jjhmno, leamts, eydate, prodcd, trtype, status, mgrate, vldate, madate, inrate, interfaceid, lcno);
		}

	}
	/**
	 * 保存信用证注销
	 */
	public void saveLcCloseRetrun(String txnno,String newtxnno) throws EbillsException {
		SendCreditNoticeDao dao = new SendCreditNoticeDao();
		List<Map<String, Object>> list = dao.getLcCloseByTxnno(txnno);
		String lcno = "";
		// 查询出拮据号
		String dlrfno = "";
		String jjhmno = "";
		String leamts = "";
		Date eydate = null;
		String prodcd = TradeNoConstant.LCCLOASE_TRADENO;
		String trtype = TradeNoConstant.TradeTpe.TRADE_TYPE_15;
		String status = TradeNoConstant.PZStatus.PZ_STATUS_1;
		String mgrate="";
		Date vldate = null;
		Date madate = null;
		String interfaceid = "1";
		String inrate = "";
		if (list != null && list.size() > 0) {
			lcno = (String) list.get(0).get("lcno");
			dlrfno = (String) list.get(0).get("feeno");// 借据号
			jjhmno = (String) list.get(0).get("feeno");// 借据号
			leamts = (String) list.get(0).get("lcamt");
			eydate = CommonUtil.formatDate(
					(String) list.get(0).get("trandate"), "yyyyMMdd");
			//
			if(list.get(0).get("mgrate")!=null){
				mgrate = (String)list.get(0).get("mgrate");
			}
			vldate = CommonUtil.formatDate(
					(String) list.get(0).get("lcissuedate"), "yyyyMMdd");
			madate = CommonUtil.formatDate(
					(String) list.get(0).get("expirydate"), "yyyyMMdd");
			dao.saveTpCredNotic(newtxnno, dlrfno, jjhmno, leamts, eydate, prodcd,
					trtype, status, mgrate, vldate, madate, inrate,
					interfaceid, lcno);
		}

	}
	/**
	 * 保存信用证撤销
	 */
	public void saveLcCancelRetrun(String txnno,String newtxnno) throws EbillsException{
		SendCreditNoticeDao dao=new SendCreditNoticeDao();
		List<Map<String,Object>> list=dao.getLcCancelByTxnno(txnno);
		String lcno="";

		//查询出拮据号
		String dlrfno="";
		String jjhmno="";
		String leamts="";
		Date eydate=null;
		String prodcd=TradeNoConstant.LCCLOASE_TRADENO;
		String trtype=TradeNoConstant.TradeTpe.TRADE_TYPE_15;
		String status=TradeNoConstant.PZStatus.PZ_STATUS_1;
		String mgrate="";
		Date vldate=null;
		Date madate=null;
		String inrate="";
		String interfaceid="1";
		if(list!=null&&list.size()>0){
			lcno=(String) list.get(0).get("lcno");
			dlrfno=(String) list.get(0).get("feeno");//借据号
			jjhmno=(String) list.get(0).get("feeno");//借据号
			leamts=(String) list.get(0).get("lcamt");
			eydate=CommonUtil.formatDate((String) list.get(0).get("trandate"), "yyyyMMdd");
			if(list.get(0).get("mgrate")!=null){
				mgrate = (String)list.get(0).get("mgrate");
			}
			vldate=CommonUtil.formatDate((String)list.get(0).get("lcissuedate"), "yyyyMMdd");
			madate=CommonUtil.formatDate((String) list.get(0).get("expirydate"),"yyyyMMdd");
			dao.saveTpCredNotic(newtxnno, dlrfno, jjhmno, leamts, eydate, prodcd, trtype, status, mgrate, vldate, madate, inrate, interfaceid, lcno);
		}
	}
	/**
	 * 保存信用退单
	 */
	public void saveLcReturnRetrun(String txnno,String newtxnno) throws EbillsException{
		SendCreditNoticeDao dao = new SendCreditNoticeDao();
		List<Map<String, Object>> list = dao.getLcReturnByTxnno(txnno);
		String lcno = "";
		// 查询出拮据号
		String dlrfno = "";
		String jjhmno = "";
		String leamts = "";
		Date eydate = null;
		String prodcd = TradeNoConstant.LCCLOASE_TRADENO;
		String trtype = TradeNoConstant.TradeTpe.TRADE_TYPE_15;
		String status = TradeNoConstant.PZStatus.PZ_STATUS_1;
		String mgrate="";
		Date vldate = null;
		Date madate = null;
		String interfaceid = "1";
		String inrate = "";
		if (list != null && list.size() > 0) {
			lcno = (String) list.get(0).get("lcno");
			dlrfno = (String) list.get(0).get("feeno");// 借据号
			jjhmno = (String) list.get(0).get("feeno");// 借据号
			leamts = (String) list.get(0).get("lcamt");
			eydate = CommonUtil.formatDate(
					(String) list.get(0).get("trandate"), "yyyyMMdd");
			//
			if(list.get(0).get("mgrate")!=null){
				mgrate = (String)list.get(0).get("mgrate");
			}
			vldate = CommonUtil.formatDate(
					(String) list.get(0).get("lcissuedate"), "yyyyMMdd");
			madate = CommonUtil.formatDate(
					(String) list.get(0).get("expirydate"), "yyyyMMdd");
			dao.saveTpCredNotic(newtxnno, dlrfno, jjhmno, leamts, eydate, prodcd,
					trtype, status, mgrate, vldate, madate, inrate,
					interfaceid, lcno);
		}

	}
	/**
	 * 保存信用证承兑
	 */
	public void saveLcAcptRetrun(String txnno,String newtxnno) throws EbillsException{
		SendCreditNoticeDao dao = new SendCreditNoticeDao();
		List<Map<String, Object>> list = dao.getLcAcptReturnByTxnno(txnno);
		String lcno = "";
		// 查询出拮据号
		String dlrfno = "";
		String jjhmno = "";
		String leamts = "";
		Date eydate = null;
		String prodcd = TradeNoConstant.LCCLOASE_TRADENO;
		String trtype = TradeNoConstant.TradeTpe.TRADE_TYPE_15;
		String status = TradeNoConstant.PZStatus.PZ_STATUS_1;
		String mgrate="";
		Date vldate = null;
		Date madate = null;
		String interfaceid = "1";
		String inrate = "";
		if (list != null && list.size() > 0) {
			lcno = (String) list.get(0).get("lcno");
			dlrfno = (String) list.get(0).get("feeno");// 借据号
			jjhmno = (String) list.get(0).get("feeno");// 借据号
			leamts = (String) list.get(0).get("lcamt");
			eydate = CommonUtil.formatDate(
					(String) list.get(0).get("trandate"), "yyyyMMdd");
			//
			if(list.get(0).get("mgrate")!=null){
				mgrate = (String)list.get(0).get("mgrate");
			}
			vldate = CommonUtil.formatDate(
					(String) list.get(0).get("lcissuedate"), "yyyyMMdd");
			madate = CommonUtil.formatDate(
					(String) list.get(0).get("expirydate"), "yyyyMMdd");
			dao.saveTpCredNotic(newtxnno, dlrfno, jjhmno, leamts, eydate, prodcd,
					trtype, status, mgrate, vldate, madate, inrate,
					interfaceid, lcno);
		}
	}

	/**
	 * 保存信用证承兑
	 */
	public void saveLcAcpttexRetrun(String txnno, String newtxnno)
			throws EbillsException {
		SendCreditNoticeDao dao = new SendCreditNoticeDao();
		List<Map<String, Object>> list = dao.getLcAcptReturnByTxnno(txnno);
		String lcno = "";
		// 查询出拮据号
		String dlrfno = "";
		String jjhmno = "";
		String leamts = "";
		Date eydate = null;
		String prodcd = TradeNoConstant.LCCLOASE_TRADENO;
		String trtype = TradeNoConstant.TradeTpe.TRADE_TYPE_15;
		String status = TradeNoConstant.PZStatus.PZ_STATUS_1;
		String mgrate="";
		Date vldate = null;
		Date madate = null;
		String interfaceid = "1";
		String inrate = "";
		if (list != null && list.size() > 0) {
			lcno = (String) list.get(0).get("lcno");
			dlrfno = (String) list.get(0).get("feeno");// 借据号
			jjhmno = (String) list.get(0).get("feeno");// 借据号
			leamts = (String) list.get(0).get("lcamt");
			eydate = CommonUtil.formatDate(
					(String) list.get(0).get("trandate"), "yyyyMMdd");
			//
			if (list.get(0).get("mgrate") != null) {
				mgrate = (String) list.get(0).get("mgrate");
			}
			vldate = CommonUtil.formatDate(
					(String) list.get(0).get("lcissuedate"), "yyyyMMdd");
			madate = CommonUtil.formatDate(
					(String) list.get(0).get("expirydate"), "yyyyMMdd");
			dao.saveTpCredNotic(newtxnno, dlrfno, jjhmno, leamts, eydate,
					prodcd, trtype, status, mgrate, vldate, madate, inrate,
					interfaceid, lcno);
		}
	}
	/**
	 * 保存信用付汇
	 */
	public void saveLcPayRetrun(String txnno, String newtxnno)throws EbillsException{
		SendCreditNoticeDao dao = new SendCreditNoticeDao();
		List<Map<String, Object>> list = dao.getLcPayReturnByTxnno(txnno);
		String lcno = "";
		// 查询出拮据号
		String dlrfno = "";
		String jjhmno = "";
		String leamts = "";
		Date eydate = null;
		String prodcd = TradeNoConstant.LCCLOASE_TRADENO;
		String trtype = TradeNoConstant.TradeTpe.TRADE_TYPE_15;
		String status = TradeNoConstant.PZStatus.PZ_STATUS_1;
		String mgrate="";
		Date vldate = null;
		Date madate = null;
		String interfaceid = "1";
		String inrate = "";
		if (list != null && list.size() > 0) {
			lcno = (String) list.get(0).get("lcno");
			dlrfno = (String) list.get(0).get("feeno");// 借据号
			jjhmno = (String) list.get(0).get("feeno");// 借据号
			leamts = (String) list.get(0).get("lcamt");
			eydate = CommonUtil.formatDate(
					(String) list.get(0).get("trandate"), "yyyyMMdd");
			//
			if (list.get(0).get("mgrate") != null) {
				mgrate =(String) list.get(0).get("mgrate");
			}
			vldate = CommonUtil.formatDate(
					(String) list.get(0).get("lcissuedate"), "yyyyMMdd");
			madate = CommonUtil.formatDate(
					(String) list.get(0).get("expirydate"), "yyyyMMdd");
			dao.saveTpCredNotic(newtxnno, dlrfno, jjhmno, leamts, eydate,
					prodcd, trtype, status, mgrate, vldate, madate, inrate,
					interfaceid, lcno);
		}
	}
	/**
	 * 保存提货担保赔付
	 */
	public void saveThdbPedlRetrun(String txnno, String newtxnno)
			throws EbillsException {
		SendCreditNoticeDao dao = new SendCreditNoticeDao();
		List<Map<String, Object>> list = dao.getThdbCalRetrunByTxnno(txnno);
		String lcno = "";
		// 查询出拮据号
		String dlrfno = "";
		String jjhmno = "";
		String leamts = "";
		Date eydate = null;
		String prodcd = TradeNoConstant.LCCLOASE_TRADENO;
		String trtype = TradeNoConstant.TradeTpe.TRADE_TYPE_15;
		String status = TradeNoConstant.PZStatus.PZ_STATUS_1;
		String mgrate="";
		Date vldate = null;
		Date madate = null;
		String interfaceid = "1";
		String inrate = "";
		if (list != null && list.size() > 0) {
			dlrfno = (String) list.get(0).get("feeno");// 借据号
			jjhmno = (String) list.get(0).get("feeno");// 借据号
			leamts = (String) list.get(0).get("assamt");
			eydate = CommonUtil.formatDate(
					(String) list.get(0).get("trandate"), "yyyyMMdd");
			//
			if (list.get(0).get("mgrate") != null) {
				mgrate = (String) list.get(0).get("mgrate");
			}
			vldate = CommonUtil.formatDate((String) list.get(0).get("assdate"),
					"yyyyMMdd");
			madate = CommonUtil.formatDate((String) list.get(0).get("duedate"),
					"yyyyMMdd");
			dao.saveTpCredNotic(newtxnno, dlrfno, jjhmno, leamts, eydate,
					prodcd, trtype, status, mgrate, vldate, madate, inrate,
					interfaceid, lcno);
		}
	}
	/**
	 * 保存提货担保注销
	 */
	public void saveThdBpedlRetrun(String txnno, String newtxnno) throws EbillsException {
		SendCreditNoticeDao dao = new SendCreditNoticeDao();
		List<Map<String, Object>> list = dao.getThdbCalRetrunByTxnno(txnno);
		String lcno = "";
		// 查询出拮据号
		String dlrfno = "";
		String jjhmno = "";
		String leamts = "";
		Date eydate = null;
		String prodcd = TradeNoConstant.LCCLOASE_TRADENO;
		String trtype = TradeNoConstant.TradeTpe.TRADE_TYPE_15;
		String status = TradeNoConstant.PZStatus.PZ_STATUS_1;
		String mgrate="";
		Date vldate = null;
		Date madate = null;
		String interfaceid = "1";
		String inrate = "";
		if (list != null && list.size() > 0) {
			dlrfno = (String) list.get(0).get("feeno");// 借据号
			jjhmno = (String) list.get(0).get("feeno");// 借据号
			leamts = (String) list.get(0).get("assamt");
			eydate = CommonUtil.formatDate(
					(String) list.get(0).get("trandate"), "yyyyMMdd");
			//
			if (list.get(0).get("mgrate") != null) {
				mgrate = (String) list.get(0).get("mgrate");
			}
			vldate = CommonUtil.formatDate((String) list.get(0).get("assdate"),
					"yyyyMMdd");
			madate = CommonUtil.formatDate((String) list.get(0).get("duedate"),
					"yyyyMMdd");
			dao.saveTpCredNotic(newtxnno, dlrfno, jjhmno, leamts, eydate,
					prodcd, trtype, status, mgrate, vldate, madate, inrate,
					interfaceid, lcno);
		}
	}

	/**
	 * 保存保函注销
	 */
	public void saveLgRfguRetrun(String txnno, String newtxnno)
			throws EbillsException {
		SendCreditNoticeDao dao = new SendCreditNoticeDao();
		List<Map<String, Object>> list = dao.getLgRfguRetrunByTxnno(txnno);
		String lcno = "";
		// 查询出拮据号
		String dlrfno = "";
		String jjhmno = "";
		String leamts = "";
		Date eydate = null;
		String prodcd = TradeNoConstant.LCCLOASE_TRADENO;
		String trtype = TradeNoConstant.TradeTpe.TRADE_TYPE_15;
		String status = TradeNoConstant.PZStatus.PZ_STATUS_1;
		String mgrate="";
		Date vldate = null;
		Date madate = null;
		String interfaceid = "1";
		String inrate = "";
		if (list != null && list.size() > 0) {
			dlrfno = (String) list.get(0).get("creditno");// 借据号
			jjhmno = (String) list.get(0).get("creditno");// 借据号
			leamts = (String) list.get(0).get("lgamt");
			eydate = CommonUtil.formatDate(
					(String) list.get(0).get("trandate"), "yyyyMMdd");
			//
			if (list.get(0).get("depositpct") != null) {
				mgrate = (String) list.get(0).get("depositpct");
			}
			vldate = CommonUtil.formatDate((String) list.get(0).get("effectdate"),
					"yyyyMMdd");
			madate = CommonUtil.formatDate((String) list.get(0).get("failruedate"),
					"yyyyMMdd");
			dao.saveTpCredNotic(newtxnno, dlrfno, jjhmno, leamts, eydate,
					prodcd, trtype, status, mgrate, vldate, madate, inrate,
					interfaceid, lcno);
		}
	}
	/**
	 * 保存保函撤销
	 */
	public void saveLgCancelRetrun(String txnno, String newtxnno)
			throws EbillsException {
		SendCreditNoticeDao dao = new SendCreditNoticeDao();
		List<Map<String, Object>> list = dao.getLgCancelRetrunByTxnno(txnno);
		String lcno = "";
		// 查询出拮据号
		String dlrfno = "";
		String jjhmno = "";
		String leamts = "";
		Date eydate = null;
		String prodcd = TradeNoConstant.LCCLOASE_TRADENO;
		String trtype = TradeNoConstant.TradeTpe.TRADE_TYPE_15;
		String status = TradeNoConstant.PZStatus.PZ_STATUS_1;
		String mgrate="";
		Date vldate = null;
		Date madate = null;
		String interfaceid = "1";
		String inrate = "";
		if (list != null && list.size() > 0) {
			dlrfno = (String) list.get(0).get("creditno");// 借据号
			jjhmno = (String) list.get(0).get("creditno");// 借据号
			leamts = (String) list.get(0).get("lgamt");
			eydate = CommonUtil.formatDate(
					(String) list.get(0).get("trandate"), "yyyyMMdd");
			//
			if (list.get(0).get("depositpct") != null) {
				mgrate = (String) list.get(0).get("depositpct");
			}
			vldate = CommonUtil.formatDate((String) list.get(0).get("effectdate"),
					"yyyyMMdd");
			madate = CommonUtil.formatDate((String) list.get(0).get("failruedate"),
					"yyyyMMdd");
			dao.saveTpCredNotic(newtxnno, dlrfno, jjhmno, leamts, eydate,
					prodcd, trtype, status, mgrate, vldate, madate, inrate,
					interfaceid, lcno);
		}
	}
	/**
	 * 保存保函赔付
	 */
	public void saveLgPayRetrun(String txnno, String newtxnno)
			throws EbillsException {
		SendCreditNoticeDao dao = new SendCreditNoticeDao();
		List<Map<String, Object>> list = dao.getLgPayRetrunByTxnno(txnno);
		String lcno = "";
		// 查询出拮据号
		String dlrfno = "";
		String jjhmno = "";
		String leamts = "";
		Date eydate = null;
		String prodcd = TradeNoConstant.LCCLOASE_TRADENO;
		String trtype = TradeNoConstant.TradeTpe.TRADE_TYPE_15;
		String status = TradeNoConstant.PZStatus.PZ_STATUS_1;
		String mgrate="";
		Date vldate = null;
		Date madate = null;
		String interfaceid = "1";
		String inrate = "";
		if (list != null && list.size() > 0) {
			dlrfno = (String) list.get(0).get("creditno");// 借据号
			jjhmno = (String) list.get(0).get("creditno");// 借据号
			leamts = (String) list.get(0).get("lgamt");
			eydate = CommonUtil.formatDate(
					(String) list.get(0).get("trandate"), "yyyyMMdd");
			//
			if (list.get(0).get("depositpct") != null) {
				mgrate = (String) list.get(0).get("depositpct");
			}
			vldate = CommonUtil.formatDate((String) list.get(0).get("effectdate"),
					"yyyyMMdd");
			madate = CommonUtil.formatDate((String) list.get(0).get("failruedate"),
					"yyyyMMdd");
			dao.saveTpCredNotic(newtxnno, dlrfno, jjhmno, leamts, eydate,
					prodcd, trtype, status, mgrate, vldate, madate, inrate,
					interfaceid, lcno);
		}
	}
	
	/**
	 * 保存保函闭卷索赔
	 */
	public void saveLgCloseRetrun(String txnno, String newtxnno) throws EbillsException {
		SendCreditNoticeDao dao = new SendCreditNoticeDao();
		List<Map<String, Object>> list = dao.getLgPayRetrunByTxnno(txnno);
		String lcno = "";
		// 查询出拮据号
		String dlrfno = "";
		String jjhmno = "";
		String leamts = "";
		Date eydate = null;
		String prodcd = TradeNoConstant.LCCLOASE_TRADENO;
		String trtype = TradeNoConstant.TradeTpe.TRADE_TYPE_15;
		String status = TradeNoConstant.PZStatus.PZ_STATUS_1;
		String mgrate="";
		Date vldate = null;
		Date madate = null;
		String interfaceid = "1";
		String inrate = "";
		if (list != null && list.size() > 0) {
			dlrfno = (String) list.get(0).get("creditno");// 借据号
			jjhmno = (String) list.get(0).get("creditno");// 借据号
			leamts = (String) list.get(0).get("lgamt");
			eydate = CommonUtil.formatDate(
					(String) list.get(0).get("trandate"), "yyyyMMdd");
			//
			if (list.get(0).get("depositpct") != null) {
				mgrate = (String) list.get(0).get("depositpct");
			}
			vldate = CommonUtil.formatDate((String) list.get(0).get("effectdate"),
					"yyyyMMdd");
			madate = CommonUtil.formatDate((String) list.get(0).get("failruedate"),
					"yyyyMMdd");
			dao.saveTpCredNotic(newtxnno, dlrfno, jjhmno, leamts, eydate,
					prodcd, trtype, status, mgrate, vldate, madate, inrate,
					interfaceid, lcno);
		}
	}
}
