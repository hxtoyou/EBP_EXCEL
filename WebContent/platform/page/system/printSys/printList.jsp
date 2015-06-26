<%@page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<script type="text/javascript">
var queryJs = {};
queryJs.cols = [[ {
	"field" : "reftxnno",
	"title" : "打印流水号",
	"width" : 200,
	"sortable" : true
}, {
	"title" : "交易步骤",
	"field" : "stepno",
	"width" : 200,
	"sortable" : true
}, {
	"title" : "版本号",
	"field" : "versionno",
	"width" : 200,
	"sortable" : true
}, {
	"title" : "模板名称",
	"field" : "cardname",
	"width" : 200,
	"sortable" : true
}]];
var txnNo = $("#txnNo").val();
queryJs.url = "./printSys.do?method=queryByStep&txnNo=" + txnNo;
function createDatagr(){
	//初始化表格数据
	$("#paramQueryListTab").datagrid({
		loadMsg : $xcp.i18n('sys.loadMsg'),
		url: queryJs.url,
		columns:queryJs.cols,
		rownumbers:true,
		pagination:true,
		pageSize: 10,//每页显示的记录条数，默认为10  
	    pageList: [5,10,15,20],//可以设置每页记录条数的列表  
		onDblClickRow : function(rowIndex, rowData){
		},
		height: "auto",
		fit   : true,
		fitColumns: true,
		sortOrder : "stepno"
	});
};
$(function(){
	createDatagr();
});
</script>
</head>
<body class="easyui-layout" border="true" fit="true">
	<div region="center" style="height:100%;" data-options="split:true" border="false">
		<div id="paramQueryListTab"></div>
	</div>
</body>
</html>
