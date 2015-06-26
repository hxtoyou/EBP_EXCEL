package com.ebills.product.dg.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.bussprocess.context.Context;

import com.alibaba.fastjson.JSONObject;
import com.ebills.commons.BusinessComponentAbstract;
import com.ebills.commons.ComponentConfig;
import com.ebills.commons.RollBack;
import com.ebills.param.newtask.NewTaskFactory;
import com.ebills.product.dg.commons.Commons;
import com.ebills.util.EbillsException;
import com.ebills.util.EbillsLog;
import com.ebills.utils.CommonUtil;
import com.ebills.utils.EbpConstants;
import com.ebills.utils.EbpDao;

public class DgNoteInfo extends BusinessComponentAbstract{
	private static String className = DgNoteInfo.class.getName();
	private static EbillsLog log = new EbillsLog(className);
	private static String dePositInfo = "NOTE"; 			//表名
	private static String cffKey ="module_note";
	
	

	@Override
	public void doCorrectAfterScript(Context context) throws EbillsException {
		this.doHandleAfterScript(context);
	}

	@Override
	public void doDeleteAfterScript(Context context) throws EbillsException {
		String txnNo = CommonUtil.getTxnNo(context);
		Map<String,Object> mapParam = new HashMap<String,Object>();
		mapParam.put("txnNo", txnNo);
		EbpDao gjDao = new EbpDao();
		gjDao.deleteRow(dePositInfo, EbpConstants.TABLE_TP, mapParam);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void doHandleAfterScript(Context context) throws EbillsException {
	try {
			
			EbpDao gjDao = new EbpDao();
			Map<String,Object> mapParam = new HashMap<String,Object>();
			mapParam.put("txnNo", (String)context.getValue(EbpConstants.TXNSERIALNO));
			
			//String txnNo=(String)context.getValue(GjjsConstants.TXNSERIALNO);
			
			//删除临时表
			gjDao.deleteRow(dePositInfo, EbpConstants.TABLE_TP, mapParam);
			
			List<Map<String,Object>> allData = (List<Map<String,Object>>)context.get(ComponentConfig.getContextKey(cffKey));
			List<Map<String,Object>> insertTmp = new LinkedList<Map<String,Object>> ();
			if(allData == null || allData.isEmpty())return ;
			for(Map<String,Object> oneChg : allData){
					oneChg.put("BIZNO", context.getValue(EbpConstants.TRANSACTION_BIZNO));
					insertTmp.add(oneChg);
			}
			gjDao.insertRow(dePositInfo, EbpConstants.TABLE_TP, insertTmp);
			
			/*List<Object> paramList  = new ArrayList<Object>();
			
			paramList.add(0,txnNo);
			
			String sql = "select txnNo  from NOTEFO where draftno in (select draftno from NOTETP where txnNo=?)";
			
	        List<Map<String,Object>> queryRet = gjDao.sqlQuery(sql, paramList);
	        
	        
	        System.out.println("======================="+queryRet.size());
	        
	        if(queryRet.size()>0){
	            
		        String txnNo1=(String)queryRet.get(0).get("txnNo");
		        
		        Map<String,Object> mapParam1 = new HashMap<String,Object>();
				mapParam1.put("txnNo", txnNo1);
		
				gjDao.deleteRow(dePositInfo, GjjsConstants.TABLE_INFO, mapParam1);
			
	        }*/
		
	
	} catch (EbillsException ex) {
			throw ex;
		}  catch (Exception e) {
			throw new EbillsException(e,className,2,null,null) ;
		}

	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public void doProcessFinishAfterScript(Context context) throws EbillsException {
		String txnNo = CommonUtil.getTxnNo(context);
		EbpDao gjDao = new EbpDao();
		Map<String,Object> mapParam = new HashMap<String,Object>();
		mapParam.put("txnNo", txnNo);
		List<Object> paramList  = new ArrayList<Object>();
		
		paramList.add(0,txnNo);
		
		String sql = "select txnNo,deal  from NOTEFO where draftno in (select draftno from NOTETP where txnNo=?)";
		
        List<Map<String,Object>> queryRet = gjDao.sqlQuery(sql, paramList);
        
        System.out.println("======================="+queryRet.size());
        
        String tradeno=(String)context.get("tradeNo");
        if(queryRet.size()>0){
	        if("050903".equals(tradeno)){
	            
		        String txnNo1=(String)queryRet.get(0).get("TXNNO");
		        
		        Map<String,Object> mapParam1 = new HashMap<String,Object>();
				mapParam1.put("txnNo", txnNo1);
				
		
				gjDao.deleteRow(dePositInfo, EbpConstants.TABLE_INFO, mapParam1);
			
	        }else if("050904".equals(tradeno)){
	        	String txnNo1=(String)queryRet.get(0).get("TXNNO");
	        	String deal=(String)queryRet.get(0).get("DEAL");
		        
		        Map<String,Object> mapParam1 = new HashMap<String,Object>();
				mapParam1.put("txnNo", txnNo1);
				mapParam1.put("DEAL", deal);
				gjDao.deleteRow(dePositInfo, EbpConstants.TABLE_INFO, mapParam1);
	        }
        }
		//gjDao.archiveByDataId(dePositInfo, mapParam);
		gjDao.archiveByDataId(dePositInfo,mapParam,"FO");
		//删除临时表
		gjDao.deleteRow(dePositInfo, EbpConstants.TABLE_TP, mapParam);
		
		List<Map<String,Object>> obj=(List<Map<String,Object>>)context.get("xcpNote");
		String orgno=(String)context.get("butxn_tranOrgNo");
		if("050903".equals(tradeno)||"050904".equals(tradeno)){
			for(int i = 0 ; i < obj.size() ; i ++){
				Map<String,Object> info=obj.get(i);
				String deal=(String)info.get("DEAL");
				String draftno=(String)info.get("DRAFTNO");
				List<Object> paramList1  = new ArrayList<Object>();
				paramList1.add(0,draftno);
				String sql1 = "select yesOrNo from draftregfo where draftno=?";
		        List<Map<String,Object>> queryRet1 = gjDao.sqlQuery(sql1,paramList1);
		        String yesOrNo=(String)queryRet1.get(0).get("YESORNO");
				int amt=(Integer)info.get("AMT");
				String cur=(String)info.get("CUR");
				Map<String,Object> task =new HashMap<String,Object>();
				task.put("bizNo", draftno);
				task.put("tranCur", cur);
				task.put("tranAmt", amt);
				task.put("startModeId", "9");
				task.put("startDate", Commons.getWorkDate());
				task.put("orgNo", orgno);
				if("收汇".equals(deal)){
				    if("N".equals(yesOrNo)){
					    task.put("tradeNo", "050905");
					    NewTaskFactory.createNewTask(task);
				    }else{
				    	task.put("tradeNo", "050907");
						NewTaskFactory.createNewTask(task);
				    }
				}else if("退票".equals(deal)){
					task.put("tradeNo", "050906");
					NewTaskFactory.createNewTask(task);
				}else{
					System.out.println(draftno+",该光票编号操作处理为无");
				}
			}
		}
	}

	@Override
	public void doRollBackAfterScript(String oldTxnNo, Context context) throws EbillsException {
		RollBack.rollBackProcess(oldTxnNo, dePositInfo);
	}

	@Override
	public void doSuspendAfterScript(Context context) throws EbillsException {
	
	}
	
	/**
	 * 格式化数据
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	public void initData(Map<String, Object> context)
			throws EbillsException {
		log.info("DgDepositBase ------------->>>"+contextKey);
		String dgDepositBase = (String)CommonUtil.getValFromContextMap(context, contextKey);
		String txnNo = (String)context.get(EbpConstants.TXNSERIALNO);
		log.info("DgDepositBase ------------->>>"+dgDepositBase);
		log.info("txnNo ------------->>>"+txnNo);
		//Object obj[] =null;
		if(dgDepositBase==null||"\"\"".equals(dgDepositBase.trim())){
			log.debug(1,new String[]{"dgDepositBase no data!"});
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
		Object obj[] = CommonUtil.JsonToList(dgDepositBase);
		List<Map<String,Object>> lAddInfo  = new LinkedList<Map<String,Object>>();
		if(null != obj && obj.length >0){
			for(int i = 0 ; i < obj.length ; i ++){
				JSONObject json = (JSONObject)obj[i];
				Map<String,Object> oneChg = CommonUtil.JsonToMap(json.toString()) ;
				oneChg.put("txnNo", (String)context.get(EbpConstants.TXNSERIALNO));
				//oneChg.put("BailInfoID", getSerialNo());
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
	
	/**
	 * 保证金业务编号产生规则
	 * @return
	 * @throws EbillsException
	 *//*
	private static String getSerialNo()throws EbillsException {
		SerialNoFactory snf =  new SerialNoFactory();
		String serialNo = snf.getSerialNo("bufndfo",10);
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		String ymd = Integer.toString(year + month  + day);
		return "DEP"+ymd+serialNo;
	}*/
}
