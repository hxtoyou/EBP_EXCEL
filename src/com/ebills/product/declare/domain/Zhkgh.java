package com.ebills.product.declare.domain;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import com.ebills.product.declare.Entity;
/**账户开关户信息
 * SbZhkgh entity. @twteam
 * 对应表名：SB_ZHKGH
 */

public class Zhkgh extends Entity {
    //操作流水号
	private String nguid;
	//操作类型;A－新建C－修改D－删除。已经有收支余信息的账户不能删除。
	private String actiontype;
	//删除原因
	private String actiondesc;
	//金融机构标识码
	private String branch_Code;
	//金融机构名称
	private String branch_Name;
	//账号
	private String accountno;
	//账户状态
	private String accountstat;
	//开户主体类型
	private String amtype;
	//开户主体代码
	private String en_Code;
	//开户主体名称
	private String en_Name;
	//账户性质代码
	private String account_Type;
	//账户类别
	private String account_Cata;
	//币种
	private String currency_Code;
	//业务发生日期
	private Date business_Date;
	//外汇局批件号/备案表号/业务编号
	private String file_Number;
	//限额类型
	private String limit_Type;
	//账户限额
	private BigDecimal account_Limit;
	//备注
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
	//老流水号
    private String oguid; 
    
  //接收/创建时间
    private Date recdate;
  //数据来源
    private String datasources;
    
    public Zhkgh(){
    	this.setAccount_Limit(new BigDecimal(0));
    	this.setActiontype("A");
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
	public String getActiontype() {
		return actiontype;
	}
	public void setActiontype(String actiontype) {
		this.actiontype = actiontype;
	}
	public String getActiondesc() {
		return actiondesc;
	}
	public void setActiondesc(String actiondesc) {
		this.actiondesc = actiondesc;
	}
	public String getBranch_Code() {
		return branch_Code;
	}
	public void setBranch_Code(String branchCode) {
		branch_Code = branchCode;
	}
	public String getBranch_Name() {
		return branch_Name;
	}
	public void setBranch_Name(String branchName) {
		branch_Name = branchName;
	}
	public String getAccountno() {
		return accountno;
	}
	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}
	public String getAccountstat() {
		return accountstat;
	}
	public void setAccountstat(String accountstat) {
		this.accountstat = accountstat;
	}
	public String getAmtype() {
		return amtype;
	}
	public void setAmtype(String amtype) {
		this.amtype = amtype;
	}
	public String getEn_Code() {
		return en_Code;
	}
	public void setEn_Code(String enCode) {
		en_Code = enCode;
	}
	public String getEn_Name() {
		return en_Name;
	}
	public void setEn_Name(String enName) {
		en_Name = enName;
	}
	public String getAccount_Type() {
		return account_Type;
	}
	public void setAccount_Type(String accountType) {
		account_Type = accountType;
	}
	public String getAccount_Cata() {
		return account_Cata;
	}
	public void setAccount_Cata(String accountCata) {
		account_Cata = accountCata;
	}
	public String getCurrency_Code() {
		return currency_Code;
	}
	public void setCurrency_Code(String currencyCode) {
		currency_Code = currencyCode;
	}
	public Date getBusiness_Date() {
		return business_Date;
	}
	public void setBusiness_Date(Date businessDate) {
		business_Date = businessDate;
	}
	public String getFile_Number() {
		return file_Number;
	}
	public void setFile_Number(String fileNumber) {
		file_Number = fileNumber;
	}
	public String getLimit_Type() {
		return limit_Type;
	}
	public void setLimit_Type(String limitType) {
		limit_Type = limitType;
	}
    
	public BigDecimal getAccount_Limit() {
		return account_Limit;
	}

	public void setAccount_Limit(BigDecimal accountLimit) {
		account_Limit = accountLimit;
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

	
}