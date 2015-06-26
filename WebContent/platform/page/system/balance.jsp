
<%
	String txnNo = request.getParameter("txnNo")==null?"1":request.getParameter("txnNo");
	String balId = request.getParameter("balId")==null?"":request.getParameter("balId");
	String balTable = "balTable" + balId;
%>

<script>

    
	$(function(){
		loadBalData('<%=txnNo%>','<%=balTable%>');
	});
	 
	function loadBalData(txnNo,tblId){
		$('#'+tblId).datagrid({
			loadMsg : $xcp.i18n("sys.loadMsg"),
			singleSelect: true,
			url:$xcp.def.getFullUrl('./commonQuery.do?method=queryBalance&txnNo='+txnNo),
			width: "900",
			height: "auto",
			fit   : true,
			fitColumns: true,
			columns:[[
						
						{title : $xcp.i18n("pendingTask.tranCur"), field : 'cur', width : 100, align : 'center'},
						{title : $xcp.i18n("formPubMgr.Bal"), field : 'amount', width : 100, align : 'center'},
						{title : $xcp.i18n("formPubMgr.BalName"), field : 'fieldname', width : 100, align : 'center'},
						{title : $xcp.i18n("pendingTask.txnNo"), field : 'txnno', width : 150, align : 'center'},
						{title : $xcp.i18n("pendingTask.bizNo"), field : 'bizno', width : 100, align : 'center'}
					]],
			pagination:true,
			rownumbers:true,
			onDblClickRow: function(rowIndex, rowData){
			}
			
		});
		$xcp.leftTreeMgr.addDatagridList(tblId);
	}

</script>

	<table id="<%=balTable%>" style="width:100%">
	</table>
