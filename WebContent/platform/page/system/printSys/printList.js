var queryJs = {};
queryJs.cols = [ {
	"text" : "打印流水号",
	"name" : "reftxnno",
	"width" : 200,
	"keyClos" :true,
	"sortable" : true
}, {
	"text" : "交易步骤",
	"name" : "stepno",
	"width" : 200,
	"sortable" : true
}, {
	"text" : "版本号",
	"name" : "versionno",
	"width" : 200,
	"sortable" : true
}, {
	"text" : "模板名称",
	"name" : "cardname",
	"width" : 200,
	"sortable" : true
}];
queryJs.url = "./printSys.do?method=queryByStep&stepNo=''";
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
	alert(1);
});