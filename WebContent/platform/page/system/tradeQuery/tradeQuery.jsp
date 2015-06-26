<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@include file="/platform/page/system/head.jsp"%>
<%@taglib uri="/WEB-INF/eap.tld" prefix="eap"%>
<!-- 交易查询jsp文件 -->
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
		
		<script type="text/javascript" src="<%=webAppPath%>platform/js/jquery-core/jquery-1.8.0.min.js"></script> 
		<script type="text/javascript" src="<%=webAppPath%>platform/js/share/include.js"></script>
		<script type="text/javascript" src="<%=webAppPath%>platform/js/share/param.js"></script>
		<script type="text/javascript" src="<%=webAppPath%>myDocument/js/tools/commQueryCondition.js"></script>
		<script type="text/javascript" src="<%=webAppPath%>platform/page/system/tradeQuery/tradeQuery.js"></script>
	</head>
	<BODY style="background: #FFFFFF" id="tradeQuery_sysQueryList" class="easyui-layout" border=false fit="true">
		<div id="tradeQuery_npanel" region="north" title=" " border="true"
		data-options="tools:'#tradeQuery_paneltools'">
				<div id="tradeQuery_condition" class='commQueryCondWrap'>
					<!-- 查询条件输入区域 -->
					<div class="paramQueryExport" title="导出" id="linkButLskPrint" style="height: 100px;"></div>
					<div class='query'>
						<div class='querybutton'>
							<div class='text'></div>
							<div class='input' id="tradeQuery_queryDiv"></div>
						</div>
					</div>
				</div>
			</div>
			<!-- 工作流任务信息区域 -->
			<table id="workflowTaskInfo" style="position: none;"></table>
		<div id="tradeQuery_cpanel" region="center" border="false" style="background-color: #FAFAF9;">
			<table id="tradeQuery_letterTable">
			</table>
		</div>
		<div id='tradeQuery_paneltools'>
			<div id='tradeQuery_commQueryIcon' class='commQueryUpicon'></div>
		</div>
</BODY>
</HTML>