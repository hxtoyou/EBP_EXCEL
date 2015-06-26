//任务指派
taskAssion = function($){
	//初始化
	function init() {
		loadOnlineUsers();
		
		var queryID = "taskAssign";
		//初始化
		var options = [
			     	      {id:'orgNo',eleType:'easyui-combobox',dataType:'0',disName:$xcp.i18n('butxn.orgName'),i18n:'butxn.orgName',isMainCondition:true},
			     	      {id:'tradeNo',eleType:'easyui-combobox',dataType:'0',disName:$xcp.i18n('pendingTask.tradeName'),i18n:'pendingTask.tradeName',isMainCondition:true}
			     	   ];
		var title = $xcp.i18n("taskAssign.title");
		
		var allparams = {title : title,options : options, callback:initQueryCondition,queryID:queryID};
		$xcp.comQueryCondMgr.init(allparams);
	};
	//初始化查询条件
	function initQueryCondition() {
		$("#taskAssign_orgNo").combobox({
			data : $xcp.getConstant("belongOrg"),
			textField : "name",
			valueField : "val",
			panelHeight : 300
		});
		
		$("#taskAssign_tradeNo").combobox({
			data : $xcp.getConstant("tradeNo"),
			textField : "tradeName",
			valueField : "tradeNo",
			panelHeight : 100
		});
	};
	
	//加载在线用户数据
	function loadOnlineUsers(){
		$('#taskAssign_letterTable').datagrid({
			loadMsg : $xcp.i18n("sys.loadMsg"),
			singleSelect: true,
			url:$xcp.def.getFullUrl('./taskAssign.do?method=queryTaskList'),
			width: "900",
			height: "auto",
			fit   : true,
			fitColumns: true,
			pageSize: 10,            //每页显示的记录条数，默认为10  
		    pageList: [5,10,15,20],  //可以设置每页记录条数的列表  
		    columns:[[	
		              	//执行机构
			          	{title : $xcp.i18n("taskAssign.tranOrgName"), field : 'orgName', width : 100, align : 'center',"sortable" : true},
			          	//业务流水号
			          	{title : $xcp.i18n("taskAssign.txnNo"), field : 'txnNo', width : 100, align : 'center',"sortable" : true},
			          	//交易名称
			          	{title : $xcp.i18n("taskAssign.tradeName"), field : 'tradeDesc', width : 100, align : 'center',"sortable" : true},
			          	//操作步骤
			          	{title : $xcp.i18n("taskAssign.stepName"), field : 'stepName', width : 100, align : 'center',"sortable" : true},
			          	//创建时间
						{title : $xcp.i18n("taskAssign.createTime"), field : 'crtDate', width : 100, align : 'center',"sortable" : true},
						//指定处理人
			          	{title : $xcp.i18n("taskAssign.fixOpName"), field : 'fixOpName', width : 100, align : 'center',"sortable" : true},
			          	
						{title : '实际处理人编号', field : 'actOp', width : 100, align : 'center',hidden:true},
						{title : '实际处理人名称', field : 'actOpName', width : 100, align : 'center',hidden:true},
						{title : '执行机构编号', field : 'orgNo', width : 100, align : 'center',hidden:true},
						{title : '交易编号', field : 'tradeNo', width : 100, align : 'center',hidden:true},
						{title : '操作步骤号', field : 'stepNo', width : 100, align : 'center',hidden:true},
						{title : '指派处理人', field : 'dspOpName', width : 100, align : 'center',hidden:true},
						{title : '指派处理人编号', field : 'dspOpNo', width : 100, align : 'center',hidden:true},
						{title : '指定处理人编号', field : 'fixOp', width : 100, align : 'center',hidden:true}
					]],
			pagination:true,
			rownumbers:true,
			remoteSort:true,
			sortName:"crtDate",
		    sortOrder :"desc",
			onDblClickRow: function(rowIndex, rowData){
				showWindow(rowData);
			},
			onRowContextMenu : function(e, rowIndex, rowData){
				//onRowAddAnnotation(e, rowIndex, rowData);
			}
		});
		
		var wh = $(window).height();
	    wh = wh > 500 ? wh -100 : (400);//lsm3
	    var tanCk = "任务指派";

		$("#edintFromWindow").window({
			width:600,
			height: wh,
			minimizable : false,
			collapsible : false,
			title : tanCk,
			modal : false,
			shadow: false,
			closed :true
		}).show();
		
		$("#linkButLsdBreak").bind("click",function(){
			refreshFromData();
		});
		
		trdDataEvent();
	};
	
	function showWindow(rowData)
	{
		$("#edintFromWindow").window("open"); 
		$("#edintFromWindowForm").form('load',rowData);
		var tradeNo = rowData.tradeNo;
		var orgNo = rowData.orgNo;
		var stepNo = rowData.stepNo;
		//查询用户信息
		queryUserByTask(tradeNo,orgNo,stepNo);
	}
	function queryUserByTask(tradeNo,orgNo,stepNo)
	{
		$.ajax({
			type : "POST",
			url  : $xcp.def.getFullUrl("taskAssign.do?method=queryUserByTask"),
			data : {
				tradeNo: tradeNo,
				orgNo:orgNo,
				stepNo:stepNo
			},
			dataType : "json",
			async : false,
			success  : function(result) {
				if(result.success =='1'){
					$xcp.dispAjaxError($xcp.cloneObject(result));
					result = [];
				}else{
					if(typeof result  == "object" ){
						$('#fixOp').combobox("loadData",result.outEntity);
					}  
				}
			},
			beforeSend : function(XMLHttpRequest, textStatus){
				
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){
				alert(errorThrown );
			}
		});
	}
	
	var user = $xcp.getConstant("user")[0];
	//收报查询
	function taskQuery()
	{
//		var bol = $("#" + $xcp.formPubMgr.defaults.currFormId).form('validate'); 
//		alert(JSON.stringify(bol));
		qrParams = $("#taskTab").datagrid('options').queryParams;	
		qrParams.orgNo = $("#queryOrgNo").xcpVal();
		qrParams.tradeNo = $("#queryTradeNo").xcpVal();
		
		$("#taskTab").datagrid('load');
	}
	//清空查询
	function clearParam()
	{
		$("#paramForm").find("input[type!='radio'][type!='checkbox'],textarea,select").attr("value","");
	}
		

	//关闭窗口
	function hidShow(stat){
			if(stat != "" && stat != null){
			}else{
				$("#edintFromWindow").window("close"); 
			}
	}

	function refreshFromData(){
		hidShow();
		$("#edintFromWindowForm").find("input[id!='closparamId'][type!='radio'][type!='checkbox'],textarea,select").attr("value","");
	}

	//cobobox 对象数据 end
	function trdDataEvent(){
		$("#linkButLsdSave").bind("click",function(){
			$.ajax({
				type : "POST",
				url  : $xcp.def.getFullUrl("taskAssign.do?method=saveTaskAssign"),
				data : {
					taskId: $("#taskId").val(),
					fixOp : $("#fixOp").xcpVal(),
					userId: user.userId
				},
				dataType : "json",
				async : false,
				success  : function(result) {
					if(result.success =='1'){
						$xcp.dispAjaxError($xcp.cloneObject(result));
						result = [];
					}else{
						if(typeof result  == "object" ){
							hidShow();
							$("#taskTab").datagrid("load");
						}  
					}
				},
				beforeSend : function(XMLHttpRequest, textStatus){
					
				},
				error : function(XMLHttpRequest, textStatus, errorThrown){
					alert(errorThrown);
				}
			});
		});
	};
	
	return{
		init:init
	};
}(jQuery);



$(function(){
	//页面初始化
	taskAssion.init();
	//关闭进度条窗口
	$xcp.stopPorcess();
});

/*
 */