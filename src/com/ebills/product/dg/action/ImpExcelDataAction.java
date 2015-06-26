package com.ebills.product.dg.action;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.bussprocess.context.Context;
import org.apache.commons.bussprocess.exception.BPException;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSON;
import com.eap.action.file.PropertiesUtil;
import com.eap.core.EAPConstance;
import com.eap.exception.EAPException;
import com.eap.flow.EAPAction;
import com.ebills.utils.CommonUtil;
import com.ebills.utils.EbpConstants;
import com.ebills.utils.OutPutBean;

public class ImpExcelDataAction extends EAPAction{
	
	private OutPutBean output = null;

	protected HttpServletRequest request = null;

	@SuppressWarnings("unused")
	@Override
	public String execute(Context context) throws EAPException {
		String language = "";
		
		try{
			try {
				language = (String) context.getValue(EbpConstants.USER_LANGUAGE);
			} catch (Exception e) {
				language = "en_US";
			}
			
			Object object = null;
			
			if(context !=null)
			{
				object = context.getValue(EAPConstance.SERVLET_REQUEST);
			}
			
			if(null!=object)
			{
				request = (HttpServletRequest)object;
			}
			String fileName=request.getParameter("fileName");
			String path=request.getParameter("path");
			List<Map<String,Object>> retMap=new ArrayList<Map<String,Object>>();
			retMap = getExcelData(path,fileName);
			output = new OutPutBean(CommonUtil.ListToJson(retMap));
			output.writeOutPut(context);
			}catch(Exception e)
				{
					e.printStackTrace();
								
				}
			return "0";
		}

	private List<Map<String,Object>> getExcelData(String uploadPath,String fileName){
		List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
		boolean isE2007 = false;    //判断是否是excel2007格式
		if(fileName.endsWith("xlsx")){
            isE2007 = true;  
		}
        try {  
            InputStream input = new FileInputStream(uploadPath+"\\"+fileName);  //建立输入流  
            Workbook wb  = null;  
            //根据文件格式(2003或者2007)来初始化  
            if(isE2007){  
                wb = new XSSFWorkbook(input);  
            }else{  
            	wb = new HSSFWorkbook(input);
            }
            Sheet sheet = wb.getSheetAt(0);     //获得第一个表单  
            Iterator<Row> rows = sheet.rowIterator(); //获得第一个表单的迭代器 
            boolean endFlag = false;
            while (rows.hasNext()) {
                Row row = rows.next();  //获得行数据 
                if(row.getRowNum() == 0){
                	continue;
                }
                Iterator<Cell> cells = row.cellIterator();    //获得第一行的迭代器
                Map<String,Object> rowMap = new HashMap<String,Object>();
                while (cells.hasNext()) {
                	String key = "";
                    Cell cell = cells.next();
                    int colIdx = cell.getColumnIndex();
                    String cellVal="";
                    switch(cell.getCellType()){
                    case XSSFCell.CELL_TYPE_NUMERIC:
                    	if(HSSFDateUtil.isCellDateFormatted(cell)){
                    		Date date=cell.getDateCellValue();
                    		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                    		cellVal=sdf.format(date);
                    		break;
                    	}else{
                    		cellVal=cell.getNumericCellValue()+"";
                    		break;
                    	}
                    case XSSFCell.CELL_TYPE_STRING:
                    	cellVal=cell.getStringCellValue();
                    	break;
                    case XSSFCell.CELL_TYPE_BOOLEAN:
                    	cellVal=cell.getBooleanCellValue()+"";
                    	break;
                    case XSSFCell.CELL_TYPE_FORMULA:
                    	cellVal=cell.getCellFormula()+"";
                    	break;
                    case XSSFCell.CELL_TYPE_BLANK:
                    	cellVal="";
                    	break;
                    case XSSFCell.CELL_TYPE_ERROR:
                    	cellVal="非法字符";
                    	break;
                    default: 
                    	cellVal="未知类型";
                    	break;
                    }
                    
                    if(colIdx == 0){//字段名称
                    if("".equals(cellVal)){
                    	endFlag = true;
                    	break;
                    }else{
                    	key = "EXEORG";
                    }
                    }else if(colIdx == 1){//字段描述
                    	key = "REMITCUR";
                    }else if(colIdx == 2){//字段类型
                    	key = "REMITAMT";
                    }else if(colIdx == 3){//长度大小
                    	key = "REMITNO";
                    }else if(colIdx == 4){//默认值
                    	key = "REMITNAME";
                    }else if(colIdx == 5){//是否必输
                    	key = "REMITACCT";
                    	/*if("是".equals(cellVal)){
                    		cellVal = "1";
                    	}else{
                    		cellVal = "0";
                    	}*/
                    }else if(colIdx == 6){//中文名称
                    	key = "BENENAME";
                    }else if(colIdx == 7){//英文名称
                    	key = "BENEACCT";
                    }else if(colIdx == 8){
                    	key = "FEE";
                    }else if(colIdx == 9){//英文名称
                    	key = "SKBK";
	                }else if(colIdx == 10){//英文名称
	                	key = "ACCK";
		            }else if(colIdx == 11){//英文名称
		            	key = "ACCT";
			        }else if(colIdx == 12){//英文名称
			        	key = "DATER";
					}else if(colIdx == 13){//英文名称
				    	key = "DATEX";
                    }
            		rowMap.put(key, cellVal);
                }
                if(!endFlag){
                	dataList.add(rowMap);
                }else{
                	break;
                }
            }
        }catch (IOException ex){
        	ex.printStackTrace();
        }
		return dataList;
	}
}
