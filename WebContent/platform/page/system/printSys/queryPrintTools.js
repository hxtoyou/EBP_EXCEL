
//批量打印
function batchPrintProcess(ptC){
	var data = ptC.getData();
	var str = doPrint(data, ptC);
	if (str && str=="done"){
		resetPrintstate(ptC.getIds());
		ptC.clearIds();
		ptC.reloadGrid();
	}
}

//打印方法  data打印数据      ptC打印控制台,为了避免并发打印
function doPrint(data, ptC){
	if(data == null || data.length==0){
		return "done";
	}
	if( ptC && !ptC.getIds() ){
		top.$xcp.batchPrintMgr.stopPrint();
		top.$xcp.batchPrintMgr.clear();
		return "done";
	}
	if (ptC){ 
		ptC.cutIdCount();
		if (ptC.getIdCount()<=0){ 
			resetPrintstate(ptC.getIds());
			ptC.clearIds();
			//ptC.reloadGrid();
		}
	}
	var o = data.shift();
	top.$xcp.batchPrintMgr.clear();
	var msg = "";
	var isaddcount = false;
	if (ptC && ptC.getTabname().toLowerCase()=="pttaskar"){
		isaddcount = true;
	}
	
	function cb(k, status){
		if(status){
			$.messager.alert($xcp.i18n("paramMar.tisxx"), $xcp.i18n("print.failure") + ":" +$xcp.i18n("print.txnNo") + "=" + k.ptTxnNo + ",status=" + status);
			doPrint(data, ptC);
			return ;
		}
		
		if (isaddcount){
			//如果是已打列表,需要数据打印次数加1
			dateNumber(k.ptTxnNo);
			doPrint(data, ptC);
		}else{
			//如果是待打列表,需要处理数据迁移
			var stut = dateShift(k.ptTxnNo);
			if(stut == undefined){
				$.messager.alert($xcp.i18n("paramMar.tisxx"), $xcp.i18n("print.failure"));
				//top.$xcp.batchPrintMgr.stopPrint();
				doPrint(data, ptC);
				return ;
			}else{
				//var nt = $("#queryPrintList_letterTable").datagrid("getRowIndex",k);
				//$("#queryPrintList_letterTable").datagrid('deleteRow',nt);
				doPrint(data, ptC);
			}
			msg += k.filePath + " is process \n";
		}
	}
	function fcb(nt3){
		if (msg)
		$.messager.alert($xcp.i18n("paramMar.tisxx"),msg);
		msg = "";
		if (ptC){
			if (ptC.getIdCount()<=0){ 
				ptC.reloadGrid();
			}
		}
	}
	top.$xcp.batchPrintMgr.add([o],cb,fcb);
}

/**
 * 数据迁移
 */
function dateShift(ptTxnNo){
	return $xcp.ajax({
		   url   : $xcp.def.getFullUrl("printSys.do?method=dataShift"),
		   data : {"ptTxnNo":ptTxnNo}
	});
}

/**打印列表控制帮助方法*/
function PrintC(tabid){
	var _tabid = tabid;
	var _tabname = "";
	var ids = "";
	var data = [];
	var pt = "";
	var idcount = 0;
	//设置打印表名
	function setTabname(tabname){
		_tabname = tabname;
	}
	//取得打印表名
	function getTabname(){
		return _tabname;
	}
	//记录选中打印id串
	function loadIds(){
		data = [];
		ids = "";
		$.each($("#"+_tabid+"").datagrid('getSelections'), function(i,k){
			data.push(k);
			ids = ids+pt+k.ptTxnNo; pt = ',';
			idcount ++;
		});
		pt = "";
	};
	//获取选中的data
	function getData(){
		return data;
	}
	//获取记录的选中打印id串
	function getIds(){
		return ids;
	};
	//清除id串记录
	function clearIds(){
		ids = "";
		idcount = 0;
	}
	//记数减少
	function cutIdCount(){
		idcount--;
		if (idcount <0){
			clearIds();
		}
	}
	function getIdCount(){
		return idcount;
	}
	
	//重新加载表格
	function reloadGrid(){
		$("#"+_tabid+"").datagrid('reload');
	}
	
	function reloadPrintC(){
		ids = "";
		data = [];
		idcount = 0;
	}
	
	return {
		loadIds : function(){loadIds();},
		getIds : function(){return getIds();},
		clearIds : function(){clearIds();},
		cutIdCount : function(){cutIdCount();},
		getIdCount : function(){return getIdCount();},
		setTabname : function(tabname){setTabname(tabname);},
		getTabname : function(){return getTabname();},
		getData : function(){return getData();},
		reloadGrid : function(){reloadGrid();},
		reloadPrintC : function(){reloadPrintC();}
	};
}



//启动打印
function batchPrint(ptC){
	//查看是否在PrintC对像中记录了正在处理的id串,如果有表示已经启动了打印
	if ( ptC.getIds() ){
		$.messager.alert($xcp.i18n("sys.tip"),$xcp.i18n("printSys.printing"));
		return;
	}
	
	//将列表选中的Id串记入到PrintC中,打印完成和停止需要这个id串做空闲处理
	ptC.loadIds();
	var ids = ptC.getIds();
	//如果没有ID串,提示选择记录
	if (!ids){
		$.messager.alert($xcp.i18n("sys.tip"),$xcp.i18n("print.selectRecord"));
		return;
	}
	
	//查看选中的处理记录是否有被占用(待打列表查tp,已打列表查ar)
	var tabname = ptC.getTabname();
	var optAjax = {
			url:$xcp.def.getFullUrl('./printConsole.do?method=printC'),
			data :{"action":"req", "tab":tabname, "ids": ids}
	};

	$xcp.ajax(optAjax,null,null,function(rs,result){
		if( rs.length > 0 ){
			$.messager.alert($xcp.i18n("sys.tip"),$xcp.i18n("printSys.printoccup"));
			//启动打印不成功,重置打印控制对象
			ptC.reloadPrintC();
			return ;
		}
	
		//开启打印控制全局变量
//		printFlag = true;
		batchPrintProcess(ptC);
	});
	
}

//停止打印
function stopPrint(ptC){
	var ids = ptC.getIds();
	if(ids){
		$.messager.confirm($xcp.i18n("sys.tip"), $xcp.i18n("printSys.stopOk"), function(r){
			 if (r){
//				 printFlag = false;
				 resetPrintstate(ids);
				 //ptC.clearIds();
				 ptC.reloadPrintC();
			 }
		});
	}else{
		$.messager.alert($xcp.i18n("sys.tip"), "没有打印开启");
	}
}

//重置打印状态为空闲
function resetPrintstate(ids){
	//重置打印任务状态
	var optAjax = {
			url:$xcp.def.getFullUrl('./printConsole.do?method=printC'),
			data :{"ids": ids, "action":"reset"}
	};
	$xcp.ajax(optAjax,null,null,function(rs,result){
		//打印完成
//		$.messager.alert("提示窗口","打印完成");
		return;
	});
}

//打印次数加
function dateNumber(ptTxnNo){
	return $xcp.ajax({
		url : $xcp.def.getFullUrl("printConsole.do?method=printC"),
		data : {"action":"addnum", "ptTxnNo":ptTxnNo}
	});
}
