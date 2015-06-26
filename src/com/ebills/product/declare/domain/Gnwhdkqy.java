package com.ebills.product.declare.domain;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import com.ebills.product.declare.Entity;


/**
 * 国内外汇贷款--签约信息 SbGnwhdkqy entity. @twteam 对应表名:SB_GNWHDKQY
 */

public class Gnwhdkqy extends Entity {
	// 操作类型
	private String actiontype;
	// 删除原因
	private String actiondesc;
	// 国内外汇贷款编号
	private String dofoexlocode;
	// 债权人代码
	private String creditorcode;
	// 债务人代码
	private String debtorcode;
	// 债务人中文名称
	private String debtorname;
	// 国内外汇贷款类型
	private String dofoexlotype;
	// 转贷项目名称
	private String lenproname;
	// 转贷协议号
	private String lenagree;
	// 起息日
	private Date valuedate;
	// 到期日
	private Date maturity;
	// 贷款币种
	private String currence;
	// 签约金额
	private BigDecimal contractamount;
	// 年化利率值
	private double anninrate;
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
	// 操作流水号
	private String nguid;
	// 老流水号
	private String oguid;
	// 接收/创建时间
	private Date recdate;
	// 数据来源
	private String datasources;
	// 核心虚拟外债编号 烟台银行加     由于烟台银行核心不能反馈外债编号数据，所以由其返回一个虚拟的外债编号保存到国结，该虚拟外债编号由机构号+日期+随机数组成
	private String hxxnwzbh;

	public Gnwhdkqy() {
		this.setContractamount(new BigDecimal(0));
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

	public String getDofoexlocode() {
		return dofoexlocode;
	}

	public void setDofoexlocode(String dofoexlocode) {
		this.dofoexlocode = dofoexlocode;
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

	public String getDofoexlotype() {
		return dofoexlotype;
	}

	public void setDofoexlotype(String dofoexlotype) {
		this.dofoexlotype = dofoexlotype;
	}

	public String getLenproname() {
		return lenproname;
	}

	public void setLenproname(String lenproname) {
		this.lenproname = lenproname;
	}

	public String getLenagree() {
		return lenagree;
	}

	public void setLenagree(String lenagree) {
		this.lenagree = lenagree;
	}

	public Date getValuedate() {
		return valuedate;
	}

	public void setValuedate(Date valuedate) {
		this.valuedate = valuedate;
	}

	public Date getMaturity() {
		return maturity;
	}

	public void setMaturity(Date maturity) {
		this.maturity = maturity;
	}

	public String getCurrence() {
		return currence;
	}

	public void setCurrence(String currence) {
		this.currence = currence;
	}

	public BigDecimal getContractamount() {
		return contractamount;
	}

	public void setContractamount(BigDecimal contractamount) {
		this.contractamount = contractamount;
	}

	public double getAnninrate() {
		return anninrate;
	}

	public void setAnninrate(double anninrate) {
		this.anninrate = anninrate;
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

	public String getNguid() {
		return nguid;
	}

	public void setNguid(String nguid) {
		this.nguid = nguid;
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