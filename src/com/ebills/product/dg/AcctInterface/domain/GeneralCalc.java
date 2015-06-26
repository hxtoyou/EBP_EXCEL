package com.ebills.product.dg.AcctInterface.domain;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * 
 * 本类为一些基本计算的封装，不涉及到数据库或EJB
 * @author XJBS
 * 
 * */
public class GeneralCalc {
	
	private static String formatDate(Date date){
		DateFormat  timeGuidFormater = new SimpleDateFormat("yyMMddHHmmssSSS");
		return timeGuidFormater.format(date);
	}
	/**
	 * 计算一个日期加上天数后的日期
     * @param theDay
     * @param addDays            
     */
    public static Date calcDate(Date theDay,int addDays){
    	if (theDay == null)
            return null;
        GregorianCalendar cal = new GregorianCalendar();        
        cal.setTime(theDay);
        
        cal.add(GregorianCalendar.DAY_OF_YEAR, addDays);
        Date newDay = new Date(cal.getTimeInMillis());
        newDay.setTime(cal.getTimeInMillis());
        return newDay;
    }
    /**
	 * 计算一个日期加上月份后的日期
     * @param theDay
     * @param addMonths            
     */
    public static Date calcMonth(Date theDay,int addMonths){
    	if (theDay == null)
            return null;
        GregorianCalendar cal = new GregorianCalendar();        
        cal.setTime(theDay);
        
        cal.add(GregorianCalendar.MONTH, addMonths);
        Date newDay = new Date(cal.getTimeInMillis());
        newDay.setTime(cal.getTimeInMillis());
        return newDay;
    }
    /**
	 * 计算一个日期加上年份后的日期
     * @param theDay
     * @param addYears            
     */
    public static Date calcYear(Date theDay,int addYears){
    	if (theDay == null)
            return null;
        GregorianCalendar cal = new GregorianCalendar();        
        cal.setTime(theDay);
        
        cal.add(GregorianCalendar.YEAR, addYears);
        Date newDay = new Date(cal.getTimeInMillis());
        newDay.setTime(cal.getTimeInMillis());
        return newDay;
    }
       
    /**
     * 数字进行格式化，四舍五入
     * @param inNum
     * @param pLen 保留小数位长度
     * */
    public static double roundFormat(double inNum,int pLen){
    	BigDecimal big=new BigDecimal(inNum);   	
    	big =big.divide(new BigDecimal(1),pLen,BigDecimal.ROUND_HALF_UP);
    	return big.doubleValue();
    }
    /**
     * 数字格式化，直接截短多余小数位，不进行舍入计算
     * @param inNum 
     * @param pLen 保留小数位长度
     * */
    public static double numTruncate(double inNum,int pLen){
    	BigDecimal big=new BigDecimal(inNum);
    	big =big.divide(new BigDecimal(1),pLen,BigDecimal.ROUND_DOWN);
    	return big.doubleValue();
    }   
    
    /**
     * 计算两个日期之间的间隔日期
     */
    public static int getIntervalDays(Date startDate, Date endDate) {
    	DateFormat df = DateFormat.getDateInstance();
    	GregorianCalendar cal1= new GregorianCalendar(),cal2= new GregorianCalendar(),calTmp= new GregorianCalendar();
		cal1.setTime(startDate);
		cal2.setTime(endDate);
		int flag=1;
		if (cal2.getTimeInMillis()<cal1.getTimeInMillis()){
			java.util.Date dt=cal2.getTime();
			cal2.setTime(cal1.getTime());
			cal1.setTime(dt);
			flag=-1;
		}
		int year1 = cal1.get(GregorianCalendar.YEAR),year2 = cal2.get(GregorianCalendar.YEAR),days=year2-year1;
		String firstday="-01-01",endday="-12-31";
		while(year2-year1>0){
			try {
				calTmp.setTime(df.parse(String.valueOf(year1)+endday));
				days+=calTmp.get(GregorianCalendar.DAY_OF_YEAR)-cal1.get(GregorianCalendar.DAY_OF_YEAR);
				cal1.setTime(df.parse(String.valueOf(++year1)+firstday));
			} catch (ParseException e) {
				e.printStackTrace();
				return 0;
			}
		}
		days+=cal2.get(GregorianCalendar.DAY_OF_YEAR)-cal1.get(GregorianCalendar.DAY_OF_YEAR);
		days= days*flag;
		if (days==-0) days=0;
		return days;
    }
    
    /**
	 * 将字符串转换为java.sql.Date
	 * 
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Date strToSQLDate(String dateStr)
			throws EbillsException {
		Date date = null;
		if(dateStr==null||"".equals(dateStr)){
			return date;
		}
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			java.util.Date tmpDate = df.parse(dateStr);
			date = new java.sql.Date(tmpDate.getTime());
		} catch (Exception e) {
			throw new EbillsException("将字符串转换为Date类型失败:" + e.getMessage());
		}
		return date;
	}
    
    /**
     * 判断是否节假日,如果是则日期要顺延,用于融资还款业务
     * @param date
     * @deprecated 应当使用JJRManagerEJB计算是否节假日
     * @return
     */
    public static Date getFactDate(Date date){    	
    	 SimpleDateFormat formatter= new SimpleDateFormat("E");
    	 String weekDays = formatter.format(date);
    	 if(weekDays.equals("星期六"))
    		 return calcDate(date,1);
    	 else if(weekDays.equals("星期日"))
    		 return calcDate(date,2);
    	 else
    		 return date;
    }
    
    /**
     * 将数据转变为给定精度的数字
     * @param x
     * @param scale
     * @return
     */
    public static double round(double x, int scale) {       
        BigDecimal a = new BigDecimal(x);
        a = a.divide(new BigDecimal(1), scale, BigDecimal.ROUND_HALF_UP);       
        return a.doubleValue();
    }

    /**
     * 判断一个字符串是否为空或为字符串null,如果是，则返回为空的默认值，如果不是，则返回源字符串
     * @param s
     * @param whenNullValue
     * @return String
     * 特别说明：
     * 如果处理程序中，源串值可以为字符串'null',可按如下方法调用：
     * nvl(s,(s==null?"为空时的值",s));
     * */
    public static String nvl(String s,String whenNullValue){
    	if (s==null)
    		return whenNullValue;
    	if (s.trim().toLowerCase().equals("null"))
    		return whenNullValue;
    	return s;
    }
    
    public static String nvl(String s){
    	return nvl(s,"").trim();
    }
    /**
     * 获取系统文件路径分隔符
     * @return String
     * */
    public static String getFileSplit(){
    	return System.getProperty("file.separator");
    }
    
  
	public static String zfFormat(String acctInfo,int count,String delValue){
		return zfFormat(acctInfo,count,delValue,"R",true);
	}
	
	public static String zfFormat(String acctInfo,int count,String delValue,String again,boolean bl){
		acctInfo = acctInfo ==null?"":acctInfo;
		if(!bl){
			acctInfo = acctInfo.length() <= count ? acctInfo : acctInfo.substring(0,count);
		}else{
			if(acctInfo.length()<=count){			
				int y=count-acctInfo.length();
				for(int i=0;i<y;i++){					
					if("L".equals(again))
						acctInfo = (delValue!=null?delValue:" ") + acctInfo;
					else
						acctInfo+= (delValue!=null?delValue:" ");
				}
			}else{
				acctInfo = acctInfo.substring(0,count);
			}
		}
	    return acctInfo;
	}
	
    //对帐时用到,不匹配NULL 和 空字符
    public static boolean compareStr(String a,String b){
    	if(a==null || b==null)
    		return false;
    	else
    		return a.length() > 0 && a.equals(b);    		
    }
    
    //截取字符长度
    public static String jqzf(String str,int len){
    	str = str==null?"":str;
    	if(str==null || str.length() <= len)
    		return str;
    	else
    		return str.substring(0,len);
    }
    
	/**18位数据*/
	public static String getTimeGUID() {
		String ret = "";
		Date d1 = new Date(System.currentTimeMillis());
		ret = formatDate(d1);
		double ran = Math.random();
		ret += Math.round(1000 * ran);
		return ret;
	}
	
	/**16位数据(用于同城清算申报，请不要改动返回长度)*/
	public static String getGUID() {
		Date d1 = new Date(System.currentTimeMillis());
		String ret = formatDate(d1);
		double ran = Math.random();
		ret += Math.round(10 * ran);
		return ret;
	}
	
	public static String formatDateToStr(Date date){
		if(date == null)
			return "";
		DateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");
		return dfDate.format(date);
	}
	
	public static Date formatStrToDate(String str){
		if(str == null || "".equals(str.trim()))
			return null;
		Date date = null;
		try{
			date = Date.valueOf(str.trim());
		}catch(Exception e){
			e.printStackTrace();
		}
		return date;
	}
}

