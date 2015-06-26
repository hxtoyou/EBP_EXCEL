package com.ebills.product.dg.action;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.bussprocess.context.Context;
import org.apache.commons.bussprocess.exception.BPException;
import org.apache.commons.lang.StringUtils;

import com.eap.core.EAPConstance;
import com.eap.exception.EAPException;
import com.eap.flow.EAPAction;
import com.eap.resource.jdbc.data.Page;
import com.ebills.eap.action.query.QueryForAutoApendConditons;
import com.ebills.util.EbillsException;
import com.ebills.utils.CommonUtil;
import com.ebills.utils.EbpConstants;
import com.ebills.utils.EbpDao;
import com.ebills.utils.OutPutBean;

public class QueryUsrLog extends EAPAction {
	private static final String FIELD_SEPERATOR = ",";

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String execute(Context context) throws BPException {
		String local = null;
		OutPutBean outPut = null;
		try {
			local = (String) context.getValue(EbpConstants.USER_LANGUAGE);
			HttpServletRequest request = (HttpServletRequest) context.getValue(EAPConstance.SERVLET_REQUEST);
			int currPageNo = Integer.parseInt(request.getParameter("page"));
			int pageSize = Integer.parseInt(request.getParameter("rows"));
			
			//增加前台排序功能
			String sort = request.getParameter("sort");
			String order = request.getParameter("order");
			String _order = null;
			if( !StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order) ){
				_order = ("order by "+sort+" "+order).toLowerCase();
			}else{
				_order="order by operatedate desc";
			}
			
			String sql = " select t.txnno,t.usrid,t.operate,t.operatedate,t.operateduserid,t.operatedtls from PAUSRLOG t ";
			String outItem = "txnno, usrid, operate, operatedate, operateduserid, operatedtls";
			LinkedList<Object> input = new LinkedList<Object>();
			LinkedList outPutField = parseOutputFields(outItem);
			QueryForAutoApendConditons qfac = new QueryForAutoApendConditons();
			String finalSQL = qfac.appendConditionToSql(sql,input,outPutField,request);
			EbpDao gjDao = new EbpDao();
			Page page = gjDao.page(finalSQL, _order, input, outPutField, currPageNo, pageSize);
			Map<String,Object> finalOut = new HashMap<String, Object>();
			//由于jsp页面使用easy-ui的 datagrid 组件来装载数据,所以需要rows 、total等标识
//			for(int i=0;i<list.size(); i++){
//				Map<String,Object> map = (Map<String, Object>) list.get(i);
//				String classNo = (String) map.get("txnno");
//				String type = (String) map.get("usrid");
//				String proKey = (String) map.get("operate");
//				String logNo = (String) map.get("operatedate");
//				String tradeNo = (String) map.get("operateduserid");
//				String stepNo = (String) map.get("operatedtls");
//				//String logMsg = EbillsLog.getLogMsg(local, type, proKey, classNo, logNo, tradeNo, stepNo, args);
////				if( StringUtils.isEmpty(logMsg) ){
////					logMsg = _logMsg;
////				}
////				map.put("logMsg", logMsg);
//			}
			finalOut.put("rows", page.getList());
			finalOut.put("total", page.getTotalNum());
			outPut = new OutPutBean(CommonUtil.MapToJson(finalOut));
			outPut.writeOutPut(context);
		} catch (EbillsException e) {
			throw new EAPException(e.getErrorCode(), e.getMessage(local));
		} catch (Exception e) {
			throw new EAPException("1", new EbillsException(e, this.getClass().getName(), 2, null, null).getMessage(local));
		}
		return "0";
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public LinkedList parseOutputFields(String outputFields) {
		LinkedList result = new LinkedList();
		StringTokenizer toke = new StringTokenizer(outputFields, FIELD_SEPERATOR);
		while (toke.hasMoreElements()) {
			String input = ((String) toke.nextElement()).trim();
			result.add(input);
		}
		return result;
	}
}
