<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@include file="/platform/page/system/head.jsp"%>  
<%@taglib uri="/WEB-INF/eap.tld" prefix="eap"%>
<html>
	<head>
		 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
		 <link rel="stylesheet" type="text/css" href="./platform/css/gjjs-themes/<%=xcp_sys_usertheme%>/printSys.css"></link>	
		 <script type="text/javascript" src="<%=webAppPath%>platform/js/jquery-core/jquery-1.8.0.min.js"></script>
    	 <script type="text/javascript" src="<%=webAppPath%>platform/js/share/include.js"></script>
    	 <script type="text/javascript" src="<%=webAppPath%>myDocument/js/tools/commQueryCondition.js"></script>
		 <script type="text/javascript" src="<%=webAppPath%>platform/page/system/printSys/queryPrintLogList.js"></script>
	</head>
	 
	<body id="queryPrintLogList_sysQueryList" class="easyui-layout"	border=false fit="true">
			<div id="queryPrintLogList_npanel" region="north" border="false">
				<div id="queryPrintLogList_condition" class='commQueryCondWrap'>
					<div class='query'>
						<div class='querybutton'>
							<div class='text'></div>
							<div class='input' id="queryPrintLogList_queryDiv"></div>
						</div>
					</div>
				</div>
			</div>
			<div id="queryPrintLogList_cpanel" region="center" border="false" style="background-color: #FAFAF9;">
				<table id="queryPrintLogList_letterTable">
				</table>
			</div>
	</body>
</html>