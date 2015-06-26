package com.ebills.product.declare.domain;


import java.sql.Date;

import com.ebills.product.declare.Entity;


/**
 * 
 * @author twteam
 *
 */
public class BsptFile extends Entity {
 
    /**文件类型*/
    private String fileType;
    /**文件名称*/
    private String fileName;
    /**业务参号*/
    private String refNumber;
    /**发生日期*/
    private Date   dealDate;
    /**是否解析成功*/
    private String isParse;
    
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getRefNumber() {
		return refNumber;
	}
	public void setRefNumber(String refNumber) {
		this.refNumber = refNumber;
	}
	public Date getDealDate() {
		return dealDate;
	}
	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}
	public String getIsParse() {
		return isParse;
	}
	public void setIsParse(String isParse) {
		this.isParse = isParse;
	}
    
}
