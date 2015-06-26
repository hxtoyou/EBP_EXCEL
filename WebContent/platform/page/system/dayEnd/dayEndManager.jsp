<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@include file="/platform/page/system/head.jsp"%>
<%@taglib uri="/WEB-INF/eap.tld" prefix="eap"%>
<html>
<head>
<style type="text/css">
.dayEndQueryBtn {
	border: 1px solid #0073ea;
	border-radius: 3px;
	height: 26px;
	width: 90px;
	background-color: #3499fe;
	color: #FFF;
	font-size: 12px;
	line-height: 25px;
	text-align: center;
	cursor: pointer;
}

div.layout-expand {
	background-color: #ffffff !important;
}

.dayEndQueryBtn:hover {
	background-color: #0073ea;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<script type="text/javascript"
	src="<%=webAppPath%>platform/js/jquery-core/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=webAppPath%>platform/js/share/include.js"></script>
<script type="text/javascript"
	src="<%=webAppPath%>/platform/page/system/dayEnd/dayEnd.js">
	</script>
</head>
<body style="background: #FFFFFF" class="easyui-layout" border=false
	fit="true">
	<div id="npanel" data-options="region:'north', split:true " border="false" title="<eap:language name="dayEnd.title" type="global" />"
		style="height: 130px; overflow-y: hidden;">
		<div id="condition" class='commQueryCondWrap'>
			<table id="nc" class="etable">
				<tr>
					<td align="left" style="text-align: right;"><eap:language name="dayEnd.RunMode" type="global" /></td>
					<td align="left"><input type="text" id="runMode"
						class="easyui-combobox" data-options="valueField:'val',textField:'name',data:$xcp.getConstant('ITBATITM.RUNMODE')"/></td>
					<%-- <td align="left" style="text-align: right;"><eap:language name="dayEnd.frequency" type="global" /></td>
					<td align="left"><input type="text" class="easyui-combobox"
						id="rate" onChange="getBatch()" /></td>
					<td align="left" style="text-align: right;"><eap:language name="dayEnd.batchNO" type="global" /></td>
					<td align="left"><input type="text" class="easyui-combobox O "
						id="execNo" /></td> --%>
					<td align="left"><button class="but_query paramQueryBtn"
							id="dayEndQuery" type="button" onclick="dayEndQuery()"><eap:language name="leftTree.query" type="global" /></button>
							<button id="startBut"
							class="but_query dayEndQueryBtn" type="button" onclick="start()"><eap:language name="dayEnd.startBatch" type="global" /></button>
							<button id="startAllBut"
							class="but_query dayEndQueryBtn" type="button" onclick="startAll()"><eap:language name="dayEnd.startBatchAll" type="global" /></button>
					</td>
				</tr>
				<tr align="left">
					<td></td>
					<td align="left">
						
						<%-- <button id="startBut"
							class="but_query dayEndQueryBtn" type="button" onclick="start()"><eap:language name="dayEnd.startBatch" type="global" /></button>
						<button id="stopBut" class="but_query dayEndQueryBtn"
							type="button" onclick="stop()" disabled="disabled"><eap:language name="dayEnd.stopBatch" type="global" /></button> --%>
					</td>
					<td></td>
					<TD id="showTdStatus" align="left"></TD>
				</tr>
			</table>
		</div>
	</div>
	<div id="cpanel" region="center" border="false">
		<table id="dayEndTab" style="height: 40%; width: 100%">
		</table>
	</div>
	<div id="showText" data-options="region:'south',split:true"
		title="<eap:language name="dayEnd.log" type="global" />" style="height: 200px;"></div>
</body>
</html>
