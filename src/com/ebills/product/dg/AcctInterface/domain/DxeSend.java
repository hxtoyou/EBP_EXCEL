package com.ebills.product.dg.AcctInterface.domain;

import java.sql.Date;
import java.sql.Timestamp;

public class DxeSend{
	/** 交易流水号 */
	private String txnSerialNo = null;
	/**
     * 针对国内信用证付汇增加的一个标示位
     * 如果使用大小额付汇的交易
     * icPayFlag 初始值 为  0
     * 如果 与核心交互，且 核心反馈成功，则反馈5,7
     * 小额为  5（金额小于5W）
     * 大额为7 （金额大于等于5W） 
     * 2 为已清算
     * 3 为其他
     * 4 已扎差
     * 记账接口中，与核心交互成功的情况下 为 
     * 后续根据清算接口情况 查询是否有其他 状态
     * */
    private String icPayFlag = "";
    
    /**
     * 大小额序号， 用于查询大小额状态
     * */
    private String icPaySerNo;
    
    /**
     * 大小额交易日期， 用于查询大小额状态
     * */
    private Date icPayDate;
    
    /**
     * 汇款行号， 用于查询大小额状态
     * */
    private String payBankNo;
    
    
    /**
     * 经办人
     * */
    private int userId;
    
    /**
     * 经办机构
     * */
    private String orgNo;
    /**
     * 添加
     * */
    private String bk_fl_drawee_name;//付款人名称
    private String bk_fl_drawee_acct_no;//付款人帐号
    private String bk_fl_payee_name;//收款人名称
    private String bk_fl_payee_acct_no;//收款人帐号
    private String bk_fl_irmt_bk_no;//汇入行行号
    private String bk_fl_irmt_bk_name;//汇入行行名
    private String bk_fl_odoc_no;//原凭证号
    private String bk_fl_trnt_amt;//汇划金额
    private String blg_date;//汇划日期
    private String bk_fl_blng_instn;//所属机构号
    private String bk_fl_opr_no;//操作员号
    private String bk_fl_rckr_no;//复核员号
    private String bk_fl_net_busn_typ1;//小额支付业务种类
    private String bk_fl_pkg_msg_no;//包内序号
    
    
	public String getBk_fl_drawee_name() {
		return bk_fl_drawee_name;
	}

	public void setBk_fl_drawee_name(String bkFlDraweeName) {
		bk_fl_drawee_name = bkFlDraweeName;
	}

	public String getBk_fl_drawee_acct_no() {
		return bk_fl_drawee_acct_no;
	}

	public void setBk_fl_drawee_acct_no(String bkFlDraweeAcctNo) {
		bk_fl_drawee_acct_no = bkFlDraweeAcctNo;
	}

	public String getBk_fl_payee_name() {
		return bk_fl_payee_name;
	}

	public void setBk_fl_payee_name(String bkFlPayeeName) {
		bk_fl_payee_name = bkFlPayeeName;
	}

	public String getBk_fl_payee_acct_no() {
		return bk_fl_payee_acct_no;
	}

	public void setBk_fl_payee_acct_no(String bkFlPayeeAcctNo) {
		bk_fl_payee_acct_no = bkFlPayeeAcctNo;
	}

	public String getBk_fl_irmt_bk_no() {
		return bk_fl_irmt_bk_no;
	}

	public void setBk_fl_irmt_bk_no(String bkFlIrmtBkNo) {
		bk_fl_irmt_bk_no = bkFlIrmtBkNo;
	}

	public String getBk_fl_irmt_bk_name() {
		return bk_fl_irmt_bk_name;
	}

	public void setBk_fl_irmt_bk_name(String bkFlIrmtBkName) {
		bk_fl_irmt_bk_name = bkFlIrmtBkName;
	}

	public String getBk_fl_odoc_no() {
		return bk_fl_odoc_no;
	}

	public void setBk_fl_odoc_no(String bkFlOdocNo) {
		bk_fl_odoc_no = bkFlOdocNo;
	}

	public String getBk_fl_trnt_amt() {
		return bk_fl_trnt_amt;
	}

	public void setBk_fl_trnt_amt(String bkFlTrntAmt) {
		bk_fl_trnt_amt = bkFlTrntAmt;
	}

	public String getBlg_date() {
		return blg_date;
	}

	public void setBlg_date(String blgDate) {
		blg_date = blgDate;
	}

	public String getBk_fl_blng_instn() {
		return bk_fl_blng_instn;
	}

	public void setBk_fl_blng_instn(String bkFlBlngInstn) {
		bk_fl_blng_instn = bkFlBlngInstn;
	}

	public String getBk_fl_opr_no() {
		return bk_fl_opr_no;
	}

	public void setBk_fl_opr_no(String bkFlOprNo) {
		bk_fl_opr_no = bkFlOprNo;
	}

	public String getBk_fl_rckr_no() {
		return bk_fl_rckr_no;
	}

	public void setBk_fl_rckr_no(String bkFlRckrNo) {
		bk_fl_rckr_no = bkFlRckrNo;
	}

	public String getBk_fl_net_busn_typ1() {
		return bk_fl_net_busn_typ1;
	}

	public void setBk_fl_net_busn_typ1(String bkFlNetBusnTyp1) {
		bk_fl_net_busn_typ1 = bkFlNetBusnTyp1;
	}

	public String getBk_fl_pkg_msg_no() {
		return bk_fl_pkg_msg_no;
	}

	public void setBk_fl_pkg_msg_no(String bkFlPkgMsgNo) {
		bk_fl_pkg_msg_no = bkFlPkgMsgNo;
	}

	public String getTxnSerialNo() {
		return txnSerialNo;
	}

	public void setTxnSerialNo(String txnSerialNo) {
		this.txnSerialNo = txnSerialNo;
	}

	public String getIcPayFlag() {
		return icPayFlag;
	}

	public void setIcPayFlag(String icPayFlag) {
		this.icPayFlag = icPayFlag;
	}

	public String getIcPaySerNo() {
		return icPaySerNo;
	}

	public void setIcPaySerNo(String icPaySerNo) {
		this.icPaySerNo = icPaySerNo;
	}

	public Date getIcPayDate() {
		return icPayDate;
	}

	public void setIcPayDate(Date icPayDate) {
		this.icPayDate = icPayDate;
	}

	public String getPayBankNo() {
		return payBankNo;
	}

	public void setPayBankNo(String payBankNo) {
		this.payBankNo = payBankNo;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	
}
