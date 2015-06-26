var paramMarComboxKey = {} ,queryJs = {} , qrParams =  {};

//待迁移数据
var prtNameCountList = "";
var busmtcfgInfo = "";
var machineName = "";
var credTypeList = top.window.credTypeList;
var sysPrint = {};

//批量打印开关
//var printFlag =false;
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
queryJs.cols = [[
                 {
                	 "title": "","field": "printCheck","width":"180","sortable":false,"checkbox":true,"align":"center"
                 }
				/*,{//文件名
					 "title": "文件名","field": "filepath","width":"230","sortable":true,"align":"center"
				}*/
				,{//打印类型
					 "title": $xcp.i18n("print.tplName"),"field": "cardtype","width":"100","sortable":true,"align":"center"
						 ,formatter: queryJs.formattertplname
				},{	//业务编号
					 "title": $xcp.i18n("print.bizNo"),"field": "bizNo","width":"150","sortable":true,"align":"center"
				},{//客户
					 "title": $xcp.i18n("print.cust"),"field": "custName","width":"200","sortable":true,"align":"center"
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
              ]];
var userId = $xcp.getConstant('user')[0].userId;
var belongOrgNo = $xcp.getConstant('user')[0].belongOrgNo;
queryJs.url = "./printSys.do?method=queryList&userId="+userId+"&userOrgNo="+belongOrgNo;

queryJs.dblClickTrue = function(rowData){
	top.window.$("body").xcpApp("createPopWindow",{
		title: $xcp.i18n("print.preview"),
		src : $xcp.def.getFullUrl("birtConsole.do?method=prePDF&fileName="+rowData.filepath + "&operDate=" + rowData.operdate.substring(0,10) + "&cardType="+rowData.cardType)
	});
};

queryJs.params = {
		orgNo 	   : "orgNo",
		cardType    : "cardType"
}; 

var logFlag = true;
  
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
	/*var options = [
		     	      {id:'orgNo',eleType:'easyui-combobox',dataType:'0',disName:$xcp.i18n('printSys.orgNo'),i18n:'printSys.orgNo',isMainCondition:true},
		     	      {id:'cardType',eleType:'easyui-combobox',dataType:'0',disName:$xcp.i18n('printSys.cardNo'),i18n:'printSys.cardNo',isMainCondition:true}
		     	  ];*/
	
	var options = [
		     	      {id:'cardType',eleType:'easyui-combobox',dataType:'0',disName:$xcp.i18n('printSys.cardNo'),i18n:'printSys.cardNo',isMainCondition:true},
		     	      {id:'tradeNo',eleType:'easyui-combobox',dataType:'0',disName:$xcp.i18n('pendingTask.tradeName'),i18n:'pendingTask.tradeName',isMainCondition:true},
		     	      {id:'bizNo',eleType:'easyui-validate',dataType:'0',disName:$xcp.i18n('print.bizNo'),i18n:'print.bizNo',isMainCondition:true},
		    	      {id:'custName',eleType:'easyui-searchbox',dataType:'0',disName:$xcp.i18n('print.cust'),i18n:'print.cust',isMainCondition:true},
		    	      {id:'trancur',eleType:'easyui-combobox',dataType:'0',disName:$xcp.i18n('pendingTask.tranCur'),i18n:'pendingTask.tranCur',isMainCondition:true},
		     	      {id:'tranamt',eleType:'easyui-numberbox',dataType:'1',disName:$xcp.i18n('pendingTask.tranAmt'),i18n:'pendingTask.tranAmt',isMainCondition:true},
		     	      {id:'operdate',eleType:'easyui-datebox',dataType:'2',disName:$xcp.i18n('print.date'),i18n:'print.date',isMainCondition:true},
//		     	      {id:'orgNo',eleType:'easyui-combobox',dataType:'0',disName:$xcp.i18n('printSys.orgNo'),i18n:'printSys.orgNo',isMainCondition:true}
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
 	$xcp.comQueryCondMgr.init(allparams);
}

/**
 * 事件绑定
 */
function bventFunActPrt(){
	//待打列表打印按钮注册事件
	$("#linkButLskPrint").bind("click.fixed", function(){
		batchPrint($xcp.plistC);
	});
	//待打列表停止按钮注册事件
	$("#linkButStopPrint").bind("click.stop", function(){
		stopPrint($xcp.plistC);
	});
	
	createNoPrintedDatagrid();
	$xcp.plistC = new PrintC("queryPrintList_letterTable");	//待打印列表的打印控制对像初始
	$xcp.plistC.setTabname("pttasktp");
}

/**
 * 未打印赋值处理
 */
function counstDataFun(){
	sysPrint.drigName = "packetsFrmDialog";
	
	$("#queryPrintList_cardType").combobox({
		valueField:'value',
		textField:'name',
		data:credTypeList,
		panelHeight:100
	});
	
//	$("#queryPrintList_orgNo").combobox({
//		data : $xcp.getConstant("tOrgEntire"),
//		textField : "orgName",
//		valueField : "orgNo",
//		panelHeight : 200
//	});
	
	$('#queryPrintList_trancur').combobox({
		textField : "val",
		valueField : "name",
		data: $xcp.getConstant('cur'),
	    panelHeight : 200
	});
	
	// 绑定搜索框的搜索按钮
	$("#queryPrintList_custName").xcpSrchBox({
		  lxOrFun : 'SRCHBOXPACRP', 
		  callback : function(rowIndex, rowData){
			  custSearchResultDbclick(rowIndex, rowData);
		},params : {
			cnName:"queryPrintList_custName"
	    }
	});
	
	$('#queryPrintList_tradeNo').combobox({
		textField : "name",
		valueField : "val",
		data: $xcp.getConstant('PATRDTYP.BUSSTRADEDESC'),
	    panelHeight : 200
	});
	
	
}

//客户搜索框 的回调函数
function custSearchResultDbclick(rowIndex,rowData){
	var custName = null;
	//中文环境 拿中文名称
	if(EAP_LANGUAGE == 'zh_CN'){
		custName = rowData.pacrp_cnName;
	}else{
		//其他环境拿英文名称
		custName = rowData.pacrp_enName;
	}
	$("#queryPrintList_custName").xcpVal(custName,'change.fixed');
	//客户id
	var custId = rowData.pacrp_corpNo;
	$("#queryPrintList_custId").xcpVal(custId,'change.fixed');
};

$(function(){
	bventFunActPrt(); 		   // 绑定事件
	$xcp.stopPorcess();	       //关闭进度条窗口
});

