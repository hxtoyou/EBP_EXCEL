package com.ebills.product.declare.parse;


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
import com.ebills.product.declare.dao.BsptFileDAO;
import com.ebills.product.declare.domain.ParseBsptFile;
import com.ebills.product.declare.domain.ReceiveBsptFile;
import com.ebills.product.declare.domain.Zhszy;
import com.ebills.product.declare.util.CIConstants;
import com.ebills.product.declare.util.DataUtil;
import com.ebills.product.declare.util.GeneralCalc;
import com.ebills.product.declare.util.RecvDataChecker;
import com.ebills.util.EbillsLog;
import com.ebills.utils.EbpConstants;


public class ParseZhszy {
	private String className = this.getClass().getName();
	private EbillsLog log = new EbillsLog(className);
	private BufferedReader bufferRead = null;
	private RecvDataChecker recvDataChecker = new RecvDataChecker();
	BsptFileDAO dao = new BsptFileDAO();
	/**
	 * 解析从外围系统FTP上下载下来账户 的文件
	 * @param file		需要解析的文件名称
	 * @param orgNo		采集机构
	 * @param dataType	数据文件
	 * @param fileType	文件类型
	 * @param tranDate	解析日期
	 * @return
	 * 
	 */
	public String parse(File file, String orgNo, String dataType, 
			String fileType, String tranDate) throws Exception {
		String tip = "";
		String Readin = "";
		int linenumber = 0;
		Zhszy zhszy = null;
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
						zhszy = this.parseLine(fields, orgNo, tranDate);
						tip += recvDataChecker.check_Zhszy(zhszy);
						
						Map zhszyMap = GeneralCalc.getValueMap(zhszy);
						String rptNo = GeneralCalc.objToString(zhszy.getBranch_Code()) + GeneralCalc.objToString(zhszy.getAccountno()) 
								+ GeneralCalc.objToString(zhszy.getCurrency_Code()+ DataUtil.dateToString(zhszy.getDeal_Date(),"yyyyMMdd"));
						zhszyMap.put("txnNo",zhszy.getNguid());
						zhszyMap.put("sbkeyNo",rptNo);
						Map<String, Object> dldclbsc = new HashMap<String, Object>();
						dldclbsc.put("txnNo", zhszy.getNguid());
						dldclbsc.put("orgNo", zhszy.getBank_Id());
						dldclbsc.put("rptNo", rptNo);
						String dclKind = dao.getDldclMDName(dataType);
						dldclbsc.put("dclKind", dclKind);
						dldclbsc.put("dclDate", zhszy.getRecdate());
						dldclbsc.put("initDate", zhszy.getRecdate());
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
						
						// 根据申报主键获取对应记录
						List<Map<String, Object>> lst = dao.getDecalreByRptNo(rptNo, dclKind, EbpConstants.TABLE_INFO);
						if (null != lst && lst.size() > 0) {
							Map<String, Object> zhszyinfo = (Map<String, Object>) lst.get(0);
							if (null != zhszyinfo
									&& !"1".equals(GeneralCalc.objToString(zhszyinfo.get("bscState")))) {
								Map<String,Object> fkeyMap = new HashMap<String, Object>();
								Map<String,Object> zkeyMap = new HashMap<String, Object>();
								fkeyMap.put("txnNo", GeneralCalc.objToString(zhszyinfo.get("txnNo")));
								fkeyMap.put("sbkeyNo", GeneralCalc.objToString(zhszyinfo.get("rptNo")));
								zhszyMap.put("txnNo", GeneralCalc.objToString(zhszyinfo.get("txnNo")));
								zhszyMap.put("sbkeyNo", GeneralCalc.objToString(zhszyinfo.get("rptNo")));
								zkeyMap.put("txnNo", GeneralCalc.objToString(zhszyinfo.get("txnNo")));
								zkeyMap.put("rptNo", GeneralCalc.objToString(zhszyinfo.get("rptNo")));
								zkeyMap.put("dclKind", GeneralCalc.objToString(zhszyinfo.get("dclKind")));
								dldclbsc.put("txnNo", GeneralCalc.objToString(zhszyinfo.get("txnNo")));
								dldclbsc.put("rptNo", GeneralCalc.objToString(zhszyinfo.get("rptNo")));
								dldclbsc.put("dclKind", GeneralCalc.objToString(zhszyinfo.get("dclKind")));
								dao.updateDecalreBuss(CIConstants.TAB_SB_ZHSZY, zhszyMap, fkeyMap, EbpConstants.TABLE_INFO);
								dao.updateDecalre(dldclbsc, zkeyMap, EbpConstants.TABLE_INFO);
								log.error("记录存在，状态不为已发送申报，执行更新操作完毕！" + zhszyinfo);
								// 金额、币种、来源 类型、序号、文件名称 文件类型、是否解析通过、执行机构
								// 解析时间、解析备注1
								dao.create(
										zhszy.getBalance(),
										zhszy.getCurrency_Code(),
										zhszy.getDatasources(),
										dataType,
										String.valueOf(linenumber),
										fileName,
										fileType,
										"Y",
										orgNo,
										DataUtil.getDate(tranDate), "第"
												+ (linenumber)
												+ "行解析成功,记录存在，状态不为已发送申报，执行更新操作完毕！",
										"", "", "", zhszy.getNguid());
							} else {
								zhszy.setNguid(GeneralCalc.objToString(zhszyinfo.get("txnNo")));
								dao.create(
										zhszy.getBalance(),
										zhszy.getCurrency_Code(),
										zhszy.getDatasources(),
										dataType,
										String.valueOf(linenumber),
										fileName,
										fileType,
										"Y",
										orgNo,
										DataUtil.getDate(tranDate), "第"
												+ (linenumber)
												+ "行解析成功,记录存在，状态为已发送申报，不执行操作！", "",
										"", "", zhszy.getNguid());
								log.error("记录存在，已申报，不执行操作！");
							}
						} else {
							dao.create(zhszyMap, CIConstants.TAB_SB_ZHSZY);
							dao.create(dldclbsc, EbpConstants.DLDCLBSCID);
							parsefile = new ParseBsptFile();
							parsefile.setFileName(fileName);
							parsefile.setDataType(dataType);
							parsefile.setFileType(fileType);
							parsefile.setYwbh(zhszy.getYwbh());
							parsefile.setParseDate(DataUtil.getDate(tranDate));
							parsefile.setCursign(zhszy.getCurrency_Code());
							parsefile.setAmount(zhszy.getBalance());
							parsefile.setOrgNo(orgNo);
							parsefile.setDataSources(zhszy.getDatasources());
							parsefile.setIsParse("Y");
							parsefile.setRemark1("第" + (linenumber) + "行解析成功");
							parsefile.setFileInfoNo(String.valueOf(linenumber));
							dao.create(parsefile);
						}
						log.info("共成功读取了:: " + linenumber + " 行");
					}
				} catch (Exception e) {
					log.error("读取文件" + fileName + "失败,在第" + (linenumber)
							+ "行解析不成功");
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
	 * 解析文件 由于山东联盟传递的数据中前面的ActionType和ActionDesc都不传递，所以默认为新增，这样数组就又原来的长度缩短了2位
	 * 
	 * 
	 */
	public Zhszy parseLine(String[] fields, String orgNo, String tranDate)
			throws Exception {
		java.util.Date tmpDate = null;
		java.sql.Date bussDate = null;
		java.sql.Date recdate = null;
		Zhszy zhszy = new Zhszy();
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			//业务发生日期
			tmpDate = format.parse(fields[2].replaceAll("\"", "").trim());
			bussDate = new java.sql.Date(tmpDate.getTime());
			//数据接收或创建时间
			recdate = GeneralCalc.strToSQLDate(tranDate);
		} catch (Exception e) {
			e.printStackTrace();
			log.equals("日期格式化错误: " + e);
		}
		try {
			zhszy.setActiontype("A");										// 操作类型	
			zhszy.setActiondesc("");										// 修改、删除原因
			zhszy.setBranch_Code(fields[0].replaceAll("\"", "").trim());	// 金融机构标识码
			zhszy.setAccountno(fields[1].replaceAll("\"", "").trim());		// 账号
			if (null != bussDate && !"".equals(bussDate))
				zhszy.setDeal_Date(bussDate);								// 业务发生日期
			zhszy.setCurrency_Code(fields[3].replaceAll("\"", "").trim());	// 币种
			if (null != fields[4].trim() && !"".equals(fields[4].trim()))
				zhszy.setCredit(new BigDecimal(fields[4].trim()));			// 当日贷方发生额
			if (null != fields[5].trim() && !"".equals(fields[5].trim()))
				zhszy.setDebit(new BigDecimal(fields[5]));					// 当日借方发生额
			if (null != fields[6].trim() && !"".equals(fields[6].trim()))
				zhszy.setBalance(new BigDecimal(fields[6].trim()));			// 账户余额
			zhszy.setRemark(fields[7].replaceAll("\"", "").trim());			// 备注
			if (null != recdate && !"".equals(recdate))				
				zhszy.setRecdate(recdate);									//接收/创建时间
			zhszy.setDatasources("HX");										//数据来源(山东城商联盟)
			
			//根据金融机构标识码，获取国结机构号(采集机构)
			zhszy.setBank_Id(dao.getOrgNoByBranchCode(zhszy.getBranch_Code()));
			SerialNoFactory snf =  new SerialNoFactory();
			String txnNo = snf.getSerialNo(EbpConstants.DLDCLBSCID,16);
			zhszy.setNguid(txnNo);					//国结生成的流水号
			zhszy.setRwidh(zhszy.getNguid());
			zhszy.setYwbh(zhszy.getNguid());
			zhszy.setSxbz("1");
			zhszy.setSfzx("Y");
			zhszy.setSfysb("N");
			zhszy.setHanddate(DataUtil.getTime(tranDate));
		} catch (Exception e) {
			e.printStackTrace();
			log.equals("文件解析赋值错误: " + e);
			throw new Exception("文件解析赋值错误:" + e);
		}
		return zhszy;
	}
}