package com.ebills.product.declare.domain;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import com.ebills.product.declare.Entity;


/**
 * SbSybxx entity. @author MyEclipse Persistence Tools
 * RQFII境内证券投资年度损益表信息
 * 对应的表名：sb_Sybxx
 */

public class Sybxx extends Entity  {

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
	/**股利收入金额*/
	private BigDecimal glsramt;
	/**利息收入金额*/
	private BigDecimal lxsramt;
	/**公允价值变动损益金额*/
	private BigDecimal gxjzjsyamt;
	/**投资损益金额*/
	private BigDecimal tzsyamt;
	/**其他收入金额*/
	private BigDecimal qtsramt;
	/**收入合计*/
	private BigDecimal sramtsum;
	/**托管费金额*/
	private BigDecimal paytgamt;
	/**管理费金额*/
	private BigDecimal payglamt;
	/**税款金额*/
	private BigDecimal taxableamt;
	/**利息支出金额*/
	private BigDecimal lxzcamt;
	/**其他费用金*/
	private BigDecimal payotheramt;
	/**费用合计*/
	private BigDecimal costsum;
	/**已实现资本利得（亏损）额*/
	private BigDecimal realizelossamt;
	/**未实现资本利得（亏损）额*/
	private BigDecimal notrealizelossamt;
	/**本年度净损益金额*/
	private BigDecimal bndjsyamt;
	/**期初累计盈余金额*/
	private BigDecimal qcyyamtsum;
	/**期末累计盈余金额*/
	private BigDecimal qmyyamtsum;
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

	public BigDecimal getGlsramt() {
		return glsramt;
	}
	public void setGlsramt(BigDecimal glsramt) {
		this.glsramt = glsramt;
	}
	public BigDecimal getLxsramt() {
		return lxsramt;
	}
	public void setLxsramt(BigDecimal lxsramt) {
		this.lxsramt = lxsramt;
	}
	public BigDecimal getGxjzjsyamt() {
		return gxjzjsyamt;
	}
	public void setGxjzjsyamt(BigDecimal gxjzjsyamt) {
		this.gxjzjsyamt = gxjzjsyamt;
	}
	public BigDecimal getTzsyamt() {
		return tzsyamt;
	}
	public void setTzsyamt(BigDecimal tzsyamt) {
		this.tzsyamt = tzsyamt;
	}
	public BigDecimal getQtsramt() {
		return qtsramt;
	}
	public void setQtsramt(BigDecimal qtsramt) {
		this.qtsramt = qtsramt;
	}
	public BigDecimal getSramtsum() {
		return sramtsum;
	}
	public void setSramtsum(BigDecimal sramtsum) {
		this.sramtsum = sramtsum;
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
	public BigDecimal getLxzcamt() {
		return lxzcamt;
	}
	public void setLxzcamt(BigDecimal lxzcamt) {
		this.lxzcamt = lxzcamt;
	}
	public BigDecimal getPayotheramt() {
		return payotheramt;
	}
	public void setPayotheramt(BigDecimal payotheramt) {
		this.payotheramt = payotheramt;
	}
	public BigDecimal getCostsum() {
		return costsum;
	}
	public void setCostsum(BigDecimal costsum) {
		this.costsum = costsum;
	}
	public BigDecimal getRealizelossamt() {
		return realizelossamt;
	}
	public void setRealizelossamt(BigDecimal realizelossamt) {
		this.realizelossamt = realizelossamt;
	}
	public BigDecimal getNotrealizelossamt() {
		return notrealizelossamt;
	}
	public void setNotrealizelossamt(BigDecimal notrealizelossamt) {
		this.notrealizelossamt = notrealizelossamt;
	}
	public BigDecimal getBndjsyamt() {
		return bndjsyamt;
	}
	public void setBndjsyamt(BigDecimal bndjsyamt) {
		this.bndjsyamt = bndjsyamt;
	}
	public BigDecimal getQcyyamtsum() {
		return qcyyamtsum;
	}
	public void setQcyyamtsum(BigDecimal qcyyamtsum) {
		this.qcyyamtsum = qcyyamtsum;
	}
	public BigDecimal getQmyyamtsum() {
		return qmyyamtsum;
	}
	public void setQmyyamtsum(BigDecimal qmyyamtsum) {
		this.qmyyamtsum = qmyyamtsum;
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
	public void setCurrency(String currency) {
		this.currency = currency;
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

    public Sybxx(){
    	this.setGlsramt(new BigDecimal(0));
    	this.setLxsramt(new BigDecimal(0));
    	this.setGxjzjsyamt(new BigDecimal(0));
    	this.setTzsyamt(new BigDecimal(0));
    	this.setQtsramt(new BigDecimal(0));
    	this.setSramtsum(new BigDecimal(0));
    	this.setPaytgamt(new BigDecimal(0));
    	this.setPayglamt(new BigDecimal(0));
    	this.setTaxableamt(new BigDecimal(0));
    	this.setLxzcamt(new BigDecimal(0));
    	this.setPayotheramt(new BigDecimal(0));
    	this.setCostsum(new BigDecimal(0));
    	this.setRealizelossamt(new BigDecimal(0));
    	this.setNotrealizelossamt(new BigDecimal(0));
    	this.setBndjsyamt(new BigDecimal(0));
    	this.setQcyyamtsum(new BigDecimal(0));
        this.setQmyyamtsum(new BigDecimal(0));
    }	

}