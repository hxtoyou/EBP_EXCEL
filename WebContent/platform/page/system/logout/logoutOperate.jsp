<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="com.ebills.wf.WfEngine" %>
<%
	//注销操作的jsp页面
	//userId 来源请参加 head.jsp文件  ,操作员id
	String operateId = request.getParameter("operateId");
	// 将被注销的用户id
	String logoutUserId = request.getParameter("logoutUserId");
	if (operateId != null && !(operateId = operateId.trim()).isEmpty()
			&& logoutUserId != null
			&& !(logoutUserId = logoutUserId.trim()).isEmpty()) {
		Integer operateInt = Integer.parseInt(operateId);
		Integer logoutUserInt = Integer.parseInt(logoutUserId);
		//注销用户
		WfEngine.getInstance().getLoginManager()
				.killOp(operateInt, logoutUserInt);
	}
%>