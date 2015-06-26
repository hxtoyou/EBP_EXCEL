package com.ebills.product.declare.util.copy;


import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;

import com.ebills.util.EbillsException;
import com.jcraft.jsch.ChannelSftp;



/**
 * @说明:ebills系统用到的客户端类(抽象类)
 * @author WHT
 * 
 */
public abstract class EbillsClient {

	public static String LOCK_FILE = "ok";
	private String servip;
	private String servport;
	private String user;
	private String password;
	private String maindir;
	private int timeout = 600000;
	private Timestamp loginTime = new Timestamp(System.currentTimeMillis());
	private FTPClient ftpclient = new FTPClient();
	private ChannelSftp channelsftp = new ChannelSftp();

	/**
	 * 构造方法
	 * 
	 * @param servip
	 * @param servport
	 * @param user
	 * @param password
	 * @param maindir
	 */
	public EbillsClient(String servip, String servport, String user,
			String password, String maindir) {
		this.servip = servip;
		this.servport = servport;
		this.user = user;
		this.password = password;
		this.maindir = maindir;
	}

	/**
	 * 取得与FTP服务器的连接
	 * 
	 * @return boolean
	 * @throws Exception
	 */
	abstract public boolean connect() throws Exception;

	/**
	 * 断开FTP服务
	 * 
	 * @return boolean
	 */
	abstract public boolean disconnect() throws IOException;

	/**
	 * 判断与FTP服务是否断开
	 * 
	 * @return boolean
	 */
	abstract public boolean isConnected() throws Exception;

	/**
	 * 切换工作区间
	 * 
	 * @param remotePath
	 * @param descript
	 * @throws Exception
	 */
	abstract public void changeWorkingDirectory(String remotePath,
			String descript) throws Exception;

	/**
	 * 打印当前ftp到工作区间目录
	 * 
	 * @return
	 * @throws Exception
	 */
	abstract public String printWorkingDirectory() throws Exception;

	/**
	 * 删除FTP上指定文件夹(空文件夹)
	 * 
	 * @param remotePath
	 * @return
	 * @throws Exception
	 */
	abstract public void removeDirectory(String remotePath)
			throws Exception;

	/**
	 * 上传本地文件到远程目录
	 * 
	 * @param remotePath
	 *            远程路径
	 * @param localFile
	 *            localFile文件
	 * @param localPath
	 *            本地路径
	 * @param isDelFile
	 *            是否删除文件
	 * @throws Exception
	 * @throws Exception
	 */
	abstract public void putFile(String remotePath, String localPath,
			String localFile, boolean isDelFile) throws Exception;

	/**
	 * 下载远程目录下的文件到本地
	 * @param c_ffield  ftp配置对象
	 * @param remoteFile ftp 中指定要上传的文件
	 * @param tranDate  数据交易日期
	 * @param isDelFile 是否删除FTP上的文件
	 * @throws Exception
	 */
	abstract public void getFile(CoreCfgField c_ffield, String remoteFile, 
			String tranDate, boolean isDelFile) throws Exception;

	/**
	 * 删除远程FTP指定目录下的指定文件
	 * 
	 * @param remotePath
	 * @param remoteFile
	 */
	abstract public void deleteFileByPath(String remotePath, String remoteFile)
			throws Exception;

	/**
	 * 删除远程目录下的指定文件
	 * 
	 * @param remotePath
	 * @param remoteFile
	 */
	abstract public void deleteFile(String remoteFile) throws Exception;

	/**
	 * 判断指定路径下的制定文件是否存在
	 * 
	 * @param path
	 * @param name
	 * @return
	 * @throws IOException
	 */
	abstract public boolean isExist(String path, String name)
			throws IOException;

	/**
	 * 判断FTP服务器上的指定令牌文件是否存在
	 * 
	 * @param path
	 *            FTP服务器上指定的下载目录
	 * @param name
	 *            令牌文件名称
	 * @return File 令牌文件
	 * @throws IOException
	 * @throws Exception
	 */
	abstract public boolean getFilesLock(String path, String name)
			throws Exception;

	/**
	 * 获取FTP服务器上指定目录下的所有文件
	 * 
	 * @param remotePath
	 *            FTP服务器上指定的下载目录
	 * @return List
	 * @throws Exception
	 */
	abstract public List getFileNames(String remotePath) throws Exception;

	/**
	 * 根据后缀名获取FTP服务器上指定目录下的所有文件 根据山东城商联盟要求加 add by Hudan 2012_10_15
	 * 
	 * @param remotePath
	 *            FTP服务器上指定的下载目录
	 * @param suffix
	 *            后缀名
	 * @return List
	 * @throws Exception
	 */
	abstract public List getFileNamesBySuffix(String remotePath, String suffix)
			throws Exception;

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public Timestamp getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Timestamp loginTime) {
		this.loginTime = loginTime;
	}

	public String getServip() {
		return servip;
	}

	public void setServip(String servip) {
		this.servip = servip;
	}

	public String getServport() {
		return servport;
	}

	public void setServport(String servport) {
		this.servport = servport;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMaindir() {
		return maindir;
	}

	public void setMaindir(String maindir) {
		this.maindir = maindir;
	}

	public FTPClient getFtpclient() {
		return ftpclient;
	}

	public void setFtpclient(FTPClient ftpclient) {
		this.ftpclient = ftpclient;
	}

	public ChannelSftp getChannelsftp() {
		return channelsftp;
	}

	public void setChannelsftp(ChannelSftp channelsftp) {
		this.channelsftp = channelsftp;
	}
}