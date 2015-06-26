<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@include file="/platform/page/system/head.jsp"%>
<%@taglib uri="/WEB-INF/eap.tld" prefix="eap"%>
<!-- 注销管理jsp文件 -->
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
		<script type="text/javascript" src="<%=webAppPath%>platform/js/jquery-core/jquery-1.8.0.min.js"></script> 
		<script type="text/javascript" src="<%=webAppPath%>platform/js/share/include.js"></script>
		<script type="text/javascript" src="<%=webAppPath%>myDocument/js/tools/commQueryCondition.js"></script>
		<script type="text/javascript" src="<%=webAppPath%>platform/page/system/logout/logoutMag.js"></script>
	</head>
	<BODY style="background: #FFFFFF" id="logoutMgr_sysQueryList" class="easyui-layout" border=false fit="true">
		<div id="logoutMgr_npanel" region="north" title="panel" border="false"
		data-options="tools:'#logoutMgr_paneltools'">
				<div id="logoutMgr_condition" class='commQueryCondWrap'>
					<!-- 查询条件输入区域 -->
					<div class='query'>
						<div class='querybutton'>
							<div class='text'></div>
							<div class='input' id="logoutMgr_queryDiv"></div>
						</div>
					</div>
				</div>
			</div>
			<!-- 工作流任务信息区域 -->
			<table id="workflowTaskInfo" style="position: none;"></table>
		<div id="logoutMgr_cpanel" region="center" border="false" style="background-color: #FAFAF9;">
			<table id="logoutMgr_letterTable">
			</table>
		</div>
		<div id='logoutMgr_paneltools'>
			<div id='logoutMgr_commQueryIcon' class='commQueryUpicon'></div>
		</div>
</BODY>
</HTML>