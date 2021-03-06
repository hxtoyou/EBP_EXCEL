package com.ebills.product.dg.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.bussprocess.context.Context;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.eap.core.EAPConstance;
import com.eap.exception.EAPException;
import com.eap.flow.EAPAction;
import com.ebills.util.EbillsCfg;
import com.ebills.utils.CommonUtil;
import com.ebills.utils.OutPutBean;

public class UploadAction extends EAPAction {
	
	private OutPutBean output = null;
	
	protected HttpServletRequest request = null;
	
	String uploadPath=EbillsCfg.getProperty("default.upload");
	
	public String execute(Context context) throws EAPException
	{
		try{
			
			Object object = null;
			
			if(context !=null)
			{
				object = context.getValue(EAPConstance.SERVLET_REQUEST);
			}
			
			if(null!=object)
			{
				request = (HttpServletRequest)object;
			}
			
			System.out.println(uploadPath);
			Map<String,String> fileMap = uploadFile(request,uploadPath);
			String fileName=fileMap.get("fileName");
			List<Map<String,Object>> retMap=new ArrayList<Map<String,Object>>();
			Map<String,Object> retMap1=new HashMap<String,Object>();
			retMap = getExcelData(uploadPath,fileName);
			retMap1.put("fileName", fileName);
			retMap1.put("path", uploadPath);
			retMap1.put("excelData", retMap);
	        output = new OutPutBean(CommonUtil.MapToJson(retMap1));
	        output.writeOutPut(context);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
						
		}
		return "0";
	}
	public Map<String,String> uploadFile(HttpServletRequest request,String path) throws Exception{
		Map<String,String> fileName=new HashMap<String,String>();
		File f1 = new File(path);
		if (!f1.exists()) {
			f1.mkdirs();
		}
		request.setCharacterEncoding("utf-8");
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		List<FileItem> fileList = null;
		try {
			fileList = upload.parseRequest(request);
		} catch (FileUploadException ex) {
			ex.printStackTrace();
		}
		Iterator<FileItem> it = fileList.iterator();
		String name = "";
		while (it.hasNext()) {
			FileItem item = it.next();
			if(!item.isFormField()){
				name = item.getName();
				if (name == null || name.trim().equals("")) {
					continue;
				}
				File file = null;
				file = new File(path + name);
				try {
					OutputStream out = new FileOutputStream(new File(path,name));  
					InputStream in = item.getInputStream();
					int length = 0;
					byte [] buf = new byte[1024] ;  
					while( (length = in.read(buf) ) != -1)  
                    {  
                        //在   buf 数组中 取出数据 写到 （输出流）磁盘上  
                        out.write(buf, 0, length);  
                          
                    }  
                    in.close();  
                    out.close();  
					item.write(file);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		fileName.put("fileName",name);
		fileName.put("path", uploadPath);
		return fileName;
	}
	private List<Map<String,Object>> getExcelData(String uploadPath,String fileName){
		List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
		boolean isE2007 = false;    //判断是否是excel2007格式
		if(fileName.endsWith("xlsx")){
            isE2007 = true;  
		}
        try {  
            InputStream input = new FileInputStream(uploadPath+File.separator+fileName);  //建立输入流  
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
                	int i=0;		
                    int j=0;	
                    @SuppressWarnings("rawtypes")
    				Iterator it=rowMap.entrySet().iterator();
                    while(it.hasNext()){
                    i++;	
                    @SuppressWarnings("rawtypes")
    				Map.Entry  entry=(Map.Entry)it.next();
                    Object value=entry.getValue();
                    if("".equals(((String) value).replaceAll(" ", ""))){  
                    	j++;
                    }
                    System.out.print("i  "+i+"j"+j);
                    }
                     if(i!=j){ 
                    	 dataList.add(rowMap);
                     }
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
