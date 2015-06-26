//需要传递的参数列表
var pendingTaskParam = ['txnNo','tradeNo','taskFlag','taskId','state','taskState'];

$(function(){
	initSearchCondition();
	
	$('#customTask_letterTable').datagrid({
		loadMsg : $xcp.i18n("sys.loadMsg"),
		singleSelect: true,
		title: '',//$xcp.i18n("pendingTask.finishTask"),
		url:$xcp.def.getFullUrl('./pendingTask.do?method=customTask'),
		width: "900",
		height: "auto",
		fit   : true,
		fitColumns: true,
		sortOrder : "desc",
		remoteSort : true, //是否远程排序
		multiSort: true,
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
					
					{title : $xcp.i18n("pendingTask.bizNo"), field : 'currentBizNo', width : 100, align : 'center' , sortable : true },
		          	{title : $xcp.i18n("pendingTask.tradeName"), field : 'tradeDesc', width : 100, align : 'center' , sortable : true },
		          	{title : $xcp.i18n("pendingTask.tranStateDesc"), field : 'tranStateDesc', width : 100, align : 'center' , sortable : true },
		          	{title : $xcp.i18n("pendingTask.tranOrgName"), field : 'tranOrgName', width : 100, align : 'center' , sortable : true },
		          	{title : $xcp.i18n("pendingTask.tranCur"), field : 'tranCur', width : 100, align : 'center' , sortable : true },
					{title : $xcp.i18n("pendingTask.tranAmt"), field : 'tranAmt', width : 100, align : 'center' , sortable : true },
					/*** TASK #230 【浦发】任务列表增加2列显示经办、复核人，可以去掉流水号一列 begin ***/
					{title : $xcp.i18n("pendingTask.txnNo"), field : 'txnNo', width : 100, align : 'center' , sortable : true },
					/*** TASK #230 【浦发】任务列表增加2列显示经办、复核人，可以去掉流水号一列 end ***/
					{title : $xcp.i18n("pendingTask.handler"), field : 'handlerCode', width : 100, align : 'center' , sortable : true },
					{title : $xcp.i18n("pendingTask.checker"), field : 'checkerCode', width : 100, align : 'center' , sortable : true },
					{title : $xcp.i18n("pendingTask.createTime"), field : 'crtDate', width : 100, align : 'center' , sortable : true },
					
					
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
		}
	});
	
	// 查询按钮绑定事件
//	$('#searchBtn').bind('click', function() {
//		doQuery(param);
//	});
	
	//多条件按钮事件初始化
	//onMoreCondtitonBtnClick();
	
//	autoLoadData();
	
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
	      {id:'tranOrgNo',eleType:'easyui-combobox',dataType:'0',disName:$xcp.i18n('butxn.orgName'),i18n:'butxn.orgName',isMainCondition:true},
	      //{id:'custName',eleType:'easyui-searchbox',dataType:'0',disName:$xcp.i18n('butxn.custName'),i18n:'butxn.custName',isHaveHidden:true,hiddenId:'custId',isMainCondition:true},
	      {id:'tradeNo',eleType:'easyui-combobox',dataType:'0',disName:$xcp.i18n('butxn.tradeName'),i18n:'butxn.tradeName',isMainCondition:true},
	      {id:'crtDate',eleType:'easyui-datebox',dataType:'2',disName:$xcp.i18n('commQuery.date'),i18n:'commQuery.date',isMainCondition:true}
	];
	/**
	 * title 为原来datagrid 的title 此处需传递 并删除原来datagrid中的title
	 */
	var title = $xcp.i18n("pendingTask.curTask");
	/**
	 * queryID 为用户保存自己的查看信息时，必要的字段
	 */
	var queryID = "customTask";
	/**
	 * initQueryCondition 为 初始化 查询数据函数
	 */
	var allparams = {
		title : title,
		options : options,
		addHeight : 35,
		callback : initQueryCondition,
		queryID : queryID,
		
	};
	$xcp.comQueryCondMgr.init(allparams);
	/*** 公共查询条件组件 end ***/
});

//初始化数据
function initQueryCondition() {
	//交易名称下拉框
	$("#customTask_condition").find('#customTask_tradeNo').combobox({
		data : $xcp.getConstant("tradeNo"),
		textField : "tradeName",
		valueField : "tradeNo",
		panelHeight : 300
	});
	// 所属 机构下拉框
	$("#customTask_condition").find("#customTask_tranOrgNo").combobox({
		data : $xcp.getConstant("transOrg"),
		textField : "orgName",
		valueField : "orgNo",
		panelHeight : 200
	});
	// 绑定搜索框的搜索按钮
	$("#customTask_condition").find("#customTask_custName").searchbox({
		searcher:function(value,name){
			var param = null;
			//中文环境拿根据中文名称搜索
			if(EAP_LANGUAGE == 'zh_CN'){
				param = {cnName:value};
			}else{
				//其他的根据英文名称搜索
				param = {enName:value};
			}
			$xcp.comQuery(
					"corp",// 查询客户帐的标识
					custSearchResultDbclick, //客户搜索结果集 双击选择事件
					param //查询参数对象
			);
		}
	});
};

var param = new Object();

var moreCon = new Object();

/**
 * 初始化默认的查询条件
 */
function initSearchCondition() {
	
	$('#tradeNo').combobox({
		data : $xcp.getConstant("tradeNo"),
		textField : "tradeName",
		valueField : "tradeNo",
		panelHeight : 300
	});
	$('#transOrg').combobox({
		data : $xcp.getConstant("transOrg"),
		textField : "orgName",
		valueField : "orgNo"/*,
		panelHeight : 300*/
	});
	
	//获得用户的查询条件
	var userDefData = getUserCfgData({"smartId":$xcp.getUSERID(),"smartKey":"taskListQuery"});
	 
	//将用户保存的查询条件展示到页面
	if(userDefData && userDefData.length > 0 ){
		userDefData = $xcp.parseJson(userDefData[0].smartInfo);
		$.each(userDefData,function(i,o){
			var tr = "";
			if(i % 2 == 0){
				tr = "<tr></tr>";
				$(tr).appendTo("#nc");
			
			}
			var tdhtml = "<td style='text-align: right;width: 6%;'>"+$xcp.i18n($(o).attr("i18n"))+":</td> " +
			 "<td class='ele'><input class='"+$(o).attr("eleType")+"' id='"+$(o).attr("id")+"'></td>";
			
			$(tdhtml).appendTo($("#nc tr:last"));
			
			$.parser.parse($("#nc tr:last"));
		});
		 
		if(userDefData.length % 2 == 1){
			var tdhtml = "<td style='text-align: right;width: 6%;'></td><td class='ele'></td>";
			$(tdhtml).appendTo($("#nc tr:last"));
		}
		//计算布局
		initTaskLayout();
		//数据初始化
		initConditionData(userDefData);
		moreCon = userDefData;
		
	}
}

/**
 * 查询按钮查询事件
 * @param param
 */
function doQuery() {
	var param  = {};
	var condition = $("#nc").find(".ele input");
	
	$.each(condition,function(i,o){
		param[$(o).attr("id")] = $(o).xcpVal();
	});
//	alert($xcp.toJson(param)) ; 
	$('#taskListTaskTable').datagrid('options').queryParams = param;
	$('#taskListTaskTable').datagrid('load');
}

/**
 * 初始化布局
 */ 
function initTaskLayout() {
	var nortHeight = $("#condition").find(".etable").height();
	if(nortHeight != undefined){
		$('#taskList').layout('panel', 'north').panel('resize', {
			height : nortHeight||""
		});
	}
	
	$('#taskList').layout('resize');
	
	$("#morecon").css({top:nortHeight});
	
}

/**
 *查询条件初始化数据
 */
function initConditionData(cEles){
	
	$.each(cEles,function(i,o){
		var eleId = $(o).attr("id");
		var eleType = $(o).attr("eleType");
		if(eleType == "easyui-combobox"){
			if(eleId == "stepNo"){
				$("#"+eleId).combobox({
					data : $xcp.getConstant(eleId),
					textField : "stepName",
					valueField : "stepNo",
					panelHeight : 300
				});
			}
			
			if(eleId == "tranCur"){
				$("#"+eleId).combobox({
					data : $xcp.getConstant("cur"),
					textField : "curSign",
					valueField : "curSign"
				});
			}
		}
		if(eleType =="easyui-searchbox"){
			if(eleId == "corpNo"){
				$('#'+eleId).searchbox({
					searcher : function(value, name) {
						$xcp.comQuery('corp', fillCorpText, null);
					}

				});
				
				function fillCorpText(rowIndex, rowData){
					$("#corpNo").xcpVal(rowData.corpNo);
				}
			}
		}
	});
}

/**
 * 保存用户自定义数据  保存查询条件 
 */
function saveUserCfgData(cEles){
	var rs = [];
	$.each(cEles,function(i,o){
		rs.push({
				id      : $(o).attr("id"),
				disName : $(o).attr("disName"),
				i18n    : $(o).attr("i18n"),
				eleType : $(o).attr("eleType")
		});
		
	});
	var userData ={};
	userData.smartKey = "taskListQuery";
	userData.smartInfo = rs;
	if(cEles.length == 0)rs=[{}];
	if(rs.length > 0){
		$.ajax({
			type : "post",
			url  : $xcp.def.getFullUrl("userinfo.do?method=saveUserDefData"),
			data : $xcp.parseJson(userData,true),
			dataType : "json",
			async    : false,
			success : function (resp) {
			},
			error : function (XMLHttpRequest, textStatus, errorThrown) {
				$.messager.alert("", textStatus || errorThrown, "error");
			}
		});
	}
}
 
/**
 * 获得用户自定义的任务列表查询条件信息
 * @param opts
 */
function getUserCfgData(opts){
	var data = null;
	if(opts){
		$.ajax({
			type : "post",
			url  : $xcp.def.getFullUrl("userinfo.do?method=getUserDefData"),
			data : opts,
			dataType : "json",
			async    : false,
			success : function (resp) {
				if(resp.success =='1'){
					$xcp.dispAjaxError($xcp.cloneObject(resp));
				}else{
					data = resp.outEntity;
				}
			},
			error : function (XMLHttpRequest, textStatus, errorThrown) {
				$.messager.alert("", textStatus || errorThrown, "error");
			}
		});
	}
	return data;
}

/***
 * 选择跟多条件,弹出条件面包可供选择
 */
function onMoreCondtitonBtnClick(){
	
	var divs =  "<div id='morecon' class='toolNavigation'> ";
	divs += "	<div class='arrowhead'></div>";
	divs += "	<div class='contentWrap'>";
	divs += "			<div class='content' style=\"padding-top: 5px;\">";
	divs += "				<input id=\"corpNo\"  eleType='easyui-searchbox' disName='"+$xcp.i18n("pendingTask.corpName")+"' i18n='pendingTask.corpName'  class=\"ele\" type=\"checkbox\" style=\"margin-right: 2px;\"><label style=\"margin-right:15px;\">"+$xcp.i18n("pendingTask.corpName")+"</label></input>" +
			"				<input id=\"tranCur\" eleType='easyui-combobox' disName='"+$xcp.i18n("pendingTask.tranCur")+"' i18n='pendingTask.tranCur' class=\"ele\" type=\"checkbox\" style=\"margin-right: 2px;\"><label style=\"margin-right:15px;\">"+$xcp.i18n("pendingTask.tranCur")+"</label></input> " +
			"				<input id=\"tranAmt\" eleType='easyui-numberbox' disName='"+$xcp.i18n("pendingTask.tranAmt")+"' i18n='pendingTask.tranAmt' class=\"ele\" type=\"checkbox\" style=\"margin-right: 2px;\"><label style=\"margin-right:15px;\">"+$xcp.i18n("pendingTask.tranAmt")+"</label></input>"+
			"				<input id=\"stepNo\" eleType='easyui-combobox' disName='"+$xcp.i18n("pendingTask.stepName")+"' i18n='pendingTask.stepName' class=\"ele\" type=\"checkbox\" style=\"margin-right: 2px;\"><label style=\"margin-right:15px;\">"+$xcp.i18n("pendingTask.stepName")+"</label></input>"+
			"				<input id=\"curtBizNo\" eleType='text' disName='"+$xcp.i18n("pendingTask.bizNo")+"' i18n='pendingTask.bizNo' class=\"ele\" type=\"checkbox\" style=\"margin-right: 2px;\"><label style=\"margin-right:15px;\">"+$xcp.i18n("pendingTask.bizNo")+"</label></input>";
	divs +=	"				<div style=\"margin-top: 20px; float:right;\">" ;
	divs +="					<button class='paramQueryBtn confirmOk' style=\"margin-right: 40px;\">"+$xcp.i18n("app.confirm")+"</button>" ;
	divs +="					<button class='cancelBtn confirmNo' style=\"margin-right: 40px;\">"+$xcp.i18n("app.cancel")+"</button>" +
							"</div> ";
	divs += "			</div>" ;
	divs += "		</div>";
	divs += "  </div>";
		
   $(divs).appendTo("body");
		
   $("#morecon").css({top:$("#condition").find(".etable").height()}); //36
		
   var refTarget = $("#morecon" );
		
		
	if(moreCon.length > 0 ){
			var cEles = $("#morecon").find(".ele");
			$.each(moreCon,function(i,o){
				$.each(cEles,function(j,b){
					if($(o).attr("id") == $(b).attr("id")){
						$(b).attr("checked","checked");
					}
				});
			});
	}
		
	$("#moreCondition").unbind("click.taskList").bind('click.taskList',function(e){
		alert(1111) ; 
			 if(refTarget.is(":hidden")){
				 $('.toolNavigation').hide();  //refTarget.fadeIn('slow');
			 }else{
				 refTarget.fadeOut('slow');
			 }
	});
	
	//取消按钮事件
	$("#morecon").find(".confirmNo").unbind("click.confirmNo").bind("click.confirmNo",function(){
			//$(':checkbox').removeAttr("checked");
			refTarget.fadeOut('slow');
	});
		
		
	//确认按钮事件 
	$("#morecon").find(".confirmOk").unbind("click.confirmOk").bind("click.confirmOk",function(){
			var cEles = $("#morecon").find(".ele:checked");
			$("#nc tr:gt(0)").remove();
			$.each(cEles,function(i,o){
				var tr = "";
				if(i % 2 == 0){
					tr = "<tr></tr>";
					$(tr).appendTo("#nc");
				
				}
				var tdhtml = "<td style='text-align: right;width: 6%;'>"+$(o).attr("disName")+":</td> " +
				 "<td class='ele'><input class='"+$(o).attr("eleType")+"' id='"+$(o).attr("id")+"'></td>";
				
				$(tdhtml).appendTo($("#nc tr:last"));
				
				$.parser.parse($("#nc tr:last"));
			
			});
			if(cEles.length % 2 == 1){
				var tdhtml = "<td style='text-align: right;width: 6%;'></td><td class='ele'></td>";
				$(tdhtml).appendTo($("#nc tr:last"));
			}		
			refTarget.fadeOut('slow');
			
			//查询条件数据初始化
			initConditionData(cEles);
			//保存查询条件
			saveUserCfgData(cEles);
			//重新计算Layout
			initTaskLayout();
			
	});
}