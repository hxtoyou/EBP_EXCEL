<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/eap.tld" prefix="eap"%>
<div class="easyui-layout" data-options="fit:true,border:false">
		<div id="querySearch_npanel" region="north" style="height: 85px;border:none;">
				<div id="querySearch_condition" class='commQueryCondWrap'>
					<!-- 查询条件输入区域 -->
				
					<div class='query'>
						<div class='querybutton'>
							<div class='text'></div>
							<div class='input' id="querySearch_queryDiv"></div>
						</div>
					</div>
				</div>
			</div>
			<div id="querySearch_cpanel" region="center" border="false"
				style="background-color: #FAFAF9;">
				<table id="querySearch_letterTable">
				</table>
			</div>
			<div data-options="region:'south'" style="height: 125px;border:none;">
				<div id="opBtnContainer"></div>
			</div>
</div>
<script>
$(function(){

	//业务编号、交易名称、执行机构、客户名称、币种、金额
	$("#querySearch_letterTable").datagrid({
		loadMsg : $xcp.i18n("sys.loadMsg"),
		singleSelect: true,
		url:$xcp.def.getFullUrl('./commonQuery.do?method=quickQuery'),
		width: "900",
		height: "auto",
		title: $xcp.i18n("quickquery.qresult"),
		fit   : true,
		fitColumns: true,
		pageSize: 10,            //每页显示的记录条数，默认为10  
	    pageList: [5,10,15,20],  //可以设置每页记录条数的列表  
		columns:[[
					{title : $xcp.i18n("pendingTask.bizNo"), field : 'curtBizNo', width : 100, align : 'center'},
					{title : $xcp.i18n("pendingTask.tradeName"), field : 'tradeDesc', width : 100, align : 'center'},
					{title : $xcp.i18n("pendingTask.tranOrgName"), field : 'tranOrgName', width : 100, align : 'center'},
					{title : $xcp.i18n("pendingTask.corpName"), field : 'cnName', width : 100, align : 'center'},
					{title : $xcp.i18n("pendingTask.tranCur"), field : 'tranCur', width : 100, align : 'center'},
					{title : $xcp.i18n("pendingTask.tranAmt"), field : 'tranAmt', width : 100, align : 'right', formatter: function(value, rowData){
						/** author:wangxinlin 金额字段进行格式化，并且居右对齐 */
						if( value && rowData && rowData.tranCur ){
							return $xcp.def.formatterAmt(value, rowData.tranCur);
						}
						return value;
					}},
					{title : $xcp.i18n("quickquery.optBtn") ,field:'tranOrgNo',width:80,align:'center',formatter:enterBtnfmt},
					//{title : '', field : 'tranOrgNo', width : 100, align : 'center',hidden:true},
					{title : '业务流水', field : 'txnNo', width : 100, align : 'center',hidden:true},
					{title : '交易编号', field : 'tradeNo', width : 100, align : 'center',hidden:true},
					{title : 'mvc', field : 'tradeName', width : 100, align : 'center',hidden:true}

				]],
		pagination:true,
		rownumbers:true,
		onClickRow : function(rowIndex,rowData){
			displayValidTradeBtns(rowIndex,rowData);
		},
		onDblClickRow: function(rowIndex, rowData){
			//addWindow(rowData);	
		},
		onLoadSuccess : function(){
			$.parser.parse($(".enterBtnfmt"));
			initEnterBtnfmt();
		}
	});		
		var options = [
		     	      {id:'curtBizNo',eleType:'easyui-validatebox',dataType:'0',disName:$xcp.i18n('quickquery.bizNo'),i18n:'quickquery.bizNo',isMainCondition:true},
		     	      {id:'cnName',eleType:'easyui-validatebox',dataType:'0',disName:$xcp.i18n('pendingTask.corpName'),i18n:'pendingTask.corpName',isMainCondition:true},
		     	      {id:'tradeNo',eleType:'easyui-combobox',dataType:'0',disName:$xcp.i18n('pendingTask.tradeName'),i18n:'pendingTask.tradeName',isMainCondition:true}
		     	  ];
		     	/**
		     	 * title 为原来datagrid 的title 此处需传递 并删除原来datagrid中的title
		     	 */
		     	var title = "";
		     	/**
		     	 * queryID 为用户保存自己的查看信息时，必要的字段
		     	 */
		     	var queryID = "querySearch";
		     	/**
		     	 * initQueryCondition 为 初始化 查询数据函数
		     	 */
		     	var allparams = {
		     		title : title,
		     		options : options,
		     		callback : initQueryCondtion,
		     		queryID : queryID,
		     		
		     	};
		     	$xcp.comQueryCondMgr.initBeforeLoadMethod(allparams);


});

/**初始化进入操作按钮*/
function enterBtnfmt(value, row, index){
	var s = "";
	s += '<div class="enterBtnfmt"><a class="easyui-linkbutton" data-options="plain:true,iconCls:\'icon-view\'"></a></div>';
	return s;
}

/**操作按钮事件绑定*/
function initEnterBtnfmt(){
	$(".enterBtnfmt").unbind("click.enterBtnfmt").bind("click.enterBtnfmt",function(){
		setTimeout(function(){
			var row = $("#querySearch_letterTable").datagrid("getSelected");
			if(row != null){
				addWindow(row);
			}
		
		},500);
	});
}

var pendingTaskParam = ['txnNo','tradeNo','taskFlag','taskId','curtBizNo','taskState','tradeName','cnName'];
//需要传递的参数列表
function addWindow(rowData){
	
	  var url  =$xcp.def.getFullUrl(rowData.tradeName+'.do?method=getInfo&queryFlag=History&taskState=2&txnNo='+rowData.txnNo);
	
	  var task =  {
				url : url+"&tradeName="+encodeURI(encodeURI(rowData.tradeDesc))+"&bizNo="
						+rowData.curtBizNo+"&butxn_tranOrgNo="+rowData.tranOrgNo+"&butxn_tradeNo="+rowData.tradeNo+"&overalParam=q&latestFlag=1",
				name : rowData.tradeDesc,
				txnNo : rowData.txnNo,
				tradeNo : rowData.tradeNo,
				taskFlag : rowData.stepNo || '',
				currentBizNo : rowData.currentBizNo,
				hasLeft : "N" //$xcp.task.isUpdateData(rowData.stepNo||"1") ? "Y" : "N"
			};
			
	  $xcp.formPubMgr.openTrade(task);
} 

function initQueryCondtion(){
	$('#querySearch_condition').find('#querySearch_tradeNo').combobox({
		data : $xcp.getConstant("tradeNo",{isMainTrade:"Y"}),
		textField : "tradeName",
		valueField : "tradeNo",
		panelHeight : 200
	});
}

/**
 * 单击记录返回可操作的交易信息
 */
function displayValidTradeBtns(rowIndex,rowData){
	
	$("#opBtnContainer").empty();
	
	$.ajax({
		url : $xcp.def.getFullUrl("commonQuery.do?method=getUserCanLaunchTrade"),
		data : {userId:$xcp.def.getUSERID(),tradeNo:rowData.tradeNo,"curtBizNo":rowData.curtBizNo},
		dataType : "JSON",
		async : false,
		success : function (data) {		
			if(data.success =='1'){
				$xcp.dispAjaxError($xcp.cloneObject(data));					
			}else{
				btnsObj = data.outEntity || {};
			}					
		},
		error : function (XMLHttpRequest, textStatus, errorThrown) {
			$.messager.alert("", textStatus || errorThrown, "error");
		}
	});
	
	var opBtns = "";
	if(btnsObj && btnsObj.length > 0 ){
		
		$.each(btnsObj,function(i,o){
				opBtns += "<input type='button' class='opbtn' id='"+o.tradeNo+"' value ='"+o.tradeDesc+"' name='"+o.tradeDesc+"' url='"+o.tradeName+".do?method=getHandle' tranOrgNo='"+o.tranOrgNo+"' />";
			}
		);
	}
	$(opBtns).appendTo($("#opBtnContainer"));
	
	var aList = $("#opBtnContainer").find(".opbtn");
	$.each(aList,function(j,b){
		$("#"+$(b).attr("id")).unbind("click").bind("click",function(){
			  //打开窗口(处理BUG #49 快速查询功能，第二层弹出层不需要，第一层可以完成满足。)
			  top.window.$('body').xcpApp('createTask',
						  {
					  		 txnNo : '',
					   		 url : $(b).attr("url")+"&tradeName="+encodeURI(encodeURI($(b).attr("name")))+"&bizNo="+rowData.curtBizNo+"&tradeNo="+$(b).attr("id")+"&butxn_tranOrgNo="+rowData.tranOrgNo,
					   		 name : $(b).attr("name"),
					   		 txnNo : "",
					   		 tradeNo : $(b).attr("id"),
					   		 taskFlag : "1001" ,//工作流步骤号
					   		 currentBizNo:''
					   	  }
			  );
		});
	});
}
</script>