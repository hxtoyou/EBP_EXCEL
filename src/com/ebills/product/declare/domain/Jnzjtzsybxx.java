package com.ebills.product.declare.domain;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import com.ebills.product.declare.Entity;


/**
 * SbJnzjtzsybxx entity. @author MyEclipse Persistence Tools QFII境内证券投资年度损益表信息
 * 对应的表名：sb_Jnzjtzsybxx
 */

public class Jnzjtzsybxx extends Entity {

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
	/** 股息收入金额 */
	private BigDecimal gxsramt;
	/** 利息收入金额 */
	private BigDecimal lxsramt;
	/** 其他收入金额 */
	private BigDecimal othersramt;
	/** 收入合计 */
	private BigDecimal sramtsum;
	/** 托管费金额 */
	private BigDecimal paytgamt;
	/** 管理费金额 */
	private BigDecimal payglamt;
	/** 税款金额 */
	private BigDecimal taxableamt;
	/** 其他费用（税费）金额 */
	private BigDecimal payotheramt;
	/** 费用合计 */
	private BigDecimal costsum;
	/** 本年度已实现资本利得（亏损）额 */
	private BigDecimal realizelossamt;
	/** 未实现资本利得（亏损）额 */
	private BigDecimal notrealizelossamt;
	/** 公允价值变动损益 */
	private BigDecimal gxjzjsyamt;
	/** 本年度净损益金额 */
	private BigDecimal bndjsyamt;
	/** 期初累计盈余金额 */
	private BigDecimal qcyyamtsum;
	/** 期末累计盈余金额 */
	private BigDecimal qmyyamtsum;
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

	private String datasources;

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

	public BigDecimal getGxsramt() {
		return gxsramt;
	}

	public void setGxsramt(BigDecimal gxsramt) {
		this.gxsramt = gxsramt;
	}

	public BigDecimal getLxsramt() {
		return lxsramt;
	}

	public void setLxsramt(BigDecimal lxsramt) {
		this.lxsramt = lxsramt;
	}

	public BigDecimal getOthersramt() {
		return othersramt;
	}

	public void setOthersramt(BigDecimal othersramt) {
		this.othersramt = othersramt;
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

	public BigDecimal getGxjzjsyamt() {
		return gxjzjsyamt;
	}

	public void setGxjzjsyamt(BigDecimal gxjzjsyamt) {
		this.gxjzjsyamt = gxjzjsyamt;
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
    public Jnzjtzsybxx(){
    	this.setGxsramt(new BigDecimal(0));
    	this.setLxsramt(new BigDecimal(0));
    	this.setOthersramt(new BigDecimal(0));
    	this.setSramtsum(new BigDecimal(0));
    	this.setPaytgamt(new BigDecimal(0));
    	this.setPayglamt(new BigDecimal(0));
    	this.setTaxableamt(new BigDecimal(0));
    	this.setPayotheramt(new BigDecimal(0));
    	this.setCostsum(new BigDecimal(0));
    	this.setRealizelossamt(new BigDecimal(0));
    	this.setNotrealizelossamt(new BigDecimal(0));
    	this.setGxjzjsyamt(new BigDecimal(0));
    	this.setBndjsyamt(new BigDecimal(0));
    	this.setQcyyamtsum(new BigDecimal(0));
    	this.setQmyyamtsum(new BigDecimal(0));
    }
}