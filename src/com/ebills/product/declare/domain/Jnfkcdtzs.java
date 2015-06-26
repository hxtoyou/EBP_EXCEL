package com.ebills.product.declare.domain;


import java.sql.Date;

import com.ebills.product.declare.Entity;



public class Jnfkcdtzs extends Entity {
    /**@build by Class builder @auth XJBS*/
    /**新流水号*/
    private String NGUID;
    /**操作类型*/
    private String ACTIONTYPE;
    /**修改/删除原因*/
    private String ACTIONDESC;
    /**进口核销专用申报号码*/
    private String RPTNO;
    /**付款人类型*/
    private String CUSTYPE;
    /**个人身份证件号码*/
    private String IDCODE;
    /**组织机构代码*/
    private String CUSTCOD;
    /**付款人名称*/
    private String CUSTNM;
    /**收款人名称*/
    private String OPPUSER;
    /**付款币种*/
    private String TXCCY;
    /**付款金额*/
    private double TXAMT;
    /**购汇汇率*/
    private double TXRATE;
    /**购汇金额*/
    private double LCYAMT;
    /**人民币帐号/银行卡号*/
    private String LCYACC;
    /**现汇金额*/
    private double FCYAMT;
    /**外汇帐号/银行卡号*/
    private String FCYACC;
    /**其它金额*/
    private double OTHAMT;
    /**其它帐号/银行卡号*/
    private String OTHACC;
    /**结算方式*/
    private String METHOD;
    /**银行业务编号*/
    private String BUSCODE;
    /**实际付款币种*/
    private String ACTUCCY;
    /**实际付款金额*/
    private double ACTUAMT;
    /**扣费币种*/
    private String OUTCHARGECCY;
    /**扣费金额*/
    private double OUTCHARGEAMT;
    /**期限*/
    private int TENOR;
    /**开证日期*/
    private Date ISSDATE;
    /**信用证编号*/
    private String LCBGNO;
    /**任务编号*/
    private String RWIDH;
    /**业务编号*/
    private String YWBH;
    /**数据录入日期*/
    private Date SJLRRQ;
    /**是否已经申报*/
    private String SFYSB;
    /**生效标志*/
    private String SXBZ;
    /**最新标志*/
    private String SFZX;
    /**分行编号*/
    private String BANK_ID;
    /**老流水号*/
    private String OGUID;
    /**@Build by Class builder @auth XJBS*/
    public Jnfkcdtzs(){
        this.setACTIONTYPE("A");
		this.setSFYSB("N");
		this.setSFZX("Y");
		this.setSXBZ("0");
		this.setSJLRRQ(new Date(System.currentTimeMillis()));
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
    public double getACTUAMT() {
        return ACTUAMT;
    }
    public void setACTUAMT(double actuamt) {
        ACTUAMT = actuamt;
    }
    public String getACTUCCY() {
        return ACTUCCY;
    }
    public void setACTUCCY(String actuccy) {
        ACTUCCY = actuccy;
    }
    public String getBANK_ID() {
        return BANK_ID;
    }
    public void setBANK_ID(String bank_id) {
        BANK_ID = bank_id;
    }
    public String getBUSCODE() {
        return BUSCODE;
    }
    public void setBUSCODE(String buscode) {
        BUSCODE = buscode;
    }
    public String getCUSTCOD() {
        return CUSTCOD;
    }
    public void setCUSTCOD(String custcod) {
        CUSTCOD = custcod;
    }
    public String getCUSTNM() {
        return CUSTNM;
    }
    public void setCUSTNM(String custnm) {
        CUSTNM = custnm.length()>128?custnm.substring(0,127).trim():custnm;
    }
    public String getCUSTYPE() {
        return CUSTYPE;
    }
    public void setCUSTYPE(String custype) {
        CUSTYPE = custype;
    }
    public String getFCYACC() {
        return FCYACC;
    }
    public void setFCYACC(String fcyacc) {
        FCYACC = fcyacc;
    }
    public double getFCYAMT() {
        return FCYAMT;
    }
    public void setFCYAMT(double fcyamt) {
        FCYAMT = fcyamt;
    }
    public String getIDCODE() {
        return IDCODE;
    }
    public void setIDCODE(String idcode) {
        IDCODE = idcode;
    }
    public Date getISSDATE() {
        return ISSDATE;
    }
    public void setISSDATE(Date issdate) {
        ISSDATE = issdate;
    }
    public String getLCBGNO() {
        return LCBGNO;
    }
    public void setLCBGNO(String lcbgno) {
        LCBGNO = lcbgno;
    }
    public String getLCYACC() {
        return LCYACC;
    }
    public void setLCYACC(String lcyacc) {
        LCYACC = lcyacc;
    }
    public double getLCYAMT() {
        return LCYAMT;
    }
    public void setLCYAMT(double lcyamt) {
        LCYAMT = lcyamt;
    }
    public String getMETHOD() {
        return METHOD;
    }
    public void setMETHOD(String method) {
        METHOD = method;
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
    public String getOPPUSER() {
        return OPPUSER;
    }
    public void setOPPUSER(String oppuser) {
        OPPUSER = oppuser.length()>128?oppuser.substring(0,127).trim():oppuser;
    }
    public String getOTHACC() {
        return OTHACC;
    }
    public void setOTHACC(String othacc) {
        OTHACC = othacc;
    }
    public double getOTHAMT() {
        return OTHAMT;
    }
    public void setOTHAMT(double othamt) {
        OTHAMT = othamt;
    }
    public double getOUTCHARGEAMT() {
        return OUTCHARGEAMT;
    }
    public void setOUTCHARGEAMT(double outchargeamt) {
        OUTCHARGEAMT = outchargeamt;
    }
    public String getOUTCHARGECCY() {
        return OUTCHARGECCY;
    }
    public void setOUTCHARGECCY(String outchargeccy) {
        OUTCHARGECCY = outchargeccy;
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
    public int getTENOR() {
        return TENOR;
    }
    public void setTENOR(int tenor) {
        TENOR = tenor;
    }
    public double getTXAMT() {
        return TXAMT;
    }
    public void setTXAMT(double txamt) {
        TXAMT = txamt;
    }
    public String getTXCCY() {
        return TXCCY;
    }
    public void setTXCCY(String txccy) {
        TXCCY = txccy;
    }
    public double getTXRATE() {
        return TXRATE;
    }
    public void setTXRATE(double txrate) {
        TXRATE = txrate;
    }
    public String getYWBH() {
        return YWBH;
    }
    public void setYWBH(String ywbh) {
        YWBH = ywbh;
    }
}
