package com.ebills.product.dg.AcctInterface;

import java.math.BigDecimal;

/**
 * 2014-06-14 LT
 * 定义记帐分录对象
 * @return
 * @throws Exception
 */
public class BuAcctDataInfo {
	private String accvouNo;//ACCVOUNO
	private String txnNo;//TXNNO
	private String dcFlag;//借贷标识:D：借，C：贷	P：付，R：收
	private String ccy;//CCY IS '币种'
	private BigDecimal amount;//AMOUNT IS '金额';
	private String acctNo;//ACCTNO IS '账号'
	private String acctNoName;//ACCTNONAME IS '账户名称'
	private String subJect;//SUBJECT IS '科目'
	private BigDecimal localAmount;//LOCALAMOUNT IS '折本币金额'
	private BigDecimal buyPrice;//BUYPRICE IS '折本币汇率'
	
	//东莞记账循环体包含结售汇类型、汇率、卖出价、买入价
	private String  jshType;
	private BigDecimal rate;
	private BigDecimal salePrice;
	
	
	public String getAccvouNo() {
		return accvouNo;
	}
	public void setAccvouNo(String accvouNo) {
		this.accvouNo = accvouNo;
	}
	public String getTxnNo() {
		return txnNo;
	}
	public void setTxnNo(String txnNo) {
		this.txnNo = txnNo;
	}
	public String getDcFlag() {
		return dcFlag;
	}
	public void setDcFlag(String dcFlag) {
		this.dcFlag = dcFlag;
	}
	public String getCcy() {
		return ccy;
	}
	public void setCcy(String ccy) {
		this.ccy = ccy;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getAcctNo() {
		return acctNo;
	}
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	public String getAcctNoName() {
		return acctNoName;
	}
	public void setAcctNoName(String acctNoName) {
		this.acctNoName = acctNoName;
	}
	public String getSubJect() {
		return subJect;
	}
	public void setSubJect(String subJect) {
		this.subJect = subJect;
	}
	public BigDecimal getLocalAmount() {
		return localAmount;
	}
	public void setLocalAmount(BigDecimal localAmount) {
		this.localAmount = localAmount;
	}
	public BigDecimal getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(BigDecimal buyPrice) {
		this.buyPrice = buyPrice;
	}
	public String getJshType() {
		return jshType;
	}
	public void setJshType(String jshType) {
		this.jshType = jshType;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public BigDecimal getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}
	
}
