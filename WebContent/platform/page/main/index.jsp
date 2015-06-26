<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="com.eap.core.*"%>
<%@page import="org.apache.commons.bussprocess.data.*"%>
<%@taglib uri="/WEB-INF/eap.tld" prefix="eap"%>
<jsp:useBean id="utb" scope="page" class="com.eap.channel.web.JspContextServices">
	<%
		try {
				utb.initialize(request, response);
			} catch (Exception ex) {
				response.sendRedirect(request.getContextPath()
						+ "/platform/page/main/session_expire.html");
				return;
			}
	int login = -1;
	if(request.getAttribute("login") != null){
		login = (Integer) request.getAttribute("login");
	}
	/* if(login != 3){
		Cookie cookie = new Cookie("EAP_SID", "");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	} */
	%>
</jsp:useBean>
<%
	String errorMes = "", errorCode = "" , userCode = "";
	try {
		errorMes = (String) request.getAttribute("errorMessage");
		errorCode = (String) request.getAttribute("errorCode");
		userCode = (String) request.getAttribute("userCode");
		userCode = (userCode == "null" || userCode == null ) ? "" : userCode;
	} catch (Exception ex) {
	}
	errorMes = errorMes == null || errorMes == "SYSERROR" ? ""
			: errorMes;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title><eap:language name="main.title" type="main" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />

<link rel="stylesheet" type="text/css" href="./platform/css/gjjs-themes/default/login.css"></link>

<script src="platform/js/jquery-core/jquery-1.8.0.min.js"></script>
<script src="platform/js/share/md5.js"></script>

<script type="text/javascript">
 	var userCode = '<%=userCode%>';//登陆的用户id 方便密码错误后记录账号
 	var errCode  = '<%=errorCode%>';//错误信息 根据错误code设计焦点
</script>
<script src="./platform/page/main/index.js"></script>
<%
if(errorCode != null && "EBP-101".equals(errorCode)){
	String outScript = "<script>eval(duplicateLogin('"+ request.getContextPath() +"'));</script>";
	out.print(outScript);
}
%>
</head>
<body class="loginbg">
		<form method="post" action="./signIn.do" name="form2">
			<input type="hidden" id="usr_language" name="usr_language" />
			<div style="display:none">
           <input type="password" id="hidePass" name="hidePass" />
           </div>
			<div class="banner_bg">Login</div>
			<div class="login" >
				<div class="logo" ></div>
				<div id="main">
				
				</div>
				<div><span id="logShow"
					style="color: red; line-height: 18px; padding-left: 10px; padding-right: 85px; margin-left: 1px; font-size: 14px;"><%=errorMes%></span>
				</div>
				<div class="button loginBtn" id="loginBtn">登   录</div>
			</div>
	</form>
</body>
</html>