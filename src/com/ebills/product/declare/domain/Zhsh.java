package com.ebills.product.declare.domain;


import java.text.SimpleDateFormat;
import java.sql.Date;

import com.ebills.product.declare.Entity;


/**
 * 账户售汇表
 * @author Hudan
 *
 */
public class Zhsh extends Entity {

	private String nguId;				//新流水号
	private String actionType;			//操作类型:	A－新建、C－修改、D－删除、R-申报无误（银行反馈），预留
	private String actionDesc;			//修改/删除原因或申报无误理由
	private String rptNo;				//申报号码
	private String busCode;				//银行业务编号
	private String custCod;				//购汇申请人组织机构代码
	private String cusType;				//结汇申请人主体类型：C－对公用户、D－对私中国居民、F－对私中国非居民
	private String idcode;				//购汇申请人个人身份证件号码
	private String custnm;				//购汇申请人名称
	private String lcyAcc;				//人民币账户账号
	private String fcyAcc;				//外汇账户账号
	private String oppUser;				//外汇收款人名称
	private String oppBank;				//外汇账户开户行
	private double lcyAmt;				//购汇金额（人民币）
	private String lcyCcy;				//购汇币别
	private double exrate;				//汇率
	private String rwIdh;				//任务编号
	private String ywbh;				//业务流水号
	private Date sjlrrq;				//数据录入日期
	private String sfysb;				//是否已申报
	private String sxbz;				//生效标志
	private String sfzx;				//最新标志
	private String bankId;				//机构编号
	private String oguId;				//老流水号
	private String lastModiDate = (new SimpleDateFormat("yyyyMMdd")).format(new Date(System.currentTimeMillis()));//最后更新日期
	private String passPeople;			//核查人
	private String passDate;			//核查日期
	private String isPass;				//是否已经核查
	private String operId;				//操作员ID
	
	
	public String getNguId() {
		return nguId;
	}
	public void setNguId(String nguId) {
		this.nguId = nguId;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	
	public String getCustCod() {
		return custCod;
	}
	
	public void setCustCod(String custCod) {
		this.custCod = custCod;
	}
	public String getActionDesc() {
		return actionDesc;
	}
	public void setActionDesc(String actionDesc) {
		this.actionDesc = actionDesc;
	}
	public String getRptNo() {
		return rptNo;
	}
	public void setRptNo(String rptNo) {
		this.rptNo = rptNo;
	}
	public String getBusCode() {
		return busCode;
	}
	public void setBusCode(String busCode) {
		this.busCode = busCode;
	}
	public String getCusType() {
		return cusType;
	}
	public void setCusType(String cusType) {
		this.cusType = cusType;
	}
	public String getIdcode() {
		return idcode;
	}
	public void setIdcode(String idcode) {
		this.idcode = idcode;
	}
	public String getCustnm() {
		return custnm;
	}
	public void setCustnm(String custnm) {
		this.custnm = custnm.length()>128?custnm.substring(0,127).trim():custnm;
	}
	public String getLcyAcc() {
		return lcyAcc;
	}
	public void setLcyAcc(String lcyAcc) {
		this.lcyAcc = lcyAcc;
	}
	public String getFcyAcc() {
		return fcyAcc;
	}
	public void setFcyAcc(String fcyAcc) {
		this.fcyAcc = fcyAcc;
	}
	public String getOppUser() {
		return oppUser;
	}
	public void setOppUser(String oppUser) {
		this.oppUser = oppUser.length()>128?oppUser.substring(0,127).trim():oppUser;
	}
	public String getOppBank() {
		return oppBank;
	}
	public void setOppBank(String oppBank) {
		this.oppBank = oppBank;
	}
	public String getLcyCcy() {
		return lcyCcy;
	}
	public void setLcyCcy(String lcyCcy) {
		this.lcyCcy = lcyCcy;
	}
	public double getExrate() {
		return exrate;
	}
	public void setExrate(double exrate) {
		this.exrate = exrate;
	}
	public double getLcyAmt() {
		return lcyAmt;
	}
	public void setLcyAmt(double lcyAmt) {
		this.lcyAmt = lcyAmt;
	}
	public String getRwIdh() {
		return rwIdh;
	}
	public void setRwIdh(String rwIdh) {
		this.rwIdh = rwIdh;
	}
	public String getYwbh() {
		return ywbh;
	}
	public void setYwbh(String ywbh) {
		this.ywbh = ywbh;
	}
	public Date getSjlrrq() {
		return sjlrrq;
	}
	public void setSjlrrq(Date sjlrrq) {
		this.sjlrrq = sjlrrq;
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
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getOguId() {
		return oguId;
	}
	public void setOguId(String oguId) {
		this.oguId = oguId;
	}
	public String getLastModiDate() {
		return lastModiDate;
	}
	public void setLastModiDate(String lastModiDate) {
		this.lastModiDate = (new SimpleDateFormat("yyyyMMdd")).format(new Date(System.currentTimeMillis()));
	}
	public String getPassPeople() {
		return passPeople;
	}
	public void setPassPeople(String passPeople) {
		this.passPeople = passPeople;
	}
	public String getPassDate() {
		return passDate;
	}
	public void setPassDate(String passDate) {
		this.passDate = passDate;
	}
	public String getIsPass() {
		return isPass;
	}
	public void setIsPass(String isPass) {
		this.isPass = isPass;
	}
	public String getOperId() {
		return operId;
	}
	public void setOperId(String operId) {
		this.operId = operId;
	}
	
	 /**@Build by Class builder @auth XJBS*/

    public Zhsh(){
        this.setActionType("A");
		this.setSfysb("N");
		this.setSfzx("Y");
		this.setSxbz("0");
		this.setSjlrrq(new Date(System.currentTimeMillis()));
		this.setIsPass("N");
    }
}
