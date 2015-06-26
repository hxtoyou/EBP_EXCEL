package com.ebills.product.dg.AcctInterface.domain;

public class CmtSend{
	/** 交易流水号 */
	private String txnSerialNo = null;

	/** 收报行、收款行大额行号 */
	private String beneBankPayNo = null;

	/** 收款人开户行名称 */
	private String beneBankNameAddr = null;

	/** 收款人名称 */
	private String beneAppName = null;
	
	/** 收款人账号 */
	private String beneAppAccNo = null;
	
	/** 汇款金额 */
	private double cmtAmt = 0.00;
	
	/** 汇款人账号 */
	private String payAccNo = null;
	
	/** 大额业务种类号 */
	private String cmtpaytype = null;
	
	public String getTxnSerialNo() {
		return txnSerialNo;
	}

	public void setTxnSerialNo(String txnSerialNo) {
		this.txnSerialNo = txnSerialNo;
	}

	public String getBeneBankPayNo() {
		return beneBankPayNo;
	}

	public void setBeneBankPayNo(String beneBankPayNo) {
		this.beneBankPayNo = beneBankPayNo;
	}

	public String getBeneBankNameAddr() {
		return beneBankNameAddr;
	}

	public void setBeneBankNameAddr(String beneBankNameAddr) {
		this.beneBankNameAddr = beneBankNameAddr;
	}

	public String getBeneAppName() {
		return beneAppName;
	}

	public void setBeneAppName(String beneAppName) {
		this.beneAppName = beneAppName;
	}

	public String getBeneAppAccNo() {
		return beneAppAccNo;
	}

	public void setBeneAppAccNo(String beneAppAccNo) {
		this.beneAppAccNo = beneAppAccNo;
	}

	public double getCmtAmt() {
		return cmtAmt;
	}

	public void setCmtAmt(double cmtAmt) {
		this.cmtAmt = cmtAmt;
	}

	public String getPayAccNo() {
		return payAccNo;
	}

	public void setPayAccNo(String payAccNo) {
		this.payAccNo = payAccNo;
	}

	public void setCmtpaytype(String cmtpaytype) {
		this.cmtpaytype = cmtpaytype;
	}

	public String getCmtpaytype() {
		return cmtpaytype;
	}
}
