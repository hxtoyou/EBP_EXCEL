package com.ebills.product.declare.domain;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import com.ebills.product.declare.Entity;


/**
 * SbJnzjtzzcfzbxx entity. @author MyEclipse Persistence Tools
 * QFII境内证券投资年度资产负债表信息 对应的表名：sb_Jnzjtzzcfzbxx
 */

public class Jnzjtzzcfzbxx extends Entity {

	// Fields

	/** 操作流水号 */
	private String nguid;

	/** 托管人代码 */
	private String apno;
	/** QFII机构代码 */
	private String qfiicod;
	/** QFII机构名称 */
	private String qfiiname;
	/** 业务编号 */
	private String bizo;
	/** 产品名称 */
	private String producename;
	/** 结算年份 */
	private String settlementdate;
	/** 币种 */
	private String currency;
	/** 银行存款余额 */
	private BigDecimal depositamt;
	/** 公司股票资产金额 */
	private BigDecimal corpstockamt;
	/** 国债资产金额 */
	private BigDecimal gzzcamt;
	/** 其他债券资产金额 */
	private BigDecimal otherzjzcamt;
	/** 其他投资资产金额 */
	private BigDecimal othertzzcamt;
	/** 预付股款 */
	private BigDecimal yupaygkamt;
	/** 应收股款 */
	private BigDecimal revicegkamt;
	/** 应收股利 */
	private BigDecimal reviceglamt;
	/** 应收利息 */
	private BigDecimal revicelxamt;
	/** 其他应收款 */
	private BigDecimal reviceotheramt;
	/** 资产总额 */
	private BigDecimal zcamtsum;
	/** 应付管理费 */
	private BigDecimal payglamt;
	/** 应付托管费 */
	private BigDecimal paytgamt;
	/** 应纳税款 */
	private BigDecimal taxableamt;
	/** 应付股款 */
	private BigDecimal paygkamt;
	/** 其他应付款额 */
	private BigDecimal payotheramt;
	/** 负债总额 */
	private BigDecimal fzamtsum;
	/** 净资产 */
	private BigDecimal jzcamt;
	/** 汇入本金净值 */
	private BigDecimal hrbjamt;
	/** 累计盈余（已实现） */
	private BigDecimal totalyyamt;
	/** 备注 */
	private String memo;
	/** 业务流水号 */
	private String rwidh;
	/** 业务编号 */
	private String ywbh;
	/** 是否已申报 */
	private String sfysb;
	/** 生效标志 */
	private String sxbz;
	/** 最新标志 */
	private String sfzx;
	/** 机构编号 */
	private String bank_Id;
	/** 经办人 */
	private int handid;
	/** 经办日期 */
	private Timestamp handdate;
	/** 复核人 */
	private int checkid;
	/** 复核时间 */
	private Timestamp checkdate;
	/** 是否复核通过 */
	private String ischeck;
	/** 授权人 */
	private int authid;
	/** 授权时间 */
	private Timestamp authdate;
	/** 授权是否通过 */
	private String isauth;

	private String oguid;

	private Date recdate;

	private String datasources;

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

	public String getApno() {
		return this.apno;
	}

	public void setApno(String apno) {
		this.apno = apno;
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

	public String getSettlementdate() {
		return settlementdate;
	}

	public void setSettlementdate(String settlementdate) {
		this.settlementdate = settlementdate;
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getDepositamt() {
		return depositamt;
	}

	public void setDepositamt(BigDecimal depositamt) {
		this.depositamt = depositamt;
	}

	public BigDecimal getCorpstockamt() {
		return corpstockamt;
	}

	public void setCorpstockamt(BigDecimal corpstockamt) {
		this.corpstockamt = corpstockamt;
	}

	public BigDecimal getGzzcamt() {
		return gzzcamt;
	}

	public void setGzzcamt(BigDecimal gzzcamt) {
		this.gzzcamt = gzzcamt;
	}

	public BigDecimal getOtherzjzcamt() {
		return otherzjzcamt;
	}

	public void setOtherzjzcamt(BigDecimal otherzjzcamt) {
		this.otherzjzcamt = otherzjzcamt;
	}

	public BigDecimal getOthertzzcamt() {
		return othertzzcamt;
	}

	public void setOthertzzcamt(BigDecimal othertzzcamt) {
		this.othertzzcamt = othertzzcamt;
	}

	public BigDecimal getYupaygkamt() {
		return yupaygkamt;
	}

	public void setYupaygkamt(BigDecimal yupaygkamt) {
		this.yupaygkamt = yupaygkamt;
	}

	public BigDecimal getRevicegkamt() {
		return revicegkamt;
	}

	public void setRevicegkamt(BigDecimal revicegkamt) {
		this.revicegkamt = revicegkamt;
	}

	public BigDecimal getReviceglamt() {
		return reviceglamt;
	}

	public void setReviceglamt(BigDecimal reviceglamt) {
		this.reviceglamt = reviceglamt;
	}

	public BigDecimal getRevicelxamt() {
		return revicelxamt;
	}

	public void setRevicelxamt(BigDecimal revicelxamt) {
		this.revicelxamt = revicelxamt;
	}

	public BigDecimal getReviceotheramt() {
		return reviceotheramt;
	}

	public void setReviceotheramt(BigDecimal reviceotheramt) {
		this.reviceotheramt = reviceotheramt;
	}

	public BigDecimal getZcamtsum() {
		return zcamtsum;
	}

	public void setZcamtsum(BigDecimal zcamtsum) {
		this.zcamtsum = zcamtsum;
	}

	public BigDecimal getPayglamt() {
		return payglamt;
	}

	public void setPayglamt(BigDecimal payglamt) {
		this.payglamt = payglamt;
	}

	public BigDecimal getPaytgamt() {
		return paytgamt;
	}

	public void setPaytgamt(BigDecimal paytgamt) {
		this.paytgamt = paytgamt;
	}

	public BigDecimal getTaxableamt() {
		return taxableamt;
	}

	public void setTaxableamt(BigDecimal taxableamt) {
		this.taxableamt = taxableamt;
	}

	public BigDecimal getPaygkamt() {
		return paygkamt;
	}

	public void setPaygkamt(BigDecimal paygkamt) {
		this.paygkamt = paygkamt;
	}

	public BigDecimal getPayotheramt() {
		return payotheramt;
	}

	public void setPayotheramt(BigDecimal payotheramt) {
		this.payotheramt = payotheramt;
	}

	public BigDecimal getFzamtsum() {
		return fzamtsum;
	}

	public void setFzamtsum(BigDecimal fzamtsum) {
		this.fzamtsum = fzamtsum;
	}

	public BigDecimal getJzcamt() {
		return jzcamt;
	}

	public void setJzcamt(BigDecimal jzcamt) {
		this.jzcamt = jzcamt;
	}

	public BigDecimal getHrbjamt() {
		return hrbjamt;
	}

	public void setHrbjamt(BigDecimal hrbjamt) {
		this.hrbjamt = hrbjamt;
	}

	public BigDecimal getTotalyyamt() {
		return totalyyamt;
	}

	public void setTotalyyamt(BigDecimal totalyyamt) {
		this.totalyyamt = totalyyamt;
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

	public String getIscheck() {
		return this.ischeck;
	}

	public void setIscheck(String ischeck) {
		this.ischeck = ischeck;
	}

	public String getIsauth() {
		return this.isauth;
	}

	public void setIsauth(String isauth) {
		this.isauth = isauth;
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

	public int getAuthid() {
		return authid;
	}

	public void setAuthid(int authid) {
		this.authid = authid;
	}

	public Jnzjtzzcfzbxx() {
		this.setDepositamt(new BigDecimal(0));
		this.setCorpstockamt(new BigDecimal(0));
		this.setGzzcamt(new BigDecimal(0));
		this.setOtherzjzcamt(new BigDecimal(0));
		this.setOthertzzcamt(new BigDecimal(0));
		this.setYupaygkamt(new BigDecimal(0));
		this.setRevicegkamt(new BigDecimal(0));
		this.setReviceglamt(new BigDecimal(0));
		this.setRevicelxamt(new BigDecimal(0));
		this.setReviceotheramt(new BigDecimal(0));
		this.setZcamtsum(new BigDecimal(0));
		this.setPayglamt(new BigDecimal(0));
		this.setPaytgamt(new BigDecimal(0));
		this.setTaxableamt(new BigDecimal(0));
		this.setPaygkamt(new BigDecimal(0));
		this.setPayotheramt(new BigDecimal(0));
		this.setFzamtsum(new BigDecimal(0));
		this.setJzcamt(new BigDecimal(0));
		this.setHrbjamt(new BigDecimal(0));
		this.setTotalyyamt(new BigDecimal(0));
	}
}