package com.ebills.product.commons;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ebills.param.org.TradeOrgFactory;
import com.ebills.util.EbillsException;
import com.ebills.utils.CommonUtil;
import com.ebills.utils.EbpConstants;
import com.ebills.utils.EbpDao;

public class QuotePrice{
	
	/**
	 * 根据执行机构获得当前牌价
	 */
	public static List<Map<String,Object>>  getCurrentQuotePrice(String transOrgNo)throws EbillsException {
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		if(StringUtils.isEmpty(transOrgNo)){
			return list;
		}
		
		EbpDao dao = new EbpDao();
		
		String sql = "select INSOURCE,closingRate,impDate,innersalePrice,orgNo,curSign,STATUS,TXNNO,middlePrice,billbuyPrice," +
				"innerbuyPrice,zybuyprice,buyPrice,salePrice,billsalePrice,zysalePrice,basePrice from PACCYPRI where orgNo = '"+ transOrgNo+"'";
		list = dao.queryBySql(sql, "", null);
		
		return list;
	}
	
	/**
	 * 根据执行机构获得牌价机构
	 */
	private static String  getRuleOrg(String transOrgNo)throws EbillsException {
		
		return TradeOrgFactory.getOrgBizByBizName(transOrgNo, TradeOrgFactory.QUOTE_PRICE_ORGNO);
		
		
	}
	
	/**
	 * 获取两个币种的换算汇率
	 * @param fromCur
	 * @param toCur
	 * @return
	 * @throws EbillsException 
	 */
	public static double getExchangeRate(String fromCur,String toCur,String transOrgNo) throws EbillsException{
		
		double fRate = 0;
		double tRate = 0;
		
		if(StringUtils.isEmpty(fromCur) || StringUtils.isEmpty(toCur)){
			return 0;
		}
		
		if(fromCur.equals(toCur)){
			return 1;
		}
		String ruleOrg = getRuleOrg(transOrgNo);
		List<Map<String,Object>> qpList =  getCurrentQuotePrice(ruleOrg);
		if(qpList.size() == 0  ){
			return 0;
		}
		
		boolean flag = false;
		String localCcy = getLocalCcy();
		for (int i = 0; i < qpList.size(); i++) {
			Map<String, Object> map = qpList.get(i);
			String ccy = (String) map.get("curSign");
			
			if(fromCur.equals(ccy)){
				fRate = fromCur.equals(localCcy)? 100 : Double.parseDouble(map.get("buyPrice") == null ? "0" :map.get("buyPrice").toString());
			}
			if(fromCur.equals(localCcy)){
				fRate = 100;
			}
			if(toCur.equals(ccy)){
				tRate =  toCur.equals(localCcy)? 100 : Double.parseDouble(map.get("salePrice") == null ? "0" :map.get("salePrice").toString());
			}
			if(toCur.equals(localCcy)){
				tRate = 100;
			}
			if(tRate !=0 && fRate != 0 ){
				flag = true;
				break;
			}
		}
		
		if(flag){
			return fRate/tRate;
			//return tRate/fRate; 
		}
		
		return 0;
	}
	
	/**
	 * 获取两个币种之间的换算金额
	 * @param fromCur
	 * @param toCur
	 * @param amt
	 * @return
	 * @throws EbillsException 
	 */
	public static double getExchangeAmt(String fromCur,String toCur, double amt,String transOrgNo) throws EbillsException{
	
		double rate = getExchangeRate(fromCur, toCur, transOrgNo);
		if(rate > 0 ){
			BigDecimal   b   =   new   BigDecimal(rate * amt);  
			return b.setScale(getPerssionByCur(toCur),   BigDecimal.ROUND_HALF_UP).doubleValue();  
		}
		return 0;
	}
	
	/**
	 * 根据币种获得币种的保留小数位数
	 * @param cur
	 * @return
	 * @throws EbillsException
	 */
	public static int getPerssionByCur(String cur) throws EbillsException{
		String sql = "select t.percision from paccy t where t.cursign = '"+cur+"'";
		EbpDao dao = new EbpDao();
		List<Map<String, Object>>  resultLit = dao.queryBySql(sql, "", null);
		if(resultLit.size() ==1){
			Map<String, Object> map = resultLit.get(0);
			String percision = (String) map.get("percision"); 
			if(!StringUtils.isEmpty(percision)){
				return Integer.parseInt(percision);
			}
		}
		return 2;
	}
	
	/**
	 * 获得本币
	 * @return
	 * @throws EbillsException 
	 */
	private static String getLocalCcy() throws EbillsException{
		String sql = "select t.sysval from pasys t where t.sysnameid = '"+EbpConstants.LOCAL_CURRENCY+"'";
		EbpDao dao = new EbpDao();
		List<Map<String, Object>>  resultLit = dao.queryBySql(sql, "", null);
		if(resultLit.size() ==1){
			Map<String, Object> map = resultLit.get(0);
			String ccy = (String) map.get("sysval"); 
			if(!StringUtils.isEmpty(ccy)){
				return ccy;
			}
		}
		return "";
	}

	public static List<Map<String, Object>> getQuotePrice(String orgNo) throws EbillsException{
		String ruleOrg = getRuleOrg(orgNo);
		EbpDao dao = new EbpDao();
//		Map<String,Object> mParam = new HashMap<String,Object>();
//		mParam.put("orgNo", ruleOrg);
		String cond = " orgNo = '"+ruleOrg+"' and substr(impDate,0,10)= '"+CommonUtil.getSysFld(EbpConstants.WORK_DATE)+"'";
		return  dao.queryByDataId(GeneralConstants.QUOTEPRICE, null, cond);
	}
}
