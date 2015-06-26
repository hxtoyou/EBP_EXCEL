package com.ebills.product.dg.imdf;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.bussprocess.context.Context;
import org.apache.poi.ss.usermodel.DataFormat;

public class TradingServ {
	public void recevice(Context context) {
		// context.put(key, value)
		// {LOANCUR=, HKFS=, PRDCD=, YQDKRATE=, ZCDKRATE=, JXZQ=, DKACCT=,
		// ACCTORG=, STARTDATE=, KHH=客户号, HTH=, JJH=客户号, ENDDATE=}
		System.out.println("dbitno========================"+context.get("dbitno"));
		context.put("errorCode", "12324");//客户号
		context.put("errorMsg", "123425");//账务机构
		context.put("workdate", "2134214");//合同号
		context.put("worktime", "213424");//借据号
		context.put("serialno", "125234");//产品代码
		context.put("channelserno", "USD");//融资币种
		
		context.put("custno", "1234");//融资金额
		context.put("orgno","2014-09-29");//起息日
		context.put("cmitno", "2014-09-29");//到期日
		context.put("dbitno", "0");//还款方式
		context.put("fnpdid", "123");//计息周期
		context.put("cucyno", "123");//正常贷款执行利率
		context.put("tramts", "123");//贷款入账账号
		context.put("valuedate", "123");//逾期贷款执行利率
		
		context.put("mudate", "12324");//客户号
		context.put("retnfs", "123425");//账务机构
		context.put("overrate", 0.00012);//合同号
		context.put("accono", "213424");//借据号
		context.put("rate", 0.00019);//产品代码
		context.put("inrcyc", "USD");//融资币种
	}
}
