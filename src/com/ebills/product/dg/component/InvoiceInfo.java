package com.ebills.product.dg.component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.bussprocess.context.Context;

import com.alibaba.fastjson.JSONObject;
import com.ebills.commons.BusinessComponentAbstract;
import com.ebills.commons.ComponentConfig;
import com.ebills.util.EbillsException;
import com.ebills.util.EbillsLog;
import com.ebills.util.db.ConnectionManager;
import com.ebills.util.db.ConnectionManagerFactory;
import com.ebills.utils.CommonUtil;
import com.ebills.utils.EbpConstants;
import com.ebills.utils.EbpDao;

public class InvoiceInfo extends BusinessComponentAbstract  {
	private static String className = InvoiceInfo.class.getName();
	private static EbillsLog log = new EbillsLog(className);
	private static String INVOICE_DATA_ID = "INVOICEINFO"; 			//表名
	private static String CFF_KEY ="module_invoice";
	@Override
	public void doCorrectAfterScript(Context context) throws EbillsException {
		// TODO Auto-generated method stub
		try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			try {
				EbpDao dao = new EbpDao();
				Map<String,Object> condition=new HashMap<String,Object>();
				List<Object> inputList=new LinkedList<Object>();
				String bizNo=(String)context.get("butxn_curtBizNo");
				condition.put("bizNo", bizNo);
				inputList.add(bizNo);
			    String delfosql="delete from INVOICEINFOAR where bizNo=? ";
			    String delARsql="delete from INVOICEINFOFO where bizNo=? ";
			   // String delTPsql="delete from INVOICEINFOAR where bizNo=? ";
				dao.execute(delfosql, inputList);
				dao.execute(delARsql, inputList);
				List<Map<String,Object>> allData = (List<Map<String,Object>>)context.get(ComponentConfig.getContextKey(CFF_KEY));
				List<Map<String,Object>> insertTmp = new LinkedList<Map<String,Object>> ();
				if(allData == null || allData.isEmpty())return ;
				for(Map<String,Object> oneChg : allData){
						oneChg.put("bizNo", context.getValue(EbpConstants.TRANSACTION_BIZNO));
						insertTmp.add(oneChg);
				}
				dao.insertRow(INVOICE_DATA_ID, EbpConstants.TABLE_TP, insertTmp);
			} catch (Exception e) {
				cm.setRollbackOnly();
			}finally{
				cm.commit();
			}
			
		} catch (EbillsException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void doDeleteAfterScript(Context context) throws EbillsException {
		String txnNo = CommonUtil.getTxnNo(context);
		Map<String,Object> mapParam = new HashMap<String,Object>();
		mapParam.put("txnNo", txnNo);
		EbpDao gjDao = new EbpDao();
		gjDao.deleteRow(INVOICE_DATA_ID, EbpConstants.TABLE_TP, mapParam);
		
	}

	@Override
	public void doHandleAfterScript(Context context) throws EbillsException {
		try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			try {
				EbpDao dao = new EbpDao();
				Map<String,Object> condition=new HashMap<String,Object>();
				List<Object> inputList=new LinkedList<Object>();
				String bizNo=(String)context.get("butxn_curtBizNo");
				condition.put("bizNo", bizNo);
				inputList.add(bizNo);
			    String delfosql="delete from INVOICEINFOAR where bizNo=? ";
			    String delARsql="delete from INVOICEINFOFO where bizNo=? ";
			   // String delTPsql="delete from INVOICEINFOAR where bizNo=? ";
				dao.execute(delfosql, inputList);
				dao.execute(delARsql, inputList);
				List<Map<String,Object>> allData = (List<Map<String,Object>>)context.get(ComponentConfig.getContextKey(CFF_KEY));
				List<Map<String,Object>> insertTmp = new LinkedList<Map<String,Object>> ();
				if(allData == null || allData.isEmpty())return ;
				for(Map<String,Object> oneChg : allData){
						oneChg.put("bizNo", context.getValue(EbpConstants.TRANSACTION_BIZNO));
						insertTmp.add(oneChg);
				}
				dao.insertRow(INVOICE_DATA_ID, EbpConstants.TABLE_TP, insertTmp);
			} catch (Exception e) {
				cm.setRollbackOnly();
			}finally{
				cm.commit();
			}
			
		} catch (EbillsException e1) {
			e1.printStackTrace();
		}
		
	}

	@Override
	public void doProcessFinishAfterScript(Context context) throws EbillsException {
		try {
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			cm.startTransaction(true);
			try {
				EbpDao dao = new EbpDao();
				Map<String,Object> condition=new HashMap<String,Object>();
				List<Object> inputList=new LinkedList<Object>();
				String bizNo=(String)context.get("butxn_curtBizNo");
				condition.put("bizNo", bizNo);
				inputList.add(bizNo);
			    String infosql="insert into INVOICEINFOFO (select * from INVOICEINFOTP where bizNo=?) ";
			    String inARsql="insert into INVOICEINFOAR (select * from INVOICEINFOTP where bizNo=?) ";
			    String delTPsql="delete from INVOICEINFOTP where bizNo=? ";
				dao.execute(infosql, inputList);
				dao.execute(inARsql, inputList);
				dao.execute(delTPsql, inputList);
				//dao.deleteRow(INVOICE_DATA_ID, GjjsConstants.TABLE_TP, condition);
			} catch (Exception e) {
				cm.setRollbackOnly();
			}finally{
				cm.commit();
			}
			
		} catch (EbillsException e1) {
			e1.printStackTrace();
		}
		
	}


	@Override
	public void doSuspendAfterScript(Context arg0) throws EbillsException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initData(Map<String, Object> context)
			throws EbillsException {
		log.info("invoiceKey ------------->>>"+contextKey);
		String invoiceJson = (String)CommonUtil.getValFromContextMap(context, contextKey);
		System.out.println(CommonUtil.getValFromContextMap(context, contextKey));
		String txnNo = (String)context.get(EbpConstants.TXNSERIALNO);
		log.info("invoiceJson ------------->>>"+invoiceJson);
		log.info("txnNo ------------->>>"+txnNo);
		//Object obj[] =null;
		if(invoiceJson==null||"".equals(invoiceJson.trim())){
			log.debug(1,new String[]{"invoiceJson no data!"});
			return  ;
			/*Map<String,JSONArray> map = CommonUtil.JsonToMap(dgDepositBase);
			JSONArray rows = ((JSONArray) map.get("rows"));
		    obj= rows.toArray();
			log.info("obj ------------->>>"+obj[0]);
			 if(obj.length==0){
					log.debug(1,new String[]{"---------->>>DgDepositInfo no data!"});
					return ;
			 }*/
		}

		Object obj[] = CommonUtil.JsonToList(invoiceJson);
		List<Map<String,Object>> lAddInfo  = new LinkedList<Map<String,Object>>();
		if(null != obj && obj.length >0){
			for(int i = 0 ; i < obj.length ; i ++){
				JSONObject json = (JSONObject)obj[i];
				Map<String,Object> oneChg = CommonUtil.JsonToMap(json.toString()) ;
				oneChg.put("txnNo", (String)context.get(EbpConstants.TXNSERIALNO));
				lAddInfo.add(oneChg);
			}
		}
		if(context.containsKey(contextKey)){
			context.remove(contextKey);
		}
		context.put(contextKey, lAddInfo);
	}

	@Override
	public void formatData(Map<String, Object> arg0, List<String> arg1)
			throws EbillsException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void doHandleBeforeScript(Context context) throws EbillsException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doCorrectBeforeScript(Context context) throws EbillsException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doCheckBeforeScript(Context context) throws EbillsException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doCheckAfterScript(Context context) throws EbillsException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doAuthBeforeScript(Context context) throws EbillsException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doAuthAfterScript(Context context) throws EbillsException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doProcessFinishBeforeScript(Context context)
			throws EbillsException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doDeleteBeforeScript(Context context) throws EbillsException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isNeedRun(Context context) throws EbillsException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Map<String, Object>> getAjaxInfo(Context context)
			throws EbillsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void doRollBackAfterScript(String oldTxnNo, Context context)
			throws EbillsException {
		// TODO Auto-generated method stub
		
	}

	
}
