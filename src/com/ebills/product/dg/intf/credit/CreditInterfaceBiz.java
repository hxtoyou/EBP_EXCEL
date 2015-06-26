package com.ebills.product.dg.intf.credit;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.bussprocess.context.Context;
import org.apache.commons.lang.StringUtils;

import com.ebills.util.EbillsException;
import com.ebills.product.dg.commons.Commons;
import com.ebills.product.dg.intf.IErrorCode;
import com.ebills.product.dg.intf.InterfaceBiz;
import com.ebills.utils.CommonUtil;
import com.ebills.utils.EbpDao;

public class CreditInterfaceBiz extends InterfaceBiz {

	public static final String INTERFACEID_CRIN01 = "1";
	public static final String INTERFACEID_CRIN02 = "2";
	public static final String INTERFACEID_CRIN03 = "3";
	public static final String INTERFACEID_CRIN04 = "4";
	public static final String INTERFACEID_CRIN05 = "5";


	public void callCRIN01(InterfaceCreditRequst request, Context context) {
		CreditInterfaceDao dao = new CreditInterfaceDao();
		if (dao.isAppnoExist(request.getAppno())) {
			if (!dao.isExistJjno(request.getJjhmno(), INTERFACEID_CRIN01)) {
				try {
					request.setIntrefaceid(INTERFACEID_CRIN01);
					dao.create(request);
					context.put("errorCode", IErrorCode.GJYW_0);
					context.put("errorMsg", IErrorCode.GJYW__MSG_0);
					context.put("sverretcod", IErrorCode.GJYW_0);
					context.put("sverretmsg", IErrorCode.GJYW_0);
				} catch (EbillsException e) {
					e.printStackTrace();
					context.put("errorCode", IErrorCode.GJYW_CRIN01_3);
					context.put("errorMsg", IErrorCode.GJYW_CRIN01__MSG_3);
					context.put("sverretcod", IErrorCode.GJYW_CRIN01_3);
					context.put("sverretmsg", IErrorCode.GJYW_CRIN01__MSG_3);
				} catch (Exception e) {
					e.printStackTrace();
					context.put("errorCode", IErrorCode.GJYW_CRIN01_4);
					context.put("errorMsg", IErrorCode.GJYW_CRIN01__MSG_4);
					context.put("sverretcod", IErrorCode.GJYW_CRIN01_4);
					context.put("sverretmsg", IErrorCode.GJYW_CRIN01__MSG_4);
				}
			} else {
				if (dao.isExistSameJjno(request.getFileno(),
						request.getJjhmno(), INTERFACEID_CRIN01)) {
					context.put("errorCode", IErrorCode.GJYW_0);
					context.put("errorMsg", IErrorCode.GJYW__MSG_0);
					context.put("sverretcod", IErrorCode.GJYW_0);
					context.put("sverretmsg", IErrorCode.GJYW_0);
				} else {
					context.put("errorCode", IErrorCode.GJYW_7);
					context.put("errorMsg", IErrorCode.GJYW_MSG_7);
					context.put("sverretcod", IErrorCode.GJYW_7);
					context.put("sverretmsg", IErrorCode.GJYW_MSG_7);
				}
			}
		} else {
			context.put("errorCode", IErrorCode.GJYW_2);
			context.put("errorMsg", IErrorCode.GJYW_MSG_2);
			context.put("sverretcod", IErrorCode.GJYW_2);
			context.put("sverretmsg", IErrorCode.GJYW_MSG_2);
		}
	}

	public void callCRIN02(InterfaceCreditRequst request, Context context) {
		CreditInterfaceDao dao = new CreditInterfaceDao();
		if (!dao.isExistJjno(request.getJjhmno(), INTERFACEID_CRIN02)) {
			try {
				request.setIntrefaceid(INTERFACEID_CRIN02);
				dao.create(request);
				context.put("errorCode", IErrorCode.GJYW_0);
				context.put("errorMsg", IErrorCode.GJYW__MSG_0);
				context.put("sverretcod", IErrorCode.GJYW_0);
				context.put("sverretmsg", IErrorCode.GJYW_0);
			} catch (EbillsException e) {
				e.printStackTrace();
				context.put("errorCode", IErrorCode.GJYW_CRIN02_3);
				context.put("errorMsg", IErrorCode.GJYW_CRIN02__MSG_3);
				context.put("sverretcod", IErrorCode.GJYW_CRIN02_3);
				context.put("sverretmsg", IErrorCode.GJYW_CRIN02__MSG_3);
			} catch (Exception e) {
				e.printStackTrace();
				context.put("errorCode", IErrorCode.GJYW_CRIN02_4);
				context.put("errorMsg", IErrorCode.GJYW_CRIN02__MSG_4);
				context.put("sverretcod", IErrorCode.GJYW_CRIN02_4);
				context.put("sverretmsg", IErrorCode.GJYW_CRIN02__MSG_4);
			}
		} else {
			if (dao.isExistSameJjno(request.getFileno(), request.getJjhmno(),
					INTERFACEID_CRIN02)) {
				context.put("errorCode", IErrorCode.GJYW_0);
				context.put("errorMsg", IErrorCode.GJYW__MSG_0);
				context.put("sverretcod", IErrorCode.GJYW_0);
				context.put("sverretmsg", IErrorCode.GJYW_0);
			} else {
				context.put("errorCode", IErrorCode.GJYW_7);
				context.put("errorMsg", IErrorCode.GJYW_MSG_7);
				context.put("sverretcod", IErrorCode.GJYW_7);
				context.put("sverretmsg", IErrorCode.GJYW_MSG_7);
			}
		}

	}

	public void callCRIN03(InterfaceCreditRequst request, Context context) {
		CreditInterfaceDao dao = new CreditInterfaceDao();
		if (dao.isAppnoExist(request.getAppno())) {
			if (!dao.isExistJjno(request.getJjhmno(), INTERFACEID_CRIN03)) {
				try {
					request.setIntrefaceid(INTERFACEID_CRIN03);
					dao.create(request);
					context.put("errorCode", IErrorCode.GJYW_0);
					context.put("errorMsg", IErrorCode.GJYW__MSG_0);
					context.put("sverretcod", IErrorCode.GJYW_0);
					context.put("sverretmsg", IErrorCode.GJYW_0);
				} catch (EbillsException e) {
					e.printStackTrace();
					context.put("errorCode", IErrorCode.GJYW_CRIN03_3);
					context.put("errorMsg", IErrorCode.GJYW_CRIN03__MSG_3);
					context.put("sverretcod", IErrorCode.GJYW_CRIN03_3);
					context.put("sverretmsg", IErrorCode.GJYW_CRIN03__MSG_3);
				} catch (Exception e) {
					e.printStackTrace();
					context.put("errorCode", IErrorCode.GJYW_CRIN03_4);
					context.put("errorMsg", IErrorCode.GJYW_CRIN03__MSG_4);
					context.put("sverretcod", IErrorCode.GJYW_CRIN03_4);
					context.put("sverretmsg", IErrorCode.GJYW_CRIN03__MSG_4);
				}
			} else {
				if (dao.isExistSameJjno(request.getFileno(),
						request.getJjhmno(), INTERFACEID_CRIN03)) {
					context.put("errorCode", IErrorCode.GJYW_0);
					context.put("errorMsg", IErrorCode.GJYW__MSG_0);
					context.put("sverretcod", IErrorCode.GJYW_0);
					context.put("sverretmsg", IErrorCode.GJYW_0);
				} else {
					context.put("errorCode", IErrorCode.GJYW_7);
					context.put("errorMsg", IErrorCode.GJYW_MSG_7);
					context.put("sverretcod", IErrorCode.GJYW_7);
					context.put("sverretmsg", IErrorCode.GJYW_MSG_7);
				}
			}

		} else {
			context.put("errorCode", IErrorCode.GJYW_2);
			context.put("errorMsg", IErrorCode.GJYW_MSG_2);
			context.put("sverretcod", IErrorCode.GJYW_2);
			context.put("sverretmsg", IErrorCode.GJYW_MSG_2);
		}
	}

	public void callCRIN04(InterfaceCreditRequst request, Context context) {
		CreditInterfaceDao dao = new CreditInterfaceDao();
		if (dao.isAppnoExist(request.getAppno())) {
			if (!dao.isExistJjno(request.getJjhmno(), INTERFACEID_CRIN04)) {
				try {
					request.setIntrefaceid(INTERFACEID_CRIN04);
					dao.create(request);
					context.put("errorCode", IErrorCode.GJYW_0);
					context.put("errorMsg", IErrorCode.GJYW__MSG_0);
					context.put("sverretcod", IErrorCode.GJYW_0);
					context.put("sverretmsg", IErrorCode.GJYW_0);
				} catch (EbillsException e) {
					e.printStackTrace();
					context.put("errorCode", IErrorCode.GJYW_CRIN04_3);
					context.put("errorMsg", IErrorCode.GJYW_CRIN04__MSG_3);
					context.put("sverretcod", IErrorCode.GJYW_CRIN04_3);
					context.put("sverretmsg", IErrorCode.GJYW_CRIN04__MSG_3);
				} catch (Exception e) {
					e.printStackTrace();
					context.put("errorCode", IErrorCode.GJYW_CRIN04_4);
					context.put("errorMsg", IErrorCode.GJYW_CRIN04__MSG_4);
					context.put("sverretcod", IErrorCode.GJYW_CRIN04_4);
					context.put("sverretmsg", IErrorCode.GJYW_CRIN04__MSG_4);
				}
			} else {
				if (dao.isExistSameJjno(request.getFileno(),
						request.getJjhmno(), INTERFACEID_CRIN03)) {
					context.put("errorCode", IErrorCode.GJYW_0);
					context.put("errorMsg", IErrorCode.GJYW__MSG_0);
					context.put("sverretcod", IErrorCode.GJYW_0);
					context.put("sverretmsg", IErrorCode.GJYW_0);
				} else {
					context.put("errorCode", IErrorCode.GJYW_7);
					context.put("errorMsg", IErrorCode.GJYW_MSG_7);
					context.put("sverretcod", IErrorCode.GJYW_7);
					context.put("sverretmsg", IErrorCode.GJYW_MSG_7);
				}
			}

		} else {
			context.put("errorCode", IErrorCode.GJYW_2);
			context.put("errorMsg", IErrorCode.GJYW_MSG_2);
			context.put("sverretcod", IErrorCode.GJYW_2);
			context.put("sverretmsg", IErrorCode.GJYW_MSG_2);
		}
	}

	public void callCRIN05(InterfaceCreditRequst request, Context context) {
		CreditInterfaceDao dao = new CreditInterfaceDao();
		try {
			request.setIntrefaceid(INTERFACEID_CRIN05);
			if (dao.isExistJylsh(request.getOldjylsh())) {
				dao.deleteByjylsh(request.getOldjylsh());
				dao.create(request);
				context.put("errorCode", IErrorCode.GJYW_0);
				context.put("errorMsg", IErrorCode.GJYW__MSG_0);
				context.put("sverretcod", IErrorCode.GJYW_0);
				context.put("sverretmsg", IErrorCode.GJYW_0);
			} else {
				context.put("errorCode", IErrorCode.GJYW_6);
				context.put("errorMsg", IErrorCode.GJYW_MSG_6);
				context.put("sverretcod", IErrorCode.GJYW_6);
				context.put("sverretmsg", IErrorCode.GJYW_MSG_6);
			}
		} catch (EbillsException e) {
			e.printStackTrace();
			context.put("errorCode", IErrorCode.GJYW_CRIN05_3);
			context.put("errorMsg", IErrorCode.GJYW_CRIN05__MSG_3);
			context.put("sverretcod", IErrorCode.GJYW_CRIN05_3);
			context.put("sverretmsg", IErrorCode.GJYW_CRIN05__MSG_3);
		} catch (Exception e) {
			e.printStackTrace();
			context.put("errorCode", IErrorCode.GJYW_CRIN05_4);
			context.put("errorMsg", IErrorCode.GJYW_CRIN05__MSG_4);
			context.put("sverretcod", IErrorCode.GJYW_CRIN05_4);
			context.put("sverretmsg", IErrorCode.GJYW_CRIN05__MSG_4);
		}
	}

	public void callCROU01(InterfaceCreditRequst request, Context context) {

	}

	public void callCROU02(InterfaceCreditRequst request, Context context) {

	}

	public void callCROU03(InterfaceCreditRequst request, Context context) {

	}


	public void queryInterfaceByNo(String no) {
		List<Object> params = new LinkedList<Object>();
		String sql = "";
		EbpDao dao = new EbpDao();
		try {
			List list = dao.queryBySql(sql, null, params);
		} catch (EbillsException e) {
			e.printStackTrace();
		}
	}
	
	public InterfaceCreditRequst parse(Context context){
		InterfaceCreditRequst request=new InterfaceCreditRequst();
        //获取报文信息
		request.setEciSeverId((String)context.get("eciSeverId"));
		request.setXmlflag((String)context.get("xmlflag"));
		request.setTemplateCodeName((String)context.get("templateCodeName"));
		request.setTransCode((String)context.get("transCode"));
		request.setSysId((String)context.get("sysId"));
		request.setChannelCode((String)context.get("channelCode"));
		request.setSubchannelCode((String)context.get("subchannelCode"));
		request.setTradeFlag((String)context.get("tradeFlag"));
        request.setCheckFlag((String)context.get("checkFlag"));
        request.setChannelserno((String)context.get("channelserno"));
        request.setSessid((String)context.get("sessid"));
        request.setPrcscd((String)context.get("prcscd"));
        request.setZoneno((String)context.get("zoneno"));
        request.setMbrno((String)context.get("mbrno"));
        request.setBrno((String)context.get("brno"));
        request.setTellerno((String)context.get("tellerno"));
        request.setTellertp((String)context.get("tellertp"));
        request.setCsbxno((String)context.get("csbxno"));
        request.setDutytp((String)context.get("dutytp"));
        request.setDutyno((String)context.get("dutyno"));
        request.setAuthno((String)context.get("authno"));
        request.setAuthce((String)context.get("authpw"));
        request.setAuthmanfttype((String)context.get("authmg"));
        request.setAuthce((String)context.get("authce"));
        request.setAuthmanfttype((String)context.get("authmanfttype"));
        request.setReplyquery((String)context.get("replyquery"));
        //===下面是业务数据
        if(context.get("jylsh")!=null){
        	request.setFileno((String)context.get("jylsh"));
        }
        if(context.get("applno")!=null){
        	 request.setAppno((String)context.get("applno"));
        }
        if(context.get("sutype")!=null){
 
     	   if(((String)context.get("sutype")).equals("00")){
    		   request.setSutype("Y");//即期
    	   }else if(((String)context.get("sutype")).equals("01")){
    		   request.setSutype("N");//远期
    	   }
        }
        if(context.get("lccyno")!=null){
        	String tallyCode=(String)context.get("lccyno");
        	if(!StringUtils.isEmpty(Commons.getCurSignCode(null, tallyCode))){
        		request.setLccyno(Commons.getCurSignCode(null, tallyCode));
        	}else{
        		request.setLccyno((String)context.get("lccyno"));
        	}
        }else if(context.get("gucyno")!=null){
        	String tallyCode=(String)context.get("gucyno");
        	if(!StringUtils.isEmpty(Commons.getCurSignCode(null, tallyCode))){
        		request.setLccyno(Commons.getCurSignCode(null, tallyCode));
        	}else{
        		request.setLccyno((String)context.get("gucyno"));
        	}
        }
        if(context.get("lcamts")!=null){
        	 request.setLcamts((String)context.get("lcamts"));
        }else if(context.get("guamts")!=null){
        	 request.setLcamts((String)context.get("guamts"));
        }
        if(context.get("Tolep1")!=null){
       	    request.setTolep1((String)context.get("Tolep1"));
        }      
        if(context.get("Tolep2")!=null){
          	 request.setTolep2((String)context.get("Tolep2"));
        }  
        if(context.get("tolep")!=null){
        	double tolep=Double.valueOf((String)context.get("tolep"));
        	tolep=tolep*100;//对方发送的是小数
        	if(tolep<0){
        		 request.setTolep1(String.valueOf(tolep));
        		 request.setTolep2(String.valueOf(tolep));
        	}else if(tolep==0){
           	    request.setTolep1(String.valueOf(tolep));
           		request.setTolep2(String.valueOf(tolep));
        	}else{
        		request.setTolep1(String.valueOf(tolep));
        		request.setTolep2(String.valueOf(tolep));
        	}
        }   
        if(context.get("mgrate")!=null){
         	 request.setMgrate((String)context.get("mgrate"));
        } 
        if(context.get("epdate")!=null){
            try {
    			request.setEpdate( CommonUtil.formatDate((String)context.get("epdate"),"yyyyMMdd"));
    		} catch (EbillsException e) {
    			e.printStackTrace();
    		}
        }
        if(context.get("jjhmno")!=null){
            request.setJjhmno((String)context.get("jjhmno"));
        }
        if(context.get("apdate")!=null){
            try {
    			request.setApdate( CommonUtil.formatDate((String)context.get("apdate"),"yyyyMMdd"));
    		} catch (EbillsException e) {
    			e.printStackTrace();
    		}
        }else if(context.get("iudate")!=null){
            try {
    			request.setApdate( CommonUtil.formatDate((String)context.get("iudate"),"yyyyMMdd"));
    		} catch (EbillsException e) {
    			e.printStackTrace();
    		}
        }
        if(context.get("lcopno")!=null){
            request.setLcopno((String)context.get("lcopno"));
            request.setBizno((String)context.get("lcopno"));
        }
        if(context.get("prodcd")!=null){
            request.setProdcd((String)context.get("prodcd"));
        }
        if(context.get("old_jylsh")!=null){
            request.setOldjylsh((String)context.get("old_jylsh"));
        }
		return request;
	}
	

}
