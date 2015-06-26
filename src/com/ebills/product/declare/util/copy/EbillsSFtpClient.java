package com.ebills.product.declare.util.copy;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.ChannelSftp.LsEntry;

/**
 * @说明：ebills系统连接SFTP的客户端类
 * @author WHT
 * 
 */
public class EbillsSFtpClient extends EbillsClient {

	private static final Log log = LogFactory.getLog(EbillsSFtpClient.class);

	/**
	 * 构造方法，初始化FTP服务器设置
	 * 
	 * @param ftpIP
	 * @param ftpUser
	 * @param ftpPwd
	 * @param mainDir
	 */
	public EbillsSFtpClient(String ftpIP, String ftpPort, String ftpUser, String ftpPwd,
			String mainDir) {
		super(ftpIP, ftpPort, ftpUser, ftpPwd, mainDir);
	}

	/**
	 * 取得与FTP服务器的连接
	 * 
	 * @return boolean
	 */
	public boolean connect() throws Exception {
		try {
			JSch jsch = new JSch();
			log.debug("[SFTP]getServip:" + this.getServip()
					+ "[SFTP]getServport:" + this.getServport()
					+ "[SFTP]getUser:" + this.getUser() + "[SFTP]getPassword:"
					+ this.getPassword());
			System.out.println("[SFTP]getServip:" + this.getServip()
					+ "[SFTP]getServport:" + this.getServport()
					+ "[SFTP]getUser:" + this.getUser() + "[SFTP]getPassword:"
					+ this.getPassword());
			Session sshSession = jsch.getSession(this.getUser(),
					this.getServip(), Integer.parseInt(this.getServport()));
			System.out.println("Session created.");
			sshSession.setPassword(this.getPassword());
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			sshSession.connect();
			System.out.println("Session connected.");
			System.out.println("Opening Channel.");
			Channel channel = sshSession.openChannel("sftp");
			channel.connect();
			this.setChannelsftp((ChannelSftp) channel);
			System.out.println("Connected to " + this.getServip() + ".");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("取得与FTP服务器的连接失败:" + e.getMessage());
			System.out.println("取得与FTP服务器的连接失败:" + e.getMessage());
			throw new Exception("取得与FTP服务器的连接失败:" + e.getMessage());
		}
	}

	/**
	 * 断开FTP服务
	 * 
	 * @return boolean
	 */
	public boolean disconnect() throws IOException {
		try {
			this.getChannelsftp().getSession().disconnect();
			this.getChannelsftp().disconnect();
			return true;
		} catch (Exception e) {
			log.debug("断开FTP服务失败:" + e.getMessage());
			System.out.println("断开FTP服务失败:" + e.getMessage());
			throw new IOException("断开FTP服务失败:" + e.getMessage());
		}
	}

	/**
	 * 判断与FTP服务是否断开
	 * 
	 * @return boolean
	 */
	public boolean isConnected() throws Exception {
		try {
			return this.getChannelsftp().isConnected();
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
	 */
	public void changeWorkingDirectory(String remotePath, String descript)
			throws Exception {
		try {
			System.out.println("[" + descript + "]当前工作目录:"
					+ this.getChannelsftp().pwd());
			try {
				System.out.println("切换目录:" + remotePath);
				this.getChannelsftp().cd(remotePath);
			} catch (Exception e) {
				try {
					this.getChannelsftp().cd(DataUtil.cffield.getSendPath());
					this.getChannelsftp().cd(remotePath);
				} catch (Exception e2) {
					System.out.println("切换目录:" + remotePath + "失败!"
							+ e.getMessage());
				}
			}
			System.out.println("[" + descript + "]当前工作目录:"
					+ this.getChannelsftp().pwd());
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
			workingDirectory = this.getChannelsftp().pwd();
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
			this.getChannelsftp().rmdir(remotePath);
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
			System.out.println("上传:" + localFile);
			// 本地的SEND目录
			File file = new File(localPath + File.separator + localFile);
			if (file.isFile()) {
				FileInputStream localFIS = new FileInputStream(file);
				this.getChannelsftp().put(localFIS, file.getName());
				localFIS.close();
				System.out.println("上传本地" + localPath + "目录下的文件" + localFile
						+ "到远程目录" + remotePath + "成功!");
				if (isDelFile) {
					file.delete();
				}
			} else {
				System.out.println("上传本地" + localPath + "目录下的文件" + localFile
						+ "到远程目录" + remotePath + "失败!");
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
	 * 删除远程目录下的文件
	 * 
	 * @param remotePath
	 * @param remoteFile
	 */
	public void deleteFileByPath(String remotePath, String remoteFile)
			throws Exception {
		try {
			this.changeWorkingDirectory(remotePath, "deleteFile");
			this.getChannelsftp().rm(remoteFile);
			System.out.println("删除FTP目录" + remotePath + "文件[" + remoteFile
					+ "]成功!");
		} catch (Exception e) {
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
			this.getChannelsftp().rm(remoteFile);
			System.out.println("删除FTP目录文件[" + remoteFile
					+ "]成功!");
		} catch (Exception e) {
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
	 * 判断指定路径下的制定文件是否存在
	 * 
	 * @param path
	 * @param name
	 * @return
	 * @throws IOException
	 */
	public boolean isExist(String path, String name) throws IOException {
		try {
			this.changeWorkingDirectory(path, "isExies");
			Vector v = this.getChannelsftp().ls(name);
			if (v != null) {
				Iterator it = v.iterator();
				while (it.hasNext()) {
					LsEntry lsEntry = (LsEntry) it.next();
					System.out.println("lsEntry.getFilename():::"
							+ lsEntry.getFilename());
					if (lsEntry.getFilename().equals(name)) {
						return true;
					}
				}
			}
			return false;
		} catch (Exception e) {
			log.debug("判断指定路径" + path + "下的指定文件是否存在失败!" + e.getMessage());
			System.out.println("判断指定路径" + path + "下的指定文件是否存在失败!"
					+ e.getMessage());
			throw new IOException("判断指定路径" + path + "下的指定文件是否存在失败!"
					+ e.getMessage());
		}
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
		System.out.println(":::::::::::::::" + path + "| 判断条件：" + name);
		boolean flag = false;
		try {
			Vector v = this.getChannelsftp().ls(path);
			if (v != null) {
				Iterator it = v.iterator();
				while (it.hasNext()) {
					LsEntry lsEntry = (LsEntry) it.next();
					if (lsEntry.getFilename().equals(name)) {
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
			Vector v = this.getChannelsftp().ls(remotePath);
			if (v != null) {
				Iterator it = v.iterator();
				while (it.hasNext()) {
					LsEntry lsEntry = (LsEntry) it.next();
					if (!lsEntry.getFilename().equals(".")
							&& !lsEntry.getFilename().equals("..")
							&& (lsEntry.getFilename().indexOf(".") != -1)) {
						fileNames.add(lsEntry.getFilename());
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
			Vector v = this.getChannelsftp().ls(remotePath);
			if (v != null) {
				Iterator it = v.iterator();
				while (it.hasNext()) {
					LsEntry lsEntry = (LsEntry) it.next();
					if (!lsEntry.getFilename().equals(".")
							&& !lsEntry.getFilename().equals("..")
							&& !lsEntry
									.getFilename()
									.substring(
											lsEntry.getFilename().indexOf("."))
									.equals(suffix)
							&& (lsEntry.getFilename().indexOf(".") != -1)) {
						fileNames.add(lsEntry.getFilename());
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
