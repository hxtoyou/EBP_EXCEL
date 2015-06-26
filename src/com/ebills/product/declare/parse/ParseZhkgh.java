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
import com.ebills.product.declare.domain.Zhkgh;
import com.ebills.product.declare.util.CIConstants;
import com.ebills.product.declare.util.DataUtil;
import com.ebills.product.declare.util.GeneralCalc;
import com.ebills.product.declare.util.RecvDataChecker;
import com.ebills.util.EbillsLog;
import com.ebills.utils.EbpConstants;


public class ParseZhkgh {
	private String className = this.getClass().getName();
	private EbillsLog log = new EbillsLog(className);
	private BufferedReader bufferRead = null;
	private RecvDataChecker recvDataChecker = new RecvDataChecker();
	BsptFileDAO dao = new BsptFileDAO();
	/**
	 * 解析从外围系统FTP上下载下来账户开关户的文件
	 * @param file		需要解析的文件名称
	 * @param orgNo		采集机构
	 * @param dataType	数据文件
	 * @param fileType	文件类型
	 * @param tranDate	解析日期
	 * @return
	 * @throws Exception 
	 * 
	 */
	public String parse(File file, String orgNo, String dataType, 
			String fileType, String tranDate) throws Exception {
		String Readin = "";
		int linenumber = 0;
		Zhkgh zhkgh = null;
		String fileName = file.getName();
		ParseBsptFile parsefile = null;
		ReceiveBsptFile receiveBsptFile = new ReceiveBsptFile();
		receiveBsptFile.setFileName(fileName);
		String tip = "";
		try {
			bufferRead = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "GB2312"));
			while ((Readin = bufferRead.readLine()) != null) {
				try {
					if (null != Readin && !"".equals(Readin.trim())) {
						linenumber++;
						String[] fields = Readin.split(",");
						zhkgh = this.parseLine(fields, orgNo, tranDate);
						tip += recvDataChecker.check_Zhkgh(zhkgh);
						
						Map zhkghMap = GeneralCalc.getValueMap(zhkgh);
						String rptNo = GeneralCalc.objToString(zhkgh.getBranch_Code()) + GeneralCalc.objToString(zhkgh.getAccountno()) 
								+ GeneralCalc.objToString(zhkgh.getCurrency_Code());
						zhkghMap.put("accounttypeXXX", dao.getSBDM(zhkgh.getAccount_Type(), "zhxzcode", "pazhxz", "zhxzname"));
						zhkghMap.put("txnNo",zhkgh.getNguid());
						zhkghMap.put("sbkeyNo",rptNo);
						Map<String, Object> dldclbsc = new HashMap<String, Object>();
						dldclbsc.put("txnNo", zhkgh.getNguid());
						dldclbsc.put("orgNo", zhkgh.getBank_Id());
						dldclbsc.put("rptNo", rptNo);
						String dclKind = dao.getDldclMDName(dataType);
						dldclbsc.put("dclKind", dclKind);
						dldclbsc.put("dclDate", zhkgh.getRecdate());
						dldclbsc.put("initDate", zhkgh.getRecdate());
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
							Map<String, Object> zhkghinfo = (Map<String, Object>) lst.get(0);
							if (null != zhkghinfo
									&& !"1".equals(GeneralCalc.objToString(zhkghinfo.get("bscState")))) {
								Map<String,Object> fkeyMap = new HashMap<String, Object>();
								Map<String,Object> zkeyMap = new HashMap<String, Object>();
								fkeyMap.put("txnNo", GeneralCalc.objToString(zhkghinfo.get("txnNo")));
								fkeyMap.put("sbkeyNo", GeneralCalc.objToString(zhkghinfo.get("rptNo")));
								zhkghMap.put("txnNo", GeneralCalc.objToString(zhkghinfo.get("txnNo")));
								zhkghMap.put("sbkeyNo", GeneralCalc.objToString(zhkghinfo.get("rptNo")));
								zkeyMap.put("txnNo", GeneralCalc.objToString(zhkghinfo.get("txnNo")));
								zkeyMap.put("rptNo", GeneralCalc.objToString(zhkghinfo.get("rptNo")));
								zkeyMap.put("dclKind", GeneralCalc.objToString(zhkghinfo.get("dclKind")));
								dldclbsc.put("txnNo", GeneralCalc.objToString(zhkghinfo.get("txnNo")));
								dldclbsc.put("rptNo", GeneralCalc.objToString(zhkghinfo.get("rptNo")));
								dldclbsc.put("dclKind", GeneralCalc.objToString(zhkghinfo.get("dclKind")));
								dao.updateDecalreBuss(CIConstants.TAB_SB_ZHKGH, zhkghMap, fkeyMap, EbpConstants.TABLE_INFO);
								dao.updateDecalre(dldclbsc, zkeyMap, EbpConstants.TABLE_INFO);
								log.error("记录存在，状态不为已发送申报，执行更新操作完毕！" + zhkghinfo);
								// 金额、币种、来源 类型、序号、文件名称 文件类型、是否解析通过、执行机构
								// 解析时间、解析备注1
								dao.create(
										zhkgh.getAccount_Limit(),
										zhkgh.getCurrency_Code(),
										zhkgh.getDatasources(),
										dataType,
										String.valueOf(linenumber),
										fileName,
										fileType,
										"Y",
										orgNo,
										DataUtil.getDate(tranDate), 
										"第"+ (linenumber)
											+ "行解析成功,记录存在，状态不为已发送申报，执行更新操作完毕！",
										"", "", "", zhkgh.getNguid());
							} else {
								zhkgh.setNguid(GeneralCalc.objToString(zhkghinfo.get("txnNo")));
								dao.create(
										zhkgh.getAccount_Limit(),
										zhkgh.getCurrency_Code(),
										zhkgh.getDatasources(),
										dataType,
										String.valueOf(linenumber),
										fileName,
										fileType,
										"Y",
										orgNo,
										DataUtil.getDate(tranDate), 
										"第"+ (linenumber)
											+ "行解析成功,记录存在，状态为已发送申报，不执行操作！", "",
										"", "", zhkgh.getNguid());
								log.error("记录存在，已申报，不执行操作！");
							}
						} else {
							dao.create(zhkghMap, CIConstants.TAB_SB_ZHKGH);
							dao.create(dldclbsc, EbpConstants.DLDCLBSCID);
							parsefile = new ParseBsptFile();
							parsefile.setFileName(fileName);
							parsefile.setDataType(dataType);
							parsefile.setFileType(fileType);
							parsefile.setYwbh(zhkgh.getYwbh());
							parsefile.setParseDate(DataUtil.getDate(tranDate));
							parsefile.setCursign(zhkgh.getCurrency_Code());
							parsefile.setAmount(zhkgh.getAccount_Limit());
							parsefile.setOrgNo(orgNo);
							parsefile.setDataSources(zhkgh.getDatasources());
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
	 * 解析文件
	 * 由于山东联盟传递的数据中前面的ActionType和ActionDesc都不传递，所以默认为新增，这样数组就又原来的长度缩短了2位
	 * 
	 */
	public Zhkgh parseLine(String[] fields, String orgNo, String tranDate)
			throws Exception {
		java.util.Date tmpDate = null;
		java.sql.Date bussDate = null;
		java.sql.Date recdate = null;
		Zhkgh zhkgh = new Zhkgh();
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			//业务发生日期
			tmpDate = format.parse(fields[10].replaceAll("\"", "").trim());
			bussDate = new java.sql.Date(tmpDate.getTime());
			//数据接收或创建时间
			recdate = GeneralCalc.strToSQLDate(tranDate);
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("日期格式化错误: " + e);
		}
		try {
			zhkgh.setActiontype("A");											//操作类型
			zhkgh.setActiondesc("");											//修改、删除原因
			zhkgh.setBranch_Code(fields[0].replaceAll("\"", "").trim());		//金融机构标识码
			zhkgh.setBranch_Name(fields[1].replaceAll("\"", "").trim());		//金融机构名称
			zhkgh.setAccountno(fields[2].replaceAll("\"", "").trim());			//账号	
			zhkgh.setAccountstat(fields[3].replaceAll("\"", "").trim());		//账户状态
			zhkgh.setAmtype(fields[4].replaceAll("\"", "").trim());				//开户主体类型
			zhkgh.setEn_Code(fields[5].replaceAll("\"", "").trim());			//开户主体代码
			zhkgh.setEn_Name(fields[6].replaceAll("\"", "").trim());			//开户主体名称
			zhkgh.setAccount_Type(fields[7].replaceAll("\"", "").trim());		//账户性质代码
			zhkgh.setAccount_Cata(fields[8].replaceAll("\"", "").trim());		//账户类别
			zhkgh.setCurrency_Code(fields[9].replaceAll("\"", "").trim());		//币种
			if (null != bussDate && !"".equals(bussDate))				
				zhkgh.setBusiness_Date(bussDate);								//业务发生日期
			zhkgh.setFile_Number(fields[11].replaceAll("\"", "").trim());		//外汇局批件号/备案表号/业务编号
			zhkgh.setLimit_Type(fields[12].replaceAll("\"", "").trim());		//限额类型
			if (null != fields[13].trim() && !"".equals(fields[13].trim()))
				zhkgh.setAccount_Limit(new BigDecimal(fields[13].trim()));		//账户限额金额
			zhkgh.setRemark(fields[14].replaceAll("\"", "").trim());			//备注
			if (null != recdate && !"".equals(recdate))				
				zhkgh.setRecdate(recdate);										//接收/创建时间
			zhkgh.setDatasources("HX");											//数据来源(山东城商联盟)
			
			//根据金融机构标识码，获取国结机构号(采集机构)
			zhkgh.setBank_Id(dao.getOrgNoByBranchCode(zhkgh.getBranch_Code()));
			SerialNoFactory snf =  new SerialNoFactory();
			String txnNo = snf.getSerialNo(EbpConstants.DLDCLBSCID,16);
			zhkgh.setNguid(txnNo);						//国结生成的流水号
			zhkgh.setRwidh(zhkgh.getNguid());
			zhkgh.setYwbh(zhkgh.getNguid());
			zhkgh.setSxbz("1");
			zhkgh.setSfzx("Y");
			zhkgh.setSfysb("N");
			zhkgh.setHanddate(DataUtil.getTime(tranDate));
			
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("文件解析赋值错误: " + e);
			throw new Exception("文件解析赋值错误:" + e);
		}
		return zhkgh;
	}
}