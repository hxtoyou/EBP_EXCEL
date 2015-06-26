package com.ebills.product.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.ebills.product.action.excel.entity.HtmlRowStructure;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @author Xiao He E-mail:hxtoyou@163.com
 * @version 创建时间：2015年6月4日 下午1:24:48
 * 
 */
public class ExcelToHtmlUtil {
//	public final static Map<Short,String> colorMap=getColorMap();
//	public  static Map<Short, String> getColorMap(){
//		 Map<Short,String> colorMaps = Maps.newHashMap();
//		 for(int i=0;i<IndexedColors.values().length;i++){
//		       	XSSFColor color = new XSSFColor();
//		       	color.setIndexed(IndexedColors.values()[i].getIndex());
//		       	colorMaps.put(IndexedColors.values()[i].getIndex(), !Strings.isNullOrEmpty(color.getARGBHex())?color.getARGBHex().substring(2):"");
//		       	System.out.println("getARGBHex:"+color.getARGBHex());
//	
//		 }
//		 return colorMaps;
//	}
	private static String top="";
	private static String left="";
	private static String right="";
	private static String bottom="";
	private static String bg_color="";
//	public  static Map<Short, String> getColorMap(){
//		 Map<Short,String> colorMaps = Maps.newHashMap();
//		 for(int i=0;i<IndexedColors.values().length;i++){
//		       	XSSFColor color = new XSSFColor();
//		       	color.setIndexed(IndexedColors.values()[i].getIndex());
//		       	colorMaps.put(IndexedColors.values()[i].getIndex(), !Strings.isNullOrEmpty(color.getARGBHex())?color.getARGBHex().substring(2):"");
//		       	System.out.println("getARGBHex:"+color.getARGBHex());
//	
//		 }
//		 return colorMaps;
//	} 
//	public ExcelToHtmlUtil() {
//		// TODO Auto-generated constructor stub
////		   for(int i=0;i<IndexedColors.values().length;i++){
////		    	XSSFColor color = new XSSFColor();
////		    	color.setIndexed(IndexedColors.values()[i].getIndex());
////		    	colorMap.put(IndexedColors.values()[i].getIndex(), !Strings.isNullOrEmpty(color.getARGBHex())?color.getARGBHex().substring(2):"");
////		    }
//	}
	public static String getFileNameByInternationnal(String fileName ,String locale){
		int splitIndex = fileName.indexOf(".xls");
		String startString = fileName.substring(0, splitIndex);
		String end = fileName.substring(splitIndex);
		
		if(locale.equals( "en_US")){
			fileName = startString+"_US"+end;
		}else if(locale.equals("zh_CN")){
			fileName = startString+"_CN"+end;
		}else{
			fileName=startString+end;
		}
		return fileName;
	}
	/**
	 * 根据文件的路径创建Workbook对象
	 * 
	 * @param filePath
	 */
	public static Workbook getExcelWorkBook(String filePath) {
		InputStream ins = null;
		Workbook book = null;
		try {
			ins = new FileInputStream(new File(filePath));
			// ins=
			// ExcelService.class.getClassLoader().getResourceAsStream(filePath);
			book = WorkbookFactory.create(ins);
			ins.close();
			return book;
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ins != null) {
				try {
					ins.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	/**
	 * 执行SQL匹配
	 */
	public static String getPreSQL(Row row){
		String preSQL="";
		String preSQLpattern = "执行SQL_*[0-9]{0,}$";
		Pattern pre = Pattern.compile(preSQLpattern);
		Matcher preM = pre.matcher(row.getCell(0).toString());
		if (preM.find()) {
			if (!Strings.isNullOrEmpty(row.getCell(1).toString())) {
				preSQL += row.getCell(1).toString() + ";";
			}
		}
		return preSQL;
	}
	/**
	 * 模板基础参数解析
	 * @param sheet
	 * @param rownum
	 * @return
	 */
	public static Map<String,String> getExcelAnalyze(Sheet sheet,Integer rownum){
		String preSQL = "";// 查询钱执行sql
		String querySQL = "";// 查询sql
		String queryTag = "";// queryTag表示输出结果是data还是datasum
		String queryParamaters = "";
		String queryConvert = "";
		String templateName="";
		String totalColumnNum="";
		String heads="";
		String tails="";
		String details="";
		String pagination="";
		System.out.println("rowNUm:"+rownum);
		Map<String,String> result = Maps.newHashMap();
			for (int i = 1; i <= rownum; i++) {
				Row row = sheet.getRow(i);
				if(row==null){
					continue;
				}
				
				/**
				 * 执行SQL匹配
				 */
				String preSQLpattern = "PRESQL_*[0-9]{0,}$";
				Pattern pre = Pattern.compile(preSQLpattern);
				Matcher preM = pre.matcher(row.getCell(0).toString());
				if (preM.find()) {
					if (!Strings.isNullOrEmpty(row.getCell(2).toString())) {
						preSQL += row.getCell(2).toString() + ";";
					}
				}
				/**
				 * 查询sql匹配
				 */
				String querySQLpattern = "QUERYSQL_*[0-9]{0,}$";
				// 创建 Pattern 对象
				Pattern query = Pattern.compile(querySQLpattern);
				// 现在创建 matcher 对象
				Matcher queryM = query.matcher(row.getCell(0).toString());
				if (queryM.find()) {
					if (!Strings.isNullOrEmpty(row.getCell(2).toString())) {
						querySQL += row.getCell(2).toString() + ";";
						queryParamaters += row.getCell(5)==null ? ""+";" : row.getCell(5).toString() + ";";
						queryTag += row.getCell(3).toString() + ";";
						queryConvert += row.getCell(6) != null ? row.getCell(6).toString()+";" : "" + ";";
					}
				}

				/**
				 * 配置页基本选项配置
				 */
				if (row.getCell(0).toString().equals("PAGINATION")) {
					pagination =row.getCell(2).toString();
				}
				if (row.getCell(0).toString().equals("TABLENAME")) {
					templateName =row.getCell(2).toString();
				}
				if (row.getCell(0).toString().equals("TABLEHEADS")) {
					heads = row.getCell(2).toString();
				}
				if (row.getCell(0).toString().equals("TOTALCOLUMNS")) {
					totalColumnNum=row.getCell(2).toString();
				}
				if (row.getCell(0).toString().equals("TABLEDATAS")) {
					details = row.getCell(2).toString();
				}
				if (row.getCell(0).toString().equals("TABLETAILS")) {
					tails = row.getCell(2).toString();
				}

			}
			result.put("pagination", pagination);
			result.put("preSQL", preSQL);
			result.put("querySQL", querySQL);
			result.put("queryTag", queryTag);
			result.put("queryParamaters", queryParamaters);
			result.put("templateName", templateName);
			result.put("totalColumnNum", totalColumnNum);
			result.put("heads", heads);
			result.put("tails", tails);
			result.put("details", details);
			result.put("queryConvert", queryConvert);
			return result;
	}

	/**
	 * 解析起始行
	 * 
	 * @param details
	 * @return
	 */
	public static int paraseHead(String details) {
		List<String> headsNum = Arrays.asList(details.split(","));
		int headsHead = (int) Math.floor(Double.parseDouble(headsNum.get(0)));
		return headsHead;
	}

	/**
	 * 解析结束行
	 * 
	 * @param details
	 * @return
	 */
	public static int paraseTail(String details) {
		List<String> headsNum = Arrays.asList(details.split(","));
		int headsTail = (int) Math.floor(Double.parseDouble(headsNum.get(headsNum.size() - 1)));
		return headsTail;
	}
	/**
	 * 带小数位数的String类型转为int
	 * @param value
	 * @return
	 */
	public static int paraseString(String value){
		Integer result = 0;
		if(!Strings.isNullOrEmpty(value)){
			 result = (int) Math.floor(Double.parseDouble(value));
			
		}
		return result;
	}
	/**
	 * 返回所有合并单元格对象
	 * @param sheet
	 * @return
	 */
	public static List<CellRangeAddress> getMergeRegons(Sheet sheet){
		List<CellRangeAddress> addresses = Lists.newArrayList();
		// 遍历合并单元格
		int sheetmergerCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetmergerCount; i++) {
			// 获得合并单元格加入list中
			CellRangeAddress ca = sheet.getMergedRegion(i);
			addresses.add(ca);
		}
		return addresses;
	}
	/**
	 * 读取内容上下排版
	 * @param align
	 * @return
	 */
	public static String getVerticalAlign(Short align){
		switch (align) {
		case 0:
			return "top";
		case 1:
			return "center";
		case 2:
			return "bottom";
		case 3:
			return "justify";
		default:
			return "center";
		}
	}
	/**
	 * 读取内容左右排版
	 * @param align
	 * @return
	 */
	public static String getHorizontalAlign(Short align){
		switch (align) {
		case 1:
			return "left";
		case 2:
			return "center";
		case 3:
			return "right";
		case 5:
			return "justify";
		default:
			return "center";
		}
	}
	/**
	 * 把enum类型转为用序列号为Key，颜色的RGB值为value的map对象
	 * @return
	 */
	public static String getColorRGB(Short colorIndex){
//		Map<Short,String> colorMap=Maps.newHashMap();
//		 for(int i=0;i<IndexedColors.values().length;i++){
//		       	XSSFColor color = new XSSFColor();
//		       	color.setIndexed(IndexedColors.values()[i].getIndex());
//		       	colorMap.put(IndexedColors.values()[i].getIndex(), !Strings.isNullOrEmpty(color.getARGBHex())?color.getARGBHex().substring(2):"");
//		       	System.out.println(color.getARGBHex());
//		       }
//		colorMap = getColorMap();
//		 byte[] rgb = null;
//		String colorRGB = colorMap.get(colorIndex);
		if(colorIndex>0&&colorIndex!=null){
			HSSFColor indexed = (HSSFColor) HSSFColor.getIndexHash().get((int)colorIndex);
			if(indexed!=null){
				String rgbhex = getARGBHex(indexed);
				System.out.println(rgbhex);
				return "#"+rgbhex.substring(2);
			}else{
				return "";
			}
		}else{
			return "";
		}
//		if(Strings.isNullOrEmpty(colorRGB)||"null".equals(colorRGB)){
//			return "";
//		}else{
//			return "#"+colorMap.get(colorIndex);
//		}
	}
	/**
	 * 获取颜色
	 * @param indexed
	 * @return
	 */
    public static String getARGBHex(HSSFColor indexed) {
        StringBuffer sb = new StringBuffer();
        byte[] rgb = getARgb(indexed);
        if(rgb == null) {
           return null;
        }
        for(byte c : rgb) {
           int i = (int)c;
           if(i < 0) {
              i += 256;
           }
           String cs = Integer.toHexString(i);
           if(cs.length() == 1) {
              sb.append('0');
           }
           sb.append(cs);
        }
        return sb.toString().toUpperCase();
     }
    public static byte[] getARgb(HSSFColor indexed) {
        byte[] rgb = getRGBOrARGB(indexed);
        if(rgb == null) return null;

        if(rgb.length == 3) {
           // Pad with the default Alpha
           byte[] tmp = new byte[4];
           tmp[0] = -1;
           System.arraycopy(rgb, 0, tmp, 1, 3);
           return tmp;
        } else {
           return rgb;
        }
     }
    private static byte[] getRGBOrARGB(HSSFColor indexed) {
        byte[] rgb = null;

               rgb = new byte[3];
               rgb[0] = (byte) indexed.getTriplet()[0];
               rgb[1] = (byte) indexed.getTriplet()[1];
               rgb[2] = (byte) indexed.getTriplet()[2];
               return rgb;

         // Grab the colour
    }
	/**
	 * 对一行的每个单元格设置边框颜色、宽度、背景颜色
	 * @param j
	 * @param k
	 * @param sheet
	 * @param htmlRowStructures
	 */
	public static void setColor(Integer j,Integer k,Sheet sheet1,List<HtmlRowStructure> htmlRowStructures){
		if(sheet1 instanceof XSSFSheet){
			XSSFSheet sheet = (XSSFSheet)sheet1;
			if(sheet.getRow(j).getCell(k)!=null){
				top=!Strings.isNullOrEmpty(String.valueOf(((Short)sheet.getRow(j).getCell(k).getCellStyle().getBorderTop())))?String.valueOf(((Short)sheet.getRow(j).getCell(k).getCellStyle().getBorderTop())):top;
				left= !Strings.isNullOrEmpty(String.valueOf(((Short)sheet.getRow(j).getCell(k).getCellStyle().getBorderLeft())))?String.valueOf(((Short)sheet.getRow(j).getCell(k).getCellStyle().getBorderLeft())):left;
				right = !Strings.isNullOrEmpty(String.valueOf(((Short)sheet.getRow(j).getCell(k).getCellStyle().getBorderRight())))?String.valueOf(((Short)sheet.getRow(j).getCell(k).getCellStyle().getBorderRight())):right;
				bottom = !Strings.isNullOrEmpty(String.valueOf(((Short)sheet.getRow(j).getCell(k).getCellStyle().getBorderBottom())))?String.valueOf(((Short)sheet.getRow(j).getCell(k).getCellStyle().getBorderBottom())):bottom;
				bg_color = getColorRGB(sheet.getRow(j).getCell(k).getCellStyle().getFillForegroundColor());
				htmlRowStructures.get(j).getHtmlTdStructure().get(k).setBorder_top(top);
				htmlRowStructures.get(j).getHtmlTdStructure().get(k).setBorder_left(left);
				htmlRowStructures.get(j).getHtmlTdStructure().get(k).setBorder_right(right);
				htmlRowStructures.get(j).getHtmlTdStructure().get(k).setBorder_bottom(bottom);
				htmlRowStructures.get(j).getHtmlTdStructure().get(k).setBg_color(bg_color);
				htmlRowStructures.get(j).getHtmlTdStructure().get(k).setFont_color(!Strings.isNullOrEmpty(getColorRGB(sheet.getRow(j).getCell(k).getCellStyle().getFont().getColor()))?getColorRGB(sheet.getRow(j).getCell(k).getCellStyle().getFont().getColor()):"");
				htmlRowStructures.get(j).getHtmlTdStructure().get(k).setFont_size(String.valueOf(((Short)sheet.getRow(j).getCell(k).getCellStyle().getFont().getFontHeightInPoints())));
				htmlRowStructures.get(j).getHtmlTdStructure().get(k).setIsBold(sheet.getRow(j).getCell(k).getCellStyle().getFont().getBold());
				htmlRowStructures.get(j).getHtmlTdStructure().get(k).setFontFamily(sheet.getRow(j).getCell(k).getCellStyle().getFont().getFontName());
				htmlRowStructures.get(j).getHtmlTdStructure().get(k).setHorizontalAlign(getHorizontalAlign(sheet.getRow(j).getCell(k).getCellStyle().getAlignment()));
				htmlRowStructures.get(j).getHtmlTdStructure().get(k).setVerticalAlign(getVerticalAlign(sheet.getRow(j).getCell(k).getCellStyle().getVerticalAlignment()));
			}else{
				htmlRowStructures.get(j).getHtmlTdStructure().get(k).setBorder_top(top);
				htmlRowStructures.get(j).getHtmlTdStructure().get(k).setBorder_left(left);
				htmlRowStructures.get(j).getHtmlTdStructure().get(k).setBorder_right(right);
				htmlRowStructures.get(j).getHtmlTdStructure().get(k).setBorder_bottom(bottom);
				htmlRowStructures.get(j).getHtmlTdStructure().get(k).setBg_color(bg_color);
			}
		}else if(sheet1 instanceof HSSFSheet){
			
		}
	
//		htmlRowStructures.get(j).getHtmlTdStructure().get(k).setBorder_top_color(getColorRGB(sheet.getRow(j).getCell(k).getCellStyle().getTopBorderColor()));
//		htmlRowStructures.get(j).getHtmlTdStructure().get(k).setBorder_left_color(getColorRGB(sheet.getRow(j).getCell(k).getCellStyle().getLeftBorderColor()));
//		htmlRowStructures.get(j).getHtmlTdStructure().get(k).setBorder_right_color(getColorRGB(sheet.getRow(j).getCell(k).getCellStyle().getRightBorderColor()));
//		htmlRowStructures.get(j).getHtmlTdStructure().get(k).setBorder_bottom_color(getColorRGB(sheet.getRow(j).getCell(k).getCellStyle().getBottomBorderColor()));
	
	}
	/**
	 * 解析系统设置excel模板
	 * @param sheet
	 * @param rownum
	 */
	public static Map<String,String> analyzeSystemSetting(Sheet sheet,Integer rownum){
		Map<String,String> result = Maps.newHashMap();
		String showType = "";
		String detailSetSheet = "";
		String detailTemplateSheet = "";
		String statisticSetSheet = "";
		String statisticTemplateSheet = "";
		String querySheet = "";
		String queryFromName="";
		for (int i = 0; i < rownum; i++) {
			Row row = sheet.getRow(i);
			if(row==null){
				continue;
			}
			if (row.getCell(0).toString().equals("SHOWTYPE")) {
				showType =row.getCell(2).toString();
			}
			if (row.getCell(0).toString().equals("DETAILSETSHEET")) {
				if(row.getCell(2)!=null){
					detailSetSheet =row.getCell(2).toString();
				}
			}
			if (row.getCell(0).toString().equals("DETAILTEMPLATESHEET")) {
				if(row.getCell(2)!=null){
				detailTemplateSheet =row.getCell(2).toString();
				}
			}
			if (row.getCell(0).toString().equals("STATISTICSETSHEET")) {
				if(row.getCell(2)!=null){
				statisticSetSheet =row.getCell(2).toString();
				}
			}
			if (row.getCell(0).toString().equals("STATISTICTEMPLATESHEET")) {
				if(row.getCell(2)!=null){
				statisticTemplateSheet =row.getCell(2).toString();
				}
			}
			if (row.getCell(0).toString().equals("QUERYFORMNAME")) {
				if(row.getCell(2)!=null){
					queryFromName =row.getCell(2).toString();
				}
			}
			if (row.getCell(0).toString().equals("QUERYSHEET")) {
				if(row.getCell(2)!=null){
				querySheet =row.getCell(2).toString();
				}else{
					System.out.println("未设置excel查询页码");
				}
			}
		}
		result.put("showType", showType);
		result.put("detailSetSheet", detailSetSheet);
		result.put("detailTemplateSheet", detailTemplateSheet);
		result.put("statisticSetSheet",statisticSetSheet);
		result.put("statisticTemplateSheet", statisticTemplateSheet);
		result.put("querySheet", querySheet);
		result.put("queryFromName", queryFromName);
		return result;
	}
}
