<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@include file="/platform/page/system/head.jsp"%>
<%@taglib uri="/WEB-INF/eap.tld" prefix="eap"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>


<script type="text/javascript" src="<%=webAppPath%>platform/js/jquery-core/jquery-1.8.0.min.js"></script> 
<script type="text/javascript" src="<%=webAppPath%>platform/js/share/include.js"></script>
<script type="text/javascript" src="<%=webAppPath%>platform/js/share/app.js"></script>
<style type="text/css">
.editTdL{
	width: 20%; 
	text-align: right
}

.editTdR{
	width: 80%; 
	text-align: left
}
</style>
<script>	
	//需要传递的参数列表
	 var pendingTaskParam = ['txnNo','tradeNo','taskFlag','taskId','state','taskState'];
	
	function addWindow(rowData){
		
		  rowData.taskFlag = rowData.stepNo;
		  url = './' + rowData.tradeName + '.do?method=getEditData';
		  $.each(pendingTaskParam,function(i,param){
			  url += "&" + param + "=" + (rowData[param] || '');
		  });
		  
		  url += "&queryFlag=History";
		  
		  //打开窗口
		var task =  {
						url : url+"&tradeName="+encodeURI(encodeURI(rowData.tradeDesc))+"&bizNo="+rowData.currentBizNo+"&butxn_tranOrgNo="+rowData.tranOrgNo+"&butxn_tradeNo="+rowData.tradeNo,
						name : rowData.tradeDesc,
						txnNo : rowData.txnNo,
						tradeNo : rowData.tradeNo,
						taskFlag : rowData.stepNo || '',
						currentBizNo : rowData.currentBizNo,
						hasLeft : "N" //$xcp.task.isUpdateData(rowData.stepNo||"1") ? "Y" : "N"
					};
					
		$xcp.formPubMgr.openTrade(task);
	}
	$(function(){
		$('#finishedTaskTable').datagrid({
			loadMsg : $xcp.i18n("sys.loadMsg"),
			singleSelect: true,
			title: '',//$xcp.i18n("pendingTask.finishTask"),
			url:$xcp.def.getFullUrl('./pendingTask.do?method=doneList'),
			width: "900",
			height: "auto",
			fit   : true,
			fitColumns: true,
			pageSize: 10,            //每页显示的记录条数，默认为10  
		    pageList: [5,10,15,20],  //可以设置每页记录条数的列表  
			columns:[[
					/* 	{title : '业务流水号', field : 'txnNo', width : 100, align : 'center'},
						{title : '交易名称', field : 'tradeDesc', width : 100, align : 'center'},
						{title : '执行机构编码', field : 'tranOrgName', width : 100, align : 'center'},
						{title : '任务状态', field : 'tranStateDesc', width : 100, align : 'center'},
						{title : '创建时间', field : 'crtDate', width : 100, align : 'center'},
						{title : '业务编号', field : 'currentBizNo', width : 100, align : 'center'},
						{title : '币种', field : 'tranCur', width : 100, align : 'center'},
						{title : '金额', field : 'tranAmt', width : 100, align : 'center'}, */
						
						{title : $xcp.i18n("pendingTask.bizNo"), field : 'currentBizNo', width : 100, align : 'center'},
			          	{title : $xcp.i18n("pendingTask.tradeName"), field : 'tradeDesc', width : 100, align : 'center'},
			          	{title : $xcp.i18n("pendingTask.tranStateDesc"), field : 'tranStateDesc', width : 100, align : 'center'},
			          	{title : $xcp.i18n("pendingTask.tranOrgName"), field : 'tranOrgName', width : 100, align : 'center'},
			          	{title : $xcp.i18n("pendingTask.tranCur"), field : 'tranCur', width : 100, align : 'center'},
						{title : $xcp.i18n("pendingTask.tranAmt"), field : 'tranAmt', width : 100, align : 'center'},
						{title : $xcp.i18n("pendingTask.txnNo"), field : 'txnNo', width : 100, align : 'center'},
						{title : $xcp.i18n("pendingTask.createTime"), field : 'crtDate', width : 100, align : 'center'},
						
						
						{title : '', field : 'state', width : 100, align : 'center',hidden:true},
						{title : '', field : 'stepNo', width : 100, align : 'center',hidden:true},
						{title : '处理名称', field : 'tradeName', width : 100, align : 'center',hidden:true},
						{title : '', field : 'tranState', width : 100, align : 'center',hidden:true},
						{title : '交易编号', field : 'tradeNo', width : 100, align : 'center',hidden : true},
						
						{title : '', field : 'taskState', width : 100, align : 'center',hidden : true},
						{title : 'mvc', field : 'mvc', width : 100, align : 'center',hidden : true}
					]],
			pagination:true,
			rownumbers:true,
			onDblClickRow: function(rowIndex, rowData){
				addWindow(rowData);
			}
		});
		$xcp.stopPorcess();//关闭进度条窗口
	});

</script>
</HEAD>
<BODY style="background: #FFFFFF"  class="easyui-layout" border=false>
	<div region="center" border="false" style="background-color: #FAFAF9;">
		<table id="finishedTaskTable" style="width:100%">
		</table>
	</div>
</BODY>
</HTML>