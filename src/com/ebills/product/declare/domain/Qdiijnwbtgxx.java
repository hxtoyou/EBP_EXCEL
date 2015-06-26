package com.ebills.product.declare.domain;


import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import com.ebills.product.declare.Entity;

/**
 * 
 * @author对应的表名：sb_Qdiijnwbtgxx
 *
 */

public class Qdiijnwbtgxx extends Entity {
    /**@build by Class builder @auth XJBS*/
	/**操作流水号*/
	private String nguid;
	/**QDII机构代码*/
	private String qdiiorgno;
	/**QDII机构中文名称*/
	private String qdiiorgname;
	/**QDII境内托管行代码*/
	private String qdiidepoitno;
	/**产品/客户名称*/
	private String customno;
	/**境内外汇托管账户账号*/
	private String inwardaccountno;
	/**报告期*/
	private String reportdate;
	/**币种*/
	private String currency;
	/**月末账户余额*/
	private BigDecimal endbalance;
	/**月末账户余额折美元*/
	private BigDecimal endbalancetousd;
	/**本月购汇金额*/
	private BigDecimal lcyamt;
	/**本月购汇金额折美元*/
	private BigDecimal lcyamttousd;
	/**累计购汇金额*/
	private BigDecimal lcyamtsum;
	/**累计购汇金额折美元*/
	private BigDecimal lcyamtsumtousd;
	/**本月境外托管账户划入金额*/
	private BigDecimal depoitamt;
	/**本月境外托管账户划入金额折美元*/
	private BigDecimal depoitamttousd;
	/**累计境外托管账户划入金额*/
	private BigDecimal depoitamtsum;
	/**累计境外托管账户划入金额折美元*/
	private BigDecimal depoitamtsumtousd;
	/**本月申购款汇入金额*/
	private BigDecimal inwardamt;
	/**本月申购款汇入金额折美元*/
	private BigDecimal inwardamttousd;
	/**累计申购款汇入金额*/
	private BigDecimal inwardamtsum;
	/**累计申购款汇入金额折美元*/
	private BigDecimal inwardamtsumtousd;
	/**本月利息收入金额*/
	private BigDecimal insterest;
	/**本月利息收入金额折美元*/
	private BigDecimal insteresttousd;
	/**累计利息收入金额*/
	private BigDecimal insterestsum;
	/**累计利息收入金额折美元*/
	private BigDecimal inseterestsumtousd;
	/**本月其他收入金额*/
	private BigDecimal otheramt;
	/**本月其他收入金额折美元*/
	private BigDecimal otheramttousd;
	/**累计其他收入金额*/
	private BigDecimal otheramtsum;
	/**累计其他收入金额折美元*/
	private BigDecimal otheramtsumtousd;
	/**本月收入合计*/
	private BigDecimal monthamtsum;
	/**本月收入合计折美元*/
	private BigDecimal monthamtsumtousd;
	/**累计收入合计*/
	private BigDecimal amtsum;
	/**累计收入合计折美元*/
	private BigDecimal amtsumtousd;
	/**本月结汇金额*/
	private BigDecimal fcyamt;
	/**本月结汇金额折美元*/
	private BigDecimal fcyamttousd;
	/**累计结汇金额*/
	private BigDecimal fcyamtsum;
	/**累计结汇金额折美元*/
	private BigDecimal fcyamtsumtousd;
	/**本月划往境外托管账户金额*/
	private BigDecimal outdepoitamt;
	/**本月划往境外托管账户金额折美元*/
	private BigDecimal outdepoitamttousd;
	/**累计划往境外托管账户金额*/
	private BigDecimal outdepoitamtsum;
	/**累计划往境外托管账户金额折美元*/
	private BigDecimal outdepoitamtsumtousd;
	/**本月支付赎回款金额*/
	private BigDecimal ransomamt;
	/**本月支付赎回款金额折美元*/
	private BigDecimal ransomamttousd;
	/**累计支付赎回款金额*/
	private BigDecimal ransomamtsum;
	/**累计支付赎回款金额折美元*/
	private BigDecimal ransomamtsumtousd;
	/**本月分红金额*/
	private BigDecimal shareamt;
	/**本月分红金额折美元*/
	private BigDecimal shareamttousd;
	/**累计分红金额*/
	private BigDecimal shareamtsum;
	/**累计分红金额折美元*/
	private BigDecimal shareamtsumtousd;
	/**本月托管费支出金额*/
	private BigDecimal depoitoutamt;
	/**本月托管费支出金额折美元*/
	private BigDecimal depoitoutamttousd;
	/**累计托管费支出金额*/
	private BigDecimal depoitoutamtsum;
	/**累计托管费支出金额折美元*/
	private BigDecimal depoitoutamtsumtousd;
	/**本月管理费支持金额*/
	private BigDecimal manageramt;
	/**本月管理费支持金额折美元*/
	private BigDecimal manageramttousd;
	/**累计管理费支出金额*/
	private BigDecimal manageramtsum;
	/**累计管理费支出金额折美元*/
	private BigDecimal manageramtsumtousd;
	/**本月手续费支出金额*/
	private BigDecimal chargeamt;
	/**本月手续费支出金额折美元*/
	private BigDecimal chargeamttousd;
	/**累计手续费支出金额*/
	private BigDecimal chargeamtsum;
	/**累计手续费支出金额折美元*/
	private BigDecimal chargeamtsumtousd;
	/**本月其他支出金额*/
	private BigDecimal otheroutamt;
	/**本月其他支出金额折美元*/
	private BigDecimal otheroutamttousd;
	/**累计其他支出金额*/
	private BigDecimal otheroutamtsum;
	/**累计其他支出金额折美元*/
	private BigDecimal otheroutamtsumtousd;
	/**本月支出合计金额*/
	private BigDecimal monthoutamt;
	/**本月支出合计金额折美元*/
	private BigDecimal monthoutamttousd;
	/**累计支出金额合计*/
	private BigDecimal outamtsum;
	/**累计支出金额合计折美元*/
	private BigDecimal outamtsumtousd;
	/**备注*/
	private String remark;
	//业务流水号
	private String rwidh;
	//业务编号 
	private String ywbh;
	//是否已申报 
	private String sfysb;
	//生效标志 
	private String sxbz;
	//最新标志 
	private String sfzx;
	//机构编号 
	private String bank_Id;
	//经办人 
	private int handid;
	//经办日期 
	private Timestamp handdate;
	//复核人  
	private int checkid;
	//复核时间
	private Timestamp checkdate;
	//是否复核通过
	private String ischeck;
	//授权人 
	private int authid;
	//授权时间 
	private Timestamp authdate;
	//授权是否通过
	private String isauth;
	//
    private String oguid;
    
    private Date recdate;
    
    private String datasources;
    
    public Date getRecdate() {
		return recdate;
	}
	public void setRecdate(Date recdate) {
		this.recdate = recdate;
	}
	public String getDatasources() {
		return datasources;
	}
	public void setDatasources(String datasources) {
		this.datasources = datasources;
	}
	public Qdiijnwbtgxx(){
		this.setSfysb("N");
		this.setSfzx("Y");
		this.setSxbz("0");
		this.setEndbalance(new BigDecimal(0));
		this.setEndbalance(new BigDecimal(0));
		this.setLcyamt(new BigDecimal(0));
		this.setLcyamttousd(new BigDecimal(0));
		this.setLcyamtsum(new BigDecimal(0));
		this.setLcyamtsumtousd(new BigDecimal(0));
		this.setDepoitamt(new BigDecimal(0));
		this.setDepoitamttousd(new BigDecimal(0));
		this.setDepoitamtsum(new BigDecimal(0));
		this.setDepoitamtsumtousd(new BigDecimal(0));
		this.setInwardamt(new BigDecimal(0));
		this.setInwardamttousd(new BigDecimal(0));
		this.setInwardamtsum(new BigDecimal(0));
		this.setInwardamtsumtousd(new BigDecimal(0));
		this.setInsterest(new BigDecimal(0));
		this.setInsteresttousd(new BigDecimal(0));
		this.setInsterest(new BigDecimal(0));
		this.setInseterestsumtousd(new BigDecimal(0));
		this.setOtheramt(new BigDecimal(0));
		this.setOtheramttousd(new BigDecimal(0));
		this.setOtheramtsum(new BigDecimal(0));
		this.setOtheramtsumtousd(new BigDecimal(0));
		this.setMonthamtsum(new BigDecimal(0));
		this.setMonthamtsumtousd(new BigDecimal(0));
		this.setAmtsum(new BigDecimal(0));
		this.setAmtsumtousd(new BigDecimal(0));
		this.setFcyamt(new BigDecimal(0));
		this.setFcyamttousd(new BigDecimal(0));
		this.setFcyamtsum(new BigDecimal(0));
		this.setFcyamtsumtousd(new BigDecimal(0));
		this.setOutdepoitamt(new BigDecimal(0));
		this.setOutdepoitamttousd(new BigDecimal(0));
		this.setOutdepoitamtsum(new BigDecimal(0));
		this.setOutdepoitamtsumtousd(new BigDecimal(0));
		this.setRansomamt(new BigDecimal(0));
		this.setRansomamttousd(new BigDecimal(0));
		this.setRansomamtsum(new BigDecimal(0));
		this.setRansomamtsumtousd(new BigDecimal(0));
		this.setShareamt(new BigDecimal(0));
		this.setShareamttousd(new BigDecimal(0));
		this.setShareamtsum(new BigDecimal(0));
		this.setShareamtsumtousd(new BigDecimal(0));
		this.setDepoitoutamt(new BigDecimal(0));
		this.setDepoitoutamttousd(new BigDecimal(0));
		this.setDepoitoutamtsum(new BigDecimal(0));
		this.setDepoitoutamtsumtousd(new BigDecimal(0));
		this.setManageramt(new BigDecimal(0));
		this.setManageramttousd(new BigDecimal(0));
		this.setManageramtsum(new BigDecimal(0));
		this.setManageramtsumtousd(new BigDecimal(0));
		this.setChargeamt(new BigDecimal(0));
		this.setChargeamttousd(new BigDecimal(0));
		this.setChargeamtsum(new BigDecimal(0));
		this.setChargeamtsumtousd(new BigDecimal(0));
		this.setOtheroutamt(new BigDecimal(0));
		this.setOtheroutamttousd(new BigDecimal(0));
		this.setOtheroutamtsum(new BigDecimal(0));
		this.setOtheroutamtsumtousd(new BigDecimal(0));
		this.setMonthoutamt(new BigDecimal(0));
		this.setMonthoutamttousd(new BigDecimal(0));
		this.setOutamtsum(new BigDecimal(0));
		this.setOutamtsumtousd(new BigDecimal(0));
		this.setEndbalancetousd(new BigDecimal(0));
		this.setInsterestsum(new BigDecimal(0));
    }
	public String getNguid() {
		return nguid;
	}
	public void setNguid(String nguid) {
		this.nguid = nguid;
	}
	public String getQdiiorgno() {
		return qdiiorgno;
	}
	public void setQdiiorgno(String qdiiorgno) {
		this.qdiiorgno = qdiiorgno;
	}
	public String getQdiiorgname() {
		return qdiiorgname;
	}
	public void setQdiiorgname(String qdiiorgname) {
		this.qdiiorgname = qdiiorgname;
	}
	public String getQdiidepoitno() {
		return qdiidepoitno;
	}
	public void setQdiidepoitno(String qdiidepoitno) {
		this.qdiidepoitno = qdiidepoitno;
	}
	public String getCustomno() {
		return customno;
	}
	public void setCustomno(String customno) {
		this.customno = customno;
	}
	public String getInwardaccountno() {
		return inwardaccountno;
	}
	public void setInwardaccountno(String inwardaccountno) {
		this.inwardaccountno = inwardaccountno;
	}
	public String getReportdate() {
		return reportdate;
	}
	public void setReportdate(String reportdate) {
		this.reportdate = reportdate;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public BigDecimal getEndbalance() {
		return endbalance;
	}
	public void setEndbalance(BigDecimal endbalance) {
		this.endbalance = endbalance;
	}
	public BigDecimal getEndbalancetousd() {
		return endbalancetousd;
	}
	
	public Timestamp getAuthdate() {
		return authdate;
	}
	public void setAuthdate(Timestamp authdate) {
		this.authdate = authdate;
	}
	public String getIsauth() {
		return isauth;
	}
	public void setIsauth(String isauth) {
		this.isauth = isauth;
	}
	public String getOguid() {
		return oguid;
	}
	public void setOguid(String oguid) {
		this.oguid = oguid;
	}
	public void setEndbalancetousd(BigDecimal endbalancetousd) {
		this.endbalancetousd = endbalancetousd;
	}
	public BigDecimal getLcyamt() {
		return lcyamt;
	}
	public void setLcyamt(BigDecimal lcyamt) {
		this.lcyamt = lcyamt;
	}
	public BigDecimal getLcyamttousd() {
		return lcyamttousd;
	}
	public void setLcyamttousd(BigDecimal lcyamttousd) {
		this.lcyamttousd = lcyamttousd;
	}
	public BigDecimal getLcyamtsum() {
		return lcyamtsum;
	}
	public void setLcyamtsum(BigDecimal lcyamtsum) {
		this.lcyamtsum = lcyamtsum;
	}
	public BigDecimal getLcyamtsumtousd() {
		return lcyamtsumtousd;
	}
	public void setLcyamtsumtousd(BigDecimal lcyamtsumtousd) {
		this.lcyamtsumtousd = lcyamtsumtousd;
	}
	public BigDecimal getDepoitamt() {
		return depoitamt;
	}
	public void setDepoitamt(BigDecimal depoitamt) {
		this.depoitamt = depoitamt;
	}
	public BigDecimal getDepoitamttousd() {
		return depoitamttousd;
	}
	public void setDepoitamttousd(BigDecimal depoitamttousd) {
		this.depoitamttousd = depoitamttousd;
	}
	public BigDecimal getDepoitamtsum() {
		return depoitamtsum;
	}
	public void setDepoitamtsum(BigDecimal depoitamtsum) {
		this.depoitamtsum = depoitamtsum;
	}
	public BigDecimal getDepoitamtsumtousd() {
		return depoitamtsumtousd;
	}
	public void setDepoitamtsumtousd(BigDecimal depoitamtsumtousd) {
		this.depoitamtsumtousd = depoitamtsumtousd;
	}
	public BigDecimal getInwardamt() {
		return inwardamt;
	}
	public void setInwardamt(BigDecimal inwardamt) {
		this.inwardamt = inwardamt;
	}
	public BigDecimal getInwardamttousd() {
		return inwardamttousd;
	}
	public void setInwardamttousd(BigDecimal inwardamttousd) {
		this.inwardamttousd = inwardamttousd;
	}
	public BigDecimal getInwardamtsum() {
		return inwardamtsum;
	}
	public void setInwardamtsum(BigDecimal inwardamtsum) {
		this.inwardamtsum = inwardamtsum;
	}
	public BigDecimal getInwardamtsumtousd() {
		return inwardamtsumtousd;
	}
	public void setInwardamtsumtousd(BigDecimal inwardamtsumtousd) {
		this.inwardamtsumtousd = inwardamtsumtousd;
	}
	public BigDecimal getInsterest() {
		return insterest;
	}
	public void setInsterest(BigDecimal insterest) {
		this.insterest = insterest;
	}
	public BigDecimal getInsteresttousd() {
		return insteresttousd;
	}
	public void setInsteresttousd(BigDecimal insteresttousd) {
		this.insteresttousd = insteresttousd;
	}
	public BigDecimal getInsterestsum() {
		return insterestsum;
	}
	public void setInsterestsum(BigDecimal insterestsum) {
		this.insterestsum = insterestsum;
	}
	public BigDecimal getInseterestsumtousd() {
		return inseterestsumtousd;
	}
	public void setInseterestsumtousd(BigDecimal inseterestsumtousd) {
		this.inseterestsumtousd = inseterestsumtousd;
	}
	public BigDecimal getOtheramt() {
		return otheramt;
	}
	public void setOtheramt(BigDecimal otheramt) {
		this.otheramt = otheramt;
	}
	public BigDecimal getOtheramttousd() {
		return otheramttousd;
	}
	public void setOtheramttousd(BigDecimal otheramttousd) {
		this.otheramttousd = otheramttousd;
	}
	public BigDecimal getOtheramtsum() {
		return otheramtsum;
	}
	public void setOtheramtsum(BigDecimal otheramtsum) {
		this.otheramtsum = otheramtsum;
	}
	public BigDecimal getOtheramtsumtousd() {
		return otheramtsumtousd;
	}
	public void setOtheramtsumtousd(BigDecimal otheramtsumtousd) {
		this.otheramtsumtousd = otheramtsumtousd;
	}
	public BigDecimal getMonthamtsum() {
		return monthamtsum;
	}
	public void setMonthamtsum(BigDecimal monthamtsum) {
		this.monthamtsum = monthamtsum;
	}
	public BigDecimal getMonthamtsumtousd() {
		return monthamtsumtousd;
	}
	public void setMonthamtsumtousd(BigDecimal monthamtsumtousd) {
		this.monthamtsumtousd = monthamtsumtousd;
	}
	public BigDecimal getAmtsum() {
		return amtsum;
	}
	public void setAmtsum(BigDecimal amtsum) {
		this.amtsum = amtsum;
	}
	public BigDecimal getAmtsumtousd() {
		return amtsumtousd;
	}
	public void setAmtsumtousd(BigDecimal amtsumtousd) {
		this.amtsumtousd = amtsumtousd;
	}
	public BigDecimal getFcyamt() {
		return fcyamt;
	}
	public void setFcyamt(BigDecimal fcyamt) {
		this.fcyamt = fcyamt;
	}
	public BigDecimal getFcyamttousd() {
		return fcyamttousd;
	}
	public void setFcyamttousd(BigDecimal fcyamttousd) {
		this.fcyamttousd = fcyamttousd;
	}
	public BigDecimal getFcyamtsum() {
		return fcyamtsum;
	}
	public void setFcyamtsum(BigDecimal fcyamtsum) {
		this.fcyamtsum = fcyamtsum;
	}
	public BigDecimal getFcyamtsumtousd() {
		return fcyamtsumtousd;
	}
	public void setFcyamtsumtousd(BigDecimal fcyamtsumtousd) {
		this.fcyamtsumtousd = fcyamtsumtousd;
	}
	public BigDecimal getOutdepoitamt() {
		return outdepoitamt;
	}
	public void setOutdepoitamt(BigDecimal outdepoitamt) {
		this.outdepoitamt = outdepoitamt;
	}
	public BigDecimal getOutdepoitamttousd() {
		return outdepoitamttousd;
	}
	public void setOutdepoitamttousd(BigDecimal outdepoitamttousd) {
		this.outdepoitamttousd = outdepoitamttousd;
	}
	public BigDecimal getOutdepoitamtsum() {
		return outdepoitamtsum;
	}
	public void setOutdepoitamtsum(BigDecimal outdepoitamtsum) {
		this.outdepoitamtsum = outdepoitamtsum;
	}
	public BigDecimal getOutdepoitamtsumtousd() {
		return outdepoitamtsumtousd;
	}
	public void setOutdepoitamtsumtousd(BigDecimal outdepoitamtsumtousd) {
		this.outdepoitamtsumtousd = outdepoitamtsumtousd;
	}
	public BigDecimal getRansomamt() {
		return ransomamt;
	}
	public void setRansomamt(BigDecimal ransomamt) {
		this.ransomamt = ransomamt;
	}
	public BigDecimal getRansomamttousd() {
		return ransomamttousd;
	}
	public void setRansomamttousd(BigDecimal ransomamttousd) {
		this.ransomamttousd = ransomamttousd;
	}
	public BigDecimal getRansomamtsum() {
		return ransomamtsum;
	}
	public void setRansomamtsum(BigDecimal ransomamtsum) {
		this.ransomamtsum = ransomamtsum;
	}
	public BigDecimal getRansomamtsumtousd() {
		return ransomamtsumtousd;
	}
	public void setRansomamtsumtousd(BigDecimal ransomamtsumtousd) {
		this.ransomamtsumtousd = ransomamtsumtousd;
	}
	public BigDecimal getShareamt() {
		return shareamt;
	}
	public void setShareamt(BigDecimal shareamt) {
		this.shareamt = shareamt;
	}
	public BigDecimal getShareamttousd() {
		return shareamttousd;
	}
	public void setShareamttousd(BigDecimal shareamttousd) {
		this.shareamttousd = shareamttousd;
	}
	public BigDecimal getShareamtsum() {
		return shareamtsum;
	}
	public void setShareamtsum(BigDecimal shareamtsum) {
		this.shareamtsum = shareamtsum;
	}
	public BigDecimal getShareamtsumtousd() {
		return shareamtsumtousd;
	}
	public void setShareamtsumtousd(BigDecimal shareamtsumtousd) {
		this.shareamtsumtousd = shareamtsumtousd;
	}
	public BigDecimal getDepoitoutamt() {
		return depoitoutamt;
	}
	public void setDepoitoutamt(BigDecimal depoitoutamt) {
		this.depoitoutamt = depoitoutamt;
	}
	public BigDecimal getDepoitoutamttousd() {
		return depoitoutamttousd;
	}
	public void setDepoitoutamttousd(BigDecimal depoitoutamttousd) {
		this.depoitoutamttousd = depoitoutamttousd;
	}
	public BigDecimal getDepoitoutamtsum() {
		return depoitoutamtsum;
	}
	public void setDepoitoutamtsum(BigDecimal depoitoutamtsum) {
		this.depoitoutamtsum = depoitoutamtsum;
	}
	public BigDecimal getDepoitoutamtsumtousd() {
		return depoitoutamtsumtousd;
	}
	public void setDepoitoutamtsumtousd(BigDecimal depoitoutamtsumtousd) {
		this.depoitoutamtsumtousd = depoitoutamtsumtousd;
	}
	public BigDecimal getManageramt() {
		return manageramt;
	}
	public void setManageramt(BigDecimal manageramt) {
		this.manageramt = manageramt;
	}
	public BigDecimal getManageramttousd() {
		return manageramttousd;
	}
	public void setManageramttousd(BigDecimal manageramttousd) {
		this.manageramttousd = manageramttousd;
	}
	public BigDecimal getManageramtsum() {
		return manageramtsum;
	}
	public void setManageramtsum(BigDecimal manageramtsum) {
		this.manageramtsum = manageramtsum;
	}
	public BigDecimal getManageramtsumtousd() {
		return manageramtsumtousd;
	}
	public void setManageramtsumtousd(BigDecimal manageramtsumtousd) {
		this.manageramtsumtousd = manageramtsumtousd;
	}
	public BigDecimal getChargeamt() {
		return chargeamt;
	}
	public void setChargeamt(BigDecimal chargeamt) {
		this.chargeamt = chargeamt;
	}
	public BigDecimal getChargeamttousd() {
		return chargeamttousd;
	}
	public void setChargeamttousd(BigDecimal chargeamttousd) {
		this.chargeamttousd = chargeamttousd;
	}
	public BigDecimal getChargeamtsum() {
		return chargeamtsum;
	}
	public void setChargeamtsum(BigDecimal chargeamtsum) {
		this.chargeamtsum = chargeamtsum;
	}
	public BigDecimal getChargeamtsumtousd() {
		return chargeamtsumtousd;
	}
	public void setChargeamtsumtousd(BigDecimal chargeamtsumtousd) {
		this.chargeamtsumtousd = chargeamtsumtousd;
	}
	public BigDecimal getOtheroutamt() {
		return otheroutamt;
	}
	public void setOtheroutamt(BigDecimal otheroutamt) {
		this.otheroutamt = otheroutamt;
	}
	public BigDecimal getOtheroutamttousd() {
		return otheroutamttousd;
	}
	public void setOtheroutamttousd(BigDecimal otheroutamttousd) {
		this.otheroutamttousd = otheroutamttousd;
	}
	public BigDecimal getOtheroutamtsum() {
		return otheroutamtsum;
	}
	public void setOtheroutamtsum(BigDecimal otheroutamtsum) {
		this.otheroutamtsum = otheroutamtsum;
	}
	public BigDecimal getOtheroutamtsumtousd() {
		return otheroutamtsumtousd;
	}
	public void setOtheroutamtsumtousd(BigDecimal otheroutamtsumtousd) {
		this.otheroutamtsumtousd = otheroutamtsumtousd;
	}
	public BigDecimal getMonthoutamt() {
		return monthoutamt;
	}
	public void setMonthoutamt(BigDecimal monthoutamt) {
		this.monthoutamt = monthoutamt;
	}
	public BigDecimal getMonthoutamttousd() {
		return monthoutamttousd;
	}
	public void setMonthoutamttousd(BigDecimal monthoutamttousd) {
		this.monthoutamttousd = monthoutamttousd;
	}
	public BigDecimal getOutamtsum() {
		return outamtsum;
	}
	public void setOutamtsum(BigDecimal outamtsum) {
		this.outamtsum = outamtsum;
	}
	public BigDecimal getOutamtsumtousd() {
		return outamtsumtousd;
	}
	public void setOutamtsumtousd(BigDecimal outamtsumtousd) {
		this.outamtsumtousd = outamtsumtousd;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRwidh() {
		return rwidh;
	}
	public void setRwidh(String rwidh) {
		this.rwidh = rwidh;
	}
	public String getYwbh() {
		return ywbh;
	}
	public void setYwbh(String ywbh) {
		this.ywbh = ywbh;
	}
	public String getSfysb() {
		return sfysb;
	}
	public void setSfysb(String sfysb) {
		this.sfysb = sfysb;
	}
	public String getSxbz() {
		return sxbz;
	}
	public void setSxbz(String sxbz) {
		this.sxbz = sxbz;
	}
	public String getSfzx() {
		return sfzx;
	}
	public void setSfzx(String sfzx) {
		this.sfzx = sfzx;
	}
	public String getBank_Id() {
		return bank_Id;
	}
	public void setBank_Id(String bankId) {
		bank_Id = bankId;
	}
	public int getHandid() {
		return handid;
	}
	public void setHandid(int handid) {
		this.handid = handid;
	}
	public Timestamp getHanddate() {
		return handdate;
	}
	public void setHanddate(Timestamp handdate) {
		this.handdate = handdate;
	}
	public int getCheckid() {
		return checkid;
	}
	public void setCheckid(int checkid) {
		this.checkid = checkid;
	}
	public Timestamp getCheckdate() {
		return checkdate;
	}
	public void setCheckdate(Timestamp checkdate) {
		this.checkdate = checkdate;
	}
	public String getIscheck() {
		return ischeck;
	}
	public void setIscheck(String ischeck) {
		this.ischeck = ischeck;
	}
	public int getAuthid() {
		return authid;
	}
	public void setAuthid(int authid) {
		this.authid = authid;
	}
  

}