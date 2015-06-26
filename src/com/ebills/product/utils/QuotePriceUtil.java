package com.ebills.product.utils;

import com.ebills.product.commons.QuotePrice;
import com.ebills.util.EbillsException;

public class QuotePriceUtil {
	/**
	 * 根据金额和币种转换成另一币种的等值金额
	 * @param fCur 原币种
	 * @param tCur 转换后的币种
	 * @param amt 金额
	 * @return 转换后的金额
	 */
	public double getPrice(String fCur, String tCur, double amt){
		try {
			return QuotePrice.getExchangeAmt(fCur, tCur, amt, this.orgNo);
		} catch (EbillsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 交易的执行机构
	 */
	private String orgNo;
	
	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
}
