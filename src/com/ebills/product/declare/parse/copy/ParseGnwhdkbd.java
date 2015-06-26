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
import com.ebills.product.declare.domain.Gnwhdkbd;
import com.ebills.product.declare.domain.ParseBsptFile;
import com.ebills.product.declare.domain.ReceiveBsptFile;
import com.ebills.product.declare.util.CIConstants;
import com.ebills.product.declare.util.DataUtil;
import com.ebills.product.declare.util.GeneralCalc;
import com.ebills.product.declare.util.RecvDataChecker;
import com.ebills.util.EbillsLog;
import com.ebills.utils.EbpConstants;


/**
 * @ejb.bean name="ParseGnwhdkbdManager" display-name="ParseGnwhdkbdManager"
 *           local-jndi-name="ParseGnwhdkbdManagerLocal" type="Stateless"
 *           transaction-type="Container" view-type="local"
 * @ejb.home local-extends="javax.ejb.EJBLocalHome"
 *           local-class="com.amerisia.ebills.bspt.apix.ParseGnwhdkbdManagerLocalHome"
 * @ejb.interface local-extends="javax.ejb.EJBLocalObject"
 *                local-class="com.amerisia.ebills.bspt.apix.ParseGnwhdkbdManagerLocal"
 * @ejb.transaction type="Required"
 */
public class ParseGnwhdkbd {
	private String className = this.getClass().getName();
	private EbillsLog log = new EbillsLog(className);
	private BufferedReader br = null;
	private RecvDataChecker recvDataChecker = new RecvDataChecker();
	BsptFileDAO dao = new BsptFileDAO();
	

	/**
	 * 解析从外围系统FTP上下载下来的国内外汇贷款变动的文件
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
		Gnwhdkbd gnwhdkbd = new Gnwhdkbd();
		String str = "";
		int line = 0;
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
						gnwhdkbd = parseLine(array, orgNo, tranDate);
						//去掉没有外债编号的变动数据2013830
						 if(gnwhdkbd.getDofoexlocode()==null ||gnwhdkbd.getDofoexlocode().equals(""))
							 continue;
						tip += recvDataChecker.check_GnwhdkBdxx(gnwhdkbd);
						if (null == gnwhdkbd)
							gnwhdkbd = new Gnwhdkbd();
						Map gnwhdkbdMap = GeneralCalc.getValueMap(gnwhdkbd);
						String rptNo = GeneralCalc.objToString(gnwhdkbd.getDofoexlocode()) + GeneralCalc.objToString(gnwhdkbd.getChangeno());
						gnwhdkbdMap.put("txnNo",gnwhdkbd.getNguid());
						gnwhdkbdMap.put("sbkeyNo",rptNo);
						Map<String, Object> dldclbsc = new HashMap<String, Object>();
						dldclbsc.put("txnNo", gnwhdkbd.getNguid());
						dldclbsc.put("orgNo", gnwhdkbd.getBank_Id());
						dldclbsc.put("rptNo", rptNo);
						String dclKind = dao.getDldclMDName(dataType);
						dldclbsc.put("dclKind", dclKind);
						dldclbsc.put("dclDate", gnwhdkbd.getRecdate());
						dldclbsc.put("initDate", gnwhdkbd.getRecdate());
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
						dldclbsc.put("hxxnwzbh", gnwhdkbd.getHxxnwzbh()+"_HZ");
						
						// 根据申报主键获取对应记录
						List<Map<String, Object>> lst = dao.getDecalreByRptNo(rptNo, dclKind, EbpConstants.TABLE_INFO);
						if (null != lst && lst.size() > 0) {
							Map<String, Object> gnwhdkbdinfo = (Map<String, Object>) lst.get(0);
							if (null != gnwhdkbdinfo
									&& !"1".equals(GeneralCalc.objToString(gnwhdkbdinfo.get("bscState")))) {
								Map<String,Object> fkeyMap = new HashMap<String, Object>();
								Map<String,Object> zkeyMap = new HashMap<String, Object>();
								fkeyMap.put("txnNo", GeneralCalc.objToString(gnwhdkbdinfo.get("txnNo")));
								fkeyMap.put("sbkeyNo", GeneralCalc.objToString(gnwhdkbdinfo.get("rptNo")));
								gnwhdkbdMap.put("txnNo", GeneralCalc.objToString(gnwhdkbdinfo.get("txnNo")));
								gnwhdkbdMap.put("sbkeyNo", GeneralCalc.objToString(gnwhdkbdinfo.get("rptNo")));
								zkeyMap.put("txnNo", GeneralCalc.objToString(gnwhdkbdinfo.get("txnNo")));
								zkeyMap.put("rptNo", GeneralCalc.objToString(gnwhdkbdinfo.get("rptNo")));
								zkeyMap.put("dclKind", GeneralCalc.objToString(gnwhdkbdinfo.get("dclKind")));
								dldclbsc.put("txnNo", GeneralCalc.objToString(gnwhdkbdinfo.get("txnNo")));
								dldclbsc.put("rptNo", GeneralCalc.objToString(gnwhdkbdinfo.get("rptNo")));
								dldclbsc.put("dclKind", GeneralCalc.objToString(gnwhdkbdinfo.get("dclKind")));
								dao.updateDecalreBuss(CIConstants.TAB_SB_GNWHDKBD, gnwhdkbdMap, fkeyMap, EbpConstants.TABLE_INFO);
								dao.updateDecalre(dldclbsc, zkeyMap, EbpConstants.TABLE_INFO);
								log.error("记录存在，状态不为已发送申报，执行更新操作完毕！" + gnwhdkbd);
								// 金额、币种、来源 类型、序号、文件名称 文件类型、是否解析通过、执行机构
								// 解析时间、解析备注1
								dao.create(
										gnwhdkbd.getWithamount(),
										gnwhdkbd.getWithcurrence(),
										gnwhdkbd.getDatasources(),
										dataType,
										String.valueOf(line),
										fileName,
										fileType,
										"Y",
										orgNo,
										DataUtil.getDate(tranDate), "第"
												+ (line)
												+ "行解析成功,记录存在，状态不为已发送申报，执行更新操作完毕！",
										"", "", "", gnwhdkbd.getYwbh());
							} else {
								dao.create(
										gnwhdkbd.getWithamount(),
										gnwhdkbd.getWithcurrence(),
										gnwhdkbd.getDatasources(),
										dataType,
										String.valueOf(line),
										fileName,
										fileType,
										"Y",
										orgNo,
										DataUtil.getDate(tranDate), "第"
												+ (line)
												+ "行解析成功,记录存在，状态为已发送申报，不执行操作！", "",
										"", "", gnwhdkbd.getYwbh());
								log.error("记录存在，状态为已发送申报，不执行操作！");
							}
						} else {
							// 在此处理采集的C或D类型的数据
							boolean isParsefile = false;
							if ("C".equals(array[0]) || "D".equals(array[0])) {
								// 是否存在A类型数据，若不存在便不接受此数据
								/*gnwhdkbd.setActiontype("A");
								List listA = gnwhdkbdManager
										.getDataByYWBHDATASources(gnwhdkbd);
								gnwhdkbd.setActiontype("C");
								List listC = gnwhdkbdManager
										.getDataByYWBHDATASources(gnwhdkbd);
								gnwhdkbd.setActiontype("D");
								List listD = gnwhdkbdManager
										.getDataByYWBHDATASources(gnwhdkbd);
								Gnwhdkbd temp = null;
								if (null != listA || null != listC
										|| null != listD) {
									// 将类型更新掉
									if (null != listA) {
										temp = (Gnwhdkbd) listA.get(0);
									} else if (null != listC) {
										temp = (Gnwhdkbd) listC.get(0);
									} else if (null != listD) {
										temp = (Gnwhdkbd) listD.get(0);
									}
									if ("Y".equals(temp.getSfysb())) {
										temp.setSfzx("N");
										gnwhdkbdManager.updateInfo(temp);
										gnwhdkbd.setActiontype(array[0]);
										gnwhdkbdManager.create(gnwhdkbd);
										isParsefile = true;
									} else {
										dao.create(
												gnwhdkbd.getWithamount(),
												gnwhdkbd.getWithcurrence(),
												gnwhdkbd.getDatasources(),
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
												"", "", gnwhdkbd.getYwbh());
										log.error("接收到" + array[0]
												+ "的数据，在库中存在"
												+ temp.getActiontype()
												+ "类型数据未申报，不执行操作！");
										isParsefile = false;
									}
								} else {
									dao
											.create(gnwhdkbd.getWithamount(),
													gnwhdkbd.getWithcurrence(),
													gnwhdkbd.getDatasources(),
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
													"", "", "", gnwhdkbd
															.getYwbh());
									log.error("接收到" + array[0]
											+ "的数据，在库中不存在A,C或D类型数据，此数据不执行操作！");
									isParsefile = false;
								}*/
							} else {
								dao.create(gnwhdkbdMap, CIConstants.TAB_SB_GNWHDKBD);
								dao.create(dldclbsc, EbpConstants.DLDCLBSCID);
								isParsefile = true;
							}
							if (isParsefile) {
								parsefile = new ParseBsptFile();
								parsefile.setFileName(fileName);
								parsefile.setDataType(dataType);
								parsefile.setFileType(fileType);
								parsefile.setYwbh(gnwhdkbd.getYwbh());
								parsefile.setParseDate(DataUtil.getDate(tranDate));
								parsefile
										.setCursign(gnwhdkbd.getWithcurrence());
								if (!"".equals(String.valueOf(gnwhdkbd
										.getWithamount())))
									parsefile.setAmount(gnwhdkbd
											.getWithamount());
								parsefile.setOrgNo(orgNo);
								parsefile.setDataSources(gnwhdkbd
										.getDatasources());
								parsefile.setIsParse("Y");
								if (null == gnwhdkbd.getDofoexlocode()
										|| "".equals(gnwhdkbd.getDofoexlocode())) {
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
						log.info(fileName + "里面共读入了" + line + "条数据");
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
	public Gnwhdkbd parseLine(String[] fields, String orgNo, String tranDate)
			throws Exception {
		java.util.Date tmpDate = null;
		java.sql.Date changeDate = null;
		java.sql.Date recdate = null;
		Gnwhdkbd gnwhdkbd = new Gnwhdkbd();
		try {
			try {
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
				// 变动日期
				tmpDate = format.parse(fields[4].replaceAll("\"", "").trim());
				changeDate = new java.sql.Date(tmpDate.getTime());
				// 数据接收或创建时间
				recdate = GeneralCalc.strToSQLDate(tranDate);
			} catch (Exception e) {
				System.out.println("核心传递的日期格式有误!"+e.getMessage());
				log.equals("核心传递的日期格式有误!"+e.getMessage());
			}
			gnwhdkbd.setActiontype("A");																					// 操作类型
			gnwhdkbd.setActiondesc("");																						// 删除原因
			String hxXnBranchCode = fields[0].replaceAll("\"", "").trim();													// 核心传递的编号(包含国内金融机构标识码)，用于下面获取金融机构标识码
			gnwhdkbd.setBuscode(fields[1].replaceAll("\"", "").trim());														// 银行业务编号
			gnwhdkbd.setChangeno(fields[2].replaceAll("\"", "").trim());													// 变动编号
			gnwhdkbd.setLoanopenbalan(null == fields[3] || "".equals(fields[3].replaceAll("\"", "").trim()) ? null
					: new BigDecimal(fields[3].replaceAll("\"", "").trim()));												// 期初余额
			if (changeDate!=null && !"".equals(changeDate))
				gnwhdkbd.setChangedate(changeDate);																			// 变动日期
			gnwhdkbd.setWithcurrence(fields[5].replaceAll("\"", "").trim()); 												// 提款币种。字母代码，必须在币种代码表里存在			
			gnwhdkbd.setWithamount(null == fields[6] || "".equals(fields[6].replaceAll("\"", "").trim()) ? null
					: new BigDecimal(fields[6].replaceAll("\"", "").trim()));												// 提款金额
			gnwhdkbd.setSettamount(null == fields[7] || "".equals(fields[7].replaceAll("\"", "").trim()) ? null
					: new BigDecimal(fields[7].replaceAll("\"", "").trim()));												// 结汇金额
			gnwhdkbd.setUseofunds(fields[8].replaceAll("\"", "").trim());													// 资金用途
			gnwhdkbd.setPrincurr(fields[9].replaceAll("\"", "").trim()); 													// 还本币种。字母代码，必须在币种代码表里存在
			gnwhdkbd.setRepayamount(null == fields[10] || "".equals(fields[10].replaceAll("\"", "").trim()) ? null
					: new BigDecimal(fields[10].replaceAll("\"", "").trim()));												// 还本金额
			gnwhdkbd.setPrepayamount(null == fields[11] || "".equals(fields[11].replaceAll("\"", "").trim()) ? null
					: new BigDecimal(fields[11].replaceAll("\"", "").trim()));									  			// 购汇还本金额
			gnwhdkbd.setInpaycurr(fields[12].replaceAll("\"", "").trim()); 													// 付息币种。字母代码，必须在币种代码表里存在
			gnwhdkbd.setInpayamount(null == fields[13] || "".equals(fields[13].replaceAll("\"", "").trim()) ? null
					: new BigDecimal(fields[13].replaceAll("\"", "").trim()));												// 付息金额
			gnwhdkbd.setPinpayamount(null == fields[14] || "".equals(fields[14].replaceAll("\"", "").trim()) ? null
					: new BigDecimal(fields[14].replaceAll("\"", "").trim()));												// 购汇付息金额
			gnwhdkbd.setEndbalan(null == fields[15] || "".equals(fields[15].replaceAll("\"", "").trim()) ? null
					: new BigDecimal(fields[15].replaceAll("\"", "").trim()));												// 期末余额
			gnwhdkbd.setRemark(fields[16].replaceAll("\"", "").trim());														// 备注
			gnwhdkbd.setHxxnwzbh(fields[17].replaceAll("\"", "").trim());													// 核心虚拟国内外汇贷款编号(不能用于申报)，核心传递的用来确定唯一一条记录，方便余额、变动记录的操作
			if (null != recdate && !"".equals(recdate))				
				gnwhdkbd.setRecdate(recdate);																				// 接收/创建时间
			gnwhdkbd.setDatasources("HX");	
						
			// 虚拟国内外汇贷款编号的第3位至14位为银行金融机构标识码
			String branchCode = hxXnBranchCode.substring(2, 14);
			// 根据金融机构标识码，获取国结机构号(采集机构)
			gnwhdkbd.setBank_Id(dao.getOrgNoByBranchCode(branchCode));
			Map dldclMap = this.getWzbh(gnwhdkbd, orgNo);
			if(dldclMap != null){
				gnwhdkbd.setDofoexlocode((String) dldclMap.get("rptNo")); 														// 国内外汇贷款编号通过签约信息取得
				gnwhdkbd.setYwbh(gnwhdkbd.getDofoexlocode()); 																	// 业务编号 跟外债编号同
			}
			gnwhdkbd.setChangeno(DclNoFactory.getChangeNo("gnwhdkbd", gnwhdkbd.getDofoexlocode()));															// 变动编号
			SerialNoFactory snf =  new SerialNoFactory();
			String txnNo = snf.getSerialNo(EbpConstants.DLDCLBSCID,16);
			gnwhdkbd.setNguid(txnNo);																// 业务流水号
			gnwhdkbd.setRwidh(gnwhdkbd.getNguid());
			gnwhdkbd.setSxbz("1");
			gnwhdkbd.setSfzx("Y");
			gnwhdkbd.setSfysb("N");
			gnwhdkbd.setHanddate(DataUtil.getTime(tranDate));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("解析核心传递的国内外汇贷款变动数据有误:"+e.getMessage());
			log.equals("解析核心传递的国内外汇贷款变动数据有误:"+e.getMessage());
			throw new Exception("解析核心传递的国内外汇贷款变动数据有误:"+e.getMessage());
		}
		return gnwhdkbd;
	}

	/**
	 * 根据外债类型和核心虚拟外债编号，获取对应的签约信息中的外债编号
	 * 
	 * @param gnwhdkbd
	 * @param orgNo
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> getWzbh(Gnwhdkbd bdxx, String orgNo) throws Exception {
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