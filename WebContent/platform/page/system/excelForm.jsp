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
		<script type="text/javascript" src="<%=webAppPath%>platform/js/share/include.js"></script>
		<script type="text/javascript" src="<%=webAppPath%>platform/js/share/param.js"></script>
<script type="text/javascript"
	src="<%=webAppPath%>platform/js/jquery-core/jquery.cookie.js"></script>
<script type="text/javascript">

$(function() {
	var name = '${context.user}';
	var data = JSON.parse(name);
	var html = "";
	
	$("#content").html(html);
	$.each(data, function(i, value) {
		if(value.comboAttr==""){
			html+=value.name + " :<input id='"+value.varName+"' class='" + value.classType + "' name='" + value.varName + "'><p/>"
		}else{
		html += value.name + " :<input id='"+value.varName+"' class='" + value.classType + "' name='" + value.varName + "' "+"data-options=valueField:'val',textField:'name',data:$xcp.getConstant('"+value.comboAttr+"'),></input><p/>"
		}
		
	});



	html+='<input type="button" value="提交" id ="submit">';
	$("#content").html(html);
	$.parser.parse();
	$("#submit").click(function(){
		var param = $("#content").serialize();

		$.cookie('param', param);
		var url = "./preview.do?method=getDocReport&paramSheetNum=0&formatSheetNum=3&templateName=test3.xlsx&pageSize=4";
		post(url, param);  
	});
	function post(URL, PARAMS) {        
	    var temp = document.createElement("form");        
	    temp.action = URL;        
	    temp.method = "post";        
	    temp.style.display = "none";  
	    var paramlist = PARAMS.split("&");
	    $.each(paramlist,function(i,value){
	    	valueList = value.split("=");
	    	var opt = document.createElement("textarea");
	    	opt.name = valueList[0];        
	        opt.value = valueList[1]; 
	        temp.appendChild(opt);  
	    });
	    document.body.appendChild(temp);        
	    temp.submit();        
	    return temp;        
	}        
})

</script>
</head>
<body style="background: #FFFFFF;">
	<h>EXCEL</h>
		<div>
			<form id='content'>
				
			</form>
			
		</div>
	</body>
</html>