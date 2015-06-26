package com.ebills.product.dg.AcctInterface.domain;


import java.sql.Date;

public class AcctList {

	/**交易流水号*/
    private String txnSerialNo = null;
    /**业务编号*/
    private String currentBizNo = null;
    /**交易类型编号*/
    private String tradeNo = null;
    /**序号（一组分录中的序号）*/
    private int groupNo ;
    /**序号（一组分录中的序号）*/
    private int serialNo ;
    /**借贷标志（D借 C贷）*/
    private String DCFlag = null;
    /**账号*/
    private String acctNo = null;
    /**账号所属科目*/
    private String acctSubject = null;
    /**记账币种*/
    private String curSign = null;
    /**记账金额*/
    private double amount;
    /**牌价*/
    private double SPFERate ;
    /**账务种类编号*/
    private String acctTypeNo = null;
    /**账务种类*/
    private String acctTypeName = null;
    /**账号所属机构*/
    private String acctOrgNo = null;
    /**对方科目*/
    private String toAcctSubject = null;
    /**出票日期*/
    private String remitDate = null;
    /**凭证号 注意：烟台该字段做为摘要栏使用*/
    private String warrantCode = null;
    /**凭证种类*/
    private String warrantType = null;
    /**支付密码*/
    private String warrantPwd = null;
    /**0－表内1－表外0*/
    private String ioFlag = null;
    /**核心机构*/
    private String mainAcctOrgNo = null;
    /**钞汇标识*/
    private String isOofAcct = null;
    /**买入红蓝字标志1=正常,2-红字,3-蓝字*/
    private String rbFlag = "1";
    /**记帐标志1=正常,2-冲帐 3-不记帐 4-代表已被冲帐的正常交易*/
    private String tallyFlag = "1";
    /**是否是过渡分录(系统自动匹配增加的分录,如结售汇) Y OR N */
    private String isAddi = "N";
    //核心组号，现改为是否原始记帐中的借贷方，便于分组处理,控制分录排列顺序
    private String mainGroupNo;
    //**大总账要求折算金额*/
    private double reportAmt =0;
    
    /*20130516新增下列字段*/
    /**核心流水号*/
    private String coreSerNo=null;
    /**记账日期*/
    private Date accountDate = null;
    /**国业0－表内1－表外0*/
    private String ibIoFlag = null;
    
    private String dxzxh;//数据库原有此字段，现启用做为判断交易类别，用于通用记帐中的结售汇或平盘交易 2013-6-13 LT
    
    private String note;//20130614临商银行专用字段，此字段为拼凑字段，包含多条分录的值和金额，具体组装按照联盟对系统内平盘的需求拼凑。
    
	public String getDxzxh() {
		return dxzxh;
	}
	public void setDxzxh(String dxzxh) {
		this.dxzxh = dxzxh;
	}
	public String getAcctNo() {
		return acctNo;
	}
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	public String getAcctOrgNo() {
		return acctOrgNo;
	}
	public void setAcctOrgNo(String acctOrgNo) {
		this.acctOrgNo = acctOrgNo;
	}
	public String getAcctSubject() {
		return acctSubject;
	}
	public void setAcctSubject(String acctSubject) {
		this.acctSubject = acctSubject;
	}
	public String getAcctTypeName() {
		return acctTypeName;
	}
	public void setAcctTypeName(String acctTypeName) {
		this.acctTypeName = acctTypeName;
	}
	public String getAcctTypeNo() {
		return acctTypeNo;
	}
	public void setAcctTypeNo(String acctTypeNo) {
		this.acctTypeNo = acctTypeNo;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
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
	public String getDCFlag() {
		return DCFlag;
	}
	public void setDCFlag(String flag) {
		DCFlag = flag;
	}
	public int getGroupNo() {
		return groupNo;
	}
	public void setGroupNo(int groupNo) {
		this.groupNo = groupNo;
	}
	public String getIoFlag() {
		return ioFlag;
	}
	public void setIoFlag(String ioFlag) {
		this.ioFlag = ioFlag;
	}
	public String getIsAddi() {
		return isAddi;
	}
	public void setIsAddi(String isAddi) {
		this.isAddi = isAddi;
	}
	public String getIsOofAcct() {
		return isOofAcct;
	}
	public void setIsOofAcct(String isOofAcct) {
		this.isOofAcct = isOofAcct;
	}
	public String getMainAcctOrgNo() {
		return mainAcctOrgNo;
	}
	public void setMainAcctOrgNo(String mainAcctOrgNo) {
		this.mainAcctOrgNo = mainAcctOrgNo;
	}
	public String getRbFlag() {
		return rbFlag;
	}
	public void setRbFlag(String rbFlag) {
		this.rbFlag = rbFlag;
	}
	public String getRemitDate() {
		return remitDate;
	}
	public void setRemitDate(String remitDate) {
		this.remitDate = remitDate;
	}
	public int getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}
	public double getSPFERate() {
		return SPFERate;
	}
	public void setSPFERate(double rate) {
		SPFERate = rate;
	}
	public String getTallyFlag() {
		return tallyFlag;
	}
	public void setTallyFlag(String tallyFlag) {
		this.tallyFlag = tallyFlag;
	}
	public String getToAcctSubject() {
		return toAcctSubject;
	}
	public void setToAcctSubject(String toAcctSubject) {
		this.toAcctSubject = toAcctSubject;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getTxnSerialNo() {
		return txnSerialNo;
	}
	public void setTxnSerialNo(String txnSerialNo) {
		this.txnSerialNo = txnSerialNo;
	}
	public String getWarrantCode() {
		return warrantCode;
	}
	public void setWarrantCode(String warrantCode) {
		this.warrantCode = warrantCode;
	}
	public String getWarrantPwd() {
		return warrantPwd;
	}
	public void setWarrantPwd(String warrantPwd) {
		this.warrantPwd = warrantPwd;
	}
	public String getWarrantType() {
		return warrantType;
	}
	public void setWarrantType(String warrantType) {
		this.warrantType = warrantType;
	}
	public String getMainGroupNo() {
		return mainGroupNo;
	}
	public void setMainGroupNo(String mainGroupNo) {
		this.mainGroupNo = mainGroupNo;
	}
	public double getReportAmt() {
		return reportAmt;
	}
	public void setReportAmt(double reportAmt) {
		this.reportAmt = reportAmt;
	}
	public Date getAccountDate() {
		return accountDate;
	}
	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}
	public String getCoreSerNo() {
		return coreSerNo;
	}
	public void setCoreSerNo(String coreSerNo) {
		this.coreSerNo = coreSerNo;
	}
	public String getIbIoFlag() {
		return ibIoFlag;
	}
	public void setIbIoFlag(String ibIoFlag) {
		this.ibIoFlag = ibIoFlag;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}    
	
	
}
