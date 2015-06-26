/**
 * 交易查询js文件
 */

//需要传递的参数列表
var pendingTaskParam = [ 'txnNo', 'tradeNo'/*, 'taskFlag', 'taskId', 'state','stepNo', 'taskState'*/ ];

function addWindow(rowData) {
	rowData.taskFlag = rowData.stepNo;
	url = './' + rowData.tradeName + '.do?method=getEditData';
	$.each(pendingTaskParam, function(i, param) {
		url += "&" + param + "=" + (rowData[param] || '');
	});
	url = url+"&tradeName="+encodeURI(encodeURI(rowData.tradeDesc))+"&bizNo="+rowData.curtBizNo+"&butxn_tranOrgNo="+rowData.tranOrgNo;
	url += "&queryFlag=History&taskState=2";
	var obj = {
			url : url,
			name : rowData.tradeDesc,
			txnNo : rowData.txnNo,
			tradeNo : rowData.tradeNo,
			taskFlag : rowData.stepNo || '',
			currentBizNo : rowData.curtBizNo,
			hasLeft : "N"
	};
	//alert($xcp.toJson(str));
	// 打开窗口
	$xcp.formPubMgr.openTrade(obj);
//	top.window.$('body').xcpApp('createTask', str);
}


/**
 * 交易查询帮助类
 */
var ButxnarQueryHelp = function($){
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
			$("#tradeQuery_condition").find("#tradeQuery_custName").searchbox("setValue",custName);
			//客户id
			var custId = rowData.pacrp_corpNo;
			$("#tradeQuery_condition").find("#tradeQuery_custId").val(custId);
		};
		
		//初始化查询条件
		function ininQueryCondition() {
			//币种
			$('#tradeQuery_tranCur').combobox({
				valueField: 'val',   
		        textField: 'name', 
		        data: $xcp.getConstant('cur'),
		        panelHeight : 200
			});
			
			//交易名称下拉框
			$('#tradeQuery_tradeNo').combobox({
				data : $xcp.getConstant("tradeNo"),
				textField : "tradeName",
				valueField : "tradeNo",
				panelHeight : 300
			});
			// 所属 机构下拉框
			$("#tradeQuery_tranOrgNo").combobox({
				data : $xcp.getConstant("queryLowerOrg",{orgNo:$xcp.getConstant('user')[0].belongOrgNo}),
				textField : "orgName",
				valueField : "orgNo",
				panelHeight : 200
			});
			
			//前端过滤数据用于加载(交易状态（完成、取消、回滚）)
			var tranStateDatas = $xcp.getConstant("queryPalan",{tabName:'BUTXNAR.TRANSTATE',lang:EAP_LANGUAGE}) ;
			var addArr = new Array() ;
			if( tranStateDatas ){//过滤数据
				//查询范围为完成、取消（经办删除）、回滚的交易；
				var tranStateDatasLen = tranStateDatas.length ;
				for( var i = 0 ; i < tranStateDatasLen ; i++ ){
					var curTranStateObj = tranStateDatas[i] ;
					if( curTranStateObj ){
						var curTranStateObj_keyVal = parseInt( curTranStateObj["keyVal"] );
						if( curTranStateObj_keyVal == 4 || curTranStateObj_keyVal == 5 || curTranStateObj_keyVal == 6 ){
							addArr.push(curTranStateObj) ;
						}
					}
				}
			}
			// 状态下拉框
			$("#tradeQuery_tranState").combobox({
				data : addArr,
				textField : "val",
				valueField : "keyVal",
				panelHeight : 200
			});
			
			var excelColumns = $xcp.paramMar.defaults.queryJs.excelColumns;
			
			$('#linkButLskPrint').bind('click.fixed', function() {
				var queryComGridId = '#tradeQuery_letterTable'; //列表id对象
				
				qrParams = $(queryComGridId).datagrid('options').queryParams;	
				if(excelColumns == null || excelColumns == ""){
					columns = $(queryComGridId).datagrid('getColumnFields');
					var titles = "";
					for(var i = 0;i<columns.length - 1;i++){
						var coloptions = $(queryComGridId).datagrid('getColumnOption',columns[i]);
						titles += coloptions.title + ";";
					}
					if(titles!="") titles = titles.substring(0, titles.length-1);
					qrParams["dataNameList"] = titles;
				}
				else{
					qrParams["dataNameList"] = excelColumns;
				}
				 
				var opts = $('#tradeQuery_letterTable').datagrid('options');
				
				var realSendParam = $xcp.comQueryCondMgr.getParam("tradeQuery_");
				
				
				var jon = {"cols":
					[{"name":"prebizno","width":100,"align":"left","text":"主业务编码"},{"name":"curtbizno","width":100,"align":"left","text":"业务编码"},{"name":"tradeDesc","width":100,"align":"left","text":"交易名称"},
					 {"name":"findate","width":100,"align":"left","text":"任务完成日期"},
					 {"name":"trancur","width":100,"align":"left","text":"币种"},
					 {"name":"tranamt","width":100,"align":"left","text":"金额"},
					 {"name":"custname","width":100,"align":"left","text":"客户名称"},
					 {"name":"i18nTranstateName","width":100,"align":"left","text":"交易状态"},
					 {"name":"handler","width":100,"align":"left","text":"经办人"},
					 {"name":"checker","width":100,"align":"left","text":"复核人"},
					 {"name":"managerId","width":100,"align":"left","text":"经理"}]
				    ,"condition":[{"id":"prebizno","text":"主业务编码"},{"id":"curtbizno","text":"业务编码"},{"id":"tradeDesc","text":"交易名称"},{"id":"findate","text":"任务完成日期"},{"id":"trancur","text":"币种"},{"id":"tranamt","text":"金额"},{"id":"custname","text":"客户名称"},{"id":"i18nTranstateName","text":"交易状态"},{"id":"handler","text":"经办人"},{"id":"checker","text":"复核人"},{"id":"managerId","text":"经理"}]
				    ,"querySql":""};
				$xcp.paramMar.defaults.mvcName = "tradeQuery";
				jon["querySql"] = getExeclSql(opts,realSendParam);
				srchBoxExportData(qrParams,jon,"tradeQuery",opts);
			});
			
			function getExeclSql(opts,realSendParam){
				$xcp.ajax({
					url:opts.url, 
					data:$.extend({execlSql:"true",rows:opts.pageSize,page:opts.pageNumber,sort:opts.sortName,order:opts.sortOrder},realSendParam)}, null, null, function(data){
					setOpenSql(data.sql);
				});
				return $("#tradeQuery_sql").val();
			}
			
			var srchBoxExportData = function(qrParams,queryJs,mvcName,opts){
				if(null == qrParams){
					return false;
				}
				//var exportDataUrl = "./srchBox.do?method=exportData";
				$("<div style='display:hidden'><form id='_submitFormHid' action='"+$xcp.def.getFullUrl("./srchBox.do?method=exportData&fileName=" + mvcName)+"' target='hiddenIframe' method='post'></form><iframe name='hiddenIframe'  id='hiddenIframe'></iframe></div>").appendTo($("body"));
				if(null != qrParams && typeof qrParams != "undefined")
				{
					$.each(qrParams,function(i,k)
					{
						//exportDataUrl+="&"+i+"="+encodeURIComponent(k);
						$("<input id='" + i + "' name='" + i + "'></input>").appendTo($("#_submitFormHid"));
						$("#_submitFormHid #"+i).val(k);
					});
				}
				//exportDataUrl+="&sort="+sortName;
				$("<input id='" + "sort" + "' name='" + "sort" + "'></input>").val(opts.sortName).appendTo($("#_submitFormHid"));
				
				//exportDataUrl+="&order="+sortOrder;
				var i = "order";
				$("<input id='" + i + "' name='" + i + "'></input>").val(opts.sortOrder).appendTo($("#_submitFormHid"));
				
				var colNames = '[';
				var colKeys = [];
				var _spt = '';
				$.each(queryJs.cols,function(i,o){
					colNames += (_spt + (o.text) );
					colKeys.push((o.name));
					_spt = ',';
				});
				colNames += ']';
				
				/*exportDataUrl+="&querySql="+queryJs.querySql;
				exportDataUrl+="&condition="+$xcp.toJson(encodeURIComponent(queryJs.condition));
				exportDataUrl+="&colKeys="+$xcp.toJson(colKeys);
				exportDataUrl+="&colNames="+colNames;*/
				
				var i = "querySql";
				var k = queryJs.querySql;
				$("<input id='" + i + "' name='" + i + "'></input>").appendTo($("#_submitFormHid"));
				$("#_submitFormHid #"+i).val(k);
				
				var i = "condition";
				var k = $xcp.toJson((queryJs.condition));
				$("<input id='" + i + "' name='" + i + "'></input>").appendTo($("#_submitFormHid"));
				$("#_submitFormHid #"+i).val(k);
				
				var i = "colKeys";
				var k = $xcp.toJson((colKeys));
				$("<input id='" + i + "' name='" + i + "'></input>").appendTo($("#_submitFormHid"));
				$("#_submitFormHid #"+i).val(k);
				
				var i = "colNames";
				var k = colNames;
				$("<input id='" + i + "' name='" + i + "'></input>").appendTo($("#_submitFormHid"));
				$("#_submitFormHid #"+i).val(k);
				
				$("#_submitFormHid").submit();
				$("#_submitFormHid").remove();
			};
			
			// 绑定搜索框的搜索按钮
			$("#tradeQuery_custName").xcpSrchBox({
				  lxOrFun : 'SRCHBOXPACRP', 
				  callback : function(rowIndex, rowData){
					  custSearchResultDbclick(rowIndex, rowData);
				},params : {

				  }
				});
			// 绑定核心流水号的搜索按钮
			$("#tradeQuery_hxflserialno").xcpSrchBox({
				  lxOrFun : 'SRCHBUTALLYRECORDINFO', 
				  callback : function(rowIndex, rowData){
					  var hxflserialno = rowData.butallyrecordinfo_HXFLSERIALNO;
					  $("#tradeQuery_condition").find("#tradeQuery_hxflserialno").xcpVal(hxflserialno);
					  var txnserialno = rowData.butallyrecordinfo_TXNSERIALNO;
					  $('#tradeQuery_txnNo').val(txnserialno);
				},params : {

				  }
				});
			
			$("#tradeQuery_custName").bind('change.pending', function() {
				if($(this).xcpVal().length <= 0){
					$("#tradeQuery_custId").xcpVal("");
					$("#custId").xcpVal("");
				}
				
			});
			$("#tradeQuery_hxflserialno").bind('change.pending', function() {
				if($(this).xcpVal().length <= 0){
					$("#tradeQuery_txnNo").xcpVal("");
					$("#txnNo").xcpVal("");
				}else{
					var asd = {
							url:$xcp.def.getFullUrl("./tradeQuery.do?method=queryButallTxnNo"),
							data:{hxflserialno:$(this).xcpVal()}
					};
					var tr = $xcp.ajax(asd,"json");
					var txnNo = tr[0].txnNo;			
					$('#tradeQuery_txnNo').val(txnNo);
				}
				
			});
		};
		
		
		function setOpenSql(sqlVal){
			var opDocu = $("#tradeQuery_sql");
			if(opDocu.length == 0 ){
				$("<input/>").attr("id", "tradeQuery_sql").val(sqlVal).hide().appendTo("body");
				return;
			}
			opDocu.val(sqlVal);
		}
		
		//初始化绑定事件
		function initBindEvents(){
//			$("#recycleId").bind("click",function(){
//				$("#condition").slideUp("show");
//			});
//			$("#queryBtnId").bind("mousemove",function(){
//				$("#condition").slideDown("show");
//			});
//			// 查询按钮绑定事件
//			$('#searchBtn').bind('click', function() {
//				doQuery();
//			});
			
		};
		/**
		 * 查询按钮查询事件
		 * @param param
		 */
		function doQuery() {
		};
		
		//初始化表格数据加载
		function intiTableDataLoad(){
			$('#tradeQuery_letterTable').datagrid({
				loadMsg : $xcp.i18n("sys.loadMsg"),
				singleSelect: true,
				url:$xcp.def.getFullUrl('./tradeQuery.do?method=query'),
				width: "900",
				height: "auto",
				fit   : true,
				fitColumns: true,
				pageSize: 10,            //每页显示的记录条数，默认为10  
			    pageList: [5,10,15,20],  //可以设置每页记录条数的列表  
				columns:[[	
				          	{title : $xcp.i18n("butxn.preBizNo"), field : 'preBizNo', width : 100, align : 'center',"sortable" : true},
				          	{title : $xcp.i18n("butxn.curtBizNo"), field : 'curtBizNo', width : 100, align : 'center',"sortable" : true/*,styler:cellStyler*/},
				          	{title : $xcp.i18n("butxn.tradeName"), field : 'tradeDesc', width : 100, align : 'center',"sortable" : true},
				          	{title : $xcp.i18n("butxn.finDate"), field : 'finDate', width : 100, align : 'center',"sortable" : true},
				          	{title : $xcp.i18n("butxn.tranCur"), field : 'tranCur', width : 100, align : 'center',"sortable" : true},
							{title : $xcp.i18n("butxn.tranAmt"), field : 'tranAmt', width : 100, align : 'center',"sortable" : true,
				          		formatter: function(value, rowData){
									if( value && rowData && rowData.tranCur ){
										return $xcp.def.formatterAmt(value, rowData.tranCur);
									}
									return value;
								}
				          	},
							{title : $xcp.i18n("butxn.custName"), field : 'custName', width : 100, align : 'center',"sortable" : true},
							{title : $xcp.i18n("butxn.tranState"), field : 'i18nTranstateName', width : 100, align : 'center',"sortable" : true,
								formatter : tradeStateInfo
							},
							{title : $xcp.i18n("butxn.handler"), field : 'handler', width : 100, align : 'center',"sortable" : true/*hidden:true*/},
							{title : $xcp.i18n("butxn.checker"), field : 'checker', width : 100, align : 'center',"sortable" : true},
							{title : $xcp.i18n("butxn.managerId"), field : 'managerId', width : 100, align : 'center',"sortable" : true},
							{title : $xcp.i18n("pendingTask.txnNo"), field : 'txnNo', width : 100, align : 'center'},
							{title : $xcp.i18n("quickquery.workflowInfo"), field : 'operate',width : 100,align : 'center',
								formatter:lookOverWorkflowTaskInfo
							},{title : $xcp.i18n("quickquery.flowBtn"), field:'stepNo', width:80, align:'center', hidden:true,formatter: function(value, rowData, idx){
								/** author:wangxinlin 添加查看任务流程的按钮 */
								var s = "";
								s += '<div class="wflowBtn" myIdx="'+idx+'"><a class="easyui-linkbutton" data-options="plain:true,iconCls:\'icon-view\'"></a></div>';
								return s;
							}}
						]],
				pagination:true,
				rownumbers:true,
				remoteSort:true,
				sortName:"finDate",
			    sortOrder :"desc",
				onDblClickRow: function(rowIndex, rowData){
					addWindow(rowData);
				},onLoadSuccess : function(){
					/*$.parser.parse($(".wflowBtn"));
					$(".wflowBtn").unbind("click.wflowBtn").bind("click.wflowBtn",function(){
						var i = $(this).attr("myIdx");
						var datas = $('#tradeQuery_letterTable').datagrid('getRows');
						var txnNo = datas[i].txnNo;
						
						$xcp.ajax({url:$xcp.def.getFullUrl("./transitTradeQuery.do?method=queryNodes"), data:{"txnNo":txnNo}}, null, null, function(data){
							
							var stepNo = data.stepNos[data.stepNos.length-1];
							var nodeArw = sp = "";
							var templateNo = data.templateNo;
							for(var i=0; i<data.stepNos.length-1; i++){
								nodeArw += sp + data.stepNos[i];
								sp = ",";
							}
							
							var winId = "wfpWin";
							if(!$("#"+winId) || $("#"+winId).length == 0){
								var wfpHtml = "";
								wfpHtml += "<div id=\"" + winId + "\" title=\"  \">";
								wfpHtml += "<iframe id='wfpIframe' frameborder='0'  style='width:100%;height:100%;overflow-x:auto;overflow-y:auto'>";
								wfpHtml += "</iframe>";
								wfpHtml += "</div>";
								$(wfpHtml).appendTo("body");
							}
							$("#wfpIframe").attr("src",$xcp.def.getFullUrl("./transitTradeQuery.do?method=viewWFP") + "&templateNo="+ templateNo + "&stepNo="+stepNo+"&nodeArw="+nodeArw);
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
						
					});*/
				}
			});
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
		//查看工作流任务信息
		function lookOverWorkflowTaskInfo(value,rowData,rowIndex){
			var html = "";
				html  = "<div class='paramDmlOperaBtnWrap'>";
				html += 	"<span  class='mod' id='torkflowTask' onclick='ButxnarQueryHelp.showWorkflowTaskInfo(\""+rowData.txnNo+"\");'></span>";
			    html += "</div>";
			return html;
		};
		//查看交易状态信息
		function tradeStateInfo(value,rowData,rowIndex){
			// tradeState == 5 表示这笔交易的状态为取消,对于取消的状态 需要能查看到取消的原因
			if(rowData.tranState && rowData.tranState == '5'){
				// 以txnNo(流水号) 为id 拼成一个span 标签
				var html = "<span id='"+rowData.txnNo+"' class='easyui-tooltip' " +
						"data-options=\"position:'right'\,onShow : function(){" +
						"$(this).tooltip('tip').css({" +
						"backgroundColor :  '#666'," +
						"borderColor : '#666'," +
						"color:'#fff'" +
						"});}\" title='"+rowData.sysOperateIdea+"'> "+ value + "</span>";
				return html;
			}else{
				return value;
			}
		}
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
			//ininQueryCondition();
			//initBindEvents();
			var queryID = "tradeQuery";
			//初始化
			var options = [
							//执行机构
							{id:'tranOrgNo',eleType:'easyui-combobox',dataType:'0',disName:$xcp.i18n('butxn.orgName'),i18n:'butxn.orgName',isMainCondition:true},
							//交易名称
							{id:'tradeNo',eleType:'easyui-combobox',dataType:'0',disName:$xcp.i18n('butxn.tradeName'),i18n:'butxn.tradeName',isMainCondition:true},
							//交易状态
							{id:'tranState',eleType:'easyui-combobox',dataType:'0',disName:$xcp.i18n('butxn.tranState'),i18n:'butxn.tranState',isMainCondition:true},
							//客户名称
							{id:'custName',eleType:'easyui-searchbox',dataType:'0',disName:$xcp.i18n('butxn.custName'),i18n:'butxn.custName',isHaveHidden:true,hiddenId:'custId',isMainCondition:true},
							//业务编号
							{id:'curtBizNo',eleType:'easyui-validatebox',dataType:'0',disName:$xcp.i18n('quickquery.bizNo'),i18n:'quickquery.bizNo',isMainCondition:true},
							//币种
							{id:'tranCur',eleType:'easyui-combobox',dataType:'0',disName:$xcp.i18n('qp.curSign'),i18n:'qp.curSign',isMainCondition:true},
							//金额>=(公共查询组件commQueryCondition.js中对于金额会自动构建金额>=、金额<=、金额=)
							{id:'tranAmt',eleType:'easyui-numberbox',dataType:'1',disName:$xcp.i18n('commQuery.accountMore'),i18n:"commQuery.accountMore",isMainCondition:true},
							//开始日期、结束日期
							{id:'finDate',eleType:'easyui-datebox',dataType:'2',disName:$xcp.i18n('commQuery.date'),i18n:'commQuery.date',isMainCondition:true},
							{id:'hxflserialno',eleType:'easyui-searchbox',dataType:'0',disName:$xcp.i18n('butallyrecordinfo.HXFLSERIALNO'),i18n:'butallyrecordinfo.HXFLSERIALNO',isHaveHidden:true,hiddenId:'txnNo',isMainCondition:true}

//							{id:'tranOrgNo',eleType:'easyui-combobox',dataType:'0',disName:$xcp.i18n('butxn.orgName'),i18n:'butxn.orgName',isMainCondition:true},
//				     	    {id:'custName',eleType:'easyui-searchbox',dataType:'0',disName:$xcp.i18n('butxn.custName'),i18n:'butxn.custName',isHaveHidden:true,hiddenId:'custId',isMainCondition:true},
//				     	    {id:'tradeNo',eleType:'easyui-combobox',dataType:'0',disName:$xcp.i18n('butxn.tradeDes'),i18n:'butxn.tradeDes',isMainCondition:true},
//				     	    {id:'finDate',eleType:'easyui-datebox',dataType:'2',disName:$xcp.i18n('commQuery.date111'),i18n:'commQuery.date1111',isMainCondition:true,dateFamter:"YYYY-MM-DD",isChar:""},
//				     	    {id:'tranState',eleType:'easyui-combobox',dataType:'0',disName:$xcp.i18n('butxn.tranState'),i18n:'butxn.tranState',isMainCondition:true},
//				     	    {id:'tranAmt',eleType:'easyui-numberbox',dataType:'1',disName:"金额",i18n:"金额",isMainCondition:true}
			     	     ];
			var title = $xcp.i18n("main.tradeQuery");
			var allparams = {
				title : title,
				options : options,
				callback:ButxnarQueryHelp.ininQueryCondition,
				queryID:queryID
			};
			$xcp.comQueryCondMgr.init(allparams);
		};
		
		return {
			//初始化函数
			init:init,
			ininQueryCondition:ininQueryCondition,
			showWorkflowTaskInfo:showWorkflowTaskInfo
		};
}(jQuery);

$(function() {
	//初始化
	ButxnarQueryHelp.init();
	$xcp.stopPorcess();//关闭进度条窗口
});