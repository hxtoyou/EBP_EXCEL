package com.ebills.product.declare.domain;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Date;

import com.ebills.product.declare.Entity;

/**
 * 
 */

public class DownDate extends Entity {
	// 
	private String trandate;

	public String getTrandate() {
		return trandate;
	}

	public void setTrandate(String trandate) {
		this.trandate = trandate;
	}
	

}