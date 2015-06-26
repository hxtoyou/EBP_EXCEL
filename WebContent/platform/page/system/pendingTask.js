//需要传递的参数列表
var pendingTaskParam = [ 'txnNo', 'tradeNo', 'taskFlag', 'taskId', 'state','stepNo', 'taskState' ];

$.extend($.fn.pagination.defaults, {  
	onBeforeRefresh : function(_a0, _a1) {
		$('#pendingTask_letterTable').datagrid("options").sortName = "";
		$(".datagrid-sort-asc").removeClass("datagrid-sort-asc");
	} 
}); 

function valitTrand(str){
	var blean = false;
	var url = "./validateData.do?method=checkData" + str;
	$xcp.ajax({
		url : url
	},null,null,function(rs){
		blean = true;
	});
	
	return blean;
}
/**
 * overalParam 是所有交易公用的一个传参对象 应为json格式 现为string 主动更正有用到  判断是否是手工经办带了no值 
 * 其他交易需要使用overalParam参数请开发人员注意
 * @param rowData
 */
function addWindow(rowData) {
	//验证交易是否已经被占用
	var str = "";
	rowData.taskFlag = rowData.stepNo;
	$.each(pendingTaskParam, function(i, param) {
		str += "&" + param + "=" + (rowData[param] || '');
	});
	
	//butxn_preBizNo
	if($xcp.task.isHandle() && valitTrand(str) == false) return ;
	
	url = './' + rowData.tradeName + '.do?method=getEditData';
	url += str + "&queryFlag=query&butxn_launchModeNo="+rowData.launchModeNo+"&overalParam="+(rowData.canAgree == "" ? "no" : rowData.canAgree);
	
	var task =  {
		url : url+"&tradeName="+encodeURI(encodeURI(rowData.tradeDesc))+"&bizNo="+rowData.currentBizNo+"&butxn_tranOrgNo="+rowData.tranOrgNo,
		name : rowData.tradeDesc,
		txnNo : rowData.txnNo,
		tradeNo : rowData.tradeNo,
		taskFlag : rowData.stepNo || '',
		currentBizNo : rowData.currentBizNo,
		taskId : rowData.taskId,
		hasLeft : "N" //$xcp.task.isUpdateData(rowData.stepNo||"1") ? "Y" : "N"
	};
	
	$xcp.formPubMgr.openTrade(task);
}

$(function() {
	//initSearchCondition();

	$('#pendingTask_letterTable').datagrid({
		loadMsg : $xcp.i18n("sys.loadMsg"),
		singleSelect: true,
		title: '',//$xcp.i18n("pendingTask.myTask"),
		url:$xcp.def.getFullUrl('./pendingTask.do?method=query'),
		width: "900",
		height: "auto",
		fit   : true,
		fitColumns: false,
		sortOrder : "desc",
		remoteSort : true, //是否远程排序
		multiSort: true,
		pageSize: 10,            //每页显示的记录条数，默认为10  
	    pageList: [20,50,100],  //可以设置每页记录条数的列表  
		columns:[[	
		          	//执行机构
		          	{title : $xcp.i18n("pendingTask.tranOrgName"), field : 'tranOrgName', width : 190, align : 'center' , sortable : true },
		          	//交易名称
		          	{title : $xcp.i18n("pendingTask.tradeName"), field : 'tradeDesc', width : 100, align : 'center',styler:cellStyler , sortable : true },
		          	//发记方式
		          	{title : $xcp.i18n("pendingTask.launchmodeNo"), field : 'launchModeNo', width : 55, align : 'center' , sortable : true, formatter: function(value, rowData){
		          		return (value && $.isNumeric(value)) ? $xcp.i18n("pendingTask.launchmodeNo."+value) : $xcp.i18n("pendingTask.launchmodeNo.1");
		          	} },
	          		//交易步骤
		          	{title : $xcp.i18n("pendingTask.stepName"), field : 'stepName', width : 100, align : 'center' , sortable : true, formatter: function(value, rowData){
		          		if( value.indexOf('Pending') > -1 && ( rowData.stepNo == '4001' || rowData.stepNo == '4002' || rowData.stepNo == '4003') ){
		          			var stepQueue = $xcp.parseJson(rowData.vars).DoneStepQueue;
		          			var arr = stepQueue.split("@@");
		          			if( stepQueue != "" && arr.length > 0 ){
		          				return value +" "+ (arr.length+1);
		          			}
		          		}
		          		return value;
		          	} },
		          	//客户名称
		          	{title : $xcp.i18n("butxn.custName"), field : 'custName', width : 190, align : 'center' , sortable : true },
		          	//业务编号
		          	{title : $xcp.i18n("pendingTask.bizNo"), field : 'currentBizNo', width : 125, align : 'center' , sortable : true },
		          	//币种
		          	{title : $xcp.i18n("pendingTask.tranCur"), field : 'tranCur', width : 38, align : 'center' , sortable : true },
		          	//金额
		          	{title : $xcp.i18n("pendingTask.tranAmt"), field : 'tranAmt', width : 100, align : 'center' , sortable : true 
		          		 ,formatter: function(value, rowData){
								if( value && rowData && rowData.tranCur ){
									return $xcp.def.formatterAmt(value, rowData.tranCur);
								}
								return value;
							}
		          	},
		          	//经办员
		          	{title : $xcp.i18n("pendingTask.handler"), field : 'handlerCode', width : 70, align : 'center' , sortable : true },
		          	//复核员
		          	{title : $xcp.i18n("pendingTask.checker"), field : 'checkerCode', width : 70, align : 'center' , sortable : true },
		          	//创建日期
		          	{title : $xcp.i18n("pendingTask.createTime"), field : 'crtDate', width : 120, align : 'center' , sortable : true },
		          	//完成日期
					/*{title : $xcp.i18n("pendingTask.completeTime"), field : 'endDate', width : 100, align : 'center' , sortable : true},*/
		          	//交易流水
		          	/*** TASK #230 【浦发】任务列表增加2列显示经办、复核人，可以去掉流水号一列 begin ***/
		          	{title : $xcp.i18n("pendingTask.txnNo"), field : 'txnNo', width : 125, align : 'center' , sortable : true },
		          	/*** TASK #230 【浦发】任务列表增加2列显示经办、复核人，可以去掉流水号一列 end ***/
		         	//备注
					{title : $xcp.i18n("pendingTask.memo"), field : 'memo', width : 360, align : 'center' , sortable : true},

					{title : '任务ID', field : 'taskId', width : 100, align : 'center',hidden:true},
					{title : '', field : 'stepNo', width : 100, align : 'center',hidden:true},
					{title : '处理名称', field : 'tradeName', width : 100, align : 'center',hidden:true},
					{title : '', field : 'state', width : 100, align : 'center',hidden:true},
					{title : '交易编号', field : 'tradeNo', width : 100, align : 'center',hidden : true},
					{title : '', field : 'tranOrgNo', width : 100, align : 'center',hidden : true},
					{title : '', field : 'tskOpinion', width : 100, align : 'center',hidden : true},
					{title : '', field : 'taskState', width : 100, align : 'center',hidden : true},
//					{title : $xcp.i18n("butxn.custName"), field : 'custId', width : 100, align : 'center',hidden : true},
					{title : 'mvc', field : 'mvc', width : 100, align : 'center',hidden : true}
				]],
		pagination:true,
		rownumbers:true,
		rowStyler: function(index,rowData){
//			/*** START-author:yangkai-如果任务申请了经办更正，行标红 ***/
//			if( rowData.canAgree == '1' ){
//				//color:#fff;
//				return 'background-color:red;';
//			}
//			/*** START-author:yangkai-如果任务申请了经办更正，行标红 ***/
		},
		onDblClickRow: function(rowIndex, rowData){
			/** START-author:wangxinlin-如果任务申请了经办更正，提示*/
			if(configOveralObj.formMenu.taskListInit == '1'){
				if( rowData.canAgree == '1' ){
//					$.messager.confirm($xcp.i18n("tip"), $xcp.i18n("sys.taskHit"), function(r){
//					     if(r){ addWindow(rowData); }
//					});
					
					//显示请求更正内容
					var taskId = rowData["taskId"] ;
					var optAjax = {
						url:$xcp.def.getFullUrl("./correctA.do?method=getRequestBackComment") ,
						data:{"taskId":taskId}
					};
					
					$xcp.ajax(optAjax,null,null,function(result){
						//存在申请主动更正的信息则弹窗，由弹窗决定提交方式(同意申请更正(不弹窗的打回)、继续(按照原有提交处理))
						if( null != result && result["isReqBckCmt"] == "true" ){
							/**
							 * desc:申请主动更正的交易打开窗体使用的方法(对于申请主动更正的交易，由于业务，不使用app.js中定义的公共方法，即createDialog)
							 * */
							createDialogForRequestBack({
								title    : $xcp.i18n('formPubMgr.CorrectApplicaComment'),
								taskUuid : uuidHead,
								isReadonly : true,
								inputLen : 200,
								value    : result["reqBckTskComment"] == "null" ? "":result["reqBckTskComment"] ,
								rowData  : rowData
			        		}) ;
			        	}
					});
					
				}else{
					addWindow(rowData);
				}
			}else{
				addWindow(rowData);
			}
			
			/** END-author:wangxinlin-如果任务申请了经办更正，提示*/
		},
		onRowContextMenu : function(e, rowIndex, rowData){
			onRowAddAnnotation(e, rowIndex, rowData);
		},
		onLoadSuccess : function(){
			top.$xcp.toolsMgr.bindBefUnLoad();
		}
	});

//	// 查询按钮绑定事件
//	$('#searchBtn').bind('click', function() {
//		doQuery(param);
//	});
	
	
	//autoLoadData();
	$xcp.stopPorcess();//关闭进度条窗口
	
	/*** 公共查询条件组件 begin ***/
	/**
	* options:	查询条件数组
	* id:	id
	* eleType:	控件类型
	* dataType:	数据类型 0为string 1为金额 2为日期
	* disName:	显示名称
	* isMainCondition:	是否默认显示 若为默认显示 金额会默认显示 金额等于， 日期会默认显示 日期早于 日期晚于 
	* isHaveHidden:	若eleType 为easyui-searchbox 这种 会带有隐藏字段的 需传递为true
	* hiddenId 若isHaveHidden为true 需传递
	*/
	var options = [
	      //执行机构
	      {id:'tranOrgNo',eleType:'easyui-combobox',dataType:'0',disName:$xcp.i18n('butxn.orgName'),i18n:'butxn.orgName',isMainCondition:true},
	      //交易名称
	      {id:'tradeNo',eleType:'easyui-combobox',dataType:'0',disName:$xcp.i18n('butxn.tradeName'),i18n:'butxn.tradeName',isMainCondition:true},
	      //交易步骤 butxn.tranState
	      {id:'stepNo',eleType:'easyui-combobox',dataType:'0',disName:$xcp.i18n("pendingTask.stepName"),i18n:'pendingTask.stepName',isMainCondition:true},
	      //客户名称
	      {id:'custName',eleType:'easyui-searchbox',dataType:'0',disName:$xcp.i18n('butxn.custName'),i18n:'butxn.custName',isHaveHidden:true,hiddenId:'custId',isMainCondition:true},
	      //业务编号
	      {id:'curtBizNo',eleType:'easyui-validatebox',dataType:'0',disName:$xcp.i18n('quickquery.bizNo'),i18n:'quickquery.bizNo',isMainCondition:true},
	      //币种
	      {id:'tranCur',eleType:'easyui-combobox',dataType:'0',disName:$xcp.i18n('qp.curSign'),i18n:'qp.curSign',isMainCondition:true},
	      //金额>=(公共查询组件commQueryCondition.js中对于金额会自动构建金额>=、金额<=、金额=)
	      {id:'tranAmt',eleType:'easyui-numberbox',dataType:'1',disName:$xcp.i18n('commQuery.accountMore'),i18n:"commQuery.accountMore",isMainCondition:true},
	      //开始日期、结束日期
	      {id:'crtDate',eleType:'easyui-datebox',dataType:'2',disName:$xcp.i18n('commQuery.date'),i18n:'commQuery.date',isMainCondition:true},
	      //经办人
	      {id:'handlerCode',eleType:'easyui-searchbox',dataType:'0',disName:$xcp.i18n('pendingTask.handler'),i18n:'pendingTask.handler',isHaveHidden:true,hiddenId:'handlerId',isMainCondition:true}
	      
	];
	/**
	 * title 为原来datagrid 的title 此处需传递 并删除原来datagrid中的title
	 */
	var title = " ";//$xcp.i18n("pendingTask.myTask");
	/**
	 * queryID 为用户保存自己的查看信息时，必要的字段
	 */
	var queryID = "pendingTask";
	/**
	 * initQueryCondition 为 初始化 查询数据函数
	 */
	var allparams = {
		title : title,
		options : options,
		addHeight : 35,
		callback : initQueryCondition,
		queryID : queryID
	};
	
	$xcp.comQueryCondMgr.init(allparams);
	/*** 公共查询条件组件 end ***/
});

//初始化数据
function initQueryCondition() {
	//币种
	$('#pendingTask_tranCur').combobox({
		valueField: 'val',   
        textField: 'name', 
        data: $xcp.getConstant('cur'),
        panelHeight : 200
	});
	
	//交易名称下拉框
	$('#pendingTask_tradeNo').combobox({
		data : $xcp.getConstant("tradeNo"),
		textField : "tradeName",
		valueField : "tradeNo",
		panelHeight : 300
	});
	
	// 所属 机构下拉框
	$("#pendingTask_tranOrgNo").combobox({
		data : $xcp.getConstant("transOrg",{"orgType":"query","notNeedAll":"Y"}),
		textField : "name",
		valueField : "val",
		panelHeight : 200
	});
	// 经办人搜索框
	$("#pendingTask_handlerCode").xcpSrchBox({
		  lxOrFun : 'SRCHBUTXNTP', 
		  callback : function(rowIndex, rowData){
			  var handlerCode = rowData.PAUSR_userCode;
			  $("#pendingTask_condition").find("#pendingTask_handlerCode").xcpVal(handlerCode);
			  var handlerId = rowData.butxn_handlerId;
			  $('#pendingTask_handlerId').val(handlerId);
			  
		},params : {

		  }
		});
	$("#pendingTask_handlerCode").bind('change.pending', function() {
		if($(this).xcpVal().length <= 0){
			$("#pendingTask_handlerId").xcpVal("");
			$("#handlerId").xcpVal("");
		}else{
			var asd = {
					url:$xcp.def.getFullUrl("./pendingTask.do?method=queryHandlerId"),
					data:{handlerCode:$(this).xcpVal()}
			};
			var data = $xcp.ajax(asd,"json");
			var hand = data.rows[0].handler;			
			$('#pendingTask_handlerId').val(hand);
		}
		
	});
	
	
	// 交易步骤下拉框 Palan表tabName字段值:BUTXNAR.TRANSTATE
	$("#pendingTask_stepNo").combobox({
		data : $xcp.getConstant("queryPalan",{tabName:'WFSTP.STEPNO',lang:EAP_LANGUAGE}),
		textField : "val",
		valueField : "keyVal",
		panelHeight : 200
	});
	
	// 绑定搜索框的搜索按钮
	$("#pendingTask_custName").xcpSrchBox({
		  lxOrFun : 'SRCHBOXPACRP', 
		  callback : function(rowIndex, rowData){
			  custSearchResultDbclick(rowIndex, rowData);
		},params : {

		  }
		});
	
	$("#pendingTask_custName").bind('change.pending', function() {
		if($(this).xcpVal().length <= 0){
			$("#pendingTask_custId").xcpVal("");
			$("#custId").xcpVal("");
		}
		
	});
};

var param = new Object();
var moreCon = new Object();

/**
 * 增加批注
 */
var annotation = "";
function onRowAddAnnotation(e, rowIndex, rowData) {
	e.preventDefault();
	
	/** START-author:wangxinlin-如果任务已经申请经办更正，右键菜单显示操作按钮，否则隐藏按钮*/
	if( rowData.canAgree == '1' ){
		$('#mmm div#gotoHandle').unbind("click.gotoHandle").bind('click.gotoHandle',function(){
			//同意经办更正alert( 'wxl' );
			$xcp.menuMgrFun.correctionsInit("agree",rowData,function(){
				$('#pendingTask_letterTable').datagrid("reload") ;
			}) ;
		});
		$('#mmm div#gotoHandle').show();
	}else{
		$('#mmm div#gotoHandle').hide();
	}
	/** END-author:wangxinlin-如果任务已经申请经办更正，右键菜单显示操作按钮，否则隐藏按钮*/
	
	$('#mmm').menu('show', {
		left : e.pageX,
		top : e.pageY
	});
	
	if(rowData.tskOpinion ==""){
		$('#mmm').menu('disableItem','#viewAnnotation');
	}else{
		$('#mmm').menu('enableItem','#viewAnnotation');
	}
	
	//新增批注
	$("#addAnnotation").unbind("click.annotation").bind("click.annotation",function(){
		crateAnnotation(false,{rowData:rowData,rowIndex:rowIndex});
	});
	//查看批注
	$("#viewAnnotation").unbind("click.annotation").bind("click.annotation",function(){
		veiwAnnotation( rowIndex, rowData);
	});
	
}

/**
 * 任务列表鼠标右键菜单栏新增批注
 */
function crateAnnotation(opinion,param){
	if(opinion === false){
		top.window.$('body').xcpApp('createDialog',{
				title    : $xcp.i18n("pendingTask.addAnnotaion"),
				taskUuid : uuidHead,
				method   : crateAnnotation,
				value    : annotation,
				inputLen : 200,
				wTarget  : "taskMgrMain-iframe-a",
				data     : param});
	}else{
		annotation = opinion;
		if(annotation != ""){
			param.rowData.tskOpinion = "1";
			$("#pendingTask_letterTable").datagrid('updateRow',{index:param.rowIndex,row:param.rowData});
			param.rowData.userId = $xcp.getUSERID();
			param.rowData.taskOpinion = opinion;
			
			var ajaxOpt = {
					url  : $xcp.def.getFullUrl("comment.do?method=taskCommentMgr&type=save"),
					data : param.rowData,
					async    : false,
					error:function(XMLHttpRequest, textStatus, errorThrown){
						$.messager.alert($xcp.i18n('error'),textStatus+'\n'+errorThrown,'error');
					}
			};
			$xcp.ajax(ajaxOpt,'array');
		}
	}
	
}

/**
 * desc:
 * 	   主动更正的任务 交易名称用红色字表示
 *	   添加批注的任务 交易名称添加下划线
 * */
function cellStyler(value,row,index){
	if(row.tskOpinion != "" && row.tskOpinion != "null"){/** 已添加批注信息的任务 */
//		return 'color:#66CC66;';
		return 'text-decoration:underline;' ;
	}else if( row.canAgree == "1" ){
		/** 已经申请主动更正的任务 */
		return 'color:red;';
	}
}


/**
 * 任务列表鼠标右键菜单栏查看批注
 */
function veiwAnnotation( rowIndex, rowData){
	 var html = "<div id=\"anno\" class=\"easyui-window\"></div>";
	 var tbl = "<table id=\"infoList\" class=\"easyui-datagrid\"></table>";
	 $(html).appendTo($("body"));
	 $(tbl).appendTo("#anno");
	 $("#anno").window({
		modal:true,
		closed:false,
		title:$xcp.i18n("pendingTask.viewTaskAnnotation"),
		inline : true,
		collapsible : false,
		width:500,
		height:300,
		minimizable : false,
		onClose     : function(){
			$(this).window('destroy');
		}
	 });
	 $("#infoList").datagrid({
			loadMsg : $xcp.i18n("sys.loadMsg"),
			singleSelect: true,
			url:$xcp.def.getFullUrl('./comment.do?method=taskCommentMgr&type=view'),
			queryParams:rowData,
			width: "400",
			height: "auto",
			fit   : true,
			fitColumns: true,
			pageSize: 5,            //每页显示的记录条数，默认为10  
		    pageList: [5,10,15,20],  //可以设置每页记录条数的列表  
			columns:[[
						{title : $xcp.i18n("pendingTask.annPerson"), field : 'userId', width : 100, align : 'center'},
						{title : $xcp.i18n("pendingTask.annContent"), field : 'tskOpinion', width : 100, align : 'center'},
						{title : $xcp.i18n("pendingTask.annTime"), field : 'tskTime', width : 100, align : 'center'}

					]],
			pagination:true,
			rownumbers:true,
			onClickRow : function(){
			},
			onDblClickRow: function(rowIndex, rowData){
			}
	 });
}

/***
 * 点击任务列表切换时自动刷新表格
 */
function autoLoadData(){
	var ele = top.window.$('body').find("#taskBarId-pendingTask");
	$(ele).unbind("click.pendingTask").bind("click.pendingTask",function(){
		var masterWin = top.window.$('body').find("#pendingTask").parent().css("display");
		if(masterWin == "block"){
			var param  = {};
			var condition = $("#nc").find(".ele input");
			$.each(condition,function(i,o){
				param[$(o).attr("id")] = $(o).xcpVal();
			});
			
			$('#pendingTask_letterTable').datagrid('options').queryParams = param;
			$('#pendingTask_letterTable').datagrid('reload');
		}
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
	$("#pendingTask_condition").find("#pendingTask_custName").xcpVal(custName);
	//客户id
	var custId = rowData.pacrp_corpNo;
	$("#pendingTask_custId").val(custId);
};


/**
 * desc:申请主动更正的交易打开窗体使用的方法(对于申请主动更正的交易，由于业务，不使用app.js中定义的公共方法，即createDialog)
 * */
function createDialogForRequestBack(opts){
	var taskUuid = opts.taskUuid || '';
	var html = 	'<div id="'+taskUuid+'-frmDialog"  class="easyui-dialog" style="width:480px;height:280px;padding:10px;text-align:center;">';
		html += 	'<textarea   class="pubFrmDialogOpinion" cols=75 rows=10 style="border: 1px solid #999999;" ></textarea>';
		html +=      '<span style=" margin-left: 280px;">'+$xcp.i18n("app.inputLen").format(opts.inputLen)+'</span>';
		html += '	<div id="dlg-buttons">';
		html += 		'<a  class="easyui-linkbutton  pubDialogSaveBtn" data-options="plain:true">'+$xcp.i18n('app.accept')+'</a>';
		html += 		'<a id="frm-DelClose" class="easyui-linkbutton pubDialogCloseBtn" data-options="plain:true">'+$xcp.i18n('app.unAccept')+'</a>';
		html += 	'</div>';
		html += '</div>';
	
	$(html).appendTo('body');
	$.parser.parse($("#"+taskUuid+"-frmDialog"));
	
	$('#'+taskUuid+'-frmDialog').dialog({
		title   : opts.title,
		modal   : true,
		iconCls : 'icon-tip',
		buttons : '#dlg-buttons',
		onClose     : function(){
			$(this).dialog('destroy');
		}
	});
	
	
	//接受(同意主动更正的click事件)
	$('#'+taskUuid+'-frmDialog').find('a.pubDialogSaveBtn').bind('click',function(){
		var frmDialogVal = $('#'+taskUuid+'-frmDialog').find('.pubFrmDialogOpinion').xcpVal();
		//opts.inputLen
		if(opts.inputLen && (frmDialogVal.length > opts.inputLen) ){
			$.messager.alert($xcp.i18n("sys.tip"),$xcp.i18n("app.inputLenTip"), "info");
			return false;
		}
		
		/** START-author:wangxinlin-如果任务已经申请经办更正，右键菜单显示操作按钮，否则隐藏按钮*/
		var rowData = opts["rowData"] ;
		if( rowData.canAgree == '1' ){
			//同意经办更正alert( 'wxl' );
			$xcp.menuMgrFun.correctionsInit("agree",rowData,function(){
				$('#pendingTask_letterTable').datagrid("reload") ;
			}) ;
			$('#mmm div#gotoHandle').show();
		}else{
			$('#mmm div#gotoHandle').hide();
		}
		/** END-author:wangxinlin-如果任务已经申请经办更正，右键菜单显示操作按钮，否则隐藏按钮*/
		
		$('#'+taskUuid+'-frmDialog').dialog('destroy');
	});
	
	//不接受的click事件(直接打开窗体)
	$('#'+taskUuid+'-frmDialog').find('a.pubDialogCloseBtn').bind('click',function(){
		//打开交易
		addWindow( opts["rowData"] );
		$('#'+taskUuid+'-frmDialog').dialog('destroy');
	});
	
	var pubOpin = $('#'+taskUuid+'-frmDialog').find(".pubFrmDialogOpinion");
	if(opts.value){
		pubOpin.val(opts.value);
	}
	
	if(opts.isReadonly == true){
		pubOpin.attr("readonly","readonly").addClass("P");
	}else{
		pubOpin.focus();
	}
}
