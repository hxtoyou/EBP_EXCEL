package com.ebills.product.dg.credit.dao;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.bussprocess.context.Context;

import com.ebills.util.EbillsException;
import com.ebills.util.EbillsLog;
import com.ebills.util.db.ConnectionManager;
import com.ebills.util.db.ConnectionManagerFactory;
import com.ebills.product.dg.commons.Commons;
import com.ebills.utils.EbpDao;

public class ForexDao {
	private static EbillsLog log = new EbillsLog(ForexDao.class.getName());
	  public void saveForex(Context context){
		  EbpDao gjjsDao = new EbpDao();
		  String txnno=(String) context.get("txnNo");//交易流水
		  String  curBiz=(String) context.get("butxn_curtBizNo");//交易业务编号
		  String tradeNo=(String) context.get("butxn_tradeNo");
		  String tradOrgNo=(String) context.get("butxn_tranOrgNo");
			try {
				ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
				cm.startTransaction(true);
				try {
					StringBuffer sql = new StringBuffer();
					sql.append("select fundsflowid ,txnno,ybcur,ybamt ,ybcrflag ,ybacctno ,yborgno,ybtypeno,jzcur,jzamt,jzacctno");
				    sql.append(",jzcrflag,jzorgno,jztypeno,jshcode,thmidhr,jsthflag,jsthkm,realhr,syshr,cshr,pphr,jsthsy,acckind");
				    sql.append(",jzgrpno,ioflag,jztype,isautojh,jhcnyno from BUFNDTP where txnno=?");
				    List<Object> inputList=new LinkedList<Object>();
				    inputList.add(txnno);
					StringBuffer item = new StringBuffer();
					item.append("fundsflowid ,txnno,ybcur,ybamt ,ybcrflag ,ybacctno ,yborgno,ybtypeno,jzcur,jzamt,jzacctno");
					item.append(",jzcrflag,jzorgno,jztypeno,jshcode,thmidhr,jsthflag,jsthkm,realhr,syshr,cshr,pphr,jsthsy,acckind");
					item.append(",jzgrpno,ioflag,jztype,isautojh,jhcnyno");
					List result= gjjsDao.queryBySql(sql.toString(), item.toString(), null, inputList);
					if(result.size()>0){
						Map<String,Object> resultMap=(Map<String,Object>)result.get(0);
						resultMap.get("fundsflowid");
						resultMap.get("txnno");
						String ybCur=(String)resultMap.get("ybcur");
						double ybamt=0;
						if(resultMap.get("ybamt")!=null){
							 ybamt=Double.valueOf((String)resultMap.get("ybamt"));
						}
						String ybCrFlag=(String)resultMap.get("ybcrflag");
						String ybacctno=(String)resultMap.get("ybacctno");
						resultMap.get("yborgno");
						resultMap.get("yborgno");
						resultMap.get("ybtypeno");
						String jzCur=(String)resultMap.get("jzcur");
						double jzamt=0;
						if(resultMap.get("jzamt")!=null){
							jzamt=Double.valueOf((String)resultMap.get("jzamt"));
						}
						String jzacctno=(String)resultMap.get("jzacctno");
						String jzcrflag=(String)resultMap.get("jzcrflag");
						resultMap.get("jzorgno");
						resultMap.get("jztypeno");
						String jshCode=(String)resultMap.get("jshcode");
						resultMap.get("thmidhr");
						resultMap.get("jsthflag");
						resultMap.get("jsthkm");
						double realhr=0;
						if(resultMap.get("realhr")!=null){
							realhr=Double.valueOf((String)resultMap.get("realhr"));
						}
						resultMap.get("syshr");
						resultMap.get("pphr");
						resultMap.get("jsthsy");
						resultMap.get("acckind");
						resultMap.get("jzgrpno");
						resultMap.get("ioflag");
						resultMap.get("jztype");
						resultMap.get("isautojh");
						resultMap.get("jhcnyno");
						String forexflag="";
						if(!"".equals(ybCur)&&ybCur!=null&&!"".equals(jzCur)&&jzCur!=null){
							if(!ybCur.equals(jzCur)){
								if(ybCur.equals("CNY")){
									forexflag="2";//售汇
								}else if(jzCur.equals("CNY")){
									forexflag="1";//结汇
								}else{
									forexflag="3";//套汇
								}
							}else{
								forexflag="0";//原币记账
							}
						}
						List<Object> params=new LinkedList<Object>();	
						params.add(curBiz);
						params.add(txnno);
                        params.add(forexflag);
						params.add(tradeNo);
						params.add(jshCode);
						params.add(null);
						params.add(ybCur);
						params.add(ybCrFlag);
						params.add(ybacctno);
						params.add(realhr);
						params.add(ybamt);
						params.add(jzCur);
						params.add(jzcrflag);
						params.add(jzacctno);
						params.add(realhr);
						params.add(jzamt);
						params.add("1");
						params.add(Commons.getWorkDate());
						params.add(Commons.getWorkDate());
						params.add(tradOrgNo);
						if(!"".equals(forexflag)&&!"0".equals(forexflag)){
							String insql="insert into "+"PASPFEDETAIL"+
									"(currentbizno,txnno,forexflag,tradeno,spfecode,spfetradesource,buycursign"+
		                              ",buyisoofacct,buyacctno,sferate,buyamt,salecursign,saleisoofacct,saleacctno,pferate,saleamt" +
		                              ",tallyflag,lastmodidate,tradetime,tradorgno"+
										") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

							gjjsDao.execute(insql,params);
						}

					}

					
				} catch (Exception e) {
					cm.setRollbackOnly();
					e.printStackTrace();
				}finally{
					cm.commit();
				}
				
			} catch (EbillsException e1) {
				e1.printStackTrace();
			}
		}
}
