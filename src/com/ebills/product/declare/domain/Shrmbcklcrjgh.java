package com.ebills.product.declare.domain;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import com.ebills.product.declare.Entity;


/**
 * SbShrmbcklcrjgh entity. @author MyEclipse Persistence Tools
 * 对应的表名：sb_Shrmbcklcrjgh
 */

public class Shrmbcklcrjgh extends Entity {
	// 操作流水号
	private String nguid;
	// 操作类型
	private String actiontype;
	// 删除原因
	private String actiondesc;
	// 人民币结构性存款编号
	private String strdecode; // 36修改《国家外汇管理局关于规范境内银行资本项目数据报送的通知》（汇发[2012]36号文）相关问题解答（第一期）（三）中删除“<STRDECODE>人民币结构性存款编号 //保留但不使用
	// 金融机构标识码
	private String branchcode;
	// 报告期
	private int buocmonth;
	// 币种
	private String currency;
	// 本月汇出金额折美元
	private BigDecimal moexamusd;
	// 本月汇入金额折美元
	private BigDecimal moamreusd;
	// 本月购汇金额折美元
	private BigDecimal mopfexamusd;
	// 本月结汇金额折美元
	private BigDecimal mosettamusd;
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

	public Shrmbcklcrjgh() {
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

	public String getStrdecode() {
		return strdecode;
	}

	public void setStrdecode(String strdecode) {
		this.strdecode = strdecode;
	}

	public String getBranchcode() {
		return branchcode;
	}

	public void setBranchcode(String branchcode) {
		this.branchcode = branchcode;
	}

	public int getBuocmonth() {
		return buocmonth;
	}

	public void setBuocmonth(int buocmonth) {
		this.buocmonth = buocmonth;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getMoexamusd() {
		return moexamusd;
	}

	public void setMoexamusd(BigDecimal moexamusd) {
		this.moexamusd = moexamusd;
	}

	public BigDecimal getMoamreusd() {
		return moamreusd;
	}

	public void setMoamreusd(BigDecimal moamreusd) {
		this.moamreusd = moamreusd;
	}

	public BigDecimal getMopfexamusd() {
		return mopfexamusd;
	}

	public void setMopfexamusd(BigDecimal mopfexamusd) {
		this.mopfexamusd = mopfexamusd;
	}

	public BigDecimal getMosettamusd() {
		return mosettamusd;
	}

	public void setMosettamusd(BigDecimal mosettamusd) {
		this.mosettamusd = mosettamusd;
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
}