package com.ebills.product.dg.AcctInterface;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * 2014-07-3 LT
 * 定义资金流向对象
 * @return
 * @throws Exception
 */
public class BuFundDataInfo {
	private String fundSflowId;//FUNDSFLOWID
	private String txnNo;//TXNNO
	private String ybCur;//YBCUR
	private BigDecimal ybAmt;//YBAMT
	private String jzCur;//JZCUR
	private BigDecimal jzAmt;//JZAMT	
	private String jzacctno;
	private String jshcode;
	private String tradeMateSwift;
	private Date valueDate;
	
	public Date getValueDate() {
		return valueDate;
	}
	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}
	public String getTradeMateSwift() {
		return tradeMateSwift;
	}	
	public void setTradeMateSwift(String tradeMateSwift) {
		this.tradeMateSwift = tradeMateSwift;
	}
	public String getJshcode() {
		return jshcode;
	}
	public void setJshcode(String jshcode) {
		this.jshcode = jshcode;
	}
	public String getJzacctno() {
		return jzacctno;
	}
	public void setJzacctno(String jzacctno) {
		this.jzacctno = jzacctno;
	}
	private BigDecimal realHr;//REALHR
	private String jsthFlag;//JSTHFLAG
	
	public String getJsthFlag() {
		return jsthFlag;
	}
	public void setJsthFlag(String jsthFlag) {
		this.jsthFlag = jsthFlag;
	}
	public String getFundSflowId() {
		return fundSflowId;
	}
	public void setFundSflowId(String fundSflowId) {
		this.fundSflowId = fundSflowId;
	}
	public String getTxnNo() {
		return txnNo;
	}
	public void setTxnNo(String txnNo) {
		this.txnNo = txnNo;
	}
	public String getYbCur() {
		return ybCur;
	}
	public void setYbCur(String ybCur) {
		this.ybCur = ybCur;
	}
	public BigDecimal getYbAmt() {
		return ybAmt;
	}
	public void setYbAmt(BigDecimal ybAmt) {
		this.ybAmt = ybAmt;
	}
	public String getJzCur() {
		return jzCur;
	}
	public void setJzCur(String jzCur) {
		this.jzCur = jzCur;
	}
	public BigDecimal getJzAmt() {
		return jzAmt;
	}
	public void setJzAmt(BigDecimal jzAmt) {
		this.jzAmt = jzAmt;
	}
	public BigDecimal getRealHr() {
		return realHr;
	}
	public void setRealHr(BigDecimal realHr) {
		this.realHr = realHr;
	}
	
}
