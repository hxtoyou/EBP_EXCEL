package com.ebills.product.declare.domain;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Date;

import com.ebills.product.declare.Entity;


/**
 * SbWhzhszxx entity. @author MyEclipse Persistence Tools
 * 对应的表名：sb_WHZHSZXX
 */

public class Whzhszxx extends Entity{
    
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
	//外汇账户账号
	private String whaccount;
	//币种
	private String currency;
	//本月汇入本金金额
	private BigDecimal byhrbjamt;
	//本月汇入本金金额折美元
	private BigDecimal byhrbjusdamt;
	//累计汇入本金金额
	private BigDecimal hrbjamtsum;
	//累计汇入本金金额折美元
	private BigDecimal hrbjusdamtsum;
	//本月利息收入金额
	private BigDecimal bylxsramt;
	//本月利息收入金额折美元
	private BigDecimal bylxsrusdamt;
	//累计利息收入金额
	private BigDecimal lxsramtsum;
	//累计利息收入金额折美元
	private BigDecimal lxsrusdamtsum;
	//本月人民币特殊账户购汇划入金额
	private BigDecimal bycnyghhramt;
	//本月人民币特殊账户购汇划入金额折美元
	private BigDecimal bycnyghhrusdamt;
	//累计人民币特殊账户购汇划入金额
	private BigDecimal cnyghhramtsum;
	//累计人民币特殊账户购汇划入金额折美元
	private BigDecimal cnyghhrusdamtsum;
	//本月收入合计
	private BigDecimal bysramtsum;
	//本月收入合计折美元
	private BigDecimal bysrusdamtsum;
	//累计收入合计
	private BigDecimal srsamtsum;
	//累计收入合计折美元
	private BigDecimal srusdamtsum;
	//本月结汇划入人民币特殊账户金额
	private BigDecimal bycnyjhhramt;
	//本月结汇划入人民币特殊账户金额折美元
	private BigDecimal bycnyjhhrusdamt;
	//累计结汇划入人民币特殊账户金额
	private BigDecimal cnyjhhramtsum;
	//累计结汇划入人民币特殊账户金额折美元
	private BigDecimal cnyjhhrusdamtsum;
	//本月汇出本金金额
	private BigDecimal byhcbjamt;
	//本月汇出本金金额折美元
	private BigDecimal byhcbjusdamt;
	//累计汇出本金金额
	private BigDecimal hcbjamtsum;
	//累计汇出本金金额折美元
	private BigDecimal hcbjusdamtsum;
	//本月汇出收益金额
	private BigDecimal byhcsyamt;
	//本月汇出收益金额折美元
	private BigDecimal byhcsyusdamt;
	//累计汇出收益金额
	private BigDecimal hcsyamtsum;
	//累计汇出收益金额折美元
	private BigDecimal hcsyusdamtsum;
	//本月支出合计
	private BigDecimal byzcamt;
	//本月支出合计折美元
	private BigDecimal byzcusdamt;
	//累计支出合计
	private BigDecimal zcamtsum;
	//累计支出合计折美元
	private BigDecimal zcusdamtsum;
	//本月净汇入金额
	private BigDecimal byjhramt;
	//本月净汇入金额折美元
	private BigDecimal byjhrusdamt;
	//累计净汇入金额
	private BigDecimal jhramtsum;
	//累计净汇入金额折美元
	private BigDecimal jhrusdamtsum;
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
	public Whzhszxx(){
		this.setSfysb("N");
		this.setSfzx("Y");
		this.setSxbz("0");
		this.setByhrbjamt(new BigDecimal(0)) ;
		this.setByhrbjusdamt(new BigDecimal(0)) ;
		this.setHrbjamtsum(new BigDecimal(0)) ;
		this.setHrbjusdamtsum(new BigDecimal(0)) ;
		this.setBylxsramt(new BigDecimal(0) ) ;
		this.setBylxsrusdamt(new BigDecimal(0) ) ;
		this.setLxsramtsum(new BigDecimal(0) ) ;
		this.setLxsrusdamtsum(new BigDecimal(0) ) ;
		this.setBycnyghhramt(new BigDecimal(0) ) ;
		this.setBycnyghhrusdamt(new BigDecimal(0) ) ;
		this.setCnyghhramtsum(new BigDecimal(0) ) ;
		this.setCnyghhrusdamtsum(new BigDecimal(0) ) ;
		this.setBysramtsum(new BigDecimal(0) ) ;
		this.setBysrusdamtsum(new BigDecimal(0) ) ;
		this.setSrsamtsum(new BigDecimal(0) ) ;
		this.setSrusdamtsum(new BigDecimal(0) ) ;
		this.setBycnyjhhramt(new BigDecimal(0) ) ;
		this.setBycnyjhhrusdamt(new BigDecimal(0) ) ;
		this.setCnyjhhramtsum(new BigDecimal(0) ) ;
		this.setCnyjhhrusdamtsum(new BigDecimal(0) ) ;
		this.setByhcbjamt(new BigDecimal(0) ) ;
		this.setByhcbjusdamt(new BigDecimal(0) ) ;
		this.setHcbjamtsum(new BigDecimal(0) ) ;
		this.setHcbjusdamtsum(new BigDecimal(0) ) ;
		this.setByhcsyamt(new BigDecimal(0) ) ;
		this.setByhcsyusdamt(new BigDecimal(0) ) ;
		this.setHcsyamtsum(new BigDecimal(0) ) ;
		this.setHcsyusdamtsum(new BigDecimal(0) ) ; 
		this.setByzcamt(new BigDecimal(0) ) ;
		this.setByzcusdamt(new BigDecimal(0) ) ;
		this.setZcamtsum(new BigDecimal(0) ) ;
		this.setZcusdamtsum(new BigDecimal(0) ) ;
		this.setByjhramt(new BigDecimal(0) ) ;
		this.setByjhrusdamt(new BigDecimal(0) ) ;
		this.setJhramtsum(new BigDecimal(0) ) ;
		this.setJhrusdamtsum(new BigDecimal(0) ) ;
	 
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
	public String getWhaccount() {
		return whaccount;
	}
	public void setWhaccount(String whaccount) {
		this.whaccount = whaccount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public BigDecimal getByhrbjamt() {
		return byhrbjamt;
	}
	public void setByhrbjamt(BigDecimal byhrbjamt) {
		this.byhrbjamt = byhrbjamt;
	}
	public BigDecimal getByhrbjusdamt() {
		return byhrbjusdamt;
	}
	public void setByhrbjusdamt(BigDecimal byhrbjusdamt) {
		this.byhrbjusdamt = byhrbjusdamt;
	}
	public BigDecimal getHrbjamtsum() {
		return hrbjamtsum;
	}
	public void setHrbjamtsum(BigDecimal hrbjamtsum) {
		this.hrbjamtsum = hrbjamtsum;
	}
	public BigDecimal getHrbjusdamtsum() {
		return hrbjusdamtsum;
	}
	public void setHrbjusdamtsum(BigDecimal hrbjusdamtsum) {
		this.hrbjusdamtsum = hrbjusdamtsum;
	}
	public BigDecimal getBylxsramt() {
		return bylxsramt;
	}
	public void setBylxsramt(BigDecimal bylxsramt) {
		this.bylxsramt = bylxsramt;
	}
	public BigDecimal getBylxsrusdamt() {
		return bylxsrusdamt;
	}
	public void setBylxsrusdamt(BigDecimal bylxsrusdamt) {
		this.bylxsrusdamt = bylxsrusdamt;
	}
	public BigDecimal getLxsramtsum() {
		return lxsramtsum;
	}
	public void setLxsramtsum(BigDecimal lxsramtsum) {
		this.lxsramtsum = lxsramtsum;
	}
	public BigDecimal getLxsrusdamtsum() {
		return lxsrusdamtsum;
	}
	public void setLxsrusdamtsum(BigDecimal lxsrusdamtsum) {
		this.lxsrusdamtsum = lxsrusdamtsum;
	}
	public BigDecimal getBycnyghhramt() {
		return bycnyghhramt;
	}
	public void setBycnyghhramt(BigDecimal bycnyghhramt) {
		this.bycnyghhramt = bycnyghhramt;
	}
	public BigDecimal getBycnyghhrusdamt() {
		return bycnyghhrusdamt;
	}
	public void setBycnyghhrusdamt(BigDecimal bycnyghhrusdamt) {
		this.bycnyghhrusdamt = bycnyghhrusdamt;
	}
	public BigDecimal getCnyghhramtsum() {
		return cnyghhramtsum;
	}
	public void setCnyghhramtsum(BigDecimal cnyghhramtsum) {
		this.cnyghhramtsum = cnyghhramtsum;
	}
	public BigDecimal getCnyghhrusdamtsum() {
		return cnyghhrusdamtsum;
	}
	public void setCnyghhrusdamtsum(BigDecimal cnyghhrusdamtsum) {
		this.cnyghhrusdamtsum = cnyghhrusdamtsum;
	}
	public BigDecimal getBysramtsum() {
		return bysramtsum;
	}
	public void setBysramtsum(BigDecimal bysramtsum) {
		this.bysramtsum = bysramtsum;
	}
	public BigDecimal getBysrusdamtsum() {
		return bysrusdamtsum;
	}
	public void setBysrusdamtsum(BigDecimal bysrusdamtsum) {
		this.bysrusdamtsum = bysrusdamtsum;
	}
	public BigDecimal getSrsamtsum() {
		return srsamtsum;
	}
	public void setSrsamtsum(BigDecimal srsamtsum) {
		this.srsamtsum = srsamtsum;
	}
	public BigDecimal getSrusdamtsum() {
		return srusdamtsum;
	}
	public void setSrusdamtsum(BigDecimal srusdamtsum) {
		this.srusdamtsum = srusdamtsum;
	}
	public BigDecimal getBycnyjhhramt() {
		return bycnyjhhramt;
	}
	public void setBycnyjhhramt(BigDecimal bycnyjhhramt) {
		this.bycnyjhhramt = bycnyjhhramt;
	}
	public BigDecimal getBycnyjhhrusdamt() {
		return bycnyjhhrusdamt;
	}
	public void setBycnyjhhrusdamt(BigDecimal bycnyjhhrusdamt) {
		this.bycnyjhhrusdamt = bycnyjhhrusdamt;
	}
	public BigDecimal getCnyjhhramtsum() {
		return cnyjhhramtsum;
	}
	public void setCnyjhhramtsum(BigDecimal cnyjhhramtsum) {
		this.cnyjhhramtsum = cnyjhhramtsum;
	}
	public BigDecimal getCnyjhhrusdamtsum() {
		return cnyjhhrusdamtsum;
	}
	public void setCnyjhhrusdamtsum(BigDecimal cnyjhhrusdamtsum) {
		this.cnyjhhrusdamtsum = cnyjhhrusdamtsum;
	}
	public BigDecimal getByhcbjamt() {
		return byhcbjamt;
	}
	public void setByhcbjamt(BigDecimal byhcbjamt) {
		this.byhcbjamt = byhcbjamt;
	}
	public BigDecimal getByhcbjusdamt() {
		return byhcbjusdamt;
	}
	public void setByhcbjusdamt(BigDecimal byhcbjusdamt) {
		this.byhcbjusdamt = byhcbjusdamt;
	}
	public BigDecimal getHcbjamtsum() {
		return hcbjamtsum;
	}
	public void setHcbjamtsum(BigDecimal hcbjamtsum) {
		this.hcbjamtsum = hcbjamtsum;
	}
	public BigDecimal getHcbjusdamtsum() {
		return hcbjusdamtsum;
	}
	public void setHcbjusdamtsum(BigDecimal hcbjusdamtsum) {
		this.hcbjusdamtsum = hcbjusdamtsum;
	}
	public BigDecimal getByhcsyamt() {
		return byhcsyamt;
	}
	public void setByhcsyamt(BigDecimal byhcsyamt) {
		this.byhcsyamt = byhcsyamt;
	}
	public BigDecimal getByhcsyusdamt() {
		return byhcsyusdamt;
	}
	public void setByhcsyusdamt(BigDecimal byhcsyusdamt) {
		this.byhcsyusdamt = byhcsyusdamt;
	}
	public BigDecimal getHcsyamtsum() {
		return hcsyamtsum;
	}
	public void setHcsyamtsum(BigDecimal hcsyamtsum) {
		this.hcsyamtsum = hcsyamtsum;
	}
	public BigDecimal getHcsyusdamtsum() {
		return hcsyusdamtsum;
	}
	public void setHcsyusdamtsum(BigDecimal hcsyusdamtsum) {
		this.hcsyusdamtsum = hcsyusdamtsum;
	}
	public BigDecimal getByzcamt() {
		return byzcamt;
	}
	public void setByzcamt(BigDecimal byzcamt) {
		this.byzcamt = byzcamt;
	}
	public BigDecimal getByzcusdamt() {
		return byzcusdamt;
	}
	public void setByzcusdamt(BigDecimal byzcusdamt) {
		this.byzcusdamt = byzcusdamt;
	}
	public BigDecimal getZcamtsum() {
		return zcamtsum;
	}
	public void setZcamtsum(BigDecimal zcamtsum) {
		this.zcamtsum = zcamtsum;
	}
	public BigDecimal getZcusdamtsum() {
		return zcusdamtsum;
	}
	public void setZcusdamtsum(BigDecimal zcusdamtsum) {
		this.zcusdamtsum = zcusdamtsum;
	}
	public BigDecimal getByjhramt() {
		return byjhramt;
	}
	public void setByjhramt(BigDecimal byjhramt) {
		this.byjhramt = byjhramt;
	}
	public BigDecimal getByjhrusdamt() {
		return byjhrusdamt;
	}
	public void setByjhrusdamt(BigDecimal byjhrusdamt) {
		this.byjhrusdamt = byjhrusdamt;
	}
	public BigDecimal getJhramtsum() {
		return jhramtsum;
	}
	public void setJhramtsum(BigDecimal jhramtsum) {
		this.jhramtsum = jhramtsum;
	}
	public BigDecimal getJhrusdamtsum() {
		return jhrusdamtsum;
	}
	public void setJhrusdamtsum(BigDecimal jhrusdamtsum) {
		this.jhrusdamtsum = jhrusdamtsum;
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