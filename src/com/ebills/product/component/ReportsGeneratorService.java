package com.ebills.product.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.bussprocess.context.Context;
import org.apache.commons.bussprocess.exception.InvalidArgumentException;
import org.apache.commons.bussprocess.exception.ObjectNotFoundException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.eap.core.EAPConstance;
import com.ebills.product.action.excel.ExcelCreateFormAction;
import com.ebills.product.action.excel.entity.ExcelForm;
import com.ebills.product.action.excel.entity.ExcelFormEntity;
import com.ebills.product.action.excel.entity.ExcelInputType;
import com.ebills.product.action.excel.entity.HtmlRowStructure;
import com.ebills.product.action.excel.entity.HtmlStructure;
import com.ebills.product.action.excel.entity.HtmlTdStructure;
import com.ebills.product.utils.ExcelToHtmlUtil;
import com.ebills.product.utils.OrcleConnectManager;
import com.ebills.product.utils.Page;
import com.google.common.base.Strings;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;

/**
 * @author Xiao He E-mail:hxtoyou@163.com
 * @version 创建时间：2015年6月4日 下午4:48:18
 * 
 */
public class ReportsGeneratorService implements ReportsGenerator<Context>{
	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation"})
	@Override
	public Context generateReport(Context context) {

//		HttpServletResponse response = (HttpServletResponse)context.getValue(EAPConstance.SERVLET_REQSPONSE);

		HttpServletRequest request = null;
		try {
			request = (HttpServletRequest)context.getValue(EAPConstance.SERVLET_REQUEST);
		} catch (ObjectNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvalidArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String fileName = request.getParameter("templateName");//模板名称
		String locale = request.getParameter("locale");
        //将要被返回到客户端的对象  
		String path = request.getRealPath(ExcelCreateFormAction.EXCEL_TEMPLATE_PATH);
		String realPath = "";
		String fileRealName = ExcelToHtmlUtil.getFileNameByInternationnal(fileName, locale);
		
		realPath = path+"/"+fileRealName;
		Workbook workbook = ExcelToHtmlUtil.getExcelWorkBook(realPath);
		/**
		 * 解析excel系统设置模板配置信息
		 */
		Sheet sheet = workbook.getSheetAt(0);//系统设置模板
		int systtemSetrownum = sheet.getLastRowNum();
		Map<String,String> systemSetResult = ExcelToHtmlUtil.analyzeSystemSetting(sheet, systtemSetrownum);
		Integer detailSetSheet = ExcelToHtmlUtil.paraseString(systemSetResult.get("detailSetSheet"));//明细配置配置页码
		Integer detailTemplateSheet = ExcelToHtmlUtil.paraseString(systemSetResult.get("detailTemplateSheet"));//明细格式模板页码
		Integer statisticSetSheet = ExcelToHtmlUtil.paraseString(systemSetResult.get("statisticSetSheet"));//统计配置模板页码
		Integer statisticTemplateSheet = ExcelToHtmlUtil.paraseString(systemSetResult.get("statisticTemplateSheet"));//统计格式模板页码
		Integer querySheet = ExcelToHtmlUtil.paraseString(systemSetResult.get("querySheet"));//查询参数模板页码
		String showType = systemSetResult.get("showType");//模板类型
		List<String> types  = Arrays.asList(showType.split("&"));
		Integer setSheet = null;//设置模板页码
		Integer templateSheet = null;//样式模板页码
		if(types.size()<1){
			System.out.println("未设置模板信息");
			return null;
		}
		context.put("templateType", showType);
		String templateType = request.getParameter("templateType");//获取模板类型参数
		System.out.println("templateType:"+templateType);
		Page page = new Page();
		if(Strings.isNullOrEmpty(templateType)){
			templateType = types.get(0);
		}
		
		//根据模板类型参数选择模板
		if(templateType.equals("detail")){
			setSheet = detailSetSheet;
			templateSheet = detailTemplateSheet;
		}else if(templateType.equals("statistic")){
			setSheet = statisticSetSheet;
			templateSheet = statisticTemplateSheet;
			
		}else{
			System.out.println("模板类型设置错误");
			return null;
		}
		if(setSheet<1||templateSheet<1||querySheet<1){
			System.out.println("模板类型设置错误");
			return null;
		}
		Sheet sheet1 = workbook.getSheetAt(setSheet-1);//配置sheet
		Sheet sheet2 = workbook.getSheetAt(templateSheet-1);//模板sheet
		Sheet sheet3 = workbook.getSheetAt(querySheet-1);//查询参数sheet
		List<ExcelForm> forms =getExcelForm(sheet3);
		
	
		/**
		 * 模板配置页面条件设置sheet1
		 */
		int cellnum = 0;
		int rownum = sheet1.getLastRowNum();
		Map<String,String> analyzeResult = ExcelToHtmlUtil.getExcelAnalyze(sheet1, rownum);
		String querySQL =  analyzeResult.get("querySQL");// 查询sql
		String queryTag =  analyzeResult.get("queryTag");// queryTag表示输出结果是data还是datasum
		String pagination =  analyzeResult.get("pagination");//是否分页
		String totalColumnNum= analyzeResult.get("totalColumnNum");//总列数
		String heads= analyzeResult.get("heads");//数据头所占行
		String tails= analyzeResult.get("tails");//数据尾所占行
		String details= analyzeResult.get("details");//数据体所占行
		List<CellRangeAddress> addresses =ExcelToHtmlUtil.getMergeRegons(sheet2);//所有合并单元格
		cellnum = (int) Math.floor(Double.parseDouble(totalColumnNum));// 获取中列数
		/**
		 * 根据是否分页设置查询数据结果的多少，不分页最多显示1000条数据
		 */
		if(pagination.equals("YES")){
			if(!Strings.isNullOrEmpty(request.getParameter("pageSize"))){
				Integer pageSize  = Integer.parseInt(request.getParameter("pageSize"));
				page.setPageSize(pageSize);
			}else{
				page.setPageSize(10);
			}
			if(!Strings.isNullOrEmpty(request.getParameter("pageNumber"))){
				Integer pageNumber =Integer.parseInt(request.getParameter("pageNumber"));
				page.setPageNumber(pageNumber);
			}else{
				page.setPageNumber(1);
			}
		}else if(pagination.equals("NO")){
			page.setPageSize(1000);
			page.setPageNumber(1);
		}else{
			System.out.println("模板类型设置错误");
			return null;
		}
		
		// 获取查询sql
		List<String> querySqlList = Lists.newArrayList();
		if (!Strings.isNullOrEmpty(querySQL)) {
			querySqlList = Arrays.asList(querySQL.split(";"));
		}
		List<String> queryTagList = Lists.newArrayList();
		if (!Strings.isNullOrEmpty(queryTag)) {

			queryTagList = Arrays.asList(queryTag.split(";"));
		}
		// 获取详细内容行数
		int detailHead = ExcelToHtmlUtil.paraseHead(details);// 起始位置
		int detailTail = ExcelToHtmlUtil.paraseTail(details);// 结束位置
		int copyRowNum = detailTail - detailHead + 1;// 源数据（详细内容）行数
		// 每页显示的内容条数
		
		int pageViewNum = (page.getPageSize() / copyRowNum) < 1 ? 1 : (page.getPageSize() / copyRowNum);
		// 显示数据的起始位置
		int dataStartIndex = pageViewNum * (page.getPageNumber()-1);
		// 获取表头行数
		int headsTail = ExcelToHtmlUtil.paraseTail(heads);
		// 获取表尾行数
		int tailRowNum = 0;
		if(!Strings.isNullOrEmpty(tails)){
			int tailHead = ExcelToHtmlUtil.paraseHead(tails);
			int tailTail = ExcelToHtmlUtil.paraseTail(tails);
			tailRowNum = tailTail - tailHead + 1;//
		}
		
		// table模板的行数
		int trueRowNum = headsTail + copyRowNum + tailRowNum;// 排除空行后的table结构的真实行数
		int contentEnd = trueRowNum - tailRowNum + copyRowNum * pageViewNum - copyRowNum;// 加上N条源数据后详细内容加上表头的行数
		int detailLineNum = contentEnd - headsTail;// 详细内容行数
		
		
		Map<String,String> params = Maps.newHashMap();
		Table<String,String,String> paramValues = HashBasedTable.create();
		//解析查询模板中的查询条件
		for(ExcelForm form: forms){
			try {
					paramValues.put(form.getVarName(),form.getClassType(),request.getParameter(form.getVarName()));
					params.put(form.getVarName(),request.getParameter(form.getVarName()));
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		// 遍历合并单元格，标记被合并的单元格
		HtmlStructure htmlStructure = createByRegin(addresses, null, null, null, trueRowNum, cellnum);
		// 向单元格填充结构数据并返回宽度
		Double tableWidth = fillStructureData(htmlStructure, trueRowNum, cellnum, sheet2);
		// table宽度
		htmlStructure.setTableWidth(tableWidth / trueRowNum);
		//删除被合并的单元格
		tableStyle(htmlStructure, addresses, null, null,
				trueRowNum - 1, rownum, cellnum);
		//根据要显示的数据行数扩展htmlStructure对象行数
		expendHtmlStructure(htmlStructure, copyRowNum, pageViewNum, detailHead, detailTail);
		//执行sql返回数据
		OrcleConnectManager baseDAO = new OrcleConnectManager();
		page = getResult(page,htmlStructure, querySqlList, queryTagList, baseDAO, params, dataStartIndex, pageViewNum, detailLineNum, copyRowNum, headsTail,tailRowNum,pagination,paramValues);
		page.setPageSize(pageViewNum);
		Map<String,String> resultMap = Maps.newHashMap();
		try {
			 ObjectMapper objectMapper = new ObjectMapper();
			 String pageResult =  objectMapper.writeValueAsString(page);
			 resultMap.put("data",pageResult);
			 resultMap.put("showType",showType);
			 resultMap.put("tableTitle", String.valueOf(headsTail));
			 resultMap.put("pagination",pagination);
			 String resultOut = objectMapper.writeValueAsString(resultMap);
			 context.put("output", resultOut);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
			return context;  
	}
	/**
	 * 执行sql填充数据
	 * @param htmlStructure
	 * @param querySqlList
	 * @param queryTagList
	 * @param baseDAO
	 * @param params
	 * @param dataStartIndex
	 * @param pageViewNum
	 * @param map
	 * @param detailLineNum
	 * @param copyRowNum
	 * @param headsTail
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public  Page<HtmlStructure> getResult(Page<HtmlStructure> page,HtmlStructure htmlStructure, List<String> querySqlList,List<String> queryTagList,OrcleConnectManager baseDAO,Map params,Integer dataStartIndex,Integer pageViewNum,Integer detailLineNum,Integer copyRowNum,Integer headsTail,Integer tailRowNum,String pagination,Table paramValues ){
		int totalSize = 0;
		int resultSize = 0;
		for (int y = 0; y < querySqlList.size(); y++) {
			if (!Strings.isNullOrEmpty(querySqlList.get(y))) {
				String resultTag = queryTagList.get(y);
				List<Map<String,String>> results = Lists.newArrayList();
				List<Map<String,String>> totalResults = Lists.newArrayList();
				String sql="";
				String totalSql;
				// 如果是内容项，则数据库分页查询
				if(pagination.equals("YES")){
					if (resultTag.matches(".*DATA_.*")) {
						totalSql = "SELECT COUNT(*) FROM(" + querySqlList.get(y) + ")";
						totalResults = baseDAO.queryBySql(totalSql,paramValues,resultTag);
						for(Map.Entry<String, String> entry:totalResults.get(0).entrySet()){
							if(totalSize<Integer.parseInt(entry.getValue())){//取最大的totalSize
								totalSize = Integer.parseInt(entry.getValue());
							}
						}
						sql = "SELECT * FROM(SELECT A.*, rownum r FROM (" + querySqlList.get(y) + ") A WHERE rownum <="
								+ (dataStartIndex + pageViewNum) + ") B WHERE r >" + dataStartIndex;
						results = baseDAO.queryBySql(sql, paramValues,resultTag);
					}else if(resultTag.matches(".*DATASUM_.*")){
						sql = querySqlList.get(y);
						results = baseDAO.queryBySql(sql, paramValues,resultTag);
					}
				}else if(pagination.equals("NO")){
					sql = querySqlList.get(y);
					results = baseDAO.queryBySql(sql, paramValues,resultTag);
					totalSql = "SELECT COUNT(*) FROM(" + querySqlList.get(y) + ")";
					totalResults = baseDAO.queryBySql(totalSql,paramValues,resultTag);
					for(Map.Entry<String, String> entry:totalResults.get(0).entrySet()){
						if(totalSize<Integer.parseInt(entry.getValue())){//取最大的totalSize
							totalSize = Integer.parseInt(entry.getValue());
						}
					}
				}
				if(resultSize<results.size()){
					resultSize=results.size();
				}
				// 谈查询结果只有一行的时候（大多数时候是统计查询），则直接遍历所有excel单元格向里面填充数据
				if (results.size() <= 1 && results.size() > 0) {
					inputStatisticData(results, htmlStructure,copyRowNum,headsTail,tailRowNum);
				}else if(results.size()>1){
					inputData(detailLineNum, copyRowNum, headsTail, results, pageViewNum, htmlStructure,tailRowNum);
				}
			}
		}
		Integer delRowNum = resultSize*copyRowNum+headsTail;
		int size = htmlStructure.getHtmlRowStructure().size();
		Iterator<HtmlRowStructure> rowIter = htmlStructure.getHtmlRowStructure().iterator();
		int rowCount = 0;
		while (rowIter.hasNext()) {
			rowCount++;
			rowIter.next();
			if(totalSize==0){
				if (rowCount>(delRowNum+3)&&rowCount<(size-tailRowNum+1)) {
					rowIter.remove();// 这里要使用Iterator的remove方法移除当前对象，如果使用List的remove方法，则同样会出现ConcurrentModificationException
				}
			}else {
				if (rowCount>delRowNum&&rowCount<(size-tailRowNum+1)) {
					rowIter.remove();// 这里要使用Iterator的remove方法移除当前对象，如果使用List的remove方法，则同样会出现ConcurrentModificationException
				}
			}
		}
		page.setTotalCount(totalSize);
		page.setPageSize(pageViewNum);
		List<HtmlStructure> resultHtmlStructure = Lists.newArrayList();
		resultHtmlStructure.add(htmlStructure);
		page.setResult(resultHtmlStructure);
		return page;
	}
	/**
	 * 填充统计数据
	 * @param results
	 * @param htmlStructure
	 * @param map
	 */
	public void inputStatisticData(List<Map<String,String>>  results ,HtmlStructure htmlStructure,Integer copyRowNum,Integer headsTail,Integer tailRowNum){
		for (HtmlRowStructure addRowData : htmlStructure.getHtmlRowStructure()) {
				for (HtmlTdStructure tdStructure : addRowData.getHtmlTdStructure()) {
					for (Map.Entry<String, String> entry : results.get(0).entrySet()) {// 取出第n条数据
						if (tdStructure.getValue() != null) {
							if (tdStructure.getValue().equals("{" + entry.getKey() + "}")) {
									tdStructure.setValue(entry.getValue());
							}
						}
				}
			}
		}
	}
	/**
	 * 填充主数据
	 * @param detailLineNum
	 * @param copyRowNum
	 * @param headsTail
	 * @param results
	 * @param pageViewNum
	 * @param htmlStructure
	 * @param map
	 */
	public void inputData(Integer detailLineNum,Integer copyRowNum,Integer headsTail,List<Map<String,String>>  results, Integer pageViewNum ,HtmlStructure htmlStructure,Integer tailRowNum){
		int n = 0;
		int l = 0;
		for (int q = 0; q < detailLineNum / copyRowNum; q++) {// 取N条data,如果表格的源数据是N行，那么当一行处理只取一条数据
			if (results.size() - n >= (pageViewNum - q)) {// 当结果集不能填满整个表格的时候判断
				for (int a = 0; a < copyRowNum; a++) {
					HtmlRowStructure addRowData = htmlStructure.getHtmlRowStructure().get(headsTail + l * copyRowNum + a);// 取出一行的表格结构数据
					List<HtmlTdStructure> htmlTdStructures = addRowData.getHtmlTdStructure();
					for (Map.Entry<String, String> entry : results.get(n).entrySet()) {// 取出第n条数据
						for (int s = 0; s < htmlTdStructures.size(); s++) {
								if (!Strings.isNullOrEmpty(htmlTdStructures.get(s).getValue())) {
								if (htmlTdStructures.get(s).getValue().equals("{" + entry.getKey() + "}")) {
										htmlTdStructures.get(s).setValue(entry.getValue());
								}
							}
						}
					}
				}
				n++;
				l++;
			}
		}
	}
	/**
	 * 填充结构数据
	 * @param htmlStructure
	 * @param trueRowNum
	 * @param cellnum
	 * @param sheet
	 * @return
	 */
	public  Double fillStructureData(HtmlStructure htmlStructure,Integer trueRowNum,Integer cellnum,Sheet sheet){
		Double tableWidth =0d;
		List<HtmlRowStructure> htmlRowStructures =htmlStructure.getHtmlRowStructure();
		for (int j = 0; j < trueRowNum; j++) {
			if (sheet.getRow(j) != null) {
				for (int k = 0; k < cellnum; k++) {
					String value = "";
					if (sheet.getRow(j).getCell(k) != null&&!"null".equals(sheet.getRow(j).getCell(k))) {
						try {
							value = sheet.getRow(j).getCell(k).getRichStringCellValue().toString();
						} catch (IllegalStateException e) {
							value = String.valueOf(sheet.getRow(j).getCell(k).getNumericCellValue());
							// TODO: handle exception
						}
					}else if("null".equals(sheet.getRow(j).getCell(k))){
						value="";
					}
					else{
						value="";
					}
					ExcelToHtmlUtil.setColor(j, k, sheet, htmlRowStructures);//设置颜色、边框、背景色
					Short height = sheet.getRow(j).getHeight();
					htmlRowStructures.get(j).getHtmlTdStructure().get(k).setValue((Strings.isNullOrEmpty(value.trim())||"null".equals(value.trim()))?"":value);
					htmlRowStructures.get(j).getHtmlTdStructure().get(k)
							.setHeight(String.valueOf((height / 20) * 1.2368));
					htmlRowStructures.get(j).getHtmlTdStructure().get(k)
							.setWidth(String.valueOf((sheet.getColumnWidth(k) / 256) * 7.425));
					tableWidth += (sheet.getColumnWidth(k) / 256) * 7.425;
				}
			}
		}
		return tableWidth;
	}
	/**
	 * 返回所有合并单元格对象
	 * @param sheet
	 * @return
	 */
	public  List<CellRangeAddress> getMergeRegons(Sheet sheet){
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
	 * 根据要显示的页数扩展html机构对象
	 * @param htmlStructure
	 * @param copyRowNum
	 * @param pageViewNum
	 * @param detailHead
	 * @param detailTail
	 * @return
	 */
	public  List<HtmlRowStructure> expendHtmlStructure(HtmlStructure htmlStructure, Integer copyRowNum,Integer pageViewNum,Integer detailHead,Integer detailTail){
		List<HtmlRowStructure> htmlRowStructures = htmlStructure.getHtmlRowStructure();
		List<HtmlRowStructure> copyRow = htmlRowStructures.subList(detailHead - 1, detailTail);
		// 复制内容行的表格结构
		List<HtmlRowStructure> pasteRow = new ArrayList<HtmlRowStructure>(copyRowNum * pageViewNum);
		for (int e = 1; e < pageViewNum; e++) {
			try {
				for (HtmlRowStructure rowStructure : copyRow) {
					HtmlRowStructure copyStructure = new HtmlRowStructure();
					List<HtmlTdStructure> tdS = Lists.newArrayList();
					for (HtmlTdStructure tdStructure : rowStructure.getHtmlTdStructure()) {
						HtmlTdStructure copyTdStructure = new HtmlTdStructure();
						copyTdStructure.setColspan(tdStructure.getColspan());
						copyTdStructure.setRowspan(tdStructure.getRowspan());
						copyTdStructure.setTag(tdStructure.getTag());
						copyTdStructure.setValue(tdStructure.getValue());
						copyTdStructure.setHeight(tdStructure.getHeight());
						copyTdStructure.setWidth(tdStructure.getWidth());
						copyTdStructure.setBg_color(tdStructure.getBg_color());
						copyTdStructure.setBorder_bottom(tdStructure.getBorder_bottom());
						copyTdStructure.setBorder_bottom_color(tdStructure.getBorder_bottom_color());
						copyTdStructure.setBorder_left(tdStructure.getBorder_left());
						copyTdStructure.setBorder_left_color(tdStructure.getBorder_left_color());
						copyTdStructure.setBorder_right(tdStructure.getBorder_right());
						copyTdStructure.setBorder_right_color(tdStructure.getBorder_right_color());
						copyTdStructure.setBorder_top(tdStructure.getBorder_top());
						copyTdStructure.setBorder_top_color(tdStructure.getBorder_top_color());
						copyTdStructure.setFont_color(tdStructure.getFont_size());
						copyTdStructure.setFont_size(tdStructure.getFont_size());
						copyTdStructure.setIsBold(tdStructure.getIsBold());
						copyTdStructure.setFontFamily(tdStructure.getFontFamily());
						copyTdStructure.setVerticalAlign(tdStructure.getVerticalAlign());
						copyTdStructure.setHorizontalAlign(tdStructure.getHorizontalAlign());
						tdS.add(copyTdStructure);
					}
					copyStructure.setHtmlTdStructure(tdS);
					pasteRow.add(copyStructure);
					// pasteRow.add(rowStructure.clone());
				}
			} catch (Exception e2) {
				// TODO: handle exception
			} finally {
			}
		}
		htmlRowStructures.addAll(detailTail, pasteRow);
		return htmlRowStructures;
	}
	/**
	 * 根据表格合并情况设置整个表格每行的行数和列数，表格的合成情况在htmlTDstructure表中的tag标志位来表示是否被合并
	 * 
	 * @param addresses
	 *            所有合并项
	 * @param headers
	 *            表格头行数
	 * @param details
	 *            表格详细数据行数
	 * @param tails
	 *            表格统计栏行数
	 * @param row
	 *            表格总行数
	 * @param cells
	 *            表格总列行数
	 */
	public HtmlStructure createByRegin(List<CellRangeAddress> addresses, List<Integer> headers, List<Integer> details,
			List<Integer> tails, Integer row, Integer cell) {
		HtmlStructure htmlStructure = new HtmlStructure();
		htmlStructure.setRowNum(row);
		htmlStructure.setColumn(cell);
		// 初始化html表的数据结构
		List<HtmlRowStructure> htmlRowStructures = Lists.newArrayList();
		for (int k = 0; k <= row; k++) {
			HtmlRowStructure htmlStructureTemp = new HtmlRowStructure();
			List<HtmlTdStructure> htmlTdStructures = Lists.newArrayList();
			for (int h = 0; h < cell; h++) {
				HtmlTdStructure htmlTdStructureTemp = new HtmlTdStructure();
				htmlTdStructures.add(htmlTdStructureTemp);
			}
			htmlStructureTemp.setHtmlTdStructure(htmlTdStructures);
			htmlStructureTemp.setCellsNum(cell);
			htmlRowStructures.add(htmlStructureTemp);
		}
		// 标记被合并的单元格，计算每一行有多少个单元格
		for (int i = 0; i <= row; i++) {

			HtmlRowStructure htmlRowStructure = htmlRowStructures.get(i);
			int cellNum = htmlRowStructure.getCellsNum();
			for (CellRangeAddress address : addresses) {
				int firstColumn = address.getFirstColumn();
				int lastColumn = address.getLastColumn();
				int firstRow = address.getFirstRow();
				int lastRow = address.getLastRow();
				int reginRow = lastRow - firstRow;
				int reginCell = lastColumn - firstColumn;
				if (firstRow > i) {
					continue;
				} else if (firstRow == i) {
					if (reginCell > 0) {
						cellNum -= reginCell;
						if (reginCell > 0) {
							for (int y = 1; y <= reginCell; y++) {
								htmlRowStructures.get(i).getHtmlTdStructure().get(firstColumn + y).setTag(true);
							}
						}
					}
					// 但出现合并行的时候，下一行需要生成的表格最好减一，再加上合并的列数并且把被合并的单元格标记为true
					if (reginRow > 0) {
						for (int j = 1; j <= reginRow; j++) {
							int otherRowCellnum = htmlRowStructures.get(i + j).getCellsNum() - 1 - reginCell;
							htmlRowStructures.get(i + j).setCellsNum(otherRowCellnum);
							htmlRowStructures.get(i + j).getHtmlTdStructure().get(firstColumn).setTag(true);
							if (reginCell > 0) {
								for (int x = 1; x <= reginCell; x++) {
									htmlRowStructures.get(i + j).getHtmlTdStructure().get(firstColumn + x).setTag(true);
								}
							}
						}
					}
				} else {

				}
			}
			htmlRowStructure.setRowNum(i);
			htmlRowStructure.setCellsNum(cellNum);
			htmlRowStructures.set(i, htmlRowStructure);
		}
		htmlStructure.setHtmlRowStructure(htmlRowStructures);
		return htmlStructure;
	}

	/**
	 * 删除被标记的单元格
	 * 
	 * @param htmlStructure
	 * @param addresses
	 * @param headers
	 * @param details
	 * @param tails
	 * @param row
	 * @param cell
	 * @return
	 */
	public HtmlStructure tableStyle(HtmlStructure htmlStructure, List<CellRangeAddress> addresses,
			List<Integer> headers, List<Integer> details, int trueRowNum, Integer row, Integer cell) {
		List<HtmlRowStructure> htmlRowStructures = htmlStructure.getHtmlRowStructure();
		for (CellRangeAddress address : addresses) {
			int firstColumn = address.getFirstColumn();
			int lastColumn = address.getLastColumn();
			int firstRow = address.getFirstRow();
			int lastRow = address.getLastRow();
			int reginRow = lastRow - firstRow;
			int reginCell = lastColumn - firstColumn;
			List<HtmlTdStructure> tdStructures = htmlRowStructures.get(firstRow).getHtmlTdStructure();
			if (reginRow > 0) {
				tdStructures.get(firstColumn).setRowspan(reginRow + 1);
			}
			if (reginCell > 0) {
				tdStructures.get(firstColumn).setColspan(reginCell + 1);
			}
		}
		// 根据表尾行数，删除剩余的row
		int tailsize = trueRowNum + 1;
		int rowSize = htmlRowStructures.size();
		for (int delRow = tailsize; delRow < rowSize; delRow++) {
			htmlRowStructures.remove(tailsize);
		}
		// 删除被标记为被合并的单元格，由于一次遍历删除不掉某一些单元格，遍历两次。待优化
		for (HtmlRowStructure htmlRowStructure : htmlRowStructures) {
			List<HtmlTdStructure> htmlTdStructures = htmlRowStructure.getHtmlTdStructure();
			Iterator<HtmlTdStructure> tdIter = htmlTdStructures.iterator();
			while (tdIter.hasNext()) {
				HtmlTdStructure td = tdIter.next();
				if (td.getTag() != null) {
					tdIter.remove();// 这里要使用Iterator的remove方法移除当前对象，如果使用List的remove方法，则同样会出现ConcurrentModificationException
				}
			}
		}
		return htmlStructure;
	}
	/**
	 * excel查询参数解析
	 * @param sheet
	 * @return
	 */
	public List<ExcelForm> getExcelForm(Sheet sheet){
		int sheet2LastRow = sheet.getLastRowNum();
		List<ExcelForm> inputs = Lists.newArrayList();
		for (int i = 1; i <= sheet2LastRow; i++) {
			Row row = sheet.getRow(i);
			ExcelForm configItem = new ExcelFormEntity();
			if ((row.getCell(0)!=null)&&(!Strings.isNullOrEmpty(row.getCell(0).toString()))
					&& (!Strings.isNullOrEmpty(row.getCell(2).toString()))) {
				configItem.setName(row.getCell(0).toString());
				configItem.setContent(row.getCell(1).toString());
				configItem.setVarName(row.getCell(2).toString());
				configItem.setClassType(!Strings.isNullOrEmpty(ExcelInputType.getValue(row.getCell(3).toString()))?ExcelInputType.getValue(row.getCell(3).toString()):"easyui-textbox");
				configItem.setComboAttr(row.getCell(4).toString());
				configItem.setAcurrate(row.getCell(5).toString());
				inputs.add(configItem);
			}
		}
		return inputs;
	} 
	@Override
	public void export(Context context) {
		// TODO Auto-generated method stub
	}
}
