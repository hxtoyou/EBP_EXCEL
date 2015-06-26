package com.ebills.product.dg.AcctInterface;

/**
 * 2014-06-14 LT
 * 定义记帐类公共对象
 * @return
 * @throws Exception
 */
public class AcctUtilDataInfo {
	private String tradeNo;//交易类型号
	private String tradOrgNo;//执行机构号
	private String curtBizNo;//当前业务号
	private String preBizNo;//主业务号
	private String handlerId;//经办人
	private String checkerId;//复核人
	private String userTallyId;//核心虚拟记帐柜员
	private String orgCode;//核心机构号
	private String corpType;//客户类型CORPTYPE
	
	public String getCorpType() {
		return corpType;
	}
	public void setCorpType(String corpType) {
		this.corpType = corpType;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getTradOrgNo() {
		return tradOrgNo;
	}
	public void setTradOrgNo(String tradOrgNo) {
		this.tradOrgNo = tradOrgNo;
	}
	public String getCurtBizNo() {
		return curtBizNo;
	}
	public void setCurtBizNo(String curtBizNo) {
		this.curtBizNo = curtBizNo;
	}
	public String getPreBizNo() {
		return preBizNo;
	}
	public void setPreBizNo(String preBizNo) {
		this.preBizNo = preBizNo;
	}
	public String getHandlerId() {
		return handlerId;
	}
	public void setHandlerId(String handlerId) {
		this.handlerId = handlerId;
	}
	public String getCheckerId() {
		return checkerId;
	}
	public void setCheckerId(String checkerId) {
		this.checkerId = checkerId;
	}
	public String getUserTallyId() {
		return userTallyId;
	}
	public void setUserTallyId(String userTallyId) {
		this.userTallyId = userTallyId;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

}
