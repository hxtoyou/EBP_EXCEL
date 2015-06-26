<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@include file="/platform/page/system/head.jsp"%>
<%@taglib uri="/WEB-INF/eap.tld" prefix="eap"%>
<%
	String txnNo = request.getParameter("txnNo")==null?"":request.getParameter("txnNo");

%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
		
		<script type="text/javascript" src="<%=webAppPath%>platform/js/jquery-core/jquery-1.8.0.min.js"></script> 
		<script type="text/javascript" src="<%=webAppPath%>platform/js/share/include.js"></script>
<script>	

	//需要传递的参数列表
	var pendingTaskParam = ['txnNo','tradeNo','taskFlag','taskId','state','stepNo','txnctxid'];
	
	function addWindow(rowData,index){
		  url = './' + rowData.tradename + '.do?method=getEditData';
		  $.each(pendingTaskParam,function(i,param){
			  url += "&" + param + "=" + (rowData[param] || '');
		  });
		  
		  url += "&queryFlag=HisCorrect&taskState=2";
		  //打开窗口
		  top.window.$('body').xcpApp('createTask',
				  {
			   		 url : url,
			   		 name : rowData.tradedesc,
			   		 txnNo : rowData.txnNo,
			   		 tradeNo : rowData.tradeno,
			   		 taskFlag : rowData.stepNo || '' ,
			   		 currentBizNo:rowData.bizno
			   	  }
		  );
	}
	$(function(){
		$('#letterTable').datagrid({
			loadMsg : "请等待,正在加载数据......",
			singleSelect: true,
			title: '业务变更信息列表',
			url:$xcp.def.getFullUrl('./commonQuery.do?method=queryHandleInfo&txnNo='+<%=txnNo%>),
			width: "900",
			height: "auto",
			fit   : true,
			fitColumns: true,
			columns:[[
						{title : '业务流水号', field : 'txnNo', width : 150, align : 'center'},
						{title : '交易名称', field : 'tradedesc', width : 150, align : 'center'},
						{title : '业务编号', field : 'bizno', width : 100, align : 'center'},
						{title : '状态', field : 'state', width : 100, align : 'center'},
						{title : '主键', field : 'txnctxid', width : 100, align : 'center',hidden:true},
						{title : '', field : 'tradeno', width : 150, align : 'center',hidden:true},
						{title : '', field : 'tradename', width : 150, align : 'center',hidden:true}
					]],
			pagination:true,
			rownumbers:true,
			onDblClickRow: function(rowIndex, rowData){
				addWindow(rowData);
			}
			
		});
		
		var p = $('#letterTable').datagrid('getPager');
	    $(p).pagination({
	        pageSize: 10,//每页显示的记录条数，默认为10  
	        pageList: [5,10,15,20],//可以设置每页记录条数的列表  
	        beforePageText: '第',//页数文本框前显示的汉字  
	        afterPageText: '页    共 {pages} 页',  
	        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	    });
	    
	});

	function doquery() {
		/* $('#letterTable').datagrid('options').queryParams = $.getJson('findtable', false);
		$("#letterTable").datagrid('reload'); */
	}
</script>
</HEAD>
<BODY style="background: #FFFFFF"  class="easyui-layout" border=false>
	<div region="center" border="false" style="background-color: #FAFAF9;">
		<table id="letterTable" style="width:100%">
		</table>
	</div>
</BODY>
</HTML>