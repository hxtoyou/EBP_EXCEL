package com.ebills.product.dg.AcctInterface.domain;

import java.sql.Date;
/**
 * @描述 海外代付申请对象 于信用证信息里添加是否代付字段。 用于来单付汇时区分。
 */
public class DFApply {
	
	/** 交易流水号* */
	private String txnSerialNo;

	/** 代付编号* */
	private String ipNo;

	/** 信用证号* */
	private String lcNo;

	/** 来单编号(代收编号)* */
	private String abNo;

	/** 来单币种(代收)* */
	private String negoCur;

	/** 来单金额(代收)* */
	private double negoAmt;

	/** 付款人编号* */
	private String appNo;

	/** 付款人名称* */
	private String appName;

	/** 收款行编号* */
	private String beneBankNo;

	/** 收款行swiftCode* */
	private String beneBankswiftCode;

	/** 收款行名称* */
	private String beneBankswiftName;

	/** 收款人编号* */
	private String beneNo;

	/** 收款人名称* */
	private String beneName;

	/** 代付行编号* */
	private String dfBankNo;

	/** 代付行swiftCode* */
	private String dfBankSwiftCode;

	/** 代付行名称* */
	private String dfBankName;

	/** 代付币种* */
	private String dfCur;

	/** 代付金额* */
	private double dfAmt;

	/** 同业利率* */
	private double tyRate;

	/** 同业到期日* */
	private Date tyEndDate;

	/** 支付同业利息* */
	private double tyInterest;
	
	/** 客户利率* */
	private double appRate;	
	
	/*************************以下为临沂加（liguo）***********************/
	private String dfArea;
	
	/**利息总额**/
	private double totalInterest;
	
	/**应缴所得税款**/
	private double tax;
	
	/**是否含税**/
	private String hasTax;
	
	/**所得税率**/
	private double taxRate;
	
	/**扣划我行存于他行保证金金额*/
    private double otherDeposit;
    
    /**营业税*/
    private double yyTax;
    /**营业税率*/
    private double yyTaxRate;
    /**我方扣费*/
    private double ourCharge;
	/************************以上为临沂加（liguo）***********************/
	private String creditFileNo;
	
	/** 到期日* */
	private Date endDate;

	/** 客户利息* */
	private double appInsterest;

	/** 申请日期* */
	private Date applyDate;

	/** 起息日* */
	private Date startRateDate;

	/** 是否发送299报文* */
	private String isSend299;

	/** 海外代付模式(代收海外付汇，信用证海外付汇)* */
	private String ipFlag;

	/** 是否付汇* */
	private String isPayment;

	/** 期限天数* */
	private int termDays;

	/** 代收\来单中的OC\BP号* */
	private String ocbpNo;

	/** 是否从保证金帐上预先支付* */
	private String isUseDeposit = "N";

	/** 是否发送202报文* */
	private String isSend202;

	/** 帐户行编号* */
	private String acctBankNo;

	/** 帐户行SwiftCode* */
	private String acctBankSwiftCode;

	/** 帐户行名称地址* */
	private String acctBankNameAddr;

	/** 帐户行内部帐号* */
	private String innerAcctNo;

	/** 帐户行外部帐号* */
	private String outerAcctNo;

	/** 支付金额* */
	private double depositAmt;

	/** 登记的保证金帐号* */
	private String acctNo;

	/** 帐务类型* */
	private String acctType;

	/** **************************海外代付确认信息******************************* */
	/** 是否确认* */
	private String isAffirm;

	/** 确认交易流水号* */
	private String affTxnSerialNo;

	/** 确认日期* */
	private Date affirmDate;

	/** 借进口押汇帐号* */
	private String fromAcct;

	/** 进口押汇帐号对应科目* */
	private String formSubject = "13202";

	/** 进口代付款项去向信息* */
	private String toAcct;

	/** 去向帐务类型* */
	private String toAcctTypeNo;

	/** **************************海外代付撤消信息********************************* */
	/** 是否撤消* */
	private String isUndo;

	/** 撤消日期* */
	private Date undoDate;

	/** 撤消原因* */
	private String undoReason;

	/** 撤消交易流水号* */
	private String undoTxnSerialNo;

	/* libor利率 */
	private double liborRate;

	/* 点数 */
	private double point;

	/* 境内外 */
	private String tradeArea;

	/* 计息方式N表示后收，其它表示先收， */
	private String interestWay;

	/** 是否记帐* */
	private String isAcct;
	
	/**相关业务编号**/
	private String refNo;
	
	/** 代付行帐户行SwiftCode* */
	private String dfAcctBankSwiftCode;

	/** 帐户行名称地址* */
	private String dfAcctBankNameAddr;
	
	
	/** 帐户行SwiftCode* */
	private String midBankSwiftCode;

	/** 帐户行名称地址* */
	private String midBankNameAddr;
	
    //  昆明加
    /**他行授信额度费率种类*/
	private String chargeType;
	/**他行授信额度费率比例*/
	private double chargeRate;
	/**他行授信额度费用金额*/
	private double chargeAmt;
	/**他行授信额度编号*/
	private String lmtNo;
	
	/**是否有押汇*/
	private String isJkyf;
	/**代付差额(应付金额减去代付金我额）*/
	private double ceAmt;
	
	private String constractNo;
	
	private String debtNo;
	
	public double getChargeAmt() {
		return chargeAmt;
	}

	public void setChargeAmt(double chargeAmt) {
		this.chargeAmt = chargeAmt;
	}

	public double getChargeRate() {
		return chargeRate;
	}

	public void setChargeRate(double chargeRate) {
		this.chargeRate = chargeRate;
	}

	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	public String getLmtNo() {
		return lmtNo;
	}

	public void setLmtNo(String lmtNo) {
		this.lmtNo = lmtNo;
	}

	public String getDfAcctBankSwiftCode() {
		return dfAcctBankSwiftCode;
	}

	public void setDfAcctBankSwiftCode(String dfAcctBankSwiftCode) {
		this.dfAcctBankSwiftCode = dfAcctBankSwiftCode;
	}
	
	
	
	
	public String getDfAcctBankNameAddr() {
		return dfAcctBankNameAddr;
	}

	public void setDfAcctBankNameAddr(String dfAcctBankNameAddr) {
		this.dfAcctBankNameAddr = dfAcctBankNameAddr;
	}
	
	
	
	
	public String getMidBankSwiftCode() {
		return midBankSwiftCode;
	}

	public void setMidBankSwiftCode(String midBankSwiftCode) {
		this.midBankSwiftCode = midBankSwiftCode;
	}
	
	
	
	
	public String getMidBankNameAddr() {
		return midBankNameAddr;
	}

	public void setMidBankNameAddr(String midBankNameAddr) {
		this.midBankNameAddr = midBankNameAddr;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	

	public String getIsAcct() {
		return isAcct;
	}

	public void setIsAcct(String isAcct) {
		this.isAcct = isAcct;
	}

	public String getIsAffirm() {
		return isAffirm;
	}

	public void setIsAffirm(String isAffirm) {
		this.isAffirm = isAffirm;
	}

	public String getIsPayment() {
		return isPayment;
	}

	public void setIsPayment(String isPayment) {
		this.isPayment = isPayment;
	}

	public String getIsUndo() {
		return isUndo;
	}

	public void setIsUndo(String isUndo) {
		this.isUndo = isUndo;
	}

	public String getIpFlag() {
		return ipFlag;
	}

	public void setIpFlag(String ipFlag) {
		this.ipFlag = ipFlag;
	}

	public String getAbNo() {
		return abNo;
	}

	public void setAbNo(String abNo) {
		this.abNo = abNo;
	}

	public double getAppInsterest() {
		return appInsterest;
	}

	public void setAppInsterest(double appInsterest) {
		this.appInsterest = appInsterest;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public double getAppRate() {
		return appRate;
	}

	public void setAppRate(double appRate) {
		this.appRate = appRate;
	}

	public String getBeneName() {
		return beneName;
	}

	public void setBeneName(String beneName) {
		this.beneName = beneName;
	}

	public String getTxnSerialNo() {
		return txnSerialNo;
	}

	public void setTxnSerialNo(String txnSerialNo) {
		this.txnSerialNo = txnSerialNo;
	}

	public double getDfAmt() {
		return dfAmt;
	}

	public void setDfAmt(double dfAmt) {
		this.dfAmt = dfAmt;
	}

	public String getDfCur() {
		return dfCur;
	}

	public void setDfCur(String dfCur) {
		this.dfCur = dfCur;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getLcNo() {
		return lcNo;
	}

	public void setLcNo(String lcNo) {
		this.lcNo = lcNo;
	}

	public double getNegoAmt() {
		return negoAmt;
	}

	public void setNegoAmt(double negoAmt) {
		this.negoAmt = negoAmt;
	}

	public String getNegoCur() {
		return negoCur;
	}

	public void setNegoCur(String negoCur) {
		this.negoCur = negoCur;
	}

	public Date getTyEndDate() {
		return tyEndDate;
	}

	public void setTyEndDate(Date tyEndDate) {
		this.tyEndDate = tyEndDate;
	}

	public double getTyInterest() {
		return tyInterest;
	}

	public void setTyInterest(double tyInterest) {
		this.tyInterest = tyInterest;
	}

	public double getTyRate() {
		return tyRate;
	}

	public void setTyRate(double tyRate) {
		this.tyRate = tyRate;
	}

	public String getIpNo() {
		return ipNo;
	}

	public void setIpNo(String ipNo) {
		this.ipNo = ipNo;
	}

	public String getIsSend299() {
		return isSend299;
	}

	public void setIsSend299(String isSend299) {
		this.isSend299 = isSend299;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public String getBeneBankNo() {
		return beneBankNo;
	}

	public void setBeneBankNo(String beneBankNo) {
		this.beneBankNo = beneBankNo;
	}

	public String getBeneBankswiftCode() {
		return beneBankswiftCode;
	}

	public void setBeneBankswiftCode(String beneBankswiftCode) {
		this.beneBankswiftCode = beneBankswiftCode;
	}

	public String getBeneBankswiftName() {
		return beneBankswiftName;
	}

	public void setBeneBankswiftName(String beneBankswiftName) {
		this.beneBankswiftName = beneBankswiftName;
	}

	public String getBeneNo() {
		return beneNo;
	}

	public void setBeneNo(String beneNo) {
		this.beneNo = beneNo;
	}

	public String getDfBankName() {
		return dfBankName;
	}

	public void setDfBankName(String dfBankName) {
		this.dfBankName = dfBankName;
	}

	public String getDfBankNo() {
		return dfBankNo;
	}

	public void setDfBankNo(String dfBankNo) {
		this.dfBankNo = dfBankNo;
	}

	public String getDfBankSwiftCode() {
		return dfBankSwiftCode;
	}

	public void setDfBankSwiftCode(String dfBankSwiftCode) {
		this.dfBankSwiftCode = dfBankSwiftCode;
	}

	public Date getAffirmDate() {
		return affirmDate;
	}

	public void setAffirmDate(Date affirmDate) {
		this.affirmDate = affirmDate;
	}

	public String getFormSubject() {
		return formSubject;
	}

	public void setFormSubject(String formSubject) {
		this.formSubject = formSubject;
	}

	public String getFromAcct() {
		return fromAcct;
	}

	public void setFromAcct(String fromAcct) {
		this.fromAcct = fromAcct;
	}

	public Date getUndoDate() {
		return undoDate;
	}

	public void setUndoDate(Date undoDate) {
		this.undoDate = undoDate;
	}

	public String getUndoReason() {
		return undoReason;
	}

	public void setUndoReason(String undoReason) {
		this.undoReason = undoReason;
	}

	public String getAffTxnSerialNo() {
		return affTxnSerialNo;
	}

	public void setAffTxnSerialNo(String affTxnSerialNo) {
		this.affTxnSerialNo = affTxnSerialNo;
	}

	public String getUndoTxnSerialNo() {
		return undoTxnSerialNo;
	}

	public void setUndoTxnSerialNo(String undoTxnSerialNo) {
		this.undoTxnSerialNo = undoTxnSerialNo;
	}

	public String getToAcct() {
		return toAcct;
	}

	public void setToAcct(String toAcct) {
		this.toAcct = toAcct;
	}

	public String getToAcctTypeNo() {
		return toAcctTypeNo;
	}

	public void setToAcctTypeNo(String toAcctTypeNo) {
		this.toAcctTypeNo = toAcctTypeNo;
	}

	public int getTermDays() {
		return termDays;
	}

	public void setTermDays(int termDays) {
		this.termDays = termDays;
	}

	public String getOcbpNo() {
		return ocbpNo;
	}

	public void setOcbpNo(String ocbpNo) {
		this.ocbpNo = ocbpNo;
	}

	public Date getStartRateDate() {
		return startRateDate;
	}

	public void setStartRateDate(Date startRateDate) {
		this.startRateDate = startRateDate;
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

	public String getAcctBankSwiftCode() {
		return acctBankSwiftCode;
	}

	public void setAcctBankSwiftCode(String acctBankSwiftCode) {
		this.acctBankSwiftCode = acctBankSwiftCode;
	}

	public String getAcctNo() {
		return acctNo;
	}

	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}

	public String getAcctType() {
		return acctType;
	}

	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}

	public double getDepositAmt() {
		return depositAmt;
	}

	public void setDepositAmt(double depositAmt) {
		this.depositAmt = depositAmt;
	}

	public String getIsSend202() {
		return isSend202;
	}

	public double getLiborRate() {
		return liborRate;
	}

	public void setLiborRate(double liborRate) {
		this.liborRate = liborRate;
	}

	public double getPoint() {
		return point;
	}

	public void setPoint(double point) {
		this.point = point;
	}

	public void setIsSend202(String isSend202) {
		this.isSend202 = isSend202;
	}

	public String getIsUseDeposit() {
		return isUseDeposit;
	}

	public void setIsUseDeposit(String isUseDeposit) {
		this.isUseDeposit = isUseDeposit;
	}

	public String getInnerAcctNo() {
		return innerAcctNo;
	}

	public void setInnerAcctNo(String innerAcctNo) {
		this.innerAcctNo = innerAcctNo;
	}

	public String getOuterAcctNo() {
		return outerAcctNo;
	}

	public void setOuterAcctNo(String outerAcctNo) {
		this.outerAcctNo = outerAcctNo;
	}

	public String getInterestWay() {
		return interestWay;
	}

	public void setInterestWay(String interestWay) {
		this.interestWay = interestWay;
	}

	public String getTradeArea() {
		return tradeArea;
	}

	public void setTradeArea(String tradeArea) {
		this.tradeArea = tradeArea;
	}
	
	public double getTotalInterest() {
		return totalInterest;
	}

	public void setTotalInterest(double totalInterest) {
		this.totalInterest = totalInterest;
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
	 
	public void setDfArea(String dfArea) {
		this.dfArea = dfArea;
	}

	public String getDfArea() {
		return dfArea;
	}
	public void setHasTax(String hasTax) {
		this.hasTax = hasTax;
	}

	public String getHasTax() {
		return hasTax;
	}

	public double getCeAmt() {
		return ceAmt;
	}

	public void setCeAmt(double ceAmt) {
		this.ceAmt = ceAmt;
	}

	public String getIsJkyf() {
		return isJkyf;
	}

	public void setIsJkyf(String isJkyf) {
		this.isJkyf = isJkyf;
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

	public String getConstractNo() {
		return constractNo;
	}

	public void setConstractNo(String constractNo) {
		this.constractNo = constractNo;
	}

	public String getDebtNo() {
		return debtNo;
	}

	public void setDebtNo(String debtNo) {
		this.debtNo = debtNo;
	}

	public String getCreditFileNo() {
		return creditFileNo;
	}

	public void setCreditFileNo(String creditFileNo) {
		this.creditFileNo = creditFileNo;
	}
    
	
}

