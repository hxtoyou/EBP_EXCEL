package com.ebills.product.declare.parse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ebills.commons.SerialNoFactory;
import com.ebills.declare.bussiness.DclNoFactory;
import com.ebills.product.declare.dao.BsptFileDAO;
import com.ebills.product.declare.domain.Fjmjgck;
import com.ebills.product.declare.domain.ParseBsptFile;
import com.ebills.product.declare.domain.ReceiveBsptFile;
import com.ebills.product.declare.util.CIConstants;
import com.ebills.product.declare.util.DataUtil;
import com.ebills.product.declare.util.GeneralCalc;
import com.ebills.product.declare.util.RecvDataChecker;
import com.ebills.util.EbillsLog;
import com.ebills.utils.EbpConstants;


public class ParseFjmjgck {
	private String className = this.getClass().getName();
	private EbillsLog log = new EbillsLog(className);
	private BufferedReader bufferRead = null;
	private RecvDataChecker recvDataChecker = new RecvDataChecker();
	BsptFileDAO dao = new BsptFileDAO();

	/**
	 * 解析从外围系统FTP上下载下来的非居民机构的文件
	 * @param file		需要解析的文件名称
	 * @param orgNo		采集机构
	 * @param dataType	数据文件
	 * @param fileType	文件类型
	 * @param tranDate	解析日期
	 * @return
	 * 
	 */
	public String parse(File file, String orgNo, String dataType,
			String FileType, String tranDate) throws Exception {
		String tip = "";
		String Readin = "";
		int linenumber = 0;
		Fjmjgck fjmjgck = null;
		String fileName = file.getName();
		ParseBsptFile parsefile = null;
		ReceiveBsptFile receiveBsptFile = new ReceiveBsptFile();
		receiveBsptFile.setFileName(fileName);
		try {
			bufferRead = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "GB2312"));
			while ((Readin = bufferRead.readLine()) != null) {
				try {
					if (null != Readin && !"".equals(Readin.trim())) {
						linenumber++;
						String[] fields = Readin.split(",");
						fjmjgck = this.parseLine(fields, orgNo, tranDate);
						tip += recvDataChecker.check_Fjmjgck(fjmjgck);
						
						Map fjmjgckMap = GeneralCalc.getValueMap(fjmjgck);
						String rptNo = GeneralCalc.objToString(fjmjgck.getExdebtcode());
						fjmjgckMap.put("debtypeXXX", dao.getSBDM(fjmjgck.getDebtype(), "zbzwlxejcode", "pazbzwlx", "zbzwlxej"));
						fjmjgckMap.put("creditortypeXXX", dao.getSBDM(fjmjgck.getCreditortype(), "zbjwztejcode", "pazbjwzttype", "zbjwztej"));
						fjmjgckMap.put("crehqcodeXXX", dao.getSBDM(fjmjgck.getCrehqcode(), "safeCode", "PACY", "cnName"));
						fjmjgckMap.put("opercodeXXX", dao.getSBDM(fjmjgck.getOpercode(), "safeCode", "PACY", "cnName"));
						fjmjgckMap.put("txnNo",fjmjgck.getNguid());
						fjmjgckMap.put("sbkeyNo",rptNo);
						Map<String, Object> dldclbsc = new HashMap<String, Object>();
						dldclbsc.put("txnNo", fjmjgck.getNguid());
						dldclbsc.put("orgNo", fjmjgck.getBank_Id());
						dldclbsc.put("rptNo", rptNo);
						String dclKind = dao.getDldclMDName(dataType);
						dldclbsc.put("dclKind", dclKind);
						dldclbsc.put("dclDate", fjmjgck.getRecdate());
						dldclbsc.put("initDate", fjmjgck.getRecdate());
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
						dldclbsc.put("hxxnwzbh", fjmjgck.getHxxnwzbh());
						// 根据申报主键和数据来源获取对应记录
						List<Map<String, Object>> lst = dao.getDecalreByRptNo(rptNo, dclKind, EbpConstants.TABLE_INFO);
						if (null != lst && lst.size() > 0) {
							Map<String, Object> fjmjgckinfo = (Map<String, Object>) lst.get(0);
							if (null != fjmjgckinfo
									&& "0".equals(GeneralCalc.objToString(fjmjgckinfo.get("bscState")))) {
								Map<String,Object> fkeyMap = new HashMap<String, Object>();
								Map<String,Object> zkeyMap = new HashMap<String, Object>();
								fkeyMap.put("txnNo", GeneralCalc.objToString(fjmjgckinfo.get("txnNo")));
								fkeyMap.put("sbkeyNo", GeneralCalc.objToString(fjmjgckinfo.get("rptNo")));
								fjmjgckMap.put("txnNo", GeneralCalc.objToString(fjmjgckinfo.get("txnNo")));
								fjmjgckMap.put("sbkeyNo", GeneralCalc.objToString(fjmjgckinfo.get("rptNo")));
								zkeyMap.put("txnNo", GeneralCalc.objToString(fjmjgckinfo.get("txnNo")));
								zkeyMap.put("rptNo", GeneralCalc.objToString(fjmjgckinfo.get("rptNo")));
								zkeyMap.put("dclKind", GeneralCalc.objToString(fjmjgckinfo.get("dclKind")));
								dldclbsc.put("txnNo", GeneralCalc.objToString(fjmjgckinfo.get("txnNo")));
								dldclbsc.put("rptNo", GeneralCalc.objToString(fjmjgckinfo.get("rptNo")));
								dldclbsc.put("dclKind", GeneralCalc.objToString(fjmjgckinfo.get("dclKind")));
								dao.updateDecalreBuss(CIConstants.TAB_SB_FJMJGCK, fjmjgckMap, fkeyMap, EbpConstants.TABLE_INFO);
								dao.updateDecalre(dldclbsc, zkeyMap, EbpConstants.TABLE_INFO);
								log.error("记录存在，状态不为已发送申报，执行更新操作完毕！" + fjmjgck);
								// 金额、币种、来源 类型、序号、文件名称 文件类型、是否解析通过、执行机构
								// 解析时间、解析备注1
								dao.create(
										null,
										fjmjgck.getContractcurr(),
										fjmjgck.getDatasources(),
										dataType,
										String.valueOf(linenumber),
										fileName,
										FileType,
										"Y",
										orgNo,
										DataUtil.getDate(tranDate), "第"
												+ (linenumber)
												+ "行解析成功,记录存在，状态不为已发送申报，执行更新操作完毕！",
										"", "", "", fjmjgck.getYwbh());
							} else {
								dao.create(
										null,
										fjmjgck.getContractcurr(),
										fjmjgck.getDatasources(),
										dataType,
										String.valueOf(linenumber),
										fileName,
										FileType,
										"Y",
										orgNo,
										DataUtil.getDate(tranDate), "第"
												+ (linenumber)
												+ "行解析成功,记录存在，状态为已发送申报，不执行操作！", "",
										"", "", fjmjgck.getYwbh());
							}
						} else {
							// 在此处理采集的C或D类型的数据(由于加入山东联盟的银行 核心不传递ActionType，所以这个if语句是进不去的，只能进入到else语句里)
							boolean isParsefile = false;
							if ("C".equals(fields[0]) || "D".equals(fields[0])) {
								// 是否存在A类型数据，若不存在便不接受此数据
								/*fjmjgck.setActiontype("A");
								List listA = fjmjgckManager
										.getDataByYWBHDATASources(fjmjgck);
								fjmjgck.setActiontype("C");
								List listC = fjmjgckManager
										.getDataByYWBHDATASources(fjmjgck);
								fjmjgck.setActiontype("D");
								List listD = fjmjgckManager
										.getDataByYWBHDATASources(fjmjgck);
								Fjmjgck temp = null;
								if (null != listA || null != listC
										|| null != listD) {
									// 将类型更新掉
									if (null != listA) {
										temp = (Fjmjgck) listA.get(0);
									} else if (null != listC) {
										temp = (Fjmjgck) listC.get(0);
									} else if (null != listD) {
										temp = (Fjmjgck) listD.get(0);
									}
									if ("Y".equals(temp.getSfysb())) {
										temp.setSfzx("N");
										fjmjgckManager.update(temp);
										fjmjgck.setActiontype(fields[0]);
										fjmjgckManager.create(fjmjgck);
										isParsefile = true;
									} else {
										dao.create(
												null,
												fjmjgck.getContractcurr(),
												fjmjgck.getDatasources(),
												dataType,
												String.valueOf(linenumber),
												fileName,
												FileType,
												"Y",
												orgNo,
												new java.sql.Date(System
														.currentTimeMillis()),
												"第" + (linenumber) + "接收到"
														+ fields[0]
														+ "的数据，在库中存在"
														+ temp.getActiontype()
														+ "类型数据未申报，不执行操作！", "",
												"", "", fjmjgck.getYwbh());
										log.error("接收到" + fields[0]
												+ "的数据，在库中存在"
												+ temp.getActiontype()
												+ "类型数据未申报，不执行操作！");
										isParsefile = false;
									}
								} else {
									dao
											.create(null,
													fjmjgck.getContractcurr(),
													fjmjgck.getDatasources(),
													dataType,
													String.valueOf(linenumber),
													fileName,
													FileType,
													"Y",
													orgNo,
													new java.sql.Date(
															System.currentTimeMillis()),
													"第"
															+ (linenumber)
															+ "接收到"
															+ fields[0]
															+ "的数据，在库中不存在A,C或D类型数据，此数据不执行操作！",
													"", "", "", fjmjgck
															.getYwbh());
									log.error("接收到" + fields[0]
											+ "的数据，在库中不存在A,C或D类型数据，此数据不执行操作！");
									isParsefile = false;
								}*/
							} else {
								dao.create(fjmjgckMap, CIConstants.TAB_SB_FJMJGCK);
								dao.create(dldclbsc, EbpConstants.DLDCLBSCID);
								isParsefile = true;
							}
							if (isParsefile) {
								parsefile = new ParseBsptFile();
								parsefile.setFileName(fileName);
								parsefile.setDataType(dataType);
								parsefile.setFileType(FileType);
								parsefile.setYwbh(fjmjgck.getYwbh());
								parsefile.setParseDate(DataUtil.getDate(tranDate));
								parsefile.setCursign(fjmjgck.getContractcurr());
								//parsefile.setAmount(new BigDecimal(fjmjgck.getAnninrate()));
								parsefile.setOrgNo(orgNo);
								parsefile.setDataSources(fjmjgck
										.getDatasources());
								parsefile.setIsParse("Y");
								parsefile.setRemark1("第" + (linenumber)
										+ "行解析成功");
								parsefile.setFileInfoNo(String
										.valueOf(linenumber));
								dao.create(parsefile);
							}
						}
						log.info("共成功读取了:: " + linenumber + " 行");
					}
				} catch (Exception e) {
					log.error("读取文件" + fileName + "失败,在第" + (linenumber)
							+ "行解析不成功" + e);
					log.error(Readin);
					parsefile = new ParseBsptFile();
					parsefile.setFileName(fileName);
					parsefile.setDataType(dataType);
					parsefile.setFileType(FileType);
					parsefile.setYwbh("ERROR");
					parsefile.setParseDate(DataUtil.getDate(tranDate));
					parsefile.setOrgNo(orgNo);
					parsefile.setDataSources("ERROR");
					parsefile.setIsParse("N");
					parsefile.setFileInfoNo(String.valueOf(linenumber));
					parsefile.setRemark1("第" + (linenumber) + "行解析不成功");
					List list = dao.getErrorData(parsefile);
					if (null == list || list.size() <= 0) {
						dao.create(parsefile);
					}
				}
			}
		} catch (IOException e) {
			log.error("读取文件" + fileName + "失败,在第" + linenumber + "行解析不成功：" + e);
		} finally {
			try {
				bufferRead.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return tip;
	}

	/**
	 * 解析文件
	 * 
	 * 
	 */
	public Fjmjgck parseLine(String[] fields, String orgNo, String tranDate)
			throws Exception {
		java.util.Date tmpDate = null;
		java.sql.Date valuedate = null;
		java.sql.Date recdate = null;
		Fjmjgck fjmjgck = new Fjmjgck();
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			//起息日
			tmpDate = format.parse(fields[4].replaceAll("\"", "").trim());
			valuedate = new java.sql.Date(tmpDate.getTime());
			//数据接收或创建时间
			recdate = GeneralCalc.strToSQLDate(tranDate);
		} catch (Exception e) {
			System.out.println("核心传递的日期格式有误!"+e.getMessage());
			log.equals("核心传递的日期格式有误!"+e.getMessage());
		}
		try {
			fjmjgck.setActiontype("A");																// 操作类型
			fjmjgck.setActiondesc("");																// 修改、删除原因
			String hxXnBranchCode = fields[0].replaceAll("\"", "").trim();							// 核心传递的虚拟金融机构标识码(该虚拟编号中存在金融机构标识码，方便后面根据金融机构标识码，获取国结机构号(采集机构))
			fjmjgck.setLimittype(fields[1].replaceAll("\"", "").trim());							// 账户类型
			fjmjgck.setDebtorcode(fields[2].replaceAll("\"", "").trim());							// 债务人代码
			fjmjgck.setDebtype(fields[3].replaceAll("\"", "").trim());								// 债务类型
			if (null != valuedate && !"".equals(valuedate))
				fjmjgck.setValuedate(valuedate);													// 起息日
			fjmjgck.setContractcurr(fields[5].replaceAll("\"", "").trim());							// 签约币种
			fjmjgck.setFloatrate(fields[6].replaceAll("\"", "").trim());							// 是否浮动利率
			if (null != fields[7] && !"".equals(fields[7].replaceAll("\"", "").trim()))
				// 根据城商联盟徐宇20130110日说，需要将核心传递的年化利率值除以100
				fjmjgck.setAnninrate(GeneralCalc.roundFormat(Double.parseDouble(fields[7].replaceAll("\"", "").trim())/100,8));// 年化利率值
			fjmjgck.setCreditorcode(fields[8].replaceAll("\"", "").trim());							// 债权人代码
			fjmjgck.setCreditorname(fields[9].replaceAll("\"", "").trim());							// 债权人中文名称
			fjmjgck.setCreditornamen(fields[10].replaceAll("\"", "").trim());						// 债权人英文名称
			fjmjgck.setCreditortype(fields[11].replaceAll("\"", "").trim());						// 债权人类型代码
			fjmjgck.setCrehqcode(fields[12].replaceAll("\"", "").trim());							// 债权人总部所在国家（地区）代码
			fjmjgck.setOpercode(fields[13].replaceAll("\"", "").trim());							// 债权人经营地所在国家（地区）代码
			fjmjgck.setSpapfeboindex(fields[14].replaceAll("\"", "").trim());						// 是否经外汇局特批不需占用指标
			fjmjgck.setRemark(fields[15].replaceAll("\"", "").trim());								// 备注
			// 这里非居民机构存款的核心虚拟外债编号取外部账号,用于关联对应的余额信息
			fjmjgck.setHxxnwzbh(fields[16].replaceAll("\"", "").trim());							// 外部账号,非报送栏位，核心虚拟外债编号(不能用于申报)，核心传递的用来确定唯一一条记录，方便余额、变动记录的操作
			if (null != recdate && !"".equals(recdate))				
				fjmjgck.setRecdate(recdate);														// 接收/创建时间
			fjmjgck.setDatasources("HX");															// 数据来源(山东城商联盟)
			
			// 核心传递的虚拟金融机构标识码的第3位至14位为银行金融机构标识码
			String branchCode = hxXnBranchCode.substring(2, 14);
			//根据金融机构标识码，获取国结机构号(采集机构)
			fjmjgck.setBank_Id(dao.getOrgNoByBranchCode(branchCode));
			fjmjgck.setExdebtcode(DclNoFactory.getEXDEBTCODE("fjmjgck",fjmjgck.getBank_Id(),null, "")); 						// 外债编号编号(核心不传递外债编号，国结自己生成)
			fjmjgck.setYwbh(fjmjgck.getExdebtcode()); 												// 业务编号，资本项下为外债编号			
			SerialNoFactory snf =  new SerialNoFactory();
			String txnNo = snf.getSerialNo(EbpConstants.DLDCLBSCID,16);
			fjmjgck.setNguid(txnNo);										// 国结生成的流水号
			fjmjgck.setRwidh(fjmjgck.getNguid());
			fjmjgck.setSxbz("1");
			fjmjgck.setSfzx("Y");
			fjmjgck.setSfysb("N");
			fjmjgck.setHanddate(DataUtil.getTime(tranDate));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("解析核心传递的非居民机构数据有误:"+e.getMessage());
			log.equals("解析核心传递的非居民机构数据有误:"+e.getMessage());
			throw new Exception("解析核心传递的非居民机构数据有误:"+e.getMessage());
		}
		return fjmjgck;
	}
}