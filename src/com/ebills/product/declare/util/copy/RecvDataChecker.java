package com.ebills.product.declare.util.copy;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ebills.product.declare.domain.CkshhxzylJnsr;
import com.ebills.product.declare.domain.CkshhxzylJnsrHxxx;
import com.ebills.product.declare.domain.Fjmgrck;
import com.ebills.product.declare.domain.Fjmjgck;
import com.ebills.product.declare.domain.Gnwhdkbd;
import com.ebills.product.declare.domain.Gnwhdkqy;
import com.ebills.product.declare.domain.Jnhksqs;
import com.ebills.product.declare.domain.JnhksqsHxxx;
import com.ebills.product.declare.domain.Mchg;
import com.ebills.product.declare.domain.Sbdk;
import com.ebills.product.declare.domain.Whzyrmbdkbd;
import com.ebills.product.declare.domain.Whzyrmbdkqy;
import com.ebills.product.declare.domain.Yexx;
import com.ebills.product.declare.domain.Zhjh;
import com.ebills.product.declare.domain.ZhjhGlxx;
import com.ebills.product.declare.domain.Zhkgh;
import com.ebills.product.declare.domain.Zhsh;
import com.ebills.product.declare.domain.ZhshGlxx;
import com.ebills.product.declare.domain.Zhszy;
import com.ebills.utils.EbpConstants;
import com.ebills.utils.EbpDao;

/**
 * 核心申报数据校验,根据49号问附件3
 */
public class RecvDataChecker {
	private final Log log = LogFactory.getLog(getClass());

	/**
	 * 核查结汇基础信息
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 * @ejb.interface-method
	 */
	public String check_Zhjhjcxx(Zhjh info) throws Exception {
		String tip = "";
		try {
			this.checkActionType(info.getActionType());
			this.checkActionDesc(info.getActionType(), info.getActionDesc());
			this.checkRptNo(info.getRptno()); // 申报号码
			this.checkBuscode(info.getBusCode()); // 银行业务编号
			this.checkCusType(info.getCusType()); // 结汇申请人主体类型
			this.checkCusCod(info.getCusType(), info.getCustCod()); // 织机构代码
			this.checkIdCode(info.getCusType(), info.getIdCode()); // 身份证件号码
			this.CannotBeNull(info.getCustnm(), "结汇申请人"); // 结汇申请人名称
			this.CannotBeNull(info.getFcyAcc(), "外汇账户账号"); // 外汇账户账号
			this.CannotBeNull(info.getFcyCcy() + "", "结汇币别"); // 结汇币别
			this.CannotBeNull(String.valueOf(info.getFcyAmt()), "结汇金额"); // 结汇金额
			this.CannotBeNull(String.valueOf(info.getExrate()), "汇率"); // 结汇汇率
			this.CannotBeNull(info.getOppUser(), "人民币收款人名称"); // 人民币收款人名称
			this.CannotBeNullByCon(info.getLcyAcc(), info.getOppBank(),
					"人民币账户账号", "人民币账户开户行"); // (选输项)
			this.checkGUID(info);
		} catch (Exception e) {
			tip = "核心传入的结汇基础信息数据:" + info.getRptno() + "存在异常，请进行补录!<br />";
		}
		return tip;
	}

	/**
	 * 核查结汇管理信息
	 * 
	 * @param info
	 * @throws Exception
	 */
	public String check_Zhjhglxx(ZhjhGlxx info) throws Exception {
		String tip = "";
		try {
			this.checkActionType(info.getActionType());
			this.checkActionDesc(info.getActionType(), info.getActionDesc());
			this.checkRptNo(info.getRptno()); // 申报号码
			this.CannotBeNull(info.getTxCode(), "交易编码"); // 交易编码
			this.CannotBeNull(info.getCrtUser(), "填报人"); // 填报人
			this.CannotBeNull(info.getInptelc(), "填报人电话"); // 填报人电话
			this.CannotBeNull(String.valueOf(info.getRptDate()), "申报日期"); // 申报日期
			this.checkRegNo(info.getTxCode(), info.getRegno(), "外管局批件号、备案号"); // 外管局批件号、备案号
			this.checkUserType(info.getTxCode(), info.getUseType(), "结汇用途"); // 结汇用途
			this.checkUserDetail(info.getUseType(), info.getUsedeTail(),
					"结汇详细用途"); // 结汇详细用途
			this.checkGUID(info);
		} catch (Exception e) {
			tip = "核心传入的结汇管理信息数据:" + info.getRptno() + "存在异常，请进行补录!<br />";
		}
		return tip;
	}

	/**
	 * 核查购汇基础信息
	 * 
	 * @param info
	 * @throws Exception
	 */
	public String check_Zhshjcxx(Zhsh info) throws Exception {
		String tip = "";
		try {
			this.checkActionType(info.getActionType());
			this.checkActionDesc(info.getActionType(), info.getActionDesc());
			this.checkRptNo(info.getRptNo()); // 申报号码
			this.checkBuscode(info.getBusCode()); // 银行业务编号
			this.checkCusType(info.getCusType()); // 购汇申请人主体类型
			this.checkCusCod(info.getCusType(), info.getCustCod()); // 购汇申请人组织机构代码
			this.checkIdCode(info.getCusType(), info.getIdcode()); // 购汇申请人个人身份证件号码
			this.CannotBeNull(info.getCustnm(), "购汇申请人名称"); // 购汇申请人名称
			this.CannotBeNull(info.getFcyAcc(), "外汇账户账号"); // 外汇账户账号
			this.CannotBeNull(info.getOppUser(), "外汇收款人名称"); // 外汇收款人名称
			this.CannotBeNull(info.getOppBank(), "外汇账户开户行"); // 外汇账户开户行
			this.CannotBeNull(String.valueOf(info.getLcyAmt()), "购汇金额"); // 购汇金额（人民币）
			this.CannotBeNull(info.getLcyCcy(), "购汇币别"); // 购汇币别
			this.CannotBeNull(String.valueOf(info.getExrate()), "汇率"); // 购汇的汇率。
			this.checkGUID(info);
		} catch (Exception e) {
			tip = "核心传入的售汇基础信息数据:" + info.getRptNo() + "存在异常，请进行补录!<br />";
		}
		return tip;
	}

	/**
	 * 核查购汇管理信息
	 * 
	 * @param info
	 * @throws Exception
	 */
	public String check_Zhshglxx(ZhshGlxx info) throws Exception {
		String tip = "";
		try {
			this.checkActionType(info.getActionType());
			this.checkActionDesc(info.getActionType(), info.getActionDesc());
			this.checkRptNo(info.getRptNo()); // 申报号码
			this.CannotBeNull(info.getTxCode(), "交易编码"); // 交易编码
			this.CannotBeNull(info.getCrtUser(), "填报人"); // 填报人
			this.CannotBeNull(info.getInptelc(), "填报人电话"); // 填报人电话
			this.CannotBeNull(String.valueOf(info.getRptDate()), "申报日期"); // 申报日期
			this.checkRegNo(info.getTxCode(), info.getRegNo(), "外管局批件号、备案号"); // 外管局批件号、备案号
			this.checkGUID(info);
		} catch (Exception e) {
			tip = "核心传入的售汇管理信息数据:" + info.getRptNo() + "存在异常，请进行补录!<br />";
		}
		return tip;
	}

	/**
	 * 核查账户开关户信息
	 * 
	 * @param info
	 * @throws Exception
	 */
	public String check_Zhkgh(Zhkgh info) throws Exception {
		String tip = "";
		try {
			this.checkActionType(info.getActiontype());
			this.checkActionDesc(info.getActiontype(), info.getActiondesc());
			this.checkBrancCode(info.getBranch_Code(), "金融机构标识码");// 金融机构标识码
			this.checkBrancName(info.getBranch_Name()); // 金融机构名称
			this.CannotBeNull(info.getAccountno(), "外汇账户开户账号"); // 账号
			this.CannotBeNull(info.getAccountstat(), "账户状态"); // 账户状态
			this.CannotBeNull(info.getAmtype(), "开户主体类型"); // 开户主体类型
			this.CannotBeNull(info.getEn_Code(), "开户主体代码"); // 开户主体代码
			this.CannotBeNull(info.getEn_Name(), "开户主体名称"); // 开户主体名称
			this.CannotBeNull(info.getAccount_Type(), "账户性质代码"); // 账户性质代码
			this.CannotBeNull(info.getAccount_Cata(), "账户类别"); // 账户类别
			this.CannotBeNull(info.getCurrency_Code(), "币种"); // 币种
			this.CannotBeNull(String.valueOf(info.getBusiness_Date()), "业务发生时期"); // 业务发生时期
			this.CannotBeNull(info.getLimit_Type(), "限额类型"); // 限额类型
			this.checkaccountLimit(info.getLimit_Type(),
					info.getAccount_Limit()); // 账户限额
			this.checkAccountType(info.getAccount_Type(), info.getFile_Number()); // 外汇局批件号/备案表号/业务编号
			this.checkGUID(info);
		} catch (Exception e) {
			tip = "核心传入的账户开关户数据:" + info.getAccountno() + "存在异常，请进行补录!<br />";
		}
		return tip;
	}

	/**
	 * 核查账户收支余信息
	 * 
	 * @param info
	 * @throws Exception
	 */
	public String check_Zhszy(Zhszy info) throws Exception {
		String tip = "";
		try {
			this.checkActionType(info.getActiontype());
			this.checkActionDesc(info.getActiontype(), info.getActiondesc());
			this.checkBrancCode(info.getBranch_Code(), "金融机构标识码"); 					// 金融机构标识码
			this.CannotBeNull(info.getAccountno(), "外汇账户开户账号"); 						// 账号
			this.CannotBeNull(String.valueOf(info.getDeal_Date()), "业务发生日期"); 			// 业务发生日期
			this.CannotBeNull(info.getCurrency_Code(), "币种"); 								// 币种
			this.CannotBeNull(info.getBalance().toString(), "账户余额"); 					// 账户余额
			this.checkGUID(info);
		} catch (Exception e) {
			tip = "核心传入的账户收支余数据:" + info.getAccountno() + "存在异常，请进行补录!<br />";
		}
		return tip;
	}

	/**
	 * 核查外债双边贷款—签约信息
	 * 
	 * @param info
	 * @throws Exception
	 */
	public String check_Sbdk(Sbdk info) throws Exception {
		String tip = "";
		try {
			this.checkActionType(info.getActiontype());
			this.checkActionDesc(info.getActiontype(), info.getActiondesc());
			this.checkExdebtcode(info.getExdebtcode(), "外债编号"); 							// 外债编号
			this.checkBrancCode(info.getDebtorcode(), "债务人代码"); 						// 债务人代码(金融机构标识码)
			this.checkDebType(info.getDebtype()); 											// 债务类型
			this.checkTime(info.getContractdate(), "签约日期"); 								// 签约日期
			this.checkTime(info.getValuedate(), "起息日"); 									// 起息日
			this.CannotBeNull(info.getContractcurr(), "币种"); 								// 币种
			this.CannotBeNull(info.getContractamount().toString(), "签约金额"); 				// 签约金额
			this.checkTime(info.getMaturity(), "到期日"); 									// 到期日
			this.checkYorN(info.getFloatrate(), "是否浮动利率"); 							// 是否浮动利率
			this.CannotBeNull(info.getContractamount().toString(), "年化利率值"); 			// 年化利率值
			this.CannotBeNull(info.getCreditortype(), "债权人类型代码"); 					// 债权人类型代码
			this.CannotBeNull(info.getCrehqcode(), "债权人总部所在国家（地区）代码"); 		// 债权人总部所在国家（地区）代码
			this.CannotBeNull(info.getOpercode(),
					"债权人总部所在国家（地区）代码债权人经营地所在国家（地区）代码"); 			// 债权人经营地所在国家（地区）代码
			this.CannotBeNull(info.getOpercode(),
					"债权人总部所在国家（地区）代码债权人经营地所在国家（地区）代码"); 			// 债权人经营地所在国家（地区）代码
			this.checkYorN(info.getInprterm(), "是否有利息本金化条款"); 						// 是否有利息本金化条款
			this.checkYorN(info.getSpapfeboindex(), "是否经外汇局特批不需占用指标"); 			// 是是否经外汇局特批不需占用指标
			this.CannotBeNull(info.getProjectname(), "项目名称"); 							// 项目名称
			this.checkGUID(info);
		} catch (Exception e) {
			tip = "核心传入的外债双边贷款—签约信息数据:存在异常，请进行补录!<br />";
		}
		return tip;
	}

	/**
	 * 外债卖出回购—签约信息
	 * 
	 * @param info
	 * @throws Exception
	 */
	public String check_Mchg(Mchg info) throws Exception {
		String tip = "";
		try {
			this.checkActionType(info.getActiontype());
			this.checkActionDesc(info.getActiontype(), info.getActiondesc());
			this.checkExdebtcode(info.getExdebtcode(), "外债编号"); 						// 外债编号
			this.checkBrancCode(info.getDebtorcode(), "债务人代码"); 					// 债务人代码(金融机构标识码)
			this.checkDebType(info.getDebtype()); 										// 债务类型
			this.checkTime(info.getValuedate(), "起息日"); 								// 起息日
			this.CannotBeNull(info.getContractcurr(), "签约币种"); 						// 签约币种
			this.CannotBeNull(info.getContractamount().toString(), "签约金额"); 			// 签约金额
			this.checkTime(info.getMaturity(), "到期日"); 								// 到期日
			this.checkYorN(info.getFloatrate(), "是否浮动利率"); 						// 是否浮动利率
			this.CannotBeNull(String.valueOf(info.getAnninrate()), "年化利率值"); 		// 年化利率值
			this.checkCreditorType(info.getCreditortype(), "债权人类型代码"); 			// 债权人类型代码
			this.CannotBeNull(info.getCrehqcode(), "债权人总部所在国家（地区）代码"); 	// 债权人总部所在国家（地区）代码
			this.CannotBeNull(info.getOpercode(),
					"债权人总部所在国家（地区）代码债权人经营地所在国家（地区）代码"); 		// 债权人经营地所在国家（地区）代码
			this.checkYorN(info.getSpapfeboindex(), "是否经外汇局特批不需占用指标"); 		// 是是否经外汇局特批不需占用指标
			this.checkGUID(info);
		} catch (Exception e) {
			tip = "核心传入的外债卖出回购—签约信息数据:存在异常，请进行补录!<br />";
		}
		return tip;
	}

	/**
	 * 外债非居民机构存款—签约信息
	 * 
	 * @param info
	 * @throws Exception
	 */
	public String check_Fjmjgck(Fjmjgck info) throws Exception {
		String tip = "";
		try {
			this.checkActionType(info.getActiontype());
			this.checkActionDesc(info.getActiontype(), info.getActiondesc());
			this.checkExdebtcode(info.getExdebtcode(), "外债编号"); 						// 外债编号
			this.checkZhType(info.getLimittype(), "非居民账户类型"); 					// 账户类型
			this.checkBrancCode(info.getDebtorcode(), "债务人代码"); 					// 债务人代码(金融机构标识码)
			this.checkDebType(info.getDebtype()); 										// 债务类型
			this.checkTime(info.getValuedate(), "起息日"); 								// 起息日
			this.CannotBeNull(info.getContractcurr(), "签约币种"); 						// 签约币种
			this.checkYorN(info.getFloatrate(), "是否浮动利率"); 						// 是否浮动利率
			this.CannotBeNull(String.valueOf(info.getAnninrate()), "年化利率值"); 		// 年化利率值
			this.checkCreditorType(info.getCreditortype(), "债权人类型代码"); 			// 债权人类型代码
			this.CannotBeNull(info.getCrehqcode(), "债权人总部所在国家（地区）代码"); 	// 债权人总部所在国家（地区）代码
			this.CannotBeNull(info.getOpercode(),
					"债权人总部所在国家（地区）代码债权人经营地所在国家（地区）代码"); 		// 债权人经营地所在国家（地区）代码
			this.checkYorN(info.getSpapfeboindex(), "是否经外汇局特批不需占用指标"); 		// 是是否经外汇局特批不需占用指标
			this.checkGUID(info);
		} catch (Exception e) {
			tip = "核心传入的外债非居民机构存款—签约信息数据:存在异常，请进行补录!<br />";
		}
		return tip;
	}

	/**
	 * 外债非居民个人存款—签约信息
	 * 
	 * @param info
	 * @throws Exception
	 */
	public String check_Fjmgrck(Fjmgrck info) throws Exception {
		String tip = "";
		try {
			this.checkActionType(info.getActiontype());
			this.checkActionDesc(info.getActiontype(), info.getActiondesc());
			this.checkExdebtcode(info.getExdebtcode(), "外债编号"); 						// 外债编号
			this.checkBrancCode(info.getDebtorcode(), "债务人代码"); 					// 债务人代码(金融机构标识码)
			this.checkDebType(info.getDebtype()); 										// 债务类型
			this.CannotBeNull(info.getContractcurr(), "签约币种"); 						// 签约币种
			this.checkYorN(info.getFloatrate(), "是否浮动利率"); 						// 是否浮动利率
			this.CannotBeNull(String.valueOf(info.getAnninrate()), "年化利率值"); 		// 年化利率值
			this.checkCreditorType(info.getCreditortype(), "债权人类型代码"); 			// 债权人类型代码
			this.CannotBeNull(info.getCrehqcode(), "债权人总部所在国家（地区）代码"); 	// 债权人总部所在国家（地区）代码
			this.checkYorN(info.getSpapfeboindex(), "是否经外汇局特批不需占用指标"); 		// 是是否经外汇局特批不需占用指标
			this.checkGUID(info);
		} catch (Exception e) {
			tip = "核心传入的外债非居民个人存款—签约信息数据:存在异常，请进行补录!<br />";
		}
		return tip;
	}

	/**
	 * 外债—余额信息
	 * 
	 * @param info
	 * @throws Exception
	 */
	public String check_Yexx(Yexx info) throws Exception {
		String tip = "";
		try {
			this.checkActionType(info.getActiontype());
			this.checkActionDesc(info.getActiontype(), info.getActiondesc());
			this.checkExdebtcode(info.getExdebtcode(), "外债编号"); 				// 外债编号
			//this.checkChangeNo(info.getChangeno()); 							// 变动编号
			//this.CannotBeNull(info.getBuscode(), "银行账号"); 					// 银行账号
			this.CannotBeNull(info.getAccoamount().toString(), "外债余额"); 		// 外债余额
			this.CannotBeNull(info.getChdate().toString(), "变动日期"); 			// 变动日期
			this.checkGUID(info);
		} catch (Exception e) {
			tip = "核心传入的外债—余额信息数据,存在异常，请进行补录!<br />";
		}
		return tip;
	}

	/**
	 * 国内外汇贷款签约信息
	 * 
	 * @param info
	 * @throws Exception
	 */
	public String check_GnwhdkQyxx(Gnwhdkqy info) throws Exception {
		String tip = "";
		try {
			this.checkActionType(info.getActiontype());
			this.checkActionDesc(info.getActiontype(), info.getActiondesc());
			this.checkExdebtcode(info.getDofoexlocode(), "国内外汇贷款编号"); // 国内外汇贷款编号
			this.checkBrancCode(info.getCreditorcode(), "债权人代码"); // 债权人代码(金融机构标识码)
			this.checkZjjgCode(info.getDebtorcode(), "债务人代码"); // 债务人代码
			this.CannotBeNull(info.getDebtorname(), "债务人中文名称"); // 债务人中文名称
			this.checkWhdklx(info.getDofoexlotype(), "国内外汇贷款类型"); // 国内外汇贷款类型
			this.checkTime(info.getValuedate(), "起息日"); // 起息日
			this.checkTime(info.getMaturity(), "到期日"); // 到期日
			this.CannotBeNull(info.getCurrence(), "贷款币种"); // 贷款币种
			this.CannotBeNull(info.getContractamount().toString(), "签约金额"); // 签约金额
			this.CannotBeNull(String.valueOf(info.getAnninrate()), "年化利率值"); // 年化利率值
			this.checkGUID(info);
		} catch (Exception e) {
			tip = "核心传入的国内外汇贷款签约信息数据:存在异常，请进行补录!<br />";
		}
		return tip;
	}

	/**
	 * 国内外汇贷款7.2 变动信息
	 * 
	 * @param info
	 * @throws Exception
	 */
	public String check_GnwhdkBdxx(Gnwhdkbd info) throws Exception {
		String tip = "";
		try {
			this.checkActionType(info.getActiontype());
			this.checkActionDesc(info.getActiontype(), info.getActiondesc());
			this.checkExdebtcode(info.getDofoexlocode(), "国内外汇贷款编号"); // 国内外汇贷款编号
			this.CannotBeNull(info.getBuscode(), "银行业务参号"); // 银行业务参号
			this.CannotBeNull(info.getChangeno(), "变动编号"); // 变动编号
			this.CannotBeNull(info.getLoanopenbalan().toString(), "期初余额"); // 期初余额
			this.checkTime(info.getChangedate(), "变动日期"); // 变动日期
			this.CannotBeNullByCon(String.valueOf(info.getWithamount()),
					info.getWithcurrence(), "提款金额", "提款币种"); // 提款币种、提款金额
			this.CannotBeNullByCon(String.valueOf(info.getWithamount()),
					info.getUseofunds(), "提款金额", "资金用途"); // 资金用途、提款金额
			this.CannotBeNullByCon(String.valueOf(info.getSettamount()),
					info.getUseofunds(), "结汇金额", "资金用途"); // 资金用途、提款金额
			this.CannotBeNullByCon(String.valueOf(info.getRepayamount()),
					info.getPrincurr(), "还本金额", "还本币种"); // 还本金额、还本币种
			this.CannotBeNull(info.getEndbalan().toString(), "期末余额"); // 期末余额
			this.checkGUID(info);
		} catch (Exception e) {
			tip = "国内外汇贷款变动找不到签约，贷款账号为："+info.getHxxnwzbh()+"<br />";
		}
		return tip;
	}

	/**
	 * 外汇质押人民币贷款签约信息
	 * 
	 * @param info
	 * @throws Exception
	 */
	public String check_WhzyrmbdkQyxx(Whzyrmbdkqy info) throws Exception {
		String tip = "";
		try {
			this.checkActionType(info.getActiontype());
			this.checkActionDesc(info.getActiontype(), info.getActiondesc());
			this.checkExdebtcode(info.getExplrmblono(), "外汇质押人民币贷款编号"); // 外汇质押人民币贷款编号
			this.checkBrancCode(info.getDebtorcode(), "债权人代码"); // 债权人代码(金融机构标识码)
			this.checkZjjgCode(info.getDebtorcode(), "债务人代码"); // 债务人代码
			this.CannotBeNull(info.getDebtorname(), "债务人中文名称"); // 债务人中文名称
			this.checkTime(info.getValuedate(), "贷款起息日"); // 贷款起息日
			this.CannotBeNull(info.getCredconcurr(), "贷款签约币种"); // 贷款签约币种
			this.CannotBeNull(info.getCredconamount().toString(), "贷款签约金额"); // 贷款签约金额
			this.checkTime(info.getMaturity(), "贷款到期日"); // 贷款到期日
			this.CannotBeNull(info.getExplcurr(), "质押外汇币种"); // 质押外汇币种
			this.CannotBeNull(info.getExplamount().toString(), "质押外汇金额"); // 质押外汇金额
			this.checkGUID(info);
		} catch (Exception e) {
			tip = "核心传入的 外汇质押人民币贷款签约信息数据:存在异常，请进行补录!<br />";
		}
		return tip;
	}

	/**
	 * 外汇质押人民币贷款变动信息
	 * 
	 * @param info
	 * @throws Exception
	 */
	public String check_WhzyrmbdkBdxx(Whzyrmbdkbd info) throws Exception {
		String tip = "";
		try {
			this.checkActionType(info.getActiontype());
			this.checkActionDesc(info.getActiontype(), info.getActiondesc());
			this.checkExdebtcode(info.getExplrmblono(), "外汇质押人民币贷款编号"); // 外汇质押人民币贷款编号
			// this.CannotBeNull(info.getBuscode(), "银行业务参号"); // 银行业务参号
			this.checkBgqMonth(info.getBuscode(), "报告期");// 报告期(根据答疑一新增加的)
			this.CannotBeNull(info.getChangeno(), "变动编号"); // 变动编号
			this.CannotBeNull(info.getMonbeloadbal().toString(), "月初贷款余额"); // 月初贷款余额
			this.CannotBeNull(info.getMonenloadbal().toString(), "月末贷款余额"); // 月末贷款余额
			this.CannotBeNull(info.getExplbala().toString(), "质押外汇余额"); // 质押外汇余额
			this.checkGUID(info);
		} catch (Exception e) {
			tip = "核心传入的 外汇质押人民币贷款变动信息数据:存在异常，请进行补录!<br />";
		}
		return tip;
	}

	/**
	 * 核查境内汇款基础信息
	 * 
	 * @param info
	 * @throws Exception
	 */
	public void check_Jnhksqsjcxx(Jnhksqs info) throws Exception {
		try {
			this.checkActionType(info.getACTIONTYPE());
			this.checkActionDesc(info.getACTIONTYPE(), info.getACTIONDESC());
			this.checkRptNo(info.getRPTNO());
			this.checkIdCode(info.getCUSTYPE(), info.getIDCODE());
			this.checkCusCod(info.getCUSTYPE(), info.getCUSTCOD());
			this.CannotBeNull(info.getCUSTNM(), "汇款人名称");
			this.CannotBeNull(info.getOPPUSER(), "收款人名称");
			this.CannotBeNull(info.getOPPACC(), "收款人帐号");
			this.checkJnJcxxs(info.getLCYAMT(), info.getFCYAMT(),
					info.getOTHAMT(), info.getTXAMT(), info.getEXRATE(),
					info.getLCYACC(), info.getFCYACC(), info.getOTHACC());
			this.checkBuscode(info.getBUSCODE());
			this.checkGUID(info);
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("国结核查数据出错>>>" + e.getMessage());
			throw new Exception("国结核查数据出错>>>" + info.getRPTNO() + ":"
					+ e.getMessage());
		}
	}

	/**
	 * 核查境内汇款管理信息
	 * 
	 * @param info
	 * @throws Exception
	 */
	public void check_Jnhksqsglxx(JnhksqsHxxx info) throws Exception {
		try {
			this.checkActionType(info.getACTIONTYPE());
			this.checkActionDesc(info.getACTIONTYPE(), info.getACTIONDESC());
			this.checkRptNo(info.getRPTNO());
			this.CannotBeNull(info.getCOUNTRY(), "收款人常驻国家");
			this.checkJnGlxx(info.getTXCODE(), info.getTC1AMT(),
					info.getTXCODE2(), info.getTC2AMT());
			this.CannotBeNull(info.getCONTRNO(), "合同号");
			this.CannotBeNull(info.getINVOINO(), "发票号");
			this.CannotBeNull(info.getCRTUSER(), "填报人");
			this.CannotBeNull(info.getINPTELC(), "填报人电话");
			this.checkGUID(info);
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("国结核查数据出错>>>" + e.getMessage());
			throw new Exception("国结核查数据出错>>>" + info.getRPTNO() + ":"
					+ e.getMessage());
		}
	}

	/**
	 * 核查境内收入基础信息
	 * 
	 * @param info
	 * @throws Exception
	 */
	public void check_Jnsrjcxx(CkshhxzylJnsr info) throws Exception {
		try {
			this.checkActionType(info.getACTIONTYPE());
			this.checkActionDesc(info.getACTIONTYPE(), info.getACTIONDESC());
			this.checkRptNo(info.getRPTNO());
			this.checkIdCode(info.getCUSTYPE(), info.getIDCODE());
			this.checkCusCod(info.getCUSTYPE(), info.getCUSTCOD());
			this.CannotBeNull(info.getCUSTNM(), "收款人名称");
			this.CannotBeNull(info.getOPPUSER(), "付款人名称");
			this.checkJnJcxxs(info.getLCYAMT(), info.getFCYAMT(),
					info.getOTHAMT(), info.getTXAMT(), info.getEXRATE(),
					info.getLCYACC(), info.getFCYACC(), info.getOTHACC());
			this.checkBuscode(info.getBUSCODE());
			this.checkInnerCharge(info.getINCHARGEAMT(), info.getINCHARGECCY());
			this.checkGUID(info);
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("国结核查数据出错>>>" + e.getMessage());
			throw new Exception("国结核查数据出错>>>" + info.getRPTNO() + ":"
					+ e.getMessage());
		}
	}

	/**
	 * 核查境内收入管理信息
	 * 
	 * @param info
	 * @throws Exception
	 */
	public void check_Jnsrglxx(CkshhxzylJnsrHxxx info) throws Exception {
		try {
			this.checkActionType(info.getACTIONTYPE());
			this.checkActionDesc(info.getACTIONTYPE(), info.getACTIONDESC());
			this.checkRptNo(info.getRPTNO());
			this.checkJnGlxx(info.getTXCODE(), info.getTC1AMT(),
					info.getTXCODE2(), info.getTC2AMT());
			this.CannotBeNull(info.getCRTUSER(), "填报人");
			this.CannotBeNull(info.getINPTELC(), "填报人电话");
			this.checkGUID(info);
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("国结核查数据出错>>>" + e.getMessage());
			throw new Exception("国结核查数据出错>>>" + info.getRPTNO() + ":"
					+ e.getMessage());
		}
	}

	/**
	 * 校验循环体中的 主机日期、主机柜员、主机流水号 原主机日期、原主机柜员、原主机流水号格式
	 * 
	 * @param actionType
	 * @param OGUID
	 * @param NGUID
	 * @throws Exception
	 */
	private void checkGUID(Object obj) throws Exception {
		String NGUID = "";
		String Table = "";
		/*if (obj instanceof Zhjh) {
			Zhjh info = (Zhjh) obj;
			NGUID = info.getNguId();
			Table = CIConstants.TAB_SB_ZHJH;
		} else if (obj instanceof ZhjhGlxx) {
			ZhjhGlxx info = (ZhjhGlxx) obj;
			NGUID = info.getNguId();
			Table = CIConstants.TAB_SB_ZHJH_GLXX;
		} else if (obj instanceof Zhsh) {
			Zhsh info = (Zhsh) obj;
			NGUID = info.getNguId();
			Table = CIConstants.TAB_SB_ZHSH;
		} else if (obj instanceof ZhshGlxx) {
			ZhshGlxx info = (ZhshGlxx) obj;
			NGUID = info.getNguId();
			Table = CIConstants.TAB_SB_ZHSH_GLXX;
		} else */
		if (obj instanceof Zhkgh) {
			Zhkgh info = (Zhkgh) obj;
			NGUID = info.getNguid();
			Table = CIConstants.TAB_SB_ZHKGH;
		} else if (obj instanceof Zhszy) {
			Zhszy info = (Zhszy) obj;
			NGUID = info.getNguid();
			Table = CIConstants.TAB_SB_ZHSZY;
		} /*else if (obj instanceof Mchg) {
			Mchg info = (Mchg) obj;
			NGUID = info.getNguid();
			Table = CIConstants.TAB_SB_MCHG;
		} */else if (obj instanceof Fjmjgck) {
			Fjmjgck info = (Fjmjgck) obj;
			NGUID = info.getNguid();
			Table = CIConstants.TAB_SB_FJMJGCK;
		} else if (obj instanceof Fjmgrck) {
			Fjmgrck info = (Fjmgrck) obj;
			NGUID = info.getNguid();
			Table = CIConstants.TAB_SB_FJMGRCK;
		} else if (obj instanceof Yexx) {
			Yexx info = (Yexx) obj;
			NGUID = info.getNguid();
			Table = CIConstants.TAB_SB_YEXX;
		} else if (obj instanceof Gnwhdkqy) {
			Gnwhdkqy info = (Gnwhdkqy) obj;
			NGUID = info.getNguid();
			Table = CIConstants.TAB_SB_GNWHDKQY;
		} else if (obj instanceof Gnwhdkbd) {
			Gnwhdkbd info = (Gnwhdkbd) obj;
			NGUID = info.getNguid();
			Table = CIConstants.TAB_SB_GNWHDKBD;
		} else if (obj instanceof Whzyrmbdkqy) {
			Whzyrmbdkqy info = (Whzyrmbdkqy) obj;
			NGUID = info.getNguid();
			Table = CIConstants.TAB_SB_WHZYRMBDKQY;
		} else if (obj instanceof Whzyrmbdkbd) {
			Whzyrmbdkbd info = (Whzyrmbdkbd) obj;
			NGUID = info.getNguid();
			Table = CIConstants.TAB_SB_WHZYRMBDKBD;
		}

		log.debug("NGUID=[" + NGUID + "]  NGUID.length()=[" + NGUID.length()
				+ "]");
		if (GeneralCalc.isNull(NGUID))
			this.throwMyException("主机日期柜员流水不能为空");
		else if (!GeneralCalc.isNull(NGUID) && NGUID.length() > 24)
			this.throwMyException("主机日期柜员流水格式错误");
		this.checkNguid(NGUID, Table);
	}

	private void throwMyException(String Exstr) throws Exception {
		throw new Exception(Exstr);
	}

	/** ACTIONTYPE校验 */
	private void checkActionType(String str) throws Exception {
		if (!GeneralCalc.isNull(str)) {
			if ("A,C,D,R".indexOf(str) == -1)
				throw new Exception("申报类型只能为[A,C,D,R]");
			else if (str.length() != 1)
				throw new Exception("申报类型长度只能=1");
		} else {
			this.throwMyException("申报类型不能空");
		}
	}

	/** ACTIONDESC校验 */
	private void checkActionDesc(String type, String desc) throws Exception {
		if (("C".equals(type) || "D".equals(type)) && GeneralCalc.isNull(desc))
			throw new Exception("修改/删除原因不能为空");
	}

	/** 申报号码校验 */
	private void checkRptNo(String rptno) throws Exception {
		if (!GeneralCalc.isNull(rptno)) {
			if (rptno.length() != 22)
				throw new Exception("错误申报号码[" + rptno + "]");
		} else {
			throw new Exception("申报号码不能空");
		}
	}

	/** 银行业务编号校验 */
	private void checkBuscode(String buscode) throws Exception {
		if (!GeneralCalc.isNull(buscode)) {
			// 后续校验
		} else {
			throw new Exception("银行业务编号不能空");
		}
	}

	/** 申请人主体类型校验 */
	private void checkCusType(String cusType) throws Exception {
		if (GeneralCalc.isNull(cusType))
			throw new Exception("申请人主体类型不能空");
	}

	/** 织机构代码校验 */
	private void checkCusCod(String cusType, String custCod) throws Exception {
		if (!GeneralCalc.isNull(cusType) && "C".equals(cusType)) {
			if (GeneralCalc.isNull(custCod)) {
				throw new Exception("组织机构代码不能空");
			}
		} else {
			throw new Exception("申请人主体类型不能空");
		}
	}

	/** 身份证件号码校验 */
	private void checkIdCode(String cusType, String IdCode) throws Exception {
		if (!GeneralCalc.isNull(cusType) && !"C".equals(cusType))
			if (GeneralCalc.isNull(IdCode))
				throw new Exception("证件号码不能空");
			else
				throw new Exception("申请人主体类型不能空");
	}

	private boolean checkCodeInTextCodeArray(String texCode) {
		boolean result = false;
//		2014年外管国际收支代码更改		将原来的编码101010更改为121010	update by LT 2014-04-23
//		String[] zbTexCode = { "901010", "901020", "903010", "903020",
//				"903090", "904010", "904020", "904030", "904090", "909020",
//				"909110", "909130" };
		String[] zbTexCode = { "921010", "921020", "923010", "923020",
				"923090", "924010", "924020", "924030", "924090", "929020",
				"929090"};
		for (int i = 0; i < zbTexCode.length; i++) {
			if (texCode != null && texCode.equals(zbTexCode[i]))
				result = true;
		}
		return result;
	}

	/** 外管局批件号、备案号校验 */
	private void checkRegNo(String txCode, String regNo, String name)
			throws Exception {
		if (!GeneralCalc.isNull(txCode)
				&& (txCode.startsWith("5") || txCode.startsWith("6")
						|| txCode.startsWith("7") || txCode.startsWith("8"))) {
			if (regNo == null || "".equals(regNo))
				throw new Exception("当交易编码为5、6、7、8开头的资本项目项下时，" + name + "为必输项。");
		}
		if (!GeneralCalc.isNull(txCode)
				&& checkCodeInTextCodeArray(txCode) == false) {
			if (regNo == null || "".equals(regNo))
				throw new Exception("当交易编码为部分9开头的资本项目项下时，" + name + "为必输项。");
		}
	}

	/** 结汇用途校验 */
	private void checkUserType(String txCode, String userType, String name)
			throws Exception {
		if (!GeneralCalc.isNull(txCode)
				&& (txCode.startsWith("5") || txCode.startsWith("6")
						|| txCode.startsWith("7") || txCode.startsWith("8") || txCode
							.startsWith("9"))) {
			if (userType == null || "".equals(userType))
				throw new Exception("当交易编码为5、6、7、8、9开头的资本项目项下时，" + name
						+ "为必输项。");
		}
	}

	/** 结汇详细用途 */
	private void checkUserDetail(String userType, String userDetail, String name)
			throws Exception {
		if (!GeneralCalc.isNull(userType)
				&& ("005".equals(userType.trim()) || "099".equals(userType
						.trim()))) {
			if (userDetail == null || "".equals(userDetail))
				throw new Exception("当结汇用途为005支付其他服务费用或099其他时，" + name
						+ "为必输项。");
		}
	}

	/**
	 * 综合校验基础信息
	 * 
	 * @param lcamt
	 *            购汇金额
	 * @param fcyAmt
	 *            现汇金额
	 * @param othAmt
	 *            其它金额
	 * @param txAMT
	 *            汇款金额
	 * @param lcyExrate汇率
	 * @param lcyAcct
	 *            人民币帐户
	 * @param fcyAcct外币帐户
	 * @param otherAcct其它帐户
	 * @throws Exception
	 * 
	 */
	private void checkJnJcxxs(double lcamt, double fcyAmt, double othAmt,
			double txAMT, double lcyExrate, String lcyAcct, String fcyAcct,
			String otherAcct) throws Exception {

		if (isZero(lcamt) && isZero(fcyAmt) && isZero(othAmt))
			throw new Exception("购汇现汇其它金额至少输入一项");
		if (lcamt + fcyAmt + othAmt > txAMT)
			throw new Exception("购汇现汇其它金额之和不能大于汇款金额");
		if (lcamt + fcyAmt + othAmt < txAMT)
			throw new Exception("购汇现汇其它金额之和不能小于汇款金额");

		if ((GeneralCalc.isNull(lcyAcct) || isZero(lcyExrate) || isZero(lcamt))
				&& (!GeneralCalc.isNull(lcyAcct) || !isZero(lcyExrate) || !isZero(lcamt)))
			throw new Exception("购汇金额汇率帐号三个或同时空或同时有值");
		if ((isZero(fcyAmt) || GeneralCalc.isNull(fcyAcct))
				&& (!isZero(fcyAmt) || (!GeneralCalc.isNull(fcyAcct)))) {
			throw new Exception("外汇帐号金额应同时空或同时有值");
		}
		if ((isZero(othAmt) || GeneralCalc.isNull(otherAcct))
				&& (!isZero(othAmt) || (!GeneralCalc.isNull(otherAcct)))) {
			throw new Exception("其它金额帐号或同时空或同时有值");
		}
	}

	/**
	 * 综合校验管理信息
	 * 
	 * @param txCode1交易编码
	 * @param tcAmt1交易金额
	 * @param txCode2
	 * @param tcAmt2
	 * @throws Exception
	 */
	private void checkJnGlxx(String txCode1, double tcAmt1, String txCode2,
			double tcAmt2) throws Exception {

		if (GeneralCalc.isNull(txCode1)) {
			throw new Exception("交易编码1不能为空");
		}
		if (isZero(tcAmt1)) {
			throw new Exception("交易金额1不能为空或为零");
		}
		if (txCode1 == txCode2) {
			throw new Exception("交易编码1和2不能相同");
		}
		if ((GeneralCalc.isNull(txCode2) || isZero(tcAmt2))
				&& (!GeneralCalc.isNull(txCode2) || !isZero(tcAmt2))) {
			throw new Exception("交易编码2与相应金额2应同为空或同时有值");
		}
	}

	/**
	 * 国内费用校验
	 * 
	 * @param inChargeAmt
	 * @param inChargeCur
	 * @throws Exception
	 */
	private void checkInnerCharge(double inChargeAmt, String inChargeCur)
			throws Exception {
		if ((isZero(inChargeAmt) || GeneralCalc.isNull(inChargeCur))
				&& (!isZero(inChargeAmt) || !GeneralCalc.isNull(inChargeCur)))
			throw new Exception("国内银行扣费币种与金额应同为空或同时有值");
	}

	/**
	 * @param nguid
	 * @throws Exception
	 */
	private void checkNguid(String nguid, String tab) throws Exception {
		EbpDao dao = new EbpDao();
		HashMap pkInfo = new HashMap();
		pkInfo.put("txnNo", nguid);
		List<Map<String, Object>> list = dao.queryByDataId(tab, EbpConstants.TABLE_INFO, pkInfo);
		if (list != null && list.size() > 0)
			throw new Exception("已存在主机日期柜员流水:" + nguid);
	}

	private boolean isZero(double amount) {
		if (amount == 0)
			return true;
		return false;
	}

	/************************ 36 号文外汇账户、资本项目 必输项、数据有效性校验 *****************************/
	/** 必输项校验 */
	private void CannotBeNull(String str, String name) throws Exception {
		if (GeneralCalc.isNull(str))
			throw new Exception("[" + name + "]不能空");
	}

	/** 选输项校验 */
	private void CannotBeNullByCon(String conStr, String str, String conName,
			String name) throws Exception {
		if (!GeneralCalc.isNull(conStr))
			if (GeneralCalc.isNull(str))
				throw new Exception("当" + conName + "不为空时,[" + name + "]不能空");
	}

	/** 债权人、债务人代码 即 银行金融机构标识码校验 */
	private void checkBrancCode(String brancCode, String tip) throws Exception {
		if (!GeneralCalc.isNull(brancCode)) {
			// 金融机构标识码必须为12位
			if (brancCode.trim().length() != 12) {
				throw new Exception("[" + tip + "]必须为12位");
			}
		} else {
			throw new Exception("[" + tip + "]不能空");
		}
	}

	/** 组织机构代码校验 */
	private void checkZjjgCode(String zjjgCode, String tip) throws Exception {
		if (!GeneralCalc.isNull(zjjgCode)) {
			// 组织机构代码必须为9位
			if (zjjgCode.trim().length() != 9) {
				throw new Exception("[" + tip + "]必须为9位");
			}
		} else {
			throw new Exception("[" + tip + "]不能空");
		}
	}

	/** 银行金融机构名称校验 */
	private void checkBrancName(String brancName) throws Exception {
		if (!GeneralCalc.isNull(brancName)) {
		} else {
			throw new Exception("金融机构名称不能空");
		}
	}

	/** 账户限额校验 */
	private void checkaccountLimit(String limitType, BigDecimal accountLimit)
			throws Exception {
		if (Integer.parseInt(limitType) == 12
				|| Integer.parseInt(limitType) == 13) {
			if (accountLimit == null || accountLimit.doubleValue() < 0) {
				throw new Exception("当限额类型为余额限额或者贷方流入限额，账户限额不能为0，且必须大于0");
			}
		}
	}

	/** 账户性质和外管备案号校验 */
	private void checkAccountType(String accountType, String fileNumber)
			throws Exception {
		if (accountType.trim().startsWith("2")
				&& "2105".equals(accountType.trim())
				&& GeneralCalc.isNull(fileNumber)) {
			throw new Exception(
					"除【境内划入保证金专用账户】以外的各类资本项目外汇账户的开立、变更、关户，外汇局批件号/备案表号/业务编号不能为空!");
		}
	}

	/** 银行外债编号, 国内外汇贷款编号 校验 */
	private void checkExdebtcode(String exdebtcode, String tip)
			throws Exception {
		if (!GeneralCalc.isNull(exdebtcode)) {
			// 银行外债编号必须为28位
			if (exdebtcode.trim().length() != 28) {
				throw new Exception("[" + tip + "]必须为28位");
			}
		} else {
			throw new Exception("[" + tip + "]不能空");
		}
	}

	/** 债务类型校验 */
	private void checkDebType(String debType) throws Exception {
		String wzlx = "1101,1102,1103,1104,1105,1106,1107,1108,1109,1110,1111,1112,1113,1114,1115,1116,1117,1199,1201,1202,1301,1302,1303,1304,1305,9900";
		if (!GeneralCalc.isNull(debType)) {
			// 银行外债编号必须为4位
			if (debType.trim().length() != 4) {
				throw new Exception("债务类型代码必须为4位");
			}
			if (wzlx.indexOf(debType) < 0 ) {
				throw new Exception("债务类型代码有误，不是外管规定的债务类型代码");
			}
		} else {
			throw new Exception("债务类型代码不能空");
		}
	}

	/** 国内外汇贷款类型代码校验 */
	private void checkWhdklx(String whdklxCode, String tip) throws Exception {
		String wzlx = "1101,1102,1200,1300";
		if (!GeneralCalc.isNull(wzlx)) {
			// 银行外债编号必须为4位
			if (whdklxCode.trim().length() != 4) {
				throw new Exception("[" + tip + "]必须为4位");
			}
			if (wzlx.indexOf(whdklxCode) < 0 ) {
				throw new Exception("[" + tip + "]有误，不是外管规定的[" + tip + "]");
			}
		} else {
			throw new Exception("[" + tip + "]不能空");
		}
	}

	/** 8位日期字符串校验 */
	private void checkTime(Date time, String tip) throws Exception {
		if (!GeneralCalc.isNull(time.toString())) {
			try {
				String timeStr = new SimpleDateFormat("yyyyMMdd").format(time);
				// 8位日期字符串
				if (timeStr.trim().length() != 8) {
					throw new Exception("[" + tip + "]日期字符串必须为8位");
				} 
			} catch (Exception e) {
				throw new Exception("[" + tip + "]时间格式不正确");
			}
		} else {
			throw new Exception("[" + tip + "]不能空");
		}
	}

	/** 报告期校验 */
	private void checkBgqMonth(String time, String tip) throws Exception {
		if (!GeneralCalc.isNull(time)) {
			// 6位日期字符串 yyyyMM
			if (time.toString().trim().length() != 6) {
				throw new Exception("[" + tip + "]字符串必须为6位");
			} else {
				try {
					new SimpleDateFormat("yyyyMM").parse(time.toString());
				} catch (Exception e) {
					throw new Exception("[" + tip + "]格式不正确");
				}
			}
		} else {
			throw new Exception("[" + tip + "]不能空");
		}
	}

	/** 是否下拉选项的校验 */
	private void checkYorN(String floatRate, String tip) throws Exception {
		if (!GeneralCalc.isNull(floatRate)) {
			if (!"Y".equals(floatRate) && !"N".equals(floatRate)) {
				throw new Exception("[" + tip + "]的值不正确");
			}
		} else {
			throw new Exception("[" + tip + "]不能空");
		}
	}

	/** 债权人类型代码校验 */
	private void checkCreditorType(String creditortype, String tip)
			throws Exception {
		if (!GeneralCalc.isNull(creditortype)) {
			// 债权人类型代码必须为8位
			if (creditortype.trim().length() != 8) {
				throw new Exception("[" + tip + "]必须为8位");
			}
		} else {
			throw new Exception("[" + tip + "]不能空");
		}
	}

	/** 非居民账户类型校验，共分为两种： NRA NRA非居民账户 OSA OSA离岸账户 */
	private void checkZhType(String limitType, String tip) throws Exception {
		String typeStr = "NRA,OSA";
		if (!GeneralCalc.isNull(limitType)) {
			if (typeStr.indexOf(limitType) < 0 ) {
				throw new Exception("[" + tip + "]有误，不是外管规定的" + tip);
			}
		} else {
			throw new Exception("[" + tip + "]不能空");
		}
	}

//	/** 变动编号校验 */
//	private void checkChangeNo(String changeNo) throws Exception {
//		if (!GeneralCalc.isNull(changeNo)) {
//			// 变动编号必须为4位
//			if (changeNo.trim().length() != 4) {
//				throw new Exception("变动编号必须为4位");
//			}
//		} else {
//			throw new Exception("变动编号不能空");
//		}
//	}
}