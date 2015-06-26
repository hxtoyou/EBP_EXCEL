package com.ebills.product.dg.imdf;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.bussprocess.context.Context;

import com.ebills.intf.spi.InterfaceManager;

public class TradingClient {
	
  public void sendBefore(Context context){
	  context.put("dbitno", "123456");
  }
  public void sendAfter(Context context){
	  //{LOANCUR=, HKFS=, PRDCD=, YQDKRATE=, ZCDKRATE=, JXZQ=, DKACCT=, ACCTORG=, STARTDATE=, KHH=客户号, HTH=, JJH=客户号, ENDDATE=}
		Map<String, Object> map = (Map<String, Object>) context
				.get(InterfaceManager.RESULT_KEY);
		Map<String, Object> row = new HashMap<String, Object>();
		row.put("errorCode", map.get("errorCode"));
		row.put("errorMsg", map.get("errorMsg"));
		row.put("workdate", map.get("workdate"));
		row.put("worktime", map.get("worktime"));
		row.put("serialno", map.get("serialno"));
		row.put("channelserno", map.get("channelserno"));
		row.put("custno", map.get("custno"));
		row.put("orgno", map.get("orgno"));
		row.put("cmitno", map.get("cmitno"));
		row.put("dbitno", map.get("dbitno"));
		row.put("fnpdid", map.get("fnpdid"));
		row.put("cucyno", map.get("cucyno"));
		row.put("tramts", map.get("tramts"));
		row.put("valuedate", map.get("valuedate"));
		row.put("mudate", map.get("mudate"));
		row.put("retnfs", map.get("retnfs"));
		row.put("overrate", map.get("overrate"));
		row.put("accono", map.get("accono"));
		row.put("rate", map.get("rate"));
		row.put("inrcyc", map.get("inrcyc"));
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list.add(row);
		context.put("listData", list);
  }
}
