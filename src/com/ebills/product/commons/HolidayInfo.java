package com.ebills.product.commons;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ebills.util.EbillsException;
import com.ebills.utils.EbpConstants;
import com.ebills.utils.EbpDao;


public class HolidayInfo{

	public final static String DATAID = "paholiday";
	/**
	 * 判断是否工作日
	 * @param date 指定的日期
	 * @param holidayType 类型
	 * @param curSign  币种
	 * @param countryNo 国家编号
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean isWorkHld(Date date,String curSign,String countryNo) throws EbillsException{
		return isWorkHld(date,null,curSign,countryNo);
	}
	
	
	/**
	 * 判断是否工作日
	 * @param date 指定的日期
	 * @param holidayType 类型
	 * @param executeYear 年份
	 * @param curSign  币种
	 * @param countryNo 国家编号
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean isWorkHld(Date date,String executeYear,String curSign,String countryNo) throws EbillsException{
		
		boolean isHld = false;
		boolean workHld = false; 
		//返回默认值为工作日
		if(date == null){
			workHld = true;
			return workHld;
		}
		//不传年份则默认为指定日期年份
		if(executeYear == null || "".equals(executeYear)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			executeYear=sdf.format(date);
		}
		
		Map<String, String> resultMap = getHolidyData(executeYear,curSign,countryNo);
		//返回默认值为工作日
		if(resultMap==null || resultMap.size()==0){
			workHld = true;
			return workHld;
		}
		String includeHld = resultMap.get(GeneralConstants.INCLUDEHLD);
		String excludeHld = resultMap.get(GeneralConstants.EXCLUDEHLD);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar cal = new GregorianCalendar();
		String dt= sdf.format(date);
		cal.setTime(date);
		int weekend= cal.get(Calendar.DAY_OF_WEEK)-1;
		if(weekend ==0 || weekend ==6 ){
			isHld = true;
		}
		
		if(includeHld != null && !"".equals(includeHld)){
		    String[] includeHlds = includeHld.split(",");
		    for (String str : includeHlds) {
				 if(dt.equals(str)){
					 isHld = true;
					 break;
				 }
			}
		}
		
	    if(isHld){
	    	String[] excludeHlds = null;
	    	if(excludeHld !=null && !"".equals(excludeHld)){
	    		excludeHlds = excludeHld.split(",");
	    		for (String str : excludeHlds) {
			    	if(dt.equals(str)){
			    		workHld = true;
			    		break;
					 }
				}
	    	}
	    }else{
	    	workHld = true;
	    }
	    return workHld;
	} 
	
	/**
	 * 获得N天后的工作日期时间
	 * @param date 指定的日期
	 * @param number 天数
	 * @param isContainCurrDay 是否包含当天
	 * @param curSign  币种
	 * @param countryNo 国家编号
	 * @return date
	 * @throws Exception
	 */
	public static Date getAfterManyDayDate(Date date,int number,boolean isContainCurrDay,String curSign,String countryNo) throws EbillsException{
		return getAfterManyDayDate(date,number,isContainCurrDay,null,curSign,countryNo);
				
	}
	

	/**
	 * 获得N天后的工作日期时间
	 * @param date 指定的日期
	 * @param number 天数
	 * @param isContainCurrDay 是否包含当天
	 * @param executeYear 年份
	 * @param curSign  币种
	 * @param countryNo 国家编号
	 * @return date
	 * @throws Exception
	 */
	public static Date getAfterManyDayDate(Date date,int number,boolean isContainCurrDay,
						String executeYear,String curSign,String countryNo) throws EbillsException{
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		
		boolean firstFlag = false;
		if(isContainCurrDay){
			firstFlag = true;
		}
		int i= 0;
		int _number = number;
		number = Math.abs(number);
		while (i<=number) {
			if(firstFlag){
				firstFlag = false;
			}else{
				if(_number >0){
					cal.add(Calendar.DATE,1);
				}else if(_number < 0){
					cal.add(Calendar.DATE,-1);
				}else{
					cal.add(Calendar.DATE,1);
				}
			}
			
			//if(!isWork){
//				//System.out.println("===>>>"+sdf.format(cal.getTime())+"    "+ (cal.get(Calendar.DAY_OF_WEEK)-1)+"休息日");
			//continue;
			//}else{
//				//System.out.println("===>>>"+sdf.format(cal.getTime())+"    "+ (cal.get(Calendar.DAY_OF_WEEK)-1)+"工作日");
			//}
			++i;
		}
		//现在只判断最后一天是否是节假日是节假日才递延  by zhangqi 20150203
		boolean isWork = isWorkHld(cal.getTime(),executeYear,curSign,countryNo);
		while(!isWork)	{
			if(_number >0){
				cal.add(Calendar.DATE,1);
			}else if(_number < 0){
				cal.add(Calendar.DATE,-1);
			}
		    isWork = isWorkHld(cal.getTime(),executeYear,curSign,countryNo);
		}
		return cal.getTime();
	}
	
	/**
	 * 获得N天后的工作日期时间
	 * @param date 指定的日期
	 * @param number 天数
	 * @param isContainCurrDay 是否包含当天
	 * @param executeYear 年份
	 * @param curSign  币种
	 * @param countryNo 国家编号
	 * @return date
	 * @throws Exception
	 */
	public static Date getNumManyDayDate(Date date,int number,
						String curSign,String countryNo) throws EbillsException{
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		
		String executeYear = "";
		//不传年份则默认为指定日期年份
		if(date != null){
			executeYear = new SimpleDateFormat("yyyy").format(date);
		}
		for (int i = 0; i < number; i++) {
			date = getAfterManyDayDate(date, 1, true, executeYear, curSign, countryNo);
		}
		 
		return date;
	}
	
	private static Map<String, String> getHolidyData(String executeYear,String curSign,String countryNo){
		Map<String, String> rtnMap = new HashMap<String, String>();
		try {
			EbpDao gjDao = new EbpDao();
			Map<String, Object> mapParam = new HashMap<String, Object>();
			
			if(curSign != null && !"".equals(curSign)){
				mapParam.put("curSign", curSign);
				mapParam.put("holidayType", "0");
			}else if(countryNo != null && !"".equals(countryNo)){
				mapParam.put("countryNo", countryNo);	
				mapParam.put("holidayType", "1");
			}else{
				throw new Exception("Query condition is not correct");
			}
			
			if(executeYear != null && !"".equals(executeYear)){
				mapParam.put("executeYear", executeYear);
			}else{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
				mapParam.put("executeYear", sdf.format(new Date()));
			}
			List<Map<String,Object>>  resultList =  gjDao.queryByDataId(DATAID, "", mapParam);
			if(resultList != null && resultList.size() == 1){
				Map<String,Object> map =  resultList.get(0);
				String includeHld = (String) map.get(GeneralConstants.INCLUDEHLD);
				String excludeHld = (String) map.get(GeneralConstants.EXCLUDEHLD);
				rtnMap.put(GeneralConstants.INCLUDEHLD, includeHld);
				rtnMap.put(GeneralConstants.EXCLUDEHLD, excludeHld);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return rtnMap;
	}
	
	/**
	 * 获取两个日期之间的节假日天数（包含起止时间）
	 * 币种和年份之间二选一
	 * @param startDate起始时间
	 * @param endDate结束时间
	 * @param curSign币种
	 * @param countryNo年份
	 * @return
	 * @throws EbillsException 
	 */
	public static int getHolidayNum(Date startDate,Date endDate,String curSign,String countryNo) throws EbillsException{
		
		int holidayNum = 0;
		 GregorianCalendar cal = new GregorianCalendar();
		 cal.setTime(startDate);
		 Date _startDate =startDate;
		while(endDate.compareTo(_startDate)>=0){
			boolean isWork = isWorkHld(_startDate,null,curSign,countryNo);
			if(!isWork){//非工作日，即节假日
				holidayNum++;
			}
			cal.add(Calendar.DATE,1);
			_startDate = cal.getTime();
		}
		return holidayNum;
	}
}
