package com.ebills.product.declare.domain;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Date;

import com.ebills.product.declare.Entity;


/**
 * SbQfiiydzcfzxx entity. @author MyEclipse Persistence Tools
 * 对应的表名：sb_Qfiiydzcfzxx
 */

public class Qfiiydzcfzxx extends Entity {

	// Fields

	 //操作流水号
	private String nguid;
	//托管人代码
	private String apno;
	//QFII机构代码
	private String qfiicod;
	//QFII机构名称
	private String qfiiname;
	//业务编号
	private String bizo;
	//产品名称
	private String producename;
	//报告期(YYYYMM)
	private String reportdate;
	//结算日
	private Date settlementdate;
	//币种
	private String currency;
	//银行存款余额
	private BigDecimal depositamt;
	//股票成本
	private BigDecimal stockcost;
	//股票市值
	private BigDecimal stockvalue;
	//国债成本
	private BigDecimal governmentloancost;
	//国债市值
	private BigDecimal governmentloanvalue;
	//可转债成本
	private BigDecimal convertiblebondcost;
	//可转债市值
	private BigDecimal convertiblebondvalue;
	//公司债成本
	private BigDecimal corporatebondcost;
	//公司债市值
	private BigDecimal corporatebondvalue;
	
	//封闭式基金成本
	private BigDecimal colseendfundcost;
	//封闭式基金市值
	private BigDecimal colseendfundvalue;
	//开放式基金成本
	private BigDecimal openendfundcost;
	//开放式基金市值
	private BigDecimal openendfundvalue;
	//权证成本
	private BigDecimal warrantcost;
	//权证市值
	private BigDecimal warrantvalue;
	//股指期货成本
	private BigDecimal indexfuturescost;
	//股指期货市值
	private BigDecimal indexfuturesvalue;
	//其他投资成本
	private BigDecimal othercost;
	//其他投资市值
	private BigDecimal othervalue;
	//投资市值合计
	private BigDecimal investvaluesum;
	//应收股利金额
	private BigDecimal reviceclearamt;
	//应收清算款金额
	private BigDecimal reviceglamt;
	//应收利息金额
	private BigDecimal revicelxamt;
	//其他应收款金额
	private BigDecimal reviceotheramt;
	//资产合计
	private BigDecimal propertysum;
	//	应付清算款金额
	private BigDecimal payqsamt;
	//应付托管费金额
	private BigDecimal paytgamt;
	//应付管理费金额
	private BigDecimal payglamt;
	//应纳税款金额
	private BigDecimal taxableamt;
	//其他应付款金额
	private BigDecimal payotheramt;
	//负债合计
	private BigDecimal liabilitiessum;
	//净资产
	private BigDecimal netasset;
	//本月买入周转率
	private double bybuyrate;
	//本月卖出周转率
	private double bysalerate;
	//平均获利率
	private double avgrate;
	//本月平均获利率
	private double byavgrate;
	//备注
	private String memo;
	//业务流水号
	private String rwidh;
	//业务编号 
	private String ywbh;
	//是否已申报 
	private String sfysb;
	//生效标志 
	private String sxbz;
	//最新标志 
	private String sfzx;
	//机构编号 
	private String bank_Id;
	//经办人 
	private int handid;
	//经办日期 
	private Timestamp handdate;
	//复核人  
	private int checkid;
	//复核时间
	private Timestamp checkdate;
	//是否复核通过
	private String ischeck;
	//授权人 
	private int authid;
	//授权时间 
	private Timestamp authdate;
	//授权是否通过
	private String isauth;
	//
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

	public Qfiiydzcfzxx(){
		this.setSfysb("N");
		this.setSfzx("Y");
		this.setSxbz("0");
		this.setDepositamt(new BigDecimal(0));
		this.setStockcost(new BigDecimal(0));
		this.setStockvalue(new BigDecimal(0));
		this.setGovernmentloancost(new BigDecimal(0));
		this.setGovernmentloanvalue(new BigDecimal(0));
		this.setConvertiblebondcost(new BigDecimal(0));
		this.setConvertiblebondvalue(new BigDecimal(0));
		this.setCorporatebondcost(new BigDecimal(0));
		this.setCorporatebondvalue(new BigDecimal(0));
		this.setColseendfundcost(new BigDecimal(0));
		this.setColseendfundvalue(new BigDecimal(0));
		this.setOpenendfundcost(new BigDecimal(0));
		this.setOpenendfundvalue(new BigDecimal(0));
		this.setWarrantcost(new BigDecimal(0));	
		this.setWarrantvalue(new BigDecimal(0));
		this.setIndexfuturescost(new BigDecimal(0));
		this.setIndexfuturesvalue(new BigDecimal(0));
		this.setOthercost(new BigDecimal(0));
		this.setOthervalue(new BigDecimal(0));
		this.setInvestvaluesum(new BigDecimal(0));
		this.setReviceclearamt(new BigDecimal(0));
		this.setReviceglamt(new BigDecimal(0));
		this.setRevicelxamt(new BigDecimal(0));
		this.setReviceotheramt(new BigDecimal(0));
		this.setPropertysum(new BigDecimal(0));
		this.setPayqsamt(new BigDecimal(0));
		this.setPaytgamt(new BigDecimal(0));
		this.setPayglamt(new BigDecimal(0));
		this.setTaxableamt(new BigDecimal(0));
		this.setPayotheramt(new BigDecimal(0));
		this.setLiabilitiessum(new BigDecimal(0));
		this.setNetasset(new BigDecimal(0));
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

	public String getReportdate() {
		return reportdate;
	}

	public void setReportdate(String reportdate) {
		this.reportdate = reportdate;
	}

	public Date getSettlementdate() {
		return settlementdate;
	}

	public void setSettlementdate(Date settlementdate) {
		this.settlementdate = settlementdate;
	}

	public String getCurrency() {
		return currency;
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

	public BigDecimal getStockcost() {
		return stockcost;
	}

	public void setStockcost(BigDecimal stockcost) {
		this.stockcost = stockcost;
	}

	public BigDecimal getStockvalue() {
		return stockvalue;
	}

	public void setStockvalue(BigDecimal stockvalue) {
		this.stockvalue = stockvalue;
	}

	public BigDecimal getGovernmentloancost() {
		return governmentloancost;
	}

	public void setGovernmentloancost(BigDecimal governmentloancost) {
		this.governmentloancost = governmentloancost;
	}

	public BigDecimal getGovernmentloanvalue() {
		return governmentloanvalue;
	}

	public void setGovernmentloanvalue(BigDecimal governmentloanvalue) {
		this.governmentloanvalue = governmentloanvalue;
	}

	public BigDecimal getConvertiblebondcost() {
		return convertiblebondcost;
	}

	public void setConvertiblebondcost(BigDecimal convertiblebondcost) {
		this.convertiblebondcost = convertiblebondcost;
	}

	public BigDecimal getConvertiblebondvalue() {
		return convertiblebondvalue;
	}

	public void setConvertiblebondvalue(BigDecimal convertiblebondvalue) {
		this.convertiblebondvalue = convertiblebondvalue;
	}

	public BigDecimal getCorporatebondcost() {
		return corporatebondcost;
	}

	public void setCorporatebondcost(BigDecimal corporatebondcost) {
		this.corporatebondcost = corporatebondcost;
	}

	public BigDecimal getCorporatebondvalue() {
		return corporatebondvalue;
	}

	public void setCorporatebondvalue(BigDecimal corporatebondvalue) {
		this.corporatebondvalue = corporatebondvalue;
	}

	public BigDecimal getColseendfundcost() {
		return colseendfundcost;
	}

	public void setColseendfundcost(BigDecimal colseendfundcost) {
		this.colseendfundcost = colseendfundcost;
	}

	public BigDecimal getColseendfundvalue() {
		return colseendfundvalue;
	}

	public void setColseendfundvalue(BigDecimal colseendfundvalue) {
		this.colseendfundvalue = colseendfundvalue;
	}

	public BigDecimal getOpenendfundcost() {
		return openendfundcost;
	}

	public void setOpenendfundcost(BigDecimal openendfundcost) {
		this.openendfundcost = openendfundcost;
	}

	public BigDecimal getOpenendfundvalue() {
		return openendfundvalue;
	}

	public void setOpenendfundvalue(BigDecimal openendfundvalue) {
		this.openendfundvalue = openendfundvalue;
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

	public BigDecimal getOthercost() {
		return othercost;
	}

	public void setOthercost(BigDecimal othercost) {
		this.othercost = othercost;
	}

	public BigDecimal getOthervalue() {
		return othervalue;
	}

	public void setOthervalue(BigDecimal othervalue) {
		this.othervalue = othervalue;
	}

	public BigDecimal getInvestvaluesum() {
		return investvaluesum;
	}

	public void setInvestvaluesum(BigDecimal investvaluesum) {
		this.investvaluesum = investvaluesum;
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

	public BigDecimal getPropertysum() {
		return propertysum;
	}

	public void setPropertysum(BigDecimal propertysum) {
		this.propertysum = propertysum;
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

	public BigDecimal getPayglamt() {
		return payglamt;
	}

	public void setPayglamt(BigDecimal payglamt) {
		this.payglamt = payglamt;
	}

	public BigDecimal getTaxableamt() {
		return taxableamt;
	}

	public void setTaxableamt(BigDecimal taxableamt) {
		this.taxableamt = taxableamt;
	}

	public BigDecimal getPayotheramt() {
		return payotheramt;
	}

	public void setPayotheramt(BigDecimal payotheramt) {
		this.payotheramt = payotheramt;
	}

	public BigDecimal getLiabilitiessum() {
		return liabilitiessum;
	}

	public void setLiabilitiessum(BigDecimal liabilitiessum) {
		this.liabilitiessum = liabilitiessum;
	}

	public BigDecimal getNetasset() {
		return netasset;
	}

	public void setNetasset(BigDecimal netasset) {
		this.netasset = netasset;
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

	public double getAvgrate() {
		return avgrate;
	}

	public void setAvgrate(double avgrate) {
		this.avgrate = avgrate;
	}

	public double getByavgrate() {
		return byavgrate;
	}

	public void setByavgrate(double byavgrate) {
		this.byavgrate = byavgrate;
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
	
}