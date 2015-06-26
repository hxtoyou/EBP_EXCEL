package com.ebills.product.declare.domain;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import com.ebills.product.declare.Entity;

/**
 * 对外担保-履约信息 SbDwdbly entity. @twteam 对应表名：SB_DWDBLY
 */

public class Dwdbly extends Entity {
	// 操作流水号
	private String nguid;
	// 操作类型
	private String actiontype;
	// 删除原因
	private String actiondesc;
	// 对外担保编号
	private String exguarancode;
	// 履约编号
	private String complianceno;
	// 担保人代码
	private String guarantorcode;
	// 银行业务参号
	private String buscode;
	// 受益人代码
	private String bencode;
	// 受益人中文名称
	private String bename;
	// 受益人英文名称
	private String benamen;
	// 履约日期
	private Date guperdate;
	// 履约币种
	private String guperdurr;
	// 履约金额
	private BigDecimal guperamount;
	// 购汇履约金额
	private BigDecimal pguperamount;
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
	// 核心虚拟外债编号 烟台银行加      由于烟台银行核心不能反馈外债编号数据，所以由其返回一个虚拟的外债编号保存到国结，该虚拟外债编号由机构号+日期+随机数组成
	private String hxxnwzbh;

	public Dwdbly() {
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

	public String getExguarancode() {
		return exguarancode;
	}

	public void setExguarancode(String exguarancode) {
		this.exguarancode = exguarancode;
	}

	public String getComplianceno() {
		return complianceno;
	}

	public void setComplianceno(String complianceno) {
		this.complianceno = complianceno;
	}

	public String getGuarantorcode() {
		return guarantorcode;
	}

	public void setGuarantorcode(String guarantorcode) {
		this.guarantorcode = guarantorcode;
	}

	public String getBuscode() {
		return buscode;
	}

	public void setBuscode(String buscode) {
		this.buscode = buscode;
	}

	public String getBencode() {
		return bencode;
	}

	public void setBencode(String bencode) {
		this.bencode = bencode;
	}

	public String getBename() {
		return bename;
	}

	public void setBename(String bename) {
		this.bename = bename;
	}

	public String getBenamen() {
		return benamen;
	}

	public void setBenamen(String benamen) {
		this.benamen = benamen;
	}

	public Date getGuperdate() {
		return guperdate;
	}

	public void setGuperdate(Date guperdate) {
		this.guperdate = guperdate;
	}

	public String getGuperdurr() {
		return guperdurr;
	}

	public void setGuperdurr(String guperdurr) {
		this.guperdurr = guperdurr;
	}

	public BigDecimal getGuperamount() {
		return guperamount;
	}

	public void setGuperamount(BigDecimal guperamount) {
		this.guperamount = guperamount;
	}

	public BigDecimal getPguperamount() {
		return pguperamount;
	}

	public void setPguperamount(BigDecimal pguperamount) {
		this.pguperamount = pguperamount;
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