package com.ebills.product.declare.domain;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Date;

import com.ebills.product.declare.Entity;

/**
 * 外债海外代付—签约信息 SbHwdf entity. @twteam 对应表名：SB_HWDF
 */

public class Hwdf extends Entity {
	// 操作类型;A－新建C－修改D－删除。已经有收支余信息的账户不能删除。
	private String actiontype;
	// 删除原因
	private String actiondesc;
	// 外债编号
	private String exdebtcode;
	// 债务人代码
	private String debtorcode;
	// 债务类型
	private String debtype;
	// 起息日
	private Date valuedate;
	// 签约币种
	private String contractcurr;
	// 签约金额
	private BigDecimal contractamount;
	// 到期日
	private Date maturity;
	// 是否浮动利率
	private String floatrate;
	// 年化利率值
	private double anninrate;
	// 债权人代码
	private String creditorcode;
	// 债权人中文名称
	private String creditorname;
	// 债权人英文名称
	private String creditornamen;
	// 债权人类型代码
	private String creditortype;
	// 债权人总部所在国家（地区）代码
	private String crehqcode;
	// 债权人经营地所在国家（地区）代码
	private String opercode;
	// 申请人代码
	private String appcode;
	// 申请人名称
	private String appname;
	// 承继的远期信用证承兑银行业务参号
	private String inltcabuscode;
	// 是否经外汇局特批不需占用指标
	private String spapfeboindex;
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
	//
	// 接收/创建时间
	private Date recdate;
	// 数据来源
	private String datasources;
	// 操作流水号
	private String nguid;
	private String oguid;
	// 核心虚拟外债编号 烟台银行加    由于烟台银行核心不能反馈外债编号数据，所以由其返回一个虚拟的外债编号保存到国结，该虚拟外债编号由机构号+日期+随机数组成
	private String hxxnwzbh;

	public Hwdf() {
		this.setContractamount(new BigDecimal(0));
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

	public String getExdebtcode() {
		return exdebtcode;
	}

	public void setExdebtcode(String exdebtcode) {
		this.exdebtcode = exdebtcode;
	}

	public String getDebtorcode() {
		return debtorcode;
	}

	public void setDebtorcode(String debtorcode) {
		this.debtorcode = debtorcode;
	}

	public String getDebtype() {
		return debtype;
	}

	public void setDebtype(String debtype) {
		this.debtype = debtype;
	}

	public Date getValuedate() {
		return valuedate;
	}

	public void setValuedate(Date valuedate) {
		this.valuedate = valuedate;
	}

	public String getContractcurr() {
		return contractcurr;
	}

	public void setContractcurr(String contractcurr) {
		this.contractcurr = contractcurr;
	}

	public BigDecimal getContractamount() {
		return contractamount;
	}

	public void setContractamount(BigDecimal contractamount) {
		this.contractamount = contractamount;
	}

	public Date getMaturity() {
		return maturity;
	}

	public void setMaturity(Date maturity) {
		this.maturity = maturity;
	}

	public String getFloatrate() {
		return floatrate;
	}

	public void setFloatrate(String floatrate) {
		this.floatrate = floatrate;
	}

	public double getAnninrate() {
		return anninrate;
	}

	public void setAnninrate(double anninrate) {
		this.anninrate = anninrate;
	}

	public String getCreditorcode() {
		return creditorcode;
	}

	public void setCreditorcode(String creditorcode) {
		this.creditorcode = creditorcode;
	}

	public String getCreditorname() {
		return creditorname;
	}

	public void setCreditorname(String creditorname) {
		this.creditorname = creditorname;
	}

	public String getCreditornamen() {
		return creditornamen;
	}

	public void setCreditornamen(String creditornamen) {
		this.creditornamen = creditornamen;
	}

	public String getCreditortype() {
		return creditortype;
	}

	public void setCreditortype(String creditortype) {
		this.creditortype = creditortype;
	}

	public String getCrehqcode() {
		return crehqcode;
	}

	public void setCrehqcode(String crehqcode) {
		this.crehqcode = crehqcode;
	}

	public String getOpercode() {
		return opercode;
	}

	public void setOpercode(String opercode) {
		this.opercode = opercode;
	}

	public String getAppcode() {
		return appcode;
	}

	public void setAppcode(String appcode) {
		this.appcode = appcode;
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public String getInltcabuscode() {
		return inltcabuscode;
	}

	public void setInltcabuscode(String inltcabuscode) {
		this.inltcabuscode = inltcabuscode;
	}

	public String getSpapfeboindex() {
		return spapfeboindex;
	}

	public void setSpapfeboindex(String spapfeboindex) {
		this.spapfeboindex = spapfeboindex;
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

	public String getOguid() {
		return oguid;
	}

	public void setOguid(String oguid) {
		this.oguid = oguid;
	}

	public String getHxxnwzbh() {
		return hxxnwzbh;
	}

	public void setHxxnwzbh(String hxxnwzbh) {
		this.hxxnwzbh = hxxnwzbh;
	}
}