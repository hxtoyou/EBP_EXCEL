package com.ebills.product.dg.AcctInterface.domain;
import java.sql.Date;

/**
 * 贷款信息 对应表:BULOANS
 * 
 * @author majiangbao 2014-07-12
 * 
 */
public class LoanInfo{
	/** 交易流水号 */
	private String txnSerialNo;

	/** 前交易流水号 */
	private String oldTxnSerialNo;

	/** 业务流水号 */
	private String bizNo;

	/** 交易类型号 */
	private String tradeNo;

	/** 关联主业务流水号 */
	private String priBizNo;

	/** 借据编号 */
	private String debtNo;

	/** 公司编号 */
	private String corpNo;

	/** 公司名称 */
	private String corpName;

	/** 担保人名称地址 */
	private String warrantorNameAddr;

	/** 担保人编号 */
	private String warrantorNo;

	/** 贷款币种 */
	private String loanCur;

	/** 贷款金额 */
	private double loanAmount;

	/** 贷款帐号 */
	private String loanAccount;

	/** 折业务币种汇率 */
	private double exchangeRate;

	/** 业务币种 */
	private String tradeCur;

	/** 折业务金额 */
	private double tradeAmt;

	/** 押汇起息日 */
	private Date interestDate;

	/** 押汇天数 */
	private int loanDays;

	/** 押汇到期日 */
	private Date matureDate;

	/** 押汇利率 */
	private double interestRate;

	/** 系统利率 */
	private double sysInterestRate;

	/** 逾期利率 */
	private double oveInterestRate;

	/** 优惠点数 */
	private double loanRateFavRatio;

	/** 利息币种 */
	private String interestCur;

	/** 结息方式 */
	private String inttMode;

	/** 预计利息 */
	private double interestAmt;

	/** 贷款余额 */
	private double loanBalanceAmt;

	/** 未收利息金额 */
	private double withOutIntAmt;

	/** 预收利息余额 */
	private double preIntBalanceAmt;

	/** 结息日期 */
	private Date calcuInterestDate;

	/** 收取利息总额 */
	private double interestTotalAmt;

	/** 还款次数 */
	private int paymentTimes;

	/** 是否还清 */
	private String isPayOff;

	/** 合同号 */
	private String constractNo;

	/** 贷转存账号 */
	private String ltdact;

	/** 是否代转存 */
	private String isExt;

	/** 是否逾期 */
	private String isOverDue;

	/** 是否呆滞 */
	private String isBadDebt;

	/** 是否呆帐 */
	private String isBadAcct;

	/** 敞口头寸金额 */
	private double ckdcAmt;

	/** 最后处理计提/摊销日期 */
	private Date lastProcDate;

	/** 累计计提/摊销金额 */
	private double amortizeAmt;

	/** 已冲销计提/摊销金额 */
	private double prcAmortizeAmt;
	
	//2009-10-17  吉林加
	/** 贷款发送核心账号 */
	private String loanBuildAcct;
	
	////2009-10-30  吉林加
	/** 是否核销 */
	private String isBadAccted;
	
	//2011-11-18  临沂加
	/** 拆借利率 */
	private double cjRate;
	
	/** 拆借利息 */
	private double cjIntAmt;
	
	/** 是否新做业务 */
	private String isNew="Y";
	
	/** 拆借账户 */
	private String cjAcctno;
	/** 资金来源 */
	private String zjFrom;
	
	/** 是否使用自有资金 */
	private String recvAcctno;
	
	/*20130516 新增字段如下*/
	/** 出账机构 */
    private String acctOrgNo;
    
    private String LoanAcctNo;//入账账号
    

	public String getCjAcctno() {
		return cjAcctno;
	}

	public void setCjAcctno(String cjAcctno) {
		this.cjAcctno = cjAcctno;
	}

	public String getRecvAcctno() {
		return recvAcctno;
	}

	public void setRecvAcctno(String recvAcctno) {
		this.recvAcctno = recvAcctno;
	}

	public String getZjFrom() {
		return zjFrom;
	}

	public void setZjFrom(String zjFrom) {
		this.zjFrom = zjFrom;
	}

	public String getIsBadAccted() {
		return isBadAccted;
	}

	public void setIsBadAccted(String isBadAccted) {
		this.isBadAccted = isBadAccted;
	}

	public String getLoanBuildAcct() {
		return loanBuildAcct;
	}

	public void setLoanBuildAcct(String loanBuildAcct) {
		this.loanBuildAcct = loanBuildAcct;
	}

	public double getAmortizeAmt() {
		return amortizeAmt;
	}

	public void setAmortizeAmt(double amortizeAmt) {
		this.amortizeAmt = amortizeAmt;
	}

	public Date getLastProcDate() {
		return lastProcDate;
	}

	public void setLastProcDate(Date lastProcDate) {
		this.lastProcDate = lastProcDate;
	}

	public double getPrcAmortizeAmt() {
		return prcAmortizeAmt;
	}

	public void setPrcAmortizeAmt(double prcAmortizeAmt) {
		this.prcAmortizeAmt = prcAmortizeAmt;
	}

	public double getCkdcAmt() {
		return ckdcAmt;
	}

	public void setCkdcAmt(double ckdcAmt) {
		this.ckdcAmt = ckdcAmt;
	}

	public String getIsBadAcct() {
		return isBadAcct;
	}

	public void setIsBadAcct(String isBadAcct) {
		this.isBadAcct = isBadAcct;
	}

	/**
	 * @return Returns the isBadDebt.
	 */
	public String getIsBadDebt() {
		return isBadDebt;
	}

	/**
	 * @param isBadDebt
	 *            The isBadDebt to set.
	 */
	public void setIsBadDebt(String isBadDebt) {
		this.isBadDebt = isBadDebt;
	}

	/**
	 * @return Returns the isExt.
	 */
	public String getIsExt() {
		return isExt;
	}

	/**
	 * @param isExt
	 *            The isExt to set.
	 */
	public void setIsExt(String isExt) {
		this.isExt = isExt;
	}

	/**
	 * @return Returns the isOverDue.
	 */
	public String getIsOverDue() {
		return isOverDue;
	}

	/**
	 * @param isOverDue
	 *            The isOverDue to set.
	 */
	public void setIsOverDue(String isOverDue) {
		this.isOverDue = isOverDue;
	}

	public String getBizNo() {
		return bizNo;
	}

	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}

	public Date getCalcuInterestDate() {
		return calcuInterestDate;
	}

	public void setCalcuInterestDate(Date calcuInterestDate) {
		this.calcuInterestDate = calcuInterestDate;
	}

	public String getConstractNo() {
		return constractNo;
	}

	public void setConstractNo(String constractNo) {
		this.constractNo = constractNo;
	}

	public String getCorpNo() {
		return corpNo;
	}

	public void setCorpNo(String corpNo) {
		this.corpNo = corpNo;
	}

	public String getDebtNo() {
		return debtNo;
	}

	public void setDebtNo(String debtNo) {
		this.debtNo = debtNo;
	}

	public double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public double getInterestAmt() {
		return interestAmt;
	}

	public void setInterestAmt(double interestAmt) {
		this.interestAmt = interestAmt;
	}

	public String getInterestCur() {
		return interestCur;
	}

	public void setInterestCur(String interestCur) {
		this.interestCur = interestCur;
	}

	public Date getInterestDate() {
		return interestDate;
	}

	public void setInterestDate(Date interestDate) {
		this.interestDate = interestDate;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public double getInterestTotalAmt() {
		return interestTotalAmt;
	}

	public void setInterestTotalAmt(double interestTotalAmt) {
		this.interestTotalAmt = interestTotalAmt;
	}

	public String getInttMode() {
		return inttMode;
	}

	public void setInttMode(String inttMode) {
		this.inttMode = inttMode;
	}

	public String getIsPayOff() {
		return isPayOff;
	}

	public void setIsPayOff(String isPayOff) {
		this.isPayOff = isPayOff;
	}

	public String getLoanAccount() {
		return loanAccount;
	}

	public void setLoanAccount(String loanAccount) {
		this.loanAccount = loanAccount;
	}

	public double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public double getLoanBalanceAmt() {
		return loanBalanceAmt;
	}

	public void setLoanBalanceAmt(double loanBalanceAmt) {
		this.loanBalanceAmt = loanBalanceAmt;
	}

	public String getLoanCur() {
		return loanCur;
	}

	public void setLoanCur(String loanCur) {
		this.loanCur = loanCur;
	}

	public int getLoanDays() {
		return loanDays;
	}

	public void setLoanDays(int loanDays) {
		this.loanDays = loanDays;
	}

	public String getLtdact() {
		return ltdact;
	}

	public void setLtdact(String ltdact) {
		this.ltdact = ltdact;
	}

	public Date getMatureDate() {
		return matureDate;
	}

	public void setMatureDate(Date matureDate) {
		this.matureDate = matureDate;
	}

	public int getPaymentTimes() {
		return paymentTimes;
	}

	public void setPaymentTimes(int paymentTimes) {
		this.paymentTimes = paymentTimes;
	}

	public double getPreIntBalanceAmt() {
		return preIntBalanceAmt;
	}

	public void setPreIntBalanceAmt(double preIntBalanceAmt) {
		this.preIntBalanceAmt = preIntBalanceAmt;
	}

	public double getTradeAmt() {
		return tradeAmt;
	}

	public void setTradeAmt(double tradeAmt) {
		this.tradeAmt = tradeAmt;
	}

	public String getTradeCur() {
		return tradeCur;
	}

	public void setTradeCur(String tradeCur) {
		this.tradeCur = tradeCur;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getTxnSerialNo() {
		return txnSerialNo;
	}

	public void setTxnSerialNo(String txnSerialNo) {
		this.txnSerialNo = txnSerialNo;
	}

	public String getWarrantorNo() {
		return warrantorNo;
	}

	public void setWarrantorNo(String warrantorNo) {
		this.warrantorNo = warrantorNo;
	}

	public double getWithOutIntAmt() {
		return withOutIntAmt;
	}

	public void setWithOutIntAmt(double withOutIntAmt) {
		this.withOutIntAmt = withOutIntAmt;
	}

	public String getCorpName() {
		return corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	public String getOldTxnSerialNo() {
		return oldTxnSerialNo;
	}

	public void setOldTxnSerialNo(String oldTxnSerialNo) {
		this.oldTxnSerialNo = oldTxnSerialNo;
	}

	/**
	 * @return Returns the priBizNo.
	 */
	public String getPriBizNo() {
		return priBizNo;
	}

	/**
	 * @param priBizNo
	 *            The priBizNo to set.
	 */
	public void setPriBizNo(String priBizNo) {
		this.priBizNo = priBizNo;
	}

	public String getWarrantorNameAddr() {
		return warrantorNameAddr;
	}

	public void setWarrantorNameAddr(String warrantorNameAddr) {
		this.warrantorNameAddr = warrantorNameAddr;
	}

	public double getLoanRateFavRatio() {
		return loanRateFavRatio;
	}

	public void setLoanRateFavRatio(double loanRateFavRatio) {
		this.loanRateFavRatio = loanRateFavRatio;
	}

	public double getOveInterestRate() {
		return oveInterestRate;
	}

	public void setOveInterestRate(double oveInterestRate) {
		this.oveInterestRate = oveInterestRate;
	}

	public double getSysInterestRate() {
		return sysInterestRate;
	}

	public void setSysInterestRate(double sysInterestRate) {
		this.sysInterestRate = sysInterestRate;
	}

	public double getCjIntAmt() {
		return cjIntAmt;
	}

	public void setCjIntAmt(double cjIntAmt) {
		this.cjIntAmt = cjIntAmt;
	}

	public double getCjRate() {
		return cjRate;
	}

	public void setCjRate(double cjRate) {
		this.cjRate = cjRate;
	}

	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}

	public String getAcctOrgNo() {
		return acctOrgNo;
	}

	public void setAcctOrgNo(String acctOrgNo) {
		this.acctOrgNo = acctOrgNo;
	}

	public String getLoanAcctNo() {
		return LoanAcctNo;
	}

	public void setLoanAcctNo(String loanAcctNo) {
		LoanAcctNo = loanAcctNo;
	}
	
}

