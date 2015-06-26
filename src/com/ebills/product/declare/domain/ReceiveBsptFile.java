package com.ebills.product.declare.domain;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Date;

import com.ebills.product.declare.Entity;


/**
 * 采集文件实体类 对应数据库表 ：BU_RECEIVEBSPTFILE
 * @author twteam
 *
 */
public class ReceiveBsptFile extends Entity {
  
    
    /**文件名称*/
    private String fileName;
    /**数据类型*/
    private String dataType;
    /**文件类型  txt \ xml*/
    private String fileType;
    /**接收时间 用于显示*/
    private Timestamp reportDate;
    /**接收日期 用于查询*/
    private Date reciveDate;
    /**是否解析*/
    private String isParse;
    /**业务编号*/
    private String ywbh;
    /**采集机构*/
    private String orgno;
    private String remark1;//存放文件类型名称
    private String Remark2;
    private String Remark3;
    private String Remark4;//下载路径

    
	public ReceiveBsptFile(){
		this.setFileName("");
		this.setDataType("");
		this.setFileType("");
		this.setReportDate(new Timestamp(System.currentTimeMillis()));
		this.setReciveDate(new Date(System.currentTimeMillis()));
		this.setYwbh("");
		this.setOrgno("");
		this.setRemark1("");
		this.setRemark2("");
		this.setRemark3("");
		this.setRemark4("");
    }
    
	public Date getReciveDate() {
		return reciveDate;
	}

	public void setReciveDate(Date reciveDate) {
		this.reciveDate = reciveDate;
	}

	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
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
	public String getYwbh() {
		return ywbh;
	}
	public void setYwbh(String ywbh) {
		this.ywbh = ywbh;
	}
	public String getOrgno() {
		return orgno;
	}
	public void setOrgno(String orgno) {
		this.orgno = orgno;
	}
	public String getRemark1() {
		return remark1;
	}
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	public String getRemark2() {
		return Remark2;
	}
	public void setRemark2(String remark2) {
		Remark2 = remark2;
	}
	public String getRemark3() {
		return Remark3;
	}
	public void setRemark3(String remark3) {
		Remark3 = remark3;
	}
	public String getRemark4() {
		return Remark4;
	}
	public void setRemark4(String remark4) {
		Remark4 = remark4;
	}
	public Timestamp getReportDate() {
		return reportDate;
	}
	public void setReportDate(Timestamp reportDate) {
		this.reportDate = reportDate;
	} 

}
