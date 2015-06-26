package com.ebills.product.dg.credit.send;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.bussprocess.context.Context;
import org.apache.commons.lang.StringUtils;

import com.ebills.util.EbillsException;
import com.ebills.product.component.BalanceInfo;
import com.ebills.product.dg.commons.Commons;
import com.ebills.product.dg.credit.dao.SendCreditNoticeDao;
import com.ebills.intf.spi.InterfaceManager;
import com.ebills.utils.CommonUtil;
import com.ebills.utils.EbpConstants;

public class SendCreditNoticeBiz {
	public final static String CROU_01 = "CROU01";
	public final static String CROU_INTERFACE_ID_01 = "1";
	public final static String CROU_02 = "CROU02";
	public final static String CROU_INTERFACE_ID_02 = "2";
	public final static String CROU_03 = "CROU03";
	public final static String CROU_INTERFACE_ID_03 = "3";
	public final static String INTF_SUCC = "00000000";
	private String className = this.getClass().getName();

	/**
	 * 调用还款通知
	 */
	public void callCrou01(Context context, List<Map<String, Object>> list)
			throws Exception {
		SendCreditNoticeDao noticeDao = new SendCreditNoticeDao();
		if (list != null) {
			for (Map<String, Object> map : list) {
				String jjhmno = (String) map.get("jjhmno");
				if (map.get("jjhmno") != null) {
					context.put("jjhmno", (String) map.get("jjhmno"));
				}
				if (map.get("leamts") != null) {
					context.put("leamts", (String) map.get("leamts"));
				}
				if (map.get("eydate") != null) {
					String date = (String) map.get("eydate");
					context.put("eydate", date);
				}
				if (map.get("prodcd") != null) {
					context.put("prodcd", (String) map.get("prodcd"));
				}
				if (map.get("trtype") != null) {
					context.put("trtype", (String) map.get("trtype"));
				}
				if (map.get("status") != null) {
					context.put("status", (String) map.get("status"));
				}
				if (map.get("mgrate") != null) {
					context.put("mgrate", (String) map.get("mgrate"));
				}
				if (map.get("vldate") != null) {
					String date = (String) map.get("vldate");
					context.put("vldate", date);
				}
				if (map.get("madate") != null) {
					String date = (String) map.get("madate");
					context.put("madate", date);
				}
				if (map.get("leamts") != null) {
					context.put("inrate", (String) map.get("inrate"));
				}
				String txnno = (String) map.get("txnno");
				context.put("dlrfno", txnno);
				if (jjhmno != null && !"".equals(jjhmno)) {
					context = InterfaceManager.execute(CROU_01, context);
					Map data = (Map) context.get(InterfaceManager.RESULT_KEY);
					if (INTF_SUCC.equals((String) data.get("errorCode"))) {
						String dlrfno = (String) context.get("intfno");
						noticeDao.moveData(txnno, dlrfno);
					}
				}

			}
		}
	}

	/**
	 * crou2出账成功通知
	 */
	public void callCrou02(Context context, List<Map<String, Object>> list)
			throws Exception {
		SendCreditNoticeDao noticeDao = new SendCreditNoticeDao();
		if (list != null) {
			for (Map<String, Object> map : list) {
				String jjhmno = (String) map.get("jjhmno");
				String prodcd = (String) map.get("prodcd");
				String lcno = (String) map.get("lcno");
				String txnno = (String) map.get("txnno");
				context.put("jjhmno", jjhmno);
				context.put("prodcd", prodcd);
				context.put("lcopno", lcno);
				context.put("dlrfno", txnno);
				if (jjhmno != null && !"".equals(jjhmno)) {
					context = InterfaceManager.execute(CROU_02, context);

					Map data = (Map) context.get(InterfaceManager.RESULT_KEY);
					if (INTF_SUCC.equals((String) data.get("errorCode"))) {
						String dlrfno = (String) context.get("intfno");
						noticeDao.moveData(txnno, dlrfno);
					}
				}

			}

		}

	}

	/**
	 * 调用新增变更通知接口
	 */
	public void callCrou03(Context context, List<Map<String, Object>> list) {
		SendCreditNoticeDao noticeDao = new SendCreditNoticeDao();
		if (list != null) {
			for (Map<String, Object> map : list) {
				String jjhmno = (String) map.get("jjhmno");
				String txnno = (String) map.get("txnno");
				context.put("jjhmno", jjhmno);
				context.put("dlrfno", txnno);
				if (jjhmno != null && !"".equals(jjhmno)) {
					try {
						context = InterfaceManager.execute(CROU_03, context);
					} catch (EbillsException e) {
						e.printStackTrace();
						continue;
						// TODO Auto-generated catch block
					}
					Map data = (Map) context.get(InterfaceManager.RESULT_KEY);
					if (INTF_SUCC.equals((String) data.get("errorCode"))) {
						String dlrfno = (String) context.get("intfno");
						try {
							noticeDao.moveData(txnno, dlrfno);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							continue;
						}
					}
				}

			}
		}
	}

	/**
	 * 发送通知给信贷
	 */
	public void sendNotice(Context context) {
		String tradeNo = (String) context
				.get(EbpConstants.TRANSACTION_TRADENO);
		if (TradeNoConstant.IMLCISSUE_TRADENO.equals(tradeNo)) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			String jjhmno = (String) context.get("IMLCISSUE_feeNo");// 借据号
			String lcno = (String) context.get("butxn_curtBizNo");// 信用证编号
			String txnno = (String) context.get("butxn_txnNo");// 流水号
			paramMap.put("jjhmno", jjhmno);
			paramMap.put("prodcd",
					TradeNoConstant.Prodcd.PPODCD_IMLCISSUE_TRADENO);
			paramMap.put("lcno", lcno);
			paramMap.put("txnno", txnno);

		} else if (TradeNoConstant.IMTHDB_TRADENO.equals(tradeNo)) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			String jjhmno = (String) context.get("IMLCISSUE_feeNo");// 借据号
			String lcno = (String) context.get("butxn_curtBizNo");// 信用证编号
			String txnno = (String) context.get("butxn_txnNo");// 流水号
			paramMap.put("jjhmno", jjhmno);
			paramMap.put("prodcd", TradeNoConstant.Prodcd.PPODCD_IMTHDB);
			paramMap.put("lcno", lcno);
			paramMap.put("txnno", txnno);
		} else if (TradeNoConstant.IMLGISSUE_TRADENO.equals(tradeNo)) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			String jjhmno = (String) context.get("IMLCISSUE_feeNo");// 借据号
			String lcno = (String) context.get("butxn_curtBizNo");// 信用证编号
			String txnno = (String) context.get("butxn_txnNo");// 流水号
			paramMap.put("jjhmno", jjhmno);
			paramMap.put("prodcd", TradeNoConstant.Prodcd.PPODCD_IMTHDB);
			paramMap.put("lcno", lcno);
			paramMap.put("txnno", txnno);
		} else if (TradeNoConstant.IMLCAMEND_TRADENO.equals(tradeNo)) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			String jjhmno = (String) context.get("IMLCISSUE_feeNo");// 借据号
			String lcno = (String) context.get("butxn_curtBizNo");// 信用证编号
			String txnno = (String) context.get("butxn_txnNo");// 流水号
			paramMap.put("jjhmno", jjhmno);
			paramMap.put("prodcd", TradeNoConstant.Prodcd.PPODCD_IMTHDB);
			paramMap.put("lcno", lcno);
			paramMap.put("txnno", txnno);
		}

	}

	/**
	 * 调用通知接口
	 * */
	public void callCrou(Context context) throws Exception {
		SendCreditNoticeDao noticeDao = new SendCreditNoticeDao();
		List<Map<String, Object>> list = noticeDao.getAllNotice();
		if (list != null) {
			for (Map<String, Object> map : list) {
				if (map.get("interfaceid") != null) {
					String interfaceid = (String) map.get("interfaceid");
					if (interfaceid.equals(CROU_INTERFACE_ID_01)) {
						this.callCrou01(context, list);
					} else if (interfaceid.equals(CROU_INTERFACE_ID_02)) {
						this.callCrou02(context, list);
					} else if (interfaceid.equals(CROU_INTERFACE_ID_03)) {
						this.callCrou03(context, list);
					}
				}
			}
		}
	}

	/**
	 * 保存数据
	 * 
	 * @throws EbillsException
	 * */
	public void saveNotice(Context context) throws EbillsException {
		String tradeNo = (String) context.get("butxn_tradeNo");
		TradeDtlsBiz dtlsBiz=new TradeDtlsBiz();
		TradeOtherDtlsBiz otherDtlsBiz=new TradeOtherDtlsBiz();
		if (TradeNoConstant.IMLCISSUE_TRADENO.equals(tradeNo)) {
			saveLcIssue(context);
			dtlsBiz.saveLcIssueDtls(context);
		} else if (TradeNoConstant.IMTHDB_TRADENO.equals(tradeNo)) {
			saveThdb(context);
			dtlsBiz.saveThdbDtls(context);
		} else if (TradeNoConstant.IMLGISSUE_TRADENO.equals(tradeNo)) {
			saveLgIssue(context);
			dtlsBiz.saveLGIssueDtls(context);
		} else if (TradeNoConstant.IMLCAMEND_TRADENO.equals(tradeNo)) {
			saveLcModify(context);
			dtlsBiz.saveLcAmendDtls(context);
		}else if (TradeNoConstant.IMLCAMENDAFF_TRADENO.equals(tradeNo)) {
			dtlsBiz.saveLcAmendAffirmDtls(context);
		}else if (TradeNoConstant.IMLCREVO_TRADENO.equals(tradeNo)) {
			dtlsBiz.saveLcRevoDtls(context);
		} else if (TradeNoConstant.LCCLOASE_TRADENO.equals(tradeNo)) {
			saveLcClose(context);
			dtlsBiz.saveLcCloselDtls(context);
		} else if (TradeNoConstant.LCRECOVER_TRADENO.equals(tradeNo)) {
			dtlsBiz.saveLcRecoverDtls(context);
		} else if (TradeNoConstant.LCCANCEL_TRADENO.equals(tradeNo)) {
			saveLcCancel(context);
			dtlsBiz.saveLcCancelDtls(context);
		} else if (TradeNoConstant.LCRETURN_TRADENO.equals(tradeNo)) {
			saveLcReturn(context);
			otherDtlsBiz.saveLcReturnDtls(context);
		} else if (TradeNoConstant.LCACPT_TRADENO.equals(tradeNo)) {
			saveLcAcpt(context);
			dtlsBiz.saveLcAcptDtls(context);
		} else if (TradeNoConstant.LCACPTTEX_TRADENO.equals(tradeNo)) {
			saveLcAcpttex(context);
			dtlsBiz.saveLcAcptexDtls(context);
		} else if (TradeNoConstant.LCPAY_TRADENO.equals(tradeNo)) {
			saveLcPay(context);
			otherDtlsBiz.saveLcPayDtls(context);
		} else if (TradeNoConstant.THDBCAL_TRADENO.equals(tradeNo)) {
			saveThdbCal(context);
			dtlsBiz.saveThdbCalDtls(context);
		} else if (TradeNoConstant.THDBPEDL_TRADENO.equals(tradeNo)) {
			saveThdBpedl(context);
			dtlsBiz.saveThdbPedDtls(context);
		} else if (TradeNoConstant.LGRFGU_TRADENO.equals(tradeNo)) {
			saveLgRfgu(context);
			dtlsBiz.saveLGCancelDtls(context);
		} else if (TradeNoConstant.LGCANCEL_TRADENO.equals(tradeNo)) {
			saveLgCancel(context);
		} else if (TradeNoConstant.LGPAY_TRADENO.equals(tradeNo)) {
			saveLgPay(context);
			otherDtlsBiz.saveLGPayDtls(context);
		} else if (TradeNoConstant.LGCLOSE_TRADENO.equals(tradeNo)) {
			saveLgClose(context);
			otherDtlsBiz.saveLGCloseDtls(context);
		} else if (TradeNoConstant.BUREME_TRADENO.equals(tradeNo)) {
			saveBureme(context);
		} else if (TradeNoConstant.LCAB_TRADENO.equals(tradeNo)) {
			otherDtlsBiz.saveLcAbDtls(context);
		} else if (TradeNoConstant.LCABSEC_TRADENO.equals(tradeNo)) {
			otherDtlsBiz.saveLcAbSecDtls(context);
		}else if (TradeNoConstant.LCABAMD_TRADENO.equals(tradeNo)) {
			otherDtlsBiz.saveLcAbAmdDtls(context);
		}else if (TradeNoConstant.LCREJ_TRADENO.equals(tradeNo)) {
			// saveBureme(context);
		}
	}

	/**
	 * 保存信用证开立数据
	 **/
	private void saveLcIssue(Context context) throws EbillsException {
		SendCreditNoticeDao dao = new SendCreditNoticeDao();
		String txnno = (String) context.get("txnNo");// 流水号
		String lcno = (String) context.get("IMLCISSUE_lcNo");// 信用证编号
		// 查询出拮据号
		String dlrfno = "";
		dlrfno = (String) context.get("IMLCISSUE_feeNo");// 借据号
		String jjhmno = "";
		jjhmno = (String) context.get("IMLCISSUE_feeNo");// 借据号
		String leamts = null;
		Date eydate = null;
		String prodcd = TradeNoConstant.Prodcd.PPODCD_IMLCISSUE_TRADENO;
		String trtype = TradeNoConstant.TradeTpe.TRADE_TYPE_02;
		String status = TradeNoConstant.PZStatus.PZ_STATUS_1;
		String mgrate = "";
		Date vldate = null;
		Date madate = null;
		String inrate = "";
		String interfaceid = "2";
		dao.saveTpCredNotic(txnno, dlrfno, jjhmno, leamts, eydate, prodcd,
				trtype, status, mgrate, vldate, madate, inrate, interfaceid,
				lcno);
	}

	/**
	 * 保存保函开立数据
	 **/
	private void saveLgIssue(Context context) throws EbillsException {
		SendCreditNoticeDao dao = new SendCreditNoticeDao();
		String txnno = (String) context.get("txnNo");// 流水号
		String lcno = null;
		// 查询出拮据号
		String dlrfno = "";
		dlrfno = (String) context.get("IMLGISSUE_creditNo");// 借据号
		String jjhmno = "";
		jjhmno = (String) context.get("IMLGISSUE_creditNo");// 借据号
		String leamts = null;
		Date eydate = null;
		String prodcd = TradeNoConstant.Prodcd.PPODCD_IMLGISSUE;
		String trtype = TradeNoConstant.TradeTpe.TRADE_TYPE_02;
		String status = TradeNoConstant.PZStatus.PZ_STATUS_1;
		String mgrate = "";
		Date vldate = null;
		Date madate = null;
		String inrate = "";
		String interfaceid = "2";
		dao.saveTpCredNotic(txnno, dlrfno, jjhmno, leamts, eydate, prodcd,
				trtype, status, mgrate, vldate, madate, inrate, interfaceid,
				lcno);
	}

	/**
	 * 保存提货担保
	 **/
	private void saveThdb(Context context) throws EbillsException {
		SendCreditNoticeDao dao = new SendCreditNoticeDao();
		String txnno = (String) context.get("txnNo");// 流水号
		String lcno = (String) context.get("IMLCISSUE_lcNo");// 信用证编号
		// 查询出拮据号
		String dlrfno = "";
		dlrfno = (String) context.get("IMTHDB_feeNo");// 借据号
		String jjhmno = "";
		jjhmno = (String) context.get("IMTHDB_feeNo");// 借据号
		String leamts = null;
		Date eydate = null;
		String prodcd = TradeNoConstant.Prodcd.PPODCD_IMTHDB;
		String trtype = TradeNoConstant.TradeTpe.TRADE_TYPE_02;
		String status = TradeNoConstant.PZStatus.PZ_STATUS_1;
		String mgrate = "";
		Date vldate = null;
		Date madate = null;
		String inrate = "";
		String interfaceid = "2";
		dao.saveTpCredNotic(txnno, dlrfno, jjhmno, leamts, eydate, prodcd,
				trtype, status, mgrate, vldate, madate, inrate, interfaceid,
				lcno);
	}

	/**
	 * 保存信用证开立修改
	 **/
	private void saveLcModify(Context context) throws EbillsException {
		SendCreditNoticeDao dao = new SendCreditNoticeDao();
		String txnno = (String) context.get("txnNo");// 流水号
		String lcno = (String) context.get("IMLCISSUE_lcNo");// 信用证编号
		String varFlag=(String)context.get("IMLCAMEND_varFlag");
		// 查询出拮据号
//		if("+".equals(varFlag)){
			String dlrfno = "";
			dlrfno = (String) context.get("IMLCISSUE_feeNo");// 借据号
			String jjhmno = "";
			jjhmno = (String) context.get("IMLCISSUE_feeNo");// 借据号
			if(!"".equals(jjhmno)){
				String leamts = null;
				Date eydate = null;
				String prodcd = TradeNoConstant.Prodcd.PPODCD_IMLCISSUE_TRADENO;
				String trtype = TradeNoConstant.TradeTpe.TRADE_TYPE_43;
				String status = TradeNoConstant.PZStatus.PZ_STATUS_1;
				String mgrate = "";
				Date vldate = null;
				Date madate = null;
				String inrate = "";
				String interfaceid = "3";
				dao.saveTpCredNotic(txnno, dlrfno, jjhmno, leamts, eydate, prodcd,
						trtype, status, mgrate, vldate, madate, inrate, interfaceid,
						lcno);
			}

//		}

	}

	/**
	 * 保存信用证注销
	 */
	private void saveLcClose(Context context) throws EbillsException {
		SendCreditNoticeDao dao = new SendCreditNoticeDao();
		String txnno = (String) context.get("txnNo");
		String lcno = (String) context.get("IMLCISSUE_lcNo");

		// 查询出拮据号
		String dlrfno = "";
		List<Map<String, Object>> lcList = dao.getLcList(lcno);
		if (lcList.size() > 0) {
			dlrfno = (String) lcList.get(0).get("feeno");
		}
		String jjhmno = "";
		if (lcList.size() > 0) {
			jjhmno = (String) lcList.get(0).get("feeno");
		}
		String leamts = "";
		leamts = String.valueOf(BalanceInfo.getBalance(lcno, "LCACTBAL"));// 表外余额
		Date eydate = null;
		if (lcList.size() > 0) {
			eydate = Commons.getWorkDate();
		}
		String prodcd = TradeNoConstant.Prodcd.PPODCD_IMLCISSUE_TRADENO;
		String trtype = TradeNoConstant.TradeTpe.TRADE_TYPE_03;
		String status = TradeNoConstant.PZStatus.PZ_STATUS_9;
		String mgrate = "";
		if (lcList.size() > 0 && lcList.get(0).get("mgrrate") != null) {
			mgrate = (String) lcList.get(0).get("mgrrate");
		}
		Date vldate = null;
		if (lcList.size() > 0) {
			vldate = CommonUtil.formatDate((String) context.get("lcissuedate"),
					"yyyyMMdd");
		}
		Date madate = null;
		if (lcList.size() > 0) {
			madate = CommonUtil.formatDate((String) context.get("expirydate"),
					"yyyyMMdd");
		}
		String inrate = "";
		String interfaceid = "1";
		dao.saveTpCredNotic(txnno, dlrfno, jjhmno, leamts, eydate, prodcd,
				trtype, status, mgrate, vldate, madate, inrate, interfaceid,
				lcno);
	}

	/**
	 * 保存信用证撤销
	 */
	private void saveLcCancel(Context context) throws EbillsException {
		SendCreditNoticeDao dao = new SendCreditNoticeDao();
		String txnno = (String) context.get("txnNo");
		String lcno = (String) context.get("IMLCISSUE_lcNo");

		// 查询出拮据号
		String dlrfno = "";
		List<Map<String, Object>> lcList = dao.getLcList(lcno);
		if (lcList.size() > 0) {
			dlrfno = (String) lcList.get(0).get("feeno");
		}
		String jjhmno = "";
		if (lcList.size() > 0) {
			jjhmno = (String) lcList.get(0).get("feeno");
		}
		String leamts = "";
		leamts = String.valueOf(BalanceInfo.getBalance(lcno, "LCACTBAL"));// 表外余额
		Date eydate = null;
		if (lcList.size() > 0) {
			eydate = Commons.getWorkDate();
		}
		String prodcd = TradeNoConstant.Prodcd.PPODCD_IMLCISSUE_TRADENO;
		String trtype = TradeNoConstant.TradeTpe.TRADE_TYPE_03;
		String status = TradeNoConstant.PZStatus.PZ_STATUS_8;
		String mgrate = "";
		if (lcList.size() > 0 && lcList.get(0).get("mgrrate") != null) {
			mgrate = (String) lcList.get(0).get("mgrrate");
		}
		leamts = String.valueOf(BalanceInfo.getBalance(lcno, "LCACTBAL"));
		Date vldate = null;
		if (lcList.size() > 0) {
			vldate = CommonUtil.formatDate((String) context.get("lcissuedate"),
					"yyyyMMdd");
		}
		Date madate = null;
		if (lcList.size() > 0) {
			madate = CommonUtil.formatDate((String) context.get("expirydate"),
					"yyyyMMdd");
		}
		String inrate = "";
		String interfaceid = "1";
		dao.saveTpCredNotic(txnno, dlrfno, jjhmno, leamts, eydate, prodcd,
				trtype, status, mgrate, vldate, madate, inrate, interfaceid,
				lcno);
	}

	/**
	 * 保存信用放单
	 */
	private void saveLcReturn(Context context) throws EbillsException {
		SendCreditNoticeDao dao = new SendCreditNoticeDao();
		String txnno = (String) context.get("txnNo");
		String lcno = (String) context.get("IMLCAB_lcNo");//
		String abno = (String) context.get("IMLCAB_abNo");// 信用证到单号
		String dealType = (String) context.get("PBREJ_dealType");
		String returnAmt = (String) context.get("PBREJ_returnAmt");
		if ("1".equals(dealType)) {// 退单才处理否则不处理
			// 只有退单时候才处理
			/**
			 * 当时无偿放单时候 如果已经承兑， 还款金额=承兑金额 如果未承兑 还看金=退单金额
			 */
			// 查询出拮据号
			String dlrfno = "";
			List<Map<String, Object>> lcList = dao.getLcList(lcno);
			List<Map<String, Object>> acptList = dao.getLcAcptList(abno);
			if (lcList.size() > 0) {
				dlrfno = (String) lcList.get(0).get("feeno");
			}
			String jjhmno = "";
			if (lcList.size() > 0) {
				jjhmno = (String) lcList.get(0).get("feeno");
			}
			String leamts = "";
			if (acptList.size() > 0) {
				leamts = (String) acptList.get(0).get("acptamt");
			} else {
				leamts = returnAmt;
			}
			// leamts=String.valueOf(Balance.getBalance(lcno, "LCACTBAL"));
			Date eydate = null;
			eydate = Commons.getWorkDate();
			String prodcd = TradeNoConstant.Prodcd.PPODCD_IMLCISSUE_TRADENO;
			String trtype = TradeNoConstant.TradeTpe.TRADE_TYPE_03;
			String status = TradeNoConstant.PZStatus.PZ_STATUS_8;
			String mgrate = "";
			if (lcList.size() > 0 && lcList.get(0).get("mgrrate") != null) {
				mgrate = (String) lcList.get(0).get("mgrrate");
			}
			Date vldate = null;
			if (lcList.size() > 0) {
				vldate = CommonUtil.formatDate(
						(String) context.get("lcissuedate"), "yyyyMMdd");
			}
			Date madate = null;
			if (lcList.size() > 0) {
				madate = CommonUtil.formatDate(
						(String) context.get("expirydate"), "yyyyMMdd");
			}
			String inrate = "";
			String interfaceid = "1";
			dao.saveTpCredNotic(txnno, dlrfno, jjhmno, leamts, eydate, prodcd,
					trtype, status, mgrate, vldate, madate, inrate,
					interfaceid, lcno);
		}
	}

	/**
	 * 保存信用证承兑
	 */
	private void saveLcAcpt(Context context) throws EbillsException {
		SendCreditNoticeDao dao = new SendCreditNoticeDao();
		String txnno = (String) context.get("txnNo");
		String lcno = (String) context.get("IMLCAB_lcNo");
		String acptAmt = (String) context.get("PBACPT_acptAmt");
		/**
		 * 承兑取承兑金额
		 * */
		// 查询出拮据号
		String dlrfno = "";
		List<Map<String, Object>> lcList = dao.getLcList(lcno);
		if (lcList.size() > 0) {
			dlrfno = (String) lcList.get(0).get("feeno");
		}
		String jjhmno = "";
		if (lcList.size() > 0) {
			jjhmno = (String) lcList.get(0).get("feeno");
		}
		String leamts = "";
		leamts = acptAmt;
		leamts = (String) context.get("PBACPT_acptAmt");
		Date eydate = null;
		eydate = Commons.getWorkDate();
		String prodcd = TradeNoConstant.Prodcd.PPODCD_IMLCISSUE_TRADENO;
		String trtype = TradeNoConstant.TradeTpe.TRADE_TYPE_03;
		String status = TradeNoConstant.PZStatus.PZ_STATUS_1;
		String mgrate = "";
		if (lcList.size() > 0 && lcList.get(0).get("mgrrate") != null) {
			mgrate = (String) lcList.get(0).get("mgrrate");
		}
		Date vldate = null;
		if (lcList.size() > 0) {
			vldate = CommonUtil.formatDate((String) context.get("lcissuedate"),
					"yyyyMMdd");
		}
		Date madate = null;
		if (lcList.size() > 0) {
			madate = CommonUtil.formatDate((String) context.get("expirydate"),
					"yyyyMMdd");
		}
		String inrate = "";
		String interfaceid = "1";
		dao.saveTpCredNotic(txnno, dlrfno, jjhmno, leamts, eydate, prodcd,
				trtype, status, mgrate, vldate, madate, inrate, interfaceid,
				lcno);
	}

	/**
	 * 保存信用证承兑
	 */
	private void saveLcAcpttex(Context context) throws EbillsException {
		SendCreditNoticeDao dao = new SendCreditNoticeDao();
		String txnno = (String) context.get("txnNo");
		String lcno = (String) context.get("IMLCAB_lcNo");
		String newAcptAmt = (String) context.get("PBACPT_newAcptAmt");
		// 查询出拮据号
		/**
		 * 还款金额=承兑后的金额
		 */
		String dlrfno = "";
		List<Map<String, Object>> lcList = dao.getLcList(lcno);
		if (lcList.size() > 0) {
			dlrfno = (String) lcList.get(0).get("feeno");
		}
		String jjhmno = "";
		if (lcList.size() > 0) {
			jjhmno = (String) lcList.get(0).get("feeno");
		}
		String leamts = "";
		leamts = newAcptAmt;
		Date eydate = null;
		if (lcList.size() > 0) {
			eydate = Commons.getWorkDate();
		}
		String prodcd = TradeNoConstant.Prodcd.PPODCD_IMLCISSUE_TRADENO;
		String trtype = TradeNoConstant.TradeTpe.TRADE_TYPE_03;
		String status = TradeNoConstant.PZStatus.PZ_STATUS_1;
		String mgrate = "";
		if (lcList.size() > 0 && lcList.get(0).get("mgrrate") != null) {
			mgrate = (String) lcList.get(0).get("mgrrate");
		}
		Date vldate = null;
		if (lcList.size() > 0) {
			vldate = CommonUtil.formatDate((String) context.get("lcissuedate"),
					"yyyyMMdd");
		}
		Date madate = null;
		if (lcList.size() > 0) {
			madate = CommonUtil.formatDate((String) context.get("expirydate"),
					"yyyyMMdd");
		}
		String inrate = "";
		String interfaceid = "1";
		dao.saveTpCredNotic(txnno, dlrfno, jjhmno, leamts, eydate, prodcd,
				trtype, status, mgrate, vldate, madate, inrate, interfaceid,
				lcno);
	}

	/**
	 * 保存信用付汇
	 */
	private void saveLcPay(Context context) throws EbillsException {
		SendCreditNoticeDao dao = new SendCreditNoticeDao();
		String txnno = (String) context.get("txnNo");
		String lcno = (String) context.get("IMLCAB_lcNo");
		String abno = (String) context.get("IMLCAB_abNo");
		String isLcClose = (String) context.get("PBPAY_isLcClose");
		String payAmt = (String) context.get("PBPAY_payAmt");// 付款金额
		String agentPayAmt = (String) context.get("IMLCPAY_agentPayAmt");// 代付金额
		String outBalance = String
				.valueOf(BalanceInfo.getBalance(lcno, "LCACTBAL"));// 表外余额
		String acpBalance = String
				.valueOf(BalanceInfo.getBalance(abno, "LCACPBAL"));// 承兑余额
		double total = Double.valueOf(payAmt) + Double.valueOf(agentPayAmt);
		String isusance = "";
		String draftDays = "0";
		// 查询出拮据号
		/**
		 * 信用付汇 分即期付汇和远期付汇 PBPAY_payAmt IMLCPAY_agentPayAmt 即期 未闭卷
		 * 付款金额+代付金额>=表外余额 还款金额去表外余额 付款金额+代付金额<表外余额 付款金+代付金额=还款金额 闭卷：表外余额
		 * 远期信用证付汇 未闭卷 付款+代付》=承兑 承兑=还款金额 为付清 付款+代付《承兑 付款+ 闭卷 表外余额+承兑余额
		 **/
		String dlrfno = "";
		List<Map<String, Object>> lcList = dao.getLcInfoList(lcno);

		if (lcList.size() > 0) {
			dlrfno = (String) lcList.get(0).get("feeno");
		}
		String jjhmno = "";
		if (lcList.size() > 0) {
			jjhmno = (String) lcList.get(0).get("feeno");
		}
		String leamts = "";
		if (lcList.size() > 0) {
			leamts = (String) lcList.get(0).get("lcamt");
		}
		Date eydate = null;
		eydate = Commons.getWorkDate();
		// 根据闭卷标识做判断如果闭卷 就是PZ_STATUS_9 如果未闭卷就是PZ_STATUS_1
		String prodcd = TradeNoConstant.Prodcd.PPODCD_IMLCISSUE_TRADENO;
		String trtype = TradeNoConstant.TradeTpe.TRADE_TYPE_03;
		String status = TradeNoConstant.PZStatus.PZ_STATUS_9;
		if ("Y".equals(isLcClose)) {
			status = TradeNoConstant.PZStatus.PZ_STATUS_9;
		} else {
			status = TradeNoConstant.PZStatus.PZ_STATUS_1;
		}
		if (lcList.size() > 0) {
			isusance = (String) lcList.get(0).get("isusance");
			draftDays = (String) lcList.get(0).get("draftdays");
			if ("Y".equals(isusance) || Integer.valueOf(draftDays) == 0) {// 即期信用证
				if ("Y".equals(isLcClose)) {
					leamts = outBalance;// 表外余额
				} else {
					if (total >= Double.valueOf(outBalance)) {
						leamts = outBalance;
					} else {
						leamts =String.valueOf(Double.valueOf(payAmt) + Double.valueOf(agentPayAmt));
					}
				}
			} else {// 远期信用证
				List<Map<String, Object>> acptList = dao.getLcAcptList(abno);
				String acptAmt = "0";// 承兑金额
				if (acptList.size() > 0) {
					acptAmt = (String) acptList.get(0).get("acptamt");
				}
				if ("Y".equals(isLcClose)) {
					leamts = String.valueOf(Double.valueOf(outBalance) + Double.valueOf(acpBalance));// 表外余额
				} else {
					if (total >= Double.valueOf(acptAmt)) {
						leamts = acptAmt;
					} else {
						leamts = String.valueOf(Double.valueOf(acptAmt) + Double.valueOf(acpBalance));
					}
				}
			}
		}
		String mgrate = "";
		if (lcList.size() > 0 && lcList.get(0).get("mgrrate") != null) {
			mgrate = (String) lcList.get(0).get("mgrrate");
		}
		Date vldate = null;
		if (lcList.size() > 0) {
			vldate = CommonUtil.formatDate((String) context.get("lcissuedate"),
					"yyyyMMdd");
		}
		Date madate = null;
		if (lcList.size() > 0) {
			madate = CommonUtil.formatDate((String) context.get("expirydate"),
					"yyyyMMdd");
		}
		String inrate = "";
		String interfaceid = "1";
		dao.saveTpCredNotic(txnno, dlrfno, jjhmno, leamts, eydate, prodcd,
				trtype, status, mgrate, vldate, madate, inrate, interfaceid,
				lcno);
	}

	/**
	 * 保存提货担保注销
	 */
	private void saveThdbCal(Context context) throws EbillsException {
		SendCreditNoticeDao dao = new SendCreditNoticeDao();
		String txnno = (String) context.get("txnNo");
		String billNo = (String) context.get("IMTHDB_ladBillNo");
		String payedAmt = null;
		double assamt = 0;// 提货担保金额
		double payAmt = 0;
		// 查询出拮据号
		/**
		 * 未赔付直接注销 还款=提货担保金额 赔付再注销 若赔付时冲的是提货担保金额 还款金额=0 若赔付时 还款金额是赔付金额
		 * 则还款金额=提货担保金额-赔付金额
		 * */
		String dlrfno = "";
		List<Map<String, Object>> lcList = dao.getTHDBList(billNo);
		List<Map<String, Object>> thdbpedList = dao.getTHDBPEDList(billNo);
		if (lcList.size() > 0) {
			dlrfno = (String) lcList.get(0).get("feeno");
		}
		String jjhmno = "";
		if (lcList.size() > 0) {
			jjhmno = (String) lcList.get(0).get("feeno");
		}
		String leamts = "";
		if (lcList.size() > 0) {
			leamts = (String) lcList.get(0).get("assamt");
			if (!StringUtils.isEmpty(leamts)) {
				assamt = Double.valueOf(leamts);
			}
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
		Date eydate = null;
		if (lcList.size() > 0) {
			eydate = Commons.getWorkDate();
		}
		String prodcd = TradeNoConstant.Prodcd.PPODCD_IMTHDB;
		String trtype = TradeNoConstant.TradeTpe.TRADE_TYPE_03;
		String status = TradeNoConstant.PZStatus.PZ_STATUS_9;
		String mgrate = "";
		if (lcList.size() > 0 && lcList.get(0).get("mgrrate") != null) {
			mgrate = (String) lcList.get(0).get("mgrrate");
		}
		Date vldate = null;
		if (lcList.size() > 0) {
			vldate = CommonUtil.formatDate((String) context.get("assdate"),
					"yyyyMMdd");
		}
		Date madate = null;
		if (lcList.size() > 0) {
			madate = CommonUtil.formatDate((String) context.get("duedate"),
					"yyyyMMdd");
		}
		String inrate = "";
		String interfaceid = "1";
		dao.saveTpCredNotic(txnno, dlrfno, jjhmno, leamts, eydate, prodcd,
				trtype, status, mgrate, vldate, madate, inrate, interfaceid,
				null);
	}

	/**
	 * 保存提货担保赔付
	 */
	public void saveThdBpedl(Context context) throws EbillsException {
		SendCreditNoticeDao dao = new SendCreditNoticeDao();
		String txnno = (String) context.get("txnNo");
		String billNo = (String) context.get("IMTHDB_ladBillNo");
		String payedAmt = (String) context.get("IMTHDBPED_payedAmt");
		double assamt = 0;// 提货担保金额
		double payAmt = 0;
		// 查询出拮据号
		/**
		 * 赔付金额 大于提货担保金额 还款金额=提货担保金额 赔付金额 <提货担保金额 还款金额=赔付金额
		 **/
		List<Map<String, Object>> lcList = dao.getTHDBList(billNo);
		String dlrfno = "";
		if (lcList.size() > 0) {
			dlrfno = (String) lcList.get(0).get("feeno");
		}
		String jjhmno = "";
		if (lcList.size() > 0) {
			jjhmno = (String) lcList.get(0).get("feeno");
		}
		String leamts = "";
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
		Date eydate = null;
		eydate = Commons.getWorkDate();
		String prodcd = TradeNoConstant.Prodcd.PPODCD_IMTHDB;
		String trtype = TradeNoConstant.TradeTpe.TRADE_TYPE_03;
		String status = TradeNoConstant.PZStatus.PZ_STATUS_9;
		String mgrate = "";
		if (lcList.size() > 0 && lcList.get(0).get("mgrrate") != null) {
			mgrate = (String) lcList.get(0).get("mgrrate");
		}
		Date vldate = null;
		if (lcList.size() > 0) {
			vldate = CommonUtil.formatDate((String) context.get("assdate"),
					"yyyyMMdd");
		}
		Date madate = null;
		if (lcList.size() > 0) {
			madate = CommonUtil.formatDate((String) context.get("duedate"),
					"yyyyMMdd");
		}
		String inrate = "";
		String interfaceid = "1";
		dao.saveTpCredNotic(txnno, dlrfno, jjhmno, leamts, eydate, prodcd,
				trtype, status, mgrate, vldate, madate, inrate, interfaceid,
				null);
	}

	/**
	 * 保存保函注销
	 */
	private void saveLgRfgu(Context context) throws EbillsException {
		SendCreditNoticeDao dao = new SendCreditNoticeDao();
		String txnno = (String) context.get("txnNo");
		String lgNo = (String) context.get("IMLGISSUE_lgNo");
		// 查询出拮据号
		String dlrfno = "";
		List<Map<String, Object>> lcList = dao.getLgList(lgNo);
		if (lcList.size() > 0) {
			dlrfno = (String) lcList.get(0).get("creditno");
		}
		String jjhmno = "";
		if (lcList.size() > 0) {
			jjhmno = (String) lcList.get(0).get("creditno");
		}
		String leamts = "";
		if (lcList.size() > 0) {
			leamts = (String) lcList.get(0).get("lgamt");
		}
		Date eydate = null;
		if (lcList.size() > 0) {
			eydate = Commons.getWorkDate();
		}
		String prodcd = TradeNoConstant.Prodcd.PPODCD_IMLGISSUE;
		String trtype = TradeNoConstant.TradeTpe.TRADE_TYPE_03;
		String status = TradeNoConstant.PZStatus.PZ_STATUS_9;
		String mgrate = "";
		if (lcList.size() > 0 && lcList.get(0).get("depositpct") != null) {
			mgrate = (String) lcList.get(0).get("depositpct");
		}
		Date vldate = null;
		if (lcList.size() > 0) {
			vldate = CommonUtil.formatDate((String) context.get("effectdate"),
					"yyyyMMdd");
		}
		Date madate = null;
		if (lcList.size() > 0) {
			madate = CommonUtil.formatDate((String) context.get("failruedate"),
					"yyyyMMdd");
		}
		String inrate = "";
		String interfaceid = "1";
		dao.saveTpCredNotic(txnno, dlrfno, jjhmno, leamts, eydate, prodcd,
				trtype, status, mgrate, vldate, madate, inrate, interfaceid,
				null);
	}

	/**
	 * 保存保函撤销
	 */
	private void saveLgCancel(Context context) throws EbillsException {
		SendCreditNoticeDao dao = new SendCreditNoticeDao();
		String txnno = (String) context.get("txnNo");
		String lgNo = (String) context.get("IMLGISSUE_lgNo");
		// 查询出拮据号
		String dlrfno = "";
		List<Map<String, Object>> lcList = dao.getLgList(lgNo);
		if (lcList.size() > 0) {
			dlrfno = (String) lcList.get(0).get("creditno");
		}
		String jjhmno = "";
		if (lcList.size() > 0) {
			jjhmno = (String) lcList.get(0).get("creditno");
		}
		String leamts = "";
		if (lcList.size() > 0) {
			leamts = (String) lcList.get(0).get("lgamt");
		}
		Date eydate = null;
		if (lcList.size() > 0) {
			eydate = Commons.getWorkDate();
		}
		String prodcd = TradeNoConstant.Prodcd.PPODCD_IMLGISSUE;
		String trtype = TradeNoConstant.TradeTpe.TRADE_TYPE_03;
		String status = TradeNoConstant.PZStatus.PZ_STATUS_8;
		String mgrate = "";
		if (lcList.size() > 0 && lcList.get(0).get("depositpct") != null) {
			mgrate = (String) lcList.get(0).get("depositpct");
		}
		Date vldate = null;
		if (lcList.size() > 0) {
			vldate = CommonUtil.formatDate((String) context.get("effectdate"),
					"yyyyMMdd");
		}
		Date madate = null;
		if (lcList.size() > 0) {
			madate = CommonUtil.formatDate((String) context.get("failruedate"),
					"yyyyMMdd");
		}
		String inrate = "";
		String interfaceid = "1";
		dao.saveTpCredNotic(txnno, dlrfno, jjhmno, leamts, eydate, prodcd,
				trtype, status, mgrate, vldate, madate, inrate, interfaceid,
				null);
	}

	/**
	 * 保存保函赔付
	 */
	private void saveLgPay(Context context) throws EbillsException {
		SendCreditNoticeDao dao = new SendCreditNoticeDao();
		String txnno = (String) context.get("txnNo");
		String lgNo = (String) context.get("IMLGISSUE_lgNo");
		// 查询出拮据号
		String dlrfno = "";
		List<Map<String, Object>> lcList = dao.getLgList(lgNo);
		if (lcList.size() > 0) {
			dlrfno = (String) lcList.get(0).get("creditno");
		}
		String jjhmno = "";
		if (lcList.size() > 0) {
			jjhmno = (String) lcList.get(0).get("creditno");
		}
		String leamts = "";
		if (lcList.size() > 0) {
			leamts = (String) lcList.get(0).get("lgamt");
		}
		Date eydate = null;
		if (lcList.size() > 0) {
			eydate = Commons.getWorkDate();
		}
		String prodcd = TradeNoConstant.Prodcd.PPODCD_IMLGISSUE;
		String trtype = TradeNoConstant.TradeTpe.TRADE_TYPE_03;
		String status = TradeNoConstant.PZStatus.PZ_STATUS_9;
		String mgrate = "";
		if (lcList.size() > 0 && lcList.get(0).get("depositpct") != null) {
			mgrate = (String) lcList.get(0).get("depositpct");
		}
		Date vldate = null;
		if (lcList.size() > 0) {
			vldate = CommonUtil.formatDate((String) context.get("effectdate"),
					"yyyyMMdd");
		}
		Date madate = null;
		if (lcList.size() > 0) {
			madate = CommonUtil.formatDate((String) context.get("failruedate"),
					"yyyyMMdd");
		}
		String inrate = "";
		String interfaceid = "1";
		dao.saveTpCredNotic(txnno, dlrfno, jjhmno, leamts, eydate, prodcd,
				trtype, status, mgrate, vldate, madate, inrate, interfaceid,
				null);
	}

	/**
	 * 保存保函闭卷索赔
	 */
	private void saveLgClose(Context context) throws EbillsException {
		SendCreditNoticeDao dao = new SendCreditNoticeDao();
		String txnno = (String) context.get("txnNo");
		String lgNo = (String) context.get("IMLGISSUE_lgNo");
		// 查询出拮据号
		String dlrfno = "";
		List<Map<String, Object>> lcList = dao.getLgList(lgNo);
		if (lcList.size() > 0) {
			dlrfno = (String) lcList.get(0).get("creditno");
		}
		String jjhmno = "";
		if (lcList.size() > 0) {
			jjhmno = (String) lcList.get(0).get("creditno");
		}
		String leamts = "";
		if (lcList.size() > 0) {
			leamts = (String) lcList.get(0).get("lgamt");
		}
		Date eydate = null;
		if (lcList.size() > 0) {
			eydate = Commons.getWorkDate();
		}
		String prodcd = TradeNoConstant.Prodcd.PPODCD_IMLGISSUE;
		String trtype = TradeNoConstant.TradeTpe.TRADE_TYPE_03;
		String status = TradeNoConstant.PZStatus.PZ_STATUS_9;
		String mgrate = "";
		if (lcList.size() > 0 && lcList.get(0).get("depositpct") != null) {
			mgrate = (String) lcList.get(0).get("depositpct");
		}
		Date vldate = null;
		if (lcList.size() > 0) {
			vldate = CommonUtil.formatDate((String) context.get("effectdate"),
					"yyyyMMdd");
		}
		Date madate = null;
		if (lcList.size() > 0) {
			madate = CommonUtil.formatDate((String) context.get("failruedate"),
					"yyyyMMdd");
		}
		String inrate = "";
		String interfaceid = "1";
		dao.saveTpCredNotic(txnno, dlrfno, jjhmno, leamts, eydate, prodcd,
				trtype, status, mgrate, vldate, madate, inrate, interfaceid,
				null);
	}

	/**
	 * 手工冲账
	 */
	private void saveBureme(Context context) throws EbillsException {
		SendCreditNoticeDao dao = new SendCreditNoticeDao();
		String newtxnno = (String) context.get("txnNo");//
		String oldtxnno = (String) context.get("BUREMDEBT_srhTxnNo");
		String lgNo = (String) context.get("IMLGISSUE_lgNo");
		List<Map<String, Object>> buremelist = dao.getButxnar(oldtxnno);
		String buremeTradno = "";
		String buremeTxnno = oldtxnno;
		if (buremelist != null && buremelist.size() > 0) {
			buremeTradno = (String) buremelist.get(0).get("tradeno");
			if (TradeNoConstant.IMLCISSUE_TRADENO.equals(buremeTradno)) {
				ReturnAcctBiz accBiz = new ReturnAcctBiz();
				accBiz.saveLcIssueRetrun(buremeTxnno, newtxnno);
			} else if (TradeNoConstant.IMTHDB_TRADENO.equals(buremeTradno)) {
				ReturnAcctBiz accBiz = new ReturnAcctBiz();
				accBiz.saveThdbRetrun(buremeTxnno, newtxnno);
			} else if (TradeNoConstant.IMLGISSUE_TRADENO.equals(buremeTradno)) {
				ReturnAcctBiz accBiz = new ReturnAcctBiz();
				accBiz.saveLgIssueRetrun(buremeTxnno, newtxnno);
			} else if (TradeNoConstant.IMLCAMEND_TRADENO.equals(buremeTradno)) {
				ReturnAcctBiz accBiz = new ReturnAcctBiz();
				accBiz.saveLcModifyRetrun(buremeTxnno, newtxnno);
			} else if (TradeNoConstant.LCCLOASE_TRADENO.equals(buremeTradno)) {
				ReturnAcctBiz accBiz = new ReturnAcctBiz();
				accBiz.saveLcCloseRetrun(buremeTxnno, newtxnno);
			} else if (TradeNoConstant.LCCANCEL_TRADENO.equals(buremeTradno)) {
				ReturnAcctBiz accBiz = new ReturnAcctBiz();
				accBiz.saveLcCancelRetrun(buremeTxnno, newtxnno);
			} else if (TradeNoConstant.LCRETURN_TRADENO.equals(buremeTradno)) {
				ReturnAcctBiz accBiz = new ReturnAcctBiz();
				accBiz.saveLcReturnRetrun(buremeTxnno, newtxnno);
			} else if (TradeNoConstant.LCACPT_TRADENO.equals(buremeTradno)) {
				ReturnAcctBiz accBiz = new ReturnAcctBiz();
				accBiz.saveLcAcptRetrun(buremeTxnno, newtxnno);
			} else if (TradeNoConstant.LCACPTTEX_TRADENO.equals(buremeTradno)) {
				ReturnAcctBiz accBiz = new ReturnAcctBiz();
				accBiz.saveLcAcpttexRetrun(buremeTxnno, newtxnno);
			} else if (TradeNoConstant.LCPAY_TRADENO.equals(buremeTradno)) {
				ReturnAcctBiz accBiz = new ReturnAcctBiz();
				accBiz.saveLcPayRetrun(buremeTxnno, newtxnno);
			} else if (TradeNoConstant.THDBCAL_TRADENO.equals(buremeTradno)) {
				ReturnAcctBiz accBiz = new ReturnAcctBiz();
				accBiz.saveThdBpedlRetrun(buremeTxnno, newtxnno);
			} else if (TradeNoConstant.THDBPEDL_TRADENO.equals(buremeTradno)) {
				ReturnAcctBiz accBiz = new ReturnAcctBiz();
				accBiz.saveThdbPedlRetrun(buremeTxnno, newtxnno);
			} else if (TradeNoConstant.LGRFGU_TRADENO.equals(buremeTradno)) {
				ReturnAcctBiz accBiz = new ReturnAcctBiz();
				accBiz.saveLgRfguRetrun(buremeTxnno, newtxnno);
			} else if (TradeNoConstant.LGCANCEL_TRADENO.equals(buremeTradno)) {
				ReturnAcctBiz accBiz = new ReturnAcctBiz();
				accBiz.saveLgCancelRetrun(buremeTxnno, newtxnno);
			} else if (TradeNoConstant.LGPAY_TRADENO.equals(buremeTradno)) {
				ReturnAcctBiz accBiz = new ReturnAcctBiz();
				accBiz.saveLgPayRetrun(buremeTxnno, newtxnno);
			} else if (TradeNoConstant.LGCLOSE_TRADENO.equals(buremeTradno)) {
				ReturnAcctBiz accBiz = new ReturnAcctBiz();
				accBiz.saveLgCloseRetrun(buremeTxnno, newtxnno);
			}
		}
	}

}
