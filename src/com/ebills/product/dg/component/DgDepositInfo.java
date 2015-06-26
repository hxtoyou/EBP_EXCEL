package com.ebills.product.dg.component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.bussprocess.context.Context;

import com.alibaba.fastjson.JSONObject;
import com.ebills.commons.BusinessComponentAbstract;
import com.ebills.commons.ComponentConfig;
import com.ebills.commons.RollBack;
import com.ebills.util.EbillsException;
import com.ebills.util.EbillsLog;
import com.ebills.utils.CommonUtil;
import com.ebills.utils.EbpConstants;
import com.ebills.utils.EbpDao;

/**
 * 保证金组件
 * @author zhangQi
 *
 */
public class DgDepositInfo extends BusinessComponentAbstract {
	private static String className = DgDepositInfo.class.getName();
	private static EbillsLog log = new EbillsLog(className);
	private static String dePositInfo = "DGMGRINFO"; 			//表名
	private static String cffKey ="module_dgdepositBase";
	
	

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
			String tradeno = (String) context.getValue(EbpConstants.TRANSACTION_TRADENO);
			log.info("tradeno ------------->>>"+tradeno);
			if("050115".equals(tradeno)) return;
			EbpDao gjDao = new EbpDao();
			Map<String,Object> mapParam = new HashMap<String,Object>();
			mapParam.put("txnNo", (String)context.getValue(EbpConstants.TXNSERIALNO));
	
			//删除临时表
			gjDao.deleteRow(dePositInfo, EbpConstants.TABLE_TP, mapParam);
		
			List<Map<String,Object>> allData = (List<Map<String,Object>>)context.get(ComponentConfig.getContextKey(cffKey));
			List<Map<String,Object>> insertTmp = new LinkedList<Map<String,Object>> ();
			if(allData == null || allData.isEmpty())return ;
			for(Map<String,Object> oneChg : allData){
					oneChg.put("bizNo", context.getValue(EbpConstants.TRANSACTION_BIZNO));
					insertTmp.add(oneChg);
			}
			gjDao.insertRow(dePositInfo, EbpConstants.TABLE_TP, insertTmp);
		
	
	} catch (EbillsException ex) {
			throw ex;
		}  catch (Exception e) {
			throw new EbillsException(e,className,2,null,null) ;
		}

	}

	@Override
	public void doProcessFinishAfterScript(Context context) throws EbillsException {
		String txnNo = CommonUtil.getTxnNo(context);
		EbpDao gjDao = new EbpDao();
		Map<String,Object> mapParam = new HashMap<String,Object>();
		mapParam.put("txnNo", txnNo);
		gjDao.archiveByDataId(dePositInfo, mapParam);
//		gjDao.archiveByDataId(dePositInfo,mapParam,"FO");
		//删除临时表
		gjDao.deleteRow(dePositInfo, EbpConstants.TABLE_TP, mapParam);
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
	public void initData(Map<String, Object> context)throws EbillsException {
	
		try{
			log.info("DgDepositBase ------------->>>"+contextKey);
			String dgDepositBase = (String)CommonUtil.getValFromContextMap(context, contextKey);
			log.info("dgDepositBase ------------->>>"+dgDepositBase);
			if(dgDepositBase != null){
				log.info("length------->"+dgDepositBase.trim().length());
			}
			//前台未添加保证金面板的值时，传到后台的为“”长度为2
			if(dgDepositBase ==null  || "".equals(dgDepositBase.trim())||dgDepositBase.trim().length()==2){
				log.debug(1,new String[]{"dgDepositBase no data!"});
				return;
			}
			Object obj[] = CommonUtil.JsonToList(dgDepositBase);
			List<Map<String,Object>> chList  = new LinkedList<Map<String,Object>>();
			if(null != obj && obj.length >0){
				for(int i = 0 ; i < obj.length ; i ++){
					JSONObject json = (JSONObject)obj[i];
					Map<String,Object> oneChg = CommonUtil.JsonToMap(json.toString()) ;
					String BailInfoID = (String)oneChg.get("BailInfoID");
					
					if(null == BailInfoID || "".equals(BailInfoID.trim())){
						oneChg.put("BailInfoID", getSerialNo());
						oneChg.put("txnNo", (String)context.get(EbpConstants.TXNSERIALNO));
					}
					chList.add(oneChg);
				}
			}
			if(context.containsKey(contextKey)){
				context.remove(contextKey);
			}
			context.put(contextKey, chList);
		}catch(EbillsException ex){
			throw ex;
		}  catch (Exception e) {
			throw new EbillsException(e,className,2,null,null) ;
		}
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
	

}

