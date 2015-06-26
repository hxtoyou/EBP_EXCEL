package com.ebills.product.declare.domain;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import com.ebills.product.declare.Entity;

/**
 * 外汇质押人民币贷款—变动信息 SbWhzyrmbdkbd entity. @twteam 对应表名：SB_WHZYRMBDKBD
 */

public class Whzyrmbdkbd extends Entity {
	// 操作类型
	private String actiontype;
	// 删除原因
	private String actiondesc;
	// 外汇质押人民币贷款编号
	private String explrmblono;
	// 报告期
	private String buscode;
	// 变动编号
	private String changeno;
	// 月初贷款余额
	private BigDecimal monbeloadbal;
	// 本月提款金额
	private BigDecimal credwithamount;
	// 本月还本金额
	private BigDecimal credrepayamount;
	// 本月付息费金额
	private BigDecimal picamount;
	// 月末贷款余额
	private BigDecimal monenloadbal;
	// 外汇质押币种
	private String explcurr; 
	// 质押外汇余额
	private BigDecimal explbala;
	// 质押外汇履约金额
	private BigDecimal explperamount;
	// 质押履约结汇金额
	private BigDecimal plcoseamount;
	// 外汇质押多种情况存储
	private String explcurrInfos;
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
	// 旧流水号
	private String oguid;
	// 操作流水号
	private String nguid;
	// 接收数据
	private Date recdate;
	// 数据来源
	private String datasources;
	// 核心虚拟外债编号 烟台银行加     由于烟台银行核心不能反馈外债编号数据，所以由其返回一个虚拟的外债编号保存到国结，该虚拟外债编号由机构号+日期+随机数组成
	private String hxxnwzbh;

	public Whzyrmbdkbd() {
		this.setExplbala(new BigDecimal(0));
		this.setMonbeloadbal(new BigDecimal(0));
		this.setMonenloadbal(new BigDecimal(0));
		this.setActiontype("A");
		this.setSfysb("N");
		this.setSfzx("Y");
		this.setSxbz("0");
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

	public String getExplrmblono() {
		return explrmblono;
	}

	public void setExplrmblono(String explrmblono) {
		this.explrmblono = explrmblono;
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

	public BigDecimal getMonbeloadbal() {
		return monbeloadbal;
	}

	public void setMonbeloadbal(BigDecimal monbeloadbal) {
		this.monbeloadbal = monbeloadbal;
	}

	public BigDecimal getCredwithamount() {
		return credwithamount;
	}

	public void setCredwithamount(BigDecimal credwithamount) {
		this.credwithamount = credwithamount;
	}

	public BigDecimal getCredrepayamount() {
		return credrepayamount;
	}

	public void setCredrepayamount(BigDecimal credrepayamount) {
		this.credrepayamount = credrepayamount;
	}

	public BigDecimal getPicamount() {
		return picamount;
	}

	public void setPicamount(BigDecimal picamount) {
		this.picamount = picamount;
	}

	public BigDecimal getMonenloadbal() {
		return monenloadbal;
	}

	public void setMonenloadbal(BigDecimal monenloadbal) {
		this.monenloadbal = monenloadbal;
	}

	public BigDecimal getExplbala() {
		return explbala;
	}

	public void setExplbala(BigDecimal explbala) {
		this.explbala = explbala;
	}

	public BigDecimal getExplperamount() {
		return explperamount;
	}

	public void setExplperamount(BigDecimal explperamount) {
		this.explperamount = explperamount;
	}

	public BigDecimal getPlcoseamount() {
		return plcoseamount;
	}

	public void setPlcoseamount(BigDecimal plcoseamount) {
		this.plcoseamount = plcoseamount;
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

	public String getNguid() {
		return nguid;
	}

	public void setNguid(String nguid) {
		this.nguid = nguid;
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

	public String getExplcurr() {
		return explcurr;
	}

	public void setExplcurr(String explcurr) {
		this.explcurr = explcurr;
	}

	public String getExplcurrInfos() {
		return explcurrInfos;
	}

	public void setExplcurrInfos(String explcurrInfos) {
		this.explcurrInfos = explcurrInfos;
	}

	public String getHxxnwzbh() {
		return hxxnwzbh;
	}

	public void setHxxnwzbh(String hxxnwzbh) {
		this.hxxnwzbh = hxxnwzbh;
	}
}