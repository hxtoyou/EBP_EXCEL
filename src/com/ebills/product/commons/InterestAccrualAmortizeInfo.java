package com.ebills.product.commons;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.bussprocess.context.Context;

import com.ebills.commons.ImpCommons;
import com.ebills.commons.SerialNoFactory;
import com.ebills.param.datainfo.DataFactory;
import com.ebills.param.datainfo.DataInfo;
import com.ebills.util.EbillsException;
import com.ebills.util.EbillsLog;
import com.ebills.util.db.BaseDAO;
import com.ebills.utils.EbpConstants;
import com.ebills.utils.EbpDao;

/**
 * 计提摊销处理
 * 
 * @author panhao
 * 
 */
public class InterestAccrualAmortizeInfo {
	private static String className = InterestAccrualAmortizeInfo.class.getName();
	private static EbillsLog log = new EbillsLog(className);
	// 定义涉及的DataInfo名称
	private static String interestAmortize = "InterestAmortize";
	private static String interestAmortizeAcctInfo = "InterestAmortizeAcctInfo";
	private static String rateInfo = "TMPITAINRMA";

	@SuppressWarnings("unchecked")
	/**
	 * 利息计提摊销处理
	 * @param context
	 * @throws EbillsException
	 */
	public static void interestAccrualAmortize(Context context, String testDate)
			throws EbillsException {
		try {
			System.out.println("开始进行利息计提摊销处理..................");
			log.debug("开始进行利息计提摊销处理..................");
			ImpCommons.PARAM_PATH = "D:\\NewProduct\\EBP\\WebContent\\xmlcfg\\";
			List<Map<String, Object>> lnloanfoData = new LinkedList<Map<String, Object>>();
			/*
			 * HttpServletRequest request = (HttpServletRequest) context
			 * .getValue(EAPConstance.SERVLET_REQUEST);
			 */
			EbpDao gjDao = new EbpDao();
//			BaseDAO baseDao = new BaseDAO(null);
			String orgNo = "1000000000";// 机构名称
			// 查询目前所有未结清的融资信息
			List<Object> inputList = new LinkedList<Object>();
			String loanDataSql = "select a.txnno,a.loanbizno,a.loancur,a.loanamt,a.loanbal,a.interestdate,a.maturedate,a.loandays,a.rateType,a.Realrate,a.Inttmode,"
					+ "a.floatRateType,a.floatPoint,a.oveRate from buloanfo a where a.loanstate in ('0')";
			lnloanfoData = gjDao.queryBySql(loanDataSql, null, inputList);
			if (lnloanfoData != null) {
				for (int i = 0; i < lnloanfoData.size(); i++) {
					Map<String, Object> loanInfo = lnloanfoData.get(i);
					java.sql.Date currDate = Date.valueOf(testDate);
					System.out.println("系统当前日期:" + currDate);
					log.debug("系统当前日期:" + currDate);
					String loanBizNo = loanInfo.get("loanbizno").toString();
					System.out.println("融资编号:" + loanBizNo);
					log.debug("融资编号:" + loanBizNo);
					String loanCur = loanInfo.get("loancur").toString();
					double loanBal = Double.parseDouble(loanInfo.get("loanbal")
							.toString());
					double realRate = Double.parseDouble(loanInfo.get(
							"Realrate").toString());
					java.sql.Date interestDate = java.sql.Date.valueOf(loanInfo
							.get("interestdate").toString());
					java.sql.Date matureDate = java.sql.Date.valueOf(loanInfo
							.get("maturedate").toString());
					String floatRateType = loanInfo.get("floatRateType") == null ? ""
							: loanInfo.get("floatRateType").toString();
					double floatPoint = 0;
					if(loanInfo.get(
							"floatPoint")!=null){
						System.out.println("floatPoint test:"+loanInfo.get(
								"floatPoint"));
						floatPoint = Double.parseDouble(loanInfo.get(
								"floatPoint").toString());
					}
					String inttMode = loanInfo.get("Inttmode").toString();
					if("ADD".equals(inttMode)){
						inttMode = "0";
					}else{
						inttMode = "1";
					}
					double oveRate = 0;
					if(loanInfo.get("oveRate")!=null){
						oveRate = Double.parseDouble(loanInfo.get(
								"oveRate").toString());
					}
					/**
					 * 根据融资编号和计提摊销日期删除为当日的计提摊销记录
					 */
					deleteAccrualAmortizeByDate(loanBizNo, currDate);
					/**
					 * 统计该笔融资已计提摊销总金额
					 */
					double alreadyAccrualAmortizeSumAmt = 0;
					alreadyAccrualAmortizeSumAmt = getAlreadyAccrualAmortizeSumAmtByLoanBizNo(loanBizNo);
					System.out.println("已计提摊销总金额:"
							+ alreadyAccrualAmortizeSumAmt);
					log.debug("已计提摊销总金额:" + alreadyAccrualAmortizeSumAmt);
					
					/**
					 * 判断当天是否存在归还融资,如果存在,则还需要将当天冲销的利息减掉当天已归还的利息
					 */
					double payInttAmt = getLoanRepayInfo(loanBizNo,currDate);
					if(payInttAmt>0){
						System.out.println("当天归还融资计息金额:"+payInttAmt);
						log.debug("当天归还融资计息金额:"+payInttAmt);
						alreadyAccrualAmortizeSumAmt =  roundFormat(alreadyAccrualAmortizeSumAmt-payInttAmt,2);
						System.out.println("当天需冲销的计息金额:"+alreadyAccrualAmortizeSumAmt);
						log.debug("当天需冲销的计息金额:"+alreadyAccrualAmortizeSumAmt);
					}
					
					/**
					 * 获取利息账务类型的相关账号
					 */
					String lxsrAcctNo = getAcctNo(orgNo,"ZA00000106",loanCur);
					String lxsrAcctName = "利息收入";// 科目名称

					String ysyslxAcctNo = "";
					String ysyslxAcctName = "";
					// 利随本清:进行计提处理
					if ("0".equals(inttMode)) {
						ysyslxAcctName = "应收利息";
						ysyslxAcctNo = getAcctNo(orgNo,"ZA00000107",loanCur);
					}// 预收息:进行摊销处理
					else {
						ysyslxAcctName = "预收利息";
						ysyslxAcctNo = getAcctNo(orgNo,"ZA00000129",loanCur);
					}
					
					System.out.println("matureDate : " + matureDate);
				    System.out.println("currDate :" +currDate);
				    System.out.println("matureDate.getTime(): " + matureDate.getTime());
				    System.out.println("currDate.getTime():" +currDate.getTime());
					/**
					 * 融资正常-未逾期情况处理
					 */
					if (matureDate.getTime() >= currDate.getTime()) {
						// 开始计算利息
						String isOveDue = "N";//是否逾期
						double interestDayAmt = 0;
						Date fromAmortizeDate = null;
						Date toAccrualAmortizeDate = null;
						int accrualAmortizeDays = 0;
						HashMap map = getInterestAccrualAmortizeDayAmt(
								loanBizNo, loanCur, interestDate, loanBal,
								inttMode, realRate, floatRateType, floatPoint,
								currDate,isOveDue,matureDate,oveRate);
						interestDayAmt = Double.parseDouble(map.get(
								"interestDayAmt").toString());
						fromAmortizeDate = Date.valueOf(map.get(
								"fromAmortizeDate").toString());
						toAccrualAmortizeDate = Date.valueOf(map.get(
								"toAmortizeDate").toString());
						accrualAmortizeDays = Integer.parseInt(map.get(
								"amortizeDays").toString());
						Integer index = 0;
						// 本次计提摊销会计分录金额=当日计提摊销金额+会计分录冲销金额
						//double amortizeSumAcctAmt = roundFormat(interestDayAmt
						//		+ alreadyAccrualAmortizeSumAmt, 2);
						//20140419
						double amortizeSumAcctAmt = interestDayAmt;
						/**
						 * 根据已计提摊销的总金额, 来判断是否为第一天计提摊销
						 */
						if (alreadyAccrualAmortizeSumAmt > 0) {
							System.out.println("该笔为非第一天进行利息计提摊销");
							log.debug("该笔为非第一天进行利息计提摊销");
							// 插入利息计提摊销表数据
							insertInterestAccrualAmortize(loanBizNo, inttMode,
									currDate, realRate, interestDayAmt,
									fromAmortizeDate, toAccrualAmortizeDate,
									accrualAmortizeDays, loanBal,
									alreadyAccrualAmortizeSumAmt,
									amortizeSumAcctAmt,isOveDue);
							// Dr 利息收入 loanAccrualAmortizeSumAmt
							// Cr 预收利息(摊销)/应收利息(计提) loanAccrualAmortizeSumAmt
							insertInterestAccrualAmortizeAcctInfo(loanBizNo,
									loanBizNo, getSerialNo(),
									String.valueOf(++index), "", "D", loanCur,
									alreadyAccrualAmortizeSumAmt, currDate,
									lxsrAcctNo, lxsrAcctName, orgNo, lxsrAcctNo);
							insertInterestAccrualAmortizeAcctInfo(loanBizNo,
									loanBizNo, getSerialNo(),
									String.valueOf(++index), "", "C", loanCur,
									alreadyAccrualAmortizeSumAmt, currDate,
									ysyslxAcctNo, ysyslxAcctName, orgNo,
									ysyslxAcctNo);
							// Dr 预收利息(摊销)/应收利息(计提)
							// C*R1/Y+loanAccrualAmortizeSumAmt
							// Cr 利息收入 C*R1/Y +loanAccrualAmortizeSumAmt
							insertInterestAccrualAmortizeAcctInfo(loanBizNo,
									loanBizNo, getSerialNo(),
									String.valueOf(++index), "", "D", loanCur,
									amortizeSumAcctAmt, currDate, ysyslxAcctNo,
									ysyslxAcctName, orgNo, ysyslxAcctNo);
							insertInterestAccrualAmortizeAcctInfo(loanBizNo,
									loanBizNo, getSerialNo(),
									String.valueOf(++index), "", "C", loanCur,
									amortizeSumAcctAmt, currDate, lxsrAcctNo,
									lxsrAcctName, orgNo, lxsrAcctNo);
						} else {
							// 插入当日利息计提计提摊销会计分录
							// Dr 预收利息(摊销)/应收利息(计提) C*R1/Y
							// Cr 利息收入 C*R1/Y
							System.out.println("该笔为第一天进行利息计提摊销");
							log.debug("该笔为第一天进行利息计提摊销");
							// 插入利息计提摊销表数据
							insertInterestAccrualAmortize(loanBizNo, inttMode,
									currDate, realRate, interestDayAmt,
									fromAmortizeDate, toAccrualAmortizeDate,
									accrualAmortizeDays, loanBal,
									alreadyAccrualAmortizeSumAmt,
									amortizeSumAcctAmt,isOveDue);
							insertInterestAccrualAmortizeAcctInfo(loanBizNo,
									loanBizNo, getSerialNo(),
									String.valueOf(++index), "", "D", loanCur,
									interestDayAmt, currDate, ysyslxAcctNo,
									ysyslxAcctName, orgNo, ysyslxAcctNo);
							insertInterestAccrualAmortizeAcctInfo(loanBizNo,
									loanBizNo, getSerialNo(),
									String.valueOf(++index), "", "C", loanCur,
									interestDayAmt, currDate, lxsrAcctNo,
									lxsrAcctName, orgNo, lxsrAcctNo);
						}
						//20140508,更新已计提摊销金额
						updateBuLoanInfo(loanBizNo,amortizeSumAcctAmt);
					}
					// 逾期
					else {
						String isOveDue = "Y";//是否逾期
						ysyslxAcctName = "应收利息";
						ysyslxAcctNo = getAcctNo(orgNo,"ZA00000107",loanCur);
						double interestDayAmt = 0;
						Date fromAmortizeDate = null;
						Date toAccrualAmortizeDate = null;
						int accrualAmortizeDays = 0;
						HashMap map = getInterestAccrualAmortizeDayAmt(
								loanBizNo, loanCur, interestDate, loanBal,
								inttMode, realRate, floatRateType, floatPoint,
								currDate,isOveDue,matureDate,oveRate);
						interestDayAmt = Double.parseDouble(map.get(
								"interestDayAmt").toString());
						fromAmortizeDate = Date.valueOf(map.get(
								"fromAmortizeDate").toString());
						toAccrualAmortizeDate = Date.valueOf(map.get(
								"toAmortizeDate").toString());
						accrualAmortizeDays = Integer.parseInt(map.get(
								"amortizeDays").toString());
						Integer index = 0;
						// 本次计提摊销会计分录金额=当日计提摊销金额+会计分录冲销金额
						double amortizeSumAcctAmt = interestDayAmt;
						/**
						 * 根据逾期是否已处理过计提和摊销, 来判断是否为第一天计提摊销
						 */
						boolean isOveDueFlag = getLoanIsOveDue(loanBizNo);
						if (isOveDueFlag==true) {
							System.out.println("该笔为非第一天进行逾期利息计提摊销");
							log.debug("该笔为非第一天进行逾期利息计提摊销");
							// 插入利息计提摊销表数据
							insertInterestAccrualAmortize(loanBizNo, inttMode,
									currDate, oveRate, interestDayAmt,
									fromAmortizeDate, toAccrualAmortizeDate,
									accrualAmortizeDays, loanBal,
									alreadyAccrualAmortizeSumAmt,
									amortizeSumAcctAmt,isOveDue);
							// Dr 利息收入 loanAccrualAmortizeSumAmt
							// Cr 预收利息(摊销)/应收利息(计提) loanAccrualAmortizeSumAmt
							insertInterestAccrualAmortizeAcctInfo(loanBizNo,
									loanBizNo, getSerialNo(),
									String.valueOf(++index), "", "D", loanCur,
									alreadyAccrualAmortizeSumAmt, currDate,
									lxsrAcctNo, lxsrAcctName, orgNo, lxsrAcctNo);
							insertInterestAccrualAmortizeAcctInfo(loanBizNo,
									loanBizNo, getSerialNo(),
									String.valueOf(++index), "", "C", loanCur,
									alreadyAccrualAmortizeSumAmt, currDate,
									ysyslxAcctNo, ysyslxAcctName, orgNo,
									ysyslxAcctNo);
							// Dr 预收利息(摊销)/应收利息(计提)
							// C*R1/Y+loanAccrualAmortizeSumAmt
							// Cr 利息收入 C*R1/Y +loanAccrualAmortizeSumAmt
							insertInterestAccrualAmortizeAcctInfo(loanBizNo,
									loanBizNo, getSerialNo(),
									String.valueOf(++index), "", "D", loanCur,
									amortizeSumAcctAmt, currDate, ysyslxAcctNo,
									ysyslxAcctName, orgNo, ysyslxAcctNo);
							insertInterestAccrualAmortizeAcctInfo(loanBizNo,
									loanBizNo, getSerialNo(),
									String.valueOf(++index), "", "C", loanCur,
									amortizeSumAcctAmt, currDate, lxsrAcctNo,
									lxsrAcctName, orgNo, lxsrAcctNo);
						} else {
							// 插入当日利息计提计提摊销会计分录
							// Dr 预收利息(摊销)/应收利息(计提) C*R1/Y
							// Cr 利息收入 C*R1/Y
							System.out.println("该笔为第一天进行逾期利息计提摊销");
							log.debug("该笔为第一天进行逾期利息计提摊销");
							// 插入利息计提摊销表数据
							insertInterestAccrualAmortize(loanBizNo, inttMode,
									currDate, oveRate, interestDayAmt,
									fromAmortizeDate, toAccrualAmortizeDate,
									accrualAmortizeDays, loanBal,
									0,
									amortizeSumAcctAmt,isOveDue);
							insertInterestAccrualAmortizeAcctInfo(loanBizNo,
									loanBizNo, getSerialNo(),
									String.valueOf(++index), "", "D", loanCur,
									interestDayAmt, currDate, ysyslxAcctNo,
									ysyslxAcctName, orgNo, ysyslxAcctNo);
							insertInterestAccrualAmortizeAcctInfo(loanBizNo,
									loanBizNo, getSerialNo(),
									String.valueOf(++index), "", "C", loanCur,
									interestDayAmt, currDate, lxsrAcctNo,
									lxsrAcctName, orgNo, lxsrAcctNo);
						}
						
						//20140508,更新已计提摊销金额
						updateBuLoanInfo(loanBizNo,amortizeSumAcctAmt);
						// 利随本清:进行计提处理
						if ("0".equals(inttMode)) {
							ysyslxAcctName = "应收利息";
							ysyslxAcctNo = getAcctNo(orgNo,"ZA00000107",loanCur);
						}// 预收息:进行摊销处理
						else {
							ysyslxAcctName = "预收利息";
							ysyslxAcctNo = getAcctNo(orgNo,"ZA00000129",loanCur);
						}
					}
				}
			}
		} catch (EbillsException ex) {
			throw ex;
		} catch (Exception e) {
			throw new EbillsException(e, className, 2, null, null);
		}
	}
	
	public void NormalLoanProcess() throws EbillsException {
		
	}

	/**
	 * 获取该笔融资已计提/摊销总金额，取最新计提摊销日期的数据
	 * 
	 * @return
	 * @throws EbillsException
	 */
	public static double getAlreadyAccrualAmortizeSumAmtByLoanBizNo(
			String loanBizNo) throws EbillsException {
		double alreadyAccrualAmortizeSumAmt = 0;
		EbpDao gjDao = new EbpDao();
		String loanAccrualAmortizeSql = "select case when sum(amortizeamt) is null then 0 else sum(amortizeamt) end sumamt from InterestAmortize where loanbizNo =? "
				+"and amortizedate = (select max(amortizedate) from InterestAmortize where loanbizNo =?)";
		List<Map<String, Object>> loanAccrualAmortizeData = new LinkedList<Map<String, Object>>();
		List<Object> loanAccrualAmortizeList = new LinkedList<Object>();
		loanAccrualAmortizeList.add(loanBizNo);
		loanAccrualAmortizeList.add(loanBizNo);
		loanAccrualAmortizeData = gjDao.sqlQuery(loanAccrualAmortizeSql,
				loanAccrualAmortizeList);
		System.out.println("loanAccrualAmortizeData:"+loanAccrualAmortizeData);
		if (loanAccrualAmortizeData != null) {
			for (int y = 0; y < loanAccrualAmortizeData.size(); y++) {
				Map<String, Object> loanAccrualAmortizeInfo = loanAccrualAmortizeData
						.get(y);
				alreadyAccrualAmortizeSumAmt = Double
						.parseDouble(loanAccrualAmortizeInfo.get("SUMAMT") == null ? "0"
								: loanAccrualAmortizeInfo.get("SUMAMT")
										.toString());
			}
		}
		return alreadyAccrualAmortizeSumAmt;
	}

	/**
	 * 计算本次当天计提摊销金额
	 * 
	 * @return
	 * @throws EbillsException
	 */
	public static HashMap getInterestAccrualAmortizeDayAmt(String loanBizNo,
			String loanCur, java.sql.Date interestDate, double loanBal,
			String inttMode, double realRate, String floatRateType,
			double floatPoint, java.sql.Date currDate,String isOveDue,java.sql.Date matureDate,double oveRate) throws EbillsException {
		EbpDao gjDao = new EbpDao();
		double dayRate = 0;// 当日计算利息利率
		//Date fromAccrualAmortizeDate = currDate;// 本次开始计息日
		Date fromAccrualAmortizeDate = interestDate;// 本次开始计息日
		Date orverDueStartDate = null;//逾期开始计息日期
		if(isOveDue.equals("Y")){//如果逾期时, 则计提摊销从到期日后一天开始计息
			orverDueStartDate = calcDate(matureDate,1);
			fromAccrualAmortizeDate = orverDueStartDate;
		}
		Date toAccrualAmortizeDate = null;// 本次计息结束日
		int accrualAmortizeDays = 0; // 本次计息多少天
		int ccyInterDays = 0;// 币种计息天数,从币种参数中获取
		HashMap map = new HashMap();

		/**
		 * 从币种表中获取计息天数
		 */
		String sql = "select datedays from paccy where cursign = ?";
		List<Map<String, Object>> data = new LinkedList<Map<String, Object>>();
		List<Object> list = new LinkedList<Object>();
		list.add(loanCur);
		data = gjDao.sqlQuery(sql, list);
		if (data != null) {
			for (int y = 0; y < data.size(); y++) {
				Map<String, Object> info = data.get(y);
				ccyInterDays = Integer
						.parseInt((info.get("datedays") == null) ? "360" : info
								.get("datedays").toString());
			}
		}
		System.out.println(loanCur + "计息天数:" + ccyInterDays);
		log.debug(loanCur + "计息天数:" + ccyInterDays);

		/**
		 * 判断为固定利率或浮动利率
		 */
		if (floatRateType == null || "".equals(floatRateType)) {
			// 固定利率
			dayRate = realRate;
			if(isOveDue.equals("Y")){//逾期利率
				dayRate = oveRate;
			}
		} else {
			// 浮动利率:需要到参数表中获取当天浮动利率
			dayRate = getRateInfo(floatRateType, loanCur,
					String.valueOf(currDate));
			if(isOveDue.equals("Y")){//逾期利率
				dayRate = oveRate;
			}
		}

		/**
		 * 先判断到下一工作日有多少天,如果存在节假日，需要将节假日利息一并摊销 如果下一工作日为当月,则正常计息至下一工作日
		 * 如果下一工作日为下月,则正常计息至当月底
		 */
		// 先找到下一个工作日
		Date nextWorkDate = new java.sql.Date(HolidayInfo.getAfterManyDayDate(
				new java.util.Date(currDate.getTime()), 0, false, "HKD", null)
				.getTime());
		System.out.println("下一个工作日为  nextWorkDate:" + nextWorkDate);
		log.debug("下一个工作日为  nextWorkDate:" + nextWorkDate);
		int workDays = 0;
		// 需要先判断当日起与下一工作日是否为同一月份
		String currMonth = String.valueOf(currDate).substring(0, 4);
		String workMonth = String.valueOf(nextWorkDate).substring(0, 4);
		// 如果下一工作日为当月
		if (currMonth.equals(workMonth)) {
			// 计算当前日期与下一工作日有多少天
			workDays = getIntervalDays(currDate, nextWorkDate)-1;
		}// 如果下一工作日为下月
		else {
			// 先得出当前月份最后一天
			Calendar cal = Calendar.getInstance();
			cal.setTime(Date.valueOf(workMonth + "-01"));
			cal.add(Calendar.DATE, -1); // 再减一天即为上个月最后一天
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String s = df.format(cal.getTime());
			// 计算当前日期到月份最后一天有多少天,则为当日计息天数
			workDays = getIntervalDays(currDate, Date.valueOf(s));
		}
		System.out.println("当前日期:" + currDate + " 下一工作日期:" + nextWorkDate);
		log.debug("当前日期:" + currDate + " 下一工作日期:" + nextWorkDate);
		System.out.println("本次计息天数,包括至下一次工作日天数 :" + workDays);
		log.debug("本次计息天数,包括至下一次工作日天数 :" + workDays);
		
		System.out.println("matureDate="+matureDate+ "currDate:"+currDate);
		/**
		 * 本次计提摊销到哪一天
		 */
		if(matureDate.getTime()==currDate.getTime()){
			System.out.println("融资到期日与当前日期一致!");
			//如果融资到期日与当前计提摊销日一致，则只需to当天,也不需要计算后续的工作日
			toAccrualAmortizeDate = currDate;
			workDays = 0;
		}else{
			// 本次计息至哪一天
			toAccrualAmortizeDate = calcDate(nextWorkDate, -1);
			//toAccrualAmortizeDate = nextWorkDate;
			System.out.println("本次计息至哪一天:" + toAccrualAmortizeDate);
			log.debug("本次计息至哪一天:" + toAccrualAmortizeDate);
		}
		ImpCommons.PARAM_PATH = "D:\\NewProduct\\EBP\\WebContent\\xmlcfg\\";

		/**
		 * 获取上一次计提摊销日期至当天有多少天需要计息
		 */
//		Date lastToAccrualAmortizeDate = getToAccrualAmortizeDateByLoanBizNo(loanBizNo);
//		System.out.println("上一次计提摊销日期:" + lastToAccrualAmortizeDate);
//		log.debug("上一次计提摊销日期:" + lastToAccrualAmortizeDate);
//		int ldays = 0;
//		if (lastToAccrualAmortizeDate != null) {
//			System.out.println("上一次已计提摊销日期:" + lastToAccrualAmortizeDate);
//			log.debug("上一次已计提摊销日期:" + lastToAccrualAmortizeDate);
//			//ldays = getIntervalDays(lastToAccrualAmortizeDate, currDate) - 1;
//			//20140419
//			ldays = getIntervalDays(lastToAccrualAmortizeDate, currDate);
//			System.out.println("上一次已计提摊销日期至当天有多少天需要计息:" + ldays);
//			log.debug("上一次已计提摊销日期至当天有多少天需要计息:" + ldays);
//			//20140419
//			fromAccrualAmortizeDate = interestDate;// 从起息日开始计息
//		} else {
//			// 如果之前没有做过计提摊销,则计算起息日至当前日期有多少天需要计息的
//			ldays = getIntervalDays(interestDate, currDate);
//			fromAccrualAmortizeDate = interestDate;// 从起息日开始计息
//			System.out.println("该笔融资还未做过计息处理,需要从起息日至当日补充计息天数为:" + ldays);
//			log.debug("该笔融资还未做过计息处理,需要从起息日至当日补充计息天数为:" + ldays);
//		}

		/*
		 * // 获取上一次计提摊销日期至当天有多少天 java.sql.Date lastDate =
		 * getLastAmortizeDate(loanBizNo); // 如果为空，则暂时先默认为当天开始(应该为起息日开始) if
		 * (lastDate == null) { lastDate = currDate; } //
		 * 如果当期日期大于上一次计提谈笑日,需要计算中间日期是否需要计息,需排除节假日 int ldays = 0; if
		 * (currDate.getTime() > lastDate.getTime()) { Date lastNextWorkDate =
		 * new java.sql.Date(holidayInfo .getAfterManyDayDate( new
		 * java.util.Date(lastDate.getTime()), 0, false, "HKD",
		 * null).getTime()); System.out.println("上一次计提摊销日期的下一工作日:" +
		 * lastNextWorkDate); if (currDate.getTime() >
		 * lastNextWorkDate.getTime()) { ldays =
		 * getIntervalDays(lastNextWorkDate, lastDate); if (ldays < 0) { ldays =
		 * 0; } } System.out.println("上一次计提摊销日期至当天有多少天没有计提摊销(排除节假日) :" + ldays);
		 * }
		 */

		/**
		 * 开始利息计算处理
		 */
		//20140419:从起息日至当日的天数为当天的总计息天数
	    int loanDays = 0;
	    if(isOveDue.equals("N")){//正常计息
	    	loanDays = getIntervalDays(interestDate, currDate)+1;
	    	System.out.println("融资起息日:"+interestDate+" 当前日期:"+currDate+ " 相隔天数"+loanDays);
	 		log.debug("融资起息日:"+interestDate+" 当前日期:"+currDate+ " 相隔天数"+loanDays);
		}else{//如果逾期时, 则计提摊销从到期日后开始计息
			loanDays = getIntervalDays(orverDueStartDate, currDate)+1;
			System.out.println("逾期融资起息日:"+orverDueStartDate+" 当前日期:"+currDate+ " 相隔天数"+loanDays);
	 		log.debug("融资起息日:"+interestDate+" 当前日期:"+currDate+ " 相隔天数"+loanDays);
		}
	   
		accrualAmortizeDays = loanDays + workDays;// 当日至下一工作日计息天数+上一次计息日至当日天数
		System.out.println("当天需计提摊销总天数:" + (accrualAmortizeDays));
		log.debug("当天需计提摊销总天数:" + (accrualAmortizeDays));
		double interestDayAmt = 0;
		interestDayAmt = roundFormat(loanBal * (accrualAmortizeDays) * dayRate
				/ ccyInterDays, 2);
		System.out.println("当天计提摊销金额:" + interestDayAmt);
		log.debug("当天计提摊销金额:" + interestDayAmt);
		
		map.put("interestDayAmt", interestDayAmt);
		map.put("fromAmortizeDate", fromAccrualAmortizeDate);
		map.put("toAmortizeDate", toAccrualAmortizeDate);
		map.put("amortizeDays", accrualAmortizeDays);
		return map;
	}

	/**
	 * 插入利息计提摊销主业务表
	 * 
	 * @throws EbillsException
	 */
	public static void insertInterestAccrualAmortize(String loanBizNo,
			String flag, java.sql.Date amortizeDate, double amortizeRate,
			double interestDayAmt, java.sql.Date fromAmortizeDate,
			java.sql.Date toAmortizeDate, int amortizeDays, double loanBalAmt,
			double alreadyAmortizeSumAmt, double amortizeSumAcctAmt,String isOveDue)
			throws EbillsException {
		EbpDao gjDao = new EbpDao();
		List<Map<String, Object>> insertTmpList = new LinkedList<Map<String, Object>>();
		Map<String, Object> insertMap = new HashMap<String, Object>();
		insertMap.put("loanBizNo", loanBizNo);
		insertMap.put("flag", flag);
		insertMap.put("amortizeDate", amortizeDate);
		insertMap.put("amortizeRate", amortizeRate);
		insertMap.put("amortizeAmt", interestDayAmt);
		insertMap.put("fromAmortizeDate", fromAmortizeDate);
		insertMap.put("toAmortizeDate", toAmortizeDate);
		insertMap.put("amortizeDays", amortizeDays);
		insertMap.put("loanBalAmt", loanBalAmt);
		insertMap.put("writeOffAcctAmt", alreadyAmortizeSumAmt);
		insertMap.put("amortizeSumAcctAmt", amortizeSumAcctAmt);
		insertMap.put("isOveDue", isOveDue);
		insertTmpList.add(insertMap);
		System.out.println("插入计提摊销主档 insertMap:" + insertMap);
		log.debug("插入计提摊销主档 insertMap:" + insertMap);
		gjDao.insertRow(interestAmortize, "", insertTmpList);
	}

	/**
	 * 插入利息计提摊销会计
	 * 
	 * @throws EbillsException
	 */
	public static void insertInterestAccrualAmortizeAcctInfo(String txnNo,
			String bizNo, String accVouNo, String indexNo, String accVouName,
			String dcFlag, String ccy, double interestDayAmt,
			java.sql.Date valueDate, String acctNo, String acctNoName,
			String orgNo, String subject) throws EbillsException {
		EbpDao gjDao = new EbpDao();
		List<Map<String, Object>> insertTmpAcctList = new LinkedList<Map<String, Object>>();
		Map<String, Object> insertAcctMap = new HashMap<String, Object>();
		insertAcctMap.put("txnNo", txnNo);
		insertAcctMap.put("bizNo", bizNo);
		insertAcctMap.put("accVouNo", accVouNo);
		insertAcctMap.put("indexNo", indexNo);
		insertAcctMap.put("accVouName", accVouName);
		insertAcctMap.put("dcFlag", dcFlag);
		insertAcctMap.put("ccy", ccy);
		insertAcctMap.put("amount", interestDayAmt);
		insertAcctMap.put("valueDate", valueDate);
		insertAcctMap.put("acctNo", acctNo);
		insertAcctMap.put("acctNoName", acctNoName);
		insertAcctMap.put("orgNo", orgNo);
		insertAcctMap.put("subject", subject);
		//计算本币折算金额
		HashMap map = getLocalCcyAmt(ccy,interestDayAmt);
		double localRate = Double.parseDouble(map.get("localRate").toString());
		double localAmt = Double.parseDouble(map.get("localAmt").toString());
		insertAcctMap.put("localAmount",localAmt);
		insertAcctMap.put("buyPrice",localRate);
		insertTmpAcctList.add(insertAcctMap);
		System.out.println("插入计提摊销会计分录 insertAcctMap:" + insertAcctMap);
		log.debug("插入计提摊销会计分录 insertAcctMap:" + insertAcctMap);
		gjDao.insertRow(interestAmortizeAcctInfo, "", insertTmpAcctList);
	}

	/**
	 * 根据日期删除计提摊销数据
	 * 
	 * @param date
	 * @throws EbillsException
	 */
	public static void deleteAccrualAmortizeByDate(String loanBizNo,
			java.sql.Date date) throws EbillsException {
		EbpDao gjDao = new EbpDao();
		// 删除计提摊销主档记录
		Map<String, Object> delIntAMap = new HashMap<String, Object>();
		delIntAMap.put("loanBizNo", loanBizNo);
		delIntAMap.put("amortizeDate", date);
		gjDao.deleteRow(interestAmortize, "", delIntAMap);
		// 删除计提摊销分录记录
		Map<String, Object> delIntACCMap = new HashMap<String, Object>();
		delIntACCMap.put("bizNo", loanBizNo);
		delIntACCMap.put("valueDate", date);
		gjDao.deleteRow(interestAmortizeAcctInfo, "", delIntACCMap);
	}

	/**
	 * 根据融资编号获取上一次计提摊销日期
	 * 
	 * @param loanBizNo
	 * @return
	 * @throws EbillsException
	 */
	public static Date getLastAccrualAmortizeDate(String loanBizNo)
			throws EbillsException {
		String sql = "select max(amortizedate) amortizedate from InterestAmortize where loanbizNo =?";
		EbpDao gjDao = new EbpDao();
		java.sql.Date lastDate = null;
		List<Map<String, Object>> dataMap = new LinkedList<Map<String, Object>>();
		List<Object> list = new LinkedList<Object>();
		list.add(loanBizNo);
		dataMap = gjDao.queryBySql(sql, null, list);
		if (dataMap != null) {
			for (int y = 0; y < dataMap.size(); y++) {
				Map<String, Object> mapInfo = dataMap.get(y);
				if (mapInfo.get("amortizedate") != null) {
					lastDate = Date.valueOf(mapInfo.get("amortizedate")
							.toString());
				}
			}
		}
		return lastDate;
	}

	/**
	 * 根据融资编号获取最新已计息至哪一天
	 * 
	 * @param loanBizNo
	 * @return
	 * @throws EbillsException
	 */
	public static Date getToAccrualAmortizeDateByLoanBizNo(String loanBizNo)
			throws EbillsException {
		String sql = "select max(toAmortizeDate) toAmortizeDate from InterestAmortize where loanbizNo =?";
		EbpDao gjDao = new EbpDao();
		java.sql.Date toAmortizeDate = null;
		List<Map<String, Object>> dataMap = new LinkedList<Map<String, Object>>();
		List<Object> list = new LinkedList<Object>();
		list.add(loanBizNo);
		dataMap = gjDao.queryBySql(sql, null, list);
		if (dataMap != null) {
			for (int y = 0; y < dataMap.size(); y++) {
				Map<String, Object> mapInfo = dataMap.get(y);
				if (mapInfo.get("toAmortizeDate") != null) {
					toAmortizeDate = Date.valueOf(mapInfo.get("toAmortizeDate")
							.toString());
				}
			}
		}
		return toAmortizeDate;
	}

	/**
	 * 根据利率类型、币种和日期获取当天浮动利率
	 * 
	 * @param rateType
	 * @param date
	 * @return
	 * @throws EbillsExcepton
	 */
	public static double getRateInfo(String rateType, String cur, String date)
			throws EbillsException {
		String sql1 = "select intrtpa from parate where ratetyp = ? and ccycode = ? "
				+ "and effdate=to_date(?,'yyyy-MM-dd') and to_char(exprydt,'YYYY-MM-DD') = '9999-12-12'";
		String sql2 = "SELECT intrtpa from parate WHERE ratetyp = ? and ccycode = ? "
				+ "AND to_char (exprydt, 'YYYY-MM-DD') <> '9999-12-12' "
				+ "AND  TRUNC(to_date (?, 'YYYY-MM-DD'))- TRUNC(EFFDATE)>=0 "
				+ "AND  TRUNC(to_date (?, 'YYYY-MM-DD'))- TRUNC(EXPRYDT)<=0 ";
		EbpDao gjDao = new EbpDao();
		double intrtpa = 0;
		List<Map<String, Object>> dataMap = new LinkedList<Map<String, Object>>();
		List<Object> list = new LinkedList<Object>();
		list.add(rateType);
		list.add(cur);
		list.add(date);
		dataMap = gjDao.queryBySql(sql1, null, list);
		if (dataMap != null && dataMap.size() > 0) {
			for (int y = 0; y < dataMap.size(); y++) {
				Map<String, Object> mapInfo = dataMap.get(y);
				if (mapInfo.get("intrtpa") != null) {
					if (mapInfo.get("intrtpa") != null) {
						intrtpa = Double.parseDouble(mapInfo.get("intrtpa")
								.toString());
					}
				}
			}
		} else {
			List<Map<String, Object>> dataMapEx = new LinkedList<Map<String, Object>>();
			List<Object> listEx = new LinkedList<Object>();
			listEx.add(rateType);
			listEx.add(cur);
			listEx.add(date);
			listEx.add(date);
			dataMapEx = gjDao.queryBySql(sql2, null, listEx);
			if (dataMapEx != null) {
				for (int i = 0; i < dataMapEx.size(); i++) {
					Map<String, Object> mapInfoEx = dataMapEx.get(i);
					if (mapInfoEx.get("intrtpa") != null) {
						intrtpa = Double.parseDouble(mapInfoEx.get("intrtpa")
								.toString());
					}
				}
			}
		}
		return intrtpa;
	}

	/**
	 * 根据账务类型和币种获取账号
	 * 
	 * @param acctTypeNo
	 * @param cur
	 * @param orgNo
	 * @return
	 * @throws EbillsException
	 */
	public static String getAcctNo(String orgNo, String acctTypeNo, String cur)
			throws EbillsException {
		String acctNo = "";
		String sql = "select acctno from paaccfo where acctorgno=? and acctTypeNo = ? and cursign = ?";
		EbpDao gjDao = new EbpDao();
		List<Map<String, Object>> dataMap = new LinkedList<Map<String, Object>>();
		List<Object> list = new LinkedList<Object>();
		list.add(orgNo);
		list.add(acctTypeNo);
		list.add(cur);
		dataMap = gjDao.queryBySql(sql, null, list);
		if (dataMap != null && dataMap.size() > 0) {
			for (int y = 0; y < dataMap.size(); y++) {
				Map<String, Object> mapInfo = dataMap.get(y);
				if (mapInfo.get("acctno") != null) {
					if (mapInfo.get("acctno") != null) {
						acctNo = mapInfo.get("acctno").toString();
					}
				}
			}
		}
		return acctNo;
	}
	
	/**
	 * 根据融资编号和还款日期获取融资归还信息
	 * @param loanBizNo
	 * @throws EbillsException
	 */
	public static double getLoanRepayInfo(String loanBizNo, java.sql.Date repayDate) throws EbillsException {
		double payInttAmt = 0;
		String sql = "select payInttAmt from burepayloanar where loanbizno=? and paydate=to_date(?,'yyyy-MM-dd')";
		EbpDao gjDao = new EbpDao();
		
		List<Map<String, Object>> dataMap = new LinkedList<Map<String, Object>>();
		List<Object> list = new LinkedList<Object>();
		list.add(loanBizNo);
		list.add(repayDate);

		dataMap = gjDao.queryBySql(sql, null, list);
		if (dataMap != null && dataMap.size() > 0) {
			for (int y = 0; y < dataMap.size(); y++) {
				Map<String, Object> mapInfo = dataMap.get(y);
				if (mapInfo.get("payInttAmt") != null) {
					if (mapInfo.get("payInttAmt") != null) {
						payInttAmt = Double.parseDouble(mapInfo.get("payInttAmt").toString());
					}
				}
			}
		}
		
		return payInttAmt;
	}
	
	/**
	 * 用于判断该笔融资是否处理过逾期的计提与摊销
	 * @param loanBizNo
	 * @return
	 * @throws EbillsException
	 */
	public static boolean getLoanIsOveDue(String loanBizNo) throws EbillsException {
		boolean isOveDue = false;
		String sql = "select isOveDue from interestamortize where loanBizNo = ? and isOveDue = 'Y'";
		EbpDao gjDao = new EbpDao();
		
		List<Map<String, Object>> dataMap = new LinkedList<Map<String, Object>>();
		List<Object> list = new LinkedList<Object>();
		list.add(loanBizNo);

		dataMap = gjDao.queryBySql(sql, null, list);
		if (dataMap != null && dataMap.size() > 0) {
			for (int y = 0; y < dataMap.size(); y++) {
				Map<String, Object> mapInfo = dataMap.get(y);
				if (mapInfo.get("isOveDue") != null) {
					if ("Y".equals(mapInfo.get("isOveDue"))) {
						isOveDue = true;
					}
				}
			}
		}
		return isOveDue;
	}
	
	/**
	 * 折算本币金额
	 * @param cur
	 * @param amt
	 * @return
	 * @throws EbillsException
	 */
	public static HashMap getLocalCcyAmt(String cur,double amt) throws EbillsException {
       double localRate = 0;//折算汇率
		HashMap map = new HashMap();
		String sql = "select closingRate from paccypri where cursign = ?";
        EbpDao gjDao = new EbpDao();
		
		List<Map<String, Object>> dataMap = new LinkedList<Map<String, Object>>();
		List<Object> list = new LinkedList<Object>();
		list.add(cur);
		
		dataMap = gjDao.queryBySql(sql, null, list);
		if (dataMap != null && dataMap.size() > 0) {
			for (int y = 0; y < dataMap.size(); y++) {
				Map<String, Object> mapInfo = dataMap.get(y);
				if (mapInfo.get("closingRate") != null) {
                    localRate = Double.parseDouble(mapInfo.get("closingRate").toString());
				}
			}
		}
		map.put("localRate", localRate);
		map.put("localAmt", roundFormat(amt*localRate,2));
		return map;
	}
	
	/**
	 * 根据融资编号更新贷款表已计提摊销金额
	 * @param loanBizNo
	 * @return
	 * @throws EbillsException
	 */
	public static void updateBuLoanInfo(String loanBizNo,double duemIntrAmt) throws EbillsException {
		EbpDao gjDao = new EbpDao();
		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("loanBizNo", loanBizNo);
		System.out.println("准备更新贷款表记录:"+loanBizNo + " 更新的计提摊澥金额:"+duemIntrAmt);
		List<Map<String, Object>> datalist = getBuloanInfoByLoanBizNo(loanBizNo);
		for (int i = 0; i < datalist.size(); i++) {
			Map<String, Object> data = datalist.get(i);
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("loanBizNo", loanBizNo);
			data.put("duemIntrAmt", duemIntrAmt);
			gjDao.updateByDataId(GeneralConstants.BULOAN, EbpConstants.TABLE_INFO, data, param);
			//gjDao.insertRow(GeneralConstants.BULOAN, GjjsConstants.TABLE_AR, data);
		}
	}
	
	
	public static List<Map<String, Object>> getBuloanInfoByLoanBizNo(
			String loanBizNo) throws EbillsException {
		EbpDao gjDao = new EbpDao();
		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("loanBizNo", loanBizNo);
		return gjDao.queryByDataId(GeneralConstants.BULOAN,
				EbpConstants.TABLE_INFO, mapParam);
	}

	/**
	 * 生成会计分录流水
	 * 
	 * @return
	 * @throws EbillsException
	 */
	private static String getSerialNo() throws EbillsException {
		SerialNoFactory snf = new SerialNoFactory();
		DataInfo di = DataFactory.getDataInfoFile(interestAmortizeAcctInfo);
		String serialNo = snf.getSerialNo(di.getTablename(), 10);
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		String ymd = Integer.toString(year * 10000 + month * 100 + day);
		return "IA" + ymd + serialNo;
	}

	/**
	 * 数字进行格式化，四舍五入
	 * 
	 * @param inNum
	 * @param pLen
	 *            保留小数位长度
	 * */
	public static double roundFormat(double inNum, int pLen) {
		BigDecimal big = new BigDecimal(inNum);
		big = big.divide(new BigDecimal(1), pLen, BigDecimal.ROUND_HALF_UP);
		return big.doubleValue();
	}

	/**
	 * 计算两个日期之间的间隔日期
	 */
	public static int getIntervalDays(Date startDate, Date endDate) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar cal1 = new GregorianCalendar(), cal2 = new GregorianCalendar(), calTmp = new GregorianCalendar();
		cal1.setTime(startDate);
		cal2.setTime(endDate);
		int flag = 1;
		if (cal2.getTimeInMillis() < cal1.getTimeInMillis()) {
			java.util.Date dt = cal2.getTime();
			cal2.setTime(cal1.getTime());
			cal1.setTime(dt);
			flag = -1;
		}
		int year1 = cal1.get(GregorianCalendar.YEAR), year2 = cal2
				.get(GregorianCalendar.YEAR), days = year2 - year1;
		String firstday = "-01-01", endday = "-12-31";
		while (year2 - year1 > 0) {
			try {
				calTmp.setTime(df.parse(String.valueOf(year1) + endday));
				days += calTmp.get(GregorianCalendar.DAY_OF_YEAR)
						- cal1.get(GregorianCalendar.DAY_OF_YEAR);
				cal1.setTime(df.parse(String.valueOf(++year1) + firstday));
			} catch (ParseException e) {
				e.printStackTrace();
				return 0;
			}
		}
		days += cal2.get(GregorianCalendar.DAY_OF_YEAR)
				- cal1.get(GregorianCalendar.DAY_OF_YEAR);
		days = days * flag;
		if (days == -0)
			days = 0;
		return days;
	}

	/**
	 * 计算一个日期加上天数后的日期
	 * 
	 * @param theDay
	 * @param addDays
	 */
	public static Date calcDate(Date theDay, int addDays) {
		if (theDay == null)
			return null;
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(theDay);

		cal.add(GregorianCalendar.DAY_OF_YEAR, addDays);
		Date newDay = new Date(cal.getTimeInMillis());
		newDay.setTime(cal.getTimeInMillis());
		return newDay;
	}

}
