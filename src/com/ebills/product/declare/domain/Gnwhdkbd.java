package com.ebills.product.declare.domain;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import com.ebills.product.declare.Entity;

/**
 * 国内外汇贷款—变动信息 SbGnwhdkbd entity. @twteam 对应表名：SB_GNWHDKBD
 */

public class Gnwhdkbd extends Entity {

	// 操作流水号
	private String nguid;
	// 操作类型
	private String actiontype;
	// 删除原因
	private String actiondesc;
	// 国内外汇贷款编号
	private String dofoexlocode;
	// 银行业务参号
	private String buscode;
	// 变动编号
	private String changeno;
	// 期初余额
	private BigDecimal loanopenbalan;
	// 变动日期
	private Date changedate;
	// 提款币种
	private String withcurrence;
	// 提款金额
	private BigDecimal withamount;
	// 结汇金额
	private BigDecimal settamount;
	// 资金用途
	private String useofunds;
	// 还本币种
	private String princurr;
	// 还本金额
	private BigDecimal repayamount;
	// 购汇还本金额
	private BigDecimal prepayamount;
	// 付息币种
	private String inpaycurr;
	// 付息金额
	private BigDecimal inpayamount;
	// 购汇付息金额
	private BigDecimal pinpayamount;
	// 期末余额
	private BigDecimal endbalan;
	// 备注
	private String remark;
	// 业务流水号
	private String rwidh;
	// 业务编号
	private String ywbh;
	// 是否已申报
	private String sfysb;
	// 生效标志
	private String sxbz;
	// 最新标志
	private String sfzx;
	// 机构编号
	private String bank_Id;
	// 经办人
	private int handid;
	// 经办日期
	private Timestamp handdate;
	// 复核人
	private int checkid;
	// 复核时间
	private Timestamp checkdate;
	// 是否复核通过
	private String ischeck;
	// 授权人
	private int authid;
	// 授权时间
	private Timestamp authdate;
	// 授权是否通过
	private String isauth;

	private String oguid;
	// 接收/创建时间
	private Date recdate;
	// 数据来源
	private String datasources;
	// 核心虚拟外债编号 烟台银行加    由于烟台银行核心不能反馈外债编号数据，所以由其返回一个虚拟的外债编号保存到国结，该虚拟外债编号由机构号+日期+随机数组成
	private String hxxnwzbh;

	public Gnwhdkbd() {
		this.setEndbalan(new BigDecimal(0));
		this.setLoanopenbalan(new BigDecimal(0));
		this.setActiontype("A");
		this.setSfysb("N");
		this.setSfzx("Y");
		this.setSxbz("0");
	}

	public String getNguid() {
		return nguid;
	}

	public void setNguid(String nguid) {
		this.nguid = nguid;
	}

	public String getActiontype() {
		return actiontype;
	}

	public void setActiontype(String actiontype) {
		this.actiontype = actiontype;
	}

	public String getActiondesc() {
		return actiondesc;
	}

	public void setActiondesc(String actiondesc) {
		this.actiondesc = actiondesc;
	}

	public String getDofoexlocode() {
		return dofoexlocode;
	}

	public void setDofoexlocode(String dofoexlocode) {
		this.dofoexlocode = dofoexlocode;
	}

	public String getBuscode() {
		return buscode;
	}

	public void setBuscode(String buscode) {
		this.buscode = buscode;
	}

	public String getChangeno() {
		return changeno;
	}

	public void setChangeno(String changeno) {
		this.changeno = changeno;
	}

	public BigDecimal getLoanopenbalan() {
		return loanopenbalan;
	}

	public void setLoanopenbalan(BigDecimal loanopenbalan) {
		this.loanopenbalan = loanopenbalan;
	}

	public Date getChangedate() {
		return changedate;
	}

	public void setChangedate(Date changedate) {
		this.changedate = changedate;
	}

	public String getWithcurrence() {
		return withcurrence;
	}

	public void setWithcurrence(String withcurrence) {
		this.withcurrence = withcurrence;
	}

	public BigDecimal getWithamount() {
		return withamount;
	}

	public void setWithamount(BigDecimal withamount) {
		this.withamount = withamount;
	}

	public BigDecimal getSettamount() {
		return settamount;
	}

	public void setSettamount(BigDecimal settamount) {
		this.settamount = settamount;
	}

	public String getUseofunds() {
		return useofunds;
	}

	public void setUseofunds(String useofunds) {
		this.useofunds = useofunds;
	}

	public String getPrincurr() {
		return princurr;
	}

	public void setPrincurr(String princurr) {
		this.princurr = princurr;
	}

	public BigDecimal getRepayamount() {
		return repayamount;
	}

	public void setRepayamount(BigDecimal repayamount) {
		this.repayamount = repayamount;
	}

	public BigDecimal getPrepayamount() {
		return prepayamount;
	}

	public void setPrepayamount(BigDecimal prepayamount) {
		this.prepayamount = prepayamount;
	}

	public String getInpaycurr() {
		return inpaycurr;
	}

	public void setInpaycurr(String inpaycurr) {
		this.inpaycurr = inpaycurr;
	}

	public BigDecimal getInpayamount() {
		return inpayamount;
	}

	public void setInpayamount(BigDecimal inpayamount) {
		this.inpayamount = inpayamount;
	}

	public BigDecimal getPinpayamount() {
		return pinpayamount;
	}

	public void setPinpayamount(BigDecimal pinpayamount) {
		this.pinpayamount = pinpayamount;
	}

	public BigDecimal getEndbalan() {
		return endbalan;
	}

	public void setEndbalan(BigDecimal endbalan) {
		this.endbalan = endbalan;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRwidh() {
		return rwidh;
	}

	public void setRwidh(String rwidh) {
		this.rwidh = rwidh;
	}

	public String getYwbh() {
		return ywbh;
	}

	public void setYwbh(String ywbh) {
		this.ywbh = ywbh;
	}

	public String getSfysb() {
		return sfysb;
	}

	public void setSfysb(String sfysb) {
		this.sfysb = sfysb;
	}

	public String getSxbz() {
		return sxbz;
	}

	public void setSxbz(String sxbz) {
		this.sxbz = sxbz;
	}

	public String getSfzx() {
		return sfzx;
	}

	public void setSfzx(String sfzx) {
		this.sfzx = sfzx;
	}

	public String getBank_Id() {
		return bank_Id;
	}

	public void setBank_Id(String bank_Id) {
		this.bank_Id = bank_Id;
	}

	public int getHandid() {
		return handid;
	}

	public void setHandid(int handid) {
		this.handid = handid;
	}

	public Timestamp getHanddate() {
		return handdate;
	}

	public void setHanddate(Timestamp handdate) {
		this.handdate = handdate;
	}

	public int getCheckid() {
		return checkid;
	}

	public void setCheckid(int checkid) {
		this.checkid = checkid;
	}

	public Timestamp getCheckdate() {
		return checkdate;
	}

	public void setCheckdate(Timestamp checkdate) {
		this.checkdate = checkdate;
	}

	public String getIscheck() {
		return ischeck;
	}

	public void setIscheck(String ischeck) {
		this.ischeck = ischeck;
	}

	public int getAuthid() {
		return authid;
	}

	public void setAuthid(int authid) {
		this.authid = authid;
	}

	public Timestamp getAuthdate() {
		return authdate;
	}

	public void setAuthdate(Timestamp authdate) {
		this.authdate = authdate;
	}

	public String getIsauth() {
		return isauth;
	}

	public void setIsauth(String isauth) {
		this.isauth = isauth;
	}

	public String getOguid() {
		return oguid;
	}

	public void setOguid(String oguid) {
		this.oguid = oguid;
	}

	public Date getRecdate() {
		return recdate;
	}

	public void setRecdate(Date recdate) {
		this.recdate = recdate;
	}

	public String getDatasources() {
		return datasources;
	}

	public void setDatasources(String datasources) {
		this.datasources = datasources;
	}

	public String getHxxnwzbh() {
		return hxxnwzbh;
	}

	public void setHxxnwzbh(String hxxnwzbh) {
		this.hxxnwzbh = hxxnwzbh;
	}
}