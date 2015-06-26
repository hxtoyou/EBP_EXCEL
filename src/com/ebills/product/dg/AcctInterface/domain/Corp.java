package com.ebills.product.dg.AcctInterface.domain;

import java.sql.Date;

public class Corp {
	/** 公司编号* */
	private String corpNo;

	/** 客户类型(0表示公司,1表示私人) */
	private String corpKind;

	/** 中文名称* */
	private String cnName;

	/** 中文地址* */
	private String cnAddr;

	/** 英文名称* */
	private String enName;

	/** 英文地址* */
	private String enAddr;

	/** 公司简称* */
	private String shortName;

	/** 第一外管局注册名称* */
	private String firSAFERegName;

	/** 第二外管局注册名称* */
	private String secSAFERegName;

	/** 所属机构* */
	private String orgNo;

	/** 维护机构 */
	private String servOrgNo;

	/** 公司所属行业（外管用）* */
	private String corpVocation;

	/** 企业属性（外管用）* */
	private String corpAttribute;

	/** 企业所有制性质* */
	private String corpCharacter;

	/** 企业组织类型* */
	private String corpOrgType;

	/** 企业规模* */
	private String corpSize;

	/** 企业类型* */
	private String corpType;

	/** 企业组织结构代码* */
	private String corpOrgCode;

	/** 公司外管局编号* */
	private String SAFERegCode;

	/** 申报联系人* */
	private String declareLinkman;

	
	/**
	 * 证件类型
	 * 101	 	身份证                 
	102		户口簿                 
	103		护照                   
	104		军官证                 
	105		士兵证                 
	106		警官证                 
	111		港澳居民来往内地通行证 
	112		台湾同胞来往内地通行证 
	113		外国人居留证           
	120		社会保障证             
	121		个人纳税证             
	199		个人其它证件           
	201		注册登记证             
	202		营业执照     S          
	203		企业法人代码 P          
	301		外貿许可证             
	302		开户许可证             
	303		特种行业许可证         
	304		外汇经营许可证         
	305		金融经营许可证         
	401		国税登记证             
	402		地税登记证             
	501		批文                   
	511		贷款证
	999		公司其他证件  */
	
	private String certificateType;

	/** 联系人证件号 */
	private String idCard;

	/** 申报联系人电话* */
	private String declareTel;

	/** 注册日期* */
	private Date registerDate;

	/** 注册资金* */
	private double registerFund;

	/** 公司电话* */
	private String telephone;

	/** 公司邮编* */
	private String postalCode;

	/** 公司传真* */
	private String fax;

	/** 公司法人代表* */
	private String corpDelegate;

	/** email* */
	private String email;

	/** 公司网址* */
	private String webSite;

	/** 电话查询密码* */
	private String teleQueryPW;

	/** 总公司中文名称* */
	private String cnChiefName;

	/** 总公司中文地址* */
	private String cnChiefAddr;

	/** 总公司英文名称* */
	private String enChiefName;

	/** 总公司英文地址* */
	private String enChiefAddr;

	/** 是否使用总公司* */
	private String isChief;

	/** 进出口权限 */
	private String hasImExRight;

	/** 网上查询密码* */
	private String netQueryPW;

	/** 备注 */
	private String memo;

	/** 是否收取额度 */
	private String isLimit;

	/** 保证金占额度的比例 */
	private double perDeposit;

	/** 核心系统编号 */
	private String mainCorpNo;

	/** 营业执照号码 */
	private String licNo;

	/** 特殊经济区域 是否特殊经济区内企业,如是选择Y,不是选择N */
	private String SPEACIALECONOMICZONE;

	/** 停用标志 两种选择：Y-可以使用N-停止使用 */
	private String isStop;

	/** 常驻国家代码 常驻国家代码(PA_COUNTRY 中的国家编号) */
	private String COUNTRYCODE;

	/** 是否有不良贷 是否有不良贷款(Y/N 默认为N) */
	private String ISBADLENDING;

	/** 是否需传真 */
	private String isFax;

	/**开户行名称*/
	private String createBank;
	
	/**交易用EMAIL*/
	private String tradeEmail;
	
	/**申报方式*/
	private String declareMode;
	
	/**特殊经济区企业类型*/
	private String spEconZoneCorpType;
	
	/**住所/营业场所代码*/
	private String buSplaceCode;
	
	/**外方投资者国别列表*/
	private String investCountryCode;
	
	/**国家行业分类代码*/
	private String industryTypeCode; 
	
	/**是否在进口单位名录(Y-是 N-否)*/
	private String isImportCorp;
	
	/**是否已申报(Y-是 N-否)*/
	private String isDeclare;
	
	/**是否关注企业(默认为'N')**/
	private String isAttention;
	
	//哈尔滨增加
	/**手机号码**/
	private String mobileNo;
	/**是否短消息通知(默认为'N')**/
	private String isMessage;
	
	/**是否能允许网银发起*/
	private String isNetBank;
	
	/** 网银客户经理*/
	private String netManagerId;
	
    /**手续费用是否要过待核查*/
    private String isFyHc;
	
	public Corp(){
		COUNTRYCODE="156";
		isAttention="N";
		isImportCorp="N";
		isMessage="N";
		corpType="03";
		buSplaceCode="";
		isNetBank = "N";
	}
	
	public String getIsMessage() {
		return isMessage;
	}

	public void setIsMessage(String isMessage) {
		this.isMessage = isMessage;
	}
	

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getIsAttention() {
		return isAttention;
	}

	public void setIsAttention(String isAttention) {
		this.isAttention = isAttention;
	}

	public String getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}

	public String getCnAddr() {
		return cnAddr;
	}

	public void setCnAddr(String cnAddr) {
		this.cnAddr = cnAddr;
	}

	public String getCnChiefAddr() {
		return cnChiefAddr;
	}

	public void setCnChiefAddr(String cnChiefAddr) {
		this.cnChiefAddr = cnChiefAddr;
	}

	public String getCnChiefName() {
		return cnChiefName;
	}

	public void setCnChiefName(String cnChiefName) {
		this.cnChiefName = cnChiefName;
	}

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public String getCorpAttribute() {
		return corpAttribute;
	}

	public void setCorpAttribute(String corpAttribute) {
		this.corpAttribute = corpAttribute;
	}

	public String getCorpCharacter() {
		return corpCharacter;
	}

	public void setCorpCharacter(String corpCharacter) {
		this.corpCharacter = corpCharacter;
	}

	public String getCorpDelegate() {
		return corpDelegate;
	}

	public void setCorpDelegate(String corpDelegate) {
		this.corpDelegate = corpDelegate;
	}

	public String getCorpKind() {
		return corpKind;
	}

	public void setCorpKind(String corpKind) {
		this.corpKind = corpKind;
	}

	public String getCorpNo() {
		return corpNo;
	}

	public void setCorpNo(String corpNo) {
		this.corpNo = corpNo;
	}

	public String getCorpOrgCode() {
		return corpOrgCode;
	}

	public void setCorpOrgCode(String corpOrgCode) {
		this.corpOrgCode = corpOrgCode;
	}

	public String getCorpOrgType() {
		return corpOrgType;
	}

	public void setCorpOrgType(String corpOrgType) {
		this.corpOrgType = corpOrgType;
	}

	public String getCorpSize() {
		return corpSize;
	}

	public void setCorpSize(String corpSize) {
		this.corpSize = corpSize;
	}

	public String getCorpType() {
		return corpType;
	}

	public void setCorpType(String corpType) {
		this.corpType = corpType;
	}

	public String getCorpVocation() {
		return corpVocation;
	}

	public void setCorpVocation(String corpVocation) {
		this.corpVocation = corpVocation;
	}

	public String getCOUNTRYCODE() {
		return COUNTRYCODE;
	}

	public void setCOUNTRYCODE(String countrycode) {
		COUNTRYCODE = countrycode;
	}

	public String getDeclareLinkman() {
		return declareLinkman;
	}

	public void setDeclareLinkman(String declareLinkman) {
		this.declareLinkman = declareLinkman;
	}

	public String getDeclareTel() {
		return declareTel;
	}

	public void setDeclareTel(String declareTel) {
		this.declareTel = declareTel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEnAddr() {
		return enAddr;
	}

	public void setEnAddr(String enAddr) {
		this.enAddr = enAddr;
	}

	public String getEnChiefAddr() {
		return enChiefAddr;
	}

	public void setEnChiefAddr(String enChiefAddr) {
		this.enChiefAddr = enChiefAddr;
	}

	public String getEnChiefName() {
		return enChiefName;
	}

	public void setEnChiefName(String enChiefName) {
		this.enChiefName = enChiefName;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getFirSAFERegName() {
		return firSAFERegName;
	}

	public void setFirSAFERegName(String firSAFERegName) {
		this.firSAFERegName = firSAFERegName;
	}

	public String getHasImExRight() {
		return hasImExRight;
	}

	public void setHasImExRight(String hasImExRight) {
		this.hasImExRight = hasImExRight;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getISBADLENDING() {
		return ISBADLENDING;
	}

	public void setISBADLENDING(String isbadlending) {
		ISBADLENDING = isbadlending;
	}

	public String getIsChief() {
		return isChief;
	}

	public void setIsChief(String isChief) {
		this.isChief = isChief;
	}

	public String getIsFax() {
		return isFax;
	}

	public void setIsFax(String isFax) {
		this.isFax = isFax;
	}

	public String getIsLimit() {
		return isLimit;
	}

	public void setIsLimit(String isLimit) {
		this.isLimit = isLimit;
	}

	public String getIsStop() {
		return isStop;
	}

	public void setIsStop(String isStop) {
		this.isStop = isStop;
	}

	public String getLicNo() {
		return licNo;
	}

	public void setLicNo(String licNo) {
		this.licNo = licNo;
	}

	public String getMainCorpNo() {
		return mainCorpNo;
	}

	public void setMainCorpNo(String mainCorpNo) {
		this.mainCorpNo = mainCorpNo;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getNetQueryPW() {
		return netQueryPW;
	}

	public void setNetQueryPW(String netQueryPW) {
		this.netQueryPW = netQueryPW;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public double getPerDeposit() {
		return perDeposit;
	}

	public void setPerDeposit(double perDeposit) {
		this.perDeposit = perDeposit;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public double getRegisterFund() {
		return registerFund;
	}

	public void setRegisterFund(double registerFund) {
		this.registerFund = registerFund;
	}

	public String getSAFERegCode() {
		return SAFERegCode;
	}

	public void setSAFERegCode(String regCode) {
		SAFERegCode = regCode;
	}

	public String getSecSAFERegName() {
		return secSAFERegName;
	}

	public void setSecSAFERegName(String secSAFERegName) {
		this.secSAFERegName = secSAFERegName;
	}

	public String getServOrgNo() {
		return servOrgNo;
	}

	public void setServOrgNo(String servOrgNo) {
		this.servOrgNo = servOrgNo;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getSPEACIALECONOMICZONE() {
		return SPEACIALECONOMICZONE;
	}

	public void setSPEACIALECONOMICZONE(String speacialeconomiczone) {
		SPEACIALECONOMICZONE = speacialeconomiczone;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getTeleQueryPW() {
		return teleQueryPW;
	}

	public void setTeleQueryPW(String teleQueryPW) {
		this.teleQueryPW = teleQueryPW;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	/**
	 * @return Returns the buSplaceCode.
	 */
	public String getBuSplaceCode() {
		return buSplaceCode;
	}

	/**
	 * @param buSplaceCode The buSplaceCode to set.
	 */
	public void setBuSplaceCode(String buSplaceCode) {
		this.buSplaceCode = buSplaceCode;
	}

	/**
	 * @return Returns the createBank.
	 */
	public String getCreateBank() {
		return createBank;
	}

	/**
	 * @param createBank The createBank to set.
	 */
	public void setCreateBank(String createBank) {
		this.createBank = createBank;
	}

	/**
	 * @return Returns the declareMode.
	 */
	public String getDeclareMode() {
		return declareMode;
	}

	/**
	 * @param declareMode The declareMode to set.
	 */
	public void setDeclareMode(String declareMode) {
		this.declareMode = declareMode;
	}

	/**
	 * @return Returns the investCountryCode.
	 */
	public String getInvestCountryCode() {
		return investCountryCode;
	}

	/**
	 * @param investCountryCode The investCountryCode to set.
	 */
	public void setInvestCountryCode(String investCountryCode) {
		this.investCountryCode = investCountryCode;
	}

	/**
	 * @return Returns the spEconZoneCorpType.
	 */
	public String getspEconZoneCorpType() {
		return spEconZoneCorpType;
	}

	/**
	 * @param spEconZoneCorpType The spEconZoneCorpType to set.
	 */
	public void setspEconZoneCorpType(String spEconZoneCorpType) {
		this.spEconZoneCorpType = spEconZoneCorpType;
	}

	/**
	 * @return Returns the tradeEmail.
	 */
	public String getTradeEmail() {
		return tradeEmail;
	}

	/**
	 * @param tradeEmail The tradeEmail to set.
	 */
	public void setTradeEmail(String tradeEmail) {
		this.tradeEmail = tradeEmail;
	}

	/**
	 * @return Returns the industryTypeCode.
	 */
	public String getIndustryTypeCode() {
		return industryTypeCode;
	}

	/**
	 * @return Returns the isDeclare.
	 */
	public String getIsDeclare() {
		return isDeclare;
	}

	/**
	 * @return Returns the isImportCorp.
	 */
	public String getisImportCorp() {
		return isImportCorp;
	}

	/**
	 * @return Returns the spEconZoneCorpType.
	 */
	public String getSpEconZoneCorpType() {
		return spEconZoneCorpType;
	}

	/**
	 * @return Returns the isImportCorp.
	 */
	public String getIsImportCorp() {
		return isImportCorp;
	}

	/**
	 * @param isImportCorp The isImportCorp to set.
	 */
	public void setIsImportCorp(String isImportCorp) {
		this.isImportCorp = isImportCorp;
	}

	/**
	 * @param industryTypeCode The industryTypeCode to set.
	 */
	public void setIndustryTypeCode(String industryTypeCode) {
		this.industryTypeCode = industryTypeCode;
	}

	/**
	 * @param isDeclare The isDeclare to set.
	 */
	public void setIsDeclare(String isDeclare) {
		this.isDeclare = isDeclare;
	}
	
	/**	 
	 * @param spEconZoneCorpType The spEconZoneCorpType to set.
	 */
	public void setSpEconZoneCorpType(String spEconZoneCorpType) {
		this.spEconZoneCorpType = spEconZoneCorpType;
	}

	public String getIsNetBank() {
		return isNetBank;
	}

	public void setIsNetBank(String isNetBank) {
		this.isNetBank = isNetBank;
	}

	public String getNetManagerId() {
		return netManagerId;
	}

	public void setNetManagerId(String netManagerId) {
		this.netManagerId = netManagerId;
	}

	public String getIsFyHc() {
		return isFyHc;
	}

	public void setIsFyHc(String isFyHc) {
		this.isFyHc = isFyHc;
	}    
}



