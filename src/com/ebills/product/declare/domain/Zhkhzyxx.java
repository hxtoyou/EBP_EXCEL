package com.ebills.product.declare.domain;


import java.sql.Date;
import java.sql.Timestamp;

import com.ebills.product.declare.Entity;


/**
 * SbZhkhzyxx entity. @author MyEclipse Persistence Tools
 */

public class Zhkhzyxx extends Entity  {

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
	//QFII账户性质
	private String qfiitype;
	//账号
	private String account;
	//币种
	private String currency;
	//开户日期
	private Date openaccountdate;
	//关户日期
	private Date closeaccountdate;
	//沪市券商代码
	private String shcustcod;
	//沪市券商名称
	private String shcustcodname;
	//深市券商代码
	private String szcustcod;
	//深市券商名称
	private String szcustcodname;
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

	public Zhkhzyxx(){
		this.setSfysb("N");
		this.setSfzx("Y");
		this.setSxbz("0");
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
	public String getQfiitype() {
		return qfiitype;
	}
	public void setQfiitype(String qfiitype) {
		this.qfiitype = qfiitype;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Date getOpenaccountdate() {
		return openaccountdate;
	}
	public void setOpenaccountdate(Date openaccountdate) {
		this.openaccountdate = openaccountdate;
	}
	public Date getCloseaccountdate() {
		return closeaccountdate;
	}
	public void setCloseaccountdate(Date closeaccountdate) {
		this.closeaccountdate = closeaccountdate;
	}
	public String getShcustcod() {
		return shcustcod;
	}
	public void setShcustcod(String shcustcod) {
		this.shcustcod = shcustcod;
	}
	public String getShcustcodname() {
		return shcustcodname;
	}
	public void setShcustcodname(String shcustcodname) {
		this.shcustcodname = shcustcodname;
	}
	public String getSzcustcod() {
		return szcustcod;
	}
	public void setSzcustcod(String szcustcod) {
		this.szcustcod = szcustcod;
	}
	public String getSzcustcodname() {
		return szcustcodname;
	}
	public void setSzcustcodname(String szcustcodname) {
		this.szcustcodname = szcustcodname;
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