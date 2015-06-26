<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@include file="/platform/page/system/head.jsp"%>
<%@taglib uri="/WEB-INF/eap.tld" prefix="eap"%>
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<script type="text/javascript" src="<%=webAppPath%>platform/js/workFlow/lib/raphael-min.js"></script>
<script type="text/javascript" src="<%=webAppPath%>platform/js/jquery-core/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=webAppPath%>platform/js/share/include.js"></script>
<script type="text/javascript" src="<%=webAppPath%>platform/js/workFlow/workFlow.js"></script>
<script type="text/javascript" src="<%=webAppPath%>platform/js/workFlow/workFlow.jpdl.js"></script>
<script type="text/javascript" src="<%=webAppPath%>platform/js/workFlow/workFlow.editors.js"></script>
<script type="text/javascript" src="<%=webAppPath%>platform/page/system/transitTrade/workflowDesign.js"></script>

<style type="text/css">
body {
	margin: 0;
	pading: 0;
	text-align: left;
	font-family: Arial, sans-serif, Helvetica, Tahoma;
	font-size: 12px;
	line-height: 1.5;
	color: black;
	background-image: url(platform/js/workFlow/img/bg.png);
}

.node {
	width: 70px;
	text-align: center;
	vertical-align: middle;
	border: 1px solid #fff;
}

.mover {
	border: 1px solid #ddd;
	background-color: #ddd;
}

.selected {
	background-color: #ddd;
}

.state {
	
}

#workFlow_props table {
	
}

#workFlow_props th {
	letter-spacing: 2px;
	text-align: left;
	padding: 6px;
	background: #ddd;
	width : 70px;
}

#workFlow_props td {
	background: #fff;
	padding: 6px;
	width : 200px;
}

#pointer {
	background-repeat: no-repeat;
	background-position: center;
}

#path {
	background-repeat: no-repeat;
	background-position: center;
}

#task {
	background-repeat: no-repeat;
	background-position: center;
}

#state {
	background-repeat: no-repeat;
	background-position: center;
}
</style>
</head>
<script type="text/javascript">
	function getWebAppPath(){
		var webAppPath = "<%=webAppPath%>"; 
		return webAppPath;
	}
</script>

<body>
<div id="workFlow_tools"
	style="display:none;position: absolute; top: 10; left: 10; background-color: #fff; width: 70px; cursor: default; padding: 3px;"
	class="ui-widget-content">
<div id="workFlow_tools_handle" style="text-align: center;"
	class="ui-widget-header">工具集</div>


<div class="node" id="workFlow_save"><img src="platform/js/workFlow/img/save.gif" />&nbsp;&nbsp;保存</div>
<div>
<hr />
</div>
<div class="node selectable" id="pointer"><img
	src="platform/js/workFlow/img/select16.gif" />&nbsp;&nbsp;选择</div>
<div class="node selectable" id="path"><img
	src="platform/js/workFlow/img/16/flow_sequence.png" />&nbsp;&nbsp;转换</div>
<div>
<hr />
</div>
<div class="node" id="start" type="start"><img
	src="platform/js/workFlow/img/16/start_event_empty.png" />&nbsp;&nbsp;开始</div>
<div class="node state" id="task" type="task"><img
	src="platform/js/workFlow/img/16/task_empty.png" />&nbsp;&nbsp;任务</div>
<div class="node" id="end" type="end"><img
	src="platform/js/workFlow/img/16/end_event_terminate.png" />&nbsp;&nbsp;结束</div>
</div>

<div id="workFlow_props"
	style="display:none;position: absolute; top: 30; right: 50; background-color: #fff; width: 300px; padding: 3px;"
	class="ui-widget-content">
<div id="workFlow_props_handle" class="ui-widget-header">属性</div>
<table border="1" width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td>aaa</td>
	</tr>
	<tr>
		<td>aaa</td>
	</tr>
</table>
<div>&nbsp;</div>
</div>

<div id="workFlow"></div>
</body>

</html>
<script type="text/javascript">
	templateNo = "<%=request.getParameter("templateNo")%>";
	stepNo = "<%=request.getParameter("stepNo")%>";
	nodeArw = "<%=request.getParameter("nodeArw")%>";
	nodeIds = "<%=request.getParameter("nodeIds")%>";
</script>