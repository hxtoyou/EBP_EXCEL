package com.ebills.product.dg.commons;

import java.sql.Timestamp;

public class Paccypri {
    
	/**机构编号**/
    private String orgNo;
    /**导入日期**/
    private Timestamp impDate;
    /**币种符号**/
    private String curSign;
    /**汇买价**/
    private double buyPrice;
    /**汇卖价**/
    private double salePrice;
    /**汇中间价**/
    private double middlePrice;
    /**钞买价**/
    private double billBuyPrice;
    /**钞卖价**/
    private double billSalePrice;
    /**内部买入价**/
    private double innerbuyPrice;
    /**内部卖出价**/
    private double innersalePrice;   
    /**最优买价*/
    private double zyBuyPrice;
    /**最优卖价*/
    private double zySalePrice;
    /**基准价（上海外汇中心发布的中间价）*/
    private double basePrice;
    /**关市汇率*/
    private double closingRate;
	
	private String  insoutce;
    
    private String txnno;
    
    private String status;
    
    
    public Paccypri(){
    	impDate =new Timestamp(System.currentTimeMillis());
    	buyPrice=0;
    	salePrice=0;
    	middlePrice=0;
    	billBuyPrice=0;
    	billSalePrice=0;
    	innerbuyPrice=0;
    	innersalePrice=0;
    	zyBuyPrice=0;
    	basePrice=0;
    	closingRate=0;
    }
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	public Timestamp getImpDate() {
		return impDate;
	}
	public void setImpDate(Timestamp impDate) {
		this.impDate = impDate;
	}
	public String getCurSign() {
		return curSign;
	}
	public void setCurSign(String curSign) {
		this.curSign = curSign;
	}
	public double getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(double buyPrice) {
		this.buyPrice = buyPrice;
	}
	public double getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}
	public double getMiddlePrice() {
		return middlePrice;
	}
	public void setMiddlePrice(double middlePrice) {
		this.middlePrice = middlePrice;
	}
	public double getBillBuyPrice() {
		return billBuyPrice;
	}
	public void setBillBuyPrice(double billBuyPrice) {
		this.billBuyPrice = billBuyPrice;
	}
	public double getBillSalePrice() {
		return billSalePrice;
	}
	public void setBillSalePrice(double billSalePrice) {
		this.billSalePrice = billSalePrice;
	}
	public double getInnerbuyPrice() {
		return innerbuyPrice;
	}
	public void setInnerbuyPrice(double innerbuyPrice) {
		this.innerbuyPrice = innerbuyPrice;
	}
	public double getInnersalePrice() {
		return innersalePrice;
	}
	public void setInnersalePrice(double innersalePrice) {
		this.innersalePrice = innersalePrice;
	}
	public double getZyBuyPrice() {
		return zyBuyPrice;
	}
	public void setZyBuyPrice(double zyBuyPrice) {
		this.zyBuyPrice = zyBuyPrice;
	}
	public double getZySalePrice() {
		return zySalePrice;
	}
	public void setZySalePrice(double zySalePrice) {
		this.zySalePrice = zySalePrice;
	}
	public double getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(double basePrice) {
		this.basePrice = basePrice;
	}
	public double getClosingRate() {
		return closingRate;
	}
	public void setClosingRate(double closingRate) {
		this.closingRate = closingRate;
	}
	public String getInsoutce() {
		return insoutce;
	}
	public void setInsoutce(String insoutce) {
		this.insoutce = insoutce;
	}
	public String getTxnno() {
		return txnno;
	}
	public void setTxnno(String txnno) {
		this.txnno = txnno;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
    
    
}
