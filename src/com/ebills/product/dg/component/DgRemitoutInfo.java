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
import com.ebills.script.BaseScript;
import com.ebills.util.EbillsException;
import com.ebills.util.EbillsLog;
import com.ebills.utils.CommonUtil;
import com.ebills.utils.EbpConstants;
import com.ebills.utils.EbpDao;

public class DgRemitoutInfo extends BusinessComponentAbstract{
	private static String className = DgRemitoutInfo.class.getName();
	private static EbillsLog log = new EbillsLog(className);
	private static String dePositInfo = "REMITOUTSERY"; 			//表名
	private static String cffKey ="module_remitout";
	
	

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
					String orgNo = (String)oneChg.get("EXEORG");
					BaseScript bs=new BaseScript();
					orgNo=bs.getTableValue("orgno", "paorg", "orgcode='"+orgNo+"'");
					String bizNo=getSerialNo(orgNo);
					oneChg.put("REMITOUTNO", bizNo);
					insertTmp.add(oneChg);
			}
			gjDao.insertRow(dePositInfo, EbpConstants.TABLE_TP, insertTmp);
			
		
	
	} catch (EbillsException ex) {
			throw ex;
		}  catch (Exception e) {
			throw new EbillsException(e,className,2,null,null) ;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public void doProcessFinishAfterScript(Context context) throws EbillsException {
		String txnNo = CommonUtil.getTxnNo(context);
		EbpDao gjDao = new EbpDao();
		Map<String,Object> mapParam = new HashMap<String,Object>();
		mapParam.put("txnNo", txnNo);
		List<Object> paramList  = new ArrayList<Object>();
		
		paramList.add(0,txnNo);
		
		String sql = "select txnNo  from REMITOUTSERYFO where REMITOUTNO in (select REMITOUTNO from REMITOUTSERYTP where txnNo=?)";
		
        List<Map<String,Object>> queryRet = gjDao.sqlQuery(sql, paramList);
        
        System.out.println("======================="+queryRet.size());
        
        if(queryRet.size()>0){
        
	            
		String txnNo1=(String)queryRet.get(0).get("TXNNO");
		        
		Map<String,Object> mapParam1 = new HashMap<String,Object>();
        mapParam1.put("txnNo", txnNo1);
		
		gjDao.deleteRow(dePositInfo, EbpConstants.TABLE_INFO, mapParam1);
		
        }
			
		//gjDao.archiveByDataId(dePositInfo, mapParam);
		gjDao.archiveByDataId(dePositInfo,mapParam,"FO");
		//删除临时表
		gjDao.deleteRow(dePositInfo, EbpConstants.TABLE_TP, mapParam);
		
		List<Map<String,Object>> obj=(List<Map<String,Object>>)context.get("xcpRemitout");
		//String orgno=(String)context.get("butxn_tranOrgNo");
			for(int i = 0 ; i < obj.size() ; i ++){
				Map<String,Object> info=obj.get(i);
				double amt=Float.parseFloat((String)info.get("REMITAMT"));
				String cur=(String)info.get("REMITCUR");
				String bizNo=(String)info.get("REMITOUTNO");
				String orgno=(String)info.get("EXEORG");
				BaseScript bs=new BaseScript();
				orgno=bs.getTableValue("orgno", "paorg", "orgcode='"+orgno+"'");
				Map<String,Object> task =new HashMap<String,Object>();
				task.put("fileNo", bizNo);
				task.put("tranCur", cur);
				task.put("tranAmt", amt);
				task.put("startModeId", "9");
				task.put("startDate", Commons.getWorkDate());
				task.put("orgNo", orgno);
				task.put("tradeNo", "070202");
				NewTaskFactory.createNewTask(task);
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
		if(dgDepositBase == null||"\"\"".equals(dgDepositBase.trim())){
			log.debug(1,new String[]{"dgDepositBase no data!"});
			return  ;
		}
		Object obj[] = CommonUtil.JsonToList(dgDepositBase);
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

	}
	
	/**
	 * 汇出汇款业务编号产生
	 * @return
	 * @throws EbillsException
	 */
	private static String getSerialNo(String orgNo)throws EbillsException {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("butxn_tradeNo","070202");
		map.put("butxn_tranOrgNo", orgNo);
//		String bizNo=TradeCodeInfo.getCurrentBizNO(map);
		return "";
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
}
