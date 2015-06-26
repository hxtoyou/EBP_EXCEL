package com.ebills.product.dg.AcctInterface;

/**
 * 东莞公共报文头
 * @return
 * @throws Exception
 */
public class CommXmlHead {
	private String eciSeverId;//ECI服务接口ID
	private String xmlflag;//报文标识
	private String templateCodeName;//模板名称
	private String transCode;//交易代码
	private String sysId;//系统标识
	private String channelCode;//渠道分类
	private String subchannelCode;//渠道标识
	private String tradeFlag;//是否需要勾兑
	private String checkFlag;//是否需要判重
	private String channelserno;//渠道分类流水号
	private String sessid;//会话标识
	private String prcscd;//渠道处理码
	private String zoneno;//操作分行
	private String mbrno;//操作支行
	private String brno;//操作网点
	private String tellerno;//操作柜员
	private String tellertp;//操作类别
	private String csbxno;//柜员钱箱号
	private String dutytp;//岗位类型
	private String dutyno;//实体岗位编号
	private String authno;//授权员
	private String authpw;//授权员密码
	private String authmg;//授权员指纹
	private String authce;//授权员验证类型
	private String authmanfttype;//授权员指纹厂商
	private String replyquery;//请求应答队列名
	public String getEciSeverId() {
		return eciSeverId;
	}
	public void setEciSeverId(String eciSeverId) {
		this.eciSeverId = eciSeverId;
	}
	public String getXmlflag() {
		return xmlflag;
	}
	public void setXmlflag(String xmlflag) {
		this.xmlflag = xmlflag;
	}
	public String getTemplateCodeName() {
		return templateCodeName;
	}
	public void setTemplateCodeName(String templateCodeName) {
		this.templateCodeName = templateCodeName;
	}
	public String getTransCode() {
		return transCode;
	}
	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
	public String getSysId() {
		return sysId;
	}
	public void setSysId(String sysId) {
		this.sysId = sysId;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public String getSubchannelCode() {
		return subchannelCode;
	}
	public void setSubchannelCode(String subchannelCode) {
		this.subchannelCode = subchannelCode;
	}
	public String getTradeFlag() {
		return tradeFlag;
	}
	public void setTradeFlag(String tradeFlag) {
		this.tradeFlag = tradeFlag;
	}
	public String getCheckFlag() {
		return checkFlag;
	}
	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}
	public String getChannelserno() {
		return channelserno;
	}
	public void setChannelserno(String channelserno) {
		this.channelserno = channelserno;
	}
	public String getSessid() {
		return sessid;
	}
	public void setSessid(String sessid) {
		this.sessid = sessid;
	}
	public String getPrcscd() {
		return prcscd;
	}
	public void setPrcscd(String prcscd) {
		this.prcscd = prcscd;
	}
	public String getZoneno() {
		return zoneno;
	}
	public void setZoneno(String zoneno) {
		this.zoneno = zoneno;
	}
	public String getMbrno() {
		return mbrno;
	}
	public void setMbrno(String mbrno) {
		this.mbrno = mbrno;
	}
	public String getBrno() {
		return brno;
	}
	public void setBrno(String brno) {
		this.brno = brno;
	}
	public String getTellerno() {
		return tellerno;
	}
	public void setTellerno(String tellerno) {
		this.tellerno = tellerno;
	}
	public String getTellertp() {
		return tellertp;
	}
	public void setTellertp(String tellertp) {
		this.tellertp = tellertp;
	}
	public String getCsbxno() {
		return csbxno;
	}
	public void setCsbxno(String csbxno) {
		this.csbxno = csbxno;
	}
	public String getDutytp() {
		return dutytp;
	}
	public void setDutytp(String dutytp) {
		this.dutytp = dutytp;
	}
	public String getDutyno() {
		return dutyno;
	}
	public void setDutyno(String dutyno) {
		this.dutyno = dutyno;
	}
	public String getAuthno() {
		return authno;
	}
	public void setAuthno(String authno) {
		this.authno = authno;
	}
	public String getAuthpw() {
		return authpw;
	}
	public void setAuthpw(String authpw) {
		this.authpw = authpw;
	}
	public String getAuthmg() {
		return authmg;
	}
	public void setAuthmg(String authmg) {
		this.authmg = authmg;
	}
	public String getAuthce() {
		return authce;
	}
	public void setAuthce(String authce) {
		this.authce = authce;
	}
	public String getAuthmanfttype() {
		return authmanfttype;
	}
	public void setAuthmanfttype(String authmanfttype) {
		this.authmanfttype = authmanfttype;
	}
	public String getReplyquery() {
		return replyquery;
	}
	public void setReplyquery(String replyquery) {
		this.replyquery = replyquery;
	}

	
	
}
