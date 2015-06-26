package com.ebills.product.declare.util.copy;

/**
 * XML配置文件
 * @author frank
 * Describe 解析XML
 */
public class CoreCfgField {
	//执行机构
	private String orgNo;
	
	//是否晚间批量下载
	private String isStartTimeFlag;
	//晚间批量下载时间 格式：HHmmss
	private String startTime;
	// 是否实时下载
	private String isIntervalFlag;
	//实时下载时间间隔 单位：秒
	private String intervalTime;
	
	/***********FTP配置信息***********/
	// 传输协议类型
	private String tranPrtc;
	// FTP服务器配置 
	private String ftpServer;		// FTP IP地址
	private String ftpUser;			//FTP用户名
	private String ftpPassword;		//FTP密码

	// SFTP服务器配置
	private String sftpServer;		//SFTP IP地址
	private String sftpPort;		//SFTP端口
	private String sftpUser;		//SFTP用户名
	private String sftpPassword;	//SFTP密码
	// 路径和令牌文件配置
	// 银行编号
	private String bankCode;
	private String sendPath;		//远程发送目录
	private String tokenPath;		//远程令牌路径
	private String tokenName;		//远程令牌名
	// 是否下载ftp文件后，删除FTP上的文件
	private String isDelFtpFile;
	// 是否5天后删除核心的ftp时间文件夹(只有当ftp上的文件下载不删除时，才可以启用)
	private String isDel5DaysFtpFile;
	
	// 本地接收文件
	private String receivePath;
	// 本地接收文件备份路径
	private String receivePathBak;
	// 本地自动接收路径令牌1
	private String token1;
	// 本地手工接收路径令牌2
	private String token2;
	// 本地手工接收结售汇路径令牌3
	private String token3;
	
	// 是否在从 FTP目录下下载文件后，上传令牌文件到FTP指定目录 ，如果为Y，则将根据下载的方式是自动还是手动，上传对应的令牌文件到ftp目录下，防止重复下载(只有当ftp上的文件下载不删除时，才可以启用)
	private String isLoadToken;
	// 上传到远程FTP对应目录下的已自动下载的令牌文件
	private String autoToken;
	// 上传到远程FTP对应目录下的已手动下载的令牌文件
	private String handToken;
	
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	public String getIsStartTimeFlag() {
		return isStartTimeFlag;
	}
	public void setIsStartTimeFlag(String isStartTimeFlag) {
		this.isStartTimeFlag = isStartTimeFlag;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getIsIntervalFlag() {
		return isIntervalFlag;
	}
	public void setIsIntervalFlag(String isIntervalFlag) {
		this.isIntervalFlag = isIntervalFlag;
	}
	public String getIntervalTime() {
		return intervalTime;
	}
	public void setIntervalTime(String intervalTime) {
		this.intervalTime = intervalTime;
	}
	public String getTranPrtc() {
		return tranPrtc;
	}
	public void setTranPrtc(String tranPrtc) {
		this.tranPrtc = tranPrtc;
	}
	public String getFtpServer() {
		return ftpServer;
	}
	public void setFtpServer(String ftpServer) {
		this.ftpServer = ftpServer;
	}
	public String getFtpUser() {
		return ftpUser;
	}
	public void setFtpUser(String ftpUser) {
		this.ftpUser = ftpUser;
	}
	public String getFtpPassword() {
		return ftpPassword;
	}
	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}
	public String getSftpServer() {
		return sftpServer;
	}
	public void setSftpServer(String sftpServer) {
		this.sftpServer = sftpServer;
	}
	public String getSftpPort() {
		return sftpPort;
	}
	public void setSftpPort(String sftpPort) {
		this.sftpPort = sftpPort;
	}
	public String getSftpUser() {
		return sftpUser;
	}
	public void setSftpUser(String sftpUser) {
		this.sftpUser = sftpUser;
	}
	public String getSftpPassword() {
		return sftpPassword;
	}
	public void setSftpPassword(String sftpPassword) {
		this.sftpPassword = sftpPassword;
	}
	public String getSendPath() {
		return sendPath;
	}
	public void setSendPath(String sendPath) {
		this.sendPath = sendPath;
	}
	public String getTokenPath() {
		return tokenPath;
	}
	public void setTokenPath(String tokenPath) {
		this.tokenPath = tokenPath;
	}
	public String getTokenName() {
		return tokenName;
	}
	public void setTokenName(String tokenName) {
		this.tokenName = tokenName;
	}
	public String getIsDelFtpFile() {
		return isDelFtpFile;
	}
	public void setIsDelFtpFile(String isDelFtpFile) {
		this.isDelFtpFile = isDelFtpFile;
	}
	public String getIsDel5DaysFtpFile() {
		return isDel5DaysFtpFile;
	}
	public void setIsDel5DaysFtpFile(String isDel5DaysFtpFile) {
		this.isDel5DaysFtpFile = isDel5DaysFtpFile;
	}
	public String getReceivePath() {
		return receivePath;
	}
	public void setReceivePath(String receivePath) {
		this.receivePath = receivePath;
	}
	public String getReceivePathBak() {
		return receivePathBak;
	}
	public void setReceivePathBak(String receivePathBak) {
		this.receivePathBak = receivePathBak;
	}
	public String getToken1() {
		return token1;
	}
	public void setToken1(String token1) {
		this.token1 = token1;
	}
	public String getToken2() {
		return token2;
	}
	public void setToken2(String token2) {
		this.token2 = token2;
	}
	public String getToken3() {
		return token3;
	}
	public void setToken3(String token3) {
		this.token3 = token3;
	}
	public String getIsLoadToken() {
		return isLoadToken;
	}
	public void setIsLoadToken(String isLoadToken) {
		this.isLoadToken = isLoadToken;
	}
	public String getAutoToken() {
		return autoToken;
	}
	public void setAutoToken(String autoToken) {
		this.autoToken = autoToken;
	}
	public String getHandToken() {
		return handToken;
	}
	public void setHandToken(String handToken) {
		this.handToken = handToken;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
}