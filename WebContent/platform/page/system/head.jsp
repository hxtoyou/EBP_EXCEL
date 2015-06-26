<%@page import="com.ebills.util.EbillsCfg"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.util.*,java.net.*"%>
<%@page import="com.eap.core.*"%>
<%@page import="org.apache.commons.bussprocess.context.*"%>
<%@page import="org.apache.commons.bussprocess.data.*"%>
<%@taglib uri="/WEB-INF/eap.tld" prefix="eap"%>
<jsp:useBean id="utb" scope="page"
	class="com.eap.channel.web.JspContextServices">
	<%
		try {
				utb.initialize(request, response);
		} catch (Exception ex) {
			response.sendRedirect(request.getContextPath()
					+ "/page/session_expire.html");
			return;
		}
	%>
</jsp:useBean>
<%
	Context context = utb.getContext();
	String webAppPath = utb.getWebAppPath();
	String pdfPath = EbillsCfg.getProperty("birt.common.pdf");
	if (null != pdfPath && "" != pdfPath) {
		pdfPath = pdfPath.replace("\\", "/");
	}
	String hostAppPath = "http://"
			+ request.getLocalAddr() + ":"
			+ request.getServerPort() + webAppPath;
	//用户主题
	String xcp_sys_usertheme = (String) context.get("userTheme");
	xcp_sys_usertheme = (xcp_sys_usertheme == null || xcp_sys_usertheme
			.equals("")) ? "bootstrap" : xcp_sys_usertheme;
	String EAP_SessionID = utb.getSessionId();
	String xcp_sys_formInitData = "";
	String userId = "";
	String messageOutPut = "";
	try {
		messageOutPut = (String) context.get("messageOutPut");
	} catch (Exception e) {
		System.out.println(e);
	}
	Map<String,String> cfgMap = new HashMap<String, String>();
	try {
		userId = (String) context.getValue("userId");
		xcp_sys_formInitData = (String) context.getValue("initData");
		cfgMap = (Map) context.getValue("cfmMap");
	} catch (Exception e) {
		System.err.println(e);
	}

	String sys_language = (String) context.get("userLanguage");
	String userRptMtd = (String) context.get("userRptMtd");
	String uuidHead = request.getParameter("uuid");
	String taskFlag = request.getParameter("taskFlag");
	String taskState = request.getParameter("taskState");
	String overalParam = request.getParameter("overalParam");

	String sys_workDate = (String) context.get("workDate");

	String latestFlag = request.getParameter("latestFlag") == null
			? ""
			: request.getParameter("latestFlag");
	
	String module_autoTrc = webAppPath + "platform/js/share/autoTrc.js";
	
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<script>
	var rootPath      = '<%=webAppPath%>';    //此处定义系统的上下文wxl
	var hostAppPath   = '<%=hostAppPath%>';   //服务器的ip加端口
	var EAP_SID       = '<%=EAP_SessionID%>';
	var EAP_LANGUAGE  = '<%=sys_language%>';  //设置系统默认语言
	var xcp_sys_formInitData     = '<%=xcp_sys_formInitData%>'; 
	var messageOutPut = '<%=messageOutPut%>';
	var uuidHead      = '<%=uuidHead%>';
	var pdfPath 	  = '<%=pdfPath%>';
	var taskFlag      = '<%=taskFlag%>';
	var taskFlagHadle = false;   //判断是否是手工经办
	
	var taskState = '<%=taskState%>'; // 对应工作流步骤    0 可操作 1未完成 2已完成
	
	if(taskState == null || taskState == "" || taskState == "null"){
		taskState = "0" ;
	}
	if(taskState =="2" ){
		taskFlag = "K001";//已经完成
	}
	if((taskFlag == null || taskFlag == "" || taskFlag == "null")){
		taskFlag = "1001", taskFlagHadle = true;
	}
	var overalParam = '<%=overalParam%>';
	var USERID        = '<%=userId%>';
	var sysDate = '<%=sys_workDate%>';
	
	var xcp_sys_constant_usertheme = '<%=xcp_sys_usertheme%>';
	
	var latestFlag = '<%=latestFlag%>';
	
	var USER_RPTMTD = '<%=userRptMtd%>';
	if("null" == USER_RPTMTD)USER_RPTMTD == null;
</script>

