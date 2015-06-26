package com.ebills.product.declare.domain;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import com.ebills.product.declare.Entity;


/**
 * 商业银行人民币结构性存款业务-签约信息 SbShrmbckqy entity. @author MyEclipse Persistence Tools
 * 对应的表名：sb_Shrmbckqy
 */

public class Shrmbckqy extends Entity {
	// 操作类型
	private String actiontype;
	// 删除原因
	private String actiondesc;
	// 约定的利率下限
	private double aginralo;
	// 利息给付方式
	private String aginraloinpay;
	// 约定的利率上限
	private double aginraup;
	// 授权时间
	private Timestamp authdate;
	// 授权人
	private int authid;
	// 机构编号
	private String bank_Id;
	// 金融机构标识码
	private String branchcode;
	// 复核时
	private Timestamp checkdate;
	// 复核人
	private int checkid;
	// 客户代码
	private String clientcode;
	// 客户名称
	private String clientname;
	// 合同号
	private String contract;
	// 签约金额
	private BigDecimal contractamount;
	// 签约日期
	private Date contractdate;
	// 数据来源
	private String datasources;
	// 经办日期
	private Timestamp handdate;
	// 经办人
	private int handid;
	// 授权是否通
	private String isauth;
	// 是否复核通过
	private String ischeck;
	// 挂钩指标
	private String lincame;
	// 挂钩指标计算方法
	private String lincamethod;
	// 到期日
	private Date maturity;
	// 新流水号
	private String nguid;
	// 老业务编号
	private String oguid;
	private Date recdate;
	// 备注
	private String remark;
	// 业务流水号
	private String rwidh;
	// 是否已申报
	private String sfysb;
	// 最新标志
	private String sfzx;
	// 人民币结构性存款编号
	private String strdecode;
	// 生效标志
	private String sxbz;
	// 业务编号
	private String ywbh;
	// 核心虚拟外债编号 烟台银行加     由于烟台银行核心不能反馈外债编号数据，所以由其返回一个虚拟的外债编号保存到国结，该虚拟外债编号由机构号+日期+随机数组成
	private String hxxnwzbh;

	public Shrmbckqy() {
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

	public double getAginralo() {
		return aginralo;
	}

	public void setAginralo(double aginralo) {
		this.aginralo = aginralo;
	}

	public String getAginraloinpay() {
		return aginraloinpay;
	}

	public void setAginraloinpay(String aginraloinpay) {
		this.aginraloinpay = aginraloinpay;
	}

	public double getAginraup() {
		return aginraup;
	}

	public void setAginraup(double aginraup) {
		this.aginraup = aginraup;
	}

	public Timestamp getAuthdate() {
		return authdate;
	}

	public void setAuthdate(Timestamp authdate) {
		this.authdate = authdate;
	}

	public int getAuthid() {
		return authid;
	}

	public void setAuthid(int authid) {
		this.authid = authid;
	}

	public String getBank_Id() {
		return bank_Id;
	}

	public void setBank_Id(String bank_Id) {
		this.bank_Id = bank_Id;
	}

	public String getBranchcode() {
		return branchcode;
	}

	public void setBranchcode(String branchcode) {
		this.branchcode = branchcode;
	}

	public Timestamp getCheckdate() {
		return checkdate;
	}

	public void setCheckdate(Timestamp checkdate) {
		this.checkdate = checkdate;
	}

	public int getCheckid() {
		return checkid;
	}

	public void setCheckid(int checkid) {
		this.checkid = checkid;
	}

	public String getClientcode() {
		return clientcode;
	}

	public void setClientcode(String clientcode) {
		this.clientcode = clientcode;
	}

	public String getClientname() {
		return clientname;
	}

	public void setClientname(String clientname) {
		this.clientname = clientname;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public BigDecimal getContractamount() {
		return contractamount;
	}

	public void setContractamount(BigDecimal contractamount) {
		this.contractamount = contractamount;
	}

	public Date getContractdate() {
		return contractdate;
	}

	public void setContractdate(Date contractdate) {
		this.contractdate = contractdate;
	}

	public String getDatasources() {
		return datasources;
	}

	public void setDatasources(String datasources) {
		this.datasources = datasources;
	}

	public Timestamp getHanddate() {
		return handdate;
	}

	public void setHanddate(Timestamp handdate) {
		this.handdate = handdate;
	}

	public int getHandid() {
		return handid;
	}

	public void setHandid(int handid) {
		this.handid = handid;
	}

	public String getIsauth() {
		return isauth;
	}

	public void setIsauth(String isauth) {
		this.isauth = isauth;
	}

	public String getIscheck() {
		return ischeck;
	}

	public void setIscheck(String ischeck) {
		this.ischeck = ischeck;
	}

	public String getLincame() {
		return lincame;
	}

	public void setLincame(String lincame) {
		this.lincame = lincame;
	}

	public String getLincamethod() {
		return lincamethod;
	}

	public void setLincamethod(String lincamethod) {
		this.lincamethod = lincamethod;
	}

	public Date getMaturity() {
		return maturity;
	}

	public void setMaturity(Date maturity) {
		this.maturity = maturity;
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

	public String getSfysb() {
		return sfysb;
	}

	public void setSfysb(String sfysb) {
		this.sfysb = sfysb;
	}

	public String getSfzx() {
		return sfzx;
	}

	public void setSfzx(String sfzx) {
		this.sfzx = sfzx;
	}

	public String getStrdecode() {
		return strdecode;
	}

	public void setStrdecode(String strdecode) {
		this.strdecode = strdecode;
	}

	public String getSxbz() {
		return sxbz;
	}

	public void setSxbz(String sxbz) {
		this.sxbz = sxbz;
	}

	public String getYwbh() {
		return ywbh;
	}

	public void setYwbh(String ywbh) {
		this.ywbh = ywbh;
	}

	public String getHxxnwzbh() {
		return hxxnwzbh;
	}

	public void setHxxnwzbh(String hxxnwzbh) {
		this.hxxnwzbh = hxxnwzbh;
	}
}