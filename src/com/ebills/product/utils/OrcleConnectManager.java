package com.ebills.product.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ebills.message.IMessageService;
import com.ebills.product.action.excel.entity.ExcelInputType;
import com.ebills.util.EbillsException;
import com.ebills.util.EbillsLog;
import com.ebills.util.db.ConnectionManager;
import com.ebills.util.db.ConnectionManagerFactory;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;


/**
 * @author Xiao He E-mail:hxtoyou@163.com
 * @version 创建时间：2015年6月3日 上午10:18:09
 * 
 */
public class OrcleConnectManager {
	protected Connection m_Connection;
	private ConnectionManager cm ;
	private static String className = OrcleConnectManager.class.getName();
	private EbillsLog log = new EbillsLog(className);
	protected  synchronized void makeConnection() throws EbillsException {
		cm = ConnectionManagerFactory.getConnectionManager();
		m_Connection = cm.getConnection();
	}
	public Connection getConnection()throws EbillsException {
		if(null == m_Connection)makeConnection();
		return m_Connection;
	}
	protected void releaseConnection() throws EbillsException {
		cm.releaseConnection(m_Connection);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map<String,String>> queryBySql(String sql, Table params,String resultTag) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Map<String,String>> list = Lists.newArrayList();
		try {
			String newSql = analyzeSQL(sql, params);
			statement = getConnection().prepareStatement(newSql);
			resultSet = statement.executeQuery();
			
			ResultSetMetaData meta = resultSet.getMetaData(); //ResultSetMetaData可获取列的类型和属性信息
	        int col = meta.getColumnCount(); //获取列的数目
			while(resultSet.next()) {
				Map result = Maps.newHashMap();
				for (int i = 0; i < col; i++) {
					int type = meta.getColumnType(i+1);
					String resultType = resultTag+".";
					String ColumnName =resultType + meta.getColumnName(i+1);
					if(java.sql.Types.DECIMAL == type || java.sql.Types.NUMERIC == type){
						//小数点后的位数 
						int scale=meta.getScale(i+1);
						java.text.NumberFormat nf = java.text.NumberFormat.getInstance();   
						nf.setGroupingUsed(false);  
						if(scale > 0){
							result.put(ColumnName,DataConvertUtil.resultSetConvert(String.valueOf(nf.format(resultSet.getDouble(i+1)))));
						} else {
							result.put(ColumnName,DataConvertUtil.resultSetConvert(String.valueOf(nf.format(resultSet.getLong(i+1)))));
						}
					} else if(java.sql.Types.DATE == type ){
						java.sql.Date sqldate = resultSet.getDate(i+1);
						if(sqldate != null){
							java.util.Date date = new Date(sqldate.getTime());
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							result.put(ColumnName,DataConvertUtil.resultSetConvert(sdf.format(date)));
						}
					} else if(java.sql.Types.TIMESTAMP == type || java.sql.Types.TIME == type) {
						java.util.Date date = new Date(resultSet.getTimestamp(i+1).getTime());
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						result.put(ColumnName,DataConvertUtil.resultSetConvert(sdf.format(date)));
					} else if(java.sql.Types.INTEGER == type){
						result.put(ColumnName,DataConvertUtil.resultSetConvert(String.valueOf(resultSet.getLong(i+1))));
					} else if(java.sql.Types.BLOB == type){
						try{
							InputStream stream = resultSet.getBinaryStream(i+1);
							result.put(ColumnName, readObject(stream));
						}catch (Exception e) {
							result.put(ColumnName, DataConvertUtil.resultSetConvert(new String(resultSet.getBytes(i+1))));
						}
					} else if(java.sql.Types.CLOB == type){
						Clob clob = resultSet.getClob(i+1);
						String content = clobToString(clob);
						content = content.replaceAll(IMessageService.swiftBegin, "").replaceAll(IMessageService.swiftEnd, "");
						result.put(ColumnName,DataConvertUtil.resultSetConvert(content));
					}else if(java.sql.Types.VARCHAR==type){
						result.put(ColumnName,DataConvertUtil.resultSetConvert(resultSet.getString(i+1)));
					}else{
						result.put(ColumnName, DataConvertUtil.resultSetConvert(resultSet.getString(i+1)));
					}
				}
				list.add(result);
			}
			System.out.println("--执行SQL语句--"+ newSql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cleanResources(statement);
		}
		return list;
	}
	/**
	 * 为参数赋值
	 * @param pstmt PreparedStatement
	 * @param params 参数列表
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	private void setSqlValue(PreparedStatement pstmt, Table params) {
		Set<Table.Cell<String, String, String>> cells = params.cellSet();
		int i=1;
		for(Table.Cell<String, String, String> rowData:cells){
//			String name = rowData.getRowKey();
			String type = rowData.getColumnKey();
			String value = rowData.getValue();
			try {
					if(!Strings.isNullOrEmpty(value)){
						
						if(type.equals(ExcelInputType.getValue("datebox"))) {
							pstmt.setDate(i,DataConvertUtil.String2date(value, "yyyyMMdd"));
						}else{
							pstmt.setString(i,value);
						}
					}else{
						pstmt.setNull(i,java.sql.Types.VARCHAR);
					}
//				}
				
			} catch (SQLException e) {
				// TODO: handle exception
			}
			i++;
		}
		
	}
	/**
	 * jdbc资源清理
	 * 
	 * @param statement
	 */
	protected void cleanResources(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException ex) {
				log.error(11,new String[]{"Exception occurred when close Statement"+ex.getMessage()});
			}
		}
	}
	protected Object readObject(InputStream inputStream) throws Exception {
		try {
			if(null == inputStream)
				return null;
			ObjectInputStream ois = new ObjectInputStream(inputStream);
			Object o = ois.readObject();
			ois.close();
			return o;
		} catch (Exception ex) {
			ex.printStackTrace();
			log.debug(11,new String[]{"read buffer error"+ex.getMessage()});
			throw ex;
		}
	}
	/**
	* Clob字段的通用转换
	* @return 转好的字符串，
	* **/
	public String clobToString(Clob clob) throws SQLException, IOException {   
		String reString = "";   //拼接变量
		Reader is = clob.getCharacterStream();// 得到流   
		BufferedReader br = new BufferedReader(is);   
		String s = br.readLine();   
		StringBuffer sb = new StringBuffer();   
		while (s != null) {  
			sb.append(s).append("\r\n");   
			s = br.readLine();   
		}  
		reString = sb.toString(); //转换成字符串，进行返回  
		if(reString.endsWith("\r\n")){
			reString = reString.substring(0, reString.length() -2);
		}
		return reString;   
	} 
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String analyzeSQL(String sql,Table params){
		Set<Table.Cell<String, String, String>> cells = params.cellSet();
		String newsql=sql;
		for(Table.Cell<String, String, String> rowData:cells){
				String name = rowData.getRowKey();
				String type = rowData.getColumnKey();
				String value = rowData.getValue();
				if(!Strings.isNullOrEmpty(value)){
					if(type.equals("easyui-datebox")) {
						newsql = newsql.replaceAll("\\?"+name, "to_date('"+value+"','yyyy-MM-dd')");
					}else{
						newsql = newsql.replaceAll("\\?"+name, "'"+value+"'");
					}
				}else{
					if(type.equals("easyui-datebox")) {
						newsql = newsql.replaceAll("\\?"+name, "to_date(null,'yyyy-MM-dd')");
					}else{
						newsql = newsql.replaceAll("\\?"+name, "null");
					}
				}
		}
		return newsql;
	}
}
