package com.ebills.product.dg.AcctInterface;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.bussprocess.context.Context;

import com.ebills.util.EbillsLog;
import com.ebills.product.dg.commons.Commons;
import com.ebills.intf.spi.InterfaceManager;
import com.ebills.utils.EbpConstants;

public class AcctItsccrInterface {
	private static String className = AcctItsccrInterface.class.getName();
	private static EbillsLog log = new EbillsLog(className);

	/**
	 * 接收记帐节点调用上下文信息
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public void beforeCallItsccr(Context context) throws Exception {
		try {
			// 从上下文提取需要的数据(常用数据提取可用到常量)
			String txnNo = (String) context.get(EbpConstants.TXNSERIALNO);
			String custno = (String) context.get("BULOANS_LOANAPPNO");// 客户编号
			String accono = (String) context.get("BULOANS_LOANACCTNO");// 存款账号
			String cmitno = (String) context.get("BULOANS_CONTRACTNO");// 贸易合同号码
			String torfno = (String) context.get("txnNo");// 交易流水
			String dbitno = (String) context.get("BULOANS_DEBTNO");// 贷款拮据号码
			String cucyno = (String) context.get("BULOANS_LOANCUR");// 币别
			String tramts = (String) context.get("BULOANS_LOANAMT");// 金额
			// String remark=(String) context.get("");//摘要
			// String latype=(String) context.get("BULOANS_INTTMODE");//贷款类型
			String lnrate = (String) context.get("BULOANS_REALRATE");// 年利率
			String disbdt = (String) context.get("BULOANS_INTERESTDATE");// 起息日
			String mudate = (String) context.get("BULOANS_MATUREDATE");// 到期日
			String inrcyc = (String) context.get("BULOANS_CONTIRATETYPE");// 计息周期
			String retnfs = (String) context.get("BULOANS_INTTMODE");// 还款方式
			String fnpdid = (String) context.get("BULOANS_LOANPRD");// 产品代码
			retnfs = parseRetnfs(retnfs);
			custno = Commons.getMainCorpNo(custno);
			cucyno = Commons.getCurSignCode(cucyno, null);
			String loantp = parseLatType(fnpdid);
			context.put("txnno", txnNo);
			context.put("custno", custno);
			context.put("acctno", accono);
			context.put("loancn", cmitno);
			context.put("torfno", txnNo);
			context.put("lncfno", dbitno);
			context.put("crcycd", cucyno);
			context.put("loanam", tramts);
			// context.put("remark",(String)map.get("remark"));
			context.put("loantp", loantp);
			context.put("cntrir", lnrate);
			log.debug("日期转换为"+parseDate(disbdt));
			context.put("disbdt", parseDate(disbdt));
			context.put("matudt", parseDate(mudate));
			context.put("intrvl", inrcyc);
			context.put("retnfs", retnfs);
			context.put("fnpdid", fnpdid);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 接收记帐节点调用上下文信息
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> afterCallItsccr(Context context)
			throws Exception {
		Map<String, Object> recMap = new HashMap<String, Object>();
		try {
			Map map = (Map) context.get(InterfaceManager.RESULT_KEY);

			log.debug("返回的 原始数据>>>>>>>>>>:" + map);
			String txnNo = (String) context.get("txnNo");
			String tellerno = (String) context.get("tellerno");
			String errorCode = (String) map.get("errorCode");
			String errorMsg = (String) map.get("errorMsg");
			String workdate = (String) map.get("workdate");
			String worktime = (String) map.get("worktime");
			String serialno = (String) map.get("serialno");
			String loanac = (String) map.get("loanac");
			String transq = (String) map.get("transq");
			String trandt = (String) map.get("trandt");
			if ("00000000".equals(errorCode)) {
				DgAcctInterface.insertBuTallyercordInfo(txnNo,transq,trandt,"EBP01");//插入BUTALLYRECORDINFO表，当日冲帐用到TCCM流水号
				recMap.put("desc", errorMsg);
				recMap.put("status", errorCode);
				recMap.put("errCode", errorCode);
			} else {
				context.put(InterfaceManager.ERROR_CODE, errorCode);
				context.put(InterfaceManager.ERROR_MSG, errorMsg);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return recMap;
	}

	public String parseLatType(String fnpdid) {
		String result = "";
		if ("10001199".equals(fnpdid)) {// 信用证项下出口押汇
			result = "";
		} else if ("10001200".equals(fnpdid)) {// 托收项下出口押汇
			result = "200";
		} else if ("10001201".equals(fnpdid)) {// 信用证项下进口押汇
			result = "201";
		} else if ("10001202".equals(fnpdid)) {// 代收项下进口押汇
			result = "202";
		} else if ("10001203".equals(fnpdid)) {// 出口议付（即期）
			result = "203";
		} else if ("10001204".equals(fnpdid)) {// 出口议付（远期）
			result = "204";
		} else if ("10001205".equals(fnpdid)) {// 远期信用证项下汇票贴现
			result = "205";
		} else if ("10001206".equals(fnpdid)) {// 延期信用证项下应收款买入
			result = "206";
		} else if ("10001207".equals(fnpdid)) {// 出口商业发票融资
			result = "207";
		} else if ("10001209".equals(fnpdid)) {// 短期出口信用保险项下贷款

		} else if ("10001210".equals(fnpdid)) {// 国内代付

		} else if ("10001211".equals(fnpdid)) {// 海外代付

		} else if ("10001214".equals(fnpdid)) {// 打包贷款
			result = "214";
		}
		return result;
	}

	public String parseRetnfs(String retnfs) {
		String result = "";
		if (("1".equals(retnfs))) {
			result = "D";
		} else if ("2".equals(retnfs)) {
			result = "A";
		} else if ("0".equals(retnfs)) {
			result = "F";
		}
		return result;
	}
   public String parseDate(String date){
	   String disbdtArray[]= date.split("-");
	   return disbdtArray[0]+disbdtArray[1]+disbdtArray[2];
   }
}
