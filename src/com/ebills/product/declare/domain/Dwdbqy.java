package com.ebills.product.declare.domain;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Date;

import com.ebills.product.declare.Entity;

/**
 * 对外担保—签约信息 SbDwdbqy entity. @twteam 对应表名：SB_DWDBQY
 */

public class Dwdbqy extends Entity {
	// 操作类型
	private String actiontype;
	// 删除原因
	private String actiondesc;
	// 对外担保编号
	private String exguarancode;
	// 担保人代码
	private String guarantorcode;
	// 签约日期
	private Date contractdate;
	// 保函金额
	private BigDecimal guaranamount;
	// 保函币种
	private String guarancurr;
	// 到期日
	private Date maturity;
	// 担保类型
	private String guarantype;
	// 主债务币种
	private String maindebtcurr;
	// 主债务金额
	private BigDecimal maindebtamount;
	// 受益人代码
	private String bencode;
	// 受益人中文名称
	private String bename;
	// 受益人英文名称
	private String benamen;
	// 受益人类型
	private String bentype;
	// 受益人国别/地区
	private String bencountrycode;
	// 被担保人代码
	private String guedcode;
	// 被担保人中文名称
	private String guedname;
	// 被担保人英文名称
	private String guednamen;
	// 被担保人类型
	private String guedtype;
	// 被担保人国别/地区代码
	private String guedcouncode;
	// 担保申请人代码
	private String guappcode;
	// 担保申请人中文名称
	private String guappname;
	// 担保申请人英文名称
	private String guappnamen;
	// 核准文件号
	private String appdocuno;
	// 备注
	private String remark;
	// 业务流水号
	private String rwidh;
	// 业务编号
	private String ywbh;
	// 是否已申报
	private String sfysb;
	// 生效标志
	private String sxbz;
	// 最新标志
	private String sfzx;
	// 机构编号
	private String bank_Id;
	// 经办人
	private int handid;
	// 经办日期
	private Timestamp handdate;
	// 复核人
	private int checkid;
	// 复核时间
	private Timestamp checkdate;
	// 是否复核通过
	private String ischeck;
	// 授权人
	private int authid;
	// 授权时间
	private Timestamp authdate;
	// 授权是否通过
	private String isauth;
	// 老流水
	private String oguid;
	// 操作流水号
	private String nguid;
	// 接收/创建时间
	private Date recdate;
	// 数据来源
	private String datasources;
	// 备用字段1
	private String dataStr1;
	// 备用字段2
	private String dataStr2;
	// 核心虚拟外债编号 烟台银行加    由于烟台银行核心不能反馈外债编号数据，所以由其返回一个虚拟的外债编号保存到国结，该虚拟外债编号由机构号+日期+随机数组成
	private String hxxnwzbh;

	public Dwdbqy() {
		this.setGuaranamount(new BigDecimal(0));
		this.setActiontype("A");
		this.setSfysb("N");
		this.setSfzx("Y");
		this.setSxbz("0");
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

	public String getExguarancode() {
		return exguarancode;
	}

	public void setExguarancode(String exguarancode) {
		this.exguarancode = exguarancode;
	}

	public String getGuarantorcode() {
		return guarantorcode;
	}

	public void setGuarantorcode(String guarantorcode) {
		this.guarantorcode = guarantorcode;
	}

	public Date getContractdate() {
		return contractdate;
	}

	public void setContractdate(Date contractdate) {
		this.contractdate = contractdate;
	}

	public BigDecimal getGuaranamount() {
		return guaranamount;
	}

	public void setGuaranamount(BigDecimal guaranamount) {
		this.guaranamount = guaranamount;
	}

	public String getGuarancurr() {
		return guarancurr;
	}

	public void setGuarancurr(String guarancurr) {
		this.guarancurr = guarancurr;
	}

	public Date getMaturity() {
		return maturity;
	}

	public void setMaturity(Date maturity) {
		this.maturity = maturity;
	}

	public String getGuarantype() {
		return guarantype;
	}

	public void setGuarantype(String guarantype) {
		this.guarantype = guarantype;
	}

	public String getMaindebtcurr() {
		return maindebtcurr;
	}

	public void setMaindebtcurr(String maindebtcurr) {
		this.maindebtcurr = maindebtcurr;
	}

	public BigDecimal getMaindebtamount() {
		return maindebtamount;
	}

	public void setMaindebtamount(BigDecimal maindebtamount) {
		this.maindebtamount = maindebtamount;
	}

	public String getBencode() {
		return bencode;
	}

	public void setBencode(String bencode) {
		this.bencode = bencode;
	}

	public String getBename() {
		return bename;
	}

	public void setBename(String bename) {
		this.bename = bename;
	}

	public String getBenamen() {
		return benamen;
	}

	public void setBenamen(String benamen) {
		this.benamen = benamen;
	}

	public String getBentype() {
		return bentype;
	}

	public void setBentype(String bentype) {
		this.bentype = bentype;
	}

	public String getBencountrycode() {
		return bencountrycode;
	}

	public void setBencountrycode(String bencountrycode) {
		this.bencountrycode = bencountrycode;
	}

	public String getGuedcode() {
		return guedcode;
	}

	public void setGuedcode(String guedcode) {
		this.guedcode = guedcode;
	}

	public String getGuedname() {
		return guedname;
	}

	public void setGuedname(String guedname) {
		this.guedname = guedname;
	}

	public String getGuednamen() {
		return guednamen;
	}

	public void setGuednamen(String guednamen) {
		this.guednamen = guednamen;
	}

	public String getGuedtype() {
		return guedtype;
	}

	public void setGuedtype(String guedtype) {
		this.guedtype = guedtype;
	}

	public String getGuedcouncode() {
		return guedcouncode;
	}

	public void setGuedcouncode(String guedcouncode) {
		this.guedcouncode = guedcouncode;
	}

	public String getGuappcode() {
		return guappcode;
	}

	public void setGuappcode(String guappcode) {
		this.guappcode = guappcode;
	}

	public String getGuappname() {
		return guappname;
	}

	public void setGuappname(String guappname) {
		this.guappname = guappname;
	}

	public String getGuappnamen() {
		return guappnamen;
	}

	public void setGuappnamen(String guappnamen) {
		this.guappnamen = guappnamen;
	}

	public String getAppdocuno() {
		return appdocuno;
	}

	public void setAppdocuno(String appdocuno) {
		this.appdocuno = appdocuno;
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

	public void setBank_Id(String bank_Id) {
		this.bank_Id = bank_Id;
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

	public String getNguid() {
		return nguid;
	}

	public void setNguid(String nguid) {
		this.nguid = nguid;
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

	public String getDataStr1() {
		return dataStr1;
	}

	public void setDataStr1(String dataStr1) {
		this.dataStr1 = dataStr1;
	}

	public String getDataStr2() {
		return dataStr2;
	}

	public void setDataStr2(String dataStr2) {
		this.dataStr2 = dataStr2;
	}

	public String getHxxnwzbh() {
		return hxxnwzbh;
	}

	public void setHxxnwzbh(String hxxnwzbh) {
		this.hxxnwzbh = hxxnwzbh;
	}
}