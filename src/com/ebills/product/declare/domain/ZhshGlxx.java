package com.ebills.product.declare.domain;


import java.text.SimpleDateFormat;
import java.sql.Date;

import com.ebills.product.declare.Entity;


public class ZhshGlxx extends Entity {

	private String nguId;
	private String actionType;
	private String actionDesc;
	private String rptNo;
	private String regNo;
	private String txCode;
	private String crtUser;
	private String inptelc;
	private Date rptDate;
	private String rwIdh;
	private String ywbh;
	private Date sjlrrq;
	private String sfysb;
	private String sxbz;
	private String sfzx;
	private String bankId;
	private String oguId;
	private String lastModiDate = (new SimpleDateFormat("yyyyMMdd")).format(new Date(System.currentTimeMillis()));
	
	
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
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public String getTxCode() {
		return txCode;
	}
	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}
	public String getCrtUser() {
		return crtUser;
	}
	public void setCrtUser(String crtUser) {
		this.crtUser = crtUser;
		if (crtUser !=null && crtUser.length()>20){
			crtUser = crtUser.substring(0,20);
        }
	}
	public String getInptelc() {
		return inptelc;
	}
	public void setInptelc(String inptelc) {
		this.inptelc = inptelc;
		if (inptelc!=null && inptelc.length()>20){
			inptelc = inptelc.substring(0,20);
	    }
	}
	public Date getRptDate() {
		return rptDate;
	}
	public void setRptDate(Date rptDate) {
		this.rptDate = rptDate;
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
	
	public ZhshGlxx(){
		this.setActionType("A");
		this.setSfysb("N");
		this.setSfzx("Y");
		this.setSxbz("0");
		this.setSjlrrq(new Date(System.currentTimeMillis()));
		this.setRptDate(new Date(System.currentTimeMillis()));
	}
}
