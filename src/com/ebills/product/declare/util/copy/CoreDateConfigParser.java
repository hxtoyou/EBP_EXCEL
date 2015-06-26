package com.ebills.product.declare.util.copy;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.ebills.util.EbillsCfg;
import com.ebills.util.EbillsException;
import com.ebills.util.EbillsLog;


/*
 * 功能：读取配置文件
 * 
 */
public class CoreDateConfigParser {
	private static EbillsLog log = new EbillsLog(CoreDateConfigParser.class.getName());
	private static CoreCfgField field;

	/**
	 * 根据传入的日期字符串,实例化报送平台配置信息
	 * @param tranDate	日期字符串
	 * @return
	 */
	public static CoreCfgField getInstance(String tranDate) {
		field = new CoreCfgField();
		try {
			loadFieldConfig(tranDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return field;
	}
	
	/**
	 * 解析配置文件
	 * @param tranDate 日期字符串
	 * @throws Exception 
	 */
	synchronized static void loadFieldConfig(String tranDate) throws Exception {
		try {
			log.debug("解析配置文件---->   加载配置文件BsptCfg.xml start"); 
			DownloadXMLParser parser = new DownloadXMLParser();
			File file = new File(EbillsCfg.getHomeDir()+"BsptCfg.xml");
			parser.parseFile(file); 
			List nodes = DownloadXMLParser.getChildElements((Element) parser.getRootNode(), "Field");  
			if (!nodes.isEmpty()) {
				Node tmpNode = (Node) nodes.get(0);
				// 执行机构
				field.setOrgNo(DownloadXMLParser.getText((Element) parser.getChildNode(tmpNode, "orgNo")));
				// 是否晚间批量下载
				field.setIsStartTimeFlag(DownloadXMLParser.getText((Element) parser.getChildNode(tmpNode, "isStartTimeFlag")));
				// 间 格式：HHmmss
				field.setStartTime(DownloadXMLParser.getText((Element) parser.getChildNode(tmpNode, "startTime")));
				// 是否实时下载
				field.setIsIntervalFlag(DownloadXMLParser.getText((Element) parser.getChildNode(tmpNode, "isIntervalFlag")));
				// 实时下载时间间隔 单位：秒
				field.setIntervalTime(DownloadXMLParser.getText((Element) parser.getChildNode(tmpNode, "intervalTime")));
				
				/*********************FTP/SFTP 服务器配置信息*********************/
				// 协议类型 FTP/SFTP 
				field.setTranPrtc(DownloadXMLParser.getText((Element) parser.getChildNode(tmpNode, "tranPrtc")));
				/*********************FTP服务器配置信息*********************/
				//FTP IP地址
				field.setFtpServer(DownloadXMLParser.getText((Element) parser.getChildNode(tmpNode, "ftpServer")));
				//FTP用户名
				field.setFtpUser(DownloadXMLParser.getText((Element) parser.getChildNode(tmpNode, "ftpUser")));
				//FTP 密码
				field.setFtpPassword(DownloadXMLParser.getText((Element) parser.getChildNode(tmpNode, "ftpPassword")));
				/*********************SFTP服务器配置信息*********************/
				//SFTP IP地址
				field.setSftpServer(DownloadXMLParser.getText((Element) parser.getChildNode(tmpNode, "sftpServer")));
				//SFTP 端口
				field.setSftpPort(DownloadXMLParser.getText((Element) parser.getChildNode(tmpNode, "sftpPort")));
				//SFTP 用户名
				field.setSftpUser(DownloadXMLParser.getText((Element) parser.getChildNode(tmpNode, "sftpUser")));
				//SFTP 密码
				field.setSftpPassword(DownloadXMLParser.getText((Element) parser.getChildNode(tmpNode, "sftpPassword")));
				// 银行编号 加入山东城商联盟的银行用到 比如:齐商银行在联盟的编号为801
				field.setBankCode(DownloadXMLParser.getText((Element) parser.getChildNode(tmpNode, "bankCode")));
				// 远程发送路径     由于山东城商行联盟的ftp路径根据日期动态变动,这里也将路径改为根据日期时间动态变动 如：/odsdata/sfts/send/chaps/20120901/811/
				String sendPath = DownloadXMLParser.getText((Element) parser.getChildNode(tmpNode, "sendPath"));
				// 银行编号 加入山东城商联盟的银行用到 
				sendPath += "/"+ tranDate+"/"+field.getBankCode();			//齐商在城商行联盟的成员行号为801
				field.setSendPath(sendPath);
				System.out.println("得到核心FTP下载目录："+sendPath);
				// 远程令牌所在路径  令牌所在路径默认情况下应与远程发送路径相同 
				String tokenPath = DownloadXMLParser.getText((Element) parser.getChildNode(tmpNode, "tokenPath"));
				tokenPath += "/"+ tranDate+"/"+field.getBankCode();;
				field.setTokenPath(tokenPath);
				// 远程令牌文件名
				field.setTokenName(DownloadXMLParser.getText((Element) parser.getChildNode(tmpNode, "tokenName")));
				// 是否下载ftp文件后,删除FTP上的文件
				field.setIsDelFtpFile(DownloadXMLParser.getText((Element) parser.getChildNode(tmpNode, "isDelFtpFile")));
				// 核心的ftp时间文件夹是否5天后删除
				field.setIsDel5DaysFtpFile(DownloadXMLParser.getText((Element) parser.getChildNode(tmpNode, "isDel5DaysFtpFile")));
				
				// 本地接收文件路径 
				field.setReceivePath(DownloadXMLParser.getText((Element) parser.getChildNode(tmpNode, "receivePath")));
				// 本地接收文件备份路径 
				field.setReceivePathBak(DownloadXMLParser.getText((Element) parser.getChildNode(tmpNode, "receivePathBak")));
				// 本地自动接收路径令牌1
				field.setToken1(DownloadXMLParser.getText((Element) parser.getChildNode(tmpNode, "token1")));
				// 本地手工接收路径令牌2
				field.setToken2(DownloadXMLParser.getText((Element) parser.getChildNode(tmpNode, "token2")));
				// 本地手工接收结售汇路径令牌3
				field.setToken3(DownloadXMLParser.getText((Element) parser.getChildNode(tmpNode, "token3")));
				
				// 是否在从 FTP目录下下载文件后,上传令牌文件到FTP指定目录 ,如果为Y,则将根据下载的方式是自动还是手动,上传对应的令牌文件到ftp目录下
				field.setIsLoadToken(DownloadXMLParser.getText((Element) parser.getChildNode(tmpNode, "isLoadToken")));
				// 上传到远程FTP对应目录下的已自动下载的令牌文件
				field.setAutoToken(DownloadXMLParser.getText((Element) parser.getChildNode(tmpNode, "autoToken")));
				// 上传到远程FTP对应目录下的已手动下载的令牌文件
				field.setHandToken(DownloadXMLParser.getText((Element) parser.getChildNode(tmpNode, "handToken")));
			}
			//validation
			String orgNo = field.getOrgNo();
			String isStartTimeFlag = field.getIsStartTimeFlag();
			String startTime = field.getStartTime();
			String isIntervalFlag = field.getIsIntervalFlag();
			String intervalTime = field.getIntervalTime();
			
			String tranPrtc = field.getTranPrtc();
			String ftpServer = field.getFtpServer();
			String ftpUser = field.getFtpUser();
			String ftpPassword = field.getFtpPassword();
			String sftpServer = field.getSftpServer();
			String sftpPort = field.getSftpPort();
			String sftpUser = field.getSftpUser();
			String sftpPassword = field.getSftpPassword();
			String bankCode = field.getBankCode();
			String sendPath = field.getSendPath();
			String tokenPath = field.getTokenPath();
			String tokenName = field.getTokenName();
			String isDelFtpFile = field.getIsDelFtpFile();
			String isDel5DaysFtpFile = field.getIsDel5DaysFtpFile();
			
			String receivePath = field.getReceivePath();
			String receivePathBak = field.getReceivePathBak();
			String token1 = field.getToken1();
			String token2 = field.getToken2();
			String token3 = field.getToken3();
			
			String isLoadToken = field.getIsLoadToken();
			String autoToken = field.getAutoToken();
			String handToken = field.getHandToken();
			
			String message = "";
			
			if("".equals(orgNo)){
				message += "执行机构不能为空,请修改!\n";
			}else if(!orgNo.matches("^[0-9]*$")){
				message += "执行机构编号格式错误,请修改!\n";
			}
			if("".equals(isStartTimeFlag)){
				message += "是否晚间批量下载不能为空,请修改!\n";
			}
			if("Y".equals(isStartTimeFlag)){
				if("".equals(startTime)){
					message += "晚间批量下载时间不能为空,请修改!\n";
				}else if(!startTime.matches("^([0-1][0-9]|2[0-3])([0-5][0-9])([0-5][0-9])$")){
					message += "晚间批量下载时间格式错误,请修改!\n";
				}
			}
			if("".equals(isIntervalFlag)){
				message += "是否实时下载不能为空,请修改!\n";
			}
			if("Y".equals(isIntervalFlag)){
				if("".equals(intervalTime)){
					message += "实时下载时间间隔不能为空,请修改!\n";
				}else if(!intervalTime.matches("^[0-9]*$")){
					message += "实时下载时间间隔必须是正整数,请修改!\n";
				}
			}
			if("".equals(tranPrtc)){
				message += "FTP协议类型不能为空,请修改!\n";
			}else if(!("FTP".equals(tranPrtc) || "SFTP".equals(tranPrtc))){
				message += "协议类型必须为FTP或者SFTP,请修改!\n";
			}
			if("FTP".equals(tranPrtc)){
				if("".equals(ftpServer)){
					message += "FTP服务器地址不能为空,请修改!\n";
				}else if(!ftpServer.matches("^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])"
						+ "\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)"
						+ "\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)"
						+ "\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$")){
					message += "FTP服务器地址格式错误,请修改!\n";
				}
				if("".equals(ftpUser)){
					message += "FTP用户名不能为空,请修改!\n";
				}
				if("".equals(ftpPassword)){
					message += "FTP密码不能为空,请修改!\n";
				}
			}else if("SFTP".equals(tranPrtc)){
				if("".equals(sftpServer)){
					message += "SFTP服务器地址不能为空,请修改!\n";
				}else if(!sftpServer.matches("^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])"
						+ "\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)"
						+ "\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)"
						+ "\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$")){
					message += "SFTP服务器地址格式错误,请修改!\n";
				}
				if("".equals(sftpPort)){
					message += "SFTP端口不能为空,请修改!\n";
				}else if(!(sftpPort.matches("^[0-9]*$") && Integer.parseInt(sftpPort)<=65535 && Integer.parseInt(sftpPort)>=1)){
					message += "SFTP端口格式错误,请修改!\n";
				}
				if("".equals(sftpUser)){
					message += "SFTP用户名不能为空,请修改!\n";
				}	
				if("".equals(sftpPassword)){
					message += "SFTP密码不能为空,请修改!\n";
				}
			}
			if("".equals(bankCode)){
				message += "银行编号不能为空,请修改!\n";
			}	
			if("".equals(sendPath)){
				message += "远程发送路径不能为空,请修改!\n";
			}	
			if("".equals(tokenPath)){
				message += "远程令牌所在路径不能为空,请修改!\n";
			}			
			if("".equals(tokenName)){
				message += "远程令牌文件名不能为空,请修改!\n";
			}
			if("".equals(isDelFtpFile)){
				message += "是否下载ftp文件后ftpClient删除FTP上的文件标识不能为空,请修改!\n";
			}
			if("".equals(isDel5DaysFtpFile)){
				message += "是否5天后删除核心的ftp时间文件夹标识不能为空,请修改!\n";
			}
			if("".equals(receivePath)){
				message += "本地接收文件路径不能为空,请修改!\n";
			}else{
				File localPath = new File(receivePath);
			    if(!localPath.exists()) {
			    	localPath.mkdir();
			    }
			}
			if("".equals(receivePathBak)){
				message += "本地接收文件备份路径不能为空,请修改!\n";
			}else{
				File localPath = new File(receivePathBak);
			    if(!localPath.exists()) {
			    	localPath.mkdir();
			    }
			}
			if("".equals(token1)){
				message += "本地自动接收路径令牌1不能为空,请修改!\n";
			}
			if("".equals(token2)){
				message += "本地手工接收路径令牌2不能为空,请修改!\n";
			}
			if("".equals(token3)){
				message += "本地手工接收结售汇路径令牌3不能为空,请修改!\n";
			}
			if("".equals(isLoadToken)){
				message += "是否在从 FTP目录下下载文件后,上传令牌文件到FTP指定目录标识不能为空,请修改!\n";
			}
			if("".equals(autoToken)){
				message += "上传到远程FTP对应目录下的已自动下载的令牌文件不能为空,请修改!\n";
			}
			if("".equals(handToken)){
				message += "上传到远程FTP对应目录下的已手动下载的令牌文件不能为空,请修改!\n";
			}
			
			if(message!=null && !"".equals(message)){
				throw new Exception(message);
			}
			//校验完毕
			System.out.println("解析配置文件---->   解析配置文件"+file.getName()+"完毕.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("解析配置文件---->   解析配置文件失败!请核查!");
		}
	}

	public static CoreCfgField getField() {
		return field;
	}

	public static void setField(CoreCfgField field) {
		CoreDateConfigParser.field = field;
	}

	
	/**
	 * @param args
	 * @throws EbillsException
	 */
	public static void main(String[] args) throws EbillsException {
	}
}
