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
public class DgDepositMgrInfo extends BusinessComponentAbstract {
	private static String className = DgDepositInfo.class.getName();
	private static EbillsLog log = new EbillsLog(className);
	private static String dePositInfo = "DGMGRINFO"; 			//表名
	private static String cffKey ="module_dgdepositMgr";
	
	@Override
	public void doCorrectAfterScript(Context context) throws EbillsException {
		this.doHandleAfterScript(context);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doDeleteAfterScript(Context context) throws EbillsException {
		List<Map<String,Object>> allData = (List<Map<String,Object>>)context.get(ComponentConfig.getContextKey(cffKey));
		String bizNo = (String) allData.get(0).get("bizNo");
		Map<String,Object> mapParam = new HashMap<String,Object>();
		mapParam.put("bizNo", bizNo);
		EbpDao gjDao = new EbpDao();
		gjDao.deleteRow(dePositInfo, EbpConstants.TABLE_TP, mapParam);
	}
	   
	@SuppressWarnings("unchecked")
	@Override
	public void doHandleAfterScript(Context context) throws EbillsException {
		
		try {
			String bizNo ="";
			EbpDao gjDao = new EbpDao();
			List<Map<String,Object>> allData = (List<Map<String,Object>>)context.get(ComponentConfig.getContextKey(cffKey));
			List<Map<String,Object>> insertTmp = new LinkedList<Map<String,Object>> ();
			if(allData == null || allData.isEmpty())return ;
			for(Map<String,Object> oneChg : allData){
					insertTmp.add(oneChg);
			}
			bizNo = (String) allData.get(0).get("bizNo");
			log.info("delete rows's bizNo is ->"+bizNo);
			Map<String,Object> mapParam = new HashMap<String,Object>();
			mapParam.put("bizNo", bizNo);
			//删除临时表
			gjDao.deleteRow(dePositInfo, EbpConstants.TABLE_TP, mapParam);
			
			gjDao.insertRow(dePositInfo, EbpConstants.TABLE_TP, insertTmp);
	} catch (EbillsException ex) {
			throw ex;
		}  catch (Exception e) {
			throw new EbillsException(e,className,2,null,null) ;
		}

	}

	@Override
	public void doProcessFinishAfterScript(Context context) throws EbillsException {
		processFinishing(context);
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
			log.info("DgDepositMgr ------------->>>"+contextKey);
			String DgDepositMgr = (String)CommonUtil.getValFromContextMap(context, contextKey);
			log.info("DgDepositMgr ------------->>>"+DgDepositMgr);
			if(DgDepositMgr != null){
				log.info("length------->"+DgDepositMgr.trim().length());
			}
			//前台未添加保证金面板的值时，传到后台的为“”长度为2
			if(DgDepositMgr ==null  || "".equals(DgDepositMgr.trim())||DgDepositMgr.trim().length()==2){
			log.debug("coming!");
				log.debug(1,new String[]{"dgDepositMgr no data!"});
				return;
			}
			Object obj[] = CommonUtil.JsonToList(DgDepositMgr);
			List<Map<String,Object>> chList  = new LinkedList<Map<String,Object>>();
			if(null != obj && obj.length >0){
				for(int i = 0 ; i < obj.length ; i ++){
					JSONObject json = (JSONObject)obj[i];
					Map<String,Object> oneChg = CommonUtil.JsonToMap(json.toString()) ;
					String BailInfoID = (String)oneChg.get("BailInfoID");	
					if(null == BailInfoID || "".equals(BailInfoID.trim())){
						oneChg.put("BailInfoID", getSerialNo());
					}
					oneChg.put("txnNo", (String)context.get(EbpConstants.TXNSERIALNO));
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
	
	
	/**
	 * 正在迁移
	 * @param context
	 * @throws EbillsException 
	 */
	private void processFinishing(Context context) throws EbillsException {
		EbpDao gjDao = new EbpDao();
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> allData = (List<Map<String,Object>>)context.get(ComponentConfig.getContextKey(cffKey));
		log.info("allData ------------->>>"+allData);
		List<Map<String,Object>> insertTmp = new LinkedList<Map<String,Object>> ();
		if(allData == null || allData.isEmpty())return ;
		for(Map<String,Object> oneChg : allData){
				String zjFlag  = (String)oneChg.get("zjFlag");
				String zjAmt = (String)oneChg.get("zjAmt");
				String bailAmt = (String)oneChg.get("bailAmt");
				String bailCur = (String)oneChg.get("bailCur");
				log.info("zjFlag ------------->>>"+zjFlag);
				log.info("zjAmt ------------->>>"+zjAmt);
				log.info("bailAmt ------------->>>"+bailAmt);
				log.info("bailCur ------------->>>"+bailCur);
				if("+".equals(zjFlag)&&zjAmt!=null&&zjAmt.length()>0&&bailAmt!=null&&bailAmt.length()>0){
					float zjamt = Float.valueOf(zjAmt);
					float bailamt = Float.valueOf(bailAmt);
					bailamt +=zjamt;
					if("JPY".equals(bailCur)){
						bailamt = Math.round(bailamt);	
					}
					oneChg.put("bailAmt", bailamt);
				}else if("-".equals(zjFlag)&&zjAmt.length()>0&&bailAmt!=null&&bailAmt.length()>0){
					float zjamt = Float.valueOf(zjAmt);
					float bailamt = Float.valueOf(bailAmt);
					bailamt -=zjamt;
					if("JPY".equals(bailCur)){
						bailamt = Math.round(bailamt);	
					}
					log.info("bailamt ------------->>>"+bailamt);
					oneChg.put("bailAmt", bailamt);
				}
				oneChg.put("zjFlag", "");
				oneChg.put("zjAmt", "");
				insertTmp.add(oneChg);
		}
		String bizNo = (String) allData.get(0).get("bizNo");
		log.info("delete rows's bizNo is ->"+bizNo);
		Map<String,Object> mapParam = new HashMap<String,Object>();
		mapParam.put("bizNo", bizNo);
		//删除TP表和AR表内原本数据
		gjDao.deleteRow(dePositInfo, EbpConstants.TABLE_TP, mapParam);
		gjDao.deleteRow(dePositInfo, EbpConstants.TABLE_AR, mapParam);
		
		gjDao.insertRow(dePositInfo, EbpConstants.TABLE_AR, insertTmp);
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

