package com.ebills.product.dg.action;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.bussprocess.context.Context;

import com.eap.exception.EAPException;
import com.eap.flow.EAPAction;
import com.ebills.util.EbillsException;
import com.ebills.util.EbillsLog;
import com.ebills.util.db.ConnectionManager;
import com.ebills.util.db.ConnectionManagerFactory;
import com.ebills.product.dg.AcctInterface.DgAcctInterface;
import com.ebills.intf.spi.InterfaceManager;
import com.ebills.utils.EbpDao;
/*
 * 批量向核心查询账户信息，后台处理。2014-11-21
 * */
public class GetAcctInfoAction extends EAPAction{
	private static String className = GetAcctInfoAction.class.getName();
	private static EbillsLog log = new EbillsLog(className);
	public String execute(Context context) throws EAPException{
			try {
				EbpDao dao  =new EbpDao();
				String sql="select acctno from dgmgrinfoar";
				List<Map<String,Object>> list  = dao.queryBySql(sql,"",null);
				if(list.size()>0){
					context.put("listnm", String.valueOf(list.size()));
					String acctno ="";
					boolean isFirst = true;
					for(int i=0;i<list.size();i++){
							List bodyList = new ArrayList();						
							Map map=(Map)list.get(i);
							String acctno1 = (String) map.get("acctno");	
							if(isFirst){
								isFirst = false;
								acctno = acctno1;
							}else{
								acctno = acctno+ ";"+acctno1;
							}										
					}
					context.put("bodrcd1", acctno);
					context = InterfaceManager.execute("ITSACL", context);
					Map  returnMap = (Map)context.get(InterfaceManager.RESULT_KEY);
					String listnm = (String)returnMap.get("listnm");
					List retunList = (List)returnMap.get("bodrcd");
					/*int nm = Integer.parseInt(listnm);
					for(int i=0;i<nm;i++){
						
					}*/
					String errorCode = (String) returnMap.get("errorCode");	
					String errorMsg = (String) returnMap.get("errorMsg");
					if("00000000".equals(errorCode)){						
							for(int i=0;i<retunList.size();i++){
								try {
									Map map = (Map)retunList.get(i);
									updateDate(map);
								}catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									continue;
								}
							}						 
					}
				}				
			} catch (Exception e) {
				e.printStackTrace();
		        log.debug("更新保证金账户余额失败，"+ e.getMessage());
			}
			return "";
	}
	
	public void updateDate(Map map)throws Exception{
		ConnectionManager cm = null;
		EbpDao dao  =new EbpDao();
		Map<String,Object> mapParam=new HashMap<String,Object>();
		Map<String,Object> condition=new HashMap<String,Object>();
		String returnAcctno = (String)map.get("acctno");
		String onlnbl = (String)map.get("onlnbl");
		Double bailAmt = Double.valueOf(onlnbl);
		try {
			cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);	
			condition.put("acctNo", returnAcctno);
			mapParam.put("bailAmt", onlnbl);
			dao.updateByDataId("DGMGRINFO", "ar", mapParam,condition);
		} catch (Exception ex) {
			if(cm != null){
				cm.setRollbackOnly();
			}
			ex.printStackTrace();
		}finally{
    		try {
    			if(cm != null){
    				cm.commit();
    			}
			} catch (EbillsException e) {
				log.error("释放连接失败"+e.getMessage());
			}
    	}
			
	}
}
