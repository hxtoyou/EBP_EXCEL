package com.ebills.product.dg.AcctInterface.domain;

public class OrgAcct {

		/** 帐务对象(0表示公司,1表示帐户银行,2表示机构,3表示私人) */
		private String acctObject;

		/** 逻辑ID* */
		private String orgAcctNo;

		/** 帐务类型* */
		private String acctTypeNo;

		/** 账户* */
		private String acctNo;

		/** 币种* */
		private String curSign;

		/** 帐务类型所属机构* */
		private String acctTypeOrgNo;

		/** 对方币种* */
		private String otherCurSign;

		/** 对方机构* */
		private String otherOrgNo;

		/** 客户编号/银行编号* */
		private String custBankNo;

		/** 使用频度* */
		private double frequency;

		public String getAcctNo() {
			return acctNo;
		}

		public void setAcctNo(String acctNo) {
			this.acctNo = acctNo;
		}

		public String getAcctTypeNo() {
			return acctTypeNo;
		}

		public void setAcctTypeNo(String acctTypeNo) {
			this.acctTypeNo = acctTypeNo;
		}

		public String getCurSign() {
			return curSign;
		}

		public void setCurSign(String curSign) {
			this.curSign = curSign;
		}

		public String getCustBankNo() {
			return custBankNo;
		}

		public void setCustBankNo(String custBankNo) {
			this.custBankNo = custBankNo;
		}

		public double getFrequency() {
			return frequency;
		}

		public void setFrequency(double frequency) {
			this.frequency = frequency;
		}

		public String getOrgAcctNo() {
			return orgAcctNo;
		}

		public void setOrgAcctNo(String orgAcctNo) {
			this.orgAcctNo = orgAcctNo;
		}

		public String getAcctTypeOrgNo() {
			return acctTypeOrgNo;
		}

		public void setAcctTypeOrgNo(String acctTypeOrgNo) {
			this.acctTypeOrgNo = acctTypeOrgNo;
		}

		public String getOtherCurSign() {
			return otherCurSign;
		}

		public void setOtherCurSign(String otherCurSign) {
			this.otherCurSign = otherCurSign;
		}

		public String getOtherOrgNo() {
			return otherOrgNo;
		}

		public void setOtherOrgNo(String otherOrgNo) {
			this.otherOrgNo = otherOrgNo;
		}

		public String getAcctObject() {
			return acctObject;
		}

		public void setAcctObject(String acctObject) {
			this.acctObject = acctObject;
		}
		
		public void reset(){
			this.acctNo = null;
			this.acctObject = null;
			this.acctTypeNo = null;
			this.acctTypeOrgNo = null;
			this.curSign = null;
			this.custBankNo = null;
			this.frequency = 0.0;
			this.orgAcctNo = null;
			this.otherCurSign = null;
			this.otherOrgNo = null;
		}

	

}
