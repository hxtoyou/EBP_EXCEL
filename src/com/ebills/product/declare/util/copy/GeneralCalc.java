package com.ebills.product.declare.util.copy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.ebills.util.EbillsCfg;
import com.ebills.util.EbillsException;

/**
 * 
 * 本类为一些基本计算的封装，不涉及到数据库或EJB
 * 
 * @author XJBS
 * 
 * */
public class GeneralCalc {

	private static DateFormat timeGuidFormater;
	public static String notNewtable = ",nguid,rwidh,ywbh,sfysb,sxbz,sfzx,bank_Id,handid,handdate,checkid,checkdate,ischeck,authid,authdate,isauth,oguid,recdate,datasources,hxxnwzbh";

	/**
	 * 计算一个日期加上天数后的日期
	 * 
	 * @param theDay
	 * @param addDays
	 */
	public static Date calcDate(Date theDay, int addDays) {
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
	 * 
	 * @param theDay
	 * @param addMonths
	 */
	public static Date calcMonth(Date theDay, int addMonths) {
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
	 * 
	 * @param theDay
	 * @param addYears
	 */
	public static Date calcYear(Date theDay, int addYears) {
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
	 * 
	 * @param inNum
	 * @param pLen
	 *            保留小数位长度
	 * */
	public static double roundFormat(double inNum, int pLen) {
		BigDecimal big = new BigDecimal(inNum);
		big = big.divide(new BigDecimal(1), pLen, BigDecimal.ROUND_HALF_UP);
		return big.doubleValue();
	}

	/**
	 * 数字格式化，直接截短多余小数位，不进行舍入计算
	 * 
	 * @param inNum
	 * @param pLen
	 *            保留小数位长度
	 * */
	public static double numTruncate(double inNum, int pLen) {
		BigDecimal big = new BigDecimal(inNum);
		big = big.divide(new BigDecimal(1), pLen, BigDecimal.ROUND_DOWN);
		return big.doubleValue();
	}

	/**
	 * 计算两个日期之间的间隔日期
	 */
	public static int getIntervalDays(Date startDate, Date endDate) {
		DateFormat df = DateFormat.getDateInstance();
		GregorianCalendar cal1 = new GregorianCalendar(), cal2 = new GregorianCalendar(), calTmp = new GregorianCalendar();
		cal1.setTime(startDate);
		cal2.setTime(endDate);
		int flag = 1;
		if (cal2.getTimeInMillis() < cal1.getTimeInMillis()) {
			java.util.Date dt = cal2.getTime();
			cal2.setTime(cal1.getTime());
			cal1.setTime(dt);
			flag = -1;
		}
		int year1 = cal1.get(GregorianCalendar.YEAR), year2 = cal2
				.get(GregorianCalendar.YEAR), days = year2 - year1;
		String firstday = "-01-01", endday = "-12-31";
		while (year2 - year1 > 0) {
			try {
				calTmp.setTime(df.parse(String.valueOf(year1) + endday));
				days += calTmp.get(GregorianCalendar.DAY_OF_YEAR)
						- cal1.get(GregorianCalendar.DAY_OF_YEAR);
				cal1.setTime(df.parse(String.valueOf(++year1) + firstday));
			} catch (ParseException e) {
				e.printStackTrace();
				return 0;
			}
		}
		days += cal2.get(GregorianCalendar.DAY_OF_YEAR)
				- cal1.get(GregorianCalendar.DAY_OF_YEAR);
		days = days * flag;
		if (days == -0)
			days = 0;
		return days;
	}

	/**
	 * 判断是否节假日,如果是则日期要顺延,用于融资还款业务
	 * 
	 * @param date
	 * @deprecated 应当使用JJRManagerEJB计算是否节假日
	 * @return
	 */
	public static Date getFactDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("E");
		String weekDays = formatter.format(date);
		if (weekDays.equals("星期六"))
			return calcDate(date, 1);
		else if (weekDays.equals("星期日"))
			return calcDate(date, 2);
		else
			return date;
	}

	/**
	 * 将数据转变为给定精度的数字
	 * 
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
	 * 
	 * @param s
	 * @param whenNullValue
	 * @return String 特别说明： 如果处理程序中，源串值可以为字符串'null',可按如下方法调用：
	 *         nvl(s,(s==null?"为空时的值",s));
	 * */
	public static String nvl(String s, String whenNullValue) {
		if (s == null)
			return whenNullValue;
		if (s.trim().toLowerCase().equals("null"))
			return whenNullValue;
		return s;
	}

	public static String nvl(String s) {
		return nvl(s, "").trim();
	}

	/**
	 * 获取系统文件路径分隔符
	 * 
	 * @return String
	 * */
	public static String getFileSplit() {
		return System.getProperty("file.separator");
	}

	public static String zfFormat(String acctInfo, int count, String delValue) {
		return zfFormat(acctInfo, count, delValue, "R", true);
	}

	public static String zfFormat(String acctInfo, int count, String delValue,
			String again, boolean bl) {
		acctInfo = acctInfo == null ? "" : acctInfo;
		if (!bl) {
			acctInfo = acctInfo.length() <= count ? acctInfo : acctInfo
					.substring(0, count);
		} else {
			if (acctInfo.length() <= count) {
				int y = count - acctInfo.length();
				for (int i = 0; i < y; i++) {
					if ("L".equals(again))
						acctInfo = (delValue != null ? delValue : " ")
								+ acctInfo;
					else
						acctInfo += (delValue != null ? delValue : " ");
				}
			} else {
				acctInfo = acctInfo.substring(0, count);
			}
		}
		return acctInfo;
	}

	// 对帐时用到,不匹配NULL 和 空字符
	public static boolean compareStr(String a, String b) {
		if (a == null || b == null)
			return false;
		else
			return a.length() > 0 && a.equals(b);
	}

	// 截取字符长度
	public static String jqzf(String str, int len) {
		str = str == null ? "" : str;
		if (str == null || str.length() <= len)
			return str;
		else
			return str.substring(0, len);
	}

	/** 18位数据 */
	public static String getTimeGUID() {
		String ret = "";
		Date d1 = new Date(System.currentTimeMillis());
		if (timeGuidFormater == null)
			timeGuidFormater = new SimpleDateFormat("yyMMddHHmmssSSS");
		ret = timeGuidFormater.format(d1);
		double ran = Math.random();
		ret += Math.round(1000 * ran);
		return ret;
	}

	/** 16位数据(用于同城清算申报，请不要改动返回长度) */
	public static String getGUID() {
		String ret = "";
		Date d1 = new Date(System.currentTimeMillis());
		if (timeGuidFormater == null)
			timeGuidFormater = new SimpleDateFormat("yyMMddHHmmssSSS");
		ret = timeGuidFormater.format(d1);
		double ran = Math.random();
		ret += Math.round(10 * ran);
		return ret;
	}

	public static String formatDateToStr(Date date) {
		if (date == null)
			return "";
		DateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");
		return dfDate.format(date);
	}

	public static Date formatStrToDate(String str) {
		if (str == null || "".equals(str.trim()))
			return null;
		Date date = null;
		try {
			date = Date.valueOf(str.trim());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/***************** 49号文、36号文加 add by Hudan 2013_05_20 begin **********************/
	/**
	 * 获取并初始化申报文件OldSend环境路径
	 */
	public synchronized static String initDeclareEnv(String bankId) {
		String homeDir = getSzsbHome();
		File file = new File(homeDir);
		if (!file.exists())
			file.mkdir();
		homeDir = homeDir + "oldSend";
		file = new File(homeDir);
		if (!file.exists())
			file.mkdir();
		homeDir = homeDir + getFileSplit() + bankId;
		file = new File(homeDir);
		if (!file.exists())
			file.mkdir();
		return homeDir + getFileSplit();
	}

	/**
	 * 设置收支申报申报文件存放根目录
	 * 
	 * @return
	 */
	public static String getSzsbHome() {
		String path = EbillsCfg.getHomeDir() + "szsb" + getFileSplit();
		File f = new File(path);
		if (!f.exists()) {
			f.mkdirs();
		}
		if (!f.isDirectory()) {
			f.deleteOnExit();
			f.mkdirs();
		}
		return path;
	}

	/**
	 * @desc 根据申报大类种类,获取对应的申报发送文件路径
	 * @param dclKind
	 * @return
	 */
	public static String getDeclareSendHome(String dclKind) {
		String sendPath = "";
		if ("BOP".equals(dclKind)) {
			sendPath = GeneralCalc.getBOPDeclareHome();
		} else if ("JSH".equals(dclKind)) {
			sendPath = GeneralCalc.getJSHDeclareHome();
		} else if ("ACC".equals(dclKind)) {
			sendPath = GeneralCalc.getACCDeclareHome();
		} else if ("CFA".equals(dclKind)) {
			sendPath = GeneralCalc.getCFADeclareHome();
		} else if ("EXD".equals(dclKind)) {
			sendPath = GeneralCalc.getEXDDeclareHome();
		} else if ("FAL".equals(dclKind)) {
			sendPath = GeneralCalc.getFALDeclareHome();
		} else if ("CWD".equals(dclKind)) {
			sendPath = GeneralCalc.getCWDDeclareHome();
		} else {
			System.out.println("申报大类" + dclKind + "超出外管定义,请注意对应系统配置是否正确!");
		}
		return sendPath;
	}

	/**
	 * 设置国际收支申报基础申报BOP申报文件根目录
	 * 
	 * @return
	 */
	public static String getBOPDeclareHome() {
		return getSzsbHome() + "BOPData" + getFileSplit();
	}

	/**
	 * 设置账户结售汇JSH申报文件根目录
	 * 
	 * @return
	 */
	public static String getJSHDeclareHome() {
		return getSzsbHome() + "JSHData" + getFileSplit();
	}

	/**
	 * 设置外汇账户ACC申报文件根目录
	 * 
	 * @return
	 */
	public static String getACCDeclareHome() {
		return getSzsbHome() + "ACCData" + getFileSplit();
	}

	/**
	 * 设置银行自身资本项目CFA申报文件根目录
	 * 
	 * @return
	 */
	public static String getCFADeclareHome() {
		return getSzsbHome() + "CFAData" + getFileSplit();
	}

	/**
	 * 设置银行自身外债项目EXD申报文件根目录
	 * 
	 * @return
	 */
	public static String getEXDDeclareHome() {
		return getSzsbHome() + "EXDData" + getFileSplit();
	}

	/**
	 * 设置对外金融资产负债FAL申报文件根目录
	 * 
	 * @return
	 */
	public static String getFALDeclareHome() {
		return getSzsbHome() + "FALData" + getFileSplit();
	}

	/**
	 * 设置个人外币现钞存取款CWD报文件根目录
	 * 
	 * @return
	 */
	public static String getCWDDeclareHome() {
		return getSzsbHome() + "CWDData" + getFileSplit();
	}

	/**
	 * 设置形成的申报文件的发送目录
	 * 
	 * @return
	 */
	public static String getSendPath() {
		return "send";
	}

	/**
	 * 设置接收外管错误反馈的接收目录
	 * 
	 * @return
	 */
	public static String getReceivePath() {
		return "recive";
	}

	/**
	 * 设置接收外管错误反馈的接收目录的备份
	 * 
	 * @return
	 */
	public static String getReceiveBakPath() {
		return "reciveBak";
	}
	

	/**
	 * @desc 获取申报大类的文件夹(BOPData/JSHData/ACCData/CFAData/EXDData/FALData/CWDData)
	 * @return
	 */
	public static List<String> getDclTypeFolder () {
		List<String> list = new ArrayList<String>();
		File file = new File(getSzsbHome());
		String[] files = file.list();
		for(int i=0; i<files.length; i++) {
			if(files[i].endsWith("Data")) {
				list.add(files[i].trim());
			}
		}
		return list;
	}

	public static boolean isNull(String str) {
		return str == null || str.trim().length() == 0;
	}

	public static boolean isNotNull(String str) {
		return !isNull(str);
	}

	/**
	 * 新建文件夹
	 * 
	 * @ejb.interface-method
	 */
	public static void newFolder(String folderPath) {
		try {
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.mkdir();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 复制整个文件夹内容
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf/ff
	 * @param delFlag
	 *            boolean 是否删除原文件/夹
	 * @return boolean
	 */
	public static void copyFolder(String oldPath, String newPath, boolean delFlag) {
		try {
			(new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}

				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ getFileSplit() + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {// 如果是子文件夹
					copyFolder(oldPath + File.separator + file[i], newPath
							+ getFileSplit() + file[i], delFlag);
				}
				if(delFlag) {
					temp.delete();
				}
			}

			// 删除最外层的文件/夹
			if(delFlag) {
				a.delete();
			}
		} catch (Exception e) {
			System.out.println("复制整个文件夹内容操作出错");
			e.printStackTrace();
		}
	}

	/**
	 * 将字符串转换为Timestamp
	 * 
	 * @param dateStr
	 * @return
	 * @throws Exception
	 * @throws ParseException
	 */
	public static Timestamp strToTimeStamp(String dateStr) throws Exception {
		Timestamp ts = null;
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			java.util.Date date = df.parse(dateStr);
			ts = new Timestamp(date.getTime());
		} catch (Exception e) {
			throw new Exception("将字符串转换为Timestamp类型失败:" + e.getMessage());
		}
		return ts;
	}

	/**
	 * 将字符串转换为java.sql.Date
	 * 
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Date strToSQLDate(String dateStr) throws Exception {
		Date date = null;
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			java.util.Date tmpDate = df.parse(dateStr);
			date = new java.sql.Date(tmpDate.getTime());
		} catch (Exception e) {
			throw new Exception("将字符串转换为Date类型失败:" + e.getMessage());
		}
		return date;
	}

	public static Map<String, Object> getValueMap(Object obj) {

		Map<String, Object> map = new HashMap<String, Object>();
		// System.out.println(obj.getClass());
		// 获取f对象对应类中的所有属性域
		Field[] fields = obj.getClass().getDeclaredFields();
		for (int i = 0, len = fields.length; i < len; i++) {
			String varName = fields[i].getName();
			if (notNewtable.indexOf(varName) != -1)
				continue;
			try {
				// 获取原来的访问控制权限
				boolean accessFlag = fields[i].isAccessible();
				// 修改访问控制权限
				fields[i].setAccessible(true);
				// 获取在对象f中属性fields[i]对应的对象中的变量
				Object o = fields[i].get(obj);
				if (o != null)
					map.put((varName.toLowerCase()).replace("_", ""), o);
				// System.out.println("传入的对象中包含一个如下的变量：" + varName + " = " + o);
				// 恢复访问控制权限
				fields[i].setAccessible(accessFlag);
			} catch (IllegalArgumentException ex) {
				ex.printStackTrace();
			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			}
		}
		return map;
	}

	public static String objToString(Object obj) throws EbillsException {
		if (obj == null) {
			return "";
		}
		return (String) obj;
	}

	public static Map<String, Object> Bean2Map(Object obj) {
		Map<String, Object> map = new HashMap<String, Object>();

		// System.out.println(obj.getClass());
		// 获取f对象对应类中的所有属性域
		Field[] fields = obj.getClass().getDeclaredFields();
		for (int i = 0, len = fields.length; i < len; i++) {
			String varName = fields[i].getName();
			try {
				// 获取原来的访问控制权限
				boolean accessFlag = fields[i].isAccessible();
				// 修改访问控制权限
				fields[i].setAccessible(true);
				// 获取在对象f中属性fields[i]对应的对象中的变量
				Object o = fields[i].get(obj);
				if (o != null)
					map.put(varName, o);
				// System.out.println("传入的对象中包含一个如下的变量：" + varName + " = " + o);
				// 恢复访问控制权限
				fields[i].setAccessible(accessFlag);
			} catch (IllegalArgumentException ex) {
				ex.printStackTrace();
			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			}
		}
		return map;
	}

	public static Object Map2Bean(Map<String, Object> map, Object obj) {
		Class class1 = obj.getClass();
		Method[] methods = class1.getMethods();
		for (Method method : methods) {
			String methodName = method.getName();
			if (methodName.startsWith("set")) {
				String propertyName = methodName.substring(3).toUpperCase(
						Locale.getDefault());
				Object value = map.get(propertyName);
				try {
					method.invoke(obj, value);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return obj;
	}

	public static Map<String, Object> buildFbMap(Map<String, Object> map)
			throws Exception {
		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			if (notNewtable.indexOf(key) != -1) {
				it.remove();
			}
		}
		return map;
	}
}
