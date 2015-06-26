package com.ebills.product.dg.AcctInterface.domain;

import java.sql.Date;
import java.sql.Timestamp;

public class DxeRecv{
	private String bk_opun_cod;//      *         营业单位代码
	private String bk_acct_typ;//      *         帐别
	private String bk_lg_no;//      *         科目号
	private String bk_acct_name;//      *         帐户名称
	private String bk_date_grp;//
	private Date bk_date;//      *         日期
	private String bk_tx_log_no;//      *         交易流水号
	private String bk_dscrp;//      *         摘要
	private double bk_dr_amt;//      *         借方发生额
	private double bk_cr_amt;//      *         贷方发生额
	private double bk_bal ;//      *         余额
	private String bk_tx_tlr_no;//      *         交易柜员
	private String bk_rckr_no ;//      *         复核员号
	private String bk_tx_instn;//      *         交易机构
	private String bk_tx_tm;//      *         交易时间
	private String txnserialno;//国结流水
	private String preBizNo;//对应的国结来单编号
	private String orgno;//机构号
	private String orgName;//机构号
	private String optstate;//状态  0 初始化 ；1需要手动发起业务的（含自动发起失败，冲账），2已发起业务、未完结，3，已发起业务，已完结，
	private String memo;//备注
	public String getBk_opun_cod() {
		return bk_opun_cod;
	}
	public void setBk_opun_cod(String bk_opun_cod) {
		this.bk_opun_cod = bk_opun_cod;
	}
	public String getBk_acct_typ() {
		return bk_acct_typ;
	}
	public void setBk_acct_typ(String bk_acct_typ) {
		this.bk_acct_typ = bk_acct_typ;
	}
	public String getBk_lg_no() {
		return bk_lg_no;
	}
	public void setBk_lg_no(String bk_lg_no) {
		this.bk_lg_no = bk_lg_no;
	}
	public String getBk_acct_name() {
		return bk_acct_name;
	}
	public void setBk_acct_name(String bk_acct_name) {
		this.bk_acct_name = bk_acct_name;
	}
	public String getBk_date_grp() {
		return bk_date_grp;
	}
	public void setBk_date_grp(String bk_date_grp) {
		this.bk_date_grp = bk_date_grp;
	}
	public Date getBk_date() {
		return bk_date;
	}
	public void setBk_date(Date bk_date) {
		this.bk_date = bk_date;
	}
	public String getBk_tx_log_no() {
		return bk_tx_log_no;
	}
	public void setBk_tx_log_no(String bk_tx_log_no) {
		this.bk_tx_log_no = bk_tx_log_no;
	}
	public String getBk_dscrp() {
		return bk_dscrp;
	}
	public void setBk_dscrp(String bk_dscrp) {
		this.bk_dscrp = bk_dscrp;
	}
	public double getBk_dr_amt() {
		return bk_dr_amt;
	}
	public void setBk_dr_amt(double bk_dr_amt) {
		this.bk_dr_amt = bk_dr_amt;
	}
	public double getBk_cr_amt() {
		return bk_cr_amt;
	}
	public void setBk_cr_amt(double bk_cr_amt) {
		this.bk_cr_amt = bk_cr_amt;
	}
	public double getBk_bal() {
		return bk_bal;
	}
	public void setBk_bal(double bk_bal) {
		this.bk_bal = bk_bal;
	}
	public String getBk_tx_tlr_no() {
		return bk_tx_tlr_no;
	}
	public void setBk_tx_tlr_no(String bk_tx_tlr_no) {
		this.bk_tx_tlr_no = bk_tx_tlr_no;
	}
	public String getBk_rckr_no() {
		return bk_rckr_no;
	}
	public void setBk_rckr_no(String bk_rckr_no) {
		this.bk_rckr_no = bk_rckr_no;
	}
	public String getBk_tx_instn() {
		return bk_tx_instn;
	}
	public void setBk_tx_instn(String bk_tx_instn) {
		this.bk_tx_instn = bk_tx_instn;
	}
	public String getBk_tx_tm() {
		return bk_tx_tm;
	}
	public void setBk_tx_tm(String bk_tx_tm) {
		this.bk_tx_tm = bk_tx_tm;
	}
	public String getTxnserialno() {
		return txnserialno;
	}
	public void setTxnserialno(String txnserialno) {
		this.txnserialno = txnserialno;
	}
	public String getPreBizNo() {
		return preBizNo;
	}
	public void setPreBizNo(String preBizNo) {
		this.preBizNo = preBizNo;
	}
	public String getOrgno() {
		return orgno;
	}
	public void setOrgno(String orgno) {
		this.orgno = orgno;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOptstate() {
		return optstate;
	}
	public void setOptstate(String optstate) {
		this.optstate = optstate;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	

	
}
