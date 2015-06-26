package com.ebills.product.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebills.wf.dao.ProcessDAO;

/**
 * 多级审批
 * @author Pactera
 */
public class AuthHelp {
	/**
	 * 下一步授权。A:一级授权；B：二级授权；C：三级授权
	 */
	private static final String AUTH_NEXTSTEP = "NextStep";
	
	/**
	 * 授权分级
	 */
	private static final String[] AUTH_STEP = new String[]{"A", "A@@B", "A@@B@@C"};
	
	/**
	 * 已经授权的步骤。A@@B@@C
	 */
	private static final String AUTH_DONESTEPQUEUE = "DoneStepQueue";
	
	/**
	 * 需要审批的序列。A@@B@@C@@D
	 */
	public static final String AUTH_ALLSTEPQUEUE = "AllStepQueue";
	
	/**
	 * 已经授权的用户。1234@@3456@@8972
	 */
	public static final String AUTH_DONESTEPUSERS = "doneStepUsers";
	
	/**
	 * 复核后首次设置下一步授权和已经授权的用户及步骤
	 * @param txnNo 交易流水号
	 * @return 
	 */
	public static Map<String, String> getFirst(String txnNo){
		Map<String, String> vars = new HashMap<String, String>();
		String NextStep = "";
		String AllStepQueue = "";
		
		try{
			String orgNo = "";//机构
			String tradeNo = "";//交易
			String pdtNo = "";//产品
			String cur = "";//交易币种
			double amt = 0.0d;//交易金额
			String limitCur = "";//审批币种
			double limitAmt = 0.0d;//审批金额
			String rang = "";//审批范围
			
			//0.数据访问器
			ProcessDAO dao = new ProcessDAO();
			
			//1.获取业务数据，当前交易的业务币种和金额
			List<Object> params = new ArrayList<Object>();
			params.add(txnNo);
			String sql = "select tranorgno, trancur, tranamt, tradeno, pdtno from butxntp where txnno = ?";
			List<Map<String, Object>> result = dao.sqlQuery(sql, params);
			if( result != null && result.size() > 0 ){
				try{
					Map<String, Object> res0 = result.get(0);
					orgNo = String.valueOf(res0.get("tranorgno"));
					tradeNo = String.valueOf(res0.get("tradeno"));
					pdtNo = String.valueOf(res0.get("pdtno"));
					cur = String.valueOf(res0.get("trancur"));
					amt = ((BigDecimal)res0.get("tranamt")).doubleValue();
				}catch(Exception e){
					Logger.getLogger(AuthHelp.class.getName()).error(e.getMessage(), e);
				}
			}
			
			//2.获取审批配置，如果未配置，不走授权
			params.clear();
			params.add(orgNo);
			params.add(tradeNo);
			params.add(pdtNo);
			sql = "select cur, amt, rang from bupmscfg where orgno = ? and tradeno = ? and ( pdtno = ? or pdtno = 'ALL' )";
			List<Map<String, Object>> result2 = dao.sqlQuery(sql, params);
			boolean hasCfg = false;
			if( result2 != null && result2.size() > 0 ){
				try{
					Map<String, Object> map = result2.get(0);
					limitCur = String.valueOf(map.get("cur"));
					limitAmt = ((BigDecimal)map.get("amt")).doubleValue();
					rang = String.valueOf(map.get("rang"));
					hasCfg = true;
				}catch(Exception e){
					Logger.getLogger(AuthHelp.class.getName()).error("AuthHelp getFirst Error:"+e.getMessage());
				}
			}
			
			//3.获取转换后的金额
			boolean bNeedAuth = false;
			double cur_amt = 0.0d;
			if( hasCfg ){
				QuotePriceUtil pu = new QuotePriceUtil();
				pu.setOrgNo(orgNo);
				cur_amt = pu.getPrice(cur, limitCur, amt);
				
				//交易是否要授权
				if( cur_amt >= limitAmt ){
					bNeedAuth = true;
				}
			}
			
			
			//需要审批的人、必须审批的人
			if( bNeedAuth && StringUtils.isNotEmpty(rang) ){
				//配置了多级
				if( StringUtils.isNotEmpty(rang) ){
					//授权范围对象
					List<AuthHelpRang> rangLst = new ArrayList<AuthHelpRang>();
					JSONArray jarr = (JSONArray)JSON.parse(rang);
					for(int i=0; i<jarr.size(); i++){
						JSONObject jobj = (JSONObject)jarr.get(i);
						String minamt = (String)jobj.get("minamt");
						minamt = minamt.replaceAll(",", "");
						String maxamt = (String)jobj.get("maxamt");
						maxamt = maxamt.replaceAll(",", "");
						
						double _minamt = Double.parseDouble(minamt);//最小金额
						double _maxamt = -1;//最大金额
						if( maxamt != null && !"".equals(maxamt) ){
							try {
								_maxamt = Double.parseDouble(maxamt);
							} catch (Exception e) {
							}
						}
						String rangus = "";
						rangLst.add( new AuthHelpRang(_minamt, _maxamt, rangus) );
					}
					
					//排序
					AuthHelpRang.sort(rangLst);
					
					//获得当前授权的用户
					AllStepQueue = "";
					for(int i=0; i<rangLst.size(); i++){
						/*if( cur_amt >= rangLst.get(i).getMinamt() ){
							if( StringUtils.isEmpty(NextStep) ){
								NextStep = AUTH_STEP[i];
							}
							AllStepQueue += ("".equals(AllStepQueue) ? "" : "@@") +AUTH_STEP[i];
						}*/
						if( cur_amt >= rangLst.get(i).getMinamt() && ( rangLst.get(i).getMaxamt() == -1 || cur_amt <= rangLst.get(i).getMaxamt() ) ){
							AllStepQueue = AUTH_STEP[i];
							NextStep = "A";
							break;
						}
					}
				}
				
				//未配置多级，只需要一级授权
				else{
					NextStep = "A";
					AllStepQueue = "A";
				}
			}
		}catch(Exception e){
			Logger.getLogger(AuthHelp.class.getName()).error("AuthHelp getFirst Error:"+e.getMessage(), e);
		}
		
		vars.put(AUTH_NEXTSTEP, NextStep);//下一个授权的步骤
		vars.put(AUTH_DONESTEPQUEUE, "");//已经授权的步骤
		vars.put(AUTH_ALLSTEPQUEUE, AllStepQueue);//需要授权的步骤
		vars.put(AUTH_DONESTEPUSERS, "");//已经授权的用户
		return vars;
	}
	
	/**
	 * @param txnNo 交易流水
	 * @return
	 */
	public static Map<String, String> getNext(String txnNo, Map<String, String> vars){
		String NextStep = "";
		String DoneStepQueue = "";
		String AllStepQueue = "";
		String DoneStepUsers = "";
		
		try{
			String curUserid = vars.get("wf_userId");
			
			//上一次授权步骤
			NextStep = vars.get(AUTH_NEXTSTEP);
			
			//需要授权用户
			AllStepQueue = vars.get(AUTH_ALLSTEPQUEUE);
			
			//已授权步骤
			DoneStepQueue = vars.get(AUTH_DONESTEPQUEUE);
			DoneStepQueue += ("".equals(DoneStepQueue)? "" : "@@")+NextStep;
			
			//已授权的用户
			DoneStepUsers = vars.get(AUTH_DONESTEPUSERS);
			DoneStepUsers += ("".equals(DoneStepUsers)? "" : "@@")+curUserid;
			
			//计算下一次授权的步骤
			if( AllStepQueue.equals(DoneStepQueue) ){
				NextStep = "";
			}else{
				int idx = DoneStepQueue.length()+2;
				NextStep = AllStepQueue.substring(idx, idx+1);
			}
		}catch (Exception e){
			Logger.getLogger(AuthHelp.class.getName()).error("AuthHelp getNext Error:"+e.getMessage(), e);
		}
		
		vars.put(AUTH_NEXTSTEP, NextStep);//下一个授权的步骤
		vars.put(AUTH_DONESTEPQUEUE, DoneStepQueue);//已经授权的步骤
		vars.put(AUTH_ALLSTEPQUEUE, AllStepQueue);//需要授权的步骤
		vars.put(AUTH_DONESTEPUSERS, DoneStepUsers);//已经授权的用户
		return vars;
	}
	
	
	//STATIC VARS
	private static final String B_NEED_AUTH = "bNeedAuth";
	private static final String B_NEED_AUTH_USERS = "bNeedAuthUsers";
	private static final String B_NEED_AUTH_USERS_DONE = "bNeedAuthDoneUsers";
	private static final String B_NEED_AUTH_USERS_MUST = "bNeedAuthMustUsers";
	
	/**
	 * 复核完成后调用此方法，用于在工作流上下文中设置是否授权及如何授权的变量，这些变量用户Map的方式保存并返回:
	 * 变量bNeedAuth: true-需要授权,false-不需要授权
	 * 变量bNeedAuthUsers: 可以进行授权的用户ID，多个用@@分隔
	 * 变量bNeedAuthDoneUsers: 已经授权的用户ID，多个用@@分隔
	 * 变量bNeedAuthMustUsers: 必须授权的用户ID，多个分组用@@分隔，每个分组有多个用户ID用+连接
	 * @param txnNo 交易流水
	 * @return vars 如何授权的变量
	 */
	public static Map<String, String> getFirst3(String txnNo){
		Map<String, String> vars = new HashMap<String, String>();
		String bNeedAuth = "false";
		String bNeedAuthUsers = "";
		String bNeedAuthDoneUsers = "";
		String bNeedAuthMustUsers = "";
		
		try{
			String orgNo = "";//机构
			String tradeNo = "";//交易
			String pdtNo = "";//产品
			String cur = "";//币种
			double amt = 0.0d;//金额
			String limitCur = "";//审批币种
			double limitAmt = 0.0d;//审批金额
			String rang = "";//审批范围
			
			//0.数据访问器
			ProcessDAO dao = new ProcessDAO();
			
			//1.获取业务数据，当前交易的业务币种和金额
			List<Object> params = new ArrayList<Object>();
			params.add(txnNo);
			String sql = "select tranorgno, trancur, tranamt, tradeno, pdtno from butxntp where txnno = ?";
			List<Map<String, Object>> result = dao.sqlQuery(sql, params);
			if( result != null && result.size() > 0 ){
				try{
					Map<String, Object> res0 = result.get(0);
					orgNo = String.valueOf(res0.get("tranorgno"));
					tradeNo = String.valueOf(res0.get("tradeno"));
					pdtNo = String.valueOf(res0.get("pdtno"));
					cur = String.valueOf(res0.get("trancur"));
					amt = ((BigDecimal)res0.get("tranamt")).doubleValue();
				}catch(Exception e){
					Logger.getLogger(AuthHelp.class.getName()).error(e.getMessage(), e);
				}
			}
			
			//2.获取审批配置，如果未配置，不走授权
			params.clear();
			params.add(orgNo);
			params.add(tradeNo);
			params.add(pdtNo);
			sql = "select cur, amt, rang from bupmscfg where orgno = ? and tradeno = ? and ( pdtno = ? or pdtno = 'ALL' )";
			List<Map<String, Object>> result2 = dao.sqlQuery(sql, params);
			boolean hasCfg = false;
			if( result2 != null && result2.size() > 0 ){
				try{
					Map<String, Object> map = result2.get(0);
					limitCur = String.valueOf(map.get("cur"));
					limitAmt = ((BigDecimal)map.get("amt")).doubleValue();
					rang = String.valueOf(map.get("rang"));
					hasCfg = true;
				}catch(Exception e){
					Logger.getLogger(AuthHelp.class.getName()).error("AuthHelp getFirst Error:"+e.getMessage());
				}
			}
			
			//3.获取转换后的金额
			double cur_amt = 0.0d;
			QuotePriceUtil pu = new QuotePriceUtil();
			pu.setOrgNo(orgNo);
			cur_amt = pu.getPrice(cur, limitCur, amt);
			
			//4.交易是否要授权
			if( cur_amt >= limitAmt && hasCfg){
				bNeedAuth = "true";
			}
			
			//需要审批的人、必须审批的人
			if( "true".equals(bNeedAuth) && StringUtils.isNotEmpty(rang) ){
				//授权范围对象
				List<AuthHelpRang> rangLst = new ArrayList<AuthHelpRang>();
				JSONArray jarr = (JSONArray)JSON.parse(rang);
				for(int i=0; i<jarr.size(); i++){
					JSONObject jobj = (JSONObject)jarr.get(i);
					String minamt = (String)jobj.get("minamt");
					String maxamt = (String)jobj.get("maxamt");
					double _minamt = Double.parseDouble(minamt);//最小金额
					double _maxamt = -1;//最大金额
					if( maxamt != null && !"".equals(maxamt) ){
						try {
							_maxamt = Double.parseDouble(maxamt);
						} catch (Exception e) {
						}
					}
					String rangus = "";
					try {
						String _rangus = (String)jobj.get("rangus");
						if( StringUtils.isNotEmpty(_rangus) ){
							JSONArray _jarr2 = (JSONArray)JSON.parse(_rangus);
							for(int j=0; j<_jarr2.size(); j++){
								JSONObject _jobj2 = (JSONObject)_jarr2.get(j);
								rangus += ("".equals(rangus)? "" : "@@")+_jobj2.getString("userid");
							}
						}
					} catch (Exception e) {
					}
					rangLst.add( new AuthHelpRang(_minamt, _maxamt, rangus) );
				}
				
				//获得当前授权的用户
				AuthHelpRang cur_range = AuthHelpRang.getRange(cur_amt, rangLst, null);
				
				//可授权用户=必须授权用户
				if( cur_range != null && cur_range.getUsers() != null){
					bNeedAuthUsers = cur_range.getUsers();
					bNeedAuthMustUsers = cur_range.getUsers();
				}
			}
		}catch(Exception e){
			bNeedAuth = "false";
			Logger.getLogger(AuthHelp.class.getName()).error("AuthHelp getFirst Error:"+e.getMessage(), e);
		}
		
		vars.put(B_NEED_AUTH, bNeedAuth);
		vars.put(B_NEED_AUTH_USERS, bNeedAuthUsers);
		vars.put(B_NEED_AUTH_USERS_DONE, bNeedAuthDoneUsers);
		vars.put(B_NEED_AUTH_USERS_MUST, bNeedAuthMustUsers);
		return vars;
	}
	
	
	
	/**
	 * @param txnNo 交易流水
	 * @return
	 */
	public static Map<String, String> getNext3(String txnNo, Map<String, String> vars){
//		 * > 0
//		 * 0 - 1000     A
//		 * 1001- 2000   B,C
//		 * 2001- 3000   D,E
//		 * 3001- 4000   F,G
//		 * 
		String bNeedAuth = "false";
		String bNeedAuthUsers = "";
		String bNeedAuthDoneUsers = "";
		String bNeedAuthMustUsers = "";
		
		try{
			String orgNo = "";//机构
			String tradeNo = "";//交易
			String pdtNo = "";//产品
			String cur = "";//币种
			double amt = 0.0d;//金额
			String limitCur = "";//审批币种
			double limitAmt = 0.0d;//审批金额
			String rang = "";//审批范围
			
			//已授权用户
			bNeedAuthDoneUsers = vars.get(B_NEED_AUTH_USERS_DONE);
			String curUserid = vars.get("wf_userId");
			if( StringUtils.isEmpty(bNeedAuthDoneUsers) ){
				bNeedAuthDoneUsers = curUserid;
			}else{
				bNeedAuthDoneUsers = bNeedAuthDoneUsers+"@@"+curUserid;
			}
			
			//0.数据访问器
			ProcessDAO dao = new ProcessDAO();
			
			//1.获取业务数据，当前交易的业务币种和金额
			List<Object> params = new ArrayList<Object>();
			params.add(txnNo);
			String sql = "select tranorgno, trancur, tranamt, tradeno, pdtno from butxntp where txnno = ?";
			List<Map<String, Object>> result = dao.sqlQuery(sql, params);
			if( result != null && result.size() > 0 ){
				try{
					Map<String, Object> res0 = result.get(0);
					orgNo = String.valueOf(res0.get("tranorgno"));
					tradeNo = String.valueOf(res0.get("tradeno"));
					pdtNo = String.valueOf(res0.get("pdtno"));
					cur = String.valueOf(res0.get("trancur"));
					amt = ((BigDecimal)res0.get("tranamt")).doubleValue();
				}catch(Exception e){
					Logger.getLogger(AuthHelp.class.getName()).error(e.getMessage(), e);
				}
			}
			
			//2.获取审批配置，如果未配置，不走授权
			params.clear();
			params.add(orgNo);
			params.add(tradeNo);
			params.add(pdtNo);
			sql = "select cur, amt, rang from bupmscfg where orgno = ? and tradeno = ? and ( pdtno = ? or pdtno = 'ALL' )";
			List<Map<String, Object>> result2 = dao.sqlQuery(sql, params);
			boolean hasCfg = false;
			if( result2 != null && result2.size() > 0 ){
				try{
					Map<String, Object> map = result2.get(0);
					limitCur = String.valueOf(map.get("cur"));
					limitAmt = ((BigDecimal)map.get("amt")).doubleValue();
					rang = String.valueOf(map.get("rang"));
					hasCfg = true;
				}catch(Exception e){
					Logger.getLogger(AuthHelp.class.getName()).error("AuthHelp getFirst Error:"+e.getMessage());
				}
			}
			
			//3.获取转换后的金额
			double cur_amt = 0.0d;
			QuotePriceUtil pu = new QuotePriceUtil();
			pu.setOrgNo(orgNo);
			cur_amt = pu.getPrice(cur, limitCur, amt);
			
			//4.交易是否要授权
			if( cur_amt >= limitAmt && hasCfg){
				bNeedAuth = "true";
			}
			
			//需要审批的人、必须审批的人
			if( "true".equals(bNeedAuth) && StringUtils.isNotEmpty(rang) ){
				//授权范围对象
				List<AuthHelpRang> rangLst = new ArrayList<AuthHelpRang>();
				JSONArray jarr = (JSONArray)JSON.parse(rang);
				for(int i=0; i<jarr.size(); i++){
					JSONObject jobj = (JSONObject)jarr.get(i);
					String minamt = (String)jobj.get("minamt");
					String maxamt = (String)jobj.get("maxamt");
					minamt = minamt.replaceAll(",", "");
					maxamt = maxamt.replaceAll(",", "");
					double _minamt = Double.parseDouble(minamt);//最小金额
					double _maxamt = -1;//最大金额
					if( maxamt != null && !"".equals(maxamt) ){
						try {
							_maxamt = Double.parseDouble(maxamt);
						} catch (Exception e) {
						}
					}
					String rangus = "";
					try {
						String _rangus = (String)jobj.get("rangus");
						if( StringUtils.isNotEmpty(_rangus) ){
							JSONArray _jarr2 = (JSONArray)JSON.parse(_rangus);
							for(int j=0; j<_jarr2.size(); j++){
								JSONObject _jobj2 = (JSONObject)_jarr2.get(j);
								rangus += ("".equals(rangus)? "" : "@@")+_jobj2.getString("userid");
							}
						}
					} catch (Exception e) {
					}
					rangLst.add( new AuthHelpRang(_minamt, _maxamt, rangus) );
				}
				//获得当前授权的用户
				AuthHelpRang cur_range = AuthHelpRang.getRange(cur_amt, rangLst, bNeedAuthDoneUsers);
				
				//可授权用户=必须授权用户
				if( cur_range != null && cur_range.getUsers() != null){
					bNeedAuth = "true";
					bNeedAuthUsers = cur_range.getUsers();
					bNeedAuthMustUsers = cur_range.getUsers();
				}else{
					bNeedAuth = "false";
				}
			}else{
				bNeedAuth = "false";
			}
		}catch(Exception e){
			bNeedAuth = "false";
			Logger.getLogger(AuthHelp.class.getName()).error("AuthHelp getFirst Error:"+e.getMessage(), e);
		}
		
		vars.put(B_NEED_AUTH, bNeedAuth);
		vars.put(B_NEED_AUTH_USERS, bNeedAuthUsers);
		vars.put(B_NEED_AUTH_USERS_DONE, bNeedAuthDoneUsers);
		vars.put(B_NEED_AUTH_USERS_MUST, bNeedAuthMustUsers);
		return vars;
	}
	
	
	public static Map<String, String> getFirst2(String txnNo){
		Map<String, String> vars = new HashMap<String, String>();
		String bNeedAuth = "false";
		String bNeedAuthUsers = "";
		String bNeedAuthDoneUsers = "";
		String bNeedAuthMustUsers = "";
		
		try{
			String orgNo = "";//机构
			String tradeNo = "";//交易
			String pdtNo = "";//产品
			String cur = "";//币种
			double amt = 0.0d;//金额
			String limitCur = "";//审批币种
			double limitAmt = 0.0d;//审批金额
			String rang = "";//审批范围
			
			//0.数据访问器
			ProcessDAO dao = new ProcessDAO();
			
			//1.获取业务数据
			List<Object> params = new ArrayList<Object>();
			params.add(txnNo);
			String sql = "select tranorgno, trancur, tranamt, tradeno, pdtno from butxntp where txnno = ?";
			List<Map<String, Object>> result = dao.sqlQuery(sql, params);
			if( result != null && result.size() > 0 ){
				try{
					Map<String, Object> res0 = result.get(0);
					orgNo = String.valueOf(res0.get("tranorgno"));
					tradeNo = String.valueOf(res0.get("tradeno"));
					pdtNo = String.valueOf(res0.get("pdtno"));
					cur = String.valueOf(res0.get("trancur"));
					amt = ((BigDecimal)res0.get("tranamt")).doubleValue();
				}catch(Exception e){
					Logger.getLogger(AuthHelp.class.getName()).error(e.getMessage(), e);
				}
			}
			
			//2.获取审批配置
			params.clear();
			params.add(orgNo);
			params.add(tradeNo);
			params.add(pdtNo);
			sql = "select cur, amt, rang from bupmscfg where orgno = ? and tradeno = ? and ( pdtno = ? or pdtno = 'ALL' )";
			List<Map<String, Object>> result2 = dao.sqlQuery(sql, params);
			boolean hasCfg = false;
			if( result2 != null && result2.size() > 0 ){
				try{
					Map<String, Object> map = result2.get(0);
					limitCur = String.valueOf(map.get("cur"));
					limitAmt = ((BigDecimal)map.get("amt")).doubleValue();
					rang = String.valueOf(map.get("rang"));
					hasCfg = true;
				}catch(Exception e){
					Logger.getLogger(AuthHelp.class.getName()).error("AuthHelp getFirst Error:"+e.getMessage());
				}
			}
			
			//转换金额
			double cur_amt = 0.0d;
			QuotePriceUtil pu = new QuotePriceUtil();
			pu.setOrgNo(orgNo);
			cur_amt = pu.getPrice(cur, limitCur, amt);
			if( cur_amt >= limitAmt && hasCfg){
				bNeedAuth = "true";
			}
			
			//需要审批的人、必须审批的人
			if( "true".equals(bNeedAuth) && StringUtils.isNotEmpty(rang) ){
				JSONArray jarr = (JSONArray)JSON.parse(rang);
				for(int i=0; i<jarr.size(); i++){
					JSONObject jobj = (JSONObject)jarr.get(i);
					String minamt = (String)jobj.get("minamt");
					String maxamt = (String)jobj.get("maxamt");
					double _minamt = Double.parseDouble(minamt);//最小金额
					double _maxamt = -1;//最大金额
					if( maxamt != null && !"".equals(maxamt) ){
						try {
							_maxamt = Double.parseDouble(maxamt);
						} catch (Exception e) {
						}
					}
					String rangus = "";
					try {
						String _rangus = (String)jobj.get("rangus");
						if( StringUtils.isNotEmpty(_rangus) ){
							JSONArray _jarr2 = (JSONArray)JSON.parse(_rangus);
							for(int j=0; j<_jarr2.size(); j++){
								JSONObject _jobj2 = (JSONObject)_jarr2.get(j);
								rangus += ("".equals(rangus)? "" : "@@")+_jobj2.getString("userid");
							}
						}
					} catch (Exception e) {
					}
					
					//可授权用户
					if( cur_amt >= _minamt ){
						bNeedAuthUsers += ("".equals(bNeedAuthUsers)? "" : "@@")+rangus;
						
						//必须授权用户
						if( _maxamt == -1 || cur_amt <= _maxamt ){
							rangus = rangus.replaceAll("@@", "+");
							bNeedAuthMustUsers += ("".equals(bNeedAuthMustUsers)? "" : "@@")+rangus;
						}
					}
				}
			}
		}catch(Exception e){
			bNeedAuth = "false";
			Logger.getLogger(AuthHelp.class.getName()).error("AuthHelp getFirst Error:"+e.getMessage(), e);
		}
		
		vars.put(B_NEED_AUTH, bNeedAuth);
		vars.put(B_NEED_AUTH_USERS, bNeedAuthUsers);
		vars.put(B_NEED_AUTH_USERS_DONE, bNeedAuthDoneUsers);
		vars.put(B_NEED_AUTH_USERS_MUST, bNeedAuthMustUsers);
		return vars;
	}
	
	public static void main(String[] args) {
		String rangus = "q2@@232";
		System.out.println( rangus.replaceAll("@@", "+") );
		System.out.println( rangus.split("\\+")[0] );
		
		Map<String, String> vars = new HashMap<String, String>();
		vars.put(AUTH_NEXTSTEP, "B");//下一个授权的步骤
		vars.put(AUTH_DONESTEPQUEUE, "A");//已经授权的步骤
		vars.put(AUTH_ALLSTEPQUEUE, "A@@B@@C");//需要授权的步骤
		vars.put(AUTH_DONESTEPUSERS, "1234");//已经授权的用户
		vars.put("wf_userId", "6578");//已经授权的用户
		System.out.println( AuthHelp.getNext("", vars) );

	}
	
	/**
	 * @param txnNo 交易流水
	 * @return
	 */
	public static Map<String, String> getNext2(String txnNo, Map<String, String> vars){
		String bNeedAuth = "false";
		String bNeedAuthDoneUsers = "";
		
		try{
			bNeedAuth = vars.get(B_NEED_AUTH);
			String curUserid = vars.get("wf_userId");
			
			//已授权用户
			bNeedAuthDoneUsers = vars.get(B_NEED_AUTH_USERS_DONE);
			if( StringUtils.isEmpty(bNeedAuthDoneUsers) ){
				bNeedAuthDoneUsers = curUserid;
			}else{
				bNeedAuthDoneUsers = bNeedAuthDoneUsers+"@@"+curUserid;
			}
			
			//是否还要授权
			String bNeedAuthMustUsers = vars.get(B_NEED_AUTH_USERS_MUST);
			if( StringUtils.isEmpty(bNeedAuthMustUsers) ){
				bNeedAuth = "false";//没有必须授权的用户时，只授权一次
			}
			String[] authUsers =  bNeedAuthMustUsers.split("@@");
			for(String aus : authUsers){
				String[] ausArr = aus.split("\\+");
				if( allInArr(ausArr, bNeedAuthDoneUsers.split("@@"))){
					bNeedAuth = "false";
					break;
				}
			}
		}catch (Exception e){
			Logger.getLogger(AuthHelp.class.getName()).error("AuthHelp getNext Error:"+e.getMessage(), e);
		}
		
		vars.put(B_NEED_AUTH, bNeedAuth);
		vars.put(B_NEED_AUTH_USERS_DONE, bNeedAuthDoneUsers);
		return vars;
	}
	
	
	/**
	 * 第一个数组的所有元素都在第二个数组中
	 * @param arr
	 * @param otherArr
	 * @return true-第一个数组的所有元素都在第二个数组中, false-否
	 */
	private static boolean allInArr(String[] arr, String[] otherArr){
		boolean is = true;
		Map<String, String> map = new HashMap<String, String>();
		if( otherArr != null ){
			for(String e : otherArr){
				map.put(e, e);
			}
		}
		
		if( arr != null ){
			for(String e : arr){
				is = is && map.containsKey(e);
			}
		}
		
		return is;
	}

}
