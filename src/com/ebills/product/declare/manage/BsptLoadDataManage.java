package com.ebills.product.declare.manage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ebills.commons.SerialNoFactory;
import com.ebills.declare.bussiness.DclNoFactory;
import com.ebills.declare.inf.DeclareInf;
import com.ebills.product.declare.dao.BsptFileDAO;
import com.ebills.product.declare.domain.BsptCommon;
import com.ebills.product.declare.domain.ReceiveBsptFile;
import com.ebills.product.declare.parse.ParseFjmgrck;
import com.ebills.product.declare.parse.ParseFjmjgck;
import com.ebills.product.declare.parse.ParseGnwhdkbd;
import com.ebills.product.declare.parse.ParseGnwhdkqy;
import com.ebills.product.declare.parse.ParseWhzyrmbdkbd;
import com.ebills.product.declare.parse.ParseWhzyrmbdkqy;
import com.ebills.product.declare.parse.ParseWzyexx;
import com.ebills.product.declare.parse.ParseZhkgh;
import com.ebills.product.declare.parse.ParseZhszy;
import com.ebills.product.declare.util.CIConstants;
import com.ebills.product.declare.util.CoreCfgField;
import com.ebills.product.declare.util.CoreDateConfigParser;
import com.ebills.product.declare.util.DataUtil;
import com.ebills.product.declare.util.GeneralCalc;
import com.ebills.util.EbillsException;
import com.ebills.util.EbillsLog;
import com.ebills.utils.CommonUtil;
import com.ebills.utils.EbpConstants;
import com.ebills.utils.EbpDao;
import com.ebills.utils.OutPutBean;

/**
 * 资本取数后台实现类(此后台逻辑属于烟台银行申报版本，可能各个银行会有不同的调整，请知悉)
 * 
 * @author Administrator
 * @date 2013-12-4 下午2:53:53
 * 
 */
public class BsptLoadDataManage implements DeclareInf {
	private String className = this.getClass().getName();
	private EbillsLog log = new EbillsLog(className);
	private CoreCfgField Core_cffield = null;

	@Override
	public OutPutBean execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String todo = request.getParameter("todo");
		Method[] met = this.getClass().getMethods();
		OutPutBean out = null;
		for (int i = 0; i < met.length; i++) {
			if (met[i].getName().equals(todo)) {
				out = (OutPutBean) met[i].invoke(this, request, response);
			}
		}
		return out;
	}

	/**
	 * 下载核心资本数据文件
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public OutPutBean reciveFile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BsptFileDAO bsptdao = new BsptFileDAO();
		String orgNo = request.getParameter("curOrgNo");
		String tranDate = (request.getParameter("fileDate")).replace("-", "");
		String beforeDate = DataUtil.getBeforeDate(tranDate);
		List blist = bsptdao.getBsptKz(beforeDate);
		if (blist == null || blist.size() == 0) {
			throw new Exception("未下载过前一日的文件，不允许进行当日下载!");
		}
		List list = bsptdao.getBsptKz(tranDate);
		if (list != null && list.size() > 0) {
			throw new Exception("已经下载过该日期的文件，不允许重复下载!");
		}
		doReciveFile(orgNo, tranDate);
		bsptdao.creatBsptKz(tranDate);
		return new OutPutBean(CommonUtil.MapToJson(new HashMap()));
	}

	/**
	 * 下载核心资本数据文件
	 * 
	 * @param orgNo
	 * @param tranDate
	 * @throws Exception
	 */
	public void doReciveFile(String orgNo, String tranDate) throws Exception {
		// 初始化配置
		Core_cffield = CoreDateConfigParser.getInstance(tranDate);
		// 检测是否在执行自动下载和解析
		File token1File = new File(Core_cffield.getReceivePath()
				+ File.separator + Core_cffield.getToken1());
		if (token1File.exists()) {
			System.out.println("正在执行自动下载和解析，本次手动下载中止,请等自动下载和解析完毕后再次执行手工下载!");
			throw new Exception("正在执行自动下载和解析,本次手动下载中止,请等自动下载和解析完毕后再次执行手工下载!");
		}
		// 检测是否在执行手动下载和解析
		File token2File = new File(Core_cffield.getReceivePath()
				+ File.separator + Core_cffield.getToken2());
		if (token2File.exists()) {
			System.out
					.println("其他人员正在执行手动下载和解析,本次手动下载中止,请等其他人员下载和解析完毕后再次执行手工下载!");
			throw new Exception(
					"其他人员正在执行手动下载和解析,本次手动下载中止,请等其他人员下载和解析完毕后再次执行手工下载!");
		}
		// 校验远程目录是否存在文件
		try {
			if (!DataUtil.isExitsFiles(Core_cffield))
				throw new Exception("远程服务器中暂时没有文件,请稍后下载!");
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		// 开始执行手动下载，先加锁保护，后下载
		if (!token2File.exists()) {
			token2File.createNewFile();
		}
		try {
			// 下载文件(ftp配置信息，是否有令牌文件， 下载时间)
			DataUtil.downFile(Core_cffield, true, tranDate);
		} catch (Exception e) {
			// 删除手工令牌,不然自动下载失败 (新加的)
			token2File.delete();
			log.debug("手工下载采集文件错误::::" + e.getMessage());
			throw new Exception("手工下载采集文件错误[" + e.getMessage() + "]");
		}

		// 获取本地接收目录下的文件，并将接收的文件信息保存到对应数据库中
		File receivePath = new File(Core_cffield.getReceivePath());
		if (receivePath.isDirectory()) {
			File receiveFiles[] = receivePath.listFiles();
			if (receiveFiles != null) {
				BsptFileDAO dao = new BsptFileDAO();
				for (int i = 0; i < receiveFiles.length; i++) {
					if (receiveFiles[i].isFile()
							&& null != receiveFiles[i].getName()
							&& !token2File.getName().equals(
									receiveFiles[i].getName())) {
						// 判断下载到得文件是否与接收文件表的数据重复
						String fileName = receiveFiles[i].getName();
						ReceiveBsptFile receiveFile = new ReceiveBsptFile();
						receiveFile.setFileName(fileName);
						int index = -1;
						for (int j = 0; j < fileName.length(); j++) {
							if ((int) fileName.charAt(j) <= 57
									&& (int) fileName.charAt(j) >= 48) {
								index = j;
								break;
							}
							if ((int) fileName.charAt(j) == 46) {
								index = j;
								break;
							}
						}
						if (index > 0) {
							receiveFile.setDataType(fileName
									.substring(0, index));
						} else {
							receiveFile.setDataType("error");
						}
						System.out.println("dataType:"
								+ receiveFile.getDataType());
						receiveFile.setFileType(fileName.substring(fileName
								.lastIndexOf(".") + 1));
						receiveFile.setReportDate(DataUtil.getTime(tranDate));
						receiveFile.setReciveDate(DataUtil.getDate(tranDate));
						receiveFile.setIsParse("N");
						receiveFile.setYwbh("");
						receiveFile.setOrgno(Core_cffield.getOrgNo());
						receiveFile
								.setRemark1(String.valueOf(BsptCommon
										.getInstance().FiletradeType
										.get(receiveFile.getDataType()) == null
										|| "".equals(BsptCommon.getInstance().FiletradeType
												.get(receiveFile.getDataType())) ? "错误文件类型"
										: BsptCommon.getInstance().FiletradeType
												.get(receiveFile.getDataType())));
						receiveFile.setRemark2("手动采集核心申报文件");
						receiveFile.setRemark3("手动采集核心申报文件");
						receiveFile
								.setRemark4(Core_cffield.getReceivePathBak());
						dao.create(receiveFile);
					}
				}
			}
		}
		// 手动下载完成后，上传手动下载令牌文件到FTP对应的下载目录，防止重复下载
		if (Core_cffield.getIsLoadToken() != null
				&& "Y".equals(Core_cffield.getIsLoadToken())) {
			try {
				// 首先，在本地接收目录下写入一个手动下载的令牌文件
				File handTokenFile = DataUtil.createTockenFile(
						Core_cffield.getReceivePath(),
						Core_cffield.getHandToken());
				// 然后，上传此手动下载的令牌文件到FTP指定目录 最后，删除本地的手动下载的令牌文件
				DataUtil.uploadTokenFile(Core_cffield, handTokenFile, true);
			} catch (Exception e) {
				log.debug("上传手动下载令牌文件错误::::" + e.getMessage());
				throw new Exception("上传手动下载令牌文件错误[" + e.getMessage() + "]");
			}
		}
		// 根据烟台银行要求，ftp文件跟swift系统的处理一样，当天不删除对应时间文件夹，5天后再删除5天前的时间文件夹
		if (Core_cffield.getIsDel5DaysFtpFile() != null
				&& "Y".equals(Core_cffield.getIsDel5DaysFtpFile())) {
			DataUtil.delFtpFile5DaysBefore(Core_cffield, tranDate);
		}
	}

	/**
	 * 下载核心资本数据文件
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public OutPutBean parseFile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			Map retMap = new HashMap();
			String orgNo = request.getParameter("curOrgNo");
			String tranDate = (request.getParameter("fileDate")).replace("-",
					"");
			String tip = doParseFile(orgNo, tranDate);
			retMap.put("tip", tip);
			return new OutPutBean(CommonUtil.MapToJson(retMap));
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			// 释放锁
			File token2File = new File(Core_cffield.getReceivePath()
					+ File.separator + Core_cffield.getToken2());
			if (token2File.exists()) {
				token2File.delete();
			}
		}
	}

	public String doParseFile(String orgNo, String tranDate) throws Exception {
		String tip = "";
		// 初始化配置
		Core_cffield = CoreDateConfigParser.getInstance(tranDate);
		// 检测是否在执行自动下载和解析
		File token1File = new File(Core_cffield.getReceivePath()
				+ File.separator + Core_cffield.getToken1());
		if (token1File.exists()) {
			System.out.println("正在执行自动下载和解析,本次手动解析中止!");
			throw new Exception("正在执行自动下载和解析,本次手动解析中止!");
		}
		// 检查是否执行过手动下载
		File token2File = new File(Core_cffield.getReceivePath()
				+ File.separator + Core_cffield.getToken2());
		if (!token2File.isFile()) {
			System.out.println("你没有进行下载数据文件,请先执行手动下载,再执行手动解析操作!");
			throw new Exception("你没有进行下载数据文件,请先执行手动下载,再执行手动解析操作!");
		}
		log.debug("手工解析本地文件地址：" + Core_cffield.getReceivePath());
		File receivePath = new File(Core_cffield.getReceivePath());
		if (receivePath.isDirectory()) {
			File receiveFiles[] = receivePath.listFiles();
			if (null != receiveFiles && receiveFiles.length > 0) {
				log.debug("手工解析文件数目：" + receiveFiles.length);
				for (int i = 0; i < receiveFiles.length; i++) {
					if (receiveFiles[i].isFile()
							&& null != receiveFiles[i].getName()
							&& !token2File.getName().equals(
									receiveFiles[i].getName())) {
						log.debug("手工解析文件：" + receivePath + File.separator
								+ receiveFiles[i].getName() + "|" + orgNo);
						tip += parseBsptFile(new File(receivePath
								+ File.separator + receiveFiles[i].getName()),
								"Handle", orgNo, tranDate);
						log.debug("备份路径：" + Core_cffield.getReceivePathBak()
								+ File.separator + tranDate);
						// 条件判断 当把核心传入的错误文件备份到错误目录中并删除后不再执行此if条件中代码
						if (new File(receivePath + File.separator
								+ receiveFiles[i].getName()).exists()) {
							DataUtil.backupParsedFile(
									Core_cffield.getReceivePathBak()
											+ File.separator + tranDate,
									new File(receivePath + File.separator
											+ receiveFiles[i].getName()));
						}
					} else if (receiveFiles[i].isFile()) {
						// 如果是本地令牌文件则不备份
						if (token2File.getName().equals(
								receiveFiles[i].getName())) {
							log.debug("备份路径-："
									+ Core_cffield.getReceivePathBak()
									+ File.separator + tranDate);
							DataUtil.backupParsedFile(
									Core_cffield.getReceivePathBak()
											+ File.separator + tranDate,
									new File(receivePath + File.separator
											+ receiveFiles[i].getName()));
						}
					}
				}
			}
		}
		return tip;
	}

	public String parseBsptFile(File file, String handFlag, String orgNo,
			String tranDate) throws Exception {
		String tip = "";
		log.debug("开始解析核心文件：" + file + "|:::" + handFlag + "|" + orgNo + "|"
				+ tranDate);
		try {
			if (file == null)
				return "";
			if (file.exists()) {
				String fileName = file.getName();
				System.out.println("将解析文件为:" + fileName);
				if ("indicate.txt".equals(fileName)) {
					return "";
				}
				log.debug("fileName::::::" + fileName);
				// 解析txt文件的操作，最后的一个判断为山东城商联盟要求加，防止将令牌文件解析
				if (null != fileName && !"".equals(fileName)
						&& (fileName.toUpperCase().endsWith(".TXT"))) {
					if (fileName.startsWith("ACCCA")) {// CA 账户开关户信息
						ParseZhkgh parseZhkgh = new ParseZhkgh();
						tip += parseZhkgh.parse(file, orgNo, "ACCCA", "TXT",
								tranDate);
					} else if (fileName.startsWith("ACCCB")) {// 外汇账户信息--CB
																// 账户收支余信息
						ParseZhszy parseZhszy = new ParseZhszy();
						tip += parseZhszy.parse(file, orgNo, "ACCCB", "TXT",
								tranDate);
					} else if (fileName.startsWith("CFAAA")) {// 资本项目--AA
																// 外债双边贷款—签约信息
						// parseSbdkManager.parse(file, orgNo, "AA", "TXT",
						// tranDate);
					} else if (fileName.startsWith("CFAAB")) {// 资本项目--AB
																// 外债买方信贷—签约信息
						// parseMfxdManager.parse(file, orgNo, "AB", "TXT",
						// tranDate);
					} else if (fileName.startsWith("CFAAC")) {// 资本项目--AC
																// 外债境外同业拆借—签约信息
						// parseJwtycjManager.parse(file, orgNo, "AC", "TXT",
						// tranDate);
					} else if (fileName.startsWith("CFAAD")) {// 资本项目--AD
																// 外债海外代付—签约信息
						// parseHwdfManager.parse(file, orgNo, "AD", "TXT",
						// tranDate);
					} else if (fileName.startsWith("CFAAE")) {// 资本项目--AE
																// 外债卖出回购—签约信息
						// tip +=parseMchgManager.parse(file, orgNo, "AE",
						// "TXT", tranDate);
					} else if (fileName.startsWith("CFAAF")) {// 资本项目--AF
																// 外债远期信用证—签约信息
						// parseYqxyzManager.parse(file, orgNo, "AF", "TXT",
						// tranDate);
					} else if (fileName.startsWith("CFAAG")) {// 资本项目--AG
																// 外债银团贷款—签约信息
						// parseYtdkManager.parse(file, orgNo, "AG", "TXT",
						// tranDate);
					} else if (fileName.startsWith("CFAAH")) {// 资本项目--AH
																// 外债贵金属拆借—签约信息
						// parseGjscjManager.parse(file, orgNo, "AH", "TXT",
						// tranDate);
					} else if (fileName.startsWith("CFAAI")) {// 资本项目--AI
																// 外债其他贷款—签约信息
						// parseQtdkManager.parse(file, orgNo, "AI", "TXT",
						// tranDate);
					} else if (fileName.startsWith("CFAAJ")) {// 资本项目--AJ
																// 外债货币市场工具—签约信息
						// parseHbscgjManager.parse(file, orgNo, "AJ", "TXT",
						// tranDate);
					} else if (fileName.startsWith("CFAAK")) {// 资本项目--AK
																// 外债债券和票据—签约信息
						// parseZqpjManager.parse(file, orgNo, "AK", "TXT",
						// tranDate);
					} else if (fileName.startsWith("CFAAL")) {// 资本项目--AL
																// 外债境外同业存放—签约信息
						// tip += parseJwtycfManager.parse(file, orgNo, "AL",
						// "TXT", tranDate);
					} else if (fileName.startsWith("CFAAM")) {// 资本项目--AM
																// 外债境外联行及附属机构往来—签约信息
						// tip += parseJwlhfsjgwlManager.parse(file, orgNo,
						// "AM", "TXT", tranDate);
					} else if (fileName.startsWith("CFAAN")) {// 资本项目--AN
																// 外债非居民机构存款—签约信息
						ParseFjmjgck parseFjmjgck = new ParseFjmjgck();
						tip += parseFjmjgck.parse(file, orgNo, "CFAAN", "TXT",
								tranDate);
					} else if (fileName.startsWith("CFAAP")) {// 资本项目--AP
																// 外债非居民个人存款—签约信息
						ParseFjmgrck parseFjmgrck = new ParseFjmgrck();
						tip += parseFjmgrck.parse(file, orgNo, "CFAAP", "TXT",
								tranDate);
					} else if (fileName.startsWith("CFAAQ")) {// 资本项目--AQ
																// 外债其他外债—签约信息
						// parseQtwzManager.parse(file, orgNo, "AQ", "TXT",
						// tranDate);
					} else if (fileName.startsWith("CFAAR")) {// 资本项目--AR
																// 外债—变动信息
						// parseBdxxManager.parse(file, orgNo, "AR", "TXT",
						// tranDate);
						// } else if (fileName.startsWith("CFAAS")) {// 资本项目--AS
						// 外债—余额信息
						// tip += parseWzyexxManager.parse(file, orgNo, "AS",
						// "TXT", tranDate);
					} else if (fileName.startsWith("CFAAS_JG")) {// 资本项目--AS
																	// 外债—余额信息
						ParseWzyexx parseWzyexx = new ParseWzyexx();
						tip += parseWzyexx.parse(file, orgNo, "CFAAS_JG",
								"TXT", tranDate);
					} else if (fileName.startsWith("CFAAS_GR")) {// 资本项目--AS
																	// 外债—余额信息
						ParseWzyexx parseWzyexx = new ParseWzyexx();
						tip += parseWzyexx.parse(file, orgNo, "CFAAS_GR",
								"TXT", tranDate);
					} else if (fileName.startsWith("CFABA")) {// 资本项目--BA
																// 对外担保—签约信息
						// parseDwdbqyManager.parse(file, orgNo, "BA", "TXT",
						// tranDate);
					} else if (fileName.startsWith("CFABB")) {// 资本项目--BB
																// 对外担保—责任余额信息
						// parseDwdbzryeManager.parse(file, orgNo, "BB", "TXT",
						// tranDate);
					} else if (fileName.startsWith("CFABC")) {// 资本项目--BC
																// 对外担保-履约信息
						// parseDwdblyManager.parse(file, orgNo, "BC", "TXT",
						// tranDate);
					} else if (fileName.startsWith("CFACA")) {// 资本项目--CA
																// 国内外汇贷款—签约信息
						ParseGnwhdkqy parseGnwhdkqy = new ParseGnwhdkqy();
						tip += parseGnwhdkqy.parse(file, orgNo, "CFACA", "TXT",
								tranDate);
					} else if (fileName.startsWith("CFACB")) {// 资本项目--CB
																// 国内外汇贷款—变动信息
						ParseGnwhdkbd parseGnwhdkbd = new ParseGnwhdkbd();
						tip += parseGnwhdkbd.parse(file, orgNo, "CFACB", "TXT",
								tranDate);
					} else if (fileName.startsWith("CFADA")) {// 资本项目--DA
																// 境外担保项下境内贷款—签约信息
						// parseJwdbjndkqyManager.parse(file, orgNo, "DA",
						// "TXT", tranDate);
					} else if (fileName.startsWith("CFADB")) {// 资本项目--DB
																// 境外担保项下境内贷款—变动及履约信息
						// parseJwdbjndkbdlyManager.parse(file, orgNo, "DB",
						// "TXT", tranDate);
					} else if (fileName.startsWith("CFAEA")) {// 资本项目--EA
																// 外汇质押人民币贷款—签约信息
						ParseWhzyrmbdkqy parseWhzyrmbdkqy = new ParseWhzyrmbdkqy();
						tip += parseWhzyrmbdkqy.parse(file, orgNo, "CFAEA",
								"TXT", tranDate);
					} else if (fileName.startsWith("CFAEB")) {// 资本项目--EB
																// 外汇质押人民币贷款—变动信息
						ParseWhzyrmbdkbd parseWhzyrmbdkbd = new ParseWhzyrmbdkbd();
						tip += parseWhzyrmbdkbd.parse(file, orgNo, "CFAEB",
								"TXT", tranDate);
					} else if (fileName.startsWith("CFAFA")) {// 资本项目--FA 商业
																// 银行人民币结构性存款—签约信息
						// parseShrmbckqyManager.parse(file, orgNo, "FA", "TXT",
						// tranDate);
					} else if (fileName.startsWith("CFAFB")) {// 资本项目--FB
																// 商业银行人民币结构性存款—终止信息
						// parseShrmbckzzManager.parse(file, orgNo, "FB", "TXT",
						// tranDate);
					} else if (fileName.startsWith("CFAFC")) {// 资本项目--FC
																// 商业银行人民币结构性存款—利息给付信息
						// parseShrmbcklxgfManager.parse(file, orgNo, "FC",
						// "TXT", tranDate);
					} else if (fileName.startsWith("CFAFD")) {// 资本项目--FD
																// 商业银行人民币结构性存款—资金流出入和结购汇信息
						// parseShrmbcklcrjghManager.parse(file, orgNo,
						// "FD","TXT", tranDate);
					} else if (fileName.startsWith("QFIIZY")) {// 合格境外机构投资者境内证券投资（QFII）—
																// 合格境外机构投资者境内账户及资金汇出入信息(QFII账户开户专有信息)
						// parseZhkhzyxxManager.parse(file, orgNo, "QFIIZY",
						// "TXT", tranDate);
					} else if (fileName.startsWith("QFIIMX")) {// 合格境外机构投资者境内证券投资（QFII）—
																// 合格境外机构投资者境内账户及资金汇出入信息(QFII资金汇出入及结购汇明细信息)
						// parseZjjghmxxxManager.parse(file, orgNo,
						// "QFIIMX","TXT", tranDate);
					} else if (fileName.startsWith("QFIITSZHSZ")) {// 合格境外机构投资者境内证券投资（QFII）—合格境外机构投资者月度信息(QFII机构人民币特殊账户收支信息)
						// parseCnyzhszxxManager.parse(file, orgNo,
						// "QFIITSZHSZ","TXT", tranDate);
					} else if (fileName.startsWith("QFIIWHZHSZ")) {// 合格境外机构投资者境内证券投资（QFII）—
																	// 合格境外机构投资者月度信息(QFII机构外汇账户收支信息)
						// parseWhzhszxxManager.parse(file, orgNo,
						// "QFIIWHZHSZ","TXT", tranDate);
					} else if (fileName.startsWith("QFIIYDZCFZ")) {// 合格境外机构投资者境内证券投资（QFII）—
																	// 合格境外机构投资者月度信息(QFII机构月度资产负债信息)
						// parseqfiiydzcfzxxManager.parse(file,
						// orgNo,"QFIIYDZCFZ", "TXT", tranDate);
					} else if (fileName.startsWith("QFIIFZ")) {// 合格境外机构投资者境内证券投资（QFII）—QFII境内证券投资年度财务报表信息(资产负债表信息)
						// parseJnzjtzzcfzbxxManager.parse(file, orgNo,
						// "QFIIFZ","TXT", tranDate);
					} else if (fileName.startsWith("QFIISY")) {// 合格境外机构投资者境内证券投资（QFII）—QFII境内证券投资年度财务报表信息(损益表信息)"
						// parseJnzjtzsybxxManager.parse(file, orgNo,
						// "QFIISY","TXT", tranDate);
					} else if (fileName.startsWith("RQFIIMX")) {// 人民币合格境外机构投资者境内证券投资（RQFII）—(人民币合格境外机构投资者资金汇出入及购汇明细信息)
						// parseJwzjhcrghmxxxManager.parse(file, orgNo,
						// "RQFIIMX","TXT", tranDate);
					} else if (fileName.startsWith("RQFIICR")) {// "人民币合格境外机构投资者境内证券投资（RQFII）—人民币合格境外机构投资者收支信息(RQFII境内证券投资资金汇出入信息)"
						// parseJnzjtzzjhcrxxManager.parse(file, orgNo,
						// "RQFIICR","TXT", tranDate);
					} else if (fileName.startsWith("RQFIISZ")) {// 人民币合格境外机构投资者境内证券投资（RQFII）—人民币合格境外机构投资者收支信息(RQFII境内人民币账户收支情况信息)
						// parseJncnyzhszxxManager.parse(file, orgNo,
						// "RQFIISZ","TXT", tranDate);
					} else if (fileName.startsWith("RQFIIYDZCFZ")) {// 人民币合格境外机构投资者境内证券投资（RQFII）—(RQFII机构月度资产负债信息)
						// parseRqfiiydzcfzxxManager.parse(file,
						// orgNo,"RQFIIYDZCFZ", "TXT", tranDate);
					} else if (fileName.startsWith("RQFIIZCFZ")) {// 人民币合格境外机构投资者境内证券投资（RQFII）—RQFII境内证券投资年度财务报表信息(资产负债表信息)
						// parseZcfzbxxManager.parse(file, orgNo,
						// "RQFIIZCFZ","TXT", tranDate);
					} else if (fileName.startsWith("RQFIISY")) {// 人民币合格境外机构投资者境内证券投资（RQFII）—RQFII境内证券投资年度财务报表信息(损益表信息)
						// parseSybxxManager.parse(file, orgNo, "RQFIISY",
						// "TXT", tranDate);
					} else if (fileName.startsWith("QDIIZH")) {// 合格境内机构投资者境外证券投资（QDII）—合格境内机构投资者境内账户及资金汇出入信息(QDII账户信息)
						// parseQdiizhxxManager.parse(file, orgNo, "QDIIZH",
						// "TXT", tranDate);
					} else if (fileName.startsWith("QDIIMX")) {// 合格境内机构投资者境外证券投资（QDII）—合格境内机构投资者境内账户及资金汇出入信息(QDII资金汇出入及结购汇明细信息)
						// parseQdiizjhcrmxxxManager.parse(file, orgNo,
						// "QDIIMX","TXT", tranDate);
					} else if (fileName.startsWith("QDIITGZH")) {// 合格境内机构投资者境外证券投资（QDII）—合格境内机构投资者境外证券投资信息(QDII境内外币托管账户信息)
						// parseQdiijnwbtgxxManager.parse(file, orgNo,
						// "QDIITGZH","TXT", tranDate);
					} else if (fileName.startsWith("QDIIZQTZ")) {// 合格境内机构投资者境外证券投资（QDII）—合格境内机构投资者境外证券投资信息(QDII境外证券投资信息)
						// parseQdiijwzqtzxxManager.parse(file, orgNo,
						// "QDIIZQTZ","TXT", tranDate);
					} else if (fileName.startsWith("JLJHCZ")) {// 内个人参与境外上市公司股权激励计划—(境内专用外汇账户关户资金处置信息)
						// parseJnzywhghzjxxManager.parse(file, orgNo,
						// "JLJHCZ","TXT", tranDate);
					} else if (fileName.startsWith("JLJHSZ")) {// 内个人参与境外上市公司股权激励计划—(境内专用外汇账户收支信息)
						// parseJnzywhszxxManager.parse(file, orgNo,
						// "JLJHSZ","TXT", tranDate);
					} else {
						this.bakErrorFile(file, tranDate, fileName);
					}
				} else {
					this.bakErrorFile(file, tranDate, fileName);
				}
			} else {
				log.debug("文件不存在" + file);
				throw new Exception("传送文件解析信息失败-文件不存在:" + file);
			}
		} catch (EbillsException ex) {
			log.debug("传送文件解析信息失败" + ex);
			throw new Exception("传送文件解析信息失败" + ex);
		}
		return tip;
	}

	/**
	 * 备份从核心下载的错误文件
	 * 
	 * @param file
	 * @param tranDate
	 * @param fileName
	 */
	public void bakErrorFile(File file, String tranDate, String fileName) {
		// 如果核心传送错误的文件，这边将错误文件copy到本地接收目录下的错误文件目录中如：e:/gjyw/CBOD/bspt/errorFileBak/20130115
		String localErrorBakPath = file.getAbsolutePath();
		localErrorBakPath = localErrorBakPath.substring(0,
				localErrorBakPath.lastIndexOf(File.separator));
		localErrorBakPath = localErrorBakPath + File.separator + "errorFileBak";
		File bakFile = new File(localErrorBakPath);
		if (!bakFile.exists()) {
			bakFile.mkdir();
		}
		String localErrorFilePath = localErrorBakPath + File.separator
				+ tranDate;
		try {
			this.backupParsedFile(localErrorFilePath, file);
			System.out.println("核心错误文件[" + fileName + "]备份成功!");
		} catch (Exception e) {
			System.out.println("核心错误文件[" + fileName + "]备份失败!");
			e.printStackTrace();
		}
	}

	/**
	 * 备份下载的文件
	 * 
	 * @param backErrorPath
	 * @param file
	 */
	public void backupParsedFile(String backErrorPath, File file)
			throws Exception {
		boolean firstLine = true;
		FileWriter fw = null;
		FileReader fr = null;
		BufferedWriter bw = null;
		BufferedReader br = null;
		try {
			File filePath = new File(backErrorPath);
			if (!filePath.exists()) {
				filePath.mkdir();
			}

			File newFile = new File(backErrorPath + File.separator
					+ file.getName());
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
			System.out.println("备份文件[" + file.getName() + "]至路径 ["
					+ backErrorPath + "]成功!");
		} catch (Exception e) {
			System.out.println("备份文件[" + file.getName() + "]至路径 ["
					+ backErrorPath + "]失败!" + e.getMessage());
			throw new Exception("备份文件[" + file.getName() + "]至路径 ["
					+ backErrorPath + "]失败!");
		} finally {
			try {
				bw.close();
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("关闭文件流操作失败:" + e.getMessage());
			}
		}
		file.delete();
	}

	/**
	 * 查询下载日志
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public OutPutBean reciveSearch(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BsptFileDAO bsptdao = new BsptFileDAO();
		String orgNo = request.getParameter("curOrgNo");
		String tranDate = (request.getParameter("fileDate")).replace("-", "");
		String[] dcltpClu = request.getParameterValues("dcltpClu[]");
		List list = bsptdao.selectXzList(GeneralCalc.strToSQLDate(tranDate),
				dcltpClu);
		return new OutPutBean(CommonUtil.ListToJson(list));
	}

	/**
	 * 查询下载日志
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public OutPutBean parseSearch(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BsptFileDAO bsptdao = new BsptFileDAO();
		String orgNo = request.getParameter("curOrgNo");
		String tranDate = (request.getParameter("fileDate")).replace("-", "");
		String[] dcltpClu = request.getParameterValues("dcltpClu[]");
		List list = bsptdao.selectJxList(GeneralCalc.strToSQLDate(tranDate),
				dcltpClu, "");
		return new OutPutBean(CommonUtil.ListToJson(list));
	}

	/**
	 * 国结资本取数
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public OutPutBean loadData(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BsptFileDAO bsptdao = new BsptFileDAO();
		String orgNo = request.getParameter("curOrgNo");
		String tranDate = (request.getParameter("fileDate")).replace("-", "");
		String[] loadType = request.getParameterValues("loadType[]");
		HashMap retMap = impBusData(tranDate, orgNo, loadType);
		return new OutPutBean(CommonUtil.MapToJson(retMap));
	}

	/**
	 * 导入国结业务数据
	 * 
	 * @param tranDate
	 * @param orgNo
	 * @param flag
	 * @return
	 * @throws Exception
	 */
	public HashMap impBusData(String tranDate, String orgNo, String[] flags)
			throws Exception {
		String succ = "";
		String fail = "";
		BsptFileDAO bsptDao = new BsptFileDAO();
		String branchCode = bsptDao.getBranchCodeByOrgNo(orgNo);
		HashMap map = new HashMap();
		for (int i = 0; i < flags.length; i++) {
			String f = flags[i];
			// AA-海外代付起息
			if ("AA".equals(f)) {
				String sql = "select actiontype,debtorcode,debtype,debtypeXXX,valuedate,maturity,contractcurr,contractamount,"
						+ "floatrate,anninrate,crehqcode,crehqcodeXXX,opercode,opercodeXXX,creditortype,creditortypeXXX,creditorcode,creditorname,creditornamen,"
						+ "appcode,appname,inltcabuscode,spapfeboindex,remark,rwidh,ywbh,handdate from("
						+ "select 'A' actiontype," +
						// 债务人代码、债务类型、 起息日、到期日、
						"'"
						+ branchCode
						+ "' debtorcode, '1106' debtype,"
						+ "(SELECT ZBZWLXEJ FROM pazbzwlx WHERE ZBZWLXEJCODE='1106') debtypeXXX,"
						+
						// 签约币种/金额、是否浮动利率/年化利率值
						"a.startratedate valuedate, a.enddate maturity,"
						+ "a.dfcur contractcurr,a.dfamt contractamount,'N' floatrate,a.tyrate anninrate,"
						+
						// 债权人总部所在国家（地区）代码、债权人经营地所在国家（地区）代码、债权人类型代码
						"d.safecode crehqcode,"
						+ "d.cnname crehqcodeXXX,"
						+ "d.safecode opercode,"
						+ "d.cnname opercodeXXX,"
						+
						// 债权人类型代码 (默认为银行类存款机构)
						// "'ceb'||(select c.safecode from pa_country c where c.countryno=(select b.countryno from pa_bank b where b.bankno=a.dfbankno)) creditortype,"+
						"case "
						+ "when 'CHN'!=d.safecode "
						+ "then '20001401' "
						+ "else '20001402' "
						+ "end creditortype,"
						+ "(CASE WHEN 'CHN'!=d.safecode THEN (SELECT ZBJWZTEJ FROM pazbjwzttype WHERE ZBJWZTEJCODE='20001402') "
						+ "ELSE (SELECT ZBJWZTEJ FROM pazbjwzttype WHERE ZBJWZTEJCODE='20001401') END) creditortypeXXX,"
						+
						// 债权人代码、债权人中文名、债权人英文名
						"a.dfbankswiftcode creditorcode,"
						+
						// "(select cnname from pa_bank where bankno = a.dfbankno) as creditorname,"+
						// "(select bankname from pa_bank where bankno = a.dfbankno) as creditornamen,"+
						"'' as creditorname,"
						+ " substr (a.dfbankname ,1,128) creditornamen,"
						+
						// 申请人代码、申请人名称、
						"(select case when corp.corpkind='0' then corp.corporgcode else corp.idcard end appcode from pacrp corp where corp.corpno=a.appno) appcode,"
						+ "(select corp.cnname from pacrp corp where corp.corpno=a.appno) appname,"
						+
						// 承继的远期信用证承兑银行业务参号、是否经外汇局特批不需占用指标、备注
						"case "
						+ "when a.lcno=(select lcno from imlcissuefo where expirydate > lcissuedate and lcno = a.lcno) then a.abno "
						+ "else '' "
						+ "end inltcabuscode,'N' spapfeboindex,'' remark,"
						+ "t.txnno rwidh,t.curtbizno ywbh,t.findate as handdate "
						+ "from butxnar t, imdfapplyar a, pabank c, pacy d "
						+ "where 1=1  "
						+ "and t.transtate = '4' "
						+ "and t.txnno = a.txnno "
						+ "and t.tradeno='180702' "
						+ "and c.bankno=a.dfbankno "
						+ "and d.countryno=c.country "
						+ "and c.country <> '156' "
						+ "and a.isaffirm = 'Y' "
						+ "and t.tranorgno = '"
						+ orgNo
						+ "' "
						+ "and t.findate >= ? and t.findate <= ? "
						+
						// " and cast(t.findate as date)='"+todate+"' "+
						"and not exists (select 1 from dldclbscfo h where h.transtxnno = t.txnno and h.dclkind='hwdf')"
						+ ")";
				if (this.impWZ(sql, orgNo, tranDate, CIConstants.TAB_SB_HWDF,
						"hwdf")) {
					succ += "外债签约信息[海外代付起息],";
				} else {
					fail += "外债签约信息[海外代付起息],";
				}

				// 海外代付起息时同时需要生成一笔海外代付的变动信息
				sql = "select actiontype,buscode,wztype,exdebtcode,changtype,chdate,chcurrency,"
						+ "chamount,fairvalue,remark,rwidh,ywbh,handdate from("
						+ "select distinct 'A' actiontype,"
						+
						// 银行业务参号、外债类型、
						"t.curtbizno buscode,'020004' wztype,"
						+
						// 外债编号、
						"(select distinct y.exdebtcode from sbexdadfo y,dldclbscfo x where y.txnno=x.txnno and y.sbkeyno=x.rptno and "
						+ "x.bizno = t.curtbizno and y.actiontype<>'d') exdebtcode,"
						+
						// 变动类型：还本、变动日期、变动币种、变动金额
						"'1101' changtype, a.applydate chdate,"
						+ "a.dfcur chcurrency,a.dfamt chamount,"
						+
						// 公允价值、备注
						"0 fairvalue,'' remark,"
						+ "t.txnno rwidh,t.curtbizno ywbh,t.findate as handdate "
						+ "from butxnar t, imdfapplyar a , pabank c, pacy d "
						+ "where 1=1 "
						+ "and t.transtate = '4' "
						+ "and t.txnno = a.txnno "
						+ "and t.tradeno='180702' "
						+ "and c.bankno=a.dfbankno "
						+ "and d.countryno=c.country "
						+ "and c.country <> '156' "
						+ "and a.isaffirm = 'Y' "
						+ "and t.tranorgno = '"
						+ orgNo
						+ "' "
						+ "and t.findate >= ? and t.findate <= ? "
						+ "and not exists (select 1 from dldclbscfo h where h.transtxnno = t.txnno and h.dclkind='bdxx') "
						+ ")";
				if (this.impBD(sql, orgNo, tranDate, CIConstants.TAB_SB_BDXX,
						"bdxx")) {
					succ += "外债变动信息[海外代付起息],";
				} else {
					fail += "外债变动信息[海外代付起息],";
				}
				// 海外代付起息生成一笔海外代付起息的签约信息同时还要生成一笔远期信用证承兑的变动信息
				sql = "select actiontype,buscode,wztype,exdebtcode,changtype,chdate,chcurrency,"
						+ "chamount,fairvalue,remark,rwidh,ywbh,handdate from("
						+ "select distinct 'A' actiontype,"
						+
						// 银行业务参号、外债类型、
						"t.curtbizno buscode,'020006' wztype,"
						+
						// 外债编号、
						"(select distinct y.exdebtcode from sbexdaffo y,dldclbscfo x where y.txnno=x.txnno and y.sbkeyno=x.rptno and "
						+ "x.bizno = t.curtbizno and y.actiontype<>'d') exdebtcode,"
						+
						// 变动类型：还本、变动日期、变动币种、变动金额
						"'1201' changtype, a.startratedate chdate,"
						+ "a.dfcur chcurrency,a.dfamt chamount,"
						+
						// 公允价值、备注
						"0 fairvalue,'' remark,"
						+ "t.txnno rwidh,t.curtbizno ywbh,t.findate as handdate "
						+ "from butxnar t, imdfapplyar a, pbacptfo b, imlcissuefo c, pabank e, pacy f "
						+ "where 1=1 "
						+ "and t.transtate = '4' "
						+ "and t.txnno = a.txnno "
						+ "and t.tradeno='180702' "
						+ "and a.lcno = c.lcno "
						+ "and a.abno = b.bizno "
						+ "and e.bankno=a.dfbankno "
						+ "and f.countryno=e.country "
						+ "and e.country <> '156' "
						+ "and c.draftdays > 0 "
						+ "and a.isaffirm = 'Y' "
						+ "and t.tranorgno = '"
						+ orgNo
						+ "' "
						+ "and t.findate >= ? and t.findate <= ? "
						+ "and not exists (select 1 from dldclbscfo h where h.transtxnno = t.txnno and h.dclkind='bdxx') "
						+ ")";
				if (this.impBD(sql, orgNo, tranDate, CIConstants.TAB_SB_BDXX,
						"bdxx")) {
					succ += "外债变动信息[远期信用证承兑],";
				} else {
					fail += "外债变动信息[远期信用证承兑],";
				}

				// AB-海外代付到期
			} else if ("AB".equals(f)) {
				String sql = "select actiontype,buscode,wztype,exdebtcode,changtype,chdate,chcurrency,"
						+ "chamount,fairvalue,remark,rwidh,ywbh,handdate from("
						+ "select distinct 'A' actiontype,"
						+
						// 银行业务参号、外债类型、
						"a.ipno buscode,'020004' wztype,"
						+
						// 外债编号、
						"(select distinct y.exdebtcode from sbexdadfo y,dldclbscfo x where y.txnno=x.txnno and y.sbkeyno=x.rptno and "
						+ "x.bizno = t.curtbizno and y.actiontype<>'d') exdebtcode,"
						+
						// 变动类型：还本、变动日期、变动币种、变动金额
						"'1201' changtype,a.paymentdate chdate,"
						+ "a.paymentcur chcurrency,a.paymentamt chamount,"
						+
						// 公允价值、备注
						"0 fairvalue,'' remark,"
						+ "t.txnno rwidh,t.curtbizno ywbh,t.findate as handdate  "
						+ "from butxnar t,imdfpaymentar a "
						+ "where 1=1  "
						+ "and t.transtate = '4' "
						+ "and t.txnno = a.txnno "
						+ "and t.tradeno='180702' "
						+ "and t.tranorgno = '"
						+ orgNo
						+ "' "
						+ "and t.findate >= ? and t.findate <= ? "
						+ "and not exists (select 1 from dldclbscfo h where h.transtxnno = t.txnno and h.dclkind='bdxx') "
						+ ")";
				if (this.impBD(sql, orgNo, tranDate, CIConstants.TAB_SB_BDXX,
						"bdxx")) {
					succ += "外债变动信息[海外代付付汇],";
				} else {
					fail += "外债变动信息[海外代付付汇],";
				}
				// AC-远期信用证承兑
			} else if ("AC".equals(f)) {
				String sql =
				// 远期信用证承兑签约信息
				"select actiontype,debtorcode,debtype,debtypeXXX,contractcurr,contractamount,"
						+ "valuedate,maturity,appcode,appname,crehqcode,crehqcodeXXX,opercode,opercodeXXX,creditortype,creditortypeXXX,creditorcode,"
						+ "creditorname,creditornamen,spapfeboindex,remark,rwidh,ywbh,handdate from("
						+ "select 'A' actiontype," +
						// 债务人代码、债务类型
						"'"
						+ branchCode
						+ "' debtorcode, '1108' debtype,"
						+ "(SELECT ZBZWLXEJ FROM pazbzwlx WHERE ZBZWLXEJCODE='1108') debtypeXXX,"
						+
						// 签约币种/金额 (承兑币种、金额)起息日、到期日、
						"b.abcur contractcurr, c.acptamt contractamount,"
						+ "c.acptdate valuedate,c.maturydate maturity,"
						+
						// 开证申请人代码、名称
						"(select case when corp.corpkind= '0' then corp.corporgcode else corp.idcard end appcode from pacrp corp where corp.corpno=a.appno) appcode,"
						+ "(select corp.cnname from pacrp corp where corp.corpno=a.appno) appname,"
						+
						// 债权人总部所在国家(地区)代码、债权人经营地所在国家(地区)代码
						"d.safecode crehqcode,"
						+ "d.cnname crehqcodeXXX,"
						+ "d.safecode opercode,"
						+ "d.cnname opercodeXXX,"
						+
						// 债权人类型代码 (默认为银行类存款机构)
						// "'ceb'||(select c.safecode from pa_country c where c.countryno=b.countrycode) creditortype,"+
						// "case "+
						// "when 'chn'!=(select c.safecode from pa_country c where c.countryno=b.countrycode) "+
						// "then '20001401' "+
						// "else '20001402' "+
						// "end creditortype,"+
						"'20001699' as creditortype,"
						+ // 根据外管局要求修改，2013-8-10 '债权人类型代码’请默认设置为非金融企业-其他企业
						"(SELECT ZBJWZTEJ FROM pazbjwzttype WHERE ZBJWZTEJCODE='20001699') creditortypeXXX,"
						+ "'' as creditorcode,"
						+ // 根据外管局要求修改，2013-8-10 债权人代码’请默认设置为空
						// 债权人代码、债权人中文名、债权人英文名
						// "c.negobankswiftcode creditorcode," +
						// "(select cnname from pa_bank where bankno = c.negobankno) as creditorname,"+
						// "(select bankname from pa_bank where bankno = c.negobankno) as creditornamen,"+
						"'' as creditorname,"
						+ " substr (a.benefnameaddr ,1,128) creditornamen,"
						+
						// 是否经外汇局特批不需占用指标、备注
						"'N' spapfeboindex,'' remark,"
						+ "t.txnno rwidh,t.curtbizno ywbh,t.findate as handdate "
						+ "from butxnar t, imlcissuefo a, imlcabfo b, pbacptar c, pacy d,BUTXNOTHAR e "
						+ "where 1=1 "
						+ "and t.transtate = '4' "
						+ "and t.txnno = c.txnno  "
						+ "and t.tradeno='050110' "
						+ "AND t.TXNNO =e.TXNNO "
						+ "and a.draftdays > 0 "
						+ "and a.lcno = b.lcno "
						+ "and b.abno = c.bizno "
						+ "AND d.COUNTRYNO = a.BENEFCOUNTRY "
						+ "and e.zbbsflag = 'Y' "
						+ // 资本报送标志 新系统交易没有这个字段，建议加上此字段
						"and t.tranorgno = '"
						+ orgNo
						+ "' "
						+ "and t.findate >= ? and t.findate <= ? "
						+ "and not exists (select 1 from dldclbscfo h where h.transtxnno = t.txnno and h.dclkind='yqxyz') "
						+ ")";
				if (this.impWZ(sql, orgNo, tranDate, CIConstants.TAB_SB_YQXYZ,
						"yqxyz")) {
					succ += "外债签约信息[远期信用证承兑],";
				} else {
					fail += "外债签约信息[远期信用证承兑],";
				}
				// 远期信用证承兑的时候，会产生一笔联动的远期信用证的变动信息
				sql = "select actiontype,buscode,wztype,exdebtcode,changtype,chdate,chcurrency,"
						+ "chamount,fairvalue,remark,rwidh,ywbh,handdate from("
						+ "select distinct 'A' actiontype,"
						+
						// 银行业务参号、外债类型、
						"t.curtbizno buscode,'020006' wztype,"
						+
						// 外债编号、变动类型、变动日期、
						"(select distinct y.exdebtcode from sbexdaffo y,dldclbscfo x where y.txnno=x.txnno and y.sbkeyno=x.rptno and "
						+ "x.bizno = t.curtbizno and y.actiontype<>'d') exdebtcode,"
						+ "'1101' changtype, c.acptdate chdate,"
						+
						// 变动币种、变动金额
						" a.lccursign chcurrency,c.acptamt chamount,"
						+
						// 公允价值、备注
						" 0 fairvalue,'' remark,"
						+ "t.txnno rwidh,t.curtbizno ywbh,t.findate as handdate "
						+ "from butxnar t, imlcissuefo a, imlcabfo b, pbacptar c,BUTXNOTHAR e "
						+ "where 1=1 "
						+ "and t.transtate = '4' "
						+ "and t.txnno = c.txnno "
						+ "and t.tradeno='050110' "
						+ "AND t.TXNNO =e.TXNNO "
						+ "and a.draftdays > 0 "
						+ "and a.lcno = b.lcno "
						+ "and b.abno = c.bizno "
						+ "and e.zbbsflag = 'Y' "
						+ // 资本报送标志 新系统交易没有这个字段，建议加上此字段
						// "and a.lcno = t.primarybizno "+
						"and t.tranorgno = '"
						+ orgNo
						+ "' "
						+ "and t.findate >= ? and t.findate <= ? "
						+ "and not exists (select 1 from dldclbscfo h where h.transtxnno = t.txnno and h.dclkind='bdxx') "
						+ ")";
				if (this.impBD(sql, orgNo, tranDate, CIConstants.TAB_SB_BDXX,
						"bdxx")) {
					succ += "外债变动信息[远期信用证承兑],";
				} else {
					fail += "外债变动信息[远期信用证承兑],";
				}
				// AD-远期信用证承兑修改
			} else if ("AD".equals(f)) {
				// AE-来单付汇
			} else if ("AE".equals(f)) {
				String sql = "select actiontype,buscode,wztype,exdebtcode,changtype,chdate,chcurrency,"
						+ "chamount,fairvalue,remark,rwidh,ywbh,handdate from("
						+ "select distinct 'A' actiontype,"
						+
						// 银行业务参号、外债类型
						"c.paymentno buscode,'020006' wztype,"
						+
						// 外债编号、变动类型、变动日期、
						"(select distinct y.exdebtcode from sbexdaffo y,dldclbscfo x where y.txnno=x.txnno and y.sbkeyno=x.rptno and "
						+ "x.bizno = t.curtbizno and y.actiontype<>'d') exdebtcode,"
						+ "'1201' changtype,c.paymentdate chdate,"
						+
						// 变动币种、变动金额20131105 c.payamt ->paycorpusamt
						"a.abcur chcurrency,c.paycorpusamt chamount,"
						+
						// 公允价值、备注
						"0 fairvalue,'' remark,"
						+ "t.txnno rwidh,t.curtbizno ywbh,t.findate as handdate "
						+ "from butxnar t,imlcabfo a,pbacptfo b, imlcpayar c,BUTXNOTHAR e "
						+ "where 1=1 "
						+ "and t.transtate = '4' "
						+ "and t.txnno = c.txnno  "
						+ "and t.tradeno='050110' "
						+ "AND t.TXNNO =e.TXNNO "
						+ "and c.abno = b.bizno "
						+ "and c.abno = a.abno "
						+ "and e.zbbsflag = 'Y' "
						+ // 资本报送标志 新系统交易没有这个字段，建议加上此字段
						"and t.tranorgno = '"
						+ orgNo
						+ "' "
						+ "and t.findate >= ? and t.findate <= ? "
						+ "and not exists (select 1 from dldclbscfo h where h.transtxnno = t.txnno and h.dclkind='bdxx') "
						+ ")";
				if (this.impBD(sql, orgNo, tranDate, CIConstants.TAB_SB_BDXX,
						"bdxx")) {
					succ += "外债变动信息[来单付汇],";
				} else {
					fail += "外债变动信息[来单付汇],";
				}
			}
		}
		if (!succ.equals("")) {
			succ = succ.substring(0, succ.length() - 1);
		}
		if (!fail.equals("")) {
			fail = fail.substring(0, fail.length() - 1);
			map.put("success", "false");
		} else {
			map.put("success", "true");
		}
		map.put("succStr", succ);
		map.put("failStr", fail);
		return map;
	}

	/**
	 * 外债--签约信息
	 * 
	 * @param sql
	 * @param orgNo
	 * @param tranDate
	 * @param tabName
	 * @param modId
	 * @return
	 */
	public boolean impWZ(String sql, String orgNo, String tranDate,
			String tabName, String modId) {
		try {
			EbpDao dao = new EbpDao();
			BsptFileDAO bsptDao = new BsptFileDAO();
			SerialNoFactory snf = new SerialNoFactory();
			LinkedList<Object> inputList = new LinkedList<Object>();
			inputList.add(DataUtil.getMinTime(tranDate));
			inputList.add(DataUtil.getMaxTime(tranDate));
			List<Map<String, Object>> list = dao.queryBySql(sql, "",
					inputList);
			for (Map<String, Object> map : list) {
				map.put("exdebtcode", DclNoFactory.getEXDEBTCODE(modId, orgNo,null, ""));
				map.put("txnNo", snf.getSerialNo(EbpConstants.DLDCLBSCID, 16));
				Map<String, Object> zmap = buildDclMap(map, orgNo,
						"exdebtcode", modId);
				bsptDao.create(zmap, EbpConstants.DLDCLBSCID);
				Map<String, Object> fmap = GeneralCalc.buildFbMap(map);
				fmap.put("sbkeyNo", zmap.get("rptNo"));
				bsptDao.create(fmap, tabName);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("导入外债--签约信息失败:" + e.getMessage());
		}
		return false;
	}

	/**
	 * 外债--变动信息
	 * 
	 * @param sql
	 * @param orgNo
	 * @param tranDate
	 * @param tabName
	 * @param modId
	 * @return
	 */
	public boolean impBD(String sql, String orgNo, String tranDate,
			String tabName, String modId) {
		try {
			EbpDao dao = new EbpDao();
			BsptFileDAO bsptDao = new BsptFileDAO();
			SerialNoFactory snf = new SerialNoFactory();
			LinkedList<Object> inputList = new LinkedList<Object>();
			inputList.add(DataUtil.getMinTime(tranDate));
			inputList.add(DataUtil.getMaxTime(tranDate));
			List<Map<String, Object>> list = dao.queryBySql(sql, "",
					inputList);
			for (Map<String, Object> map : list) {
				map.put("changeno",
						DclNoFactory.getChangeNo(modId,
								GeneralCalc.objToString(map.get("exdebtcode"))));
				map.put("txnNo", snf.getSerialNo(EbpConstants.DLDCLBSCID, 16));
				Map<String, Object> zmap = buildDclMap(map, orgNo,
						"exdebtcode,changeno", modId);
				bsptDao.create(zmap, EbpConstants.DLDCLBSCID);
				Map<String, Object> fmap = GeneralCalc.buildFbMap(map);
				fmap.put("sbkeyNo", zmap.get("rptNo"));
				bsptDao.create(fmap, tabName);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("导入外债--变动信息失败:" + e.getMessage());
		}
		return false;
	}

	public Map<String, Object> buildDclMap(Map<String, Object> smap,
			String orgNo, String rptName, String modId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("transTxnNo", smap.get("rwidh"));
		map.put("bizNo", smap.get("ywbh"));
		map.put("initDate", smap.get("handdate"));
		map.put("dclDate", smap.get("handdate"));
		map.put("txnNo", smap.get("txnNo"));
		map.put("orgNo", orgNo);
		String[] rpts = rptName.split(",");
		String rptNo = "";
		for (String rpt : rpts) {
			rptNo += smap.get(rpt);
		}
		map.put("rptNo", rptNo);
		map.put("dclKind", modId);
		map.put("bscState", "0");
		map.put("bscInput", "Y");
		map.put("bscSend", "N");
		map.put("mgeState", "0");
		map.put("mgeInput", "Y");
		map.put("mgeSend", "N");
		map.put("dclState", "0");
		map.put("dclInput", "Y");
		map.put("dclSend", "N");
		map.put("hxxnwzbh", rptNo + "_" + "GJ");
		map.put("dataResource", "GJ");
		return map;
	}

	/**
	 * 查询数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public OutPutBean loadSearch(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		EbpDao dao = new EbpDao();
		String orgNo = request.getParameter("curOrgNo");
		String tranDate = request.getParameter("fileDate");
		String sql = "select a.transtxnno,a.bizno,c.tradedesc,a.rptno,a.dcldate,"
				+ "(select distinct mdcnname from dldclmd where mdenname=a.dclkind) mdcnname "
				+ "from dldclbscfo a,butxnar b,patrdtyp c "
				+ "where a.transtxnno = b.txnno and b.tradeno=c.tradeno "
				+ "and a.dcldate = ? and a.orgno = ? and a.hxxnwzbh like '%_GJ'";
		LinkedList<Object> inputList = new LinkedList<Object>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date fdate = df.parse(tranDate);
		inputList.add(fdate);
		inputList.add(orgNo);
		List<Map<String, Object>> list = dao.queryBySql(sql, "",
				inputList);
		return new OutPutBean(CommonUtil.ListToJson(list));
	}
}
