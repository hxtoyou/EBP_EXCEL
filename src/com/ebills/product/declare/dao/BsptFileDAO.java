package com.ebills.product.declare.dao;


import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ebills.product.declare.domain.BsptCommon;
import com.ebills.product.declare.domain.ParseBsptFile;
import com.ebills.product.declare.domain.ReceiveBsptFile;
import com.ebills.product.declare.util.CIConstants;
import com.ebills.product.declare.util.GeneralCalc;
import com.ebills.util.EbillsException;
import com.ebills.util.EbillsLog;
import com.ebills.util.db.ConnectionManager;
import com.ebills.util.db.ConnectionManagerFactory;

import com.ebills.utils.EbpDao;

public class BsptFileDAO {
	private String className = this.getClass().getName();
	private EbillsLog log = new EbillsLog(className);
	private EbpDao dao = new EbpDao();
	// ****************************************************************下载**************************************************************************
	/**
	 * 新增报送平台接收的信息
	 * 
	 * @param info
	 * @return
	 * @throws Exception 
	 */
	public void create(ReceiveBsptFile info) throws Exception {
		try {
			dao.insertRow(CIConstants.RECEIVEBSPTFILE_TABLENAME, "", GeneralCalc.Bean2Map(info));
		} catch (Exception e) {
			log.debug("新增记录失败：" + CIConstants.RECEIVEBSPTFILE_TABLENAME + e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * 更新报送平台接收的信息
	 * 
	 * @param info
	 * @return
	 * @throws Exception 
	 */
	public void update(ReceiveBsptFile info) throws Exception {
		try {
			HashMap pkInfo = new HashMap();
			pkInfo.put("fileName", info.getFileName());
			pkInfo.put("isParse", "Y");
			dao.updateByDataId(CIConstants.RECEIVEBSPTFILE_TABLENAME, "", GeneralCalc.Bean2Map(info), pkInfo);
		} catch (Exception e) {
			log.debug("更新记录失败：" + CIConstants.RECEIVEBSPTFILE_TABLENAME + e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * 更新报送平台接收的错误信息
	 * 
	 * @param info
	 * @return
	 * @throws Exception 
	 */
	public void update_error(ReceiveBsptFile info) throws Exception {
		try {
			HashMap pkInfo = new HashMap();
			pkInfo.put("fileName", info.getFileName());
			pkInfo.put("isParse", "N");
			dao.updateByDataId(CIConstants.RECEIVEBSPTFILE_TABLENAME, "", GeneralCalc.Bean2Map(info), pkInfo);
		} catch (Exception e) {
			log.debug("更新记录失败：" + CIConstants.RECEIVEBSPTFILE_TABLENAME + e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * 删除报送平台接收的信息
	 * 
	 * @param info
	 * @return
	 * @throws Exception 
	 */
	public void delete(ReceiveBsptFile info) throws Exception {
		try {
			HashMap pkInfo = new HashMap();
			pkInfo.put("fileName", info.getFileName());
			dao.deleteRow(CIConstants.RECEIVEBSPTFILE_TABLENAME, "", pkInfo);
		} catch (Exception e) {
			log.debug("删除记录失败：" + CIConstants.RECEIVEBSPTFILE_TABLENAME + e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * 根据文件名查找接收文件
	 * 
	 * @param
	 * @return
	 * @throws Exception 
	 */
	public ReceiveBsptFile getBsptFileinfo(String name) throws Exception {
		try {
			HashMap pkInfo = new HashMap();
			pkInfo.put("fileName", name);
			List<Map<String, Object>> list = dao.queryByDataId(CIConstants.RECEIVEBSPTFILE_TABLENAME, "", pkInfo);
			ReceiveBsptFile receiveBsptFile = new ReceiveBsptFile();
			for (Map<String, Object> map : list) {
				receiveBsptFile = (ReceiveBsptFile) GeneralCalc.Map2Bean(map, receiveBsptFile);
			}
			return receiveBsptFile;
		} catch (Exception e) {
			log.info("根据文件名查找接收文件失败" + e.getMessage());
			throw new Exception("查询下载文件时失败" + e.getMessage());
		}
	}


	// ****************************************************************解析**************************************************************************
	/**
	 * @param info
	 * @throws Exception 
	 * @ejb.interface-method
	 */
	public void create(ParseBsptFile info) throws Exception {
		try {
			log.info("info::" + info);
			dao.insertRow(CIConstants.PARSEBSPTFILE_TABLENAME, "", GeneralCalc.Bean2Map(info));
		} catch (Exception e) {
			log.debug("新增记录失败：" + CIConstants.PARSEBSPTFILE_TABLENAME + e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * @param info
	 * @throws Exception 
	 * @ejb.interface-method
	 */
	public void create(BigDecimal Amount, String Currency_Code,
			String Datasources, String DataType, String Fileinfono,
			String fileName, String FileType, String isparse, String Orgno,
			Date parseDate, String Remark1, String remark2, String remark3,
			String remark4, String ywbh) throws Exception {
		try {
			ParseBsptFile info = new ParseBsptFile();
			info.setFileName(fileName);
			info.setDataType(DataType);
			info.setFileType(FileType);
			info.setYwbh(ywbh);
			info.setParseDate(parseDate);
			info.setCursign(Currency_Code);
			info.setAmount(Amount);
			info.setOrgNo(Orgno);
			info.setDataSources(Datasources);
			info.setIsParse(isparse);
			info.setRemark1(Remark1);
			info.setFileInfoNo(Fileinfono);
			log.info("info::" + info);
			dao.insertRow(CIConstants.PARSEBSPTFILE_TABLENAME, "", GeneralCalc.Bean2Map(info));
		} catch (Exception e) {
			log.debug("新增记录失败：" + CIConstants.PARSEBSPTFILE_TABLENAME + e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

	public List getErrorData(ParseBsptFile parsefile) throws Exception {
		try {
			HashMap pkInfo = new HashMap();
			pkInfo.put("fileName", parsefile.getFileName());
			pkInfo.put("fileInfoNo", parsefile.getFileInfoNo());
			pkInfo.put("ywbh", parsefile.getYwbh());
			pkInfo.put("dataSources", parsefile.getDataSources());
			List<Map<String, Object>> list = dao.queryByDataId(CIConstants.PARSEBSPTFILE_TABLENAME, "", pkInfo);
			List retlist = new LinkedList();
			for (Map<String, Object> map : list) {
				ParseBsptFile parseBsptFile = (ParseBsptFile) GeneralCalc.Map2Bean(map, (new ParseBsptFile()));
				retlist.add(parseBsptFile);
			}
			return retlist;
		} catch (Exception e) {
			log.info("查询失败记录失败：" + e.getMessage());
			throw new Exception("查询失败记录失败：" + e.getMessage());
		}
	}

	/**
	 * @param info
	 * @throws Exception 
	 * @ejb.interface-method
	 */
	public void update_ParseBsptFile(ParseBsptFile info) throws Exception {
		try {
			HashMap pkInfo = new HashMap();
			pkInfo.put("fileName", info.getFileName());
			pkInfo.put("isParse", "Y");
			dao.updateByDataId(CIConstants.PARSEBSPTFILE_TABLENAME, "", GeneralCalc.Bean2Map(info), pkInfo);
		} catch (Exception e) {
			log.debug("更新记录失败：" + CIConstants.PARSEBSPTFILE_TABLENAME + e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * @param info
	 * @throws Exception 
	 * @ejb.interface-method
	 */
	public void delete_ParseBsptFile(ParseBsptFile info) throws Exception {
		try {
			HashMap pkInfo = new HashMap();
			pkInfo.put("fileName", info.getFileName());
			dao.deleteRow(CIConstants.PARSEBSPTFILE_TABLENAME, "", pkInfo);
		} catch (Exception e) {
			log.debug("删除记录失败：" + CIConstants.PARSEBSPTFILE_TABLENAME + e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	
	
	/**
	 * 查询下载文件List ParseBsptFile 实体类下载文件
	 * 
	 * @return
	 * @throws Exception 
	 */
	public List selectXzList(Date selectdate, String[] typeName)
			throws Exception {
		String SQLtradetype = "";
		String sql = "select * from BURECVBSPTFILE where 1 = 1 ";

		if (typeName != null) {
			for (int i = 0; i < typeName.length; i++) {
				if (i == 0) {
					SQLtradetype += (" AND (  fileName like ('" + typeName[i] + "%')");
				} else {
					SQLtradetype += ("  or fileName like ('" + typeName[i] + "%')");
				}
				if (i == typeName.length - 1) {
					SQLtradetype += " )";
				}
			}
		}
		log.info("查询下载业务种类：" + SQLtradetype);
		if (null != selectdate && !"".equals(selectdate)) {
			sql += " and reciveDate=?"; //'" + selectdate + "'";
		}
		sql += SQLtradetype + " ORDER BY REPORTDATE ";
		log.info("查询下载业务SQL：" + sql);
		List selectXzList = new LinkedList();
		try {
			selectXzList = selectXzListBySql(sql, selectdate);
		} catch (EbillsException e) {
			log.info("查询下载文件失败" + e);
			e.printStackTrace();
		}
		return selectXzList;
	}
	
	/**
	 * 查询解析文件List ParseBsptFile 实体类解析文件
	 * 
	 * @return
	 * @throws Exception 
	 */
	public List selectJxList(Date selectdate, String[] typeName, String flag)
			throws Exception {
		String sql = "select * from BUPASBSPTFILE where 1 = 1 ";
		String SQLtradetype = "";
		if (typeName != null) {
			for (int i = 0; i < typeName.length; i++) {
				if (i == 0) {
					SQLtradetype += (" AND (  fileName like ('" + typeName[i] + "%')");
				} else {
					SQLtradetype += ("  or fileName like ('" + typeName[i] + "%')");
				}
				if (i == typeName.length - 1) {
					SQLtradetype += " )";
				}
			}
		}
		log.info("查询解析业务种类：" + SQLtradetype);
		if (null != selectdate) {
			sql += " and parseDate =?"; // + selectdate;
		}
		if ("1".equals(flag)) {// 解析成功
			SQLtradetype += " AND ISPARSE='Y' ";
		} else if ("2".equals(flag)) {// 解析失败
			SQLtradetype += " AND ISPARSE='N' ";
		}
		List selectJxList = new LinkedList();
		sql += SQLtradetype + "  ORDER BY FILENAME ";
		log.info("查询解析sql:::" + sql);
		try {
			selectJxList = selectJxListBySql(sql, selectdate);
		} catch (EbillsException e) {
			log.info("查询解析文件数据失败" + e);
			e.printStackTrace();
		}
		return selectJxList;
	}
	
	/**
	 * 查询要下载文件List
	 * 
	 * @param info
	 * @throws Exception 
	 */
	public List selectXzListBySql(String sql, Date selectdate) throws Exception {
		LinkedList searchXzList = new LinkedList();
		try {
			LinkedList<Object> inputList = new LinkedList<Object>();
			if(selectdate != null){
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date fdate = df.parse(selectdate.toString());
				inputList.add(fdate);
			}
			List<Map<String, Object>> rsList = dao.queryBySqlForDataAllType(sql, inputList, CIConstants.RECEIVEBSPTFILE_TABLENAME);
			for (Map<String, Object> map : rsList) {
				map.put("remark1", String.valueOf(BsptCommon.getInstance().FiletradeType
						.get(map.get("dataType")) == null
						|| "".equals(BsptCommon.getInstance().FiletradeType
								.get(map.get("dataType"))) ? "错误文件类型"
						: BsptCommon.getInstance().FiletradeType.get(map.get("dataType"))));
				searchXzList.add(map);
			}
		} catch (Exception e) {
			log.info("查询下载文件时失败" + e.getMessage());
			System.out.println("查询下载文件时失败" + e.getMessage());
			throw new Exception("查询下载文件时失败" + e.getMessage());
		}
		return searchXzList;
	}

	/**
	 * 查询解析文件List
	 * 
	 * @param info
	 * @throws Exception
	 */
	public List selectJxListBySql(String sql, Date selectdate) throws Exception {
		LinkedList searchJxList = new LinkedList();
		try {
			LinkedList<Object> inputList = new LinkedList<Object>();
			if(selectdate != null){
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date fdate = df.parse(selectdate.toString());
				inputList.add(fdate);
			}
			List<Map<String, Object>> rsList = dao.queryBySqlForDataAllType(sql, inputList, CIConstants.PARSEBSPTFILE_TABLENAME);
			for (Map<String, Object> map : rsList) {
				if (map.get("dataType") == null
						|| "".equals(map.get("dataType"))
						|| null == map.get("fileName")
						|| "".equals(map.get("fileName"))) {
					map.put("remark2", "错误文件类型");
				} else {
					if (((String)map.get("fileName")).startsWith("ACC")
							&& ((String)map.get("dataType")).length() <= 5) {
						map.put("remark2",String.valueOf(BsptCommon
								.getInstance().ZHtradeType.get(map.get("dataType")) == null
								|| "".equals(BsptCommon.getInstance().ZHtradeType
										.get(map.get("dataType"))) ? "错误文件类型"
								: BsptCommon.getInstance().ZHtradeType
										.get(map.get("dataType"))));
					} else {
						map.put("remark2",String.valueOf(BsptCommon
								.getInstance().ZBtradeType.get(map.get("dataType")) == null
								|| "".equals(BsptCommon.getInstance().ZBtradeType
										.get(map.get("dataType"))) ? "错误文件类型"
								: BsptCommon.getInstance().ZBtradeType
										.get(map.get("dataType"))));
					}
				}
				searchJxList.add(map);
			}
		} catch (Exception e) {
			log.debug("查询解析文件List时失败" + e.getMessage());
			System.out.println("查询解析文件List时失败" + e.getMessage());
			throw new Exception("查询解析文件List时失败" + e.getMessage());
		}
		return searchJxList;
	}
	
	/** 
	* 根据核心机构号获取国结机构号
	* @param orgCode
	* @throws EbillsException
	*/
	public String getOrgNoByOrgCode(String orgCode) throws EbillsException {
		Map<String,Object> mapParam = new HashMap<String,Object>();
		mapParam.put("orgCode", orgCode);
		List<Map<String, Object>> list = dao.queryByDataId("PAORG", "", mapParam);
		String orgNo = "";
		for (Map<String, Object> map : list) {
			orgNo = (String) map.get("orgNo");
		}
		return orgNo;
	}
	
	/** 
	* 获取申报类型
	* @param fileType
	* @throws EbillsException
	*/
	public String getDldclMDName(String fileType) throws EbillsException {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("FILETYPE", fileType);
		List<Map<String, Object>> retList = dao.queryByDataId(CIConstants.DLDCLMD, "", paramMap);
		String name = "";
		for (Map<String, Object> retmap : retList) {
			name = GeneralCalc.objToString(retmap.get("MDENNAME"));
		}
		return name;
	}
	
	
	/** 
	* 获取金宏申报信息
	* @throws EbillsException
	*/
	public Object getSBDM(Object paramVal, String paramKey, String tabName, String retFeild) throws EbillsException {
		Map<String,Object> mapParam = new HashMap<String, Object>();
		mapParam.put(paramKey, paramVal);
		List<Map<String, Object>> list = dao.queryByDataId(tabName, "", mapParam);
		Object obj = null;
		for (Map<String, Object> map : list) {
			obj = map.get(retFeild);
		}
		return obj;
	}
	
	/**
	 * 新增
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public void create(Map<String, Object> zhkghMap , String tabName) throws Exception {
		dao.insertRow(tabName, CIConstants.TABLE_INFO, zhkghMap);
	}
	
	
	/**
	 * @desc 根据机构号获取对应的金融机构标识码
	 * @param bankNo 国结申报机构号码
	 * @return
	 * @throws Exception
	 */
	public String getOrgNoByBranchCode(String branchCode) throws Exception {
		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("DQDM", branchCode.substring(0,6));
		mapParam.put("JGDM", branchCode.substring(6,10));
		mapParam.put("JGXH", branchCode.substring(10,12));
		List<Map<String, Object>> dataList = dao.queryByDataId(
				CIConstants.PAORGCDID, "", mapParam);
		String bankNo = "";
		for (Map<String, Object> map : dataList) {
			// 规则: 6位地区代码+2位机构代码+4为机构序号
			bankNo = (String)map.get("BANKNO");
		}
		return bankNo;
	}
	
	
	/** 
	* 根据核心虚拟编号获取申报信息
	* @param orgCode
	* @throws EbillsException
	*/
	public List getDateByHxxnwzbh(String hxxnwzbh, String tabName) throws EbillsException {
		Map<String,Object> mapParam = new HashMap<String,Object>();
		mapParam.put("hxxnwzbh", hxxnwzbh);
		return dao.queryByDataId(tabName, "", mapParam);
	}
	
	
	/** 
	* 根据申报主键和数据来源获取金宏申报信息
	* @param rptNo
	* @param dataResource
	* @param tableEnd
	* @throws EbillsException
	*/
	public List<Map<String, Object>> getDecalreByRptNo(String rptNo, String dclKind, String tableEnd) throws EbillsException {
		Map<String,Object> mapParam = new HashMap<String,Object>();
		mapParam.put("rptNo", rptNo);
		mapParam.put("dclKind", dclKind);
		return dao.queryByDataId(CIConstants.DLDCLBSCID, tableEnd, mapParam);
	}
	
	/** 
	* 根据核心外债编号和数据来源获取金宏申报信息
	* @param rptNo
	* @param dataResource
	* @param tableEnd
	* @throws EbillsException
	*/
	public List<Map<String, Object>> getDecalreByHxxnwzbh(String hxxnwzbh, String dataResource, String orgNo, String tableEnd) throws EbillsException {
		Map<String,Object> mapParam = new HashMap<String,Object>();
		mapParam.put("hxxnwzbh", hxxnwzbh);
		mapParam.put("dataResource", dataResource);
		//mapParam.put("orgNo", orgNo);
		return dao.queryByDataId(CIConstants.DLDCLBSCID, tableEnd, mapParam);
	}
	
	/** 
	* 根据下载文件日期获取数据
	* @param kzDate
	* @throws EbillsException
	*/
	public List<Map<String, Object>> getBsptKz(String kzDate) throws EbillsException {
		Map<String,Object> mapParam = new HashMap<String,Object>();
		mapParam.put("KZDATE", kzDate);
		return dao.queryByDataId("PABSPTKZ", "", mapParam);
	}
	
	/** 
	* 新增资本取数控制日期
	* @param kzDate
	* @throws EbillsException
	*/
	public void creatBsptKz(String kzDate) throws EbillsException {
		Map<String,Object> mapParam = new HashMap<String,Object>();
		mapParam.put("KZDATE", kzDate);
		dao.insertRow("PABSPTKZ", "", mapParam);
	}
	/** 
	* 修改金宏申报信息
	* @param paramMap
	* @param keyMap
	* @param tableEnd
	* @throws EbillsException
	*/
	public void updateDecalreBuss(String tabName, Map<String,Object> paramMap, Map<String,Object> keyMap,String tableEnd) throws EbillsException {
		dao.updateByDataId(tabName, tableEnd, paramMap, keyMap);
	}
	
	/**
	 * 修改金宏申报信息
	 * @param paramMap
	 * @param keyMap
	 * @param tableEnd
	 * @throws EbillsException
	 */
	public void updateDecalre(Map<String,Object> paramMap, Map<String,Object> keyMap,String tableEnd) throws EbillsException {
		dao.updateByDataId(CIConstants.DLDCLBSCID, tableEnd, paramMap, keyMap);
	}
	
	/**
	 * @desc 根据机构号获取对应的金融机构标识码
	 * @param bankNo 国结申报机构号码
	 * @return
	 * @throws Exception
	 */
	public String getBranchCodeByOrgNo(String bankNo) throws Exception {
		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("BANKNO", bankNo);
		List<Map<String, Object>> dataList = dao.queryByDataId(
				CIConstants.PAORGCDID, "", mapParam);
		String branchCode = "";
		for (Map<String, Object> map : dataList) {
			// 规则: 6位地区代码+2位机构代码+4为机构序号
			branchCode = (String)map.get("DQDM")+(String)map.get("JGDM")+(String)map.get("JGXH");
		}
		return branchCode;
	}
	
	/**
	 * 2014-08-26 LT
	 * fjmgrck.getContractcurr(), fjmgrck.getCrehqcode(),fjmgrck.getSpapfeboindex()
	 * @return
	 * @throws Exception
	 */
	public static boolean isFjmgrck(String contractcurr,String crehqcode,String spapfeboindex) throws Exception{		
		boolean isFjmgrckData = false;		
		ConnectionManager cm = null;
		try {
			cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);				
			EbpDao gjDao = new EbpDao();			
			if(!StringUtils.isEmpty(contractcurr) && !StringUtils.isEmpty(crehqcode) && !StringUtils.isEmpty(spapfeboindex)){
				String isFjmgrckSql = "select a.EXDEBTCODE from SBEXDAPFO a WHERE a.CONTRACTCURR='"+ contractcurr + "' AND a.CREHQCODE = '" + crehqcode + "' AND a.SPAPFEBOINDEX = '" + spapfeboindex + "'";
				List<Map<String, Object>> fjmgrckList = gjDao.queryBySql(isFjmgrckSql, null,null);
				if(null != fjmgrckList && !fjmgrckList.isEmpty() 
						&& fjmgrckList != null && fjmgrckList.size() > 0){
						Map<String,Object> map =  fjmgrckList.get(0);
//						exdebtCode = (String) map.get("EXDEBTCODE");
						isFjmgrckData = true;
				}
			}			
		} catch (Exception ex) {
			cm.setRollbackOnly();
			ex.printStackTrace();
		}finally{
    		try {
				cm.commit();
			} catch (EbillsException e) {
				e.printStackTrace();
			}
    	}
		return isFjmgrckData;
	}
}