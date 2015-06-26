package com.ebills.product.dg.AcctInterface.domain;


import java.sql.Date;
import java.sql.Timestamp;

/**
 * 该类描述的是从各个业务中抽取的公用信息，一个完整的业务信息由该类和该业务的具体类 共同组成。
 */
public class Transaction  {
	/** 交易流水号 */
	private String txnSerialNo = null;

	/** 工作流流程编号 */
	long processNo = 0;

	/** 交易类型号 */
	private String tradeNo = null;

	/** 所属机构号 */
	private String belongOrgNo = null;

	/** 执行机构号 */
	private String transactOrgNo = null;

	/** 当前业务号 */
	private String currentBizNo = null;

	/** 次业务号 */
	private String secondaryBizNo = null;

	/** 主业务号 */
	private String primaryBizNo = null;

	/** 发起方式号 */
	private String launchModeNo = null;

	/** 发起日期 */
	private Timestamp launchDate = null;

	/** 卷宗号 */
	private String fileNo = null;

	/** 币种 */
	private String curSign = null;

	/** 金额 */
	private double amount = (double) 0.0;

	/** 起息日 */
	private Date interetDate = null;

	/** 是否需要申报 */
	private String isNeedDeclare = null;

	/** 申报日期 */
	private Timestamp declareDate = null;

	/** 是否需要记帐 */
	private String isNeedAccount = null;

	/** 记帐日期 */
	private Timestamp accountDate = null;

	/** 是否需要发报 */
	private String isNeedSwf = null;

	/** 发报日期 */
	private Timestamp swfDate = null;

	/** 完成日期 */
	private Timestamp finishedDate = null;

	/** 冲帐日期 */
	private Timestamp reverseEntryDate = null;

	/** 是否有效 */
	private String tranState = null;

	/** 备注 */
	private String memo = null;

	/** 公司编号 */
	private String corpNo = null;

	/** 经办人 */
	private int handleOperId;

	/** 复核人 */
	private int checkOperId;

	/** 客户经理号 */
	private String managerId;

	/** 贷款编号 */
	private String debtNo;
	
	/**传真号码*/
    private String faxNo;

	/** 传真类型 0 面函传真 1 报文传真 2 面函报文传真 3 不发传真 */
	private String faxType;

	/** 传真备注 */
	private String faxNote = null;  //汇出汇款中用于判断是否核销项下的标志

	/** 收款人/付款人国别 */
	private String countryCode = "156";

	// 昆明修改
	/** 经办更正时发生了更改的字符串* */
	private String handleAgainModify = "NULL";

	/** 复核时作了标记的字符串* */
	private String checkedModify = "NULL";

	/** 作信贷查询交易状态标志，若为Y则信贷可以取消该交易，X为正在记账，状态不明，N为信贷已经取消，国结不能进行后续业务 */
	private String creditFlag ;

	/**
	 * 汇款，收汇，付汇过帐帐号
	 */
	private String midAcct;
	
	/**第一授权人**/
	private int auth1;
	
	/**第二授权人**/
	private int auth2;

	/** 是否发境内外币报文* */
	private String isfxcc = "N";
	
	/**实时清算业务编号,实时清算编号用途改为是否需要写入SWIFT对账数据(账务报文清分交易作判断)*/
    private String reckoningBizNo = null;
    
    /**影像扫描编号 SRCB-ADD,单据扫描发起时赋值 add by LJ*/
	private String picNo;
    
	/**资金拆借几个交易共用同一个tradeno无法发起对应的任务，该字段用来区别是哪种类型的业务20120621*/
	private String scantradeflag;
    /**境内非居民**/
	private String isjnfjm;
	
	/**是否资本报送36号文， 当为Y时，表示需要报送 20131106*/
    private String zbbsFlag;
	
    public String getPicNo() {
		return picNo;
	}

	public void setPicNo(String picNo) {
		this.picNo = picNo;
	}
    
	public String getIsfxcc() {
		return isfxcc;
	}

	public void setIsfxcc(String isfxcc) {
		this.isfxcc = isfxcc;
	}

	public String getMidAcct() {
		return midAcct;
	}

	public void setMidAcct(String midAcct) {
		this.midAcct = midAcct;
	}

	public String getCreditFlag() {
		return creditFlag;
	}

	public void setCreditFlag(String creditFlag) {
		this.creditFlag = creditFlag;
	}

	public String getHandleAgainModify() {
		return handleAgainModify;
	}

	public void setHandleAgainModify(String handleAgainModify) {
		this.handleAgainModify = handleAgainModify;
	}

	public String getCheckedModify() {
		return checkedModify;
	}

	public void setCheckedModify(String checkedModify) {
		this.checkedModify = checkedModify;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getFaxNote() {
		return faxNote;
	}

	public void setFaxNote(String faxNote) {
		this.faxNote = faxNote;
	}

	public Timestamp getAccountDate() {
		return accountDate;
	}

	public void setAccountDate(Timestamp accountDate) {
		this.accountDate = accountDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getBelongOrgNo() {
		return belongOrgNo;
	}

	public void setBelongOrgNo(String belongOrgNo) {
		this.belongOrgNo = belongOrgNo;
	}

	public String getCurrentBizNo() {
		return currentBizNo;
	}

	public void setCurrentBizNo(String currentBizNo) {
		this.currentBizNo = currentBizNo;
	}

	public String getCurSign() {
		return curSign;
	}

	public void setCurSign(String curSign) {
		this.curSign = curSign;
	}

	public Timestamp getDeclareDate() {
		return declareDate;
	}

	public void setDeclareDate(Timestamp declareDate) {
		this.declareDate = declareDate;
	}

	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}

	public Timestamp getFinishedDate() {
		return finishedDate;
	}

	public void setFinishedDate(Timestamp finishedDate) {
		this.finishedDate = finishedDate;
	}

	public Date getInteretDate() {
		return interetDate;
	}

	public void setInteretDate(Date interetDate) {
		this.interetDate = interetDate;
	}

	public String getIsNeedAccount() {
		return isNeedAccount;
	}

	public void setIsNeedAccount(String isNeedAccount) {
		this.isNeedAccount = isNeedAccount;
	}

	public String getIsNeedDeclare() {
		return isNeedDeclare;
	}

	public void setIsNeedDeclare(String isNeedDeclare) {
		this.isNeedDeclare = isNeedDeclare;
	}

	public String getIsNeedSwf() {
		return isNeedSwf;
	}

	public void setIsNeedSwf(String isNeedSwf) {
		this.isNeedSwf = isNeedSwf;
	}

	public String getTranState() {
		return tranState;
	}

	public void setTranState(String isValid) {
		this.tranState = isValid;
	}

	public Timestamp getLaunchDate() {
		return launchDate;
	}

	public void setLaunchDate(Timestamp launchDate) {
		this.launchDate = launchDate;
	}

	public String getLaunchModeNo() {
		return launchModeNo;
	}

	public void setLaunchModeNo(String launchModeNo) {
		this.launchModeNo = launchModeNo;
	}

	public String getPrimaryBizNo() {
		return primaryBizNo;
	}

	public void setPrimaryBizNo(String primaryBizNo) {
		this.primaryBizNo = primaryBizNo;
	}

	public long getProcessNo() {
		return processNo;
	}

	public void setProcessNo(long processNo) {
		this.processNo = processNo;
	}

	public Timestamp getReverseEntryDate() {
		return reverseEntryDate;
	}

	public void setReverseEntryDate(Timestamp reverseEntryDate) {
		this.reverseEntryDate = reverseEntryDate;
	}

	public String getSecondaryBizNo() {
		return secondaryBizNo;
	}

	public void setSecondaryBizNo(String secondaryBizNo) {
		this.secondaryBizNo = secondaryBizNo;
	}

	public Timestamp getSwfDate() {
		return swfDate;
	}

	public void setSwfDate(Timestamp swfDate) {
		this.swfDate = swfDate;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getTransactOrgNo() {
		return transactOrgNo;
	}

	public void setTransactOrgNo(String transactOrgNo) {
		this.transactOrgNo = transactOrgNo;
	}

	public String getTxnSerialNo() {
		return txnSerialNo;
	}

	public void setTxnSerialNo(String txnSerialNo) {
		this.txnSerialNo = txnSerialNo;
	}

	public String getCorpNo() {
		return corpNo;
	}

	public void setCorpNo(String corpNo) {
		this.corpNo = corpNo;
	}

	public String getMemo() {
		if(memo==null)memo="";
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * @return Returns the checkOperId.
	 */
	public int getCheckOperId() {
		return checkOperId;
	}

	/**
	 * @param checkOperId
	 *            The checkOperId to set.
	 */
	public void setCheckOperId(int checkOperId) {
		this.checkOperId = checkOperId;
	}

	/**
	 * @return Returns the handleOperId.
	 */
	public int getHandleOperId() {
		return handleOperId;
	}

	/**
	 * @param handleOperId
	 *            The handleOperId to set.
	 */
	public void setHandleOperId(int handleOperId) {
		this.handleOperId = handleOperId;
	}

	public String getDebtNo() {
		return debtNo;
	}

	public void setDebtNo(String debtNo) {
		this.debtNo = debtNo;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getFaxType() {
		return faxType;
	}

	public void setFaxType(String faxType) {
		this.faxType = faxType;
	}

	public String getFaxNo() {
		return faxNo;
	}

	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	public String getReckoningBizNo() {
		return reckoningBizNo;
	}

	public void setReckoningBizNo(String reckoningBizNo) {
		this.reckoningBizNo = reckoningBizNo;
	}

	public int getAuth1() {
		return auth1;
	}

	public void setAuth1(int auth1) {
		this.auth1 = auth1;
	}

	public int getAuth2() {
		return auth2;
	}

	public void setAuth2(int auth2) {
		this.auth2 = auth2;
	}

	public String getScantradeflag() {
		return scantradeflag;
	}

	public void setScantradeflag(String scantradeflag) {
		this.scantradeflag = scantradeflag;
	}

	public String getIsjnfjm() {
		return isjnfjm;
	}

	public void setIsjnfjm(String isjnfjm) {
		this.isjnfjm = isjnfjm;
	}

	public String getZbbsFlag() {
		return zbbsFlag;
	}

	public void setZbbsFlag(String zbbsFlag) {
		this.zbbsFlag = zbbsFlag;
	}
	

}
