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
<script type="text/javascript"
	src="<%=webAppPath%>platform/js/jquery-core/jquery.cookie.js"></script>
<style>
	table.ta_1{width:100%;text-align:center;border-color: floralwhite;border-spacing: 0;border-style: outset;}
	table.ta_1 td tr{text-align:center;table-layout:fixed;}
	div.display_1{
	display:none;}
</style>
<script type="text/javascript">
$(function(){
	var pageSize=4;
	var pageNum=1;
	var totalPage = 0;
	var construct = '${context.output}';
	var templateType = '${context.templateType}';
	var types = templateType.split("&");
	var data = JSON.parse(construct);
	var html="";
	totalPage = data.totalPages;
	var param = $.cookie("param");
	var html = '<h1>Excel Converter</h1><table class="ta_1" width="'+data.result[0].tableWidth+'" height="auto" border="1">';
	var re = new RegExp("\\{(.*?)\\}");
	$.each(data.result[0].htmlRowStructure, function(i, value) {
		html+='<tr valign="middle">';
		$.each(value.htmlTdStructure,function(j,item){
			var value = "";
			if(!re.test(item.value)){
				value = item.value;
			}
			html+='<td colspan="'+item.colspan+'" rowspan="'+item.rowspan+'" align="center" width="'+item.width+'" height="'+item.height+'">'+value+'</td>'
		});
		html+='</tr>';
		
	
	});
	html+="</table>"
	$("#content").css("width",data.result[0].tableWidth);
	$("#bottom").css("width",data.result[0].tableWidth);
	// $("#out").css("width",data.result[0].tableWidth);
	var value = "当前第"+1+"页"+"共"+totalPage+"页";
	$("#pageTail").html(value);
	$("#content").html(html);

	if(types.length===1){
		if(types[0]==="detail"){
			 $("#swift").addClass("display_1");
			 $("#statisticContent").addClass("display_1");
		}else if(types[0]==="statistic"){
			 $("#bottom").addClass("display_1");
			  $("#swift").addClass("display_1");
			   $("content").addClass("display_1");
		}
	}else if(types.length===2){
		if(types[0]==="detail"){
			$("#statisticContent").addClass("display_1");
		}else if(types[0]==="statistic"){
			 $("#bottom").addClass("display_1");
			 $("content").addClass("display_1");
			
		}

	}else{
		console.log("无显示方式参数，默认显示所有");
	}
	
	if(pageNum>=totalPage){
			$("#next").attr("disable","disable");
		}
	function changeButtonType(pageNumber){
		if(pageNum>=totalPage){
			$("#next").attr("disabled","disabled");
			
		}else{
			$("#next").removeAttr("disabled");
		}
		if(pageNum<=1){
			$("#back").attr("disabled","disabled");
			
		}else{
			$("#back").removeAttr("disabled");
		}
	}
	$("#first").click(function(){
		pageNum=1;
		var value = "当前第"+(pageNum)+"页"+"共"+totalPage+"页";
		$("#pageTail").html(value);
		getData(pageNum);
		changeButtonType(pageNum);
	});
	$("#next").click(function(){
		pageNum+=1;
		changeButtonType(pageNum);
		var value = "当前第"+(pageNum)+"页"+"共"+totalPage+"页";
		$("#pageTail").html(value);
		getData(pageNum);
	});
	$("#back").click(function(){
		pageNum-=1;
		changeButtonType(pageNum);
		var value = "当前第"+(pageNum)+"页"+"共"+totalPage+"页";
		$("#pageTail").html(value);
		getData(pageNum);
	});
	$("#last").click(function(){
		pageNum=totalPage;
		var value = "当前第"+(pageNum)+"页"+"共"+totalPage+"页";
		$("#pageTail").html(value);
		getData(pageNum);
		changeButtonType(pageNum);
	});
	
	
	function getStatisticData(){
		$.ajax({
				type: 'POST',
				url: "./preview.do?method=getMsg&"+param ,
				data: {
					"templateType" : "statistic",
					"templateName":"test3.xlsx",
					"pageNumber": 1,
					"pageSize":pageSize
				} ,
				success: function(data){
					var data = JSON.parse(data);
					var data = data.outEntity;
					var statisticHtml = "";
					generateHtml(statisticHtml,"statisticContent",data);
					// $("#statisticContent").css("width",data.result[0].tableWidth);
					// $("#statisticContent").html(statisticHtml);
				} ,
				error: function(){
					console.log("error");
				}
			});
	}
	function getData(pageNumber){
			$.ajax({
				type: 'POST',
				url: "./preview.do?method=getMsg&"+param,
				data: {
					"templateType":"detail",
					"templateName": "test3.xlsx",
					"pageNumber": pageNumber,
					"pageSize":pageSize
				} ,
				success: function(data){
					var data = JSON.parse(data);
					var data = data.outEntity;
					totalPage = data.totalPages;
					var value = "当前第"+pageNum+"页"+"共"+totalPage+"页";
					$("#pageTail").html(value);
					var contentHtml="";
					generateHtml(contentHtml,"content",data);
					
					$("#bottom").css("width",data.result[0].tableWidth);
				} ,
				error: function(){
					console.log("error");
				}
			});
	} 
	var type =types[0];
	$("#statistic").click(function(){
		if(type==="detail"){
			 getStatisticData();
			$("#statisticContent").removeClass("display_1");
			$("#statistic").html("切换台账明细表");
			$("#content").addClass("display_1");
			$("#bottom").addClass("display_1");
			type="statistic";
		}else if(type==="statistic"){
			 getData(pageNum);
			 $("#statisticContent").addClass("display_1");
			 $("#content").removeClass("display_1");
			 $("#statistic").html("切换统计信息表");
			 $("#bottom").removeClass("display_1");
			 type="detail";
		}

	});
	function generateHtml(html,statisticContent,data){
			var html='<h1>Excel Converter</h1><table class="ta_1" width="'+data.result[0].tableWidth+'" height="auto" border="1">';
						$.each(data.result[0].htmlRowStructure, function(i, value) {
							html+='<tr valign="middle">';
							$.each(value.htmlTdStructure,function(j,item){
								var value = "";
								if(!re.test(item.value)){
									value = item.value;
								}
								html+='<td colspan="'+item.colspan+'" rowspan="'+item.rowspan+'" align="center" width="'+item.width+'" height="'+item.height+'">'+value+'</td>'
							});
							html+='</tr>';
						});
						html+="</table>";
						$("#"+statisticContent).css("width",data.result[0].tableWidth);
						$("#"+statisticContent).html(html);
	}
});
</script>
</head>
<tbody>
<div style="float:left;" id = "swift">
<button  class="btn btn-large" id="statistic">
		切换统计表
</button>
</div>
<div class="container" align="center" id="out">
<div class="container" align="center" id="content">
</div>
<div class="container" align="center" id="statisticContent">
</div>
<div class="container" align="center" id="bottom">
<button id="first" class="btn btn-large">
		第一页
</button>
<button id="back" class="btn btn-large">
		上一页
</button>
<button id="next" class="btn btn-large">
		下一页
</button>
<button id="last" class="btn btn-large">
		最后一页
</button>
<p id="pageTail"></p>
</div>
</div>

</tbody>
</html>
