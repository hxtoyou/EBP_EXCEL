package com.ebills.product.declare.dao.copy;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jfree.util.Log;

import com.ebills.product.declare.util.CIConstants;
import com.ebills.util.EbillsException;
import com.ebills.utils.EbpDao;

public class DeclareBussDAO {
	private EbpDao dao = new EbpDao();

	/**
	 * 根据条件获取金宏申报分表信息
	 * @param tabName
	 * @param paramMap
	 * @param tableEnd
	 * @return
	 * @throws EbillsException
	 */
	public List<Map<String, Object>> getDecalreBussByParams(String tabName, Map<String,Object> paramMap, String tableEnd) throws EbillsException {
		String sql = 
			"select b.* " +
				"from DLDCLBSCFO a,"+tabName+tableEnd+" b " +
			"WHERE a.TXNNO = b.TXNNO " +
			"AND a.RPTNO=b.SBKEYNO " +
			"AND a.DCLKIND=? " +
			"AND a.DCLDATE BETWEEN ? AND ? " +
			"AND a.ORGNO=?  " +
			"AND a."+paramMap.get("input")+" = 'Y' AND a."+paramMap.get("state")+"='0' " +
			"ORDER BY a.DCLDATE ";
		Log.info("形成" + tabName+tableEnd + "申报表的数据的SQL为:"+sql);
		LinkedList<Object> inputList = new LinkedList<Object>();
		inputList.add(paramMap.get("dclKind"));
		inputList.add(paramMap.get("startDate"));
		inputList.add(paramMap.get("endDate"));
		inputList.add(paramMap.get("orgNo"));

		return dao.queryBySqlForDataAllType(sql, inputList, tabName);
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
	 * 根据条件获取金宏申报信息
	 * @param paramMap
	 * @param tableEnd
	 * @return
	 * @throws EbillsException
	 */
	public List<Map<String, Object>> getDecalreByParams(Map<String,Object> paramMap, String tableEnd) throws EbillsException {
		EbpDao dao = new EbpDao();
		return dao.queryByDataId(CIConstants.DLDCLBSCID, tableEnd, paramMap);
	}
	
	/** 
	* 新增申报文件控制
	* @param paramMap
	* @throws EbillsException
	*/
	public void createDldclkz(Map<String,Object> paramMap) throws EbillsException {
		dao.insertRow(CIConstants.DLDCLKZ, "", paramMap);
	}
	
	/** 
	* 创建金宏申报信息
	* @param paramMap
	* @param tableEnd
	* @throws EbillsException
	*/
	public void createDecalre(Map<String,Object> paramMap, String tableEnd) throws EbillsException {
		dao.insertRow(CIConstants.DLDCLBSCID, tableEnd, paramMap);
	}
	
	
	/** 
	* 创建金宏申报信息
	* @param paramMap
	* @param tableEnd
	* @throws EbillsException
	*/
	public void createDecalreBuss(String tabName, Map<String,Object> paramMap, String tableEnd) throws EbillsException {
		dao.insertRow(tabName, tableEnd, paramMap);
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
	* 根据流水号获取金宏申报信息
	* @param txnNo
	* @param tableEnd
	* @throws EbillsException
	*/
	public List<Map<String, Object>> getDecalreByTxn(String txnNo, String tableEnd) throws EbillsException {
		Map<String,Object> mapParam = new HashMap<String,Object>();
		mapParam.put("txnNo", txnNo);
		return dao.queryByDataId(CIConstants.DLDCLBSCID, tableEnd, mapParam);
	}
	
	/** 
	* 根据流水号获取金宏申报补录信息
	* @param txnNo
	* @param tableEnd
	* @throws EbillsException
	*/
	public List<Map<String, Object>> getDecalreInputByTxn(String txnNo, String tableEnd) throws EbillsException {
		Map<String,Object> mapParam = new HashMap<String,Object>();
		mapParam.put("txnNo", txnNo);
		return dao.queryByDataId(CIConstants.DLDCLINPUTID, tableEnd, mapParam);
	}
	
	/** 
	* 根据流水号获取金宏申报信息
	* @param txnNo
	* @param tableEnd
	* @throws EbillsException
	*/
	public List<Map<String, Object>> getDecalreBussByTxnNo(Map<String, Object> mapParam, String tabName, String tableEnd) throws EbillsException {
		return dao.queryByDataId(tabName, tableEnd, mapParam);
	}
	
	/** 
	* 删除金宏申报信息
	* @param txnNo
	* @param tableEnd
	* @throws EbillsException
	*/
	public void deleteDecalreBussByParams(String tabName, Map<String,Object> keyMap, String tableEnd) throws EbillsException {
		dao.deleteRow(tabName, tableEnd, keyMap);
	}
	
	/** 
	* 删除金宏申报信息
	* @param txnNo
	* @param tableEnd
	* @throws EbillsException
	*/
	public void deleteDecalreByParams(Map<String,Object> keyMap, String tableEnd) throws EbillsException {
		dao.deleteRow(CIConstants.DLDCLBSCID, tableEnd, keyMap);
	}
	
	/** 
	* 删除金宏申报信息
	* @param txnNo
	* @param tableEnd
	* @throws EbillsException
	*/
	public void deleteDecalreBuss(String tabName, String txnNo, String tableEnd) throws EbillsException {
		Map<String,Object> keyMap = new HashMap<String, Object>();
		keyMap.put("txnNo", txnNo);
		dao.deleteRow(tabName, tableEnd, keyMap);
	}
	
	/** 
	* 删除金宏申报信息
	* @param txnNo
	* @param tableEnd
	* @throws EbillsException
	*/
	public void deleteDecalre(String txnNo, String tableEnd) throws EbillsException {
		Map<String,Object> keyMap = new HashMap<String, Object>();
		keyMap.put("txnNo", txnNo);
		dao.deleteRow(CIConstants.DLDCLBSCID, tableEnd, keyMap);
	}
}