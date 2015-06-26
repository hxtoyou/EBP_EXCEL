/**
 * 在途交易查询js文件
 */

//需要传递的参数列表
var pendingTaskParam = ['txnNo','tradeNo','taskFlag','taskId','state'];

var param = new Object();

var moreCon = new Object();

function addWindow(rowData){	
	  rowData.taskFlag = rowData.stepNo;
	  url = './' + rowData.tradeName + '.do?method=getEditData';
	  $.each(pendingTaskParam,function(i,param){
		  url += "&" + param + "=" + (rowData[param] || '');
	  });
	  url = url+"&tradeName="+encodeURI(encodeURI(rowData.tradeDesc))+"&bizNo="+rowData.curtBizNo+"&butxn_tranOrgNo="+rowData.tranOrgNo;
	  url += "&queryFlag=query&taskState=1&workflow=off";
	  //打开窗口
	  var obj = {
		  url : url,
		  name : rowData.tradeDesc,
		  txnNo : rowData.txnNo,
		  tradeNo : rowData.tradeNo,
		  taskFlag : rowData.stepNo || '',
		  currentBizNo : rowData.curtBizNo,
		  hasLeft : "N"
	  };
	  $xcp.formPubMgr.openTrade(obj);
//	  top.window.$('body').xcpApp('createTask',
//			  {
//		   		 url : url,
//		   		 name : rowData.tradeName,
//		   		 txnNo : rowData.txnNo,
//		   		 taskFlag : rowData.stepNo || '' ,
//		   		hasLeft : $xcp.task.isUpdateData(rowData.stepNo||"1") ? "Y" : "N"
//		   	  }
//	  );
}
var orgNo = $xcp.getConstant('belongOrg');
paramMarComboxKey={};
paramMarComboxKey.formatOrg = function(val,rowData,index){
	var checkFlag = "";
	$.each(orgNo,function(i,k)
	{
		if(k.val == val)
		{
			checkFlag = k.name;
			return checkFlag;
		}
	});
	return checkFlag;
};

/**\
 * 交易查询帮助类
 */
var ButxnarQueryHelp = function($){
		//初始化表格数据加载
		function intiTableDataLoad(){
			$('#transitTQ_letterTable').datagrid({
				loadMsg : $xcp.i18n("sys.loadMsg"),
				singleSelect: true,
				url:$xcp.def.getFullUrl('./transitTradeQuery.do?method=queryList'),
				width: "900",
				height: "auto",
				//toolbar:"#tb",
				//collapsible:true,
				//tools: "#commonQueryToolBar",
				fit   : true,
				fitColumns: true,
				pageSize: 10,            //每页显示的记录条数，默认为10  
			    pageList: [5,10,15,20],  //可以设置每页记录条数的列表  
			    sortName:"tranDate",
			    sortOrder :"desc",
				columns:[[	
				          	//执行机构
				            {title : $xcp.i18n("butxn.tranOrgNo"), field : 'tranOrgNo',"formatter":paramMarComboxKey.formatOrg, width : 100, align : 'center',"sortable" : true},
				            //交易名称
//				            {title : $xcp.i18n("butxn.tradeName"), field : 'tradeName', width : 100, align : 'center',"sortable" : true},
				            {title : $xcp.i18n("butxn.tradeName"), field : 'tradeDesc', width : 100, align : 'center',"sortable" : true},
				            //交易步骤
				            {title : $xcp.i18n("PATRDDOC.stepNo"), field : 'i18nTranstateName', width : 100, align : 'center',"sortable" : true, formatter: function(value,row,index){
				            	if( value.indexOf('Pending') > -1 && ( row.stepNo == '4001' || row.stepNo == '4002' || row.stepNo == '4003') ){
	    		          			try{
	    		          				var stepQueue = $xcp.parseJson(row.vars).DoneStepQueue;
		    		          			var arr = stepQueue.split("@@");
		    		          			if( stepQueue != "" && arr.length > 0 ){
		    		          				return value +" "+ (arr.length+1);
		    		          			}
	    		          			}catch(e){}
	    		          		}
		    					return value;
				            }},
				            //客户名称
				            {title : $xcp.i18n("butxn.custName"), field : 'custName', width : 100, align : 'center',"sortable" : true},
				            //主业务编号
				          	{title : $xcp.i18n("butxn.preBizNo"), field : 'preBizNo', width : 100, align : 'center',"sortable" : true},
				          	//业务编号
				          	{title : $xcp.i18n("butxn.curtBizNo"), field : 'curtBizNo', width : 100, align : 'center',"sortable" : true/*,styler:cellStyler*/},
				          	//{title : "任务完成日期", field : 'finDate', width : 100, align : 'center',"sortable" : true},
				          	//币种
				          	{title : $xcp.i18n("butxn.tranCur"), field : 'tranCur', width : 100, align : 'center',"sortable" : true},
				          	//金额
							{title : $xcp.i18n("butxn.tranAmt"), field : 'tranAmt', width : 100, align : 'center',"sortable" : true},
							//日期
							{title : $xcp.i18n("commQuery.date"), field : 'crtDate', width : 100, align : 'center',"sortable" : true},
							//经办人
							{title : $xcp.i18n("butxn.handler"), field : 'handler', width : 100, align : 'center',"sortable" : true/*hidden:true*/},
							//复核人
							{title : $xcp.i18n("butxn.checker"), field : 'checker', width : 100, align : 'center',"sortable" : true},
							//经理
							{title : $xcp.i18n("butxn.managerId"), field : 'managerId', width : 100, align : 'center',"sortable" : true},
							{title : $xcp.i18n("quickquery.flowBtn"), field:'stepNo', width:80, align:'center',hidden:false, formatter: function(value, rowData, idx){
								/** author:wangxinlin 添加查看任务流程的按钮 */
								var s = "";
								s += '<div class="wflowBtn" myIdx="'+idx+'"><a class="easyui-linkbutton" data-options="plain:true,iconCls:\'icon-view\'"></a></div>';
								return s;
							}},{title : $xcp.i18n("quickquery.workflowInfo"), field : 'operate',width : 100,align : 'center',
								formatter:lookOverWorkflowTaskInfo
							}
						]],
				pagination:true,
				rownumbers:true,
				onDblClickRow: function(rowIndex, rowData){
					addWindow(rowData);
				},
				onLoadSuccess : function(){
					$.parser.parse($(".wflowBtn"));
					$(".wflowBtn").unbind("click.wflowBtn").bind("click.wflowBtn",function(){
						var i = $(this).attr("myIdx");
						var datas = $('#transitTQ_letterTable').datagrid('getRows');
						var txnNo = datas[i].txnNo;
						$xcp.ajax({url:$xcp.def.getFullUrl("./transitTradeQuery.do?method=queryNodes"), data:{"txnNo":txnNo}}, null, null, function(data){
							var templateNo = data.templateNo;
							var stepNo = data.stepNos[data.stepNos.length-1];
							var nodeArw = sp = "";
							for(var i=0; i<data.stepNos.length-1; i++){
								nodeArw += sp + data.stepNos[i];
								sp = ",";
							}
							var nodeIds = data.nodeIds.join(",");
							var winId = "wfpWin";
							if(!$("#"+winId) || $("#"+winId).length == 0){
								var wfpHtml = "";
								wfpHtml += "<div id=\"" + winId + "\" title=\"  \">";
								wfpHtml += "<iframe id='wfpIframe' frameborder='0'  style='width:100%;height:100%;overflow-x:auto;overflow-y:auto'>";
								wfpHtml += "</iframe>";
								wfpHtml += "</div>";
								$(wfpHtml).appendTo("body");
							}
							$("#wfpIframe").attr("src",$xcp.def.getFullUrl("./transitTradeQuery.do?method=viewWFP") + "&templateNo="+ templateNo + "&stepNo="+stepNo+"&nodeArw="+nodeArw+"&nodeIds="+nodeIds);
							$("#"+winId).window({
								width : 1000,
								collapsible : false,
								height : 500,
								modal : true,
								onClose : function(){
									$(this).window('destroy');
								}
							}).window('open');
						});
					});
				}
			});
			
			//查看工作流任务信息
			function lookOverWorkflowTaskInfo(value,rowData,rowIndex){
				var html = "";
					html  = "<div class='paramDmlOperaBtnWrap'>";
					html += 	"<span  class='mod' id='torkflowTask' onclick='ButxnarQueryHelp.showWorkflowTaskInfo(\""+rowData.txnNo+"\");'></span>";
				    html += "</div>";
				return html;
			};
			//工作流 任务信息显示窗口初始化
			$("#workflowTaskInfo").window({
				width:550,
				//height: 600,
				minimizable : true,
				collapsible : false,
				title : $xcp.i18n("butxn.workflowTaskInfo"),
				modal : true,
				shadow: false,
				closed :true,
				draggable : true,
				onMove: function(left,top){
					//也许window 和 datagrid有点冲突,他们都是继承自$.fn.panel.defaults,反正这里定义上这个 移动就不会出现问题了
					//console.info(left+":"+top);
				}
			});
			
			//工作流 任务信息显示表格 初始化
			$("#workflowTaskInfo").datagrid({
				loadMsg : $xcp.i18n("sys.loadMsg"),
				singleSelect: true,
				title:'',
				left:0,
				top:0,
				width: 540,
				//height: 600,
				fitColumns: false,
				columns:[[	
				          	{title : $xcp.i18n("butxn.stepName"), field : 'stepName', width : 100, align : 'center'},
				          	{title : $xcp.i18n("butxn.userName"), field : 'userName', width : 100, align : 'center'},
				          	{title : $xcp.i18n("butxn.startTime"), field : 'strDate', width : 150, align : 'center'},
				          	{title : $xcp.i18n("butxn.endTime"), field : 'endDate', width : 150, align : 'center'}
						]],
				pagination:false,
				rownumbers:true
			});
		};
		//显示工作流任务信息
		function showWorkflowTaskInfo(txnNo){
			$("#workflowTaskInfo").window("open");
			var aopt = {
				url:"./pendingTask.do?method=queryOpHis&txnNo="+txnNo
			};
			var rs = $xcp.ajax(aopt);
			if(rs == null || !rs || rs.length < 1){
				rs = {"rows":[]};
			}
			$("#workflowTaskInfo").datagrid("loadData",rs);
		};
		//初始化
		function init(){
			intiTableDataLoad();
		};
		return {
			//初始化函数
			init:init,
			showWorkflowTaskInfo:showWorkflowTaskInfo
		};
}(jQuery);

$(function() {
	//初始化
	ButxnarQueryHelp.init();
	$xcp.stopPorcess();//关闭进度条窗口
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
	      {id:'tranOrgNo',eleType:'easyui-combobox',dataType:'0',disName:$xcp.i18n('butxn.tranOrgNo'),i18n:'butxn.tranOrgNo',isMainCondition:true},
	      {id:'tradeNo',eleType:'easyui-combobox',dataType:'0',disName:$xcp.i18n('butxn.tradeName'),i18n:'butxn.tradeName',isMainCondition:true},
	      {id:'stepNo',eleType:'easyui-combobox',dataType:'0',disName:$xcp.i18n('PATRDDOC.stepNo'),i18n:'PATRDDOC.stepNo',isMainCondition:true},
	      {id:'custName',eleType:'easyui-searchbox',dataType:'0',disName:$xcp.i18n('butxn.custName'),i18n:'butxn.custName',isHaveHidden:true,hiddenId:'custId',isMainCondition:true},
	      {id:'curtBizNo',eleType:'easyui-validatebox',dataType:'0',disName:$xcp.i18n('quickquery.bizNo'),i18n:'quickquery.bizNo',isMainCondition:true},
	      {id:'tranCur',eleType:'easyui-combobox',dataType:'0',disName:$xcp.i18n('butxn.tranCur'),i18n:'butxn.tranCur',isMainCondition:true},
	      {id:'tranAmt',eleType:'easyui-numberbox',dataType:'1',disName:$xcp.i18n('butxn.tranAmt'),i18n:'butxn.tranAmt',isMainCondition:true},
	      {id:'tranDate',eleType:'easyui-datebox',dataType:'2',disName:$xcp.i18n('commQuery.date'),i18n:'commQuery.date',isMainCondition:true,dateFamter:"YYYY-MM-DD"}
	];
	/**
	 * title 为原来datagrid 的title 此处需传递 并删除原来datagrid中的title
	 */
	var title = $xcp.i18n("transitTradeQuery.transitTradeTask");
	/**
	 * queryID 为用户保存自己的查看信息时，必要的字段
	 */
	var queryID = "transitTQ";
	/**
	 * initQueryCondition 为 初始化 查询数据函数
	 */
	var allparams = {
		title : title,
		options : options,
		callback : initQueryCondition,
		queryID : queryID,
		
	};
	$xcp.comQueryCondMgr.init(allparams);
});

//初始化数据
function initQueryCondition() {
	//交易名称下拉框
	$("#transitTQ_condition").find('#transitTQ_tradeNo').combobox({
		data : $xcp.getConstant("tradeNo"),
		textField : "tradeName",
		valueField : "tradeNo",
		panelHeight : 300
	});
	//执行机构下拉框
	$("#transitTQ_condition").find("#transitTQ_tranOrgNo").combobox({
		data : orgNo,
		textField : "name",
		valueField : "val",
		panelHeight : 200
	});
	//交易步骤下拉框
	$("#transitTQ_condition").find("#transitTQ_stepNo").combobox({
		data : $xcp.getConstant("queryPalan",{tabName:'WFSTP.STEPNO',lang:EAP_LANGUAGE}),
		textField : "val",
		valueField : "keyVal",
		panelHeight : 200
	});
	//币种
	$("#transitTQ_condition").find("#transitTQ_tranCur").combobox({
		data : $xcp.getConstant("cur"),
		textField : "name",
		valueField : "val",
		panelHeight : 200
	});
	
	// 绑定搜索框的搜索按钮
	$("#transitTQ_condition").find("#transitTQ_custName").xcpSrchBox({
		  lxOrFun : 'SRCHBOXPACRP', 
		  callback : function(rowIndex, rowData){
			  custSearchResultDbclick(rowIndex, rowData);
		},params : {

		  }
		});
	
	$("#transitTQ_custName").bind('change.pending', function() {
		if($(this).xcpVal().length <= 0){
			$("#transitTQ_custId").xcpVal("");
			$("#custId").xcpVal("");
		}
		
	});
};
// 客户搜索框 的回调函数
function custSearchResultDbclick(rowIndex,rowData){
	var custName = null;
	//中文环境 拿中文名称
	if(EAP_LANGUAGE == 'zh_CN'){
		custName = rowData.pacrp_cnName;
	}else{
		//其他环境拿英文名称
		custName = rowData.pacrp_enName;
	}
	$("#transitTQ_condition").find("#transitTQ_custName").searchbox("setValue",custName);
	//客户id
	var custId = rowData.pacrp_corpNo;
	$("#transitTQ_condition").find("#transitTQ_custId").val(custId);
};