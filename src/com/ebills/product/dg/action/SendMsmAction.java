package com.ebills.product.dg.action;

import java.sql.Time;
import java.util.ArrayList;
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

public class SendMsmAction extends EAPAction{
	private static String className = SendMsmAction.class.getName();
	private static EbillsLog log = new EbillsLog(className);
	public String execute(Context context) throws EAPException{
			try {
				EbpDao dao  =new EbpDao();
				String sql="select txnNo,srno,acctno,phone,msgtxt, sendtp,accttp, acctty,trantp, signtp,isfree, dcmttp," +
						"gntime, userid, trbrno,febrno, fezone from tpsendmsmtp";
				List<Map<String,Object>> list  = dao.queryBySql(sql,"",null);
				for(int i=0;i<list.size();i++){
					try {
						List bodyList = new ArrayList();
						context.put("listnm", "1");//一条一条数据的发送
						Map map=(Map)list.get(i);
						bodyList.add(map);
						context.put("bodrcd", bodyList);
						String txnNo = (String) map.get("txnNo");
						context = InterfaceManager.execute("IFMSMSG", context);
						Map  returnMap = (Map)context.get(InterfaceManager.RESULT_KEY);
						String channelserno = (String)context.get("channelserno");
						String intfno = "";
						//把交易流水号更新至itstu表
						if(!"".equals(channelserno)){
							intfno = channelserno.substring(3);
							List<Object> params2 = new LinkedList<Object>();
							params2.add( intfno );
							String sql1 = "update itstu set txnNo='"+txnNo+ "' where  intfno=?";
							dao.execute(sql1, params2);
						}
						String errorCode = (String) returnMap.get("errorCode");	
						String errorMsg = (String) returnMap.get("errorMsg");
						if("00000000".equals(errorCode)){
							proceessFinish(map);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						continue;
					}
				}

					
				
			} catch (Exception e) {
				e.printStackTrace();
		        log.debug("向短信平台发送信息出错，"+ e.getMessage());
			}
			return "";
	}
	
	public void proceessFinish(Map map)throws Exception{
		ConnectionManager cm = null;
		EbpDao dao  =new EbpDao();
		try {
			cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);	
			dao.insertRow("TPSENDMSM", "ar", map);
			dao.deleteRow("TPSENDMSM", "tp", map);
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
