package com.ebills.product.declare.domain;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import com.ebills.product.declare.Entity;

/**
 * 外汇质押人民币贷款—签约信息 SbWhzyrmbdkqy entity. @twteam 对应表名：SB_WHZYRMBDKQY
 */

public class Whzyrmbdkqy extends Entity {
	// 操作类型
	private String actiontype;
	// 删除原因
	private String actiondesc;
	// 外汇质押人民币贷款编号
	private String explrmblono;
	// 债权人代码
	private String creditorcode;
	// 债务人代码
	private String debtorcode;
	// 债务人中文名称
	private String debtorname;
	// 贷款起息日
	private Date valuedate;
	// 贷款签约币种
	private String credconcurr;
	// 贷款签约金额
	private BigDecimal credconamount;
	// 贷款到期日
	private Date maturity;
	// 质押外汇币种
	private String explcurr;
	// 质押外汇金额
	private BigDecimal explamount;
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
	// 质押外汇多种情况存储
	private String explcurrInfos;
	// 核心虚拟外债编号 烟台银行加       由于烟台银行核心不能反馈外债编号数据，所以由其返回一个虚拟的外债编号保存到国结，该虚拟外债编号由机构号+日期+随机数组成
	private String hxxnwzbh;

	public Whzyrmbdkqy() {
		this.setCredconamount(new BigDecimal(0));
		this.setExplamount(new BigDecimal(0));
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

	public String getCreditorcode() {
		return creditorcode;
	}

	public void setCreditorcode(String creditorcode) {
		this.creditorcode = creditorcode;
	}

	public String getDebtorcode() {
		return debtorcode;
	}

	public void setDebtorcode(String debtorcode) {
		this.debtorcode = debtorcode;
	}

	public String getDebtorname() {
		return debtorname;
	}

	public void setDebtorname(String debtorname) {
		this.debtorname = debtorname;
	}

	public Date getValuedate() {
		return valuedate;
	}

	public void setValuedate(Date valuedate) {
		this.valuedate = valuedate;
	}

	public String getCredconcurr() {
		return credconcurr;
	}

	public void setCredconcurr(String credconcurr) {
		this.credconcurr = credconcurr;
	}

	public BigDecimal getCredconamount() {
		return credconamount;
	}

	public void setCredconamount(BigDecimal credconamount) {
		this.credconamount = credconamount;
	}

	public Date getMaturity() {
		return maturity;
	}

	public void setMaturity(Date maturity) {
		this.maturity = maturity;
	}

	public String getExplcurr() {
		return explcurr;
	}

	public void setExplcurr(String explcurr) {
		this.explcurr = explcurr;
	}

	public BigDecimal getExplamount() {
		return explamount;
	}

	public void setExplamount(BigDecimal explamount) {
		this.explamount = explamount;
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