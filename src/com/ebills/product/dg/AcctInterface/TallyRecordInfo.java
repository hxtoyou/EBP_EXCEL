package com.ebills.product.dg.AcctInterface;

import java.sql.Date;

/**
 * 2014-06-14 LT
 * 定义冲帐对象
 * @return
 * @throws Exception
 */
public class TallyRecordInfo {
	/*交易流水号*/
    private String txnSerialNo;//TXNSERIALNO
    /*记帐日期*/
    private Date tallyDate;//TALLYDATE
    /*核心流水号*/
    private String hxFlserialNo;//HXFLSERIALNO
    /*记帐柜员号*/
    private String tallyUser;//TALLYUSER
    
	public String getTxnSerialNo() {
		return txnSerialNo;
	}
	public void setTxnSerialNo(String txnSerialNo) {
		this.txnSerialNo = txnSerialNo;
	}
	public Date getTallyDate() {
		return tallyDate;
	}
	public void setTallyDate(Date tallyDate) {
		this.tallyDate = tallyDate;
	}
	public String getHxFlserialNo() {
		return hxFlserialNo;
	}
	public void setHxFlserialNo(String hxFlserialNo) {
		this.hxFlserialNo = hxFlserialNo;
	}
	public String getTallyUser() {
		return tallyUser;
	}
	public void setTallyUser(String tallyUser) {
		this.tallyUser = tallyUser;
	}

	
}
