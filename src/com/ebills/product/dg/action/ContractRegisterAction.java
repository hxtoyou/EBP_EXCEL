package com.ebills.product.dg.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.bussprocess.context.Context;

import com.eap.core.EAPConstance;
import com.eap.exception.EAPException;
import com.eap.flow.EAPAction;
import com.ebills.util.EbillsException;
import com.ebills.utils.CommonUtil;
import com.ebills.utils.EbpDao;
import com.ebills.utils.OutPutBean;

public class ContractRegisterAction extends EAPAction {

	protected HttpServletRequest request = null;

	public String execute(Context context) {
		try {
			Object object = null;

			if (context != null) {
				object = context.getValue(EAPConstance.SERVLET_REQUEST);
			}

			if (null != object) {
				request = (HttpServletRequest) object;
			}

			String method = request.getParameter("method");
			System.out.println("method-------------->>>" + method);
			if ("queryCount".equals(method)) {
				queryCount(context, request);
			} else if ("isExistsBizNo".equals(method)) {
				isExistsBizNo(context, request);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "0";
	}

	public void queryCount(Context context, HttpServletRequest request) throws EbillsException, EAPException {
		String corpNo = request.getParameter("corpNo");
		String swiftcode = request.getParameter("swiftcode");
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(corpNo);
		paramList.add(swiftcode);
		EbpDao gjjsDao = new EbpDao();
		String sql = "select to_char(max(expirydate),'yyyy-MM-dd')as expirydate from filongtermjsfhtdjfo where CORPNO=? and swiftcode=?";
		List<Map<String, Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
		//查询tp表中是否有值
		String sqlTp = "select to_char(max(expirydate),'yyyy-MM-dd')as expirydatetp from filongtermjsfhtdjtp where CORPNO=? and swiftcode=?";
		List<Map<String, Object>> queryRetTp = gjjsDao.sqlQuery(sqlTp, paramList);
		if(null!=queryRetTp&&queryRetTp.size()>0){
			queryRet.add(queryRetTp.get(0));
		}
		OutPutBean output = new OutPutBean(CommonUtil.ListToJson(queryRet));
		output.writeOutPut(context);
	}

	public void isExistsBizNo(Context context, HttpServletRequest request) throws EbillsException, EAPException {
		String bizNo = request.getParameter("bizNo");
		// 判断合同ID是否存在,如果存在取出签订日期
//		String contractDate = request.getParameter("contractDate");
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(bizNo);
//		paramList.add(contractDate);
		EbpDao gjjsDao = new EbpDao();
//		String sql = "select to_char(expirydate,'yyyy-MM-dd') as expirydate from FILONGTERMJSFHTDJFO where bizno = ? and expirydate >= to_date(?,'yyyy/MM/dd')";
		String sql = "select to_char(expirydate,'yyyy-MM-dd') as expirydate from FILONGTERMJSFHTDJFO where bizno = ?";
		List<Map<String, Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
		//查询tp表中是否有值
		String sqlTp = "select to_char(expirydate,'yyyy-MM-dd') as expirydatetp from FILONGTERMJSFHTDJTP where bizno = ?";
		List<Map<String, Object>> queryRetTp = gjjsDao.sqlQuery(sqlTp, paramList);
		Map<String, String> map = new HashMap<String, String>();
		
		if(null!=queryRet && queryRet.size()>0){
			map.put("expiryDate", queryRet.get(0).get("EXPIRYDATE").toString());
		}else{
			map.put("expiryDate", null);
		}
		
		if(null!=queryRetTp && queryRetTp.size()>0){
			map.put("expiryDateTp", queryRetTp.get(0).get("EXPIRYDATETP").toString());
		}else{
			map.put("expiryDateTp", null);
		}
		
		OutPutBean output = new OutPutBean(CommonUtil.MapToJson(map));
        output.writeOutPut(context);
	}
}
