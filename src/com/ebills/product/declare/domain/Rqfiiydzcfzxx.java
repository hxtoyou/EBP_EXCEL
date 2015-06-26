package com.ebills.product.declare.domain;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import com.ebills.product.declare.Entity;


/**
 * SbRqfiiydzcfzxx entity. @author MyEclipse Persistence Tools RQFII机构月度资产负债信息
 * 对应的表名：sb_Rqfiiydzcfzxx
 */

public class Rqfiiydzcfzxx extends Entity  {

	// Fields

	/** 托管人代码 */
	private String apno;
	/** 授权时间 */
	private Timestamp authdate;
	/** 授权人 */
	private int authid;
	
	/** 平均获利率 */
	private double avgrate;
	
	/** 机构编号 */
	private String bank_Id;
	/** 银行间市场央票成本 */
	private BigDecimal barbillcost;
	/** 银行间市场央票市值 */
	private BigDecimal barbillvalue;
	/** 银行间市场企业债成本 */
	private BigDecimal barcorporatebondcost;
	/** 银行间市场企业债市值 */
	private BigDecimal barcorporatebondvalue;
	/** 银行间市场金融债成本 */
	private BigDecimal barfinancialbondcost;
	/** 银行间市场金融债市值 */
	private BigDecimal barfinancialbondvalue;
	/** 银行间市场短期融资券成本 */
	private BigDecimal barfinancingbillscost;
	/** 银行间市场短期融资券市值 */
	private BigDecimal barfinancingbillsvalue;
	/** 银行间市场国债成本 */
	private BigDecimal bargovernmentloancost;
	/** 银行间市场国债市值 */
	private BigDecimal bargovernmentloanvalue;
	/** 银行间市场地方政府债成本 */
	private BigDecimal barlocalgovernmentcost;
	/** 银行间市场地方政府债市值 */
	private BigDecimal barlocalgovernmentvalue;
	/** 银行间市场中期票据成本 */
	private BigDecimal bartermnotecost;
	/** 银行间市场中期票据市值 */
	private BigDecimal bartermnotevalue;
	/** 银行间市场资产支持成本 */
	private BigDecimal barzczccost;
	/** 银行间市场资产支持市值 */
	private BigDecimal barzczcvalue;
	
	/** 本月平均获利率 */
	private double byavgrate;
	/** 本月买入周转率 */
	private double bybuyrate;
	/** 本月卖出周转率 */
	private double bysalerate;
	
	/** 复核时间 */
	private Timestamp checkdate;
	/** 复核人 */
	private int checkid;
	/** 币种 */
	private String currency;
	private String datasources;
	/** 银行存款余额 */
	private BigDecimal depositamt;
	/** 固定收益类基金成本 */
	private BigDecimal gdsyljjcost;
	/** 固定收益类基金市值 */
	private BigDecimal gdsyljjvalue;
	/** 经办日期 */
	private Timestamp handdate;
	/** 经办人 */
	private int handid;
	/** 股指期货成本 */
	private BigDecimal indexfuturescost;
	/** 股指期货市值 */
	private BigDecimal indexfuturesvalue;
	/** 投资市值合计 */
	private BigDecimal investvaluesum;
	/** 授权是否通过 */
	private String isauth;
	/** 是否复核通过 */
	private String ischeck;
	/** 交易所市场可转债成本 */
	private BigDecimal jysconvertiblebondcost;
	/** 交易所市场可转债市值 */
	private BigDecimal jysconvertiblebondvalue;
	/** 交易所市场公司债（企业债）成本 */
	private BigDecimal jyscorporatebondcost;
	/** 交易所市场公司债（企业债）市值 */
	private BigDecimal jyscorporatebondvalue;
	/** 交易所市场国债成本 */
	private BigDecimal jysgovernmentloancost;
	/** 交易所市场国债市值 */
	private BigDecimal jysgovernmentloanvalue;
	/** 交易所市场地方债成本 */
	private BigDecimal jysterritorialbondcost;
	/** 交易所市场地方债市值 */
	private BigDecimal jysterritorialbondvalue;
	/** 负债合计 */
	private BigDecimal liabilitiessum;
	/** 备注 */
	private String memo;
	/** 净资产 */
	private BigDecimal netasset;
	/** 操作流水号 */
	private String nguid;
	private String oguid;
	/** 其他银行间市场证券成本 */
	private BigDecimal otherbarcost;
	/** 其他银行间市场证券成本 */
	private BigDecimal otherbarvalue;
	/** 其他基金成本 */
	private BigDecimal otherjjcost;
	/** 其他基金市值 */
	private BigDecimal otherjjvalue;
	/** 其他交易所市场证券成本 */
	private BigDecimal otherjyscost;
	/** 其他交易所市场证券市值 */
	private BigDecimal otherjysvalue;
	/** 应付管理费金额 */
	private BigDecimal payglamt;
	/** 其他应付款金额 */
	private BigDecimal payotheramt;
	/** 应付清算款金额 */
	private BigDecimal payqsamt;
	/** 应付托管费金额 */
	private BigDecimal paytgamt;
	/** 资产市值合计 */
	private BigDecimal propertysum;
	/** RQFII机构代码 */
	private String qfiicod;
	/** RQFII机构名称 */
	private String qfiiname;
	private Date recdate;
	/** 报告期(YYYYMM) */
	private String reportdate;
	/** 应收清算款金额 */
	private BigDecimal reviceclearamt;
	/** 应收股利金额 */
	private BigDecimal reviceglamt;
	/** 应收利息金额 */
	private BigDecimal revicelxamt;
	/** 其他应收款金额 */
	private BigDecimal reviceotheramt;
	/** 业务流水号 */
	private String rwidh;
	/** 结算日 */
	private Date settlementdate;
	/** 是否已申报 */
	private String sfysb;
	/** 最新标志 */
	private String sfzx;
	/** 股票成本 */
	private BigDecimal stockcost;
	/** 股票类基金成本 */
	private BigDecimal stockjjcost;
	/** 股票类基金市值 */
	private BigDecimal stockjjvalue;
	/** 股票市值 */
	private BigDecimal stockvalue;
	/** 生效标志 */
	private String sxbz;
	/** 应纳税款金额 */
	private BigDecimal taxableamt;

	/** 权证成本 */
	private BigDecimal warrantcost;

	/** 权证市值 */
	private BigDecimal warrantvalue;

	/** 业务编号 */
	private String ywbh;

	public String getApno() {
		return apno;
	}



	public int getAuthid() {
		return authid;
	}

	public double getAvgrate() {
		return avgrate;
	}

	public String getBank_Id() {
		return bank_Id;
	}

	public void setBank_Id(String bankId) {
		bank_Id = bankId;
	}

	public BigDecimal getBarbillcost() {
		return barbillcost;
	}

	public void setBarbillcost(BigDecimal barbillcost) {
		this.barbillcost = barbillcost;
	}

	public BigDecimal getBarbillvalue() {
		return barbillvalue;
	}

	public void setBarbillvalue(BigDecimal barbillvalue) {
		this.barbillvalue = barbillvalue;
	}

	public BigDecimal getBarcorporatebondcost() {
		return barcorporatebondcost;
	}

	public void setBarcorporatebondcost(BigDecimal barcorporatebondcost) {
		this.barcorporatebondcost = barcorporatebondcost;
	}

	public BigDecimal getBarcorporatebondvalue() {
		return barcorporatebondvalue;
	}

	public void setBarcorporatebondvalue(BigDecimal barcorporatebondvalue) {
		this.barcorporatebondvalue = barcorporatebondvalue;
	}

	public BigDecimal getBarfinancialbondcost() {
		return barfinancialbondcost;
	}

	public void setBarfinancialbondcost(BigDecimal barfinancialbondcost) {
		this.barfinancialbondcost = barfinancialbondcost;
	}

	public BigDecimal getBarfinancialbondvalue() {
		return barfinancialbondvalue;
	}

	public void setBarfinancialbondvalue(BigDecimal barfinancialbondvalue) {
		this.barfinancialbondvalue = barfinancialbondvalue;
	}

	public BigDecimal getBarfinancingbillscost() {
		return barfinancingbillscost;
	}

	public void setBarfinancingbillscost(BigDecimal barfinancingbillscost) {
		this.barfinancingbillscost = barfinancingbillscost;
	}

	public BigDecimal getBarfinancingbillsvalue() {
		return barfinancingbillsvalue;
	}

	public void setBarfinancingbillsvalue(BigDecimal barfinancingbillsvalue) {
		this.barfinancingbillsvalue = barfinancingbillsvalue;
	}

	public BigDecimal getBargovernmentloancost() {
		return bargovernmentloancost;
	}

	public void setBargovernmentloancost(BigDecimal bargovernmentloancost) {
		this.bargovernmentloancost = bargovernmentloancost;
	}

	public BigDecimal getBargovernmentloanvalue() {
		return bargovernmentloanvalue;
	}

	public void setBargovernmentloanvalue(BigDecimal bargovernmentloanvalue) {
		this.bargovernmentloanvalue = bargovernmentloanvalue;
	}

	public BigDecimal getBarlocalgovernmentcost() {
		return barlocalgovernmentcost;
	}

	public void setBarlocalgovernmentcost(BigDecimal barlocalgovernmentcost) {
		this.barlocalgovernmentcost = barlocalgovernmentcost;
	}

	public BigDecimal getBarlocalgovernmentvalue() {
		return barlocalgovernmentvalue;
	}

	public void setBarlocalgovernmentvalue(BigDecimal barlocalgovernmentvalue) {
		this.barlocalgovernmentvalue = barlocalgovernmentvalue;
	}

	public BigDecimal getBartermnotecost() {
		return bartermnotecost;
	}

	public void setBartermnotecost(BigDecimal bartermnotecost) {
		this.bartermnotecost = bartermnotecost;
	}

	public BigDecimal getBartermnotevalue() {
		return bartermnotevalue;
	}

	public void setBartermnotevalue(BigDecimal bartermnotevalue) {
		this.bartermnotevalue = bartermnotevalue;
	}

	public BigDecimal getBarzczccost() {
		return barzczccost;
	}

	public void setBarzczccost(BigDecimal barzczccost) {
		this.barzczccost = barzczccost;
	}

	public BigDecimal getBarzczcvalue() {
		return barzczcvalue;
	}

	public void setBarzczcvalue(BigDecimal barzczcvalue) {
		this.barzczcvalue = barzczcvalue;
	}

	public double getByavgrate() {
		return byavgrate;
	}

	public void setByavgrate(double byavgrate) {
		this.byavgrate = byavgrate;
	}

	public double getBybuyrate() {
		return bybuyrate;
	}

	public void setBybuyrate(double bybuyrate) {
		this.bybuyrate = bybuyrate;
	}

	public double getBysalerate() {
		return bysalerate;
	}

	public void setBysalerate(double bysalerate) {
		this.bysalerate = bysalerate;
	}



	public int getCheckid() {
		return checkid;
	}

	public void setCheckid(int checkid) {
		this.checkid = checkid;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getDatasources() {
		return datasources;
	}

	public void setDatasources(String datasources) {
		this.datasources = datasources;
	}

	public BigDecimal getDepositamt() {
		return depositamt;
	}

	public void setDepositamt(BigDecimal depositamt) {
		this.depositamt = depositamt;
	}

	public BigDecimal getGdsyljjcost() {
		return gdsyljjcost;
	}

	public void setGdsyljjcost(BigDecimal gdsyljjcost) {
		this.gdsyljjcost = gdsyljjcost;
	}

	public BigDecimal getGdsyljjvalue() {
		return gdsyljjvalue;
	}

	public void setGdsyljjvalue(BigDecimal gdsyljjvalue) {
		this.gdsyljjvalue = gdsyljjvalue;
	}



	public int getHandid() {
		return handid;
	}

	public void setHandid(int handid) {
		this.handid = handid;
	}

	public BigDecimal getIndexfuturescost() {
		return indexfuturescost;
	}

	public void setIndexfuturescost(BigDecimal indexfuturescost) {
		this.indexfuturescost = indexfuturescost;
	}

	public BigDecimal getIndexfuturesvalue() {
		return indexfuturesvalue;
	}

	public void setIndexfuturesvalue(BigDecimal indexfuturesvalue) {
		this.indexfuturesvalue = indexfuturesvalue;
	}

	public BigDecimal getInvestvaluesum() {
		return investvaluesum;
	}

	public void setInvestvaluesum(BigDecimal investvaluesum) {
		this.investvaluesum = investvaluesum;
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

	public BigDecimal getJysconvertiblebondcost() {
		return jysconvertiblebondcost;
	}

	public void setJysconvertiblebondcost(BigDecimal jysconvertiblebondcost) {
		this.jysconvertiblebondcost = jysconvertiblebondcost;
	}

	public BigDecimal getJysconvertiblebondvalue() {
		return jysconvertiblebondvalue;
	}

	public void setJysconvertiblebondvalue(BigDecimal jysconvertiblebondvalue) {
		this.jysconvertiblebondvalue = jysconvertiblebondvalue;
	}

	public BigDecimal getJyscorporatebondcost() {
		return jyscorporatebondcost;
	}

	public void setJyscorporatebondcost(BigDecimal jyscorporatebondcost) {
		this.jyscorporatebondcost = jyscorporatebondcost;
	}

	public BigDecimal getJyscorporatebondvalue() {
		return jyscorporatebondvalue;
	}

	public void setJyscorporatebondvalue(BigDecimal jyscorporatebondvalue) {
		this.jyscorporatebondvalue = jyscorporatebondvalue;
	}

	public BigDecimal getJysgovernmentloancost() {
		return jysgovernmentloancost;
	}

	public void setJysgovernmentloancost(BigDecimal jysgovernmentloancost) {
		this.jysgovernmentloancost = jysgovernmentloancost;
	}

	public BigDecimal getJysgovernmentloanvalue() {
		return jysgovernmentloanvalue;
	}

	public void setJysgovernmentloanvalue(BigDecimal jysgovernmentloanvalue) {
		this.jysgovernmentloanvalue = jysgovernmentloanvalue;
	}

	public BigDecimal getJysterritorialbondcost() {
		return jysterritorialbondcost;
	}

	public void setJysterritorialbondcost(BigDecimal jysterritorialbondcost) {
		this.jysterritorialbondcost = jysterritorialbondcost;
	}

	public BigDecimal getJysterritorialbondvalue() {
		return jysterritorialbondvalue;
	}

	public void setJysterritorialbondvalue(BigDecimal jysterritorialbondvalue) {
		this.jysterritorialbondvalue = jysterritorialbondvalue;
	}

	public BigDecimal getLiabilitiessum() {
		return liabilitiessum;
	}

	public void setLiabilitiessum(BigDecimal liabilitiessum) {
		this.liabilitiessum = liabilitiessum;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public BigDecimal getNetasset() {
		return netasset;
	}

	public void setNetasset(BigDecimal netasset) {
		this.netasset = netasset;
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

	public BigDecimal getOtherbarcost() {
		return otherbarcost;
	}

	public void setOtherbarcost(BigDecimal otherbarcost) {
		this.otherbarcost = otherbarcost;
	}

	public BigDecimal getOtherbarvalue() {
		return otherbarvalue;
	}

	public void setOtherbarvalue(BigDecimal otherbarvalue) {
		this.otherbarvalue = otherbarvalue;
	}

	public BigDecimal getOtherjjcost() {
		return otherjjcost;
	}

	public void setOtherjjcost(BigDecimal otherjjcost) {
		this.otherjjcost = otherjjcost;
	}

	public BigDecimal getOtherjjvalue() {
		return otherjjvalue;
	}

	public void setOtherjjvalue(BigDecimal otherjjvalue) {
		this.otherjjvalue = otherjjvalue;
	}

	public BigDecimal getOtherjyscost() {
		return otherjyscost;
	}

	public void setOtherjyscost(BigDecimal otherjyscost) {
		this.otherjyscost = otherjyscost;
	}

	public BigDecimal getOtherjysvalue() {
		return otherjysvalue;
	}

	public void setOtherjysvalue(BigDecimal otherjysvalue) {
		this.otherjysvalue = otherjysvalue;
	}

	public BigDecimal getPayglamt() {
		return payglamt;
	}

	public void setPayglamt(BigDecimal payglamt) {
		this.payglamt = payglamt;
	}

	public BigDecimal getPayotheramt() {
		return payotheramt;
	}

	public void setPayotheramt(BigDecimal payotheramt) {
		this.payotheramt = payotheramt;
	}

	public BigDecimal getPayqsamt() {
		return payqsamt;
	}

	public void setPayqsamt(BigDecimal payqsamt) {
		this.payqsamt = payqsamt;
	}

	public BigDecimal getPaytgamt() {
		return paytgamt;
	}

	public void setPaytgamt(BigDecimal paytgamt) {
		this.paytgamt = paytgamt;
	}

	public BigDecimal getPropertysum() {
		return propertysum;
	}

	public void setPropertysum(BigDecimal propertysum) {
		this.propertysum = propertysum;
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

	public Date getRecdate() {
		return recdate;
	}

	public void setRecdate(Date recdate) {
		this.recdate = recdate;
	}

	public String getReportdate() {
		return reportdate;
	}

	public void setReportdate(String reportdate) {
		this.reportdate = reportdate;
	}

	public BigDecimal getReviceclearamt() {
		return reviceclearamt;
	}

	public void setReviceclearamt(BigDecimal reviceclearamt) {
		this.reviceclearamt = reviceclearamt;
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

	public String getRwidh() {
		return rwidh;
	}

	public void setRwidh(String rwidh) {
		this.rwidh = rwidh;
	}

	public Date getSettlementdate() {
		return settlementdate;
	}

	public void setSettlementdate(Date settlementdate) {
		this.settlementdate = settlementdate;
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

	public BigDecimal getStockcost() {
		return stockcost;
	}

	public void setStockcost(BigDecimal stockcost) {
		this.stockcost = stockcost;
	}

	public BigDecimal getStockjjcost() {
		return stockjjcost;
	}

	public void setStockjjcost(BigDecimal stockjjcost) {
		this.stockjjcost = stockjjcost;
	}

	public BigDecimal getStockjjvalue() {
		return stockjjvalue;
	}

	public void setStockjjvalue(BigDecimal stockjjvalue) {
		this.stockjjvalue = stockjjvalue;
	}

	public BigDecimal getStockvalue() {
		return stockvalue;
	}

	public void setStockvalue(BigDecimal stockvalue) {
		this.stockvalue = stockvalue;
	}

	public String getSxbz() {
		return sxbz;
	}

	public void setSxbz(String sxbz) {
		this.sxbz = sxbz;
	}

	public BigDecimal getTaxableamt() {
		return taxableamt;
	}

	public void setTaxableamt(BigDecimal taxableamt) {
		this.taxableamt = taxableamt;
	}

	public BigDecimal getWarrantcost() {
		return warrantcost;
	}

	public void setWarrantcost(BigDecimal warrantcost) {
		this.warrantcost = warrantcost;
	}

	public BigDecimal getWarrantvalue() {
		return warrantvalue;
	}

	public void setWarrantvalue(BigDecimal warrantvalue) {
		this.warrantvalue = warrantvalue;
	}

	public String getYwbh() {
		return ywbh;
	}

	public void setYwbh(String ywbh) {
		this.ywbh = ywbh;
	}

	public void setApno(String apno) {
		this.apno = apno;
	}



	public Timestamp getAuthdate() {
		return authdate;
	}



	public void setAuthdate(Timestamp authdate) {
		this.authdate = authdate;
	}



	public Timestamp getCheckdate() {
		return checkdate;
	}



	public void setCheckdate(Timestamp checkdate) {
		this.checkdate = checkdate;
	}



	public Timestamp getHanddate() {
		return handdate;
	}



	public void setHanddate(Timestamp handdate) {
		this.handdate = handdate;
	}



	public void setAuthid(int authid) {
		this.authid = authid;
	}

	public void setAvgrate(double avgrate) {
		this.avgrate = avgrate;
	}
    public Rqfiiydzcfzxx(){
    	this.setBarbillcost(new BigDecimal(0));
    	this.setBarbillvalue(new BigDecimal(0));
    	this.setBarcorporatebondcost(new BigDecimal(0));
    	this.setBarcorporatebondvalue(new BigDecimal(0));
    	this.setBarfinancialbondcost(new BigDecimal(0));
    	this.setBarfinancialbondvalue(new BigDecimal(0));
    	this.setBarfinancingbillscost(new BigDecimal(0));
    	this.setBarfinancingbillsvalue(new BigDecimal(0));
    	this.setBargovernmentloancost(new BigDecimal(0));
    	this.setBargovernmentloanvalue(new BigDecimal(0));
    	this.setBarlocalgovernmentcost(new BigDecimal(0));
    	this.setBarlocalgovernmentvalue(new BigDecimal(0));
    	this.setBartermnotecost(new BigDecimal(0));
    	this.setBartermnotevalue(new BigDecimal(0));
    	this.setBarzczccost(new BigDecimal(0));
    	this.setBarzczcvalue(new BigDecimal(0));
    	this.setDepositamt(new BigDecimal(0));
    	this.setGdsyljjcost(new BigDecimal(0));
    	this.setGdsyljjvalue(new BigDecimal(0));
    	this.setIndexfuturescost(new BigDecimal(0));
    	this.setIndexfuturesvalue(new BigDecimal(0));
    	this.setInvestvaluesum(new BigDecimal(0));
    	this.setJysconvertiblebondcost(new BigDecimal(0));
    	this.setJysconvertiblebondvalue(new BigDecimal(0));
    	this.setJyscorporatebondcost(new BigDecimal(0));
    	this.setJyscorporatebondvalue(new BigDecimal(0));
    	this.setJysgovernmentloancost(new BigDecimal(0));
    	this.setJysgovernmentloanvalue(new BigDecimal(0));
    	this.setJysterritorialbondcost(new BigDecimal(0));
    	this.setJysterritorialbondvalue(new BigDecimal(0));
    	this.setLiabilitiessum(new BigDecimal(0));
    	this.setNetasset(new BigDecimal(0));
    	this.setOtherbarcost(new BigDecimal(0));
    	this.setOtherbarvalue(new BigDecimal(0));
    	this.setOtherjjcost(new BigDecimal(0));
    	this.setOtherjjvalue(new BigDecimal(0));
    	this.setOtherjyscost(new BigDecimal(0));
    	this.setOtherjysvalue(new BigDecimal(0));
    	this.setPayglamt(new BigDecimal(0));
    	this.setPayotheramt(new BigDecimal(0));
    	this.setPayqsamt(new BigDecimal(0));
    	this.setPaytgamt(new BigDecimal(0));
    	this.setPropertysum(new BigDecimal(0));
    	this.setReviceclearamt(new BigDecimal(0));
    	this.setReviceglamt(new BigDecimal(0));
    	this.setRevicelxamt(new BigDecimal(0));
    	this.setReviceotheramt(new BigDecimal(0));
    	this.setStockcost(new BigDecimal(0));
    	this.setStockjjcost(new BigDecimal(0));
    	this.setStockjjvalue(new BigDecimal(0));
    	this.setStockvalue(new BigDecimal(0));
    	this.setTaxableamt(new BigDecimal(0));
    	this.setWarrantcost(new BigDecimal(0));
    	this.setWarrantvalue(new BigDecimal(0));	
    }
	

}