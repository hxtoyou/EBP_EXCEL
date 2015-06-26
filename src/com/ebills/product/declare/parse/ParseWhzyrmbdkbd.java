package com.ebills.product.declare.parse;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ebills.commons.SerialNoFactory;
import com.ebills.declare.bussiness.DclNoFactory;
import com.ebills.product.declare.dao.BsptFileDAO;
import com.ebills.product.declare.domain.ParseBsptFile;
import com.ebills.product.declare.domain.ReceiveBsptFile;
import com.ebills.product.declare.domain.Whzyrmbdkbd;
import com.ebills.product.declare.util.CIConstants;
import com.ebills.product.declare.util.DataUtil;
import com.ebills.product.declare.util.GeneralCalc;
import com.ebills.product.declare.util.RecvDataChecker;
import com.ebills.util.EbillsLog;
import com.ebills.utils.EbpConstants;


public class ParseWhzyrmbdkbd {
	private String className = this.getClass().getName();
	private EbillsLog log = new EbillsLog(className);
	private BufferedReader br = null;
	private RecvDataChecker recvDataChecker = new RecvDataChecker();
	BsptFileDAO dao = new BsptFileDAO();


	/**
	 * 解析从外围系统FTP上下载下来的外汇质押人民币贷款变动的文件
	 * @param file        需要解析的文件名称
	 * @param orgNo       采集机构
	 * @param dataType    数据文件
	 * @param fileType    文件类型
	 * @param tranDate    解析日期
	 * @return
	 * @ejb.interface-method
	 */
	public String parse(File file, String orgNo, String dataType,
			String fileType, String tranDate) throws Exception {
		String tip = "";
		String str = "";
		int line = 0;
		Whzyrmbdkbd whzyrmbdkbd = null;
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
						whzyrmbdkbd = parseLine(array, orgNo, tranDate);
						tip += recvDataChecker.check_WhzyrmbdkBdxx(whzyrmbdkbd);
						if (null == whzyrmbdkbd)
							whzyrmbdkbd = new Whzyrmbdkbd();
						Map whzyrmbdkbdMap = GeneralCalc.getValueMap(whzyrmbdkbd);
						String rptNo = GeneralCalc.objToString(whzyrmbdkbd.getExplrmblono()) + GeneralCalc.objToString(whzyrmbdkbd.getChangeno());
						whzyrmbdkbdMap.put("txnNo",whzyrmbdkbd.getNguid());
						whzyrmbdkbdMap.put("sbkeyNo",rptNo);
						Map<String, Object> dldclbsc = new HashMap<String, Object>();
						dldclbsc.put("txnNo", whzyrmbdkbd.getNguid());
						dldclbsc.put("orgNo", whzyrmbdkbd.getBank_Id());
						dldclbsc.put("rptNo", rptNo);
						String dclKind = dao.getDldclMDName(dataType);
						dldclbsc.put("dclKind", dclKind);
						dldclbsc.put("dclDate", whzyrmbdkbd.getRecdate());
						dldclbsc.put("initDate", whzyrmbdkbd.getRecdate());
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
						dldclbsc.put("hxxnwzbh", whzyrmbdkbd.getHxxnwzbh()+"_HZ");
						
						// 根据申报主键获取对应记录
						List<Map<String, Object>> lst = dao.getDecalreByRptNo(rptNo, dclKind, EbpConstants.TABLE_INFO);
						if (null != lst && lst.size() > 0) {
							Map<String, Object> whzyrmbdkbdinfo = (Map<String, Object>) lst.get(0);
							if (null != whzyrmbdkbdinfo
									&& !"1".equals(GeneralCalc.objToString(whzyrmbdkbdinfo.get("bscState")))) {
								Map<String,Object> fkeyMap = new HashMap<String, Object>();
								Map<String,Object> zkeyMap = new HashMap<String, Object>();
								fkeyMap.put("txnNo", GeneralCalc.objToString(whzyrmbdkbdinfo.get("txnNo")));
								fkeyMap.put("sbkeyNo", GeneralCalc.objToString(whzyrmbdkbdinfo.get("rptNo")));
								whzyrmbdkbdMap.put("txnNo", GeneralCalc.objToString(whzyrmbdkbdinfo.get("txnNo")));
								whzyrmbdkbdMap.put("sbkeyNo", GeneralCalc.objToString(whzyrmbdkbdinfo.get("rptNo")));
								zkeyMap.put("txnNo", GeneralCalc.objToString(whzyrmbdkbdinfo.get("txnNo")));
								zkeyMap.put("rptNo", GeneralCalc.objToString(whzyrmbdkbdinfo.get("rptNo")));
								zkeyMap.put("dclKind", GeneralCalc.objToString(whzyrmbdkbdinfo.get("dclKind")));
								dldclbsc.put("txnNo", GeneralCalc.objToString(whzyrmbdkbdinfo.get("txnNo")));
								dldclbsc.put("rptNo", GeneralCalc.objToString(whzyrmbdkbdinfo.get("rptNo")));
								dldclbsc.put("dclKind", GeneralCalc.objToString(whzyrmbdkbdinfo.get("dclKind")));
								dao.updateDecalreBuss(CIConstants.TAB_SB_WHZYRMBDKBD, whzyrmbdkbdMap, fkeyMap, EbpConstants.TABLE_INFO);
								dao.updateDecalre(dldclbsc, zkeyMap, EbpConstants.TABLE_INFO);
								log.error("记录存在，状态不为已发送申报，执行更新操作完毕！" + whzyrmbdkbd);
								dao.create(
										whzyrmbdkbd.getPlcoseamount(),
										null,
										whzyrmbdkbd.getDatasources(),
										dataType,
										String.valueOf(line),
										fileName,
										fileType,
										"Y",
										orgNo,
										DataUtil.getDate(tranDate), "第"
												+ (line)
												+ "行解析成功,记录存在，状态不为已发送申报，执行更新操作完毕！",
										"", "", "", whzyrmbdkbd.getYwbh());
							} else {
								dao.create(
										whzyrmbdkbd.getPlcoseamount(),
										null,
										whzyrmbdkbd.getDatasources(),
										dataType,
										String.valueOf(line),
										fileName,
										fileType,
										"Y",
										orgNo,
										DataUtil.getDate(tranDate), "第"
												+ (line)
												+ "行解析成功,记录存在，状态为已发送申报，不执行操作！", "",
										"", "", whzyrmbdkbd.getYwbh());
								log.error("记录存在，状态为已发送申报，不执行操作！");
							}
						} else {
							// 在此处理采集的C或D类型的数据
							boolean isParsefile = false;
							if ("C".equals(array[0]) || "D".equals(array[0])) {
								// 是否存在A类型数据，若不存在便不接受此数据
								/*whzyrmbdkbd.setActiontype("A");
								List listA = whzyrmbdkbdManager
										.getDataByYWBHDATASources(whzyrmbdkbd);
								whzyrmbdkbd.setActiontype("C");
								List listC = whzyrmbdkbdManager
										.getDataByYWBHDATASources(whzyrmbdkbd);
								whzyrmbdkbd.setActiontype("D");
								List listD = whzyrmbdkbdManager
										.getDataByYWBHDATASources(whzyrmbdkbd);
								Whzyrmbdkbd temp = null;
								if (null != listA || null != listC
										|| null != listD) {
									// 将类型更新掉
									if (null != listA) {
										temp = (Whzyrmbdkbd) listA.get(0);
									} else if (null != listC) {
										temp = (Whzyrmbdkbd) listC.get(0);
									} else if (null != listD) {
										temp = (Whzyrmbdkbd) listD.get(0);
									}
									if ("Y".equals(temp.getSfysb())) {
										temp.setSfzx("N");
										whzyrmbdkbdManager.update(temp);
										whzyrmbdkbd.setActiontype(array[0]);
										whzyrmbdkbdManager.create(whzyrmbdkbd);
										isParsefile = true;
									} else {
										dao.create(
												whzyrmbdkbd.getPlcoseamount(),
												null,
												whzyrmbdkbd.getDatasources(),
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
												"", "", whzyrmbdkbd.getYwbh());
										log.error("接收到" + array[0]
												+ "的数据，在库中存在"
												+ temp.getActiontype()
												+ "类型数据未申报，不执行操作！");
										isParsefile = false;
									}

								} else {
									dao
											.create(whzyrmbdkbd
													.getPlcoseamount(),
													null,
													whzyrmbdkbd
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
													"", "", "", whzyrmbdkbd
															.getYwbh());
									log.error("接收到" + array[0]
											+ "的数据，在库中不存在A,C或D类型数据，此数据不执行操作！");
									isParsefile = false;
								}*/
							} else {
								dao.create(whzyrmbdkbdMap, CIConstants.TAB_SB_WHZYRMBDKBD);
								dao.create(dldclbsc, EbpConstants.DLDCLBSCID);
								isParsefile = true;
							}
							if (isParsefile) {
								parsefile = new ParseBsptFile();
								parsefile.setFileName(fileName);
								parsefile.setDataType(dataType);// "CFAEB"
								parsefile.setFileType(fileType);
								parsefile.setYwbh(whzyrmbdkbd.getYwbh());
								parsefile.setParseDate(DataUtil.getDate(tranDate));
								if (!"".equals(String.valueOf(whzyrmbdkbd
										.getPlcoseamount())))
									parsefile.setAmount(whzyrmbdkbd
											.getPlcoseamount());
								parsefile.setOrgNo(orgNo);
								parsefile.setDataSources(whzyrmbdkbd
										.getDatasources());
								parsefile.setIsParse("Y");
								if (null == whzyrmbdkbd.getExplrmblono()
										|| "".equals(whzyrmbdkbd
												.getExplrmblono())) {
									parsefile.setRemark1("第" + (line)
											+ "行解析成功,但关联签约信息失败！请核查！");
								} else {
									parsefile
											.setRemark1("第" + (line) + "行解析成功");
								}

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
					parsefile.setDataType(dataType);// "CFAAA"
					parsefile.setFileType(fileType);// "TXT"
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
	public Whzyrmbdkbd parseLine(String[] fields, String orgNo, String tranDate)
			throws Exception {
		java.sql.Date recdate = null;
		Whzyrmbdkbd whzyrmbdkbd = new Whzyrmbdkbd();
		try {
			// 数据接收或创建时间
			recdate = GeneralCalc.strToSQLDate(tranDate);
		} catch (Exception e) {
			e.printStackTrace();
			log.equals("日期格式化错误: " + e);
		}
		try {
			whzyrmbdkbd.setActiontype("A"); 										// 操作类型
			whzyrmbdkbd.setActiondesc(""); 											// 删除原因
			String hxXnBranchCode = fields[0].replaceAll("\"", "").trim();													// 核心传递的编号(包含国内金融机构标识码)，用于下面获取金融机构标识码
			whzyrmbdkbd.setBuscode(fields[1].replaceAll("\"", "").trim()); 			// 报告期
			whzyrmbdkbd.setChangeno(fields[2].replaceAll("\"", "").trim()); 		// 变动编号
			whzyrmbdkbd.setMonbeloadbal(null == fields[3]
					|| "".equals(fields[3].replaceAll("\"", "").trim()) ? null : new BigDecimal(
					fields[3].replaceAll("\"", "").trim())); 						// 月初贷款余额
			whzyrmbdkbd.setCredwithamount(null == fields[4]
					|| "".equals(fields[4].replaceAll("\"", "").trim()) ? null : new BigDecimal(
					fields[4].replaceAll("\"", "").trim())); 						// 本月提款金额
			whzyrmbdkbd.setCredrepayamount(null == fields[5]
					|| "".equals(fields[5].replaceAll("\"", "").trim()) ? null : new BigDecimal(
					fields[5].replaceAll("\"", "").trim())); 						// 本月还本金额
			whzyrmbdkbd.setPicamount(null == fields[6]
					|| "".equals(fields[6].replaceAll("\"", "").trim()) ? null : new BigDecimal(
					fields[6].replaceAll("\"", "").trim())); 						// 本月付息费金额
			whzyrmbdkbd.setMonenloadbal(null == fields[7]
					|| "".equals(fields[7].replaceAll("\"", "").trim()) ? null : new BigDecimal(
					fields[7].replaceAll("\"", "").trim())); 						// 月末贷款余额
//			whzyrmbdkbd.setExplcurr(fields[8].replaceAll("\"", "").trim()); 		// 外汇质押币种
//			whzyrmbdkbd.setExplbala(null == fields[9]
//					|| "".equals(fields[9].replaceAll("\"", "").trim()) ? null : new BigDecimal(
//					fields[9].replaceAll("\"", "").trim())); 						// 质押外汇余额
//			whzyrmbdkbd.setExplperamount(null == fields[10]
//					|| "".equals(fields[10].replaceAll("\"", "").trim()) ? null : new BigDecimal(
//					fields[10].replaceAll("\"", "").trim())); 						// 质押外汇履约金额
//			whzyrmbdkbd.setPlcoseamount(null == fields[11]
//					|| "".equals(fields[11].replaceAll("\"", "").trim()) ? null : new BigDecimal(
//					fields[11].replaceAll("\"", "").trim())); 						// 质押履约结汇金额
			// 将核心传递的外汇质押币种、质押外汇余额、质押外汇履约金额、质押履约结汇金额拼接  如：USD;5;6;7
			whzyrmbdkbd.setExplcurrInfos(fields[8].replaceAll("\"", "").trim()+";"+fields[9].replaceAll("\"", "").trim()+";"
					+fields[10].replaceAll("\"", "").trim()+";"+fields[11].replaceAll("\"", "").trim());	
			whzyrmbdkbd.setRemark(fields[12].replaceAll("\"", "").trim()); 			// 备注
			whzyrmbdkbd.setHxxnwzbh(fields[13].replaceAll("\"", "").trim()); 		// 核心虚拟外汇质押人民币贷款编号(不能用于申报)，核心传递的用来确定唯一一条记录，方便余额、变动记录的操作
			if (null != recdate && !"".equals(recdate))
				whzyrmbdkbd.setRecdate(recdate); 									// 接收/创建时间
			whzyrmbdkbd.setDatasources("HX"); 										// 数据来源
			
			// 虚拟国内外汇贷款编号的第3位至14位为银行金融机构标识码
			String branchCode = hxXnBranchCode.substring(2, 14);
			// 根据金融机构标识码，获取国结机构号(采集机构)
			whzyrmbdkbd.setBank_Id(dao.getOrgNoByBranchCode(branchCode));
			Map dldclMap = this.getWzbh(whzyrmbdkbd, orgNo);
			if(dldclMap != null){
				whzyrmbdkbd.setExplrmblono((String) dldclMap.get("rptNo")); 			// 外汇质押人民币贷款编号      通过签约信息取得
				whzyrmbdkbd.setYwbh(whzyrmbdkbd.getExplrmblono()); 						// 业务编号    跟外汇质押人民币贷款编号同
			}
			
			whzyrmbdkbd.setChangeno(DclNoFactory.getChangeNo("whzyrmbdkbd", whzyrmbdkbd.getExplrmblono()));	// 变动编号
			SerialNoFactory snf =  new SerialNoFactory();
			String txnNo = snf.getSerialNo(EbpConstants.DLDCLBSCID,16);
			whzyrmbdkbd.setNguid(txnNo); 					// 业务流水号
			whzyrmbdkbd.setRwidh(whzyrmbdkbd.getNguid());
			whzyrmbdkbd.setSxbz("1");
			whzyrmbdkbd.setSfzx("Y");
			whzyrmbdkbd.setSfysb("N");
			whzyrmbdkbd.setHanddate(DataUtil.getTime(tranDate));
		} catch (Exception e) {
			log.info("date转换出错");
			throw new Exception("格式转换失败" + e.getMessage());
		}
		return whzyrmbdkbd;
	}

	/**
	 * 根据外债类型和核心虚拟外债编号，获取对应的签约信息中的外债编号
	 * 
	 * @param whzyrmbdkbd
	 * @param orgNo
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> getWzbh(Whzyrmbdkbd bdxx, String orgNo) throws Exception {
		Map<String, Object> retMap = null;
		try {
			// 根据外债类型和核心虚拟外债编号，获取对应的签约信息中的外债编号
			List<Map<String, Object>> qyxxQuerrylist = dao.getDecalreByHxxnwzbh(bdxx.getHxxnwzbh(),
					bdxx.getDatasources(), orgNo, EbpConstants.TABLE_INFO);
			if (qyxxQuerrylist != null && qyxxQuerrylist.size() > 0) {
				for (Map<String, Object> map : qyxxQuerrylist) {
					retMap = map;
				}
			} else {
				log.debug("无法从业务信息：" + bdxx.getHxxnwzbh() + "|系统来源:"
						+ bdxx.getDatasources() + "|操作类型" + "A" + "|执行机构："
						+ orgNo + "取得签约信息");
			}
		} catch (Exception e) {
			throw new Exception("无法从业务信息：" + bdxx.getHxxnwzbh()
					+ "|系统来源:" + bdxx.getDatasources() + "|操作类型" + "A"
					+ "|执行机构：" + orgNo + "取得签约信息");
		}
		return retMap;
	}
}