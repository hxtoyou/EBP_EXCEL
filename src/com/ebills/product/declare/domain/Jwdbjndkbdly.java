package com.ebills.product.declare.domain;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import com.ebills.product.declare.Entity;

/**
 * 境外担保项下境内贷款—变动及履约信息 SbJwdbjndkbdly entity. @twteam 对应表名：SB_JWDBJNDKBDLY
 */

public class Jwdbjndkbdly extends Entity {
	// 操作流水号
	private String nguid;
	// 操作类型
	private String actiontype;
	// 删除原因
	private String actiondesc;
	// 外保内贷编号
	private String lounexgucode;
	// 银行业务参号
	private String buscode;
	// 变动编号
	private String changeno;
	// 变动日期
	private Date changedate;
	// 变动币种
	private String credcurrcode;
	// 提款金额
	private BigDecimal credwithamount;
	// 还本金额
	private BigDecimal credrepayamount;
	// 付息费金额
	private BigDecimal picamount;
	// 贷款余额
	private BigDecimal credprinbala;
	// 担保责任余额
	private BigDecimal guarantlibala;
	// 担保履约金额
	private BigDecimal guperamount;
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

	private Date recdate;

	private String datasources;
	// 核心虚拟外债编号 烟台银行加      由于烟台银行核心不能反馈外债编号数据，所以由其返回一个虚拟的外债编号保存到国结，该虚拟外债编号由机构号+日期+随机数组成
	private String hxxnwzbh;

	public Jwdbjndkbdly() {
		this.setCredprinbala(new BigDecimal(0));
		this.setGuarantlibala(new BigDecimal(0));
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

	public String getLounexgucode() {
		return lounexgucode;
	}

	public void setLounexgucode(String lounexgucode) {
		this.lounexgucode = lounexgucode;
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

	public Date getChangedate() {
		return changedate;
	}

	public void setChangedate(Date changedate) {
		this.changedate = changedate;
	}

	public String getCredcurrcode() {
		return credcurrcode;
	}

	public void setCredcurrcode(String credcurrcode) {
		this.credcurrcode = credcurrcode;
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

	public BigDecimal getCredprinbala() {
		return credprinbala;
	}

	public void setCredprinbala(BigDecimal credprinbala) {
		this.credprinbala = credprinbala;
	}

	public BigDecimal getGuarantlibala() {
		return guarantlibala;
	}

	public void setGuarantlibala(BigDecimal guarantlibala) {
		this.guarantlibala = guarantlibala;
	}

	public BigDecimal getGuperamount() {
		return guperamount;
	}

	public void setGuperamount(BigDecimal guperamount) {
		this.guperamount = guperamount;
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