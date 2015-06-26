package com.ebills.product.dg.AcctInterface.domain;

import java.sql.Date;
public class DFPayment {
	
	/** 交易流水号* */
	private String txnSerialNo;

	/** 信用证号* */
	private String lcNo;

	/** 来单/代收编号* */
	private String abNo;

	/** 代付编号(申请时产生)* */
	private String ipNo;

	/** 代付行帐号* */
	private String dfAcctNo;

	/** 帐户行编号* */
	private String acctBankNo;

	/** 帐户行swiftcode* */
	private String acctBankSwift;

	/** 帐户行名称地址* */
	private String acctBankNameAddr;

	/** 帐户行内部帐号* */
	private String innerAccount;

	/** 帐户行外部帐号* */
	private String outerAccount;

	/** 付汇币种* */
	private String paymentCur;

	/** 同业利息* */
	private double tyRate;

	/** 同业利率* */
	private double tyInterestRate;

	/** 客户利息* */
	private double appRate;

	/** 客户利率* */
	private double appInterestRate;

	/** 保证金预先支付金额* */
	private double depositAmt;

	/** 客户扣帐金额* */
	private double appAmt;

	/** 付汇金额* */
	private double paymentAmt;

	/** 内扣金额* */
	private double innerDeductAmt;

	/** 国外费用* */
	private double ourSideCharge;

	/** 实际付汇金额* */
	private double factPaymentAmt;

	/** 是否发送202报文* */
	private String isSend202 = "Y";

	/** 付款人编号* */
	private String appNo;

	/** 付款人名称* */
	private String appName;

	/** 计息天数* */
	private int rateDays;

	/** 海外代付模式(代收海外付汇，信用证海外付汇)* */
	private String ipFlag;

	/** 付汇日期* */
	private Date paymentDate;

	/** :+ 收款方所属地区1)境外;2)保税区.系统默认为境外 */
	private String recvAddr;

	/** 转汇行SWIFT */
	private String midBankSwift;//后补加 资金业务进口代付到期中使用

	/** 转汇行名称 */
	private String midBankName;//同上

	/** 收款人所在国家 */
	private String countryNo;
	
	private String isAcct; 
	
	/*************************以下为临沂加（liguo）***********************/
	private String dfArea;
	
	/**利息总额**/
	private double totalInterest;
	
	/**应缴税款**/
	private double tax;
	
	/**是否含税**/
	private String hasTax;
	
	/**税率**/
	private double taxRate;
	
	private double otherDeposit;
	
	private String isAgentLC;
	
	/**营业税*/
    private double yyTax;
    /**营业税率*/
    private double yyTaxRate;
    /**我方扣费*/
    private double ourCharge;
	/************************以上为临沂加（liguo）***********************/

	public String getIsAcct() {
		return isAcct;
	}

	public void setIsAcct(String isAcct) {
		this.isAcct = isAcct;
	}
	
	

	public void setCountryNo(String countryNo) {
		this.countryNo = countryNo;
	}

	public String getCountryNo() {
		return countryNo;
	}

	public String getRecvAddr() {
		return recvAddr;
	}

	public void setRecvAddr(String recvAddr) {
		this.recvAddr = recvAddr;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getIpFlag() {
		return ipFlag;
	}

	public void setIpFlag(String ipFlag) {
		this.ipFlag = ipFlag;
	}

	public int getRateDays() {
		return rateDays;
	}

	public void setRateDays(int rateDays) {
		this.rateDays = rateDays;
	}

	public String getAcctBankNameAddr() {
		return acctBankNameAddr;
	}

	public void setAcctBankNameAddr(String acctBankNameAddr) {
		this.acctBankNameAddr = acctBankNameAddr;
	}

	public String getAcctBankNo() {
		return acctBankNo;
	}

	public void setAcctBankNo(String acctBankNo) {
		this.acctBankNo = acctBankNo;
	}

	public String getAcctBankSwift() {
		return acctBankSwift;
	}

	public void setAcctBankSwift(String acctBankSwift) {
		this.acctBankSwift = acctBankSwift;
	}

	public double getAppAmt() {
		return appAmt;
	}

	public void setAppAmt(double appAmt) {
		this.appAmt = appAmt;
	}

	public double getAppRate() {
		return appRate;
	}

	public void setAppRate(double appRate) {
		this.appRate = appRate;
	}

	public String getDfAcctNo() {
		return dfAcctNo;
	}

	public void setDfAcctNo(String dfAcctNo) {
		this.dfAcctNo = dfAcctNo;
	}

	public String getIpNo() {
		return ipNo;
	}

	public void setIpNo(String ipNo) {
		this.ipNo = ipNo;
	}

	public String getIsSend202() {
		return isSend202;
	}

	public void setIsSend202(String isSend202) {
		this.isSend202 = isSend202;
	}

	public double getPaymentAmt() {
		return paymentAmt;
	}

	public void setPaymentAmt(double paymentAmt) {
		this.paymentAmt = paymentAmt;
	}

	public String getPaymentCur() {
		return paymentCur;
	}

	public void setPaymentCur(String paymentCur) {
		this.paymentCur = paymentCur;
	}

	public String getTxnSerialNo() {
		return txnSerialNo;
	}

	public void setTxnSerialNo(String txnSerialNo) {
		this.txnSerialNo = txnSerialNo;
	}

	public double getTyRate() {
		return tyRate;
	}

	public void setTyRate(double tyRate) {
		this.tyRate = tyRate;
	}

	public String getAbNo() {
		return abNo;
	}

	public void setAbNo(String abNo) {
		this.abNo = abNo;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public String getInnerAccount() {
		return innerAccount;
	}

	public void setInnerAccount(String innerAccount) {
		this.innerAccount = innerAccount;
	}

	public String getOuterAccount() {
		return outerAccount;
	}

	public void setOuterAccount(String outerAccount) {
		this.outerAccount = outerAccount;
	}

	public String getLcNo() {
		return lcNo;
	}

	public void setLcNo(String lcNo) {
		this.lcNo = lcNo;
	}

	public double getInnerDeductAmt() {
		return innerDeductAmt;
	}

	public void setInnerDeductAmt(double innerDeductAmt) {
		this.innerDeductAmt = innerDeductAmt;
	}

	public double getOurSideCharge() {
		return ourSideCharge;
	}

	public void setOurSideCharge(double ourSideCharge) {
		this.ourSideCharge = ourSideCharge;
	}

	public double getFactPaymentAmt() {
		return factPaymentAmt;
	}

	public void setFactPaymentAmt(double factPaymentAmt) {
		this.factPaymentAmt = factPaymentAmt;
	}

	public double getAppInterestRate() {
		return appInterestRate;
	}

	public void setAppInterestRate(double appInterestRate) {
		this.appInterestRate = appInterestRate;
	}

	public double getTyInterestRate() {
		return tyInterestRate;
	}

	public void setTyInterestRate(double tyInterestRate) {
		this.tyInterestRate = tyInterestRate;
	}

	public double getDepositAmt() {
		return depositAmt;
	}

	public void setDepositAmt(double depositAmt) {
		this.depositAmt = depositAmt;
	}

	public String getMidBankName() {
		return midBankName;
	}

	public void setMidBankName(String midBankName) {
		this.midBankName = midBankName;
	}

	public String getMidBankSwift() {
		return midBankSwift;
	}

	public void setMidBankSwift(String midBankSwift) {
		this.midBankSwift = midBankSwift;
	}

	public String getDfArea() {
		return dfArea;
	}

	public void setDfArea(String dfArea) {
		this.dfArea = dfArea;
	}

	public String getHasTax() {
		return hasTax;
	}

	public void setHasTax(String hasTax) {
		this.hasTax = hasTax;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}

	public double getTotalInterest() {
		return totalInterest;
	}

	public void setTotalInterest(double totalInterest) {
		this.totalInterest = totalInterest;
	}

	public String getIsAgentLC() {
		return isAgentLC;
	}

	public void setIsAgentLC(String isAgentLC) {
		this.isAgentLC = isAgentLC;
	}

	public double getOtherDeposit() {
		return otherDeposit;
	}

	public void setOtherDeposit(double otherDeposit) {
		this.otherDeposit = otherDeposit;
	}

	public double getOurCharge() {
		return ourCharge;
	}

	public void setOurCharge(double ourCharge) {
		this.ourCharge = ourCharge;
	}

	public double getYyTax() {
		return yyTax;
	}

	public void setYyTax(double yyTax) {
		this.yyTax = yyTax;
	}

	public double getYyTaxRate() {
		return yyTaxRate;
	}

	public void setYyTaxRate(double yyTaxRate) {
		this.yyTaxRate = yyTaxRate;
	}

}
