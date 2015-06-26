package com.ebills.product.dg.AcctInterface.domain;
import java.sql.Date;

/**
 *  2006-5-23
 */
public class LoanRepayInfo  {
    /**交易流水号*/
    private String txnSerialNo;
    /**前交易流水号*/
    private String oldTxnSerialNo;
    /**交易类型号*/
    private String tradeNo;
    /**业务流水号*/
    private String bizNo;
    /**贷款业务编号*/
    private String primaryBizNo;
    /**贷款币种*/
    private String loanCur;
    /**实际贷款利率*/
    private double factIntRate;
    /**是否还本*/
    private String isRepayAmt;
    /**还本金额*/
    private double repayAmt;
    /**是否还息*/
    private String isRepIntAmt;
    /**应收利息金额*/
    private double preIntAmt;
    /**实际收取利息金额*/
    private double factIntAmt;
    /**还款日期*/
    private Date repayDate;
    /**是否记帐*/
    private String haveDebtrecd;
    /**还息币种*/
    private String repIntCur;
    /**还息金额*/
    private double repIntAmt;
    /**押汇天数*/
    private int loanDays;
    /**起息日*/
    private Date interestDate;
    
    /**拆借利息*/
    private double cjIntAmt;
    private String payacctno;
    private String dzcacctno;
    
    public Date getInterestDate() {
        return interestDate;
    }

    public void setInterestDate(Date interestDate) {
        this.interestDate = interestDate;
    }

    public int getLoanDays() {
        return loanDays;
    }

    public void setLoanDays(int loanDays) {
        this.loanDays = loanDays;
    }

    public double getRepIntAmt() {
        return repIntAmt;
    }

    public void setRepIntAmt(double repIntAmt) {
        this.repIntAmt = repIntAmt;
    }

    public String getRepIntCur() {
        return repIntCur;
    }

    public void setRepIntCur(String repIntCur) {
        this.repIntCur = repIntCur;
    }

    public String getHaveDebtrecd() {
        return haveDebtrecd;
    }

    public void setHaveDebtrecd(String haveDebtrecd) {
        this.haveDebtrecd = haveDebtrecd;
    }

    public String getBizNo() {
        return bizNo;
    }

    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }

    public double getFactIntAmt() {
        return factIntAmt;
    }

    public void setFactIntAmt(double factIntAmt) {
        this.factIntAmt = factIntAmt;
    }

    public double getFactIntRate() {
        return factIntRate;
    }

    public void setFactIntRate(double factIntRate) {
        this.factIntRate = factIntRate;
    }

    public String getIsRepayAmt() {
        return isRepayAmt;
    }

    public void setIsRepayAmt(String isRepayAmt) {
        this.isRepayAmt = isRepayAmt;
    }

    public String getIsRepIntAmt() {
        return isRepIntAmt;
    }

    public void setIsRepIntAmt(String isRepIntAmt) {
        this.isRepIntAmt = isRepIntAmt;
    }

    public String getLoanCur() {
        return loanCur;
    }

    public void setLoanCur(String loanCur) {
        this.loanCur = loanCur;
    }

    public double getPreIntAmt() {
        return preIntAmt;
    }

    public void setPreIntAmt(double preIntAmt) {
        this.preIntAmt = preIntAmt;
    }

    public String getPrimaryBizNo() {
        return primaryBizNo;
    }

    public void setPrimaryBizNo(String primaryBizNo) {
        this.primaryBizNo = primaryBizNo;
    }

    public double getRepayAmt() {
        return repayAmt;
    }

    public void setRepayAmt(double repayAmt) {
        this.repayAmt = repayAmt;
    }

    public Date getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(Date repayDate) {
        this.repayDate = repayDate;
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

    public String getOldTxnSerialNo() {
        return oldTxnSerialNo;
    }

    public void setOldTxnSerialNo(String oldTxnSerialNo) {
        this.oldTxnSerialNo = oldTxnSerialNo;
    }

	public double getCjIntAmt() {
		return cjIntAmt;
	}

	public void setCjIntAmt(double cjIntAmt) {
		this.cjIntAmt = cjIntAmt;
	}

	public String getDzcacctno() {
		return dzcacctno;
	}

	public void setDzcacctno(String dzcacctno) {
		this.dzcacctno = dzcacctno;
	}

	public String getPayacctno() {
		return payacctno;
	}

	public void setPayacctno(String payacctno) {
		this.payacctno = payacctno;
	}


}

