package com.ebills.product.declare.manage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.jfree.util.Log;

import com.eap.file.excel.ExcelException;
import com.ebills.commons.SerialNoFactory;
import com.ebills.declare.DeclareField;
import com.ebills.declare.DeclareInfos;
import com.ebills.declare.bussiness.DclNoFactory;
import com.ebills.declare.inf.DeclareInf;
import com.ebills.param.org.TradeOrgFactory;
import com.ebills.product.declare.dao.DeclareBussDAO;
import com.ebills.product.declare.dao.DeclareErrDAO;
import com.ebills.product.declare.dao.DeclareParamDAO;
import com.ebills.product.declare.util.DataUtil;
import com.ebills.product.declare.util.GeneralCalc;
import com.ebills.util.EbillsException;
import com.ebills.utils.CommonUtil;
import com.ebills.utils.EbpConstants;
import com.ebills.utils.OutPutBean;

/**
 * 金宏申报文件形成后台实现类
 * @author 
 * @date 2013-12-4 下午2:53:53
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class FileBuildManage implements DeclareInf {
	private static String className = FileBuildManage.class.getName();

	@Override
	public OutPutBean execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String todo = request.getParameter("todo");
		Method[] met = this.getClass().getMethods();
		OutPutBean out = null;
		for (int i = 0; i < met.length; i++) {
			if (met[i].getName().equals(todo)) {
				out = (OutPutBean) met[i].invoke(this, request, response);
			}
		}
		return out;
	}
	
	/**
	 * 形成申报文件
	 * @param request
	 * @return
	 */
	public OutPutBean buildFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String[] declaredl = request.getParameterValues("declaredl[]");
		String curOrgNo = request.getParameter("curOrgNo");
		String startDate = request.getParameter("startDate");			// 形成/下载文件的起始日期
		String endDate = request.getParameter("endDate");				// 形成/下载文件的终止日期
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date sDate = df.parse(startDate);
		Date eDate = df.parse(endDate);
		// 国结最开始形成的申报文件存储的目录比如:E:/gjyw/ebills/szsb/oldSend
		String oldSendDir = GeneralCalc.initDeclareEnv(curOrgNo);
		String nullTip = "";

		if (declaredl != null) {
			for (String dl : declaredl) {
				// BOP国际收支申报数据
				if ("BOP".equals(dl)) {
					String[] dcltpBop = request
							.getParameterValues("dcltpBop[]");
					if (dcltpBop != null) {
						nullTip += getTotalXml(oldSendDir, dl,
								dcltpBop, curOrgNo, sDate, eDate);
					}
					
				// JSH外汇账户内结售汇申报数据
				} else if ("JSH".equals(dl)) {
					String[] dcltpJsh = request
							.getParameterValues("dcltpJsh[]");
					if (dcltpJsh != null) {
						nullTip += getTotalXml(oldSendDir, dl,
								dcltpJsh, curOrgNo, sDate, eDate);
					}
					
				// 银行自身资本项目申报数据
				} else if ("ACC".equals(dl)) {
					String[] dcltpAcc = request
							.getParameterValues("dcltpAcc[]");
					if (dcltpAcc != null) {
						nullTip += getTotalXml(oldSendDir, dl,
								dcltpAcc, curOrgNo, sDate, eDate);
					}
					
				// 银行自身资本项目申报数据
				} else if ("CFA".equals(dl)) {
					String[] dcltpCfa = request
							.getParameterValues("dcltpCfa[]");
					if (dcltpCfa != null) {
						nullTip += getTotalXml(oldSendDir, dl,
								dcltpCfa, curOrgNo, sDate, eDate);
					}
					
				// FAL对外金融资产申报数据
				} else if ("FAL".equals(dl)) {
					String[] dcltpFal = request
							.getParameterValues("dcltpFal[]");
					if (dcltpFal != null) {
						nullTip += getTotalXml(oldSendDir, dl,
								dcltpFal, curOrgNo, sDate, eDate);
					}
					
				// 个人外币现钞存取申报数据
				} else if ("CWD".equals(dl)) {
					String[] dcltpCwd = request
							.getParameterValues("dcltpCwd[]");
					if (dcltpCwd != null) {
						nullTip += getTotalXml(oldSendDir, dl,
								dcltpCwd, curOrgNo, sDate, eDate);
					}
					
				// 银行自身外债数据
				} else if ("EXD".equals(dl)) {
					String[] dcltpExd = request
							.getParameterValues("dcltpExd[]");
					if (dcltpExd != null) {
						nullTip += getTotalXml(oldSendDir, dl,
								dcltpExd, curOrgNo, sDate, eDate);
					}
				}
			}
		}

		if(!GeneralCalc.isNull(nullTip)) {
			throw new ExcelException(nullTip);
		}
		return new OutPutBean(CommonUtil.MapToJson(new HashMap()));
	}
	
	/**
	 * 形成金宏报送文件
	 * @param basePath
	 * @param dclKind
	 * @param dclTypes
	 * @param orgNo
	 * @param fileDate
	 * @throws EbillsException
	 */
	public String getTotalXml(String downDir,String dclKind,String[] dclTypes,String orgNo,Date startDate,Date endDate) throws Exception {
		String nullTip = "";		// 无数据时提示信息
		try{
			String TV = "";
			if("BOP".equals(dclKind) || "JSH".equals(dclKind)){
				TV = "T";
			}else{
				TV = "TT";
			}

			File downDirFile = new File(downDir); // 基本目录即OldSend目录,如:e:\szsb\oldSend
			if (!downDirFile.exists()) {
				GeneralCalc.newFolder(downDir);
			}

			// 获取申报机构
			String sborgNo = TradeOrgFactory.getOrgBizByBizName(orgNo, "declareOrgNo");
			// 获取申报机构对应的金融机构标识码
			String branchCode = DclNoFactory.getBranchCodeByOrgNo(sborgNo);
			// 获取形成申报文件的控制文件名称
			String fileName = getXMLName(dclKind,TV,branchCode,endDate,sborgNo);

			// 获取当前时间目录文件夹如:e:\szsb\oldSend\20121126_185127_BOP
			String foldName = getFoleName(DataUtil.getCurrentDate());
			String foldDir = downDir + foldName  + "_"+dclKind;
			GeneralCalc.newFolder(foldDir); // 在基本目录下创建当前时间目录
			downDir = foldDir;

			// 当前时间的目录如:e:\szsb\oldSend\20121126_185127_BOP\BOPT37060000770114082406
			foldDir = foldDir + File.separator + fileName;
			GeneralCalc.newFolder(foldDir); // 按外管要求生成目录路径

			List ttt = new ArrayList();
			if(dclTypes != null){
				for (int i = 0; i < dclTypes.length; i++) {
					getDeclareXml(foldDir,dclKind,dclTypes[i],startDate,endDate,ttt,fileName,orgNo,branchCode);
				}
			}
			if (ttt != null && ttt.size() > 0) {
				Document document = DocumentHelper.createDocument();
				Element MSGElement = document.addElement("MSG");
				Element APPTYPEElement = MSGElement.addElement("APPTYPE");
				APPTYPEElement.setText(dclKind);
				Element CURRENTFILEElement = MSGElement.addElement("CURRENTFILE");
				CURRENTFILEElement.setText(dclKind+TV);
				Element INOUTElement = MSGElement.addElement("INOUT");
				INOUTElement.setText("IN");
				
				Element TOTALFILESElement = MSGElement.addElement("TOTALFILES");
				TOTALFILESElement.setText(new Integer(ttt.size()).toString());
				Element FILESElement = MSGElement.addElement("FILES");
				for (int i = 0; i < ttt.size(); i++) {
					Element FILENAMEElement = FILESElement.addElement("FILENAME");
					FILENAMEElement.setText((String) ttt.get(i));
				}

				OutputFormat format = OutputFormat.createPrettyPrint();
				format.setEncoding("gb18030");
				// 将形成的申报数据写入XML文件,形成申报数据文件
				XMLWriter writer = new XMLWriter(new FileWriter(new File(
						foldDir + File.separator + fileName + ".XML")), format);
				writer.write(document);
				writer.close();

				// 将形成在oldSend目录下的申报文件copy到对应的申报类型的send目录下
				GeneralCalc.copyFolder(
						foldDir,
						GeneralCalc.getDeclareSendHome(dclKind)
								+ GeneralCalc.getSendPath()
								+ GeneralCalc.getFileSplit() + fileName, false);

				// 将形成在oldSend目录下的申报文件打包
				DataUtil.formZip(downDir, fileName, true);
			} else {
				File file = new File(foldDir);
				file.delete();
				file.getParentFile().delete();
				nullTip = "您选择的"+dclKind+"申报无对应的数据,无需生成申报文件!";
			}
		} catch (Exception e) {
			throw new EbillsException(e,className,2,null,null);
		}
		return nullTip;
	}

	/**
	 * 形成金宏报送文件
	 * @param filePath
	 * @param dclKind
	 * @param dclType
	 * @param fileDate
	 * @param ttt
	 * @param kzFileName
	 * @param orgNo
	 * @param bankID
	 * @throws Exception
	 */
	public void getDeclareXml(String filePath, String dclKind,
			String dclType, Date startDate, Date endDate, List ttt, String kzFileName,
			String orgNo,String bankID) throws Exception {
			DeclareParamDAO paramdao = new DeclareParamDAO();
			DeclareBussDAO bussdao = new DeclareBussDAO();
			Map<String,Object> mapParam = new HashMap<String,Object>();	
			Map<String,String> typeMap = paramdao.getDldclFileType();
			if("2".equals(paramdao.getDldclMDFlag(dclKind+dclType))) return;
			if(!typeMap.containsKey(dclKind+dclType)) return;
			String kt = typeMap.get(dclKind+dclType);
			String type = kt.substring(kt.length()-4);
			String kind = kt.substring(0, kt.length()-4);
			mapParam.put("dclKind", kind);
			mapParam.put("startDate", startDate);
			mapParam.put("endDate", endDate);
			mapParam.put("orgNo", orgNo);

			if("jcxx".equals(type)){
				mapParam.put("input", "bscInput");
				mapParam.put("state", "bscState");
			}else if("glxx".equals(type)){
				mapParam.put("input", "mgeInput");
				mapParam.put("state", "mgeState");
			}else if("sbxx".equals(type)){
				mapParam.put("input", "dclInput");
				mapParam.put("state", "dclState");
			}

			List<Map<String, Object>> resultLit = bussdao.getDecalreBussByParams(paramdao.getTabName(kind, type), mapParam, EbpConstants.TABLE_INFO);
			if(null == resultLit || resultLit.isEmpty()){
				System.out.println("=========为空======");
				return;
			}

			Document document = DocumentHelper.createDocument();
			Element MSGElement = document.addElement("MSG");
			Element APPTYPEElement = MSGElement.addElement("APPTYPE");
			APPTYPEElement.setText(dclKind);
			Element CURRENTFILEElement = MSGElement
					.addElement("CURRENTFILE");
			CURRENTFILEElement.setText(dclKind+dclType);
			Element INOUTElement = MSGElement.addElement("INOUT");
			INOUTElement.setText("IN");
			Element TOTALRECORDSElement = MSGElement
					.addElement("TOTALRECORDS");
			TOTALRECORDSElement.setText((new Integer(resultLit.size()))
					.toString());
			Element RECORDSElement = MSGElement.addElement("RECORDS");
			DeclareInfos dInfo = paramdao.getFFMap(kind, type);
			Map<String, DeclareField> fieldMap =  dInfo.getField();
			LinkedList<String> fieldNameList = dInfo.getFieldName();
			for(Map<String,Object> tmpInfo : resultLit){
				Element RECElement = RECORDSElement.addElement("REC");
				Map iMap = new LinkedHashMap();
				Map oMap = new LinkedHashMap();
				Map pMap = new LinkedHashMap();
				for(String fieldName : fieldNameList){
					DeclareField field = fieldMap.get(fieldName);
					if(tmpInfo.containsKey(fieldName) && !"N".equals(field.getFileFlag())){
						iMap.put(fieldName, tmpInfo.get(fieldName));
					}
					if(field.getOutElem() != null){
						oMap.put(fieldName, field.getOutElem());
					}
					if(!DataUtil.ObjectIsNull(field.getDLine())){
						pMap.put(fieldName, field.getDLine());
					}
				}
				Map dclInfo = iMap;
				Set set = dclInfo.keySet();
				Iterator records = set.iterator();
				while (records.hasNext()) {
					String key = (String) records.next();
					if(key.indexOf("XXX") != -1){
						continue;
					}
					if(key.indexOf("18") != -1){
						if("CFA".equals(dclKind))
							continue;
						else
							key.replace("18", "");
					}
					if (oMap.containsKey(key)) {
						Element paramElement = null;
						Element infoElement = null;
						if(!"".equals(oMap.get(key))){
							paramElement = RECElement
									.addElement(((String) oMap.get(key)).toUpperCase());
						}else{
							paramElement = RECElement;
							infoElement = paramElement
									.addElement(key.toUpperCase());
						}
						
						Object[] infos = (Object[])dclInfo.get(key);
						for (int i = 0; i < infos.length; i++) {
							if(!"".equals(oMap.get(key))){
								 infoElement = paramElement
										.addElement(key.toUpperCase());
							}
							if(infos[i] instanceof Map){
								Map bankmap = (Map) infos[i];
								Iterator<String> it = bankmap.keySet().iterator();
								while(it.hasNext()){
									String childkey = it.next();
									if(childkey.indexOf("XXX") != -1){
										continue;
									}
									if(childkey.indexOf("18") != -1){
										if("CFA".equals(dclKind))
											continue;
										else
											childkey.replace("18", "");
									}
									Element childElement = infoElement
											.addElement(childkey.split("_")[2].toUpperCase());
									if(bankmap.get(childkey) == null){
										childElement.setText("");
									}else{
										String value = (String) bankmap.get(childkey);
										childElement.setText(DataUtil.StringChange(value));
									}
								}
							}
						}
						
					}else{
						Element paramElement = null;
						if (pMap.containsKey(key)) {
								paramElement = RECElement
										.addElement(((String) pMap.get(key)).toUpperCase());
						}else{
							paramElement = RECElement
									.addElement(key.toUpperCase());
						}
						if(dclInfo.get(key) == null){
							paramElement.setText("");
						}else{
							String value = "";
							Object object = dclInfo.get(key);
							if(object instanceof java.sql.Date ||
									object instanceof java.util.Date) {

								value = DclNoFactory.dateToString((Date)object, "yyyyMMdd");
//								System.out.println("date类型数据:"+value);
							} else if(object instanceof Double || 
									object instanceof BigDecimal) {

								BigDecimal bd = new BigDecimal((Double)object);
								value = bd.toPlainString();

								// 非国际收支的,不进行四舍五入
								if(!"BOP".equals(dclKind)) {
//									System.out.println("非BOP金额:"+value);
								// 国际收支申报中金额的四舍五入的取值
								} else{
									// 判断小数点后是否的0是否大于或等于3位,大于3位表示汇率,不截取0
									if(value.lastIndexOf(".000") > 0) {
										
									} else {
										value = value.replaceAll("0+?$", "");
										value = value.replaceAll("[.]$", "");
									}
//									System.out.println("BOP金额:"+value);
								}
								// 判断小数点后出现科学技术
								if(value.lastIndexOf(".") > 0) {
									String len = value.substring(value.indexOf(".") +1, value.length());
									if(len.length() > 6) {
										value = String.valueOf(object);
									} 
								}
							} else {
								value = String.valueOf(object);
							}
							paramElement.setText(DataUtil.StringChange(value));
						}
					}
				}

				Map<String,Object> keyMap = new HashMap<String, Object>();
				keyMap.put("txnNo", tmpInfo.get("txnNo"));
				keyMap.put("rptNo", tmpInfo.get("sbkeyNo"));
				keyMap.put("dclKind", kind);

				if("0".equals(paramdao.getDldclMDFlag(dclKind+dclType))){
					Map<String,Object> paramMap = new HashMap<String, Object>();
					if("jcxx".equals(type)){
						paramMap.put("bscState", "1");
					}else if("glxx".equals(type)){
						paramMap.put("mgeState", "1");
					}else if("sbxx".equals(type)){
						paramMap.put("dclState", "1");
					}
					bussdao.updateDecalre(paramMap, keyMap, EbpConstants.TABLE_INFO);
				}

				List<Map<String, Object>> list = bussdao.getDecalreByParams(keyMap, EbpConstants.TABLE_INFO);
				for (Map<String, Object> map : list) {
					Map<String,Object> kzMap = new HashMap<String, Object>();
					kzMap.put("kzName", kzFileName);
					kzMap.put("tpName", type);
					kzMap.put("kdName", kind);
					kzMap.put("rptNo",  (String)map.get("rptNo"));
					bussdao.createDldclkz(kzMap);
				}
			}
			// 形成最终文件名,按照选择的日期的终止日期
			String fileName = getXMLName(dclKind,dclType,bankID,endDate,orgNo);
			DataUtil.writeXmlFile(document, filePath +File.separator
					+ fileName + ".XML");
			ttt.add(fileName + ".XML");
	}
	
	/**
	 * 根据当前日期获得文件夹名
	 * @param fileDate
	 * @return
	 * @throws Exception
	 */
	public static String getFoleName(Date fileDate) throws Exception {
			Timestamp aa = new Timestamp(System.currentTimeMillis());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
			String xmlFile = "";
			xmlFile = sdf.format(aa);
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			xmlFile = df.format(fileDate)+"_"+xmlFile.split("_")[1];
			return xmlFile;
	}

	/**
	 * 获得报送XML文件名称
	 * 规则：数据类型 + 12位机构号 + 6位日期YYMMDD + 2位序号
	 * @param dclKind
	 * @param dclType
	 * @param branchCode
	 * @param date
	 * @param orgNo
	 * @return
	 * @throws Exception
	 */
	public String getXMLName(String dclKind, String dclType,String branchCode,Date date,String orgNo)
			throws Exception {
			DeclareParamDAO paramdao = new DeclareParamDAO();
			String fileName = "";
			SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
			String dclDate = df.format(date);

			// 数据类型  + 12位机构号 + 6位日期YYMMDD + 2位序号,文件扩展名为XML
			fileName = dclKind + dclType + branchCode + dclDate + paramdao.getDldclxfxh(orgNo, dclKind, dclType, dclDate);
			return fileName;
	}

	/**
	 * 下载形成的申报文件
	 * @param request
	 * @param response
	 * @return
	 */
	public OutPutBean downSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String[] declaredn = request.getParameterValues("declaredn[]");
		String curOrgNo = request.getParameter("curOrgNo");
		String startDate = request.getParameter("startDate");			// 形成/下载文件的起始日期
		String endDate = request.getParameter("endDate");				// 形成/下载文件的终止日期
		String oleSendPath = GeneralCalc.initDeclareEnv(curOrgNo);

		List<LinkedHashMap<String, Object>> outList = new ArrayList<LinkedHashMap<String, Object>>();
		for (String dclType : declaredn) {
			outList = this.getDaysFileList(outList, startDate, endDate, oleSendPath, dclType);
		}
		return new OutPutBean(CommonUtil.ListToJson(outList));
	}

	/**
	 * 下载形成的申报文件
	 * @param request
	 * @return
	 */
	public OutPutBean downFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fileName = request.getParameter("fileName");
		String filePath = request.getParameter("filePath");
		BufferedInputStream  in= new BufferedInputStream (new FileInputStream(filePath));
		response.setContentType("application/zip");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		ServletOutputStream outputStream = response.getOutputStream();
		BufferedOutputStream  oos = new BufferedOutputStream(outputStream);
		
		int i=0;
		byte[] bt=new byte[1024];
		i=in.read(bt);
		while (i>=0){
			oos.write(bt,0,i);
			i=in.read(bt);
		}
		oos.flush();
		oos.close();
		outputStream.flush();
		outputStream.close();
		return new OutPutBean("_self");
	}

	/**
	 * @desc 解析外管申报错误反馈文件(定时任务处理DeclareFileEngine)
	 * @throws EbillsException
	 */
	public static void parseDeclareErrorFile() throws EbillsException{
		try{
			SerialNoFactory snf =  new SerialNoFactory();
			DeclareErrDAO errDao = new DeclareErrDAO();
			DeclareParamDAO paramDao = new DeclareParamDAO();
			DeclareBussDAO bussDao = new DeclareBussDAO();
			Map<String,String> typeMap = paramDao.getDldclFileType();		// 获取所有申报交易类型

			File reciveDir = null;							// 国结本地接收外管错误反馈的文件的目录(根据不同申报类型进行分类,如BOPData/recive)
			File[] reciveFiles = null;						// recive目录下接收的外管的错误反馈文件夹
			String dclKind = "";							// 申报大类,如ACC
			String kzName = "";								// 具体报送给外管的申报文件名,如ACCTT37060000770214082501
			File[] xmlFiles = null;							// 某个错误反馈文件夹下的XML错误反馈文件
			Map<String, Object> dataMap = null;				// 将某个错误反馈文件解析后存储的Map对象
			String curr = "";								// 外管错误反馈文件中的反馈类型(进行转换后的国结自定义名称)
			String kind = "";								// 申报种类,如swsr
			String type = "";								// 申报种类的不同类型,如jcxx
			Map errsRecords = null;							// 外管错误反馈信息根节点
			List recs = null;;								// 外管错误反馈信息记录节点
			Map errRec = null;								// 具体某条错误反馈信息
			Map errDesc = null;								// 具体某条错误反馈信息下的错误描述信息
			String rptNo = "";								// 错误反馈对应的某条数据的申报号码/外债编号/自编号等(国结可以根据此号码找到对应的国结业务数据)

			Map<String,Object> mapParam = new HashMap<String,Object>();
			
			// 首先获取外管错误反馈文件接收目录
			List<String> folderList = GeneralCalc.getDclTypeFolder();
			for(String dclType : folderList) {

				reciveDir = new File(GeneralCalc.getSzsbHome() + dclType + File.separator + GeneralCalc.getReceivePath());
				if(reciveDir.isDirectory()){

					// recive目录下接收的外管的错误反馈文件夹
					reciveFiles = reciveDir.listFiles();
					for (int i = 0; i < reciveFiles.length; i++) {

						// 处理具体某个错误反馈文件夹下的错误反馈信息
						if(reciveFiles[i].isDirectory()){
							dclKind = reciveFiles[i].getName().substring(0, 3);
							kzName = reciveFiles[i].getName().substring(0, (reciveFiles[i].getName()).length()-3);

							/** 获取某个具体错误反馈文件夹下的所有XML错误反馈文件,并循环遍历是否存在错误反馈信息 */
							xmlFiles = reciveFiles[i].listFiles();
							for (int j = 0; j < xmlFiles.length; j++) {

								if(!"T".equals(xmlFiles[j].getName().substring(3, 4)) 
										&& !"TT".equals(xmlFiles[j].getName().substring(3, 5))){

									dataMap = DataUtil.Dom2Map(xmlFiles[j]);

									if("2".equals(paramDao.getDldclMDFlag((String) dataMap.get("CURRENTFILE")))) continue;
									if(!typeMap.containsKey((String) dataMap.get("CURRENTFILE"))) continue;
									curr = typeMap.get((String) dataMap.get("CURRENTFILE"));
									kind = curr.substring(0,curr.length()-4);		// 申报种类,如swsr
									type = curr.substring(curr.length()-4);			// 申报种类的不同类型,如jcxx

									/** 更新申报公共信息表中的错误反馈标识为有错误信息 */
									if("jcxx".equals(type)){
										errDao.updateDldclState(kzName, "BSCSTATE", type, "2", kind);
									}else if("glxx".equals(type)){
										errDao.updateDldclState(kzName, "MGESTATE", type, "2", kind);
									}else if("sbxx".equals(type)){
										errDao.updateDldclState(kzName, "DCLSTATE", type, "2", kind);
									}

									/** 读取是否有文件格式错误反馈信息,并保存到错误反馈信息表中 */
									List fts = null;
									int formaterrs = Integer.parseInt((String)dataMap.get("FORMATERRS"));
									if(formaterrs > 0) {
										Map formats = (Map) dataMap.get("FORMATS");
										if(formaterrs ==1 ) {
											fts = new ArrayList();
											fts.add(formats.get("FORMAT"));
										} else {
											fts = (List) formats.get("FORMAT");
										}
									}
									if(fts != null){
										for (int h = 0; h < fts.size(); h++) {
											String ft = (String) fts.get(h);
											Map<String,Object> paramMap = new HashMap<String,Object>();
											paramMap.put("dclErrNo", snf.getSerialNo(EbpConstants.DLDCLERRID,10));
											paramMap.put("errType", "1");
											paramMap.put("errMsg", ft);
											paramMap.put("dclState", "0");
											paramMap.put("parseDate", new Timestamp(System.currentTimeMillis()));
											errDao.createDldclerr(paramMap);
										}
										if("jcxx".equals(type)){
											errDao.updateDldclState(kzName, "BSCSTATE", type, "3", kind);
										}else if("glxx".equals(type)){
											errDao.updateDldclState(kzName, "MGESTATE", type, "3", kind);
										}else if("sbxx".equals(type)){
											errDao.updateDldclState(kzName, "DCLSTATE", type, "3", kind);
										}
									}else{
										errDao.updateDldclSend(kzName, "BSCSEND", "jcxx", kind);
										errDao.updateDldclSend(kzName, "MGESEND", "glxx", kind);
										errDao.updateDldclSend(kzName, "DCLSEND", "sbxx", kind);
									}

									/** 读取具体错误反馈信息,并保存到错误反馈信息表中 */
									errsRecords = (Map) dataMap.get("ERRRECORDS");			// 错误信息根节点

									// 根据错误记录数,重新处理节点数据
									if(Integer.parseInt((String)dataMap.get("FALRECORDS"))== 1 ) {
										Map recMap = (Map) errsRecords.get("REC");
										recs = new ArrayList();
										recs.add(recMap);
									} else {
										recs = (List) errsRecords.get("REC");					// 错误信息记录节点
									}

									if(recs != null){
										for (int k = 0; k < recs.size(); k++) {
											errRec = (Map)recs.get(k);
											// 错误反馈对应的某天数据的申报号码/外债编号/自编号等
											if("BOP".equals(dclKind) || "JSH".equals(dclKind)){
												rptNo = DataUtil.ObjectIsNull(errRec.get("RPTNO"))?"":(String)errRec.get("RPTNO");
											}else{
												rptNo = DataUtil.ObjectIsNull(errRec.get("BUSSNO"))?"":(String)errRec.get("BUSSNO");
											}
											// 由于在DLDCLRPT表中保存的主键是按照外管拼接的去掉,号的字符串,所以这里需要去掉外管局反馈编号中的逗号分隔符
											rptNo = rptNo.replace(",", "").replace("\r\n", "");

											// 获取具体错误反馈信息,并插入到数据库中(一个RPTNO可能对应有多条错误反馈信息)
											if(!DataUtil.ObjectIsNull(rptNo)){
												mapParam.put("rptNo", rptNo);
												mapParam.put("dclKind", kind);

												List<Map<String, Object>> resultLit = bussDao.getDecalreByParams(mapParam, EbpConstants.TABLE_INFO);
												for (Map<String, Object> retMap : resultLit) {
													Map errfields = (Map) errRec.get("ERRFIELDS");		// 错误描述根节点
													List errs = null;									// 错误描述信息
													if(errfields.get("ERR") instanceof Map) {
														errs = new ArrayList();
														errs.add((Map)errfields.get("ERR"));
													} else{
														errs = (List) errfields.get("ERR");
													}
													if(errs != null){
														for (int l = 0; l < errs.size(); l++) {
															errDesc = (Map)errs.get(l);
															Map<String,Object> paramMap = new HashMap<String,Object>();
															paramMap.put("dclErrNo", snf.getSerialNo(EbpConstants.DLDCLERRID,10));
															paramMap.put("txnNo", retMap.get("txnNo"));
															paramMap.put("bizNo", retMap.get("bizNo"));
															paramMap.put("rptNo", retMap.get("rptNo"));
															paramMap.put("dclKind", retMap.get("dclKind"));
															paramMap.put("errType", "0");
															paramMap.put("errMsg", errDesc.get("ERRFIELDCN")+","+errDesc.get("ERRDESC"));
															paramMap.put("dclState", "0");
															paramMap.put("parseDate", new Timestamp(System.currentTimeMillis()));
															errDao.createDldclerr(paramMap);
														}
													}
												}

												if("jcxx".equals(type)){
													errDao.updateDldclStatefs(rptNo,"BSCSTATE",kind);
													// 删除申报形成文件与申报号码关联信息表记录
													errDao.deleteDldclkzByParams(kzName,rptNo,"jcxx",kind);
												}else if("glxx".equals(type)){
													errDao.updateDldclStatefs(rptNo,"MGESTATE",kind);
													errDao.deleteDldclkzByParams(kzName,rptNo,"glxx",kind);
												}else if("sbxx".equals(type)){
													errDao.updateDldclStatefs(rptNo,"DCLSTATE",kind);
													errDao.deleteDldclkzByParams(kzName,rptNo,"sbxx",kind);
												}
											}
										}
									}

								} else {
									Log.info("TT文件不记录错误反馈");
									System.out.println("TT文件不记录错误反馈");
								}
							}
							// 根据申报文件名删除申报形成文件与申报号码关联信息表记录
							errDao.deleteDldclkz(kzName);
						} else {
							Log.info("文件"+reciveDir+",不是外管的正确反馈文件,请注意!");
							System.out.println("文件"+reciveDir+",不是外管的正确反馈文件,请注意!");
						}

						/** 处理文件后,将文件备份到reciveBak目录下 */
						java.sql.Date currDate = DataUtil.getCurrentDate();					// 获取系统当前日期
						String reciveBakPath = GeneralCalc.getSzsbHome() + dclType 			// 文件备份目录
								+ File.separator + GeneralCalc.getReceiveBakPath() + File.separator + currDate;
						GeneralCalc.copyFolder(reciveFiles[i].getAbsolutePath(), 			// 将文件copy到备份目录
								reciveBakPath + File.separator + reciveFiles[i].getName(), true);
					}
				}
			}
		}catch (Exception e) {
			throw new EbillsException(e,className,4,null,null);
		}
	}

	/**
	 * @desc 获取指定时间段下的所有zip文件并加入List
	 * @param startDate 起始时间
	 * @param endDate 结束时间
	 * @param filePath 文件路径
	 * @param dclType 申报大类类型
	 * @return
	 * @throws ParseException
	 */
	public List<LinkedHashMap<String, Object>> getDaysFileList(List<LinkedHashMap<String, Object>> fileList, 
			String startDate, String endDate, String filePath, String dclType)
			throws ParseException {

		SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date begin = sdf.parse(startDate);
		Date end = sdf.parse(endDate);
		double between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
		double day = between / (24 * 3600);

		for (int i = 0; i <= day; i++) {
			Calendar cd = Calendar.getInstance();
			cd.setTime(sdf.parse(startDate));
			cd.add(Calendar.DATE, i);// 增加一天
			String days = df2.format(cd.getTime());

			getOneDayFileList(
					fileList, days, filePath, dclType);
		}
		return fileList;
	}

	/**
	 * @desc 根据指定文件路径、时间格式和申报类型 获取指定目录下的国结申报压缩文件拼接的字符串
	 * @param fileList 某日下的指定申报类型的zip包
	 * @param dateStr  指定的日期字符串
	 * @param filePath 申报文件压缩包存储路径
	 * @param dclType  申报文件类型
	 * @return
	 */
	public List<LinkedHashMap<String, Object>> getOneDayFileList(
			List<LinkedHashMap<String, Object>> fileList, String dateStr,
			String filePath, String dclType) {

		File inFile = new File(filePath);
		String[] fileNames = inFile.list();
		if (fileNames == null) {
			fileNames = new String[0];
		}
		for (int i = 0; i < fileNames.length; i++) {
			String fileName = filePath + File.separator + fileNames[i];
			File fileTmp = new File(fileName);
			if (fileTmp.isFile()) {
				String[] zipCurString = fileNames[i].split("_");
				LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
				// 所有的申报文件
				if (dclType == null || "".equals(dclType)) {
					if (zipCurString[0].equals(dateStr)) {
						map.put("fileType", dclType);
						map.put("fileName", fileNames[i]);
						map.put("fileTime", zipCurString[0] + " "
								+ zipCurString[1]);
						map.put("filePath", fileName);
						fileList.add(map);
					}
				} else {
					String suffix = zipCurString[2].substring(0,
							zipCurString[2].indexOf("."));
					if (zipCurString[0].equals(dateStr)
							&& suffix.equals(dclType)) {
						map.put("fileType", dclType);
						map.put("fileName", fileNames[i]);
						map.put("fileTime", zipCurString[0] + " "
								+ zipCurString[1]);
						map.put("filePath", fileName);
						fileList.add(map);
					}
				}
			}
		}
		return fileList;
	}
}
