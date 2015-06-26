package com.ebills.product.action.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.bussprocess.context.Context;
import org.apache.commons.bussprocess.exception.InvalidArgumentException;
import org.apache.commons.bussprocess.exception.ObjectNotFoundException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.eap.core.EAPConstance;
import com.eap.flow.EAPAction;
import com.ebills.product.action.excel.entity.ExcelForm;
import com.ebills.product.component.ReportsGeneratorService;
import com.ebills.product.utils.ExcelToHtmlUtil;
import com.ebills.util.EbillsException;
import com.ebills.utils.CommonUtil;
import com.google.common.collect.Lists;


/**
 * @author Xiao He E-mail:hxtoyou@163.com
 * @version 创建时间：2015年6月1日 下午2:57:46
 * 
 */
public class ExcelCreateFormAction extends EAPAction {
	public final static String EXCEL_TEMPLATE_PATH="/myDocument/template";
	private List<ExcelForm> configItems = Lists.newArrayList();
	@SuppressWarnings("deprecation")
	@Override
	public String execute(Context context) throws ObjectNotFoundException, InvalidArgumentException{
		HttpServletRequest request = (HttpServletRequest)context.getValue(EAPConstance.SERVLET_REQUEST);
	        //将要被返回到客户端的对象  
			String path = request.getRealPath(EXCEL_TEMPLATE_PATH);
			long start = System.currentTimeMillis();
			String locale = request.getParameter("locale");
			String realPath = "";
			String fileName ="test3.xlsx";
			String fileRealName = ExcelToHtmlUtil.getFileNameByInternationnal(fileName, locale);
		
			realPath = path+"/"+fileRealName;
			Workbook workbook = ExcelToHtmlUtil.getExcelWorkBook(realPath);
			/**
			 * 解析excel系统设置模板配置信息
			 */
			Sheet sheet1 = workbook.getSheetAt(0);//系统设置模板
			int systtemSetrownum = sheet1.getLastRowNum();
			Map<String,String> systemSetResult = ExcelToHtmlUtil.analyzeSystemSetting(sheet1, systtemSetrownum+1);
			Integer querySheet = ExcelToHtmlUtil.paraseString(systemSetResult.get("querySheet"));//查询参数模板页码
			String queryFromName = systemSetResult.get("queryFromName");
			/**
			 * 查询条件模板页条件解析
			 * 
			 * 
			 */
			Sheet sheet = workbook.getSheetAt(querySheet-1);
			ReportsGeneratorService excelToHtmlStyle = new ReportsGeneratorService();
			configItems = excelToHtmlStyle.getExcelForm(sheet);
			  try {
					context.put("user", CommonUtil.ListToJson(configItems).toString());
					context.put("queryFromName", queryFromName);
				} catch (EbillsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  System.out.println("耗时: " + (System.currentTimeMillis() - start)+"毫秒");
			  return null;
	}

}
