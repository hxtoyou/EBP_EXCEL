package com.ebills.product.dg.AcctInterface.domain;

public class Org  {
	
    /** 机构编号* */
    private String orgNo;

    /** 网点全称* */
    private String orgName;

    /** 机构简称* */
    private String shortName;

    /** 上级机构* */
    private String parentOrgNo;

    /** 机构级别* */
    private String orgRank;

    /** SWIFT号码* */
    private String swiftCode;

    /** 地址* */
    private String address;

    /** 电话* */
    private String telephone;

    /** 联系人* */
    private String linkMan;

    /** 传真电话* */
    private String faxPhone;

    /** 网点英文名称* */
    private String enName;

    /** 英文地址* */
    private String enAddress;

    /** UID号码* */
    private String uidCode;

    /** 银行编号* */
    private String bankNo;

    /** 所属单证中心* */
    // private String billCenterNo;
    /** 行所号* */
    private String orgCode;

    /** 是否回单直接打印* */
    private String isPrint;

    /** 是否有外汇权限* */
    private String foreignPopedom;

    /** 是否打印核销联* */
    private String isPrintHXL;

    /** 是否要寄单* */
    private String isSendEbill;

    /** 是否要传真* */
    private String isFax;

    /** 所属地区* */
    private String areaId;

    /** 是否是业务处理机构* */
    private String isDisposalOrg;
    
    
    /**网点号:不能超过10位*/
    private String netNo;
    
    /**机构次级别：分为A,B,C*/
    private String lvl;
    
    /**板块*/
    private String areaBlock;

    /**虚拟记帐柜员(本要素改为网银任务打印员,赋USERID值)*/
    private String tallyUser;
    
    /**是否是开户机构*/
    private String isOpenOrg;    

	public String getTallyUser() {
		return tallyUser;
	}

	public void setTallyUser(String tallyUser) {
		this.tallyUser = tallyUser;
	}

	public String getAreaBlock() {
		return areaBlock;
	}

	public void setAreaBlock(String areaBlock) {
		this.areaBlock = areaBlock;
	}

	public String getAddress() {
        return address;
    }

    public String getLvl() {
		return lvl;
	}

	public void setLvl(String lvl) {
		this.lvl = lvl;
	}

	public void setAddress(String address) {
        this.address = address;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getEnAddress() {
        return enAddress;
    }

    public void setEnAddress(String enAddress) {
        this.enAddress = enAddress;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getFaxPhone() {
        return faxPhone;
    }

    public void setFaxPhone(String faxPhone) {
        this.faxPhone = faxPhone;
    }

    public String getForeignPopedom() {
        return foreignPopedom;
    }

    public void setForeignPopedom(String foreignPopedom) {
        this.foreignPopedom = foreignPopedom;
    }

    public String getIsDisposalOrg() {
        return isDisposalOrg;
    }

    public void setIsDisposalOrg(String isDisposalOrg) {
        this.isDisposalOrg = isDisposalOrg;
    }

    public String getIsFax() {
        return isFax;
    }

    public void setIsFax(String isFax) {
        this.isFax = isFax;
    }

    public String getIsPrint() {
        return isPrint;
    }

    public void setIsPrint(String isPrint) {
        this.isPrint = isPrint;
    }

    public String getIsPrintHXL() {
        return isPrintHXL;
    }

    public void setIsPrintHXL(String isPrintHXL) {
        this.isPrintHXL = isPrintHXL;
    }

    public String getIsSendEbill() {
        return isSendEbill;
    }

    public void setIsSendEbill(String isSendEbill) {
        this.isSendEbill = isSendEbill;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    public String getOrgRank() {
        return orgRank;
    }

    public void setOrgRank(String orgRank) {
        this.orgRank = orgRank;
    }

    public String getParentOrgNo() {
        return parentOrgNo;
    }

    public void setParentOrgNo(String parentOrgNo) {
        this.parentOrgNo = parentOrgNo;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUidCode() {
        return uidCode;
    }

    public void setUidCode(String uidCode) {
        this.uidCode = uidCode;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

	public String getNetNo() {
		return netNo;
	}

	public void setNetNo(String netNo) {
		this.netNo = netNo;
	}

	public String getIsOpenOrg() {
		return isOpenOrg;
	}

	public void setIsOpenOrg(String isOpenOrg) {
		this.isOpenOrg = isOpenOrg;
	}

}
