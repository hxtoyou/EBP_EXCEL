package com.ebills.product.dg.credit.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.commons.bussprocess.context.Context;

import com.eap.exception.EAPException;
import com.ebills.util.EbillsException;
import com.ebills.util.EbillsLog;
import com.ebills.utils.CommonUtil;
import com.ebills.utils.EbpConstants;
import com.ebills.utils.EbpDao;
import com.ebills.utils.OutPutBean;
import javax.servlet.http.HttpServletRequest;
/**
 * 进口、公共交易ajax
 * @author zhangQi
 *
 */
public class SearchIMCreditDao {
	private static String className = SearchIMCreditDao.class.getName();
	private static EbillsLog log = new EbillsLog(className);
	/**
	 * 校验发票号码是否做过到单
	 * @param context
	 * @param request
	 * @param output
	 * @throws EbillsException
	 * @throws EAPException
	 * @return void
	 */
	public void validInvoiceNo(Context context,HttpServletRequest request,OutPutBean output)
			throws EbillsException, EAPException{
		String invoiceNo = request.getParameter("invoiceNo");
		String abNo = request.getParameter("abNo");
		String sql ="";
		List<Object> paramList  = new ArrayList<Object>();
		if(abNo==null||"".equals(abNo)){
			//做到单交易时候的验证
			 sql ="select count(*) counts from imlcabfo f"
					+" where f.invoiceNo =?";
			 paramList.add(invoiceNo);
		}else{
			//做到单修改时候的验证
			sql ="select count(*) counts from imlcabfo f where f.invoiceno not in " 
				+"(select t.invoiceno from imlcabfo t where t.abno =? and t.invoiceno =?)"
				+" and f.invoiceno =?"	;
			paramList.add(abNo);
			paramList.add(invoiceNo);
			paramList.add(invoiceNo);
		}
		EbpDao gjjsDao = new EbpDao();
        List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
        log.info("queryRet-------------------->>>"+queryRet);
      	output = new OutPutBean(CommonUtil.ListToJson(queryRet));
        output.writeOutPut(context);
	}
	
	/**
	 * 根据提货担保编号查询运单号
	 * @param context
	 * @param request
	 * @param output
	 * @throws EbillsException
	 * @throws EAPException
	 */
	public void searchOrderNo(Context context,HttpServletRequest request,OutPutBean output)
			throws EbillsException, EAPException{
		String ladBillNo = request.getParameter("ladBillNo");
		String sql ="select WAYBILLNO from imthdbfo where ladbillno =?";
		List<Object> paramList  = new ArrayList<Object>();
		paramList.add(ladBillNo);
		EbpDao gjjsDao = new EbpDao();
		List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
		log.info("queryRet-------------------->>>"+queryRet);
	    output = new OutPutBean(CommonUtil.ListToJson(queryRet));
	    output.writeOutPut(context);
	}
	
	/**
	 * 根据代付编号查询代付金额
	 * @param context
	 * @param request
	 * @param output
	 * @throws EbillsException
	 * @throws EAPException
	 */
	public void searchAgentPayAmt(Context context,HttpServletRequest request,OutPutBean output)
			throws EbillsException, EAPException{
		String agentPayNo = request.getParameter("agentPayNo");
		String sql ="select DFAMT from imdfapplyfo where IPNO =?";
		List<Object> paramList  = new ArrayList<Object>();
		paramList.add(agentPayNo);
		EbpDao gjjsDao = new EbpDao();
		List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
		log.info("queryRet-------------------->>>"+queryRet);
	    output = new OutPutBean(CommonUtil.ListToJson(queryRet));
	    output.writeOutPut(context);
	}
	
	/**
	 * 往来函电 检查该编号必须在业务系统中已存在，且编号只能属于当前机构或当前机构的下属机构办理的业务。
	 * @param context
	 * @param request
	 * @param output
	 * @throws EbillsException 
	 * @throws EAPException 
	 */
	public void buSwiftNoValidate(Context context, HttpServletRequest request,
			OutPutBean output) throws EbillsException, EAPException {
		String bizNo = request.getParameter("bizNo");
		String arrOrgNo = request.getParameter("arrOrgNo");
		EbpDao gjjsDao = new EbpDao();
		boolean isUnder = isUnder(arrOrgNo,gjjsDao,bizNo);
		if(!isUnder){
			log.info("---------------------------->>不属于");
			return;
		}
		
		String sql ="select count(*) counts from  butxnar t " 
				+ "where t.curtbizno = ? "
				+ "and not exists (select f.bizno from pbstafo f where f.bizno = t.curtbizno and f.isclose ='Y')"
				+ "and not exists (select f.bizno from pbstafo f where f.bizno = t.prebizno and f.isclose ='Y')";
		List<Object> paramList  = new ArrayList<Object>();
		paramList.add(bizNo);
		List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
		log.info("queryRet-------------------->>>"+queryRet);
	    output = new OutPutBean(CommonUtil.ListToJson(queryRet));
	    output.writeOutPut(context);
	}
	
	/**
	 * 根据业务编号查询客户信息
	 * @param context
	 * @param request
	 * @param output
	 * @throws EAPException 
	 * @throws EbillsException 
	 */
	public void searchAppInfo(Context context, HttpServletRequest request,
			OutPutBean output) throws EAPException, EbillsException {
				String bizNo = request.getParameter("bizNo");
				String sql ="select corpno,enname from pacrp  " 
						+ "where corpno in (select custid from butxnar where curtbizno = ? and ismaintrade ='Y')";
				List<Object> paramList  = new ArrayList<Object>();
				paramList.add(bizNo);
				EbpDao gjjsDao = new EbpDao();
				List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
				String sql1 = "SELECT A,B from (select t.trancur a, t.tranamt b, rownum r from butxnar" 
							+ " t where t.curtbizno =? and t.ismaintrade = 'Y'  "
							+ "and trancur is not null) where r<=1" ;
				List<Map<String,Object>> queryRet1 = gjjsDao.sqlQuery(sql1, paramList);
				if(null!=queryRet1&&queryRet1.size()>0){
					queryRet.add(queryRet1.get(0));
				}
				log.info("queryRet-------------------->>>"+queryRet);
			    output = new OutPutBean(CommonUtil.ListToJson(queryRet));
				output.writeOutPut(context);
	}
	
	/**
	 * 注销恢复 交易 中查询器业务编号是否存在且做过注销
	 * @param context
	 * @param request
	 * @param output
	 * @throws EbillsException 
	 * @throws EAPException 
	 */
	public void pbCanNoValidate(Context context, HttpServletRequest request,
			OutPutBean output) throws EbillsException, EAPException {
		String bizNo = request.getParameter("bizNo");
		String arrOrgNo = request.getParameter("arrOrgNo");
		EbpDao gjjsDao = new EbpDao();
		boolean isUnder = isUnder(arrOrgNo,gjjsDao,bizNo);
		if(!isUnder){
			log.info("---------------------------->>不属于");
			return;
		}
		String sql ="select count(*) counts from pbstafo p, pbclosefo r"
				+" where p.isclose = 'Y'"
				+" and p.bizno = r.curbizno"
				+" and r.dealtype = 'CLOSE'"
				+" and p.bizno =?";
		List<Object> paramList  = new ArrayList<Object>();
		paramList.add(bizNo);
		List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
		log.info("queryRet-------------------->>>"+queryRet);
	    output = new OutPutBean(CommonUtil.ListToJson(queryRet));
		output.writeOutPut(context);
		
	}
	
	/**
	 * 根据业务编号 查询注销日期
	 * @param context
	 * @param request
	 * @param output
	 * @throws EAPException 
	 * @throws EbillsException 
	 */
	public void searchCalDate(Context context, HttpServletRequest request,
			OutPutBean output) throws EAPException, EbillsException {
		String bizNo = request.getParameter("bizNo");
		String sql ="select cancelDate from pbclosefo t where t.CURBIZNO = ?";
		List<Object> paramList  = new ArrayList<Object>();
		paramList.add(bizNo);
		EbpDao gjjsDao = new EbpDao();
		List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
		log.info("queryRet-------------------->>>"+queryRet);
	    output = new OutPutBean(CommonUtil.ListToJson(queryRet));
		output.writeOutPut(context);
		
	}
	
	/**
	 * 根据银行编号 如果选择的偿付行是我行账户行，查询出我行在偿付行开立的账号
	 * @param context
	 * @param request
	 * @param output
	 * @throws EAPException 
	 * @throws EbillsException 
	 */
	public void searchAcctno(Context context, HttpServletRequest request,
			OutPutBean output) throws EAPException, EbillsException {
		String bankNo = request.getParameter("bankNo");
		String Cursign = request.getParameter("Cursign");
		String sql ="select b.acctno acctno from PABANK a, PAACCFO b where a.bankno = b.custbankno"
				+" and a.bankno = ? and b.cursign =?"
				+" and b.acctTypeNo in('ZA00000051','ZA00000052')";
		List<Object> paramList  = new ArrayList<Object>();
		paramList.add(bankNo);
		paramList.add(Cursign);
		EbpDao gjjsDao = new EbpDao();
		List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
		log.info("queryRet-------------------->>>"+queryRet);
	    output = new OutPutBean(CommonUtil.ListToJson(queryRet));
		output.writeOutPut(context);
		
	}
	
	/**
	 * 根据流水号查询其对应的业务编号，和核心记账信息
	 * 输出【业务编号，客户号,业务币种，业务金额,原核心流水号，核心记账日期】
	 * @param context
	 * @param request
	 * @param output
	 * @throws EbillsException 
	 * @throws EAPException 
	 */
	public void searchBziNoByTxnNo(Context context, HttpServletRequest request,
			OutPutBean output) throws EbillsException, EAPException {
		String srhTxnNo = request.getParameter("srhTxnNo");
		String sql  = "select t.curtbizno bizno,t.custid appno ,t.TRANCUR BUSCUR," 
					+ " t.TRANAMT BUSAMT,f.hxflserialno  HXFLSERIALNO,f.tallydate TALLYDATE," 
					+ " f.ISMAINTRADE ISMAINTRADE,f.DATASNAME"
					+ " from butxnar t left join butallyrecordinfo f on t.txnno = f.txnserialno"
					+ " left join patrdtyp f on f.TRADENO = t.TRADENO "
					+ " where t.txnno = ? and TRANSTATE ='4'";
		List<Object> paramList  = new ArrayList<Object>();
		paramList.add(srhTxnNo);
		EbpDao gjjsDao = new EbpDao();
		List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
		log.info("queryRet-------------------->>>"+queryRet);
	    output = new OutPutBean(CommonUtil.ListToJson(queryRet));
		output.writeOutPut(context);
	}
	
	/**
	 * 手工冲账根据核心流水号查询业务信息
	 * @param context
	 * @param request
	 * @param output
	 * @throws EbillsException
	 * @throws EAPException
	 */
	public void searchBziNoByCoreTxnNo(Context context, HttpServletRequest request,
			OutPutBean output) throws EbillsException, EAPException {
		String coreTxnNo = request.getParameter("coreTxnNo");
		String tallyDate = "";
		String hxflSerialNo = "";
		if(coreTxnNo.length()==20){
			 tallyDate = coreTxnNo.substring(0,8);
			 hxflSerialNo = coreTxnNo.substring(8);
		}
		String sql  = "select TXNSERIALNO from butallyrecordinfo " 
					+ "where tallyDate = to_date(?,'yyyy-mm-dd') and hxflSerialNo =?";
		List<Map<String,Object>> queryRet = null;
		if(!"".equals(tallyDate)){
			List<Object> paramList  = new ArrayList<Object>();
			paramList.add(tallyDate);
			paramList.add(hxflSerialNo);
			EbpDao gjjsDao = new EbpDao();
			queryRet = gjjsDao.sqlQuery(sql, paramList);
			if(null!=queryRet&&queryRet.size()>0){
				queryRet.get(0).put("flag", true);
			}else{
				if(null == queryRet){
					queryRet =  new ArrayList<Map<String,Object>>();
				}
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("txnserialNo", "");
				map.put("flag", false);
				queryRet.add(map);
			}
		}else{
			queryRet =  new ArrayList<Map<String,Object>>();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("txnserialNo", "");
			map.put("flag", false);
			queryRet.add(map);
		}
		log.info("queryRet-------------------->>>"+queryRet);
	    output = new OutPutBean(CommonUtil.ListToJson(queryRet));
		output.writeOutPut(context);
	}
	/**
	 * 根据业务编号查询此业务是否做过大额付汇交易
	 * @param context
	 * @param request
	 * @param output
	 * @throws EbillsException 
	 * @throws EAPException 
	 */
	public void checkBigPayByBizNo(Context context, HttpServletRequest request,
			OutPutBean output) throws EbillsException, EAPException {
		String srhBizNo = request.getParameter("srhBizNo");
		String sql ="select count(*) counts from pbpayfo t where t.settlementway ='3' and t.curbizno =?";
		List<Object> paramList  = new ArrayList<Object>();
		paramList.add(srhBizNo);
		EbpDao gjjsDao = new EbpDao();
		List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
		log.info("queryRet-------------------->>>"+queryRet);
	    output = new OutPutBean(CommonUtil.ListToJson(queryRet));
		output.writeOutPut(context);
		
	}
	
	/**
	 *根据信用证号查询其项下做过的所有提货担保编号 
	 * @param context
	 * @param request
	 * @param output
	 * @throws EbillsException 
	 * @throws EAPException 
	 */
	public void initDataLadBillNo(Context context, HttpServletRequest request,
			OutPutBean output) throws EbillsException, EAPException {
		String lcNo = request.getParameter("lcNo");
		String abNo = request.getParameter("abNo");
		String flag = request.getParameter("flag");
		String sql ="";
		List<Object> paramList =null;
		if("1".equals(flag)){
			sql ="select ladbillno from imthdbfo t, pbstafo a where lcno = ?" 
				+" and not exists (select ladbillno from imthdbpayfo a where a.ladbillno = t.ladbillno)"
				+" and not exists (select ladbillno from imlcabfo b where b.ladbillno = t.ladbillno)"
				+" and t.ladbillno =a.bizno and (a.isclose is null or a.isclose ='N')";
			paramList  = new ArrayList<Object>();
			paramList.add(lcNo);
		}else{
			sql ="select ladbillno from imthdbfo t, pbstafo a  where lcno = ?" 
					+" and not exists (select ladbillno from imthdbpayfo a where a.ladbillno = t.ladbillno)"
					+" and not exists (select ladbillno from imlcabfo b where b.ladbillno = t.ladbillno and b.abno !=?)"
					+" and t.ladbillno =a.bizno and (a.isclose is null or a.isclose ='N')";
			paramList  = new ArrayList<Object>();
			paramList.add(lcNo);
			paramList.add(abNo);
		}
		EbpDao gjjsDao = new EbpDao();
		List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
		log.info("queryRet-------------------->>>"+queryRet);
	    output = new OutPutBean(CommonUtil.ListToJson(queryRet));
		output.writeOutPut(context);
		
	}
	
	/**
	 * 根据信用证号查询出气项下所有电提/电索编号
	 * @param context
	 * @param request
	 * @param output
	 * @throws EAPException 
	 * @throws EbillsException 
	 */
	public void initAbNo(Context context, HttpServletRequest request,
			OutPutBean output) throws EAPException, EbillsException {
		String lcNo = request.getParameter("lcNo");
		String sql ="select t.discno discno from imlcdiscfo t where t.lcno = ?"
				+"and t.discno not in(select b.curtbizno from butxnar b where b.tradeno = '050109' and b.prebizno = ? and b.transtate !='5')"
				+"and t.discno not in(select b.curtbizno from butxntp b where b.tradeno = '050109' and b.prebizno = ?)";
		List<Object> paramList  = new ArrayList<Object>();
		paramList.add(lcNo);
		paramList.add(lcNo);
		paramList.add(lcNo);
		EbpDao gjjsDao = new EbpDao();
		List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
		log.info("queryRet-------------------->>>"+queryRet);
	    output = new OutPutBean(CommonUtil.ListToJson(queryRet));
		output.writeOutPut(context);
	}
	
	/**
	 * 根据电提/电索编号查询相关信息
	 * 输出【不符点、电提金额、兑付行swiftCoed、兑付行名称地址、对方编号】
	 * @param context
	 * @param request
	 * @param output
	 * @throws EAPException 
	 * @throws EbillsException 
	 */
	public void searchInfoByDiscNo(Context context, HttpServletRequest request,
			OutPutBean output) throws EAPException, EbillsException {
		String abNo = request.getParameter("abNo");
		String sql ="select t.discdesc a,t.billamt b,t.negobankswiftcode c,t.negobanknameaddr d,t.BPNO e  from imlcdiscfo t" 
					+" where t.discno =?";
		List<Object> paramList  = new ArrayList<Object>();
		paramList.add(abNo);
		EbpDao gjjsDao = new EbpDao();
		List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
		log.info("queryRet-------------------->>>"+queryRet);
	    output = new OutPutBean(CommonUtil.ListToJson(queryRet));
		output.writeOutPut(context);
		
	}
	/**
	 * 根据银行swiftCode查询其所在国家的国家编号
	 * @param context
	 * @param request
	 * @param output
	 * @throws EbillsException 
	 * @throws EAPException 
	 */
	public void isOut(Context context, HttpServletRequest request,
			OutPutBean output) throws EbillsException, EAPException {
		String negSwiftCode = request.getParameter("negSwiftCode");
		String sql ="select COUNTRY COUNTRY from PABANK where BANKSWIFTCODE = ?";
		List<Object> paramList  = new ArrayList<Object>();
		paramList.add(negSwiftCode);
		EbpDao gjjsDao = new EbpDao();
		List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
		log.info("queryRet-------------------->>>"+queryRet);
	    output = new OutPutBean(CommonUtil.ListToJson(queryRet));
		output.writeOutPut(context);
	}
	
	/**
	 * 验证业务编号是否存由当前机构或当前执行机构的下属机构所办理，并查询出该业务编号的相关信息
	 * 输出【借据号，保证金比例】 
	 * @param context
	 * @param request
	 * @param output
	 * @throws EAPException 
	 * @throws EbillsException 
	 */
	public void biNoNoValidate(Context context, HttpServletRequest request,
			OutPutBean output) throws EAPException, EbillsException {
		String biNo = request.getParameter("biNo");
		String tranOrgNo = request.getParameter("tranOrgNo");
		String bisTradeNo = request.getParameter("bisTradeNo");
		String sql ="";
		log.info("bisTradeNo-------------------->>>"+bisTradeNo);
		if("050101".equals(bisTradeNo)){//信用证
			sql ="select a.feeNo CREDITNO, a.MGRRATE DEPOSITPCT from imlcissuefo a,butxnar t"
					+" where t.curtBizNo = a.lcno ";
		}else if("170102".equals(bisTradeNo)){//保函
			sql ="select a.CREDITNO CREDITNO,a.DEPOSITPCT  DEPOSITPCT from imlgissuefo a ,butxnar t"
					+" where t.curtBizNo = a.LGNO";
		}else if("050106".equals(bisTradeNo)){//提货担保
			sql ="select a.FEENO CREDITNO, a.MGRRATE DEPOSITPCT  from imthdbfo a ,butxnar t"
					+" where t.curtBizNo = a.LADBILLNO ";
		}
		//只能是当前机构或当前机构的下属机构办理
		String userLanguage = "";
		try {
		    userLanguage = (String) context.getValue(EbpConstants.USER_LANGUAGE);
		} catch (Exception e) {
			userLanguage = "en_US";
		}
		List<Map<String,Object>> list = new LinkedList<Map<String,Object>>();
		CommonUtil.queryLowerOrg(tranOrgNo, userLanguage, list, true);//查询出当前机构及其下属机构
		sql += " and t.TRANORGNO in (";
		for(int i = 0; i<list.size(); i++){
			if(i==list.size()-1){
				sql += "'"+ (String) list.get(i).get("orgNo") + "')";
			}else{
				sql += "'"+ (String) list.get(i).get("orgNo") + "',";
			}
		}
		sql += "and t.curtBizNo =?";
		List<Object> paramList  = new ArrayList<Object>();
		paramList.add(biNo);
		EbpDao gjjsDao = new EbpDao();
		List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
		log.info("queryRet-------------------->>>"+queryRet);
	    output = new OutPutBean(CommonUtil.ListToJson(queryRet));
		output.writeOutPut(context);
	}
	/**
	 * 验证账号是否在保证金组件表中存在
	 * @param context
	 * @param request
	 * @param output
	 * @throws EbillsException 
	 * @throws EAPException 
	 */
	public void isUsedAcctNo(Context context, HttpServletRequest request,
			OutPutBean output) throws EbillsException, EAPException {
		String acctNo = request.getParameter("acctNo");
		String sql ="select count(*) COUNTS from dgmgrinfoar where acctNo = ?";
		List<Object> paramList  = new ArrayList<Object>();
		paramList.add(acctNo);
		EbpDao gjjsDao = new EbpDao();
		List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
		log.info("queryRet-------------------->>>"+queryRet);
	    output = new OutPutBean(CommonUtil.ListToJson(queryRet));
		output.writeOutPut(context);
	}
	
	/**
	 * 加载保证金信息
	 * @param context
	 * @param request
	 * @param output
	 * @throws EbillsException 
	 * @throws EAPException 
	 */
	public void loadDepositInfo(Context context, HttpServletRequest request,
			OutPutBean output) throws EbillsException, EAPException {
		String bizNo = request.getParameter("bizNo");
		String sql ="select * from dgmgrinfoar t where t.bizno =?";
		List<Object> paramList  = new ArrayList<Object>();
		paramList.add(bizNo);
		EbpDao gjjsDao = new EbpDao();
		List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
		log.info("queryRet-------------------->>>"+queryRet);
	    output = new OutPutBean(CommonUtil.ListToJson(queryRet));
		output.writeOutPut(context);
	}
	
	/**
	 * 保证金管理交易提交验证
	 * @param context
	 * @param request
	 * @param output
	 * @throws EbillsException 
	 * @throws EAPException 
	 */
	public void commitSuccess(Context context, HttpServletRequest request,
			OutPutBean output) throws EbillsException, EAPException {
		String bizNo = request.getParameter("bizNo");
		String sql ="select count(*) COUNTS from dgmgrinfotp t where t.bizno =?";
		List<Object> paramList  = new ArrayList<Object>();
		paramList.add(bizNo);
		EbpDao gjjsDao = new EbpDao();
		List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
		log.info("queryRet-------------------->>>"+queryRet);
	    output = new OutPutBean(CommonUtil.ListToJson(queryRet));
		output.writeOutPut(context);
		
	}
	
	/**
	 * 查询该笔代付号是否做过发放
	 * @param context
	 * @param request
	 * @param output
	 * @throws EbillsException 
	 * @throws EAPException 
	 */
	public void isDFFF(Context context, HttpServletRequest request,
			OutPutBean output) throws EbillsException, EAPException {
		String agentPayNo = request.getParameter("agentPayNo");
		String sql ="select count(*) COUNTS from buloansfo t where t.PRIBIZNO =?";
		List<Object> paramList  = new ArrayList<Object>();
		paramList.add(agentPayNo);
		EbpDao gjjsDao = new EbpDao();
		List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
		log.info("queryRet-------------------->>>"+queryRet);
	    output = new OutPutBean(CommonUtil.ListToJson(queryRet));
		output.writeOutPut(context);
	}
	/**
	 * 根据电索编号查询电索余额
	 * 输出【电索余额，电索类型】
	 * @param context
	 * @param request
	 * @param output
	 * @throws EbillsException
	 * @throws EAPException
	 */
	public void searchDsBal(Context context, HttpServletRequest request,
			OutPutBean output) throws EbillsException, EAPException {
		String abNo = request.getParameter("abNo");
		String sql ="select a.amount AMOUNT,b.noticetype  from bubalfo a, imlcdiscfo b  where a.fieldname = 'LCDSBAL'  " +
				"and a.bizno = b.discno and bizno = ?";
		List<Object> paramList  = new ArrayList<Object>();
		paramList.add(abNo);
		EbpDao gjjsDao = new EbpDao();
		List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
		log.info("queryRet-------------------->>>"+queryRet);
	    output = new OutPutBean(CommonUtil.ListToJson(queryRet));
		output.writeOutPut(context);
	}
	
	/**
	 * 查询tradeNo
	 * @param context
	 * @param request
	 * @param output
	 * @throws EbillsException
	 * @throws EAPException
	 */
	public  void searchTradeNo(Context context, HttpServletRequest request,
			OutPutBean output) throws EbillsException, EAPException {
		String tradneName = request.getParameter("tradneName");
		String sql ="select MEUACTION from pameu t where t.meuparent = ?";
		List<Object> paramList  = new ArrayList<Object>();
		String tradeStr = tradneName + "：  ";
		paramList.add(tradneName);
		EbpDao gjjsDao = new EbpDao();
		List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
		for(int i=0;i<queryRet.size();i++){
			tradeStr +=queryRet.get(i).get("MEUACTION") +",";
		}
		log.info("tradeStr-------------------->>>"+tradeStr);
	    output = new OutPutBean(CommonUtil.ListToJson(queryRet));
		output.writeOutPut(context);
	}
	
	/**
	 * 根据传入执行机构字符串，业务编号查询此笔业务编号的执行机构是否存在于执行机构字符创中
	 * @param arrOrgNo 执行机构字符串
	 * @param gjjsDao
	 * @param bizNo 业务编号
	 * @return
	 * @throws EbillsException
	 */
	private boolean isUnder(String arrOrgNo,EbpDao gjjsDao,String bizNo) throws EbillsException{
		boolean isUnder = false; //用于判断当前执行机构是否属于业务编号的执行机构或者是前执行机构下属机构
		if(arrOrgNo.length()>0){
			String tranSql ="select distinct t.tranorgno tranorgno  from butxnar t where t.curtbizno = ? ";
			List<Object> paramTmp  = new ArrayList<Object>();
			paramTmp.add(bizNo);
			List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(tranSql, paramTmp);
			String orgn ="";
			if(queryRet!=null&&queryRet.size()>0){
				orgn = (String) queryRet.get(0).get("TRANORGNO");
			}
			
			String [] arrOrgNos = arrOrgNo.split(",");
			log.info("arrOrgNo-------------------->>>"+arrOrgNo);
			log.info("orgn-------------------->>>"+orgn);
			log.info("arrOrgNos-------------------->>>"+Arrays.toString(arrOrgNos));	
			for(int i = 0; i<arrOrgNos.length; i++){
				if(arrOrgNos[i].equals(orgn)){
					isUnder= true;
					break;
				}
			}
		}
		return isUnder;
	}
	
	
	/**
	 * 根据前台传入的tradeNo字符串和客户id查询此客户下所有费用信息
	 * @param context
	 * @return
	 * @throws EbillsException
	 */
	public  List<Map<String,Object>>  getChargeInfoByTradeNo(Context context,HttpServletRequest request) throws EbillsException{
		List<Map<String,Object>> lOutPut = null;
		try {
			lOutPut = new ArrayList<Map<String,Object>>();
			EbpDao gjjsDao = new EbpDao();
			int chargeType = Integer.valueOf((String)CommonUtil.getValFromContext(context,"chrType"));//费用类型
			String cusId = (String)CommonUtil.getValFromContext(context,"cusId");//客户号
			String startDate = (String)request.getParameter("startDate");//开始日期
			String endDate = (String)request.getParameter("endDate");//截止日期
			String tranOrgNo = (String)request.getParameter("tranOrgNo");//截止日期
			log.info("startDate-------------------->>>"+startDate);
			log.info("endDate-------------------->>>"+endDate);
			String bizNoType = "";//交易类型	
			List<String> tradeNoArr = new ArrayList<String>(); //对应类型下交易编号
			//查询此客户下对应费用分类的业务编号
			switch(chargeType){
			 case 1 :bizNoType = "信用证";break;
			 case 2 :bizNoType = "汇款";break;
			 case 3 :bizNoType = "托收";break;
			 case 4 :bizNoType = "保函";break;
			 case 5 :bizNoType = "所有";break;
			}
			String sql1 = "select keyval KEYVAL from palan where val = ? and tabname ='BUCHARGNEW.chargeType'";
			List<Object> paramTradeNo  = new ArrayList<Object>();
			paramTradeNo.add(bizNoType);
			List<Map<String,Object>> tradeNoList = gjjsDao.sqlQuery(sql1, paramTradeNo);
			String tradeNo = "";
			if(tradeNoList.size()>=0){
				 tradeNo =(String)tradeNoList.get(0).get("KEYVAL");
			}
			String [] tradeNoarr =tradeNo.split(",");
			if(tradeNoarr.length<=0) return null;
			for(int i =0;i<tradeNoarr.length; i++){
				tradeNoArr.add(tradeNoarr[i]);
			}
			log.info("tradeNoArr-------------------->>>"+Arrays.toString(tradeNoarr));	
			//查询客户对应tradeno下的业务编号  查询范围 开始日期 截止日期
			String sql2 = "select distinct t.curtbizno CURTBIZNO from butxnar t where t.custid = ? and tradeNo in(";
			for(int i =0;i<tradeNoArr.size();i++){
				if(i==tradeNoArr.size()-1){//最后一个tradeNo
					sql2 += "'"+ tradeNoArr.get(i) + "')";
				}else{
					sql2 += "'"+ tradeNoArr.get(i) + "',";
				}
			}
			sql2 += " and (to_char(FINDATE, 'yyyy-mm-dd') >=?"+formatSql(startDate);
			sql2 += " and (to_char(FINDATE, 'yyyy-mm-dd') <=?"+formatSql(endDate);
			//查询的费用，只能是当前机构或当前机构的下属机构办理的未收费用；
			String userLanguage = "";
			try {
			    userLanguage = (String) context.getValue(EbpConstants.USER_LANGUAGE);
			} catch (Exception e) {
				userLanguage = "en_US";
			}
			List<Map<String,Object>> list = new LinkedList<Map<String,Object>>();
			CommonUtil.queryLowerOrg(tranOrgNo, userLanguage, list, true);//查询出当前机构及其下属机构
			sql2 += " and t.TRANORGNO in (";
			for(int i = 0; i<list.size(); i++){
				if(i==list.size()-1){
					sql2 += "'"+ (String) list.get(i).get("orgNo") + "')";
				}else{
					sql2 += "'"+ (String) list.get(i).get("orgNo") + "',";
				}
			}
			List<Object> paramBizNo  = new ArrayList<Object>();
			paramBizNo.add(0,cusId);
			paramBizNo.add(1,startDate);
			paramBizNo.add(2,endDate);
			List<Map<String,Object>> bizNoArr = gjjsDao.sqlQuery(sql2, paramBizNo);
			List<String> bizNoList = new ArrayList<String>();//此客户对应tradeNo下的业务编号
			for(int i =0;i<bizNoArr.size(); i++){
				String curtbizno =(String)bizNoArr.get(i).get("CURTBIZNO");
				bizNoList.add(curtbizno);
			}
			//查询出此对应业务编号下的费用条目 
			String sql3 ="";
			if(bizNoList.size()>0){
				sql3 ="ispayment ='2' and bizno in(";
				for(int i =0;i<bizNoList.size();i++){
					if(i==bizNoList.size()-1){//最后一个tradeNo
						sql3 = sql3 + "'"+bizNoList.get(i)+"')";
					}else{
						sql3 = sql3 + "'"+bizNoList.get(i)+"',";
					}
				}
			}
			
			if(!"".equals(sql3)){
				//带出业务全部为前述业务，不暂收
				List<Map<String,Object>>  lTmpData = gjjsDao.queryByDataId("bucharge",EbpConstants.TABLE_INFO, sql3);
				if(null != lTmpData && !lTmpData.isEmpty()){
					for (Map<String,Object> mInfoData : lTmpData ) {
						mInfoData.put("isCurrent","N");
						mInfoData.put("isTemp","N");
						//将后手费用显示为现收
						mInfoData.put("isPayment","1");
					}
					lOutPut.addAll(lTmpData);
				}
			}
			
		} catch (EbillsException ex) {
			throw ex;
		}  catch (Exception e) {
			throw new EbillsException(e,className,3,null,null) ;
		} 
		return lOutPut;
	}
	/**
	 * 待收费用收取手续费查询
	 * @param context
	 * @param request
	 * @param output
	 * @throws EbillsException
	 * @throws EAPException
	 */
	public void searchChargeInfo(Context context, HttpServletRequest request,
			OutPutBean output) throws EbillsException, EAPException {
		 List<Map<String,Object>> queryRet = getChargeInfoByTradeNo(context,request);
		 log.info("queryRet-------------------->>>"+queryRet);
		 output = new OutPutBean(CommonUtil.ListToJson(queryRet));
		 output.writeOutPut(context);
	}
	
	/**
	 * 付汇未发报勾对条件查询
	 * @param context
	 * @param request
	 * @param output
	 * @throws EbillsException 
	 * @throws EAPException 
	 */
	public void searchPayCheckInfo(Context context, HttpServletRequest request,
			OutPutBean output) throws EbillsException, EAPException {
		String tradeNo = request.getParameter("tradeNo");
		String bizNo = request.getParameter("bizNo");
		String cur = request.getParameter("cur");
		String isCheck = request.getParameter("isCheck");
		String acctBktSwiftcode = request.getParameter("acctBktSwiftcode");
		String amtMax = request.getParameter("amtMax");
		String amtMin = request.getParameter("amtMin");
		String begin = request.getParameter("begin");
		String end = request.getParameter("end");
		
		String sql ="select a.tradeno tradeNo,a.txnno txnno,a.curbizno curBizNo, a.acctbkname acctBkName,c.orgname tranOrgNo,a.paycur payCur," 
				+ "a.payamt payAmt,a.paydate payDate,d.username oparater,a.ischeck isCheck" 
				+ " from pbpayfo a left join butxnar b on a.txnno = b.txnno  left join PAORG c  on b.tranorgno = c.orgno "
				+ " left join pausr d on d.userid = b.checkerid where a.isPacket ='N'";
		
		sql += " and (upper(a.tradeNo) like upper(?)" + formatSql(tradeNo);
		sql += " and (upper(a.curBizNo) like upper(?)" + formatSql(bizNo);
		sql += " and (upper(a.payCur) like upper(?)" + formatSql(cur);
		sql += " and (upper(a.isCheck) like upper(?)" + formatSql(isCheck);
		sql += " and (upper(a.ACCTBKSWIFT) like upper(?) " + formatSql(acctBktSwiftcode);
		sql += " and (payAmt >= ?" + formatSql(amtMax);
		sql += " and (payAmt <= ?"  + formatSql(amtMin) ;
		sql += " and (payDate >= to_date( ?,'YY-MM-DD')" + formatSql(begin);
		sql += " and (payDate <= to_date( ?,'YY-MM-DD')" + formatSql(end);
		sql += " order by txnno,payDate desc";
		log.info("sql-------------------->>>"+sql);	
		List<Object> paramList  = new ArrayList<Object>();
		paramList.add(0,tradeNo);
		paramList.add(1,bizNo);
		paramList.add(2,cur);
		paramList.add(3,isCheck);
		paramList.add(4,acctBktSwiftcode);
		paramList.add(5,amtMax);
		paramList.add(6,amtMin);
		paramList.add(7,begin);
		paramList.add(8,end);

		EbpDao gjjsDao = new EbpDao();
		List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
		String canChange = "";
		//如果已经勾对的需要增加一个标志，使得其不能再页面上去掉其勾对状态
		for(int i = 0; i<queryRet.size();i++){
			Map<String, Object> map = queryRet.get(i);
			String ISCHECK = (String) map.get("ISCHECK");
			log.info("ISCHECK ---------->>>" + ISCHECK);
			if("Y".equals(ISCHECK)){
				canChange = "N";
			}else{
				canChange = "Y";
			}
			map.put("CANCHANGE", canChange);
		}
		log.info("queryRet-------------------->>>"+queryRet);
	    output = new OutPutBean(CommonUtil.ListToJson(queryRet));
		output.writeOutPut(context);
	}
	
	/**
	 * sql拼接
	 * @param arg
	 * @return
	 */
	private String formatSql(String arg) {
		String sql ="";
		if(null!=arg&&arg.trim().length()==0){
			sql += " or null is null)";
		}else{
			sql += " or 1 is null)";
		}
		return sql;
	}
	
	/**
	 * 根据业务为流水号查询账务信息
	 * @param context
	 * @param request
	 * @param output
	 * @throws EbillsException 
	 * @throws EAPException 
	 */
	public void doLoadManualInfo(Context context, HttpServletRequest request,
			OutPutBean output) throws EbillsException, EAPException {
		String txnNo = request.getParameter("txnNo");
		String sql ="select * from buaccvouar t where  t.txnno = ? and state ='0'";
		List<Object> paramList  = new ArrayList<Object>();
		paramList.add(txnNo);
		EbpDao gjjsDao = new EbpDao();
		List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
		log.info("queryRet-------------------->>>"+queryRet);
	    output = new OutPutBean(CommonUtil.ListToJson(queryRet));
		output.writeOutPut(context);
	}
	
	/**
	 * 手工冲账验证原业务流水号
	 * @param context
	 * @param request
	 * @param output
	 * @throws EbillsException 
	 * @throws EAPException 
	 */
	@SuppressWarnings("deprecation")
	public void validateTxnNo(Context context, HttpServletRequest request,
			OutPutBean output) throws EbillsException, EAPException {
		String srhTxnNo = request.getParameter("srhTxnNo");//原业务编号
		String userId = request.getParameter("userId");//柜员ID
		String tranOrgNo = request.getParameter("tranOrgNo");//当前执行机构
		String erroType ="";
		Map<String,Object> map = new HashMap<String,Object>();
		EbpDao gjjsDao = new EbpDao();
		List<Object> paramList  = new ArrayList<Object>();
		//验证该流水水号是否存在(erroType : 1)
		String sql1 ="select count(*) COUNTS from butxnar t where  t.txnno = ? and TRANSTATE ='4'";
		paramList.add(srhTxnNo);
		List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql1, paramList);
		List<Map<String,Object>> outPutList = new ArrayList<Map<String,Object>>();
		if(queryRet!=null&&queryRet.size()>0){
			BigDecimal countsBig = (BigDecimal) queryRet.get(0).get("COUNTS");
			int counts = countsBig.intValue();
			if(counts==0){
				erroType = "1";
				map.put("erroType", erroType);
				outPutList.add(map);
				output = new OutPutBean(CommonUtil.ListToJson(outPutList));
				output.writeOutPut(context);
				return;
			}
		}
		//隔日的冲账只能由本机构的柜员处理。(erroType : 2)
		if(queryRet != null){
			queryRet.clear();
		}
		paramList.clear();
		String sql2 ="select (select trandate TRANDATE from butxnar where txnno = ?) A," 
						+ " (select sysVal from pasys where sysnameid ='workDate') B from dual";
		paramList.add(srhTxnNo);
		queryRet  = gjjsDao.sqlQuery(sql2, paramList);
		if(queryRet!=null&&queryRet.size()>0){
			Date tanDateSql = (Date) queryRet.get(0).get("A");//原业务复核时间  
			String sysval = (String) queryRet.get(0).get("B");//系统当前时间
			log.info("tanDateSql-------------------->>>"+tanDateSql.toLocaleString());//2015-1-5 0:00:00
			log.info("sysval-------------------->>>"+sysval);		//2015-01-05
			String tranDate = tanDateSql.toLocaleString().split(" ")[0]; //2015-1-5
			String [] tranDateArr = tranDate.split("-"); 
			String [] sysvalArr =sysval.split("-");
			boolean flag = false;
			for(int i =0 ;i <tranDateArr.length; i++){
				if(tranDateArr[i].equals(sysvalArr[i])){
					flag =true;
				}else{
					if(tranDateArr[i].equals(sysvalArr[i].substring(1))){
						log.info(tranDateArr[i] +"--"+sysvalArr[i].substring(1)+"--"+sysvalArr[i]);
						flag =true;
					}else{
						flag =false;
						break;
					}
				}
			}
			log.info("tranDate-------------------->>>"+tranDate);
			log.info("sysval-------------------->>>"+sysval);
			queryRet.clear();
			paramList.clear();
			if(!flag){
				sql2 ="select count(*) COUNTS from butxnar t where t.txnno = ? and t.tranorgno = ?";
				paramList.add(srhTxnNo);
				paramList.add(tranOrgNo);
				queryRet  = gjjsDao.sqlQuery(sql2, paramList);
				BigDecimal countsBig = (BigDecimal) queryRet.get(0).get("COUNTS");
				int counts = countsBig.intValue();
				if(counts==0){
					erroType = "2";
					map.put("erroType", erroType);
					outPutList.add(map);
					output = new OutPutBean(CommonUtil.ListToJson(outPutList));
					output.writeOutPut(context);
				}
			}else{
				//当日的业务冲账只能由经办的柜员处理(erroType : 3)
				String sql3 ="select count(*) COUNTS from butxnar where  HANDLERID = ? and txnNo =?";
				paramList.add(userId);
				paramList.add(srhTxnNo);
				queryRet  = gjjsDao.sqlQuery(sql3, paramList);
				BigDecimal countsBig = (BigDecimal) queryRet.get(0).get("COUNTS");
				int counts = countsBig.intValue();
				if(counts==0){
					erroType = "3";
					map.put("erroType", erroType);
					outPutList.add(map);
					output = new OutPutBean(CommonUtil.ListToJson(outPutList));
					output.writeOutPut(context);
				}
			}
		}
	}
	
	/**
	 * 手工冲账验证此原业务流水号所对应的交易是否发送过报文
	 * @param context
	 * @param request
	 * @param output
	 * @throws EbillsException 
	 * @throws EAPException 
	 */
	public void hasSendPackets(Context context, HttpServletRequest request,
			OutPutBean output) throws EbillsException, EAPException {
		String srhTxnNo = request.getParameter("srhTxnNo");
		// AB 废弃状态  FD 修改状态
		String sql ="select count(*) COUNTS from MSSDMSGFO " 
					+ " where state not in('AB','FD')"
					+ " and txnno = ?";
		List<Object> paramList  = new ArrayList<Object>();
		paramList.add(srhTxnNo);
		EbpDao gjjsDao = new EbpDao();
		List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
		log.info("queryRet-------------------->>>"+queryRet);
	    output = new OutPutBean(CommonUtil.ListToJson(queryRet));
		output.writeOutPut(context);
	}
	
	/**
	 * 待收费用收取  查询的费用，只能是当前机构或当前机构的下属机构办理的未收费用；
	 * @param context
	 * @param request
	 * @param output
	 * @throws EbillsException 
	 * @throws EAPException 
	 */
	public void validateChargeBizno(Context context,
			HttpServletRequest request, OutPutBean output) throws EbillsException, EAPException {
		String bizNo = request.getParameter("bizNo");
		String tranOrgNo = request.getParameter("tranOrgNo");
		String sql ="select count(*) COUNTS from butxnar t where (t.TRANORGNO = ?"
				+" or t.TRANORGNO in (select b.orgcode from PAORG b where b.PARENTORGNO = ?))"
				+" and t.curtbizno =?";
		List<Object> paramList  = new ArrayList<Object>();
		paramList.add(tranOrgNo);
		paramList.add(tranOrgNo);
		paramList.add(bizNo);
		EbpDao gjjsDao = new EbpDao();
		List<Map<String,Object>> queryRet = gjjsDao.sqlQuery(sql, paramList);
		log.info("queryRet-------------------->>>"+queryRet);
	    output = new OutPutBean(CommonUtil.ListToJson(queryRet));
		output.writeOutPut(context);
		
	}
	
	
}
