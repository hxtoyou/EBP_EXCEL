package com.ebills.product.declare.domain;

/*
 * Created on 2005-9-12
 */

import java.sql.Date;

import com.ebills.product.declare.Entity;


/**
 * 功能： 对外承兑付款通知书——申报信息
 */
public class DwfkcdtzsSbxx extends Entity {
	/** 新流水号 */
	private String NGUID;

	/** 操作类型 */
	private String ACTIONTYPE;

	/** 修改/删除原因 */
	private String ACTIONDESC;

	/** 申报号码 */
	private String RPTNO;

	/** 收款人常驻国家 */
	private String COUNTRY;

	/** 付款类型 */
	private String PAYTYPE;

	/** 交易编码1 */
	private String TXCODE;

	/** 交易金额1 */
	private double TC1AMT;

	/** 交易附言1 */
	private String TXREM;

	/** 交易编码2 */
	private String TXCODE2;

	/** 交易金额2 */
	private double TC2AMT;

	/** 交易附言2 */
	private String TX2REM;

	/** 是否核销项下付款 */
	private String ISREF;

	/** 申请人 */
	private String CRTUSER;

	/** 申请人电话 */
	private String INPTELC;

	/** 申报日期 */
	private Date RPTDATE;

	/** 业务任务编号 */
	private String RWIDH;

	/** 业务编号 */
	private String YWBH;

	/** 数据录入日期 */
	private Date SJLRRQ;

	/** 是否申报标志 */
	private String SFYSB;

	/** 是否生效标志 */
	private String SXBZ;

	/** 最新标志 */
	private String SFZX;

	/** 分行编号 */
	private String BANK_ID;

	/** 老流水号 */
	private String OGUID;
    
	/**外汇局批件号/备案表号/业务编号 49号文修改*/
	private String REGNO; 
	/**
	 * @Build by Class builder
	 * @auth XJBS
	 */
	public DwfkcdtzsSbxx() {
		this.setACTIONTYPE("A");
		this.setSFYSB("N");
		this.setSFZX("Y");
		this.setSXBZ("1");// 昆明要求默认为录入完成 @PengZishun KunMing
		this.setSJLRRQ(new Date(System.currentTimeMillis()));
		this.setRPTDATE(new Date(System.currentTimeMillis()));
	}
    
	public String getREGNO() {
		return REGNO;
	}

	public void setREGNO(String regno) {
		REGNO = regno;
	}

	public String getACTIONDESC() {
		return ACTIONDESC;
	}

	public void setACTIONDESC(String actiondesc) {
		ACTIONDESC = actiondesc;
	}

	public String getACTIONTYPE() {
		return ACTIONTYPE;
	}

	public void setACTIONTYPE(String actiontype) {
		ACTIONTYPE = actiontype;
		ACTIONTYPE = ACTIONTYPE.equals("U") ? "C" : ACTIONTYPE;
	}

	public String getBANK_ID() {
		return BANK_ID;
	}

	public void setBANK_ID(String bank_id) {
		BANK_ID = bank_id;
	}

	public String getCOUNTRY() {
		return COUNTRY;
	}

	public void setCOUNTRY(String country) {
		COUNTRY = country;
	}

	public String getCRTUSER() {
		return CRTUSER;
	}

	public void setCRTUSER(String crtuser) {
        CRTUSER = crtuser;
        if (CRTUSER!=null && CRTUSER.length()>20){
        	CRTUSER=CRTUSER.substring(0,20);
        }
    }
    public void setINPTELC(String inptelc) {
        INPTELC = inptelc;
        if (INPTELC!=null && INPTELC.length()>20){
        	INPTELC=INPTELC.substring(0,20);
        }
    }

	public String getINPTELC() {
		return INPTELC;
	}


	public String getISREF() {
		return ISREF;
	}

	public void setISREF(String isref) {
		ISREF = isref;
	}

	public String getNGUID() {
		return NGUID;
	}

	public void setNGUID(String nguid) {
		NGUID = nguid;
	}

	public String getOGUID() {
		return OGUID;
	}

	public void setOGUID(String oguid) {
		OGUID = oguid;
	}

	public String getPAYTYPE() {
		return PAYTYPE;
	}

	public void setPAYTYPE(String paytype) {
		PAYTYPE = paytype;
	}

	public String getRPTNO() {
		return RPTNO;
	}

	public void setRPTNO(String rptno) {
		RPTNO = rptno;
	}

	public String getRWIDH() {
		return RWIDH;
	}

	public void setRWIDH(String rwidh) {
		RWIDH = rwidh;
	}

	public String getSFYSB() {
		return SFYSB;
	}

	public void setSFYSB(String sfysb) {
		SFYSB = sfysb;
	}

	public String getSFZX() {
		return SFZX;
	}

	public void setSFZX(String sfzx) {
		SFZX = sfzx;
	}

	public Date getRPTDATE() {
		return RPTDATE;
	}

	public void setRPTDATE(Date rptdate) {
		RPTDATE = rptdate;
	}

	public Date getSJLRRQ() {
		return SJLRRQ;
	}

	public void setSJLRRQ(Date sjlrrq) {
		SJLRRQ = sjlrrq;
	}

	public String getSXBZ() {
		return SXBZ;
	}

	public void setSXBZ(String sxbz) {
		SXBZ = sxbz;
	}

	public double getTC1AMT() {
		return TC1AMT;
	}

	public void setTC1AMT(double tc1amt) {
		TC1AMT = tc1amt;
	}

	public double getTC2AMT() {
		return TC2AMT;
	}

	public void setTC2AMT(double tc2amt) {
		TC2AMT = tc2amt;
	}

	public String getTX2REM() {
		return TX2REM;
	}

	public void setTX2REM(String tx2rem) {
		TX2REM = tx2rem;
	}

	public String getTXCODE() {
		return TXCODE;
	}

	public void setTXCODE(String txcode) {
		TXCODE = txcode;
	}

	public String getTXCODE2() {
		return TXCODE2;
	}

	public void setTXCODE2(String txcode2) {
		TXCODE2 = txcode2;
	}

	public String getTXREM() {
		return TXREM;
	}

	public void setTXREM(String txrem) {
		TXREM = txrem;
	}

	public String getYWBH() {
		return YWBH;
	}

	public void setYWBH(String ywbh) {
		YWBH = ywbh;
	}
}
