package com.ebills.product.declare.domain;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Date;

import com.ebills.product.declare.Entity;


/**
 * SbCnyzhszxx entity. @author MyEclipse Persistence Tools
 * 对应的表名:sb_Cnyzhszxx
 */

public class Cnyzhszxx extends Entity {

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
	//人民币特殊账户账号
	private String cnyaccount;
	//本月外汇账户结汇划入金额
	private BigDecimal byjhhramt;
	//累计外汇账户结汇划入金额
	private BigDecimal jhhramtsum;
	//本月卖出证券所得价款金额
	private BigDecimal bysellzjamt;
	//累计卖出证券所得价款金额
	private BigDecimal swllzjamtsum;
	//本月股利收入金额
	private BigDecimal byglsramt;
	//累计股利收入金额
	private BigDecimal glsramtsum;
	//本月利息收入金额
	private BigDecimal bylxsramt;
	//累计利息收入金额
	private BigDecimal lxsramtsum;
	//本月其他收入金额
	private BigDecimal byqtsramt;
	//累计其他收入金额
	private BigDecimal qtsramtsum;
	//本月收入合计
	private BigDecimal bysramt;
	//累计收入合计
	private BigDecimal sramtsum;
	//本月购汇划入外汇账户金额
	private BigDecimal byghhrwhamt;
	//累计购汇划入外汇账户金额
	private BigDecimal ghhrwhamtsum;
	//本月买入证券支付价款金额
	private BigDecimal bymrzjzfamt;
	//累计买入证券支付价款金额
	private BigDecimal mrzjzfamtsum;
	//本月托管费支出金额
	private BigDecimal bytgfamt;
	//累计托管费支出金额
	private BigDecimal tgfamtsum;
	//本月管理费支出金额
	private BigDecimal byglfzcamt;
	//累计管理费支出金额
	private BigDecimal glfzcamtsum;
	//本月其他支出金额
	private BigDecimal byqtzcamt;
	//累计其他支出金额
	private BigDecimal qtzcamtsum;
	//本月支出合计
	private BigDecimal byzcamtsum;
	//累计支出合计
	private BigDecimal zcamtsum;
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
	// Property accessors
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
	public String getCnyaccount() {
		return cnyaccount;
	}
	public void setCnyaccount(String cnyaccount) {
		this.cnyaccount = cnyaccount;
	}
	
	public Cnyzhszxx(){
		
		this.setByjhhramt(new BigDecimal(0));
		this.setJhhramtsum(new BigDecimal(0));
		this.setBysellzjamt(new BigDecimal(0));
		this.setSwllzjamtsum(new BigDecimal(0));
		this.setByglsramt(new BigDecimal(0));
		this.setGlsramtsum(new BigDecimal(0));
		this.setBylxsramt(new BigDecimal(0));
		this.setLxsramtsum(new BigDecimal(0));
		this.setByqtsramt(new BigDecimal(0));
		this.setQtsramtsum(new BigDecimal(0));
		this.setBysramt(new BigDecimal(0));
		this.setSramtsum(new BigDecimal(0));
		this.setByghhrwhamt(new BigDecimal(0));
		this.setGhhrwhamtsum(new BigDecimal(0));
		this.setBymrzjzfamt(new BigDecimal(0));
		this.setMrzjzfamtsum(new BigDecimal(0));
		this.setBytgfamt(new BigDecimal(0));
		this.setTgfamtsum(new BigDecimal(0));
		this.setByglfzcamt(new BigDecimal(0));
		this.setGlfzcamtsum(new BigDecimal(0));
		this.setByqtzcamt(new BigDecimal(0));
		this.setQtzcamtsum(new BigDecimal(0));
		this.setByzcamtsum(new BigDecimal(0));
		this.setZcamtsum(new BigDecimal(0));
		
	}
	public BigDecimal getByjhhramt() {
		return byjhhramt;
	}
	public void setByjhhramt(BigDecimal byjhhramt) {
		this.byjhhramt = byjhhramt;
	}
	public BigDecimal getJhhramtsum() {
		return jhhramtsum;
	}
	public void setJhhramtsum(BigDecimal jhhramtsum) {
		this.jhhramtsum = jhhramtsum;
	}
	public BigDecimal getBysellzjamt() {
		return bysellzjamt;
	}
	public void setBysellzjamt(BigDecimal bysellzjamt) {
		this.bysellzjamt = bysellzjamt;
	}
	public BigDecimal getSwllzjamtsum() {
		return swllzjamtsum;
	}
	public void setSwllzjamtsum(BigDecimal swllzjamtsum) {
		this.swllzjamtsum = swllzjamtsum;
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
	public BigDecimal getByghhrwhamt() {
		return byghhrwhamt;
	}
	public void setByghhrwhamt(BigDecimal byghhrwhamt) {
		this.byghhrwhamt = byghhrwhamt;
	}
	public BigDecimal getGhhrwhamtsum() {
		return ghhrwhamtsum;
	}
	public void setGhhrwhamtsum(BigDecimal ghhrwhamtsum) {
		this.ghhrwhamtsum = ghhrwhamtsum;
	}
	public BigDecimal getBymrzjzfamt() {
		return bymrzjzfamt;
	}
	public void setBymrzjzfamt(BigDecimal bymrzjzfamt) {
		this.bymrzjzfamt = bymrzjzfamt;
	}
	public BigDecimal getMrzjzfamtsum() {
		return mrzjzfamtsum;
	}
	public void setMrzjzfamtsum(BigDecimal mrzjzfamtsum) {
		this.mrzjzfamtsum = mrzjzfamtsum;
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