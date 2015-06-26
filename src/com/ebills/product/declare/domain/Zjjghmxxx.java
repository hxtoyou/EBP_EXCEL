package com.ebills.product.declare.domain;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import com.ebills.product.declare.Entity;


/**
 * SbZjjghmxxx entity. @author MyEclipse Persistence Tools
 */

public class Zjjghmxxx extends Entity {
	// 操作流水号
	private String nguid;
	// 托管人代码
	private String apno;
	// QFII机构代码
	private String qfiicod;
	// QFII机构名称
	private String qfiiname;
	// 业务编号
	private String bizo;
	// 产品名称
	private String producename;
	// 账号
	private String accounts;
	// 汇兑日期
	private Date remittancedate;
	// 变动编号
	private String changebizo;
	// 资金性质
	private String fundtype;
	// 汇入币种
	private String importcur;
	// 汇入金额
	private BigDecimal importamt;
	// 汇入金额折美元
	private BigDecimal importusdamt;
	// 结汇币种
	private String jhcur;
	// 结汇金额
	private BigDecimal jhamt;
	// 结汇金额折美元
	private BigDecimal jhusdamt;
	// 结汇所得人民币金额
	private BigDecimal jhcnyamt;
	// 购汇人民币金额
	private BigDecimal ghcnyamt;
	// 购汇币种
	private String ghcur;
	// 购汇所得金额
	private BigDecimal ghamt;
	// 购汇金额折美元
	private BigDecimal ghusdamt;
	// 汇出本金币种
	private String remitbenjincur;
	// 汇出本金金额
	private BigDecimal remitbenjinamt;
	// 汇出本金折美元
	private BigDecimal remitbenjinusdamt;
	// 汇出收益币种
	private String remitincomecur;
	// 汇出收益金额
	private BigDecimal remitincomeamt;
	// 汇出收益折美元
	private BigDecimal remitincomeusdamt;
	// 备注
	private String memo;
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
	private Date recdate;
	private String datasources;
	// 核心虚拟外债编号 烟台银行加       由于烟台银行核心不能反馈外债编号数据，所以由其返回一个虚拟的外债编号保存到国结，该虚拟外债编号由机构号+日期+随机数组成
	private String hxxnwzbh;

	public Zjjghmxxx() {
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

	public String getApno() {
		return apno;
	}

	public void setApno(String apno) {
		this.apno = apno;
	}

	public String getQfiicod() {
		return qfiicod;
	}

	public void setQfiicod(String qfiicod) {
		this.qfiicod = qfiicod;
	}

	public String getQfiiname() {
		return qfiiname;
	}

	public void setQfiiname(String qfiiname) {
		this.qfiiname = qfiiname;
	}

	public String getBizo() {
		return bizo;
	}

	public void setBizo(String bizo) {
		this.bizo = bizo;
	}

	public String getProducename() {
		return producename;
	}

	public void setProducename(String producename) {
		this.producename = producename;
	}

	public String getAccounts() {
		return accounts;
	}

	public void setAccounts(String accounts) {
		this.accounts = accounts;
	}

	public Date getRemittancedate() {
		return remittancedate;
	}

	public void setRemittancedate(Date remittancedate) {
		this.remittancedate = remittancedate;
	}

	public String getChangebizo() {
		return changebizo;
	}

	public void setChangebizo(String changebizo) {
		this.changebizo = changebizo;
	}

	public String getFundtype() {
		return fundtype;
	}

	public void setFundtype(String fundtype) {
		this.fundtype = fundtype;
	}

	public String getImportcur() {
		return importcur;
	}

	public void setImportcur(String importcur) {
		this.importcur = importcur;
	}

	public BigDecimal getImportamt() {
		return importamt;
	}

	public void setImportamt(BigDecimal importamt) {
		this.importamt = importamt;
	}

	public BigDecimal getImportusdamt() {
		return importusdamt;
	}

	public void setImportusdamt(BigDecimal importusdamt) {
		this.importusdamt = importusdamt;
	}

	public String getJhcur() {
		return jhcur;
	}

	public void setJhcur(String jhcur) {
		this.jhcur = jhcur;
	}

	public BigDecimal getJhamt() {
		return jhamt;
	}

	public void setJhamt(BigDecimal jhamt) {
		this.jhamt = jhamt;
	}

	public BigDecimal getJhusdamt() {
		return jhusdamt;
	}

	public void setJhusdamt(BigDecimal jhusdamt) {
		this.jhusdamt = jhusdamt;
	}

	public BigDecimal getJhcnyamt() {
		return jhcnyamt;
	}

	public void setJhcnyamt(BigDecimal jhcnyamt) {
		this.jhcnyamt = jhcnyamt;
	}

	public BigDecimal getGhcnyamt() {
		return ghcnyamt;
	}

	public void setGhcnyamt(BigDecimal ghcnyamt) {
		this.ghcnyamt = ghcnyamt;
	}

	public String getGhcur() {
		return ghcur;
	}

	public void setGhcur(String ghcur) {
		this.ghcur = ghcur;
	}

	public BigDecimal getGhamt() {
		return ghamt;
	}

	public void setGhamt(BigDecimal ghamt) {
		this.ghamt = ghamt;
	}

	public BigDecimal getGhusdamt() {
		return ghusdamt;
	}

	public void setGhusdamt(BigDecimal ghusdamt) {
		this.ghusdamt = ghusdamt;
	}

	public String getRemitbenjincur() {
		return remitbenjincur;
	}

	public void setRemitbenjincur(String remitbenjincur) {
		this.remitbenjincur = remitbenjincur;
	}

	public BigDecimal getRemitbenjinamt() {
		return remitbenjinamt;
	}

	public void setRemitbenjinamt(BigDecimal remitbenjinamt) {
		this.remitbenjinamt = remitbenjinamt;
	}

	public BigDecimal getRemitbenjinusdamt() {
		return remitbenjinusdamt;
	}

	public void setRemitbenjinusdamt(BigDecimal remitbenjinusdamt) {
		this.remitbenjinusdamt = remitbenjinusdamt;
	}

	public String getRemitincomecur() {
		return remitincomecur;
	}

	public void setRemitincomecur(String remitincomecur) {
		this.remitincomecur = remitincomecur;
	}

	public BigDecimal getRemitincomeamt() {
		return remitincomeamt;
	}

	public void setRemitincomeamt(BigDecimal remitincomeamt) {
		this.remitincomeamt = remitincomeamt;
	}

	public BigDecimal getRemitincomeusdamt() {
		return remitincomeusdamt;
	}

	public void setRemitincomeusdamt(BigDecimal remitincomeusdamt) {
		this.remitincomeusdamt = remitincomeusdamt;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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