var paramMarComboxKey = {} ,queryJs = {} , qrParams =  {};

//待迁移数据
var credTypeList = top.window.credTypeList;

/**
 * 数据关联的cardType 如果有关联打印机则显示是 鼠标移上去显示打印机名称
 * @param value  字段值
 * @param rowData 行数据
 * @returns {String}
 */
 
queryJs.formattertplname = function(value, rowData){
	var str = "";
	switch(value){
		case '1' : str = $xcp.i18n("setting.receipt");
			break;
		case '2' : str = $xcp.i18n("setting.declaration");
			break;
		case '3' : str = $xcp.i18n("setting.packet");
			break;
		case '4' : str = $xcp.i18n("setting.schedule");
			break;
		default : str = rowData.cnName;
	}
	return str;
};

queryJs.formatErrorInfo = function(value, rowData){
	var str = "";
	switch(value){
		case 'print.notFound' : str = $xcp.i18n("print.notFound");
			break;
		case 'print.canceled' : str = $xcp.i18n("print.canceled");
			break;
		case 'print.complete' : str = $xcp.i18n("print.complete");
			break;
		case 'print.external' : str = $xcp.i18n("print.external");
			break;
		case 'print.failure'  : str = $xcp.i18n("print.failure");
			break;
		case 'print.notBeCompleted'  : str = $xcp.i18n("print.notBeCompleted");
			break;
		case 'print.orCompleted'  : str = $xcp.i18n("print.orCompleted");
			break;
		case 'print.printed'  : str = $xcp.i18n("print.printed");
			break;
		default : str = value;
	}
	return str;
};

var logFlag = true;
function printLog(){
	initLogQuery();
	//初始化表格数据
	$("#queryPrintLogList_letterTable").datagrid({
		loadMsg : $xcp.i18n('sys.loadMsg'),
		singleSelect: true,
		url: queryJsForPrintLog.url,
		columns:queryJsForPrintLog.cols,
		rownumbers:true,
		pagination:true,
		pageSize: 10,//每页显示的记录条数，默认为10  
	    pageList: [5,10,15,20],//可以设置每页记录条数的列表  
		height: "auto",
		fit   : true,
		fitColumns: false,
		sortOrder : "desc",
		remoteSort : true //是否远程排序
	});
	var options = [
		     	      {id:'orgNo',eleType:'easyui-combobox',dataType:'0',disName:$xcp.i18n('printSys.orgNo'),i18n:'printSys.orgNo',isMainCondition:true},
		     	      {id:'cardType',eleType:'easyui-combobox',dataType:'0',disName:$xcp.i18n('printSys.cardNo'),i18n:'printSys.cardNo',isMainCondition:true}
		     	];
		     	var title = "";
		     	var queryID = "queryPrintLogList";
		     	var allparams = {
		     		title : title,
		     		options : options,
		     		callback : logDataFuned,
		     		queryID : queryID,
		     		isIcon : true
		     	};
		     	if(logFlag){
		     		$xcp.comQueryCondMgr.init(allparams);
		     		logFlag = false;
		     	}
};

var queryJsForPrintLog = {};
function initLogQuery(){
	queryJsForPrintLog.cols = [[
	    						{//打印流水号
	    							"title": "打印流水号","field": "PTTXNNO","width":"200","sortable":true,"align":"center"
	    						},
	                			{//文件名
	                				"title": "文件名","field": "FILEPATH","width":"200","sortable":true,"align":"center"
	                			},
	                			{//打印机名
	                				"title": "打印机名","field": "PRINTSERVICE","width":"200","sortable":true,"align":"center"
	                			},
	                			{//用户名
	                				"title": "打印人员","field": "USERID","width":"100","sortable":true,"align":"center"
	                			},
	                			{//机构
	                				"title": "打印机构","field": "ORGNO","width":"100","sortable":true,"align":"center"
	                			},
	                			{//文件类型
	                				"title": "打印类型","field": "CARDTYPE","width":"80","sortable":true,"align":"center",formatter: queryJs.formattertplname
	                			},
	                			{//时间
	                				"title": "时间","field": "OPERDATE","width":"150","sortable":true,"align":"center"
	                			},
	                			{//打印消息
	                				"title": "日志信息","field": "MESSAGE","width":"400","sortable":true,"align":"center",formatter: queryJs.formatErrorInfo
	                			}
	                             ]];
	    queryJsForPrintLog.url = $xcp.def.getFullUrl('./printSys.do?method=printLog&action=query&orgNo='+$("#queryPrintLogList_orgNo").xcpVal() + "&cardType=" + $("#queryPrintLogList_cardType").xcpVal());
}

/**
 * 事件绑定
 */
function bventFunActPrt(){
	printLog();
}

/**
 * 打印赋值处理
 */

function logDataFuned(){
	
	$("#queryPrintLogList_cardType").combobox({
		valueField:'value',
		textField:'name',
		data:credTypeList,
		panelHeight:100
	});
	
	$("#queryPrintLogList_orgNo").combobox({
		data : $xcp.getConstant("tOrgEntire"),//allOrgList
		textField : "orgName",
		valueField : "orgNo",
		panelHeight : 200
	});
	
	$("#queryPrintLogList_searchBtn").unbind("click").bind("click.logList",function(){
		$("#queryPrintLogList_letterTable").datagrid("options").url = $xcp.def.getFullUrl('./printSys.do?method=printLog&action=query&orgNo='+$("#queryPrintLogList_orgNo").xcpVal() + "&cardType=" + $("#queryPrintLogList_cardType").xcpVal());;
//		$("#queryPrintLogList_letterTable").datagrid("load");
	});
}
$(function(){
	bventFunActPrt(); 		   // 绑定事件
	$xcp.stopPorcess();	       //关闭进度条窗口
});

