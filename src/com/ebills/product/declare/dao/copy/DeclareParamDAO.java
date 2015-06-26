package com.ebills.product.declare.dao.copy;


import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.ebills.declare.DeclareFactory;
import com.ebills.declare.DeclareInfos;
import com.ebills.product.declare.util.CIConstants;
import com.ebills.product.declare.util.DataUtil;
import com.ebills.util.EbillsException;
import com.ebills.utils.EbpDao;

public class DeclareParamDAO {
	private EbpDao dao = new EbpDao();
	/**
	 * 获得申报模板栏位
	 * @param dclKind
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public DeclareInfos getFFMap(String dclKind, String type) throws Exception{
		Map<String, DeclareInfos> infoMap = DeclareFactory.getDclInfo(dclKind);
		Iterator<String> it =   infoMap.keySet().iterator();
		DeclareInfos info = null;
		while (it.hasNext()) {
			String key = it.next();
			if(type.equals(key)){
				info =  infoMap.get(key);
			}
		}
		return info;
	}
	
	/**
	 * 根据模板名称获得模板中的表名集合
	 * @param dclKind
	 * @return
	 * @throws Exception
	 */
	public List<String> getTabName(String dclKind) throws Exception {
		Map<String, DeclareInfos> infoMap = DeclareFactory.getDclInfo(dclKind);
		Iterator<String> it =   infoMap.keySet().iterator();
		LinkedList<String> nameList = new LinkedList<String>();
		while (it.hasNext()) {
			String key = it.next();
			DeclareInfos info =  infoMap.get(key);
			nameList.add(info.getTableName());
		}
		return nameList;
	}
	
	/**
	 * 根据模板名称和申报子类型获得唯一表名
	 * @param dclKind
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public String getTabName(String dclKind, String type) throws Exception {
		Map<String, DeclareInfos> infoMap = DeclareFactory.getDclInfo(dclKind);
		Iterator<String> it =   infoMap.keySet().iterator();
		String tableName = "";
		while (it.hasNext()) {
			String key = it.next();
			if(type.equals(key)){
				DeclareInfos info =  infoMap.get(key);
				tableName = info.getTableName();
			}
		}
		return tableName;
	}
	/**
	 * 根据机构号获取对应的申报文件参数
	 * @param bankNo 国结申报机构号码
	 * @param kind
	 * @param type
	 * @param dclDate
	 * @return
	 * @throws Exception
	 */
	public String getDldclxfxh(String bankNo, String kind, String type, String dclDate) throws Exception {
		Map<String, Object> keyMap = new HashMap<String, Object>();
		keyMap.put("BANKID", bankNo);
		keyMap.put("SJLX", type);
		keyMap.put("FLAG", kind);
		List<Map<String, Object>> dataList = dao.queryByDataId(
				CIConstants.DLDCLXFID, "", keyMap);
		String maxString = "";
		for (Map<String, Object> map : dataList) {
			int maxValue = 0;
			// 如果取得日期为当前日期或者比当前日期小,则直接maxValue+1,否则重置maxValue
			if (dclDate.equals(map.get("RQ"))) {
				maxValue = (new Integer((String) map.get("MAXVAL"))).intValue() + 1;
			} else {
				maxValue = 1;
			}
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("RQ", dclDate);
			paramMap.put("MAXVAL", new Integer(maxValue).toString());
			dao.updateByDataId(CIConstants.DLDCLXFID, "", paramMap, keyMap);
			if (maxValue < 10) {
				maxString = "0" + new Integer(maxValue).toString();
			} else {
				maxString = new Integer(maxValue).toString();
			}
		}
		return maxString;
	}
	
	/**
	 * 获取申报类型MAP
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> getDldclFileType() throws EbillsException {
		Map<String,String> typeMap = new HashMap<String, String>();
		List<Map<String, Object>> retList = dao.queryByDataId(CIConstants.DLDCLMD, "", new HashMap<String,Object>());
		for (Map<String, Object> retmap : retList) {
			if(!DataUtil.ObjectIsNull(retmap.get("FILETYPE")) 
					&& !DataUtil.ObjectIsNull(retmap.get("DLDCLTYPE")))
				typeMap.put((String)retmap.get("FILETYPE"), (String)retmap.get("DLDCLTYPE"));
		}
		return typeMap;
	}

	/**
	 * 获得申报类型对应标识（0形成文件改变申报状态 1形成文件不改变申报状态 2失效即不形成文件不改状态）
	 * @param fileType
	 * @return
	 * @throws Exception
	 */
	public String getDldclMDFlag(String fileType) throws EbillsException {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("FILETYPE", fileType);
		List<Map<String, Object>> retList = dao.queryByDataId(CIConstants.DLDCLMD, "", paramMap);
		String flag = "2";
		for (Map<String, Object> retmap : retList) {
			flag = DataUtil.ObjectIsNull(retmap.get("DLDCLFLAG"))?"2":(String)retmap.get("DLDCLFLAG");
		}
		return flag;
	}
	
	/** 
	* 根据流水号获取金宏申报信息
	* @param txnNo
	* @param tableEnd
	* @throws EbillsException
	*/
	public List<Map<String, Object>> getDldclMD(String delcareKind) throws EbillsException {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("MDENNAME", delcareKind);
		return dao.queryByDataId(CIConstants.DLDCLMD, "", paramMap);
	}
}