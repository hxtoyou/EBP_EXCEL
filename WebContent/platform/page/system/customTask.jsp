<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@include file="/platform/page/system/head.jsp"%>
<%@taglib uri="/WEB-INF/eap.tld" prefix="eap"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
		
		<script type="text/javascript" src="<%=webAppPath%>platform/js/jquery-core/jquery-1.8.0.min.js"></script> 
		<script type="text/javascript" src="<%=webAppPath%>platform/js/share/include.js"></script>
		<script type="text/javascript" src="<%=webAppPath%>myDocument/js/tools/commQueryCondition.js"></script>
		<script type="text/javascript" src="<%=webAppPath%>platform/page/system/customTask.js"></script>
	</head>
	<BODY style="background: #FFFFFF" id="customTask_sysQueryList"  class="easyui-layout" border=false fit="true">
		<!-- 使用公共查询组件方式 begin -->
		<div id="customTask_npanel" region="north" title="panel" border="false"  
			data-options="tools:'#customTask_paneltools'">
			<div id="customTask_condition" class='commQueryCondWrap'>
				<!-- 查询条件输入区域 -->
				<div class='query'>
					<div class='querybutton'>
						<div class='text'></div>
						<div class='input' id="customTask_queryDiv"></div>
					</div>
				</div>
			</div>
		</div>
		<!-- 使用公共查询组件方式 end -->
		 
		<div id="customTask_cpanel" region="center" border="false"
			style="background-color: #FAFAF9;">
			<table id="customTask_letterTable">
			</table>
		</div>
		
		<div id='customTask_paneltools'>
			<div id='customTask_commQueryIcon' class='commQueryUpicon'></div>
		</div>
		
	</BODY>
</HTML>