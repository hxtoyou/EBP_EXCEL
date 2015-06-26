package com.ebills.product.declare.domain;


import java.sql.Date;
import java.sql.Timestamp;

import com.ebills.product.declare.Entity;

/**
 * 
 * 对应的表名：sb_Jnzywhghzjxx
 *
 */

public class Jnzywhghzjxx extends Entity {
    /**@build by Class builder @auth XJBS*/
    /**操作流水号*/
    private String NGUID;
    /**业务编号*/
    private String BIZNO;
    /**开户银行代码*/
    private String ISSUINGBANK_NO;
    /**开户境内代理机构代码*/
    private String ISSUINGAGENT_NO;
    /**境内专用外汇账户账号*/
    private String ACCOUNTNO;
    /**关户日期*/
    private Date CLOSEDATE;
    /**关户原因*/
    private String CLOSERESON;
    /**关户后资金处置类型*/
    private String CLOSETYPE;
    /**结汇所得人民币资金划往的账户开户行代码*/
    private String FCYRMB_ISSUINGBANK_NO;
    /**结汇所得人民币资金划往的账户户名*/
    private String FCYRMB_ISSUINGBANK_NAME;
    /**结汇所得人民币资金划往的账户账号*/
    private String FCYRMB_ACCOUNTNO;
    /**划往境内外汇账户开户行代码*/
    private String INWARD_ISSUINGBANK_NO;
    /**划往境内外汇账户户名*/
    private String INWARD_ACCOUNTNAME;
    /**划往境内外汇账户账号*/
    private String INWARD_ACCOUNTNO;
    /** 汇出境外收款账户开户行代码*/
    private String OUTWARD_ISSUINGBANK_NO;
    /** 汇出境外收款账户户名*/
    private String OUTWARD_ACCOUNTNAME;
    /**汇出境外收款账户账号*/
    private String OUTWARD_ACCOUNTNO;
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
    private double HANDID;
    /**经办日期*/
    private Timestamp HANDDATE;
    /**复核人*/
    private double CHECKID;
    /**复核时间*/
    private Timestamp CHECKDATE;
    /**是否复核通过*/
    private String ISCHECK;//char(1)
    /**授权人*/
    private double AUTHID;
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
	public String getACCOUNTNO() {
		return ACCOUNTNO;
	}
	public void setACCOUNTNO(String aCCOUNTNO) {
		ACCOUNTNO = aCCOUNTNO;
	}
	public Date getCLOSEDATE() {
		return CLOSEDATE;
	}
	public void setCLOSEDATE(Date cLOSEDATE) {
		CLOSEDATE = cLOSEDATE;
	}
	public String getCLOSERESON() {
		return CLOSERESON;
	}
	public void setCLOSERESON(String cLOSERESON) {
		CLOSERESON = cLOSERESON;
	}
	public String getCLOSETYPE() {
		return CLOSETYPE;
	}
	public void setCLOSETYPE(String cLOSETYPE) {
		CLOSETYPE = cLOSETYPE;
	}
	public String getFCYRMB_ISSUINGBANK_NO() {
		return FCYRMB_ISSUINGBANK_NO;
	}
	public void setFCYRMB_ISSUINGBANK_NO(String fCYRMBISSUINGBANKNO) {
		FCYRMB_ISSUINGBANK_NO = fCYRMBISSUINGBANKNO;
	}
	public String getFCYRMB_ISSUINGBANK_NAME() {
		return FCYRMB_ISSUINGBANK_NAME;
	}
	public void setFCYRMB_ISSUINGBANK_NAME(String fCYRMBISSUINGBANKNAME) {
		FCYRMB_ISSUINGBANK_NAME = fCYRMBISSUINGBANKNAME;
	}
	public String getFCYRMB_ACCOUNTNO() {
		return FCYRMB_ACCOUNTNO;
	}
	public void setFCYRMB_ACCOUNTNO(String fCYRMBACCOUNTNO) {
		FCYRMB_ACCOUNTNO = fCYRMBACCOUNTNO;
	}
	public String getINWARD_ISSUINGBANK_NO() {
		return INWARD_ISSUINGBANK_NO;
	}
	public void setINWARD_ISSUINGBANK_NO(String iNWARDISSUINGBANKNO) {
		INWARD_ISSUINGBANK_NO = iNWARDISSUINGBANKNO;
	}
	public String getINWARD_ACCOUNTNAME() {
		return INWARD_ACCOUNTNAME;
	}
	public void setINWARD_ACCOUNTNAME(String iNWARDACCOUNTNAME) {
		INWARD_ACCOUNTNAME = iNWARDACCOUNTNAME;
	}
	public String getINWARD_ACCOUNTNO() {
		return INWARD_ACCOUNTNO;
	}
	public void setINWARD_ACCOUNTNO(String iNWARDACCOUNTNO) {
		INWARD_ACCOUNTNO = iNWARDACCOUNTNO;
	}
	public String getOUTWARD_ISSUINGBANK_NO() {
		return OUTWARD_ISSUINGBANK_NO;
	}
	public void setOUTWARD_ISSUINGBANK_NO(String oUTWARDISSUINGBANKNO) {
		OUTWARD_ISSUINGBANK_NO = oUTWARDISSUINGBANKNO;
	}
	public String getOUTWARD_ACCOUNTNAME() {
		return OUTWARD_ACCOUNTNAME;
	}
	public void setOUTWARD_ACCOUNTNAME(String oUTWARDACCOUNTNAME) {
		OUTWARD_ACCOUNTNAME = oUTWARDACCOUNTNAME;
	}
	public String getOUTWARD_ACCOUNTNO() {
		return OUTWARD_ACCOUNTNO;
	}
	public void setOUTWARD_ACCOUNTNO(String oUTWARDACCOUNTNO) {
		OUTWARD_ACCOUNTNO = oUTWARDACCOUNTNO;
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
	public double getHANDID() {
		return HANDID;
	}
	public void setHANDID(double hANDID) {
		HANDID = hANDID;
	}
	public Timestamp getHANDDATE() {
		return HANDDATE;
	}
	public void setHANDDATE(Timestamp hANDDATE) {
		HANDDATE = hANDDATE;
	}
	public double getCHECKID() {
		return CHECKID;
	}
	public void setCHECKID(double cHECKID) {
		CHECKID = cHECKID;
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
	public double getAUTHID() {
		return AUTHID;
	}
	public void setAUTHID(double aUTHID) {
		AUTHID = aUTHID;
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
    
}
