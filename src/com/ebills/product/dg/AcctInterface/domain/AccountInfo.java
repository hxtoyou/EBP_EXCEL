package com.ebills.product.dg.AcctInterface.domain;
import java.sql.Date;

/**
 * @author majiangbao
 * 
 * 
 */
public class AccountInfo {
	/** 记账记录编号 */
	private String acctInfoNo = null;

	/** 交易流水号 */
	private String txnSerialNo = null;

	/** 资金来源金额 */
	private double fromAmt = 0;

	/** 资金来源币种 */
	private String fromCurSign = null;

	/** 资金来源账号 */
	private String fromAcctNo = null;

	/** 账户类型编号(来源) */
	private String fromAcctTypeNo = null;

	/** 来源账号所属机构 */
	private String fromOrgNo = null;

	/** 资金来源账号对应科目 */
	private String fromAcctSubject = null;

	/** 入账金额 */
	private double toAmt = 0;

	/** 入账金额币种 */
	private String toCurSign = null;

	/** 入账账号 */
	private String toAcctNo = null;

	/** 账户类型编号(入帐) */
	private String toAcctTypeNo = null;

	/** 入帐帐号所属机构 */
	private String toOrgNo = null;

	/** 入账账号对应科目 */
	private String toAcctSubject = null;

	/** 结售汇套汇科目 */
	private String SPFESubject = null;

	/** 结售汇种类 */
	private String SPFECode = null;

	/** 实际汇率 */
	private double SPFERate = 0;

	/** 系统汇率 */
	private double SYSRate = 0;

	/** 结汇汇率 */
	private double SFERERate = 0;

	/** 售汇汇率 */
	private double PFERERate = 0;

	/** 出票日期 */
	private Date remitDate = null;

	/** 凭证种类ID */
	private String warrantType = null;

	/** 凭证号码 */
	private String warrantCode = null;

	/** 支付密码 */
	private String warrantPwd = null;

	/** 暂收金额 */
	private double tmpAmt = 0;

	/** 是否入账暂收 */
	private String isFromTmpAcct = "0";

	/** 是否出账暂收 */
	private String isToTmpAcct = "0";

	/** 组号 */
	private String groupNo = null;

	/** 备注 */
	private String memo = null;

	/** 标志 */
	private String IOFlag = null;

	/** 钞汇标志(1-钞;2-汇) */
	private String isOofAcct = "2";
	/**csAmt**/
	private double csAmt;	
	/**优惠点数*/
	private double favPoint;
	/**发生结售汇时的中间价*/
	private double jshZjj;
	
	public double getJshZjj() {
		return jshZjj;
	}

	public void setJshZjj(double jshZjj) {
		this.jshZjj = jshZjj;
	}

	public double getFavPoint() {
		return favPoint;
	}

	public void setFavPoint(double favPoint) {
		this.favPoint = favPoint;
	}

	public double getCsAmt() {
		return csAmt;
	}

	public void setCsAmt(double csAmt) {
		this.csAmt = csAmt;
	}

	public String getIsOofAcct() {
		return isOofAcct;
	}

	public void setIsOofAcct(String isOofAcct) {
		this.isOofAcct = isOofAcct;
	}

	public String getAcctInfoNo() {
		return acctInfoNo;
	}

	public void setAcctInfoNo(String acctInfoNo) {
		this.acctInfoNo = acctInfoNo;
	}

	public String getFromAcctSubject() {
		return fromAcctSubject;
	}

	public void setFromAcctSubject(String fromAcctSubject) {
		this.fromAcctSubject = fromAcctSubject;
	}

	public String getFromAcctTypeNo() {
		return fromAcctTypeNo;
	}

	public void setFromAcctTypeNo(String fromAcctTypeNo) {
		this.fromAcctTypeNo = fromAcctTypeNo;
	}

	public String getFromOrgNo() {
		return fromOrgNo;
	}

	public void setFromOrgNo(String fromOrgNo) {
		this.fromOrgNo = fromOrgNo;
	}

	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public String getIOFlag() {
		return IOFlag;
	}

	public void setIOFlag(String flag) {
		IOFlag = flag;
	}

	public String getIsFromTmpAcct() {
		return isFromTmpAcct;
	}

	public void setIsFromTmpAcct(String isFromTmpAcct) {
		this.isFromTmpAcct = isFromTmpAcct;
	}

	public String getIsToTmpAcct() {
		return isToTmpAcct;
	}

	public void setIsToTmpAcct(String isToTmpAcct) {
		this.isToTmpAcct = isToTmpAcct;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getWarrantPwd() {
		return warrantPwd;
	}

	public void setWarrantPwd(String password) {
		this.warrantPwd = password;
	}

	public double getPFERERate() {
		return PFERERate;
	}

	public void setPFERERate(double rate) {
		PFERERate = rate;
	}

	public double getSFERERate() {
		return SFERERate;
	}

	public void setSFERERate(double rate) {
		SFERERate = rate;
	}

	public String getSPFECode() {
		return SPFECode;
	}

	public void setSPFECode(String code) {
		SPFECode = code;
	}

	public double getSPFERate() {
		return SPFERate;
	}

	public void setSPFERate(double rate) {
		SPFERate = rate;
	}

	public String getSPFESubject() {
		return SPFESubject;
	}

	public void setSPFESubject(String subject) {
		SPFESubject = subject;
	}

	public double getSYSRate() {
		return SYSRate;
	}

	public void setSYSRate(double rate) {
		SYSRate = rate;
	}

	public double getTmpAmt() {
		return tmpAmt;
	}

	public void setTmpAmt(double tmpAmt) {
		this.tmpAmt = tmpAmt;
	}

	public String getToAcctSubject() {
		return toAcctSubject;
	}

	public void setToAcctSubject(String toAcctSubject) {
		this.toAcctSubject = toAcctSubject;
	}

	public String getToAcctTypeNo() {
		return toAcctTypeNo;
	}

	public void setToAcctTypeNo(String toAcctTypeNo) {
		this.toAcctTypeNo = toAcctTypeNo;
	}

	public String getToOrgNo() {
		return toOrgNo;
	}

	public void setToOrgNo(String toOrgNo) {
		this.toOrgNo = toOrgNo;
	}

	public String getFromAcctNo() {
		return fromAcctNo;
	}

	public void setFromAcctNo(String transferFromAcct) {
		this.fromAcctNo = transferFromAcct;
	}

	public double getFromAmt() {
		return fromAmt;
	}

	public void setFromAmt(double transferFromAmt) {
		this.fromAmt = transferFromAmt;
	}

	public String getFromCurSign() {
		return fromCurSign;
	}

	public void setFromCurSign(String transferFromCurSign) {
		this.fromCurSign = transferFromCurSign;
	}

	public String getToAcctNo() {
		return toAcctNo;
	}

	public void setToAcctNo(String transferToAcct) {
		this.toAcctNo = transferToAcct;
	}

	public double getToAmt() {
		return toAmt;
	}

	public void setToAmt(double transferToAmt) {
		this.toAmt = transferToAmt;
	}

	public String getToCurSign() {
		return toCurSign;
	}

	public void setToCurSign(String transferToCurSign) {
		this.toCurSign = transferToCurSign;
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

	public String getWarrantType() {
		return warrantType;
	}

	public void setWarrantType(String warrantType) {
		this.warrantType = warrantType;
	}

	public Date getRemitDate() {
		return remitDate;
	}

	public void setRemitDate(Date remitDate) {
		this.remitDate = remitDate;
	}

}