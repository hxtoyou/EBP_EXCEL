package com.ebills.product.dg.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.bussprocess.context.Context;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.eap.core.EAPConstance;
import com.eap.exception.EAPException;
import com.eap.flow.EAPAction;
import com.ebills.util.EbillsCfg;
import com.ebills.utils.CommonUtil;
import com.ebills.utils.EbpConstants;
import com.ebills.utils.EbpDao;
import com.ebills.utils.OutPutBean;

public class ImpExcelPaccyPriAction extends EAPAction {

	private OutPutBean output = null;

	protected HttpServletRequest request = null;

	String uploadPath=EbillsCfg.getProperty("default.upload");

	public String execute(Context context) throws EAPException
	{
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

			System.out.println(uploadPath);
			Map<String,String> fileMap = uploadFile(request,uploadPath);
			output = new OutPutBean(CommonUtil.MapToJson(fileMap));
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
		if(fileList != null){
			Iterator<FileItem> it = fileList.iterator();
			String name = "";
			String extName = "";
			while (it.hasNext()) {
				FileItem item = it.next();
				if(!item.isFormField()){
					name = item.getName();
					long size = item.getSize();
					String type = item.getContentType();
					if (name == null || name.trim().equals("")) {
						continue;
					}
					// 扩展名格式：
					if (name.lastIndexOf(".") >= 0) {
						extName = name.substring(name.lastIndexOf("."));
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
			fileName.put("xlsName",extName);
			fileName.put("fileName",name);
			fileName.put("path", uploadPath);
		}
		return fileName;
	}
}
