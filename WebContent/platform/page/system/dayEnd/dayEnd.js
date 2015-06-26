var language = "";
var rateData = [];
// 得到批次号
/*function getBatch() {
	isDisabled();
	// 运行模式
	var runMode = $("#runMode").xcpVal();
	// 运行频率
	var rate = $("#rate").xcpVal();
	var execNo = $xcp
			.ajax({
				url : $xcp.def
						.getFullUrl("./dayEnd.do?method=dayEndManager&action=getBatchNo&runMode="
								+ runMode + "&rate=" + rate)
			});
	$("#execNo").combobox({
		textField : 'text',
		valueField : 'id',
		panelHeight : 100,
		data : execNo.execNo
	});
	if (null != execNo.execNo[0] && undefined != execNo.execNo[0]) {
		$("#execNo").combobox("setValue", execNo.execNo[0].id);
	}
}*/
// 启动日终处理
function start() {
	var rows = $("#dayEndTab").datagrid("getSelections");
	if(rows.length == 0){
		$.messager.alert($xcp.i18n('sys.warning'), $xcp.i18n('dayEnd.changeBatch'));
		return;
	}
	var execNo = "";
	var noArr = [];
	$.each(rows,function(i,o){
		noArr.push(o.itemno);
	});
	execNo = noArr.join(",");
	var opts = {
			url : $xcp.def
					.getFullUrl("./dayEnd.do?method=dayEndManager&action=startSingle&itemno="
							+ execNo)
		};
	$xcp.ajax(opts, null, function(rs, opt) {
	});
	dayEndQuery();
	getText();
	//	alert($xcp.toJson(rows));
	/*$("#showText").html("");
	var execNo = $("#execNo").xcpVal();
	if (execNo == "" || null == execNo) {
		$.messager.alert($xcp.i18n('sys.warning'), $xcp
				.i18n('dayEnd.changeBatch'));
		return;
	}
	var opts = {
		url : $xcp.def
				.getFullUrl("./dayEnd.do?method=dayEndManager&action=start&execNo="
						+ execNo)
	};
	getText();
	$xcp.ajax(opts, null, function(rs, opt) {
	});
	timeSuspend = setInterval(function() {
		dayEndQuery();
		if (rootState == $xcp.i18n('dayEnd.fail')
				|| rootState == $xcp.i18n('dayEnd.success')
				|| rootState == $xcp.i18n('dayEnd.mstop')) {
			if (timeSuspend != null) {
				clearInterval(timeSuspend);// 停止定时器
			}
		}
		getText();
	}, 1000);*/
}
function startAll(){
	var opts = {
			url : $xcp.def
					.getFullUrl("./dayEnd.do?method=dayEndManager&action=startSingle&itemno=ALL")
		};
	$xcp.ajax(opts, null, function(rs, opt) {
	});
	dayEndQuery();
	getText();
}

var timeSuspend = null;

// 停止
function stop() {
	// var row = $("#dayEndTab").datagrid('getSelected');
	var execNo = $("#execNo").xcpVal();
	if (execNo == "" || null == execNo) {
		$.messager.alert($xcp.i18n('sys.warning'), $xcp
				.i18n('dayEnd.changeBatch'));
		return;
	}
	var opts = {
		url : $xcp.def
				.getFullUrl("./dayEnd.do?method=dayEndManager&action=stop&execNo="
						+ execNo)
	};
	$xcp.ajax(opts, null, function(rs, opt) {
	});
	if (timeSuspend != null) {
		clearInterval(timeSuspend);// 停止定时器
	}
	dayEndQuery();

}
// 日志查询
function getText() {
	var opts = {
		url : $xcp.def
				.getFullUrl("./dayEnd.do?method=dayEndManager&action=showLOG")
	};
	$xcp.ajax(opts, null, function(rs, opt) {
		str = rs;
	});
	$("#showText").html(str.log);
}

// 是否禁用停止启动按钮
/*function isDisabled() {
	if ($xcp.i18n('dayEnd.running') == rootState) {
		$('#startBut').attr('disabled', "true").css({
			"background-color" : "Gray"
		});
		$('#stopBut').attr('disabled', false).css({
			"background-color" : "#3499fe"
		});
	} else if ($xcp.i18n('dayEnd.success') == rootState) {
		$('#startBut').attr('disabled', "true").css({
			"background-color" : "Gray"
		});
		$('#stopBut').attr('disabled', "true").css({
			"background-color" : "Gray"
		});
	} else if ($xcp.i18n('dayEnd.mstop') == rootState || $xcp.i18n('dayEnd.ready') == rootState
			|| $xcp.i18n('dayEnd.fail') == rootState) {
		$('#startBut').attr('disabled', false).css({
			"background-color" : "#3499fe"
		});
		$('#stopBut').attr('disabled', "true").css({
			"background-color" : "Gray"
		});
	}
	var auto = $("#runMode").xcpVal();
	if ("0" == auto) {
		$('#startBut').attr('disabled', true).css({
			"background-color" : "Gray"
		});
		$('#stopBut').attr('disabled', true).css({
			"background-color" : "Gray"
		});
	}
}*/

// 查询
function dayEndQuery() {
	// 批次号
//	var execNo = $("#execNo").xcpVal();
	qrParams = $("#dayEndTab").datagrid('options').queryParams;
	qrParams.runMode = $("#runMode").xcpVal();
	/*qrParams.execNo = execNo;

	qrParams.rate = $("#rate").xcpVal();*/
	$('#dayEndTab').datagrid('options').queryParams = qrParams;
	$('#dayEndTab').datagrid('load');

}
var rootState = null;
$(function() {
	// 得到用户语言
	language = $xcp.getEAP_LANGUAGE();
	rateData = $xcp.getConstant('ITBATITM.RATE');
	/*$('#startBut').attr('disabled', "true").css({
		"background-color" : "Gray"
	});*/
	/*$('#stopBut').attr('disabled', "true").css({
		"background-color" : "Gray"
	});*/
	// 绑定查询框中下拉框的数据
	/*$("#runMode").combobox({
		textField : 'text',
		valueField : 'id',
		panelHeight : 100,
		data : [ {
			"text" : $xcp.i18n("dayEnd.manual"),
			"id" : "1"
		}, {
			"text" : $xcp.i18n("dayEnd.auto"),
			"id" : "0"
		} ]
	});*/
	$("#runMode").combobox("setValue", 1);

	// 频率 0- 每天 1- 每周 2- 每月 3- 每季 4- 每年 5- 工作日每天
	/*$("#rate").combobox({
		textField : 'text',
		valueField : 'id',
		panelHeight : 100,
		data : [ {
			"text" : $xcp.i18n("dayEnd.everyDay"),
			"id" : "0"
		}, {
			"text" : $xcp.i18n("dayEnd.weekly"),
			"id" : "1"
		}, {
			"text" : $xcp.i18n("dayEnd.month"),
			"id" : "2"
		}, {
			"text" : $xcp.i18n("dayEnd.quarterly"),
			"id" : "3"
		}, {
			"text" : $xcp.i18n("dayEnd.year"),
			"id" : "4"
		}, {
			"text" : $xcp.i18n("dayEnd.workdays"),
			"id" : "5"
		} ]
	});
	$("#rate").combobox("setValue", 0);
	getBatch();*/
	$('#dayEndTab').datagrid({
		loadMsg : $xcp.i18n("sys.loadMsg"),
		singleSelect : false,
		title : $xcp.i18n("dayEnd.queryList"),
		url : $xcp.def.getFullUrl('./dayEnd.do?method=dayEndManager'),
		queryParams:{runMode:"1"},
		width : $('body').outerWidth(),
		height : "250",
		fit : true,
		fitColumns : true,
		rowStyler : function(rowIndex,rowData){
				if(rowData.state == "2"){
					return "background:red";
				}else if(rowData.state == "4"){
					return "background:#99ff99";
				}
			},
		pageSize : 10, // 每页显示的记录条数，默认为10
		pageList : [ 5, 10, 15, 20 ], // 可以设置每页记录条数的列表
		columns : [ [ {
			title : $xcp.i18n("dayEnd.rate"),
			field : 'rate',
			width : 100,
			align : 'center',
			formatter : function(value, rowData, rowIndex){
				var ret = value;
				$.each(rateData,function(i,o){
					if(o.val == value){
						ret = o.name;
						return false;
					}
				});
				return ret;
			}
		},{
			title : $xcp.i18n("dayEnd.coding"),
			field : 'itemno',
			width : 100,
			align : 'center'
		}, {
			title : $xcp.i18n("dayEnd.name"),
			field : 'itemname',
			width : 100,
			align : 'center'
		}, {
			title : $xcp.i18n("dayEnd.order"),
			field : 'idx',
			width : 100,
			align : 'center'
		}, {
			title : $xcp.i18n("dayEnd.bisLogic"),
			field : 'act',
			width : 100,
			align : 'center'
		}, {
			title : $xcp.i18n("dayEnd.repeat"),
			field : 'allowretry',
			width : 100,
			align : 'center',
			formatter : function(value, rowData, rowIndex){
				return rowData.allowretryname;
			}
		}, {
			title : $xcp.i18n("dayEnd.dependencies"),
			field : 'idtidx',
			width : 100,
			align : 'center'
		}, {
			title : $xcp.i18n("dayEnd.begin"),
			field : 'strdate',
			width : 100,
			align : 'center'
		}, {
			title : $xcp.i18n("dayEnd.end"),
			field : 'enddate',
			width : 100,
			align : 'center'
		}, {
			title : $xcp.i18n("dayEnd.status"),
			field : 'state',
			width : 100,
			align : 'center',
			formatter : function(value, rowData, rowIndex){
				return rowData.statename;
			}
		}, {
			title : $xcp.i18n("dayEnd.operation"),
			field : 'operation',
			width : 100,
			align : 'center',
			formatter : enditDataField
		} ] ],
		pagination : true,
		rownumbers : true,
		onLoadSuccess : function(data) {
			
			/*var merges = [];
			var row = {};
			var oldRate = "";
			var rowspan = 0;
			var rowData = data.rows;
			$.each(rowData,function(i,o){
				if(oldRate == o.rate){
					rowspan = rowspan * 1 + 1;
					if(i == rowData.length - 1){
						row.rowspan = rowspan;
						merges.push($.extend({},row));
					}
				}else{
					if(i > 0){
						row.rowspan = rowspan;
						merges.push($.extend({},row));
						row = {};
						row.index = i;
						rowspan = 1;
						oldRate = o.rate;
					}else{
						row.index = i;
						rowspan = 1;
						oldRate = o.rate;
					}
				}
			});
			for(var i=0; i<merges.length; i++){
				$(this).datagrid('mergeCells',{
					index: merges[i].index,
					field: 'rate',
					rowspan: merges[i].rowspan
				});
			}*/
			
			/*var execNo = $("#execNo").xcpVal();
			if (!data.rootMap && null == execNo) {
				$("#showTdStatus").html("");
				return;
			}
			rootState = data.rootMap.state;
			var htmlScript = "";
			if (language == "zh_CN") {
				htmlScript = execNo + "  批次批处理状态: " + rootState;
			} else if (language == "en_US") {
				htmlScript = execNo + "  Batch Item batch status is: " + rootState;
			}
			$("#showTdStatus").html(htmlScript);
			isDisabled();*/
		}
	});
	$xcp.stopPorcess();
	$("body").layout('collapse','south');
});
// 启动单个日终任务
function startSingle(rowIndex) {
	// timeSuspend = setInterval(
	// function() {
	$("#dayEndTab").datagrid("selectRow", rowIndex);
	var rowData = $("#dayEndTab").datagrid("getSelected");
	var itemno = rowData.itemno;
	var opts = {
		url : $xcp.def
				.getFullUrl("./dayEnd.do?method=dayEndManager&action=startSingle&itemno="
						+ itemno)

	};
	$xcp.ajax(opts, null, function(rs, opt) {
	});
	dayEndQuery();

	getText();
	// }, 1000 * 5);
}

function enditDataField(value, rowData, rowIndex) {
	var html = "";
	if ( (rowData.state == "2")//运行失败
		|| (rowData.runmode == "1" && rowData.state == "0") //手动 准备运行
		|| (rowData.allowretry == "1" && rowData.state == "4") //允许重复执行并且运行成功的
	)
	{
		html = '<button class="but_query paramQueryBtn" type="button" onclick="startSingle('
				+ rowIndex + ')">'+ $xcp.i18n("dayEnd.startSig") +'</button>';
	}
	return html;
}
