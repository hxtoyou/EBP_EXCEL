<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@include file="/platform/page/system/head.jsp"%>
<%@taglib uri="/WEB-INF/eap.tld" prefix="eap"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>

<script type="text/javascript" src="<%=webAppPath%>platform/js/jquery-core/jquery-1.8.0.min.js"></script> 
<script type="text/javascript" src="<%=webAppPath%>platform/js/share/include.js"></script>
<script type="text/javascript" src="<%=webAppPath%>myDocument/js/tools/commQueryCondition.js"></script>
<style type="text/css">
.editTdL{
	width: 20%; 
	text-align: right
}

.editTdR{
	width: 80%; 
	text-align: left
}
</style>
<script>
	//初始交易步骤弹出层
	$(function(){
		//工作流 任务信息显示窗口初始化
		$("#workflowTaskInfo2").window({
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
		$("#workflowTaskInfo2").datagrid({
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
	});
	//查询任务操作步骤的方法 
	var ButxnarQueryHelp2 = function($){
		/***/
		function _showWorkflowTaskInfo(txnNo){ 
			$("#workflowTaskInfo2").window("open");
			var aopt = {
				url:"./pendingTask.do?method=queryOpHis&txnNo="+txnNo
			};
			var rs = $xcp.ajax(aopt);
			if(rs == null || !rs || rs.length < 1){
				rs = {"rows":[]};
			}
			$("#workflowTaskInfo2").datagrid("loadData",rs);
		}
		
		return {
			showWorkflowTaskInfo:_showWorkflowTaskInfo
		};
	}(jQuery);
	
	//dataGrid查询参数对象
	var qrParams ={};
	//需要传递的参数列表
	var pendingTaskParam = ['txnNo','tradeNo','taskFlag','taskId','taskState'];
	
	//用于未完成任务的addWindow
	function addWindow(rowData){
		  rowData.taskFlag = rowData.stepNo;
		  url = './' + rowData.tradeName + '.do?method=getEditData';
		  $.each(pendingTaskParam,function(i,param){
			  url += "&" + param + "=" + (rowData[param] || '');
		  });
		  
		  url += "&queryFlag=query&workflow=off&overalParam="+(rowData.canBack||"");
		  
		  //打开窗口
		var task =  {
					url : url+"&tradeName="+encodeURI(encodeURI(rowData.tradeDesc))+"&bizNo="+rowData.currentBizNo+"&butxn_tranOrgNo="+rowData.tranOrgNo+"&butxn_tradeNo="+rowData.tradeNo,
					name : rowData.tradeDesc,
					txnNo : rowData.txnNo,
					tradeNo : rowData.tradeNo,
					taskFlag : rowData.stepNo || '',
					currentBizNo : rowData.currentBizNo,
					hasLeft : "N" //$xcp.task.isUpdateData(rowData.stepNo||"1") ? "Y" : "N"
				};
				
		$xcp.formPubMgr.openTrade(task);
	}
	
	//用于已完成任务的addWindow
	function addWindowForFinishedTask(rowData){
		
		  rowData.taskFlag = rowData.stepNo;
		  url = './' + rowData.tradeName + '.do?method=getEditData';
		  $.each(pendingTaskParam,function(i,param){
			  url += "&" + param + "=" + (rowData[param] || '');
		  });
		  
		  url += "&queryFlag=History";
		  
		  //打开窗口
		var task =  {
						url : url+"&tradeName="+encodeURI(encodeURI(rowData.tradeDesc))+"&bizNo="+rowData.currentBizNo+"&butxn_tranOrgNo="+rowData.tranOrgNo+"&butxn_tradeNo="+rowData.tradeNo,
						name : rowData.tradeDesc,
						txnNo : rowData.txnNo,
						tradeNo : rowData.tradeNo,
						taskFlag : rowData.stepNo || '',
						currentBizNo : rowData.currentBizNo,
						hasLeft : "N" //$xcp.task.isUpdateData(rowData.stepNo||"1") ? "Y" : "N"
					};
					
		$xcp.formPubMgr.openTrade(task);
	}
	
	/**
	*前端排序
	*desc:jquery easyui 前端插件中自定义排序函数，用于createTime字段的日期类型排序
	*param:strTime1 待进行排序比较的满足时间类型的格式的第一个字符串
	*	   strTime2 待进行排序比较的满足时间类型的格式的第二个字符串
	*return: 1 : 第一个时间格式的字符串比第二个时间格式的字符串要晚
	        -1 : 第一个时间格式的字符串比第二个时间格式的字符串要早
	*/
	function fnDataGridCreateTimeSorter(strTime1,strTime2){
		strTime1 = new Date(strTime1.replace(/-/g,"/"));
		strTime2 = new Date(strTime2.replace(/-/g,"/"));
		return ( strTime1 > strTime2 ) ? 1 : -1 ;
	}
	//“已处理任务”查询当前用户参与的任务，包括未完成、已完成的，初始化显示当日未完成、完成任务。
	//qrParams = {'crtDate': '{"type":"2","dateFamter":"","isChar":"","data":[{"matcher":">=","paramValue":"'+ $xcp.date.format( new Date() , "yyyy-MM-dd" ) +'"},{"matcher":"<=","paramValue":""}]}' } ;
	qrParams = {'crtDate': '{"type":"2","dateFamter":"","isChar":"","data":[{"matcher":">=","paramValue":"${eap_workday}"},{"matcher":"<=","paramValue":""}]}' } ;

	$(function(){
		$('#currentTask_letterTable').datagrid({
			loadMsg : $xcp.i18n("sys.loadMsg"),
			singleSelect: true,
			title: '',//$xcp.i18n("pendingTask.curTask"),
			url:$xcp.def.getFullUrl('./pendingTask.do?method=doList'),
			width: "900",
			height: "auto",
			fit   : true,
			fitColumns: false,
			queryParams : qrParams,
			sortOrder : "desc",
			remoteSort : true, //是否远程排序
			multiSort: true,
			pageSize: 10,            //每页显示的记录条数，默认为10  
		    pageList: [20,50,100],  //可以设置每页记录条数的列表  
		    columns:[[
						/* {title : '业务流水号', field : 'txnNo', width : 100, align : 'center'},
						{title : '任务ID', field : 'taskId', width : 100, align : 'center'},
						{title : '任务名称', field : 'stepName', width : 100, align : 'center'},
						{title : '交易名称', field : 'tradeDesc', width : 100, align : 'center'},
						{title : '创建时间', field : 'crtDate', width : 100, align : 'center'},
						{title : '业务编号', field : 'currentBizNo', width : 100, align : 'center'},
						{title : '币种', field : 'tranCur', width : 100, align : 'center'},
						{title : '金额', field : 'tranAmt', width : 100, align : 'center'}, */
						
						//执行机构
						{title : $xcp.i18n("pendingTask.tranOrgName"), field : 'tranOrgName', width : 100, align : 'center' , sortable : true },
						//交易名称
			          	{title : $xcp.i18n("pendingTask.tradeName"), field : 'tradeDesc', width : 100, align : 'center' , sortable : true },
			          	//发记方式
			          	{title : $xcp.i18n("pendingTask.launchmodeNo"), field : 'launchModeNo', width : 100, align : 'center', sortable : true, formatter: function(value, rowData){
			          		return (value && $.isNumeric(value)) ? $xcp.i18n("pendingTask.launchmodeNo."+value) : $xcp.i18n("pendingTask.launchmodeNo.1");
			          	} },
			        	//交易步骤
		          		{title : $xcp.i18n("pendingTask.stepName"), field : 'stepName', width : 100, align : 'center' , sortable : true ,
			          		formatter: function(value,row,index){
			    				if ( 1 == row.taskState ){ //用户已处理未完成交易列表
			    					if( value.indexOf('Pending') > -1 && ( row.stepNo == '4001' || row.stepNo == '4002' || row.stepNo == '4003') ){
		    		          			var stepQueue = $xcp.parseJson(row.vars).DoneStepQueue;
		    		          			var arr = stepQueue.split("@@");
		    		          			if( stepQueue != "" && arr.length > 0 ){
		    		          				return value +" "+ (arr.length+1);
		    		          			}
		    		          		}
			    					return row.stepName;
			    				} else if( 2 == row.taskState ){ //用户已完成任务列表
			    					return row.tranStateDesc;
			    				}
			    			}	
			          	},
			          	//客户名称
			          	{title : $xcp.i18n("butxn.custName"), field : 'custName', width : 100, align : 'center' , sortable : true },
			          	//业务编号
			          	{title : $xcp.i18n("pendingTask.bizNo"), field : 'currentBizNo', width : 100, align : 'center' , sortable : true },
			          	//币种
			          	{title : $xcp.i18n("pendingTask.tranCur"), field : 'tranCur', width : 100, align : 'center' , sortable : true },
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
			          	{title : $xcp.i18n("pendingTask.handler"), field : 'handlerCode', width : 100, align : 'center' , sortable : true },
			          	//复核员
			          	{title : $xcp.i18n("pendingTask.checker"), field : 'checkerCode', width : 100, align : 'center' , sortable : true },
			          	//创建日期
						//{title : $xcp.i18n("pendingTask.createTime"), field : 'crtDate', width : 100, align : 'center' , sortable : true, /*sorter : fnDataGridCreateTimeSorter*/ },
						//完成日期
						//{title : $xcp.i18n("pendingTask.completeTime"), field : 'endDate', width : 100, align : 'center' , sortable : true, /*sorter : fnDataGridCreateTimeSorter*/ },
			          	//交易流水
			          	/*** TASK #230 【浦发】任务列表增加2列显示经办、复核人，可以去掉流水号一列 begin ***/
						{title : $xcp.i18n("pendingTask.txnNo"), field : 'txnNo', width : 100, align : 'center' , sortable : true },
						/*** TASK #230 【浦发】任务列表增加2列显示经办、复核人，可以去掉流水号一列 end ***/
						{title : $xcp.i18n("quickquery.workflowInfo"), field : 'operate',width : 100,align : 'center', formatter:function(value, rowData, idx){
							var html = "";
							html  = "<div class='paramDmlOperaBtnWrap'><span class='mod' id='torkflowTask' onclick='ButxnarQueryHelp2.showWorkflowTaskInfo(\""+rowData.txnNo+"\");'></span></div>";
							return html;
						}},
						{title : '处理名称', field : 'tradeName', width : 100, align : 'center',hidden:true},
						{title : '任务状态', field : 'state', width : 100, align : 'center',hidden:true},
						{title : '交易编号', field : 'tradeNo', width : 100, align : 'center',hidden : true},
						{title : '', field : 'stepNo', width : 100, align : 'center',hidden:true},
						{title : 'mvc', field : 'mvc', width : 100, align : 'center',hidden : true}
					]],
			pagination:true,
			rownumbers:true,
			onLoadSuccess : function(){
				$.parser.parse( $(".paramDmlOperaBtnWrap") );
				top.$xcp.toolsMgr.bindBefUnLoad();
			},
			onDblClickRow: function(rowIndex, rowData){
				if( rowData["taskState"] == 2 ){ //已完成的任务
					//alert(rowData["taskId"]) ;
					//alert(rowData["tradeName"]) ; 
					addWindowForFinishedTask(rowData);
				}else{ //未完成的任务
					//alert("2 "+ rowData["taskId"]) ;
					//alert("2 "+ rowData["tradeName"]) ;
					addWindow(rowData);	
				}
			},
			onRowContextMenu : function(e, rowIndex, rowData){
				/** START-author:wangxinlin-如果任务申请了经办更正，提示*/
				if(configOveralObj.formMenu.taskListInit == '1'){
					/** START-author:wangxinlin-如果是自己经办的任务，可以申请经办更正*/
					onRowContextMenu(e, rowIndex, rowData);
					/** END-author:wangxinlin-如果是自己经办的任务，可以申请经办更正*/
				}
			}
		});
		/*** dateGrid表格数据加载完后清空queryParams查询参数 begin ***/
		//qrParams = {} ;
		$('#currentTask_letterTable').datagrid('options').queryParams = qrParams ;
		/*** dateGrid表格数据加载完后清空queryParams查询参数 end ***/
		
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
		      //交易状态
		      {id:'taskState',eleType:'easyui-combobox',dataType:'0',disName:$xcp.i18n('butxn.tranState'),i18n:'butxn.tranState',isMainCondition:true},
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
		      {id:'crtDate',eleType:'easyui-datebox',dataType:'2',disName:$xcp.i18n('commQuery.date'),i18n:'commQuery.date',isMainCondition:true}
		];
		/**
		 * title 为原来datagrid 的title 此处需传递 并删除原来datagrid中的title
		 */
		var title = " ";//$xcp.i18n("pendingTask.myTask");
		/**
		 * queryID 为用户保存自己的查看信息时，必要的字段
		 */
		var queryID = "currentTask";
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
		$("#currentTask_condition").find('#currentTask_crtDate0').xcpVal('${eap_workday}');
		
		//币种
		$("#currentTask_condition").find('#currentTask_tranCur').combobox({
			valueField: 'val',   
	        textField: 'name', 
	        data: $xcp.getConstant('cur'),
	        panelHeight : 200
		});
		
		//交易名称下拉框
		$("#currentTask_condition").find('#currentTask_tradeNo').combobox({
			data : $xcp.getConstant("tradeNo"),
			textField : "tradeName",
			valueField : "tradeNo",
			panelHeight : 300
		});
		
		// 所属 机构下拉框
		$("#currentTask_condition").find("#currentTask_tranOrgNo").combobox({
			data : $xcp.getConstant("transOrg"),
			textField : "orgName",
			valueField : "orgNo",
			panelHeight : 200
		});
		
		//状态
		$("#currentTask_condition").find('#currentTask_taskState').combobox({
			valueField: 'keyVal',   
	        textField: 'val', 
	        data : $xcp.getConstant("queryPalan",{tabName:'WFTSK.TASKSTATE',lang:EAP_LANGUAGE}),
	        panelHeight : 200
		});
		
		// 交易步骤下拉框 Palan表tabName字段值:BUTXNAR.TRANSTATE
		$("#currentTask_condition").find("#currentTask_stepNo").combobox({
			data : $xcp.getConstant("queryPalan",{tabName:'WFSTP.STEPNO',lang:EAP_LANGUAGE}),
			textField : "val",
			valueField : "keyVal",
			panelHeight : 200
		});
		
		// 绑定搜索框的搜索按钮
		/* $("#currentTask_condition").find("#currentTask_custName").searchbox({
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
						"SRCHBOXPACRP",// 查询客户帐的标识
						custSearchResultDbclick, //客户搜索结果集 双击选择事件
						param //查询参数对象
				);
			}
		}); */

		// 绑定搜索框的搜索按钮
		$("#currentTask_condition").find("#currentTask_custName").xcpSrchBox({
			  lxOrFun : 'SRCHBOXPACRP', 
			  callback : function(rowIndex, rowData){
				  custSearchResultDbclick(rowIndex, rowData);
			},params : {

			  }
		});

		$("#currentTask_custName").bind('change.pending', function() {
			if($(this).xcpVal().length <= 0){
				$("#currentTask_custId").xcpVal("");
				$("#custId").xcpVal("");
			}
			
		});
	};
	
	/** START-author:wangxinlin-如果是自己经办的任务，可以申请经办更正*/
	function onRowContextMenu(e, rowIndex, rowData){
		e.preventDefault();
		//alert($xcp.toJson(rowData)); 
		if( rowData.canBack == '1' ){
			$('#mmm div#gotoHandle').unbind("click.gotoHandle").bind('click.gotoHandle',function(){
				/*** 申请经办更正时需要录入申请经办更正意见 begin ***/
				top.window.$('body').xcpApp('createDialog',{
					title    : $xcp.i18n('formPubMgr.CorrectApplicaComment'),
					taskUuid : uuidHead,
					method   : "callBackGetCommentValue",
					inputLen : 200,
					rowData  : rowData,
					value    : $xcp.formPubMgr.defaults.sysOperateIdea,
					wTarget : "taskMgrMain-iframe-b"  });
				/*** 申请经办更正时需要录入申请经办更正意见 end ***/
			});
			
			$('#mmm').menu('show', {
				left : e.pageX,
				top : e.pageY
			});
		}
	}
	
	/**
	* desc:申请经办更正的回调函数(获取申请经办更正时填入的意见内容及申请经办更正的业务处理)
	* param:
	*	_correctApplicaComment 申请经办更正时填入的意见内容
	*	_opt                   回调时传递的opt参数对象
	*/
	function callBackGetCommentValue(_correctApplicaComment,_opt){
		if( _opt ){
			var rowData = _opt.rowData ;
			rowData["correctApplicaComment"] = _correctApplicaComment ; //申请经办更正时录入的申请经办更正意见
			//申请 经办更正
			$xcp.menuMgrFun.correctionsInit("apply",rowData,function(){
				$('#currentTask_letterTable').datagrid("reload") ;
			}) ;
		}
	}
	/** END-author:wangxinlin-如果是自己经办的任务，可以申请经办更正*/
	
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
		$("#currentTask_condition").find("#currentTask_custName").searchbox("setValue",custName);
		//客户id
		var custId = rowData.pacrp_corpNo;
		$("#currentTask_condition").find("#currentTask_custId").val(custId);
	};

	function doquery() {
		/* $('#letterTable').datagrid('options').queryParams = $.getJson('findtable', false);
		$("#letterTable").datagrid('reload'); */
	}
</script>
</HEAD>
<BODY style="background: #FFFFFF" id="currentTask_sysQueryList" class="easyui-layout" border=false>
	<!-- 使用公共查询组件方式 begin -->
	<div id="currentTask_npanel" region="north" title=" " border="false" 
		data-options="tools:'#currentTask_paneltools'">
		<div id="currentTask_condition" class='commQueryCondWrap'>
			<!-- 查询条件输入区域 -->
			<div class='query'>
				<div class='querybutton'>
					<div class='text'></div>
					<div class='input' id="currentTask_queryDiv"></div>
				</div>
			</div>
		</div>
	</div>
	
	<div id="currentTask_cpanel" region="center" border="false"
		style="background-color: #FAFAF9;">
		<table id="currentTask_letterTable">
		</table>
		<div id="mmm" class="easyui-menu" style="width:120px;">
			<div id="gotoHandle" data-options="iconCls:'icon-edit'"><eap:language name="finishedTask.applyChange" type="global"/></div>
		</div>
	</div>
	
	<div id='currentTask_paneltools'>
		<div id='currentTask_commQueryIcon' class='commQueryUpicon'></div>
	</div>
	<!-- 使用公共查询组件方式 end -->
	
	
	<!-- 工作流任务信息区域 -->
	<table id="workflowTaskInfo2" style="position: none;"></table>
</BODY>
</HTML>