package com.ebills.product.dg.action;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.bussprocess.context.Context;

import com.eap.exception.EAPException;
import com.eap.flow.EAPAction;
import com.ebills.commons.SerialNoFactory;
import com.ebills.intf.spi.InterfaceManager;
import com.ebills.script.BaseScript;
import com.ebills.util.EbillsException;
import com.ebills.util.EbillsLog;
import com.ebills.util.db.ConnectionManager;
import com.ebills.util.db.ConnectionManagerFactory;
import com.ebills.utils.EbpDao;
/**
 * 更新账户行日初余额表，跑批后更新
 * @author zhangQi
 *
 */
public class GetDairyAcctBankBal extends EAPAction {
	private static String className = GetAcctInfoAction.class.getName();
	private static EbillsLog log = new EbillsLog(className);
	private  BaseScript bs = new BaseScript();
	
	@SuppressWarnings("unchecked")
	public String execute(Context context) throws EAPException{		
		EbpDao dao  =new EbpDao();
		//查询所有账户行的账号
		String acctNosql = "select acctno from PAACCFO t where acctTypeNo in('ZA00000051','ZA00000052')";
		try {
			List<Map<String,Object>> acctNoList  = dao.queryBySql(acctNosql,"",null);
			String acctno ="";
			if(acctNoList.size()>0){
				context.put("listnm",  String.valueOf(acctNoList.size()));
				boolean isFirst = true;
				for(int i=0;i<acctNoList.size();i++){					
						Map<String,Object> map=(Map<String,Object>)acctNoList.get(i);
						String acctno1 = (String) map.get("acctno");	
						if(isFirst){
							isFirst = false;
							acctno = acctno1;
						}else{
							acctno = acctno+ ";"+acctno1;
						}										
				}
			}
			//调用核心接口查询账户行余额
			context.put("bodrcd1", acctno);
			context = InterfaceManager.execute("ITSACL", context);
			//返回信息
			Map<String,Object>  returnMap = (Map<String,Object> )context.get(InterfaceManager.RESULT_KEY);
	        //返回条目
//			String listnm = (String)returnMap.get("listnm"); 	
			List<Map<String,Object>> retunList = (List<Map<String,Object>> )returnMap.get("bodrcd");
			String errorCode = (String) returnMap.get("errorCode");	//错误代码
//			String errorMsg = (String) returnMap.get("errorMsg");//错误信息
			if("00000000".equals(errorCode)){						
				for(int i=0;i<retunList.size();i++){
					try {
						Map<String,Object> map = (Map<String,Object>)retunList.get(i);
						updateDate(map);
					}catch (Exception e) {
						e.printStackTrace();
						continue;
					}
				}						 
		}
			
		} catch (EbillsException e) {
			e.printStackTrace();
		}
		return "";
	
	}
	
	/**
	 * 更新数据
	 * @param map
	 * @throws EbillsException
	 */
	@SuppressWarnings("null")
	private void updateDate(Map<String,Object> map) throws EbillsException{
		PreparedStatement prepStmt = null;
		Connection conn = null;
		@SuppressWarnings("unused")
		ResultSet rs = null;
		ConnectionManager cm  =null;
		
		List<Object> paramList  = new ArrayList<Object>();
		BaseScript base = new BaseScript();
		String acctno = (String)map.get("acctno");//账号
		String ccyCode = (String)map.get("crcycd");
		String ccy = bs.getTableValue("cursign", "paccy", "standardcode ='" + ccyCode +"'");
		String onlnbl = (String)map.get("onlnbl"); //单前余额
		Double bailAmt = Double.valueOf(onlnbl);
		String swiftcode = base.getTableValue("b.bankswiftcode", "paaccfo a, pabank b", "a.custbankno = b.bankno and a.acctno ='" + acctno + "'");
		String insertSql = "insert into  BUACCTBKBAL(serialNo,bankSwifCode,acctNo,updateDate,cursign,bal) " 
						  + " values(?,?,?," 
						  + "to_date((select a.sysval from pasys a where a.sysnameid ='workDate')," 
						  + "'yyyy-mm-dd'),?,?)";
		String serialNo = getSerialNo();
		paramList.add(serialNo);
		paramList.add(swiftcode);
		paramList.add(acctno);
		paramList.add(ccy);
		paramList.add(bailAmt);
		
		try {
			if(null == cm) cm = ConnectionManagerFactory.getConnectionManager();
			conn = cm.getConnection();
			cm.startTransaction(true);	
			prepStmt = conn.prepareStatement(insertSql);
			setParameter(prepStmt, paramList);
			rs = prepStmt.executeQuery();
			cm.commit();
			} catch (Exception ex) {
			cm.setRollbackOnly();
			ex.printStackTrace();
		}finally{
    		try {
    			if(null == cm) {
    				cm.releaseConnection(conn);
    			}
			} catch (EbillsException e) {
				log.error("释放连接失败"+e.getMessage());
			}
    	}
	}
	
	/**
	 * Description: 设置参数
	 * 
	 * @param prepStmt
	 * @param params
	 * @throws EbillsException
	 */
	private void setParameter(PreparedStatement prepStmt, List<Object> params)
			throws EbillsException {
		try {
			if (params != null) {
				for (int i = 1; i <= params.size(); i++) {
					Object o = params.get(i - 1);
					if (o == null) {
						prepStmt.setObject(i, o);
					} else if (o instanceof java.util.Date) {
						prepStmt.setTimestamp(i, new java.sql.Timestamp(
								((java.util.Date) o).getTime()));
					} else if (o instanceof Integer) {
						prepStmt.setInt(i, ((Integer) o).intValue());
					} else if (o instanceof Double) {
						prepStmt.setDouble(i, ((Double) o).doubleValue());
					} else if (o instanceof BigDecimal) {
						prepStmt.setBigDecimal(i, (BigDecimal) o);
					} else if (o instanceof String) {
						prepStmt.setString(i, (String) o);
					} else if (o instanceof byte[]) {
						prepStmt.setBytes(i, (byte[]) o);
					} else {

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 产生序列号
	 * @return
	 * @throws EbillsException
	 */
	private String getSerialNo() throws EbillsException {
		SerialNoFactory snf =  new SerialNoFactory();
		String serialNo = snf.getSerialNo("BUACCTBKBAL",10);
		return serialNo;
	}
	
	
}
