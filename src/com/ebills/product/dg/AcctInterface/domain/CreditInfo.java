package com.ebills.product.dg.AcctInterface.domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class CreditInfo {
	/** 流水号-16（允许为空） */
	private String txnSerialNo;
	/** 出账流水号-18(主键) */
	private String outAcctSerialNo;
	/** 合同号-18 */
	private String constractNo;
	/** 申请机构号-10 */
	private String appOrgNo;
	/** -核心客户号-21 */
	private String coreCorpNo;
	/** 客户名称-256 */
	private String corpName;
	/** 业务品种-6 */
	private String tradeType;
	/** 申请币种-3 */
	private String curSign;
	/** 申请金额-（15，2） */
	private double amt;
	/** 信用证号码-16 */
	private String creditNo;
	/** 业务号码-16 */
	private String bizNo;
	/** 出账账号 */
	private String loanAcctNo;
	/** 入账账号-32 */
	private String inAcctNo;
	/** 年利率-（15，6） */
	private double yearRate;
	/** 逾期利率-（15，6） */
	private double overRate;
	/** 起息日-10 */
	private Date interetDate;
	/** 到期日-10 */
	private Date matureDate;
	/** 票据币种-3 */
	private String billCur;
	/** 票据金额(买方信用限额)-（15，2） */
	private double billAmt;
	/** 票据承兑日期（限额生效日期）-10 */
	private Date billAcceptDate;
	/** 承兑日期（限额到期日期）-10 */
	private Date acceptDate;
	/** 预扣费用-（15，2） */
	private double preCharge;
	/** 品种种类-6 */
	private String tradeKind;
	/** 即远期标志-1 */
	private String flag;
	/** 保单号-16 */
	private String guarNo;
	/** 赔款转让协议号-16 */
	private String indemNityNo;
	/** 备注-256 */
	private String memo;
	/** 保证金比例-3 */
	private int depositPer;
	/** 份数-2 */
	private int times;
	private String depositstr1;
	private String depositstr2;
	private String depositstr3;
	
	/** 信用证合同号 */
	private String lcConstractNo;
	/** 合同金额 */
	private double constractNoAmt;
	/** 是否投保-1 */
	private String isGuar;
	
	/**合同流水号*/
	private String contractSerialNo;
    
    /**贷款期限（月）*/
    private int termMonth;
    /**零（天）*/
    private int termDay;
    /**利率浮动幅度*/
    private double rateFloat;
    /**利率调整方式*/
    private String adjustRateType;
    /**还款方式*/
    private String corpusPayMethod;
    /**核心科目*/
    private String subjectNo;
    /**保证金帐号*/
    private String bailAccount;
    
    private String bailAccount1;
    
    private String mainProNo;
    private String dzctype;//代转存表示
    private String dzcAcctNo;//贷转存账号
    
    /**该笔记录是否已由信贷取消*/
    private String isQx = "N";
	/**信贷对应的业务品种 */
	private String xdTradeType;
	
	private String duebillNo;//核心要求送此字段借据印刷号
	
	private String creditorgNo;
	
	public String getLcConstractNo() {
		return lcConstractNo;
	}
	public void setLcConstractNo(String lcConstractNo) {
		this.lcConstractNo = lcConstractNo;
	}
	public double getConstractNoAmt() {
		return constractNoAmt;
	}
	public void setConstractNoAmt(double constractNoAmt) {
		this.constractNoAmt = constractNoAmt;
	}
	public List getDepositData(){
		List retList = new ArrayList();
		if (depositstr1!=null && depositstr1.length()>=50){
			String[] deposit1=new String[4];
			deposit1[0]=depositstr1.substring(0,32).trim();//帐号
			deposit1[1]=depositstr1.substring(32,35);//币种
			deposit1[2]=depositstr1.substring(35,50).trim();//金额
			deposit1[3]=depositstr1.substring(50).trim();//销帐编号
			retList.add(deposit1);
		}
		if(depositstr1!=null && depositstr1.length()>66){
			
		}
		if (depositstr2!=null && depositstr2.length()>=50){
			String[] deposit2=new String[4];
			deposit2[0]=depositstr2.substring(0,32).trim();
			deposit2[1]=depositstr2.substring(32,35);
			deposit2[2]=depositstr2.substring(35,50).trim();
			deposit2[3]=depositstr2.substring(50).trim();
			retList.add(deposit2);
		}
		if (depositstr3!=null && depositstr3.length()>=50){
			String[] deposit3=new String[4];
			deposit3[0]=depositstr3.substring(0,32).trim();
			deposit3[1]=depositstr3.substring(32,35);
			deposit3[2]=depositstr3.substring(35,50).trim();
			deposit3[3]=depositstr3.substring(50).trim();
			retList.add(deposit3);
		}
		return retList;
	}
	/**
	 * 取得发票信息
	 * @return
	 */
	public List getInvoiceData(){
		List strList = new ArrayList();
		String[] Invoice=new String[4];
		if (depositstr1!=null && depositstr1.length()>=16){
			for(int i=1;i<=times;i++){
				Invoice = new String[4];
				Invoice[0]=depositstr1.substring((0+(i-1)*50),(16+50*(i-1))).trim();
				Invoice[1]=depositstr1.substring((16+50*(i-1)), (19+50*(i-1)));
				Invoice[2]=depositstr1.substring((19+50*(i-1)),(34+50*(i-1))).trim();
				if(i==times){
					Invoice[3]=depositstr1.substring((34+50*(i-1))).trim();
				}else{
					Invoice[3]=depositstr1.substring((34+50*(i-1)),50+50*(i-1)).trim();
				}
				strList.add(Invoice);
			}	
		}
		return  strList;	
	}

	

	public String getIsGuar() {
		return isGuar;
	}

	public void setIsGuar(String isGuar) {
		this.isGuar = isGuar;
	}

	public String getOutAcctSerialNo() {
		return outAcctSerialNo;
	}

	public void setOutAcctSerialNo(String outAcctSerialNo) {
		this.outAcctSerialNo = outAcctSerialNo;
	}

	public String getConstractNo() {
		return constractNo;
	}

	public void setConstractNo(String constractNo) {
		this.constractNo = constractNo;
	}

	public String getAppOrgNo() {
		return appOrgNo;
	}

	public void setAppOrgNo(String appOrgNo) {
		this.appOrgNo = appOrgNo;
	}

	public String getCoreCorpNo() {
		return coreCorpNo;
	}

	public void setCoreCorpNo(String coreCorpNo) {
		this.coreCorpNo = coreCorpNo;
	}

	public String getCorpName() {
		return corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getCurSign() {
		return curSign;
	}

	public void setCurSign(String curSign) {
		this.curSign = curSign;
	}

	public double getAmt() {
		return amt;
	}

	public void setAmt(double amt) {
		this.amt = amt;
	}

	public String getCreditNo() {
		return creditNo;
	}

	public void setCreditNo(String creditNo) {
		this.creditNo = creditNo;
	}

	public String getBizNo() {
		return bizNo;
	}

	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}

	public String getLoanAcctNo() {
		return loanAcctNo;
	}

	public void setLoanAcctNo(String loanAcctNo) {
		this.loanAcctNo = loanAcctNo;
	}

	public String getInAcctNo() {
		return inAcctNo;
	}

	public void setInAcctNo(String inAcctNo) {
		this.inAcctNo = inAcctNo;
	}

	public double getYearRate() {
		return yearRate;
	}

	public void setYearRate(double yearRate) {
		this.yearRate = yearRate;
	}

	public double getOverRate() {
		return overRate;
	}

	public void setOverRate(double overRate) {
		this.overRate = overRate;
	}

	public Date getInteretDate() {
		return interetDate;
	}

	public void setInteretDate(Date interetDate) {
		this.interetDate = interetDate;
	}

	public Date getMatureDate() {
		return matureDate;
	}

	public void setMatureDate(Date matureDate) {
		this.matureDate = matureDate;
	}

	public String getBillCur() {
		return billCur;
	}

	public void setBillCur(String billCur) {
		this.billCur = billCur;
	}

	public double getBillAmt() {
		return billAmt;
	}

	public void setBillAmt(double billAmt) {
		this.billAmt = billAmt;
	}

	public Date getBillAcceptDate() {
		return billAcceptDate;
	}

	public void setBillAcceptDate(Date billAcceptDate) {
		this.billAcceptDate = billAcceptDate;
	}

	public Date getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(Date acceptDate) {
		this.acceptDate = acceptDate;
	}

	public double getPreCharge() {
		return preCharge;
	}

	public void setPreCharge(double preCharge) {
		this.preCharge = preCharge;
	}

	public String getTradeKind() {
		return tradeKind;
	}

	public void setTradeKind(String tradeKind) {
		this.tradeKind = tradeKind;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getGuarNo() {
		return guarNo;
	}

	public void setGuarNo(String guarNo) {
		this.guarNo = guarNo;
	}

	public String getIndemNityNo() {
		return indemNityNo;
	}

	public void setIndemNityNo(String indemNityNo) {
		this.indemNityNo = indemNityNo;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getTxnSerialNo() {
		return txnSerialNo;
	}

	public void setTxnSerialNo(String txnSerialNo) {
		this.txnSerialNo = txnSerialNo;
	}

	public int getDepositPer() {
		return depositPer;
	}

	public void setDepositPer(int depositPer) {
		this.depositPer = depositPer;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}


	public String getDepositstr1() {
		return depositstr1;
	}

	public void setDepositstr1(String depositstr1) {
		this.depositstr1 = depositstr1;
	}

	public String getDepositstr2() {
		return depositstr2;
	}

	public void setDepositstr2(String depositstr2) {
		this.depositstr2 = depositstr2;
	}

	public String getDepositstr3() {
		return depositstr3;
	}

	public void setDepositstr3(String depositstr3) {
		this.depositstr3 = depositstr3;
	}
	public String getAdjustRateType() {
		return adjustRateType;
	}
	public void setAdjustRateType(String adjustRateType) {
		this.adjustRateType = adjustRateType;
	}
	public String getBailAccount() {
		return bailAccount;
	}
	public void setBailAccount(String bailAccount) {
		this.bailAccount = bailAccount;
	}
	public String getContractSerialNo() {
		return contractSerialNo;
	}
	public void setContractSerialNo(String contractSerialNo) {
		this.contractSerialNo = contractSerialNo;
	}
	public String getCorpusPayMethod() {
		return corpusPayMethod;
	}
	public void setCorpusPayMethod(String corpusPayMethod) {
		this.corpusPayMethod = corpusPayMethod;
	}
	public double getRateFloat() {
		return rateFloat;
	}
	public void setRateFloat(double rateFloat) {
		this.rateFloat = rateFloat;
	}
	public String getSubjectNo() {
		return subjectNo;
	}
	public void setSubjectNo(String subjectNo) {
		this.subjectNo = subjectNo;
	}
	public int getTermDay() {
		return termDay;
	}
	public void setTermDay(int termDay) {
		this.termDay = termDay;
	}
	public int getTermMonth() {
		return termMonth;
	}
	public void setTermMonth(int termMonth) {
		this.termMonth = termMonth;
	}
	public String getIsQx() {
		return isQx;
	}
	public void setIsQx(String isQx) {
		this.isQx = isQx;
	}
	public String getXdTradeType() {
		return xdTradeType;
	}
	public void setXdTradeType(String xdTradeType) {
		this.xdTradeType = xdTradeType;
	}
	public String getDuebillNo() {
		return duebillNo;
	}
	public void setDuebillNo(String duebillNo) {
		this.duebillNo = duebillNo;
	}
	public String getMainProNo() {
		return mainProNo;
	}
	public void setMainProNo(String mainProNo) {
		this.mainProNo = mainProNo;
	}
	public String getDzctype() {
		return dzctype;
	}
	public void setDzctype(String dzctype) {
		this.dzctype = dzctype;
	}
	public String getDzcAcctNo() {
		return dzcAcctNo;
	}
	public void setDzcAcctNo(String dzcAcctNo) {
		this.dzcAcctNo = dzcAcctNo;
	}
	public String getCreditorgNo() {
		return creditorgNo;
	}
	public void setCreditorgNo(String creditorgNo) {
		this.creditorgNo = creditorgNo;
	}
	public String getBailAccount1() {
		return bailAccount1;
	}
	public void setBailAccount1(String bailAccount1) {
		this.bailAccount1 = bailAccount1;
	}

	
}