package com.ebills.product.declare.domain;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import com.ebills.product.declare.Entity;


/**
 * SbQdiizjhcrmxxx entity. @author MyEclipse Persistence Tools
 * QDII资金汇出入及结购汇明细信息
 * 对应的表名：sb_Qdiizjhcrmxxx
 */

public class Qdiizjhcrmxxx extends Entity {

	// Fields

	/**操作流水号*/
	private String nguid;
	/**QDII机构代码*/
	private String qdiiorgno;
	/**QDII机构中文名称*/
	private String qdiiorgname;
	/**QDII托管行代码*/
	private String qdiidepoitno;
	/**产品/客户名称*/
	private String customname;
	/**变动编号*/
	private String changeno;
	/**汇兑日期*/
	private Date exchangedate;
	/**资金汇兑原因	*/
	private String exchangereson;
	/**账号*/
	private String accountno;
	/**购汇人民币金额*/
	private BigDecimal lcyamt;
	/**购汇金额折美元*/
	private BigDecimal lcyamttousd;
	/**汇出币种*/
	private String outwardcur;
	/**汇出金额*/
	private BigDecimal outwardamt;
	/**汇出金额折美元*/
	private BigDecimal outwardamttousd;
	/**汇入币种*/
	private String inwardcur;
	/**汇入金额*/
	private BigDecimal inwardamt;
	/**汇入金额折美元*/
	private BigDecimal inwardtousd;
	/**结汇金额折美元*/
	private BigDecimal fcyamttousd;
	/**结汇所得人民币金额*/
	private BigDecimal fcyamt;
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
		return this.nguid;
	}

	public void setNguid(String nguid) {
		this.nguid = nguid;
	}

	public String getQdiiorgno() {
		return this.qdiiorgno;
	}

	public void setQdiiorgno(String qdiiorgno) {
		this.qdiiorgno = qdiiorgno;
	}

	public String getQdiiorgname() {
		return this.qdiiorgname;
	}

	public void setQdiiorgname(String qdiiorgname) {
		this.qdiiorgname = qdiiorgname;
	}

	public String getQdiidepoitno() {
		return this.qdiidepoitno;
	}

	public void setQdiidepoitno(String qdiidepoitno) {
		this.qdiidepoitno = qdiidepoitno;
	}

	public String getCustomname() {
		return this.customname;
	}

	public void setCustomname(String customname) {
		this.customname = customname;
	}

	public String getChangeno() {
		return this.changeno;
	}

	public void setChangeno(String changeno) {
		this.changeno = changeno;
	}

	public Date getExchangedate() {
		return this.exchangedate;
	}

	public void setExchangedate(Date exchangedate) {
		this.exchangedate = exchangedate;
	}

	public String getExchangereson() {
		return this.exchangereson;
	}

	public void setExchangereson(String exchangereson) {
		this.exchangereson = exchangereson;
	}

	public String getAccountno() {
		return this.accountno;
	}

	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}
    
	public Qdiizjhcrmxxx(){
	}


	public String getOutwardcur() {
		return this.outwardcur;
	}

	public void setOutwardcur(String outwardcur) {
		this.outwardcur = outwardcur;
	}
    
	public BigDecimal getLcyamt() {
		return lcyamt;
	}

	public void setLcyamt(BigDecimal lcyamt) {
		this.lcyamt = lcyamt;
	}

	public BigDecimal getLcyamttousd() {
		return lcyamttousd;
	}

	public void setLcyamttousd(BigDecimal lcyamttousd) {
		this.lcyamttousd = lcyamttousd;
	}

	public BigDecimal getOutwardamt() {
		return outwardamt;
	}

	public void setOutwardamt(BigDecimal outwardamt) {
		this.outwardamt = outwardamt;
	}

	public BigDecimal getOutwardamttousd() {
		return outwardamttousd;
	}

	public void setOutwardamttousd(BigDecimal outwardamttousd) {
		this.outwardamttousd = outwardamttousd;
	}

	public BigDecimal getInwardamt() {
		return inwardamt;
	}

	public void setInwardamt(BigDecimal inwardamt) {
		this.inwardamt = inwardamt;
	}

	public BigDecimal getInwardtousd() {
		return inwardtousd;
	}

	public void setInwardtousd(BigDecimal inwardtousd) {
		this.inwardtousd = inwardtousd;
	}

	public BigDecimal getFcyamttousd() {
		return fcyamttousd;
	}

	public void setFcyamttousd(BigDecimal fcyamttousd) {
		this.fcyamttousd = fcyamttousd;
	}

	public BigDecimal getFcyamt() {
		return fcyamt;
	}

	public void setFcyamt(BigDecimal fcyamt) {
		this.fcyamt = fcyamt;
	}

	public String getInwardcur() {
		return this.inwardcur;
	}

	public void setInwardcur(String inwardcur) {
		this.inwardcur = inwardcur;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	

}