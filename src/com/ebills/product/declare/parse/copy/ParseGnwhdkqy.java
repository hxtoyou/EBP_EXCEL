package com.ebills.product.declare.parse.copy;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ebills.commons.SerialNoFactory;
import com.ebills.declare.bussiness.DclNoFactory;
import com.ebills.product.declare.dao.BsptFileDAO;
import com.ebills.product.declare.domain.Gnwhdkqy;
import com.ebills.product.declare.domain.ParseBsptFile;
import com.ebills.product.declare.domain.ReceiveBsptFile;
import com.ebills.product.declare.util.CIConstants;
import com.ebills.product.declare.util.DataUtil;
import com.ebills.product.declare.util.GeneralCalc;
import com.ebills.product.declare.util.RecvDataChecker;
import com.ebills.util.EbillsLog;
import com.ebills.utils.EbpConstants;


public class ParseGnwhdkqy {
	private String className = this.getClass().getName();
	private EbillsLog log = new EbillsLog(className);
	private BufferedReader br = null;
	private RecvDataChecker recvDataChecker = new RecvDataChecker();
	BsptFileDAO dao = new BsptFileDAO();
	
	/**
	 * 解析从外围系统FTP上下载下来的国内外汇贷款签约的文件
	 * @param file		需要解析的文件名称
	 * @param orgNo		采集机构
	 * @param dataType	数据文件
	 * @param fileType	文件类型
	 * @param tranDate	解析日期
	 * @return
	 * @ejb.interface-method
	 */
	public String parse(File file, String orgNo, String dataType,
			String fileType, String tranDate) throws Exception {
		String tip = "";
		Gnwhdkqy gnwhdkqy = null;
		ParseBsptFile parsefile = null;
		String str = "";
		int line = 0;
		String fileName = file.getName();
		ReceiveBsptFile receiveBsptFile = new ReceiveBsptFile();
		receiveBsptFile.setFileName(fileName);
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(
					file), "GB2312");
			br = new BufferedReader(isr);
			while ((str = br.readLine()) != null && str != "") {
				try {
					line++;
					if (null != str && !"".equals(str.trim())) {
						String[] array = str.split(",");
						gnwhdkqy = parseLine(array, orgNo, tranDate);
						tip += recvDataChecker.check_GnwhdkQyxx(gnwhdkqy);
						Map gnwhdkqyMap = GeneralCalc.getValueMap(gnwhdkqy);
						String rptNo = GeneralCalc.objToString(gnwhdkqy.getDofoexlocode());
						gnwhdkqyMap.put("txnNo",gnwhdkqy.getNguid());
						gnwhdkqyMap.put("sbkeyNo",rptNo);
						Map<String, Object> dldclbsc = new HashMap<String, Object>();
						dldclbsc.put("txnNo", gnwhdkqy.getNguid());
						dldclbsc.put("orgNo", gnwhdkqy.getBank_Id());
						dldclbsc.put("rptNo", rptNo);
						String dclKind = dao.getDldclMDName(dataType);
						dldclbsc.put("dclKind", dclKind);
						dldclbsc.put("dclDate", gnwhdkqy.getRecdate());
						dldclbsc.put("initDate", gnwhdkqy.getRecdate());
						dldclbsc.put("bscState", "0");
						dldclbsc.put("bscInput", "Y");
						dldclbsc.put("bscSend", "N");
						dldclbsc.put("mgeState", "0");
						dldclbsc.put("mgeInput", "Y");
						dldclbsc.put("mgeSend", "N");
						dldclbsc.put("dclState", "0");
						dldclbsc.put("dclInput", "Y");
						dldclbsc.put("dclSend", "N");
						dldclbsc.put("dataResource", "HX");
						dldclbsc.put("hxxnwzbh", gnwhdkqy.getHxxnwzbh());
						// 根据申报主键和数据来源获取对应记录
						List<Map<String, Object>> lst = dao.getDecalreByRptNo(rptNo, dclKind, EbpConstants.TABLE_INFO);
						if (null != lst && lst.size() > 0) {
							Map<String, Object> gnwhdkqyinfo = (Map<String, Object>) lst.get(0);
							if (null != gnwhdkqyinfo
									&& "0".equals(GeneralCalc.objToString(gnwhdkqyinfo.get("bscState")))) {
								Map<String,Object> fkeyMap = new HashMap<String, Object>();
								Map<String,Object> zkeyMap = new HashMap<String, Object>();
								fkeyMap.put("txnNo", GeneralCalc.objToString(gnwhdkqyinfo.get("txnNo")));
								fkeyMap.put("sbkeyNo", GeneralCalc.objToString(gnwhdkqyinfo.get("rptNo")));
								gnwhdkqyMap.put("txnNo", GeneralCalc.objToString(gnwhdkqyinfo.get("txnNo")));
								gnwhdkqyMap.put("sbkeyNo", GeneralCalc.objToString(gnwhdkqyinfo.get("rptNo")));
								zkeyMap.put("txnNo", GeneralCalc.objToString(gnwhdkqyinfo.get("txnNo")));
								zkeyMap.put("rptNo", GeneralCalc.objToString(gnwhdkqyinfo.get("rptNo")));
								zkeyMap.put("dclKind", GeneralCalc.objToString(gnwhdkqyinfo.get("dclKind")));
								dldclbsc.put("txnNo", GeneralCalc.objToString(gnwhdkqyinfo.get("txnNo")));
								dldclbsc.put("rptNo", GeneralCalc.objToString(gnwhdkqyinfo.get("rptNo")));
								dldclbsc.put("dclKind", GeneralCalc.objToString(gnwhdkqyinfo.get("dclKind")));
								dao.updateDecalreBuss(CIConstants.TAB_SB_GNWHDKQY, gnwhdkqyMap, fkeyMap, EbpConstants.TABLE_INFO);
								dao.updateDecalre(dldclbsc, zkeyMap, EbpConstants.TABLE_INFO);
								log.error("记录存在，状态不为已发送申报，执行更新操作完毕！" + gnwhdkqy);
								// 金额、币种、来源 类型、序号、文件名称 文件类型、是否解析通过、执行机构
								// 解析时间、解析备注1
								dao.create(
										gnwhdkqy.getContractamount(),
										gnwhdkqy.getCurrence(),
										gnwhdkqy.getDatasources(),
										dataType,
										String.valueOf(line),
										fileName,
										fileType,
										"Y",
										orgNo,
										DataUtil.getDate(tranDate), "第"
												+ (line)
												+ "行解析成功,记录存在，状态不为已发送申报，执行更新操作完毕！",
										"", "", "", gnwhdkqy.getYwbh());
							} else {
								dao.create(
										gnwhdkqy.getContractamount(),
										gnwhdkqy.getCurrence(),
										gnwhdkqy.getDatasources(),
										dataType,
										String.valueOf(line),
										fileName,
										fileType,
										"Y",
										orgNo,
										DataUtil.getDate(tranDate), "第"
												+ (line)
												+ "行解析成功,记录存在，状态为已发送申报，不执行操作！", "",
										"", "", gnwhdkqy.getYwbh());
								log.error("记录存在，状态为已发送申报，不执行操作！");
							}
						} else {
							// 在此处理采集的C或D类型的数据
							boolean isParsefile = false;
							if ("C".equals(array[0]) || "D".equals(array[0])) {
								// 是否存在A类型数据，若不存在便不接受此数据
								/*gnwhdkqy.setActiontype("A");
								List listA = gnwhdkqyManager
										.getDataByYWBHDATASources(gnwhdkqy);
								gnwhdkqy.setActiontype("C");
								List listC = gnwhdkqyManager
										.getDataByYWBHDATASources(gnwhdkqy);
								gnwhdkqy.setActiontype("D");
								List listD = gnwhdkqyManager
										.getDataByYWBHDATASources(gnwhdkqy);
								Gnwhdkqy temp = null;
								if (null != listA || null != listC
										|| null != listD) {
									// 将类型更新掉
									if (null != listA) {
										temp = (Gnwhdkqy) listA.get(0);
									} else if (null != listC) {
										temp = (Gnwhdkqy) listC.get(0);
									} else if (null != listD) {
										temp = (Gnwhdkqy) listD.get(0);
									}
									if ("Y".equals(temp.getSfysb())) {
										temp.setSfzx("N");
										gnwhdkqyManager.updateInfo(temp);
										gnwhdkqy.setActiontype(array[0]);
										gnwhdkqyManager.create(gnwhdkqy);
										isParsefile = true;
									} else {
										dao.create(
												gnwhdkqy.getContractamount(),
												gnwhdkqy.getCurrence(),
												gnwhdkqy.getDatasources(),
												dataType,
												String.valueOf(line),
												fileName,
												fileType,
												"Y",
												orgNo,
												new java.sql.Date(System
														.currentTimeMillis()),
												"第" + (line) + "接收到" + array[0]
														+ "的数据，在库中存在"
														+ temp.getActiontype()
														+ "类型数据未申报，不执行操作！", "",
												"", "", gnwhdkqy.getYwbh());
										log.error("接收到" + array[0]
												+ "的数据，在库中存在"
												+ temp.getActiontype()
												+ "类型数据未申报，不执行操作！");
										isParsefile = false;
									}
								} else {
									dao
											.create(gnwhdkqy
													.getContractamount(),
													gnwhdkqy.getCurrence(),
													gnwhdkqy.getDatasources(),
													dataType,
													String.valueOf(line),
													fileName,
													fileType,
													"Y",
													orgNo,
													new java.sql.Date(
															System.currentTimeMillis()),
													"第"
															+ (line)
															+ "接收到"
															+ array[0]
															+ "的数据，在库中不存在A,C或D类型数据，此数据不执行操作！",
													"", "", "", gnwhdkqy
															.getYwbh());
									log.error("接收到" + array[0]
											+ "的数据，在库中不存在A,C或D类型数据，此数据不执行操作！");
									isParsefile = false;
								}*/
							} else {
								dao.create(gnwhdkqyMap, CIConstants.TAB_SB_GNWHDKQY);
								dao.create(dldclbsc, EbpConstants.DLDCLBSCID);
								isParsefile = true;
							}
							if (isParsefile) {
								parsefile = new ParseBsptFile();
								parsefile.setFileName(fileName);
								parsefile.setDataType(dataType);// "CFACA"
								parsefile.setFileType(fileType);
								parsefile.setYwbh(gnwhdkqy.getYwbh());
								parsefile.setParseDate(DataUtil.getDate(tranDate));
								parsefile.setCursign(gnwhdkqy.getCurrence());
								if (!"".equals(String.valueOf(gnwhdkqy
										.getContractamount())))
									parsefile.setAmount(gnwhdkqy
											.getContractamount());
								parsefile.setOrgNo(orgNo);
								parsefile.setDataSources(gnwhdkqy
										.getDatasources());
								parsefile.setIsParse("Y");
								parsefile.setRemark1("第" + (line) + "行解析成功");
								parsefile.setFileInfoNo(String.valueOf(line));
								dao.create(parsefile);
							}
						}
						log.info("共成功读取了:: " + line + " 行");
					}
				} catch (Exception e) {
					log.error("WHILE------->>>>>>" + "读取文件" + fileName
							+ "失败,在第" + (line) + "行解析不成功" + e);
					log.error(str);
					parsefile = new ParseBsptFile();
					parsefile.setFileName(fileName);
					parsefile.setDataType(dataType);
					parsefile.setFileType(fileType);
					parsefile.setYwbh("ERROR");
					parsefile.setParseDate(DataUtil.getDate(tranDate));
					parsefile.setOrgNo(orgNo);
					parsefile.setDataSources("ERROR");
					parsefile.setIsParse("N");
					parsefile.setFileInfoNo(String.valueOf(line));
					parsefile.setRemark1("第" + (line) + "行解析不成功");
					List list = dao.getErrorData(parsefile);
					if (null == list || list.size() <= 0) {
						dao.create(parsefile);
					}
				}
			}
		} catch (Exception e) {
			log.error("读取文件" + fileName + "失败,在第" + line + "行解析不成功：" + e);
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return tip;
	}

	/**
	 * 解析文件
	 * 
	 * @ejb.interface-method
	 */
	public Gnwhdkqy parseLine(String[] fields, String orgNo, String tranDate)
			throws Exception {
		java.util.Date tmpDate = null;
		java.sql.Date valuedate = null;
		java.sql.Date recdate = null;
		java.sql.Date maturityDate = null;
		Gnwhdkqy gnwhdkqy = new Gnwhdkqy();
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			//起息日
			tmpDate = format.parse(fields[7].replaceAll("\"", "").trim());
			valuedate = new java.sql.Date(tmpDate.getTime());
			// 到期日
			tmpDate = format.parse(fields[8].replaceAll("\"", "").trim());
			maturityDate = new java.sql.Date(tmpDate.getTime());
			//数据接收或创建时间
			recdate = GeneralCalc.strToSQLDate(tranDate);
		} catch (Exception e) {
			System.out.println("核心传递的日期格式有误!"+e.getMessage());
			log.equals("核心传递的日期格式有误!"+e.getMessage());
		}
		try {
			gnwhdkqy.setActiontype("A");															// 操作类型
			gnwhdkqy.setActiondesc("");																// 修改、删除原因
			String hxXnBranchCode= fields[0].replaceAll("\"", "").trim();							// 核心传递的编号(包含国内金融机构标识码)
			gnwhdkqy.setCreditorcode(fields[1].replaceAll("\"", "").trim());						// 债权人代码
			gnwhdkqy.setDebtorcode(fields[2].replaceAll("\"", "").trim());							// 债务人代码
			gnwhdkqy.setDebtorname(fields[3].replaceAll("\"", "").trim());							// 债务人中文名称
			gnwhdkqy.setDofoexlotype(fields[4].replaceAll("\"", "").trim());						// 国内外汇贷款类型
			gnwhdkqy.setLenproname(fields[5].replaceAll("\"", "").trim());							// 转贷项目名称
			gnwhdkqy.setLenagree(fields[6].replaceAll("\"", "").trim());							// 转贷协议号
			gnwhdkqy.setValuedate(valuedate);														// 起息日
			gnwhdkqy.setMaturity(maturityDate);														// 到期日
			gnwhdkqy.setCurrence(fields[9].replaceAll("\"", "").trim());							// 贷款币种
			if(null != fields[10] || !"".equals(fields[10].replaceAll("\"", "").trim()))			// 签约金额
				gnwhdkqy.setContractamount(new BigDecimal(fields[10].replaceAll("\"", "").trim()));
			if (null != fields[11] && !"".equals(fields[11].replaceAll("\"", "").trim()))
				// 根据城商联盟徐宇20130110日说，需要将核心传递的年化利率值除以100
				gnwhdkqy.setAnninrate(GeneralCalc.roundFormat(Double.parseDouble(fields[11].replaceAll("\"", "").trim())/100,8));// 年化利率值
			gnwhdkqy.setRemark(fields[12].replaceAll("\"", "").trim());
			gnwhdkqy.setHxxnwzbh(fields[13].replaceAll("\"", "").trim());							// 核心虚拟国内外汇贷款编号(不能用于申报)，核心传递的用来确定唯一一条记录，方便余额、变动记录的操作
			if (null != recdate && !"".equals(recdate))				
				gnwhdkqy.setRecdate(recdate);														// 接收/创建时间
			gnwhdkqy.setDatasources("HX");															// 数据来源(山东城商联盟)
			
			// 虚拟国内外汇贷款编号的第3位至14位为银行金融机构标识码
			String branchCode =hxXnBranchCode.substring(2, 14);
			// 根据金融机构标识码，获取国结机构号(采集机构)
			gnwhdkqy.setBank_Id(dao.getOrgNoByBranchCode(branchCode));
			gnwhdkqy.setDofoexlocode(DclNoFactory.getEXDEBTCODE("gnwhdkqy",gnwhdkqy.getBank_Id(),null, "")); 					// 国内外汇贷款编号(核心不传递国内外汇贷款编号，国结自己生成)
			gnwhdkqy.setYwbh(gnwhdkqy.getDofoexlocode()); 											// 业务编号，资本项下为国内外汇贷款编号
			SerialNoFactory snf =  new SerialNoFactory();
			String txnNo = snf.getSerialNo(EbpConstants.DLDCLBSCID,16);
			gnwhdkqy.setNguid(txnNo);										// 国结生成的流水号
			gnwhdkqy.setRwidh(gnwhdkqy.getNguid());
			gnwhdkqy.setSxbz("1");
			gnwhdkqy.setSfzx("Y");
			gnwhdkqy.setSfysb("N");
			gnwhdkqy.setHanddate(DataUtil.getTime(tranDate));
			return gnwhdkqy;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("解析核心传递的国内外汇贷款签约数据有误:"+e.getMessage());
			log.equals("解析核心传递的国内外汇贷款签约数据有误:"+e.getMessage());
			throw new Exception("解析核心传递的国内外汇贷款签约数据有误:"+e.getMessage());
		}
	}
}