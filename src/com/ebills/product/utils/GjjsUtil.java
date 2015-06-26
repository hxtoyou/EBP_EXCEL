package com.ebills.product.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ebills.clear.util.ClearLog;
import com.ebills.util.EbillsException;
import com.ebills.util.db.ConnectionManager;
import com.ebills.util.db.ConnectionManagerFactory;
import com.ebills.utils.CommonUtil;
import com.ebills.utils.EbpConstants;
import com.ebills.utils.EbpDao;

/**
 * <code>GjjsUtil</code>存放实施项目的公共内容
 * @author stuu
 * @time 2015年4月1日 14:51:55
 *
 */
public class GjjsUtil {
	
	/**币种小数位*/
	private static Map<String, Integer> ccyMap = new HashMap<String, Integer>();
	
	/**
	 * 获取某币种的小数点保留几位有效数字
	 * @param ccy 币种
	 * @return
	 * @throws EbillsException
	 */
	public static int getCcyPrecision(String ccy) throws EbillsException{
		if(ccyMap.containsKey(ccy)){
			return ccyMap.get(ccy);
		}
		int iPercision = 0;
		synchronized (ccyMap) {
			EbpDao jsDao = new EbpDao();
			Map<String,Object> mapParam = new HashMap<String, Object>();
			mapParam.put("cursign", ccy);
			List<Map<String,Object>> list= jsDao.queryByDataId("paccy", "", mapParam);
			for(Map<String,Object> ccyInfo : list){
				String percision = (String) ccyInfo.get("percision");
				if(StringUtils.isNotEmpty(percision)){
					iPercision = Integer.parseInt(percision);
				}
			}
			ccyMap.put(ccy, iPercision);
			return iPercision;
		}
	}
	public static String getLocalCcy() throws EbillsException{
		String localCcy = CommonUtil.getSysFld(EbpConstants.LOCAL_CURRENCY);
		if(null == localCcy) localCcy = "";
		return localCcy;
		
	}
	
	public static void insertClearLogs(List<ClearLog> logs) throws EbillsException{
		if(logs == null || logs.isEmpty()){
			return;
		}
		String sql = "insert into clmsglogs  (msgid, message, filepath, operatedate, logtype, sort, logid) values (?, ?, ?, ?, ?, ?, ?)";
		ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
	    cm.startTransaction(true);
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    Connection _Connection = null;
	    try {
	    	_Connection = cm.getConnection();
	    	stmt = _Connection.prepareStatement(sql);
	    	for(ClearLog  log : logs){
	    		stmt.setString(1, log.getMsgId());
	    		stmt.setString(2, log.getMessage());
	    		stmt.setString(3, log.getFilePath());
	    		stmt.setTimestamp(4, new java.sql.Timestamp(log.getOperateDate().getTime()));
	    		stmt.setString(5, log.getLogType());
	    		stmt.setInt(6, log.getSort());
	    		stmt.setString(7, log.getLogId());
	    		stmt.executeUpdate();
	    	}
	    }catch (EbillsException sqle) {
			try {
				//_Connection.rollback();
				cm.setRollbackOnly();
			} catch (Exception e) {
				throw new EbillsException(e, CommonUtil.class.getName());
			}
			throw sqle;
		} catch (Exception e) {
			try {
				//_Connection.rollback();
				cm.setRollbackOnly();
			} catch (Exception e1) {
				throw new EbillsException(e1, CommonUtil.class.getName());
			}
			throw new EbillsException(e, CommonUtil.class.getName(),1,null,null);
		} finally {
	    	if( rs != null ){
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if( stmt != null ){
				try {
					stmt.close();
				} catch (SQLException e) {
				}
			}
			
			try {
				cm.releaseConnection(_Connection);
			} catch (Exception e1) {
			}
			
			try{
				//_Connection.close();
				cm.commit();
			}catch(Exception e){
				throw new EbillsException(e, CommonUtil.class.getName());
			}
		}
	}
}
