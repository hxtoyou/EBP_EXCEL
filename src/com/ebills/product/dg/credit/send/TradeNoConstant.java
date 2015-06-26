package com.ebills.product.dg.credit.send;

public interface TradeNoConstant {
	/**
	 * 信用证开立
	 */
	public final static String IMLCISSUE_TRADENO="050101";
	/**
	 * 提货担保
	 */
	public final static String IMTHDB_TRADENO="050106";
	/**
	 * 信用证开立修改
	 */
	public final static String IMLCAMEND_TRADENO="050102";
	/**
	 * 信用证开立修改拒绝
	 */
	public final static String IMLCAMENDAFF_TRADENO="050122";
	/**
	 * 循环信用证激活
	 */
	public final static String IMLCREVO_TRADENO="050111";
	/**
	 * 保函开立
	 */
	public final static String IMLGISSUE_TRADENO="170102";
	/**
	 * 保函开立
	 */
	public final static String IMLCCLOSE_TRADENO="050105";
	
	/**
	 * 信用证开立撤销确认
	 */
	public final static String IMLCCANCEL_TRADENO="050103";
	/**
	 *提货担保注销
	 */
	public final static String IMTHDBCAL_TRADENO="050108";
	/**
	 * 保函开立撤销
	 */
	public final static String IMLGCANCEL_TRADENO="1701110";
	/**
	 * 保函开立注销
	 */
	public final static String IMLGRFGU_TRADENO="170112";
	
	/**
	 *  信用证注销
	 *  */
	public final static String LCCLOASE_TRADENO="050105";
	/**
	 *  信用证注销恢复
	 *  */
	public final static String LCRECOVER_TRADENO="050124";
	/** 
	 * 信用证撤销确认
	 * */
	public final static String LCCANCEL_TRADENO="050103";
	/**
	 *  退单/放单
	 *  */
	public final static String LCRETURN_TRADENO="050116";
	/** 
	 * 信用证承兑
	 * */
	public final static String LCACPT_TRADENO="050110";
	/**
	 *  信用证承兑变更确认
	 *  */
	public final static String LCACPTTEX_TRADENO="050126";
	/** 
	 * 信用证付汇*
	 */
	public final static String LCPAY_TRADENO="050115";
	/** 
	 * 提货担保注销*
	 */
	public final static String THDBCAL_TRADENO="050108";
	/**
	 *  提货担保赔付
	 *  */
	public final static String THDBPEDL_TRADENO="050125";
	/** 
	 * 保函注销
	 * */
	public final static String LGRFGU_TRADENO="170112";
	/** 
	 * 保函撤销确认
	 * */
	public final static String LGCANCEL_TRADENO="170110";
	/** 
	 * 保函赔付
	 * */
	public final static String LGPAY_TRADENO="170106";
	/** 
	 * 保函索赔闭卷
	 * */
	public final static String LGCLOSE_TRADENO="170109";
	/**
	 *  信用证到单
	 *  */
	public final static String LCAB_TRADENO="050109";
	
	/**
	 *  信用证到单修改
	 *  */
	public final static String LCABAMD_TRADENO="050114";
	
	/**
	 *  信用证二次到单
	 *  */
	public final static String LCABSEC_TRADENO="050113";
	/** 
	 * 持单拒付
	 * */
	public final static String LCREJ_TRADENO="050109";
	/**
	 *  手工冲账
	 *  */
	public final static String BUREME_TRADENO="031004";
	/**
	 * 产品种类
	 * */
	interface Prodcd {
		/**
		 * 信用证开立
		 */
		public final static String PPODCD_IMLCISSUE_TRADENO="032265";
		/**
		 * 提后担保
		 */
		public final static String  PPODCD_IMTHDB="032294";
		/**
		 * 保函开立
		 */
		public final static String  PPODCD_IMLGISSUE="415182";
	}
	/**
	 * 交易类型
	 */
	interface TradeTpe{
		/**
		 * 发放
		 */
		public final static String TRADE_TYPE_02="02";
		/**
		 * 收回
		 */
		public final static String TRADE_TYPE_03="03";
		/**
		 * 展期
		 */
		public final static String TRADE_TYPE_04="04";
		/**
		 *信用证增额
		 */
		public final static String TRADE_TYPE_43="43";
		/**
		 * 正常收息 
		 */
		public final static String TRADE_TYPE_07="07";
		/**
		 * 清收表内欠息
		 */
		public final static String TRADE_TYPE_08="08";
		/**
		 * 清收表外欠息
		 */
		public final static String TRADE_TYPE_09="09";
		/**
		 * 回收复利
		 */
		public final static String TRADE_TYPE_10="10";
		/**
		 * 核销
		 */
		public final static String TRADE_TYPE_12="12";
		/**
		 * 发放冲正
		 */
		public final static String TRADE_TYPE_14="14";
		/**
		 * 回收冲正
		 */
		public final static String TRADE_TYPE_15="15";
		/**
		 * 垫款转入
		 */
		public final static String TRADE_TYPE_20="20";
		/**
		 * 垫款回收
		 */
		public final static String TRADE_TYPE_21="21";
	}
	/***
	 * 凭证状态
	 */
	interface PZStatus{
		/**
		 *正常
		 */
		public final static String PZ_STATUS_1="1";
		/**
		 * 核销
		 */
		public final static String PZ_STATUS_2="2";
		/**
		 * 核心冲销
		 */
		public final static String PZ_STATUS_3="3";
		/**
		 * 垫款  
		 */
		public final static String PZ_STATUS_6="6";
		/**
		 * 未用退回(撤证)
		 */
		public final static String PZ_STATUS_8="8";
		/**
		 * 结清
		 */
		public final static String PZ_STATUS_9="9";
	}
}
