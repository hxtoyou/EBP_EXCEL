var paramMarComboxKey = {} ,queryJs = {} , qrParams =  {};

//待迁移数据
var prtNameCountList = "";
var busmtcfgInfo = "";
var machineName = "";
var credTypeList = top.window.credTypeList;
var sysPrint = {};

//批量打印开关
//var printFlag =false;

/**
 * 数据关联的cardType 如果有关联打印机则显示是 鼠标移上去显示打印机名称
 * @param value  字段值
 * @param rowData 行数据
 * @returns {String}
 */
queryJs.formatterPrintLook = function(value,rowData){
	var str = $xcp.i18n("chrMgr.n"),str1 = "";
	$.each(top.window.busmtcfgInfo,function(i,k){
		if(rowData.cardType == k.cardType){
			str = $xcp.i18n("chrMgr.y");
			str1 = k.printer;
			return false;
		}
	}); 
	rowData.printerName = str1;
	return "<div title='"+str1+"' style='width:100%'>"+str+"</div>";
};
queryJs.formatterCardType = function(value,rowData){
	var str = "";
	$.each(credTypeList,function(i,k){
		if(value == k.value){
			str = k.name;
			return false;
		}
	}); 
	return str;
};

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


queryJs.cols = [[
                 {
                	 "title": "","field": "printCheck","width":"180","sortable":false,"checkbox":true,"align":"center"
                 }
                 ,{	//业务编号
					 "title": $xcp.i18n("print.bizNo"),"field": "bizNo","width":"150","sortable":true,"align":"center"
				}
				/*,{//文件名
					 "title": "文件名","field": "filepath","width":"230","sortable":true,"align":"center"
				}*/
				,{//打印类型
					 "title": $xcp.i18n("print.tplName"),"field": "cardtype","width":"100","sortable":true,"align":"center"
						 ,formatter: queryJs.formattertplname
				}
				,{//币种 
					 "title": $xcp.i18n("pendingTask.tranCur"),"field": "trancur","width":"100","sortable":true,"align":"center"
						
				}
				,{//金额 
					 "title": $xcp.i18n("pendingTask.tranAmt"),"field": "tranamt","width":"100","sortable":true,"align":"right"
						 ,formatter: function(value, rowData){
								/** author:wangxinlin 金额字段进行格式化，并且居右对齐 */
								if( value && rowData && rowData.trancur ){
									return $xcp.def.formatterAmt(value, rowData.trancur);
								}
								return value;
							}
				}
				,{//客户
					 "title": $xcp.i18n("print.cust"),"field": "custName","width":"200","sortable":true,"align":"center"
				}
				,{//日期
					 "title": $xcp.i18n("print.date"),"field": "operdate","width":"80","sortable":true,"align":"center"
				}
				,{//流水号
					 "title": $xcp.i18n("print.txnNo"),"field": "reftxnno","width":"130","sortable":true,"align":"center"
				}
				,{//小版本号
					 "title": $xcp.i18n("print.version"),"field": "versionno","width":"80","sortable":true,"align":"center"
				}
				,{//打印机构
					 "title": $xcp.i18n("print.prtOrg"),"field": "orgname","width":"100","sortable":true,"align":"center"
				}
				,{//是否自动打印
					 "title": $xcp.i18n("printSys.autoPrint"),"field": "isvalid","width":"80","sortable":true,"align":"center"
				} 
                 /*,{
					"title": $xcp.i18n("print.txnNo"),"field": "ptTxnNo","width":"180","sortable":true,"align":"center"
                 }
                 ,{
                	"title": $xcp.i18n("print.tplType"),"field": "cardType","width":"130","sortable":true,"align":"center"
                	,formatter:queryJs.formatterCardType
                 }
                 ,{
                	"title": $xcp.i18n("print.org"),"field": "orgName","width":"130","sortable":true,"align":"center"
                 }
                 ,{
                    "title": $xcp.i18n("print.date"),"field": "operDate","width":"120","sortable":true,"align":"center"//,formatter
                 }
                 ,{
                    "title": $xcp.i18n("print.count"),"field": "printCnt","width":"60","sortable":true,"align":"center"//,formatter
                 }
                 ,{
                    "title": $xcp.i18n("print.version"),"field": "versionNo","width":"60","sortable":true,"align":"center"//,formatter
                 }
                 ,{
                	"title": $xcp.i18n("print.bound"),"field": "printLook","width":"80","sortable":true,
                	formatter:queryJs.formatterPrintLook,"align":"center"
                 }*/
              ]];
var userId = $xcp.getConstant('user')[0].userId;
queryJs.url = "./printSys.do?method=queryList&userId="+userId;

queryJs.dblClickTrue = function(rowData){
	/*//临时解决方案
	$xcp.ajax({
		url:$xcp.def.getFullUrl("birtConsole.do?method=prePDF&fileName="+rowData.filePath)
	},null,null,function(rs,result){
		top.window.$("body").xcpApp("createPopWindow",{
			title:"文件预览",
			src : rs.filePath
		});
	});*/
	
	top.window.$("body").xcpApp("createPopWindow",{
		title: $xcp.i18n("print.preview"),
		src : $xcp.def.getFullUrl("birtConsole.do?method=prePDF&fileName="+rowData.filepath + "&operDate=" + rowData.operdate.substring(0,10) + "&cardType="+rowData.cardType)
	});
};

queryJs.params = {
		orgNo 	   : "orgNo",
		cardType    : "cardType"
}; 

var queryJsForPrinted = {}, qrParamsForPrinted =  {};

queryJsForPrinted.cols = [[
				{
					 "title": "","field": "printCheck","width":"180","sortable":false,"checkbox":true,"align":"center"
				}
				,{	//业务编号
					 "title": $xcp.i18n("print.bizNo"),"field": "bizNo","width":"150","sortable":true,"align":"center"
				}
				/*,{//文件名
					 "title": "文件名","field": "filepath","width":"230","sortable":true,"align":"center"
				}*/
				,{//打印类型
					 "title": $xcp.i18n("print.tplName"),"field": "cnName","width":"100","sortable":true,"align":"center"
						 ,formatter: queryJs.formattertplname
				}
				,{//币种 
					 "title": $xcp.i18n("pendingTask.tranCur"),"field": "trancur","width":"100","sortable":true,"align":"center"
				}
				,{//金额 
					 "title": $xcp.i18n("pendingTask.tranAmt"),"field": "tranamt","width":"100","sortable":true,"align":"right"
						 ,formatter: function(value, rowData){
								/** author:wangxinlin 金额字段进行格式化，并且居右对齐 */
								if( value && rowData && rowData.trancur ){
									return $xcp.def.formatterAmt(value, rowData.trancur);
								}
								return value;
							}
				}
				,{//客户
					 "title": $xcp.i18n("print.cust"),"field": "custName","width":"200","sortable":true,"align":"center"
				}
				,{//日期
					 "title": $xcp.i18n("print.date"),"field": "operdate","width":"80","sortable":true,"align":"center"
				}
				,{//流水号
					 "title": $xcp.i18n("print.txnNo"),"field": "reftxnno","width":"130","sortable":true,"align":"center"
				}
				,{//小版本号
					 "title": $xcp.i18n("print.version"),"field": "versionno","width":"80","sortable":true,"align":"center"
				}
				,{//打印机构
					 "title": $xcp.i18n("print.prtOrg"),"field": "orgname","width":"100","sortable":true,"align":"center"
				}
				,{//是否自动打印
					 "title": $xcp.i18n("printSys.autoPrint"),"field": "isvalid","width":"80","sortable":true,"align":"center"
				}           
				,{//打印次数
					"title": $xcp.i18n("print.count"),"field": "printcnt","width":"80","sortable":true,"align":"center"
				}           
                /*{
					"title": $xcp.i18n("print.txnNo"),"field": "ptTxnNo","width":"180","sortable":true,"align":"center"
				},{
                	"title": $xcp.i18n("print.bizNo"),"field": "refNo","width":"150","sortable":true,"align":"center"
                },{
                	"title": $xcp.i18n("print.tplName"),"field": "cnName","width":"130","sortable":true,"align":"center"
                },{
                	"title": $xcp.i18n("print.org"),"field": "orgName","width":"130","sortable":true,"align":"center"
                },{
                    "title": $xcp.i18n("print.date"),"field": "operDate","width":"130","sortable":true,"align":"center"//,formatter
                },{
                    "title": $xcp.i18n("print.count"),"field": "printCnt","width":"120","sortable":true,"align":"center"//,formatter
                },{
                    "title": $xcp.i18n("print.validIdentifier"),"field": "isValid","width":"120","sortable":true,
                    "align":"center" 
                }*/
              ]];
queryJsForPrinted.url = "./printSys.do?method=queryPrintedList&userId="+userId;

queryJsForPrinted.dblClickTrue = function(rowData){
	/*//临时解决方案
	$xcp.ajax({
		url:$xcp.def.getFullUrl("birtConsole.do?method=prePDF&fileName="+rowData.filePath)
	},null,null,function(rs,result){
		top.window.$("body").xcpApp("createPopWindow",{
			title:"文件预览",
			src : rs.filePath
		});
	});*/
	top.window.$("body").xcpApp("createPopWindow",{
		title: $xcp.i18n("print.preview"),
		src : $xcp.def.getFullUrl("birtConsole.do?method=prePDF&fileName="+rowData.filepath + "&operDate=" + rowData.operdate.substring(0,10) + "&cardType="+rowData.cardType)
	});
};
/**
 *已打印列表
 */
function createPrintedDatagrid(){
	//初始化表格数据
	$("#queryPrintedList_letterTable").datagrid({
		loadMsg : $xcp.i18n('sys.loadMsg'),
		singleSelect: false,
		url: queryJsForPrinted.url,
		columns:queryJsForPrinted.cols,
		rownumbers:true,
		pagination:true,
		pageSize: 10,//每页显示的记录条数，默认为10  
	    pageList: [5,10,15,20],//可以设置每页记录条数的列表  
		onDblClickRow : function(rowIndex, rowData){
			queryJsForPrinted.dblClickTrue(rowData);
		},
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
		     	var queryID = "queryPrintedList";
		     	var allparams = {
		     		title : title,
		     		options : options,
		     		callback : counstDataFuned,
		     		queryID : queryID,
		     		isIcon : true
		     	};
		     	$xcp.comQueryCondMgr.init(allparams);
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
	    queryJsForPrintLog.url = $xcp.def.getFullUrl('./printSys.do?method=printLog&action=query&orgNo='+$("#queryPrintLogList_condition").find("#queryPrintLogList_orgNo").xcpVal() + "&cardType=" + $("#queryPrintLogList_condition").find("#queryPrintLogList_cardNo").xcpVal());

}


/**
 * 生成datagrid
 */
function createNoPrintedDatagrid(){
	//初始化表格数据
	$("#queryPrintList_letterTable").datagrid({
		loadMsg : $xcp.i18n('sys.loadMsg'),
		singleSelect: false,
		url: queryJs.url,
		columns:queryJs.cols,
		rownumbers:true,
		pagination:true,
		pageSize: 10,//每页显示的记录条数，默认为10  
	    pageList: [5,10,15,20],//可以设置每页记录条数的列表  
		onDblClickRow : function(rowIndex, rowData){
			queryJs.dblClickTrue(rowData);
		},
		//queryParams : qrParams,
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
 	var title = " ";
 	var queryID = "queryPrintList";
 	var allparams = {
 		title : title,
 		options : options,
 		callback : counstDataFun,
 		queryID : queryID,
 		isIcon : true
 	};
// 	$xcp.comQueryCondMgr.init(allparams);
}

/**
 * 事件绑定
 */
function bventFunActPrt(){
	//自动打印
	/*$("#autoPrintId").bind("click",function(){
		autoPrintProcess();
	});*/
	 
	//打印机设置
	/*$("#printerSettingId").bind("click",function(){
		careaPrinterSettingDialog();
	});*/
	
	//待打列表打印按钮注册事件
	$("#linkButLskPrint").bind("click.fixed", function(){
		batchPrint($xcp.plistC);
	});
	//待打列表停止按钮注册事件
	$("#linkButStopPrint").bind("click.stop", function(){
		stopPrint($xcp.plistC);
	});
	
	//已打列表打印按钮注册事件
	$("#linkButLskPrint_alr").bind("click", function(){
		batchPrint($xcp.alrplistC);
	});
	//已打列表停止按钮注册事件
	$("#linkButStopPrint_alr").bind("click", function(){
		stopPrint($xcp.alrplistC);
	});
	
	/*$('#printTabs').tabs({
		border:false,
		onSelect:function(title, index){
			var current_tab = $('#printTabs').tabs('getSelected');
			var options_tab = $('#printTabs').tabs('options');
			$('#printTabs').tabs('update',{tab:current_tab, options:options_tab});
			
			$('#queryPrintList_morecon').hide();
			$('#queryPrintedList_morecon').hide();
			$('#queryPrintLogList_morecon').hide();
			if(index == '0' && !$xcp.plistC){
				createNoPrintedDatagrid();
				$xcp.plistC = new PrintC("queryPrintList_letterTable");	//待打印列表的打印控制对像初始
				$xcp.plistC.setTabname("pttasktp");
			}else if(index == '1' && !$xcp.alrplistC){
				createPrintedDatagrid();
				$xcp.alrplistC = new PrintC("queryPrintedList_letterTable"); //已打印列表的打印控制对像初始
				$xcp.alrplistC.setTabname("pttaskar");
			}else if (index == '2' && logFlag){
				printLog();
				$('#queryPrintLogList_searchBtn').bind('click', function(){
					printLog();
				});
			}
		}
	});*/
	
	createNoPrintedDatagrid();
	$xcp.plistC = new PrintC("queryPrintList_letterTable");	//待打印列表的打印控制对像初始
	$xcp.plistC.setTabname("pttasktp");
}

/**
 * 未打印赋值处理
 */
function counstDataFun(){
	sysPrint.drigName = "packetsFrmDialog";
	
	
	$("#queryPrintList_condition").find("#queryPrintList_cardType").combobox({
		valueField:'value',
		textField:'name',
		data:credTypeList,
		panelHeight:100
	});
	
	$("#queryPrintList_condition").find("#queryPrintList_orgNo").combobox({
		data : $xcp.getConstant("tOrgEntire"),
		textField : "orgName",
		valueField : "orgNo",
		panelHeight : 200
	});
}
/**
 * 打印赋值处理
 */
function counstDataFuned(){
	sysPrint.drigName = "packetsFrmDialog";
	
	$("#queryPrintedList_condition").find("#queryPrintedList_cardNo").combobox({
		valueField:'value',
		textField:'name',
		data:credTypeList,
		panelHeight:100
	});
	
	$("#queryPrintedList_condition").find("#queryPrintedList_orgNo").combobox({
		data : $xcp.getConstant("tOrgEntire"),
		textField : "orgName",
		valueField : "orgNo",
		panelHeight : 200
	});
}

function logDataFuned(){
sysPrint.drigName = "packetsFrmDialog";
	
	$("#queryPrintLogList_condition").find("#queryPrintLogList_cardNo").combobox({
		valueField:'value',
		textField:'name',
		data:credTypeList,
		panelHeight:100
	});
	
	$("#queryPrintLogList_condition").find("#queryPrintLogList_orgNo").combobox({
		data : $xcp.getConstant("tOrgEntire"),
		textField : "orgName",
		valueField : "orgNo",
		panelHeight : 200
	});
}
/**
 * 自动打印 //TODO  paramList datagrid
 */ 
function autoPrintProcess(onlyCheck){
	var	data =   jQuery.extend([],$("#queryPrintList_letterTable").datagrid('getData').rows);
	if(onlyCheck){
		data = new Array();
		$.each($("#queryPrintList_letterTable").datagrid('getSelections'), function(i,k){
			data.push(k);
		});
		if(data.length == 0){
			$.messager.alert($xcp.i18n("paramMar.tisxx"),$xcp.i18n("print.selectRecord"));
			return;
		}
	}
	var str = doPrint(data);
	if(str && "done" == str){
//		$("#queryPrintList_letterTable").datagrid('reload');
	}
	
}

//批量打印
function batchPrintProcess(ptC){
	var data = ptC.getData();
	var str = doPrint(data, ptC);
	if (str && str=="done"){
		resetPrintstate(ptC.getIds());
		ptC.clearIds();
		ptC.reloadGrid();
	}
}

//打印方法  data打印数据      ptC打印控制台,为了避免并发打印
function doPrint(data, ptC){
	if(data == null || data.length==0){
		return "done";
	}
	if( ptC && !ptC.getIds() ){
		top.$xcp.batchPrintMgr.stopPrint();
		top.$xcp.batchPrintMgr.clear();
		return "done";
	}
	if (ptC){ 
		ptC.cutIdCount();
		if (ptC.getIdCount()<=0){ 
			resetPrintstate(ptC.getIds());
			ptC.clearIds();
			//ptC.reloadGrid();
		}
	}
	var o = data.shift();
	top.$xcp.batchPrintMgr.clear();
	var msg = "";
	var isaddcount = false;
	if (ptC && ptC.getTabname().toLowerCase()=="pttaskar"){
		isaddcount = true;
	}
	
	function cb(k, status){
		if(status){
			$.messager.alert($xcp.i18n("paramMar.tisxx"), $xcp.i18n("print.failure") + ":" +$xcp.i18n("print.txnNo") + "=" + k.ptTxnNo + ",status=" + status);
			doPrint(data, ptC);
			return ;
		}
		
		if (isaddcount){
			//如果是已打列表,需要数据打印次数加1
			dateNumber(k.ptTxnNo);
			doPrint(data, ptC);
		}else{
			//如果是待打列表,需要处理数据迁移
			var stut = dateShift(k.ptTxnNo);
			if(stut == undefined){
				$.messager.alert($xcp.i18n("paramMar.tisxx"), $xcp.i18n("print.failure"));
				//top.$xcp.batchPrintMgr.stopPrint();
				doPrint(data, ptC);
				return ;
			}else{
				//var nt = $("#queryPrintList_letterTable").datagrid("getRowIndex",k);
				//$("#queryPrintList_letterTable").datagrid('deleteRow',nt);
				doPrint(data, ptC);
			}
			msg += k.filePath + " is process \n";
		}
	}
	function fcb(nt3){
		if (msg)
		$.messager.alert($xcp.i18n("paramMar.tisxx"),msg);
		msg = "";
		if (ptC){
			if (ptC.getIdCount()<=0){ 
				ptC.reloadGrid();
			}
		}
	}
	top.$xcp.batchPrintMgr.add([o],cb,fcb);
}

/**
 * 数据迁移
 */
function dateShift(ptTxnNo){
	return $xcp.ajax({
		   url   : $xcp.def.getFullUrl("printSys.do?method=dataShift"),
		   data : {"ptTxnNo":ptTxnNo}
	});
}
/*function careaPrinterSettingDialog(){
	var str = sysPrint.drigName;
	var tabHtml =  '<table >';
	$.each(credTypeList,function(i,k){
		tabHtml += '<tr><td width="24%" style="text-align: right">'+k.name+':</td>';
		tabHtml += '<td><input class="easyui-combobox" id="'+k.id+'" ></input></td></tr>';
	});
		tabHtml += '</table>';
	
	var html = 	'<div id="'+str+'" class="easyui-dialog" style="width:350px;height:250px;padding:10px">';
		html += 	tabHtml;
		html += '	<div id="dlg-buttons">';
		html += 		'<a  class="easyui-linkbutton  pubDialogSaveBtn" data-options="plain:true">'+$xcp.i18n('app.confirm')+'</a>';
		html += 		'<a id="frm-DelClose" class="easyui-linkbutton pubDialogCloseBtn" data-options="plain:true">'+$xcp.i18n('app.cancel')+'</a>';
		html += 	'</div>';
		html += '</div>';
	
	$(html).appendTo('body');
	$.parser.parse($("#"+str));
	
	$('#'+str).dialog({
		title   : $xcp.i18n("print.setting"),
		modal   : true,
		buttons : '#dlg-buttons',
		onClose     : function(){
			$('#'+str).dialog('destroy');
		}
	});
	//新增弹出层 确定按钮执行bind方法
	$('#'+str).find('a.pubDialogSaveBtn').bind('click',function(){
		savaPrintMachSite(str);
	});
	 
	$('#'+str).find('a.pubDialogCloseBtn').bind('click',function(){
		$('#'+str).dialog('destroy');
	});
	
	$.each(credTypeList,function(i,k){
		$('#'+k.id).combobox({
			valueField:'value',
			textField:'text',
			data:prtNameCountList
		});
	});
	//设置机器对应打印机信息
	sitePrintMach();	
}*/


/**打印列表控制帮助方法*/
function PrintC(tabid){
	var _tabid = tabid;
	var _tabname = "";
	var ids = "";
	var data = [];
	var pt = "";
	var idcount = 0;
	//设置打印表名
	function setTabname(tabname){
		_tabname = tabname;
	}
	//取得打印表名
	function getTabname(){
		return _tabname;
	}
	//记录选中打印id串
	function loadIds(){
		data = [];
		ids = "";
		$.each($("#"+_tabid+"").datagrid('getSelections'), function(i,k){
			data.push(k);
			ids = ids+pt+k.ptTxnNo; pt = ',';
			idcount ++;
		});
		pt = "";
	};
	//获取选中的data
	function getData(){
		return data;
	}
	//获取记录的选中打印id串
	function getIds(){
		return ids;
	};
	//清除id串记录
	function clearIds(){
		ids = "";
		idcount = 0;
	}
	//记数减少
	function cutIdCount(){
		idcount--;
		if (idcount <0){
			clearIds();
		}
	}
	function getIdCount(){
		return idcount;
	}
	
	//重新加载表格
	function reloadGrid(){
		$("#"+_tabid+"").datagrid('reload');
	}
	
	function reloadPrintC(){
		ids = "";
		data = [];
		idcount = 0;
	}
	
	return {
		loadIds : function(){loadIds();},
		getIds : function(){return getIds();},
		clearIds : function(){clearIds();},
		cutIdCount : function(){cutIdCount();},
		getIdCount : function(){return getIdCount();},
		setTabname : function(tabname){setTabname(tabname);},
		getTabname : function(){return getTabname();},
		getData : function(){return getData();},
		reloadGrid : function(){reloadGrid();},
		reloadPrintC : function(){reloadPrintC();}
	};
}



//启动打印
function batchPrint(ptC){
	//查看是否在PrintC对像中记录了正在处理的id串,如果有表示已经启动了打印
	if ( ptC.getIds() ){
		$.messager.alert($xcp.i18n("sys.tip"),$xcp.i18n("printSys.printing"));
		return;
	}
	
	//将列表选中的Id串记入到PrintC中,打印完成和停止需要这个id串做空闲处理
	ptC.loadIds();
	var ids = ptC.getIds();
	//如果没有ID串,提示选择记录
	if (!ids){
		$.messager.alert($xcp.i18n("sys.tip"),$xcp.i18n("print.selectRecord"));
		return;
	}
	
	//查看选中的处理记录是否有被占用(待打列表查tp,已打列表查ar)
	var tabname = ptC.getTabname();
	var optAjax = {
			url:$xcp.def.getFullUrl('./printConsole.do?method=printC'),
			data :{"action":"req", "tab":tabname, "ids": ids}
	};

	$xcp.ajax(optAjax,null,null,function(rs,result){
		if( rs.length > 0 ){
			$.messager.alert($xcp.i18n("sys.tip"),$xcp.i18n("printSys.printoccup"));
			//启动打印不成功,重置打印控制对象
			ptC.reloadPrintC();
			return ;
		}
	
		//开启打印控制全局变量
//		printFlag = true;
		batchPrintProcess(ptC);
	});
	
}

//停止打印
function stopPrint(ptC){
	var ids = ptC.getIds();
	if(ids){
		$.messager.confirm($xcp.i18n("sys.tip"), $xcp.i18n("printSys.stopOk"), function(r){
			 if (r){
//				 printFlag = false;
				 resetPrintstate(ids);
				 //ptC.clearIds();
				 ptC.reloadPrintC();
			 }
		});
	}else{
		$.messager.alert($xcp.i18n("sys.tip"), "没有打印开启");
	}
}

//重置打印状态为空闲
function resetPrintstate(ids){
	//重置打印任务状态
	var optAjax = {
			url:$xcp.def.getFullUrl('./printConsole.do?method=printC'),
			data :{"ids": ids, "action":"reset"}
	};
	$xcp.ajax(optAjax,null,null,function(rs,result){
		//打印完成
//		$.messager.alert("提示窗口","打印完成");
		return;
	});
}

//打印次数加
function dateNumber(ptTxnNo){
	return $xcp.ajax({
		url : $xcp.def.getFullUrl("printConsole.do?method=printC"),
		data : {"action":"addnum", "ptTxnNo":ptTxnNo}
	});
}


$(function(){
	//counstDataFun();  		   //赋初始值
//	getLocalPrinterParams();   //获取本地机器的打印机参数
//	createNoPrintedDatagrid(); //创建未打印的列表
//	printLog();					//打印日志
//	createPrintedDatagrid();    //创建已打印列表
	bventFunActPrt(); 		   // 绑定事件
	$xcp.stopPorcess();	       //关闭进度条窗口
});

