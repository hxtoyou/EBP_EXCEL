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
						+ "/page/session_expire.html");
				return;
			}
	%>
</jsp:useBean>
<%
	String errorMes = "", errorCode = "";
	try {
		errorMes = (String) request.getAttribute("errorMessage");
		errorCode = (String) request.getAttribute("errorCode");
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
<script src="./page/index.js"></script>

</head>
<body class="loginbg">
		<form method="post" action="./signIn.do" name="form2">
			<div class="banner_bg">密码修改</div>
			<div class="login">
				<div class="logo"></div>
				<input class="search" id="userId" name="userId" type="text" value="用户名" dummyUsrName="" />
				<input class="search" id="userPasOld" name="userPasOld" type="text" value="旧密  码" dummyUsrPwd="" /> 
				<input class="search" id="userPasNew"  name="userPasNew" dummyUsrPwd="" type="password" value="新密码" style="display: none" />
				<input class="search" id="userPasNew2"  name="userPasNew2" dummyUsrPwd="" type="password" value="确认密码" style="display: none" />
				
				<!--注：实际获取密码的表单是pwd2-->
				
				<span id="logShow"
					style="color: red; line-height: 18px; padding-left: 10px; padding-right: 10px; margin-left: 79px; font-size: 14px;"><%=errorMes%></span>
				<div class="button loginBtn">ok</div>
			</div>
	</form>
</body>
</html>