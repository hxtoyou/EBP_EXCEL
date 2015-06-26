package com.ebills.product.declare.domain;

/*
 * 创建日期 2005-12-6
 *
 */

import java.util.HashMap;


/**
 * @author twteam
 * 
 * 定议了一部分常量
 * ****************************************************************
 * 2012-08-23 彭裕
 */
public class BsptCommon  {
	private static BsptCommon bsptCommon = null;
	public static HashMap ZHtradeType = new HashMap();
	public static HashMap ZBtradeType = new HashMap();
	public static HashMap FiletradeType = new HashMap();

	public BsptCommon(){
		
	}
	
	public static BsptCommon getInstance() {
		ZHtradeType = new HashMap();
		ZBtradeType = new HashMap();
		FiletradeType = new HashMap();
		ZHtradeType.put("ACCCA", "账户开关户信息");
		ZHtradeType.put("ACCCB", "账户收支余信息");	
        ZBtradeType.put("CFAAA", "外债双边贷款—签约信息");
        ZBtradeType.put("CFAAB", "外债买方信贷—签约信息");
        ZBtradeType.put("CFAAC", "外债境外同业拆借—签约信息");
        ZBtradeType.put("CFAAD", "外债海外代付—签约信息");
        ZBtradeType.put("CFAAE", "外债卖出回购—签约信息");
        ZBtradeType.put("CFAAF", "外债远期信用证—签约信息");
        ZBtradeType.put("CFAAG", "外债银团贷款—签约信息");
        ZBtradeType.put("CFAAH", "外债贵金属拆借—签约信息");
        ZBtradeType.put("CFAAI", "外债其他贷款—签约信息");
        ZBtradeType.put("CFAAJ", "外债货币市场工具—签约信息");
        ZBtradeType.put("CFAAK", "外债债券和票据—签约信息");
        ZBtradeType.put("CFAAL", "外债境外同业存放—签约信息");
        ZBtradeType.put("CFAAM", "外债境外联行及附属机构往来—签约信息");
        ZBtradeType.put("CFAAN", "外债非居民机构存款—签约信息");
        ZBtradeType.put("CFAAP", "外债非居民个人存款—签约信息");
        ZBtradeType.put("CFAAQ", "外债其他外债—签约信息");
        ZBtradeType.put("CFAAR", "外债—变动信息");
        ZBtradeType.put("CFAAS", "外债—余额信息");
        ZBtradeType.put("CFAAS_JG", "外债非居民机构存款—余额信息");
        ZBtradeType.put("CFAAS_GR", "外债非居民个人存款—余额信息");
        ZBtradeType.put("CFABA", "对外担保—签约信息");
        ZBtradeType.put("CFABB", "对外担保—责任余额");
        ZBtradeType.put("CFABC", "对外担保-履约信息");
        ZBtradeType.put("CFACA", "国内外汇贷款—签约信息");
        ZBtradeType.put("CFACB", "国内外汇贷款—变动信息");
        ZBtradeType.put("CFADA", "境外担保项下境内贷款—签约信息");
        ZBtradeType.put("CFADB", "境外担保项下境内贷款—变动及履约");
        ZBtradeType.put("CFAEA", "外汇质押人民币贷款—签约信息");
        ZBtradeType.put("CFAEB", "外汇质押人民币贷款—变动信息");
        ZBtradeType.put("CFAFA", "商业银行人民币结构性存款—签约信息");
        ZBtradeType.put("CFAFB", "商业银行人民币结构性存款—终止信息");
        ZBtradeType.put("CFAFC", "商业银行人民币结构性存款—利息给付信息");
        ZBtradeType.put("CFAFD", "商业银行人民币结构性存款—资金流出入和结购汇信息");
        ZBtradeType.put("QFIIZY", "QFII账户开户专有信息");
        ZBtradeType.put("QFIIMX", "QFII资金汇出入及结购汇明细信息");        
        ZBtradeType.put("QFIITSZHSZ", "QFII机构人民币特殊账户收支信息");        
        ZBtradeType.put("QFIIWHZHSZ", "QFII机构外汇账户收支信息");        
        ZBtradeType.put("QFIIYDZCFZ", "QFII机构月度资产负债信息");        
        ZBtradeType.put("QFIIFZ", "QFII-资产负债表信息");        
        ZBtradeType.put("QFIISY", "QFII-损益表信息");        
        ZBtradeType.put("RQFIIMX", "RQFII人民币合格境外机构投资者资金汇出入及购汇明细信息");        
        ZBtradeType.put("RQFIICR", "RQFII境内证券投资资金汇出入信息");        
        ZBtradeType.put("RQFIISZ", "RQFII境内人民币账户收支情况信息");        
        ZBtradeType.put("RQFIIYDZCFZ", "RQFII机构月度资产负债信息");        
        ZBtradeType.put("RQFIIZCFZ", "RQFII资产负债表信息");        
        ZBtradeType.put("RQFIISY", "RQFII损益表信息");        
        ZBtradeType.put("QDIIZH", "QDII账户信息");        
        ZBtradeType.put("QDIIMX", "QDII资金汇出入及结购汇明细信息");        
        ZBtradeType.put("QDIITGZH", "QDII境内外币托管账户信息");        
        ZBtradeType.put("QDIIZQTZ", "QDII境外证券投资信息");        
        ZBtradeType.put("JLJHCZ", "境内专用外汇账户关户资金处置信息");        
        ZBtradeType.put("JLJHSZ", "境内专用外汇账户收支信息");
        
    	FiletradeType.put("ACCCA", "账户开关户信息");
    	FiletradeType.put("ACCCB", "账户收支余信息");	
    	FiletradeType.put("CFAAA", "外债双边贷款—签约信息");
    	FiletradeType.put("CFAAB", "外债买方信贷—签约信息");
    	FiletradeType.put("CFAAC", "外债境外同业拆借—签约信息");
    	FiletradeType.put("CFAAD", "外债海外代付—签约信息");
    	FiletradeType.put("CFAAE", "外债卖出回购—签约信息");
    	FiletradeType.put("CFAAF", "外债远期信用证—签约信息");
    	FiletradeType.put("CFAAG", "外债银团贷款—签约信息");
    	FiletradeType.put("CFAAH", "外债贵金属拆借—签约信息");
    	FiletradeType.put("CFAAI", "外债其他贷款—签约信息");
    	FiletradeType.put("CFAAJ", "外债货币市场工具—签约信息");
    	FiletradeType.put("CFAAK", "外债债券和票据—签约信息");
    	FiletradeType.put("CFAAL", "外债境外同业存放—签约信息");
    	FiletradeType.put("CFAAM", "外债境外联行及附属机构往来—签约信息");
    	FiletradeType.put("CFAAN", "外债非居民机构存款—签约信息");
    	FiletradeType.put("CFAAP", "外债非居民个人存款—签约信息");
    	FiletradeType.put("CFAAQ", "外债其他外债—签约信息");
    	FiletradeType.put("CFAAR", "外债—变动信息");
    	FiletradeType.put("CFAAS", "外债—余额信息");
    	FiletradeType.put("CFAAS_JG", "外债非居民机构存款—余额信息");
    	FiletradeType.put("CFAAS_GR", "外债非居民个人存款—余额信息");
    	FiletradeType.put("CFABA", "对外担保—签约信息");
    	FiletradeType.put("CFABB", "对外担保—责任余额");
    	FiletradeType.put("CFABC", "对外担保-履约信息");
    	FiletradeType.put("CFACA", "国内外汇贷款—签约信息");
    	FiletradeType.put("CFACB", "国内外汇贷款—变动信息");
    	FiletradeType.put("CFADA", "境外担保项下境内贷款—签约信息");
    	FiletradeType.put("CFADB", "境外担保项下境内贷款—变动及履约");
    	FiletradeType.put("CFAEA", "外汇质押人民币贷款—签约信息");
    	FiletradeType.put("CFAEB", "外汇质押人民币贷款—变动信息");
    	FiletradeType.put("CFAFA", "商业银行人民币结构性存款—签约信息");
    	FiletradeType.put("CFAFB", "商业银行人民币结构性存款—终止信息");
    	FiletradeType.put("CFAFC", "商业银行人民币结构性存款—利息给付信息");
    	FiletradeType.put("CFAFD", "商业银行人民币结构性存款—资金流出入和结购汇信息");
    	FiletradeType.put("QFIIZY", "QFII账户开户专有信息");
    	FiletradeType.put("QFIIMX", "QFII资金汇出入及结购汇明细信息");        
    	FiletradeType.put("QFIITSZHSZ", "QFII机构人民币特殊账户收支信息");        
    	FiletradeType.put("QFIIWHZHSZ", "QFII机构外汇账户收支信息");        
    	FiletradeType.put("QFIIYDZCFZ", "QFII机构月度资产负债信息");        
    	FiletradeType.put("QFIIFZ", "QFII资产负债表信息");        
    	FiletradeType.put("QFIISY", "QFII损益表信息");        
    	FiletradeType.put("RQFIIMX", "RQFII人民币合格境外机构投资者资金汇出入及购汇明细信息");        
    	FiletradeType.put("RQFIICR", "RQFII境内证券投资资金汇出入信息");        
    	FiletradeType.put("RQFIISZ", "RQFII境内人民币账户收支情况信息");        
    	FiletradeType.put("RQFIIYDZCFZ", "RQFII机构月度资产负债信息");        
    	FiletradeType.put("RQFIIZCFZ", "RQFII资产负债表信息");        
    	FiletradeType.put("RQFIISY", "RQFII损益表信息");        
    	FiletradeType.put("QDIIZH", "QDII账户信息 ");        
    	FiletradeType.put("QDIIMX", "QDII资金汇出入及结购汇明细信息 ");        
    	FiletradeType.put("QDIITGZH", "QDII境内外币托管账户信息");        
    	FiletradeType.put("QDIIZQTZ", "QDII境外证券投资信息");        
    	FiletradeType.put("JLJHCZ", "境内专用外汇账户关户资金处置信息");        
    	FiletradeType.put("JLJHSZ", "境内专用外汇账户收支信息");	
    	//add by Hudan 2012_10_25 山东联盟加
    	FiletradeType.put("indicate", "核心传递令牌文件");	
    	if(bsptCommon==null){
    		bsptCommon = new BsptCommon();
    	}
		return bsptCommon;
	}
}