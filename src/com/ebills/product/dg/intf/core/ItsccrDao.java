package com.ebills.product.dg.intf.core;

import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.ebills.util.EbillsException;
import com.ebills.util.db.ConnectionManager;
import com.ebills.util.db.ConnectionManagerFactory;
import com.ebills.product.dg.commons.utils.ApplicationException;
import com.ebills.utils.EbpConstants;
import com.ebills.utils.EbpDao;

public class ItsccrDao {
	public final static String TPITSCCR_DATA_ID="TPITSCCR";
	/**
	 * 查询信贷放款接口表
	 */
	public  List getAllItsccr() throws Exception{
		List<Map<String, Object>> result=null;
		try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			try {
				EbpDao dao = new EbpDao();
				StringBuffer sql = new StringBuffer();
				sql.append("select txnno, bizno, custno,accono,cmitno,torfno,  dbitno,cucyno, tramts,remark, latype, lnrate,  to_char(mudate,'yyyyMMdd') mudate,inrcyc,retnfs,fnpdid from tpitsccrtp");
				StringBuffer item = new StringBuffer();
				item.append("txnno, bizno, custno,accono,cmitno,torfno,  dbitno,cucyno, tramts,remark, latype, lnrate,mudate,inrcyc,retnfs,fnpdid");
				result= dao.queryBySql(sql.toString(), item.toString(), null, null);
			} catch (Exception e) {
				cm.setRollbackOnly();
				throw new ApplicationException(e.getMessage());
			}finally{
				cm.commit();
			}
			
		} catch (EbillsException e1) {
			e1.printStackTrace();
			throw new ApplicationException(e1.getMessage());
		}
		return result;
		
	}
	/**
	 * 把信贷接口表的数据归档
	 * */
	public  List moveData(String  txnno) throws Exception{
		List<Map<String, Object>> result=null;
		try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			try {
				EbpDao dao = new EbpDao();
//				Map<String,Object> mapParam=new HashMap<String,Object>();
				Map<String,Object> condition=new HashMap<String,Object>();
				List<Object> inputList=new LinkedList<Object>();
//				Connection cn=cm.getConnection();
				condition.put("txnNo", txnno);
				inputList.add(txnno);
			    String infosql="insert into tpitsccrfo (select * from tpitsccrtp where TXNNO=?) ";
			    String inARsql="insert into tpitsccrar (select * from tpitsccrtp where TXNNO=?) ";
				dao.execute(infosql, inputList);
				dao.execute(inARsql, inputList);
				dao.deleteRow(TPITSCCR_DATA_ID, EbpConstants.TABLE_TP, condition);
			} catch (Exception e) {
				cm.setRollbackOnly();
				throw new ApplicationException(e.getMessage());
			}finally{
				cm.commit();
			}
			
		} catch (EbillsException e1) {
			e1.printStackTrace();
			throw new ApplicationException(e1.getMessage());
		}
		return result;
		
	}
	
}
