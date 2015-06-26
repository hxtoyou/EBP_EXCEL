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

import org.jfree.util.Log;

import com.ebills.commons.SerialNoFactory;
import com.ebills.declare.bussiness.DclNoFactory;
import com.ebills.product.declare.dao.BsptFileDAO;
import com.ebills.product.declare.domain.ParseBsptFile;
import com.ebills.product.declare.domain.ReceiveBsptFile;
import com.ebills.product.declare.domain.Whzyrmbdkqy;
import com.ebills.product.declare.util.CIConstants;
import com.ebills.product.declare.util.DataUtil;
import com.ebills.product.declare.util.GeneralCalc;
import com.ebills.product.declare.util.RecvDataChecker;
import com.ebills.util.EbillsLog;
import com.ebills.utils.EbpConstants;

public class ParseWhzyrmbdkqy{
	private String className = this.getClass().getName();
	private EbillsLog log = new EbillsLog(className);
	private BufferedReader br = null;
	private RecvDataChecker recvDataChecker = new RecvDataChecker();
	BsptFileDAO dao = new BsptFileDAO();
	
	/**
	 * 解析从外围系统FTP上下载下来的外汇质押人民币贷款签约的文件
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
		String str = "";
		int line = 0;
		Whzyrmbdkqy whzyrmbdkqy = null;
		String fileName = file.getName();
		ParseBsptFile parsefile = null;
		ReceiveBsptFile receiveBsptFile = new ReceiveBsptFile();
		receiveBsptFile.setFileName(fileName);
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(
					file), "GB2312");
			br = new BufferedReader(isr);
			while ((str = br.readLine()) != null && str != "") {
				try {
					if (null != str && !"".equals(str.trim())) {
						line++;
						String[] array = str.split(",");
						whzyrmbdkqy = parseLine(array, orgNo, tranDate);
						tip += recvDataChecker.check_WhzyrmbdkQyxx(whzyrmbdkqy);
						Map whzyrmbdkqyMap = GeneralCalc.getValueMap(whzyrmbdkqy);
						String rptNo = GeneralCalc.objToString(whzyrmbdkqy.getExplrmblono());
						whzyrmbdkqyMap.put("txnNo",whzyrmbdkqy.getNguid());
						whzyrmbdkqyMap.put("sbkeyNo",rptNo);
						Map<String, Object> dldclbsc = new HashMap<String, Object>();
						dldclbsc.put("txnNo", whzyrmbdkqy.getNguid());
						dldclbsc.put("orgNo", whzyrmbdkqy.getBank_Id());
						dldclbsc.put("rptNo", rptNo);
						String dclKind = dao.getDldclMDName(dataType);
						dldclbsc.put("dclKind", dclKind);
						dldclbsc.put("dclDate", whzyrmbdkqy.getRecdate());
						dldclbsc.put("initDate", whzyrmbdkqy.getRecdate());
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
						dldclbsc.put("hxxnwzbh", whzyrmbdkqy.getHxxnwzbh());
						// 根据申报主键和数据来源获取对应记录
						List<Map<String, Object>> lst = dao.getDecalreByRptNo(rptNo, dclKind, EbpConstants.TABLE_INFO);
						if (null != lst && lst.size() > 0) {
							Map<String, Object> whzyrmbdkqyinfo = (Map<String, Object>) lst.get(0);
							if (null != whzyrmbdkqyinfo
									&& "0".equals(GeneralCalc.objToString(whzyrmbdkqyinfo.get("bscState")))) {
								Map<String,Object> fkeyMap = new HashMap<String, Object>();
								Map<String,Object> zkeyMap = new HashMap<String, Object>();
								fkeyMap.put("txnNo", GeneralCalc.objToString(whzyrmbdkqyinfo.get("txnNo")));
								fkeyMap.put("sbkeyNo", GeneralCalc.objToString(whzyrmbdkqyinfo.get("rptNo")));
								whzyrmbdkqyMap.put("txnNo", GeneralCalc.objToString(whzyrmbdkqyinfo.get("txnNo")));
								whzyrmbdkqyMap.put("sbkeyNo", GeneralCalc.objToString(whzyrmbdkqyinfo.get("rptNo")));
								zkeyMap.put("txnNo", GeneralCalc.objToString(whzyrmbdkqyinfo.get("txnNo")));
								zkeyMap.put("rptNo", GeneralCalc.objToString(whzyrmbdkqyinfo.get("rptNo")));
								zkeyMap.put("dclKind", GeneralCalc.objToString(whzyrmbdkqyinfo.get("dclKind")));
								dldclbsc.put("txnNo", GeneralCalc.objToString(whzyrmbdkqyinfo.get("txnNo")));
								dldclbsc.put("rptNo", GeneralCalc.objToString(whzyrmbdkqyinfo.get("rptNo")));
								dldclbsc.put("dclKind", GeneralCalc.objToString(whzyrmbdkqyinfo.get("dclKind")));
								dao.updateDecalreBuss(CIConstants.TAB_SB_WHZYRMBDKQY, whzyrmbdkqyMap, fkeyMap, EbpConstants.TABLE_INFO);
								dao.updateDecalre(dldclbsc, zkeyMap, EbpConstants.TABLE_INFO);
								log.error("记录存在，状态不为已发送申报，执行更新操作完毕！" + whzyrmbdkqy);
								dao.create(
										whzyrmbdkqy.getCredconamount(),
										whzyrmbdkqy.getCredconcurr(),
										whzyrmbdkqy.getDatasources(),
										dataType,
										String.valueOf(line),
										fileName,
										fileType,
										"Y",
										orgNo,
										DataUtil.getDate(tranDate), "第"
												+ (line)
												+ "行解析成功,记录存在，状态不为已发送申报，执行更新操作完毕！",
										"", "", "", whzyrmbdkqy.getYwbh());
							} else {
								dao.create(
										whzyrmbdkqy.getCredconamount(),
										whzyrmbdkqy.getCredconcurr(),
										whzyrmbdkqy.getDatasources(),
										dataType,
										String.valueOf(line),
										fileName,
										fileType,
										"Y",
										orgNo,
										DataUtil.getDate(tranDate), "第"
												+ (line)
												+ "行解析成功,记录存在，状态为已发送申报，不执行操作！", "",
										"", "", whzyrmbdkqy.getYwbh());
								log.error("记录存在，状态为已发送申报，不执行操作！");
							}
						} else {
							// 在此处理采集的C或D类型的数据
							boolean isParsefile = false;
							if ("C".equals(array[0]) || "D".equals(array[0])) {
								// 是否存在A类型数据，若不存在便不接受此数据
								/*whzyrmbdkqy.setActiontype("A");
								List listA = whzyrmbdkqyManager
										.getDataByYWBHDATASources(whzyrmbdkqy);
								whzyrmbdkqy.setActiontype("C");
								List listC = whzyrmbdkqyManager
										.getDataByYWBHDATASources(whzyrmbdkqy);
								whzyrmbdkqy.setActiontype("D");
								List listD = whzyrmbdkqyManager
										.getDataByYWBHDATASources(whzyrmbdkqy);
								Whzyrmbdkqy temp = null;
								if (null != listA || null != listC
										|| null != listD) {
									// 将类型更新掉
									if (null != listA) {
										temp = (Whzyrmbdkqy) listA.get(0);
									} else if (null != listC) {
										temp = (Whzyrmbdkqy) listC.get(0);
									} else if (null != listD) {
										temp = (Whzyrmbdkqy) listD.get(0);
									}
									if ("Y".equals(temp.getSfysb())) {
										temp.setSfzx("N");
										whzyrmbdkqyManager.update(temp);
										whzyrmbdkqy.setActiontype(array[0]);
										whzyrmbdkqyManager.create(whzyrmbdkqy);
										isParsefile = true;
									} else {
										dao.create(
												whzyrmbdkqy.getCredconamount(),
												whzyrmbdkqy.getCredconcurr(),
												whzyrmbdkqy.getDatasources(),
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
												"", "", whzyrmbdkqy.getYwbh());
										log.error("接收到" + array[0]
												+ "的数据，在库中存在"
												+ temp.getActiontype()
												+ "类型数据未申报，不执行操作！");
										isParsefile = false;
									}
								} else {
									dao
											.create(whzyrmbdkqy
													.getCredconamount(),
													whzyrmbdkqy
															.getCredconcurr(),
													whzyrmbdkqy
															.getDatasources(),
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
													"", "", "", whzyrmbdkqy
															.getYwbh());
									log.error("接收到" + array[0]
											+ "的数据，在库中不存在A,C或D类型数据，此数据不执行操作！");
									isParsefile = false;
								}*/
							} else {
								dao.create(whzyrmbdkqyMap, CIConstants.TAB_SB_WHZYRMBDKQY);
								dao.create(dldclbsc, EbpConstants.DLDCLBSCID);
								isParsefile = true;
							}
							if (isParsefile) {
								parsefile = new ParseBsptFile();
								parsefile.setFileName(fileName);
								parsefile.setDataType(dataType);// "CFAEA"
								parsefile.setFileType(fileType);
								parsefile.setYwbh(whzyrmbdkqy.getYwbh());
								parsefile.setParseDate(DataUtil.getDate(tranDate));
								parsefile.setCursign(whzyrmbdkqy
										.getCredconcurr());
								if (!"".equals(String.valueOf(whzyrmbdkqy
										.getCredconamount())))
									parsefile.setAmount(whzyrmbdkqy
											.getCredconamount());
								parsefile.setOrgNo(orgNo);
								parsefile.setDataSources(whzyrmbdkqy
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
							+ "失败,在第" + (line) + "行解析不成功");
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
					dao.create(parsefile);
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
	 * @throws Exception
	 * @ejb.interface-method
	 */
	public Whzyrmbdkqy parseLine(String[] fields, String orgNo, String tranDate)
			throws Exception {
		java.util.Date tmpDate = null;
		java.sql.Date valuedate = null;
		java.sql.Date maturity = null;
		java.sql.Date recdate = null;
		Whzyrmbdkqy whzyrmbdkqy = new Whzyrmbdkqy();
		try {
			try {
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
				// 贷款起息日
				tmpDate = format.parse(fields[4].replaceAll("\"", "").trim());
				valuedate = new java.sql.Date(tmpDate.getTime());
				// 贷款到期日
				tmpDate = format.parse(fields[7].replaceAll("\"", "").trim());
				maturity = new java.sql.Date(tmpDate.getTime());
				// 数据接收或创建时间
				recdate = GeneralCalc.strToSQLDate(tranDate);
			} catch (Exception e) {
				System.out.println("核心传递的日期格式有误!"+e.getMessage());
				log.equals("核心传递的日期格式有误!"+e.getMessage());
			}
			whzyrmbdkqy.setActiontype("A"); 												// 操作类型
			whzyrmbdkqy.setActiondesc(""); 													// 删除原因
			String hxXnBranchCode = fields[0].replaceAll("\"", "").trim();					// 核心传递的编号(包含国内金融机构标识码)，用于下面获取金融机构标识码
			whzyrmbdkqy.setCreditorcode(fields[1].replaceAll("\"", "").trim()); 			// 债权人代码
			whzyrmbdkqy.setDebtorcode(fields[2].replaceAll("\"", "").trim()); 				// 债务人代码
			whzyrmbdkqy.setDebtorname(fields[3].replaceAll("\"", "").trim()); 				// 债务人中文名称
			if (null != valuedate && !"".equals(valuedate))
				whzyrmbdkqy.setValuedate(valuedate); 										// 贷款起息日
			whzyrmbdkqy.setCredconcurr(fields[5].replaceAll("\"", "").trim()); 				// 贷款签约币种。字母代码，必须在币种代码表里存在
			whzyrmbdkqy.setCredconamount(null == fields[6]
					|| "".equals(fields[6].replaceAll("\"", "").trim()) ? null
					: new BigDecimal(fields[6].replaceAll("\"", "").trim())); 				// 贷款签约金额
			if (null != maturity && !"".equals(maturity))
				whzyrmbdkqy.setMaturity(maturity); 											// 贷款到期日
//			whzyrmbdkqy.setExplcurr(fields[8].replaceAll("\"", "").trim()); 				// 质押外汇币种。字母代码，必须在币种代码表里存在
//			whzyrmbdkqy.setExplamount(null == fields[9]
//					|| "".equals(fields[9].replaceAll("\"", "").trim()) ? ""
//					: new BigDecimal(fields[9].replaceAll("\"", "").trim())); 				// 质押外汇金额
			// 将核心传递的外汇质押币种、金额拼接
			whzyrmbdkqy.setExplcurrInfos(fields[8].replaceAll("\"", "").trim()+";"+fields[9].replaceAll("\"", "").trim());
			whzyrmbdkqy.setRemark(fields[10].replaceAll("\"", "").trim()); 					// 备注
			whzyrmbdkqy.setHxxnwzbh(fields[11].replaceAll("\"", "").trim()); 				// 核心虚拟外汇质押人民币贷款编号(不能用于申报)，核心传递的用来确定唯一一条记录，方便余额、变动记录的操作
			if (null != recdate && !"".equals(recdate))
				whzyrmbdkqy.setRecdate(recdate); 											// 接收/创建时间
			whzyrmbdkqy.setDatasources("HX"); 												// 数据来源
			
			// 虚拟国内外汇贷款编号的第3位至14位为银行金融机构标识码
			String branchCode = hxXnBranchCode.substring(2, 14);
			// 根据金融机构标识码，获取国结机构号(采集机构)
			whzyrmbdkqy.setBank_Id(dao.getOrgNoByBranchCode(branchCode));
			whzyrmbdkqy.setExplrmblono(DclNoFactory.getEXDEBTCODE("whzyrmbdkqy",whzyrmbdkqy.getBank_Id(),null, "")); 	// 外汇质押人民币贷款编号(核心不传递外债编号，国结自己生成)
			whzyrmbdkqy.setYwbh(whzyrmbdkqy.getExplrmblono()); 								// 业务编号       跟外汇质押人民币贷款编号同
			SerialNoFactory snf =  new SerialNoFactory();
			String txnNo = snf.getSerialNo(EbpConstants.DLDCLBSCID,16);
			whzyrmbdkqy.setNguid(txnNo);
			whzyrmbdkqy.setRwidh(whzyrmbdkqy.getNguid());
			whzyrmbdkqy.setSxbz("1");
			whzyrmbdkqy.setSfzx("Y");
			whzyrmbdkqy.setSfysb("N");
			whzyrmbdkqy.setHanddate(DataUtil.getTime(tranDate));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("解析核心传递的外汇质押人民币签约数据有误:"+e.getMessage());
			Log.error("解析核心传递的外汇质押人民币签约数据有误:"+e.getMessage());
			throw new Exception("解析核心传递的外汇质押人民币签约数据有误:"+e.getMessage());
		}
		return whzyrmbdkqy;
	}
}