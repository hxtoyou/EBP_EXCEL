package com.ebills.product.declare.domain;


import java.math.BigDecimal;
import java.sql.Timestamp;

import com.ebills.product.declare.Entity;

/**
 * 
 *  
 */

public class QyxxQuerry extends Entity {
	private String NGUID;
	private String TXNSERIALNO ;
	private String YWBH ;
	private String WZBH ; 
	private String ORGNO;
	private String CURSIGN;
	private BigDecimal AMOUNT;
	private Timestamp YWRQ;
	private String ISCHECK;
	private String SFYSB;
	private String TRADENO;
	private String DATASOURCES;
	private String ACTIONTYPE;
	
	
	public String getDATASOURCES() {
		return DATASOURCES;
	}
	public void setDATASOURCES(String dATASOURCES) {
		DATASOURCES = dATASOURCES;
	}
	public String getACTIONTYPE() {
		return ACTIONTYPE;
	}
	public void setACTIONTYPE(String aCTIONTYPE) {
		ACTIONTYPE = aCTIONTYPE;
	}
	
	
	public String getNGUID() {
		return NGUID;
	}
	public void setNGUID(String nguid) {
		NGUID = nguid;
	}
	public String getTXNSERIALNO() {
		return TXNSERIALNO;
	}
	public void setTXNSERIALNO(String txnserialno) {
		TXNSERIALNO = txnserialno;
	}
	public String getYWBH() {
		return YWBH;
	}
	public void setYWBH(String ywbh) {
		YWBH = ywbh;
	}
	public String getWZBH() {
		return WZBH;
	}
	public void setWZBH(String wzbh) {
		WZBH = wzbh;
	}
	public String getORGNO() {
		return ORGNO;
	}
	public void setORGNO(String orgno) {
		ORGNO = orgno;
	}
	public String getCURSIGN() {
		return CURSIGN;
	}
	public void setCURSIGN(String cursign) {
		CURSIGN = cursign;
	}
	public Timestamp getYWRQ() {
		return YWRQ;
	}
	public void setYWRQ(Timestamp ywrq) {
		YWRQ = ywrq;
	}
	public String getISCHECK() {
		return ISCHECK;
	}
	public void setISCHECK(String ischeck) {
		ISCHECK = ischeck;
	}
	public String getSFYSB() {
		return SFYSB;
	}
	public void setSFYSB(String sfysb) {
		SFYSB = sfysb;
	}
	public String getTRADENO() {
		return TRADENO;
	}
	public void setTRADENO(String tradeno) {
		TRADENO = tradeno;
	}
	public BigDecimal getAMOUNT() {
		return AMOUNT;
	}
	public void setAMOUNT(BigDecimal amount) {
		AMOUNT = amount;
	}
	
	
}