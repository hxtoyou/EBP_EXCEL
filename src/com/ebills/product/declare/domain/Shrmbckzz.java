package com.ebills.product.declare.domain;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import com.ebills.product.declare.Entity;


/**
 * SbShrmbckzz entity. @author MyEclipse Persistence Tools 对应的表名：sb_Shrmbckzz
 */

public class Shrmbckzz extends Entity {
	// 操作流水号
	private String nguid;
	// 操作类型
	private String actiontype;
	// 删除原因
	private String actiondesc;
	// 人民币结构性存款编号
	private String strdecode;
	// 金融机构标识码
	private String branchcode;
	// 终止类型
	private String tertype;
	// 终止支付编号
	private String terpaycode;
	// 合同号
	private String contract;
	// 终止日期
	private Date terdate;
	// 终止支付金额合计折人民币
	private BigDecimal terpayamtormb;
	// 终止人民币支付金额
	private BigDecimal terrmbpayam;
	// 终止外币支付币种
	private String terpaycurr;
	// 终止外币支付金额
	private BigDecimal terpaycurram;
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
	private String oguid;
	// Property accessors
	private Date recdate;
	private String datasources;
	// 核心虚拟外债编号 烟台银行加
	// 由于烟台银行核心不能反馈外债编号数据，所以由其返回一个虚拟的外债编号保存到国结，该虚拟外债编号由机构号+日期+随机数组成
	private String hxxnwzbh;

	public Shrmbckzz() {
		this.setTerpayamtormb(new BigDecimal(0));
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

	public String getTertype() {
		return tertype;
	}

	public void setTertype(String tertype) {
		this.tertype = tertype;
	}

	public String getTerpaycode() {
		return terpaycode;
	}

	public void setTerpaycode(String terpaycode) {
		this.terpaycode = terpaycode;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public Date getTerdate() {
		return terdate;
	}

	public void setTerdate(Date terdate) {
		this.terdate = terdate;
	}

	public BigDecimal getTerpayamtormb() {
		return terpayamtormb;
	}

	public void setTerpayamtormb(BigDecimal terpayamtormb) {
		this.terpayamtormb = terpayamtormb;
	}

	public BigDecimal getTerrmbpayam() {
		return terrmbpayam;
	}

	public void setTerrmbpayam(BigDecimal terrmbpayam) {
		this.terrmbpayam = terrmbpayam;
	}

	public String getTerpaycurr() {
		return terpaycurr;
	}

	public void setTerpaycurr(String terpaycurr) {
		this.terpaycurr = terpaycurr;
	}

	public BigDecimal getTerpaycurram() {
		return terpaycurram;
	}

	public void setTerpaycurram(BigDecimal terpaycurram) {
		this.terpaycurram = terpaycurram;
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