package com.ebills.product.dg.component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.bussprocess.context.Context;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.ebills.util.EbillsException;
import com.ebills.commons.BusinessComponentAbstract;
import com.ebills.utils.CommonUtil;

public class DgManualVouch extends BusinessComponentAbstract {

	@Override
	public void doHandleAfterScript(Context context) throws EbillsException {
		// TODO Auto-generated method stub

	}

	@Override
	public void initData(Map<String, Object> context)throws EbillsException {
		String val = (String)CommonUtil.getValFromContextMap(context, contextKey);
		if(StringUtils.isNotEmpty(val)){
			String manualVocher = val;
			List<Map<String, Object>> manualVocherList = parseManualVocher(manualVocher);
			if(context.containsKey(contextKey)){
				context.remove(contextKey);
			}
			context.put(contextKey,manualVocherList);
		}

	}
	@SuppressWarnings("unchecked")
	private  List<Map<String,Object>> parseManualVocher(String manualVocher) throws EbillsException{
		if(StringUtils.isEmpty(manualVocher) || "\"\"".equals(manualVocher)){
			return new LinkedList<Map<String,Object>>();
		}
		Object obj[] = CommonUtil.JsonToList(manualVocher);
		List<Map<String,Object>> all  = new LinkedList<Map<String,Object>>();
		if(null != obj && obj.length >0){
			for(int i = 0 ; i < obj.length ; i ++){
				JSONObject json = (JSONObject)obj[i];
				Map<String,Object> vouInfo = CommonUtil.JsonToMap(json.toString()) ;
				if(vouInfo.containsKey("amount") && vouInfo.get("amount") != null){
					Object amount = vouInfo.get("amount");
					if(amount instanceof String){
						if(StringUtils.isEmpty((String)amount)){
							return new LinkedList<Map<String,Object>>();
						}
						vouInfo.put("amount", Double.parseDouble((String)amount));
					} else if(amount instanceof Integer){
						vouInfo.put("amount", new Double((Integer)amount));
					}
				}
				if(vouInfo.containsKey("buyPrice") && vouInfo.get("buyPrice") != null){
					Object buyPrice = vouInfo.get("buyPrice");
					if(buyPrice instanceof String){
						vouInfo.put("buyPrice", (String)buyPrice);
					} else if(buyPrice instanceof Integer){
						vouInfo.put("buyPrice",String.valueOf((Integer)buyPrice));
					}
				}
				all.add(vouInfo);
			}
		}
		return all;
	}
	
	@Override
	public void doCorrectAfterScript(Context context) throws EbillsException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doProcessFinishAfterScript(Context context) throws EbillsException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doDeleteAfterScript(Context context) throws EbillsException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doRollBackAfterScript(String oldTxnNo, Context context) throws EbillsException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doSuspendAfterScript(Context context) throws EbillsException {
		// TODO Auto-generated method stub

	}

	@Override
	public void formatData(Map<String, Object> context, List<String> mvKey)
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

}
