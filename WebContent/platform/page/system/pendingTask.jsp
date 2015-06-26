<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@include file="/platform/page/system/head.jsp"%>
<%@taglib uri="/WEB-INF/eap.tld" prefix="eap"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
		
		<script type="text/javascript" src="<%=webAppPath%>platform/js/jquery-core/jquery-1.8.0.min.js"></script> 
		<script type="text/javascript" src="<%=webAppPath%>platform/js/share/include.js"></script>
		<script type="text/javascript" src="<%=webAppPath%>myDocument/js/tools/commQueryCondition.js"></script>
		<script type="text/javascript" src="<%=webAppPath%>platform/page/system/pendingTask.js"></script>

	</head>
	<!-- 为使用公共查询组件方式将原有body id="taskList"修改为pendingTask_sysQueryList -->
	<BODY style="background: #FFFFFF" id="pendingTask_sysQueryList" class="easyui-layout" border=false fit="true">
		
		<!-- 使用公共查询组件方式 begin -->
		<div id="pendingTask_npanel" region="north" title=" " border="false" 
			data-options="tools:'#pendingTask_paneltools'">
			<div id="pendingTask_condition" class='commQueryCondWrap'>
				<!-- 查询条件输入区域 -->
				<div class='query'>
					<div class='querybutton'>
						<div class='text'></div>
						<div class='input' id="pendingTask_queryDiv"></div>
					</div>
				</div>
			</div>
		</div>
		
		<div id="pendingTask_cpanel" region="center" border="false"
			style="background-color: #FAFAF9;">
			<table id="pendingTask_letterTable">
			</table>
			<div id="mmm" class="easyui-menu" style="width:120px;">
				<div  id="addAnnotation" data-options="iconCls:'icon-add'"><eap:language name="pendingTask.addAnnotaion" type="global"/></div>
				<div  id="viewAnnotation" data-options="iconCls:'icon-view'"><eap:language name="pendingTask.viewAnnotaion" type="global"/></div>
				<div  id="gotoHandle" data-options="iconCls:'icon-edit'"><eap:language name="finishedTask.agreeChange" type="global"/></div>
	   		</div>
		</div>
		
		<div id='pendingTask_paneltools' border="true" >
			<div id='pendingTask_commQueryIcon' class='commQueryUpicon'></div>
		</div>
		<!-- 使用公共查询组件方式 end -->
		
	</BODY>
</HTML>