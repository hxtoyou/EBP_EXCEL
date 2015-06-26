package com.ebills.product.script;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.bussprocess.exception.InvalidArgumentException;
import org.apache.commons.bussprocess.exception.ObjectNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ebills.commons.SerialNoFactory;
import com.ebills.declare.util.GeneralCalc;
import com.ebills.product.dg.AcctInterface.domain.EbillsException;
import com.ebills.script.impl.ScriptTemplate;
import com.ebills.utils.CommonUtil;
import com.ebills.utils.EbpConstants;
import com.ebills.utils.EbpDao;
import com.ebills.utils.SessionUtil;

/**
 * @author wujie
 * 公共脚本函数
 */

public class CommonScript extends ScriptTemplate {
	
	private EbpDao gjjsDao = null;
	
	private final Log log = LogFactory.getLog(CommonScript.class);
	
	/***
	 * 保存参数表修改记录
	 * @throws EbillsException
	 * @throws InvalidArgumentException 
	 * @throws ObjectNotFoundException 
	 */
	public void saveParamAct(String sysPraINfo) throws EbillsException
	{
		try{
			gjjsDao = new EbpDao();
			
			Map<String,Object> map = new HashMap<String,Object>();
		
		    map.put("USERID", context.getValue("userId"));
			
			map.put("ORGNO",context.getValue("butxn_tranOrgNo"));
			//获取交易名称
			String tabname = "";
			String tradeNo = "";
			
			if(context.getValue("tradeNo")!=null)
			{	
				tradeNo = (String)context.getValue("tradeNo");
				if(tradeNo==null||tradeNo==""){
					log.debug("获取交易数据失败,交易号为空");
					throw new EbillsException("获取交易数据失败,交易号为空");
				}
			}
			
			String sql = "select DATASNAME from patrdtyp where TRADENO ='"+tradeNo+"'";
			
			List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, null);
			
			if(queryRet!=null&&queryRet.size()>0){
				for(int i=0;i<queryRet.size();i++){
					if(queryRet.get(i)!=null&&queryRet.get(i).get("DATASNAME")!=null){
						tabname = (String)queryRet.get(i).get("DATASNAME");
					}
				}
			}	
			
			if(tabname==null||tabname==""){
				log.debug("获取交易数据失败,表名为空");
				throw new EbillsException("获取交易数据失败,表名为空");
			}
			
			tabname= tabname.toLowerCase();
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			map.put("OPERTIME",df.parse(CommonUtil.getSysFld(EbpConstants.WORK_DATE)));
			//查询大字段数据
			queryBigDataInfo(sysPraINfo,map,tabname,gjjsDao);
			
			//设置表名称
			map.put("PARATABLE", tabname);
			//获取流水号
			//map.put("PARANO", request.getParameter("txnNo"));
			//获取自增序列
			SerialNoFactory sn = new SerialNoFactory();
			map.put("MAINID", sn.getSerialNo("BUPARASYN",10));
			gjjsDao.insertRow("buparasyn","", map);
		
		}catch(Exception e){
			
			log.debug("参数同步失败->"+e.getMessage());
			throw new EbillsException("参数同步失败->"+e.getMessage());
		}
		
	}
	
	/***
	 * 当机构规则表插入或修改数据时, 如果申报机构在申报号码参数表(dldclrpt)没有数据，则取1000000000机构的数据插入
	 * @param sysPraInfo
	 * @throws EbillsException
	 * @throws com.ebills.util.EbillsException
	 */
	public void saveDldclrptbyParams(String sysPraInfo) throws EbillsException, com.ebills.util.EbillsException
	{
		
		if(GeneralCalc.isNotNull(sysPraInfo)){
			  List<Map<String,Object>> rules = CommonUtil.parseListFromJson(sysPraInfo);
			   
		   for(Map<String,Object> map : rules){
			 String status = (String)map.get("status");
			 if(GeneralCalc.isNotNull(status))
			 {
				 if("add".equals(status)||"update".equals(status)){
					 String declareOrgNo = (String)map.get("PAORGRULES_declareOrgNo");
					 if(GeneralCalc.isNotNull(declareOrgNo)){
						 EbpDao gjjsDao = new EbpDao();
						 String sql = "select bankno,sbtype from dldclrpt where bankno='"+declareOrgNo+"'";
						 List<Map<String,Object>> resultList =  gjjsDao.queryBySql(sql, null, null);
						 if(resultList==null||resultList.size()==0){
							 sql = "insert into dldclrpt (SELECT '"+declareOrgNo+"' AS BANKNO, SBTYPE,  CURVALUE," +
							 		" CURMARK, SFGS, ARR, DAYNEW, CURDATE, MS, RESTOP, TRADETYPE, TRADENO," +
							 		" TEMPFILENAME, FIRSTCHAR, SECONDCHAR, THIRDCHAR, FOURCHAR from dldclrpt where BANKNO='1000000000')";
							 gjjsDao.execute(sql, null);
						 }else{
							 StringBuilder tempfilename= new StringBuilder();
							 tempfilename.append("(");
							 for(int i=0;i<resultList.size();i++){
								 if(i==resultList.size()-1){
									 tempfilename.append("'").append(resultList.get(i).get("sbtype")).append("'");
								 }else{
									 tempfilename.append("'").append(resultList.get(i).get("sbtype")).append("',");
								 }
							 }
							 tempfilename.append(")");
							 sql = "insert into dldclrpt (SELECT '"+declareOrgNo+"' AS BANKNO, SBTYPE,  CURVALUE," +
								 		" CURMARK, SFGS, ARR, DAYNEW, CURDATE, MS, RESTOP, TRADETYPE, TRADENO," +
								 		" TEMPFILENAME, FIRSTCHAR, SECONDCHAR, THIRDCHAR, FOURCHAR from dldclrpt where BANKNO='1000000000'" +
								 		" and SBTYPE not in "+tempfilename+")";
							 gjjsDao.execute(sql, null);
						 }
					}
				}
			 }
		   }
		}
	}
	
	/**
	 * 设置相关表的主键值
	 * @param paramMap
	 * @param childResult
	 * @param tabname
	 * @throws com.ebills.util.EbillsException 
	 */
	private void setParamNoValue(Map<String,Object> paramMap,Map<String,Object> childResult,String tabname,EbpDao gjjsDao) throws com.ebills.util.EbillsException 
	{
			SerialNoFactory sn = new SerialNoFactory();
		
			if("paorg".equals(tabname)){
				paramMap.put("PARANO",childResult.get("PAORG_orgNo"));
				
				if(childResult.get("PAORG_sifisid")!=null){
					String sifisid = (String)childResult.get("PAORG_sifisid");
					if(GeneralCalc.isNotNull(sifisid)){
						Map<String,Object> orgcdParam = new HashMap<String,Object>();
						orgcdParam.put("OPERTYPE", paramMap.get("OPERTYPE"));
						orgcdParam.put("PARATABLE", "paorgcd");
						orgcdParam.put("OPERTIME",paramMap.get("OPERTIME"));
						orgcdParam.put("PARANO",paramMap.get("PARANO"));
						orgcdParam.put("USERID",paramMap.get("USERID"));
						orgcdParam.put("ORGNO",paramMap.get("ORGNO"));
						orgcdParam.put("MAINID", sn.getSerialNo("BUPARASYN",10));
						gjjsDao.insertRow("buparasyn","", orgcdParam);
					}
				}
			}
			else if("paorgrules".equals(tabname)){
				paramMap.put("PARANO", childResult.get("PAORGRULES_orgNo"));
			}
			else if("pabank".equals(tabname)){
				paramMap.put("PARANO", childResult.get("PABANK_bankNo"));
			}
			else if("pacrp".equals(tabname)){
				paramMap.put("PARANO", childResult.get("pacrp_corpNo"));
			}
			else if("pasbbmb".equals(tabname)){
				if(childResult.get("pasbbmb_bm")!=null)
				{
					String bm= (String)childResult.get("pasbbmb_bm");
					if(childResult.get("pasbbmb_srzc")!=null){
						bm = bm+(String)childResult.get("pasbbmb_srzc");
					}
					paramMap.put("PARANO",bm);
				}
				
			}
			else if("paindattr".equals(tabname)){
				paramMap.put("PARANO", childResult.get("paindattr_INDUSTRYID"));
			}
			else if("paemtpcd".equals(tabname)){
				paramMap.put("PARANO", childResult.get("paemtpcd_code"));
			}
			else if("pajhyt".equals(tabname)){
				paramMap.put("PARANO", childResult.get("PAJHYT_USETYPECODE"));
			}
			else if("paccy".equals(tabname)){
				paramMap.put("PARANO", childResult.get("paccy_cursign"));
			}
			else if("pacy".equals(tabname)){
				paramMap.put("PARANO", childResult.get("PACY_countryNo"));
			}
			else if("pazhxz".equals(tabname)){
				paramMap.put("PARANO", childResult.get("pazhxz_zhxzid"));
			}
	}
	
	
	/***
	 * 查询大字段数据
	 * @param paramMap
	 * @param tabname
	 * @throws ObjectNotFoundException
	 * @throws InvalidArgumentException
	 * @throws EbillsException
	 * @throws com.ebills.util.EbillsException 
	 */
	@SuppressWarnings("unchecked")
	private void queryBigDataInfo(String sysDataParam, Map<String,Object> paramMap,String tabname,EbpDao gjjsDao) throws Exception
	{
		/*//获取大字段数据
		String sql = "select TXNNO,BIZNO,TXNCONTEXT from butxnctxtp where txnno=?";
		List<Object> paramList  = new ArrayList<Object>();
		//传入交易流水号
		paramList.add(context.getValue("txnNo"));*/
		
		//查询大字段数据
		/*List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);*/
		/*if(queryRet!=null&&queryRet.size()>0){
			for(int i=0;i<queryRet.size();i++){
				
			   Map<String,Object> resultMap = queryRet.get(i); 
			   Object value = resultMap.get("TXNCONTEXT");
			   HashMap<String,Object> bigData = null;
			   
			   if(value instanceof HashMap){
				   bigData = (HashMap<String,Object>)value;
			   }
			   
			   if(bigData!=null){
				   //获取当前交易数据
				   Object str = bigData.get("sysDataParamInfo");
				   
				   String sysDataParam = "";
				   
				   if(str instanceof String){
					   sysDataParam = (String)str;*/
					   //转换String的json格式数据
					   if(GeneralCalc.isNotNull(sysDataParam)){
					   Object[] objects = CommonUtil.JsonToList(sysDataParam);
					   
					   if(objects!=null&&objects.length>0){
						   
						   for(Object obj : objects){
							 if(obj!=null){
							    //对应交易一条交易Map集合
								Map<String,Object> childResult=  CommonUtil.JsonToMap(obj.toString());
								   
								  if(childResult!=null){
									  
									  String status = "";
									  if(childResult.get("status")!=null)
									  {
										  status = (String)childResult.get("status");
										  if(status==null||status=="")
										  {
												log.debug("获取交易数据失败,交易状态为空");
												throw new EbillsException("获取交易数据失败,交易状态为空");
										  }
										  
										  //设置参数表状态值
										  if("add".equals(status)){

											  paramMap.put("OPERTYPE", 1);
										  }
										  else if("update".equals(status)){
											  paramMap.put("OPERTYPE", 2);
										  }
										  else if("delete".equals(status)){
											  paramMap.put("OPERTYPE", 3);
										  }
									  }
									  setParamNoValue(paramMap,childResult,tabname,gjjsDao);
								  }
							  }
						  }
					 } 
			   }  
		}
	
	/**cms 2015年3月24日11:20:21
	 * 若用户在线，现在人工改为离线，则强制离线
	 * @param bigstr
	 * @throws EbillsException 
	 * @throws com.ebills.util.EbillsException 
	 * @throws com.ebills.util.EbillsException 
	 */
	@SuppressWarnings("unchecked")
	public void logoutUser(String bigstr) throws EbillsException, com.ebills.util.EbillsException{
		Object[] paramObj=CommonUtil.JsonToList(bigstr);
		for (int j = 0; j < paramObj.length; j++) {
  			EbpDao gjjsDao = new EbpDao();
			Map<String,Object> mParam = (Map<String,Object>) paramObj[j];
			String  userid = (String) mParam.get("PAUSR_userId"); //新增的话为null
			String  userStatee = (String) mParam.get("PAUSR_USERSTATEE"); //在线离线状态
			if (null != userid && !"null".equals(userid)){  //不是新增
				String sql1 = "select userId,userStatee from PAUSR where userId=" + userid; //userid要是数字
				List<Map<String,Object>> list = gjjsDao.queryBySql(sql1, null, null);
				if (null != list && list.size() > 0){ //不是新增用户
					Map<String,Object> map = list.get(0);
					String userStatee_old = (String)map.get("userStatee");
					if ("1".equals(userStatee_old) && "0".equals(userStatee)){ //原来是在线,现在强制离线
						SessionUtil.del(userid); //强制离线
					}
				}
			}
			
		}
	}}
