package com.ebills.product.declare.domain;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import com.ebills.product.declare.Entity;


/**
 * SbJncnyzhszxx entity. @author MyEclipse Persistence Tools
 * RQFII境内人民币账户收支情况信息
 * 对应的表名:sb_Jncnyzhszxx
 */

public class Jncnyzhszxx extends Entity {

	// Fields
	
	/**操作流水号*/
	private String nguid;
	/**QFII机构代码*/
	private String qfiicod;
	/**QFII机构名称*/
	private String qfiiname;
	/**托管人代码*/
	private String apno;
	/**RQFII人民币账户名称*/
	private String cnyaccountname;
	/**RQFII人民币账户账号*/
	private String cnyaccount;
	/**报告期(YYYYMM)*/
	private String reportdate;
	/**结算日*/
	private Date settlementdate;
	/**币种*/
	private String currency;
	/**本月投资本金汇入金额*/
	private BigDecimal bytzbjhramt;
	/**累计投资本金汇入金额*/
	private BigDecimal tzbjhramtsum;
	/**本月卖出证券所得价款额*/
	private BigDecimal bysalezjamt;
	/**累计卖出证券所得价款额*/
	private BigDecimal salezjamtsum;
	/**本月交易所市场卖出证券所得价款额*/
	private BigDecimal byjyssalezjamt;
	/**累计交易所市场卖出证券所得价款额*/
	private BigDecimal jyssalezjamtsum;
	/**本月银行间市场卖出证券所得价款额*/
	private BigDecimal bybarsalezjamt;
	/**累计银行间市场卖出证券所得价款额*/
	private BigDecimal barsalezjamtsum;
	/**本月股利收入金额*/
	private BigDecimal byglsramt;
	/**累计股利收入金额*/
	private BigDecimal glsramtsum;
	/**本月利息收入金额*/
	private BigDecimal bylxsramt;
	/**累计利息收入金额*/
	private BigDecimal lxsramtsum;
	/**本月其他收入金额*/
	private BigDecimal byqtsramt;
	/**累计其他收入金额*/
	private BigDecimal qtsramtsum;
	/**本月收入合计*/
	private BigDecimal bysramt;
	/**累计收入合计*/
	private BigDecimal sramtsum;
	/**本月汇出资金金额*/
	private BigDecimal byhczjamt;
	/**累计汇出资金金额*/
	private BigDecimal hczjamtsum;
	/**本月购汇汇出资金金额*/
	private BigDecimal byghhczjamt;
	/**累计购汇汇出资金金额*/
	private BigDecimal ghhczjamtsum;
	/**本月买入证券支付价款额*/
	private BigDecimal bybuyzjamt;
	/**累计买入证券支付价款额*/
	private BigDecimal buyzjamtsum;
	/**本月交易所市场买入证券支付价款额*/
	private BigDecimal byjysbuyzjamt;
	/**累计交易所市场买入证券支付价款额*/
	private BigDecimal jysbuyzjamtsum;
	/**本月银行间市场买入证券支付价款额*/
	private BigDecimal bybarbuyzjamt;
	/**累计银行间市场买入证券支付价款额*/
	private BigDecimal barbuyzjamtsum;
	/**本月托管费支出金额*/
	private BigDecimal bytgfamt;
	/**累计托管费支出金额*/
	private BigDecimal tgfamtsum;
	/**本月管理费支出金额*/
	private BigDecimal byglfzcamt;
	/**累计管理费支出金额*/
	private BigDecimal glfzcamtsum;
	/**本月其他支出金额*/
	private BigDecimal byqtzcamt;
	/**累计其他支出金额*/
	private BigDecimal qtzcamtsum;
	/**本月支出合计*/
	private BigDecimal byzcamtsum;
	/**累计支出合计*/
	private BigDecimal zcamtsum;
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
	
	
	
	
	public Jncnyzhszxx() {
		this.setBytzbjhramt(new BigDecimal(0));
		this.setTzbjhramtsum(new BigDecimal(0));
		this.setBysalezjamt(new BigDecimal(0));
		this.setSalezjamtsum(new BigDecimal(0));
		this.setByjyssalezjamt(new BigDecimal(0));
		this.setJyssalezjamtsum(new BigDecimal(0));
		this.setBybarsalezjamt(new BigDecimal(0));
		this.setBarsalezjamtsum(new BigDecimal(0));
		this.setByglsramt(new BigDecimal(0));
		this.setGlsramtsum(new BigDecimal(0));
		this.setBylxsramt(new BigDecimal(0));
		this.setLxsramtsum(new BigDecimal(0));
		this.setByqtsramt(new BigDecimal(0));
		this.setQtsramtsum(new BigDecimal(0));
		this.setBysramt(new BigDecimal(0));
		this.setSramtsum(new BigDecimal(0));
		this.setByhczjamt(new BigDecimal(0));
		this.setHczjamtsum(new BigDecimal(0));
		this.setByghhczjamt(new BigDecimal(0));
		this.setGhhczjamtsum(new BigDecimal(0));
		this.setBybuyzjamt(new BigDecimal(0));
		this.setBuyzjamtsum(new BigDecimal(0));
		this.setByjysbuyzjamt(new BigDecimal(0));
		this.setJysbuyzjamtsum(new BigDecimal(0));
		this.setBybarbuyzjamt(new BigDecimal(0));
		this.setBarbuyzjamtsum(new BigDecimal(0));
		this.setBytgfamt(new BigDecimal(0));
		this.setTgfamtsum(new BigDecimal(0));
		this.setByglfzcamt(new BigDecimal(0));
		this.setGlfzcamtsum(new BigDecimal(0));
		this.setByqtzcamt(new BigDecimal(0));
		this.setQtzcamtsum(new BigDecimal(0));
		this.setByzcamtsum(new BigDecimal(0));
		this.setZcamtsum(new BigDecimal(0));
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
	
	public BigDecimal getBytzbjhramt() {
		return bytzbjhramt;
	}
	public void setBytzbjhramt(BigDecimal bytzbjhramt) {
		this.bytzbjhramt = bytzbjhramt;
	}
	public BigDecimal getTzbjhramtsum() {
		return tzbjhramtsum;
	}
	public void setTzbjhramtsum(BigDecimal tzbjhramtsum) {
		this.tzbjhramtsum = tzbjhramtsum;
	}
	public BigDecimal getBysalezjamt() {
		return bysalezjamt;
	}
	public void setBysalezjamt(BigDecimal bysalezjamt) {
		this.bysalezjamt = bysalezjamt;
	}
	public BigDecimal getSalezjamtsum() {
		return salezjamtsum;
	}
	public void setSalezjamtsum(BigDecimal salezjamtsum) {
		this.salezjamtsum = salezjamtsum;
	}
	public BigDecimal getByjyssalezjamt() {
		return byjyssalezjamt;
	}
	public void setByjyssalezjamt(BigDecimal byjyssalezjamt) {
		this.byjyssalezjamt = byjyssalezjamt;
	}
	public BigDecimal getJyssalezjamtsum() {
		return jyssalezjamtsum;
	}
	public void setJyssalezjamtsum(BigDecimal jyssalezjamtsum) {
		this.jyssalezjamtsum = jyssalezjamtsum;
	}
	public BigDecimal getBybarsalezjamt() {
		return bybarsalezjamt;
	}
	public void setBybarsalezjamt(BigDecimal bybarsalezjamt) {
		this.bybarsalezjamt = bybarsalezjamt;
	}
	public BigDecimal getBarsalezjamtsum() {
		return barsalezjamtsum;
	}
	public void setBarsalezjamtsum(BigDecimal barsalezjamtsum) {
		this.barsalezjamtsum = barsalezjamtsum;
	}
	public BigDecimal getByglsramt() {
		return byglsramt;
	}
	public void setByglsramt(BigDecimal byglsramt) {
		this.byglsramt = byglsramt;
	}
	public BigDecimal getGlsramtsum() {
		return glsramtsum;
	}
	public void setGlsramtsum(BigDecimal glsramtsum) {
		this.glsramtsum = glsramtsum;
	}
	public BigDecimal getBylxsramt() {
		return bylxsramt;
	}
	public void setBylxsramt(BigDecimal bylxsramt) {
		this.bylxsramt = bylxsramt;
	}
	public BigDecimal getLxsramtsum() {
		return lxsramtsum;
	}
	public void setLxsramtsum(BigDecimal lxsramtsum) {
		this.lxsramtsum = lxsramtsum;
	}
	public BigDecimal getByqtsramt() {
		return byqtsramt;
	}
	public void setByqtsramt(BigDecimal byqtsramt) {
		this.byqtsramt = byqtsramt;
	}
	public BigDecimal getQtsramtsum() {
		return qtsramtsum;
	}
	public void setQtsramtsum(BigDecimal qtsramtsum) {
		this.qtsramtsum = qtsramtsum;
	}
	public BigDecimal getBysramt() {
		return bysramt;
	}
	public void setBysramt(BigDecimal bysramt) {
		this.bysramt = bysramt;
	}
	public BigDecimal getSramtsum() {
		return sramtsum;
	}
	public void setSramtsum(BigDecimal sramtsum) {
		this.sramtsum = sramtsum;
	}
	public BigDecimal getByhczjamt() {
		return byhczjamt;
	}
	public void setByhczjamt(BigDecimal byhczjamt) {
		this.byhczjamt = byhczjamt;
	}
	public BigDecimal getHczjamtsum() {
		return hczjamtsum;
	}
	public void setHczjamtsum(BigDecimal hczjamtsum) {
		this.hczjamtsum = hczjamtsum;
	}
	public BigDecimal getByghhczjamt() {
		return byghhczjamt;
	}
	public void setByghhczjamt(BigDecimal byghhczjamt) {
		this.byghhczjamt = byghhczjamt;
	}
	public BigDecimal getGhhczjamtsum() {
		return ghhczjamtsum;
	}
	public void setGhhczjamtsum(BigDecimal ghhczjamtsum) {
		this.ghhczjamtsum = ghhczjamtsum;
	}
	public BigDecimal getBybuyzjamt() {
		return bybuyzjamt;
	}
	public void setBybuyzjamt(BigDecimal bybuyzjamt) {
		this.bybuyzjamt = bybuyzjamt;
	}
	public BigDecimal getBuyzjamtsum() {
		return buyzjamtsum;
	}
	public void setBuyzjamtsum(BigDecimal buyzjamtsum) {
		this.buyzjamtsum = buyzjamtsum;
	}
	public BigDecimal getByjysbuyzjamt() {
		return byjysbuyzjamt;
	}
	public void setByjysbuyzjamt(BigDecimal byjysbuyzjamt) {
		this.byjysbuyzjamt = byjysbuyzjamt;
	}
	public BigDecimal getJysbuyzjamtsum() {
		return jysbuyzjamtsum;
	}
	public void setJysbuyzjamtsum(BigDecimal jysbuyzjamtsum) {
		this.jysbuyzjamtsum = jysbuyzjamtsum;
	}
	public BigDecimal getBybarbuyzjamt() {
		return bybarbuyzjamt;
	}
	public void setBybarbuyzjamt(BigDecimal bybarbuyzjamt) {
		this.bybarbuyzjamt = bybarbuyzjamt;
	}
	public BigDecimal getBarbuyzjamtsum() {
		return barbuyzjamtsum;
	}
	public void setBarbuyzjamtsum(BigDecimal barbuyzjamtsum) {
		this.barbuyzjamtsum = barbuyzjamtsum;
	}
	public BigDecimal getBytgfamt() {
		return bytgfamt;
	}
	public void setBytgfamt(BigDecimal bytgfamt) {
		this.bytgfamt = bytgfamt;
	}
	public BigDecimal getTgfamtsum() {
		return tgfamtsum;
	}
	public void setTgfamtsum(BigDecimal tgfamtsum) {
		this.tgfamtsum = tgfamtsum;
	}
	public BigDecimal getByglfzcamt() {
		return byglfzcamt;
	}
	public void setByglfzcamt(BigDecimal byglfzcamt) {
		this.byglfzcamt = byglfzcamt;
	}
	public BigDecimal getGlfzcamtsum() {
		return glfzcamtsum;
	}
	public void setGlfzcamtsum(BigDecimal glfzcamtsum) {
		this.glfzcamtsum = glfzcamtsum;
	}
	public BigDecimal getByqtzcamt() {
		return byqtzcamt;
	}
	public void setByqtzcamt(BigDecimal byqtzcamt) {
		this.byqtzcamt = byqtzcamt;
	}
	public BigDecimal getQtzcamtsum() {
		return qtzcamtsum;
	}
	public void setQtzcamtsum(BigDecimal qtzcamtsum) {
		this.qtzcamtsum = qtzcamtsum;
	}
	public BigDecimal getByzcamtsum() {
		return byzcamtsum;
	}
	public void setByzcamtsum(BigDecimal byzcamtsum) {
		this.byzcamtsum = byzcamtsum;
	}
	public BigDecimal getZcamtsum() {
		return zcamtsum;
	}
	public void setZcamtsum(BigDecimal zcamtsum) {
		this.zcamtsum = zcamtsum;
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





}