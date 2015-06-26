<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/platform/page/system/head.jsp"%>
<%@taglib uri="/WEB-INF/eap.tld" prefix="eap"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript"
	src="<%=webAppPath%>platform/js/jquery-core/jquery-1.8.0.min.js"></script>
<link href="<%=webAppPath%>platform/css/easyui-themes/bootstrap/easyui.css" rel="stylesheet">
<link href="<%=webAppPath%>platform/css/gjjs-themes/bootstrap/commons.css" rel="stylesheet">
<link href="<%=webAppPath%>platform/css/gjjs-themes/bootstrap/main.css" rel="stylesheet">
<link href="<%=webAppPath%>platform/css/gjjs-themes/bootstrap/uploadify.css" rel="stylesheet">
<link href="<%=webAppPath%>platform/css/gjjs-themes/default/excel.css" rel="stylesheet">
<script type="text/javascript" src="<%=webAppPath%>platform/js/share/include.js"></script>
<script type="text/javascript" src="<%=webAppPath%>platform/js/share/param.js"></script>
<script type="text/javascript" src="<%=webAppPath%>platform/js/share/excel.js"></script>
<script type="text/javascript"
	src="<%=webAppPath%>platform/js/jquery-core/jquery.cookie.js"></script>
	<style>
/*	table.ta_1{width:100%;text-align:center;border-color: floralwhite;border-spacing: 0;border-style: outset;}
	table.ta_1 td tr{text-align:center;table-layout:fixed;}
	div.display_1{
	display:none;}*/
	a.display_a{
	display:none;}
	tr.bg_wirter:hover{
		background:#deeeff;	
	}
	tr.bg_color:hover{
		background:#deeeff;	
	}
</style>
<script type="text/javascript">
	$xcp.stopPorcess();
	var contextData = '${context.user}';
	var formName = '${context.queryFromName}';
	console.log("queryFromName:",formName);
</script>
</head>
<body >
         <div class="bbcx_big display_1" style="" id ="swift">
			<div class=" bbcx_top" >
					 <a class="qhtj"  onmouseover="this.className='qhtj_out'"  onmouseout="this.className='qhtj'" href="#" data-options="iconCls:'icon-reload'" id="statistic">
	             切换统计表
	             </a>
				 <a  class="bbdc" onmouseover="this.className='bbdc_out'" onmouseout="this.className='bbdc'" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-print'"  id="export">
	             导出
	             </a>
				 <a class="bbcx" onmouseover="this.className='bbcx_out'" onmouseout="this.className='bbcx'" href="#" href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-search'" id="query"  >
	             查询
	             </a>
			</div>
		</div>
		<div  class="bbcx_box display_1" align="center" id="out">
			<div class="" align="center" id="content"></div>
			<div  align="center" id="statisticContent"></div>
		</div>
		<div id="pp" class="bbcx_pagination display_1" style="background:#efefef;border:1px solid #ccc;"></div> 
		<div id="pps" class="bbcx_pagination display_1" style="background:#efefef;border:1px solid #ccc;"></div> 
			<div class="easyui-dialog" title="" id="dialog" data-options="iconCls:'icon-search'" style="overflow-x:hidden;overflow-y:auto;" flt=true>
			<form id='form'>
				
			</form>
			
		</div>
	</body>

</html>