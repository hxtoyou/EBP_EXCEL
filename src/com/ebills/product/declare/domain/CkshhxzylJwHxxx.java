package com.ebills.product.declare.domain;


import java.sql.Date;

import com.ebills.product.declare.Entity;


public class CkshhxzylJwHxxx extends Entity {
	/**@build by Class builder @auth XJBS*/
	/**新流水号*/
	private String NGUID;

	/**操作类型*/
	private String ACTIONTYPE;

	/**操作原因*/
	private String ACTIONDESC;

	/**申报号码*/
	private String RPTNO;

	/**收汇类型*/
	private String PAYATTR;

	/**已出具出口收汇核销专用联*/
	private String CHKPRTD;

	/**余款金额*/
	private double OSAMT;

	/**出口收汇核销单号码(列表)*/
	private String REFNO;

	/**收汇总金额中用于出口核销的金额*/
	private double CHKAMT;

	/**填报人*/
	private String CRTUSER;

	/**填报人电话*/
	private String INPTELC;

	/**申报日期*/
	private Date RPTDATE;

	/**任务号*/
	private String RWIDH;

	/**业务编号*/
	private String YWBH;

	/**数据录入日期*/
	private Date SJLRRQ;

	/**是否已申报*/
	private String SFYSB;

	/**是否生效*/
	private String SXBZ;

	/**最新标志*/
	private String SFZX;

	/**机构编号*/
	private String BANK_ID;

	/**老流水号*/
	private String OGUID;

	/**@Build by Class builder @auth XJBS*/
	public CkshhxzylJwHxxx() {
		this.setACTIONTYPE("A");
		this.setSFYSB("N");
		this.setSFZX("Y");
		this.setSXBZ("1");
		this.setSJLRRQ(new Date(System.currentTimeMillis()));
		this.setRPTDATE(new Date(System.currentTimeMillis()));
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
		ACTIONTYPE=ACTIONTYPE.equals("U")?"C":ACTIONTYPE;
	}

	public String getBANK_ID() {
		return BANK_ID;
	}

	public void setBANK_ID(String bank_id) {
		BANK_ID = bank_id;
	}

	public double getCHKAMT() {
		return CHKAMT;
	}

	public void setCHKAMT(double chkamt) {
		CHKAMT = chkamt;
	}

	public String getCHKPRTD() {
		return CHKPRTD;
	}

	public void setCHKPRTD(String chkprtd) {
		CHKPRTD = chkprtd;
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

	public double getOSAMT() {
		return OSAMT;
	}

	public void setOSAMT(double osamt) {
		OSAMT = osamt;
	}

	public String getPAYATTR() {
		return PAYATTR;
	}

	public void setPAYATTR(String payattr) {
		PAYATTR = payattr;
	}

	public String getREFNO() {
		return REFNO;
	}

	public void setREFNO(String refno) {
		REFNO = refno;
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

	public String getYWBH() {
		return YWBH;
	}

	public void setYWBH(String ywbh) {
		YWBH = ywbh;
	}

	public Date getRPTDATE() {
		return RPTDATE;
	}

	public void setRPTDATE(Date rptdate) {
		RPTDATE = rptdate;
	}
}