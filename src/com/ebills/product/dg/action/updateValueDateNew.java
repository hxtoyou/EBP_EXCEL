package com.ebills.product.dg.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.bussprocess.context.Context;
import org.apache.commons.lang.StringUtils;

import com.eap.flow.EAPAction;
import com.ebills.param.datainfo.DataFactory;
import com.ebills.product.dg.AcctInterface.domain.GeneralCalc;
import com.ebills.product.dg.commons.Commons;
import com.ebills.utils.CommonUtil;
import com.ebills.utils.EbpConstants;
import com.ebills.utils.EbpDao;

public class updateValueDateNew extends EAPAction{
	

	public String execute(Context context){
		upValueDateNew();
		return "0";
	}

	
	private void upValueDateNew(){
		try {			
			EbpDao jsDao = new EbpDao();
			LinkedList<Object> inputList = new LinkedList<Object>();
			String sql = "select accbkno,cashccy from clcashfo";
			List<Map<String,Object>> list = jsDao.queryBySql(sql, "accbkno,cashccy", "", inputList);
			for(int i=0;i<list.size();i++){
				String accbkno = (String)list.get(i).get("accbkno");
				String currency = (String)list.get(i).get("cashccy");
				String msgSql  = "select sum(t.amount) from " + DataFactory.getDataInfoFile(EbpConstants.TPSENDMSGID).getTablename() + " t, clcashmsg t1 where t.msgType = t1.msgType and t1.isCash = 'Y' " +
						"and t.amount is not null and (t.state = 'SD' or  t.state = 'HS' or  t.state = 'HF') and (t.chkflg = 'N' or t.chkflg is null) and t.valuedate = ? and (t.recivercode = ? or t.recivercode = ?) and currency = ?";
				inputList.clear();
				SimpleDateFormat sdf1 = new  SimpleDateFormat("yyyy-MM-dd");
				String workDay = CommonUtil.getSysFld(EbpConstants.WORK_DATE);
				Date date = new Date();
				try {
					date = sdf1.parse(workDay);
				} catch (ParseException e) {
				}
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime(date);
				cal.add(Calendar.DATE,1);
				Date nextWorkDay = cal.getTime();
				inputList.add(nextWorkDay);
				inputList.add(accbkno);
				inputList.add(accbkno);
				inputList.add(currency);
				List<Map<String,Object>> list1 = jsDao.queryBySql(msgSql, "totalAmt", "", inputList);
				if(list1 != null && !list1.isEmpty()){
					String sql1 = "update clcashfo set cashccyamt = nvl(cashccyamt,0)-?, cashbal = nvl(cashbal,0)-?, updatetime = ?,valuedate = ? where cashccy = ?";
					String totalAmt = (String)list1.get(0).get("totalAmt");
					if(StringUtils.isNotEmpty(totalAmt)) {
						double amount = Double.parseDouble((String)list1.get(0).get("totalAmt"));
						inputList.clear();
						inputList.add(amount);
						inputList.add(amount);
						java.sql.Date workDate = Commons.getWorkDate();
						Date newDate = GeneralCalc.calcDate(workDate, 1);
						String dateStr = new SimpleDateFormat("yyyy/MM/dd").format(newDate);
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
						Date date1 = sdf.parse(dateStr);
						inputList.add(new Date());
						inputList.add(date1);		
						inputList.add(currency);
						jsDao.execute(sql1, inputList);
					}else{
						String sql2 = "update clcashfo set updatetime = ?,valuedate = ? where cashccy = ?";
						inputList.clear();
						inputList.add(new Date());
						java.sql.Date workDate = Commons.getWorkDate();
						Date newDate = GeneralCalc.calcDate(workDate, 1);
						String dateStr = new SimpleDateFormat("yyyy/MM/dd").format(newDate);
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
						Date date1 = sdf.parse(dateStr);
						inputList.add(date1);		
						inputList.add(currency);
						jsDao.execute(sql2, inputList);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
