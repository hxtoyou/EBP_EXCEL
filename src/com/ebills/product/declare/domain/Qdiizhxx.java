package com.ebills.product.declare.domain;


import java.sql.Date;
import java.sql.Timestamp;

import com.ebills.product.declare.Entity;


/**
 * SbQdiizhxx entity. @author MyEclipse Persistence Tools
 * QDII账户信息
 * 对应的表名：sb_Qdiizhxx
 */

public class Qdiizhxx extends Entity   {

	// Fields

	/**操作流水号*/
	private String nguid;
	/**QDII机构代码*/
	private String qdiiorgno;
	/**QDII机构中文名称*/
	private String qdiiorgname;
	/**QDII境内托管行代码*/
	private String qdiiindpoitno;
	/**QDII境外托管人代码*/
	private String qdiioutdepoitno;
	/**QDII境外托管人中文名称*/
	private String qdiioutdepoitcnname;
	/**QDII境外托管人英文名称*/
	private String qdiioutdepoitenname;
	/**产品/客户名称*/
	private String customname;
	/**账户性质*/
	private String accounttype;
	/**账号*/
	private String accountno;
	/**币种*/
	private String currency;
	/**开户日期*/
	private Date opendate;
	/**关户日期*/
	private Date clostdate;
	/**备注*/
	private String remark;
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
	public String getQdiiindpoitno() {
		return qdiiindpoitno;
	}
	public void setQdiiindpoitno(String qdiiindpoitno) {
		this.qdiiindpoitno = qdiiindpoitno;
	}
	public String getQdiioutdepoitno() {
		return qdiioutdepoitno;
	}
	public void setQdiioutdepoitno(String qdiioutdepoitno) {
		this.qdiioutdepoitno = qdiioutdepoitno;
	}
	public String getQdiioutdepoitcnname() {
		return qdiioutdepoitcnname;
	}
	public void setQdiioutdepoitcnname(String qdiioutdepoitcnname) {
		this.qdiioutdepoitcnname = qdiioutdepoitcnname;
	}
	public String getQdiioutdepoitenname() {
		return qdiioutdepoitenname;
	}
	public void setQdiioutdepoitenname(String qdiioutdepoitenname) {
		this.qdiioutdepoitenname = qdiioutdepoitenname;
	}
	public String getCustomname() {
		return customname;
	}
	public void setCustomname(String customname) {
		this.customname = customname;
	}
	public String getAccounttype() {
		return accounttype;
	}
	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}
	public String getAccountno() {
		return accountno;
	}
	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Date getOpendate() {
		return opendate;
	}
	public void setOpendate(Date opendate) {
		this.opendate = opendate;
	}
	public Date getClostdate() {
		return clostdate;
	}
	public void setClostdate(Date clostdate) {
		this.clostdate = clostdate;
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

	public String getIsauth() {
		return isauth;
	}
	public void setIsauth(String isauth) {
		this.isauth = isauth;
	}
	

	
	// Property accessors

}