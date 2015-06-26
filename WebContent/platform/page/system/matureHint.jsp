<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@include file="/platform/page/system/head.jsp"%>
<%@taglib uri="/WEB-INF/eap.tld" prefix="eap"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>

<script type="text/javascript" src="<%=webAppPath%>platform/js/jquery-core/jquery-1.8.0.min.js"></script> 
<script type="text/javascript" src="<%=webAppPath%>platform/js/share/include.js"></script>
<script type="text/javascript" src="<%=webAppPath%>myDocument/js/tools/commQueryCondition.js"></script>
<script>	

	function addWindow(rowData){
		  rowData.ccc = '1001';
		  url = './' + rowData.tradename + '.do?method=getHandle';
		
		  url =  url+"&tradeName="+encodeURI(encodeURI(rowData.tradedesc))+"&bizNo="+rowData.ralbizno+"&tradeNo="+rowData.tradeno+"&butxn_tranOrgNo="+rowData.orgno,
		  //打开窗口
		  top.window.$('body').xcpApp('createTask',
				  {
			   		 url : url,
			   		 name : rowData.tradedesc,
			   		 txnNo : rowData.txnNo,
			   		 taskFlag : rowData.stepNo || '' 
			   	  }
		  );
	}
	$(function(){
		$('#matureHint_letterTable').datagrid({
			loadMsg : $xcp.i18n("sys.loadMsg"),
			singleSelect: true,
			title: '',
			url:$xcp.def.getFullUrl('./matureHint.do?method=query'),
			width: "900",
			height: "auto",
			fit   : true,
			fitColumns: true,
			columns:[[
						{title : '', field : 'txnno', width : 100, align : 'center',hidden:true},
						{title : '机构名称', field : 'orgname', width : 50, align : 'center'},
						{title : '', field : 'orgno', width : 50, align : 'center',hidden:true},
						{title : $xcp.i18n("pendingTask.bizNo"), field : 'ralbizno', width : 50, align : 'center'},
						{title : $xcp.i18n("pendingTask.tradeName"), field : 'tradedesc', width : 50, align : 'center'},
						{title : '', field : 'tradename', width : 50, align : 'center',hidden:true},
						{title : '', field : 'tradeno', width : 100, align : 'center',hidden:true},
						{title : $xcp.i18n("maturyHint.maturyInfo"), field : 'maturityinfo', width : 250, align : 'center'}
					]],
			pagination:true,
			rownumbers:true,
			onDblClickRow: function(rowIndex, rowData){
				addWindow(rowData);
			}
		});
		
		var queryID = "matureHint";
		//初始化
		var options = [
						//执行机构
						{id:'orgno',eleType:'easyui-combobox',dataType:'0',disName:$xcp.i18n('butxn.orgName'),i18n:'butxn.orgName',isMainCondition:true},
						//交易名称
						{id:'tradeno',eleType:'easyui-combobox',dataType:'0',disName:$xcp.i18n('butxn.tradeName'),i18n:'butxn.tradeName',isMainCondition:true},
						//客户名称
						//{id:'custName',eleType:'easyui-searchbox',dataType:'0',disName:$xcp.i18n('butxn.custName'),i18n:'butxn.custName',isHaveHidden:true,hiddenId:'custId',isMainCondition:true},
						//业务编号
						{id:'bizno',eleType:'easyui-validatebox',dataType:'0',disName:$xcp.i18n('quickquery.bizNo'),i18n:'quickquery.bizNo',isMainCondition:true}
						/* //币种
						{id:'tranCur',eleType:'easyui-combobox',dataType:'0',disName:$xcp.i18n('qp.curSign'),i18n:'qp.curSign',isMainCondition:true}, */
						
						//开始日期、结束日期
						//{id:'finDate',eleType:'easyui-datebox',dataType:'2',disName:$xcp.i18n('commQuery.date'),i18n:'commQuery.date',isMainCondition:true}


		     	     ];
		var title = $xcp.i18n("maturyHint.maturyTaskInfo");
		var allparams = {
			title : title,
			options : options,
			callback:ininQueryCondition,
			queryID:queryID
		};
		$xcp.comQueryCondMgr.init(allparams);
		
		//$("#matureHint_npanel").attr("tilte",$xcp.i18n("maturyHint.maturyTaskInfo"));
		
		
		
		
		$xcp.stopPorcess();//关闭进度条窗口
	});
	
	
	//初始化查询条件
	function ininQueryCondition() {
	
		
		//交易名称下拉框
		$("#matureHint_condition").find('#matureHint_tradeno').combobox({
			data : $xcp.getConstant("tradeNo"),
			textField : "tradeName",
			valueField : "tradeNo",
			panelHeight : 300
		});
		
		// 所属 机构下拉框
		$("#matureHint_condition").find("#matureHint_orgno").combobox({
			data : $xcp.getConstant("transOrg",{"orgType":"query"}),
			textField : "orgName",
			valueField : "orgNo",
			panelHeight : 200
		});
		
		
		// 绑定搜索框的搜索按钮
		$("#matureHint_condition").find("#matureHint_custName").xcpSrchBox({
			  lxOrFun : 'SRCHBOXPACRP', 
			  callback : function(rowIndex, rowData){
				  custSearchResultDbclick(rowIndex, rowData);
			},params : {

			  }
			});
		
		$("#matureHint_custName").bind('change.pending', function() {
			if($(this).xcpVal().length <= 0){
				$("#matureHint_custId").xcpVal("");
				$("#custId").xcpVal("");
			}
			
		});
	};

</script>
</HEAD>
<BODY style="background: #FFFFFF" id="matureHint_sysQueryList"  class="easyui-layout" border=false>

	<div id="matureHint_npanel" region="north" title="<eap:language name="main.maturyTaskInfo" type="main"/>" border="true"
		data-options="tools:'#matureHint_paneltools'">
				<div id="matureHint_condition" class='commQueryCondWrap'>
					<!-- 查询条件输入区域 -->
					<div class='query'>
						<div class='querybutton'>
							<div class='text'></div>
							<div class='input' id="matureHint_queryDiv"></div>
						</div>
					</div>
				</div>
			</div>
	
		<div id="matureHint_cpanel" region="center" border="false" style="background-color: #FAFAF9;">
			<table id="matureHint_letterTable" style="width:100%">
			</table>
		</div>
		<div id='matureHint_paneltools'>
			<div id='matureHint_commQueryIcon' class='commQueryUpicon'></div>
		</div>
</BODY>
</HTML>