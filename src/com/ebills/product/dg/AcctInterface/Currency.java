package com.ebills.product.dg.AcctInterface;

public class Currency {
	private String cursign;//CURSIGN IS 货币符号
	private String tallyCode;//TALLYCODE IS '记账货币编号'
	
	public String getCursign() {
		return cursign;
	}
	public void setCursign(String cursign) {
		this.cursign = cursign;
	}
	public String getTallyCode() {
		return tallyCode;
	}
	public void setTallyCode(String tallyCode) {
		this.tallyCode = tallyCode;
	}
	
}
