<%
String txnNo = request.getParameter("txnNo")==null?"":request.getParameter("txnNo");
String optNo = request.getParameter("optNo")==null?"":request.getParameter("optNo");
%>
<script>
var txnNo = '<%=txnNo%>';
var optNo = '<%=optNo%>'; 
	$(function() {
		$("#interfaceInfo").datagrid({
			url : $xcp.def.getFullUrl('./interface.do?method=queryTradeIntfInfo&txnNo='+txnNo+'&optNo='+optNo),
			rownumbers : true,
			pagination : true,
			fitColumns : true,
			singleSelect: true,
			width : "900",
			height : "auto",
			fit : true,
			pageSize: 10,//每页显示的记录条数，默认为10  
		    pageList: [5,10,20],
			columns:[[  
	                    {field:'intfno',title:$xcp.i18n("itstu.intfno"),width:80,align:'left',hidden:true},
						{field:'txnno',title:$xcp.i18n("itstu.txnno"),width:80,align:'center'},
						{field:'tradedesc',title:$xcp.i18n("itstu.tradeno"),width:60,align:'center',hidden:true},
						{field:'tradeno',title:$xcp.i18n("itstu.tradeno"),width:60,align:'center',hidden:true},
						
						{field:'taskid',title:$xcp.i18n("itstu.taskid"),width:80,align:'center',hidden:true},
						{field:'stepname',title:$xcp.i18n("itstu.stepno"),width:80,align:'center'},
						{field:'optno',title:$xcp.i18n("itstucall.optno"),width:80,align:'center'},
						{field:'stepno',title:$xcp.i18n("itstu.stepno"),width:80,align:'center',hidden:true},
					
						{field:'intfid',title:$xcp.i18n("itstucall.intfid"),width:80,align:'center'},
						{field:'intfdesc',title:$xcp.i18n("itstucall.intfdesc"),width:80,align:'center'},
						{field:'startdate',title:$xcp.i18n("itstu.startdate"),width:120,align:'center'},
						{field:'state',title:$xcp.i18n("itstu.state"),width:60,align:'center',formatter:viewTranState},
						{field:'errorcode',title:$xcp.i18n("itstucall.errcode"),width:60,align:'center', formatter: function(value, row, idx){
							if( value && value.length > 0 ){
								return '<div class="btn_showerr"><button type="button" intfid="'+row.intfno+'">'+value+'</button><div>';
							}else if( row.state == '2' ){
								return '<div class="btn_showerr"><button type="button" intfid="'+row.intfno+'">EINTFSYS6001</button><div>';
							}
							return value;
						}}
	                ]], 
			onLoadSuccess : function() {
				$.parser.parse( $(".btn_showerr") );
				$('.btn_showerr button').unbind('click.intf').bind('click.intf', function(){
					var intfno = $(this).attr('intfid');
					
					$.ajax({
						type : "GET",
						url  : $xcp.def.getFullUrl('./interface.do?method=queryErr'),
						data : {'intfno': intfno},
						dataType : "json",
						async : false,
						success  : function(result) {
							var htmlid = "intf_div_showerr"+intfno+"";
							var errmsg = result.outEntity.errmsg || '';
							var html = "<div id=\""+htmlid+"\" class=\"easyui-window\"><p>"+errmsg+"</p></div>";
							
							$(html).appendTo($("body"));
							$("#"+htmlid+"").window({
								modal:true,
								closed:false,
								inline : true,
								collapsible : false,
								width:800,
								height:400,
								minimizable : false,
								title: $xcp.i18n("sys.errorMsg"),
								onClose  : function(){
									$(this).window('destroy');
								}
							 });
							 
						},
						error : function(XMLHttpRequest, textStatus, errorThrown){
							//alert(textStatus);
						}
					});
				});
			},
			onDblClickRow : function(row){
			}
		});
		
		var p = $('#interfaceInfo').datagrid('getPager');
		$(p).pagination({
			onSelectPage : function(pageNumber, pageSize) {
				 $('#interfaceInfo').treegrid("loadData",getPageData(pageNumber, pageSize));
			}
		});
		
		
		
		var strBtn = "<button type=\"button\" class=\"paramShowMoreBtn\" id=\"intfHisQuery\" style=\"margin-left: 800px; width:120px\" id=\queryIntf\">"+
			$xcp.i18n("itstu.intfHistoryQuery")+"</button>";
		$(strBtn).appendTo($("#northDiv"));
		
		$("#intfHisQuery").unbind("click.intf").bind("click.intf",function(){
			
			intfHisQuery();
			
		});
	});
	
	//分页数据处理
	function getPageData(pageNumber, pageSize) {
		var data = {};
		$.ajax({
			type : "POST",
			url  : $xcp.def.getFullUrl('./interface.do?method=queryTradeIntfInfo'),
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
	

	//接口调用状态 0：初始 1：成功 2：失败 3：运行中
	function viewTranState(value, row, index){
		var state = "";
		if(value == '0'){
			state = $xcp.i18n("intfState.init");
		} else if(value == '1'){
			state = $xcp.i18n("intfState.success");
		} else if(value == '2'){
			state = $xcp.i18n("intfState.fail");
		} else if(value == '3'){
			state = $xcp.i18n("intfState.procesing");
		}
		return state;
	}
	
	
	
	/**接口历史查询*/
	function intfHisQuery(){
		 var html = "<div id=\"anno\" class=\"easyui-window\"></div>";
		 var tbl = "<table id=\"infoList\" class=\"easyui-datagrid\"></table>";
		 $(html).appendTo($("body"));
		 $(tbl).appendTo("#anno");
		 $("#anno").window({
			modal:true,
			closed:false,
			inline : true,
			collapsible : false,
			width:800,
			height:400,
			minimizable : false,
			title:$xcp.i18n("itstu.intfHistoryQuery"),
			onClose     : function(){
				$(this).window('destroy');
			}
		 });
		 $("#infoList").datagrid({
				loadMsg : $xcp.i18n("sys.loadMsg"),
				singleSelect: true,
				url:$xcp.def.getFullUrl('./interface.do?method=queryTradeIntfInfo&txnNo='+txnNo),
				width: "400",
				height: "auto",
				fit   : true,
				fitColumns: true,
				pageSize: 5,            //每页显示的记录条数，默认为10  
			    pageList: [5,10,15,20],  //可以设置每页记录条数的列表  
			    columns:[[  
		                    {field:'intfno',title:$xcp.i18n("itstu.intfno"),width:80,align:'left',hidden:true},
							{field:'txnno',title:$xcp.i18n("itstu.txnno"),width:80,align:'center'},
							{field:'tradedesc',title:$xcp.i18n("itstu.tradeno"),width:60,align:'center',hidden:true},
							{field:'tradeno',title:$xcp.i18n("itstu.tradeno"),width:60,align:'center',hidden:true},
							
							{field:'taskid',title:$xcp.i18n("itstu.taskid"),width:80,align:'center',hidden:true},
							{field:'stepname',title:$xcp.i18n("itstu.stepno"),width:80,align:'center'},
							{field:'optno',title:$xcp.i18n("itstucall.optno"),width:80,align:'center'},
							{field:'stepno',title:$xcp.i18n("itstu.stepno"),width:80,align:'center',hidden:true},
						
							{field:'intfid',title:$xcp.i18n("itstucall.intfid"),width:80,align:'center'},
							{field:'intfdesc',title:$xcp.i18n("itstucall.intfdesc"),width:80,align:'center'},
							{field:'startdate',title:$xcp.i18n("itstu.startdate"),width:120,align:'center'},
							{field:'state',title:$xcp.i18n("itstu.state"),width:60,align:'center',formatter:viewTranState},
							{field:'errorcode',title:$xcp.i18n("itstucall.errcode"),width:60,align:'center', formatter: function(value, row, idx){
								if( value && value.length > 0 ){
									return '<div class="btn_showerr"><button type="button" intfid="'+row.intfno+'">'+value+'</button><div>';
								}
								return value;
							}}
		                ]], 
				pagination:true,
				rownumbers:true,
				onClickRow : function(){
				},
				onDblClickRow: function(rowIndex, rowData){
				},
				onLoadSuccess : function() {
					$.parser.parse( $(".btn_showerr") );
					$('.btn_showerr button').unbind('click.intf').bind('click.intf', function(){
						var intfno = $(this).attr('intfid');
						
						$.ajax({
							type : "GET",
							url  : $xcp.def.getFullUrl('./interface.do?method=queryErr'),
							data : {'intfno': intfno},
							dataType : "json",
							async : false,
							success  : function(result) {
								var htmlid = "intf_div_showerr"+intfno+"";
								var errmsg = result.outEntity.errmsg || '';
								var html = "<div id=\""+htmlid+"\" class=\"easyui-window\"><p>"+errmsg+"</p></div>";
								
								$(html).appendTo($("body"));
								$("#"+htmlid+"").window({
									modal:true,
									closed:false,
									inline : true,
									collapsible : false,
									width:800,
									height:400,
									minimizable : false,
									title:  $xcp.i18n("sys.errorMsg"),
									onClose  : function(){
										$(this).window('destroy');
									}
								 });
								 
							},
							error : function(XMLHttpRequest, textStatus, errorThrown){
								//alert(textStatus);
							}
						});
					});
				}
		 });

	}
</script>



<div style="background: #FFFFFF" id="interfaceLay" class="easyui-layout" border=false fit="true">
		<div data-options="region:'north'" style="height:30px" border="false" id="northDiv">
<!-- 			<button type="button" class="paramShowMoreBtn" id="intfHisQuery" style="margin-left: 800px; width:100px" id="queryIntf"></button>
 -->		</div>
	
		<div data-options="region:'center'" border="false">
			<table id="interfaceInfo">
			</table>
		</div>
	</div>
	
<SCRIPT>
  $("#intfHisQuery").innerText=$xcp.i18n("itstu.intfHistoryQuery");
</SCRIPT>