package com.ebills.product.declare.domain;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import com.ebills.product.declare.Entity;


/**
 * SbJnzjtzzjhcrxx entity. @author MyEclipse Persistence Tools
 * RQFII境内证券投资资金汇出入信息
 * 对应的表名：sb_Jnzjtzzjhcrxx
 */

public class Jnzjtzzjhcrxx  extends Entity  {

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
	/**报告期(YYYYMM)*/
	private String reportdate;
	/**结算日*/
	private Date settlementdate;
	/**本月汇入本金金额*/
	private BigDecimal byhrbjamt;
	/**累计汇入本金金额*/
	private BigDecimal hrbjamtsum;
	/**本月汇出本金金额*/
	private BigDecimal byhcbjamt;
	/**累计汇出本金金额*/
	private BigDecimal hcbjamtsum;
	/**本月购汇汇出本金金额*/
	private BigDecimal byghhcbjamt;
	/**累计购汇汇出本金金额*/
	private BigDecimal byghhcbjamtsum;
	/**本月汇出收益金额*/
	private BigDecimal byhcsyamt;
	/**累计汇出收益金额*/
	private BigDecimal byhcsyamtsum;
	/**本月购汇汇出收益金额*/
	private BigDecimal byghhcsyamt;
	/**累计购汇汇出收益金额*/
	private BigDecimal byghhcsyamtsum;
	/**本月净汇入资金额*/
	private BigDecimal byjhrzjamt;
	/**累计净汇入资金额*/
	private BigDecimal byjhrzjamtsum;
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

	
	
	public Jnzjtzzjhcrxx() {
		this.setByhrbjamt(new BigDecimal(0));
		this.setHrbjamtsum(new BigDecimal(0));
		this.setByhcbjamt(new BigDecimal(0));
		this.setHcbjamtsum(new BigDecimal(0));
		this.setByghhcbjamt(new BigDecimal(0));
		this.setByghhcbjamtsum(new BigDecimal(0));
		this.setByhcsyamt(new BigDecimal(0));
		this.setByhcsyamtsum(new BigDecimal(0));
		this.setByghhcsyamt(new BigDecimal(0));
		this.setByghhcsyamtsum(new BigDecimal(0));
		this.setByjhrzjamt(new BigDecimal(0));
		this.setByjhrzjamtsum(new BigDecimal(0));
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
		return this.nguid;
	}

	public void setNguid(String nguid) {
		this.nguid = nguid;
	}

	public String getQfiicod() {
		return this.qfiicod;
	}

	public void setQfiicod(String qfiicod) {
		this.qfiicod = qfiicod;
	}

	public String getQfiiname() {
		return this.qfiiname;
	}

	public void setQfiiname(String qfiiname) {
		this.qfiiname = qfiiname;
	}

	public String getApno() {
		return this.apno;
	}

	public void setApno(String apno) {
		this.apno = apno;
	}

	public String getBizo() {
		return this.bizo;
	}

	public void setBizo(String bizo) {
		this.bizo = bizo;
	}

	public String getProducename() {
		return this.producename;
	}

	public void setProducename(String producename) {
		this.producename = producename;
	}

	public String getCnyaccountname() {
		return this.cnyaccountname;
	}

	public void setCnyaccountname(String cnyaccountname) {
		this.cnyaccountname = cnyaccountname;
	}

	public String getCnyaccount() {
		return this.cnyaccount;
	}

	public void setCnyaccount(String cnyaccount) {
		this.cnyaccount = cnyaccount;
	}


	public String getReportdate() {
		return reportdate;
	}

	public void setReportdate(String reportdate) {
		this.reportdate = reportdate;
	}

	public Date getSettlementdate() {
		return this.settlementdate;
	}

	public void setSettlementdate(Date settlementdate) {
		this.settlementdate = settlementdate;
	}

	public BigDecimal getByhrbjamt() {
		return byhrbjamt;
	}

	public void setByhrbjamt(BigDecimal byhrbjamt) {
		this.byhrbjamt = byhrbjamt;
	}

	public BigDecimal getHrbjamtsum() {
		return hrbjamtsum;
	}

	public void setHrbjamtsum(BigDecimal hrbjamtsum) {
		this.hrbjamtsum = hrbjamtsum;
	}

	public BigDecimal getByhcbjamt() {
		return byhcbjamt;
	}

	public void setByhcbjamt(BigDecimal byhcbjamt) {
		this.byhcbjamt = byhcbjamt;
	}

	public BigDecimal getHcbjamtsum() {
		return hcbjamtsum;
	}

	public void setHcbjamtsum(BigDecimal hcbjamtsum) {
		this.hcbjamtsum = hcbjamtsum;
	}

	public BigDecimal getByghhcbjamt() {
		return byghhcbjamt;
	}

	public void setByghhcbjamt(BigDecimal byghhcbjamt) {
		this.byghhcbjamt = byghhcbjamt;
	}

	public BigDecimal getByghhcbjamtsum() {
		return byghhcbjamtsum;
	}

	public void setByghhcbjamtsum(BigDecimal byghhcbjamtsum) {
		this.byghhcbjamtsum = byghhcbjamtsum;
	}

	public BigDecimal getByhcsyamt() {
		return byhcsyamt;
	}

	public void setByhcsyamt(BigDecimal byhcsyamt) {
		this.byhcsyamt = byhcsyamt;
	}

	public BigDecimal getByhcsyamtsum() {
		return byhcsyamtsum;
	}

	public void setByhcsyamtsum(BigDecimal byhcsyamtsum) {
		this.byhcsyamtsum = byhcsyamtsum;
	}

	public BigDecimal getByghhcsyamt() {
		return byghhcsyamt;
	}

	public void setByghhcsyamt(BigDecimal byghhcsyamt) {
		this.byghhcsyamt = byghhcsyamt;
	}

	public BigDecimal getByghhcsyamtsum() {
		return byghhcsyamtsum;
	}

	public void setByghhcsyamtsum(BigDecimal byghhcsyamtsum) {
		this.byghhcsyamtsum = byghhcsyamtsum;
	}

	public BigDecimal getByjhrzjamt() {
		return byjhrzjamt;
	}

	public void setByjhrzjamt(BigDecimal byjhrzjamt) {
		this.byjhrzjamt = byjhrzjamt;
	}

	public BigDecimal getByjhrzjamtsum() {
		return byjhrzjamtsum;
	}

	public void setByjhrzjamtsum(BigDecimal byjhrzjamtsum) {
		this.byjhrzjamtsum = byjhrzjamtsum;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getRwidh() {
		return this.rwidh;
	}

	public void setRwidh(String rwidh) {
		this.rwidh = rwidh;
	}

	public String getYwbh() {
		return this.ywbh;
	}

	public void setYwbh(String ywbh) {
		this.ywbh = ywbh;
	}

	public String getSfysb() {
		return this.sfysb;
	}

	public void setSfysb(String sfysb) {
		this.sfysb = sfysb;
	}

	public String getSxbz() {
		return this.sxbz;
	}

	public void setSxbz(String sxbz) {
		this.sxbz = sxbz;
	}

	public String getSfzx() {
		return this.sfzx;
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
		return this.checkid;
	}

	public void setCheckid(int checkid) {
		this.checkid = checkid;
	}




	public String getIscheck() {
		return this.ischeck;
	}

	public void setIscheck(String ischeck) {
		this.ischeck = ischeck;
	}

	public int getAuthid() {
		return this.authid;
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
		return this.isauth;
	}

	public void setIsauth(String isauth) {
		this.isauth = isauth;
	}

}