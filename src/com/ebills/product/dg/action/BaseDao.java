package com.ebills.product.dg.action;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.ebills.utils.EbpDao;

import oracle.jdbc.driver.OracleTypes;

public class BaseDao {
	private Connection conn = null;  
    private CallableStatement callStmt = null;
    private PreparedStatement pstmt = null;
    private boolean flag = false;
	private int result = 0;
	private List list = new ArrayList();
	private ResultSet rs = null;
	EbpDao dao = new EbpDao();
	
	/**
	 * 创建连接
	 * @return 返回Connection
	 */
	private Connection createConn() {
		try {
			conn = dao.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * 执行存储过程(没有参数In、Out)
	 * @param procName 存储过程名
	 * @return 返回执行结果(Boolean类型)
	 */
	public boolean executeProc(String procName) {
		List params = new ArrayList();
		
		try {
			callStmt = createConn().prepareCall(createProcString(procName, params));
			result = callStmt.executeUpdate();
			if(result > 0) {
				flag = true;
				System.out.println("--执行存储过程--"+ procName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			breakConnection();
		}
		return flag;
	}
	
	/**
	 * 执行存储过程(有参数In)
	 * @param procName 存储过程名
	 * @param params 参数列表
	 * @return 返回执行结果(Boolean类型)
	 */
	public boolean executeProcByParamsIn(String procName, List params) {
		
		try {
			callStmt = createConn().prepareCall(createProcString(procName, params));
			setProcValue(callStmt, params);
			result = callStmt.executeUpdate();
			if(result > 0) {
				flag = true;
				System.out.println("--执行存储过程--"+ procName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			breakConnection();
		}
		return flag;
	}
	
	/**
	 * 执行存储过程(有参数In、Out)
	 * @param procName 存储过程名
	 * @param params 参数列表
	 * @return 返回执行结果(List类型)
	 */
	public List executeProcByParamsInAndOut(String procName, List params) {
		String str = createProcString(procName, params).substring(0, createProcString(procName, params).lastIndexOf(")"));
		str = str + ",?)}";
		
		try {
			callStmt = createConn().prepareCall(str);
			setProcValue(callStmt, params);
			callStmt.registerOutParameter(params.size() + 1, OracleTypes.CURSOR);
			result = callStmt.executeUpdate();
			System.out.println("--执行存储过程--"+ procName);
			
			rs = (ResultSet) callStmt.getObject(params.size() + 1);
			int colunmCount = rs.getMetaData().getColumnCount();  
			while(rs.next()) {
				for (int i = 0; i < colunmCount; i++) {
					list.add(rs.getString(i + 1));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			breakConnection();
		}
		return list;
	}
	
	/**
	 * 执行SQL语句
	 * @param sql SQL语句
	 * @param params 参数列表
	 * @return 返回执行结果(List类型)
	 */
	public List queryBySql(String sql, List params) {
		
		try {
			pstmt = createConn().prepareStatement(sql);
			setSqlValue(pstmt, params);
			rs = pstmt.executeQuery();
			
			ResultSetMetaData md = rs.getMetaData(); //ResultSetMetaData可获取列的类型和属性信息
	        int col = md.getColumnCount(); //获取列的数目
			while(rs.next()) {
				for (int i = 0; i < col; i++) {
					list.add(rs.getString(i + 1));
				}
			}
			System.out.println("--执行SQL语句--"+ sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			breakConnection();
		}
		return list;
	}
	
	/**
	 * 执行SQL语句
	 * @param sql SQL语句
	 * @param params 参数列表
	 * @return 返回执行结果(boolean类型)
	 */
	public boolean updateBySql(String sql, List params) {
		
		try {
			pstmt = createConn().prepareStatement(sql);
			setSqlValue(pstmt, params);
			result = pstmt.executeUpdate();
			if(result > 0) {
				flag = true;
				System.out.println("--执行SQL语句--"+ sql);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			breakConnection();
		}
		return flag;
	}
	
	/**
	 * 关闭数据库连接
	 */
	private void breakConnection() {
		try {
			if(result > 0) {
				System.out.println("--执行结果--"+ flag + "\n");
				flag = true;
			} else if(list.size() > 0) {
				System.out.println("--执行结果--true\n");
			} else {
				System.out.println("--执行结果--"+ flag + "\n");
			}
			
			if(conn != null) {
				conn = null;;
			}
			if(callStmt != null) {
				callStmt.close();
			}
			if(pstmt != null) {
				pstmt.close();
			}
			if(rs != null) {
				rs.close();
			}
//			System.out.println("--关闭连接--\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 创建存储过程字符串
	 * @param procName 存储过程名
	 * @param params 参数列表
	 * @return 返回存储过程字符串
	 */
	private String createProcString(String procName, List params) {
		String start = "";
		StringBuilder end = new StringBuilder();
		if(params.size() > 0) {
			for (int i = 0; i < params.size(); i++) {
				start = start + "?,";
			} 
			start = start.substring(0, start.lastIndexOf(","));
		} 
		end.append("{call ").append(procName)
		.append("(").append(start)
		.append(")}");
		return end.toString();
	}
	
	/**
	 * 为参数赋值
	 * @param callStmt 存储过程名
	 * @param params 参数列表
	 */
	private void setProcValue(CallableStatement callStmt, List params) {
		try {
			if(params.size() > 0) {
				for (int i = 0; i < params.size(); i++) {
					if(params.get(i) instanceof java.lang.String) {
						callStmt.setString(i + 1, (String) params.get(i));
					}
					if(params.get(i) instanceof java.lang.Integer) {
						callStmt.setInt(i + 1, ((Integer) params.get(i)).intValue());
					}
					if(params.get(i) instanceof java.util.Date) {
						callStmt.setDate(i + 1, (Date) params.get(i));
					}
					if(params.get(i) instanceof java.lang.Float) {
						callStmt.setFloat(i + 1, ((Float) params.get(i)).floatValue());
					}	
				} 
//				System.out.println("--参数赋值--");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 为参数赋值
	 * @param pstmt PreparedStatement
	 * @param params 参数列表
	 */
	private void setSqlValue(PreparedStatement pstmt, List params) {
		try {
			if(params.size() > 0) {
				for (int i = 0; i < params.size(); i++) {
					if(params.get(i) instanceof java.lang.String) {
						pstmt.setString(i + 1, (String) params.get(i));
					}
					if(params.get(i) instanceof java.lang.Integer) {
						pstmt.setInt(i + 1, ((Integer) params.get(i)).intValue());
					}
					if(params.get(i) instanceof java.util.Date) {
						pstmt.setDate(i + 1, (Date) params.get(i));
					}
					if(params.get(i) instanceof java.lang.Float) {
						pstmt.setFloat(i + 1, ((Float) params.get(i)).floatValue());
					}	
				} 
//				System.out.println("--参数赋值--");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
