package com.ebills.product.declare.dao.copy;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.ebills.product.declare.util.CIConstants;
import com.ebills.util.EbillsException;
import com.ebills.utils.EbpDao;

public class DeclareErrDAO {
	private EbpDao dao = new EbpDao();
	/** 
	* 新增申报文件控制
	* @param paramMap
	* @throws EbillsException
	*/
	public void createDldclkz(Map<String,Object> paramMap) throws EbillsException {
		dao.insertRow(CIConstants.DLDCLKZ, "", paramMap);
	}
	
	/** 
	* 新增申报反馈
	* @param paramMap
	* @throws EbillsException
	*/
	public void createDldclerr(Map<String,Object> paramMap) throws EbillsException {
		dao.insertRow(CIConstants.DLDCLERRID, "", paramMap);
	}
	
	/** 
	* 查询申报反馈
	* @param paramMap
	* @throws EbillsException
	*/
	public List<Map<String, Object>> getDldclerrByRptNo(String rptNo, String dclKind) throws EbillsException {
		Map<String,Object> mapParam = new HashMap<String,Object>();
		mapParam.put("rptNo", rptNo);
		mapParam.put("dclKind", dclKind);
		mapParam.put("dclState", "0");
		return dao.queryByDataId(CIConstants.DLDCLERRID, "", mapParam);
	}
	
	/** 
	* 修改申报反馈
	* @param rptNo
	* @throws EbillsException
	*/
	public void updateDldclerrByTxnNo(Map<String,Object> keyParam) throws EbillsException {
		Map<String,Object> mapParam = new HashMap<String,Object>();
		mapParam.put("dclState", "1");
		dao.updateByDataId(CIConstants.DLDCLERRID, "", mapParam,keyParam);
	}
	
	/** 
	* 删除申报文件控制
	* @param kzName
	* @throws EbillsException
	*/
	public void deleteDldclkz(String kzName) throws EbillsException {
		Map<String,Object> keyMap = new HashMap<String, Object>();
		keyMap.put("kzName", kzName);
		dao.deleteRow(CIConstants.DLDCLKZ, "", keyMap);
	}
	/** 
	* 删除申报文件控制
	* @param kzName
	* @param rptNo
	* @param tpName
	* @throws EbillsException
	*/
	public void deleteDldclkzByParams(String kzName, String rptNo, String tpName, String kdName) throws EbillsException {
		Map<String,Object> keyMap = new HashMap<String, Object>();
		keyMap.put("kzName", kzName);
		keyMap.put("rptNo", rptNo);
		keyMap.put("tpName", tpName);
		keyMap.put("kdName", kdName);
		dao.deleteRow(CIConstants.DLDCLKZ, "", keyMap);
	}
	/** 
	* 修改申报状态
	* @param kzName
	* @param state
	* @param tpName
	* @throws EbillsException
	*/
	public void updateDldclSend(String kzName, String state, String tpName, String kind) throws EbillsException {
		String sql = "UPDATE DLDCLBSCFO SET "+state+" = 'Y' " +
				"WHERE RPTNO IN(SELECT RPTNO FROM DLDCLKZ WHERE KZNAME = ? AND TPNAME = ? and KDNAME = ?) and DCLKIND=? and "+state+"='N'";
		LinkedList<Object> inputList = new LinkedList<Object>();
		inputList.add(kzName);
		inputList.add(tpName);
		inputList.add(kind);
		inputList.add(kind);
		dao.execute(sql, inputList);
	}
	/** 
	* 修改申报状态
	* @param kzName
	* @param state
	* @param tpName
	* @throws EbillsException
	*/
	public void updateDldclState(String kzName, String state, String tpName, String val, String kdName) throws EbillsException {
		String sql = "UPDATE DLDCLBSCFO SET "+state+" = '"+val+"' " +
				"WHERE RPTNO IN(SELECT RPTNO FROM DLDCLKZ WHERE KZNAME = ? AND TPNAME = ? AND KDNAME = ?) and DCLKIND=? ";
		LinkedList<Object> inputList = new LinkedList<Object>();
		inputList.add(kzName);
		inputList.add(tpName);
		inputList.add(kdName);
		inputList.add(kdName);
		dao.execute(sql, inputList);
	}
	
	/** 
	* 修改申报状态
	* @param rptNo
	* @param state
	* @throws EbillsException
	*/
	public void updateDldclStatefs(String rptNo, String state, String kind) throws EbillsException {
		String sql = "UPDATE DLDCLBSCFO SET "+state+" = '3' WHERE RPTNO = ? AND DCLKIND = ?";
		LinkedList<Object> inputList = new LinkedList<Object>();
		inputList.add(rptNo);
		inputList.add(kind);
		dao.execute(sql, inputList);
	}
}