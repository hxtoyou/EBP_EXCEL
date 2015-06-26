package com.ebills.product.declare.parse;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ebills.commons.SerialNoFactory;
import com.ebills.declare.bussiness.DclNoFactory;
import com.ebills.product.declare.dao.BsptFileDAO;
import com.ebills.product.declare.domain.ParseBsptFile;
import com.ebills.product.declare.domain.ReceiveBsptFile;
import com.ebills.product.declare.domain.Yexx;
import com.ebills.product.declare.util.CIConstants;
import com.ebills.product.declare.util.DataUtil;
import com.ebills.product.declare.util.GeneralCalc;
import com.ebills.product.declare.util.RecvDataChecker;
import com.ebills.util.EbillsLog;
import com.ebills.utils.EbpConstants;


public class ParseWzyexx {
	private String className = this.getClass().getName();
	private EbillsLog log = new EbillsLog(className);
	private BufferedReader bufferRead = null;
	private RecvDataChecker recvDataChecker = new RecvDataChecker();
	BsptFileDAO dao = new BsptFileDAO();


	/**
	 * 解析从外围系统FTP上下载下来的外债余额的文件
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
		Yexx yexx = null;
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
						yexx = parseLine(fields, orgNo, dataType, tranDate);
						tip += recvDataChecker.check_Yexx(yexx);
						if (null == yexx)
							yexx = new Yexx();
						
						Map yexxMap = GeneralCalc.getValueMap(yexx);
						String rptNo = GeneralCalc.objToString(yexx.getExdebtcode()) + GeneralCalc.objToString(yexx.getChangeno());
						yexxMap.put("txnNo",yexx.getNguid());
						yexxMap.put("sbkeyNo",rptNo);
						Map<String, Object> dldclbsc = new HashMap<String, Object>();
						dldclbsc.put("txnNo", yexx.getNguid());
						dldclbsc.put("orgNo", yexx.getBank_Id());
						dldclbsc.put("rptNo", rptNo);
						String dclKind = dao.getDldclMDName(dataType.split("_")[0]);
						dldclbsc.put("dclKind", dclKind);
						dldclbsc.put("dclDate", yexx.getRecdate());
						dldclbsc.put("initDate", yexx.getRecdate());
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
						dldclbsc.put("hxxnwzbh", yexx.getHxxnwzbh()+"_HZ");
						
						// 根据申报主键获取对应记录
						List<Map<String, Object>> lst = dao.getDecalreByRptNo(rptNo, dclKind, EbpConstants.TABLE_INFO);
						if (null != lst && lst.size() > 0) {
							Map<String, Object> yexxinfo = (Map<String, Object>) lst.get(0);
							if (null != yexxinfo
									&& !"1".equals(GeneralCalc.objToString(yexxinfo.get("bscState")))) {
								Map<String,Object> fkeyMap = new HashMap<String, Object>();
								Map<String,Object> zkeyMap = new HashMap<String, Object>();
								fkeyMap.put("txnNo", GeneralCalc.objToString(yexxinfo.get("txnNo")));
								fkeyMap.put("sbkeyNo", GeneralCalc.objToString(yexxinfo.get("rptNo")));
								yexxMap.put("txnNo", GeneralCalc.objToString(yexxinfo.get("txnNo")));
								yexxMap.put("sbkeyNo", GeneralCalc.objToString(yexxinfo.get("rptNo")));
								zkeyMap.put("txnNo", GeneralCalc.objToString(yexxinfo.get("txnNo")));
								zkeyMap.put("rptNo", GeneralCalc.objToString(yexxinfo.get("rptNo")));
								zkeyMap.put("dclKind", GeneralCalc.objToString(yexxinfo.get("dclKind")));
								dldclbsc.put("txnNo", GeneralCalc.objToString(yexxinfo.get("txnNo")));
								dldclbsc.put("rptNo", GeneralCalc.objToString(yexxinfo.get("rptNo")));
								dldclbsc.put("dclKind", GeneralCalc.objToString(yexxinfo.get("dclKind")));
								dao.updateDecalreBuss(CIConstants.TAB_SB_YEXX, yexxMap, fkeyMap, EbpConstants.TABLE_INFO);
								dao.updateDecalre(dldclbsc, zkeyMap, EbpConstants.TABLE_INFO);
								log.error("记录存在，状态不为已发送申报，执行更新操作完毕！" + yexx);
								dao.create(
										yexx.getAccoamount(),
										null,
										yexx.getDatasources(),
										dataType,
										String.valueOf(linenumber),
										fileName,
										fileType,
										"Y",
										orgNo,
										DataUtil.getDate(tranDate), 
										"第" + (linenumber)
												+ "行解析成功,记录存在，状态不为已发送申报，执行更新操作完毕！",
										"", "", "", yexx.getYwbh());
							} else {
								dao.create(
										yexx.getAccoamount(),
										null,
										yexx.getDatasources(),
										dataType,
										String.valueOf(linenumber),
										fileName,
										fileType,
										"Y",
										orgNo,
										DataUtil.getDate(tranDate), 
										"第" + (linenumber)
												+ "行解析成功,记录存在，状态为已发送申报，不执行操作！", "",
										"", "", yexx.getYwbh());
								log.error("记录存在，状态为已发送申报，不执行操作！");
							}
						} else {
							// 在此处理采集的C或D类型的数据
							boolean isParsefile = false;
							if ("C".equals(fields[0]) || "D".equals(fields[0])) {
								// 是否存在A类型数据，若不存在便不接受此数据
								/*yexx.setActiontype("A");
								List listA = yexxManager
										.getDataByYWBHDATASources(yexx);
								yexx.setActiontype("C");
								List listC = yexxManager
										.getDataByYWBHDATASources(yexx);
								yexx.setActiontype("D");
								List listD = yexxManager
										.getDataByYWBHDATASources(yexx);
								Yexx temp = null;
								if (null != listA || null != listC
										|| null != listD) {
									// 将类型更新掉
									if (null != listA) {
										temp = (Yexx) listA.get(0);
									} else if (null != listC) {
										temp = (Yexx) listC.get(0);
									} else if (null != listD) {
										temp = (Yexx) listD.get(0);
									}
									if ("Y".equals(temp.getSfysb())) {
										temp.setSfzx("N");
										yexxManager.update(temp);
										yexx.setActiontype(fields[0]);
										yexxManager.create(yexx);
										isParsefile = true;
									} else {
										dao.create(
												yexx.getAccoamount(),
												null,
												yexx.getDatasources(),
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
												"", "", yexx.getYwbh());
										log.error("接收到" + fields[0]
												+ "的数据，在库中存在"
												+ temp.getActiontype()
												+ "类型数据未申报，不执行操作！");
										isParsefile = false;
									}
								} else {
									dao
											.create(yexx.getAccoamount(),
													null,
													yexx.getDatasources(),
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
													"", "", "", yexx.getYwbh());
									log.error("接收到" + fields[0]
											+ "的数据，在库中不存在A,C或D类型数据，此数据不执行操作！");
									isParsefile = false;
								}*/
							} else {
								dao.create(yexxMap, CIConstants.TAB_SB_YEXX);
								dao.create(dldclbsc, EbpConstants.DLDCLBSCID);
								isParsefile = true;
							}
							if (isParsefile) {
								parsefile = new ParseBsptFile();
								parsefile.setFileName(fileName);
								parsefile.setDataType(dataType);
								parsefile.setFileType(fileType);
								parsefile.setYwbh(yexx.getYwbh());
								parsefile.setParseDate(DataUtil.getDate(tranDate));
								parsefile.setAmount(yexx.getAccoamount());
								parsefile.setOrgNo(orgNo);
								parsefile.setDataSources(yexx.getDatasources());
								parsefile.setIsParse("Y");
								if (null == yexx.getExdebtcode()
										|| "".equals(yexx.getExdebtcode())) {
									parsefile.setRemark1("第" + (linenumber)
											+ "行解析成功,但关联签约信息失败！请核查！");
								} else {
									parsefile.setRemark1("第" + (linenumber)
											+ "行解析成功");
								}
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
	 * 
	 */
	public Yexx parseLine(String[] fields, String orgNo, String dataType, String tranDate)
			throws Exception {
		java.util.Date tmpDate = null;
		java.sql.Date chdate = null;
		java.sql.Date recdate = null;
		Yexx yexx = new Yexx();
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			// 变动日期
			tmpDate = format.parse(fields[4] == null
					|| "".equals(fields[4].replaceAll("\"", "").trim()) ? format.format(new Date())
					: fields[4].replaceAll("\"", "").trim());
			chdate = new java.sql.Date(tmpDate.getTime());
			// 数据接收或创建时间
			recdate = GeneralCalc.strToSQLDate(tranDate);
		} catch (Exception e) {
			System.out.println("核心传递的日期格式有误!"+e.getMessage());
			log.equals("核心传递的日期格式有误!"+e.getMessage());
		}
		try {
			yexx.setActiontype("A"); 																// 操作类型
			yexx.setActiondesc(""); 																// 删除原因
			String hxXnBranchCode = fields[0].replaceAll("\"", "").trim();							// 核心传递的虚拟金融机构标识码(该虚拟编号中存在金融机构标识码，方便后面根据金融机构标识码，获取国结机构号(采集机构))
			yexx.setChangeno(fields[1].replaceAll("\"", "").trim()); 								// 变动编号
			yexx.setBuscode(fields[2].replaceAll("\"", "").trim()); 								// 银行帐号
			if (null != fields[3].trim() && !"".equals(fields[3].replaceAll("\"", "").trim()))
				yexx.setAccoamount(new BigDecimal(fields[3].replaceAll("\"", "").trim())); 			// 外债余额
			if (null != chdate && !"".equals(chdate))
				yexx.setChdate(chdate); 															// 变动日期
			yexx.setRemark(fields[5].replaceAll("\"", "").trim()); 									// 备注
			yexx.setRecdate(recdate); 																// 接收/创建时间
			yexx.setDatasources("HX");																// 数据来源(山东城商联盟)
			
			// 非居民机构
			if(dataType!=null && "CFAAS_JG".equals(dataType)){
				// 这里非居民机构存款的核心虚拟外债编号取外部账号,用于关联对应的余额信息
				yexx.setHxxnwzbh(fields[6].replaceAll("\"", "").trim()); 							// 核心虚拟外债编号(不能用于申报)，核心传递的用来确定唯一一条记录，方便余额、变动记录的操作				
			// 非居民个人
			}else if(dataType!=null && "CFAAS_GR".equals(dataType)){
				// 签约币种
				String signCurr = fields[6].replaceAll("\"", "").trim();
				// 债权人国别（地区）代码
				String crehqcode = fields[7].replaceAll("\"", "").trim();
				// 是否经外汇局特批不需占用指标
				String spapfeboIndex =  fields[8].replaceAll("\"", "").trim();
				/*国结系统应根据标橙色的四个栏位"签约币种，债权人国别（地区）代码，是否经外汇局特批不需占用指标"以及外债编号中的12位金融机构标识码来 唯一确认出一个外债编号，判断签约信息记录中的余额种类是否在国结系统历史数据中存在：
				由于城商联盟数据字典中规定非居民个人存款的余额信息需要根据上面的四个字段来为唯一标识一笔签约信息
				因此现在将这个字段按照：外债编号+签约币种+国别+是否经外汇局特批不需占用指标  来拼接成一个虚拟的外债编号*/
				//yexx.setHxxnwzbh(hxXnBranchCode.substring(2, 14)+signCurr+crehqcode+spapfeboIndex);				// 核心虚拟外债编号(不能用于申报)，核心传递的用来确定唯一一条记录，方便余额、变动记录的操作
				//以上为烟台屏蔽，固定用国际业务部金融标识码
				yexx.setHxxnwzbh("370600007701"+signCurr+crehqcode+spapfeboIndex);
				//外债类型为非居民个人存款的时，银行业务编码应该为N/A
				yexx.setBuscode("N/A");
			}			
			
			// 核心传递的虚拟金融机构标识码的第3位至14位为银行金融机构标识码
			String branchCode = hxXnBranchCode.substring(2, 14);
			// 根据金融机构标识码，获取国结机构号(采集机构)
			yexx.setBank_Id(dao.getOrgNoByBranchCode(branchCode));
			Map dldclMap = this.getWzbh(yexx, orgNo);
			if(dldclMap != null){
				yexx.setExdebtcode((String) dldclMap.get("rptNo")); 											// 外债编号通过签约信息取得
				yexx.setYwbh(yexx.getExdebtcode()); 													// 业务编号 跟外债编号同
				yexx.setWztype((String) dldclMap.get("dclKind"));
			}
			// 如果外债类型为非居民个人存款的时，银行业务编码应该为N/A(这里代码可以屏蔽也可以不屏蔽)
//			if (yexx.getWztype() != null && "fjmgrck".equals(yexx.getWztype()))
//				yexx.setBuscode("N/A");
			yexx.setChangeno(DclNoFactory.getChangeNo("yexx", yexx.getExdebtcode()));
			SerialNoFactory snf =  new SerialNoFactory();
			String txnNo = snf.getSerialNo(EbpConstants.DLDCLBSCID,16);
			yexx.setNguid(txnNo);
			yexx.setRwidh(yexx.getNguid());
			yexx.setSxbz("1");
			yexx.setSfzx("Y");
			yexx.setSfysb("N");
			yexx.setHanddate(DataUtil.getTime(tranDate));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("解析核心传递的外债余额数据有误:"+e.getMessage());
			log.equals("解析核心传递的外债余额数据有误:"+e.getMessage());
			throw new Exception("解析核心传递的外债余额数据有误:"+e.getMessage());
		}
		return yexx;
	}

	/**
	 * 根据外债类型和核心虚拟外债编号，获取对应的签约信息中的外债编号
	 * 
	 * @param yexx
	 * @param orgNo
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> getWzbh(Yexx yexx, String orgNo) throws Exception {
		Map<String, Object> retMap = null;
		try {
			// 根据外债类型和核心虚拟外债编号，获取对应的签约信息中的外债编号
			List<Map<String, Object>> qyxxQuerrylist = dao.getDecalreByHxxnwzbh(yexx.getHxxnwzbh(),
							yexx.getDatasources(), orgNo, EbpConstants.TABLE_INFO);
			if (qyxxQuerrylist != null && qyxxQuerrylist.size() > 0) {
				for (Map<String, Object> map : qyxxQuerrylist) {
					retMap = map;
				}
			} else {
				log.debug("无法从业务信息：" + yexx.getHxxnwzbh() + "|系统来源:"
						+ yexx.getDatasources() + "|操作类型" + "A" + "|执行机构："
						+ orgNo + "取得签约信息");
			}
		} catch (Exception e) {
			throw new Exception("无法从业务信息：" + yexx.getHxxnwzbh()
					+ "|系统来源:" + yexx.getDatasources() + "|操作类型" + "A"
					+ "|执行机构：" + orgNo + "取得签约信息");
		}
		return retMap;
	}
}