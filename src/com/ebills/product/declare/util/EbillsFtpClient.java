package com.ebills.product.declare.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**
 * @说明:ebills系统连接FTP的客户端类
 * @author WHT
 * 
 */
public class EbillsFtpClient extends EbillsClient {
	private static final Log log = LogFactory.getLog(EbillsFtpClient.class);

	/**
	 * 构造方法，初始化FTP服务器设置
	 * 
	 * @param ftpIP
	 * @param ftpUser
	 * @param ftpPwd
	 * @param mainDir
	 */
	public EbillsFtpClient(String ftpIP, String ftpUser, String ftpPwd,
			String mainDir) {
		super(ftpIP, null, ftpUser, ftpPwd, mainDir);
	}

	/**
	 * 取得与FTP服务器的连接
	 * 
	 * @return boolean
	 * @throws Exception 
	 */
	public boolean connect() throws Exception {
		int reply;
		try {
			Timestamp starttime = new Timestamp(System.currentTimeMillis());
			System.out.println("FTP开始连接时间：" + starttime);
			String ip = this.getServip();
			System.out.println("IP:" + ip);
			this.getFtpclient().connect(this.getServip());
			Timestamp endtime = new Timestamp(System.currentTimeMillis());
			System.out.println("FTP连接结束时间：" + endtime);
			this.setLoginTime(new Timestamp(System.currentTimeMillis()));

			reply = this.getFtpclient().getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				this.getFtpclient().disconnect();
				log.debug("[msg:!FTPReply.isPositiveCompletion]");
				System.out.println("[msg:!FTPReply.isPositiveCompletion]");
				throw new Exception("[msg:!FTPReply.isPositiveCompletion]");
			}
			if (!this.getFtpclient().login(this.getUser(), this.getPassword())) {
				this.getFtpclient().logout();
				log.debug("[msg:!this.getFTPCLIENT().login(this.getUSER(), this.getPASSWORD())]");
				System.out
						.println("[msg:!this.getFTPCLIENT().login(this.getUSER(), this.getPASSWORD())]");
				throw new Exception(
						"[msg:!this.getFTPCLIENT().login(this.getUSER(), this.getPASSWORD())]");
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			if (this.getFtpclient().isConnected()) {
				try {
					this.getFtpclient().disconnect();
				} catch (IOException e1) {
					log.debug("断开FTP服务失败:" + e1.getMessage());
					System.out.println("断开FTP服务失败:" + e1.getMessage());
					throw new Exception("断开FTP服务失败:" + e1.getMessage());
				}
			}
			log.debug("取得与FTP服务器的连接失败:" + e.getMessage());
			System.out.println("取得与FTP服务器的连接失败:" + e.getMessage());
			throw new Exception("取得与FTP服务器的连接失败:" + e.getMessage());
		}
	}

	/**
	 * 断开FTP服务
	 * 
	 * @return boolean
	 * @throws IOException
	 */
	public boolean disconnect() throws IOException {
		try {
			this.getFtpclient().disconnect();
			return true;
		} catch (IOException e) {
			log.debug("断开FTP服务失败:" + e.getMessage());
			System.out.println("断开FTP服务失败:" + e.getMessage());
			throw new IOException("断开FTP服务失败:" + e.getMessage());
		}
	}

	/**
	 * 判断与FTP服务是否断开
	 * 
	 * @return boolean
	 * @throws Exception 
	 */
	public boolean isConnected() throws Exception {
		try {
			return this.getFtpclient().isConnected();
		} catch (Exception e) {
			log.debug("判断与FTP服务是否断开失败:" + e.getMessage());
			System.out.println("判断与FTP服务是否断开失败:" + e.getMessage());
			throw new Exception("判断与FTP服务是否断开失败:" + e.getMessage());
		}
	}

	/**
	 * 切换工作区间
	 * 
	 * @param remotePath
	 * @param descript
	 * @throws Exception 
	 */
	public void changeWorkingDirectory(String remotePath, String descript)
			throws Exception {
		try {
			System.out.println("[" + descript + "]工作目录:"
					+ this.getFtpclient().printWorkingDirectory());
			System.out.println("切换目录:" + remotePath);
			this.getFtpclient().changeWorkingDirectory("/");
			this.getFtpclient().changeWorkingDirectory(this.getMaindir());
			this.getFtpclient().changeWorkingDirectory(remotePath);
			System.out.println("[" + descript + "]工作目录:"
					+ this.getFtpclient().printWorkingDirectory());
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("切换到工作区间" + remotePath + "失败!");
			System.out.println("切换到工作区间" + remotePath + "失败!");
			throw new Exception("切换到工作区间" + remotePath + "失败!");
		}
	}
	
	/**
	 * 打印当前ftp到工作区间目录
	 * @return
	 * @throws Exception
	 */
	public String printWorkingDirectory() throws Exception {
		String workingDirectory ="";
		try {
			workingDirectory = this.getFtpclient().printWorkingDirectory();
			System.out.println("打印当前ftp到工作区间目录:"+workingDirectory);
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("打印当前ftp到工作区间目录" + workingDirectory + "失败!");
			System.out.println("打印当前ftp到工作区间目录" + workingDirectory + "失败!");
			throw new Exception("打印当前ftp到工作区间目录" + workingDirectory + "失败!");
		}
		return workingDirectory;
	}
	
	/**
	 * 删除FTP上指定文件夹(空文件夹)
	 * @param remotePath
	 * @return
	 * @throws Exception
	 */
	public void removeDirectory(String remotePath) throws Exception{
		try {
			this.getFtpclient().removeDirectory(remotePath);
		} catch (Exception e) {
			log.debug("删除FTP上指定文件夹" + remotePath + "失败!" + e.getMessage());
			System.out.println("删除FTP上指定文件夹" + remotePath + "失败!"
					+ e.getMessage());
			throw new Exception("删除FTP上指定文件夹" + remotePath + "失败!"
					+ e.getMessage());
		}
	}
	
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
	public void putFile(String remotePath, String localPath, String localFile,
			boolean isDelFile) throws Exception {
		try {
			this.changeWorkingDirectory(remotePath, "putFile");
			// 本地目录
			File putfile = new File(localPath + File.separator + localFile);
			System.out.println("上传:" + localFile);
			if (putfile.exists() && putfile.isFile()) {
				FileInputStream putStream = new FileInputStream(putfile);
				if (this.getFtpclient().storeFile(localFile, putStream)) {
					System.out.println("上传本地" + localPath + "目录下的文件"
							+ localFile + "到远程目录" + remotePath + "成功!");
					putStream.close();
					if (isDelFile)
						putfile.delete();
				} else {
					System.out.println("上传本地" + localPath + "目录下的文件"
							+ localFile + "到远程目录" + remotePath + "失败!");
				}
				putStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("上传本地" + localPath + "目录下的文件" + localFile + "到远程目录"
					+ remotePath + "失败!");
			System.out.println("上传本地" + localPath + "目录下的文件" + localFile
					+ "到远程目录" + remotePath + "失败!");
			throw new Exception("上传本地" + localPath + "目录下的文件" + localFile
					+ "到远程目录" + remotePath + "失败!");
		}
	}

	/**
	 * 下载远程目录下的文件到本地
	 * @param c_ffield  ftp配置对象
	 * @param remoteFile ftp 中指定要上传的文件
	 * @param tranDate  数据交易日期
	 * @param isDelFile 是否删除FTP上的文件
	 * @throws Exception
	 */
	public void getFile(CoreCfgField c_ffield, String remoteFile, 
			String tranDate, boolean isDelFile) throws Exception {
		try {
			this.changeWorkingDirectory(c_ffield.getSendPath(), "getFile");
			// 根据山东城商联盟改 将联盟提供的文件名改为国结中定义的名字，比如联盟的账户开关户信息在ftp上的文件名为UserSwitchInfo.del，写入本地是改为ACCCA.TXT add by Hudan 2012_10_15
			String localFileName = "";
			// 数据文件后缀名
			String suffix = tranDate + ".TXT";
			if (remoteFile.equals("UserSwitchInfo.del")) {			// 外汇账户信息--CA 账户开关户信息
				localFileName = "ACCCA" + suffix;
			} else if (remoteFile.equals("UserBalanceInfo.del")) {	// 外汇账户信息--CB 账户收支余信息
				localFileName = "ACCCB" + suffix;
			} else if (remoteFile.equals("ACCTT")) {// 外汇账户信息--TT 接口控制文件
			} else if (remoteFile.equals("CFATT")) {// 资本项目--TT 接口控制文件
			} else if (remoteFile.equals("CFAAA")) {// 资本项目--AA 外债双边贷款—签约信息
			} else if (remoteFile.equals("CFAAB")) {// 资本项目--AB 外债买方信贷—签约信息
			} else if (remoteFile.equals("CFAAC")) {// 资本项目--AC 外债境外同业拆借—签约信息
			} else if (remoteFile.equals("CFAAD")) {// 资本项目--AD 外债海外代付—签约信息
			} else if (remoteFile.equals("SellBuyBack.del")) {// 资本项目--AE 外债卖出回购—签约信息
				localFileName = "CFAAE" + suffix;
			} else if (remoteFile.equals("CFAAF")) {// 资本项目--AF 外债远期信用证—签约信息
			} else if (remoteFile.equals("CFAAG")) {// 资本项目--AG 外债银团贷款—签约信息
			} else if (remoteFile.equals("CFAAH")) {// 资本项目--AH 外债贵金属拆借—签约信息
			} else if (remoteFile.equals("CFAAI")) {// 资本项目--AI 外债其他贷款—签约信息
			} else if (remoteFile.equals("CFAAJ")) {// 资本项目--AJ 外债货币市场工具—签约信息
			} else if (remoteFile.equals("CFAAK")) {// 资本项目--AK 外债债券和票据—签约信息
			} else if (remoteFile.equals("CFAAL")) {// 资本项目--AL 外债境外同业存放—签约信息
			} else if (remoteFile.equals("CFAAM")) {// 资本项目--AM 外债境外联行及附属机构往来—签约信息
			} else if (remoteFile.equals("NonResidentSavingsInstitutions.del")) {	// 资本项目--AN 外债非居民机构存款—签约信息
				localFileName = "CFAAN" + suffix;
			} else if (remoteFile.equals("NonResidentIndividualsDeposit.del")) {	// 资本项目--AP 外债非居民个人存款—签约信息
				localFileName = "CFAAP" + suffix;
			} else if (remoteFile.equals("CFAAQ")) {// 资本项目--AQ 外债其他外债—签约信息
			} else if (remoteFile.equals("CFAAR")) {// 资本项目--AR 外债—变动信息
			} else if (remoteFile.equals("NonResidentIndividualsAccountBalance.del")) {// 资本项目--AS 外债—余额信息
				localFileName = "CFAAS_GR" + suffix;
			} else if (remoteFile.equals("NonResidentInstitutionsAccountBalance.del")) {// 资本项目--AS 外债—余额信息
				localFileName = "CFAAS_JG" + suffix;
			} else if (remoteFile.equals("CFABA")) {// 资本项目--BA 对外担保—签约信息
			} else if (remoteFile.equals("CFABB")) {// 资本项目--BB 对外担保—责任余额信息
			} else if (remoteFile.equals("CFABC")) {// 资本项目--BC 对外担保-履约信息
			} else if (remoteFile.equals("ForeignExchangeLoanSigning.del")) {	// 资本项目--CA 国内外汇贷款—签约信息
				localFileName = "CFACA" + suffix;
			} else if (remoteFile.equals("ForeignExchangeLoanChange.del")) {	// 资本项目--CB 国内外汇贷款—变动信息
				localFileName = "CFACB" + suffix;
			} else if (remoteFile.equals("CFADA")) {// 资本项目--DA 境外担保项下境内贷款—签约信息
			} else if (remoteFile.equals("CFADB")) {// 资本项目--DB 境外担保项下境内贷款—变动及履约信息
			} else if (remoteFile.equals("ForeignExchangePledgeLoanSigning.del")) {		// 资本项目--EA 外汇质押人民币贷款—签约信息
				localFileName = "CFAEA" + suffix;
			} else if (remoteFile.equals("ForeignExchangePledgeLoanFluctuation.del")) {	// 资本项目--EB 外汇质押人民币贷款—变动信息
				localFileName = "CFAEB" + suffix;
			} else if (remoteFile.equals("CFAFA")) {// 资本项目--FA 商业银行人民币结构性存款—签约信息
			} else if (remoteFile.equals("CFAFB")) {// 资本项目--FB 商业银行人民币结构性存款—终止信息
			} else if (remoteFile.equals("CFAFC")) {// 资本项目--FC 商业银行人民币结构性存款—利息给付信息
			} else if (remoteFile.equals("CFAFD")) {// 资本项目--FD  商业银行人民币结构性存款—资金流出入和结购汇信息
			// 如果为其他文件，则直接copy过来，不用改名
			} else {
				localFileName = remoteFile;
			}
			if (localFileName != null && !"".equals(localFileName)) {
				File file = new File(c_ffield.getReceivePath() + File.separator + localFileName);
				FileOutputStream localFOS = new FileOutputStream(file);
				this.getFtpclient().retrieveFile(remoteFile, localFOS);
				localFOS.close();
				// 删除FTP上的文件
				if (isDelFile) {
					this.deleteFileByPath(c_ffield.getSendPath(), remoteFile);
				}
				log.debug("下载文件[" + remoteFile + "]成功!");
				System.out.println("下载文件[" + remoteFile + "]成功!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("下载文件[" + remoteFile + "]失败:" + e.getMessage());
			System.out.println("下载文件[" + remoteFile + "]失败:" + e.getMessage());
			throw new Exception("下载文件[" + remoteFile + "]失败:"
					+ e.getMessage());
		}
	}

	/**
	 * 删除远程FTP指定目录下的指定文件
	 * 
	 * @param remotePath
	 * @param remoteFile
	 */
	public void deleteFileByPath(String remotePath, String remoteFile)
			throws Exception {
		try {
			this.changeWorkingDirectory(remotePath, "deleteFile");
			this.getFtpclient().deleteFile(remoteFile);
			System.out.println("删除FTP目录" + remotePath + "文件[" + remoteFile
					+ "]成功!");
		} catch (IOException e) {
			e.printStackTrace();
			log.debug("删除FTP目录" + remotePath + "文件[" + remoteFile + "]!"
					+ e.getMessage());
			System.out.println("删除FTP目录" + remotePath + "文件[" + remoteFile
					+ "]!" + e.getMessage());
			throw new Exception("删除FTP目录" + remotePath + "文件["
					+ remoteFile + "]!" + e.getMessage());

		}
	}
	
	/**
	 * 删除远程目录下的指定文件
	 * 
	 * @param remotePath
	 * @param remoteFile
	 */
	public void deleteFile(String remoteFile)
			throws Exception {
		try {
			this.getFtpclient().deleteFile(remoteFile);
			System.out.println("删除FTP目录文件[" + remoteFile
					+ "]成功!");
		} catch (IOException e) {
			e.printStackTrace();
			log.debug("删除FTP目录文件[" + remoteFile + "]!"
					+ e.getMessage());
			System.out.println("删除FTP目录文件[" + remoteFile
					+ "]!" + e.getMessage());
			throw new Exception("删除FTP目录文件["
					+ remoteFile + "]!" + e.getMessage());

		}
	}

	/**
	 * 判断指定路径下的指定文件是否存在
	 * 
	 * @param path
	 * @param name
	 * @return
	 * @throws IOException
	 */
	public boolean isExist(String path, String name) throws IOException {
		boolean flag = false;
		try {
			FTPFile[] fs = this.getFtpclient().listFiles(path);
			if (fs != null) {
				for (int i = 0; i < fs.length; i++) {
					if (fs[i].getName().equals(name)) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			log.debug("判断指定路径" + path + "下的指定文件是否存在失败!" + e.getMessage());
			System.out.println("判断指定路径" + path + "下的指定文件是否存在失败!"
					+ e.getMessage());
			throw new IOException("判断指定路径" + path + "下的指定文件是否存在失败!"
					+ e.getMessage());
		}
		return flag;
	}

	/**
	 * 判断FTP服务器上的指定令牌文件是否存在
	 * 
	 * @param path
	 *            FTP服务器上指定的下载目录
	 * @param name
	 *            令牌文件名称
	 * @return File 令牌文件
	 * @throws Exception
	 */
	public boolean getFilesLock(String path, String name)
			throws Exception {
		boolean flag = false;
		try {
			FTPFile[] fs = this.getFtpclient().listFiles(path);
			if (fs != null) {
				for (int i = 0; i < fs.length; i++) {
					if (fs[i].getName().equals(name)) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			log.debug("验证FTP服务器上的指定令牌文件是否存在失败!" + e.getMessage());
			System.out.println("验证FTP服务器上的指定令牌文件是否存在失败!" + e.getMessage());
			throw new Exception("验证FTP服务器上的指定令牌文件是否存在失败!"
					+ e.getMessage());
		}
		return flag;
	}

	/**
	 * 获取FTP服务器上指定目录下的所有文件
	 * 
	 * @param remotePath
	 *            FTP服务器上指定的下载目录
	 * @return List
	 * @throws Exception
	 */
	public List getFileNames(String remotePath) throws Exception {
		List fileNames = new ArrayList();
		try {
			FTPFile[] fs = this.getFtpclient().listFiles(remotePath);
			if (fs != null) {
				for (int i = 0; i < fs.length; i++) {
					if (!fs[i].getName().equals(".")
							&& !fs[i].getName().equals("..")
							&& (fs[i].getName().indexOf(".") != -1)) {
						fileNames.add(fs[i].getName());
					}
				}
			}
		} catch (Exception e) {
			log.debug("获取FTP服务器上指定目录" + remotePath + "下的所有文件失败!"
					+ e.getMessage());
			System.out.println("获取FTP服务器上指定目录" + remotePath + "下的所有文件失败!"
					+ e.getMessage());
			throw new Exception("获取FTP服务器上指定目录" + remotePath
					+ "下的所有文件失败!" + e.getMessage());
		}
		return fileNames;
	}

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
	public List getFileNamesBySuffix(String remotePath, String suffix)
			throws Exception {
		List fileNames = new ArrayList();
		try {
			FTPFile[] fs = this.getFtpclient().listFiles(remotePath);
			if (fs != null) {
				for (int i = 0; i < fs.length; i++) {
					if (!fs[i].getName().equals(".")
							&& !fs[i].getName().equals("..")
							&& !fs[i].getName()
									.substring(fs[i].getName().indexOf("."))
									.equals(suffix)
							&& (fs[i].getName().indexOf(".") != -1)) {
						fileNames.add(fs[i].getName());
					}
				}
			}
		} catch (Exception e) {
			log.debug("根据后缀名" + suffix + "获取FTP服务器上指定目录" + remotePath
					+ "下的所有文件失败!" + e.getMessage());
			System.out.println("根据后缀名" + suffix + "获取FTP服务器上指定目录" + remotePath
					+ "下的所有文件失败!" + e.getMessage());
			throw new Exception("根据后缀名" + suffix + "获取FTP服务器上指定目录"
					+ remotePath + "下的所有文件失败!" + e.getMessage());
		}
		return fileNames;
	}
}
