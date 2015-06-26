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
		<%-- <script type="text/javascript" src="<%=webAppPath%>platform/page/system/printSys/printSys.js"></script> --%>
    	 <%-- <applet code="AppletTest.class" codebase="./" archive="<%=webAppPath%>/report/printTest.jar" width="0" height=""></applet> --%>
	</head>
	 
	<body>
		<div id="printTabs" class="easyui-tabs " fit="true" border="false">
			<div title="<eap:language name="print.penddingList" type="systemParam" />">
				<iframe id='taskMgrMain-iframe-a' src='./printSys.do?method=viewQueryPrintList' frameborder='0'  style='width:100%;height:100%;overflow-x:hidden;overflow-y:auto'></iframe>
			</div>
			<div title="<eap:language name="print.doneList" type="systemParam" />">
				<iframe id='taskMgrMain-iframe-b' src='./printSys.do?method=viewQueryPrintedList' frameborder='0'  style='width:100%;height:100%;overflow-x:hidden;overflow-y:auto'></iframe>
			</div>
			<div title="<eap:language name="print.printlogs" type="systemParam" />">
				<iframe id='taskMgrMain-iframe-c' src='./printSys.do?method=viewQueryPrintLogList' frameborder='0'  style='width:100%;height:100%;overflow-x:hidden;overflow-y:auto'></iframe>
			</div>
		</div>			
		<div id="noPrintedDblClickTrueShowId" style="display: none;">
			<iframe id="noPrintedShowWindow" scrolling="no" frameborder="0"  style="width:100%;height:100%;">
			</iframe>
		</div>
	</body>
</html>