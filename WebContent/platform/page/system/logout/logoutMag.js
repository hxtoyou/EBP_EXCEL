//注销管理帮助类
LogoutMagHelp = function($){
	
	//初始化
	function init() {
		loadOnlineUsers();
		
		var queryID = "logoutMgr";
		//初始化
		var options = [
			     	      {id:'orgNo',eleType:'easyui-combobox',dataType:'0',disName:$xcp.i18n('butxn.orgName'),i18n:'butxn.orgName',isMainCondition:true}
		     	     ];
		var title = $xcp.i18n("logoutMag.desc");
		var allparams = {title : title,options : options, callback:initQueryCondition,queryID:queryID};
		$xcp.comQueryCondMgr.init(allparams);
		
		
	};
	//初始化查询条件
	function initQueryCondition() {
		$("#logoutMgr_orgNo").combobox({
			data : $xcp.getConstant("belongOrg"),
			textField : "name",
			valueField : "val",
			panelHeight : 300
		});
	};
	
	 
	//加载在线用户数据
	function loadOnlineUsers(){
		$('#logoutMgr_letterTable').datagrid({
			loadMsg : $xcp.i18n("sys.loadMsg"),
			singleSelect: true,
			url:$xcp.def.getFullUrl('./logoutMag.do?method=queryUsersByOnline'),
			width: "900",
			height: "auto",
			fit   : true,
			fitColumns: true,
			pageSize: 10,            //每页显示的记录条数，默认为10  
		    pageList: [5,10,15,20],  //可以设置每页记录条数的列表  
			columns:[[	
			          	{title : $xcp.i18n('logoutMag.orgName'), field : 'orgName', width : 100, align : 'center',"sortable" : true},
			          	{title : $xcp.i18n('logoutMag.userName'), field : 'userName', width : 100, align : 'center',"sortable" : true},
						{title : $xcp.i18n("paramMar.caoz"), field : 'operate',width : 100,align : 'center',
							formatter:createLogOutUserButton
						}
					]],
			remoteSort:true,
		    sortOrder :"desc",
			pagination:true,
			rownumbers:true,
			onDblClickRow: function(rowIndex, rowData){
				//addWindow(rowData);
			}
		});
	};
	//创建注销用户的按钮
	function createLogOutUserButton(value,rowData,rowIndex){
		var html = "";
			html  = "<div class='paramDmlOperaBtnWrap'>";
			html += 	"<span class='del' id='torkflowTask' onclick='LogoutMagHelp.logOutUser(\""+rowData.logOutUserId+"\",\""+rowIndex+"\");'></span>";
		    html += "</div>";
		return html;
	};
	//注销指定用户,被注销用户的id(userId),需要删除的行
	function logOutUser(userId,delIndex){
//		operateId(USERID),操作员id(operateId)
		$.messager.confirm($xcp.i18n("paramMar.tixck"),$xcp.i18n('logoutMag.logoutMsg'),function(r){
		    if (r){
		    	//加载一下logoutOperate.jsp文件,执行一下里面的java代码,即可注销
		    	var url = $xcp.def.getFullUrl("./logoutMag.do?method=logoutOperate&operateId="+USERID+"&logoutUserId="+userId);
		    	//删除视图表中这行数据
		    	$('#tradeQueryTable').datagrid('deleteRow',delIndex);
		    	//注销操作
		    	//$(document).load(url);
		    	$xcp.ajax({
					url : url				
				},null,function(result){
				});
		    	setTimeout(function(){
		    		$('#logoutMgr_letterTable').datagrid("reload");
		    	},1000);
		    }
		});
	};
	
	return{
		init:init,
		logOutUser:logOutUser
	};
}(jQuery);



$(function(){
	//页面初始化
	LogoutMagHelp.init();
	//关闭进度条窗口
	$xcp.stopPorcess();
});
