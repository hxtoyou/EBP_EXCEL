package com.ebills.product.declare.parse;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ebills.commons.SerialNoFactory;
import com.ebills.declare.bussiness.DclNoFactory;
import com.ebills.product.declare.dao.BsptFileDAO;
import com.ebills.product.declare.domain.Fjmgrck;
import com.ebills.product.declare.domain.ParseBsptFile;
import com.ebills.product.declare.domain.ReceiveBsptFile;
import com.ebills.product.declare.util.CIConstants;
import com.ebills.product.declare.util.DataUtil;
import com.ebills.product.declare.util.GeneralCalc;
import com.ebills.product.declare.util.RecvDataChecker;
import com.ebills.util.EbillsLog;
import com.ebills.utils.EbpConstants;


public class ParseFjmgrck {
	private String className = this.getClass().getName();
	private EbillsLog log = new EbillsLog(className);
	private BufferedReader bufferRead = null;
	private RecvDataChecker recvDataChecker = new RecvDataChecker();
	BsptFileDAO dao = new BsptFileDAO();


	/**
	 * 解析从外围系统FTP上下载下来的非居民个人的文件
	 * @param file		需要解析的文件名称
	 * @param orgNo		采集机构
	 * @param dataType	数据文件
	 * @param fileType	文件类型
	 * @param tranDate	解析日期
	 * @return
	 */
	public String parse(File file, String orgNo, String dataType,
			String fileType, String tranDate) throws Exception {
		String tip = "";
		String Readin = "";
		int linenumber = 0;
		Fjmgrck fjmgrck = null;
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
						fjmgrck = this.parseLine(fields, orgNo, tranDate);
						if(fjmgrck!=null){
							tip += recvDataChecker.check_Fjmgrck(fjmgrck);
							
							Map fjmgrckMap = GeneralCalc.getValueMap(fjmgrck);
							String rptNo = GeneralCalc.objToString(fjmgrck.getExdebtcode());
							fjmgrckMap.put("debtypeXXX", dao.getSBDM(fjmgrck.getDebtype(), "zbzwlxejcode", "pazbzwlx", "zbzwlxej"));
							fjmgrckMap.put("creditortypeXXX", dao.getSBDM(fjmgrck.getCreditortype(), "zbjwztejcode", "pazbjwzttype", "zbjwztej"));
							fjmgrckMap.put("crehqcodeXXX", dao.getSBDM(fjmgrck.getCrehqcode(), "safeCode", "PACY", "cnName"));
							fjmgrckMap.put("txnNo",fjmgrck.getNguid());
							fjmgrckMap.put("sbkeyNo",rptNo);
							Map<String, Object> dldclbsc = new HashMap<String, Object>();
							dldclbsc.put("txnNo", fjmgrck.getNguid());
							dldclbsc.put("orgNo", fjmgrck.getBank_Id());
							dldclbsc.put("rptNo", rptNo);
							String dclKind = dao.getDldclMDName(dataType);
							dldclbsc.put("dclKind", dclKind);
							dldclbsc.put("dclDate", fjmgrck.getRecdate());
							dldclbsc.put("initDate", fjmgrck.getRecdate());
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
							dldclbsc.put("hxxnwzbh", fjmgrck.getHxxnwzbh());
							// 根据申报主键和数据来源获取对应记录
							List<Map<String, Object>> lst = dao.getDecalreByRptNo(rptNo, dclKind, EbpConstants.TABLE_INFO);
							if (null != lst && lst.size() > 0) {
								Map<String, Object> fjmgrckinfo = (Map<String, Object>) lst.get(0);
								if (null != fjmgrckinfo
										&& "0".equals(GeneralCalc.objToString(fjmgrckinfo.get("bscState")))) {
									Map<String,Object> fkeyMap = new HashMap<String, Object>();
									Map<String,Object> zkeyMap = new HashMap<String, Object>();
									fkeyMap.put("txnNo", GeneralCalc.objToString(fjmgrckinfo.get("txnNo")));
									fkeyMap.put("sbkeyNo", GeneralCalc.objToString(fjmgrckinfo.get("rptNo")));
									fjmgrckMap.put("txnNo", GeneralCalc.objToString(fjmgrckinfo.get("txnNo")));
									fjmgrckMap.put("sbkeyNo", GeneralCalc.objToString(fjmgrckinfo.get("rptNo")));
									zkeyMap.put("txnNo", GeneralCalc.objToString(fjmgrckinfo.get("txnNo")));
									zkeyMap.put("rptNo", GeneralCalc.objToString(fjmgrckinfo.get("rptNo")));
									zkeyMap.put("dclKind", GeneralCalc.objToString(fjmgrckinfo.get("dclKind")));
									dldclbsc.put("txnNo", GeneralCalc.objToString(fjmgrckinfo.get("txnNo")));
									dldclbsc.put("rptNo", GeneralCalc.objToString(fjmgrckinfo.get("rptNo")));
									dldclbsc.put("dclKind", GeneralCalc.objToString(fjmgrckinfo.get("dclKind")));
									dao.updateDecalreBuss(CIConstants.TAB_SB_FJMGRCK, fjmgrckMap, fkeyMap, EbpConstants.TABLE_INFO);
									dao.updateDecalre(dldclbsc, zkeyMap, EbpConstants.TABLE_INFO);
									log.error("记录存在，状态不为已发送申报，执行更新操作完毕！" + fjmgrck);
									// 金额、币种、来源 类型、序号、文件名称 文件类型、是否解析通过、执行机构
									// 解析时间、解析备注1
									dao.create(
											null,
											fjmgrck.getContractcurr(),
											fjmgrck.getDatasources(),
											dataType,
											String.valueOf(linenumber),
											fileName,
											fileType,
											"Y",
											orgNo,
											DataUtil.getDate(tranDate), "第"
													+ (linenumber)
													+ "行解析成功,记录存在，状态不为已发送申报，执行更新操作完毕！",
											"", "", "", fjmgrck.getYwbh());
								} else {
									dao.create(
											null,
											fjmgrck.getContractcurr(),
											fjmgrck.getDatasources(),
											dataType,
											String.valueOf(linenumber),
											fileName,
											fileType,
											"Y",
											orgNo,
											DataUtil.getDate(tranDate), "第"
													+ (linenumber)
													+ "行解析成功,记录存在，状态为已发送申报，不执行操作！", "",
											"", "", fjmgrck.getYwbh());
									log.error("记录存在，已申报，不执行操作！");
								}
							} else {
								// 在此处理采集的C或D类型的数据
								boolean isParsefile = false;
								if ("C".equals(fields[0]) || "D".equals(fields[0])) {
									// 是否存在A类型数据，若不存在便不接受此数据
									/*fjmgrck.setActiontype("A");
									List listA = fjmgrckManager
											.getDataByYWBHDATASources(fjmgrck);
									fjmgrck.setActiontype("C");
									List listC = fjmgrckManager
											.getDataByYWBHDATASources(fjmgrck);
									fjmgrck.setActiontype("D");
									List listD = fjmgrckManager
											.getDataByYWBHDATASources(fjmgrck);
									Fjmgrck temp = null;
									if (null != listA || null != listC
											|| null != listD) {
										// 将类型更新掉
										if (null != listA) {
											temp = (Fjmgrck) listA.get(0);
										} else if (null != listC) {
											temp = (Fjmgrck) listC.get(0);
										} else if (null != listD) {
											temp = (Fjmgrck) listD.get(0);
										}
										if ("Y".equals(temp.getSfysb())) {
											temp.setSfzx("N");
											fjmgrckManager.update(temp);
											fjmgrck.setActiontype(fields[0]);
											fjmgrckManager.create(fjmgrck);
											isParsefile = true;
										} else {
											dao.create(
													null,
													fjmgrck.getContractcurr(),
													fjmgrck.getDatasources(),
													dataType,
													String.valueOf(linenumber),
													fileName,
													fileType,
													"Y",
													orgNo,
													new java.sql.Date(System
															.currentTimeMillis()),
													"第" + (linenumber) + "接收到"
															+ fields[0]
															+ "的数据，在库中存在"
															+ temp.getActiontype()
															+ "类型数据未申报，不执行操作！", "",
													"", "", fjmgrck.getYwbh());
											log.error("接收到" + fields[0]
													+ "的数据，在库中存在"
													+ temp.getActiontype()
													+ "类型数据未申报，不执行操作！");
											isParsefile = false;
										}
									} else {
										dao
												.create(null,
														fjmgrck.getContractcurr(),
														fjmgrck.getDatasources(),
														dataType,
														String.valueOf(linenumber),
														fileName,
														fileType,
														"Y",
														orgNo,
														new java.sql.Date(
																System.currentTimeMillis()),
														"第"
																+ (linenumber)
																+ "接收到"
																+ fields[0]
																+ "的数据，在库中不存在A,C或D类型数据，此数据不执行操作！",
														"", "", "", fjmgrck
																.getYwbh());
										log.error("接收到" + fields[0]
												+ "的数据，在库中不存在A,C或D类型数据，此数据不执行操作！");
										isParsefile = false;
									}*/
								} else {
									dao.create(fjmgrckMap, CIConstants.TAB_SB_FJMGRCK);
									dao.create(dldclbsc, EbpConstants.DLDCLBSCID);
									isParsefile = true;
								}
								if (isParsefile) {
									parsefile = new ParseBsptFile();
									parsefile.setFileName(fileName);
									parsefile.setDataType(dataType);
									parsefile.setFileType(fileType);
									parsefile.setYwbh(fjmgrck.getYwbh());
									parsefile.setParseDate(DataUtil.getDate(tranDate));
									parsefile.setCursign(fjmgrck.getContractcurr());
									// parsefile.setAmount(new BigDecimal(fjmgrck.getAnninrate()));
									parsefile.setOrgNo(orgNo);
									parsefile.setDataSources(fjmgrck
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
					}
				} catch (Exception e) {
					log.error("读取文件" + fileName + "失败,在第" + (linenumber)
							+ "行解析不成功" + e);
					log.error(Readin);
					parsefile = new ParseBsptFile();
					parsefile.setFileName(fileName);
					parsefile.setDataType(dataType);
					parsefile.setFileType(fileType);
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
	 */
	public Fjmgrck parseLine(String[] fields, String orgNo, String tranDate)
			throws Exception {
		java.sql.Date recdate = null;
		Fjmgrck fjmgrck = new Fjmgrck();
		try {
			//数据接收或创建时间
			recdate = GeneralCalc.strToSQLDate(tranDate);
		} catch (Exception e) {
			System.out.println("核心传递的日期格式有误!"+e.getMessage());
			log.equals("核心传递的日期格式有误!"+e.getMessage());
		}
		try {
			fjmgrck.setActiontype("A");
			fjmgrck.setActiondesc("");
			String hxXnBranchCode = fields[0].replaceAll("\"", "").trim();							// 核心传递的虚拟金融机构标识码(该虚拟编号中存在金融机构标识码，方便后面根据金融机构标识码，获取国结机构号(采集机构))
			fjmgrck.setDebtorcode(fields[1].replaceAll("\"", "").trim());							// 债务人代码
			fjmgrck.setDebtype(fields[2].replaceAll("\"", "").trim());								// 债务类型
			fjmgrck.setContractcurr(fields[3].replaceAll("\"", "").trim());							// 签约币种
			fjmgrck.setFloatrate(fields[4].replaceAll("\"", "").trim());							// 是否浮动利率
			if (null != fields[5] && !"".equals(fields[5].replaceAll("\"", "").trim()))
				// 根据城商联盟徐宇20130110日说，需要将核心传递的年化利率值除以100
				fjmgrck.setAnninrate(GeneralCalc.roundFormat(Double.parseDouble(fields[5].replaceAll("\"", "").trim())/100,8));// 年化利率值
			fjmgrck.setCreditorcode(fields[6].replaceAll("\"", "").trim());							// 债权人代码
			fjmgrck.setCreditorname(fields[7].replaceAll("\"", "").trim());							// 债权人中文名称
			fjmgrck.setCreditornamen(fields[8].replaceAll("\"", "").trim());						// 债权人英文名称
			fjmgrck.setCreditortype(fields[9].replaceAll("\"", "").trim());							// 债权人类型代码
			fjmgrck.setCrehqcode(fields[10].replaceAll("\"", "").trim());							// 债权人国家（地区）代码
			fjmgrck.setSpapfeboindex(fields[11].replaceAll("\"", "").trim());						// 是否经外汇局特批不需占用指标
			fjmgrck.setRemark(fields[12].replaceAll("\"", "").trim());								// 备注
			/*国结系统应根据标橙色的四个栏位"签约币种，债权人国别（地区）代码，是否经外汇局特批不需占用指标"以及外债编号中的12位金融机构标识码来 唯一确认出一个外债编号，判断签约信息记录中的余额种类是否在国结系统历史数据中存在：
			由于城商联盟数据字典中规定非居民个人存款的余额信息需要根据上面的四个字段来为唯一标识一笔签约信息
			因此现在将这个字段按照：外债编号+签约币种+国别+是否经外汇局特批不需占用指标  来拼接成一个虚拟的外债编号*/
			fjmgrck.setHxxnwzbh(hxXnBranchCode.substring(2, 14)+fjmgrck.getContractcurr()+fjmgrck.getCrehqcode()+fjmgrck.getSpapfeboindex());		// 核心虚拟外债编号(不能用于申报)，核心传递的用来确定唯一一条记录，方便余额、变动记录的操作
			/*********************************************/
			/*判断如果已存在记录则不需要插入记录 LT 2014-08-26 根据“签约币种”、“债权人国家（地区）代码”、“是否经外汇局特批不需占用指标”*/
			boolean isFjmgrck = dao.isFjmgrck(fjmgrck.getContractcurr(), fjmgrck.getCrehqcode(),fjmgrck.getSpapfeboindex());
			if(isFjmgrck==true){
				return null;
			}
			/*********************************************/			
			if (null != recdate && !"".equals(recdate))
				fjmgrck.setRecdate(recdate); 														// 接收/创建时间
			fjmgrck.setDatasources("HX"); 															// 数据来源(山东城商联盟)
			// 根据山东联盟要求，非居民个人的签约信息会每次都传过来，因此需要做判断，如果存在此签约信息，则不再将此签约信息插入数据库中
			//List list = dao.getDateByHxxnwzbh(fjmgrck.getHxxnwzbh(), CIConstants.TAB_SB_FJMGRCK);
			List<Map<String, Object>> list = dao.getDecalreByHxxnwzbh(fjmgrck.getHxxnwzbh(), 
					fjmgrck.getDatasources(), orgNo, EbpConstants.TABLE_INFO);
			if(list!=null && list.size()>0){
				return null;
			}			
			// 核心传递的虚拟金融机构标识码的第3位至14位为银行金融机构标识码
			String branchCode = hxXnBranchCode.substring(2, 14);
			// 根据金融机构标识码，获取国结机构号(采集机构)
			fjmgrck.setBank_Id(dao.getOrgNoByBranchCode(branchCode));
			fjmgrck.setExdebtcode(DclNoFactory.getEXDEBTCODE("fjmgrck",fjmgrck.getBank_Id(),null, "")); 						// 外债编号编号(核心不传递外债编号，国结自己生成)
			fjmgrck.setYwbh(fjmgrck.getExdebtcode()); 												// 业务编号，资本项下为外债编号
			SerialNoFactory snf =  new SerialNoFactory();
			String txnNo = snf.getSerialNo(EbpConstants.DLDCLBSCID,16);
			fjmgrck.setNguid(txnNo); 										// 国结生成的流水号
			fjmgrck.setRwidh(fjmgrck.getNguid());
			fjmgrck.setSxbz("1");
			fjmgrck.setSfzx("Y");
			fjmgrck.setSfysb("N");
			fjmgrck.setHanddate(DataUtil.getTime(tranDate));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("解析核心传递的非居民个人数据有误:"+e.getMessage());
			log.equals("解析核心传递的非居民个人数据有误:"+e.getMessage());
			throw new Exception("解析核心传递的非居民个人数据有误:"+e.getMessage());
		}
		return fjmgrck;
	}
}
