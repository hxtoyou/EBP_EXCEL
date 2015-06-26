package com.ebills.product.declare.domain;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import com.ebills.product.declare.Entity;

/**
 * 对应的表名：sb_Jnzywhszxx
 */

public class Jnzywhszxx extends Entity {
    /**@build by Class builder @auth XJBS*/
    /**操作流水号*/
    private String NGUID;
    /**业务编号*/
    private String BIZNO;
    /**开户银行代码*/
    private String ISSUINGBANK_NO;
    /**开户境内代理机构代码*/
    private String ISSUINGAGENT_NO;
    /**开户境内代理机构名称*/
    private String ISSUINGAGENT_NAME;
    /**境内专用外汇账户账号*/
    private String ACCOUNTNO;
    /**报告期*/
    private String REPORTDATE;
    /**币种*/
    private String CURRENCY;
    /**月初余额*/
    private BigDecimal STARTBALANCE;
    /**汇入金额*/
    private BigDecimal INWARDAMT;
    /**购汇汇入金额*/
    private BigDecimal LCYAMT;
    /**个人外汇储蓄账户划入金额*/
    private BigDecimal PERSONALAMT;
    /**境外投资本金及收益汇回金额*/
    private BigDecimal INCOMEAMT;
    /**汇出金额*/
    private BigDecimal REMITAMT;
    /**汇往境外金额*/
    private BigDecimal OUTREMITAMT;
    /**结汇金额*/
    private BigDecimal FCYAMT;
    /**划入个人外汇储蓄账户金额*/
    private BigDecimal PERSONALAMTTMP;
    /**月末账户余额*/
    private BigDecimal ENDAMT;
    /**其他收支净额*/
    private BigDecimal OTHERAMT;
    /**备注*/
    private String REMARK;
    /**业务流水号*/
    private String RWIDH;
    /**业务编号*/
    private String YWBH;
    /**是否已申报*/
    private String SFYSB;//char(1)
    /**生效标志*/
    private String SXBZ;//char(1)
    /**最新标志*/
    private String SFZX;//char(1)
    /**机构编号*/
    private String BANK_ID;
    /**经办人*/
    private int HANDID;
    /**经办日期*/
    private Timestamp HANDDATE;
    /**复核人*/
    private int CHECKID;
    /**复核时间*/
    private Timestamp CHECKDATE;
    /**是否复核通过*/
    private String ISCHECK;//char(1)
    /**授权人*/
    private int AUTHID;
    /**授权时间*/
    private Timestamp AUTHDATE;
    /**授权是否通过*/
    private String ISAUTH;//char(1)
    
    private String OGUID;
    private Date RECDATE;
    private String DATASOURCES;
    

	public Date getRECDATE() {
		return RECDATE;
	}
	public void setRECDATE(Date rECDATE) {
		RECDATE = rECDATE;
	}
	public String getDATASOURCES() {
		return DATASOURCES;
	}
	public void setDATASOURCES(String dATASOURCES) {
		DATASOURCES = dATASOURCES;
	}
	public String getOGUID() {
		return OGUID;
	}
	public void setOGUID(String oGUID) {
		OGUID = oGUID;
	}
	public String getNGUID() {
		return NGUID;
	}
	public void setNGUID(String nGUID) {
		NGUID = nGUID;
	}
	public String getBIZNO() {
		return BIZNO;
	}
	public void setBIZNO(String bIZNO) {
		BIZNO = bIZNO;
	}
	public String getISSUINGBANK_NO() {
		return ISSUINGBANK_NO;
	}
	public void setISSUINGBANK_NO(String iSSUINGBANKNO) {
		ISSUINGBANK_NO = iSSUINGBANKNO;
	}
	public String getISSUINGAGENT_NO() {
		return ISSUINGAGENT_NO;
	}
	public void setISSUINGAGENT_NO(String iSSUINGAGENTNO) {
		ISSUINGAGENT_NO = iSSUINGAGENTNO;
	}
	public String getISSUINGAGENT_NAME() {
		return ISSUINGAGENT_NAME;
	}
	public void setISSUINGAGENT_NAME(String iSSUINGAGENTNAME) {
		ISSUINGAGENT_NAME = iSSUINGAGENTNAME;
	}
	public String getACCOUNTNO() {
		return ACCOUNTNO;
	}
	public void setACCOUNTNO(String aCCOUNTNO) {
		ACCOUNTNO = aCCOUNTNO;
	}
	
	public String getREPORTDATE() {
		return REPORTDATE;
	}
	public void setREPORTDATE(String rEPORTDATE) {
		REPORTDATE = rEPORTDATE;
	}
	public String getCURRENCY() {
		return CURRENCY;
	}
	public void setCURRENCY(String cURRENCY) {
		CURRENCY = cURRENCY;
	}
	public String getREMARK() {
		return REMARK;
	}
	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}
	public String getRWIDH() {
		return RWIDH;
	}
	public void setRWIDH(String rWIDH) {
		RWIDH = rWIDH;
	}
	public String getYWBH() {
		return YWBH;
	}
	public void setYWBH(String yWBH) {
		YWBH = yWBH;
	}
	public String getSFYSB() {
		return SFYSB;
	}
	public void setSFYSB(String sFYSB) {
		SFYSB = sFYSB;
	}
	public String getSXBZ() {
		return SXBZ;
	}
	public void setSXBZ(String sXBZ) {
		SXBZ = sXBZ;
	}
	public String getSFZX() {
		return SFZX;
	}
	public void setSFZX(String sFZX) {
		SFZX = sFZX;
	}
	public String getBANK_ID() {
		return BANK_ID;
	}
	public void setBANK_ID(String bANKID) {
		BANK_ID = bANKID;
	}
	public Timestamp getHANDDATE() {
		return HANDDATE;
	}
	public void setHANDDATE(Timestamp hANDDATE) {
		HANDDATE = hANDDATE;
	}
	public Timestamp getCHECKDATE() {
		return CHECKDATE;
	}
	public void setCHECKDATE(Timestamp cHECKDATE) {
		CHECKDATE = cHECKDATE;
	}
	public String getISCHECK() {
		return ISCHECK;
	}
	public void setISCHECK(String iSCHECK) {
		ISCHECK = iSCHECK;
	}
	
	public Timestamp getAUTHDATE() {
		return AUTHDATE;
	}
	public void setAUTHDATE(Timestamp aUTHDATE) {
		AUTHDATE = aUTHDATE;
	}
	public String getISAUTH() {
		return ISAUTH;
	}
	public void setISAUTH(String iSAUTH) {
		ISAUTH = iSAUTH;
	}
	
	public BigDecimal getSTARTBALANCE() {
		return STARTBALANCE;
	}
	public void setSTARTBALANCE(BigDecimal sTARTBALANCE) {
		STARTBALANCE = sTARTBALANCE;
	}
	public BigDecimal getINWARDAMT() {
		return INWARDAMT;
	}
	public void setINWARDAMT(BigDecimal iNWARDAMT) {
		INWARDAMT = iNWARDAMT;
	}
	public BigDecimal getLCYAMT() {
		return LCYAMT;
	}
	public void setLCYAMT(BigDecimal lCYAMT) {
		LCYAMT = lCYAMT;
	}
	public BigDecimal getPERSONALAMT() {
		return PERSONALAMT;
	}
	public void setPERSONALAMT(BigDecimal pERSONALAMT) {
		PERSONALAMT = pERSONALAMT;
	}
	public BigDecimal getINCOMEAMT() {
		return INCOMEAMT;
	}
	public void setINCOMEAMT(BigDecimal iNCOMEAMT) {
		INCOMEAMT = iNCOMEAMT;
	}
	public BigDecimal getREMITAMT() {
		return REMITAMT;
	}
	public void setREMITAMT(BigDecimal rEMITAMT) {
		REMITAMT = rEMITAMT;
	}
	public BigDecimal getOUTREMITAMT() {
		return OUTREMITAMT;
	}
	public void setOUTREMITAMT(BigDecimal oUTREMITAMT) {
		OUTREMITAMT = oUTREMITAMT;
	}
	public BigDecimal getFCYAMT() {
		return FCYAMT;
	}
	public void setFCYAMT(BigDecimal fCYAMT) {
		FCYAMT = fCYAMT;
	}
	public BigDecimal getPERSONALAMTTMP() {
		return PERSONALAMTTMP;
	}
	public void setPERSONALAMTTMP(BigDecimal pERSONALAMTTMP) {
		PERSONALAMTTMP = pERSONALAMTTMP;
	}
	public BigDecimal getENDAMT() {
		return ENDAMT;
	}
	public void setENDAMT(BigDecimal eNDAMT) {
		ENDAMT = eNDAMT;
	}
	public BigDecimal getOTHERAMT() {
		return OTHERAMT;
	}
	public void setOTHERAMT(BigDecimal oTHERAMT) {
		OTHERAMT = oTHERAMT;
	}
	
	public int getHANDID() {
		return HANDID;
	}
	public void setHANDID(int hANDID) {
		HANDID = hANDID;
	}
	public int getCHECKID() {
		return CHECKID;
	}
	public void setCHECKID(int cHECKID) {
		CHECKID = cHECKID;
	}
	public int getAUTHID() {
		return AUTHID;
	}
	public void setAUTHID(int aUTHID) {
		AUTHID = aUTHID;
	}
	public Jnzywhszxx(){
		this.setSTARTBALANCE(new BigDecimal(0));
	    this.setINWARDAMT(new BigDecimal(0));
	    this.setLCYAMT(new BigDecimal(0));
	    this.setPERSONALAMT(new BigDecimal(0));
	    this.setINCOMEAMT(new BigDecimal(0));
	    this.setREMITAMT(new BigDecimal(0));
	    this.setOUTREMITAMT(new BigDecimal(0));
	    this.setFCYAMT(new BigDecimal(0));
	    this.setPERSONALAMTTMP(new BigDecimal(0));
	    this.setENDAMT(new BigDecimal(0));
	    this.setOTHERAMT(new BigDecimal(0));
	    
	}
}
