package com.ebills.product.declare.domain;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import com.ebills.product.declare.Entity;



/**
 * QDII境外证券投资信息
 * SbQdiijwzqtzxx entity. @author MyEclipse Persistence Tools
 * 对应的表名：sb_Qdiijwzqtzxx
 */

public class Qdiijwzqtzxx extends Entity {

	// Fields
	/**操作流水号*/
	private String nguid;
	/**QDII机构代码*/
	private String qdiiorgno;
	/**QDII机构中文名称*/
	private String qdiiorgname;
	/**QDII境内托管行代码*/
	private String qdiidepoitno;
	/**产品/客户名称*/
	private String customno;
	/**报告期*/
	private String reportdate;
	/**币种*/
	private String currency;
	/**银行存款余额*/
	private BigDecimal banksavebanalce;
	/**货币市场工具成本*/
	private BigDecimal moneymarketcost;
	/**货币市场工具市值*/
	private BigDecimal moneymarketvalue;
	/**债券成本*/
	private BigDecimal bondcost;
	/**债券市值*/
	private BigDecimal bondvalue;
	/**公司股票成本*/
	private BigDecimal stockcost;
	/**公司股票市值*/
	private BigDecimal stockvalue;
	/**基金成本*/
	private BigDecimal fundcost;
	/**基金市值*/
	private BigDecimal fundvalue;
	/**衍生产品成本*/
	private BigDecimal derivativescost;
	/**衍生产品市值*/
	private BigDecimal derivativesvalue;
	/**其他投资成本*/
	private BigDecimal othercost;
	/**其他投资市值*/
	private BigDecimal othervalue;
	/**投资市值合计*/
	private BigDecimal investvalue;
	/**预付投资款金额*/
	private BigDecimal paymentamt;
	/**应收投资款金额*/
	private BigDecimal reciveinvestamt;
	/**应收股利金额*/
	private BigDecimal recivedividamt;
	/**应收利息金额*/
	private BigDecimal reciveinsterestamt;
	/**其他应收款金额*/
	private BigDecimal otherreciveamt;
	/**资产合计*/
	private BigDecimal propertysum;
	/**应付投资款金额*/
	private BigDecimal handleinvestamt;
	/**应付托管费金额*/
	private BigDecimal handledepoitamt;
	/**应付佣金额*/
	private BigDecimal handlefeeamt;
	/**应付管理费金额*/
	private BigDecimal handlemanageramt;
	/**应纳税款金额*/
	private BigDecimal handleratepayamt;
	/**其他应付款金额*/
	private BigDecimal otherhandleamt;
	/**负债合计*/
	private BigDecimal debtssum;
	/**净资产*/
	private BigDecimal netasset;
	/**所托管人民币资金存款余额*/
	private BigDecimal depoitbanlancermb;
	/**备注*/
	private String remark;
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
	//复核时
	private Timestamp checkdate;
	//是否复核通过
	private String ischeck;
	//授权人
	private int authid;
	//授权时间
	private Timestamp authdate;
	//授权是否通
	private String isauth;
	//老业务编号
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

	public Qdiijwzqtzxx(){
		this.setSfysb("N");
		this.setSfzx("Y");
		this.setSxbz("0");
		this.setBanksavebanalce(new BigDecimal(0));
		this.setMoneymarketcost(new BigDecimal(0));
		this.setMoneymarketvalue(new BigDecimal(0));
		this.setBondcost(new BigDecimal(0));
		this.setBondvalue(new BigDecimal(0));
		this.setStockcost(new BigDecimal(0));
		this.setStockvalue(new BigDecimal(0));
		this.setFundcost(new BigDecimal(0));
		this.setFundvalue(new BigDecimal(0));
		this.setDerivativescost(new BigDecimal(0));
		this.setDerivativesvalue(new BigDecimal(0));
		this.setOthercost(new BigDecimal(0));
		this.setOthervalue(new BigDecimal(0));
		this.setInvestvalue(new BigDecimal(0));
		this.setPaymentamt(new BigDecimal(0));
		this.setReciveinvestamt(new BigDecimal(0));
		this.setRecivedividamt(new BigDecimal(0));
		this.setReciveinsterestamt(new BigDecimal(0));
		this.setOtherreciveamt(new BigDecimal(0));
		this.setPropertysum(new BigDecimal(0));
		this.setHandleinvestamt(new BigDecimal(0));
		this.setHandledepoitamt(new BigDecimal(0));
		this.setHandlefeeamt(new BigDecimal(0));
		this.setHandlemanageramt(new BigDecimal(0));
		this.setHandleratepayamt(new BigDecimal(0));
		this.setOtherhandleamt(new BigDecimal(0));
		this.setDebtssum(new BigDecimal(0));
		this.setNetasset(new BigDecimal(0));
		this.setDepoitbanlancermb(new BigDecimal(0));
    }
	
	public String getNguid() {
		return nguid;
	}
	public void setNguid(String nguid) {
		this.nguid = nguid;
	}
	public String getQdiiorgno() {
		return qdiiorgno;
	}
	public void setQdiiorgno(String qdiiorgno) {
		this.qdiiorgno = qdiiorgno;
	}
	public String getQdiiorgname() {
		return qdiiorgname;
	}
	public void setQdiiorgname(String qdiiorgname) {
		this.qdiiorgname = qdiiorgname;
	}
	public String getQdiidepoitno() {
		return qdiidepoitno;
	}
	public void setQdiidepoitno(String qdiidepoitno) {
		this.qdiidepoitno = qdiidepoitno;
	}
	public String getCustomno() {
		return customno;
	}
	public void setCustomno(String customno) {
		this.customno = customno;
	}
	public String getReportdate() {
		return reportdate;
	}
	public void setReportdate(String reportdate) {
		this.reportdate = reportdate;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public BigDecimal getBanksavebanalce() {
		return banksavebanalce;
	}

	public void setBanksavebanalce(BigDecimal banksavebanalce) {
		this.banksavebanalce = banksavebanalce;
	}

	public BigDecimal getMoneymarketcost() {
		return moneymarketcost;
	}

	public void setMoneymarketcost(BigDecimal moneymarketcost) {
		this.moneymarketcost = moneymarketcost;
	}

	public BigDecimal getMoneymarketvalue() {
		return moneymarketvalue;
	}

	public void setMoneymarketvalue(BigDecimal moneymarketvalue) {
		this.moneymarketvalue = moneymarketvalue;
	}

	public BigDecimal getBondcost() {
		return bondcost;
	}

	public void setBondcost(BigDecimal bondcost) {
		this.bondcost = bondcost;
	}

	public BigDecimal getBondvalue() {
		return bondvalue;
	}

	public void setBondvalue(BigDecimal bondvalue) {
		this.bondvalue = bondvalue;
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

	public BigDecimal getFundcost() {
		return fundcost;
	}

	public void setFundcost(BigDecimal fundcost) {
		this.fundcost = fundcost;
	}

	public BigDecimal getFundvalue() {
		return fundvalue;
	}

	public void setFundvalue(BigDecimal fundvalue) {
		this.fundvalue = fundvalue;
	}

	public BigDecimal getDerivativescost() {
		return derivativescost;
	}

	public void setDerivativescost(BigDecimal derivativescost) {
		this.derivativescost = derivativescost;
	}

	public BigDecimal getDerivativesvalue() {
		return derivativesvalue;
	}

	public void setDerivativesvalue(BigDecimal derivativesvalue) {
		this.derivativesvalue = derivativesvalue;
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

	public BigDecimal getInvestvalue() {
		return investvalue;
	}

	public void setInvestvalue(BigDecimal investvalue) {
		this.investvalue = investvalue;
	}

	public BigDecimal getPaymentamt() {
		return paymentamt;
	}

	public void setPaymentamt(BigDecimal paymentamt) {
		this.paymentamt = paymentamt;
	}

	public BigDecimal getReciveinvestamt() {
		return reciveinvestamt;
	}

	public void setReciveinvestamt(BigDecimal reciveinvestamt) {
		this.reciveinvestamt = reciveinvestamt;
	}

	public BigDecimal getRecivedividamt() {
		return recivedividamt;
	}

	public void setRecivedividamt(BigDecimal recivedividamt) {
		this.recivedividamt = recivedividamt;
	}

	public BigDecimal getReciveinsterestamt() {
		return reciveinsterestamt;
	}

	public void setReciveinsterestamt(BigDecimal reciveinsterestamt) {
		this.reciveinsterestamt = reciveinsterestamt;
	}

	public BigDecimal getOtherreciveamt() {
		return otherreciveamt;
	}

	public void setOtherreciveamt(BigDecimal otherreciveamt) {
		this.otherreciveamt = otherreciveamt;
	}

	public BigDecimal getPropertysum() {
		return propertysum;
	}

	public void setPropertysum(BigDecimal propertysum) {
		this.propertysum = propertysum;
	}

	public BigDecimal getHandleinvestamt() {
		return handleinvestamt;
	}

	public void setHandleinvestamt(BigDecimal handleinvestamt) {
		this.handleinvestamt = handleinvestamt;
	}

	public BigDecimal getHandledepoitamt() {
		return handledepoitamt;
	}

	public void setHandledepoitamt(BigDecimal handledepoitamt) {
		this.handledepoitamt = handledepoitamt;
	}

	public BigDecimal getHandlefeeamt() {
		return handlefeeamt;
	}

	public void setHandlefeeamt(BigDecimal handlefeeamt) {
		this.handlefeeamt = handlefeeamt;
	}

	public BigDecimal getHandlemanageramt() {
		return handlemanageramt;
	}

	public void setHandlemanageramt(BigDecimal handlemanageramt) {
		this.handlemanageramt = handlemanageramt;
	}

	public BigDecimal getHandleratepayamt() {
		return handleratepayamt;
	}

	public void setHandleratepayamt(BigDecimal handleratepayamt) {
		this.handleratepayamt = handleratepayamt;
	}

	public BigDecimal getOtherhandleamt() {
		return otherhandleamt;
	}

	public void setOtherhandleamt(BigDecimal otherhandleamt) {
		this.otherhandleamt = otherhandleamt;
	}

	public BigDecimal getDebtssum() {
		return debtssum;
	}

	public void setDebtssum(BigDecimal debtssum) {
		this.debtssum = debtssum;
	}

	public BigDecimal getNetasset() {
		return netasset;
	}

	public void setNetasset(BigDecimal netasset) {
		this.netasset = netasset;
	}

	public BigDecimal getDepoitbanlancermb() {
		return depoitbanlancermb;
	}

	public void setDepoitbanlancermb(BigDecimal depoitbanlancermb) {
		this.depoitbanlancermb = depoitbanlancermb;
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