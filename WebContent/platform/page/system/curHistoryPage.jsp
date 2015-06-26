<%
	String bizNo = request.getParameter("curtBizNo")==null?"":request.getParameter("curtBizNo");
	String info = request.getParameter("info")==null?"":request.getParameter("info");
%>

<script>
	var bizNo = '<%=bizNo%>';
	var info = '<%=info%>'; //区分业务经办时的业务信息和快速查询的历史信息
	var param = $xcp.formPubMgr.getFormHiddenField();
	var preBizNo = param.butxn_preBizNo;
	if(info != "" && bizNo != ""){
		preBizNo = bizNo;
	} 
	$(function() {
		$("#historyTable").treegrid({
			url : $xcp.def.getFullUrl('./common.do?method=getHistory&butxn_preBizNo='+preBizNo+'&_parentId=0'),
			idField : 'id',
			treeField : 'tradeDesc',
			rownumbers : true,
			pagination : true,
			fitColumns : true,
			width : "900",
			height : "auto",
			fit : true,
			animate : true,
			pageSize: 10,//每页显示的记录条数，默认为10  
		    pageList: [5,10,20],
		    rowStyler:function(row){
		    	if(row.arflag == 'TP'){
		    		return 'background:#DCDAF1';
		    	}
		    },
			columns:[[  
	                    {field:'tradeDesc',title:$xcp.i18n("pendingTask.tradeName"),width:80,align:'left'},
						{field:'txnNo',title:$xcp.i18n("pendingTask.txnNo"),width:80,align:'center'},
						{field:'curtBizNo',title:$xcp.i18n("pendingTask.bizNo"),width:60,align:'center'},
						{field:'tranCur',title:$xcp.i18n("pendingTask.tranCur"),width:80,align:'center'},
						{field:'tranAmt',title:$xcp.i18n("pendingTask.tranAmt"),width:80,align:'center'},
						{field:'bbNum',title:$xcp.i18n("formPubMgr.viewBal"),width:80,align:'center',formatter:viewBalance},
						{field:'btNum',title:$xcp.i18n("formPubMgr.busCorrect"),width:80,align:'center',formatter:viewHandleAgain},
						{field:'tranState',title:$xcp.i18n("formPubMgr.tranState"),width:80,align:'center',formatter:viewTranState},
						{field:'tradeNo',title:'',hidden:true,width:120,align:'center'},
						{field:'tradeName',title:'',width:60,align:'center',hidden:true},
						{field:'preBizNo',title:'',width:60,align:'center',hidden:true},
						{field:'state',title:'',width:60,align:'center',hidden:true},
						{field:'id',title:'',width:60,align:'center',hidden:true},
						{field:'arflag',title:'',width:60,align:'center',hidden:true},
						{field:'ismainTrade',width:60,align:'center',hidden:true},
						{field:'tranOrgNo',width:60,align:'center',hidden:true},
						{field:'maxVersion',width:60,align:'center',hidden:true},
	                ]], 
			onLoadSuccess : function() {
				 delete $(this).treegrid('options').queryParams['id'];
				 $('#historyTable').treegrid('collapseAll');
				 $.parser.parse($(".handleAgain"));
				 initHandleAgainData();
				 $.parser.parse($(".viewBal"));
				 initViewBal();
				
				
			},
			onBeforeExpand : function(row){
				if(row)
					$("#historyTable").treegrid("options").url = $xcp.def.getFullUrl('./common.do?method=getHistory&butxn_preBizNo='+row.curtBizNo+"&_parentId="+row.id);
			},
			onDblClickRow : function(row){
				var str = $("#historyTable").parent("div").find("table tr[node-id='"+row.id+"'] span.tree-folder");//noddatagrid-row-r3-2-2e
				if(row && row.state != "closed" && str.length == 0)
					addWindow(row);	
			}
		});
		$xcp.leftTreeMgr.addDatagridList("historyTable");
		
		
		var p = $('#historyTable').datagrid('getPager');
		$(p).pagination({
			onSelectPage : function(pageNumber, pageSize) {
				 $('#historyTable').treegrid("loadData",getPageData(pageNumber, pageSize));
			}
		});
		
		
	});
	
	//分页数据处理
	function getPageData(pageNumber, pageSize) {
		var data = {};
		$.ajax({
			type : "POST",
			url  : $xcp.def.getFullUrl('./common.do?method=getHistory&butxn_preBizNo='+preBizNo),
			data : {butxn_preBizNo:preBizNo,_parentId:"0",page:pageNumber,rows:pageSize},
			dataType : "json",
			async : false,
			success  : function(result) {
				if(result.success =='1'){
					$xcp.dispAjaxError($xcp.cloneObject(result));
					result = [];
				}else{
					data = result.outEntity;
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown){
				alert(textStatus);
			}
		});
		return data;
	}
	
	var pendingTaskParam = ['txnNo','tradeNo','taskFlag','taskId','state','stepNo'];
	//双击打开新的窗口
	function addWindow(rowData){
		  rowData.taskFlag = "7001";
		  url = './' + rowData.tradeName + '.do?method=getEditData&versionNo='+$("#"+rowData.id+"_"+rowData.btNum).xcpVal()+"&tradeName="+encodeURI(encodeURI( rowData.tradeDesc))+"&bizNo="+rowData.curtBizNo+"&butxn_tranOrgNo="+rowData.tranOrgNo;
		  //rowData.taskState = "2";//已完成
		  $.each(pendingTaskParam,function(i,param){
			  url += "&" + param + "=" + (rowData[param] || '');
		  });
		  
		  url += "&queryFlag=History&overalParam=1";
		  var id = rowData.id.substring(0, rowData.id.indexOf('_'));
		  
		  if(id == '999'){
			  url += "&taskState=1&correct=1&maxVersion="+rowData.maxVersion;
		  } else {
			  url += "&taskState=2";
		  }
		  //打开窗口
		  var task = 
				  {
			   		 url : url,
			   		 name : rowData.tradeDesc,
			   		 txnNo : info==""?rowData.txnNo:rowData.txnNo+"_"+info,
			   		 taskFlag : rowData.stepNo || '' ,
			   		 currentBizNo:rowData.curtBizNo,
			   		 hasLeft :  "N"
			   	  };
		  
		  $xcp.formPubMgr.openTrade(task);
		  
		  versionNo = "";
	}
	
	/**初始化经办更正信息页面元素*/
	function viewHandleAgain(value, row, index){
		var op = "";
		row.maxVersion = row.btNum; 
		if (row.state != "closed" && row.btNum != "0") {
			op = "<input id='"+row.id+"_"+row.btNum+"' class=\"easyui-combobox handleAgain\" style=\"width:45px\"/>";
		}
		
		return op;
	}
	
	function viewTranState(value, row, index){
		var state = "";
		if(value == ''){
			state = $xcp.i18n("formPubMgr.autoComplete");
		} else if(value == '1'){
			state = $xcp.i18n("formPubMgr.handle");
		} else if(value == '2'){
			state = $xcp.i18n("formPubMgr.check");
		} else if(value == '4'){
			state = $xcp.i18n("formPubMgr.complete");
		} else if(value == '5'){
			state = $xcp.i18n("formPubMgr.cancel");
		} else if(value == '6'){
			state = $xcp.i18n("formPubMgr.rollBack");
		}
		return state;
	}
	
	/**初始化经办更正信息页面元素数据*/
	function initHandleAgainData(){
		
		$.each($(".handleAgain"),function(i,o){
			var idIndex = o.id;
			var arr = idIndex.split("_");
			var count = arr[arr.length - 1];//[1];
			var len = count.length;
			var againData  = new Array();
			for(var i=count; i>0; i--){
				var data = {};
				var fullVal = getFullVal(i,len);
				data.no = fullVal;
				data.name = fullVal;
				againData.push(data);
			}
			
			$(o).combobox({
				data : againData,
				valueField : 'no',
				textField : 'name'
			});
			
			if(againData.length > 0){
				$(o).combobox("select",$(o).combobox('getData')[0].no);
			}
			
		});
	}
	function getFullVal(i,len){
		var val = ""+i;
		for(var i= len - (i+"").length; i>0; i--){
			val = "0"+val;
		}
		return val;		
	}
	
	/**初始化查看余额信息按钮*/
	function viewBalance(value, row, index){
		var s = "";
		if (row.bbNum != '0' && row.state != "closed"  ) {
			s += '<div class="viewBal"><a class="easyui-linkbutton" data-options="plain:true,iconCls:\'icon-view\'" onclick="" name= "'
			+row.tradeDesc +'" txnNo="'+row.txnNo+'" curtbizno="'+row.curtBizNo+'" >'+$xcp.i18n("historyInfo.viewBal")+'</a></div>';
		}
		return s;
	}
	
	/**余额查询*/
	function initViewBal(){
		$(".viewBal").unbind("click.viewBal").bind("click.viewBal",function(){
				setTimeout(function(){
					var txnNo = "";
					var row = $("#historyTable").treegrid("getSelected");
					if(row != null){
						txnNo = row.txnNo;
					}
					var kscxHtml = "<div id=\"bal\" class=\"easyui-window\"></div>";
					$(kscxHtml).appendTo($("body"));
					var url =$xcp.def.getFullUrl('./platform/page/system/balance.jsp?txnNo='+txnNo);
					$.get(url,function(data){
						$("#publicTradeBal").html(data);
						$.parser.parse($("#publicTradeBal"));
					});	
					$("#publicTradeBal").window({
						modal:true,
						closed:false,
						title:$xcp.i18n("historyInfo.BalInfo"),
						inline : true,
						collapsible : false,
						width:660,
						height:480,
						minimizable : false,
						onClose     : function(){
							$(this).window('destroy');
						}
					});
				},500);
				
		});	
	}
</script>

<table id="historyTable">
</table>
