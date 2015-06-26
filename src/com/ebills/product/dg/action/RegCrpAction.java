package com.ebills.product.dg.action;

import java.sql.Connection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.bussprocess.context.Context;
import org.apache.commons.bussprocess.exception.BPException;

import com.eap.flow.EAPAction;
import com.ebills.util.EbillsException;
import com.ebills.util.db.ConnectionManager;
import com.ebills.util.db.ConnectionManagerFactory;
import com.ebills.product.dg.commons.utils.ApplicationException;
import com.ebills.product.dg.intf.IErrorCode;
import com.ebills.intf.spi.InterfaceManager;
import com.ebills.utils.EbpDao;

public class RegCrpAction  extends EAPAction {

	public static String RECUST="recust";
	public String execute(Context cxt) throws BPException {
		try {
			List<Map<String, Object>> list=this.getAllCrp();
			if(list!=null&&list.size()>0){
				for(Map<String, Object> map:list){
					String maincorpno="";
					String corpno="";
	    			if(map.get("maincorpno")!=null){
	    				maincorpno=(String)map.get("maincorpno");
	    				cxt.put("custno",(String)map.get("maincorpno"));
	    			}
	    			if(map.get("corpno")!=null){
	    				corpno=(String)map.get("corpno");
	    			}
	    			if (maincorpno != null && !"".equals(maincorpno)) {
	    				cxt=InterfaceManager.execute(RECUST, cxt);
	        	    	Map data=(Map) cxt.get(InterfaceManager.RESULT_KEY);
	        			if (IErrorCode.INTF_SUCCESS.equals((String) data.get("errorCode"))) {
	        				if(corpno != null && !"".equals(corpno)){
	        					updateHadRed(corpno);
	        				}
	        			}
	    			}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.execute(cxt);
	}
	
	/**
	 * 查出所有待注册的数据
	 */
	public  List<Map<String, Object>> getAllCrp() throws Exception{
		List<Map<String, Object>> result=null;
		try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			try {
				EbpDao dao = new EbpDao();
				StringBuffer sql = new StringBuffer();
//				List<Object> inputList=new LinkedList<Object>();
				sql.append("select maincorpno,corpno from pacrp where isRegOCIF ='N'");
				StringBuffer item = new StringBuffer();
				item.append("maincorpno,corpno");
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
     *改为已经注册状态
     */
	 public void updateHadRed(String corpno){
		try {
				ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);
				try {
					EbpDao dao = new EbpDao();
					List<Object> inputList=new LinkedList<Object>();
//					Connection cn=cm.getConnection();
					inputList.add(corpno);
				    String intpsql="update pacrp set isRegOCIF='Y' where corpno=?";
					dao.execute(intpsql, inputList);
				} catch (Exception e) {
					cm.setRollbackOnly();
				}finally{
					cm.commit();
				}
				
			} catch (EbillsException e1) {
				e1.printStackTrace();
			}
	   }
}
