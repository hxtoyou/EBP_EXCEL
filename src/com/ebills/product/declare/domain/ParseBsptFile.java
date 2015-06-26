package com.ebills.product.declare.domain;


import java.math.BigDecimal;
import java.sql.Date;

import com.ebills.product.declare.Entity;


/**
 * 采集数据文件解析
 * 数据库表 BU_PARSEBSPTFILE
 * @author twteam
 *
 */
public class ParseBsptFile extends Entity {
	/**业务金额*/
    private BigDecimal amount;
    /**业务币种*/
    private String cursign; 
    /**数据来源*/   
    private String dataSources;
    /**数据类型*/
    private String dataType;
	/**数据编号*/   
    private String fileInfoNo;
    /**文件名称*/
    private String fileName;
    /**文件类型*/
    private String fileType;
    /**是否解析*/   
    private String isParse;
    /**采集机构*/
    private String orgNo;
    /**解析时间*/
    private Date parseDate;
    /**解析备注*/
    private String remark1;
    /**数据名称*/
	private String remark2;
	private String remark3;
	private String remark4;
	/**外围文件传送的唯一业务编号*/
    private String ywbh;
    
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getCursign() {
		return cursign;
	}
	public void setCursign(String cursign) {
		this.cursign = cursign;
	}
	public String getDataSources() {
		return dataSources;
	}
	public void setDataSources(String dataSources) {
		this.dataSources = dataSources;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getFileInfoNo() {
		return fileInfoNo;
	}
	public void setFileInfoNo(String fileInfoNo) {
		this.fileInfoNo = fileInfoNo;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getIsParse() {
		return isParse;
	}
	public void setIsParse(String isParse) {
		this.isParse = isParse;
	}
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	public Date getParseDate() {
		return parseDate;
	}
	public void setParseDate(Date parseDate) {
		this.parseDate = parseDate;
	}
	public String getRemark1() {
		return remark1;
	}
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	public String getRemark2() {
		return remark2;
	}
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	public String getRemark3() {
		return remark3;
	}
	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}
	public String getRemark4() {
		return remark4;
	}
	public void setRemark4(String remark4) {
		this.remark4 = remark4;
	}
	public String getYwbh() {
		return ywbh;
	}
	public void setYwbh(String ywbh) {
		this.ywbh = ywbh;
	}
}