package com.ebills.product.declare.domain;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import com.ebills.product.declare.Entity;


/**
 * SbZcfzbxx entity. @author MyEclipse Persistence Tools
 * RQFII境内证券投资年度资产负债表信息
 * 对应的表名：sb_zcfzbxx
 */

public class Zcfzbxx extends Entity  {

	// Fields
	
	/**操作流水号*/
	private String nguid;
	/**RQFII机构代码*/
	private String qfiicod;
	/**RQFII机构名称*/
	private String qfiiname;
	/**托管人代码*/
	private String apno;
	/**结算日(YYYY)*/
	private String settlementdate;
	/**币种*/
	private String currency;
	/**银行存款余额*/
	private BigDecimal depositamt;
	/**公司股票资产金额*/
	private BigDecimal corpstockamt;
	/**债券资产金额*/
	private BigDecimal zjzcamt;
	/**基金资产金额*/
	private BigDecimal jjzcamt;
	/**资产支持证券资产金额*/
	private BigDecimal zczczjamt;
	/**权证资产金额*/
	private BigDecimal qzzcamt;
	/**其他投资资产金额*/
	private BigDecimal othertzzcamt;
	/**预付股款*/
	private BigDecimal yupaygkamt;
	/**应收股款*/
	private BigDecimal revicegkamt;
	/**应收申购款额*/
	private BigDecimal revicesgamt;
	/**应收股利*/
	private BigDecimal reviceglamt;
	/**应收利息*/
	private BigDecimal revicelxamt;
	/**结算备付金额*/
	private BigDecimal jsbfamt;
	/**买入返售金融资产余额*/
	private BigDecimal buyfsjrzcamt;
	/**其他应收款额*/
	private BigDecimal otherreviceamt;
	/**资产总额*/
	private BigDecimal amtsum;
	/**应付管理费*/
	private BigDecimal payglamt;
	/**应付托管费*/
	private BigDecimal paytgamt;
	/**应纳税款*/
	private BigDecimal taxableamt;
	/**应付股款*/
	private BigDecimal paygkamt;
	/**应付利息额*/
	private BigDecimal paylxamt;
	/**卖出回购金融资产款额*/
	private BigDecimal salehgjrzcamt;
	/**应付赎回款额*/
	private BigDecimal payshkamt;
	/**应付交易费用额*/
	private BigDecimal payjyfamt;
	/**其他应付款额*/
	private BigDecimal payotheramt;
	/**负债总额*/
	private BigDecimal fzamtsum;
	/**净资产*/
	private BigDecimal jzcamt;
	/**汇入本金净值*/
	private BigDecimal hrbjamt;
	/**本年度净损益*/
	private BigDecimal bndjsyamt;
	/**累计盈余（已实现）*/
	private BigDecimal totalyyamt;
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
	public String getSettlementdate() {
		return settlementdate;
	}
	public void setSettlementdate(String settlementdate) {
		this.settlementdate = settlementdate;
	}
	public String getCurrency() {
		return currency;
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

	public String getIsauth() {
		return isauth;
	}
	public void setIsauth(String isauth) {
		this.isauth = isauth;
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
	public BigDecimal getZjzcamt() {
		return zjzcamt;
	}
	public void setZjzcamt(BigDecimal zjzcamt) {
		this.zjzcamt = zjzcamt;
	}
	public BigDecimal getJjzcamt() {
		return jjzcamt;
	}
	public void setJjzcamt(BigDecimal jjzcamt) {
		this.jjzcamt = jjzcamt;
	}
	public BigDecimal getZczczjamt() {
		return zczczjamt;
	}
	public void setZczczjamt(BigDecimal zczczjamt) {
		this.zczczjamt = zczczjamt;
	}
	public BigDecimal getQzzcamt() {
		return qzzcamt;
	}
	public void setQzzcamt(BigDecimal qzzcamt) {
		this.qzzcamt = qzzcamt;
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
	public BigDecimal getRevicesgamt() {
		return revicesgamt;
	}
	public void setRevicesgamt(BigDecimal revicesgamt) {
		this.revicesgamt = revicesgamt;
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
	public BigDecimal getJsbfamt() {
		return jsbfamt;
	}
	public void setJsbfamt(BigDecimal jsbfamt) {
		this.jsbfamt = jsbfamt;
	}
	public BigDecimal getBuyfsjrzcamt() {
		return buyfsjrzcamt;
	}
	public void setBuyfsjrzcamt(BigDecimal buyfsjrzcamt) {
		this.buyfsjrzcamt = buyfsjrzcamt;
	}
	public BigDecimal getOtherreviceamt() {
		return otherreviceamt;
	}
	public void setOtherreviceamt(BigDecimal otherreviceamt) {
		this.otherreviceamt = otherreviceamt;
	}
	public BigDecimal getAmtsum() {
		return amtsum;
	}
	public void setAmtsum(BigDecimal amtsum) {
		this.amtsum = amtsum;
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
	public BigDecimal getPaylxamt() {
		return paylxamt;
	}
	public void setPaylxamt(BigDecimal paylxamt) {
		this.paylxamt = paylxamt;
	}
	public BigDecimal getSalehgjrzcamt() {
		return salehgjrzcamt;
	}
	public void setSalehgjrzcamt(BigDecimal salehgjrzcamt) {
		this.salehgjrzcamt = salehgjrzcamt;
	}
	public BigDecimal getPayshkamt() {
		return payshkamt;
	}
	public void setPayshkamt(BigDecimal payshkamt) {
		this.payshkamt = payshkamt;
	}
	public BigDecimal getPayjyfamt() {
		return payjyfamt;
	}
	public void setPayjyfamt(BigDecimal payjyfamt) {
		this.payjyfamt = payjyfamt;
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
	public BigDecimal getBndjsyamt() {
		return bndjsyamt;
	}
	public void setBndjsyamt(BigDecimal bndjsyamt) {
		this.bndjsyamt = bndjsyamt;
	}
	public BigDecimal getTotalyyamt() {
		return totalyyamt;
	}
	public void setTotalyyamt(BigDecimal totalyyamt) {
		this.totalyyamt = totalyyamt;
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
	public int getCheckid() {
		return checkid;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Zcfzbxx(){
		this.setDepositamt(new BigDecimal(0));
		this.setCorpstockamt(new BigDecimal(0));
		this.setZjzcamt(new BigDecimal(0));
		this.setJjzcamt(new BigDecimal(0));
		this.setZczczjamt(new BigDecimal(0));
		this.setQzzcamt(new BigDecimal(0));
		this.setOthertzzcamt(new BigDecimal(0));
		this.setYupaygkamt(new BigDecimal(0));
		this.setRevicegkamt(new BigDecimal(0));
		this.setRevicesgamt(new BigDecimal(0));
		this.setReviceglamt(new BigDecimal(0));
		this.setRevicelxamt(new BigDecimal(0));
		this.setJsbfamt(new BigDecimal(0));
		this.setBuyfsjrzcamt(new BigDecimal(0));
		this.setOtherreviceamt(new BigDecimal(0));
		this.setAmtsum(new BigDecimal(0));
		this.setPayglamt(new BigDecimal(0));
		this.setPaytgamt(new BigDecimal(0));
		this.setTaxableamt(new BigDecimal(0));
		this.setPaygkamt(new BigDecimal(0));
		this.setPaylxamt(new BigDecimal(0));
		this.setSalehgjrzcamt(new BigDecimal(0));
		this.setPayshkamt(new BigDecimal(0));
		this.setPayjyfamt(new BigDecimal(0));
		this.setPayotheramt(new BigDecimal(0));
		this.setFzamtsum(new BigDecimal(0));
		this.setJzcamt(new BigDecimal(0));
		this.setHrbjamt(new BigDecimal(0));
		this.setBndjsyamt(new BigDecimal(0));
		this.setTotalyyamt(new BigDecimal(0));
	}

}