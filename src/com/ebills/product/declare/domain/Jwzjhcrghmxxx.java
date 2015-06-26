package com.ebills.product.declare.domain;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import com.ebills.product.declare.Entity;


/**
 * SbJwzjhcrghmxxx entity. @author MyEclipse Persistence Tools
 * 人民币合格境外机构投资者资金汇出入及购汇明细信息
 * 
  *对应的表名：sb_Jwzjhcrghmxxx
 *
 */


public class Jwzjhcrghmxxx extends Entity {
	// Fields
	/**操作流水号*/
	private String nguid;
	/**QFII机构代码*/
	private String qfiicod;
	/**QFII机构名称*/
	private String qfiiname;
	/**托管人代码*/
	private String apno;
	/**业务编号*/
	private String bizo;
	/**产品名称*/
	private String producename;
	/**RQFII人民币账户名称*/
	private String cnyaccountname;
	/**RQFII人民币账户账号*/
	private String cnyaccount;
	/**变动编号*/
	private String changebizo;
	/**账户资金来源性质*/
	private String accounttype;
	/**汇兑日期*/
	private Date remittancedate;
	/**资金汇入金额*/
	private BigDecimal zjimportamt;
	/**人民币汇出金额*/
	private BigDecimal cnyhcamt;
	/**人民币购汇汇出金额*/
	private BigDecimal cnyghhcamt;
	/**购汇币种*/
	private String ghcur;
	/**购汇所得金额*/
	private BigDecimal ghamt;
	/**资金净汇入金额*/
	private BigDecimal zjjhramt;
	/**备注*/
	private String memo;
	/**业务流水号*/
	private String rwidh;
	/**业务编号*/
	private String ywbh;
	/**是否已申报*/
	private String sfysb;
	/**生效标志*/
	private String sxbz;
	/**最新标志*/
	private String sfzx;
	/**机构编号*/
	private String bank_Id;
	/**经办人*/
	private int handid;
	/**经办日期*/
	private Timestamp handdate;
	/**复核人*/
	private int checkid;
	/**复核时间*/
	private Timestamp checkdate;
	/**是否复核通过*/
	private String ischeck;
	/**授权人*/
	private int authid;
	/**授权时间*/
	private Timestamp authdate;
	/**授权是否通过*/
	private String isauth;
	
	private String oguid;

	private Date recdate;
	private String datasources;
	
	
	public Jwzjhcrghmxxx() {
		this.setZjimportamt(new BigDecimal(0));
		this.setCnyhcamt(new BigDecimal(0));
		this.setCnyghhcamt(new BigDecimal(0));
		this.setGhamt(new BigDecimal(0));
		this.setZjjhramt(new BigDecimal(0));
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
	
	public String getNguid() {
		return nguid;
	}
	public void setNguid(String nguid) {
		this.nguid = nguid;
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
	public String getApno() {
		return apno;
	}
	public void setApno(String apno) {
		this.apno = apno;
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
	public String getCnyaccountname() {
		return cnyaccountname;
	}
	public void setCnyaccountname(String cnyaccountname) {
		this.cnyaccountname = cnyaccountname;
	}
	public String getCnyaccount() {
		return cnyaccount;
	}
	public void setCnyaccount(String cnyaccount) {
		this.cnyaccount = cnyaccount;
	}
	public String getChangebizo() {
		return changebizo;
	}
	public void setChangebizo(String changebizo) {
		this.changebizo = changebizo;
	}
	public String getAccounttype() {
		return accounttype;
	}
	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}
	public Date getRemittancedate() {
		return remittancedate;
	}
	public void setRemittancedate(Date remittancedate) {
		this.remittancedate = remittancedate;
	}
	public String getGhcur() {
		return ghcur;
	}
	public void setGhcur(String ghcur) {
		this.ghcur = ghcur;
	}
	
	public BigDecimal getZjimportamt() {
		return zjimportamt;
	}

	public void setZjimportamt(BigDecimal zjimportamt) {
		this.zjimportamt = zjimportamt;
	}

	public BigDecimal getCnyhcamt() {
		return cnyhcamt;
	}

	public void setCnyhcamt(BigDecimal cnyhcamt) {
		this.cnyhcamt = cnyhcamt;
	}

	public BigDecimal getCnyghhcamt() {
		return cnyghhcamt;
	}

	public void setCnyghhcamt(BigDecimal cnyghhcamt) {
		this.cnyghhcamt = cnyghhcamt;
	}

	public BigDecimal getGhamt() {
		return ghamt;
	}

	public void setGhamt(BigDecimal ghamt) {
		this.ghamt = ghamt;
	}

	public BigDecimal getZjjhramt() {
		return zjjhramt;
	}

	public void setZjjhramt(BigDecimal zjjhramt) {
		this.zjjhramt = zjjhramt;
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
	public void setBank_Id(String bankId) {
		bank_Id = bankId;
	}
	public int getHandid() {
		return handid;
	}
	public void setHandid(int handid) {
		this.handid = handid;
	}

	public int getCheckid() {
		return checkid;
	}
	public void setCheckid(int checkid) {
		this.checkid = checkid;
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
	
	public Timestamp getHanddate() {
		return handdate;
	}

	public void setHanddate(Timestamp handdate) {
		this.handdate = handdate;
	}

	public Timestamp getCheckdate() {
		return checkdate;
	}

	public void setCheckdate(Timestamp checkdate) {
		this.checkdate = checkdate;
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

	

}