package com.ebills.product.dg.action;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.bussprocess.context.Context;

import com.eap.flow.EAPAction;
import com.ebills.product.dg.AcctInterface.domain.GeneralCalc;
import com.ebills.product.dg.commons.Commons;
import com.ebills.util.EbillsException;
import com.ebills.utils.EbpDao;

public class UpdDate extends EAPAction {
	private String className = UpdDate.class.getName();

	public String execute(Context context){
		updateDateNew();
		return "0";
	}

	
	private void updateDateNew(){
		Date workDate = Commons.getWorkDate();
		Date newDate = GeneralCalc.calcDate(workDate, 1);
		String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(newDate);
		String sql = "UPDATE PASYS SET SYSVAL = ? WHERE SYSNAMEID = ?";
		String userSql = "UPDATE PAUSR SET USERSTATE = ? WHERE USERSTATE = ?";
		EbpDao jsDao = new EbpDao();
		List<Object> inputList = new LinkedList<Object>();
		List<Object> list = new LinkedList<Object>();
		inputList.add(dateStr);
		inputList.add("workDate");
		list.add("0");
		list.add("1");
		try {
			jsDao.execute(sql, inputList);
			jsDao.execute(userSql, list);//日切之后需要把锁定的客户全部置成正常状态
		} catch (EbillsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
