package com.ebills.product.declare.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ebills.util.EbillsException;
import com.ebills.utils.CommonUtil;
import com.ebills.utils.EbpConstants;

/**
 * 36 号文加 文件处理工具类
 * 
 * @author Hudan
 * 
 *         2012-10-10
 */
public class DataUtil {
	private static Log log = LogFactory.getLog(DataUtil.class);
	public static CoreCfgField cffield = null;

	/**
	 * @des 加载XML配置文件(BsptCfg.xml)信息
	 */
	public static void loadConfig() {
		// 由于下载时下载的是前一天的核心跑批后的目录，所以默认的备份文件为前一天的时间备份目录
		Calendar calendar = Calendar.getInstance();// 此时打印它获取的是系统当前时间
		calendar.add(Calendar.DATE, -1); // 得到前一天
		String yesterday = new SimpleDateFormat("yyyyMMdd").format(calendar
				.getTime());
		cffield = CoreDateConfigParser.getInstance(yesterday);
	}

	/**
	 * 备份文件到对应的备份目录
	 * 
	 * @param backupPath
	 *            备份目录
	 * @param file
	 *            备份文件
	 */
	public static void backupFile(String backupPath, File file)
			throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date currentTime = new Date(System.currentTimeMillis());
		String dateStr = sdf.format(currentTime);
		File dir = new File(backupPath + File.separator + dateStr);
		if (!dir.exists()) {
			dir.mkdir();
		}
		boolean firstLine = true;
		FileWriter fw = null;
		FileReader fr = null;
		BufferedWriter bw = null;
		BufferedReader br = null;
		try {
			File newFile = new File(backupPath + File.separator + dateStr
					+ File.separator + file.getName());
			if (!newFile.exists()) {
				newFile.createNewFile();
			}

			fr = new FileReader(file);
			fw = new FileWriter(newFile);
			br = new BufferedReader(fr);
			bw = new BufferedWriter(fw);

			String tmpbuf = null;
			while ((tmpbuf = br.readLine()) != null) {
				if (!firstLine) {
					bw.newLine();
				}
				if (firstLine) {
					firstLine = false;
				}
				bw.write(tmpbuf);
				bw.flush();
			}
			log.debug("备份文件[" + file.getName() + "]至路径 [" + backupPath
					+ File.separator + dateStr + "]成功!");
			System.out.println("备份文件[" + file.getName() + "]至路径 [" + backupPath
					+ File.separator + dateStr + "]成功!");
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("备份文件[" + file.getName() + "]至路径 [" + backupPath
					+ File.separator + dateStr + "]失败!" + e.getMessage());
			System.out.println("备份文件[" + file.getName() + "]至路径 [" + backupPath
					+ File.separator + dateStr + "]失败!" + e.getMessage());
			throw new Exception("备份文件[" + file.getName() + "]至路径 ["
					+ backupPath + File.separator + dateStr + "]失败!"
					+ e.getMessage());
		} finally {
			try {
				bw.close();
				br.close();
			} catch (Exception e) {
				throw new Exception("关闭文件流操作失败:" + e.getMessage());
			}
		}
	}

	/**
	 * 下载方法(从核心FTP指定目录下下载对应文件)
	 * 
	 * @param c_ffield
	 *            FTP的配置信息
	 * @param getFileFlag
	 *            是否存在令牌文件，需要判断令牌文件
	 * @param tranDate
	 *            获取文件日期
	 * @return
	 * @throws Exception
	 */
	public static boolean downFile(CoreCfgField c_ffield, boolean getFileFlag,
			String tranDate) throws Exception {
		log.info("sendPath" + c_ffield.getSendPath() + "|receivePath"
				+ c_ffield.getReceivePath() + "|tokenPath "
				+ c_ffield.getTokenPath() + "|tokenName"
				+ c_ffield.getTokenName() + "|tranPrtc"
				+ c_ffield.getTokenName() + "|getFileFlag" + getFileFlag
				+ "|tranDate" + tranDate);
		boolean flag = true;
		List fileNameList = new ArrayList();
		EbillsClient ftpClient = null;

		try {
			// 根据FTP的类型来调用FTP或者SFTP的对应方法
			if ("FTP".equals(c_ffield.getTranPrtc())) {
				ftpClient = new EbillsFtpClient(c_ffield.getFtpServer(),
						c_ffield.getFtpUser(), c_ffield.getFtpPassword(), null);
			} else {
				ftpClient = new EbillsSFtpClient(c_ffield.getSftpServer(),
						c_ffield.getSftpPort(), c_ffield.getSftpUser(),
						c_ffield.getSftpPassword(), null);
			}
			ftpClient.connect();
			if (getFileFlag) {
				// 判断FTP上是否有令牌文件
				log.debug("报送平台---->判断目录:" + c_ffield.getTokenPath()
						+ ",是否有令牌文件::" + c_ffield.getTokenName());
				if (!ftpClient.getFilesLock(c_ffield.getTokenPath(),
						c_ffield.getTokenName())) {
					flag = false;
					log.debug("报送平台---->令牌文件不存在，不需下载");
					System.out.println("令牌文件不存在，本次不需下载");
					throw new Exception("令牌文件不存在，本次不需下载");
				}
			}
			// 先判断是否已经进行了自动下载(根据潍坊银行要求，在首次自动下载完成后，会将一个令牌文件上传到FTP对应的目录下，防止重复下载)
			if (ftpClient.getFilesLock(c_ffield.getTokenPath(),
					c_ffield.getAutoToken())) {
				flag = false;
				log.debug("报送平台---->存在自动下载的令牌文件(已自动下载过文件),请不要重复下载");
				System.out.println("报送平台---->存在自动下载的令牌文件(已自动下载过文件),请不要重复下载");
				throw new Exception("报送平台---->存在自动下载的令牌文件(已自动下载过文件),请不要重复下载");
			}
			// 先判断是否已经进行了手动下载(根据潍坊银行要求，在首次手动下载完成后，会将一个令牌文件上传到FTP对应的目录下，防止重复下载)
			if (ftpClient.getFilesLock(c_ffield.getTokenPath(),
					c_ffield.getHandToken())) {
				flag = false;
				log.debug("报送平台---->存在手动下载的令牌文件(已手动下载过文件),请不要重复下载");
				System.out.println("报送平台---->存在手动下载的令牌文件(已手动下载过文件),请不要重复下载");
				throw new Exception("报送平台---->存在手动下载的令牌文件(已手动下载过文件),请不要重复下载");
			}
			try {
				// 获取远程发送目录文件名
				fileNameList = ftpClient.getFileNames(c_ffield.getSendPath());
			} catch (Exception e) {
				flag = false;
				e.printStackTrace();
				log.debug("报送平台---->get downloadFileNames from ftp wrong"
						+ e.getMessage());
				System.out
						.println("报送平台---->get downloadFileNames from ftp wrong"
								+ e.getMessage());
				throw new Exception(
						"报送平台---->get downloadFileNames from ftp wrong"
								+ e.getMessage());
			}
			// 下载文件
			try {
				if (fileNameList != null && fileNameList.size() != 0) {
					boolean isDelFile = "Y".equals(c_ffield.getIsDelFtpFile()) ? true
							: false;
					for (int i = 0; i < fileNameList.size(); i++) {
						ftpClient.getFile(c_ffield,
								(String) fileNameList.get(i), tranDate,
								isDelFile);
					}
				}
			} catch (Exception e) {
				flag = false;
				e.printStackTrace();
				log.debug("报送平台---->loadFile from ftp wrong" + e.getMessage());
				System.out.println("报送平台---->loadFile from ftp wrong"
						+ e.getMessage());
				throw new Exception("报送平台---->loadFile from ftp wrong"
						+ e.getMessage());
			}
			ftpClient.disconnect();
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
			log.debug("从FTP服务器上下载文件失败!" + e.getMessage());
			System.out.println("从FTP服务器上下载文件失败!" + e.getMessage());
			throw new Exception("从FTP服务器上下载文件失败!" + e.getMessage());
		}
		return flag;
	}

	/**
	 * 上传方法(将本地指定目录下的令牌文件上传到ftp指定的目录下，防止重复下载)
	 * 
	 * @param c_ffield
	 *            FTP初始化配置信息
	 * @param tokenFile
	 *            需要上传的令牌文件
	 * @param isDel
	 *            是否删除本地对应令牌的文件
	 * @return
	 * @throws Exception
	 */
	public static boolean uploadTokenFile(CoreCfgField c_ffield,
			File tokenFile, boolean isDel) throws Exception {
		boolean flag = true;
		EbillsClient client = null;

		try {
			// 根据FTP的类型来调用FTP或者SFTP的对应方法
			if ("FTP".equals(c_ffield.getTranPrtc())) {
				client = new EbillsFtpClient(c_ffield.getFtpServer(),
						c_ffield.getFtpUser(), c_ffield.getFtpPassword(), null);
			} else {
				client = new EbillsSFtpClient(c_ffield.getSftpServer(),
						c_ffield.getSftpPort(), c_ffield.getSftpUser(),
						c_ffield.getSftpPassword(), null);
			}
			client.connect();
			try {
				// 将令牌文件上传到ftp指定目录下
				client.putFile(c_ffield.getTokenPath(),
						c_ffield.getReceivePath(), tokenFile.getName(), isDel);
			} catch (Exception e) {
				flag = false;
				e.printStackTrace();
				log.debug("报送平台---->put a tokenFile to ftp wrong"
						+ e.getMessage());
				System.out.println("报送平台---->put a tokenFile to ftp wrong"
						+ e.getMessage());
				throw new Exception("报送平台---->put a tokenFile to ftp wrong"
						+ e.getMessage());
			}
			client.disconnect();
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
			log.debug("上传令牌文件到FTP服务器失败!" + e.getMessage());
			System.out.println("上传令牌文件到FTP服务器失败!" + e.getMessage());
			throw new Exception("上传令牌文件到FTP服务器失败!" + e.getMessage());
		}
		return flag;
	}

	/**
	 * 备份下载的文件
	 * 
	 * @param parsedBackupPath
	 * @param file
	 */
	public static void backupParsedFile(String parsedBackupPath, File file)
			throws Exception {
		boolean firstLine = true;
		FileWriter fw = null;
		FileReader fr = null;
		BufferedWriter bw = null;
		BufferedReader br = null;
		try {
			File filePath = new File(parsedBackupPath);
			if (!filePath.exists()) {
				filePath.mkdir();
			}

			File newFile = new File(parsedBackupPath + File.separator
					+ file.getName());
			if (!newFile.exists()) {
				newFile.createNewFile();
			}

			// fr = new FileReader(file);
			fw = new FileWriter(newFile);
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					file), "GB2312"));
			bw = new BufferedWriter(fw);

			String tmpbuf = null;
			while ((tmpbuf = br.readLine()) != null) {
				if (!firstLine) {
					bw.newLine();
				}
				if (firstLine) {
					firstLine = false;
				}
				bw.write(tmpbuf);
				bw.flush();
			}
			log.debug("备份文件[" + file.getName() + "]至路径 [" + parsedBackupPath
					+ "]成功!");
			System.out.println("备份文件[" + file.getName() + "]至路径 ["
					+ parsedBackupPath + "]成功!");
		} catch (Exception e) {
			log.debug("备份文件[" + file.getName() + "]至路径 [" + parsedBackupPath
					+ "]失败!" + e.getMessage());
			System.out.println("备份文件[" + file.getName() + "]至路径 ["
					+ parsedBackupPath + "]失败!" + e.getMessage());
			throw new Exception("备份文件[" + file.getName() + "]至路径 ["
					+ parsedBackupPath + "]失败!");
		} finally {
			try {
				bw.close();
				br.close();
			} catch (Exception e) {
				throw new Exception("关闭文件流操作失败:" + e.getMessage());
			}
		}
		file.delete();
	}

	/********************************* 新增加的方法 ***********************************/
	/**
	 * 校验远程FTP服务器指定目录下是否存在文件
	 * 
	 * @param sendPath
	 * @param tranPrtc
	 * @param c_ffield
	 * @return
	 * @throws Exception
	 */
	public static boolean isExitsFiles(CoreCfgField c_ffield) throws Exception {
		EbillsClient client = null;
		try {
			if ("FTP".equals(c_ffield.getTranPrtc())) {
				client = new EbillsFtpClient(c_ffield.getFtpServer(),
						c_ffield.getFtpUser(), c_ffield.getFtpPassword(), null);
			} else if ("SFTP".equals(c_ffield.getTranPrtc())) {
				client = new EbillsSFtpClient(c_ffield.getSftpServer(),
						c_ffield.getSftpPort(), c_ffield.getSftpUser(),
						c_ffield.getSftpPassword(), null);
			} else {
				throw new Exception("请科技部重新配置获取文件的方式,不支持现在["
						+ c_ffield.getTranPrtc() + "]的传输方式!");
			}

			try {
				client.connect();
			} catch (Exception e) {
				throw new Exception("请科技部确认网络是否正常,所需用户名密码是否正确!");
			}
			List list = client.getFileNames(c_ffield.getSendPath());
			if (list.size() > 0) {
				client.disconnect();
				return true;
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return false;
	}

	/**
	 * 根据传递的日期，删除5天前的核心ftp上的时间文件夹
	 * 
	 * @param c_ffield
	 * @param tranDate
	 * @return
	 * @throws Exception
	 */
	public static boolean delFtpFile5DaysBefore(CoreCfgField c_ffield,
			String tranDate) throws Exception {
		boolean flag = false;
		EbillsClient ftpClient = null;
		try {
			if ("FTP".equals(c_ffield.getTranPrtc())) {
				ftpClient = new EbillsFtpClient(c_ffield.getFtpServer(),
						c_ffield.getFtpUser(), c_ffield.getFtpPassword(), null);
			} else if ("SFTP".equals(c_ffield.getTranPrtc())) {
				ftpClient = new EbillsSFtpClient(c_ffield.getSftpServer(),
						c_ffield.getSftpPort(), c_ffield.getSftpUser(),
						c_ffield.getSftpPassword(), null);
			} else {
				throw new Exception("请科技部重新配置获取文件的方式,不支持现在["
						+ c_ffield.getTranPrtc() + "]的传输方式!");
			}

			try {
				ftpClient.connect();
			} catch (Exception e) {
				throw new Exception("请科技部确认网络是否正常,所需用户名密码是否正确!");
			}
			// 根据烟台银行要求，下载下来的远程FTP主机上的数据文件先不删除，隔5天后再删除
			Calendar calendar = Calendar.getInstance();
			java.util.Date date = new SimpleDateFormat("yyyyMMdd")
					.parse(tranDate);
			calendar.setTime(date);
			// 找到5天前的文件夹，删除掉此文件夹
			calendar.add(Calendar.DATE, -5); // 得到前5天
			String befor5Day = new SimpleDateFormat("yyyyMMdd").format(calendar
					.getTime());
			System.out.println("befor5Day:" + befor5Day);
			// 取得核心FTP时间文件夹的上级目录：山东城商联盟：/odsdata/sfts/send/chaps/20121212/801
			String sendPathRoot = c_ffield.getSendPath().substring(0,
					c_ffield.getSendPath().lastIndexOf("/"));
			sendPathRoot = sendPathRoot.substring(0,
					sendPathRoot.lastIndexOf("/") + 1);
			ftpClient.changeWorkingDirectory(sendPathRoot,
					"To Ftp Time File's Parent Ftp File!");
			if (ftpClient.isExist(sendPathRoot, befor5Day)) {
				System.out.println("存在5天前的文件夹:"
						+ ftpClient.printWorkingDirectory() + "/" + befor5Day
						+ ",将其删除.");
				ftpClient.changeWorkingDirectory(sendPathRoot + befor5Day,
						"To Before Five Days!");
				// 获取类似 /odsdata/sfts/send/chaps/20121212/801 目录下的所有文件
				List delFile = ftpClient.getFileNames(sendPathRoot + befor5Day
						+ "/" + c_ffield.getBankCode());
				for (int i = 0; i < delFile.size(); i++) {
					System.out.println("删除文件:" + delFile.get(i));
					ftpClient.deleteFileByPath(sendPathRoot + befor5Day + "/"
							+ c_ffield.getBankCode(),
							String.valueOf(delFile.get(i)));
				}
				// 删除类似 /odsdata/sfts/send/chaps/20121212/801 的文件夹
				ftpClient.changeWorkingDirectory(sendPathRoot + befor5Day,
						"To Ftp Time File's Parent Ftp File Too!");
				ftpClient.removeDirectory(c_ffield.getBankCode());
				System.out.println("删除5天前的文件夹:"
						+ ftpClient.printWorkingDirectory());
				ftpClient.changeWorkingDirectory(sendPathRoot,
						"To Ftp Time File's Parent Ftp File Too!");
				ftpClient.removeDirectory(befor5Day);
			}
			ftpClient.disconnect();
			flag = true;
		} catch (Exception e) {
			flag = false;
			System.out.println("根据传递的日期，删除5天前的核心ftp上的时间文件夹:" + e.getMessage());
			throw new Exception("根据传递的日期，删除5天前的核心ftp上的时间文件夹:" + e.getMessage());
		}
		return flag;
	}

	/**
	 * 在本地指定目录下写入一个文件，作为令牌文件
	 * 
	 * @param path
	 * @return
	 */
	public static File createTockenFile(String path, String tokenName)
			throws Exception {
		try {
			File lockFile = new File(path + File.separator + tokenName);
			if (lockFile.exists() && lockFile.isFile())
				return lockFile;
			lockFile.createNewFile();
			OutputStream out = new FileOutputStream(lockFile);
			PrintWriter write = new PrintWriter(out);// 写入一点数据，防止空文件无法被检测到！
			write.println("This is Ebills declare " + tokenName + " lock File!");
			write.println("Don't delete it!");
			write.println("Author:Hudan");
			write.flush();
			write.close();
			out.close();
			return lockFile;
		} catch (Exception e) {
			throw new Exception("创建令牌文件" + tokenName + "失败!" + e.getMessage());
		}
	}

	/**
	 * @desc 获取当前系统日期
	 * @return
	 */
	public static java.sql.Date getCurrentDate() throws Exception {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			return toSQLDate(df.parse(CommonUtil
					.getSysFld(EbpConstants.WORK_DATE)));
		} catch (Exception e) {
			throw new Exception("获得当前日期:", e);
		}
	}

	/**
	 * @desc java.util.Date 转换成 java.sql.Date
	 * @param utilDate
	 * @return
	 */
	public static java.sql.Date toSQLDate(java.util.Date utilDate) {
		return new java.sql.Date(utilDate.getTime());
	}

	/**
	 * @desc 获取当前系统日期
	 * @return
	 */
	public static java.sql.Date getDate(String date) throws Exception {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			return toSQLDate(df.parse(date));
		} catch (Exception e) {
			throw new Exception("获得当前日期:", e);
		}
	}

	/**
	 * @desc 获取当前系统日期
	 * @return
	 */
	public static Timestamp getCurrentTime() throws Exception {
		try {
			/*
			 * Timestamp aa = new Timestamp(System.currentTimeMillis());
			 * SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
			 * String sysdate = sdf.format(aa); SimpleDateFormat df = new
			 * SimpleDateFormat("yyyyMMdd"); sysdate =
			 * df.format(getCurrentDate())+sysdate.split("_")[1];
			 */
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			return getTime(df.format(getCurrentDate()));
		} catch (Exception e) {
			throw new Exception("获得当前日期:", e);
		}
	}

	/**
	 * 将字符串转换为Timestamp
	 * 
	 * @param dateStr
	 * @return
	 * @throws Exception
	 * @throws ParseException
	 */
	public static Timestamp getTime(String dateStr) throws Exception {
		Timestamp ts = null;
		try {
			Timestamp aa = new Timestamp(System.currentTimeMillis());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
			String sysdate = sdf.format(aa);
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			sysdate = dateStr + sysdate.split("_")[1];
			ts = new Timestamp((df.parse(sysdate)).getTime());
		} catch (Exception e) {
			throw new Exception("将字符串转换为Timestamp类型失败:" + e.getMessage());
		}
		return ts;
	}

	/**
	 * @desc java.sql.Date转换成String
	 * @param date
	 *            java.sql.Date对象
	 * @param formater
	 *            需要转换的格式(如:yyyyMMdd)
	 * @return
	 */
	public static String dateToString(Date date, String formater) {
		String dateStr = "";
		DateFormat sdf = new SimpleDateFormat(formater);
		try {
			dateStr = sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateStr;
	}

	/**
	 * 取当天最小时间
	 * 
	 * @param dateStr
	 * @return
	 * @throws Exception
	 * @throws ParseException
	 */
	public static java.util.Date getMinTime(String dateStr) throws Exception {
		java.util.Date ts = null;
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			ts = df.parse(dateStr + "000000");
		} catch (Exception e) {
			throw new Exception("将字符串转换为Timestamp类型失败:" + e.getMessage());
		}
		return ts;
	}

	/**
	 * 取当天最大时间
	 * 
	 * @param dateStr
	 * @return
	 * @throws Exception
	 * @throws ParseException
	 */
	public static java.util.Date getMaxTime(String dateStr) throws Exception {
		java.util.Date ts = null;
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			ts = df.parse(dateStr + "595959");
		} catch (Exception e) {
			throw new Exception("将字符串转换为Timestamp类型失败:" + e.getMessage());
		}
		return ts;
	}

	/**
	 * 取上一日
	 * 
	 * @param dateStr
	 * @return
	 * @throws Exception
	 * @throws ParseException
	 */
	public static String getBeforeDate(String dateStr) throws Exception {
		String date = null;
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			java.util.Date ts = df.parse(dateStr);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(ts);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			date = df.format(calendar.getTime());

		} catch (Exception e) {
			throw new Exception("将字符串转换为Timestamp类型失败:" + e.getMessage());
		}
		return date;
	}

	/**
	 * @desc 将形成的申报文件夹打包
	 * @param downDir
	 *            打包的文件夹根目录
	 * @param secFold
	 *            需要打包的文件(夹)
	 * @param isDel
	 *            是否需要将打包文件及对应的根目录删除
	 * @ejb.interface-method
	 */
	public static void formZip(String downDir, String secFold, boolean isDel) {
		log.info("formZip:" + downDir + " secFold:" + secFold);
		try {
			FileOutputStream fileOut = new FileOutputStream(downDir + ".zip");
			ZipOutputStream outputStream = new ZipOutputStream(fileOut);

			File rootFile = new File(downDir + File.separator + secFold);
			if (rootFile.isDirectory()) {
				File lists[] = rootFile.listFiles();
				for (int i = 0; i < lists.length; i++) {
					FileInputStream fileIn = new FileInputStream(lists[i]);
					outputStream.putNextEntry(new ZipEntry(rootFile.getName()
							+ File.separator + lists[i].getName()));
					byte[] buffer = new byte[1024];
					int n;
					while ((n = fileIn.read(buffer)) != -1) {
						outputStream.write(buffer, 0, n);
					}
					outputStream.closeEntry();
					fileIn.close();
				}
			}
			outputStream.close();

			// 删除源文件
			if (isDel) {
				// 将源文件夹及该文件夹下的所有文件删除
				delFileAll(downDir);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * @desc 删除某制定文件(如果是文件夹,则递归删除该文件夹下的所有文件)
	 * @param filepath
	 * @throws IOException
	 */
	public static void delFileAll(String filePath) {
		File file = new File(filePath);
		// 判断是文件还是目录
		if (file.isFile()) {
			file.delete();
		} else {
			// 若目录下没有文件则直接删除
			if (file.listFiles().length == 0) {
				file.delete();

				// 若有则把文件放进数组，并判断是否有下级目录
			} else {
				File delFile[] = file.listFiles();
				int i = file.listFiles().length;
				for (int j = 0; j < i; j++) {
					if (delFile[j].isDirectory()) {
						// 递归调用del方法并取得子目录路径
						delFileAll(delFile[j].getAbsolutePath());
					}
					delFile[j].delete();// 删除文件
				}
			}
		}
		if (file.exists())
			file.delete();
	}

	public static boolean ObjectIsNull(Object obj) {
		if (obj == null || "".equals(obj))
			return true;
		return false;
	}

	/**
	 * 写入XML
	 * 
	 * @param doc
	 * @param fileName
	 * @throws IOException
	 */
	public static void writeXmlFile(Document doc, String fileName)
			throws IOException {
		FileWriter f = new FileWriter(fileName);
		String tmp = doc.asXML();
		tmp = tmp.replaceAll("'", "&apos;");
		tmp = tmp.replaceAll("\"", "&quot;");
		tmp = tmp.replaceFirst(
				"version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;",
				"version=\"1.0\" encoding=\"gb18030\"");
		f.write(tmp);
		f.close();
	}

	/**
	 * 根据接口规范替换字符串
	 * 
	 * @param nomalString
	 * @return
	 */
	public static String StringChange(String nomalString) {
		if (nomalString == null || "".equals(nomalString)
				|| "null".equals(nomalString)) {
			return "";
		} else {
			// 去掉字符串中的换行
			nomalString = nomalString.replaceAll("\r\n", "")
					.replaceAll("\r", "").replaceAll("\n", "");
			nomalString = nomalString.replaceAll("<", "&lt;");
			nomalString = nomalString.replaceAll("&", "&amp;");
			nomalString = nomalString.replaceAll(">", "&gt;");
			nomalString = nomalString.replaceAll("\"", "&quot;");
			nomalString = nomalString.replaceAll("\'", "&apos;");
			return nomalString;
		}
	}

	/**
	 * 写文件
	 * 
	 * @param path
	 *            文件的路径
	 * @param content
	 *            写入文件的内容
	 */
	public static void writerText(String path, String content) {

		File dirFile = new File(path);

		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		try {
			// new FileWriter(path + "t.txt", true) 这里加入true 可以不覆盖原有TXT文件内容 续写
			BufferedWriter bw1 = new BufferedWriter(new FileWriter(path
					+ "SBALL.sql", true));
			bw1.write(content);
			bw1.flush();
			bw1.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据当前日期获得文件夹名
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getFoleName(Date fileDate) throws Exception {
		Timestamp aa = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String xmlFile = "";
		xmlFile = sdf.format(aa);
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		xmlFile = df.format(fileDate) + "_" + xmlFile.split("_")[1];
		return xmlFile;
	}

	/**
	 * 解析XML转成MAP
	 * 
	 * @param file
	 * @return
	 * @throws EbillsException
	 */
	public static Map<String, Object> Dom2Map(File file) throws Exception {

		Map<String, Object> map = new LinkedHashMap<String, Object>();

		SAXReader reader = new SAXReader();
		Document doc = reader.read(file);
		if (doc == null)
			return map;

		Element root = doc.getRootElement();		// 获取根节点  
		for (Iterator iterator = root.elementIterator(); iterator.hasNext();) {
			Element e = (Element) iterator.next();
			List list = e.elements();
			if (list.size() > 0) {
				map.put(e.getName(), Dom2Map(e));
			} else {
				map.put(e.getName(), e.getText());
			}
		}
		return map;
	}

	/**
	 * 解析XML转成MAP
	 * 
	 * @param e
	 * @return
	 */
	public static Map Dom2Map(Element e) {
		Map map = new HashMap();
		List list = e.elements();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				List mapList = new ArrayList();
				Element iter = (Element) list.get(i);

				if (iter.elements().size() > 0) {
					Map m = Dom2Map(iter);
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(m);
						}
						if (obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = (List) obj;
							mapList.add(m);
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), m);
				} else {
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(iter.getText());
						}
						if (obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = (List) obj;
							mapList.add(iter.getText());
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), iter.getText());
				}
			}
		} else
			map.put(e.getName(), e.getText());
		return map;
	}

	/**
	 * 解析XML转成List
	 * 
	 * @param e
	 * @return
	 */
	public static List Dom2List(Element nodesElement) {
		List domList = new ArrayList();
		try {
			List nodes = nodesElement.elements();
			for(int i=0; i<nodes.size(); i++) {
				
			}
			for (Iterator its = nodes.iterator(); its.hasNext();) {
				Element nodeElement = (Element) its.next();
				Map map = Dom2Map(nodeElement);
				domList.add(map);
				map = null;
			}
			nodes = null;
			nodesElement = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return domList;
	}

	public static void main(String[] args) {
		File file = null;
//		file = new File("C:\\Users\\Hudan\\Desktop\\ttt\\ACCTT37068700770114082601ERR\\ACCCB37068700770114082601ERR.XML");
		file = new File ("C:\\Users\\Hudan\\Desktop\\ACCTT37068700770114081501ERR\\ACCCA37068700770114081501ERR.XML");
//		file = new File ("C:\\Users\\Hudan\\Desktop\\ACCTT37068700770114082501ERR\\ACCCB37068700770114082501ERR.XML");
		try {
			Map dataMap = Dom2Map(file);
			System.out.println(dataMap);
	
			/** 读取是否有文件格式错误反馈信息,并保存到错误反馈信息表中 */
			List fts = null;
			int formaterrs = Integer.parseInt((String)dataMap.get("FORMATERRS"));
			if(formaterrs > 0) {
				Map formats = (Map) dataMap.get("FORMATS");
				if(formaterrs ==1 ) {
					fts = new ArrayList();
					fts.add(formats.get("FORMAT"));
				} else {
					fts = (List) formats.get("FORMAT");
				}
				System.out.println("fts:"+fts);
			}
			/** 读取具体错误反馈信息,并保存到错误反馈信息表中 */
			List recs = null;
			Map errsRecords = (Map) dataMap.get("ERRRECORDS");			// 错误信息根节点
			if(Integer.parseInt((String)dataMap.get("FALRECORDS"))==1 ) {
				Map recMap = (Map) errsRecords.get("REC");
				recs = new ArrayList();
				recs.add(recMap);
			} else {
				recs = (List) errsRecords.get("REC");					// 错误信息记录节点
			}
			System.out.println(recs);
			if(recs != null){
				for (int k = 0; k < recs.size(); k++) {
					Map errRec = (Map)recs.get(k);
					
					Map errfields = (Map) errRec.get("ERRFIELDS");		// 错误描述根节点
					List errs = null;
					if(errfields.get("ERR") instanceof Map) {
						errs = new ArrayList();
						errs.add((Map)errfields.get("ERR"));
					} else{
						errs = (List) errfields.get("ERR");
					}
					System.out.println(errs);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}