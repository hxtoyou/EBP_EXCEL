package com.ebills.product.dg.intf.ocif;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.bussprocess.context.Context;
import org.apache.commons.lang.StringUtils;

import com.ebills.product.dg.AcctInterface.domain.Corp;
import com.ebills.product.dg.intf.IErrorCode;
import com.ebills.product.dg.intf.InterfaceRequestHead;

public class OcifInterface{
/*
 * 客户合并逻辑处理
 * */
	public void callCmmerg(InterfaceRequestHead head, Context context) {
		OcifInterfaceDao dao = new OcifInterfaceDao();
		try{
			List list  = (List)context.getValue("bodrcd");
			for(int i=0;i< list.size();i++){
				Map map = (Map)list.get(i);
				String custno = (String)map.get("custno");	//	合并客户号
				String mergno = (String)map.get("mergno");	//	被合并客户号
				String sverserialno = (String)context.getValue("sverserialno"); //被合并客户号
				if(!StringUtils.isEmpty(custno) && !StringUtils.isEmpty(mergno)){//两个客户号必须不能为空
					if (dao.isAppnoExist(mergno)) {//校验被合并客户是否在国结存在,如果不存在直接返回成功
						if(dao.isAppnoExist(custno)){//合并客户必须存在，否则返回错误
							String custnoState = dao.getCorpState(custno) ;
							String mergnoState = dao.getCorpState(mergno) ;
							if ("1".equals(mergnoState) && "1".equals(custnoState)) { //客户状态0-关闭  1-正常  2-已合并
									dao.create(sverserialno,custno,mergno);
									dao.updateAcctNoInfo(custno, mergno);//合并客户账号和客户状态
									context.put("errorCode", IErrorCode.GJYW_0);
									context.put("errorMsg", IErrorCode.GJYW__OCIF_MSG_0);
									context.put("sverretcod", IErrorCode.GJYW_0);
									context.put("sverretmsg", IErrorCode.GJYW__OCIF_MSG_0);						
							} else {
									context.put("errorCode", IErrorCode.GJYW_OCIF_ERRORCODE);
									context.put("errorMsg", IErrorCode.GJYW_OCIF__MSG_3);
									context.put("sverretcod", IErrorCode.GJYW_OCIF_ERRORCODE);
									context.put("sverretmsg", IErrorCode.GJYW_OCIF__MSG_3);
							}
						}else{
							context.put("errorCode", IErrorCode.GJYW_OCIF_ERRORCODE);
							context.put("errorMsg", IErrorCode.GJYW_OCIF__MSG_11);
							context.put("sverretcod", IErrorCode.GJYW_OCIF_ERRORCODE);
							context.put("sverretmsg", IErrorCode.GJYW_OCIF__MSG_11);
						}
					} else {
						context.put("errorCode", IErrorCode.GJYW_0);
						context.put("sverretcod", IErrorCode.GJYW_0);
						context.put("sverretmsg", IErrorCode.GJYW__OCIF_MSG_0);
					}
				}else{
					context.put("errorCode", IErrorCode.GJYW_OCIF_ERRORCODE);
					context.put("errorMsg", IErrorCode.GJYW_OCIF__MSG_1);
					context.put("sverretcod", IErrorCode.GJYW_OCIF_ERRORCODE);
					context.put("sverretmsg", IErrorCode.GJYW_OCIF__MSG_1);
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
			context.put("errorCode", IErrorCode.GJYW_MERGER01_1);
			context.put("errorMsg", IErrorCode.GJYW_MERGER01__MSG_1);
			context.put("sverretcod", IErrorCode.GJYW_MERGER01_1);
			context.put("sverretmsg", IErrorCode.GJYW_MERGER01__MSG_1);
		}
	}
	
	/*
	 * 客户合并冲销逻辑处理
	 * */
		public void callUnmerg(InterfaceRequestHead head, Context context) {
			OcifInterfaceDao dao = new OcifInterfaceDao();
			try{
					List list  = (List)context.getValue("bodrcd");
					for(int i=0;i< list.size();i++){
						Map map = (Map)list.get(i);
						String custno = (String)map.get("custno");	//	合并客户号
						String mergno = (String)map.get("mergno");	//	被合并客户号
						if(!StringUtils.isEmpty(custno) && !StringUtils.isEmpty(mergno)){//两个客户号必须不能为空
							if (dao.isAppnoExist(mergno)) {//校验被合并客户是否在国结存在,如果不存在直接返回成功
								if (dao.isAppnoExist(custno)) {//校验合并客户是否在国结存在，否则返回错误
									String custnoState = dao.getCorpState(custno) ;
									String mergnoState = dao.getCorpState(mergno) ;
									if("1".equals(custnoState)){//客户状态0-关闭  1-正常  2-已合并   合并客户状态为正常状态
										if ("2".equals(mergnoState)) {// 被合并客户状态为已合并状态						
											dao.updateAcct(custno,mergno);	//更新账号表，将账号表对应的客户号恢复为“被合并客户”
											dao.processFinish(custno, mergno);//数据迁移
											context.put("errorCode", IErrorCode.GJYW_0);
											context.put("errorMsg", IErrorCode.GJYW__OCIF_MSG_0);
											context.put("sverretcod", IErrorCode.GJYW_0);
											context.put("sverretmsg", IErrorCode.GJYW__OCIF_MSG_0);						
										} else {
												context.put("errorCode", IErrorCode.GJYW_OCIF_ERRORCODE);
												context.put("errorMsg", IErrorCode.GJYW_OCIF__MSG_5);
												context.put("sverretcod", IErrorCode.GJYW_OCIF_ERRORCODE);
												context.put("sverretmsg", IErrorCode.GJYW_OCIF__MSG_5);
										}
									}else{
										context.put("errorCode", IErrorCode.GJYW_OCIF_ERRORCODE);
										context.put("errorMsg", IErrorCode.GJYW_OCIF__MSG_4);
										context.put("sverretcod", IErrorCode.GJYW_OCIF_ERRORCODE);
										context.put("sverretmsg", IErrorCode.GJYW_OCIF__MSG_4);
									}
								} else {
									context.put("errorCode", IErrorCode.GJYW_OCIF_ERRORCODE);
									context.put("errorMsg", IErrorCode.GJYW_OCIF__MSG_12);
									context.put("sverretcod", IErrorCode.GJYW_OCIF_ERRORCODE);
									context.put("sverretmsg", IErrorCode.GJYW_OCIF__MSG_12);
								}
								}else{
									context.put("errorCode", IErrorCode.GJYW_0);
									context.put("sverretcod", IErrorCode.GJYW_0);
									context.put("sverretmsg", IErrorCode.GJYW__OCIF_MSG_0);							
								}						
						}else{
							context.put("errorCode", IErrorCode.GJYW_OCIF_ERRORCODE);
							context.put("errorMsg", IErrorCode.GJYW_OCIF__MSG_1);
							context.put("sverretcod", IErrorCode.GJYW_OCIF_ERRORCODE);
							context.put("sverretmsg", IErrorCode.GJYW_OCIF__MSG_1);
						}
					}			
			} catch (Exception e) {
				e.printStackTrace();
				context.put("errorCode", IErrorCode.GJYW_MERGER01_1);
				context.put("errorMsg", IErrorCode.GJYW_MERGER01__MSG_1);
				context.put("sverretcod", IErrorCode.GJYW_MERGER01_1);
				context.put("sverretmsg", IErrorCode.GJYW_MERGER01__MSG_1);
			}
		}
		
		/*
		 * 个人客户修改客户信息发布（OCIF－>国结）
		 * 国结收到OCIF发来的个人客户修改信息，自动跟新相关信息，
		 * 由于OCIF发送过来的更新信息可能会在国结不存在，
		 * 所以国结只更新客户的中文名称和证件号码。如果中文名称和证件号码为空，国结直接返回成功
		 * 虽然请求报文中包含循环体，和OCIF确认之后，其实一个报文就只有一个修改的客户信息。
		 * */
			public void callUpprsn(InterfaceRequestHead head, Context context) {
				OcifInterfaceDao dao = new OcifInterfaceDao();				
				try{
					int listnm = Integer.valueOf((String)context.getValue("listnm"));
					if(listnm>0){//修改客户信息的条数必须大于0
						List list  = (List)context.getValue("bodrcd");
						for(int i=0;i< list.size();i++){
							Map map = (Map)list.get(i);
							String custno = (String)map.get("custno");	//	查询客户号
							String custcn = (String)map.get("custcn");	//	中文名称
							String certno = (String)map.get("certno");	//	证件号码
							if(!StringUtils.isEmpty(custno)){//查询客户号不能为空
									if (dao.isAppnoExist(custno)) {//校验客户是否在国结存在,如果不存在直接返回成功
											if(("".equals(custcn) || custcn == null) && ("".equals(certno) || certno ==null)){//如果中文名称和证件号码都为空的话，国结直接返回成功
												context.put("errorCode", IErrorCode.GJYW_0);
												context.put("errorMsg", IErrorCode.GJYW__OCIF_MSG_0);
												context.put("sverretcod", IErrorCode.GJYW_0);
												context.put("sverretmsg", IErrorCode.GJYW__OCIF_MSG_0);
											}else{
												Corp corp = new Corp();
												corp.setCnName(custcn);
												corp.setIdCard(certno);
												corp.setMainCorpNo(custno);
												dao.updateCorpInfo(corp);
												context.put("errorCode", IErrorCode.GJYW_0);
												context.put("errorMsg", IErrorCode.GJYW__OCIF_MSG_0);
												context.put("sverretcod", IErrorCode.GJYW_0);
												context.put("sverretmsg", IErrorCode.GJYW__OCIF_MSG_0);
											}																	
										} else {
											context.put("errorCode", IErrorCode.GJYW_0);
											context.put("errorMsg", IErrorCode.GJYW__OCIF_MSG_0);
											context.put("sverretcod", IErrorCode.GJYW_0);
											context.put("sverretmsg", IErrorCode.GJYW__OCIF_MSG_0);
										}
							}else{
								context.put("errorCode", IErrorCode.GJYW_OCIF_ERRORCODE);
								context.put("errorMsg", IErrorCode.GJYW_OCIF__MSG_8);
								context.put("sverretcod", IErrorCode.GJYW_OCIF_ERRORCODE);
								context.put("sverretmsg", IErrorCode.GJYW_OCIF__MSG_8);
							}
						}						
					}else{
						context.put("errorCode", IErrorCode.GJYW_OCIF_ERRORCODE);
						context.put("errorMsg", IErrorCode.GJYW_OCIF__MSG_7);
						context.put("sverretcod", IErrorCode.GJYW_OCIF_ERRORCODE);
						context.put("sverretmsg", IErrorCode.GJYW_OCIF__MSG_7);
					}					
				} catch (Exception e) {
					e.printStackTrace();
					context.put("errorCode", IErrorCode.GJYW_MERGER01_1);
					context.put("errorMsg", IErrorCode.GJYW_MERGER01__MSG_1);
					context.put("sverretcod", IErrorCode.GJYW_MERGER01_1);
					context.put("sverretmsg", IErrorCode.GJYW_MERGER01__MSG_1);
				}
			}
			
			/*
			 * 对公客户修改客户信息发布（OCIF－>国结）
			 * 国结收到OCIF发来的对公客户修改信息，国结自动更新相关信息，
			 * 由于OCIF发送过来的更新信息可能会在国结不存在，
			 * 所以国结只更新客户的中文名称和组织机构代码。如果中文名称和组织机构代码为空，国结直接返回成功
			 * 虽然请求报文中包含循环体，和OCIF确认之后，其实一个报文就只有一个修改的客户信息。
			 * */
				public void callUpcorp(InterfaceRequestHead head, Context context) {
					OcifInterfaceDao dao = new OcifInterfaceDao();				
					try{
						int listnm = Integer.valueOf((String)context.getValue("listnm"));
						if(listnm>0){//修改客户信息的条数必须大于0
							List list  = (List)context.getValue("bodrcd");
							for(int i=0;i< list.size();i++){
								Map map = (Map)list.get(i);
								String custno = (String)map.get("custno");	//	查询客户号
								String custcn = (String)map.get("custcn");	//	中文名称
								String certno = (String)map.get("certno");	//	组织机构代码
								Corp corp = new Corp();
								if(!StringUtils.isEmpty(custno)){//查询客户号不能为空
										if (dao.isAppnoExist(custno)) {//校验客户是否在国结存在,如果不存在直接返回成功
												if(("".equals(custcn) || custcn == null) && ("".equals(certno) || certno ==null)){//如果中文名称和证件号码都为空的话，国结直接返回成功
													context.put("errorCode", IErrorCode.GJYW_0);
													context.put("errorMsg", IErrorCode.GJYW__OCIF_MSG_0);
													context.put("sverretcod", IErrorCode.GJYW_0);
													context.put("sverretmsg", IErrorCode.GJYW__OCIF_MSG_0);
												}else{
													if(certno != null && !"".equals(certno)){
														if(certno.length()!=9 && certno.length()!=10){//校验组织机构代码，长度必须为9或10位
															context.put("errorCode", IErrorCode.GJYW_OCIF_ERRORCODE);
															context.put("errorMsg", IErrorCode.GJYW_OCIF__MSG_9);
															context.put("sverretcod", IErrorCode.GJYW_OCIF_ERRORCODE);
															context.put("sverretmsg", IErrorCode.GJYW_OCIF__MSG_9);
														}else{
																if(certno.length() == 10){
																	String str = certno.substring(8, 9);
																	if(!"-".equals(str)){
																		context.put("errorCode", IErrorCode.GJYW_OCIF_ERRORCODE);
																		context.put("errorMsg", IErrorCode.GJYW_OCIF__MSG_10);
																		context.put("sverretcod", IErrorCode.GJYW_OCIF_ERRORCODE);
																		context.put("sverretmsg", IErrorCode.GJYW_OCIF__MSG_10);
																	}else{
																		corp.setCnName(custcn);
																		corp.setCorpOrgCode(certno);
																		corp.setMainCorpNo(custno);
																		dao.updateCorpInfo(corp);
																		context.put("errorCode", IErrorCode.GJYW_0);
																		context.put("errorMsg", IErrorCode.GJYW__OCIF_MSG_0);
																		context.put("sverretcod", IErrorCode.GJYW_0);
																		context.put("sverretmsg", IErrorCode.GJYW__OCIF_MSG_0);
																	}
																}
																if(certno.length() == 9){
																	corp.setCnName(custcn);
																	corp.setCorpOrgCode(certno);
																	corp.setMainCorpNo(custno);
																	dao.updateCorpInfo(corp);
																	context.put("errorCode", IErrorCode.GJYW_0);
																	context.put("errorMsg", IErrorCode.GJYW__OCIF_MSG_0);
																	context.put("sverretcod", IErrorCode.GJYW_0);
																	context.put("sverretmsg", IErrorCode.GJYW__OCIF_MSG_0);
																}
														}
													}else{
														corp.setCnName(custcn);
														corp.setCorpOrgCode(certno);
														corp.setMainCorpNo(custno);
														dao.updateCorpInfo(corp);
														context.put("errorCode", IErrorCode.GJYW_0);
														context.put("errorMsg", IErrorCode.GJYW__OCIF_MSG_0);
														context.put("sverretcod", IErrorCode.GJYW_0);
														context.put("sverretmsg", IErrorCode.GJYW__OCIF_MSG_0);
													}													
												}																	
											} else {
												context.put("errorCode", IErrorCode.GJYW_0);
												context.put("errorMsg", IErrorCode.GJYW__OCIF_MSG_0);
												context.put("sverretcod", IErrorCode.GJYW_0);
												context.put("sverretmsg", IErrorCode.GJYW__OCIF_MSG_0);
											}
								}else{
									context.put("errorCode", IErrorCode.GJYW_OCIF_ERRORCODE);
									context.put("errorMsg", IErrorCode.GJYW_OCIF__MSG_8);
									context.put("sverretcod", IErrorCode.GJYW_OCIF_ERRORCODE);
									context.put("sverretmsg", IErrorCode.GJYW_OCIF__MSG_8);
								}
							}						
						}else{
							context.put("errorCode", IErrorCode.GJYW_OCIF_ERRORCODE);
							context.put("errorMsg", IErrorCode.GJYW_OCIF__MSG_7);
							context.put("sverretcod", IErrorCode.GJYW_OCIF_ERRORCODE);
							context.put("sverretmsg", IErrorCode.GJYW_OCIF__MSG_7);
						}					
					} catch (Exception e) {
						e.printStackTrace();
						context.put("errorCode", IErrorCode.GJYW_MERGER01_1);
						context.put("errorMsg", IErrorCode.GJYW_MERGER01__MSG_1);
						context.put("sverretcod", IErrorCode.GJYW_MERGER01_1);
						context.put("sverretmsg", IErrorCode.GJYW_MERGER01__MSG_1);
					}
				}
		
		/*
		 * 客户信息查询
		 * OCIF向国结查询客户信息，国结只需要返回客户信息条数，如果不存在则返回0
		 * */
			public void callQucust(InterfaceRequestHead head, Context context) {
				OcifInterfaceDao dao = new OcifInterfaceDao();
				try{
					List list  = (List)context.getValue("bodrcd");
					for(int i=0;i< list.size();i++){
						Map map = (Map)list.get(i);
						String custno = (String)map.get("custno");	//	查询客户号
						if(!StringUtils.isEmpty(custno)){//查询客户号不能为空
								if (dao.isAppnoExist(custno)) {//校验客户是否在国结存在,如果不存在直接返回成功
										String listnm = dao.getCorpNumber(custno);
										context.put("listnm", listnm);
										context.put("errorCode", IErrorCode.GJYW_0);
										context.put("errorMsg", IErrorCode.GJYW__OCIF_MSG_0);
										context.put("sverretcod", IErrorCode.GJYW_0);
										context.put("sverretmsg", IErrorCode.GJYW__OCIF_MSG_0);						
									} else {
										context.put("errorCode", IErrorCode.GJYW_0);
										context.put("errorMsg", IErrorCode.GJYW__OCIF_MSG_0);
										context.put("sverretcod", IErrorCode.GJYW_0);
										context.put("sverretmsg", IErrorCode.GJYW__OCIF_MSG_0);
										context.put("listnm", "0");
									}
						}else{
							context.put("errorCode", IErrorCode.GJYW_OCIF_ERRORCODE);
							context.put("errorMsg", IErrorCode.GJYW_OCIF__MSG_6);
							context.put("sverretcod", IErrorCode.GJYW_OCIF_ERRORCODE);
							context.put("sverretmsg", IErrorCode.GJYW_OCIF__MSG_6);
							context.put("listnm", "null");
						}
					}					
				} catch (Exception e) {
					e.printStackTrace();
					context.put("errorCode", IErrorCode.GJYW_MERGER01_1);
					context.put("errorMsg", IErrorCode.GJYW_MERGER01__MSG_1);
					context.put("sverretcod", IErrorCode.GJYW_MERGER01_1);
					context.put("sverretmsg", IErrorCode.GJYW_MERGER01__MSG_1);
					context.put("listnm", "null");
				}
			}
}
